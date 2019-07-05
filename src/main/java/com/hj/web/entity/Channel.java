package com.hj.web.entity;

import java.util.Date;
import java.util.List;

public class Channel {
    private String id;

    private String channelname;

    private String areaname;

    private Integer sort;

    private String descn;
    
    private String roleId;
    
    private String roleName;

    private Integer isvalidate;

    private Date ctime;
    
    private Integer channeltype;
    
    private String languages;
    
    private String picUrl;
    
    private String moduleId;
    
    private String moduleName;
    
    private String hrefUrl;
    
    private String parentId;
    
    private List<Channel> channelList;
    
    private List<Article> articleList;

		public List<Article> getArticleList() {
			return articleList;
		}

		public void setArticleList(List<Article> articleList) {
			this.articleList = articleList;
		}

		public List<Channel> getChannelList() {
			return channelList;
		}

		public void setChannelList(List<Channel> channelList) {
			this.channelList = channelList;
		}

		public String getParentId() {
			return parentId;
		}

		public void setParentId(String parentId) {
			this.parentId = parentId;
		}

		public String getModuleName() {
			return moduleName;
		}

		public void setModuleName(String moduleName) {
			this.moduleName = moduleName;
		}

		public String getModuleId() {
			return moduleId;
		}

		public void setModuleId(String moduleId) {
			this.moduleId = moduleId;
		}

		public String getHrefUrl() {
			return hrefUrl;
		}

		public void setHrefUrl(String hrefUrl) {
			this.hrefUrl = hrefUrl;
		}

		public String getPicUrl() {
			return picUrl;
		}

		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}

		public String getLanguages() {
			return languages;
		}

		public void setLanguages(String languages) {
			this.languages = languages;
		}

		public String getRoleName() {
			return roleName;
		}

		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}

		public String getRoleId() {
			return roleId;
		}

		public void setRoleId(String roleId) {
			this.roleId = roleId;
		}

		public Integer getChanneltype() {
			return channeltype;
		}

		public void setChanneltype(Integer channeltype) {
			this.channeltype = channeltype;
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