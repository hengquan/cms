package com.hj.web.mapping;

import java.util.List;
import java.util.Map;

import com.hj.web.entity.UserInfo;

public interface UserInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
    
    //根据openid查找相当信息
	UserInfo selectByOpenid(String openid);
	
	//查找所有用户的信息
	List<UserInfo> selectAll();
	
	//分页查询所有会员信息
	List<UserInfo> getMessge(Map<String, Object> map);
	
	//分页查询所有会员信息-总记录
	Integer getMessgeCount(Map<String, Object> map);

	List<UserInfo> getMostOnlineMessge(Map<String, Object> map);

	Integer getMostOnlineMessgeCount(Map<String, Object> map);

	List<UserInfo> selectParentId(String openid);

	UserInfo findByOpenid(String openid);

	List<UserInfo> selectByParentId(String parentId);

	UserInfo selectByLoginId(String loginId);

	List<UserInfo> findAll();

	List<UserInfo> selectByName(String userName);

	void deletes(String boxeditId);

	
}