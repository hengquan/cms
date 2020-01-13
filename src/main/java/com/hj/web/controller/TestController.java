package com.hj.web.controller;

import java.util.Locale;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.utils.SendUtil;

@Controller
public class TestController {

	@RequestMapping("/wx/sentUrl")
	@ResponseBody
	public String send() {
		for (int i = 0; i < 10; i++) {
			sentUrl();
		}
		return "OK";
	}

	public static void sentUrl() {
		Random r = new Random();
		// 发送 POST 请求
		String url = "http://member.zikao365.com/mobilewap/wap/activity/insertVote.shtm";
		String uuid = r.nextDouble() + "";
		String topicID = "14015";
		String platformSource = "1";
		String version = "4.0";
		String time = "2019-07-31 10:50:56";
		String ltime = "1564541446682";
		String ranNum = r.nextDouble() + "";
		String paramReslt = "uuid=" + uuid + "&" + "topicID=" + topicID + "&" + "platformSource=" + platformSource + "&"
				+ "version=" + version + "&" + "time=" + time + "&" + "ltime=" + ltime + "&" + "ranNum=" + ranNum;
		String sr = SendUtil.sendPost(url, paramReslt);
	}
}
