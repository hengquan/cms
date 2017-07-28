package com.hj.wxmp.mobile.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.entity.UserRole;
import com.hj.wxmp.mobile.mapping.UserRoleMapper;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.UserRoleService;

@Component
public class UserRoleServiceImpl implements UserRoleService {
	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	IKeyGen key;
	@Resource
	private UserRoleMapper dao;

	//通过管理员Id查询管理员角色
	@Override
	public UserRole selectByUserId(String userId) {
		UserRole userRole=dao.selectByUserId(userId);
		return userRole;
	}

	//添加用户角色
	@Override
	public int insert(UserRole ur) {
		return dao.insertSelective(ur);
	}

	//根据userId修改用户角色
	@Override
	public int updateByUserId(UserRole ur) {
		int row=dao.updateByUserId(ur);
		return row;
	}

	@Override
	public Map<String, Object> findByUserId(String userId) {
		return dao.findByUserId(userId);
	}

	@Override
	public List<UserRole> selectAll() {
		return dao.selectAll();
	}
	
	

}
