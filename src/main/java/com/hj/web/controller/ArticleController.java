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
import com.hj.web.entity.Language;
import com.hj.web.entity.SysRole;
import com.hj.web.entity.UserInfo;
import com.hj.web.entity.UserRole;
import com.hj.web.services.ArticleService;
import com.hj.web.services.ChannelService;
import com.hj.web.services.LanguageService;
import com.hj.web.services.PageService;
import com.hj.web.services.SysRoleService;
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
	@Autowired
	SysRoleService roleService;
	@Autowired
	LanguageService languageService;

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
					// 处理站点
					if (StringUtils.isNotEmpty(roleId)) {
						SysRole role = roleService.findById(roleId);
						if (role != null) {
							String roleName = role.getRoleName();
							if (StringUtils.isNotEmpty(roleName)) {
								article.setRoleId(roleId);
								article.setRoleName(roleName);
							}
						}
					}
					// 处理图片路径
					urlManage(article);
					// 处理频道
					String channelIds = article.getArticleType();
					if (StringUtils.isNotEmpty(channelIds)) {
						List<Channel> channelList = channelService.getByIds(channelIds);
						if (channelList != null && channelList.size() > 0) {
							String channelNames = "";
							for (Channel channel : channelList) {
								if (channel != null && StringUtils.isNotEmpty(channel.getChannelname())) {
									channelNames += "，" + channel.getChannelname();
								}
							}
							if (StringUtils.isNotEmpty(channelNames)) {
								channelNames = channelNames.substring(1);
								article.setSetArticleTypeName(channelNames);
							}
						}
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
	public String addPage(ModelMap model) throws Exception {
		String articleType = getTrimParameter("articleType");
		String channelType = getTrimParameter("channelType");
		String roleId = getTrimParameter("roleId");
		String pageUrl = "article/edit";
		super.userIRoleItem(model, pageUrl);
		model.addAttribute("articleType", articleType);
		model.addAttribute("channelType", channelType);
		model.addAttribute("roleId", roleId);
		// 获取站点信息
		List<Language> languageList = new ArrayList<Language>();
		SysRole role = roleService.findById(roleId);
		if (role != null) {
			String languageId = role.getLanguageId();
			languageList = languageService.getByIds(languageId);
		}
		model.addAttribute("languageList", languageList);
		// 获取语言标识
		String languageTab = getTrimParameter("languageTab");
		if (StringUtils.isEmpty(languageTab))
			languageTab = "ZH_CN";
		model.addAttribute("languageTab", languageTab);
		// 获取相关文章
		Article article = new Article();
		String articleId = getTrimParameter("articleId");
		model.addAttribute("article", article);
		model.addAttribute("articleId", articleId);
		return pageUrl;
	}

	// 打开修改文章页面
	@RequestMapping(value = "/article/editPage")
	public String editPage(ModelMap model, Article article) throws Exception {
		String languageTab = getTrimParameter("languageTab");
		String roleId = getTrimParameter("roleId");
		String channelType = getTrimParameter("channelType");
		String articleType = "";
		String id = article.getId();
		model.addAttribute("articleId", id);
		if (StringUtils.isNotEmpty(id)) {
			article = articleService.get(id);
			// 对比ID和语言
			String language = article.getLanguage();
			if (StringUtils.isEmpty(languageTab) || StringUtils.isEmpty(language) || !languageTab.equals(language)) {
				List<Article> articles = articleService.getDataListByRelevancyId(article.getId());
				if (articles != null && articles.size() > 0) {
					boolean result = false;
					for (Article data : articles) {
						String dataLanguage = data.getLanguage();
						if (dataLanguage.equals(languageTab)) {
							article = data;
							result = true;
							break;
						}
					}
					if (!result)
						article = new Article();
				} else {
					article = new Article();
				}
			}
		}
		if (article != null && StringUtils.isNotEmpty(article.getArticleType())) {
			articleType = article.getArticleType();
			if (StringUtils.isEmpty(languageTab))
				languageTab = article.getLanguage();
		}
		urlManage(article);
		// 获取站点信息
		List<Language> languageList = new ArrayList<Language>();
		SysRole role = roleService.findById(roleId);
		if (role != null) {
			String languageId = role.getLanguageId();
			languageList = languageService.getByIds(languageId);
		}
		model.addAttribute("languageList", languageList);
		// 添加其他
		model.addAttribute("articleType", articleType);
		model.addAttribute("article", article);
		model.addAttribute("roleId", roleId);
		model.addAttribute("editOperation", "editOperation");
		model.addAttribute("languageTab", languageTab);
		model.addAttribute("channelType", channelType);
		String pageUrl = "article/edit";
		super.userIRoleItem(model, pageUrl);
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
		String roleId = getTrimParameter("roleId");
		model.addAttribute("roleId", roleId);
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
		String roleId = getTrimParameter("roleId");
		model.addAttribute("roleId", roleId);
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
