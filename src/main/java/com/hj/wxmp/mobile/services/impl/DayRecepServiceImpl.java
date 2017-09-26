package com.hj.wxmp.mobile.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.entity.DayRecep;
import com.hj.wxmp.mobile.mapping.DayRecepMapper;
import com.hj.wxmp.mobile.services.DayRecepService;
import com.hj.wxmp.mobile.services.IKeyGen;

@Component
public class DayRecepServiceImpl implements DayRecepService {

	@Resource
	private IKeyGen keyGen;
	@Resource
	private DayRecepMapper dao;
	
	@Override
	public boolean insert(DayRecep entity) throws Exception {
		return dao.insertSelective(entity)>0?true:false;
	}
	@Override
	public boolean update(DayRecep entity) throws Exception {
		return dao.updateByPrimaryKeySelective(entity)>0?true:false;
	}
	@Override
	public List<DayRecep> listEntity(Map<String, Object> map) throws Exception {
		return null;
	}
	@Override
	public void delete(DayRecep entity) throws Exception {
		dao.deleteByPrimaryKey(entity.getId());
	}
	@Override
	public DayRecep saveEntity(DayRecep entity) throws Exception {
		return null;
	}
	@Override
	public DayRecep findById(String sys_uuid) throws Exception {
		return dao.selectByPrimaryKey(sys_uuid);
	}
	@Override
	public void updateMsg(DayRecep dayRecep) {
		dao.updateMsg(dayRecep);
	}
	@Override
	public List<Map<String, Object>> selectByTimeAnd(Map<String, Object> result) {
		return dao.selectByTimeAnd(result);
	}
	
}
