package com.hj.wxmp.mobile.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.entity.ImportMapUserCust;
import com.hj.wxmp.mobile.mapping.ImportMapUserCustMapper;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.ImportMapUserCustService;

@Component
public class ImportMapUserCustServiceImpl implements ImportMapUserCustService {

	@Resource
	private IKeyGen keyGen;
	@Resource
	private ImportMapUserCustMapper dao;
	
	@Override
	public boolean insert(ImportMapUserCust entity) throws Exception {
		return dao.insert(entity)>0?true:false;
	}
	@Override
	public boolean update(ImportMapUserCust entity) throws Exception {
		return dao.updateByPrimaryKey(entity)>0?true:false;
	}
	@Override
	public List<ImportMapUserCust> listEntity(Map<String, Object> map) throws Exception {
		return null;
	}
	@Override
	public void delete(ImportMapUserCust entity) throws Exception {
		dao.deleteByPrimaryKey(entity.getId());
	}
	@Override
	public ImportMapUserCust saveEntity(ImportMapUserCust entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ImportMapUserCust findById(String sys_uuid) throws Exception {
		return dao.selectByPrimaryKey(sys_uuid);
	}
	@Override
	public ImportMapUserCust findByOpenid(String openid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int perfectUserInfo(ImportMapUserCust sysUser) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<ImportMapUserCust> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int selectCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<ImportMapUserCust> selectByUserName(String realname) {
		return dao.selectByUserName(realname);
	}

}
