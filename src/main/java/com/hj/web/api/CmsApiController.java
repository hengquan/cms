package com.hj.web.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			language = "Chinese";
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
								String str1 = "";
								if (oneLanguage.length > 1) {
									str1 = oneLanguage[1];
								}
								String str2 = "";
								if (oneLanguage.length > 2) {
									str2 = oneLanguage[2];
								}
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
			String imgNumber = request.getParameter("imgNumber") == null ? ""
					: request.getParameter("imgNumber").toString();
			int number = 3;
			if (StringUtils.isNotEmpty(imgNumber)) {
				number = Integer.parseInt(imgNumber);
			}
			String roleId = getTrimParameter("roleId");
			String language = getTrimParameter("language");
			String channelType = getTrimParameter("channelType");
			if (StringUtils.isEmpty(language)) {
				language = "Chinese";
			}
			// 获取某站点下前几篇有封面图的文章
			List<Article> articleList = new ArrayList<Article>();
			if (roleId != null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("roleId", roleId);
				map.put("channelType", channelType);
				// NO.1获取该站点所有的频道
				List<Channel> channelList = channelService.getDataByRoleId(map);
				if (channelList != null && channelList.size() > 0) {
					String channelIds = "";
					for (Channel channel : channelList) {
						String channelname = channel.getChannelname();
						if (StringUtils.isNotEmpty(channelname) && channelname.equals("轮播图频道")) {
							String id = channel.getId();
							if (StringUtils.isNotEmpty(id))
								channelIds += "," + id;
						}
					}
					if (StringUtils.isNotEmpty(channelIds))
						channelIds = channelIds.substring(1);
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("channelIds", channelIds);
					param.put("language", language);
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
			String channelType = getTrimParameter("channelType");
			String language = getTrimParameter("language");
			if (StringUtils.isNotEmpty(roleId)) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("number", number);
				param.put("roleId", roleId);
				param.put("channelType", channelType);
				List<Channel> dataList = new ArrayList<Channel>();
				List<Channel> channelList = channelService.selectDataByRoleId(param);
				if (channelList != null && channelList.size() > 0) {
					for (Channel channel : channelList) {
						String languages = channel.getLanguages();
						if (StringUtils.isNotEmpty(languages) && StringUtils.isNotEmpty(language)) {
							String name = getCorrespondingLanguage(language, languages);
							channel.setChannelname(name);
							// 获取频道名称
							String channelname = channel.getChannelname();
							if (StringUtils.isNotEmpty(channelname) && !channelname.equals("轮播图频道")) {
								dataList.add(channel);
							}
						}
					}
				}
				result.put("code", "200");
				result.put("channelList", dataList);
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
				language = "Chinese";
			}
			if (StringUtils.isNotEmpty(channelId)) {
				// 存页面起始位置信息
				pageService.getPageLocation(page, result);
				result.put("channelId", channelId);
				result.put("language", language);
				List<Article> articleList = articleService.getDataListByChannelIdAndLanguage(result);
				if (articleList != null && articleList.size() > 0) {
					for (Article article : articleList) {
						String picUrl = article.getPicUrl();
						if (StringUtils.isNotEmpty(picUrl))
							article.setPicUrl(path + picUrl);
						// 获取内容中的图片
						List<String> contentImgList = article.getContentImg();
						contentImgList = getContentImg(article.getArticle());
						article.setContentImg(contentImgList);
					}
				}
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
				language = "Chinese";
			}
			if (StringUtils.isNotEmpty(articleId)) {
				// 存页面起始位置信息
				result.put("articlelId", articleId);
				result.put("language", language);
				Article article = articleService.get(articleId);
				String relevancyId = article.getRelevancyId();
				if (StringUtils.isNotEmpty(relevancyId) && relevancyId.equals("0")) {
					relevancyId = article.getId();
				}
				if (StringUtils.isNotEmpty(relevancyId)) {
					List<Article> articleList = articleService.getDataListByRelevancyIdAndMeId(relevancyId);
					if (articleList != null && articleList.size() > 0) {
						for (Article one : articleList) {
							String oneLanguage = one.getLanguage();
							if (oneLanguage.equals(language))
								article = one;
						}
					}
				}
				// 获取该文章的频道信息
				String articleType = article.getArticleType();
				Channel channel = channelService.get(articleType);
				if (channel != null) {
					String channelname = channel.getChannelname();
					if (StringUtils.isNotEmpty(channelname)) {
						article.setSetArticleTypeName(channelname);
					}
				}

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
			String channelType = getTrimParameter("channelType");
			// 父频道名称
			String moduleId = getTrimParameter("moduleId");
			// 语言标识
			String language = getTrimParameter("language");
			if (StringUtils.isEmpty(language)) {
				language = "Chinese";
			}
			if (StringUtils.isNotEmpty(roleId)) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("roleId", roleId);
				param.put("channelType", channelType);
				param.put("moduleId", moduleId);
				List<Channel> channelList = channelService.selectDataByRoleId(param);
				if (channelList != null && channelList.size() > 0) {
					for (Channel channel : channelList) {
						// 处理图片
						String picUrl = channel.getPicUrl();
						if (StringUtils.isNotEmpty(picUrl))
							channel.setPicUrl(path + picUrl);
						// 切换语言
						String channelName = "";
						String languages = channel.getLanguages();
						String[] languageZu = languages.split(",");
						if (languageZu != null && languageZu.length > 0) {
							for (String languageStr : languageZu) {
								String[] oneLanguage = languageStr.split(":");
								if (oneLanguage != null && oneLanguage.length > 0) {
									String str1 = "";
									if (oneLanguage.length > 2 && StringUtils.isNotEmpty(oneLanguage[1])) {
										str1 = oneLanguage[1];
									}
									String str2 = "";
									if (oneLanguage.length > 2 && StringUtils.isNotEmpty(oneLanguage[2])) {
										str2 = oneLanguage[2];
									}
									if (str1.equals(language)) {
										channelName = str2;
									}
								}
							}
						}
						channel.setChannelname(channelName);
						// 获取子级频道
						String id = channel.getId();
						List<Channel> ziChannelList = channelService.getByParentId(id);
						if (ziChannelList != null && ziChannelList.size() > 0) {
							for (Channel ziChannel : ziChannelList) {
								// 切换语言
								String ziChannelName = "";
								String ziLanguages = ziChannel.getLanguages();
								String[] ziLanguageZu = ziLanguages.split(",");
								if (ziLanguageZu != null && ziLanguageZu.length > 0) {
									for (String languageStr : ziLanguageZu) {
										String[] oneLanguage = languageStr.split(":");
										if (oneLanguage != null && oneLanguage.length > 0) {
											String str1 = "";
											if (oneLanguage.length > 2 && StringUtils.isNotEmpty(oneLanguage[1])) {
												str1 = oneLanguage[1];
											}
											String str2 = "";
											if (oneLanguage.length > 2 && StringUtils.isNotEmpty(oneLanguage[2])) {
												str2 = oneLanguage[2];
											}
											if (str1.equals(language)) {
												ziChannelName = str2;
											}
										}
									}
								}
								ziChannel.setChannelname(ziChannelName);
							}
							channel.setChannelList(ziChannelList);
						}
					}
				}
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

	/**
	 * 获取指定频道下面的频道列表
	 * 
	 * @author zhq
	 * @param language-语言、channelId-频道ID
	 * @return
	 */
	@RequestMapping("/getSonChannelList")
	@ResponseBody
	public Map<String, Object> getSonChannelList() {
		// 返回信息
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 父频道名称
			String channelId = getTrimParameter("channelId");
			// 语言标识
			String language = getTrimParameter("language");
			if (StringUtils.isEmpty(language)) {
				language = "Chinese";
			}
			List<Channel> channelList = channelService.getByParentId(channelId);
			if (channelList != null && channelList.size() > 0) {
				for (Channel channel : channelList) {
					// 存相关文章的信息
					String id = channel.getId();
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("channelId", id);
					param.put("language", language);
					param.put("page", 0);
					param.put("pageSize", 100);
					List<Article> articleList = articleService.getDataListByChannelIdAndLanguage(param);
					if (articleList != null && articleList.size() > 0) {
						for (Article article : articleList) {
							String picUrl = article.getPicUrl();
							if (StringUtils.isNotEmpty(picUrl))
								article.setPicUrl(path + picUrl);
						}
					}
					channel.setArticleList(articleList);
					// 处理图片
					String picUrl = channel.getPicUrl();
					if (StringUtils.isNotEmpty(picUrl))
						channel.setPicUrl(path + picUrl);
					// 切换语言
					String channelName = "";
					String languages = channel.getLanguages();
					String[] languageZu = languages.split(",");
					if (languageZu != null && languageZu.length > 0) {
						for (String languageStr : languageZu) {
							String[] oneLanguage = languageStr.split(":");
							if (oneLanguage != null && oneLanguage.length > 0) {
								String str1 = "";
								if (oneLanguage.length > 2 && StringUtils.isNotEmpty(oneLanguage[1])) {
									str1 = oneLanguage[1];
								}
								String str2 = "";
								if (oneLanguage.length > 2 && StringUtils.isNotEmpty(oneLanguage[2])) {
									str2 = oneLanguage[2];
								}
								if (str1.equals(language)) {
									channelName = str2;
								}
							}
						}
					}
					channel.setChannelname(channelName);
				}
			}
			result.put("code", "200");
			result.put("dataList", channelList);
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
		String channelType = getTrimParameter("channelType");
		String moduleType = "";
		if (StringUtils.isNotEmpty(channelType)) {
			if (channelType.equals("1")) {
				moduleType = "APP";
			} else if (channelType.equals("2")) {
				moduleType = "H5";
			} else if (channelType.equals("3")) {
				moduleType = "触摸板";
			} else if (channelType.equals("4")) {
				moduleType = "APP视频";
			}
		}
		if (StringUtils.isEmpty(language)) {
			language = "Chinese";
		}
		try {
			if (StringUtils.isNotEmpty(roleId)) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("roleId", roleId);
				param.put("moduleType", moduleType);
				List<Module> moduleList = moduleService.getDataByRoleId(param);
				if (moduleList != null && moduleList.size() > 0) {
					for (Module module : moduleList) {
						String languages = module.getLanguages();
						if (StringUtils.isNotEmpty(languages) && StringUtils.isNotEmpty(language)) {
							String name = getCorrespondingLanguage(language, languages);
							module.setModuleName(name);
							String picUrl = module.getPicUrl();
							if (StringUtils.isNotEmpty(picUrl))
								module.setPicUrl(path + picUrl);
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

	public List<String> getContentImg(String content) {
		List<String> contentImgList = new ArrayList<String>();
		String start = "src=\"";
		String end = "\"";
		String reg = start + ".*?" + end;
		// 利用正则表达式定义规则，a开头中间除了a任意都获取，b结尾
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(content);
		while (m.find()) {
			String contentImg = m.group().substring(5).substring(0, m.group().substring(5).length() - 1);
			if (StringUtils.isNotEmpty(contentImg)) {
				contentImg = contentImg.replace("/..", serverPath);
				contentImgList.add(contentImg);
			}
			System.out.println("----------------------------------------------");
			System.out.println(contentImg);
			System.out.println("----------------------------------------------");
		}
		return contentImgList;
	}

	/**
	 * 外部调用接口
	 * 
	 * @author zhq
	 * @param channelType-频道所属类型（0-暂无，1-APP，2-H5，3-触摸板，4-APP视频）
	 * @param tab-站点标识
	 * @param language-语言标识
	 * @return
	 */
	@RequestMapping("/getHomePageList")
	@ResponseBody
	public Map<String, Object> getHomePageList() {
		// 站点标识
		String tab = getTrimParameter("tab");
		// 站点ID
		String roleId = "";
		String channelType = getTrimParameter("channelType");
		String language = getTrimParameter("language");
		if (StringUtils.isEmpty(language)) {
			language = "Chinese";
		}
		if (StringUtils.isNotEmpty(tab)) {
			SysRole role = roleService.findByPinYin(tab);
			roleId = role.getId();
		}
		// 返回信息
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(roleId)) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("number", 100);
				param.put("roleId", roleId);
				param.put("channelType", channelType);
				List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
				List<Channel> channelList = channelService.selectDataByRoleId(param);
				if (channelList != null && channelList.size() > 0) {
					for (Channel channel : channelList) {
						String languages = channel.getLanguages();
						if (StringUtils.isNotEmpty(languages) && StringUtils.isNotEmpty(language)) {
							// 获取频道名称
							String channelname = channel.getChannelname();
							// 获取频道描述
							String descn = channel.getDescn();
							if (StringUtils.isNotEmpty(channelname) && !channelname.equals("轮播图频道")
									&& descn.equals("APP导航页")) {
								Map<String, Object> map = new HashMap<String, Object>();
								String allLanguage = channel.getLanguages();
								// 访问地址所有语言（除蒙文外）
								String hrefUrl = channel.getHrefUrl();
								// 蒙文
								String mengWenHrefUrl = hrefUrl.replace("gqmd", "mengwen");
								mengWenHrefUrl = mengWenHrefUrl + "?tab=" + tab + "&channelId=" + channel.getId();
								hrefUrl = hrefUrl + "?tab=" + tab + "&channelId=" + channel.getId();
								map.put("homeChannelName", allLanguage);
								map.put("homeChannelUrl", hrefUrl);
								map.put("homeChannelMengWenUrl", mengWenHrefUrl);
								dataList.add(map);
							}
						}
					}
				}
				result.put("code", "200");
				result.put("channelList", dataList);
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

	// 保存频道
	@RequestMapping(value = "/channelSave")
	@ResponseBody
	public Map<String, Object> saveProject(Channel project) {
		// 返回信息
		Map<String, Object> result = new HashMap<String, Object>();
		String id = "abcdefghikasldf123";
		try {
			project.setId(id);
			channelService.save(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Channel channel = channelService.get(id);
		result.put("data", channel);
		return result;
	}
}
