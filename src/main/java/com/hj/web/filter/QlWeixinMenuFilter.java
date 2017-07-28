package com.hj.web.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hj.wxmp.core.WxUser;
import com.hj.wxmp.mobile.common.Configurations;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.common.Weixin;
import com.hj.wxmp.mobile.controller.WxApiController;
import com.hj.wxmp.mobile.entity.UserInfo;
import com.hj.wxmp.mobile.services.UserInfoService;
import com.hj.wxmp.mobile.services.WxLoginService;

public class QlWeixinMenuFilter implements Filter {
	
	private final static Logger logger = LoggerFactory.getLogger(QlWeixinMenuFilter.class);
	
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private WxLoginService wxLoginService;
	
	// 根据openid去腾讯后台获取用户信息并更新本地userinfo
	private void updateUserInfo(String openid,HttpServletRequest req) {
		if (StringUtils.trimToNull(openid) != null) {
			WxUser wxUser = Weixin.getInstance().getUserInfo(openid);
			wxLoginService.bandingUser(wxUser, ObjectUtils.toString(
					req.getSession().getAttribute("weixin_current_url")));
		}
	}
	
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		String requestURL = req.getRequestURL().toString();
		Map<String,Object> map = new HashMap<String,Object>();
		//01-处理参数
		//02-判断
		//int result=checkWeiXinUser(??);
		//03-根据判断结果跳转
		//if (result==1) res.sendRedirect(req.getContextPath()+loginUrl);//成功：返回本身的URL
		//if (result==2) 
		try {
			String openid = HashSessions.getInstance().getOpenId(req);
			if (StringUtils.stripToNull(openid) == null) {
				this.getOpenid(res,requestURL,req);
			}else{
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.sendRedirect("/wxfront/input/record01.html");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

	
	//通用方法-用户是否有openid
	public Boolean getUserOpenid(HttpServletResponse response,String requetUrl,
			HttpServletRequest request) throws Exception{
		String openid = HashSessions.getInstance().getOpenId(request);
		Boolean isok = false;
		if (StringUtils.stripToNull(openid) != null) {
			updateUserInfo(openid,request);
			isok = userMsg(map,openid);
		}
		return isok;
	}
	
	
	
	//是否有openid
	public void getOpenid(HttpServletResponse response,String requetUrl,
		HttpServletRequest request) throws Exception{
		String siteName = Configurations.getSiteName();
		String url = siteName+"/wxmp.ql/wx/api/redirectUrl.az?wx_url="+requetUrl;
		logger.debug("messageURL----asdfa{}",url);
		response.sendRedirect(URLDecoder.decode(url, "UTF-8"));
	}
	
	
	
	//通用接口(所有的接口都会来访问该接口看用户信息是否完整和审核是否通过)
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
	

	
	
	
	
	
}
