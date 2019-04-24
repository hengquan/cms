package com.hj.web.mapping;

import java.util.List;
import java.util.Map;

import com.hj.web.entity.TabDictRef;

public interface TabDictRefMapper {
    int deleteByPrimaryKey(String id);

    int insert(TabDictRef record);

    int insertSelective(TabDictRef record);

    TabDictRef selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TabDictRef record);

    int updateByPrimaryKey(TabDictRef record);

	TabDictRef selectCusIdAndTableName(Map<String, Object> map);

	void delete4TabColum(TabDictRef tdr);

	List<TabDictRef> selectCognitiveCaseChannel();

	List<TabDictRef> selectConcern();

	List<TabDictRef> leadTime();

	List<TabDictRef> selectResistPoint();
}