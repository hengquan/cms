package com.hj.wxmp.mobile.services;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.TblArticle;

public interface TblArticleService {
	/**
	 * 新增数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	public boolean insert(TblArticle entity) throws Exception;
	
	/**
	 * 更新数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	boolean update(TblArticle entity) throws Exception;
	
	/**
	 * 删除数据
	 * @return
	 * @throws Exception
	 */
	public void delete(TblArticle entity) throws Exception;
	
	/**
	 * 保存数据
	 * @param entity
	 * @throws Exception
	 */
	public TblArticle saveEntity(TblArticle entity) throws Exception;
	
	/**
	 * 通过ID查找
	 * @param id
	 * @return 
	 * @throws Exception
	 */
	public TblArticle findById(String sys_uuid) throws Exception;

	/**
	 * 查找总数
	 * @param map
	 * @return
	 */
	public Integer findCount(Map<String, Object> map);
	
	
	 
	public List<TblArticle> findAll(Map<String,Object> m);

	public String selectMsg();
	
}
