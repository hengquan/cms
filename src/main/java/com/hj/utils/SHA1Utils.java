package com.hj.utils;

import java.security.MessageDigest;

/**
 * 
* @ClassName: SHA1Utils
* @Description: TODO(SHA1工具类)
* @author jun.hai
* @date 2015年2月17日 下午2:11:06
*
 */
public class SHA1Utils
{

    public SHA1Utils(){
    }

    public static String encode(String sourceString) {  
        String resultString = null;  
        try {  
           resultString = new String(sourceString);  
           MessageDigest md = MessageDigest.getInstance("SHA-1");  
           resultString = byte2hexString(md.digest(resultString.getBytes()));  
        } catch (Exception ex) {  
        }  
        return resultString;  
    }  
    
    public static final String byte2hexString(byte[] bytes) {  
        StringBuffer buf = new StringBuffer(bytes.length * 2);  
        for (int i = 0; i < bytes.length; i++) {  
            if (((int) bytes[i] & 0xff) < 0x10) {  
                buf.append("0");  
            }  
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));  
        }  
        return buf.toString().toUpperCase();  
    }  
}