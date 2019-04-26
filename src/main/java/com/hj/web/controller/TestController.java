package com.hj.web.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;

@Controller
public class TestController {

	public static void main(String[] args) {
		Locale defaultLocale = Locale.getDefault();
		System.out.println("地区：country=" + defaultLocale.getCountry());
		System.out.println("语言：language=" + defaultLocale.getLanguage());
	}

}
