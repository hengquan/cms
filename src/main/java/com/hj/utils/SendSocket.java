package com.hj.utils;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**   
* @Title: SendSocket.java
* @Package com.hj.wxmp.test
* @Description: TODO
* @author Wangzhiyong   
* @date 2016年10月13日 上午9:46:12
* @version V1.0   
*/
public class SendSocket {
	
	 /**将给定的用十进制分段格式表示的ipv4地址字符串转换成字节数组*/
	 public static byte[] ipv4Address2BinaryArray(String ipAdd){
	  byte[] binIP = new byte[4];
	  String[] strs = ipAdd.split("\\.");
	  for(int i=0;i<strs.length;i++){
	   binIP[i] = (byte) Integer.parseInt(strs[i]);
	  }
	  return binIP;
	 }
	 private static String hexStr =  "0123456789ABCDEF";
	 /** 
	     *  
	     * @param bytes 
	     * @return 将二进制转换为十六进制字符输出 
	     */  
	    public static String BinaryToHexString(byte[] bytes){  
	          
	        String result = "";  
	        String hex = "";  
	        for(int i=0;i<bytes.length;i++){  
	            //字节高4位  
	            hex = String.valueOf(hexStr.charAt((bytes[i]&0xF0)>>4));  
	            //字节低4位  
	            hex += String.valueOf(hexStr.charAt(bytes[i]&0x0F));  
	            result +=hex+" ";  
	        }  
	        return result;  
	    }  
	
	    
	    
	    /**
	     *  0000    0
			0001    1
			0010    2
			0011    3
			0100    4
			0101    5
			0110    6
			0111    7
			1000    8
			1001    9
			1010    A
			1011    B
			1100    C
			1101    D
			1110    E
			1111    F
	     * @param args
	     */
	    
	    public static String get16Hex(String flag){
	    	String[] strs = new String[]{"0000","0001","0010","0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1101","1110","1111"};
	    	String[] num = new String[]{"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
	    	String arr = "";
	    	for(int i = 0; i < strs.length ; i++){
	    		if(strs[i].equals(flag)){
	    			arr = num[i];
	    			break;
	    		}
	    	}
	    	
	    	return arr;
	    }
	 
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
	    
	    public static String msgFirst(){
	    	return "1,2";
	    }
	    //报头
	    public static String msgHeader(String type,String dataLen,String toType){
	    	String timeStr = transDate(new Date());
	    	
	    	String result = type+",87,0,1,"+toType+",0,0,81,"+timeStr+","+"0,0,0,0,0,0,0,0"+","+dataLen;
	    	
	    	return result;
	    }
	    
	    //报体 ...
	    
	    public static String msgLast(){
	    	return "3,17";
	    }
	    
	    //报体 (1.注册)
	    //0,0,0,0,57,ff,21,b9,7f,0,0,1,87,0,1
	    public static String loginBody() throws UnknownHostException{
	    	  InetAddress ia=null;
	          try {
	              ia=ia.getLocalHost();
	              String localip=ia.getHostAddress();
	              System.out.println("本机的ip是 ："+localip);
	          } catch (Exception e) {
	              // TODO Auto-generated catch block
	              e.printStackTrace();
	          }
	    	String timeStr = transDate(new Date());
	    	
	    	String ip = "123.113.241.173"; //123.113.244.209
	    	ip = "7b,77,43,5a";
	    	
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
	    
	public static byte[] returnData() throws UnsupportedEncodingException{
		
		//byte[] b= "1".getBytes(); //Login.login();
		//ScoketTest.getData(b);
//		
		
		//String dataByte = ThreadPoolUtil.newThreadPool("127.0.0.1", 12345, b);
		
		//ScoketTest.getData("1");
		//System.out.println("dataByte111"+dataByte);
//		String json = "{\"MARKET\":\"SH\",\"CODE\":\"600600\",\"FUNC_ID\":\"Func_YDM_SMA\","
//				+ "\"PARADETAILS\":[{\"ID\":1,\"From\":1,\"To\":10,\"Step\":2,\"value\":0},"
//				+ "{\"ID\":2,\"From\":1,\"To\":10,\"Step\":2,\"value\":0},"
//				+ "{\"ID\":3,\"From\":1,\"To\":10,\"Step\":2,\"value\":0}],"
//				+ "\"PARACOUNT\":3,\"TASKID\":\"c6d33034-5c78-4bee-a2e8-ce99a4e1975c\","
//				+ "\"SUMTASKCOUNT\":1,\"ZY\":{\"RiskTYPE\":1,\"VALUE\":0.59},\"ZS\":{\"RiskTYPE\":0,\"VALUE\":0},"
//				+ "\"STARTDATE\":\"20000101\",\"ENDDATE\":\"20150101\"}";
		String json = "{\"CODE\":\"600600\",\"ENDDATE\":\"20150101\","
				+ "\"FUNC_ID\":\"Func_YDM_SMA\",\"MARKET\":\"SH\",\"PARACOUNT\":3,"
				+ "\"PARADETAILS\":[{\"From\":1,\"ID\":1,\"Step\":2,\"To\":10,\"value\":0},"
				+ "{\"From\":1,\"ID\":2,\"Step\":2,\"To\":10,\"value\":0},"
				+ "{\"From\":1,\"ID\":3,\"Step\":2,\"To\":10,\"value\":0}],"
				+ "\"STARTDATE\":\"20000101\",\"SUMTASKCOUNT\":1,"
				+ "\"TASKID\":\"c6d33034-5c78-4bee-a2e8-ce99a4e1975c\","
				+ "\"ZS\":{\"RiskTYPE\":0,\"VALUE\":0},\"ZY\":{\"RiskTYPE\":1,\"VALUE\":0.59}}";
	//	String s1 = stringToHexString(json);
		
		//String json = "{\"BESTPARA\":{\"CODE\":\"600600\",\"FUNC_ID\":\"Func_YDM_SMA\",\"MARKET\":\"SH\",\"PARADETAILS\":[{\"From\":0,\"ID\":1,\"Step\":0,\"To\":0,\"value\":1},{\"From\":0,\"ID\":2,\"Step\":0,\"To\":0,\"value\":1},{\"From\":0,\"ID\":3,\"Step\":0,\"To\":0,\"value\":1}],\"SUMTASKCOUNT\":1,\"ZS\":{\"RiskTYPE\":0,\"VALUE\":0},\"ZY\":{\"RiskTYPE\":1,\"VALUE\":0.59}},\"TASKID\":\"9b836bcc-01c0-49a5-a99b-d56d6a506812\"}";
		
		StringBuffer sbf = new StringBuffer();
		String dataMain = msgHeader("8a","0,0,1,a5","88");//a5
		String[] ds = dataMain.split(",");
		for (int i = 0; i < ds.length; i++) {
			if(ds[i].length() == 1){
				sbf.append("0").append(ds[i]);
			}else{
				sbf.append(ds[i]);
			}
		}
			System.out.print(sbf+getJsonTohexData(json).replace(",", ""));
		
		System.out.println(new String(HexString2Bytes(getJsonTohexData(json).replace(",", "")),"UTF-8"));
		
		byte[] aaa1 = HexString2Bytes(sbf+getJsonTohexData(json).replace(",", ""));
		//byte[] aaa1 = HexString2Bytes("8a,87,0,1,88,0,0,81,0,0,0,0,58,1a,b7,71,0,0,0,0,0,0,0,0,0,0,1,a5,7b,22,43,4f,44,45,22,3a,22,36,30,30,36,30,30,22,2c,22,45,4e,44,44,41,54,45,22,3a,22,32,30,31,35,30,31,30,31,22,2c,22,46,55,4e,43,5f,49,44,22,3a,22,46,75,6e,63,5f,59,44,4d,5f,53,4d,41,22,2c,22,4d,41,52,4b,45,54,22,3a,22,53,48,22,2c,22,50,41,52,41,43,4f,55,4e,54,22,3a,33,2c,22,50,41,52,41,44,45,54,41,49,4c,53,22,3a,5b,7b,22,46,72,6f,6d,22,3a,31,2c,22,49,44,22,3a,31,2c,22,53,74,65,70,22,3a,32,2c,22,54,6f,22,3a,31,30,2c,22,76,61,6c,75,65,22,3a,30,7d,2c,7b,22,46,72,6f,6d,22,3a,31,2c,22,49,44,22,3a,32,2c,22,53,74,65,70,22,3a,32,2c,22,54,6f,22,3a,31,30,2c,22,76,61,6c,75,65,22,3a,30,7d,2c,7b,22,46,72,6f,6d,22,3a,31,2c,22,49,44,22,3a,33,2c,22,53,74,65,70,22,3a,32,2c,22,54,6f,22,3a,31,30,2c,22,76,61,6c,75,65,22,3a,30,7d,5d,2c,22,53,54,41,52,54,44,41,54,45,22,3a,22,32,30,30,30,30,31,30,31,22,2c,22,53,55,4d,54,41,53,4b,43,4f,55,4e,54,22,3a,31,2c,22,54,41,53,4b,49,44,22,3a,22,63,36,64,33,33,30,33,34,2d,35,63,37,38,2d,34,62,65,65,2d,61,32,65,38,2d,63,65,39,39,61,34,65,31,39,37,35,63,22,2c,22,5a,53,22,3a,7b,22,52,69,73,6b,54,59,50,45,22,3a,30,2c,22,56,41,4c,55,45,22,3a,30,7d,2c,22,5a,59,22,3a,7b,22,52,69,73,6b,54,59,50,45,22,3a,31,2c,22,56,41,4c,55,45,22,3a,30,2e,35,39,7d,7d");
    	byte[] ab1 = new byte[aaa1.length+4];
    	
    	ab1[0] = 0x01;
    	ab1[1] = 0x02;
    	System.arraycopy(aaa1, 0, ab1, 2, aaa1.length);
    	ab1[ab1.length-1] = 0x17;
    	ab1[ab1.length-2] = 0x03;
    	
    	
    	
    	
    	return ab1;
//    	TestSocket ts1 = new TestSocket();
//    	String dataByte = ts1.client("139.129.212.8", 10020, ab1);
//    	System.out.println("dataByte:"+dataByte);
    	//ScoketTest.getData(ab1);
//    	String dataByte1 = ThreadPoolUtil.newThreadPool("139.129.212.8", 10020, ab1);
//    	System.out.println("dataByte:"+dataByte1);
    	
//    	String dataByte321= ThreadPoolUtil.newThreadPool("127.0.0.1", 12345, ab1);
//    	System.out.println("dataByte321"+dataByte321);
//    	
	}
	
	public static String getJsonTohexData(String data){
			StringBuffer buf = new StringBuffer();
		
		String ss = data;
		char[]chars=ss.toCharArray(); //把字符中转换为字符数组 

	    for(int i=0;i<chars.length;i++){//输出结果
	    	int sascii = (int)chars[i];
	     //   System.out.println(" "+chars[i]+":"+sascii);
	        
	        String hex = Integer.toHexString(sascii);
	        if(hex.length()%2!=0){
	        	hex = "0"+hex;
	        }
	        
	        for (int j = 0; j < hex.length(); j = j+2) {
	        	if(j <= hex.length()){
	        	 buf.append(hex.substring(j,j+2)+","); // 0,1  2,3 4,5
	        	}
			}
	        //"0123"-- 01,23
	    }
	  //  System.out.println(buf.toString().substring(0,buf.length()-1));
		return buf.toString().substring(0,buf.length()-1);
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
	
	public static  String transferLongToDate(String dateFormat,Long millSec){
	     SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	    Date date= new Date(millSec);
	            return sdf.format(date);
	    }
}
