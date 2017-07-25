package com.hj.wxmp.mobile.mapping;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.FollowResp;

public interface FollowRespMapper{
	
	int getAllCount(Map<String,Object> map);
	
	List<FollowResp> findAll(Map<String,Object> map);
	
	int insert(FollowResp record);
	
	int update(FollowResp record);

	void delete(FollowResp menu);

	
}