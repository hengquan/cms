package com.hj.web.mapping;

import java.util.List;

import com.hj.web.entity.SysItemRole;

public interface SysItemRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysItemRole record);

    int insertSelective(SysItemRole record);

    SysItemRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysItemRole record);

    int updateByPrimaryKey(SysItemRole record);
    
    //根据角色ID查看角色权限
    List<SysItemRole> selectByRoleId(String roleId);
    
    List<SysItemRole> selectItemByRoleId(String roleId);
}