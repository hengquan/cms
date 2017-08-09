package com.hj.wxmp.mobile.entity;

import java.util.Date;

public class ProjCustRef {
    private String id;

    private String projid;

    private String custid;

    private Date firstknowtime;

    private String knowway;

    private String attentionpoint;

    private String compareprojs;

    private String resistpoint;

    private Integer isvalidate;

    private Date ctime;
    
    private String knowwaydesc;

    private String attentionpointdesc;

    private String resistpointdesc;

    public String getKnowwaydesc() {
        return knowwaydesc;
    }

    public void setKnowwaydesc(String knowwaydesc) {
        this.knowwaydesc = knowwaydesc == null ? null : knowwaydesc.trim();
    }

    public String getAttentionpointdesc() {
        return attentionpointdesc;
    }

    public void setAttentionpointdesc(String attentionpointdesc) {
        this.attentionpointdesc = attentionpointdesc == null ? null : attentionpointdesc.trim();
    }

    public String getResistpointdesc() {
        return resistpointdesc;
    }

    public void setResistpointdesc(String resistpointdesc) {
        this.resistpointdesc = resistpointdesc == null ? null : resistpointdesc.trim();
    }

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

    public Date getFirstknowtime() {
        return firstknowtime;
    }

    public void setFirstknowtime(Date firstknowtime) {
        this.firstknowtime = firstknowtime;
    }

    public String getKnowway() {
        return knowway;
    }

    public void setKnowway(String knowway) {
        this.knowway = knowway == null ? null : knowway.trim();
    }

    public String getAttentionpoint() {
        return attentionpoint;
    }

    public void setAttentionpoint(String attentionpoint) {
        this.attentionpoint = attentionpoint == null ? null : attentionpoint.trim();
    }

    public String getCompareprojs() {
        return compareprojs;
    }

    public void setCompareprojs(String compareprojs) {
        this.compareprojs = compareprojs == null ? null : compareprojs.trim();
    }

    public String getResistpoint() {
        return resistpoint;
    }

    public void setResistpoint(String resistpoint) {
        this.resistpoint = resistpoint == null ? null : resistpoint.trim();
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