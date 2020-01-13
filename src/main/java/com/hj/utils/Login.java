package com.hj.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class Login {
	public static String transDate(Date d){
    	long ss = d.getTime()/1000;
		String str = Long.toHexString(ss);
		
		StringBuffer sb = new StringBuffer();
		
		int len = str.length();
		if(len%2!=0){
			str = "0"+str;
			len = str.length();
		}
		
		for (int i = 0; i < len; i=i+2) {
			sb.append(","+str.substring(i,i+2));
		}
		
		StringBuffer result = new StringBuffer();
		
		String[] arr = sb.toString().substring(1).split(",");
		for (int i = 0; i < 8-arr.length; i++) {
			result.append(",0");
		}
				
    	return result.toString().substring(1)+ sb.toString();
    }
	
	 //报头
    public static String msgHeader(String type,String dataLen,String toType){
    	String timeStr = transDate(new Date());
    	
    	String result = type+",87,0,1,"+toType+",0,0,81,"+timeStr+","+"0,0,0,0,0,0,0,0"+","+dataLen;
    	
    	return result;
    }
    
    public static String loginBody() throws UnknownHostException{
  	  InetAddress ia=null;
        try {
            ia=InetAddress.getLocalHost();
            String localip=ia.getHostAddress();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
  	String timeStr = transDate(new Date());
  	
  	String ip = "7b,75,6,d5";
  	
  	String result = timeStr + ","+ ip + ",87,0,1";
  	
  	return result;
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
    
    private static int parse(char c) {  
        if (c >= 'a')  
            return (c - 'a' + 10) & 0x0f;  
        if (c >= 'A')  
            return (c - 'A' + 10) & 0x0f;  
        return (c - '0') & 0x0f;  
    }  
    
	
	public static byte[] login() throws UnknownHostException{
		StringBuffer result = new StringBuffer();
		String data = msgHeader("81","0,0,0,2b","81")+","+loginBody();
		String[] datas = data.split(",");
		for (int i = 0; i < datas.length; i++) {
			if(datas[i].length()==1){
				result.append("0").append(datas[i]);
			}else{
				result.append(datas[i]);
			}
		}
//		
		//data = data.replaceAll(",","");
		byte[] aaa = HexString2Bytes(result.toString());
    	byte[] ab = new byte[aaa.length+4];
    	
    	ab[0] = 0x01;
    	ab[1] = 0x02;
    	
    	System.arraycopy(aaa, 0, ab, 2, aaa.length);
    	
    	
    	ab[ab.length-1] = 0x17;
    	ab[ab.length-2] = 0x03;
    	return ab;
	}
}

