package com.hj.wxmp.mobile.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @ClassName: Configurations
 * @Description: TODO(读取系统配置信息)
 * @author weilesi
 * @date 2015年2月17日 上午9:42:32
 *
 */
public class Configurations {
	static final String CONFIG_FILE = "wx.app.properties";

	private static Properties properties = null;

	private static final String REPOSITORY = System.getProperty("java.io.tmpdir",
			File.separator + "tmp" + File.separator + "upload-repository");

	static {
		new Configurations();
	}

	private Configurations() {
		init();
	}

	void init() {
		try {
			ClassLoader loader = Configurations.class.getClassLoader();
			InputStream in = loader.getResourceAsStream(CONFIG_FILE);
			properties = new Properties();
			properties.load(in);
		} catch (IOException e) {
			System.err.println("reading `stream-config.properties` error!" + e);
		}
	}

	public static String getConfig(String key) {
		return getConfig(key, null);
	}

	public static String getConfig(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	public static int getConfig(String key, int defaultValue) {
		String val = getConfig(key);
		int setting = 0;
		try {
			setting = Integer.parseInt(val);
		} catch (NumberFormatException e) {
			setting = defaultValue;
		}
		return setting;
	}

	public static boolean getBoolean(String key) {
		return Boolean.parseBoolean(getConfig(key));
	}

	// ------获取具体值-------
	public static String getToken() {
		return getConfig("token");
	}
	
	public static String getSPlatformYield() {
		return getConfig("static_platform_yield");
	}

	public static String getAppId() {
		return getConfig("appId");
	}

	public static String getAppSecret() {
		return getConfig("appSecret");
	}

	public static String getOpenIdRedirectUri() {
		return getConfig("wx.openid_redirect_uri");
	}

	public static String getAppTitle() {
		return getConfig("app.title");
	}

	public static String getFileRepository() {
		String val = getConfig("STREAM_FILE_REPOSITORY");
		if ((val == null) || (val.isEmpty()))
			val = REPOSITORY;
		return val;
	}

	public static boolean isDeleteFinished() {
		return getBoolean("STREAM_DELETE_FINISH");
	}

	public static String getAccessUrl() {
		return getConfig("ACCESSURL");
	}

	public static String getQrcodeUrl() {
		return getConfig("WX_QRCODE_URL");
	}

	// 微信公众号－－微信支付分配的商户ID
	public static String getMchId() {
		return getConfig("mch_id");
	}

	public static String getNotifyUrl() {
		return getConfig("notify_url");
	}

	public static String getPayKey() {
		return getConfig("pay_key");
	}

	public static boolean isCallApi() {
		if("1".equals(getConfig("call_api_flag"))){
			return true;
		}
			
		return false;
	}
	
	public static String getCdnSite() {
		return getConfig("cdnSite");
	}
	
	public static String getHotLine() {
		return getConfig("hotline");
	}
	
	public static String getSVersion() {
		//return getConfig("static_version");
		return String.valueOf(Math.random());
	}
}
