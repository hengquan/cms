package com.hj.common.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import com.hj.utils.Constants;
import com.hj.utils.CookieUtils;

public class AyasLogoutFilter extends LogoutFilter {
	/**
	 * 返回URL
	 */
	public static final String RETURN_URL = "returnUrl";

	protected String getRedirectUrl(ServletRequest req, ServletResponse resp, Subject subject) {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		String redirectUrl = request.getParameter(RETURN_URL);
		if (StringUtils.isBlank(redirectUrl)) {
			/*
			 * if (request.getRequestURI().startsWith(request.getContextPath() +
			 * getAdminPrefix())) { redirectUrl = getAdminLogin(); } else {
			 * redirectUrl = getRedirectUrl(); }
			 */

			redirectUrl = getRedirectUrl();
		}
		
		if("/".equals(redirectUrl))redirectUrl = "/home/index";
		
		CookieUtils.cancleCookie(request, response, Constants.COOKIE_WEIXIN_OPEN_ID, null);
		
		return redirectUrl;
	}
}
