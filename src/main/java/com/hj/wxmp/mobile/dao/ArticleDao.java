package com.hj.wxmp.mobile.dao;

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

import com.hj.wxmp.mobile.entity.TblArticle;

/**
* @author WangZhiYong
* @date 创建时间：2016年5月9日 下午12:02:02
* @version 1.0 
* @parameter  
* @since  
* @return  TblArticle
*/


@Repository
public class ArticleDao extends BaseDao{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private final class ArticleMapper implements RowMapper<TblArticle>{

		@Override
		public TblArticle mapRow(ResultSet rs, int arg1) throws SQLException {
			TblArticle t = new TblArticle();
			t.setId(rs.getString("id"));
			t.setCreateTime(rs.getTimestamp("create_time"));
			t.setArticleType(rs.getString("article_type"));
			t.setTitle(rs.getString("title"));
			t.setContent(rs.getString("content"));
			t.setImgUrl(rs.getString("img_url"));
			t.setArticleUrl(rs.getString("article_url"));
			t.setCreateUser(rs.getString("create_user"));
			t.setUpdateTime(rs.getTimestamp("update_time"));
			t.setUpdateUser(rs.getString("update_user"));
			t.setRemark(rs.getString("remark"));
			return t;
		}
		
	}
	
	/**
	 * 查询所有文章并按照创建日期倒叙
	 * @return
	 */
	public List<TblArticle> getArticles(String title,String articleType,int nowPage,int pageSize){
		String sql ="select  * FROM tbl_article where 1=1 ";
		if(title != null && !title.equals("")){
			sql += " and title like '%"+title+"%' ";
		}
		if(articleType != null && !articleType.equals("-1")){
			sql += " and article_type = '"+articleType+"' ";
		}
		sql += "ORDER BY create_time desc limit "+nowPage+","+pageSize;
		return find(sql, null, TblArticle.class);
	}
	
	public int getArticleCounts(String title,String articleType){
		String sql = "select count(*) from tbl_article where 1=1 ";
		
		if(title != null && !title.equals("")){
			sql += " and title like '%"+title+"%' ";
		}
		if(articleType != null && !articleType.equals("-1")){
			sql += " and article_type = '"+articleType+"' ";
		}
		try {
			return this.jdbcTemplate.queryForObject(sql, Integer.class);
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 查询文章ById
	 * @return
	 */
	public TblArticle getArticleById(String id){
		String sql = "select * from tbl_article where id=?";
		TblArticle t = null;
		try {
			t= this.jdbcTemplate.queryForObject(sql, new ArticleMapper(),id);
		} catch (DataAccessException e) {
			return t;
		}
		return t;
	}
	
	/**
	 * 添加一片新的文章
	 * @param tblArticle
	 */
	public void add(final TblArticle tblArticle){
		String sql = "insert into tbl_article(id,create_time,article_type,title,content,img_url,article_url,create_user,update_time,update_user,remark)"
				+ " values(?,NOW(),?,?,?,?,?,?,NOW(),?,?)";
		this.jdbcTemplate.update(sql,new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, tblArticle.getId());
				ps.setString(2, tblArticle.getArticleType());
				ps.setString(3, tblArticle.getTitle());
				ps.setString(4, tblArticle.getContent());
				ps.setString(5, tblArticle.getImgUrl());
				ps.setString(6, tblArticle.getArticleUrl());
				ps.setString(7, tblArticle.getCreateUser());
				ps.setString(8, tblArticle.getUpdateUser());
				ps.setString(9, tblArticle.getRemark());
			}
		});
	}
	
	/**
	 * 更新文章內容
	 * @param tblArticle
	 */
	public void update(final TblArticle tblArticle){
		String sql = "update tbl_article set title=?,article_type=?,content=?,"
				+ "update_time=NOW(),update_user=?,article_url=?";
				if(tblArticle.getImgUrl() != null && !tblArticle.getImgUrl().equals("")){
					sql += ",img_url='"+tblArticle.getImgUrl()+"'";
				}
				sql += ",remark=? where id=?";
		this.jdbcTemplate.update(sql,new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, tblArticle.getTitle());
				ps.setString(2, tblArticle.getArticleType());
				ps.setString(3, tblArticle.getContent());
				ps.setString(4, tblArticle.getUpdateUser());
				ps.setString(5, tblArticle.getArticleUrl());
				ps.setString(6, tblArticle.getRemark());
				ps.setString(7, tblArticle.getId());
			}
		});
	}
	
	

	/**
	 * 根据id删除一个文章信息
	 * @param id
	 */
	public void del(String id){
		String sql = "delete from tbl_article where id=?";
		this.jdbcTemplate.update(sql,id);
	}
	
	/**
	 * 根据id删除多个文章信息
	 * @param id
	 */
	public void dels(String ids){
		String sql = "delete from tbl_article where id in("+ids+")";
		this.jdbcTemplate.update(sql);
	}

	public List<TblArticle> getArticleByName(String articleType) {
		String sql = "select * from tbl_article where article_type='"+articleType+"'";
		try {
			return this.jdbcTemplate.query(sql, new ArticleMapper());
		} catch (DataAccessException e) {
			return null;
		}
	}

}
