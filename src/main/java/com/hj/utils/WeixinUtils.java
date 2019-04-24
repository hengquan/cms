package com.hj.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

import net.sf.json.JSONObject;

/**
 * 
 * @ClassName: WeixinUtils 
 * @author jun.hai
 * @date 2016年3月18日 上午11:09:35
 */
public class WeixinUtils {
	
	/**
	 * 
	* @author jun.hai
	* @date 2016年3月18日 上午11:11:27 
	* @Title: isWeixin 
	* @Description: TODO(判断是否通过微信浏览器访问) 
	* @param @param request
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public static boolean isWeixinAccess(HttpServletRequest request){
		String ua = request.getHeader("user-agent").toLowerCase();
	    if (ua.indexOf("micromessenger") > 0) {
	        return true;
	    }
	    
		return false;
	}
	
	/**
	 * 
	 * @author jun.hai 
	 * @Description: TODO(获得微信浏览器版本) 
	 * @param @param map
	 * @return String    返回类型 
	 * @date 2016年4月11日 下午3:18:18
	 * @throws
	 */
	public static String getWeixinVersion(HttpServletRequest request){
		String userAgent = request.getHeader("user-agent");
        char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger")+15);
		
        return String.valueOf(agent);
	}
	
	
	
	
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr,FileOutputStream output)
	  {
	    JSONObject jsonObject = null;
	    StringBuffer buffer = new StringBuffer();
	    try
	    {
	      TrustManager[] tm = { new MyX509TrustManager() };
	      SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
	      sslContext.init(null, tm, new SecureRandom());

	      SSLSocketFactory ssf = sslContext.getSocketFactory();

	      URL url = new URL(requestUrl);
	      HttpsURLConnection httpUrlConn = (HttpsURLConnection)url.openConnection();
	      httpUrlConn.setSSLSocketFactory(ssf);

	      httpUrlConn.setDoOutput(true);
	      httpUrlConn.setDoInput(true);
	      httpUrlConn.setUseCaches(false);

	      httpUrlConn.setRequestMethod(requestMethod);

	      if ("GET".equalsIgnoreCase(requestMethod)) {
	        httpUrlConn.connect();
	      }

	      if (outputStr != null) {
	        OutputStream outputStream = httpUrlConn.getOutputStream();
	        outputStream.write(outputStr.getBytes("UTF-8"));
	        outputStream.close();
	      }

	      InputStream inputStream = httpUrlConn.getInputStream();
	      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
	      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	      
	      if(output != null){
	    	  // 将输入流is写入文件输出流output中  
	    	  int ch = 0;
	    	  while((ch=inputStream.read()) != -1){
	    		  output.write(ch);
	    	  }
	    	  output.close();
	      }
	      
	      String str = null;
	      while ((str = bufferedReader.readLine()) != null) {
	        buffer.append(str);
	      }
	      
	      bufferedReader.close();
	      inputStreamReader.close();

	      inputStream.close();
	      inputStream = null;
	      
	      httpUrlConn.disconnect();
	      
	      jsonObject = JSONObject.fromObject(buffer.toString());
	    } catch (ConnectException ce) {
	    	
	    } catch (Exception e) {
	    	
	    }
	    return jsonObject;
	  }
	
	
}
