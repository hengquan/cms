package com.hj.wxmp.mobile.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hj.web.core.mvc.ControllerBase;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.common.UploadUtils;
import com.hj.wxmp.mobile.entity.SysAdmin;
import com.hj.wxmp.mobile.mapping.SysAdminMapper;
/**
 * 
 * @author 王志勇
 * 修改商户以及总后台头像
 */
@RequestMapping("/businesslogo")
@Controller
public class UpdateLogoController extends ControllerBase{
	
private final static Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private HashSessions hashSession = HashSessions.getInstance();
	
	@Autowired
	SysAdminMapper sysAdminMapper;
		
		
		@RequestMapping("/update")
		public String updateLogo(@RequestParam(value="business_logo") MultipartFile logofile) throws IOException{
			 		if(!logofile.isEmpty()){
				         String src = UploadUtils.upload(logofile, request);
				         String adminId = hashSession.getCurrentAdmin(request).getId();
				         SysAdmin sysAdmin = new SysAdmin();
				         sysAdmin.setId(adminId);
				         sysAdmin.setHeadimgurl(src);
				         sysAdminMapper.updateByPrimaryKeySelective(sysAdmin);
				     }    
				     return "redirect:/home/index";   //重定向
		}
		
			@RequestMapping("/getimg")
			@ResponseBody
			public SysAdmin getImg(){
				SysAdmin sysAdmin = sysAdminMapper.selectByPrimaryKey(hashSession.getCurrentAdmin(request).getId());
				return sysAdmin;
			}
}
