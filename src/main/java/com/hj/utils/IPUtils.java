package com.hj.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @file_name IPUtils.java
 * @author hj
 * @date 2016-04-1 21:30:00
 * @description IP相关的工具
 */ 
public class IPUtils {

	/**
	 * 
	 * @author jun.hai 
	 * @Description: TODO(java获取客户端访问的真实IP地址) 
	 * @param @param map
	 * @return String    返回类型 
	 * @date 2016年4月1日 下午9:32:50
	 * @throws
	 */
	public static String getClientIP(HttpServletRequest request){
	    //获取X-Forwarded-For
	    String ip = request.getHeader("x-forwarded-for"); 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        //获取Proxy-Client-IP
	        ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        //WL-Proxy-Client-IP
	        ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        //获取的IP实际上是代理服务器的地址，并不是客户端的IP地址
	        ip = request.getRemoteAddr(); 
	    }      
	    /*
	     * 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
	     * X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100
	     * 用户真实IP为： 192.168.1.110
	     */
	    if (ip.contains(",")){
	        ip = ip.split(",")[0];
	    }
	    return ip;
	}

}
