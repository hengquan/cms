package com.hj.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.hj.utils.UploadUtils;
import com.hj.web.services.FileService;

@Controller
public class FileController {
	
	@Autowired
	FileService fileService;

	// 上传附件--上传到本项目当中
	@RequestMapping(value = "/apply/uploadFile")
	@ResponseBody
	public Map<String, Object> uploadFile(HttpServletResponse response, HttpServletRequest request) throws Exception {
		Map<String, Object> retM = new HashMap<String, Object>();
		try {
			String filePath = fileService.getFilePath(request);
			// 获取文件并上传
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				MultipartFile imgFile = multiRequest.getFile("file");
				if (imgFile != null && imgFile.getSize() > 0) {
					Map<String, Object> mapData = UploadUtils.upload(imgFile, request, filePath);
					if (mapData != null) {
						String fileName = mapData.get("fileName") == null ? "" : mapData.get("fileName").toString();
						if (StringUtils.isNotEmpty(fileName))
							retM.put("fileName", fileName);
						filePath = mapData.get("filePath") == null ? "" : mapData.get("filePath").toString();
						if (StringUtils.isNotEmpty("filePath"))
							retM.put("filePath", filePath);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retM;
	}
}