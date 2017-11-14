package com.hj.wxmp.mobile.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.utils.DateUtils;
import com.hj.utils.JsonUtils;
import com.hj.utils.MD5Utils;
import com.hj.web.core.mvc.ControllerBase;
import com.hj.wxmp.mobile.common.ApiUrls;
import com.hj.wxmp.mobile.common.Configurations;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.entity.UserInfo;
import com.hj.wxmp.mobile.services.LoginService;
import com.hj.wxmp.mobile.services.UserInfoService;
import com.spiritdata.framework.component.UGA.service.UserService;

/**
 * 
* @ClassName: LoginController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jun.hai
* @date 2015年1月9日 下午8:21:12
*
 */
@Controller
public class LoginController extends ControllerBase {
	
	private final static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	private HashSessions hashSession = HashSessions.getInstance();
	
	
	@Resource
	private LoginService loginService;
	@Autowired
	private UserInfoService userInfoService;
	
	@RequestMapping(value = "/login.ky", method = RequestMethod.GET)
	public String login(ModelMap map) {
		try {
			map.put("appid", Configurations.getAppId());
			map.put("weixinCodeUrl", ApiUrls.WEIXIN_CODE_URL);
			map.put("redirect_uri", URLEncoder.encode(Configurations.getOpenIdRedirectUri(), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "login/new_login";
	}
	
	@RequestMapping(value = "/login.ky", method = RequestMethod.POST)
	public String doLogin(ModelMap map) {
		String userName = getTrimParameter("userName");
		String password = getTrimParameter("password");
	 
		String fallbackUrl = "/";
		String successUrl = null;
	    try {
	    	UserInfo userInfo = loginService.getUserInfo(userName);
	      if (null!=userInfo) {
	        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
	        Subject subject = SecurityUtils.getSubject();
	        subject.login(token);
	        
	        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
	        if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD)) {
	            successUrl = savedRequest.getRequestUrl();
	        }

	        if (successUrl == null) {
	            successUrl = fallbackUrl;
	        }
	        
	        hashSession.setUserInfoSession(request, userInfo);
	        if(successUrl.indexOf("wxmp.ql")>-1){
	        	successUrl =  successUrl.substring(successUrl.indexOf("wxmp.ql")).replaceAll("wxmp.ql", "");
	        }
	        
	        
	        String newpassword = MD5Utils.MD5(password);
	        Integer isvalidate = userInfo.getIsvalidate();
	        if(newpassword.equals(userInfo.getPassword())&&isvalidate==1){
        		successUrl="/accessRecord/hisFirstRecord";
        		successUrl = "redirect:"+successUrl;
	        }else{
				map.put("loginId", userName);
				map.put("loginFaild", "用户密码错误，请核实或联系管理员！"); 
				successUrl = "login/new_login";
	        }
	      }else{
	    	  map.put("loginId", userName);
	    	  map.put("loginFaild", "该用户未注册，请核实或联系管理员！"); 
	    	  successUrl = "login/new_login";
	      }
	    } catch (Exception e) {
	    	map.put("loginId", userName);
	    	e.printStackTrace();
	        logger.error("登录失败:--用户名："+userName+"--时间："+DateUtils.getCurrentTime());
	        map.put("loginFaild", "密码/用户名不符合，请重新输入！"); 
	        successUrl = "login/new_login";
	    }
	    
		return successUrl;
	}
	
	@RequestMapping(value = "/logout.ky")
	public String logout(ModelMap map) {
		
		return "redirect:login.ky";
	}
	
	
	@RequestMapping(value = "/wx/login.ky", method = RequestMethod.GET)
	public String wxlogin(ModelMap map) {
		try {
			map.put("appid", Configurations.getAppId());
			map.put("weixinCodeUrl", ApiUrls.WEIXIN_CODE_URL);
			map.put("redirect_uri", URLEncoder.encode(Configurations.getOpenIdRedirectUri(), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("map---Msg{}",map);
		return "login/wxlogin";
	}
	
	
	
	@RequestMapping(value = "/loginMsg")
	@ResponseBody
	public String loginMsg() {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//用户角色权限信息
			String id = hashSession.getCurrentAdmin(request).getId();
			UserInfo userInfo = userInfoService.findById(id);
			map.put("msg", "100");
			map.put("userInfo", userInfo);
		} catch (Exception e) {
			map.put("msg", "100");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}
	
}
