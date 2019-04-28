package com.hj.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hj.common.ControllerBase;
import com.hj.web.entity.Article;
import com.hj.web.entity.Channel;
import com.hj.web.services.ArticleService;

//文章管理
@Controller
public class ArticleController extends ControllerBase {

	@Autowired
	ArticleService articleService;

	// 文章列表
	@RequestMapping(value = "/article/getDataList")
	public String userList(@RequestParam(value = "nowPage", defaultValue = "1") int nowPage,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize, ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageUrl = "article/list";
		// 纪录总数
		Integer listMessgeCount = 0;
		String keyword = getTrimParameter("keyword");
		Integer start = ((nowPage - 1) * pageSize);
		map.put("page", start);
		map.put("pageSize", pageSize);
		try {
			if (StringUtils.isNotEmpty(keyword)) {
				map.put("keyword", "");
			} else {
				map.put("keyword", keyword);
			}
			// 获取所有文章信息
			List<Article> articleList = articleService.getDataList(map);
			// 所有信息数量
			listMessgeCount = articleService.getDataListCount(map);
			Integer totalCount = listMessgeCount % pageSize;
			Integer totalPageNum = 0;
			if (totalCount == 0) {
				totalPageNum = listMessgeCount / pageSize;
			} else {
				totalPageNum = (listMessgeCount / pageSize) + 1;
			}
			model.put("nowPage", nowPage);
			model.put("totalPageNum", totalPageNum);
			model.put("keyword", keyword);
			model.addAttribute("dataList", articleList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pageUrl = super.userIRoleItem(model, pageUrl);
		return pageUrl;
	}

	// 添加文章
	@RequestMapping(value = "/article/add")
	public String addProject(ModelMap model, Article article) {
		try {
			articleService.insert(article);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:getDataList";
	}

	// 修改文章
	@RequestMapping(value = "/article/edit")
	public String editProject(ModelMap model, Article article) {
		String editId = getTrimParameter("editId");
		try {
			article.setId(editId);
			articleService.update(article);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:getDataList";
	}

	// 删除文章
	@RequestMapping(value = "/article/del")
	public String deletePro(ModelMap model, Channel project) {
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
