package com.hj.wxmp.mobile.services;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.UserRole;

public interface UserRoleService {
	/**
	 * 通过管理员Id查询管理员角色
	 * @param userId
	 * @return
	 */
	public UserRole selectByUserId(String userId);
	
	/**
	 * 添加用户角色
	 * @author deng.hemei
	 * @description 
	 * @return Integer
	 * @updateDate 2016年7月1日
	 */
	public int insert(UserRole ur);
	
	/**
	 * 根据userId修改用户角色
	 * @author deng.hemei
	 * @description 
	 * @return 
	 * @updateDate 2016年7月1日
	 */
	public int updateByUserId(UserRole ur);

	public Map<String, Object> findByUserId(String userId);

	public List<UserRole> selectAll();

	public UserRole selectByuserId(String userId);

	public void update(UserRole userRole);
}
