package com.hj.wxmp.mobile.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.utils.JsonUtils;
import com.hj.wxmp.mobile.entity.FollowResp;
import com.hj.wxmp.mobile.entity.FollowRespEx;
import com.hj.wxmp.mobile.entity.Material;
import com.hj.wxmp.mobile.services.FollowRespServices;
import com.hj.wxmp.mobile.services.MaterialServices;

@Controller
@RequestMapping("wxmanage/jsp")
public class FollowRespController {
	private final Logger logger = Logger.getLogger(FollowRespController.class);

	@Resource
	private FollowRespServices followRespServices;
	
	@Resource
	private MaterialServices materialService;
	
	@RequestMapping(value =  "/follow-list" , method = RequestMethod.GET)
	public String listFollow(Map<String, Object> map) {
    	boolean found = false;
    	map.put("title", "");
    	List<FollowResp> list;
		try {
			list = followRespServices.listEntity(map);
			if(list.size()>0){
	    		FollowResp entity = null;
	    		if(list.get(0) instanceof FollowResp){
	    			entity = (FollowResp)list.get(0);
	    			map.put("follow",entity );
	        		map.put("resptype", entity.getResptype()); 
	        		
	        		 found = true;
	    		}
	    	}
	    	if(!found){
	    		map.put("follow",new FollowResp() );
	    		map.put("resptype", 0); 
	    	}
	    	
	    	List<Map<String,Object>> articleTypeList = putArticleList(map);
	    	
	    	List<Material> materialList = materialService.listEntity(new HashMap<String,Object>());
	    	for (Material material : materialList) {
				for (Map<String,Object> map1 : articleTypeList) {
					if(StringUtils.trimToEmpty(material.getArticle_type()).equals(map1.get("code"))){
						material.setArticleTypeName(ObjectUtils.toString(map1.get("val")));
						break;
					}
				}
			}
	    	map.put("materialList", materialList);
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "wxmanage/follow";
	}

	@RequestMapping(value = "/follow-update", method = RequestMethod.POST)
	public String updateFollow(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("follow") FollowResp entity,ModelMap modelMap) {
    	logger.debug(" RequestMapping  follow update");
    	Boolean result = false;
    	if(null != entity){
    		
    		try{
    			//entity.setId(1);
        		logger.debug(" follow:"+entity.getTitle());
        		followRespServices.saveEntity(entity);
        		result = true;
    		} catch (Exception e) {
    			result = null;
    			e.printStackTrace();
    			logger.error(e.getMessage());
			}
    	}
        return "redirect:follow-list.do";
	 }
	
	@RequestMapping(value="/deleteEx.do",method= RequestMethod.POST)
	@ResponseBody
	protected String doDeleteEx(HttpServletRequest reqeust, HttpServletResponse response)throws ServletException, IOException {
		reqeust.setCharacterEncoding("utf8");
		try {
			String id = reqeust.getParameter("id");
			if (StringUtils.isNotBlank(id)) {
				FollowRespEx entity = new FollowRespEx();
				entity.setId(Integer.valueOf(id));
				followRespServices.deleteEx(entity);
				return JsonUtils.toJson("success", "true");
			} else {
				return JsonUtils.toJson("success", "false");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtils.toJson("success", "false");
		}
	}
	
	private List<Map<String,Object>> putArticleList(Map<String,Object> map){
		try {
			Map<String, Object> articleMap = new HashMap<String, Object>();
			articleMap.put("code", "01");
			articleMap.put("val", "素材");
			
			List<Map<String,Object>> articleTypeList = new ArrayList<Map<String,Object>>();
			articleTypeList.add(articleMap);
			
			map.put("articleList",articleTypeList);
			
			return articleTypeList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
