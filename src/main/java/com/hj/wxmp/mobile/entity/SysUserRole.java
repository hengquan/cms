package com.hj.wxmp.mobile.entity;

import java.util.Date;

public class SysUserRole {
    private String id;

    private String userId;

    private String roleId;

    private Integer isvalidate;

    private Date ctime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
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