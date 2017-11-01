package com.hj.wxmp.mobile.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.entity.TabDictRef;
import com.hj.wxmp.mobile.mapping.TabDictRefMapper;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.TabDictRefService;

@Component
public class TabDictRefServiceImpl implements TabDictRefService {

	@Resource
	private IKeyGen keyGen;
	@Resource
	private TabDictRefMapper dao;
	@Override
	public boolean insert(TabDictRef entity) throws Exception {
		return dao.insertSelective(entity)>0?true:false;
	}
	@Override
	public boolean update(TabDictRef entity) throws Exception {
		return dao.updateByPrimaryKeySelective(entity)>0?true:false;
	}
	@Override
	public List<TabDictRef> listEntity(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void delete(TabDictRef entity) throws Exception {
		dao.deleteByPrimaryKey(entity.getId());
	}
	@Override
	public TabDictRef saveEntity(TabDictRef entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public TabDictRef findById(String sys_uuid) throws Exception {
		return dao.selectByPrimaryKey(sys_uuid);
	}
	@Override
	public TabDictRef findByOpenid(String openid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int perfectUserInfo(TabDictRef sysUser) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<TabDictRef> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int selectCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public TabDictRef selectCusIdAndTableName(Map<String, Object> map) {
		return dao.selectCusIdAndTableName(map);
	}
	@Override
	public void delete4TabColum(TabDictRef tdr) {
		dao.delete4TabColum(tdr);
	}
	@Override
	public List<TabDictRef> selectCognitiveCaseChannel() {
		return dao.selectCognitiveCaseChannel();
	}
	@Override
	public List<TabDictRef> selectConcern() {
		return dao.selectConcern();
	}
	@Override
	public List<TabDictRef> leadTime() {
		return dao.leadTime();
	}
	@Override
	public List<TabDictRef> selectResistPoint() {
		return dao.selectResistPoint();
	}
	

}
