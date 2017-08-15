package com.hj.wxmp.mobile.mapping;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.AccessRecord01;

public interface AccessRecord01Mapper {
    int deleteByPrimaryKey(String id);

    int insert(AccessRecord01 record);

    int insertSelective(AccessRecord01 record);

    AccessRecord01 selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AccessRecord01 record);

    int updateByPrimaryKey(AccessRecord01 record);

	List<Map<String, Object>> selectMessage(Map<String, Object> map);

	void dels(String boxeditId);

	List<Map<String, Object>> selectUserMsy(Map<String, Object> msg);

	List<Map<String, Object>> getRecord01List(Map<String, Object> result);
}