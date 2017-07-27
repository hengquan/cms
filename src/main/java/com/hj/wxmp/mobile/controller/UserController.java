package com.hj.wxmp.mobile.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.hj.wxmp.mobile.entity.SysItemRole;
import com.hj.wxmp.mobile.entity.SysUserRole;
import com.hj.wxmp.mobile.entity.UserInfo;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.ProjUserRoleService;
import com.hj.wxmp.mobile.services.SysUserRoleService;
import com.hj.wxmp.mobile.services.UserCustRefService;
import com.hj.wxmp.mobile.services.UserInfoService;

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
	SysUserRoleService sysUserRoleService;
	@Autowired
	ProjUserRoleService projUserRoleService;
	@Autowired
	UserCustRefService userCustRefService;
	
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
	public String userList(@RequestParam(value="nowPage",defaultValue="1") int nowPage,@RequestParam(value="pageSize",defaultValue="10") int pageSize,ModelMap model) {
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
				Map<String,Object> userRole = sysUserRoleService.findByUserId(userId);
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
		SysUserRole userRole=sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
	    List<SysItemRole> lst =sysItemRoleDao.selectItemByRoleId(userRole.getRoleId());
	    List<SysItemRole> item=sysItemRoleDao.selectItemByPId(userRole.getRoleId());
	    model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
	    String itemId = super.getTrimParameter("itemId");
	    String id = super.getTrimParameter("id");
	    model.addAttribute("itemId",itemId);
	    model.addAttribute("id",id);
		return pageUrl;
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
//		for(Map<String,Object> kehu : kehuMessage){
//		}
		model.addAttribute("kehuMessage",kehuMessage);
		//菜单栏内容
		SysUserRole userRole=sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
	    List<SysItemRole> lst =sysItemRoleDao.selectItemByRoleId(userRole.getRoleId());
	    List<SysItemRole> item=sysItemRoleDao.selectItemByPId(userRole.getRoleId());
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
				Map<String,Object> userRole = sysUserRoleService.findByUserId(userId);
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
		SysUserRole userRole=sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
	    List<SysItemRole> lst =sysItemRoleDao.selectItemByRoleId(userRole.getRoleId());
	    List<SysItemRole> item=sysItemRoleDao.selectItemByPId(userRole.getRoleId());
	    model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
	    String itemId = super.getTrimParameter("itemId");
	    String id = super.getTrimParameter("id");
	    model.addAttribute("itemId",itemId);
	    model.addAttribute("id",id);
		return pageUrl;
	}
	
	
	
	
	//审核用户信息
	@ResponseBody
	@RequestMapping(value="/user/checkUser")
	public JSON checkUser() throws Exception{
		Map<String, Object> data = new HashMap<String,Object>();
		try {
			String userId = getTrimParameter("checkedId");
			String state = getTrimParameter("state");
			UserInfo userinfo = userInfoService.findById(userId);
			userinfo.setIsvalidate(Integer.parseInt(state));
			userInfoService.update(userinfo);
			data.put("msg", "100");
		} catch (Exception e) {
			data.put("msg", "103");
			e.printStackTrace();
		}
		return JSONObject.fromObject(data);
	}
	
	
	
}
