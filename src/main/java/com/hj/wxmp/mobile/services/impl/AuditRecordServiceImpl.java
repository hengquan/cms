package com.hj.wxmp.mobile.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.entity.AuditRecord;
import com.hj.wxmp.mobile.mapping.AuditRecordMapper;
import com.hj.wxmp.mobile.services.AuditRecordService;
import com.hj.wxmp.mobile.services.IKeyGen;

@Component
public class AuditRecordServiceImpl implements AuditRecordService {

	@Resource
	private IKeyGen keyGen;
	@Resource
	private AuditRecordMapper dao;
	@Override
	public boolean insert(AuditRecord entity) throws Exception {
		return dao.insertSelective(entity)>0?true:false;
	}
	@Override
	public boolean update(AuditRecord entity) throws Exception {
		return dao.updateByPrimaryKeySelective(entity)>0?true:false;
	}
	@Override
	public List<AuditRecord> listEntity(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void delete(AuditRecord entity) throws Exception {
		dao.deleteByPrimaryKey(entity.getId());
	}
	@Override
	public AuditRecord saveEntity(AuditRecord entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AuditRecord findById(String sys_uuid) throws Exception {
		return dao.selectByPrimaryKey(sys_uuid);
	}
	@Override
	public AuditRecord findByOpenid(String openid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int perfectUserInfo(AuditRecord sysUser) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<AuditRecord> findAll() {
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
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<AuditRecord> getAuditRecordMessge(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void deletes(String boxeditId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public AuditRecord selectByProName(String pro) {
		return null;
	}
	@Override
	public List<Map<String, Object>> selectByRecordIdAndType(Map<String, Object> result) {
		return dao.selectByRecordIdAndType(result);
	}
	@Override
	public AuditRecord findByArId(Map<String,Object> data) {
		return dao.findByArId(data);
	}

}
