package com.hj.wxmp.mobile.mapping;

import com.hj.wxmp.mobile.entity.importMapUserCust;

public interface importMapUserCustMapper {
    int deleteByPrimaryKey(String id);

    int insert(importMapUserCust record);

    int insertSelective(importMapUserCust record);

    importMapUserCust selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(importMapUserCust record);

    int updateByPrimaryKey(importMapUserCust record);
}