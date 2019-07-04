package com.hj.web.entity;

import java.util.Date;
import java.util.List;

public class Article {
	private String id;

	private String articleName;

	private String articleType;

	private String setArticleTypeName;

	private String detail;

	private Integer isPush;

	private String source;

	private String userId;

	private String picUrl;

	private Integer isComment;

	private String keyword;

	private String language;

	private Integer collectionNum;

	private Integer discussNum;

	private Integer isValidate;

	private String userName;

	private Integer partakeNum;

	private Integer top;

	private Integer views;

	private String videoUrl;

	private Date createTime;

	private Date updateTime;

	private String article;
	
	private String relevancyId;
	
	private Integer sort;
	
	private String roleId;
	
	private String roleName;
	
	private List<String> contentImg;

	public List<String> getContentImg() {
		return contentImg;
	}

	public void setContentImg(List<String> contentImg) {
		this.contentImg = contentImg;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRelevancyId() {
		return relevancyId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setRelevancyId(String relevancyId) {
		this.relevancyId = relevancyId;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getSetArticleTypeName() {
		return setArticleTypeName;
	}

	public void setSetArticleTypeName(String setArticleTypeName) {
		this.setArticleTypeName = setArticleTypeName;
	}

	public Integer getIsValidate() {
		return isValidate;
	}

	public void setIsValidate(Integer isValidate) {
		this.isValidate = isValidate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName == null ? null : articleName.trim();
	}

	public String getArticleType() {
		return articleType;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail == null ? null : detail.trim();
	}

	public Integer getIsPush() {
		return isPush;
	}

	public void setIsPush(Integer isPush) {
		this.isPush = isPush;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source == null ? null : source.trim();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl == null ? null : picUrl.trim();
	}

	public Integer getIsComment() {
		return isComment;
	}

	public void setIsComment(Integer isComment) {
		this.isComment = isComment;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword == null ? null : keyword.trim();
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language == null ? null : language.trim();
	}

	public Integer getCollectionNum() {
		return collectionNum;
	}

	public void setCollectionNum(Integer collectionNum) {
		this.collectionNum = collectionNum;
	}

	public Integer getDiscussNum() {
		return discussNum;
	}

	public void setDiscussNum(Integer discussNum) {
		this.discussNum = discussNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public Integer getPartakeNum() {
		return partakeNum;
	}

	public void setPartakeNum(Integer partakeNum) {
		this.partakeNum = partakeNum;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl == null ? null : videoUrl.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article == null ? null : article.trim();
	}
}