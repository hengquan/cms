package com.hj.wxmp.mobile.mapping;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hj.wxmp.mobile.entity.KeywordResp;

public interface KeywordRespMapper{
	
	int getAllCount(Map<String,Object> map);
	
	List<KeywordResp> findAll(Map<String,Object> map);
	
	int insert(KeywordResp record);
	
	int update(KeywordResp record);

	void delete(KeywordResp menu);
	
	KeywordResp findById(@Param("id")Integer id);
	
	KeywordResp findByKeyword(@Param("keyword")String keyword);
	
}