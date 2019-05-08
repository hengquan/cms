package com.hj.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hj.common.ControllerBase;
import com.hj.utils.HashSessions;
import com.hj.web.dao.SysItemDao;
import com.hj.web.dao.SysItemRoleDao;
import com.hj.web.dao.SysRoleDao;
import com.hj.web.entity.SysItem;
import com.hj.web.entity.SysItemRole;
import com.hj.web.entity.SysRole;
import com.hj.web.entity.UserInfo;
import com.hj.web.entity.UserRole;
import com.hj.web.mapping.SysItemMapper;
import com.hj.web.mapping.SysItemRoleMapper;
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
@RequestMapping("/roleItem")
@Controller
public class SysItemRoleController extends ControllerBase {

	private final static Logger logger = LoggerFactory.getLogger(SysItemRoleController.class);
	private HashSessions hashSession = HashSessions.getInstance();
	@Autowired
	SysItemRoleDao sysItemRoleDao;
	@Autowired
	SysItemDao sysItemDao;
	@Autowired
	SysRoleDao sysRoleDao;
	@Autowired
	IKeyGen key;
	@Autowired
	SysItemRoleMapper sysItemRoleMapper;
	@Autowired
	SysItemMapper sysItemMapper;
	@Autowired
	UserRoleService sysUserRoleService;

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
	 * 给角色添加权限
	 * 
	 * @return
	 */
	@RequestMapping("/addAndUpdate")
	public String addAndUpdate(Model model) {
		SysItemRole sysItemRole = new SysItemRole();
		String itemIds = StringUtils.trim(getTrimParameter("itemIds"));
		String roleId = StringUtils.trim(getTrimParameter("roleId"));
		String itemIdss = StringUtils.trim(getTrimParameter("itemId"));
		String id = StringUtils.trim(getTrimParameter("id"));

		String[] str = itemIds.trim().split(",");
		StringBuffer sb = new StringBuffer();
		String itemId = "";
		for (String s : str) {
			sb.append(s + ",");
		}
		itemId = sb.toString().substring(0, sb.length() - 1);

		int row = sysItemRoleDao.delByRoleId(roleId);
		if (row > 0) {
			if (StringUtils.isNotBlank(itemId)) {
				String[] names = itemId.split(",");
				for (String name : names) {
					/*
					 * //根据菜单id查询菜单角色 SysItem sy=sysItemMapper.selectByPrimaryKey(name);
					 */
					// if(sy.getParentId()==null || "".equals(sy.getParentId())){
					sysItemRole.setId(key.getUUIDKey());
					sysItemRole.setItemId(name);
					sysItemRole.setRoleId(roleId);
					sysItemRoleMapper.insertSelective(sysItemRole);
					// }

				}
			}
		} else {
			if (StringUtils.isNotBlank(itemId)) {
				String[] names = itemId.split(",");
				for (String name : names) {
					// 根据菜单id查询菜单角色
					/*
					 * SysItem sy=sysItemMapper.selectByPrimaryKey(name);
					 * if(sy.getParentId()==null || "".equals(sy.getParentId())){
					 */
					sysItemRole.setId(key.getUUIDKey());
					sysItemRole.setItemId(name);
					sysItemRole.setRoleId(roleId);
					sysItemRoleMapper.insertSelective(sysItemRole);
					// }
				}
			}
		}

		logger.info("添加角色权限成功");
		UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());

		List<SysItemRole> lst = sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
		List<SysItemRole> item = sysItemRoleDao.selectItemByPId(userRole.getRoleid());
		model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
		model.addAttribute("itemId", itemIdss);
		model.addAttribute("id", id);
		logger.info("===========添加或修改角色权限失败=============");

		return "redirect:/role/list";
	}

	/**
	 * 删除角色权限
	 * 
	 * @return
	 *//*
		 * @RequestMapping("/del") public String del() { String byid =
		 * StringUtils.trim(getTrimParameter("byid")); String boxeditId =
		 * StringUtils.trim(getTrimParameter("boxeditId")); if (byid != null &&
		 * !byid.equals("")) { sysItemRoleDao.del(byid); } else { String[] str =
		 * boxeditId.trim().split(","); StringBuffer sb = new StringBuffer(); String
		 * strs = ""; for (String s : str) { sb.append("'" + s + "',"); } strs =
		 * sb.toString().substring(0, sb.length() - 1); sysItemRoleDao.dels(strs); }
		 * return "redirect:/itemRole/list"; }
		 */
	/**
	 * 根据角色ID查看角色权限
	 * 
	 * @author deng.hemei
	 * @description
	 * @return
	 * @throws Exception
	 * @updateDate 2016年6月20日
	 */
	@RequestMapping("/listItem")
	public String selectByRoleId(Model model) throws Exception {
		String logogram = "";
		SysRole loginRole = super.getUserRole();
		if (loginRole != null) {
			logogram = loginRole.getLogogram();
		}
		SysRole role = super.getUserRole();
		// 获取数据
		String roleId = StringUtils.trim(getTrimParameter("roleId"));
		String roleName = StringUtils.trim(getTrimParameter("roleName"));
		String itemId = StringUtils.trim(getTrimParameter("itemId"));
		String id = StringUtils.trim(getTrimParameter("id"));
		List<SysItemRole> lst = sysItemRoleDao.selectItemByRoleId(roleId);
		List<SysItemRole> ls = sysItemRoleDao.selectItemByPId(roleId);
		// 获取当前用户所有的权限项
		// 所有的数据
		List<SysItem> itemAll = new ArrayList<SysItem>();
		// 父级数据
		List<SysItem> itemList = new ArrayList<SysItem>();
		if (StringUtils.isNotEmpty("logogram") && logogram.equals("0")) {
			itemAll = sysItemDao.findAll();
		} else {
			itemAll = sysItemDao.findAllByRoleId(role.getId());
		}
		// 取父
		if (itemAll != null && itemAll.size() > 0) {
			for (SysItem item : itemAll) {
				String parentId = item.getParentId();
				if (StringUtils.isEmpty(parentId))
					itemList.add(item);
			}
		}
		// 取子
		if (itemList != null && itemList.size() > 0) {
			for (SysItem sysItem : itemList) {
				List<SysItem> childList = new ArrayList<SysItem>();
				String itemid = sysItem.getId();
				if (itemAll != null && itemAll.size() > 0) {
					for (SysItem item : itemAll) {
						String parentId = item.getParentId();
						if (parentId.equals(itemid))
							childList.add(item);
					}
				}
				sysItem.setChildList(childList);
			}
		}
		model.addAttribute("list", lst);
		model.addAttribute("ls", ls);
		model.addAttribute("roleName", roleName);
		model.addAttribute("roleId", roleId);
		model.addAttribute("itemList", itemList);
		model.addAttribute("itemId", itemId);
		model.addAttribute("id", id);

		UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
		List<SysItemRole> lstt = sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
		List<SysItemRole> item = sysItemRoleDao.selectItemByPId(userRole.getRoleid());
		model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lstt);
		return "itemRole/listItem";
	}
}
