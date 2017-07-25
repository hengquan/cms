package com.hj.wxmp.mobile.mapping;

import com.hj.wxmp.mobile.entity.DictM;

public interface DictMMapper {
    int deleteByPrimaryKey(String id);

    int insert(DictM record);

    int insertSelective(DictM record);

    DictM selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DictM record);

    int updateByPrimaryKey(DictM record);
}