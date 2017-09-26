package com.hj.wxmp.mobile.entity;

public class DayRecep {
    private String id;

    private String daystr;

    private String miiprojid;

    private String miiuserid;

    private String miicustid;

    private Integer ar01count;

    private Integer ar02count;

    private Integer ar03count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDaystr() {
        return daystr;
    }

    public void setDaystr(String daystr) {
        this.daystr = daystr == null ? null : daystr.trim();
    }

    public String getMiiprojid() {
        return miiprojid;
    }

    public void setMiiprojid(String miiprojid) {
        this.miiprojid = miiprojid == null ? null : miiprojid.trim();
    }

    public String getMiiuserid() {
        return miiuserid;
    }

    public void setMiiuserid(String miiuserid) {
        this.miiuserid = miiuserid == null ? null : miiuserid.trim();
    }

    public String getMiicustid() {
        return miicustid;
    }

    public void setMiicustid(String miicustid) {
        this.miicustid = miicustid == null ? null : miicustid.trim();
    }

    public Integer getAr01count() {
        return ar01count;
    }

    public void setAr01count(Integer ar01count) {
        this.ar01count = ar01count;
    }

    public Integer getAr02count() {
        return ar02count;
    }

    public void setAr02count(Integer ar02count) {
        this.ar02count = ar02count;
    }

    public Integer getAr03count() {
        return ar03count;
    }

    public void setAr03count(Integer ar03count) {
        this.ar03count = ar03count;
    }
}