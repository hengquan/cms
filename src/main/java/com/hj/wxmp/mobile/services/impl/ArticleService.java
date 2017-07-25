package com.hj.wxmp.mobile.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.wxmp.mobile.dao.ArticleDao;
import com.hj.wxmp.mobile.entity.TblArticle;

/**   
* @Title: ArticleService.java
* @Package com.hj.wxmp.mobile.services.impl
* @Description: TODO
* @author Wangzhiyong   
* @date 2016年7月14日 下午3:10:45
* @version V1.0   
*/
@Service
public class ArticleService {
	
	@Autowired
	ArticleDao articleDao;
	public Map<String,Object> getArticleCount(String title, String articleType,int pageSize){
		int count = articleDao.getArticleCounts(title,articleType);
		//总页数
		int totalPageNum = (count  +  pageSize  - 1) / pageSize;  
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("count", count);
		map.put("totalPageNum", totalPageNum);
		return map;
	}

	public List<TblArticle> getArticles(String title, String articleType,int nowPage, int pageSize) {
		List<TblArticle> list = articleDao.getArticles(title,articleType,nowPage, pageSize);
		return list;
	}
}
