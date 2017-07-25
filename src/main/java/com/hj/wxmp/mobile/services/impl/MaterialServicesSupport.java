package com.hj.wxmp.mobile.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.entity.Material;
import com.hj.wxmp.mobile.mapping.MaterialMapper;
import com.hj.wxmp.mobile.services.MaterialServices;
@Component
public class MaterialServicesSupport implements MaterialServices {

	@Resource
	private MaterialMapper mapper;

	@Override
	public boolean insert(Material entity) throws Exception {
		return mapper.insert(entity) > 0 ? true : false;
	}
	@Override
	public boolean update(Material entity) throws Exception {
		return mapper.update(entity) > 0 ? true : false;
	}
	
	/**
	 * 保存数据
	 * @param follow
	 */
	public void saveEntity(Material entity) throws Exception{
		Integer id = entity.getId();
		if(id == null || 0==id){
			mapper.insert(entity);
		}else{
			mapper.update(entity);
		}
	}
	/* (non-Javadoc)
	 * @see com.pactera.wxmanage.services.FollowRespServices#listEntity(java.util.Map)
	 */
	@Override
	public List<Material> listEntity(Map<String,Object> map) throws Exception {
    	return mapper.findAll(map);
	}
	
	@Override
	public void delete(Material entity) throws Exception {
		mapper.delete(entity);
	}
	
	@Override
	public Material findById(Integer id) throws Exception {
		if(id==null) id = 0;
		return mapper.findById(id);
	}
	@Override
	public Integer findCount(Map<String, Object> map) {
		return mapper.getAllCount(map);
	}

}
