package com.hj.wxmp.mobile.entity;

import java.util.Date;

public class ProjCustRef {
    private String id;

    private String projid;

    private String custid;

    private Integer isvalidate;

    private Date ctime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProjid() {
        return projid;
    }

    public void setProjid(String projid) {
        this.projid = projid == null ? null : projid.trim();
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid == null ? null : custid.trim();
    }

    public Integer getIsvalidate() {
        return isvalidate;
    }

    public void setIsvalidate(Integer isvalidate) {
        this.isvalidate = isvalidate;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}