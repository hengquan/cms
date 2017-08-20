package com.hj.wxmp.mobile.services;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.AccessRecord01;

public interface AccessRecord01Service {
	/**
	 * 新增数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	public boolean insert(AccessRecord01 entity) throws Exception;
	
	/**
	 * 更新数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	boolean update(AccessRecord01 entity) throws Exception;
	
	/**
	 * 查找数据
	 * @return
	 * @throws Exception
	 */
	public List<AccessRecord01> listEntity(Map<String,Object> map) throws Exception;
	
	/**
	 * 删除数据
	 * @return
	 * @throws Exception
	 */
	public void delete(AccessRecord01 entity) throws Exception;
	
	/**
	 * 保存数据
	 * @param entity
	 * @throws Exception
	 */
	public AccessRecord01 saveEntity(AccessRecord01 entity) throws Exception;
	
	/**
	 * 通过ID查找
	 * @param id
	 * @return 
	 * @throws Exception
	 */
	public AccessRecord01 findById(String sys_uuid) throws Exception;
	
	/**
	 * 通过openid查找
	 * @author jun.hai
	 * @param id
	 * @return 
	 * @throws Exception
	 */
	public AccessRecord01 findByOpenid(String openid) throws Exception;
	
	/**
	 * 
	 * @author jun.hai 
	 * @Description: TODO(通过Id查询用户信息和扩展信息) 
	 * @param @param map
	 * @return String    返回类型 
	 * @date 2016年5月26日 上午9:47:44
	 * @throws
	 */
	public int perfectUserInfo(AccessRecord01 sysUser);
	
	
	/**
	 * 查询所有会员信息（后台）
	 * @author deng.hemei
	 * @description 后台会员信息管理
	 * @return List<SysUser>
	 * @updateDate 2016年7月4日
	 */
	public List<AccessRecord01> findAll();
	
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

	public List<Map<String, Object>> selectUserMsy(Map<String, Object> msg);

	public List<Map<String, Object>> getRecord01ListGuWen(Map<String, Object> result);

	public List<Map<String, Object>> getRecord01ListGuanLi(Map<String, Object> result);

	public List<Map<String, Object>> getRecord01ListFuZe(Map<String, Object> result);

	public List<Map<String, Object>> getRecord01ListAdmin(Map<String, Object> result);

	public Integer findByCustIdCount(Map<String,Object> data);


}
