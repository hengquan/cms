package com.hj.web.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.hj.web.dao.UserDao;
import com.hj.web.entity.UserInfo;
import com.hj.web.mapping.UserInfoMapper;
import com.hj.web.services.IKeyGen;
import com.hj.web.services.UserInfoService;

@Component
public class UserInfoServiceImpl implements UserInfoService {

	@Resource
	private IKeyGen keyGen;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public boolean insert(UserInfo entity) throws Exception {
		entity.setId(keyGen.getUUIDKey());
		boolean result = userInfoMapper.insertSelective(entity) > 0 ? true : false;
		
		return result;
	}

	@Override
	public boolean update(UserInfo entity) throws Exception {
		return userInfoMapper.updateByPrimaryKeySelective(entity) > 0 ? true : false;
	}

	@Override
	public List<UserInfo> listEntity(Map<String, Object> map) throws Exception {
		return null;
	}

	@Override
	public void delete(UserInfo entity) throws Exception {
		userInfoMapper.deleteByPrimaryKey(entity.getId());
	}

	@Override
	public UserInfo saveEntity(UserInfo entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserInfo findById(String sys_uuid) throws Exception {
		return userInfoMapper.selectByPrimaryKey(sys_uuid);
	}

	@Override
	public UserInfo findByOpenid(String openid) throws Exception {
		return userInfoMapper.findByOpenid(openid);
	}

	@Override
	public int perfectUserInfo(UserInfo sysUser) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<UserInfo> findAll() {
		return userInfoMapper.findAll();
	}

	@Override
	public int selectCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<UserInfo> getListMessge() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUserType(String byOperateUserId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<UserInfo> getMessge(Map<String, Object> map) {
		return userInfoMapper.getMessge(map);
	}

	@Override
	public Integer getMessgeCount(Map<String, Object> map) {
		return userInfoMapper.getMessgeCount(map);
	}

	@Override
	public void del(String byid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dels(String[] str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUserState(String setExpertId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUserStates(String[] strs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<UserInfo> selectExperMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> findByOpenId(String openid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> selectOneExpert(String openid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserInfo> selectManyId(String[] strs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserInfo selectByOpenId(String openid) {
		return userInfoMapper.selectByOpenid(openid);
	}

	@Override
	public List<Map<String, Object>> selectExpertImgMsg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> selectExpertMsg(String expertId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> selectOneExpertMsg(String expertid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByOpenid(UserInfo user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<UserInfo> selectByZjda(int nowPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserInfo> selectByZj() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectByZjdaCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<UserInfo> selectByProblemNum(int nowPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserInfo> selectByProblem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserInfo> selectByEavesdropNum(int nowPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserInfo> selectByEavesdrop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserInfo> selectByAnswerNum(int nowPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserInfo> selectByAnswer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectByAnswerNumCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<UserInfo> getMostOnlineMessge(Map<String, Object> map) {
		return userInfoMapper.getMostOnlineMessge(map);
	}

	@Override
	public Integer getMostOnlineMessgeCount(Map<String, Object> map) {
		return userInfoMapper.getMostOnlineMessgeCount(map);
	}

	@Override
	public List<UserInfo> selectParentId(String openid) {
		return userInfoMapper.selectParentId(openid);
	}

	@Override
	public List<UserInfo> selectByMoney(int nowPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserInfo> selectByMoneyOne() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserInfo> selectByIntegral(int nowPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserInfo> selectByIntegralOne() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSelectUserType(String[] str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<UserInfo> selectAll() {
		return userInfoMapper.selectAll();
	}

	@Override
	public List<UserInfo> selectByParentId(String parentId) {
		return userInfoMapper.selectByParentId(parentId);
	}

	@Override
	public List<UserInfo> selectByName(String userName) {
		return userInfoMapper.selectByName(userName);
	}

	@Override
	public void deletes(String boxeditId) {
		userInfoMapper.deletes(boxeditId);
	}


}
