package com.hj.wxmp.mobile.entity;

import java.util.Date;

public class AccessRecord01 {
    private String id;

    private String projid;

    private String custid;

    private String custname;
    
    private String custaddress;

    public String getCustaddress() {
		return custaddress;
	}

	public void setCustaddress(String custaddress) {
		this.custaddress = custaddress;
	}

	private String custphonenum;

    private Date firstknowtime;

    private String custsex;

    private String agegroup;

    private String buyqualify;

    private String localresidence;

    private String localworkarea;

    private String outresidence;

    private String outworkarea;

    private String familystatus;

    private String traffictype;

    private String workindustry;

    private String enterprisetype;

    private String realtyproducttype;

    private String attentacreage;

    private String pricesection;

    private String buypurpose;

    private String knowway;

    private String attentionpoint;

    private String estcustworth;

    private String investtype;
    
    private String capitalprepsection;

    private String compareprojs;

    private String receptimesection;

    private String custscore;

    private String authorid;

    private String creatorid;

    private Integer status;

    private Date ctime;

    private Date lmtime;
    
    private String traffictypedesc;

    private String workindustrydesc;

    private String enterprisetypedesc;

    private String realtyproducttypedesc;

    private String buypurposedesc;

    private String knowwaydesc;

    private String attentionpointdesc;

    private String investtypedesc;

    private String custdescn;
    
    private Date receptime;

	public Date getReceptime() {
		return receptime;
	}

	public void setReceptime(Date receptime) {
		this.receptime = receptime;
	}

	public String getTraffictypedesc() {
        return traffictypedesc;
    }

    public void setTraffictypedesc(String traffictypedesc) {
        this.traffictypedesc = traffictypedesc == null ? null : traffictypedesc.trim();
    }

    public String getWorkindustrydesc() {
        return workindustrydesc;
    }

    public void setWorkindustrydesc(String workindustrydesc) {
        this.workindustrydesc = workindustrydesc == null ? null : workindustrydesc.trim();
    }

    public String getEnterprisetypedesc() {
        return enterprisetypedesc;
    }

    public void setEnterprisetypedesc(String enterprisetypedesc) {
        this.enterprisetypedesc = enterprisetypedesc == null ? null : enterprisetypedesc.trim();
    }

    public String getRealtyproducttypedesc() {
        return realtyproducttypedesc;
    }

    public void setRealtyproducttypedesc(String realtyproducttypedesc) {
        this.realtyproducttypedesc = realtyproducttypedesc == null ? null : realtyproducttypedesc.trim();
    }

    public String getBuypurposedesc() {
        return buypurposedesc;
    }

    public void setBuypurposedesc(String buypurposedesc) {
        this.buypurposedesc = buypurposedesc == null ? null : buypurposedesc.trim();
    }

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

    public String getInvesttypedesc() {
        return investtypedesc;
    }

    public String getCapitalprepsection() {
		return capitalprepsection;
	}

	public void setCapitalprepsection(String capitalprepsection) {
		this.capitalprepsection = capitalprepsection;
	}

	public void setInvesttypedesc(String investtypedesc) {
        this.investtypedesc = investtypedesc == null ? null : investtypedesc.trim();
    }

    public String getCustdescn() {
        return custdescn;
    }

    public void setCustdescn(String custdescn) {
        this.custdescn = custdescn == null ? null : custdescn.trim();
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

    public Date getFirstknowtime() {
        return firstknowtime;
    }

    public void setFirstknowtime(Date firstknowtime) {
        this.firstknowtime = firstknowtime;
    }

    public String getCustsex() {
        return custsex;
    }

    public void setCustsex(String custsex) {
        this.custsex = custsex == null ? null : custsex.trim();
    }

	public String getAgegroup() {
        return agegroup;
    }

    public void setAgegroup(String agegroup) {
        this.agegroup = agegroup == null ? null : agegroup.trim();
    }

    public String getBuyqualify() {
        return buyqualify;
    }

    public void setBuyqualify(String buyqualify) {
        this.buyqualify = buyqualify == null ? null : buyqualify.trim();
    }

    public String getLocalresidence() {
        return localresidence;
    }

    public void setLocalresidence(String localresidence) {
        this.localresidence = localresidence == null ? null : localresidence.trim();
    }

    public String getLocalworkarea() {
        return localworkarea;
    }

    public void setLocalworkarea(String localworkarea) {
        this.localworkarea = localworkarea == null ? null : localworkarea.trim();
    }

    public String getOutresidence() {
        return outresidence;
    }

    public void setOutresidence(String outresidence) {
        this.outresidence = outresidence == null ? null : outresidence.trim();
    }

    public String getOutworkarea() {
        return outworkarea;
    }

    public void setOutworkarea(String outworkarea) {
        this.outworkarea = outworkarea == null ? null : outworkarea.trim();
    }

    public String getFamilystatus() {
        return familystatus;
    }

    public void setFamilystatus(String familystatus) {
        this.familystatus = familystatus == null ? null : familystatus.trim();
    }

    public String getTraffictype() {
        return traffictype;
    }

    public void setTraffictype(String traffictype) {
        this.traffictype = traffictype == null ? null : traffictype.trim();
    }

    public String getWorkindustry() {
        return workindustry;
    }

    public void setWorkindustry(String workindustry) {
        this.workindustry = workindustry == null ? null : workindustry.trim();
    }

    public String getEnterprisetype() {
        return enterprisetype;
    }

    public void setEnterprisetype(String enterprisetype) {
        this.enterprisetype = enterprisetype == null ? null : enterprisetype.trim();
    }

    public String getRealtyproducttype() {
        return realtyproducttype;
    }

    public void setRealtyproducttype(String realtyproducttype) {
        this.realtyproducttype = realtyproducttype == null ? null : realtyproducttype.trim();
    }

    public String getAttentacreage() {
        return attentacreage;
    }

    public void setAttentacreage(String attentacreage) {
        this.attentacreage = attentacreage == null ? null : attentacreage.trim();
    }

    public String getPricesection() {
        return pricesection;
    }

    public void setPricesection(String pricesection) {
        this.pricesection = pricesection == null ? null : pricesection.trim();
    }

    public String getBuypurpose() {
        return buypurpose;
    }

    public void setBuypurpose(String buypurpose) {
        this.buypurpose = buypurpose == null ? null : buypurpose.trim();
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

    public String getEstcustworth() {
        return estcustworth;
    }

    public void setEstcustworth(String estcustworth) {
        this.estcustworth = estcustworth == null ? null : estcustworth.trim();
    }

    public String getInvesttype() {
        return investtype;
    }

    public void setInvesttype(String investtype) {
        this.investtype = investtype == null ? null : investtype.trim();
    }

    public String getCompareprojs() {
        return compareprojs;
    }

    public void setCompareprojs(String compareprojs) {
        this.compareprojs = compareprojs == null ? null : compareprojs.trim();
    }

    public String getReceptimesection() {
        return receptimesection;
    }

    public void setReceptimesection(String receptimesection) {
        this.receptimesection = receptimesection == null ? null : receptimesection.trim();
    }

    public String getCustscore() {
        return custscore;
    }

    public void setCustscore(String custscore) {
        this.custscore = custscore == null ? null : custscore.trim();
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