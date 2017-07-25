package com.hj.wxmp.mobile.mapping;

import com.hj.wxmp.mobile.entity.TblArticle;

public interface TblArticleMapper {
    int deleteByPrimaryKey(String id);

    int insert(TblArticle record);

    int insertSelective(TblArticle record);

    TblArticle selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TblArticle record);

    int updateByPrimaryKeyWithBLOBs(TblArticle record);

    int updateByPrimaryKey(TblArticle record);

    String selectMsg();
}