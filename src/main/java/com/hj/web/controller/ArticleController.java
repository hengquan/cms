package com.hj.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.common.ControllerBase;
import com.hj.utils.Configurations;
import com.hj.web.entity.Article;
import com.hj.web.entity.Channel;
import com.hj.web.entity.UserInfo;
import com.hj.web.entity.UserRole;
import com.hj.web.services.ArticleService;
import com.hj.web.services.ChannelService;
import com.hj.web.services.PageService;
import com.hj.web.services.UserRoleService;

//文章管理
@Controller
public class ArticleController extends ControllerBase {

	@Autowired
	ArticleService articleService;
	@Autowired
	ChannelService channelService;
	@Autowired
	PageService pageService;
	@Autowired
	UserRoleService userRoleService;

	// 文章列表
	@RequestMapping(value = "/article/getDataList")
	public String getDataList(PageService page, ModelMap model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageUrl = "article/list";
		String keyword = getTrimParameter("keyword");
		// 频道ID
		String articleType = getTrimParameter("articleType");
		// 渠道名称
		String channelType = getTrimParameter("channelType");
		// 站点ID
		String roleId = getTrimParameter("roleId");
		// 判断当前用户是啥级别的
		UserInfo userInfo = super.getUserInfo();
		if (userInfo != null) {
			String parentId = userInfo.getParentId();
			if (StringUtils.isNotEmpty(parentId)) {
				if (!parentId.equals("0")) {
					userInfo = super.getParentUserData(userInfo);
					if (userInfo != null) {
						String userId = userInfo.getId();
						if (StringUtils.isNotEmpty(userId)) {
							UserRole userRole = userRoleService.selectByUserId(userId);
							if (userRole != null) {
								roleId = userRole.getRoleid();
							}
						}
					}
				}
			}
		}
		map.put("roleId", roleId);
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
			model.addAttribute("roleId", roleId);
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
		String roleId = getTrimParameter("roleId");
		String pageUrl = "article/edit";
		super.userIRoleItem(model, pageUrl);
		model.addAttribute("articleType", articleType);
		model.addAttribute("channelType", channelType);
		model.addAttribute("roleId", roleId);
		// 获取相关文章
		Article article = new Article();
		String articleId = getTrimParameter("articleId");
		article.setRelevancyId(articleId);
		model.addAttribute("article", article);
		return pageUrl;
	}

	// 打开修改文章页面
	@RequestMapping(value = "/article/editPage")
	public String editPage(ModelMap model, Article article) {
		String language = getTrimParameter("language");
		String roleId = getTrimParameter("roleId");
		String articleType = "";
		String id = article.getId();
		if (StringUtils.isNotEmpty(id))
			article = articleService.get(id);
		if (article != null && StringUtils.isNotEmpty(article.getArticleType())) {
			articleType = article.getArticleType();
			if (StringUtils.isEmpty(language))
				language = article.getLanguage();
		}
		urlManage(article);
		model.addAttribute("articleType", articleType);
		model.addAttribute("article", article);
		model.addAttribute("roleId", roleId);
		model.addAttribute("editOperation", "editOperation");
		model.addAttribute("language", language);
		String pageUrl = "article/edit";
		super.userIRoleItem(model, pageUrl);
		// 判断状态是查看还是修改
		String type = getTrimParameter("type");
		model.addAttribute("type", type);
		// 获取所有相关的文章
		List<Article> articleList = new ArrayList<Article>();
		articleList = getCorrelationData(articleList, article);
		model.addAttribute("dataList", articleList);
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
		String itemId = getTrimParameter("itemId");
		String positionId = getTrimParameter("positionId");
		model.addAttribute("itemId", itemId);
		model.addAttribute("positionId", positionId);
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
		String itemId = getTrimParameter("itemId");
		String positionId = getTrimParameter("positionId");
		model.addAttribute("itemId", itemId);
		model.addAttribute("positionId", positionId);
		return "redirect:getDataList";
	}

	// 处理图片访问地址
	public Article urlManage(Article article) {
		if (article != null) {
			String picUrl = article.getPicUrl();
			if (StringUtils.isNotEmpty(picUrl)) {
				String path = Configurations.getAccessUrl();
				if (StringUtils.isNotEmpty(path)) {
					picUrl = path + picUrl;
					article.setPicUrl(picUrl);
				}
			}
		}
		return article;
	}

	// 获取所有文章相关的文章
	private List<Article> getCorrelationData(List<Article> articleList, Article article) {
		if (article != null) {
			String relevancyId = article.getRelevancyId();
			if (StringUtils.isNotEmpty(relevancyId) && !relevancyId.equals("0")) {
				article = articleService.get(relevancyId);
			}
			articleList.add(article);
			List<Article> dataList = articleService.getDataListByRelevancyId(article.getId());
			if (dataList != null && dataList.size() > 0) {
				articleList.addAll(dataList);
			}
		}
		return articleList;
	}

	@RequestMapping(value = "/article/getArticleParentDataList")
	@ResponseBody
	private Map<String, Object> getArticleParentDataList(Article article) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 查看该文章下面是否还有其他相关文章如果有的话不允许其成为其他的下级
		String id = article.getId();
		List<Article> parentDataList = articleService.getParentDataList(id);
		List<Article> articleList = new ArrayList<Article>();
		if (parentDataList == null || parentDataList.size() <= 0) {
			articleList = articleService.getArticleParentDataList(article);
		}
		result.put("dataList", articleList);
		result.put("msg", "0");
		return result;
	}
}
