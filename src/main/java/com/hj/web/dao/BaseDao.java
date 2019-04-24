package com.hj.web.dao;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

/**
* @author WangZhiYong
* @date 创建时间：2016年5月12日 下午3:59:11
* @version 1.0 
 * @param <T>
* @parameter  
* @since  
* @return  
*/

// 	 				 _ooOoo_ 
//    			    o8888888o 
//    				88" . "88 
//     	 			(| -_- |) 
//      			 O\ = /O 
//	 			 ____/`---*\____ 
//				 . * \\| |// `. 
//				/ \\||| : |||// \ 
//			  / _||||| -:- |||||- \ 
//				| | \\\ - /// | | 
//			  | \_| **\---/** | | 
//			  \  .-\__ `-` ___/-. / 
//			___`. .* /--.--\ `. . __ 
//		 ."" *< `.___\_<|>_/___.* >*"". 
//  	| | : `- \`.;`\ _ /`;.`/ - ` : | | 
//        \ \ `-. \_ __\ /__ _/ .-` / / 
//======`-.____`-.___\_____/___.-`____.-*====== 
//					 `=---=* 
//
//............................................. 
//			佛祖保佑 				 永无BUG 
//					佛曰:  
//			写字楼里写字间，写字间里程序员；  
//			程序人员写程序，又拿程序换酒钱。  
//			酒醒只在网上坐，酒醉还来网下眠；  
//			酒醉酒醒日复日，网上网下年复年。  
//			但愿老死电脑间，不愿鞠躬老板前；  
//			奔驰宝马贵者趣，公交自行程序员。  
//			别人笑我忒疯癫，我笑自己命太贱；  
//			不见满街漂亮妹，哪个归得程序员？ 

public class BaseDao{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	
		/**
		 * 添加或修改信息
		 * @param sql
		 * @param parameters
		 */
	 public void addOrUpdate(String sql, final Object[] parameters) {
		 
		  try {
			 jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(java.sql.PreparedStatement ps) throws SQLException {
				 for (int i = 0; i < parameters.length; i++)
				       ps.setObject(i + 1, parameters[i]);
				 }
		    });
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
		 }
	 
			
	 /**
		 * 根据id删除一条记录
		 * @param tableName
		 * @param id
		 */
	public void delete(String tableName,String id){
		String sql = "delete from "+tableName+" where id=?";
		this.jdbcTemplate.update(sql,id);
	}
	
	/**
	 * 根据id删除多条记录
	 * @param tableName
	 * @param id
	 */
	public void deletes(String tableName,String id){
		String sql = "delete from "+tableName+" where id in("+id+")";
		this.jdbcTemplate.update(sql);
	}
	
	
	public <T> void inserSql(String tableName,Class<T> clazz,final Object[] parameters) {
		String sql = "insert into "+tableName+"(";
		Field[] files = clazz.getDeclaredFields();
		for(Field f:files){
			if(f.getName() != null){
				String field = "";
				 char[] chs = f.getName().toCharArray();
				 for(int i = 0; i < chs.length; i++){
					 if(chs[i] < 'Z' && chs[i] > 'A'){
						 System.out.println(chs[i]);
						 field = String.valueOf(chs[i]);
					 }
				 }
				 System.out.println(field);
				 if(field != null && !field.equals("")){
					 sql+=f.getName().toString().replace(field, "_"+field.toLowerCase())+",";
				 }else{
					 sql+=f.getName()+",";
				 }
			}
		}
		sql=sql.substring(0,sql.length()-1)+") values(";
		for(int i=1; i <=files.length ; i++ ){
			sql+="?,";
		}
		sql=sql.substring(0,sql.length()-1)+");";
		jdbcTemplate.update(sql,new PreparedStatementSetter() {
			@Override
			public void setValues(java.sql.PreparedStatement ps) throws SQLException {
				for (int i = 0; i < parameters.length; i++)
				       ps.setObject(i + 1, parameters[i]);
				}
				
		});
		
		System.out.println(sql);
	}
	
	public <T> List<T> find(String sql, Object[] parameters, Class<T> clazz) {
	  List<T> resultList = null;
	  
	  try {
		  
	    if (parameters != null && parameters.length > 0)
	   		resultList = jdbcTemplate.query(sql, parameters,new BeanPropertyRowMapper<T>(clazz));
	    else
		    resultList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(clazz));// BeanPropertyRowMapper是自动映射实体类的
	  } catch (Exception e) {
		  	e.printStackTrace();
	  }
	  	return resultList;
	 }
	
	
	public <T> T findObject(String sql, Object[] parameters, Class<T> clazz) {
		  T result = null;
		  try {
			  if (parameters != null && parameters.length > 0)
			try {
				result = jdbcTemplate.queryForObject(sql, parameters,new BeanPropertyRowMapper<T>(clazz));
			} catch (Exception e) {
				return null;
			}
		  else
				try {
					result = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<T>(clazz)); // BeanPropertyRowMapper是自动映射实体类的
				} catch (Exception e) {
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
		  }
		  return result;
		 }
	
	/**
	 * 获取数量
	 * @param sql
	 * @param parameters
	 * @return
	 **/
	public int findCount(String sql, Object[] parameters){
		try {
			return this.jdbcTemplate.queryForObject(sql,Integer.class, parameters);
		} catch (DataAccessException e) {
			return 0;
		}
	}
		
	

}
