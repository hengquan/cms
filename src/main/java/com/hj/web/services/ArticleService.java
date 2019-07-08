package com.hj.web.services;

import java.util.List;
import java.util.Map;

import com.hj.web.entity.Article;
import com.hj.web.entity.UserInfo;

public interface ArticleService {

	public boolean insert(Article entity) throws Exception;

	boolean update(Article entity) throws Exception;

	public void del(Article entity) throws Exception;

	public void del(String id);

	public Boolean save(Article entity, UserInfo userInfo) throws Exception;

	public Article get(String id);

	public Article get(Article entity);

	public List<Article> findAll();

	public List<Article> getDataList(Map<String, Object> map);

	public Integer getDataListCount(Map<String, Object> map);

	public void deletes(String ids);

	public List<Article> getDataListByRelevancyId(String id);

	public List<Article> getArticleParentDataList(Article article);

	public List<Article> getParentDataList(String id);

	public List<Article> getArticlePicUrlList(Map<String, Object> param);

	public List<Article> getDataListByChannelIdAndLanguage(Map<String, Object> result);

	public int getDataListByChannelIdAndLanguageCount(Map<String, Object> result);

	public List<Article> getDataListByRelevancyIdAndMeId(String relevancyId);
}
