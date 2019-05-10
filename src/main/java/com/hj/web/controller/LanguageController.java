package com.hj.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.common.ControllerBase;
import com.hj.utils.JsonUtils;
import com.hj.web.entity.Language;
import com.hj.web.services.LanguageService;

@Controller
public class LanguageController extends ControllerBase {

	@Autowired
	LanguageService languageService;

	// 获取所有语言
	@RequestMapping(value = "/language/getAllData")
	@ResponseBody
	public String getAllData() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Language> languageList = languageService.getAllData();
			if (languageList != null && languageList.size() > 0) {
				map.put("dataList", languageList);
				map.put("msg", "0");
			} else {
				map.put("msg", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}
}
