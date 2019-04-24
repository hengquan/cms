package com.hj.web.mapping;

import com.hj.web.entity.ThirdUser;

public interface ThirdUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(ThirdUser record);

    int insertSelective(ThirdUser record);

    ThirdUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ThirdUser record);

    int updateByPrimaryKeyWithBLOBs(ThirdUser record);

    int updateByPrimaryKey(ThirdUser record);
}