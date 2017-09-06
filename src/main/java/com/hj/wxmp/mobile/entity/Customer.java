package com.hj.wxmp.mobile.entity;

import java.util.Date;

public class Customer {
    private String id;

    private String custname;

    private String phonenum;

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

    private String housetype;

    private String enterprisetype;

    private String realtyproducttype;

    private String attentacreage;

    private String pricesection;

    private String buypurpose;

    private String estcustworth;

    private String investtype;

    private String captilprepsection;

    private Integer childrennum;

    private String childagegroup;

    private String childavocations;

    private String childavocationsdesc;

    private String schooltype;

    private String schoolname;

    private String livingradius;

    private String communityname;

    private String liveacreage;

    private String enterprisename;

    private String enterpriseaddress;

    private String enterprisepost;

    private String loanstatus;

    private Integer fulltimewifeflag;

    private Integer outeduwill;

    private Integer nannyflag;

    private Integer outexperflag;

    private String outexpercity;

    private Integer childoutexperflag;

    private String childoutexpercity;

    private Integer petflag;

    private Integer housecount;

    private Integer carfamilycount;

    private String carbrand;

    private String cartotalprice;

    private String attentwx;

    private String appnames;

    private String avocations;

    private String loveactivation;

    private String freetimesection;

    private String addressmail;

    private String houseregitype;

    private String custscore;

    private String livingstatus;

    private Date ctime;

    private Date lmtime;
    
    private String traffictypedesc;

    private String workindustrydesc;

    private String enterprisetypedesc;

    private String realtyproducttypedesc;

    private String buypurposedesc;

    private String investtypedesc;

    private String avocationsdesc;

    private String loveactdesc;

    private String custdescn;
    
    private Date firstvisittime;
    
    private Integer visitcount;

    public Date getFirstvisittime() {
		return firstvisittime;
	}

	public Integer getVisitcount() {
		return visitcount;
	}

	public void setFirstvisittime(Date firstvisittime) {
		this.firstvisittime = firstvisittime;
	}

	public void setVisitcount(Integer visitcount) {
		this.visitcount = visitcount;
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

    public String getInvesttypedesc() {
        return investtypedesc;
    }

    public void setInvesttypedesc(String investtypedesc) {
        this.investtypedesc = investtypedesc == null ? null : investtypedesc.trim();
    }

    public String getAvocationsdesc() {
        return avocationsdesc;
    }

    public void setAvocationsdesc(String avocationsdesc) {
        this.avocationsdesc = avocationsdesc == null ? null : avocationsdesc.trim();
    }

    public String getLoveactdesc() {
        return loveactdesc;
    }

    public void setLoveactdesc(String loveactdesc) {
        this.loveactdesc = loveactdesc == null ? null : loveactdesc.trim();
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

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname == null ? null : custname.trim();
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum == null ? null : phonenum.trim();
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

    public String getHousetype() {
        return housetype;
    }

    public void setHousetype(String housetype) {
        this.housetype = housetype == null ? null : housetype.trim();
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

    public String getCaptilprepsection() {
        return captilprepsection;
    }

    public void setCaptilprepsection(String captilprepsection) {
        this.captilprepsection = captilprepsection == null ? null : captilprepsection.trim();
    }

    public Integer getChildrennum() {
        return childrennum;
    }

    public void setChildrennum(Integer childrennum) {
        this.childrennum = childrennum;
    }

    public String getChildagegroup() {
        return childagegroup;
    }

    public void setChildagegroup(String childagegroup) {
        this.childagegroup = childagegroup == null ? null : childagegroup.trim();
    }

    public String getChildavocations() {
        return childavocations;
    }

    public void setChildavocations(String childavocations) {
        this.childavocations = childavocations == null ? null : childavocations.trim();
    }

    public String getChildavocationsdesc() {
        return childavocationsdesc;
    }

    public void setChildavocationsdesc(String childavocationsdesc) {
        this.childavocationsdesc = childavocationsdesc == null ? null : childavocationsdesc.trim();
    }

    public String getSchooltype() {
        return schooltype;
    }

    public void setSchooltype(String schooltype) {
        this.schooltype = schooltype == null ? null : schooltype.trim();
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname == null ? null : schoolname.trim();
    }

    public String getLivingradius() {
        return livingradius;
    }

    public void setLivingradius(String livingradius) {
        this.livingradius = livingradius == null ? null : livingradius.trim();
    }

    public String getCommunityname() {
        return communityname;
    }

    public void setCommunityname(String communityname) {
        this.communityname = communityname == null ? null : communityname.trim();
    }

    public String getLiveacreage() {
        return liveacreage;
    }

    public void setLiveacreage(String liveacreage) {
        this.liveacreage = liveacreage == null ? null : liveacreage.trim();
    }

    public String getEnterprisename() {
        return enterprisename;
    }

    public void setEnterprisename(String enterprisename) {
        this.enterprisename = enterprisename == null ? null : enterprisename.trim();
    }

    public String getEnterpriseaddress() {
        return enterpriseaddress;
    }

    public void setEnterpriseaddress(String enterpriseaddress) {
        this.enterpriseaddress = enterpriseaddress == null ? null : enterpriseaddress.trim();
    }

    public String getEnterprisepost() {
        return enterprisepost;
    }

    public void setEnterprisepost(String enterprisepost) {
        this.enterprisepost = enterprisepost == null ? null : enterprisepost.trim();
    }

    public String getLoanstatus() {
        return loanstatus;
    }

    public void setLoanstatus(String loanstatus) {
        this.loanstatus = loanstatus == null ? null : loanstatus.trim();
    }

    public Integer getFulltimewifeflag() {
        return fulltimewifeflag;
    }

    public void setFulltimewifeflag(Integer fulltimewifeflag) {
        this.fulltimewifeflag = fulltimewifeflag;
    }

    public Integer getOuteduwill() {
        return outeduwill;
    }

    public void setOuteduwill(Integer outeduwill) {
        this.outeduwill = outeduwill;
    }

    public Integer getNannyflag() {
        return nannyflag;
    }

    public void setNannyflag(Integer nannyflag) {
        this.nannyflag = nannyflag;
    }

    public Integer getOutexperflag() {
        return outexperflag;
    }

    public void setOutexperflag(Integer outexperflag) {
        this.outexperflag = outexperflag;
    }

    public String getOutexpercity() {
        return outexpercity;
    }

    public void setOutexpercity(String outexpercity) {
        this.outexpercity = outexpercity == null ? null : outexpercity.trim();
    }

    public Integer getChildoutexperflag() {
        return childoutexperflag;
    }

    public void setChildoutexperflag(Integer childoutexperflag) {
        this.childoutexperflag = childoutexperflag;
    }

    public String getChildoutexpercity() {
        return childoutexpercity;
    }

    public void setChildoutexpercity(String childoutexpercity) {
        this.childoutexpercity = childoutexpercity == null ? null : childoutexpercity.trim();
    }

    public Integer getPetflag() {
        return petflag;
    }

    public void setPetflag(Integer petflag) {
        this.petflag = petflag;
    }

    public Integer getHousecount() {
        return housecount;
    }

    public void setHousecount(Integer housecount) {
        this.housecount = housecount;
    }

    public Integer getCarfamilycount() {
        return carfamilycount;
    }

    public void setCarfamilycount(Integer carfamilycount) {
        this.carfamilycount = carfamilycount;
    }

    public String getCarbrand() {
        return carbrand;
    }

    public void setCarbrand(String carbrand) {
        this.carbrand = carbrand == null ? null : carbrand.trim();
    }

    public String getCartotalprice() {
        return cartotalprice;
    }

    public void setCartotalprice(String cartotalprice) {
        this.cartotalprice = cartotalprice == null ? null : cartotalprice.trim();
    }

    public String getAttentwx() {
        return attentwx;
    }

    public void setAttentwx(String attentwx) {
        this.attentwx = attentwx == null ? null : attentwx.trim();
    }

    public String getAppnames() {
        return appnames;
    }

    public void setAppnames(String appnames) {
        this.appnames = appnames == null ? null : appnames.trim();
    }

    public String getAvocations() {
        return avocations;
    }

    public void setAvocations(String avocations) {
        this.avocations = avocations == null ? null : avocations.trim();
    }

    public String getLoveactivation() {
        return loveactivation;
    }

    public void setLoveactivation(String loveactivation) {
        this.loveactivation = loveactivation == null ? null : loveactivation.trim();
    }

    public String getFreetimesection() {
        return freetimesection;
    }

    public void setFreetimesection(String freetimesection) {
        this.freetimesection = freetimesection == null ? null : freetimesection.trim();
    }

    public String getAddressmail() {
        return addressmail;
    }

    public void setAddressmail(String addressmail) {
        this.addressmail = addressmail == null ? null : addressmail.trim();
    }

    public String getHouseregitype() {
        return houseregitype;
    }

    public void setHouseregitype(String houseregitype) {
        this.houseregitype = houseregitype == null ? null : houseregitype.trim();
    }

    public String getCustscore() {
        return custscore;
    }

    public void setCustscore(String custscore) {
        this.custscore = custscore == null ? null : custscore.trim();
    }

    public String getLivingstatus() {
        return livingstatus;
    }

    public void setLivingstatus(String livingstatus) {
        this.livingstatus = livingstatus == null ? null : livingstatus.trim();
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