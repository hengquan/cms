package com.hj.wxmp.mobile.entity;

import java.util.Date;

public class AccessRecord01 {
    private String id;

    private String projid;

    private String custid;

    private String custname;

    private String custphonenum;

    private String authorid;

    private String creatorid;

    private Integer status;

    private Date ctime;

    private Date lmtime;

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

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname == null ? null : custname.trim();
    }

    public String getCustphonenum() {
        return custphonenum;
    }

    public void setCustphonenum(String custphonenum) {
        this.custphonenum = custphonenum == null ? null : custphonenum.trim();
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid == null ? null : authorid.trim();
    }

    public String getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(String creatorid) {
        this.creatorid = creatorid == null ? null : creatorid.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getLmtime() {
        return lmtime;
    }

    public void setLmtime(Date lmtime) {
        this.lmtime = lmtime;
    }
}