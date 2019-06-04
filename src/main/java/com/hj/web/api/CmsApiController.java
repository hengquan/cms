package com.hj.web.api;

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
import com.hj.web.entity.Article;
import com.hj.web.entity.Channel;
import com.hj.web.entity.Language;
import com.hj.web.entity.Module;
import com.hj.web.entity.SysRole;
import com.hj.web.services.ArticleService;
import com.hj.web.services.ChannelService;
import com.hj.web.services.LanguageService;
import com.hj.web.services.ModuleService;
import com.hj.web.services.PageService;
import com.hj.web.services.SysRoleService;

@Controller
@RequestMapping("/api")
public class CmsApiController extends ControllerBase {

	@Autowired
	LanguageService languageService;
	@Autowired
	ChannelService channelService;
	@Autowired
	ArticleService articleService;
	@Autowired
	SysRoleService roleService;
	@Autowired
	PageService pageService;
	@Autowired
	ModuleService moduleService;

	/**
	 * 获取主页信息(站点名称、站点所有的语言)
	 * 
	 * @author zhq
	 * @param tab-站点标识、language-语言
	 * @return
	 */
	@RequestMapping("/getHomeData")
	@ResponseBody
	public Map<String, Object> getHomeData() {
		// 返回信息
		Map<String, Object> result = new HashMap<String, Object>();
		// 站点标识
		String tab = getTrimParameter("tab");
		// 站点名称
		String roleName = "";
		// 站点ID
		String roleId = "";
		// 语言标识
		String language = getTrimParameter("language");
		if (StringUtils.isEmpty(language)) {
			language = "ZH_CN";
		}
		try {
			if (StringUtils.isNotEmpty(tab)) {
				SysRole role = roleService.findByPinYin(tab);
				if (role != null) {
					roleId = role.getId();
					String languages = role.getLanguages();
					String[] languageZu = languages.split(",");
					if (languageZu != null && languageZu.length > 0) {
						for (String languageStr : languageZu) {
							String[] oneLanguage = languageStr.split(":");
							if (oneLanguage != null && oneLanguage.length > 0) {
								String str1 = oneLanguage[1];
								String str2 = oneLanguage[2];
								if (str1.equals(language)) {
									roleName = str2;
								}
							}
						}
						if (StringUtils.isEmpty(roleName)) {
							tab = languageZu[0].split(":")[1];
							roleName = languageZu[0].split(":")[2];
						}
					}
					result.put("code", "200");
					result.put("tab", tab);
					result.put("roleName", roleName);
					String languageId = role.getLanguageId();
					List<Language> languageList = languageService.getByIds(languageId);
					result.put("languageList", languageList);
					result.put("roleId", roleId);
					result.put("language", language);
				} else {
					result.put("code", "202");
					result.put("msg", "未查找到该站信息！");
				}
			} else {
				result.put("code", "201");
				result.put("msg", "请输入正确的站点标识！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "500");
			result.put("msg", "系统错误,请联系管理员！");
		}
		return result;
	}

	/**
	 * 获取主页轮播图信息(该站点有封面图的文章前几篇的封面图)
	 * 
	 * @author zhq
	 * @param imgNumber-获取轮播图的数量、roleId-站点ID
	 * @return
	 */
	@RequestMapping("/getArticlePicUrlList")
	@ResponseBody
	public Map<String, Object> getArticlePicUrlList() {
		// 返回信息
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String imgNumber = request.getParameter("imgNumber") == null ? "" : request.getParameter("imgNumber").toString();
			int number = 3;
			if (StringUtils.isNotEmpty(imgNumber)) {
				number = Integer.parseInt(imgNumber);
			}
			String roleId = getTrimParameter("roleId");
			// 获取某站点下前几篇有封面图的文章
			List<Article> articleList = new ArrayList<Article>();
			if (roleId != null) {
				// NO.1获取该站点所有的频道
				List<Channel> channelList = channelService.getDataByRoleId(roleId);
				if (channelList != null && channelList.size() > 0) {
					String channelIds = "";
					for (Channel channel : channelList) {
						String id = channel.getId();
						if (StringUtils.isNotEmpty(id))
							channelIds += "," + id;
					}
					if (StringUtils.isNotEmpty(channelIds))
						channelIds = channelIds.substring(1);
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("channelIds", channelIds);
					param.put("number", number);
					articleList = articleService.getArticlePicUrlList(param);
					if (articleList != null && articleList.size() > 0) {
						for (Article article : articleList) {
							String picUrl = article.getPicUrl();
							article.setPicUrl(path + picUrl);
						}
						result.put("code", "200");
						result.put("dataList", articleList);
					} else {
						result.put("code", "300");
						result.put("msg", "轮播图数据为空！");
					}
				} else {
					result.put("code", "300");
					result.put("msg", "该站暂无频道信息！");
				}
			} else {
				result.put("code", "201");
				result.put("msg", "获取站点信息失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "500");
			result.put("msg", "系统错误,请联系管理员！");
		}
		return result;
	}

	/**
	 * 获取主页频道列表信息
	 * 
	 * @author zhq
	 * @param channelNumber-获取频道的数量、roleId-站点ID
	 * @return
	 */
	@RequestMapping("/getHomeChannelList")
	@ResponseBody
	public Map<String, Object> getHomeChannelList() {
		// 返回信息
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String channelNumber = request.getParameter("channelNumber") == null ? ""
					: request.getParameter("channelNumber").toString();
			Integer number = 3;
			if (StringUtils.isNotEmpty(channelNumber)) {
				number = Integer.parseInt(channelNumber);
			}
			String roleId = getTrimParameter("roleId");
			String language = getTrimParameter("language");
			if (StringUtils.isNotEmpty(roleId)) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("number", number);
				param.put("roleId", roleId);
				List<Channel> channelList = channelService.selectDataByRoleId(param);
				if (channelList != null && channelList.size() > 0) {
					for (Channel channel : channelList) {
						String languages = channel.getLanguages();
						if (StringUtils.isNotEmpty(languages) && StringUtils.isNotEmpty(language)) {
							String name = getCorrespondingLanguage(language, languages);
							channel.setChannelname(name);
						}
					}
				}
				result.put("code", "200");
				result.put("channelList", channelList);
			} else {
				result.put("code", "201");
				result.put("msg", "获取站点信息失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "500");
			result.put("msg", "系统错误,请联系管理员！");
		}
		return result;
	}

	/**
	 * 根据频道ID获取相关文章
	 * 
	 * @author zhq
	 * @param nowPage-当前页数、pageSize-所取条数、channelId-频道ID
	 * @return
	 */
	@RequestMapping("/getArticleList")
	@ResponseBody
	public Map<String, Object> getArticleList(PageService page) {
		// 返回信息
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String channelId = getTrimParameter("channelId");
			// 语言标识
			String language = getTrimParameter("language");
			if (StringUtils.isEmpty(language)) {
				language = "ZH_CN";
			}
			if (StringUtils.isNotEmpty(channelId)) {
				// 存页面起始位置信息
				pageService.getPageLocation(page, result);
				result.put("channelId", channelId);
				result.put("language", language);
				List<Article> articleList = articleService.getDataListByChannelIdAndLanguage(result);
				int articleListCount = articleService.getDataListByChannelIdAndLanguageCount(result);
				// 获取页面信息
				pageService.getPageData(articleListCount, result, page);
				result.put("code", "200");
				result.put("dataList", articleList);
			} else {
				result.put("code", "201");
				result.put("msg", "获取站点信息失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "500");
			result.put("msg", "系统错误,请联系管理员！");
		}
		return result;
	}

	/**
	 * 根据文章ID获取文章内容
	 * 
	 * @author zhq
	 * @param language-语言、articleId-文章ID
	 * @return
	 */
	@RequestMapping("/getArticle")
	@ResponseBody
	public Map<String, Object> getArticle() {
		// 返回信息
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String articleId = getTrimParameter("articleId");
			// 语言标识
			String language = getTrimParameter("language");
			if (StringUtils.isEmpty(language)) {
				language = "ZH_CN";
			}
			if (StringUtils.isNotEmpty(articleId)) {
				// 存页面起始位置信息
				result.put("articlelId", articleId);
				result.put("language", language);
				Article article = articleService.get(articleId);
				result.put("code", "200");
				result.put("data", article);
			} else {
				result.put("code", "201");
				result.put("msg", "获取文章内容信息失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "500");
			result.put("msg", "系统错误,请联系管理员！");
		}
		return result;
	}

	/**
	 * 获取所有的频道列表
	 * 
	 * @author zhq
	 * @param language-语言、roleId-站点ID
	 * @return
	 */
	@RequestMapping("/getChannelList")
	@ResponseBody
	public Map<String, Object> getChannelList() {
		// 返回信息
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String roleId = getTrimParameter("roleId");
			// 父频道名称
			String moduleId = getTrimParameter("moduleId");
			// 语言标识
			String language = getTrimParameter("language");
			if (StringUtils.isEmpty(language)) {
				language = "ZH_CN";
			}
			if (StringUtils.isNotEmpty(roleId)) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("roleId", roleId);
				param.put("moduleId", moduleId);
				List<Channel> channelList = channelService.selectDataByRoleId(param);
				result.put("code", "200");
				result.put("dataList", channelList);
			} else {
				result.put("code", "201");
				result.put("msg", "获取站点信息失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "500");
			result.put("msg", "系统错误,请联系管理员！");
		}
		return result;
	}

	// 获取站点下所有的模块
	@RequestMapping(value = "/getModuleList")
	@ResponseBody
	public Map<String, Object> getModuleList(ModelMap model) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String roleId = getTrimParameter("roleId");
		String language = getTrimParameter("language");
		if (StringUtils.isEmpty(language)) {
			language = "ZH_CN";
		}
		try {
			if (StringUtils.isNotEmpty(roleId)) {
				List<Module> moduleList = moduleService.getDataByRoleId(roleId);
				if (moduleList != null && moduleList.size() > 0) {
					for (Module module : moduleList) {
						String languages = module.getLanguages();
						if (StringUtils.isNotEmpty(languages) && StringUtils.isNotEmpty(language)) {
							String name = getCorrespondingLanguage(language, languages);
							module.setModuleName(name);
						}
					}
				}
				resultMap.put("code", "200");
				resultMap.put("dataList", moduleList);
			} else {
				resultMap.put("code", "201");
				resultMap.put("msg", "获取站点信息失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("code", "500");
			resultMap.put("msg", "系统错误请联系管理员");
		}
		return resultMap;
	}

	// 根据选择获取不同的语言
	public String getCorrespondingLanguage(String language, String languages) {
		String name = "";
		String[] languageZu = languages.split(",");
		if (languageZu.length > 0) {
			for (String languageXiang : languageZu) {
				if (StringUtils.isNotEmpty(languageXiang)) {
					String[] split = languageXiang.split(":");
					if (split.length >= 3) {
						String tabName = split[1];
						if (tabName.equals(language)) {
							name = split[2];
						}
					}
				}
			}
		}
		return name;
	}
}
