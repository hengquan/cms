package com.hj.wxmp.mobile.mapping;

import com.hj.wxmp.mobile.entity.AuditRecord;

public interface AuditRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(AuditRecord record);

    int insertSelective(AuditRecord record);

    AuditRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuditRecord record);

    int updateByPrimaryKeyWithBLOBs(AuditRecord record);

    int updateByPrimaryKey(AuditRecord record);
}