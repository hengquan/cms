package com.hj.wxmp.mobile.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.common.Weixin;
import com.hj.wxmp.mobile.entity.TblArticle;
import com.hj.wxmp.mobile.mapping.TblArticleMapper;
import com.hj.wxmp.mobile.services.TblArticleService;

@Component
public class TblArticleServiceImpl implements TblArticleService {

	private Weixin weixin = Weixin.getInstance();
	
	@Resource
	private TblArticleMapper tblArticleMapper;

	@Override
	public boolean insert(TblArticle entity) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(TblArticle entity) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void delete(TblArticle entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TblArticle saveEntity(TblArticle entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TblArticle findById(String sys_uuid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer findCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TblArticle> findAll(Map<String, Object> m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String selectMsg() {
		return tblArticleMapper.selectMsg();
	}
	
	
}
