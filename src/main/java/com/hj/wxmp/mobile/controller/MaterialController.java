package com.hj.wxmp.mobile.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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

import com.hj.wxmp.mobile.common.Constants;
import com.hj.wxmp.mobile.common.PageUtil;
import com.hj.wxmp.mobile.entity.Material;
import com.hj.wxmp.mobile.services.MaterialServices;

@Controller
@RequestMapping("wxmanage/jsp")
public class MaterialController {
	private final Logger logger = Logger.getLogger(MaterialController.class);

	@Resource
	private MaterialServices materialService;
	
	private PageUtil pageUtil = new PageUtil();
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/material-list" })
	public String list(HttpServletRequest request,Map<String, Object> map) {
		try {			
			map.putAll(pageUtil.pageReturnModelMap(request, map, null,materialService.findCount(map)));
			
			List<Material> materialList = materialService.listEntity(map);

			List<Map<String,Object>> articleTypeList = putArticleList(map);
			
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
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return "wxmanage/material-list";
	}
	    
	@RequestMapping(value =  "/material-search" , method = RequestMethod.POST)
	public String doSearch(HttpServletRequest request, Map<String, Object> map) {
		String title = request.getParameter("title");
		if(!StringUtils.isBlank(title)){
			map.put("title", title);
		}
		
		String article_type = request.getParameter("article_type");
		if(!StringUtils.isBlank(article_type)){
			map.put("article_type", article_type);
		}
		
		try {
			//map.putAll(pageUtil.pageReturnModelMap(request, map, null,materialService.findCount(map)));
			
			map.put("materialList", materialService.listEntity(map));
			
			putArticleList(map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return "wxmanage/material-list";
	}
	
    @RequestMapping(value = "/material-edit", method = RequestMethod.GET)
    public String edit(Integer id, Map<String, Object> map) {
    	try{
    		Material material = materialService.findById(id);
    		
    		if(null==material)material = new Material();
    		
        	map.put("material", material);
        	
        	putArticleList(map);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return "wxmanage/material";
    }
    
    @RequestMapping(value = "/material-update", method = RequestMethod.POST)
    public String update(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("material") Material material,ModelMap modelMap) {
    	Boolean result = false;
    	String operTypeFont = "";
    	String operType = "";
    	if(null != material){
    		if(material.getId() != null){
    			operTypeFont = Constants.OPER_TYPE_EDIT_FONT;
    			operType = Constants.OPER_TYPE_EDIT;
    		}else{
    			operTypeFont = Constants.OPER_TYPE_CREATE_FONT;
    			operType = Constants.OPER_TYPE_CREATE;
    		}
    		try {
    			material.setUpdate_time(new Date());
    			materialService.saveEntity(material);
    			result = true;
			} catch (Exception e) {
				result = null;
				e.printStackTrace();
			}
    	}
        return "redirect:/wxmanage/jsp/material-list.do";
    }
    
	
    /**
	 * 删除单个模块
	 * @param codeValue 代码值
	 * @return boolean 返回结果布尔值
	 * @throws Exception
	 * */
	@RequestMapping("/material-del")
	public String delete(HttpServletRequest request, ModelMap modelMap) throws Exception {
		Boolean result = false;
    	Integer temp = 0;
    	try{
    		String id = request.getParameter("id");
    		temp = NumberUtils.toInt(id);
    		if(temp>0){
    			Material material = new Material();
        		material.setId(temp);
        		materialService.delete(material);
    		}else{
    			String[] ids = request.getParameterValues("id[]");
    			if(null!=ids){
    				for (int i = 0; i < ids.length; i++) {
    					Material material = new Material();
    					material.setId(NumberUtils.toInt(ids[i]));
    					materialService.delete(material);
					}
    			}
    		}
	    	
	    	result = true;
    	}catch(Exception e){
    		result = null;
    		e.printStackTrace();
    		logger.error(e.getMessage());
    	}
    	
		
		return "redirect:/wxmanage/jsp/material-list.do";
	}

	/**
	 * 删除多个模块
	 * @param request 请求
	 * @param response 响应
	 * @param map 页面传递的值进行封装，传给service层
	 * @param modelMap 返回到页面的modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * */
	@RequestMapping("/material-del-more")
	@ResponseBody
	public boolean deleteMore(HttpServletRequest request, HttpServletResponse response, Map<String,Object> map,
			ModelMap modelMap) {
		logger.debug("===删除多个联系我们开始===");
		Boolean result = false;
		String[] ids = request.getParameter("ids").split(",");
		for (String codeValue : ids) {
			if (!"".equals(codeValue)) {
				try {
					Integer id = codeValue != null && !"".equals(codeValue) ? Integer.valueOf(codeValue) : 0;
					Material material = new Material();
		    		material.setId(id);
		    		materialService.delete(material);
					result = true;
				} catch (Exception e) {
					result = null;
					logger.error(e.toString());
				}
			}
		}
		logger.debug("===删除多个联系我们结束===");
		return result;
	}
	
	@RequestMapping(value = { "/material-select" })
	public String doSelect(HttpServletRequest request,Map<String, Object> map) {
		try {			
			//map.putAll(pageUtil.pageReturnModelMap(request, map, null,materialService.findCount(map)));
			
			map.put("materialList", materialService.listEntity(map));
			
			putArticleList(map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return "wxmanage/material-select";
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
