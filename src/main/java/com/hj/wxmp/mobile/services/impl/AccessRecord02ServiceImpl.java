package com.hj.wxmp.mobile.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.entity.AccessRecord02;
import com.hj.wxmp.mobile.mapping.AccessRecord02Mapper;
import com.hj.wxmp.mobile.services.AccessRecord02Service;
import com.hj.wxmp.mobile.services.IKeyGen;

@Component
public class AccessRecord02ServiceImpl implements AccessRecord02Service {

	@Resource
	private IKeyGen keyGen;
	@Resource
	private AccessRecord02Mapper dao;
	@Override
	public boolean insert(AccessRecord02 entity) throws Exception {
		return dao.insertSelective(entity)>0?true:false;
	}
	@Override
	public boolean update(AccessRecord02 entity) throws Exception {
		return dao.updateByPrimaryKeySelective(entity)>0?true:false;
	}
	@Override
	public List<AccessRecord02> listEntity(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void delete(AccessRecord02 entity) throws Exception {
		dao.deleteByPrimaryKey(entity.getId());
	}
	@Override
	public AccessRecord02 saveEntity(AccessRecord02 entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AccessRecord02 findById(String sys_uuid) throws Exception {
		return dao.selectByPrimaryKey(sys_uuid);
	}
	@Override
	public AccessRecord02 findByOpenid(String openid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int perfectUserInfo(AccessRecord02 sysUser) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<AccessRecord02> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int selectCount() {
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
	public List<Map<String, Object>> getRecord02List(Map<String, Object> result) {
		return dao.getRecord02List(result);
	}
	@Override
	public Integer findByCustIdCount(Map<String,Object> data) {
		return dao.findByCustIdCount(data);
	}
	@Override
	public List<AccessRecord02> selectByUserId(Map<String, Object> datamsg) {
		return dao.selectByUserId(datamsg);
	}
	@Override
	public Integer selectMessageCount(Map<String, Object> map) {
		return dao.selectMessageCount(map);
	}

}
