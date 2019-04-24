package com.hj.web.services;

import java.util.List;
import java.util.Map;

import com.hj.web.entity.TabDictRef;

public interface TabDictRefService {
	/**
	 * 新增数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	public boolean insert(TabDictRef entity) throws Exception;
	
	/**
	 * 更新数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	boolean update(TabDictRef entity) throws Exception;
	
	/**
	 * 查找数据
	 * @return
	 * @throws Exception
	 */
	public List<TabDictRef> listEntity(Map<String,Object> map) throws Exception;
	
	/**
	 * 删除数据
	 * @return
	 * @throws Exception
	 */
	public void delete(TabDictRef entity) throws Exception;
	
	/**
	 * 保存数据
	 * @param entity
	 * @throws Exception
	 */
	public TabDictRef saveEntity(TabDictRef entity) throws Exception;
	
	/**
	 * 通过ID查找
	 * @param id
	 * @return 
	 * @throws Exception
	 */
	public TabDictRef findById(String sys_uuid) throws Exception;
	
	/**
	 * 通过openid查找
	 * @author jun.hai
	 * @param id
	 * @return 
	 * @throws Exception
	 */
	public TabDictRef findByOpenid(String openid) throws Exception;
	
	/**
	 * 
	 * @author jun.hai 
	 * @Description: TODO(通过Id查询用户信息和扩展信息) 
	 * @param @param map
	 * @return String    返回类型 
	 * @date 2016年5月26日 上午9:47:44
	 * @throws
	 */
	public int perfectUserInfo(TabDictRef sysUser);
	
	
	/**
	 * 查询所有会员信息（后台）
	 * @author zhang.hengquan
	 * @description 后台会员信息管理
	 * @return List<SysUser>
	 * @updateDate 2016年7月4日
	 */
	public List<TabDictRef> findAll();
	
	/**
	 * 查询总记录数
	 * @author zhang.hengquan
	 * @description 
	 * @return 
	 * @updateDate 2016年7月11日
	 */
	public int selectCount();

	public TabDictRef selectCusIdAndTableName(Map<String, Object> map);

	public void delete4TabColum(TabDictRef tdr);

	public List<TabDictRef> selectCognitiveCaseChannel();

	public List<TabDictRef> selectConcern();

	public List<TabDictRef> leadTime();

	public List<TabDictRef> selectResistPoint();

}
