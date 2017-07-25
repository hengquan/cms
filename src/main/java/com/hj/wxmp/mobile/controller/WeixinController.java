package com.hj.wxmp.mobile.controller;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hj.utils.HttpUtils;
import com.hj.utils.UUIDUtils;
import com.hj.web.core.mvc.ControllerBase;
import com.hj.wxmp.core.WxUser;
import com.hj.wxmp.mobile.common.ApiUrls;
import com.hj.wxmp.mobile.common.Configurations;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.common.Weixin;
import com.hj.wxmp.mobile.entity.UserInfo;
import com.hj.wxmp.mobile.services.WxLoginService;

/**
 * 
 * @ClassName: WeixinController
 * @Description: TODO(微信与腾讯相关的接口调用)
 * @author weilesi
 * @date 2015年2月16日 下午4:40:18
 *
 */
@Controller
public class WeixinController extends ControllerBase {

	private final static Logger logger = LoggerFactory.getLogger(WeixinController.class);
	
	private Weixin weixin = Weixin.getInstance();
	

	@Resource
	private WxLoginService wxLoginService;

	// ------------------- 1.获取openId--------------------------
	/**
	 * 
	 * @Title: getWeixinCode @Description:
	 * TODO(获取微信code方式不能用这种方式，必须在页面中reload或打开页面的方式) @param @param
	 * map @param @return 设定文件 @return String 返回类型 @author jun.hai @date
	 * 2015年2月16日 下午4:41:46 @throws
	 */
	@RequestMapping("/weixin/getWeixinCode.ky")
	public String getWeixinCode(ModelMap map) {
		try {
			// String appRoot = "http://"+request.getServerName()+":"
			// +request.getServerPort()+request.getContextPath();
			String appRoot = "http://" + request.getServerName() + request.getContextPath();
			String uri = appRoot + "/web/weixin/wxauth.do";
			logger.error("uri-----" + uri);
			uri = URLEncoder.encode(uri, "utf-8");

			// String params =
			// "appid="+Constants.WEIXIN_APPID+"&redirect_uri="+uri+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect";

			Map<String, String> params = new HashMap<String, String>();
			params.put("appid", Configurations.getAppId());
			params.put("redirect_uri", uri);
			params.put("response_type", "code");
			params.put("scope", "snsapi_base");
			params.put("state", "1#wechat_redirect");

			HttpUtils.connectPostHttps(ApiUrls.WEIXIN_CODE_URL, params);

			logger.error("getWeixinCode----");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "user/login";
	}

	@RequestMapping("/weixin/openid.ky")
	public String wxauth(ModelMap map, HttpServletRequest request) {
		String code = getTrimParameter("code");

		String json = "";
		try {
			String openId = HashSessions.getInstance().getOpenId(request);
			if (StringUtils.isBlank(openId)) {
				String paramsStr = "appid=" + Configurations.getAppId() + "&secret=" + Configurations.getAppSecret()
						+ "&code=" + code + "&grant_type=authorization_code";

				json = HttpUtils.sendByGet(ApiUrls.WEIXIN_OPENID_URL, paramsStr);

				Map<String, Map<String, Object>> jsonMap = json2Map(json);
				if (null != jsonMap) {
					openId = ObjectUtils.toString(jsonMap.get("openid"));
					if(StringUtils.isNotBlank(openId)){
						map.put("weixi_openId", openId);
						
						// request.getSession().setAttribute("openId",openId);
						HashSessions.getInstance().setOpenId(request, response, openId);
						WxUser wxUser = Weixin.getInstance().getUserInfo(openId);
						if(null!=wxUser){
							UserInfo  sysUser = wxLoginService.bandingUser(wxUser,ObjectUtils.toString(request.getSession().getAttribute("weixin_current_url")));
							if(null!=sysUser){
								HashSessions.getInstance().setSessionUserInfo(request, sysUser);
							}
						}
					}
				}
				
				String successUrl = request.getRequestURI();
				map.put("successUrl", successUrl);
			}
		} catch (Exception e) {
			logger.error("openId--error----" + e.toString());
		}

		String currentUri = ObjectUtils.toString(request.getSession().getAttribute("weixin_current_url"));
		logger.error("WeixinController——currentUri1----" + currentUri);
		/*if(StringUtils.isNotBlank(currentUri)){
			currentUri = currentUri.replaceAll(request.getContextPath(), "");
			if(currentUri.indexOf("/")>0)currentUri = "/" + currentUri;
		}*/
		
		logger.error("WeixinController——currentUri2----" + currentUri);
		
		//return "login/login";
		return "redirect:"+currentUri;
	}

	// ---------------------------2.调用js接口 -----------------------
	/**
	 * 
	* @Title: putJsSdkInfo
	* @Description: TODO(页面中调用微信端的js-sdk接口时所用)
	* @param  @param map
	* @param  @param url    设定文件
	* @return void    返回类型
	* @author jun.hai 
	* @date 2015年3月3日 下午9:07:15
	* @throws
	 */
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

	@RequestMapping("/weixin/qrcode")
	public String qrcode(ModelMap map) {
		String pageUrl = "weixin/qrcode";
		
		putJsSdkInfo(map,"/weixin/qrcode");
		
		return pageUrl;
	}
	
	//---------------------------3.创建红包-----------------------
//	private CloseableHttpClient checkCertificate() throws Exception{
//        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
//        FileInputStream instream = new FileInputStream(new File("H:/apiclient_cert.p12"));
//        try {
//            keyStore.load(instream, "1362088702".toCharArray());
//        } finally {
//            instream.close();
//        }
//        // Trust own CA and all self-signed certs
//        SSLContext sslcontext = SSLContexts.custom()
//                .loadKeyMaterial(keyStore, "1362088702".toCharArray())
//                .build();
//        // Allow TLSv1 protocol only
//        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
//                sslcontext,"TLSv1".split(" ") , null,
//                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
//        CloseableHttpClient httpclient = HttpClients.custom()
//                .setSSLSocketFactory(sslsf)
//                .build();
//        return httpclient;
//    }

	
	
}
