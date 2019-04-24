package com.hj.web.services;

import java.util.List;
import java.util.Map;

import com.hj.web.entity.TblMenu;
public interface TblMenuServices {

	/**
	 * 新增数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	public boolean insert(TblMenu entity) throws Exception;
	
	/**
	 * 更新数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	boolean update(TblMenu entity) throws Exception;

	/**
	 * 查找数据
	 * @return
	 * @throws Exception
	 */
	public List<TblMenu> listEntity(Map<String,Object> map) throws Exception;
	
	/**
	 * 删除数据
	 * @return
	 * @throws Exception
	 */
	public void delete(TblMenu menu) throws Exception;

	/**
	 * 
	* @Title: insertOrUpdate
	* @Description: TODO(id为0时insert，否则update)
	* @param  @param entity
	* @param  @return
	* @param  @throws Exception    设定文件
	* @return boolean    返回类型
	* @author jun.hai 
	* @date 2015年1月31日 上午10:33:46
	* @throws
	 */
	boolean insertOrUpdate(TblMenu entity) throws Exception;

	boolean isAddFirstLevelMenu();

	boolean isAddSecondLevelMenu(String parentId);

	TblMenu getEntity(int id);
	
}
