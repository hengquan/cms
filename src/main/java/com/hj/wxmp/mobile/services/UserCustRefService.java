package com.hj.wxmp.mobile.services;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.UserCustRef;

public interface UserCustRefService {
	/**
	 * 新增数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	public boolean insert(UserCustRef entity) throws Exception;
	
	/**
	 * 更新数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	boolean update(UserCustRef entity) throws Exception;
	
	/**
	 * 查找数据
	 * @return
	 * @throws Exception
	 */
	public List<UserCustRef> listEntity(Map<String,Object> map) throws Exception;
	
	/**
	 * 删除数据
	 * @return
	 * @throws Exception
	 */
	public void delete(UserCustRef entity) throws Exception;
	
	/**
	 * 保存数据
	 * @param entity
	 * @throws Exception
	 */
	public UserCustRef saveEntity(UserCustRef entity) throws Exception;
	
	/**
	 * 通过ID查找
	 * @param id
	 * @return 
	 * @throws Exception
	 */
	public UserCustRef findById(String sys_uuid) throws Exception;
	
	/**
	 * 通过openid查找
	 * @author jun.hai
	 * @param id
	 * @return 
	 * @throws Exception
	 */
	public UserCustRef findByOpenid(String openid) throws Exception;
	
	/**
	 * 
	 * @author jun.hai 
	 * @Description: TODO(通过Id查询用户信息和扩展信息) 
	 * @param @param map
	 * @return String    返回类型 
	 * @date 2016年5月26日 上午9:47:44
	 * @throws
	 */
	public int perfectUserInfo(UserCustRef sysUser);
	
	
	/**
	 * 查询所有会员信息（后台）
	 * @author deng.hemei
	 * @description 后台会员信息管理
	 * @return List<SysUser>
	 * @updateDate 2016年7月4日
	 */
	public List<UserCustRef> findAll();
	
	/**
	 * 查询总记录数
	 * @author deng.hemei
	 * @description 
	 * @return 
	 * @updateDate 2016年7月11日
	 */
	public int selectCount();

	public List<Map<String, Object>> selectByUserId(String userId);

	public List<Map<String, Object>> selectByProjectId(String proId);

	public List<Map<String, Object>> findByProjectId(String proId);

	public List<Map<String, Object>> selectByUserMessge(Map<String, Object> map);

	public Integer selectByUserMessgeCount(Map<String, Object> map);

	public void updateByProjIdAndCustId(Map<String, Object> parmeterMap);

}
