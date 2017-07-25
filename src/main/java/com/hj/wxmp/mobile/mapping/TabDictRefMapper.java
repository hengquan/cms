package com.hj.wxmp.mobile.mapping;

import com.hj.wxmp.mobile.entity.TabDictRef;

public interface TabDictRefMapper {
    int deleteByPrimaryKey(String id);

    int insert(TabDictRef record);

    int insertSelective(TabDictRef record);

    TabDictRef selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TabDictRef record);

    int updateByPrimaryKey(TabDictRef record);
}