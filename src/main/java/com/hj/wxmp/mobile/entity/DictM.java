package com.hj.wxmp.mobile.entity;

import java.util.Date;

public class DictM {
    private String id;

    private String dmname;

    private String npy;

    private Integer sort;

    private Integer isvalidate;

    private Integer mtype;

    private String mref;

    private String descn;

    private Date ctime;

    private Date lmtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDmname() {
        return dmname;
    }

    public void setDmname(String dmname) {
        this.dmname = dmname == null ? null : dmname.trim();
    }

    public String getNpy() {
        return npy;
    }

    public void setNpy(String npy) {
        this.npy = npy == null ? null : npy.trim();
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

    public Integer getMtype() {
        return mtype;
    }

    public void setMtype(Integer mtype) {
        this.mtype = mtype;
    }

    public String getMref() {
        return mref;
    }

    public void setMref(String mref) {
        this.mref = mref == null ? null : mref.trim();
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