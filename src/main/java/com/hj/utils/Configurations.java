package com.hj.utils;

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
	static final String CONFIG_FILE = "aplication.properties";

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

	public static String getFileRepository() {
		String val = getConfig("STREAM_FILE_REPOSITORY");
		if ((val == null) || (val.isEmpty()))
			val = REPOSITORY;
		return val;
	}

	public static String getPassWord() {
		return getConfig("PASS_WORD");
	}

	public static String getAccessUrl() {
		return getConfig("ACCESSURL");
	}

}
