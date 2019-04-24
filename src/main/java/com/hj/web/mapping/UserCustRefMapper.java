package com.hj.web.mapping;

import java.util.List;
import java.util.Map;

import com.hj.web.entity.UserCustRef;

public interface UserCustRefMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserCustRef record);

    int insertSelective(UserCustRef record);

    UserCustRef selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserCustRef record);

    int updateByPrimaryKey(UserCustRef record);

	List<Map<String, Object>> selectByUserId(String userId);

	List<Map<String, Object>> selectByProjectId(String proId);

	List<Map<String, Object>> findByProjectId(String proId);

	List<Map<String, Object>> selectByUserMessge(Map<String, Object> map);

	Integer selectByUserMessgeCount(Map<String, Object> map);

	Integer updateByProjIdAndCustId(Map<String, Object> parmeterMap);

	List<Map<String, Object>> downloadExcel(Map<String, Object> map);

	List<Map<String, Object>> selectZongHe(Map<String, Object> map);

	Integer selectZongHeCount(Map<String, Object> map);

	List<UserCustRef> findAll();

	List<Map<String, Object>> selectBySpecialUserMessge(Map<String, Object> map);

	Integer selectBySpecialUserMessgeCount(Map<String, Object> map);

	UserCustRef selectByData(Map<String, Object> data);

	List<UserCustRef> selectByProjIdAndCustId(Map<String, Object> parmeterMap);
}