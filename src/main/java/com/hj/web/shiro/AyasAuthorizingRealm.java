package com.hj.web.shiro;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.hj.wxmp.mobile.entity.SysAdmin;
import com.hj.wxmp.mobile.services.LoginService;

public class AyasAuthorizingRealm extends AuthorizingRealm{

	@Resource
	private LoginService  loginServiceImpl;
	
	/**
	 * 登录认证
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String userName = token.getUsername();
		SysAdmin user = loginServiceImpl.getUserInfo(userName);
		if (null!=user) {
			String password = user.getPassword();
			
			return new SimpleAuthenticationInfo(userName, password, getName());
		} else {
			return null;
		}
		
		//return new SimpleAuthenticationInfo(userName, "123456", getName());
	}

	/**
	 * 授权（登录成功后把权限加入到shiro的AuthorizationInfo认证对象中）
	 * 
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = (String) principals.getPrimaryPrincipal();
		
		//SysUser user = wx.getUserInfoByUserName(userName);//modify by 9.8
		
		SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
		if (false) {//user != null //modify by 9.8
			//读取用户权限
			/*Set<String>viewPermissionSet=new HashSet<String>();
			Set<String> perms = user.getPerms(site.getId(),viewPermissionSet);
			if (!CollectionUtils.isEmpty(perms)) {
				// 权限加入AuthorizationInfo认证对象
				auth.setStringPermissions(perms);
			}*/
			Set<String> perms = new HashSet<String>();
			perms.add("1");
			
			auth.setStringPermissions(perms);
		}
		return auth;
	}
	
	public void removeUserAuthorizationInfoCache(String username){
		  SimplePrincipalCollection pc = new SimplePrincipalCollection();
		  pc.add(username, super.getName()); 
		  super.clearCachedAuthorizationInfo(pc);
	}

}
