package com.hj.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hj.common.ControllerBase;
import com.hj.utils.Configurations;
import com.hj.web.entity.Article;
import com.hj.web.entity.Channel;
import com.hj.web.entity.UserInfo;
import com.hj.web.services.ArticleService;
import com.hj.web.services.ChannelService;
import com.hj.web.services.PageService;

//文章管理
@Controller
public class ArticleController extends ControllerBase {

	@Autowired
	ArticleService articleService;
	@Autowired
	ChannelService channelService;
	@Autowired
	PageService pageService;

	// 文章列表
	@RequestMapping(value = "/article/getDataList")
	public String getDataList(PageService page, ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageUrl = "article/list";
		String keyword = getTrimParameter("keyword");
		//频道ID
		String articleType = getTrimParameter("articleType");
		//渠道名称
		String channelType = getTrimParameter("channelType");
		try {
			// 存页面起始位置信息
			pageService.getPageLocation(page, map);
			if (StringUtils.isNotEmpty(keyword)) {
				map.put("keyword", keyword);
			} else {
				map.put("keyword", "");
			}
			if (StringUtils.isNotEmpty(articleType)) {
				map.put("articleType", articleType);
			} else {
				map.put("articleType", "");
			}
			// 获取所有文章信息
			List<Article> articleList = articleService.getDataList(map);
			if (articleList != null && articleList.size() > 0) {
				for (Article article : articleList) {
					// 处理图片路径
					urlManage(article);
					// 处理频道
					String channelId = article.getArticleType();
					if (StringUtils.isNotEmpty(channelId)) {
						Channel channel = channelService.get(channelId);
						if (channel != null && StringUtils.isNotEmpty(channel.getChannelname()))
							article.setSetArticleTypeName(channel.getChannelname());
					}
				}
			}
			// 所有信息数量
			int listMessgeCount = articleService.getDataListCount(map);
			// 获取页面信息
			pageService.getPageData(listMessgeCount, model, page);
			model.put("keyword", keyword);
			model.addAttribute("dataList", articleList);
			model.addAttribute("articleType", articleType);
			model.addAttribute("channelType", channelType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pageUrl = super.userIRoleItem(model, pageUrl);
		return pageUrl;
	}

	// 打开添加文章页面
	@RequestMapping(value = "/article/addPage")
	public String addPage(ModelMap model) {
		String articleType = getTrimParameter("articleType");
		String channelType = getTrimParameter("channelType");
		String pageUrl = "article/edit";
		super.userIRoleItem(model, pageUrl);
		model.addAttribute("articleType", articleType);
		model.addAttribute("channelType", channelType);
		return pageUrl;
	}

	// 打开修改文章页面
	@RequestMapping(value = "/article/editPage")
	public String editPage(ModelMap model, Article article) {
		String articleType = "";
		String id = article.getId();
		if (StringUtils.isNotEmpty(id))
			article = articleService.get(id);
		if(article!=null && StringUtils.isNotEmpty(article.getArticleType()))
			articleType = article.getArticleType();
		urlManage(article);
		model.addAttribute("articleType", articleType);
		model.addAttribute("article", article);
		model.addAttribute("editOperation", "editOperation");
		String pageUrl = "article/edit";
		super.userIRoleItem(model, pageUrl);
		return pageUrl;
	}

	// 编辑文章
	@RequestMapping(value = "/article/save")
	public String addArticle(ModelMap model, Article article) {
		String channelType = getTrimParameter("channelType");
		String articleType = article.getArticleType();
		try {
			Object obj = request.getSession().getAttribute("adminSession");
			if (null != obj) {
				UserInfo userInfo = (UserInfo) obj;
				articleService.save(article, userInfo);
			} else {
				return "{\"code\":\"error\"}";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("channelType", channelType);
		model.addAttribute("articleType", articleType);
		return "redirect:getDataList";
	}

	// 删除文章
	@RequestMapping(value = "/article/del")
	public String delArticle(ModelMap model, Channel project) {
		String channelType = getTrimParameter("channelType");
		String articleType = getTrimParameter("articleType");
		try {
			String boxeditId = getTrimParameter("boxeditId");
			if (StringUtils.isNotEmpty(boxeditId)) {
				articleService.deletes(boxeditId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("channelType", channelType);
		model.addAttribute("articleType", articleType);
		return "redirect:getDataList";
	}

	// 处理图片访问地址
	public Article urlManage(Article article) {
		if (article != null) {
			String picUrl = article.getPicUrl();
			if (StringUtils.isNotEmpty(picUrl)) {
				String path = Configurations.getAccessUrl();
				if (StringUtils.isNotEmpty(path)){
					picUrl = path + picUrl;
					article.setPicUrl(picUrl);
				}
			}
		}
		return article;
	}
}
