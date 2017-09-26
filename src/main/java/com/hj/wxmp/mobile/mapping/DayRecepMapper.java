package com.hj.wxmp.mobile.mapping;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.DayRecep;

public interface DayRecepMapper {
    int deleteByPrimaryKey(String id);

    int insert(DayRecep record);

    int insertSelective(DayRecep record);

    DayRecep selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DayRecep record);

    int updateByPrimaryKey(DayRecep record);

	void updateMsg(DayRecep dayRecep);

	List<Map<String, Object>> selectByTimeAnd(Map<String, Object> result);
}