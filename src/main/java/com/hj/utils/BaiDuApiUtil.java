package com.hj.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import net.sf.json.JSONObject;

/**   
* @Title: BaiDuApiUtil.java
* @Package com.hj.utils
* @Description: TODO
* @author Wangzhiyong   
* @date 2016年6月14日 上午9:56:33
* @version V1.0   
*/
public class BaiDuApiUtil {
	
	/**
	 * 根据ip地址查询省市
	 * @param ip
	 * @return 省市
	 */
	public static String request(String ip) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    String httpUrl = "http://apis.juhe.cn/ip/ip2addr?ip="+ip+"&key=ad83e5ce99c4a0e476a3ae38164995a3";
	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	       //connection.setRequestProperty("apikey",  "113fae85513d82159ccf0456bb4d4b42");
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
}
