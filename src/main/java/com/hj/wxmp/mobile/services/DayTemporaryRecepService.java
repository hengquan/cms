package com.hj.wxmp.mobile.services;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.DayTemporaryRecep;

public interface DayTemporaryRecepService {
	/**
	 * 新增数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	public boolean insert(DayTemporaryRecep entity) throws Exception;
	
	/**
	 * 更新数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	boolean update(DayTemporaryRecep entity) throws Exception;
	
	/**
	 * 查找数据
	 * @return
	 * @throws Exception
	 */
	public List<DayTemporaryRecep> listEntity(Map<String,Object> map) throws Exception;
	
	/**
	 * 删除数据
	 * @return
	 * @throws Exception
	 */
	public void delete(DayTemporaryRecep entity) throws Exception;
	
	/**
	 * 保存数据
	 * @param entity
	 * @throws Exception
	 */
	public DayTemporaryRecep saveEntity(DayTemporaryRecep entity) throws Exception;
	
	/**
	 * 通过ID查找
	 * @param id
	 * @return 
	 * @throws Exception
	 */
	public DayTemporaryRecep findById(String sys_uuid) throws Exception;

	public List<DayTemporaryRecep> selectByProj(DayTemporaryRecep dayTemporaryRecep);

	public List<DayTemporaryRecep> selectByUser(DayTemporaryRecep dayTemporaryRecep);

	public List<DayTemporaryRecep> selectByCust(DayTemporaryRecep dayTemporaryRecep);

	public List<DayTemporaryRecep> selectByProjAndUser(DayTemporaryRecep dayTemporaryRecep);

	public List<DayTemporaryRecep> selectByprojAndCust(DayTemporaryRecep dayTemporaryRecep);

	public List<DayTemporaryRecep> selectByprojAndUserAndCust(DayTemporaryRecep dayTemporaryRecep);

	public List<Map<String, Object>> selectByTimeAnd(Map<String, Object> result);

	public void deleteAll();
	


}
