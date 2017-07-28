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
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.web.core.mvc.ControllerBase;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.dao.SysItemRoleDao;
import com.hj.wxmp.mobile.entity.Project;
import com.hj.wxmp.mobile.entity.SysItemRole;
import com.hj.wxmp.mobile.entity.UserRole;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.ProjUserRoleService;
import com.hj.wxmp.mobile.services.ProjectService;
import com.hj.wxmp.mobile.services.UserCustRefService;
import com.hj.wxmp.mobile.services.UserInfoService;
import com.hj.wxmp.mobile.services.UserRoleService;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

//项目管理
@Controller
public class ProjectController extends ControllerBase {

	private final static Logger logger = LoggerFactory.getLogger(ProjectController.class);
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

	// 项目列表
	@RequestMapping(value = "/pro/projectList")
	public String userList(ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageUrl = "project/list";
		try {
			String projectName = getTrimParameter("projectName");
			if(projectName == null){
				map.put("projectName", "");
			}else{
				map.put("projectName", projectName);
			}
			// 获取所有项目信息
			List<Project> selectList = projectService.getProjectMessge(map);
			for(Project pro : selectList){
				String proId = pro.getId();
				map.put("proId", proId);
				map.put("proId", proId);
				map.put("proId", proId);
			}
			model.addAttribute("selectList", selectList);
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

	
	// 添加项目
	@RequestMapping(value = "/pro/addProject")
	public String addProject(ModelMap model,Project project) {
		try {
			project.setId(keyGen.getUUIDKey());
			projectService.insert(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:projectList";
	}
	
	
	// 修改项目
	@RequestMapping(value = "/pro/editProject")
	public String editProject(ModelMap model,Project project) {
		String editId = getTrimParameter("editId");
		try {
			project.setId(editId);
			projectService.update(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:projectList";
	}
	
	
	// 删除项目
	@RequestMapping(value = "/pro/del")
	public String deletePro(ModelMap model,Project project) {
		try {
			String byid = getTrimParameter("byid");
			Project proj = projectService.findById(byid);
			String boxeditId = getTrimParameter("boxeditId");
			if(byid != null && !byid.equals("")){
				projectService.delete(proj);
			}else{
				projectService.deletes(boxeditId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:projectList";
	}
	
	
	
	
	
	
	// 该项目所对应所有的用户
	@ResponseBody
	@RequestMapping(value = "/pro/userMsg")
	public JSON userProjecct() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			String proId = getTrimParameter("proId");
			List<Map<String, Object>> userMessage = userCustRefService.selectByProjectId(proId);
			data.put("userMessage", userMessage);
			data.put("msg", "100");
		} catch (Exception e) {
			data.put("msg", "103");
			e.printStackTrace();
		}
		return JSONObject.fromObject(data);
	}

	// 该用户所有的客户
	@RequestMapping(value = "/pro/prokehu")
	public String prokehu(ModelMap model) throws Exception {
		String pageUrl = "kehuPage/list";
		String proId = getTrimParameter("proId");
		Project project = projectService.findById(proId);
		List<Map<String, Object>> customers = userCustRefService.findByProjectId(proId);
		for(Map<String, Object> customer : customers){
			customer.put("projName", project.getProjname());
		}
		model.addAttribute("kehuMessage", customers);
		// 菜单栏内容
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

//	// 用户审核列表
//	@RequestMapping("/user/userCheck")
//	public String userCheck(@RequestParam(value = "nowPage", defaultValue = "1") int nowPage,
//			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize, ModelMap model) {
//		String pageUrl = "user/userCheck";
//		// 纪录总数
//		Integer listMessgeCount = 0;
//		Map<String, Object> map = new HashMap<String, Object>();
//		String state = getTrimParameter("state");
//		String userName = getTrimParameter("userName");
//		map.put("state", state);
//		map.put("userName", userName);
//		Integer start = ((nowPage - 1) * pageSize);
//		map.put("page", start);
//		map.put("pageSize", pageSize);
//		try {
//			// 获取用户所有的信息
//			List<UserInfo> selectList = userInfoService.getMessge(map);
//			// 获取用户所对应项目的信息和角色信息
//			for (UserInfo userinfo : selectList) {
//				String userId = userinfo.getId();
//				// 用户所对应的角色
//				Map<String, Object> userRole = sysUserRoleService.findByUserId(userId);
//				userinfo.setUserRole(userRole);
//			}
//			listMessgeCount = userInfoService.getMessgeCount(map);
//			Integer totalCount = listMessgeCount % pageSize;
//			Integer totalPageNum = 0;
//			if (totalCount == 0) {
//				totalPageNum = listMessgeCount / pageSize;
//			} else {
//				totalPageNum = (listMessgeCount / pageSize) + 1;
//			}
//			model.put("userName", userName);
//			model.put("nowPage", nowPage);
//			model.put("totalPageNum", totalPageNum);
//			model.addAttribute("userList", selectList);
//			model.put("pageSize", pageSize);
//			model.put("state", state);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		SysUserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
//		List<SysItemRole> lst = sysItemRoleDao.selectItemByRoleId(userRole.getRoleId());
//		List<SysItemRole> item = sysItemRoleDao.selectItemByPId(userRole.getRoleId());
//		model.addAttribute("itemNamesss", item);
//		model.addAttribute("lst", lst);
//		String itemId = super.getTrimParameter("itemId");
//		String id = super.getTrimParameter("id");
//		model.addAttribute("itemId", itemId);
//		model.addAttribute("id", id);
//		return pageUrl;
//	}
//
//	// 审核用户信息
//	@ResponseBody
//	@RequestMapping(value = "/user/checkUser")
//	public JSON checkUser() throws Exception {
//		Map<String, Object> data = new HashMap<String, Object>();
//		try {
//			String userId = getTrimParameter("checkedId");
//			String state = getTrimParameter("state");
//			UserInfo userinfo = userInfoService.findById(userId);
//			userinfo.setIsvalidate(Integer.parseInt(state));
//			userInfoService.update(userinfo);
//			data.put("msg", "100");
//		} catch (Exception e) {
//			data.put("msg", "103");
//			e.printStackTrace();
//		}
//		return JSONObject.fromObject(data);
//	}

}
