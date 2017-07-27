package com.hj.wxmp.mobile.services;

import com.hj.wxmp.mobile.entity.UserInfo;

public interface LoginService {
	/**
	 * 通过登录账号返回该用户信息
	 * @param loginId
	 * @return
	 */
	public UserInfo getUserInfo(String loginId);
	
}
