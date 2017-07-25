package com.hj.wxmp.mobile.mapping;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.ProjUserRole;

public interface ProjUserRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProjUserRole record);

    int insertSelective(ProjUserRole record);

    ProjUserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProjUserRole record);

    int updateByPrimaryKey(ProjUserRole record);

	List<Map<String, Object>> selectByUserId(String userId);
}