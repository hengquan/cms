package com.hj.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Callable;

/**   
* @Title: SocketUtils.java
* @Description: TODO
* @author Wangzhiyong   
* @date 2016年8月3日 上午10:44:57
* @version V1.0   
*/
public class SocketUtils implements Callable<String>{
	
	private String ip;
	
	private int port;
	
	private byte[] args;
	
	public SocketUtils(String ip,int port,byte[] args){
		this.ip = ip;
		this.port = port;
		this.args = args;
	}
	
	@Override
	public String call() throws Exception {
		 Socket socket = null;  
		 String ret = "";
		 StringBuffer sb = new StringBuffer();
            try {  
                //创建一个流套接字并将其连接到指定主机上的指定端口号  
                socket = new Socket(ip, port);    
                //读取服务器端数据    
                DataInputStream input = new DataInputStream(socket.getInputStream());    
                //向服务器端发送数据    
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());    
                out.write(args);
//                boolean flag = true;
//                while(flag){
	                     byte[] result = new byte[10240];
	                	 int numberOfBytesRead = input.read(result, 0, result.length);
	                	 int datalen = result[29] + result[28] * 256 + result[27] * 256 * 256 - 28;
	                	 byte[] ReceiveData = new byte[datalen];
	                	 System.arraycopy(result, 30, ReceiveData, 0, datalen);
	                	 String str = new String(ReceiveData,"UTF-8");
	                	 sb.append(str+",\n");
                out.close();
                input.close();  
            } catch (Exception e) {  
                e.getMessage();   
            } finally {  
                if (socket != null) {  
                    try {  
                        socket.close();  
                    } catch (IOException e) {  
                        socket = null;   
                        e.getMessage();   
                    }  
                }  
            }
			return sb.toString();
	}
	
}

