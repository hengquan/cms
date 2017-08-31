package com.hj.wxmp.mobile.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.entity.AccessRecord03;
import com.hj.wxmp.mobile.mapping.AccessRecord03Mapper;
import com.hj.wxmp.mobile.services.AccessRecord03Service;
import com.hj.wxmp.mobile.services.IKeyGen;

@Component
public class AccessRecord03ServiceImpl implements AccessRecord03Service {

	@Resource
	private IKeyGen keyGen;
	@Resource
	private AccessRecord03Mapper dao;
	@Override
	public boolean insert(AccessRecord03 entity) throws Exception {
		return dao.insertSelective(entity)>0?true:false;
	}
	@Override
	public boolean update(AccessRecord03 entity) throws Exception {
		return dao.updateByPrimaryKeySelective(entity)>0?true:false;
	}
	@Override
	public List<AccessRecord03> listEntity(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void delete(AccessRecord03 entity) throws Exception {
		dao.deleteByPrimaryKey(entity.getId());
	}
	@Override
	public AccessRecord03 saveEntity(AccessRecord03 entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AccessRecord03 findById(String sys_uuid) throws Exception {
		return dao.selectByPrimaryKey(sys_uuid);
	}
	@Override
	public AccessRecord03 findByOpenid(String openid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int perfectUserInfo(AccessRecord03 sysUser) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<AccessRecord03> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int selectCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<Map<String, Object>> selectMessage(Map<String, Object> map) {
		return dao.selectMessage(map);
	}
	@Override
	public void dels(String boxeditId) {
		dao.dels(boxeditId);
	}
	@Override
	public List<Map<String, Object>> getRecord03List(Map<String, Object> result) {
		return dao.getRecord03List(result);
	}
	@Override
	public Integer findByCustIdCount(Map<String,Object> data) {
		return dao.findByCustIdCount(data);
	}
	@Override
	public List<AccessRecord03> selectByUserId(Map<String, Object> datamsg) {
		return dao.selectByUserId(datamsg);
	}
	@Override
	public Integer selectMessageCount(Map<String, Object> map) {
		return dao.selectMessageCount(map);
	}
	@Override
	public List<Map<String, Object>> getRecord03ListGuWen(Map<String, Object> result) {
		return dao.getRecord03ListGuWen(result);
	}
	@Override
	public List<Map<String, Object>> getRecord03ListGuanLi(Map<String, Object> result) {
		return dao.getRecord03ListGuanLi(result);
	}
	@Override
	public List<Map<String, Object>> getRecord03ListFuZe(Map<String, Object> result) {
		return dao.getRecord03ListFuZe(result);
	}
	@Override
	public List<Map<String, Object>> getRecord03ListAdmin(Map<String, Object> result) {
		return dao.getRecord03ListAdmin(result);
	}

}
