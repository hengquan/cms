package com.hj.wxmp.mobile.mapping;

import java.util.Map;

import com.hj.wxmp.mobile.entity.SysUserRole;

public interface SysUserRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);
    
    //通过管理员Id查询管理员角色
    SysUserRole selectByUserId(String userId);
    
    //根据userId修改用户角色
    int updateByUserId(SysUserRole ur);

	Map<String, Object> findByUserId(String userId);
}