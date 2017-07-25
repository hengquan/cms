package com.hj.wxmp.mobile.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.entity.AccessRecord01;
import com.hj.wxmp.mobile.mapping.AccessRecord01Mapper;
import com.hj.wxmp.mobile.services.AccessRecord01Service;
import com.hj.wxmp.mobile.services.IKeyGen;

@Component
public class AccessRecord01ServiceImpl implements AccessRecord01Service {

	@Resource
	private IKeyGen keyGen;
	@Resource
	private AccessRecord01Mapper dao;
	@Override
	public boolean insert(AccessRecord01 entity) throws Exception {
		return dao.insertSelective(entity)>0?true:false;
	}
	@Override
	public boolean update(AccessRecord01 entity) throws Exception {
		return dao.updateByPrimaryKeySelective(entity)>0?true:false;
	}
	@Override
	public List<AccessRecord01> listEntity(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void delete(AccessRecord01 entity) throws Exception {
		dao.deleteByPrimaryKey(entity.getId());
	}
	@Override
	public AccessRecord01 saveEntity(AccessRecord01 entity) throws Exception {
		return null;
	}
	@Override
	public AccessRecord01 findById(String sys_uuid) throws Exception {
		return dao.selectByPrimaryKey(sys_uuid);
	}
	@Override
	public AccessRecord01 findByOpenid(String openid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int perfectUserInfo(AccessRecord01 sysUser) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<AccessRecord01> findAll() {
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
	public List<Map<String, Object>> selectUserMsy(Map<String, Object> msg) {
		return dao.selectUserMsy(msg);
	}
	

}
