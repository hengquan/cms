package com.hj.wxmp.mobile.services;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.Project;
import com.hj.wxmp.mobile.entity.UserInfo;

public interface ProjectService {
	/**
	 * 新增数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	public boolean insert(Project entity) throws Exception;
	
	/**
	 * 更新数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	boolean update(Project entity) throws Exception;
	
	/**
	 * 查找数据
	 * @return
	 * @throws Exception
	 */
	public List<Project> listEntity(Map<String,Object> map) throws Exception;
	
	/**
	 * 删除数据
	 * @return
	 * @throws Exception
	 */
	public void delete(Project entity) throws Exception;
	
	/**
	 * 保存数据
	 * @param entity
	 * @throws Exception
	 */
	public Project saveEntity(Project entity) throws Exception;
	
	/**
	 * 通过ID查找
	 * @param id
	 * @return 
	 * @throws Exception
	 */
	public Project findById(String sys_uuid) throws Exception;
	
	/**
	 * 通过openid查找
	 * @author jun.hai
	 * @param id
	 * @return 
	 * @throws Exception
	 */
	public Project findByOpenid(String openid) throws Exception;
	
	/**
	 * 
	 * @author jun.hai 
	 * @Description: TODO(通过Id查询用户信息和扩展信息) 
	 * @param @param map
	 * @return String    返回类型 
	 * @date 2016年5月26日 上午9:47:44
	 * @throws
	 */
	public int perfectUserInfo(Project sysUser);
	
	
	/**
	 * 查询所有会员信息（后台）
	 * @author deng.hemei
	 * @description 后台会员信息管理
	 * @return List<SysUser>
	 * @updateDate 2016年7月4日
	 */
	public List<Project> findAll();
	
	/**
	 * 查询总记录数
	 * @author deng.hemei
	 * @description 
	 * @return 
	 * @updateDate 2016年7月11日
	 */
	public int selectCount();

	public List<Map<String, Object>> selectByUserId(String userId);

	public List<Project> getProjectMessge(Map<String, Object> map);

	public void deletes(String boxeditId);

	public Project selectByProName(String pro);

	public Integer getProjectMessgeCount(Map<String, Object> map);

	public List<Map<String, Object>> selectAll();

}
