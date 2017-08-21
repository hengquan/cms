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
import org.springframework.web.bind.annotation.RequestParam;

import com.hj.web.core.mvc.ControllerBase;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.dao.SysItemRoleDao;
import com.hj.wxmp.mobile.entity.SysItemRole;
import com.hj.wxmp.mobile.entity.UserRole;
import com.hj.wxmp.mobile.services.CustomerService;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.ProjUserRoleService;
import com.hj.wxmp.mobile.services.ProjectService;
import com.hj.wxmp.mobile.services.UserCustRefService;
import com.hj.wxmp.mobile.services.UserInfoService;
import com.hj.wxmp.mobile.services.UserRoleService;

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
	UserRoleService sysUserRoleService;
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
	public String userList(@RequestParam(value="nowPage",defaultValue="1") int nowPage,
			@RequestParam(value="pageSize",defaultValue="10") int pageSize,ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		//纪录总数
		Integer listMessgeCount = 0;
		String name = getTrimParameter("name");
		Integer start = ((nowPage - 1) * pageSize);
		map.put("page", start);
		map.put("pageSize", pageSize);
		String pageUrl = "customer/list";
		try {
			if(name == null){
				map.put("name", "");
			}else{
				map.put("name", name);
			}
			// 获取所有用户信息
			List<Map<String,Object>> userMsg = customerService.selectByUserMessge(map);
			//所有信息数量
			listMessgeCount = customerService.selectByUserMessgeCount(map);
		 	Integer totalCount = listMessgeCount%pageSize;
			Integer totalPageNum = 0;
			if(totalCount==0){
				totalPageNum = listMessgeCount/pageSize;
			}else{
				totalPageNum = (listMessgeCount/pageSize)+1;
			}
			model.put("nowPage", nowPage);
			model.put("totalPageNum", totalPageNum);
			model.addAttribute("userMsg", userMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
		List<SysItemRole> lst = sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
		List<SysItemRole> item = sysItemRoleDao.selectItemByPId(userRole.getRoleid());
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
