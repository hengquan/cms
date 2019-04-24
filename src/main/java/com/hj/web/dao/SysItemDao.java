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

import com.hj.web.entity.SysItem;

/**
* @author denghemei
* @date 创建时间：2016年6月17日 上午11:57:19
* @version 1.0 
* @parameter  
* @since  
* @return  
*/

@Repository
public class SysItemDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	private final class SysItemMapper implements RowMapper<SysItem>{

		@Override
		public SysItem mapRow(ResultSet rs, int arg1) throws SQLException {
			SysItem s = new SysItem();
			s.setId(rs.getString("id"));
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
			return s;
		}
		
	}
	
	/**
	 * 查询所有菜单
	 */
	public List<SysItem> findAll(){
		String sql = "select * from sys_item order by seq_num";//CAST(seq_num AS int) ASC
		return this.jdbcTemplate.query(sql, new SysItemMapper());
	}
	
	public List<SysItem> findAllByParent(String parentId){
		String sql = "select * from sys_item where parent_id='"+parentId+"' and visible_flag=1 order by seq_num asc" ;
		return this.jdbcTemplate.query(sql, new SysItemMapper());
	}
	
	public List<SysItem> findAllByPa(String parentId){
		String sql = "select * from sys_item where parent_id='"+parentId+"' order by seq_num ASC" ;
		return this.jdbcTemplate.query(sql, new SysItemMapper());
	}
	
	/**
	 * 根据菜单名称查询是否存在
	 * @author deng.hemei
	 * @description 
	 * @return 
	 * @updateDate 2016年7月13日
	 */
	public SysItem selectByItemName(String itemName){
		String sql = "select * from sys_item where item_name=?";
		try {
			return this.jdbcTemplate.queryForObject(sql, new SysItemMapper(),itemName);
		} catch (DataAccessException e) {
			return null;
		}
	}
	
	/**
	 * 添加菜单
	 * @param sysItem
	 */
	 
	public void add(final SysItem sysItem){
		String sql = "insert into sys_item(id,item_name,item_url,parent_id,is_leaf,leaf_level,is_grade,icon_img,visible_flag,seq_num,remark) values(?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, sysItem.getId());
				ps.setString(2, sysItem.getItemName());
				ps.setString(3, sysItem.getItemUrl());
				ps.setString(4, sysItem.getParentId());
				ps.setInt(5, sysItem.getIsLeaf());
				ps.setInt(6, sysItem.getLeafLevel());
				ps.setString(7, sysItem.getIsGrade());
				ps.setString(8, sysItem.getIconImg());
				ps.setInt(9, sysItem.getVisibleFlag());
				ps.setInt(10, sysItem.getSeqNum());
				ps.setString(11, sysItem.getRemark());
			}
		});
	}
	
	/**
	 * 根据id删除菜单
	 * @param id
	 */
	public void del(String id){
		String sql = "delete from sys_item where id=?";
		this.jdbcTemplate.update(sql,id);
	}
	
	/**
	 * 根据id删除多个菜单
	 * @param id
	 */
	public void dels(String id){
		String sql = "delete from sys_item where id in("+id+")";
		this.jdbcTemplate.update(sql);
	}
	
	
	/**
	 * 修改菜单信息
	 * @param sysItem
	 */
	public void update(final SysItem sysItem){
		String sql = "update sys_item set item_name=?,item_url=?,parent_id=?,is_leaf=?,leaf_level=?,is_grade=?,icon_img=?,visible_flag=?,seq_num=?,remark=? where id=?";
		this.jdbcTemplate.update(sql,new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, sysItem.getItemName());
				ps.setString(2, sysItem.getItemUrl());
				ps.setString(3, sysItem.getParentId());
				ps.setInt(4, sysItem.getIsLeaf());
				ps.setInt(5, sysItem.getLeafLevel());
				ps.setString(6, sysItem.getIsGrade());
				ps.setString(7, sysItem.getIconImg());
				ps.setInt(8, sysItem.getVisibleFlag());
				ps.setInt(9, sysItem.getSeqNum());
				ps.setString(10, sysItem.getRemark());
				ps.setString(11, sysItem.getId());
			}
		});
	}
	
	public SysItem getSysItemById(String id){
		String sql = "select * from sys_item where id=?";
		try {
			return this.jdbcTemplate.queryForObject(sql, new SysItemMapper(),id);
		} catch (DataAccessException e) {
			return null;
		}
	}
	
}
