package com.hj.wxmp.mobile.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.utils.MD5Utils;
import com.hj.web.core.mvc.ControllerBase;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.dao.SysItemRoleDao;
import com.hj.wxmp.mobile.entity.AccessRecord01;
import com.hj.wxmp.mobile.entity.AccessRecord02;
import com.hj.wxmp.mobile.entity.AccessRecord03;
import com.hj.wxmp.mobile.entity.ImportMapUserCust;
import com.hj.wxmp.mobile.entity.ProjUserRole;
import com.hj.wxmp.mobile.entity.Project;
import com.hj.wxmp.mobile.entity.SysItemRole;
import com.hj.wxmp.mobile.entity.SysRole;
import com.hj.wxmp.mobile.entity.UserCustRef;
import com.hj.wxmp.mobile.entity.UserInfo;
import com.hj.wxmp.mobile.entity.UserRole;
import com.hj.wxmp.mobile.services.AccessRecord01Service;
import com.hj.wxmp.mobile.services.AccessRecord02Service;
import com.hj.wxmp.mobile.services.AccessRecord03Service;
import com.hj.wxmp.mobile.services.CustomerService;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.ImportMapUserCustService;
import com.hj.wxmp.mobile.services.ProjUserRoleService;
import com.hj.wxmp.mobile.services.ProjectService;
import com.hj.wxmp.mobile.services.SysRoleService;
import com.hj.wxmp.mobile.services.UserCustRefService;
import com.hj.wxmp.mobile.services.UserInfoService;
import com.hj.wxmp.mobile.services.UserRoleService;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

/**
 * 
* @ClassName: UserController 
* @Description: TODO(用户管理) 
* @author weilesi
* @date 2016年2月26日 上午16:38:30 
*
 */
@Controller
public class UserController extends ControllerBase {
	
	//private final static Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	//private HashSessions hashSession = HashSessions.getInstance();
	
	@Resource
	private IKeyGen keyGen;
	
	@Autowired
	private UserInfoService userInfoService;
	
	
	private HashSessions hashSession = HashSessions.getInstance();
	
	@Autowired
	SysItemRoleDao sysItemRoleDao;
	@Autowired

	ProjUserRoleService projUserRoleService;
	@Autowired
	UserCustRefService userCustRefService;
	@Autowired
	ProjectService projectService;
	@Autowired
	UserRoleService userRoleService;
	@Autowired
	SysRoleService roleService;
	@Autowired
	AccessRecord01Service accessRecord01Service;
	@Autowired
	AccessRecord02Service accessRecord02Service;
	@Autowired
	AccessRecord03Service accessRecord03Service;
	@Autowired
	ImportMapUserCustService importMapUserCustService;
	@Autowired
	CustomerService customerService;
	
//	@Autowired
//	
//	UserExtendDao userExtendDao;
	
	/**
	 * 主页
	 */		
	@RequestMapping("/user/index")
	public String index(ModelMap map) {
		String pageUrl = "user/index";
		
		try {
			Object obj = request.getSession().getAttribute("adminSession");
			if(null!=obj){
				UserInfo admin = (UserInfo)obj;
				map.put("currentUser", admin);
			}
			
			map.put("activeFlag", "user");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pageUrl;
	}	
	
	/**
	 * 修改用户密码
	 * @return 
	 * @throws Exception 
	 */
	@RequestMapping("/user/updatePwd")
	@ResponseBody
	public String updatePwd() throws Exception{
			String oldPwd = getTrimParameter("oldpwd");
			String newpwd = getTrimParameter("newpwd");
			Object obj = request.getSession().getAttribute("adminSession");
			if(null!=obj){
				UserInfo userInfo = (UserInfo)obj;
				if(userInfo.getPassword().equals(MD5Utils.MD5(oldPwd))){
					userInfo.setPassword(MD5Utils.MD5(newpwd));
					userInfoService.update(userInfo);
				}else{
					return "{\"code\":\"error\"}";
				}
				
			}
			return "{\"code\":\"ok\"}";
		
	}
	
	@RequestMapping("/user/wpPwd")
	public String upPwd(){
		return "admin/update_pwd";
	}
	
	/**
	 * 查询所有用户
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/list")
	public String list(Model model){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<UserInfo> userList = userInfoService.listEntity(map);
			model.addAttribute("userList", userList);
			model.addAttribute("activeFlag","usermanager");
			model.addAttribute("subActiveFlag","userinfo");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "user/index";
	}
	
	
	
	//用户列表
	@RequestMapping(value = "/user/userList")
	public String userList(@RequestParam(value="nowPage",defaultValue="1") int nowPage,@RequestParam(value="pageSize",defaultValue="10") int pageSize,ModelMap model)throws Exception {
		String pageUrl = "user/list";
		//纪录总数
		Integer listMessgeCount = 0;
		Map<String,Object> map = new HashMap<String,Object>();
		String state = getTrimParameter("selectState");
		String userName = getTrimParameter("userName");
		state = "1";
		map.put("state", state);
		map.put("userName", userName);
		Integer start = ((nowPage - 1) * pageSize);
		map.put("page", start);
		map.put("pageSize", pageSize);
		try {
			//获取用户所有的信息
			List<UserInfo> selectList = userInfoService.getMessge(map);
			//获取用户所对应项目的信息和角色信息
			for(UserInfo userinfo : selectList){
				String userId = userinfo.getId();
				//用户所对应的角色
				Map<String,Object> userRole = userRoleService.findByUserId(userId);
				userinfo.setUserRole(userRole);
			}
			listMessgeCount = userInfoService.getMessgeCount(map);
		 	Integer totalCount = listMessgeCount%pageSize;
			Integer totalPageNum = 0;
			if(totalCount==0){
				totalPageNum = listMessgeCount/pageSize;
			}else{
				totalPageNum = (listMessgeCount/pageSize)+1;
			}
			model.put("userName", userName);
			model.put("nowPage", nowPage);
			model.put("totalPageNum", totalPageNum);
			model.addAttribute("userList", selectList);
			model.put("pageSize", pageSize);
			model.put("state", state);
		} catch (Exception e) {
			e.printStackTrace();
		}
		UserRole userRole=userRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
	    List<SysItemRole> lst =sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
	    List<SysItemRole> item=sysItemRoleDao.selectItemByPId(userRole.getRoleid());
	    model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
	    String itemId = super.getTrimParameter("itemId");
	    String id = super.getTrimParameter("id");
	    model.addAttribute("itemId",itemId);
	    model.addAttribute("id",id);
	    //权限
	    String roleid = userRole.getRoleid();
	    SysRole role = roleService.findById(roleid);
	    model.addAttribute("roleName",role.getRoleName());
		return pageUrl;
	}	
	
	 
	
	// 删除项目
	@RequestMapping(value = "/user/del")
	public String deleteUser(ModelMap model) {
		try {
			String byid = getTrimParameter("byid");
			UserInfo userInfo = userInfoService.findById(byid);
			String boxeditId = getTrimParameter("boxeditId");
			if(StringUtils.isNotEmpty(byid)){
				userInfoService.delete(userInfo);
			}else{
				userInfoService.deletes(boxeditId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:userList";
	}
	
	
	
	
	//用户所对应的所有项目
	@ResponseBody
	@RequestMapping(value="/user/proJect")
	public JSON userProjecct() throws Exception{
		Map<String, Object> data = new HashMap<String,Object>();
		try {
			String userId = getTrimParameter("userId");
			List<Map<String,Object>> projectMessage = projUserRoleService.selectByUserId(userId);
			data.put("projectMessage", projectMessage);
			data.put("msg", "100");
		} catch (Exception e) {
			data.put("msg", "103");
			e.printStackTrace();
		}
		return JSONObject.fromObject(data);
	}
	
	
	//该用户所有的客户
	@RequestMapping(value="/user/allkehu")
	public String allkehu(ModelMap model) throws Exception{
		String pageUrl = "kehuPage/list";
		String userId = getTrimParameter("userId");
		List<Map<String,Object>> kehuMessage = userCustRefService.selectByUserId(userId);
		UserInfo userInfo = userInfoService.findById(userId);
		Map<String, Object> userrole = userRoleService.findByUserId(userId);
		String roleName = userrole.get("role_name").toString();
		model.addAttribute("kehuMessage",kehuMessage);
		model.addAttribute("userInfo",userInfo);
		model.addAttribute("roleName",roleName);
		//菜单栏内容
		UserRole userRole=userRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
	    List<SysItemRole> lst =sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
	    List<SysItemRole> item=sysItemRoleDao.selectItemByPId(userRole.getRoleid());
	    model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
	    String itemId = super.getTrimParameter("itemId");
	    String id = super.getTrimParameter("id");
	    model.addAttribute("itemId",itemId);
	    model.addAttribute("id",id);
		return pageUrl;
	}
	
	
	//该用户所属项目
	@RequestMapping(value="/user/belongToProj")
	public String belongToProj(@RequestParam(value="nowPage",defaultValue="1") int nowPage,
			@RequestParam(value="pageSize",defaultValue="10") int pageSize,ModelMap model) throws Exception{
		String pageUrl = "projPage/list";
		Map<String,Object>map = new HashMap<String,Object>();
		//纪录总数
		Integer listMessgeCount = 0;
		String userName = getTrimParameter("userName");
		String state = getTrimParameter("state");
		map.put("state", state);
		Integer start = ((nowPage - 1) * pageSize);
		map.put("page", start);
		map.put("pageSize", pageSize);
		//
		String userId = getTrimParameter("userId");
		map.put("userId", userId);
		List<Map<String, Object>> projData = projUserRoleService.findByUserId(map);
		//所有信息数量
		listMessgeCount = projUserRoleService.findByUserIdCount(map);
	 	Integer totalCount = listMessgeCount%pageSize;
		Integer totalPageNum = 0;
		if(totalCount==0){
			totalPageNum = listMessgeCount/pageSize;
		}else{
			totalPageNum = (listMessgeCount/pageSize)+1;
		}
		model.put("nowPage", nowPage);
		model.put("totalPageNum", totalPageNum);
		UserInfo userInfo = userInfoService.findById(userId);
		Map<String, Object> userrole = userRoleService.findByUserId(userId);
		String roleName = userrole.get("role_name").toString();
		model.addAttribute("projData",projData);
		model.addAttribute("userInfo",userInfo);
		model.addAttribute("roleName",roleName);
		//菜单栏内容
		UserRole userRole=userRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
	    List<SysItemRole> lst =sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
	    List<SysItemRole> item=sysItemRoleDao.selectItemByPId(userRole.getRoleid());
	    model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
	    String itemId = super.getTrimParameter("itemId");
	    String id = super.getTrimParameter("id");
	    model.addAttribute("itemId",itemId);
	    model.addAttribute("id",id);
		return pageUrl;
	}
	
	
	
	
	
	
	
	
	
	
	
	//用户审核列表
	@RequestMapping(value = "/user/userCheck")
	public String userCheck(@RequestParam(value="nowPage",defaultValue="1") int nowPage,@RequestParam(value="pageSize",defaultValue="10") int pageSize,ModelMap model) {
		String pageUrl = "user/userCheck";
		//纪录总数
		Integer listMessgeCount = 0;
		Map<String,Object> map = new HashMap<String,Object>();
		String state = getTrimParameter("state");
		String userName = getTrimParameter("userName");
		map.put("state", state);
		map.put("userName", userName);
		Integer start = ((nowPage - 1) * pageSize);
		map.put("page", start);
		map.put("pageSize", pageSize);
		map.put("isValidate", "");
		try {
			//获取用户所有的信息
			List<UserInfo> selectList = userInfoService.getMessge(map);
			//获取用户所对应项目的信息和角色信息
			for(UserInfo userinfo : selectList){
				String userId = userinfo.getId();
				//用户所对应的角色
				Map<String,Object> userRole = userRoleService.findByUserId(userId);
				userinfo.setUserRole(userRole);
			}
			listMessgeCount = userInfoService.getMessgeCount(map);
		 	Integer totalCount = listMessgeCount%pageSize;
			Integer totalPageNum = 0;
			if(totalCount==0){
				totalPageNum = listMessgeCount/pageSize;
			}else{
				totalPageNum = (listMessgeCount/pageSize)+1;
			}
			model.put("userName", userName);
			model.put("nowPage", nowPage);
			model.put("totalPageNum", totalPageNum);
			model.addAttribute("userList", selectList);
			model.put("pageSize", pageSize);
			model.put("state", state);
		} catch (Exception e) {
			e.printStackTrace();
		}
		UserRole userRole=userRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
	    List<SysItemRole> lst =sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
	    List<SysItemRole> item=sysItemRoleDao.selectItemByPId(userRole.getRoleid());
	    model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
	    String itemId = super.getTrimParameter("itemId");
	    String id = super.getTrimParameter("id");
	    model.addAttribute("itemId",itemId);
	    model.addAttribute("id",id);
		return pageUrl;
	}
	
	
	
	
	//获取审核用户的信息权限信息和所有项目信息
	@ResponseBody
	@RequestMapping(value="/user/getRoleAndProjectMsg")
	public JSON getRoleAndProjectMsg() throws Exception{
		Map<String, Object> data = new HashMap<String,Object>();
		String projNames = "";
		try {
			String id = getTrimParameter("id");
			UserInfo userInfo = userInfoService.findById(id);
			String loginname = userInfo.getLoginname();
			String password = userInfo.getPassword();
			String realname = userInfo.getRealname();
			String mainphonenum = userInfo.getMainphonenum();
			data.put("loginname", loginname);
			data.put("password", password);
			data.put("realname", realname);
			data.put("mainphonenum", mainphonenum);
			//所选项目
			projNames = userInfo.getSelfprojauth();
			//所有权限信息
			List<SysRole> roles = roleService.selectAllMsg();
			//所有项目信息
			List<Project> projects = projectService.findAll();
			//用户相关的项目
			String projUserRoleIds = "";
			List<Map<String, Object>> projUserRoles = projUserRoleService.selectByUserId(id);
			if(projUserRoles.size()>0){
				for(Map<String,Object>projUserRole : projUserRoles){
					String projUserRoleId = projUserRole.get("id").toString();
					projUserRoleIds+=projUserRoleId+",";
				}
			}
			data.put("projUserRoleIds", projUserRoleIds);
			data.put("roles", roles);
			data.put("projects", projects);
			data.put("projNames", projNames);
			data.put("msg", "100");
		} catch (Exception e) {
			data.put("msg", "103");
			e.printStackTrace();
		}
		System.out.println(JSONObject.fromObject(data));
		return JSONObject.fromObject(data);
	}

	
	
	
	
	//审核用户信息
	@ResponseBody
	@RequestMapping(value="/user/checkUser")
	public JSON checkUser() throws Exception{
		Map<String, Object> data = new HashMap<String,Object>();
		try {
			String userId = getTrimParameter("checkedId");
			String state = getTrimParameter("state");
			//其他审核数据
			String roleId = getTrimParameter("userRole");
			String checkProjIds = getTrimParameter("checkProjIds");
			//更新用户权限
			UserRole userRole = userRoleService.selectByuserId(userId);
			userRole.setRoleid(roleId);
			userRoleService.update(userRole);
			//是否更新用户所对应的项目
			if(checkProjIds != null && !"".equals(checkProjIds)){
				//添加审核项目
				String[] projids = checkProjIds.split(",");
				for(String projid : projids){
					ProjUserRole projUserRole = new ProjUserRole();
					projUserRole.setId(keyGen.getUUIDKey());
					projUserRole.setProjid(projid);
					projUserRole.setRoleid(roleId);
					projUserRole.setUserid(userId);
					projUserRoleService.insert(projUserRole);
				}
			}
			UserInfo userinfo = userInfoService.findById(userId);
			userinfo.setIsvalidate(Integer.parseInt(state));
			userInfoService.update(userinfo);
			//查特殊关系表是否有该用户的信息
			//顾问姓名
			String realname = userinfo.getRealname();
			List<ImportMapUserCust> importMapUserCusts = importMapUserCustService.selectByUserName(realname);
			if(importMapUserCusts.size()>=1){
				for(ImportMapUserCust importMapUserCust : importMapUserCusts){
					//首访ID
					String reportid = importMapUserCust.getReportid();
					AccessRecord01 accessRecord01 = accessRecord01Service.findById(reportid);
					if(accessRecord01!=null) {
						String authorid = accessRecord01.getAuthorid();
						if(StringUtils.isEmpty(authorid)){
							String projId = accessRecord01.getProjid();
							//所选项目
							String[] projids = checkProjIds.split(",");
							for(String projid : projids){
								if(projid.equals(projId)){
									//更新首访表
									accessRecord01.setAuthorid(userinfo.getId());
									accessRecord01.setCreatorid(userinfo.getId());
									accessRecord01Service.update(accessRecord01);
									//添加用户客户关系表
									UserCustRef userCustRef = new UserCustRef();
									userCustRef.setId(keyGen.getUUIDKey());
									userCustRef.setCustid(accessRecord01.getCustid());
									userCustRef.setProjid(projid);
									userCustRef.setUserid(userinfo.getId());
									userCustRefService.insert(userCustRef);
									//删除特殊对应关系表
									importMapUserCustService.delete(importMapUserCust);
								}
							}
						}else{
							importMapUserCustService.delete(importMapUserCust);
						}
					}
				}
			}
			data.put("msg", "100");
		} catch (Exception e) {
			data.put("msg", "103");
			e.printStackTrace();
		}
		return JSONObject.fromObject(data);
	}
	
	
	
	//更新用户信息
	@ResponseBody
	@RequestMapping(value="/user/updateUserMsg")
	public JSON updateUserMsg() throws Exception{
		Map<String, Object> data = new HashMap<String,Object>();
		try {
			//其他数据
			String userId = getTrimParameter("userId");
			String roleId = getTrimParameter("userRole");
			String checkProjIds = getTrimParameter("checkProjIds");
			//用户信息
			UserInfo userinfo = userInfoService.findById(userId);
			//更新用户权限
			UserRole userRole = userRoleService.selectByuserId(userId);
			userRole.setRoleid(roleId);
			userRoleService.update(userRole);
			//用户相关的项目
			String projIds = "";
			List<Map<String, Object>> projUserRoles = projUserRoleService.selectByUserId(userId);
			if(projUserRoles.size()>0){
				for(Map<String,Object>projUserRole : projUserRoles){
					String projId = projUserRole.get("id").toString();
					projIds+=projId+",";
				}
			}
			//是否更新用户所对应的项目
			if(checkProjIds != null && !"".equals(checkProjIds)){
				//添加项目
				String[] projids = checkProjIds.split(",");
				for(String projid : projids){
					if(projIds.indexOf(projid)>=0){
						continue;
					}
					ProjUserRole projUserRole = new ProjUserRole();
					projUserRole.setId(keyGen.getUUIDKey());
					projUserRole.setProjid(projid);
					projUserRole.setRoleid(roleId);
					projUserRole.setUserid(userId);
					projUserRoleService.insert(projUserRole);
				}
				//如果没有了以前的项目
				if(projUserRoles.size()>0){
					for(Map<String,Object>projUserRole : projUserRoles){
						String projId = projUserRole.get("id").toString();
						int indexOf = checkProjIds.indexOf(projId);
						if(indexOf<0){
							//根据项目ID和用户ID删除，用户项目关系表
							Map<String,Object> datamsg = new HashMap<String,Object>();
							datamsg.put("projId", projId);
							datamsg.put("userId", userId);
							projUserRoleService.deleteByProjIdAndUserId(datamsg);
							//查该用户首复成交表所有的记录并更新
							List<AccessRecord01> accessRecord01s = accessRecord01Service.selectByUserId(datamsg);
							for(AccessRecord01 accessRecord01 : accessRecord01s){
								String authorid = accessRecord01.getAuthorid();
								accessRecord01.setAuthorid(":"+authorid);
								accessRecord01Service.update(accessRecord01);
							}
							//复
							List<AccessRecord02> accessRecord02s = accessRecord02Service.selectByUserId(datamsg);
							for(AccessRecord02 accessRecord02 : accessRecord02s){
								String authorid = accessRecord02.getAuthorid();
								accessRecord02.setAuthorid(":"+authorid);
								accessRecord02Service.update(accessRecord02);
							}
							//成交
							List<AccessRecord03> accessRecord03s = accessRecord03Service.selectByUserId(datamsg);
							for(AccessRecord03 accessRecord03 : accessRecord03s){
								String authorid = accessRecord03.getAuthorid();
								accessRecord03.setAuthorid(":"+authorid);
								accessRecord03Service.update(accessRecord03);
							}
						}
					}
				}
			}
			//查特殊关系表是否有该用户的信息
			//顾问姓名
			String realname = userinfo.getRealname();
			List<ImportMapUserCust> importMapUserCusts = importMapUserCustService.selectByUserName(realname);
			if(importMapUserCusts.size()>=1){
				for(ImportMapUserCust importMapUserCust : importMapUserCusts){
					//首访ID
					String reportid = importMapUserCust.getReportid();
					AccessRecord01 accessRecord01 = accessRecord01Service.findById(reportid);
					if(accessRecord01!=null) {
						String authorid = accessRecord01.getAuthorid();
						if(StringUtils.isEmpty(authorid)){
							String projId = accessRecord01.getProjid();
							//所选项目
							String[] projids = checkProjIds.split(",");
							for(String projid : projids){
								if(projid.equals(projId)){
									//更新首访表
									accessRecord01.setAuthorid(userinfo.getId());
									accessRecord01.setCreatorid(userinfo.getId());
									accessRecord01Service.update(accessRecord01);
									//添加用户客户关系表
									UserCustRef userCustRef = new UserCustRef();
									userCustRef.setId(keyGen.getUUIDKey());
									userCustRef.setCustid(accessRecord01.getCustid());
									userCustRef.setProjid(projid);
									userCustRef.setUserid(userinfo.getId());
									userCustRefService.insert(userCustRef);
									//删除特殊对应关系表
									importMapUserCustService.delete(importMapUserCust);
								}
							}
						}else{
							importMapUserCustService.delete(importMapUserCust);
						}
					}
				}
			}
			
			data.put("msg", "100");
		} catch (Exception e) {
			data.put("msg", "103");
			e.printStackTrace();
		}
		return JSONObject.fromObject(data);
	}
}
