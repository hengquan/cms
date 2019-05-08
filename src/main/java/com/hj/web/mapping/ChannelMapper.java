package com.hj.web.mapping;

import java.util.List;
import java.util.Map;

import com.hj.web.entity.Channel;

public interface ChannelMapper {
	int del(String id);

	int insert(Channel record);

	Channel get(String id);

	int update(Channel record);

	List<Channel> getProjectMessge(Map<String, Object> map);

	void deletes(String boxeditId);

	Integer getProjectMessgeCount(Map<String, Object> map);

	List<Channel> getAllData();

	List<Channel> getDataByType(Map<String, Object> param);

}