package com.hj.web.services;

import java.util.List;
import java.util.Map;

import com.hj.web.entity.Article;

public interface ArticleService {

	public boolean insert(Article entity) throws Exception;

	boolean update(Article entity) throws Exception;

	public void del(Article entity) throws Exception;

	public void del(String id);

	public Boolean save(Article entity) throws Exception;

	public Article get(String id) throws Exception;

	public Article get(Article entity);

	public List<Article> findAll();

	public List<Article> getDataList(Map<String, Object> map);

	public Integer getDataListCount(Map<String, Object> map);

	public void deletes(String ids);
}
