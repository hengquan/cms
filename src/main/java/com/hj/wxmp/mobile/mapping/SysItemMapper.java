package com.hj.wxmp.mobile.mapping;

import com.hj.wxmp.mobile.entity.SysItem;

public interface SysItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysItem record);

    int insertSelective(SysItem record);

    SysItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysItem record);

    int updateByPrimaryKey(SysItem record);
}