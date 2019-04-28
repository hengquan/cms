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
		// 来源名称
		String sourceName = getTrimParameter("sourceName");
		try {
			// 存页面起始位置信息
			pageService.getPageLocation(page, map);
			if (StringUtils.isNotEmpty(keyword)) {
				map.put("keyword", keyword);
			} else {
				map.put("keyword", "");
			}
			// 获取所有文章信息
			List<Article> articleList = articleService.getDataList(map);
			if (articleList != null && articleList.size() > 0) {
				for (Article article : articleList) {
					String articleType = article.getArticleType();
					if (StringUtils.isNotEmpty(articleType)) {
						Channel channel = channelService.get(articleType);
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
			model.addAttribute("sourceName", sourceName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pageUrl = super.userIRoleItem(model, pageUrl);
		return pageUrl;
	}

	// 打开添加文章页面
	@RequestMapping(value = "/article/addPage")
	public String addPage(ModelMap model) {
		String pageUrl = "article/edit";
		// 来源名称
		String sourceName = getTrimParameter("sourceName");
		super.userIRoleItem(model, pageUrl);
		model.addAttribute("sourceName", sourceName);
		return pageUrl;
	}

	// 打开修改文章页面
	@RequestMapping(value = "/article/editPage")
	public String editPage(ModelMap model, Article article) {
		// 来源名称
		String sourceName = getTrimParameter("sourceName");
		String channelName = getTrimParameter("channelname");
		String id = article.getId();
		if (StringUtils.isNotEmpty(id))
			article = articleService.get(id);
		model.addAttribute("channelName", channelName);
		model.addAttribute("article", article);
		model.addAttribute("editOperation", "editOperation");
		model.addAttribute("sourceName", sourceName);
		String pageUrl = "article/edit";
		super.userIRoleItem(model, pageUrl);
		return pageUrl;
	}

	// 添加文章
	@RequestMapping(value = "/article/save")
	public String addArticle(ModelMap model, Article article) {
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
		return "redirect:getDataList";
	}

	// 删除文章
	@RequestMapping(value = "/article/del")
	public String delArticle(ModelMap model, Channel project) {
		try {
			String boxeditId = getTrimParameter("boxeditId");
			if (StringUtils.isNotEmpty(boxeditId)) {
				articleService.deletes(boxeditId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:getDataList";
	}
}
