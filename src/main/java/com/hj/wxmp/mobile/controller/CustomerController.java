package com.hj.wxmp.mobile.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hj.web.core.mvc.ControllerBase;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.dao.SysItemRoleDao;
import com.hj.wxmp.mobile.entity.SysItemRole;
import com.hj.wxmp.mobile.entity.SysUserRole;
import com.hj.wxmp.mobile.services.CustomerService;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.ProjUserRoleService;
import com.hj.wxmp.mobile.services.ProjectService;
import com.hj.wxmp.mobile.services.SysUserRoleService;
import com.hj.wxmp.mobile.services.UserCustRefService;
import com.hj.wxmp.mobile.services.UserInfoService;

//项目管理
@Controller
public class CustomerController extends ControllerBase {

	private final static Logger logger = LoggerFactory.getLogger(CustomerController.class);
	private HashSessions hashSession = HashSessions.getInstance();

	@Resource
	private IKeyGen keyGen;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	SysItemRoleDao sysItemRoleDao;
	@Autowired
	SysUserRoleService sysUserRoleService;
	@Autowired
	ProjUserRoleService projUserRoleService;
	@Autowired
	UserCustRefService userCustRefService;
	@Autowired
	ProjectService projectService;
	@Autowired
	CustomerService customerService;

	// 客户列表
	@RequestMapping(value = "/customer/customerList")
	public String userList(ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageUrl = "customer/list";
		try {
			String name = getTrimParameter("name");
			if(name == null){
				map.put("name", "");
			}else{
				map.put("name", name);
			}
			// 获取所有用户信息
			List<Map<String,Object>> userMsg = customerService.selectByUserMessge(map);
			model.addAttribute("userMsg", userMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SysUserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
		List<SysItemRole> lst = sysItemRoleDao.selectItemByRoleId(userRole.getRoleId());
		List<SysItemRole> item = sysItemRoleDao.selectItemByPId(userRole.getRoleId());
		model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
		String itemId = super.getTrimParameter("itemId");
		String id = super.getTrimParameter("id");
		model.addAttribute("itemId", itemId);
		model.addAttribute("id", id);
		return pageUrl;
	}

	
	
	
//	// 修改项目
//	@RequestMapping(value = "/pro/editProject")
//	public String editProject(ModelMap model,Project project) {
//		String editId = getTrimParameter("editId");
//		try {
//			project.setId(editId);
//			projectService.update(project);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "redirect:projectList";
//	}
//	
//	
//	// 删除项目
//	@RequestMapping(value = "/pro/del")
//	public String deletePro(ModelMap model,Project project) {
//		try {
//			String byid = getTrimParameter("byid");
//			Project proj = projectService.findById(byid);
//			String boxeditId = getTrimParameter("boxeditId");
//			if(byid != null && !byid.equals("")){
//				projectService.delete(proj);
//			}else{
//				projectService.deletes(boxeditId);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "redirect:projectList";
//	}
//	
//	
	
	

}
