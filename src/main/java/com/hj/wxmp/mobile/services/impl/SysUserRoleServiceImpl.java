package com.hj.wxmp.mobile.services.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.entity.SysUserRole;
import com.hj.wxmp.mobile.mapping.SysUserRoleMapper;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.SysUserRoleService;

@Component
public class SysUserRoleServiceImpl implements SysUserRoleService {
	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	IKeyGen key;
	@Resource
	private SysUserRoleMapper sysUserRoleMapper;

	//通过管理员Id查询管理员角色
	@Override
	public SysUserRole selectByUserId(String userId) {
		SysUserRole userRole=sysUserRoleMapper.selectByUserId(userId);
		return userRole;
	}

	//添加用户角色
	@Override
	public int insert(SysUserRole ur) {
		return sysUserRoleMapper.insertSelective(ur);
	}

	//根据userId修改用户角色
	@Override
	public int updateByUserId(SysUserRole ur) {
		int row=sysUserRoleMapper.updateByUserId(ur);
		return row;
	}

	@Override
	public Map<String, Object> findByUserId(String userId) {
		return sysUserRoleMapper.findByUserId(userId);
	}
	
	

}
