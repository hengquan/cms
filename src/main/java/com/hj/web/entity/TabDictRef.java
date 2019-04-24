package com.hj.web.entity;

import java.util.Date;

public class TabDictRef {
    private String id;

    private String dmid;

    private String ddid;

    private String tabname;

    private String tabid;

    private String refname;

    private Float sectionbegin;

    private Float sectionend;

    private Date ctime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDmid() {
        return dmid;
    }

    public void setDmid(String dmid) {
        this.dmid = dmid == null ? null : dmid.trim();
    }

    public String getDdid() {
        return ddid;
    }

    public void setDdid(String ddid) {
        this.ddid = ddid == null ? null : ddid.trim();
    }

    public String getTabname() {
        return tabname;
    }

    public void setTabname(String tabname) {
        this.tabname = tabname == null ? null : tabname.trim();
    }

    public String getTabid() {
        return tabid;
    }

    public void setTabid(String tabid) {
        this.tabid = tabid == null ? null : tabid.trim();
    }

    public String getRefname() {
        return refname;
    }

    public void setRefname(String refname) {
        this.refname = refname == null ? null : refname.trim();
    }

    public Float getSectionbegin() {
        return sectionbegin;
    }

    public void setSectionbegin(Float sectionbegin) {
        this.sectionbegin = sectionbegin;
    }

    public Float getSectionend() {
        return sectionend;
    }

    public void setSectionend(Float sectionend) {
        this.sectionend = sectionend;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}