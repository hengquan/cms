package com.hj.wxmp.mobile.services.impl;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.entity.SysAdmin;
import com.hj.wxmp.mobile.mapping.SysAdminMapper;
import com.hj.wxmp.mobile.services.LoginService;

@Component
public class LoginServiceImpl implements LoginService {
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	private SysAdminMapper sysAdminMapper;
	
	
	@Override
	public SysAdmin getUserInfo(String loginId) {
		try {
			return sysAdminMapper.selectByLoginId(loginId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
		return null;
	}

}
