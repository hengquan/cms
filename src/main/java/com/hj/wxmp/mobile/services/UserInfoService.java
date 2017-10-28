package com.hj.wxmp.mobile.services;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.UserInfo;

public interface UserInfoService {
	/**
	 * 新增数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	public boolean insert(UserInfo entity) throws Exception;
	
	/**
	 * 更新数据
	 * @param valid
	 * @return
	 * @throws Exception
	 */
	boolean update(UserInfo entity) throws Exception;
	
	/**
	 * 查找数据
	 * @return
	 * @throws Exception
	 */
	public List<UserInfo> listEntity(Map<String,Object> map) throws Exception;
	
	/**
	 * 删除数据
	 * @return
	 * @throws Exception
	 */
	public void delete(UserInfo entity) throws Exception;
	
	/**
	 * 保存数据
	 * @param entity
	 * @throws Exception
	 */
	public UserInfo saveEntity(UserInfo entity) throws Exception;
	
	/**
	 * 通过ID查找
	 * @param id
	 * @return 
	 * @throws Exception
	 */
	public UserInfo findById(String sys_uuid) throws Exception;
	
	/**
	 * 通过openid查找
	 * @author jun.hai
	 * @param id
	 * @return 
	 * @throws Exception
	 */
	public UserInfo findByOpenid(String openid) throws Exception;
	
	/**
	 * 
	 * @author jun.hai 
	 * @Description: TODO(通过Id查询用户信息和扩展信息) 
	 * @param @param map
	 * @return String    返回类型 
	 * @date 2016年5月26日 上午9:47:44
	 * @throws
	 */
	public int perfectUserInfo(UserInfo sysUser);
	
	
	/**
	 * 查询所有会员信息（后台）
	 * @author deng.hemei
	 * @description 后台会员信息管理
	 * @return List<SysUser>
	 * @updateDate 2016年7月4日
	 */
	public List<UserInfo> findAll();
	
	/**
	 * 查询总记录数
	 * @author deng.hemei
	 * @description 
	 * @return 
	 * @updateDate 2016年7月11日
	 */
	public int selectCount();

	//查找所有用户信息
	public List<UserInfo> getListMessge();

	public void updateUserType(String byOperateUserId);

	public List<UserInfo> getMessge(Map<String, Object> map);

	public Integer getMessgeCount(Map<String, Object> map);

	public void del(String byid);

	public void dels(String[] str);

	public void updateUserState(String setExpertId);

	public void updateUserStates(String[] strs);

	public List<UserInfo> selectExperMessage();

	public List<Map<String, Object>> findByOpenId(String openid);

	public List<Map<String, Object>> selectOneExpert(String openid);

	public List<UserInfo> selectManyId(String[] strs);

	public UserInfo selectByOpenId(String openid);

	public List<Map<String, Object>> selectExpertImgMsg();

	public Map<String, Object> selectExpertMsg(String expertId);

	public List<Map<String, Object>> selectOneExpertMsg(String expertid);
	
	/**
	 * 根据openid完善个人信息
	 * @author deng.hemei
	 * @description 
	 * @return 
	 * @updateDate 2016年9月29日
	 */
	public int updateByOpenid(UserInfo user);
	
	/**
	 * 支招排行(最佳支招)
	 * @author deng.hemei
	 * @description 
	 * @return 
	 * @updateDate 2016年10月13日
	 */
	public List<UserInfo> selectByZjda(int nowPage,int pageSize);
	public List<UserInfo> selectByZj();
	
	/**
	 * 支招排行(最佳支招)总记录数
	 * @author deng.hemei
	 * @description 
	 * @return 
	 * @updateDate 2016年10月28日
	 */
	public int selectByZjdaCount();
	
	/**
	 * 提问排行
	 * @author deng.hemei
	 * @description 
	 * @return 
	 * @updateDate 2016年10月13日
	 */
	public List<UserInfo> selectByProblemNum(int nowPage,int pageSize);
	public List<UserInfo> selectByProblem();
	
	/**
	 * 偷听排行
	 * @author deng.hemei
	 * @description 
	 * @return 
	 * @updateDate 2016年10月13日
	 */
	public List<UserInfo> selectByEavesdropNum(int nowPage,int pageSize);
	public List<UserInfo> selectByEavesdrop();
	
	/**
	 * 贡献排行(总支招)
	 * @author deng.hemei
	 * @description 
	 * @return 
	 * @updateDate 2016年10月17日
	 */
	public List<UserInfo> selectByAnswerNum(int nowPage,int pageSize);
	public List<UserInfo> selectByAnswer();
	
	/**
	 * 贡献排行(总支招)总记录数
	 * @author deng.hemei
	 * @description 
	 * @return 
	 * @updateDate 2016年10月28日
	 */
	public int selectByAnswerNumCount();
	
	public List<UserInfo> getMostOnlineMessge(Map<String, Object> map);

	public Integer getMostOnlineMessgeCount(Map<String, Object> map);

	public List<UserInfo> selectParentId(String openid);
	
	/**
	 * 收益排行
	 * @author deng.hemei
	 * @description 
	 * @return 
	 * @updateDate 2016年10月13日
	 */
	public List<UserInfo> selectByMoney(int nowPage,int pageSize);
	public List<UserInfo> selectByMoneyOne();
	
	/**
	 * 积分排行
	 * @author deng.hemei
	 * @description 
	 * @return 
	 * @updateDate 2016年10月13日
	 */
	public List<UserInfo> selectByIntegral(int nowPage,int pageSize);
	public List<UserInfo> selectByIntegralOne();

	public void updateSelectUserType(String[] str);
	//查找所有用户信息
	public List<UserInfo> selectAll();

	public List<UserInfo> selectByParentId(String parentId);

	public List<UserInfo> selectByName(String userName);
}
