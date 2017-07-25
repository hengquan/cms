package com.hj.wxmp.mobile.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.entity.SysAdmin;
import com.hj.wxmp.mobile.mapping.SysAdminMapper;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.SysAdminService;

@Component
public class SysAdminServiceImpl implements SysAdminService {

	@Resource
	private IKeyGen keyGen;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	private SysAdminMapper sysAdminMapper;
	
	
	@Override
	public boolean insert(SysAdmin entity) throws Exception {
		entity.setId(keyGen.getUUIDKey());
		
		boolean result = sysAdminMapper.insertSelective(entity) > 0 ? true : false;
		
		return result;
	}

	@Override
	public boolean update(SysAdmin entity) throws Exception {
		sysAdminMapper.updateByPrimaryKeySelective(entity);
		return true;
	}

	@Override
	public List<SysAdmin> listEntity(Map<String, Object> map) throws Exception {
		return null;
	}

	@Override
	public void delete(SysAdmin entity) throws Exception {

	}

	@Override
	public void saveEntity(SysAdmin entity) throws Exception {

	}

	@Override
	public SysAdmin findById(String sys_uuid) throws Exception {
		return sysAdminMapper.selectByPrimaryKey(sys_uuid);
	}

	@Override
	public Integer findCount(Map<String, Object> map) {
		return null;
	}

	@Override
	public boolean isLogin(String loginId, String password) throws Exception {
		return false;
	}

	@Override
	public boolean updateUserType() throws Exception {
		
		return false;
	}

	@Override
	public SysAdmin selectLoginId(String mobile) {
		return sysAdminMapper.selectLoginId(mobile);
	}

	@Override
	public SysAdmin selectNamePassword(SysAdmin admin) {
		return sysAdminMapper.selectNamePassword(admin);
	}

	@Override
	public SysAdmin selectMobile(String mobile) {
		return sysAdminMapper.selectMobile(mobile);
	}

}
