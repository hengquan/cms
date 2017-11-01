package com.hj.wxmp.mobile.entity;

public class ImportMapUserCust {
    private String id;

    private String useroldid;

    private String username;

    private String userphonenum;

    private String custname;

    private String custphonenum;

    private Integer reporttype;

    private String reportid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUseroldid() {
        return useroldid;
    }

    public void setUseroldid(String useroldid) {
        this.useroldid = useroldid == null ? null : useroldid.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUserphonenum() {
        return userphonenum;
    }

    public void setUserphonenum(String userphonenum) {
        this.userphonenum = userphonenum == null ? null : userphonenum.trim();
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

    public Integer getReporttype() {
        return reporttype;
    }

    public void setReporttype(Integer reporttype) {
        this.reporttype = reporttype;
    }

    public String getReportid() {
        return reportid;
    }

    public void setReportid(String reportid) {
        this.reportid = reportid == null ? null : reportid.trim();
    }
}