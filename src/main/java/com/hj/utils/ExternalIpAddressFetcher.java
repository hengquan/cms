package com.hj.utils;

public class ExternalIpAddressFetcher {
    
    public static void main(String[] args){
		
		StringBuffer buf = new StringBuffer();
		
		String ss = "421";
		char[]chars=ss.toCharArray(); //把字符中转换为字符数组 

	    for(int i=0;i<chars.length;i++){//输出结果
	    	int sascii = (int)chars[i];
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
	    StringBuffer sb = new StringBuffer();
	    String length = Integer.toHexString(421);
	    while(length.length() < 8){
	    	length = "0"+length;
	    }
	    for (int z = 0; z < length.length(); z = z+2) {
        	if(z <= length.length()){
        		sb.append(length.substring(z,z+2)+","); // 0,1  2,3 4,5
        	}
		}
    }
}
