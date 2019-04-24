package com.hj.web.mapping;

import java.util.List;
import java.util.Map;

import com.hj.web.entity.UserRole;

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

	List<UserRole> selectAll();

	UserRole selectByuserId(String userId);
}