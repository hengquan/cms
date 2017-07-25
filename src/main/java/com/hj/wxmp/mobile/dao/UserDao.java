package com.hj.wxmp.mobile.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hj.wxmp.mobile.entity.UserInfo;
//import com.hj.wxmp.mobile.mode.CompanyVO;

/**
* @author WangZhiYong
* @date 创建时间：2016年5月14日 下午3:25:48
* @version 1.0 
* @parameter  
* @since  
* @return  
*/

@Repository
public class UserDao extends BaseDao
{

	public List<UserInfo> getAllSysUsers(){
		String sql = "select * from user_info";
		return find(sql, null, UserInfo.class);
	}
	
	public UserInfo getUserByOpenId(String openid){
		String sql = "select * from sys_user where openid = ?";
		return findObject(sql, new Object[]{openid}, UserInfo.class);
	}
	
	public UserInfo getUserById(String id){
		String sql = "select * from sys_user where id = ?";
		return findObject(sql, new Object[]{id}, UserInfo.class);
	}
	
	
	
	
	public List<UserInfo> getSysUserByVideoPv(String companyId){
		String sql = "select u.*,c.company_name as companyName "
				+ "from sys_user u left join tbl_company c "
				+ "on u.company_id = c.id where u.company_id=? order by u.video_pv desc";
		return find(sql, new Object[]{companyId}, UserInfo.class);
	}
	public void updateUserAge(int age,String openid){
		String sql = "update sys_user set age=? where openid=?";
		Object[] obj = new Object[]{age,openid};
		addOrUpdate(sql, obj);
	}
	
	
	
	public int updateTelByOpenid(String tel,String openid,String idnNum){
		String sql = "update sys_user set tel=? where openid=? and idn_num=?";
		return this.jdbcTemplate.update(sql,new Object[]{tel,openid,idnNum});
	}

	/**
	 * 根据id删除管理员
	 * @param id
	 */
	public void del(String id){
		String sql = "delete from sys_user where id=?";
		this.jdbcTemplate.update(sql,id);
	}
	
	/**
	 * 根据id删除多个管理员
	 * @param id
	 */
	public void dels(String id){
		String sql = "delete from sys_user where id in("+id+")";
		this.jdbcTemplate.update(sql);
	}
	
	public List<UserInfo> getAllSysUser(){
		String sql = "select * from sys_user ";
		return find(sql, null, UserInfo.class);
	}
	
	public void addintegralNum(String openid,int score){
		String sql = "update sys_user set integral_num=integral_num+"+score+" where openid='"+openid+"'";
		this.jdbcTemplate.update(sql);
	}
	
	public void addSuggestNum(String openid){
		String sql = "update sys_user set suggest_num=suggest_num+1 where openid='"+openid+"'";
		this.jdbcTemplate.update(sql);
	}
} 
