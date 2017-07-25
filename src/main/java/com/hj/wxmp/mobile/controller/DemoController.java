package com.hj.wxmp.mobile.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.utils.HttpUtils;
import com.hj.utils.UUIDUtils;
import com.hj.web.core.mvc.ControllerBase;
import com.hj.wxmp.mobile.common.Configurations;
import com.hj.wxmp.mobile.common.Weixin;

/**
 * 
* @ClassName: DemoController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jun.hai
* @date 2015年1月9日 下午8:21:12
*
 */
@Controller
public class DemoController extends ControllerBase {
	
	private final static Logger logger = LoggerFactory.getLogger(DemoController.class);
	
	private Weixin weixin = Weixin.getInstance();
	
    @ResponseBody
	@RequestMapping("/sliderList")
    public String getSliderList(){
		try {
			List<Map<String,String>> sliderList = new ArrayList<Map<String,String>>();
			
			for (int i = 0; i < 5; i++) {
				Map<String,String> map = new LinkedHashMap<String,String>();
				map.put("imgUrl","img"+i);
				map.put("linkUrl","http://wxmp.anzeen.com:80/wxadmin/upload/2015-02-27_B1285159804_305400.jpg"+i);
				
				sliderList.add(map);
			}
			
			//Map<String,Object> result = MsgUtils.getResultMsg("", "", "sliderList",sliderList);
			
			//String json = JsonUtils.map2json(result);
			
			String json = toJson(sliderList);
			
			System.out.println("json:"+json);
			
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("");
			return null;
		}
	}
    
	@RequestMapping("/demo/home.ky")
	public String home(ModelMap map) {
		String memberId = getTrimParameter("memberId");
		map.put("memberId", memberId);
		
		
		return "demo/home";
	}
	
	@RequestMapping("/demo/widget.ky")
	public String widget(ModelMap map) {
		
		return "demo/widget";
	}
	
	@RequestMapping("/demo/widget.basic.ky")
	public String widgetBasic(ModelMap map) {
		return "demo/widget.basic";
	}
	
	@RequestMapping("/demo/landing.ky")
	public String landing(ModelMap map) {
		return "demo/landing";
	}
	
	@RequestMapping("/demo/sidebar.ky")
	public String sidebar(ModelMap map) {
		return "demo/sidebar";
	}
	
	@RequestMapping("/demo/adminIndex.ky")
	public String adminIndex(ModelMap map) {
		return "demo/admin-index";
	}
	
	@RequestMapping("/demo/jssdk.ky")
	public String jssdk(ModelMap map) {
		putJsSdkInfo(map,"/demo/jssdk.ky");
		
		return "demo/wxjsjdk_demo";
	}
	
	@RequestMapping("/demo/temp.ky")
	public String temp(ModelMap map) {
		return "demo/temp";
	}
	
	@RequestMapping("/demo/mallList.ky")
	public String mallList(ModelMap map) {
		return "activity/mallList";
	}
	
	@RequestMapping("/demo/receiptCheck")
	public String receiptCheck(ModelMap map) {
		return "activity/receiptCheck";
	}
	
	private void putJsSdkInfo(ModelMap map,String url){
		long timestamp =  new Date().getTime();
		String nonceStr = UUIDUtils.getUUIDKey();
		String currentPageUrl = HttpUtils.getAppRoot(request)+url;
		
		String signature = weixin.createSignature(nonceStr, weixin.getJsApiTicket(), timestamp, currentPageUrl);
		
		map.put("appId", Configurations.getAppId());
		map.put("timestamp", String.valueOf(timestamp));
		map.put("nonceStr", nonceStr);
		map.put("signature", signature);
	}
	
}
