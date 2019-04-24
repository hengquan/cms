package com.hj.utils;

import java.security.MessageDigest;

/**
 * 
* @ClassName: MD5Utils 
* @Description: TODO(MD5加密) 
* @author ml
* @date 2015年2月16日 下午3:02:03 
*
 */
public class MD5Utils
{
	/**
	 * 
	* <p>Title: </p> 
	* <p>Description: </p>
	 */
    public MD5Utils()
    {
    }

    /**
     * 
    * @Title: MD5 
    * @Description: TODO
    * @param @param s
    * @param @return  参数说明 
    * @return String    返回类型 
    * @throws
     */
    public static final String MD5(String s)
    {
    	try{
	        char hexDigits[] = {
	            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
	            'A', 'B', 'C', 'D', 'E', 'F'
	        };
	        char str[];
	        byte btInput[] = s.getBytes();
	        MessageDigest mdInst = MessageDigest.getInstance("MD5");
	        mdInst.update(btInput);
	        byte md[] = mdInst.digest();
	        int j = md.length;
	        str = new char[j * 2];
	        int k = 0;
	        for(int i = 0; i < j; i++)
	        {
	            byte byte0 = md[i];
	            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
	            str[k++] = hexDigits[byte0 & 0xf];
	        }
	
	        return new String(str);
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }

    public static void main(String args1[])
    {
    	System.out.println(MD5("123456"));
    }
}