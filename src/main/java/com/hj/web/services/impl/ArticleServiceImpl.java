package com.hj.web.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hj.web.entity.Article;
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
		return dao.insert(entity) > 0 ? true : false;
	}

	@Override
	public boolean update(Article entity) throws Exception {
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
	public Boolean save(Article entity) throws Exception {
		return null;
	}

	@Override
	public Article get(String id) throws Exception {
		return dao.get(id);
	}

	@Override
	public Article get(Article entity) {
		return dao.get(entity.getId());
	}

	@Override
	public List<Article> findAll() {
		return null;
	}

	@Override
	public List<Article> getDataList(Map<String, Object> map) {
		return null;
	}

	@Override
	public Integer getDataListCount(Map<String, Object> map) {
		return null;
	}

	@Override
	public void deletes(String ids) {
		dao.deletes(ids);
	}

}
