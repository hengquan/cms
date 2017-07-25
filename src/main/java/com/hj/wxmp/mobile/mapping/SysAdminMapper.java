package com.hj.wxmp.mobile.mapping;

import com.hj.wxmp.mobile.entity.SysAdmin;

public interface SysAdminMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysAdmin record);

    int insertSelective(SysAdmin record);

    SysAdmin selectByPrimaryKey(String id);
    
    SysAdmin selectByLoginId(String loginId);
    
    int updateByPrimaryKeySelective(SysAdmin record);

    int updateByPrimaryKey(SysAdmin record);
    
    int deleteByRelation(SysAdmin record);
    
    public void updatePwdByBusinessId(SysAdmin record);
    
    SysAdmin selectBybusinessId(String businessId);

	SysAdmin selectLoginId(String mobile);

	SysAdmin selectNamePassword(SysAdmin admin);

	SysAdmin selectMobile(String mobile);
}