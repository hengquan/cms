package com.hj.web.services.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.hj.web.entity.Language;
import com.hj.web.mapping.LanguageMapper;
import com.hj.web.services.IKeyGen;
import com.hj.web.services.LanguageService;

@Component
public class LanguageServiceImpl implements LanguageService {

	@Resource
	private IKeyGen keyGen;
	@Resource
	private LanguageMapper dao;

	@Override
	public boolean insert(Language entity) {
		return dao.insert(entity) > 0 ? true : false;
	}

	@Override
	public boolean update(Language entity) {
		return dao.update(entity) > 0 ? true : false;
	}

	@Override
	public void del(Language entity) {
		dao.del(entity.getId());
	}

	@Override
	public void del(String id) {
		dao.del(id);
	}

	@Override
	public Language save(Language entity) {
		return null;
	}

	@Override
	public Language get(String id) {
		return dao.get(id);
	}

	@Override
	public List<Language> getAllData() {
		return dao.getAllData();
	}

	@Override
	public List<Language> getByIds(String languageId) {
		return dao.getByIds(languageId);
	}

}
