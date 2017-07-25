package com.hj.wxmp.mobile.services;

import java.util.Map;

import com.hj.wxmp.mobile.entity.SysUserRole;

public interface SysUserRoleService {
	/**
	 * 通过管理员Id查询管理员角色
	 * @param userId
	 * @return
	 */
	public SysUserRole selectByUserId(String userId);
	
	/**
	 * 添加用户角色
	 * @author deng.hemei
	 * @description 
	 * @return Integer
	 * @updateDate 2016年7月1日
	 */
	public int insert(SysUserRole ur);
	
	/**
	 * 根据userId修改用户角色
	 * @author deng.hemei
	 * @description 
	 * @return 
	 * @updateDate 2016年7月1日
	 */
	public int updateByUserId(SysUserRole ur);

	public Map<String, Object> findByUserId(String userId);
	
}
