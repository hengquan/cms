package com.hj.web.services;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.hj.utils.Configurations;

@Component
public class FileService {
	// 获取项目目录
	public String getFilePath(HttpServletRequest request) {
		// 获取根目录路径
		String rootPath = request.getSession().getServletContext().getRealPath("");
		String savePath = Configurations.getFileRepository();
		String filePath = rootPath + savePath;
		File file = new File(rootPath + savePath);
		if (!file.exists() && !file.isDirectory()) {// 目录不存在，需要创建
			file.mkdirs();
		}
		return filePath;
	}
}
