package com.hj.web.entity;

import java.util.Date;

public class ThirdUser {
    private String id;

    private String userid;

    private String thirduserid;

    private String thirdsystype;

    private String thirdsysid;

    private Integer thirdlogincount;

    private Date ctime;

    private Date lmtime;

    private String thirduserinfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getThirduserid() {
        return thirduserid;
    }

    public void setThirduserid(String thirduserid) {
        this.thirduserid = thirduserid == null ? null : thirduserid.trim();
    }

    public String getThirdsystype() {
        return thirdsystype;
    }

    public void setThirdsystype(String thirdsystype) {
        this.thirdsystype = thirdsystype == null ? null : thirdsystype.trim();
    }

    public String getThirdsysid() {
        return thirdsysid;
    }

    public void setThirdsysid(String thirdsysid) {
        this.thirdsysid = thirdsysid == null ? null : thirdsysid.trim();
    }

    public Integer getThirdlogincount() {
        return thirdlogincount;
    }

    public void setThirdlogincount(Integer thirdlogincount) {
        this.thirdlogincount = thirdlogincount;
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

    public String getThirduserinfo() {
        return thirduserinfo;
    }

    public void setThirduserinfo(String thirduserinfo) {
        this.thirduserinfo = thirduserinfo == null ? null : thirduserinfo.trim();
    }
}