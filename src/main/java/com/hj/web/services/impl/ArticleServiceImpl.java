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
		if (StringUtils.isNotEmpty(id)) {
			entity.setUpdateTime(new Date());
			result = dao.update(entity) > 0 ? true : false;
			// 查一下子是否是中文的，如果是查一下子是否有相关联的文章，把相关联的文章的索引改成中文的索引
			updateRelevanceArticleSort(entity);
		} else {
			entity.setId(keyGen.getUUIDKey());
			entity.setUpdateTime(new Date());
			if (userInfo != null) {
				String userId = userInfo.getId();
				String userName = userInfo.getRealname();
				if (StringUtils.isNotEmpty(userId))
					entity.setUserId(userId);
				if (StringUtils.isNotEmpty(userName))
					entity.setUserName(userName);
			}
			result = dao.insert(entity) > 0 ? true : false;
		}
		return result;
	}

	// 查一下子是否是中文的，如果是查一下子是否有相关联的文章，把相关联的文章的索引改成中文的索引
	private void updateRelevanceArticleSort(Article entity) {
		String articleId = entity.getId();
		Article article = dao.get(articleId);
		if (article != null) {
			String language = article.getLanguage();
			if (StringUtils.isNotEmpty(language)) {
				if (language.equals("Chinese")) {
					Integer sort = article.getSort();
					// 查看语言
					String relevancyId = article.getRelevancyId();
					if (StringUtils.isNotEmpty(relevancyId)) {
						if (relevancyId.equals("0")) {
							// 查相关文章
							List<Article> dataList = dao.getDataListByRelevancyId(articleId);
							if (dataList != null && dataList.size() > 0) {
								for (Article oneArticle : dataList) {
									oneArticle.setSort(sort);
									dao.update(oneArticle);
								}
							}
						} else {
							Article ziArticle = dao.get(relevancyId);
							if (ziArticle != null) {
								String id = ziArticle.getId();
								if (StringUtils.isNotEmpty(id)) {
									// 查相关文章
									List<Article> dataList = dao.getDataListByRelevancyId(id);
									if (dataList != null && dataList.size() > 0) {
										for (Article oneArticle : dataList) {
											oneArticle.setSort(sort);
											dao.update(oneArticle);
										}
									}
								}
							}
						}
					}
				}
			}
		}
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

	@Override
	public List<Article> getDataListByRelevancyId(String id) {
		return dao.getDataListByRelevancyId(id);
	}

	@Override
	public List<Article> getArticleParentDataList(Article article) {
		return dao.getArticleParentDataList(article);
	}

	@Override
	public List<Article> getParentDataList(String id) {
		return dao.getParentDataList(id);
	}

	@Override
	public List<Article> getArticlePicUrlList(Map<String, Object> param) {
		return dao.getArticlePicUrlList(param);
	}

	@Override
	public List<Article> getDataListByChannelIdAndLanguage(Map<String, Object> result) {
		return dao.getDataListByChannelIdAndLanguage(result);
	}

	@Override
	public int getDataListByChannelIdAndLanguageCount(Map<String, Object> result) {
		return dao.getDataListByChannelIdAndLanguageCount(result);
	}

	@Override
	public List<Article> getDataListByRelevancyIdAndMeId(String relevancyId) {
		return dao.getDataListByRelevancyIdAndMeId(relevancyId);
	}

}
