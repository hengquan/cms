package com.hj.wxmp.mobile.mapping;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.TblMenu;

public interface TblMenuMapper{
	
	int getAllCount(Map<String,Object> map);
	
	List<TblMenu> findAll(Map<String,Object> map);
	
	int insert(TblMenu record);
	
	int update(TblMenu record);

	void delete(TblMenu menu);

	TblMenu getEntity(int id);
}