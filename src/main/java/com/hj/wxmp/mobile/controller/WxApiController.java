package com.hj.wxmp.mobile.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hj.wxmp.mobile.common.Weixin;
import com.hj.wxmp.mobile.dao.SysItemRoleDao;
import com.hj.wxmp.mobile.entity.AccessRecord01;
import com.hj.wxmp.mobile.entity.Customer;
import com.hj.wxmp.mobile.entity.UserInfo;
import com.hj.wxmp.mobile.services.AccessRecord01Service;
import com.hj.wxmp.mobile.services.AccessRecord02Service;
import com.hj.wxmp.mobile.services.AccessRecord03Service;
import com.hj.wxmp.mobile.services.CustomerService;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.ProjCustRefService;
import com.hj.wxmp.mobile.services.ProjUserRoleService;
import com.hj.wxmp.mobile.services.ProjectService;
import com.hj.wxmp.mobile.services.SysAdminService;
import com.hj.wxmp.mobile.services.SysUserRoleService;
import com.hj.wxmp.mobile.services.UserCustRefService;
import com.hj.wxmp.mobile.services.UserInfoService;
import com.hj.wxmp.mobile.services.WxLoginService;

import net.sf.json.JSONObject;

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
	private SysAdminService sysAdminService;;
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

	@RequestMapping(value = "/getOpenid")
	@ResponseBody
	public JSONObject getOpenid(HttpServletResponse response) {
		responseInfo(response);
		String openid = HashSessions.getInstance().getOpenId(request);
		logger.debug("thisOpenId1234==={}", openid);
		updateUserInfo(openid);
		JSONObject json = new JSONObject();
		if (StringUtils.stripToNull(openid) != null) {
			json.put("msg", "100");
		} else {
			json.put("msg", "103");
		}
		return json;
	}

	public void responseInfo(HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8;");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods",
				"POST, GET, OPTIONS,DELETE,PUT");
	}
	
	
	
	
	//查看首访记录是否有该客户信息
	@RequestMapping(value = "/isHaveClienteleMsg")
	@ResponseBody
	public String isHaveClienteleMsg(HttpServletResponse response,
			@RequestParam(value = "name", defaultValue = "")String name,
			@RequestParam(value = "phone", defaultValue = "")String phone,
			@RequestParam(value = "project", defaultValue = "")String project) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> msg = new HashMap<String, Object>();
		String openid = HashSessions.getInstance().getOpenId(request);
		try {
			msg.put("name", name);
			msg.put("phone", phone);
			msg.put("project", project);
			//查询客户信息
			List<Map<String, Object>> accessRecord01 = accessRecord01Service.selectUserMsy(msg);
			map.put("accessRecord01", accessRecord01);
			map.put("msg", "100");
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
	public String openFirstRecord(HttpServletResponse response) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		String openid = HashSessions.getInstance().getOpenId(request);
		try {
			//用户信息
			UserInfo userInfo = userInfoService.findByOpenid(openid);
			String userId = userInfo.getId();
			//查询用户和所属项目的所有信息
			List<Map<String, Object>> datas = projCustRefService.selectByUserId(userId);
			map.put("datas", datas);
			map.put("msg", "100");
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
	public String addHisFirstRecord(HttpServletResponse response,AccessRecord01 record01) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		String openid = HashSessions.getInstance().getOpenId(request);
		try {
			//客户表
			Customer customer = new Customer();
			String customerId = key.getUUIDKey();
			customer.setId(customerId);
			customer.setCustname(record01.getCustname());
			customer.setPhonenum(record01.getCustphonenum());
			customer.setCustsex("男");
			//添加客户
			customerService.insert(customer);
			//获取用户ID
			UserInfo userInfo = userInfoService.findByOpenid(openid);
			String userId = userInfo.getId();
			//补全首访信息-并更新
			record01.setId(key.getUUIDKey());
			record01.setCustid(customerId);
			record01.setAuthorid(userId);
			record01.setCreatorid(userId);
			//添加首访记录
			accessRecord01Service.insert(record01);
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
	public String updateUserMsg(HttpServletResponse response,UserInfo userInfo) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		String openid = HashSessions.getInstance().getOpenId(request);
		try {
			
			
			
			
			map.put("msg", "100");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}	
	
	

}
