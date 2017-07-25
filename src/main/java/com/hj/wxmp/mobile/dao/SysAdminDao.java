package com.hj.wxmp.mobile.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.hj.utils.DateTimeUtil;
import com.hj.utils.MD5Utils;
import com.hj.wxmp.mobile.entity.SysAdmin;

/**
* @author WangZhiYong
* @date 创建时间：2016年5月6日 上午11:57:19
* @version 1.0 
* @parameter  
* @since  
* @return  
*/

@Repository
public class SysAdminDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	private final class SysAdminMapper implements RowMapper<SysAdmin>{

		@Override
		public SysAdmin mapRow(ResultSet rs, int arg1) throws SQLException {
			SysAdmin s = new SysAdmin();
			s.setId(rs.getString("id"));
			s.setLoginId(rs.getString("login_id"));
			s.setPassword(rs.getString("password"));
			s.setUserName(rs.getString("user_name"));
			s.setCreateTime(rs.getTimestamp("create_time"));
			s.setUpdateTime(rs.getTimestamp("update_time"));
			s.setCreateUserId(rs.getString("create_user_id"));
			s.setUpdateUserId(rs.getString("update_user_id"));
			s.setRelationTable(rs.getString("relation_table"));
			s.setRelationTableId(rs.getString("relation_table_id"));
			s.setUserType(rs.getString("user_type"));
			s.setSex(rs.getString("sex"));
			s.setRemark(rs.getString("remark"));
			s.setDelFlag(rs.getInt("del_flag"));
			s.setHeadimgurl(rs.getString("headimgurl"));
			return s;
		}
		
	}
	
	/**
	 * 查询所有管理员
	 */
	public List<SysAdmin> findAll(){
		String sql = "select * from sys_admin order by create_time desc";
		return this.jdbcTemplate.query(sql, new SysAdminMapper());
	}
	
	/**
	 * 添加管理员
	 * @param sysAdmin
	 */
	 
	public void add(final SysAdmin sysAdmin){
		String sql = "insert into sys_admin(id,login_id,password,user_name,create_time,update_time,create_user_id,update_user_id,relation_table,relation_table_id,user_type,sex,remark) values(?,?,?,?,NOW(),NOW(),?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, sysAdmin.getId());
				ps.setString(2, sysAdmin.getLoginId());
				ps.setString(3, MD5Utils.MD5("123456"));
				ps.setString(4, sysAdmin.getUserName());
				ps.setString(5, sysAdmin.getCreateUserId());
				ps.setString(6, sysAdmin.getUpdateUserId());
				ps.setString(7, sysAdmin.getRelationTable());
				ps.setString(8, sysAdmin.getRelationTableId());
				ps.setString(9, sysAdmin.getUserType());
				ps.setString(10, sysAdmin.getSex());
				ps.setString(11, sysAdmin.getRemark());
			}
		});
	}
	
	/**
	 * 根据id删除管理员
	 * @param id
	 */
	public void del(String id){
		String sql = "delete from sys_admin where id=?";
		this.jdbcTemplate.update(sql,id);
	}
	
	/**
	 * 根据id删除多个管理员
	 * @param id
	 */
	public void dels(String id){
		String sql = "delete from sys_admin where id in("+id+")";
		this.jdbcTemplate.update(sql);
	}
	
	
	/**
	 * 修改管理员信息
	 * @param sysAdmin
	 */
	public void update(final SysAdmin sysAdmin){
		String sql = "update sys_admin set login_id=?,user_name=?,update_user_id=?,update_time=?,user_type=?,remark=? where id=?";
		this.jdbcTemplate.update(sql,new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, sysAdmin.getLoginId());
				ps.setString(2, sysAdmin.getUserName());
				ps.setString(3, sysAdmin.getUpdateUserId());
				try {
					ps.setDate(4,  DateTimeUtil.getSqlDate(sysAdmin.getUpdateTime()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ps.setString(5, sysAdmin.getUserType());
				ps.setString(6, sysAdmin.getRemark());
				ps.setString(7, sysAdmin.getId());
			}
		});
	}
	
	public SysAdmin getSysAdminByLoginId(String loginid){
		String sql = "select * from sys_admin where login_id=?";
		try {
			return this.jdbcTemplate.queryForObject(sql, new SysAdminMapper(),loginid);
		} catch (DataAccessException e) {
			return null;
		}
	}
	
}
