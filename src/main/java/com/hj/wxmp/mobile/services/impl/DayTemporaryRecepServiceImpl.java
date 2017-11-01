package com.hj.wxmp.mobile.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.entity.DayTemporaryRecep;
import com.hj.wxmp.mobile.mapping.DayTemporaryRecepMapper;
import com.hj.wxmp.mobile.services.DayTemporaryRecepService;
import com.hj.wxmp.mobile.services.IKeyGen;

@Component
public class DayTemporaryRecepServiceImpl implements DayTemporaryRecepService {

	@Resource
	private IKeyGen keyGen;
	@Resource
	private DayTemporaryRecepMapper dao;
	@Override
	public boolean insert(DayTemporaryRecep entity) throws Exception {
		return dao.insertSelective(entity)>0?true:false;
	}
	@Override
	public boolean update(DayTemporaryRecep entity) throws Exception {
		return dao.updateByPrimaryKeySelective(entity)>0?true:false;
	}
	@Override
	public List<DayTemporaryRecep> listEntity(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void delete(DayTemporaryRecep entity) throws Exception {
		dao.deleteByPrimaryKey(entity.getId());
	}
	@Override
	public DayTemporaryRecep saveEntity(DayTemporaryRecep entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DayTemporaryRecep findById(String sys_uuid) throws Exception {
		return dao.selectByPrimaryKey(sys_uuid);
	}
	@Override
	public List<DayTemporaryRecep> selectByProj(DayTemporaryRecep dayTemporaryRecep) {
		return dao.selectByProj(dayTemporaryRecep);
	}
	@Override
	public List<DayTemporaryRecep> selectByUser(DayTemporaryRecep dayTemporaryRecep) {
		return dao.selectByUser(dayTemporaryRecep);
	}
	@Override
	public List<DayTemporaryRecep> selectByCust(DayTemporaryRecep dayTemporaryRecep) {
		return dao.selectByCust(dayTemporaryRecep);
	}
	@Override
	public List<DayTemporaryRecep> selectByProjAndUser(DayTemporaryRecep dayTemporaryRecep) {
		return dao.selectByProjAndUser(dayTemporaryRecep);
	}
	@Override
	public List<DayTemporaryRecep> selectByprojAndCust(DayTemporaryRecep dayTemporaryRecep) {
		return dao.selectByprojAndCust(dayTemporaryRecep);
	}
	@Override
	public List<DayTemporaryRecep> selectByprojAndUserAndCust(DayTemporaryRecep dayTemporaryRecep) {
		return dao.selectByprojAndUserAndCust(dayTemporaryRecep);
	}

}
