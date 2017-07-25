package com.hj.wxmp.mobile.controller;

import java.util.Date;
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

import com.hj.utils.JsonUtils;
import com.hj.utils.MD5Utils;
import com.hj.web.core.mvc.ControllerBase;
import com.hj.wxmp.mobile.common.Constants;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.dao.SysAdminDao;
import com.hj.wxmp.mobile.dao.SysItemRoleDao;
import com.hj.wxmp.mobile.dao.SysRoleDao;
import com.hj.wxmp.mobile.entity.SysAdmin;
import com.hj.wxmp.mobile.entity.SysItemRole;
import com.hj.wxmp.mobile.entity.SysRole;
import com.hj.wxmp.mobile.entity.SysUserRole;
import com.hj.wxmp.mobile.mapping.SysAdminMapper;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.SysUserRoleService;

/**
 * @author denghemei
 * @date 创建时间：2016年6月23日 上午11:58:15
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@RequestMapping("/admin")
@Controller
public class AdminController extends ControllerBase {

	private final static Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	private HashSessions hashSession = HashSessions.getInstance();

	@Autowired
	SysAdminDao sysAdminDao;
	@Autowired
	SysRoleDao sysRoleDao;
	@Autowired
	IKeyGen key;
	@Autowired
	SysAdminMapper sysAdminMapper;
	@Autowired
	SysUserRoleService sysUserRoleService;
	@Autowired
	SysItemRoleDao sysItemRoleDao;
//	@Autowired
//	TblSubStoreService subStoreService;

	public String Userid() {
		try {
			Object obj = request.getSession().getAttribute("adminSession");
			if (null != obj) {
				SysAdmin admin = (SysAdmin) obj;
				return admin.getId();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
	public SysAdmin admin() {
		try {
			Object obj = request.getSession().getAttribute("adminSession");
			if (null != obj) {
				SysAdmin admin = (SysAdmin) obj;
				return admin;
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
		List<SysAdmin> adminList = sysAdminDao.findAll();
		List<SysRole> roleList = sysRoleDao.findAll();
		model.addAttribute("adminList", adminList);
		model.addAttribute("roleList", roleList);
		String itemId = super.getTrimParameter("itemId");
	    String id = super.getTrimParameter("id");
	    model.addAttribute("itemId",itemId);
	    model.addAttribute("id",id);
		logger.info("查询所有管理员信息");
		model.addAttribute("currentUser", admin());
		model.addAttribute("itemNamesss",hashSession.getItemRole("itemNamesss"));
	    model.addAttribute("lst",hashSession.getItemRole("lst"));
	    SysUserRole userRole=sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
	    List<SysItemRole> lst =sysItemRoleDao.selectItemByRoleId(userRole.getRoleId());
	    List<SysItemRole> item=sysItemRoleDao.selectItemByPId(userRole.getRoleId());
	    model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
	    model.addAttribute("itemId",itemId);
	    model.addAttribute("id",id);
		return "admin/list";
	}

	/**
	 * 添加管理员
	 * 
	 * @return
	 */
	@RequestMapping("/addAndUpdate")
	@ResponseBody
	public String addAndUpdate() {
		Map<String,Object> result = new HashMap<String,Object>();
		String editId = StringUtils.trimToEmpty(getTrimParameter("editId"));
		SysAdmin sysAdmin = new SysAdmin();
		String loginId = StringUtils.trim(getTrimParameter("loginId"));
		String userName = StringUtils.trim(getTrimParameter("userName"));
		String remark = StringUtils.trim(getTrimParameter("remark"));
		String userType = StringUtils.trim(getTrimParameter("userType"));
		String storeId=StringUtils.trim(getTrimParameter("storeId"));
		SysAdmin sysAdmin1 = sysAdminDao.getSysAdminByLoginId(loginId);
		
		boolean isSave = true;
		if (sysAdmin1 != null) {
			if(!editId.equals(sysAdmin1.getId())){
				System.out.println("loginId冲突!");
				isSave = false;
			}
		}
		if(isSave){
			if (editId == null || editId.equals("")) {
				sysAdmin.setId(key.getUUIDKey());
				sysAdmin.setLoginId(loginId);
				sysAdmin.setUserName(userName);
				sysAdmin.setCreateUserId(Userid());
				sysAdmin.setRemark(remark);
				sysAdmin.setUpdateUserId(Userid());
				sysAdmin.setUserType(userType);
				sysAdmin.setRelationTableId(storeId);
				sysAdminDao.add(sysAdmin);
				//添加用户角色
				SysUserRole ur=new SysUserRole();
				ur.setUserId(sysAdmin.getId());
				ur.setRoleId(userType);
				sysUserRoleService.insert(ur);
				
				logger.info("添加管理员成功");

			} else {
				sysAdmin.setId(editId);
				sysAdmin.setLoginId(loginId);
				sysAdmin.setUserName(userName);
				sysAdmin.setRemark(remark);
				sysAdmin.setUpdateUserId(Userid());
				sysAdmin.setUpdateTime(new Date());
				sysAdmin.setUserType(userType);
				sysAdmin.setRelationTableId(storeId);
				sysAdminDao.update(sysAdmin);
				
				//用户名修改用户角色
				SysUserRole ur=new SysUserRole();
				ur.setUserId(sysAdmin.getId());
				ur.setRoleId(userType);
				sysUserRoleService.updateByUserId(ur);
				logger.info("修改管理员成功");
			}
			result.put("msg","ok");
		}else{
			result.put("msg","error");
		}
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
			sysAdminDao.del(byid);
		} else {
			String[] str = boxeditId.trim().split(",");
			StringBuffer sb = new StringBuffer();
			String strs = "";
			for (String s : str) {
				sb.append("'" + s + "',");
			}
			strs = sb.toString().substring(0, sb.length() - 1);
			sysAdminDao.dels(strs);
		}
		return "redirect:/admin/list";
	}

	@RequestMapping("/resetpwd")
	@ResponseBody
	public String resetPassword() {
		String adminId = StringUtils.trimToEmpty(getTrimParameter("adminId"));
		String resetPwd = MD5Utils.MD5(Constants.ADMIN_DEFAULT_PASSWORD);
		SysAdmin s = new SysAdmin();
		s.setId(adminId);
		s.setPassword(resetPwd);
		sysAdminMapper.updateByPrimaryKeySelective(s);
		return "{\"code\":\"ok\"}";
	}
}
