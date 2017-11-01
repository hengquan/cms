package com.hj.wxmp.mobile.mapping;

import java.util.List;

import com.hj.wxmp.mobile.entity.ImportMapUserCust;

public interface ImportMapUserCustMapper {
    int deleteByPrimaryKey(String id);

    int insert(ImportMapUserCust record);

    int insertSelective(ImportMapUserCust record);

    ImportMapUserCust selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ImportMapUserCust record);

    int updateByPrimaryKey(ImportMapUserCust record);

    List<ImportMapUserCust> selectByUserName(String realname);
}