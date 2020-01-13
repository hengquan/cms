package com.hj.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import net.sf.json.JSONObject;

/**   
* @Title: UsUtils.java
* @Package com.hj.utils
* @Description: TODO
* @author Wangzhiyong   
* @date 2016年10月12日 上午10:35:38
* @version V1.0   
*/
public class UsUtils {
	
		/**
		 * 返回json字符串
		 * @param arg
		 * @return
		 */
		public static String toJSON(String arg) {
			if(arg != null && arg.length() >0)
				return JSONObject.fromObject(arg).toString();
			else
				return new RuntimeException("参数为空 ============== 》》》").getMessage();
		}
		
		/**
	     * 设置JSON字符串返回
	     * 
	     * @param key
	     * @param value
	     * @return
	     */
	    public static String setJsonString(String key, String value) {

	        if (!stringisEmpty(key) && !stringisEmpty(value)) {
	            JSONObject goodsJson = new JSONObject();
	            goodsJson.put(key, value);
	            return goodsJson.toString();
	        }
	        return "";
	    }
		
		/**
		 * 判断字符串是否为空 空true 非空false
		 * @param str
		 * @return
		 */
		public static boolean stringisEmpty(String str){
			return StringUtils.isBlank(str);
		}
		
		public static String fromDateToString(Date date,String pattem){
			if(stringisEmpty(pattem)){
				pattem = "yyyy-MM-dd hh:mm:ss";
			}
			SimpleDateFormat sd = new SimpleDateFormat(pattem);
			return sd.format(date).toString();
		}
		
		public static Date fromStringToDate(String time,String pattem) throws ParseException{
			if(stringisEmpty(pattem)){
				pattem = "yyyy-MM-dd hh:mm:ss";
			}
			SimpleDateFormat sd = new SimpleDateFormat(pattem);
			return sd.parse(time);
		}
		
		/**
		 * 判断List是否为空   空true 非空false
		 * @param list
		 * @return
		 */
		public static <T> boolean listIsEmpty(List<T> list){
			if(list != null && list.size() > 0){
				return false;
			}else{
				return true;
			}
		}
		
		/**
		 * 判断String[]是否为空   空true 非空false
		 * @param list
		 * @return
		 */
		public static  boolean StringArrayIsEmpty(String[] strs){
			if(strs != null && strs.length > 0){
				return false;
			}else{
				return true;
			}
		}
		
		/**
		 * 将字符串数组转换为List集合
		 * @param strs
		 * @return
		 */
		public static List<String> fromStringToList(String[] strs){
			List<String> list = null;
			if(!StringArrayIsEmpty(strs)){
				list = Arrays.asList(strs);
			}
			return list;
		}
		
		/**
		 * 将字符串数组转换为字符串
		 * @param strs
		 * @return
		 */
		public static String stringArrayToString(String[] strs){
			boolean flag = StringArrayIsEmpty(strs);
			StringBuffer sb = new StringBuffer();
			String str = "";
			if(!flag){
				for (String s : strs) {
					sb.append("'" + s + "',");
				}
				str = sb.toString().substring(0, sb.length() - 1);
			}
			return str;
		}
		
		/**
		 * 获取Http连接的返回值
		 * @param link
		 * @return
		 * @throws Exception
		 */
		public static String UrlConnection(String link) throws Exception{
			URL url = new URL(link);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			 	connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.154 Safari/537.36 LBBROWSER");
			 	connection.setDoOutput(true);
			 	connection.setDoInput(true);
			 	connection.connect();
			    InputStreamReader isr = new InputStreamReader(connection.getInputStream(),"UTF-8");
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				StringBuffer sb = new StringBuffer();
				while((line=br.readLine()) != null){
					sb.append(line+"\n");
				}
				br.close();
				return sb.toString();
		}
		
		/**
	     * 一次性判断多个或单个对象为空。
	     * @param objects
	     * @author cai niao
	     * @return 只要有一个元素为Blank，则返回true
	     */
	    public static boolean ObjectisBlank(Object...objects){
	        Boolean result = false ;
	        for (Object object : objects) {
	            if(object == null || "".equals(object.toString().trim()) 
	                    || "null".equals(object.toString().trim())
	                    || "[null]".equals(object.toString().trim())
	                    || "[]".equals(object.toString().trim())){
	                result = true ; 
	                break ; 
	            }
	        }
	        return result ; 
	    }
	    
	    private static String getMyIP() throws IOException {  
	        InputStream ins = null;  
	        try {  
	            URL url = new URL("http://iframe.ip138.com/ic.asp");  
	            URLConnection con = url.openConnection();  
	            ins = con.getInputStream();  
	            InputStreamReader isReader = new InputStreamReader(ins, "GB2312");  
	            BufferedReader bReader = new BufferedReader(isReader);  
	            StringBuffer webContent = new StringBuffer();  
	            String str = null;  
	            while ((str = bReader.readLine()) != null) {  
	                webContent.append(str);  
	            }  
	            int start = webContent.indexOf("[") + 1;  
	            int end = webContent.indexOf("]");  
	            return webContent.substring(start, end);  
	        } finally {  
	            if (ins != null) {  
	                ins.close();  
	            }  
	        }  
	    }  

		public static void main(String[] args) throws Exception {
			getMyIP();

		}
		
}
