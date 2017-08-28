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

    private String capitalprepsection;

    private Integer isvalidate;

    private Date ctime;
    
    private String knowwaydesc;

    private String attentionpointdesc;

    private String resistpointdesc;

	public String getId() {
		return id;
	}

	public String getProjid() {
		return projid;
	}

	public String getCustid() {
		return custid;
	}

	public Date getFirstknowtime() {
		return firstknowtime;
	}

	public String getKnowway() {
		return knowway;
	}

	public String getAttentionpoint() {
		return attentionpoint;
	}

	public String getCompareprojs() {
		return compareprojs;
	}

	public String getResistpoint() {
		return resistpoint;
	}

	public String getCapitalprepsection() {
		return capitalprepsection;
	}

	public Integer getIsvalidate() {
		return isvalidate;
	}

	public Date getCtime() {
		return ctime;
	}

	public String getKnowwaydesc() {
		return knowwaydesc;
	}

	public String getAttentionpointdesc() {
		return attentionpointdesc;
	}

	public String getResistpointdesc() {
		return resistpointdesc;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setProjid(String projid) {
		this.projid = projid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public void setFirstknowtime(Date firstknowtime) {
		this.firstknowtime = firstknowtime;
	}

	public void setKnowway(String knowway) {
		this.knowway = knowway;
	}

	public void setAttentionpoint(String attentionpoint) {
		this.attentionpoint = attentionpoint;
	}

	public void setCompareprojs(String compareprojs) {
		this.compareprojs = compareprojs;
	}

	public void setResistpoint(String resistpoint) {
		this.resistpoint = resistpoint;
	}

	public void setCapitalprepsection(String capitalprepsection) {
		this.capitalprepsection = capitalprepsection;
	}

	public void setIsvalidate(Integer isvalidate) {
		this.isvalidate = isvalidate;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public void setKnowwaydesc(String knowwaydesc) {
		this.knowwaydesc = knowwaydesc;
	}

	public void setAttentionpointdesc(String attentionpointdesc) {
		this.attentionpointdesc = attentionpointdesc;
	}

	public void setResistpointdesc(String resistpointdesc) {
		this.resistpointdesc = resistpointdesc;
	}
    
}