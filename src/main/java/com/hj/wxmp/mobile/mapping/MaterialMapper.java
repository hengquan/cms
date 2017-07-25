package com.hj.wxmp.mobile.mapping;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hj.wxmp.mobile.entity.Material;

public interface MaterialMapper{
	
	int getAllCount(Map<String,Object> map);
	
	List<Material> findAll(Map<String,Object> map);
	
	int insert(Material record);
	
	int update(Material record);

	void delete(Material record);

	Material findById(@Param("id") Integer id);
	
}