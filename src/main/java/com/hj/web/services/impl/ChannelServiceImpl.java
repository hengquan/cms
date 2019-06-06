package com.hj.web.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.hj.web.entity.Channel;
import com.hj.web.mapping.ChannelMapper;
import com.hj.web.services.ChannelService;
import com.hj.web.services.IKeyGen;

@Component
public class ChannelServiceImpl implements ChannelService {

	@Resource
	private IKeyGen keyGen;
	@Resource
	private ChannelMapper dao;

	@Override
	public boolean insert(Channel entity) throws Exception {
		entity.setId(keyGen.getUUIDKey());
		return dao.insert(entity) > 0 ? true : false;
	}

	@Override
	public boolean update(Channel entity) throws Exception {
		return dao.update(entity) > 0 ? true : false;
	}

	@Override
	public void delete(Channel entity) throws Exception {
		dao.del(entity.getId());
	}

	@Override
	public Channel saveEntity(Channel entity) throws Exception {
		return null;
	}

	@Override
	public Channel findById(String sys_uuid) throws Exception {
		return dao.get(sys_uuid);
	}

	@Override
	public Channel findByOpenid(String openid) throws Exception {
		return null;
	}

	@Override
	public int perfectUserInfo(Channel sysUser) {
		return 0;
	}

	@Override
	public int selectCount() {
		return 0;
	}

	@Override
	public List<Map<String, Object>> selectByUserId(String userId) {
		return null;
	}

	@Override
	public List<Channel> getProjectMessge(Map<String, Object> map) {
		return dao.getProjectMessge(map);
	}

	@Override
	public void deletes(String boxeditId) {
		dao.deletes(boxeditId);
	}

	@Override
	public Integer getProjectMessgeCount(Map<String, Object> map) {
		return dao.getProjectMessgeCount(map);
	}

	@Override
	public List<Channel> listEntity(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Channel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Channel selectByProName(String pro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Channel> getAllData() {
		return dao.getAllData();
	}

	@Override
	public Channel get(String id) {
		return dao.get(id);
	}

	@Override
	public List<Channel> getDataByType(Map<String, Object> param) {
		return dao.getDataByType(param);
	}

	@Override
	public Boolean save(Channel project) {
		Boolean result = false;
		String id = project.getId();
		if (StringUtils.isNotEmpty(id)) {
			result = dao.update(project) > 0 ? true : false;
		} else {
			project.setId(keyGen.getUUIDKey());
			result = dao.insert(project) > 0 ? true : false;
		}
		return result;
	}

	@Override
	public List<Channel> getDataByRoleId(Map<String,Object> map) {
		return dao.getDataByRoleId(map);
	}

	@Override
	public List<Channel> selectDataByRoleId(Map<String, Object> param) {
		return dao.selectDataByRoleId(param);
	}

	@Override
	public List<Channel> getByIds(String channelIds) {
		return dao.getByIds(channelIds);
	}
}
