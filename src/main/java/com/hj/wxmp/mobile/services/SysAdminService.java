package com.hj.wxmp.mobile.services;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.SysAdmin;

public interface SysAdminService {
	/**
	 * 新增数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	public boolean insert(SysAdmin entity) throws Exception;
	
	/**
	 * 更新数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	boolean update(SysAdmin entity) throws Exception;
	
	boolean updateUserType() throws Exception;

	/**
	 * 查找数据
	 * @return
	 * @throws Exception
	 */
	public List<SysAdmin> listEntity(Map<String,Object> map) throws Exception;
	
	/**
	 * 删除数据
	 * @return
	 * @throws Exception
	 */
	public void delete(SysAdmin entity) throws Exception;
	
	/**
	 * 保存数据
	 * @param entity
	 * @throws Exception
	 */
	public void saveEntity(SysAdmin entity) throws Exception;
	
	/**
	 * 通过ID查找
	 * @param id
	 * @return 
	 * @throws Exception
	 */
	public SysAdmin findById(String sys_uuid) throws Exception;

	/**
	 * 查找总数
	 * @param map
	 * @return
	 */
	public Integer findCount(Map<String, Object> map);
	
	/**
	 * 判断是否登录 
	 * @param loginId
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public boolean isLogin(String loginId,String password) throws Exception;

	public SysAdmin selectLoginId(String mobile);

	public SysAdmin selectNamePassword(SysAdmin admin);

	public SysAdmin selectMobile(String mobile);
}
