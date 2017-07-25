package com.hj.wxmp.mobile.services;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.FollowResp;
import com.hj.wxmp.mobile.entity.FollowRespEx;
public interface FollowRespServices {

	/**
	 * 新增数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	public boolean insert(FollowResp entity) throws Exception;
	
	/**
	 * 更新数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	boolean update(FollowResp entity) throws Exception;

	/**
	 * 查找数据
	 * @return
	 * @throws Exception
	 */
	public List<FollowResp> listEntity(Map<String,Object> map) throws Exception;
	
	/**
	 * 删除数据
	 * @return
	 * @throws Exception
	 */
	public void delete(FollowResp entity) throws Exception;
	
	/**
	 * 保存数据
	 * @param entity
	 * @throws Exception
	 */
	public void saveEntity(FollowResp entity) throws Exception;

	/**
	 * 
	* @Title: deleteEx 
	* @Description: TODO(通过ID删除扩展表) 
	* @param  @param entity
	* @param  @throws Exception    设定文件 
	* @return void    返回类型 
	* @author jun.hai  
	* @date 2015年2月7日 上午9:04:31 
	* @throws
	 */
	public void deleteEx(FollowRespEx entity) throws Exception;
	
}
