package com.hj.wxmp.mobile.common;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import com.hj.utils.UUIDUtils;
import com.hj.web.core.mvc.ControllerBase;

/**
 * 
 * @author jun.hai 
 * @Description: TODO(项目中微信的公共实现整理到此Controller中,直接继承该累即可) 
 * @date 2016年10月31日 上午9:56:31
 */
public class ControllerBaseWx extends ControllerBase {
	
	private final static Logger logger = LoggerFactory.getLogger(ControllerBaseWx.class);
	
	private Weixin weixin = Weixin.getInstance();
	
	/**
	 * 
	 * @author jun.hai 
	 * @Description: TODO(微信端分享页面时获取签名等操作) 
	 * @param @param map
	 * @return String    返回类型 
	 * @date 2016年10月31日 上午10:56:31
	 * @throws
	 */
	protected void initWxShareParams(Map<String,Object> map) {
		long timestamp = new Date().getTime();
		String nonceStr = UUIDUtils.getUUIDKey();
//		String currentPageUrl = request.getRequestURL().toString();
//		if(StringUtils.isNotBlank(request.getQueryString())){
//			currentPageUrl = request.getRequestURL() + "?" + request.getQueryString();
//		}
//		logger.error("currentPageUrl:"+currentPageUrl);

		String currentPageUrl = StringUtils.trimToEmpty(getTrimParameter("wx_url"));
		String signature = weixin.createSignature(nonceStr, weixin.getJsApiTicket(), timestamp, currentPageUrl);
		logger.error("currentPageUrlMsg{}",currentPageUrl);
//		model.addAttribute("appId", Configurations.getAppId());
//		model.addAttribute("timestamp", String.valueOf(timestamp));
//		model.addAttribute("nonceStr", nonceStr);
//		model.addAttribute("signature", signature);
		
		map.put("appId", Configurations.getAppId());
		map.put("timestamp", String.valueOf(timestamp));
		map.put("nonceStr", nonceStr);
		map.put("signature", signature);
		//logger.error("appId:"+Configurations.getAppId()+"--timestamp:"+timestamp+"--nonceStr:"+nonceStr+"--signature:"+signature);
	}
	
	
}
