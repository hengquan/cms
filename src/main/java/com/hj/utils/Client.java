package com.hj.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {  
      
    private Socket socket=null;  
      
    public Client() throws IOException{  
          
            socket = new Socket("139.129.212.8", 10020);  
            
           boolean b = socket.isConnected();
            /* 
             * 创建Socket的同时就发起连接，若连接异常会抛出异常。 
             *  我们通常创建Socket时会传入服务端的地址以及服务端口号。 
             *  1，服务器IP地址 
             *  2，计算机服务端口 
             */  
      
    }  
    public void start(byte[] b) throws IOException{  
              
          
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());    
            out.write(b);
            out.flush(); 
            try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            GetMessageHandler ghHandler = new GetMessageHandler();  
            Thread thread = new Thread(ghHandler);  
            thread.start();
    }  
      
    public static void main(String[] args) throws Exception {  
    	byte[] b = Login.login();
        Client client = new Client();  
        client.start(b);  
        byte[] b1 = SendSocket.returnData();
        client.start(b1);  
    }  
      
    class GetMessageHandler implements Runnable{
        @Override  
        public void run() {  
        	
        	DataInputStream input = null;
			try {
				input = new DataInputStream(socket.getInputStream());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}    
        	while(true){
            try {  
            	StringBuffer sbf = new StringBuffer();
            	 byte[] result = new byte[10240];
            	 int numberOfBytesRead = 0;
            	 numberOfBytesRead = input.read(result, 0, result.length);
            	  if (numberOfBytesRead > 0){
                      if (numberOfBytesRead > 30)
                      {
                          int datalen = FormatTransfer.toInt(result[29]) + result[28] * 256 + result[27] * 256 * 256 - 28;
                          if (result[2] == 0x8c)   //回报进度
                          {
                              byte[] ReceiveData = new byte[datalen];
                              System.arraycopy(result, 30, ReceiveData, 0, datalen);
                              String str = new String(ReceiveData,"UTF-8");
                              sbf.append(str);
                          }
                          else if (result[2] == 0x8d)   //回报统计结果
                          {
                              byte[] ReceiveData = new byte[datalen];
                              System.arraycopy(result, 30, ReceiveData, 0, datalen);
                              String str = new String(ReceiveData,"UTF-8");
                              sbf.append(str);
                          }
                          else if (result[2] == 0x8f)   //回报在线结果
                          {
                              byte[] ReceiveData = new byte[datalen];
                              System.arraycopy(result, 30, ReceiveData, 0, datalen);
                              String str = new String(ReceiveData,"UTF-8");
                              sbf.append(str);
                          }else{
                        	  byte[] ReceiveData = new byte[datalen];
                              System.arraycopy(result, 30, ReceiveData, 0, datalen);
                              String str = new String(ReceiveData,"UTF-8");
                              sbf.append(str);
                          }
                      }
            	  }   	 
            }catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
            
            try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	}
        }
    }       
}  