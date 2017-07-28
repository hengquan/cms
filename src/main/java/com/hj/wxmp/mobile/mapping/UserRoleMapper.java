package com.hj.wxmp.mobile.mapping;

import java.util.Map;

import com.hj.wxmp.mobile.entity.UserRole;

public interface UserRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
    
    //通过管理员Id查询管理员角色
    UserRole selectByUserId(String userId);
    
    //根据userId修改用户角色
    int updateByUserId(UserRole ur);

	Map<String, Object> findByUserId(String userId);
}