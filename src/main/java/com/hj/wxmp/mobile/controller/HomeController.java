package com.hj.wxmp.mobile.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.hj.wxmp.mobile.mapping.SysItemRoleMapper;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.SysUserRoleService;

/**
 * 
* @ClassName: HomeController 
* @Description: TODO(首页) 
* @author weilesi
* @date 2015年9月10日 上午8:26:30 
*
 */
@Controller
public class HomeController extends ControllerBase {
	
	private final static Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private HashSessions hashSession = HashSessions.getInstance();
	@Autowired
	IKeyGen key;
	@Autowired
	SysAdminMapper sysAdminMapper;
	@Autowired
	SysAdminDao sysAdminDao;
	@Autowired
	SysRoleDao sysRoleDao;
	@Resource
	private SysUserRoleService sysUserRoleService;
	@Resource
	private SysItemRoleMapper sysItemRoleMapper;
	@Autowired
	SysItemRoleDao sysItemRoleDao;
	
	/**
	 * 主页
	 */		
	@RequestMapping("/home/index")
	public String home(ModelMap map,Model model) {
		List<SysAdmin> adminList = sysAdminDao.findAll();
		List<SysRole> roleList = sysRoleDao.findAll();
		model.addAttribute("adminList", adminList);
		model.addAttribute("roleList", roleList);
		String itemId = super.getTrimParameter("itemId");
	    String id = super.getTrimParameter("id");
	    model.addAttribute("itemId",itemId);
	    model.addAttribute("id",id);
		logger.info("查询所有管理员信息");
		
		SysUserRole userRole=sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());

	    List<SysItemRole> lst =sysItemRoleDao.selectItemByRoleId(userRole.getRoleId());
	    List<SysItemRole> item=sysItemRoleDao.selectItemByPId(userRole.getRoleId());
	    model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
	    //hashSession.setItemRole("itemNamesss", item);
	    //hashSession.setItemRole("lst", lst);
	    
		return "admin/list";
	}	
	
	
	@RequestMapping("/home/resetpwd")
	@ResponseBody
	public String resetPassword(){
		String relationTableId = StringUtils.trimToEmpty(getTrimParameter("businessId"));
		String resetPwd = MD5Utils.MD5(Constants.BUSINESS_DEFAULT_PASSWORD);
		SysAdmin s = new SysAdmin();
		s.setRelationTableId(relationTableId);
		s.setPassword(resetPwd);
		sysAdminMapper.updatePwdByBusinessId(s);
		return "{\"code\":\"ok\"}";
	}  
	 
	
	public static void main(String[] args) {
		System.out.println(NumberUtils.toInt(ObjectUtils.toString(null)));
	}
}
