package com.hj.wxmp.mobile.entity;

import java.util.Date;

public class Material {

	private Integer id;
	private String content;
	private String title;
	private String img;
	private String url;
	private String des;
	private String con_type;
	private String article_type;
	private Date update_time;
	private String articleTypeName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getCon_type() {
		return con_type;
	}

	public void setCon_type(String con_type) {
		this.con_type = con_type;
	}

	public String getArticle_type() {
		return article_type;
	}

	public void setArticle_type(String article_type) {
		this.article_type = article_type;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public String getArticleTypeName() {
		return articleTypeName;
	}

	public void setArticleTypeName(String articleTypeName) {
		this.articleTypeName = articleTypeName;
	}

}
