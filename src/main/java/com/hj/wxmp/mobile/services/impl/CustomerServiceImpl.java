package com.hj.wxmp.mobile.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.entity.Customer;
import com.hj.wxmp.mobile.mapping.CustomerMapper;
import com.hj.wxmp.mobile.services.CustomerService;
import com.hj.wxmp.mobile.services.IKeyGen;

@Component
public class CustomerServiceImpl implements CustomerService {

	@Resource
	private IKeyGen keyGen;
	@Resource
	private CustomerMapper dao;
	
	@Override
	public boolean insert(Customer entity) throws Exception {
		return dao.insertSelective(entity)>0?true:false;
	}
	@Override
	public boolean update(Customer entity) throws Exception {
		return dao.updateByPrimaryKeySelective(entity)>0?true:false;
	}
	@Override
	public List<Customer> listEntity(Map<String, Object> map) throws Exception {
		return null;
	}
	@Override
	public void delete(Customer entity) throws Exception {
		dao.deleteByPrimaryKey(entity.getId());
	}
	@Override
	public Customer saveEntity(Customer entity) throws Exception {
		return null;
	}
	@Override
	public Customer findById(String sys_uuid) throws Exception {
		return dao.selectByPrimaryKey(sys_uuid);
	}
	@Override
	public Customer findByOpenid(String openid) throws Exception {
		return null;
	}
	@Override
	public int perfectUserInfo(Customer sysUser) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<Customer> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int selectCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<Map<String, Object>> selectByUserMessge(Map<String, Object> map) {
		return dao.selectByUserMessge(map);
	}
	@Override
	public Integer selectByUserMessgeCount(Map<String, Object> map) {
		return dao.selectByUserMessgeCount(map);
	}
	@Override
	public Customer findByPhone(String phone) {
		return dao.findByPhone(phone);
	}
	


}
