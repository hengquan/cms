package com.hj.wxmp.mobile.services;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.KeywordResp;
import com.hj.wxmp.mobile.entity.KeywordRespEx;
public interface KeywordRespServices {

	/**
	 * 新增数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	public boolean insert(KeywordResp entity) throws Exception;
	
	/**
	 * 更新数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	boolean update(KeywordResp entity) throws Exception;

	/**
	 * 查找数据
	 * @return
	 * @throws Exception
	 */
	public List<KeywordResp> listEntity(Map<String,Object> map) throws Exception;
	
	/**
	 * 删除数据
	 * @return
	 * @throws Exception
	 */
	public void delete(KeywordResp entity) throws Exception;
	
	/**
	 * 保存数据
	 * @param entity
	 * @throws Exception
	 */
	public void saveEntity(KeywordResp entity) throws Exception;
	

	/**
	 * 根据Id查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public KeywordResp findById(Integer id) throws Exception;

	/**
	 * 
	* @Title: findByTitle
	* @Description: TODO(通过title返回对象)
	* @param  @param title
	* @param  @return    设定文件
	* @return KeywordResp    返回类型
	* @author jun.hai 
	* @date 2015年1月31日 下午3:35:55
	* @throws
	 */
	KeywordResp findByTitle(String title);

	List<KeywordResp> findBy(Map<String, Object> map) throws Exception;

	void deleteEx(KeywordRespEx entity) throws Exception;

	KeywordResp findByKeyword(String keyword);

	KeywordResp findByKeywordLike(String keyword);

	Integer findCount(Map<String, Object> map); 
}
