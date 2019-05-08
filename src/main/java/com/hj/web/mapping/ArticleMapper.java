package com.hj.web.mapping;

import java.util.List;
import java.util.Map;

import com.hj.web.entity.Article;

public interface ArticleMapper {
    int del(String id);

    int insert(Article record);

    Article get(String id);

    int update(Article record);

		List<Article> findAll();

		List<Article> getDataList(Map<String, Object> map);

		Integer getDataListCount(Map<String, Object> map);

		void deletes(String ids);

		List<Article> getDataListByRelevancyId(String id);

		List<Article> getArticleParentDataList(Article article);

		List<Article> getParentDataList(String id);
}