package com.hj.wxmp.mobile.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.utils.JsonUtils;
import com.hj.wxmp.core.WxUser;
import com.hj.wxmp.mobile.common.Configurations;
import com.hj.wxmp.mobile.common.ControllerBaseWx;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.common.MD5;
import com.hj.wxmp.mobile.common.Weixin;
import com.hj.wxmp.mobile.dao.SysItemRoleDao;
import com.hj.wxmp.mobile.entity.AccessRecord01;
import com.hj.wxmp.mobile.entity.ProjUserRole;
import com.hj.wxmp.mobile.entity.Project;
import com.hj.wxmp.mobile.entity.SysRole;
import com.hj.wxmp.mobile.entity.SysUserRole;
import com.hj.wxmp.mobile.entity.UserInfo;
import com.hj.wxmp.mobile.services.AccessRecord01Service;
import com.hj.wxmp.mobile.services.AccessRecord02Service;
import com.hj.wxmp.mobile.services.AccessRecord03Service;
import com.hj.wxmp.mobile.services.CustomerService;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.ProjCustRefService;
import com.hj.wxmp.mobile.services.ProjUserRoleService;
import com.hj.wxmp.mobile.services.ProjectService;
import com.hj.wxmp.mobile.services.SysRoleService;
import com.hj.wxmp.mobile.services.SysUserRoleService;
import com.hj.wxmp.mobile.services.UserCustRefService;
import com.hj.wxmp.mobile.services.UserInfoService;
import com.hj.wxmp.mobile.services.WxLoginService;

@RequestMapping("/wx/api")
@Controller
public class WxApiController extends ControllerBaseWx {

	private final static Logger logger = LoggerFactory
			.getLogger(WxApiController.class);

	private HashSessions hashSession = HashSessions.getInstance();
	private Weixin weixin = Weixin.getInstance();
	@Autowired
	IKeyGen key;
	@Autowired
	WxLoginService wxLoginService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	SysItemRoleDao sysItemRoleDao;
	@Autowired
	SysUserRoleService sysUserRoleService;
	@Autowired
	ProjUserRoleService projUserRoleService;
	@Autowired
	ProjCustRefService projCustRefService;
	@Autowired
	UserCustRefService userCustRefService;
	@Autowired
	ProjectService projectService;
	@Autowired
	CustomerService customerService;
	@Autowired
	AccessRecord01Service accessRecord01Service;
	@Autowired
	AccessRecord02Service accessRecord02Service;
	@Autowired
	AccessRecord03Service accessRecord03Service;
	@Autowired
	SysRoleService roleService;
	@Autowired
	ProjectService projectServise;
	
	
	public UserInfo getCurrentUser() {
		UserInfo user = hashSession.getCurrentSessionUser(request);
		return user;
	}

	public String path() {
		String urlpath = Configurations.getConfig("ACCESSURL");
		return urlpath;
	}

	// 根据openid去腾讯后台获取用户信息并更新本地userinfo
	private void updateUserInfo(String openid) {
		if (StringUtils.trimToNull(openid) != null) {
			WxUser wxUser = Weixin.getInstance().getUserInfo(openid);
			wxLoginService.bandingUser(wxUser, ObjectUtils.toString(
					request.getSession().getAttribute("weixin_current_url")));
		}
	}

	@RequestMapping(value = "/redirectUrl.az")
	public void redirectUrl(HttpServletResponse response, Model model,
			HttpServletRequest request) throws IOException {
		String openid = HashSessions.getInstance().getOpenId(request);
		updateUserInfo(openid);
		// 获取RUL地址
		String wx_url = StringUtils.trimToEmpty(getTrimParameter("wx_url"));
		response.sendRedirect(URLDecoder.decode(wx_url, "UTF-8"));
	}

//	@RequestMapping(value = "/getOpenid")
//	@ResponseBody
//	public JSONObject getOpenid(HttpServletResponse response) {
//		responseInfo(response);
//		String openid = HashSessions.getInstance().getOpenId(request);
//		logger.debug("thisOpenId1234==={}", openid);
//		updateUserInfo(openid);
//		JSONObject json = new JSONObject();
//		if (StringUtils.stripToNull(openid) != null) {
//			json.put("msg", "100");
//		} else {
//			json.put("msg", "103");
//		}
//		return json;
//	}

	public void responseInfo(HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8;");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods",
				"POST, GET, OPTIONS,DELETE,PUT");
	}
	
	
	
	//通用方法-用户是否有openid
	public Boolean getUserOpenid(HttpServletResponse response,String requetUrl,Map<String,Object> map) throws Exception{
		String openid = HashSessions.getInstance().getOpenId(request);
		logger.debug("the--------openid{}",openid);
		Boolean isok = false;
		if (StringUtils.stripToNull(openid) != null) {
			updateUserInfo(openid);
			isok = userMsg(map,openid);
		}else{
			String siteName = Configurations.getSiteName();
			String url = siteName+"/wxmp.ql/wx/api/redirectUrl.az?wx_url="+requetUrl;
			logger.debug("messageURL----asdfa{}",url);
			response.sendRedirect(URLDecoder.decode(url, "UTF-8"));
		}
		return isok;
	}
	
	
	
	//通用接口
	private Boolean userMsg(Map<String, Object> map,String openid) throws Exception{
		Boolean isok = false;
		UserInfo userInfo = userInfoService.findByOpenid(openid);
		String realname = userInfo.getRealname();
		String phone = userInfo.getMainphonenum();
		String loginname = userInfo.getLoginname();
		String password = userInfo.getPassword();
		Integer isvalidate = userInfo.getIsvalidate();
		if(realname!=null && phone !=null && loginname!=null && password!=null){
			if(isvalidate==0){
				map.put("msg", "105");
			}else if(isvalidate==2){
				map.put("msg", "106");
			}else{
				isok = true;
			}
		}else{
			map.put("msg", "104");
		}
		return isok;
	}
	
	
	
	
	
	
	
	
	
	//用户微信登入补全信息页面
	@RequestMapping(value = "/signInPageMsg")
	@ResponseBody
	public String signInPageMsg(HttpServletResponse response,HttpServletRequest requet) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String requetUrl = requet.getRequestURL().toString();
			String openid = HashSessions.getInstance().getOpenId(request);
			//获取所有的用户权限信息
			List<SysRole> roles = roleService.selectAllMsg();
			map.put("roles", roles);
			map.put("msg", "100");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}
	
	
	
	//用户微信登入补全信息
	@RequestMapping(value = "/updateUserMsg")
	@ResponseBody
	public String updateUserMsg(HttpServletRequest requet,HttpServletResponse response,UserInfo userInfo,
			@RequestParam(value = "roleId", defaultValue = "")String roleId,
			@RequestParam(value = "prjectNames", defaultValue = "")String prjectNames) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String requetUrl = requet.getRequestURL().toString();
			String openid = HashSessions.getInstance().getOpenId(request);
			//用户信息
			UserInfo userinfo = userInfoService.findByOpenid(openid);
			String userId = userinfo.getId();
			//用户填写的信息
			userInfo.setId(userId);
			userInfo.setOpenid(openid);
			userInfo.setPassword(MD5.GetMD5Code(userInfo.getPassword()));
			//更新用户数据
			userInfoService.update(userInfo);
			//给用户赋权限
			SysUserRole userRole = new SysUserRole();
			userRole.setId(key.getUUIDKey());
			userRole.setUserId(userId);
			userRole.setRoleId(roleId);
			//添加
			sysUserRoleService.insert(userRole);
			//绑定用户和项目之间的关系
			String[] projNames = prjectNames.split(",");
			for(String pro : projNames){
				Project proj = projectServise.selectByProName(pro);
				//实例用户项目关系表
				ProjUserRole projUserRole = new ProjUserRole();
				projUserRole.setId(key.getUUIDKey());
				projUserRole.setUserid(userId);
				projUserRole.setRoleid(roleId);
				//项目组ID
				String proId = proj.getId();
				projUserRole.setProjid(proId);
				projUserRoleService.insert(projUserRole);
			}
			map.put("msg", "100");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}
	
	
	
	
	
	//查看首访记录是否有该客户信息
	@RequestMapping(value = "/isHaveClienteleMsg")
	@ResponseBody
	public String isHaveClienteleMsg(HttpServletRequest requet,HttpServletResponse response,
			@RequestParam(value = "name", defaultValue = "")String name,
			@RequestParam(value = "phone", defaultValue = "")String phone,
			@RequestParam(value = "project", defaultValue = "")String project) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			String requetUrl = requet.getRequestURL().toString();
			Boolean isok = getUserOpenid(response,requetUrl, map);
			if(isok){
				String openid = HashSessions.getInstance().getOpenId(request);
				msg.put("name", name);
				msg.put("phone", phone);
				msg.put("project", project);
				//查询客户信息
				List<Map<String, Object>> accessRecord01 = accessRecord01Service.selectUserMsy(msg);
				map.put("accessRecord01", accessRecord01);
				map.put("msg", "100");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}
	
	
	
	//打开首访记录单
	@RequestMapping(value = "/openFirstRecord")
	@ResponseBody
	public String openFirstRecord(HttpServletRequest requet,HttpServletResponse response) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String requetUrl = requet.getRequestURL().toString();
			Boolean isok = getUserOpenid(response,requetUrl,map);
			if(isok){
				String openid = HashSessions.getInstance().getOpenId(request);
				//用户信息
				UserInfo userInfo = userInfoService.findByOpenid(openid);
				String userId = userInfo.getId();
				//查询用户和所属项目的所有信息
				List<Map<String, Object>> datas = projCustRefService.selectByUserId(userId);
				map.put("datas", datas);
				map.put("msg", "100");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}
	
	
	
	
	
	
	
	
	//添加首访记录
	@RequestMapping(value = "/addHisFirstRecord")
	@ResponseBody
	public String addHisFirstRecord(HttpServletRequest requet,HttpServletResponse response,AccessRecord01 record01) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String requetUrl = requet.getRequestURL().toString();
			Boolean isok = getUserOpenid(response,requetUrl,map);
			if(isok){
				String openid = HashSessions.getInstance().getOpenId(request);
				map.put("msg", "100");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}	
	
	
	
	
	
//	//添加首访记录
//	@RequestMapping(value = "/addHisFirstRecord")
//	@ResponseBody
//	public String addHisFirstRecord(HttpServletRequest requet,HttpServletResponse response,AccessRecord01 record01) {
//		responseInfo(response);
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			String requetUrl = requet.getRequestURL().toString();
//			String openid = getUserOpenid(response,requetUrl);
//			Boolean isok = userMsg(map,openid);
//			if(isok){
//				//客户表
//				Customer customer = new Customer();
//				String customerId = key.getUUIDKey();
//				customer.setId(customerId);
//				customer.setCustname(record01.getCustname());
//				customer.setPhonenum(record01.getCustphonenum());
//				customer.setCustsex("男");
//				//添加客户
//				customerService.insert(customer);
//				UserInfo userInfo = userInfoService.findByOpenid(openid);
//				//获取用户ID
//				String userId = userInfo.getId();
//				//补全首访信息-并更新
//				record01.setId(key.getUUIDKey());
//				record01.setCustid(customerId);
//				record01.setAuthorid(userId);
//				record01.setCreatorid(userId);
//				//添加首访记录
//				accessRecord01Service.insert(record01);
//				map.put("msg", "100");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			map.put("msg", "103");
//		}
//		System.out.println(JsonUtils.map2json(map));
//		return JsonUtils.map2json(map);
//	}	
	
	

	
	
	
	
	
	
	
	//用户中心-我的二维码
	@RequestMapping("/myQRcode")
	@ResponseBody
	public String myQRcode(Model model,HttpServletResponse response){
		responseInfo(response);
		Map<String,Object> map = new HashMap<String,Object>();
		//用户信息
		String openid = HashSessions.getInstance().getOpenId(request);
		openid = "oaBNt0xKNjXvStRlbKqMnk7QQ2Pw";
		UserInfo user = userInfoService.selectByOpenId(openid);
		String qrCodeAddress = user.getDescn();
		try {
			if(qrCodeAddress==null || qrCodeAddress.equals("")){
				String fileName = weixin.getQrcode(openid);
				user.setDescn(fileName);
				userInfoService.update(user);
			}
			String urlpath=Configurations.getConfig("ACCESSURL");
			map.put("img", urlpath + user.getDescn());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
