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

import com.hj.web.entity.SysItemRole;

/**
* @author denghemei
* @date 创建时间：2016年6月17日 上午11:57:19
* @version 1.0 
* @parameter  
* @since  
* @return  
*/

@Repository
public class SysItemRoleDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	private final class SysItemRoleMapper implements RowMapper<SysItemRole>{

		@Override
		public SysItemRole mapRow(ResultSet rs, int arg1) throws SQLException {
			SysItemRole s = new SysItemRole();
			s.setId(rs.getString("id"));
			s.setItemId(rs.getString("item_id"));
			s.setRoleId(rs.getString("role_id"));
			
			s.setItemName(rs.getString("item_name"));
			s.setItemUrl(rs.getString("item_url"));
			s.setParentId(rs.getString("parent_id"));
			s.setIsLeaf(rs.getInt("is_leaf"));
			s.setLeafLevel(rs.getInt("leaf_level"));
			s.setIsGrade(rs.getString("is_grade"));
			s.setIconImg(rs.getString("icon_img"));
			s.setVisibleFlag(rs.getInt("visible_flag"));
			s.setSeqNum(rs.getInt("seq_num"));
			s.setRemark(rs.getString("remark"));
			
			s.setRoleName(rs.getString("role_name"));
			return s;
		}
		
	}
	
	/**
	 * 查询所有角色权限
	 */
	public List<SysItemRole> findAll(){
		String sql = "select i.*,t.*,r.role_name from sys_item_role i INNER JOIN sys_item t on i.item_id=t.id INNER JOIN sys_role r on i.role_id=r.id ";
		return this.jdbcTemplate.query(sql, new SysItemRoleMapper());
	}
	
	/**
	 * 根据角色ID查看角色权限
	 */
	/*public List<SysItemRole> selectByRoleId(String roleId){
		String sql = "select i.*,t.*,r.role_name from sys_item_role i INNER JOIN sys_item t on i.item_id=t.id INNER JOIN sys_role r on i.role_id=r.id where i.role_id='"+roleId+"'";
		return this.jdbcTemplate.query(sql, new SysItemRoleMapper());
	}*/
	public List<SysItemRole> selectByRoleId(String roleId){
		String sql = "select i.*,t.*,r.role_name from sys_item_role i right JOIN sys_item t on i.item_id=t.id left JOIN sys_role r on i.role_id=r.id where i.role_id='"+roleId+"' union select i.*,t.*,r.role_name from sys_item_role i right JOIN sys_item t on i.item_id=t.id left JOIN sys_role r on i.role_id=r.id where t.parent_id in(select st.id from sys_item_role rt inner join sys_item st on rt.item_id=st.id where rt.role_id='"+roleId+"')";
		return this.jdbcTemplate.query(sql, new SysItemRoleMapper());
	}
	
	
	public List<SysItemRole> selectItemByRoleId(String roleId){
		String sql = "select i.*,t.*,r.role_name from sys_item_role i INNER JOIN sys_item t on i.item_id=t.id INNER JOIN sys_role r on i.role_id=r.id where i.role_id='"+roleId+"' and t.parent_id='' and t.visible_flag=1 order by seq_num";
		return this.jdbcTemplate.query(sql, new SysItemRoleMapper());
	}	
	
			
	public List<SysItemRole> selectItemByPId(String roleId){
		String sql = "select i.*,t.*,r.role_name from sys_item_role i right JOIN sys_item t on i.item_id=t.id left JOIN sys_role r on i.role_id=r.id where  i.role_id='"+roleId+"' and t.parent_id!='' and t.visible_flag=1 order by seq_num";
		return this.jdbcTemplate.query(sql, new SysItemRoleMapper());
	}
	/**
	 * 添加角色权限信息
	 * @param sysItem
	 */
	public void add(final SysItemRole sysItemRole){
		String sql = "insert into sys_item_role(id,item_id,role_id) values(?,?,?)";
		jdbcTemplate.update(sql,new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, sysItemRole.getId());
				ps.setString(2, sysItemRole.getItemId());
				ps.setString(3, sysItemRole.getRoleId());
				
			}
		});
	}
	
	/**
	 * 根据id删除菜单
	 * @param id
	 */
	public void del(String id){
		String sql = "delete from sys_item_role where id=?";
		this.jdbcTemplate.update(sql,id);
	}
	
	/**
	 * 根据角色Id删除菜单
	 * 
	 */
	public int delByRoleId(String roleId){
		String sql = "delete from sys_item_role where role_id=?";
		int row=this.jdbcTemplate.update(sql,roleId);
		return row;
	}
	
	/**
	 * 根据id删除多个菜单
	 * @param id
	 */
	public void dels(String id){
		String sql = "delete from sys_item_role where id in("+id+")";
		this.jdbcTemplate.update(sql);
	}
	
	
	/**
	 * 修改角色权限信息
	 * @param sysItem
	 */
	public void update(final SysItemRole sysItemRole){
		String sql = "update sys_item_role set item_id=?,role_id=? where id=?";
		this.jdbcTemplate.update(sql,new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, sysItemRole.getItemId());
				ps.setString(2, sysItemRole.getRoleId());
				ps.setString(3, sysItemRole.getId());
				
			}
		});
	}
	
	
	public SysItemRole getSysItemRoleById(String id){
		String sql = "select i.*,t.*,r.role_name from sys_item_role i INNER JOIN sys_item t on i.item_id=t.id INNER JOIN sys_role r on i.role_id=r.id where i.id=?";
		try {
			return this.jdbcTemplate.queryForObject(sql, new SysItemRoleMapper(),id);
		} catch (DataAccessException e) {
			return null;
		}
	}
	
}
