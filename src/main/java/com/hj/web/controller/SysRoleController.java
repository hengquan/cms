package com.hj.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.common.ControllerBase;
import com.hj.utils.HashSessions;
import com.hj.utils.JsonUtils;
import com.hj.web.dao.SysItemRoleDao;
import com.hj.web.dao.SysRoleDao;
import com.hj.web.entity.Language;
import com.hj.web.entity.SysRole;
import com.hj.web.entity.UserInfo;
import com.hj.web.entity.UserRole;
import com.hj.web.mapping.SysRoleMapper;
import com.hj.web.services.IKeyGen;
import com.hj.web.services.LanguageService;
import com.hj.web.services.SysRoleService;
import com.hj.web.services.UserRoleService;

/**
 * @author deng.hemei
 * @date 创建时间：2016年6月17日 上午11:58:15
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@RequestMapping("/role")
@Controller
public class SysRoleController extends ControllerBase {

	private final static Logger logger = LoggerFactory.getLogger(SysRoleController.class);
	private HashSessions hashSession = HashSessions.getInstance();
	@Autowired
	SysRoleDao sysRoleDao;
	@Autowired
	IKeyGen key;
	@Autowired
	SysRoleMapper sysRoleMapper;
	@Autowired
	UserRoleService sysUserRoleService;
	@Autowired
	SysItemRoleDao sysItemRoleDao;
	@Autowired
	SysRoleService roleService;
	@Autowired
	LanguageService languageService;

	public String Userid() {
		try {
			Object obj = request.getSession().getAttribute("adminSession");
			if (null != obj) {
				UserInfo admin = (UserInfo) obj;
				return admin.getId();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	/**
	 * 进入管理员列表
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(ModelMap model) throws Exception {
		String pageUrl = "role/list";
		List<SysRole> roleList = new ArrayList<SysRole>();
		pageUrl = super.userIRoleItem(model, pageUrl);
		SysRole role = (SysRole) model.get("role");
		if (role != null) {
			String logogram = role.getLogogram();
			String id = role.getId();
			if (StringUtils.isNotEmpty(logogram) && logogram.equals("0")) {
				roleList = roleService.findMeAndParentList();
			} else {
				role = super.getParentRoleData(role);
				roleList = roleService.findParentById(id);
			}
			if (roleList != null && roleList.size() > 0) {
				for (SysRole sysRole : roleList) {
					// 处理语言
					sysRole = manage(sysRole);
					String roleId = sysRole.getId();
					List<SysRole> dataList = roleService.findParentById(roleId);
					if (dataList != null && dataList.size() > 0) {
						for (SysRole ziRole : dataList) {
							// 处理语言
							ziRole = manage(ziRole);
						}
						sysRole.setRoleList(dataList);
					}
				}
			}
		}
		model.addAttribute("roleList", roleList);
		return "role/list";
	}

	private SysRole manage(SysRole sysRole) {
		String languageNames = "";
		String languageId = sysRole.getLanguageId();
		List<Language> languageList = languageService.getByIds(languageId);
		if (languageList != null && languageList.size() > 0) {
			for (Language language : languageList) {
				String name = language.getName();
				if (StringUtils.isNotEmpty(name))
					languageNames += " " + name;
			}
		}
		if (StringUtils.isNotEmpty(languageNames))
			sysRole.setLanguageName(languageNames);
		return sysRole;
	}

	/**
	 * 添加管理员
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/addAndUpdate")
	public String addAndUpdate() throws Exception {
		String logogram = "";
		String roleid = "";
		SysRole loginRole = super.getUserRole();
		if (loginRole != null) {
			logogram = loginRole.getLogogram();
			roleid = loginRole.getId();
		}
		// 获取用户权限
		UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
		if (userRole != null) {
			roleid = userRole.getRoleid();
			if (StringUtils.isNotEmpty(roleid)) {
				SysRole role = roleService.findById(roleid);
				if (role != null) {
					logogram = role.getLogogram();
				}
			}
		}
		// 获取数据
		Map<String, Object> result = new HashMap<String, Object>();
		String editId = StringUtils.trimToEmpty(getTrimParameter("editId"));
		SysRole sysRole = new SysRole();
		String roleName = StringUtils.trim(getTrimParameter("roleName"));
		String pinyin = StringUtils.trim(getTrimParameter("pinyin"));
		String remark = StringUtils.trim(getTrimParameter("remark"));
		String languageId = StringUtils.trim(getTrimParameter("languageId"));

		boolean isSave = true;
		if (isSave) {
			if (StringUtils.isEmpty(editId)) {
				// 根据角色名称查询是否存在
				SysRole sr = sysRoleDao.findByRoleName(roleName);
				if ("".equals(sr) || sr == null) {
					String uuid = key.getUUIDKey();
					sysRole.setId(uuid);
					sysRole.setRoleName(roleName);
					sysRole.setPinyin(pinyin);
					if (StringUtils.isNotEmpty(logogram) && logogram.equals("0"))
						sysRole.setLogogram("1");
					else
						sysRole.setLogogram(roleid);
					sysRole.setRemark(remark);
					sysRole.setLanguageId(languageId);
					sysRoleDao.add(sysRole);
					logger.info("添加角色成功");
					result.put("msg", "isc");
				} else {
					// 添加角色失败，该角色名称已存在
					result.put("msg", "iscz");
				}
			} else {
				sysRole.setId(editId);
				sysRole.setRoleName(roleName);
				sysRole.setPinyin(pinyin);
				sysRole.setRemark(remark);
				sysRole.setLanguageId(languageId);
				sysRoleDao.update(sysRole);
				logger.info("修改角色成功");
				result.put("msg", "isupdate");
			}
		} else {
			result.put("msg", "error");
		}

		logger.info("===========添加或修改角色失败=============");

		return JsonUtils.map2json(result);
	}

	/**
	 * 删除管理员
	 * 
	 * @return
	 */
	@RequestMapping("/del")
	public String del() {
		String byid = StringUtils.trim(getTrimParameter("byid"));
		String boxeditId = StringUtils.trim(getTrimParameter("boxeditId"));
		if (byid != null && !byid.equals("")) {
			sysRoleDao.del(byid);
		} else {
			String[] str = boxeditId.trim().split(",");
			StringBuffer sb = new StringBuffer();
			String strs = "";
			for (String s : str) {
				sb.append("'" + s + "',");
			}
			strs = sb.toString().substring(0, sb.length() - 1);
			sysRoleDao.dels(strs);
		}
		return "redirect:/role/list";
	}

	/**
	 * type类型1-获取顶级权限下所有的权限列表不包含自己,否则获取顶级数据自己的数据
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAllList")
	@ResponseBody
	public String getAllList(Model model) throws Exception {
		String type = getTrimParameter("type");
		Map<String, Object> map = new HashMap<String, Object>();
		List<SysRole> roleList = new ArrayList<SysRole>();
		SysRole role = super.getUserRole();
		if (role != null) {
			String logogram = role.getLogogram();
			if (StringUtils.isNotEmpty(logogram) && logogram.equals("0")) {
				roleList = sysRoleDao.findParentList();
			} else {
				if (StringUtils.isNotEmpty(type) && type.equals("1")) {
					roleList = sysRoleDao.findParentById(role.getId());
				} else {
					role = super.getParentRoleData(role);
					roleList.add(role);
				}
			}
			if (roleList != null && roleList.size() > 0) {
				map.put("roleList", roleList);
				map.put("msg", "0");
			}
		}
		return JsonUtils.map2json(map);
	}

	@RequestMapping("/getLanguageData")
	@ResponseBody
	private Map<String, Object> getLanguageData() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Language> languageList = new ArrayList<Language>();
		String roleId = getTrimParameter("roleId");
		if (StringUtils.isNotEmpty(roleId)) {
			SysRole sysRole = roleService.findById(roleId);
			if (sysRole != null) {
				String languageId = sysRole.getLanguageId();
				languageList = languageService.getByIds(languageId);
			}
		} else {
			SysRole userRole = super.getUserRole();
			if (userRole != null) {
				String logogram = userRole.getLogogram();
				if (logogram.equals("0")) {
					languageList = languageService.getAllData();
				} else {
					String languageId = userRole.getLanguageId();
					languageList = languageService.getByIds(languageId);
				}
			}
		}
		if (languageList != null && languageList.size() > 0) {
			map.put("msg", "0");
			map.put("dataList", languageList);
		}
		return map;
	}
}
