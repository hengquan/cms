package com.hj.wxmp.mobile.mapping;

import com.hj.wxmp.mobile.entity.DictDBAK;

public interface DictDBAKMapper {
    int deleteByPrimaryKey(String id);

    int insert(DictDBAK record);

    int insertSelective(DictDBAK record);

    DictDBAK selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DictDBAK record);

    int updateByPrimaryKey(DictDBAK record);
}