package com.spirit.core.dict.persis.po;

import java.sql.Timestamp;

import com.spiritdata.framework.core.model.BaseObject;

public class DictRefResPo extends BaseObject {
    private static final long serialVersionUID=-3984169450616227079L;

    private String id; //uuid(主键)
    private String dmId; //字典组Id
    private String ddId; //字典项Id
    private String tabName; //表名称
    private String tabId;  //表记录Id
    private String refName; //表中字段名称或关系名称
    private Timestamp CTime; //创建时间

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id=id;
    }
    public String getDmId() {
        return dmId;
    }
    public void setDmId(String dmId) {
        this.dmId=dmId;
    }
    public String getDdId() {
        return ddId;
    }
    public void setDdId(String ddId) {
        this.ddId=ddId;
    }
    public String getTabName() {
        return tabName;
    }
    public void setTabName(String tabName) {
        this.tabName=tabName;
    }
    public String getTabId() {
        return tabId;
    }
    public void setTabId(String tabId) {
        this.tabId=tabId;
    }
    public String getRefName() {
        return refName;
    }
    public void setRefName(String refName) {
        this.refName=refName;
    }
    public Timestamp getCTime() {
        return CTime;
    }
    public void setCTime(Timestamp cTime) {
        CTime=cTime;
    }
}