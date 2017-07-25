package com.hj.wxmp.mobile.mapping;

import com.hj.wxmp.mobile.entity.DictM_BAK;

public interface DictM_BAKMapper {
    int deleteByPrimaryKey(String id);

    int insert(DictM_BAK record);

    int insertSelective(DictM_BAK record);

    DictM_BAK selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DictM_BAK record);

    int updateByPrimaryKey(DictM_BAK record);
}