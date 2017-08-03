package com.hj.wxmp.mobile.entity;

import java.util.Date;

public class AuditRecord {
    private String id;

    private Integer recordtype;

    private String arid;

    private Integer audittype;

    private Date ctime;

    private String reason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getRecordtype() {
        return recordtype;
    }

    public void setRecordtype(Integer recordtype) {
        this.recordtype = recordtype;
    }

    public String getarid() {
        return arid;
    }

    public void setarid(String arid) {
        this.arid = arid == null ? null : arid.trim();
    }

    public Integer getAudittype() {
        return audittype;
    }

    public void setAudittype(Integer audittype) {
        this.audittype = audittype;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }
}