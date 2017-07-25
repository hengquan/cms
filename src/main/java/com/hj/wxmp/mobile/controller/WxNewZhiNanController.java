package com.hj.wxmp.mobile.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hj.web.core.mvc.ControllerBase;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.common.Weixin;
import com.hj.wxmp.mobile.entity.UserInfo;

/**  
 *  新手指南
 * @Title: WxNewZhiNanController
 * @Package com.hj.wxmp.mobile.controller
 * @Description: TODO
 * @author deng.hemei   
 * @date 2016年9月8日 上午15:24:36
 * @version V1.0   
 */
@RequestMapping("/wx/zhinan")
@Controller
public class WxNewZhiNanController extends ControllerBase {
	
	private final static Logger logger = LoggerFactory.getLogger(WxNewZhiNanController.class);
	
	private HashSessions hashSession = HashSessions.getInstance();
	private Weixin weixin = Weixin.getInstance();
	
	public UserInfo getCurrentUser(){
		UserInfo user = hashSession.getCurrentSessionUser(request);
		return user;
	}
	
	/**
	 * 进入新手指南页面
	 * @author deng.hemei
	 * @description 
	 * @return 
	 * @updateDate 2016年9月9日
	 */
	@RequestMapping("/guide.az")
	public String getList(Model model){
		return "wx/guide";
	}
}
