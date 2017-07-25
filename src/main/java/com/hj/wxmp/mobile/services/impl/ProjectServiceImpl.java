package com.hj.wxmp.mobile.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.entity.Project;
import com.hj.wxmp.mobile.mapping.ProjectMapper;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.ProjectService;

@Component
public class ProjectServiceImpl implements ProjectService {

	@Resource
	private IKeyGen keyGen;
	@Resource
	private ProjectMapper dao;
	
	@Override
	public boolean insert(Project entity) throws Exception {
		return dao.insertSelective(entity)>0?true:false;
	}
	@Override
	public boolean update(Project entity) throws Exception {
		return dao.updateByPrimaryKeySelective(entity)>0?true:false;
	}
	@Override
	public List<Project> listEntity(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void delete(Project entity) throws Exception {
		dao.deleteByPrimaryKey(entity.getId());
	}
	@Override
	public Project saveEntity(Project entity) throws Exception {
		return null;
	}
	@Override
	public Project findById(String sys_uuid) throws Exception {
		return dao.selectByPrimaryKey(sys_uuid);
	}
	@Override
	public Project findByOpenid(String openid) throws Exception {
		return null;
	}
	@Override
	public int perfectUserInfo(Project sysUser) {
		return 0;
	}
	@Override
	public List<Project> findAll() {
		return null;
	}
	@Override
	public int selectCount() {
		return 0;
	}
	@Override
	public List<Map<String, Object>> selectByUserId(String userId) {
		return null;
	}
	@Override
	public List<Project> getProjectMessge(Map<String, Object> map) {
		return dao.getProjectMessge(map);
	}
	@Override
	public void deletes(String boxeditId) {
		dao.deletes(boxeditId);
	}


}
