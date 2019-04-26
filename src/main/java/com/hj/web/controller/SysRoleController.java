package com.hj.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.common.ControllerBase;
import com.hj.utils.HashSessions;
import com.hj.utils.JsonUtils;
import com.hj.web.dao.SysItemRoleDao;
import com.hj.web.dao.SysRoleDao;
import com.hj.web.entity.SysItemRole;
import com.hj.web.entity.SysRole;
import com.hj.web.entity.UserInfo;
import com.hj.web.entity.UserRole;
import com.hj.web.mapping.SysRoleMapper;
import com.hj.web.services.IKeyGen;
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
	 */
	@RequestMapping("/list")
	public String list(Model model) {
		List<SysRole> roleList = sysRoleDao.findAll();
		model.addAttribute("roleList", roleList);
		// model.addAttribute("itemNamesss",hashSession.getItemRole("itemNamesss"));
		// model.addAttribute("lst",hashSession.getItemRole("lst"));
		String itemId = super.getTrimParameter("itemId");
		String id = super.getTrimParameter("id");
		UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
		List<SysItemRole> lst = sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
		List<SysItemRole> item = sysItemRoleDao.selectItemByPId(userRole.getRoleid());
		model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
		model.addAttribute("itemId", itemId);
		model.addAttribute("id", id);
		logger.info("查询所有角色信息");
		return "role/list";
	}

	/**
	 * 添加管理员
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addAndUpdate")
	public String addAndUpdate() {
		Map<String, Object> result = new HashMap<String, Object>();
		String editId = StringUtils.trimToEmpty(getTrimParameter("editId"));
		SysRole sysRole = new SysRole();
		String roleName = StringUtils.trim(getTrimParameter("roleName"));
		String pinyin = StringUtils.trim(getTrimParameter("pinyin"));
		String logogram = StringUtils.trim(getTrimParameter("logogram"));
		String remark = StringUtils.trim(getTrimParameter("remark"));

		boolean isSave = true;
		if (isSave) {
			if (editId == null || editId.equals("")) {
				// 根据角色名称查询是否存在
				SysRole sr = sysRoleDao.findByRoleName(roleName);
				if ("".equals(sr) || sr == null) {
					sysRole.setId(key.getUUIDKey());
					sysRole.setRoleName(roleName);
					sysRole.setPinyin(pinyin);
					sysRole.setLogogram(logogram);
					sysRole.setRemark(remark);
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
				sysRole.setLogogram(logogram);
				sysRole.setRemark(remark);
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

	@RequestMapping("/getAllList")
	@ResponseBody
	public String getAllList(Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SysRole> roleList = sysRoleDao.findAll();
		if(roleList!=null && roleList.size()>0){
			map.put("roleList", roleList);
			map.put("msg", "0");
		}else{
			map.put("msg", "1");
		}
		return JsonUtils.map2json(map);
	}

}
