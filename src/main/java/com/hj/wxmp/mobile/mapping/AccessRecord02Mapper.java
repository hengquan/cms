package com.hj.wxmp.mobile.mapping;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.AccessRecord02;

public interface AccessRecord02Mapper {
    int deleteByPrimaryKey(String id);

    int insert(AccessRecord02 record);

    int insertSelective(AccessRecord02 record);

    AccessRecord02 selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AccessRecord02 record);

    int updateByPrimaryKey(AccessRecord02 record);

	List<Map<String, Object>> selectMessage(Map<String, Object> map);

	void dels(String boxeditId);

	List<Map<String, Object>> getRecord02List(Map<String, Object> result);
}