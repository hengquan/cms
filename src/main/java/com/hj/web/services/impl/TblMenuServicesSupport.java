package com.hj.web.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.hj.web.entity.TblMenu;
import com.hj.web.mapping.TblMenuMapper;
import com.hj.web.services.TblMenuServices;

@Component
public class TblMenuServicesSupport implements TblMenuServices {

	@Resource
	private TblMenuMapper mapper;

	@Override
	public boolean insert(TblMenu entity) throws Exception {
		return mapper.insert(entity) > 0 ? true : false;
	}
	@Override
	public boolean update(TblMenu entity) throws Exception {
		return mapper.update(entity) > 0 ? true : false;
	}
	
	@Override
	public boolean insertOrUpdate(TblMenu entity) throws Exception {
		if(entity.getId()==null || entity.getId()==0){
			return insert(entity);
		}else{
			return update(entity);
		}
	}
	
	@Override
	public List<TblMenu> listEntity(Map<String,Object> map) throws Exception {
		String title = (String)map.get("title");
		if((null == title)||(title.isEmpty())){
			map.clear();
			map.put("level", new Integer(0));
    		List<TblMenu> topList = mapper.findAll(map);
    		for(TblMenu menu:topList){
    			map.clear();
    			map.put("parentId", menu.getId());
    			List<TblMenu> childList = mapper.findAll(map);
    			if(null == childList){
    				childList = new ArrayList<TblMenu>();
    			}
    			/*if(childList.size()<5){    				
    				while(childList.size()<5){
    					childList.add(new TblMenu());
    				}
    			}*/
    			menu.setChilds(childList);
    		}    		
    		return topList;
    	}
		map.clear();
		map.put("name", title);
    	List<TblMenu>  menuList =  mapper.findAll(map);
    	for(TblMenu menu:menuList){
    		map.clear();
    		map.put("parentId", menu.getId());
			List<TblMenu> childList = mapper.findAll(map);
			if(null == childList){
				childList = new ArrayList<TblMenu>();
			}
			/*if(childList.size()<5){    				
				while(childList.size()<5){
					childList.add(new TblMenu());
				}
			}*/
			menu.setChilds(childList);
		}  
    	
    	return menuList;
	}
	
	@Override
	public void delete(TblMenu menu) throws Exception {
		mapper.delete(menu);
	}
	
	@Override
	public boolean isAddFirstLevelMenu(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("level", new Integer(0));
		List<TblMenu> topList = mapper.findAll(map);
		if(null!=topList && topList.size()>=3){
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean isAddSecondLevelMenu(String parentId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("parentId", NumberUtils.toInt(parentId));
		List<TblMenu> menuList = mapper.findAll(map);
		if(null!=menuList && menuList.size()>=5){
			return false;
		}
		
		return true;
	}
	
	@Override
	public TblMenu getEntity(int id){
		return mapper.getEntity(id);
	}

}
