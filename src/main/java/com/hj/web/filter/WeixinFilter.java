package com.hj.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hj.utils.HttpUtils;
import com.hj.utils.WeixinUtils;
import com.hj.wxmp.core.WxUser;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.common.Weixin;
import com.hj.wxmp.mobile.entity.UserInfo;
import com.hj.wxmp.mobile.services.UserInfoService;
import com.hj.wxmp.mobile.services.WxLoginService;

public class WeixinFilter implements Filter {
	
	private HashSessions hashSession = HashSessions.getInstance();
	
	private final static Logger logger = LoggerFactory.getLogger(WeixinFilter.class);
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		//HttpSession session=req.getSession();
		
		String openId = hashSession.getOpenId(req);
		if("".equals(openId)){
			if(WeixinUtils.isWeixinAccess(req)){
				String loginUrl = "/wx/login.ky";
				
//				String uri = req.getQueryString(); //req.getRequestURI();
//				if(!uri.contains("weixi_openId")){
//					req.getSession().setAttribute("wx_url",req.getQueryString());
//					logger.error("UrlTest{}===",uri);
//				}
//				logger.error("QueryString{}",req.getQueryString());

				String currentPageUrl = req.getRequestURL().toString();
				if(StringUtils.isNotBlank(req.getQueryString())){
					currentPageUrl = req.getRequestURL() + "?" + req.getQueryString();
				}
				req.getSession().setAttribute("weixin_current_url", currentPageUrl);

				res.sendRedirect(req.getContextPath()+loginUrl);
				
				/*logger.error("WeixinFilter——currentPageUrl----" + currentPageUrl);
				logger.error("WeixinFilter——req.getContextPath()----" + req.getContextPath()+"--loginUrl:"+loginUrl);*/
			}else{
				WxUser wxUser = new WxUser(true);
				openId = wxUser.getOpenid();
				
				hashSession.setOpenId(req, res, openId);
				
				String successUrl = req.getRequestURI();
				if(StringUtils.isBlank(successUrl))successUrl = req.getContextPath() +"/wx/user/index.az";
				res.sendRedirect(successUrl);
			}
			
			hashSession.setQueryParam(req, HttpUtils.getQueryParams(req));
		}else{
			
			String currentPageUrl = req.getRequestURL().toString();
			if(StringUtils.isNotBlank(req.getQueryString())){
				currentPageUrl = req.getRequestURL() + "?" + req.getQueryString();
			}
			req.getSession().setAttribute("weixin_current_url", currentPageUrl);
			
			
			if(WeixinUtils.isWeixinAccess(req)){
				Weixin weixin = Weixin.getInstance();
				WxUser wxUser = weixin.getUserInfo(openId);
				if(null!=wxUser){
					WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());  
					WxLoginService wxLoginService = (WxLoginService)wac.getBean("wxLoginServiceImpl");
					if(null!=wxLoginService){
						UserInfo  sysUser = wxLoginService.bandingUser(wxUser,ObjectUtils.toString(req.getSession().getAttribute("weixin_current_url")));
						if(null!=sysUser){
							hashSession.setSessionUserInfo(req, sysUser);
							
							System.out.println(".......runing.......sucsess banding... :"+wxUser);
						}
					}
				}
				System.out.println(".......runing.......wxUser:"+wxUser);
			}else{
				try {
					WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());  
					UserInfoService sysUserService = (UserInfoService)wac.getBean("userInfoServiceImpl");
					UserInfo userInfo = sysUserService.findById("haha123");
					if(null!=userInfo){
						hashSession.setSessionUserInfo(req, userInfo);
					}
				} catch (BeansException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	
}
