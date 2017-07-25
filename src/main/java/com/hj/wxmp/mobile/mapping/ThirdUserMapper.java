package com.hj.wxmp.mobile.mapping;

import com.hj.wxmp.mobile.entity.ThirdUser;

public interface ThirdUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(ThirdUser record);

    int insertSelective(ThirdUser record);

    ThirdUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ThirdUser record);

    int updateByPrimaryKeyWithBLOBs(ThirdUser record);

    int updateByPrimaryKey(ThirdUser record);
}