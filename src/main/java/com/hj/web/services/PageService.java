package com.hj.web.services;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component
public class PageService {
	private int nowPage = 1;

	private int pageSize = 10;

	public int getNowPage() {
		return nowPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	// 获取页面起始位置
	public void getPageLocation(PageService page, Map<String, Object> map) {
		Integer start = ((page.getNowPage() - 1) * pageSize);
		map.put("page", start);
		Integer thisPageSize = page.getPageSize();
		if (thisPageSize != null && thisPageSize != 0) {
			map.put("pageSize", thisPageSize);
		} else {
			map.put("pageSize", pageSize);
		}
	}

	// 获取数据总数量
	public void getPageData(int totalCount, ModelMap model, PageService page) {
		Integer residueCount = totalCount % pageSize;
		Integer totalPageNum = 0;
		if (residueCount == 0) {
			totalPageNum = totalCount / pageSize;
		} else {
			totalPageNum = (totalCount / pageSize) + 1;
		}
		model.put("nowPage", page.getNowPage());
		model.put("pageSize", page.getPageSize());
		model.put("totalPageNum", totalPageNum);
	}

	// 获取数据总数量
	public void getPageData(int totalCount, Map<String, Object> model, PageService page) {
		Integer residueCount = totalCount % pageSize;
		Integer totalPageNum = 0;
		if (residueCount == 0) {
			totalPageNum = totalCount / pageSize;
		} else {
			totalPageNum = (totalCount / pageSize) + 1;
		}
		model.put("nowPage", page.getNowPage());
		model.put("pageSize", page.getPageSize());
		model.put("totalPageNum", totalPageNum);
	}

}