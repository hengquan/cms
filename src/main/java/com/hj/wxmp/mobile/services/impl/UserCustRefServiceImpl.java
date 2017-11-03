package com.hj.wxmp.mobile.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.entity.UserCustRef;
import com.hj.wxmp.mobile.mapping.UserCustRefMapper;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.UserCustRefService;

@Component
public class UserCustRefServiceImpl implements UserCustRefService {
	
	@Resource
	IKeyGen key;
	@Resource
	private UserCustRefMapper dao;
	@Override
	public boolean insert(UserCustRef entity) throws Exception {
		return dao.insertSelective(entity)>0?true:false;
	}
	@Override
	public boolean update(UserCustRef entity) throws Exception {
		return dao.updateByPrimaryKey(entity)>0?true:false;
	}
	@Override
	public List<UserCustRef> listEntity(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void delete(UserCustRef entity) throws Exception {
		dao.deleteByPrimaryKey(entity.getId());
	}
	@Override
	public UserCustRef saveEntity(UserCustRef entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public UserCustRef findById(String sys_uuid) throws Exception {
		return dao.selectByPrimaryKey(sys_uuid);
	}
	@Override
	public UserCustRef findByOpenid(String openid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int perfectUserInfo(UserCustRef sysUser) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<UserCustRef> findAll() {
		return dao.findAll();
	}
	@Override
	public int selectCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<Map<String, Object>> selectByUserId(String userId) {
		return dao.selectByUserId(userId);
	}
	@Override
	public List<Map<String, Object>> selectByProjectId(String proId) {
		return dao.selectByProjectId(proId);
	}
	@Override
	public List<Map<String, Object>> findByProjectId(String proId) {
		return dao.findByProjectId(proId);
	}
	@Override
	public List<Map<String, Object>> selectByUserMessge(Map<String, Object> map) {
		return dao.selectByUserMessge(map);
	}
	@Override
	public Integer selectByUserMessgeCount(Map<String, Object> map) {
		return dao.selectByUserMessgeCount(map);
	}
	@Override
	public Integer updateByProjIdAndCustId(Map<String, Object> parmeterMap) {
		return dao.updateByProjIdAndCustId(parmeterMap);
	}
	@Override
	public List<Map<String, Object>> downloadExcel(Map<String, Object> map) {
		return dao.downloadExcel(map);
	}
	@Override
	public List<Map<String, Object>> selectZongHe(Map<String, Object> map) {
		return dao.selectZongHe(map);
	}
	@Override
	public Integer selectZongHeCount(Map<String, Object> map) {
		return dao.selectZongHeCount(map);
	}
	@Override
	public List<Map<String, Object>> selectBySpecialUserMessge(Map<String, Object> map) {
		return dao.selectBySpecialUserMessge(map);
	}
	@Override
	public Integer selectBySpecialUserMessgeCount(Map<String, Object> map) {
		return dao.selectBySpecialUserMessgeCount(map);
	}
	@Override
	public UserCustRef selectByData(Map<String, Object> data) {
		return dao.selectByData(data);
	}
	@Override
	public List<UserCustRef> selectByProjIdAndCustId(Map<String, Object> parmeterMap) {
		return dao.selectByProjIdAndCustId(parmeterMap);
	}

	

}
