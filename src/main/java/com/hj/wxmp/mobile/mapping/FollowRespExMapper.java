package com.hj.wxmp.mobile.mapping;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.FollowRespEx;

public interface FollowRespExMapper{
	
	int getAllCount(Map<String,Object> map);
	
	List<FollowRespEx> findAll(Map<String,Object> map);
	
	int insert(FollowRespEx record);
	
	int update(FollowRespEx record);

	void delete(FollowRespEx menu);

	
}