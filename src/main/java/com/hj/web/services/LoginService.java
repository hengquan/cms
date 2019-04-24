package com.hj.web.services;

import com.hj.web.entity.UserInfo;

public interface LoginService {
	/**
	 * 通过登录账号返回该用户信息
	 * @param loginId
	 * @return
	 */
	public UserInfo getUserInfo(String loginId);
	
}
