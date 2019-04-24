package com.hj.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TreeController {

	@RequestMapping("/getTree")
	public String index(ModelMap map) {
		String pageUrl = "inc/zTree";
		return pageUrl;
	}
}
