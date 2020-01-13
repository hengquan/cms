package com.hj.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**   
* @Title: SocketServer.java
* @Package com.hj.utils
* @Description: TODO
* @author Wangzhiyong   
* @date 2016年8月4日 上午9:57:34
* @version V1.0   
*/
public class SocketServer {
	public static final int PORT = 12345;//监听的端口号     
	String ip = "";
    public static void main(String[] args) {    
        SocketServer server = new SocketServer();    
        server.init();    
    }    
    
    @SuppressWarnings("resource")
	public void init() {    
        try {    
            ServerSocket serverSocket = new ServerSocket(PORT); 
            InetAddress ipAddr = serverSocket.getInetAddress();
             ip = ipAddr.getHostAddress();
            while (true) {    
                // 一旦有堵塞, 则表示服务器与客户端获得了连接    
                Socket client = serverSocket.accept();    
                // 处理这次连接    
                new HandlerThread(client);    
            }    
        } catch (Exception e) {    
            e.getMessage();    
        }    
    }    
    
    private class HandlerThread implements Runnable {    
        private Socket socket;    
        public HandlerThread(Socket client) {    
            socket = client;    
            new Thread(this).start();    
        }    
    
        public void run() {    
            try {    
                // 读取客户端数据    
                DataInputStream input = new DataInputStream(socket.getInputStream());  
                String clientInputStr = input.readUTF();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException  
                // 向客户端回复信息    
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());    
                // 发送键盘输入的一行    
                if(clientInputStr.equals("1")){
                	out.writeUTF("Hello World!"); 
                }else{
                	out.writeUTF("{\"result\":\"{\"code\":\"1000\",\"data\":\"good\"}\",\"status\":\"ok\"}");
                	
                	try {
            			Thread.sleep(5000);
            		} catch (InterruptedException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}
                	
                	out.writeUTF("{\"result\":\"{\"code\":\"1000\",\"data\":\"good\"}\",\"status\":\"ok\"}");
                }
                  
                out.close();    
                input.close();    
            } catch (Exception e) {    
                e.getMessage();    
            } finally {    
                if (socket != null) {    
                    try {    
                        socket.close();    
                    } catch (Exception e) {    
                        socket = null;    
                        e.getMessage();    
                    }    
                }    
            }   
        }    
    }    
}
