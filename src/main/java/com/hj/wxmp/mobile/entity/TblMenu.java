package com.hj.wxmp.mobile.entity;

import java.util.ArrayList;
import java.util.List;

public class TblMenu {
	
	private int id;

	private int level;
	
	private int parentId;

	private int seq;

	private String name;

	private int type;

	private String link;

	private String content;
	
	private List<TblMenu> childs = new ArrayList<TblMenu>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<TblMenu> getChilds() {
		return childs;
	}

	public void setChilds(List<TblMenu> childs) {
		this.childs = childs;
	}
	
}