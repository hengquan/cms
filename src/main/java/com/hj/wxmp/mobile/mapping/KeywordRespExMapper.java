package com.hj.wxmp.mobile.mapping;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hj.wxmp.mobile.entity.KeywordRespEx;

public interface KeywordRespExMapper{
	
	int getAllCount(Map<String,Object> map);
	
	List<KeywordRespEx> findAll(Map<String,Object> map);
	
	int insert(KeywordRespEx record);
	
	int update(KeywordRespEx record);

	void delete(KeywordRespEx menu);

	KeywordRespEx findById(@Param("id")Integer id,@Param("parent_id")Integer parent_id);
}