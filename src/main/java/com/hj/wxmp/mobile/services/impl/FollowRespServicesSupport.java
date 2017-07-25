package com.hj.wxmp.mobile.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.entity.FollowResp;
import com.hj.wxmp.mobile.entity.FollowRespEx;
import com.hj.wxmp.mobile.mapping.FollowRespExMapper;
import com.hj.wxmp.mobile.mapping.FollowRespMapper;
import com.hj.wxmp.mobile.services.FollowRespServices;

@Component
public class FollowRespServicesSupport implements FollowRespServices {

	@Resource
	private FollowRespMapper mapper;
	@Resource
	private FollowRespExMapper mapperEx;

	@Override
	public boolean insert(FollowResp entity) throws Exception {
		return mapper.insert(entity) > 0 ? true : false;
	}
	@Override
	public boolean update(FollowResp entity) throws Exception {
		return mapper.update(entity) > 0 ? true : false;
	}
	
	/**
	 * 保存数据
	 * @param follow
	 */
	public void saveEntity(FollowResp follow) throws Exception{
		Integer id = follow.getId();
		if(id == null || id==0){
			mapper.insert(follow);
		}else{
			mapper.update(follow);
		}

		//多图文时才保存扩展表
		if(2==follow.getResptype()){
			List<FollowRespEx> exlist = follow.getExlist();
			for (FollowRespEx ex : exlist) {
				if (ex.getId() != null && 0!=ex.getId()) {
					mapperEx.update(ex);
				}else{
					if(StringUtils.isBlank(ex.getTitle())){
						continue;
					}
					mapperEx.insert(ex);
				}
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.pactera.wxmanage.services.FollowRespServices#listEntity(java.util.Map)
	 */
	@Override
	public List<FollowResp> listEntity(Map<String,Object> map) throws Exception {
		String title = (String)map.get("title");
		if((null == title)||(title.isEmpty())){
			map.clear();
			map.put("id",  new Integer(0));
    		List<FollowResp> retlist = mapper.findAll(map);
    		for(FollowResp follow:retlist){
    	        List<FollowRespEx> exlist = mapperEx.findAll(map);
    	        if((null != follow)&&(null != exlist)){
    	        	follow.setExlist(exlist);
    	        }
    		}
    		return retlist;
    	}
    	return mapper.findAll(map);
	}
	
	@Override
	public void delete(FollowResp entity) throws Exception {
		mapper.delete(entity);
	}
	
	@Override
	public void deleteEx(FollowRespEx entity) throws Exception {
		mapperEx.delete(entity);
	}

}
