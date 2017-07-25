package com.hj.wxmp.mobile.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.exception.StreamException;
import com.hj.utils.DateTimeUtil;
import com.hj.utils.Range;
import com.hj.web.core.common.Constants;
import com.hj.wxmp.mobile.common.Configurations;
import com.hj.wxmp.mobile.common.IoUtil;
import com.hj.wxmp.mobile.common.TokenUtil;

@Controller
public class AyasUploadController {
	//private final Logger logger = Logger.getLogger(AyasUploadController.class);

	@RequestMapping(value="/tk.do",method = RequestMethod.GET)
	public void streamTk(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter(Constants.FILE_NAME_FIELD);
		String size = request.getParameter(Constants.FILE_SIZE_FIELD);
		String path = request.getParameter(Constants.FILE_PATH);
		String token = null;
		try {
			token = TokenUtil.generateToken(path ,name, size);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constants.TOKEN_FIELD, token);
		if (Constants.STREAM_ISCROSS)
			result.put(Constants.SERVER_FIELD, Constants.STREAM_CROSS_SERVER);
		result.put(Constants.SUCCESS, true);
		result.put(Constants.MESSAGE, "");
		PrintWriter writer = response.getWriter();
		writer.write(objectMapper.writeValueAsString(result));
	}

	@RequestMapping(value="/stream/upload.do",method= RequestMethod.GET)
	public void getFilePosition(HttpServletRequest request,HttpServletResponse response)throws IOException {
		doOptions(request, response);
		final String token = request.getParameter(Constants.TOKEN_FIELD);
		final String size = request.getParameter(Constants.FILE_SIZE_FIELD);
		final String path = request.getParameter(Constants.FILE_PATH);
		String fileName = request.getParameter(Constants.FILE_NAME_FIELD);
		final PrintWriter writer = response.getWriter();
		/** TODO: validate your token. */
		long start = 0;
		boolean success = true;
		String message = "";
		try {
			File f = IoUtil.getTokenedFile(path,token);
			start = f.length();
			/** file size is 0 bytes. */
			if (token.endsWith("_0") && "0".equals(size) && 0 == start)
				f.renameTo(IoUtil.getFile(path,fileName));
		} catch (FileNotFoundException fne) {
			message = "Error: " + fne.getMessage();
			success = false;
		} finally {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String,Object> result = new HashMap<String,Object>();
			if (success)
				result.put(Constants.START_FIELD, start);
			result.put(Constants.SUCCESS, success);
			result.put(Constants.MESSAGE, message);
			fileName = DateTimeUtil.getCurrentDateFormat()+"_"+token+fileName.substring(fileName.lastIndexOf("."));
			result.put("fileName", fileName);
			result.put("imgUrl",getImgUrl(request, fileName));
			writer.write(objectMapper.writeValueAsString(result));
			IoUtil.close(writer);
		}
	}

	@RequestMapping(value="/stream/upload.do",method= RequestMethod.POST)
	protected void doStreamUpload(HttpServletRequest request,HttpServletResponse response) throws IOException{
		doOptions(request, response);
		final String token = request.getParameter(Constants.TOKEN_FIELD);
		final String path = request.getParameter(Constants.FILE_PATH);
		String fileName = request.getParameter(Constants.FILE_NAME_FIELD);
		
		Range range = IoUtil.parseRange(request);

		OutputStream out = null;
		InputStream content = null;
		final PrintWriter writer = response.getWriter();
		/** TODO: validate your token. */
		long start = 0;
		boolean success = true;
		String message = "";
		File f = IoUtil.getTokenedFile(path,token);
		try {
			if (f.length() != range.getFrom()) {
				/** drop this uploaded data */
				throw new StreamException(StreamException.ERROR_FILE_RANGE_START);
			}
			out = new FileOutputStream(f, true);
			content = request.getInputStream();
			int read = 0;
			final byte[] bytes = new byte[Constants.BUFFER_READ_LENGTH];
			while ((read = content.read(bytes)) != -1)
				out.write(bytes, 0, read);

			start = f.length();
		} catch (StreamException se) {
			success = StreamException.ERROR_FILE_RANGE_START == se.getCode();
			message = "Code: " + se.getCode();
		} catch (FileNotFoundException fne) {
			message = "Code: " + StreamException.ERROR_FILE_NOT_EXIST;
			success = false;
		} catch (IOException io) {
			message = "IO Error: " + io.getMessage();
			success = false;
		} finally {
			IoUtil.close(out);
			IoUtil.close(content);
			
			/** rename the file */
			if (range.getSize() == start){
				fileName = DateTimeUtil.getCurrentDateFormat()+"_"+token+fileName.substring(fileName.lastIndexOf("."));
		        File dst = IoUtil.getNewFile(path,fileName);
		        //dst.delete();
		        f.renameTo(dst);
	
		        if (Configurations.isDeleteFinished())
		          dst.delete();
		    }
			
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String,Object> result = new HashMap<String,Object>();
			if (success)
				result.put(Constants.START_FIELD, start);
			result.put(Constants.SUCCESS, success);
			result.put(Constants.MESSAGE, message);
			result.put("imgUrl",getImgUrl(request, fileName));
			writer.write(objectMapper.writeValueAsString(result));
			IoUtil.close(writer);
		}
	}

	@RequestMapping(value="/form/upload.do",method= RequestMethod.POST)
	protected void doFormUpload(HttpServletRequest reqeust, HttpServletResponse response)throws ServletException, IOException {
		doOptions(reqeust, response);
		/** flash @ windows bug */
		reqeust.setCharacterEncoding("utf8");
		final PrintWriter writer = response.getWriter();
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(reqeust);
		if (!isMultipart) {
			writer.println("ERROR: It's not Multipart form.");
			return;
		}
		long start = 0;
		boolean success = true;
		String message = "";
		ServletFileUpload upload = new ServletFileUpload();
		InputStream in = null;
		String token = null;
		String path = null;
		try {
			FileItemIterator iter = upload.getItemIterator(reqeust);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				String name = item.getFieldName();
				in = item.openStream();
				if (item.isFormField()) {
					String value = Streams.asString(in);
					if (Constants.TOKEN_FIELD.equals(name)) {
						token = value;
						/** TODO: validate your token. */
					}
					if (Constants.FILE_PATH.equals(name)){
						path = value;
					}
				} else {
					String fileName = item.getName();
					start = IoUtil.streaming(in,path,token,fileName);
				}
			}
		} catch (FileUploadException fne) {
			success = false;
			message = "Error: " + fne.getLocalizedMessage();
		} finally {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String,Object> result = new HashMap<String,Object>();
			if (success)
				result.put(Constants.START_FIELD, start);
			result.put(Constants.SUCCESS, success);
			result.put(Constants.MESSAGE, message);
			writer.write(objectMapper.writeValueAsString(result));
			IoUtil.close(in);
			IoUtil.close(writer);
		}
	}

	@RequestMapping(value="/form/remove.do",method= RequestMethod.POST)
	@ResponseBody
	protected String doRemoveFile(HttpServletRequest reqeust, HttpServletResponse response)throws ServletException, IOException {
		doOptions(reqeust, response);
		reqeust.setCharacterEncoding("utf8");
		String fileNames = reqeust.getParameter("fileNames");
		String path = reqeust.getParameter(Constants.FILE_PATH);
		if(StringUtils.isNotBlank(fileNames)){
			IoUtil.removeTokenedFile(path,fileNames.trim());
			return toJson("success","true");
		}else{
			return toJson("success","false");
		}
	}

	@RequestMapping(value="/stream/delete.do",method= RequestMethod.POST)
	@ResponseBody
	protected String doDeleteFile(HttpServletRequest reqeust, HttpServletResponse response)throws ServletException, IOException {
		doOptions(reqeust, response);
		reqeust.setCharacterEncoding("utf8");
		String imgUrl = reqeust.getParameter("imgUrl");
		if(StringUtils.isNotBlank(imgUrl)){
			String fileName = imgUrl.substring(imgUrl.lastIndexOf("/")+1);
			IoUtil.deleteFile("", fileName.trim());
			return toJson("success","true");
		}else{
			return toJson("success","false");
		}
	}
	
	/**
	 * 
	 * @param success
	 * @param params
	 * @return
	 */
	public static String toJson(String... params) {

		if (null == params)
			return null;
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("{");

		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i += 2) {
				if (i > 0)
					jsonBuilder.append(",");
				jsonBuilder.append("\"" + params[i] + "\":");
				if(StringUtils.isNumeric(params[i + 1]) || 
						params[i + 1].equalsIgnoreCase("true") || 
						params[i + 1].equalsIgnoreCase("false")){
					jsonBuilder.append(params[i + 1]);
				}
				else{ 
					jsonBuilder.append("\"" + escapeHtml(params[i + 1]) + "\"");
				}
			}
		}
		jsonBuilder.append("}");
		return jsonBuilder.toString();
	}

	static String escapeHtml(String src){
		String r = src.replace("<", "&lt;");

		r = r.replace(">", "&gt;");
		return r;
	}


	private void doOptions(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Headers", "Content-Range,Content-Type");
		response.setHeader("Access-Control-Allow-Origin", Constants.STREAM_CROSS_ORIGIN);
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
	}
	
	private static String getImgUrl(HttpServletRequest request,String fileName){
		 //String httpPath = HttpUtils.getAppRoot(request);
		String httpPath = Configurations.getConfig("ACCESSURL");
		 
		 String imgUrl = httpPath + "/"+fileName;
		 
		 return imgUrl;
	}
}
