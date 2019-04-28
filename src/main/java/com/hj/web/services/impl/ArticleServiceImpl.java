package com.hj.web.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.hj.web.entity.Article;
import com.hj.web.entity.UserInfo;
import com.hj.web.mapping.ArticleMapper;
import com.hj.web.services.ArticleService;
import com.hj.web.services.IKeyGen;

@Component
public class ArticleServiceImpl implements ArticleService {

	@Resource
	private ArticleMapper dao;
	@Resource
	private IKeyGen keyGen;

	@Override
	public boolean insert(Article entity) throws Exception {
		entity.setId(keyGen.getUUIDKey());
		entity.setUpdateTime(new Date());
		return dao.insert(entity) > 0 ? true : false;
	}

	@Override
	public boolean update(Article entity) throws Exception {
		entity.setUpdateTime(new Date());
		return dao.update(entity) > 0 ? true : false;
	}

	@Override
	public void del(Article entity) throws Exception {
		dao.del(entity.getId());
	}

	@Override
	public void del(String id) {
		dao.del(id);
	}

	@Override
	public Boolean save(Article entity, UserInfo userInfo) throws Exception {
		Boolean result = false;
		String id = entity.getId();
		if (userInfo != null) {
			String userId = userInfo.getId();
			String userName = userInfo.getRealname();
			if (StringUtils.isNotEmpty(userId))
				entity.setUserId(userId);
			if (StringUtils.isNotEmpty(userName))
				entity.setUserName(userName);
		}
		if (StringUtils.isNotEmpty(id)) {
			entity.setUpdateTime(new Date());
			result = dao.update(entity) > 0 ? true : false;
		} else {
			entity.setId(keyGen.getUUIDKey());
			entity.setUpdateTime(new Date());
			result = dao.insert(entity) > 0 ? true : false;
		}
		return result;
	}

	@Override
	public Article get(String id) {
		return dao.get(id);
	}

	@Override
	public Article get(Article entity) {
		return dao.get(entity.getId());
	}

	@Override
	public List<Article> findAll() {
		return dao.findAll();
	}

	@Override
	public List<Article> getDataList(Map<String, Object> map) {
		return dao.getDataList(map);
	}

	@Override
	public Integer getDataListCount(Map<String, Object> map) {
		return dao.getDataListCount(map);
	}

	@Override
	public void deletes(String ids) {
		dao.deletes(ids);
	}

}
