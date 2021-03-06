package com.hj.web.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hj.web.entity.SysRole;
import com.hj.web.mapping.SysRoleMapper;
import com.hj.web.services.IKeyGen;
import com.hj.web.services.SysRoleService;

@Component
public class SysRoleServiceImpl implements SysRoleService {

	@Resource
	private IKeyGen keyGen;
	@Resource
	private SysRoleMapper dao;

	@Override
	public boolean insert(SysRole entity) throws Exception {
		return dao.insert(entity) > 0 ? true : false;
	}

	@Override
	public boolean update(SysRole entity) throws Exception {
		return dao.update(entity) > 0 ? true : false;
	}

	@Override
	public List<SysRole> listEntity(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(SysRole entity) throws Exception {
		dao.del(entity.getId());
	}

	@Override
	public SysRole saveEntity(SysRole entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SysRole findById(String sys_uuid) throws Exception {
		return dao.get(sys_uuid);
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

	@Override
	public List<SysRole> findMeAndParentList() {
		return dao.findMeAndParentList();
	}

	@Override
	public List<SysRole> findParentById(String id) {
		return dao.findParentById(id);
	}

	@Override
	public SysRole findByPinYin(String tab) {
		return dao.findByPinYin(tab);
	}

}
