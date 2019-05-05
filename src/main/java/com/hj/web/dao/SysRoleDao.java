package com.hj.web.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hj.web.entity.SysRole;

/**
* @author denghemei
* @date 创建时间：2016年6月17日 上午11:57:19
* @version 1.0 
* @parameter  
* @since  
* @return  
*/

@Repository
public class SysRoleDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	private final class SysRoleMapper implements RowMapper<SysRole>{

		@Override
		public SysRole mapRow(ResultSet rs, int arg1) throws SQLException {
			SysRole s = new SysRole();
			s.setId(rs.getString("id"));
			s.setRoleName(rs.getString("role_name"));
			s.setPinyin(rs.getString("pinyin"));
			s.setLogogram(rs.getString("logogram"));
			s.setRemark(rs.getString("remark"));
			return s;
		}
		
	}
	
	/**
	 * 查询所有角色
	 */
	public List<SysRole> findAll(){
		String sql = "select * from sys_role";
		return this.jdbcTemplate.query(sql, new SysRoleMapper());
	}
	
	/**
	 *根据角色名称查询是否存在
	 */
	public SysRole findByRoleName(String roleName){
		String sql = "select * from sys_role where role_name=?";
		try {
			return this.jdbcTemplate.queryForObject(sql, new SysRoleMapper(),roleName);
		} catch (DataAccessException e) {
			return null;
		}
	}
	
	/**
	 * 添加角色
	 * @param sysRole
	 */
	 
	public void add(final SysRole sysRole){
		String sql = "insert into sys_role(id,role_name,pinyin,logogram,remark) values(?,?,?,?,?)";
		jdbcTemplate.update(sql,new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, sysRole.getId());
				ps.setString(2, sysRole.getRoleName());
				ps.setString(3, sysRole.getPinyin());
				ps.setString(4, sysRole.getLogogram());
				ps.setString(5, sysRole.getRemark());
			}
		});
	}
	
	/**
	 * 根据id删除角色
	 * @param id
	 */
	public void del(String id){
		String sql = "delete from sys_role where id=?";
		this.jdbcTemplate.update(sql,id);
	}
	
	/**
	 * 根据id删除多个角色
	 * @param id
	 */
	public void dels(String id){
		String sql = "delete from sys_role where id in("+id+")";
		this.jdbcTemplate.update(sql);
	}
	
	
	/**
	 * 修改角色信息
	 * @param sysRole
	 */
	public void update(final SysRole sysRole){
		String sql = "update sys_role set role_name=?,pinyin=?,remark=? where id=?";
		this.jdbcTemplate.update(sql,new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, sysRole.getRoleName());
				ps.setString(2, sysRole.getPinyin());
				ps.setString(3, sysRole.getRemark());
				ps.setString(4, sysRole.getId());
			}
		});
	}
	
	public SysRole getSysRoleById(String id){
		String sql = "select * from sys_role where id=?";
		try {
			return this.jdbcTemplate.queryForObject(sql, new SysRoleMapper(),id);
		} catch (DataAccessException e) {
			return null;
		}
	}

	public List<SysRole> findParentById(String id) {
		String sql = "select * from sys_role where logogram = ?";
		return this.jdbcTemplate.query(sql, new SysRoleMapper(),id);
	}

	public List<SysRole> findParentList() {
		String sql = "select * from sys_role where logogram in ('0','1')";
		return this.jdbcTemplate.query(sql, new SysRoleMapper());
	}
}
