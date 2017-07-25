package com.hj.wxmp.mobile.dao;

import org.springframework.stereotype.Repository;

/**   
* @Title: IntegralDao.java
* @Package com.hj.wxmp.mobile.dao
* @Description: TODO
* @author Wangzhiyong   
* @date 2016年7月26日 上午11:11:09
* @version V1.0   
*/

@Repository
public class IntegralDao extends BaseDao{

	public int getScoreByOpenId(String openid){
		String sql = "select SUM(score) from tbl_integral where openid='"+openid+"' AND convert(varchar, getdate(), 102)=convert(varchar, integral_time, 102)";
		try {
			return jdbcTemplate.queryForObject(sql,Integer.class);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public int getSuggestScoreByOpenId(String openid){
		String sql = "select SUM(score) from tbl_suggest where openid='"+openid+"' AND convert(varchar, getdate(), 102)=convert(varchar, suggest_time, 102)";
		try {
			return jdbcTemplate.queryForObject(sql,Integer.class);
		} catch (Exception e) {
			return 0;
		}
	}
}
