package com.hj.common.shiro;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hj.utils.HashSessions;


public class AyasAuthenticationFilter extends FormAuthenticationFilter{
	private Logger logger = LoggerFactory.getLogger(AyasAuthenticationFilter.class);
	
	/**
	 * 返回URL
	 */
	public static final String RETURN_URL = "returnUrl";
	
	protected boolean executeLogin(ServletRequest request,ServletResponse response) throws Exception {
		AuthenticationToken token = createToken(request, response);
		if (token == null) {
			String msg = "create AuthenticationToken error";
			throw new IllegalStateException(msg);
		}
		
		/*HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String username = (String) token.getPrincipal();*/
		
		boolean adminLogin = true;
		try {
			Subject subject = getSubject(request, response);
			subject.login(token);;
			return onLoginSuccess(token,adminLogin,subject, request, response);
		} catch (AuthenticationException e) {
			return onLoginFailure(token,adminLogin, e, request, response);
		}
	}

	public boolean onPreHandle(ServletRequest request,ServletResponse response, Object mappedValue) throws Exception {
		boolean isAllowed = isAccessAllowed(request, response, mappedValue);
		//登录跳转
		if (isAllowed && isLoginRequest(request, response)) {
			try {
				issueSuccessRedirect(request, response);
			} catch (Exception e) {
				logger.error("", e);
			}
			return false;
		}
		
		//如果在微网站后台有数据，则默认为已登录,把用户信息写入session
		//getBindInfo
		//通过openid从微网站后台查询，是否已绑定过，若已绑定读取用户信息自动登录，否则弹出登录页面，输入用户信息进行登录的同时绑定openid，写入微网站后台
		HttpServletRequest req = (HttpServletRequest) request;
		String openId = HashSessions.getInstance().getOpenId(req);
		if(!StringUtils.isBlank(openId)){
			//SysUser user = wx.getUserInfoByOpenId(openId);//modify by 9.8
			
			if(true){ //null!=user //modify by 9.8
				//issueDefaultLoginRedirect(request, response,user.getUserName(),Base64Utils.decodeToBaseString(user.getColumn2()));
				issueDefaultLoginRedirect(request, response,"01","123456");
			}else{
				//从crm读取信息
				String mobile = req.getParameter("userName");
				String password = req.getParameter("password");
				String name = "";
				String lvName = "";
				String lvBp = "";
				String lvRole = "1";
				
				//SysUser userTemp = wx.getUserInfoByUserName(mobile);//modify by 9.8
				if(true){//null==userTemp //modify by 9.8
					//wx.bindOpenId(openId, mobile, name, password, lvName, lvBp, lvRole);//modify by 9.8
					
					Map<String,Object> userMap = new HashMap<String,Object>();
					userMap.put("mobile", mobile);
					userMap.put("name", name);
					userMap.put("id", lvBp);
					userMap.put("role", lvRole);
					HashSessions.getInstance().setUserInfo(openId, userMap);
				}
			}
		}
		
		return isAllowed || onAccessDenied(request, response, mappedValue);
	}
	
	protected void issueDefaultLoginRedirect(ServletRequest request, ServletResponse response,String userName,String password)
			throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
		
        //可以保持连接到当前点击的URL上！
		WebUtils.redirectToSavedRequest(req, res, "/login.ky");
	}

	protected void issueSuccessRedirect(ServletRequest request, ServletResponse response)
			throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String successUrl = req.getParameter(RETURN_URL);
		if (StringUtils.isBlank(successUrl)) {
			/*if (req.getRequestURI().startsWith(req.getContextPath() + getAdminPrefix())) {
				// 后台直接返回首页
				successUrl = getAdminIndex();
				// 清除SavedRequest
				WebUtils.getAndClearSavedRequest(request);
				WebUtils.issueRedirect(request, response, successUrl, null,true);
				return;
			} else {
				successUrl = getSuccessUrl();
			}*/
			
			successUrl = getSuccessUrl();
		}
		WebUtils.redirectToSavedRequest(req, res, successUrl);
	}

	protected boolean isLoginRequest(ServletRequest req, ServletResponse resp) {
		return pathsMatch(getLoginUrl(), req);
	}

	/**
	 * 登录成功
	 */
	private boolean onLoginSuccess(AuthenticationToken token,boolean adminLogin,Subject subject,ServletRequest request, ServletResponse response)
			throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		/*String username = (String) subject.getPrincipal();
		CmsUser user = cmsUserMng.findByUsername(username);
		String ip = RequestUtils.getIpAddr(req);
		userMng.updateLoginInfo(user.getId(), ip);*/
		String userName = (String) subject.getPrincipal();
		//1.调用crm接口
		//2.绑定oepnid和手机号及其crm接口返回的信息
		return super.onLoginSuccess(token, subject, request, response);
	}

	/**
	 * 登录失败
	 */
	private boolean onLoginFailure(AuthenticationToken token,boolean adminLogin,AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		/*String username = (String) token.getPrincipal();
		Integer errorRemaining = unifiedUserMng.errorRemaining(username);
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;*/

		return super.onLoginFailure(token, e, request, response);
	}
	
	
}
