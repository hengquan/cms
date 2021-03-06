package com.hj.web.services.impl;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.hj.web.entity.UserInfo;
import com.hj.web.mapping.UserInfoMapper;
import com.hj.web.services.LoginService;

@Component
public class LoginServiceImpl implements LoginService {
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	private UserInfoMapper userInfoMapper;
	
	
	@Override
	public UserInfo getUserInfo(String loginId) {
		try {
			return userInfoMapper.selectByLoginId(loginId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
