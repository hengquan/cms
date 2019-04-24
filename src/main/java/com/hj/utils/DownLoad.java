package com.hj.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


public class DownLoad {
	public static HttpServletResponse  download(String path, HttpServletResponse response) {
	    try {
	        // path是指欲下载的文件的路径。
	        File file = new File(path);
	        // 取得文件名。
	        String filename = file.getName();
	        // 取得文件的后缀名。
	        String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
	
	        // 以流的形式下载文件。
	        InputStream fis = new BufferedInputStream(new FileInputStream(path));
	        byte[] buffer = new byte[fis.available()];
	        fis.read(buffer);
	        fis.close();
	        // 清空response
	        response.reset();
	        // 设置response的Header
	        response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
	        response.addHeader("Content-Length", "" + file.length());
	        ServletOutputStream toClient = response.getOutputStream();
	        //OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
	        response.setContentType("application/octet-stream");
	        response.setContentType("application/force-download");
	        toClient.write(buffer);
	        toClient.flush();
	        toClient.close();
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	    return response;
	}
	
	
	
	
    public static void downloadLocal(HttpServletResponse response,String path) throws FileNotFoundException {
    	//TODO 以下为要完成代码
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型 
        response.setContentType("multipart/form-data");
        //2.设置文件头：最后一个参数是设置下载文件名 
        response.setHeader("Content-Disposition", "attachment;fileName="+"ceshi123789.xls");
        ServletOutputStream out;
        //通过文件路径获得File对象(假如此路径中有一个download.pdf文件)
        File file = new File(path);
        try {
            FileInputStream inputStream = new FileInputStream(file);
            //3.通过response获取ServletOutputStream对象(out)
            out = response.getOutputStream();
            int b = 0;
            byte[] buffer = new byte[1024];
            while (b != -1){
                b = inputStream.read(buffer);
                //4.写到输出流(out)中 
                out.write(buffer,0,b);
            }
            inputStream.close();
            out.close();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public static void download1(HttpServletResponse response,String path) throws FileNotFoundException {
    	try {
    		InputStream inputStream = new FileInputStream(path);
    		byte[] data = new byte[1024];
    		int len = 0;
    		FileOutputStream fileOutputStream = null;
			fileOutputStream = new FileOutputStream("D:\\aaaaaa.xls");
			while ((len = inputStream.read(data)) != -1) {
				fileOutputStream.write(data, 0, len);
			}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
}
