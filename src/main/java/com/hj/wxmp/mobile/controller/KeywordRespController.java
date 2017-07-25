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
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.utils.JsonUtils;
import com.hj.wxmp.mobile.common.PageUtil;
import com.hj.wxmp.mobile.entity.KeywordResp;
import com.hj.wxmp.mobile.entity.KeywordRespEx;
import com.hj.wxmp.mobile.entity.Material;
import com.hj.wxmp.mobile.services.KeywordRespServices;
import com.hj.wxmp.mobile.services.MaterialServices;

@Controller
@RequestMapping("wxmanage/jsp")
public class KeywordRespController {
	private final Logger logger = Logger.getLogger(KeywordRespController.class);

	@Resource
	private KeywordRespServices keywordService;
	
	@Resource
	private MaterialServices materialService;
	
	private PageUtil pageUtil = new PageUtil();
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value =  "/keyword-list" , method = RequestMethod.GET)
	public String listKeyword(HttpServletRequest request,Map<String, Object> map) {
		map.put("title", "");
		try {
			map.putAll(pageUtil.pageReturnModelMap(request, map, null,keywordService.findCount(map)));
			
			map.put("keywordList", keywordService.listEntity(map));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "wxmanage/keyword";
	}
	
	@RequestMapping(value = "/keyword-insert", method = RequestMethod.GET)
    public String insertKeyword(Map<String, Object> map) {
    	logger.debug(" RequestMapping  keyword insert");
    	try {
    		KeywordResp entity = new KeywordResp();
			//keywordService.saveEntity(entity);
			map.put("keyword", entity);  
	    	map.put("resptype", entity.getResptype()); 
	    	
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
        return "wxmanage/keyword-add";
    }
	
	@RequestMapping(value = "/keyword-edit", method = RequestMethod.GET)
    public String editKeyword(String id, Map<String, Object> map) {
    	logger.debug(" RequestMapping  keyword edit");
    	Integer temp = 0;
    	try{
    		temp = Integer.parseInt(id);
    		KeywordResp entity = keywordService.findById(temp);
    		map.put("keyword", entity);    	
	    	map.put("resptype", entity.getResptype()); 
	    	
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
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return "wxmanage/keyword-add"; //"wxmanage/jsp/keyword-update";
    }
	
	@RequestMapping(value = "/keyword-update", method = RequestMethod.POST)
    public String updateKeyword(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("keyword") KeywordResp entity, ModelMap modelMap) {
    	logger.debug(" RequestMapping  keyword update");
    	Boolean result = false;
    	if(null != entity){
    		logger.debug(" keyword:"+entity.getId());
    		try {
				keywordService.saveEntity(entity);
				result = true;
			} catch (Exception e) {
				result = null;
				e.printStackTrace();
				logger.error(e.getMessage());
			}
    	}
     return "redirect:/wxmanage/jsp/keyword-list.do";
    }
    
    @RequestMapping(value = "/keyword-del", method = RequestMethod.GET)
    public String delKeyword(HttpServletRequest request, HttpServletResponse response,String id, Map<String, Object> map, ModelMap modelMap) {
    	logger.debug(" RequestMapping  keyword del:"+id);
    	Boolean result = false;
    	Integer temp = 0;
    	try{
    		temp = NumberUtils.toInt(id);
    		if(temp>0){
    			KeywordResp entity = new KeywordResp();
        		entity.setId(temp);
        		keywordService.delete(entity);
    		}else{
    			String[] ids = request.getParameterValues("id[]");
    			if(null!=ids){
    				for (int i = 0; i < ids.length; i++) {
    					KeywordResp entity = new KeywordResp();
    	        		entity.setId(NumberUtils.toInt(ids[i]));
    	        		keywordService.delete(entity);
					}
    			}
    		}
	    	
	    	result = true;
    	}catch(Exception e){
    		result = null;
    		e.printStackTrace();
    		logger.error(e.getMessage());
    	}
    	
        return "redirect:/wxmanage/jsp/keyword-list.do";
    }    
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value =  "/keyword-search" , method = RequestMethod.POST)
	public String doSearch(HttpServletRequest request, Map<String, Object> map) {
		String keyword = request.getParameter("keyword");
		if(!StringUtils.isBlank(keyword)){
			map.put("keyword", keyword);
		}
		
		String resptype = request.getParameter("resptype");
		if(!StringUtils.isBlank(resptype)){
			map.put("resptype", resptype);
		}
		
		try {
			map.putAll(pageUtil.pageReturnModelMap(request, map, null,keywordService.findCount(map)));
			
			map.put("keywordList", keywordService.findBy(map));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "wxmanage/keyword";
	}
	
    @RequestMapping(value="/keyword-deleteEx.do",method= RequestMethod.POST)
	@ResponseBody
	protected String doDeleteEx(HttpServletRequest reqeust, HttpServletResponse response)throws ServletException, IOException {
		reqeust.setCharacterEncoding("utf8");
		try {
			String id = reqeust.getParameter("id");
			if (StringUtils.isNotBlank(id)) {
				KeywordRespEx entity = new KeywordRespEx();
				entity.setId(Integer.valueOf(id));
				keywordService.deleteEx(entity);
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
