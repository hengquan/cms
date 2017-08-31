package com.hj.wxmp.mobile.mapping;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.AccessRecord03;

public interface AccessRecord03Mapper {
    int deleteByPrimaryKey(String id);

    int insert(AccessRecord03 record);

    int insertSelective(AccessRecord03 record);

    AccessRecord03 selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AccessRecord03 record);

    int updateByPrimaryKey(AccessRecord03 record);

	List<Map<String, Object>> selectMessage(Map<String, Object> map);

	void dels(String boxeditId);

	List<Map<String, Object>> getRecord03List(Map<String, Object> result);

	Integer findByCustIdCount(Map<String,Object> data);

	List<AccessRecord03> selectByUserId(Map<String, Object> datamsg);

	Integer selectMessageCount(Map<String, Object> map);

	List<Map<String, Object>> getRecord03ListGuWen(Map<String, Object> result);

	List<Map<String, Object>> getRecord03ListGuanLi(Map<String, Object> result);

	List<Map<String, Object>> getRecord03ListFuZe(Map<String, Object> result);

	List<Map<String, Object>> getRecord03ListAdmin(Map<String, Object> result);
}