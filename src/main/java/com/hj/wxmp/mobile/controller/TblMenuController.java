package com.hj.wxmp.mobile.controller;

import java.util.ArrayList;
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

import com.hj.utils.JsonUtils;
import com.hj.web.core.common.JsonMapper;
import com.hj.wxmp.mobile.common.Weixin;
import com.hj.wxmp.mobile.entity.Material;
import com.hj.wxmp.mobile.entity.TblMenu;
import com.hj.wxmp.mobile.services.KeywordRespServices;
import com.hj.wxmp.mobile.services.MaterialServices;
import com.hj.wxmp.mobile.services.TblMenuServices;

@Controller
@RequestMapping("wxmanage/jsp")
public class TblMenuController {
	private final Logger logger = Logger.getLogger(TblMenuController.class);

	@Resource
	private TblMenuServices wxmenuService;
	
	@Resource
	private MaterialServices materialService;
	@Resource
	private KeywordRespServices keywordService;
	
	@RequestMapping(value =  "/menu-list" , method = RequestMethod.GET)
	public String listMenu(Map<String, Object> map, HttpServletRequest request) {
		try{
			TblMenu rootMenu =  new TblMenu();
			rootMenu.setName("roottt");
			//map = new HashMap<String, Object>();
			map.put("title", "");
			List<TblMenu> list = wxmenuService.listEntity(map);
			rootMenu.setChilds(list);
			map.put("rootMenu",rootMenu);		 
			//map.put("wxmenuList", list);
			
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
	    	
	    	//关键字列表
	    	map.put("keywordList", keywordService.listEntity(new HashMap<String,Object>()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return "wxmanage/menu";
	}

	
	@RequestMapping(value = "/menu-update", method = RequestMethod.POST)
	public String updateMenu(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("rootMenu") TblMenu rootMenu,ModelMap modelMap) {
		Boolean result = false;
		List<TblMenu> list = null;
		if(null != rootMenu){
			list = rootMenu.getChilds();
		} 
		try {
			if(null != list){
				logger.debug(" wxmenuList:"+list.size());
				for(int xi=0;xi<list.size();xi++){
					//System.out.println(" name:"+list.get(xi).getName());
					TblMenu parentMenu = list.get(xi);
					if(!parentMenu.getName().isEmpty()){
						//menu.setId(1+xi*6);
						parentMenu.setLevel(0);
						parentMenu.setSeq(xi);
						parentMenu.setParentId(0);
						wxmenuService.update(parentMenu);
					}
					List<TblMenu> chlist = list.get(xi).getChilds();
					for(int li=0;li<chlist.size();li++){
						System.out.println(" childName:"+chlist.get(li).getName());
						logger.debug("       name:"+chlist.get(li).getName());
						TblMenu menu = chlist.get(li);
		    			if((!menu.getName().isEmpty())&&((!menu.getContent().isEmpty())||(!menu.getLink().isEmpty()))){
		    				//menu.setId(2+xi*6+li);
		    				menu.setLevel(1);
		    				menu.setSeq(li);
		    				menu.setParentId(parentMenu.getId());
		    				if((0 == menu.getType())&&(!menu.getLink().isEmpty())){
		    					menu.setContent("");
		    				}else if((1 == menu.getType())&&(!menu.getContent().isEmpty())){
		    					menu.setLink("");
		    				}
		    				wxmenuService.insertOrUpdate(menu);
		    			}else if((null == menu.getName())||(menu.getName().isEmpty())){
		    				wxmenuService.delete(menu);
						}
		    		}
				}
				result = true;
			}
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
	    return "redirect:menu-list.do";
	}
	    
	
	@RequestMapping(value = "/menu-setup", method = RequestMethod.GET)
    public String setupMenu(Map<String, Object> map) {
    	logger.debug(" RequestMapping  WxMenu setup");
    	

		String json= createMenu();
		logger.error("menu-json:"+json);
		
		
		Weixin weixin = Weixin.getInstance();
		String rs = weixin.createMenu(json);
		JsonMapper mapper = new JsonMapper();
		Map<String, Map<String, Object>> rsMap = mapper.json2Map(rs);
		int errcode = 0;
		if(null != rsMap.get("errcode")){
			try{
				errcode = NumberUtils.toInt(ObjectUtils.toString(rsMap.get("errcode")));
				//errcode = Integer.parseInt(rsMap.get("errcode").toString());
			}catch(Exception e){
				e.printStackTrace();
			}			
		}
		if(0 == errcode){			
			logger.info("setup menu Success");
		}			
		else{
			logger.error("setup menu:" + errcode+ rsMap.get("errmsg"));
			map.put("errmsg", "发布时遇到错误："+rsMap.get("errmsg"));
		}
    	
		return "redirect:menu-list.do";
    }   
    
    
    protected String createMenu(){
		List topList = new ArrayList();		
		
    	List<TblMenu> dblist = new ArrayList<TblMenu>();
		try {
			dblist = wxmenuService.listEntity(new HashMap<String, Object>());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	for(TblMenu menu:dblist){
    		HashMap<String, Object> level1map = new HashMap<String, Object>();
    		List item2 = new ArrayList();
    		level1map.put("name",menu.getName());
    	
    		for(TblMenu child:menu.getChilds()){
    			if((null != child.getName())&&(child.getName().trim().length()>0)){
    				HashMap<String, Object> item3 = new HashMap<String, Object>();
        			item3.put("name",child.getName());
        			if((0 == child.getType())&&(!child.getLink().isEmpty())){
        				item3.put("type","view");        		
                		item3.put("url",child.getLink());
        			}else{
        				item3.put("type","click");        		
                		item3.put("key",child.getContent());
        			}        		
            		item2.add(item3);
    			}    			
    		}
    		
    		if(null==item2 || item2.isEmpty()){//add by hj 2016.10.27
    			if(menu.getType()==0){
    				level1map.put("type","view");        		
    				level1map.put("url",menu.getLink());
    			}else{
    				level1map.put("type","click");        		
    				level1map.put("key",menu.getContent());
    			}
    		}
    		level1map.put("sub_button",item2);
    		
    		topList.add(level1map);		
    	}
    	
    	HashMap<String, Object> buttonMap = new HashMap<String, Object>();	
		buttonMap.put("button", topList);
		HashMap<String, Object> menuMap = new HashMap<String, Object>();	
		menuMap.put("menu", buttonMap);
		String json = JsonUtils.map2json(buttonMap);
		json = json.replaceAll("\\\\/","/");
		logger.debug(json);
		
		return json;
	}
    
    @ResponseBody
	@RequestMapping("/isAddFirstLevelMenu")
	public String isAddFirstLevelMenu(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
    	boolean flag = wxmenuService.isAddFirstLevelMenu();
    	
    	return flag?"Y":"N";
	}
    
    @ResponseBody
	@RequestMapping("/isAddSecondLevelMenu")
	public String isAddSecondLevelMenu(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
    	String parentId = StringUtils.trimToEmpty(request.getParameter("parentId"));
    	
    	boolean flag = wxmenuService.isAddSecondLevelMenu(parentId);
    	
    	return flag?"Y":"N";
	}
    
    @ResponseBody
	@RequestMapping("/deleteMenuItem")
	public String deleteMenuItem(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
    	boolean flag = true;
    	
    	String id = StringUtils.trimToEmpty(request.getParameter("id"));
    	
    	if("".equals(id) || "0".equals(id)){
    		return "N";
    	}
    	
    	try {
			TblMenu menu = new TblMenu();
			menu.setId(NumberUtils.toInt(id));
			wxmenuService.delete(menu);
			
			menu = new TblMenu();
			menu.setParentId(NumberUtils.toInt(id));
			wxmenuService.delete(menu);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag?"Y":"N";
	}
    
    @ResponseBody
	@RequestMapping("/saveMenuItem")
	public String saveMenuItem(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
    	boolean flag = true;
    	
    	String id = StringUtils.trimToEmpty(request.getParameter("id"));
    	String menuName = StringUtils.trimToEmpty(request.getParameter("menuName"));
    	String menuLevel = StringUtils.trimToEmpty(request.getParameter("menuLevel"));
    	String menuParentId = StringUtils.trimToEmpty(request.getParameter("menuParentId"));
    	String actionType = StringUtils.trimToEmpty(request.getParameter("actionType"));
    	
    	try {
			TblMenu menu = new TblMenu();
			if("insert".equals(actionType)){
				menu.setName(menuName);
				menu.setLevel(NumberUtils.toInt(menuLevel));
				menu.setParentId(NumberUtils.toInt(menuParentId));
				
				flag = wxmenuService.insert(menu);
			}else{
				menu.setId(NumberUtils.toInt(id));
				menu.setName(menuName);
				menu.setLevel(NumberUtils.toInt(menuLevel));
				menu.setParentId(NumberUtils.toInt(menuParentId));
				
				flag = wxmenuService.update(menu);
			}
			
			
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag?"Y":"N";
	}
    
    @ResponseBody
   	@RequestMapping("/getMenuItem")
   	public String getMenuItem(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
       	String json = "";
       	
       	String id = StringUtils.trimToEmpty(request.getParameter("id"));
       	
       	if("".equals(id) || "0".equals(id)){
       		return "";
       	}
       	
       	try {
   			TblMenu menu = wxmenuService.getEntity(NumberUtils.toInt(id));
   			
   			if(null!=menu){
	   			Map<String,Object> map = new HashMap<String,Object>();
	   			map.put("id", menu.getId());
	   			map.put("level", menu.getLevel());
	   			map.put("link", menu.getLink());
	   			map.put("name", menu.getName());
	   			map.put("parentId", menu.getParentId());
	   			map.put("seq", menu.getSeq());
	   			map.put("type", menu.getType());
	   			
	   			json = JsonUtils.map2json(map);
   			}
   			
   		} catch (Exception e) {
   			json = "";
   			e.printStackTrace();
   		}
   		return json;
   	}
    
    @ResponseBody
	@RequestMapping("/saveMenuAction")
	public String saveMenuAction(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
    	boolean flag = true;
    	
    	String id = StringUtils.trimToEmpty(request.getParameter("id"));
    	String contentType = StringUtils.trimToEmpty(request.getParameter("contentType"));
    	String actionLink = StringUtils.trimToEmpty(request.getParameter("actionLink"));
    	
    	try {
			TblMenu menu = wxmenuService.getEntity(NumberUtils.toInt(id));
			menu.setType(NumberUtils.toInt(contentType));
			menu.setLink(actionLink);
			flag = wxmenuService.update(menu);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag?"Y":"N";
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
    
    @ResponseBody
	@RequestMapping("/saveMenuSort")
	public String saveMenuSort(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
    	boolean flag = true;
    	
    	String ids = StringUtils.trimToEmpty(request.getParameter("ids"));
    	String vls = StringUtils.trimToEmpty(request.getParameter("vls"));
    	
    	try {
    		String[] idArrays = ids.split(",");
    		String[] vlArrays = vls.split(",");
    		for (int i = 0; i < idArrays.length; i++) {
    			TblMenu menu = wxmenuService.getEntity(NumberUtils.toInt(idArrays[i]));
    			menu.setSeq(NumberUtils.toInt(vlArrays[i]));
    			
    			flag = wxmenuService.update(menu);
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag?"Y":"N";
	}
}
