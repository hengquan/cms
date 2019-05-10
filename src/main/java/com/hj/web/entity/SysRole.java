package com.hj.web.entity;

import java.util.List;

public class SysRole {
    private String id;

    private String roleName;

    private String pinyin;

    private String logogram;
 
    private String languageId;

    private Integer sort;

    private String remark;
    
    private List<SysRole> roleList;
    
    private String languageName;

    public String getLanguageName() {
			return languageName;
		}

		public void setLanguageName(String languageName) {
			this.languageName = languageName;
		}

		public String getLanguageId() {
			return languageId;
		}

		public void setLanguageId(String languageId) {
			this.languageId = languageId;
		}

		public List<SysRole> getRoleList() {
			return roleList;
		}

		public void setRoleList(List<SysRole> roleList) {
			this.roleList = roleList;
		}

		public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
    }

    public String getLogogram() {
        return logogram;
    }

    public void setLogogram(String logogram) {
        this.logogram = logogram == null ? null : logogram.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}