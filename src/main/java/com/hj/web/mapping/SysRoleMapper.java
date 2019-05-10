package com.hj.web.mapping;

import java.util.List;

import com.hj.web.entity.SysRole;

public interface SysRoleMapper {
	int del(String id);

	int insert(SysRole record);

	SysRole get(String id);

	int update(SysRole record);

	List<SysRole> selectAllMsg();

	List<SysRole> findMeAndParentList();

	List<SysRole> findParentById(String id);
}