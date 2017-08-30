package com.hj.wxmp.mobile.services;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.AccessRecord02;

public interface AccessRecord02Service {
	/**
	 * 新增数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	public boolean insert(AccessRecord02 entity) throws Exception;
	
	/**
	 * 更新数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	boolean update(AccessRecord02 entity) throws Exception;
	
	/**
	 * 查找数据
	 * @return
	 * @throws Exception
	 */
	public List<AccessRecord02> listEntity(Map<String,Object> map) throws Exception;
	
	/**
	 * 删除数据
	 * @return
	 * @throws Exception
	 */
	public void delete(AccessRecord02 entity) throws Exception;
	
	/**
	 * 保存数据
	 * @param entity
	 * @throws Exception
	 */
	public AccessRecord02 saveEntity(AccessRecord02 entity) throws Exception;
	
	/**
	 * 通过ID查找
	 * @param id
	 * @return 
	 * @throws Exception
	 */
	public AccessRecord02 findById(String sys_uuid) throws Exception;
	
	/**
	 * 通过openid查找
	 * @author jun.hai
	 * @param id
	 * @return 
	 * @throws Exception
	 */
	public AccessRecord02 findByOpenid(String openid) throws Exception;
	
	/**
	 * 
	 * @author jun.hai 
	 * @Description: TODO(通过Id查询用户信息和扩展信息) 
	 * @param @param map
	 * @return String    返回类型 
	 * @date 2016年5月26日 上午9:47:44
	 * @throws
	 */
	public int perfectUserInfo(AccessRecord02 sysUser);
	
	
	/**
	 * 查询所有会员信息（后台）
	 * @author deng.hemei
	 * @description 后台会员信息管理
	 * @return List<SysUser>
	 * @updateDate 2016年7月4日
	 */
	public List<AccessRecord02> findAll();
	
	/**
	 * 查询总记录数
	 * @author deng.hemei
	 * @description 
	 * @return 
	 * @updateDate 2016年7月11日
	 */
	public int selectCount();

	public List<Map<String, Object>> selectMessage(Map<String, Object> map);

	public void dels(String boxeditId);

	public List<Map<String, Object>> getRecord02List(Map<String, Object> result);

	public Integer findByCustIdCount(Map<String,Object> data);

	public List<AccessRecord02> selectByUserId(Map<String, Object> datamsg);

	public Integer selectMessageCount(Map<String, Object> map);

	public List<Map<String, Object>> getRecord02ListGuWen(Map<String, Object> result);

	public List<Map<String, Object>> getRecord02ListGuanLi(Map<String, Object> result);

	public List<Map<String, Object>> getRecord02ListFuZe(Map<String, Object> result);

	public List<Map<String, Object>> getRecord02ListAdmin(Map<String, Object> result);


}
