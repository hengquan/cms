package com.hj.wxmp.mobile.mapping;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.ProjUserRole;
import com.hj.wxmp.mobile.entity.UserInfo;

public interface ProjUserRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProjUserRole record);

    int insertSelective(ProjUserRole record);

    ProjUserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProjUserRole record);

    int updateByPrimaryKey(ProjUserRole record);

	List<Map<String, Object>> selectByUserId(String userId);

	Boolean deleteByProjIds(String projIDs);

	void deleteByProjIdAndUserId(Map<String, Object> datamsg);

	List<Map<String, Object>> selectByProjId(String projId);

	List<Map<String, Object>> selectByProjIdAndUserId(Map<String, Object> result);

	List<UserInfo> selectProjUserDataByUserIdFZR(String userid);

	List<UserInfo> selectProjUserDataByUserIdGLY(String userid);

	List<UserInfo> selectProjUserDataByProjIdGLY(String projId);

	List<UserInfo> selectProjUserDataByProjIdFZR(String projId);

	List<ProjUserRole> findAll();

	List<ProjUserRole> findByProjIds(String projIds);

	List<Map<String, Object>> findByUserId(Map<String, Object> map);

	Integer findByUserIdCount(Map<String, Object> map);
}