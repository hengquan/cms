package com.hj.web.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.common.ControllerBase;
import com.hj.web.entity.Article;
import com.hj.web.entity.Channel;
import com.hj.web.entity.Language;
import com.hj.web.entity.SysRole;
import com.hj.web.entity.UserInfo;
import com.hj.web.services.ArticleService;
import com.hj.web.services.ChannelService;
import com.hj.web.services.LanguageService;

@Controller
@RequestMapping("/api")
public class CmsApiController extends ControllerBase {

	@Autowired
	LanguageService languageService;
	@Autowired
	ChannelService channelService;
	@Autowired
	ArticleService articleService;

	// 获取当前登入用户所属口岸的主页信息
	@RequestMapping("/getHomeData")
	@ResponseBody
	public Map<String, Object> getHomeData() {
		// 返回信息
		Map<String, Object> result = new HashMap<String, Object>();
		String tab = getTrimParameter("tab");
		String language = getTrimParameter("language");
		if (StringUtils.isNotEmpty(tab)) {
			if (StringUtils.isEmpty(language))
				language = "ZH_CN";
			// 根据标识取站点信息
		} else {
			result.put("code", "201");
			result.put("msg", "请输入正确的站点标识！");
		}

		try {
			// 获取用户信息
			UserInfo userInfo = super.getUserInfo();
			// 获取站点信息
			SysRole userRole = new SysRole();
			// 获取站点相关语言
			List<Language> languageList = new ArrayList<Language>();
			// 获取站点所有频道
			List<Channel> channelList = new ArrayList<Channel>();
			if (userInfo != null) {
				// 获取站点信息
				userRole = super.getUserRole();
				if (userRole != null) {
					// 获取该站点所有相关语言
					String languageId = userRole.getLanguageId();
					if (StringUtils.isNotEmpty(languageId)) {
						languageList = languageService.getByIds(languageId);
					}
					result.put("languageList", languageList);
					result.put("roleName", userRole.getRoleName());
					// 获取频道信息
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("channeltype", 1);
					param.put("roleId", userRole.getId());
					channelList = channelService.getDataByType(param);
					result.put("channelList", channelList);
					result.put("code", "200");
				} else {
					result.put("code", "202");
					result.put("msg", "用户站点信息获取失败！");
				}
			} else {
				result.put("code", "201");
				result.put("msg", "获取用户信息失败请重新登入！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "500");
			result.put("msg", "系统错误,请联系管理员！");
		}
		return result;
	}

	// 获取某一频道下的所有文章
	@RequestMapping("/getArticleList")
	@ResponseBody
	public Map<String, Object> getArticleList(String channelId) {
		// 返回信息
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 获取用户信息
			UserInfo userInfo = super.getUserInfo();
			// 获取站点信息
			SysRole userRole = new SysRole();
			// 获取频道下所有的文章
			List<Article> articleList = new ArrayList<Article>();
			if (userInfo != null) {
				// 获取站点信息
				userRole = super.getUserRole();
				if (userRole != null) {
					articleList = articleService.getDataListByRelevancyId(userRole.getId());
				} else {
					result.put("code", "202");
					result.put("msg", "用户站点信息获取失败！");
				}
			} else {
				result.put("code", "201");
				result.put("msg", "获取用户信息失败请重新登入！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "500");
			result.put("msg", "系统错误,请联系管理员！");
		}
		return result;
	}

}
