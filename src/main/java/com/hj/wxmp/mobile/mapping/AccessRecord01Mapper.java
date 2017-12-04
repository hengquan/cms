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

	List<Map<String, Object>> getRecord01ListGuWen(Map<String, Object> result);

	List<Map<String, Object>> getRecord01ListGuanLi(Map<String, Object> result);

	List<Map<String, Object>> getRecord01ListFuZe(Map<String, Object> result);

	List<Map<String, Object>> getRecord01ListAdmin(Map<String, Object> result);

	Integer findByCustIdCount(Map<String,Object> data);

	Integer selectMessageCount(Map<String, Object> map);

	List<AccessRecord01> selectByUserId(Map<String,Object> datamsg);

	void updateByProjIdAndCustId(Map<String, Object> parmeterMap);

	Integer selectByCustIdAndProjId(Map<String, Object> parmeterMap);

	List<AccessRecord01> selectByRecepTime(String date);

	AccessRecord01 selectByPhone(String phone);

	List<AccessRecord01> selectNotInAuditRecord();
}