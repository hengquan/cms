package com.hj.wxmp.mobile.services;

import com.hj.wxmp.core.WxUser;
import com.hj.wxmp.mobile.entity.UserInfo;

public interface WxLoginService {

	/**
	 * 绑定微信用户
	 * 
	 * @author jun.hai
	 * @param wxUser
	 * @return
	 */
	public UserInfo bandingUser(WxUser wxUser,String curUrl);

}
