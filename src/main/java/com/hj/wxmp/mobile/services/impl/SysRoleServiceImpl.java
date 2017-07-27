package com.hj.wxmp.mobile.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.entity.Project;
import com.hj.wxmp.mobile.entity.SysRole;
import com.hj.wxmp.mobile.mapping.ProjectMapper;
import com.hj.wxmp.mobile.mapping.SysRoleMapper;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.ProjectService;
import com.hj.wxmp.mobile.services.SysRoleService;

@Component
public class SysRoleServiceImpl implements SysRoleService {

	@Resource
	private IKeyGen keyGen;
	@Resource
	private SysRoleMapper dao;
	
	@Override
	public boolean insert(SysRole entity) throws Exception {
		return dao.insertSelective(entity)>0?true:false;
	}
	@Override
	public boolean update(SysRole entity) throws Exception {
		return dao.updateByPrimaryKeySelective(entity)>0?true:false;
	}
	@Override
	public List<SysRole> listEntity(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void delete(SysRole entity) throws Exception {
		dao.deleteByPrimaryKey(entity.getId());
	}
	@Override
	public SysRole saveEntity(SysRole entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SysRole findById(String sys_uuid) throws Exception {
		return dao.selectByPrimaryKey(sys_uuid);
	}
	@Override
	public SysRole findByOpenid(String openid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int perfectUserInfo(SysRole sysUser) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<SysRole> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int selectCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<SysRole> selectAllMsg() {
		return dao.selectAllMsg();
	}
	


}
