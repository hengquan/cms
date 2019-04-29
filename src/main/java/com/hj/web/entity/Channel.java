package com.hj.web.entity;

import java.util.Date;

public class Channel {
    private String id;

    private String channelname;

    private String areaname;

    private Integer sort;

    private String descn;

    private Integer isvalidate;

    private Date ctime;
    
    private Integer channelType;

    public Integer getChannelType() {
			return channelType;
		}

		public void setChannelType(Integer channelType) {
			this.channelType = channelType;
		}

		public String getChannelname() {
			return channelname;
		}

		public void setChannelname(String channelname) {
			this.channelname = channelname;
		}

		public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname == null ? null : areaname.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn == null ? null : descn.trim();
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