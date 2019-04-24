package com.hj.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import net.sf.json.JSONObject;

/**
 * 
* @ClassName: ThreadPoolUtil
* @Description: TODO(获取socket接口数据的连接池)
* @author Wangzhiyong
* @date 2016年8月22日 下午2:51:59
*
 */
public class ThreadPoolUtil {
	
	/**
	 * @param ip
	 * @param port
	 * @param args
	 * @return
	 */
	public static String newThreadPool(String ip,int port,byte[] args){
		//实现callable的套接字
		SocketUtils su = new SocketUtils(ip,port,args);
		ExecutorService es = null;
		String str = "";
		try {
			//创建初始化为10的线程池
			es = Executors.newCachedThreadPool();
			FutureTask<String> fs = new FutureTask<String>(su);
			if(!fs.isDone()){
				es.submit(fs); //执行该线程
				str =fs.get(); //获取Socket返回值
			}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} finally {	
				es.shutdown();
			}
		return str;
	}
	
    public static String stringToHexString(String s) {  
        String str = "";  
        for (int i = 0; i < s.length(); i++) {  
            int ch = (int) s.charAt(i);  
            String s4 = Integer.toHexString(ch);  
            str = str + s4;  
        }  
        return str;  
    }
    
    private final static byte[] hex = "0123456789ABCDEF".getBytes();  
    
    private static int parse(char c) {  
        if (c >= 'a')  
            return (c - 'a' + 10) & 0x0f;  
        if (c >= 'A')  
            return (c - 'A' + 10) & 0x0f;  
        return (c - '0') & 0x0f;  
    }  
    // 从字节数组到十六进制字符串转换  
    public static String Bytes2HexString(byte[] b) {  
        byte[] buff = new byte[2 * b.length];  
        for (int i = 0; i < b.length; i++) {  
            buff[2 * i] = hex[(b[i] >> 4) & 0x0f];  
            buff[2 * i + 1] = hex[b[i] & 0x0f];  
        }  
        return new String(buff);  
    }  
      
    // 从十六进制字符串到字节数组转换  
    public static byte[] HexString2Bytes(String hexstr) {  
        byte[] b = new byte[hexstr.length() / 2];  
        int j = 0;  
        for (int i = 0; i < b.length; i++) { 
		            char c0 = hexstr.charAt(j++);  
		            char c1 = hexstr.charAt(j++);  
		            b[i] = (byte) ((parse(c0) << 4) | parse(c1));  
        }  
        return b;  
    } 
}

