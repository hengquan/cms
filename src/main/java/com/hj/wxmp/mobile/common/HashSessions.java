package com.hj.wxmp.mobile.common;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.hj.utils.CookieUtils;
import com.hj.wxmp.mobile.entity.SysAdmin;
import com.hj.wxmp.mobile.entity.SysItemRole;
import com.hj.wxmp.mobile.entity.UserInfo;

/**
 * 
 * @ClassName: HashSessions
 * @Description: TODO(公共信息保存到HashMap中，自己管理session)
 * @author jun.hai
 * @date 2015年2月16日 下午7:03:07
 *
 */
public class HashSessions {

	private static HashSessions uniqueInstance = null;

	private static Map<String, Object> userMap = new ConcurrentHashMap<String, Object>();
	
	private static Map<String, Object> accessTokenMap = new ConcurrentHashMap<String, Object>();
	
	private static Map<String, Object> dicMap = new ConcurrentHashMap<String, Object>();
	
	private static Map<String, Object> validateCodeMap = new ConcurrentHashMap<String, Object>();
	
	private static Map<String, Object> itemRoleMap = new ConcurrentHashMap<String, Object>();
	
	private static Map<String, Object> ticketMap = new ConcurrentHashMap<String, Object>();
	
	private HashSessions() {
	}

	public static HashSessions getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new HashSessions();
		}
		return uniqueInstance;
	}

	public void setUserInfo(String openId,Map<String,Object> userInfo){
		userMap.put(openId, userInfo);
	}
	
	/**
	 * 
	* @Title: getUserInfo 
	* @Description: TODO(通过openId返回用户信息,key值为：mobile--手机号，name--用户名，id--crm返回的唯一id下单时用，role--用户所属角色) 
	* @param  @param openId
	* @param  @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @author jun.hai  
	* @date 2015年2月26日 下午4:51:38 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getUserInfo(String openId){
		Object obj = userMap.get(openId);
		if(null !=obj){
			return (Map<String,Object>)obj;
		}
		
		return null;
	}
	
	public Map<String,Object> currentUserInfo(HttpServletRequest request){
		return getUserInfo(getOpenId(request));
	}
	
	public String currentRole(HttpServletRequest request){
		Map<String,Object> user = currentUserInfo(request);
		if(null!=user){
			return ObjectUtils.toString(user.get("role"));
		}
		return "";
	}
	
	public void setOpenId(HttpServletRequest request,HttpServletResponse response,String openId){
		CookieUtils.addCookie(request, response, Constants.COOKIE_WEIXIN_OPEN_ID, openId, Constants.COOKIE_EXPIRY, null);
	}
	
	public String getOpenId(HttpServletRequest request){
		Cookie cookie = CookieUtils.getCookie(request, Constants.COOKIE_WEIXIN_OPEN_ID);
		if(null != cookie){
			return cookie.getValue();
		}
		
		return "";
	}
	
	public void setAccessToken(String accessToken,String expires_in){
		accessTokenMap.put(Constants.HASH_KEY_ACCESS_TOKEN, accessToken);
		accessTokenMap.put(Constants.HASH_KEY_ACCESS_TOKEN_EXPIRES_IN, expires_in);
	}
	
	public void setTicket(String ticket,String expire_seconds){
		ticketMap.put(Constants.HASH_KEY_TICKET, ticket);
		ticketMap.put(Constants.HASH_KEY_TICKET_EXPIRE_SECONDS, expire_seconds);
	}
	
	public String getAccessToken(){
		return ObjectUtils.toString(accessTokenMap.get(Constants.HASH_KEY_ACCESS_TOKEN));
	}
	
	public String getTicket(){
		return ObjectUtils.toString(ticketMap.get(Constants.HASH_KEY_ACCESS_TOKEN));
	}
	
	public long getAccessTokenExpires(){
		return NumberUtils.toLong(ObjectUtils.toString(accessTokenMap.get(Constants.HASH_KEY_ACCESS_TOKEN_EXPIRES_IN)));
	}

	public long getTicketExpires(){
		return NumberUtils.toLong(ObjectUtils.toString(ticketMap.get(Constants.HASH_KEY_TICKET_EXPIRE_SECONDS)));
	}
	
	public void setJsapiTicket(String jsapiTicket,String expires_in){
		accessTokenMap.put(Constants.HASH_KEY_JSAPI_TICKET, jsapiTicket);
		accessTokenMap.put(Constants.HASH_KEY_JSAPI_TICKET_EXPIRES_IN, expires_in);
	}
	
	public String getJsapiTicket(){
		return ObjectUtils.toString(accessTokenMap.get(Constants.HASH_KEY_JSAPI_TICKET));
	}
	
	public long getJsapiTicketExpires(){
		return NumberUtils.toLong(ObjectUtils.toString(accessTokenMap.get(Constants.HASH_KEY_JSAPI_TICKET_EXPIRES_IN)));
	}
	
	public String getAppRoot(){
		return ObjectUtils.toString(dicMap.get(Constants.HASH_KEY_APP_ROOT));
	}
	
	public void setAddminSession(HttpServletRequest request,SysAdmin sysAdmin){
		request.getSession().setAttribute(Constants.HASH_KEY_ADMINSESSION, sysAdmin);
	}
	
	public void setItemRoleSession(HttpServletRequest request,List<SysItemRole> lst){
		request.getSession().setAttribute(Constants.HASH_KEY_LST, lst);
	}
	
	public SysAdmin getCurrentAdmin(HttpServletRequest request){
		Object obj = request.getSession().getAttribute(Constants.HASH_KEY_ADMINSESSION);
		if(null!=obj){
			SysAdmin admin = (SysAdmin)obj;
			return admin;
		}
		
		return new SysAdmin();
	}
	
	public  void setValidateCode(String mobile,String code){
		validateCodeMap.put(mobile, code);
	}
	
	public  String getValidateCode(String mobile){
		return ObjectUtils.toString(validateCodeMap.get(mobile));
	}
	
	public void setQueryParam(HttpServletRequest request,Map<String,Object> params){
		request.getSession().setAttribute(Constants.WEIXIN_SESSION_CURRENT_QUERYTRING, params);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> getQueryParam(HttpServletRequest request){
		 Object obj = request.getSession().getAttribute(Constants.WEIXIN_SESSION_CURRENT_QUERYTRING);
		 
		 if(null!=obj){
			 return (Map<String,Object>)obj;
		 }
		 return null;
	}
	
	public void setSessionUserInfo(HttpServletRequest request,UserInfo userInfo){
		request.getSession().setAttribute(Constants.WEIXIN_SESSION_USER_INFO, userInfo);
	}
	
	public UserInfo getCurrentSessionUser(HttpServletRequest request){
		 Object obj = request.getSession().getAttribute(Constants.WEIXIN_SESSION_USER_INFO);
		 
		 if(null!=obj) return (UserInfo)obj;
		 
		 return null;
	}
	
	public  void setItemRole(String key,List<SysItemRole> itemList){
		itemRoleMap.put(key, itemList);
	}
	
	@SuppressWarnings("unchecked")
	public  List<SysItemRole> getItemRole(String key){
		return (List<SysItemRole>)itemRoleMap.get(key);
	}
}
