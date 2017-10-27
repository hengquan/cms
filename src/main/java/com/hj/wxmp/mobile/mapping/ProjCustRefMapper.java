package com.hj.wxmp.mobile.mapping;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.ProjCustRef;

public interface ProjCustRefMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProjCustRef record);

    int insertSelective(ProjCustRef record);

    ProjCustRef selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProjCustRef record);

    int updateByPrimaryKey(ProjCustRef record);

	List<Map<String, Object>> selectByUserId(String userId);

	ProjCustRef selectByCusIdAndProjId(Map<String, Object> result);

	List<Map<String, Object>> selectByProjIdAndCusId(Map<String, Object> result);

	List<ProjCustRef> findAll();

	ProjCustRef selctByCustId(String tabid);
}