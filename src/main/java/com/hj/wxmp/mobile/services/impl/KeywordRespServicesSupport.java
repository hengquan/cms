package com.hj.wxmp.mobile.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.entity.KeywordResp;
import com.hj.wxmp.mobile.entity.KeywordRespEx;
import com.hj.wxmp.mobile.mapping.KeywordRespExMapper;
import com.hj.wxmp.mobile.mapping.KeywordRespMapper;
import com.hj.wxmp.mobile.services.KeywordRespServices;

@Component
public class KeywordRespServicesSupport implements KeywordRespServices {

	@Resource
	private KeywordRespMapper mapper;
	@Resource
	private KeywordRespExMapper mapperEx;

	@Override
	public boolean insert(KeywordResp entity) throws Exception {
		return mapper.insert(entity) > 0 ? true : false;
	}
	@Override
	public boolean update(KeywordResp entity) throws Exception {
		return mapper.update(entity) > 0 ? true : false;
	}
	
	/**
	 * 保存数据
	 * @param follow
	 */
	public void saveEntity(KeywordResp entity) throws Exception{
		Integer id = entity.getId();
		if(id == null || id==0){
			mapper.insert(entity);
		}else{
			mapper.update(entity);
		}

		//多图文时才保存扩展表
		if(2==entity.getResptype()){
			List<KeywordRespEx> exlist = entity.getExlist();
			for (KeywordRespEx ex : exlist) {
				if (ex.getId() != null && 0!=ex.getId()) {
					ex.setParent_id(entity.getId());
					mapperEx.update(ex);
				}else{
					if(StringUtils.isBlank(ex.getTitle())){
						continue;
					}
					ex.setParent_id(entity.getId());
					mapperEx.insert(ex);
				}
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.pactera.wxmanage.services.FollowRespServices#listEntity(java.util.Map)
	 */
	@Override
	public List<KeywordResp> listEntity(Map<String,Object> map) throws Exception {
		String title = (String)map.get("title");
		if((null == title)||(title.isEmpty())){
			//map.clear();
			map.put("id",  new Integer(0));
    		List<KeywordResp> retlist = mapper.findAll(map);
    		for(KeywordResp follow:retlist){
    	        List<KeywordRespEx> exlist = mapperEx.findAll(map);
    	        if((null != follow)&&(null != exlist)){
    	        	follow.setExlist(exlist);
    	        }
    		}
    		return retlist;
    	}
    	return mapper.findAll(map);
	}
	
	@Override
	public void delete(KeywordResp entity) throws Exception {
		mapper.delete(entity);
	}
	
	@Override
	public void deleteEx(KeywordRespEx entity) throws Exception {
		mapperEx.delete(entity);
	}
	
	@Override
	public KeywordResp findById(Integer id) throws Exception {
		KeywordResp keyword = (KeywordResp) mapper.findById(id);
    	if(null != keyword){
    		
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("parent_id",  keyword.getId());
			List<KeywordRespEx> exlist = mapperEx.findAll(map);
			if (exlist == null) {
				exlist = new ArrayList<KeywordRespEx>();
			}
			int i;
			if (exlist.size() < 7) {
				int num = 7 - exlist.size();
				for (i = 0; i < num; i++) {
					KeywordRespEx ex = new KeywordRespEx();
					ex.setParent_id(id);
					exlist.add(ex);
				}
			}
			keyword.setExlist(exlist);
		}
		return keyword;
	}

	@Override
    public KeywordResp findByTitle(String title) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("title", title);
		try {
			List<KeywordResp> list = listEntity(map);
			if(null!=list && list.size()>0){
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<KeywordResp> findBy(Map<String,Object> map) throws Exception {
		List<KeywordResp> retlist = mapper.findAll(map);
		for (KeywordResp keywordResp : retlist) {
			Map<String,Object> mapEx = new HashMap<String,Object>();
			mapEx.put("parent_id", keywordResp.getId());
			
			List<KeywordRespEx> exlist = mapperEx.findAll(mapEx);
			if(null!=exlist && exlist.size()>0){
				keywordResp.setExlist(exlist);
			}
		} 
		
		return retlist;
	}
	
	@Override
    public KeywordResp findByKeyword(String keyword) {
		try {
		  KeywordResp keywordResp = mapper.findByKeyword(keyword);
		  if(null!=keywordResp){
			Map<String,Object> mapEx = new HashMap<String,Object>();
			mapEx.put("parent_id", keywordResp.getId());
			
			List<KeywordRespEx> exlist = mapperEx.findAll(mapEx);
			if(null!=exlist && exlist.size()>0){
				keywordResp.setExlist(exlist);
			}
		  }
		  
		  return keywordResp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
    public KeywordResp findByKeywordLike(String keyword) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyword", keyword);
		try {
			List<KeywordResp> list = findBy(map);
			if(null!=list && list.size()>0){
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Integer findCount(Map<String, Object> map) {
		return mapper.getAllCount(map);
	}
}
