package com.hj.wxmp.mobile.services;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.Material;
public interface MaterialServices {

	/**
	 * 新增数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	public boolean insert(Material entity) throws Exception;
	
	/**
	 * 更新数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	boolean update(Material entity) throws Exception;

	/**
	 * 查找数据
	 * @return
	 * @throws Exception
	 */
	public List<Material> listEntity(Map<String,Object> map) throws Exception;
	
	/**
	 * 删除数据
	 * @return
	 * @throws Exception
	 */
	public void delete(Material entity) throws Exception;
	
	/**
	 * 保存数据
	 * @param entity
	 * @throws Exception
	 */
	public void saveEntity(Material entity) throws Exception;
	
	/**
	 * 通过ID查找
	 * @param id
	 * @return 
	 * @throws Exception
	 */
	public Material findById(Integer id) throws Exception;

	/**
	 * 查找总数
	 * @param map
	 * @return
	 */
	public Integer findCount(Map<String, Object> map);
	
}
