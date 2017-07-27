package com.hj.wxmp.mobile.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hj.web.core.mvc.ControllerBase;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.dao.SysItemDao;
import com.hj.wxmp.mobile.dao.SysItemRoleDao;
import com.hj.wxmp.mobile.dao.SysRoleDao;
import com.hj.wxmp.mobile.entity.SysItem;
import com.hj.wxmp.mobile.entity.SysItemRole;
import com.hj.wxmp.mobile.entity.SysUserRole;
import com.hj.wxmp.mobile.entity.UserInfo;
import com.hj.wxmp.mobile.mapping.SysItemMapper;
import com.hj.wxmp.mobile.mapping.SysItemRoleMapper;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.SysUserRoleService;

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
	SysUserRoleService sysUserRoleService;

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
			sb.append( s + ",");
		}
		itemId = sb.toString().substring(0, sb.length() - 1);
		
		int row=sysItemRoleDao.delByRoleId(roleId);
		if(row>0){
			if(StringUtils.isNotBlank(itemId)){
				String[] names = itemId.split(",");
				for (String name : names) {
					/*//根据菜单id查询菜单角色
					SysItem sy=sysItemMapper.selectByPrimaryKey(name);*/
					//if(sy.getParentId()==null || "".equals(sy.getParentId())){
						sysItemRole.setId(key.getUUIDKey());
						sysItemRole.setItemId(name);
						sysItemRole.setRoleId(roleId);
						sysItemRoleMapper.insertSelective(sysItemRole);
					//}
					
				}
			}
		}else{
			if(StringUtils.isNotBlank(itemId)){
				String[] names = itemId.split(",");
				for (String name : names) {
					//根据菜单id查询菜单角色
					/*SysItem sy=sysItemMapper.selectByPrimaryKey(name);
					if(sy.getParentId()==null || "".equals(sy.getParentId())){*/
						sysItemRole.setId(key.getUUIDKey());
						sysItemRole.setItemId(name);
						sysItemRole.setRoleId(roleId);
						sysItemRoleMapper.insertSelective(sysItemRole);
//					}
				}
			}
		}
		
		logger.info("添加角色权限成功");
		SysUserRole userRole=sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());

	    List<SysItemRole> lst =sysItemRoleDao.selectItemByRoleId(userRole.getRoleId());
	    List<SysItemRole> item=sysItemRoleDao.selectItemByPId(userRole.getRoleId());
	    model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
	    model.addAttribute("itemId",itemIdss);
	    model.addAttribute("id",id);
		logger.info("===========添加或修改角色权限失败=============");

		return "redirect:/role/list";
	}

	/**
	 * 删除角色权限
	 * 
	 * @return
	 *//*
	@RequestMapping("/del")
	public String del() {
		String byid = StringUtils.trim(getTrimParameter("byid"));
		String boxeditId = StringUtils.trim(getTrimParameter("boxeditId"));
		if (byid != null && !byid.equals("")) {
			sysItemRoleDao.del(byid);
		} else {
			String[] str = boxeditId.trim().split(",");
			StringBuffer sb = new StringBuffer();
			String strs = "";
			for (String s : str) {
				sb.append("'" + s + "',");
			}
			strs = sb.toString().substring(0, sb.length() - 1);
			sysItemRoleDao.dels(strs);
		}
		return "redirect:/itemRole/list";
	}
*/
	/**
	 * 根据角色ID查看角色权限
	 * @author deng.hemei
	 * @description 
	 * @return 
	 * @updateDate 2016年6月20日
	 */
	@RequestMapping("/listItem")
	public String selectByRoleId(Model model){
		String roleId = StringUtils.trim(getTrimParameter("roleId"));
		String roleName = StringUtils.trim(getTrimParameter("roleName"));
		String itemId = StringUtils.trim(getTrimParameter("itemId"));
		String id = StringUtils.trim(getTrimParameter("id"));
		List<SysItemRole> lst=sysItemRoleDao.selectItemByRoleId(roleId);
		List<SysItemRole> ls=sysItemRoleDao.selectItemByPId(roleId);
		
		List<SysItem> itemList = sysItemDao.findAllByParent("");
		for (SysItem sysItem : itemList) {
			List<SysItem> childList = sysItemDao.findAllByParent(sysItem.getId());
			
			sysItem.setChildList(childList);
		}
		
		model.addAttribute("list", lst);
		model.addAttribute("ls", ls);
		model.addAttribute("roleName", roleName);
		model.addAttribute("roleId", roleId);
		model.addAttribute("itemList", itemList);
		model.addAttribute("itemId",itemId);
		model.addAttribute("id",id);
		
		SysUserRole userRole=sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
	    List<SysItemRole> lstt =sysItemRoleDao.selectItemByRoleId(userRole.getRoleId());
	    List<SysItemRole> item=sysItemRoleDao.selectItemByPId(userRole.getRoleId());
	    model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lstt);
		return "itemRole/listItem";
	}
}
