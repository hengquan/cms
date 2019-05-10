package com.hj.web.services;

import java.util.List;
import com.hj.web.entity.Language;

public interface LanguageService {

	public boolean insert(Language entity);

	boolean update(Language entity);

	public void del(Language entity);

	public void del(String id);

	public Language save(Language entity);

	public Language get(String id);

	public List<Language> getAllData();

	public List<Language> getByIds(String languageId);
}
