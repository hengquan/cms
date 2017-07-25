package com.hj.wxmp.mobile.entity;

import java.util.Date;

public class DictD {
    private String id;

    private String mid;

    private String pid;

    private Integer sort;

    private Integer isvalidate;

    private String ddname;

    private String npy;

    private String aliasname;

    private String anpy;

    private String bcode;

    private Integer dtype;

    private String dref;

    private String descn;

    private Date ctime;

    private Date lmtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid == null ? null : mid.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIsvalidate() {
        return isvalidate;
    }

    public void setIsvalidate(Integer isvalidate) {
        this.isvalidate = isvalidate;
    }

    public String getDdname() {
        return ddname;
    }

    public void setDdname(String ddname) {
        this.ddname = ddname == null ? null : ddname.trim();
    }

    public String getNpy() {
        return npy;
    }

    public void setNpy(String npy) {
        this.npy = npy == null ? null : npy.trim();
    }

    public String getAliasname() {
        return aliasname;
    }

    public void setAliasname(String aliasname) {
        this.aliasname = aliasname == null ? null : aliasname.trim();
    }

    public String getAnpy() {
        return anpy;
    }

    public void setAnpy(String anpy) {
        this.anpy = anpy == null ? null : anpy.trim();
    }

    public String getBcode() {
        return bcode;
    }

    public void setBcode(String bcode) {
        this.bcode = bcode == null ? null : bcode.trim();
    }

    public Integer getDtype() {
        return dtype;
    }

    public void setDtype(Integer dtype) {
        this.dtype = dtype;
    }

    public String getDref() {
        return dref;
    }

    public void setDref(String dref) {
        this.dref = dref == null ? null : dref.trim();
    }

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn == null ? null : descn.trim();
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