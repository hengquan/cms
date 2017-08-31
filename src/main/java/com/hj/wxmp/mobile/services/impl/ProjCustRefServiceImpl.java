package com.hj.wxmp.mobile.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.entity.ProjCustRef;
import com.hj.wxmp.mobile.mapping.ProjCustRefMapper;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.ProjCustRefService;

@Component
public class ProjCustRefServiceImpl implements ProjCustRefService {

	@Resource
	private IKeyGen keyGen;
	@Resource
	private ProjCustRefMapper dao;
	@Override
	public boolean insert(ProjCustRef entity) throws Exception {
		return dao.insertSelective(entity)>0?true:false;
	}
	@Override
	public boolean update(ProjCustRef entity) throws Exception {
		return dao.updateByPrimaryKeySelective(entity)>0?true:false;
	}
	@Override
	public List<ProjCustRef> listEntity(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void delete(ProjCustRef entity) throws Exception {
		dao.deleteByPrimaryKey(entity.getId());
	}
	@Override
	public ProjCustRef saveEntity(ProjCustRef entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ProjCustRef findById(String sys_uuid) throws Exception {
		return dao.selectByPrimaryKey(sys_uuid);
	}
	@Override
	public ProjCustRef findByOpenid(String openid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int perfectUserInfo(ProjCustRef sysUser) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<ProjCustRef> findAll() {
		// TODO Auto-generated method stub
		return null;
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
	public ProjCustRef selectByCusIdAndProjId(Map<String, Object> result) {
		return dao.selectByCusIdAndProjId(result);
	}
	@Override
	public List<Map<String, Object>> selectByProjIdAndCusId(Map<String, Object> result) {
		return dao.selectByProjIdAndCusId(result);
	}


}
