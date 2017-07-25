package com.hj.wxmp.mobile.mapping;

import com.hj.wxmp.mobile.entity.DictD;

public interface DictDMapper {
    int deleteByPrimaryKey(String id);

    int insert(DictD record);

    int insertSelective(DictD record);

    DictD selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DictD record);

    int updateByPrimaryKey(DictD record);
}