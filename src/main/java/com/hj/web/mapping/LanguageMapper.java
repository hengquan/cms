package com.hj.web.mapping;

import java.util.List;

import com.hj.web.entity.Language;

public interface LanguageMapper {
    int del(String id);

    int insert(Language record);

    Language get(String id);

    int update(Language record);

    List<Language> getAllData();

		List<Language> getByIds(String languageId);
}