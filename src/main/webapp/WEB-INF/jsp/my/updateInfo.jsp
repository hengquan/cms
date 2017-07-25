<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/WEB-INF/jsp/inc/sessions.jsp"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/jsp/inc/head.jsp"%>
<meta name="format-detection" content="telephone=no">

<title>完善个人信息</title>
<link href="${appRoot}/static/css/app.css" rel="stylesheet" type="text/css" media="screen">

<style type="text/css">
.am-list>li{
	background-color: inherit !important;
}
</style>
 
<script>
	function isValidate(){
		if($('#name').val()==""){
			showAlert("请输入姓名！");
			return false;
		}
		
		return true;
	}
	
	function doSubmit(){
		if(isValidate()){
			$('#btn_ok').button('loading');
			
			$('#my-form').submit();
		}
	}
	
</script>
</head>
<body class="body_bg">
	<form class="am-form" id="my-form" action="${appRoot}/my/updateInfo/submit">
		<div class="am-panel am-panel-default" style="margin-bottom: 6px;font-size:14px;">
			<div class="am-panel-bd" style="padding:15px 0 2px 10px;border-bottom: 1px solid #ccc;">
				<label>手机号：</label>
				<input type="text" class="wxmp-issue-text" id="userName" name="userName" required value="${userName}" readonly="readonly" style="background: #fff;">
			</div>
			<div class="am-panel-bd" style="padding:15px 0 2px 10px;border-bottom: 1px solid #ccc;">
				<label>姓&nbsp;&nbsp;&nbsp;&nbsp;名：</label>
				<input type="text" class="wxmp-issue-text" id="name" name="name" placeholder="请输入姓名" value="${userInfo.name}" required>
			</div>
			<div class="am-panel-bd" style="padding:15px 0 2px 10px;border-bottom: 1px solid #ccc;">
				<label>昵&nbsp;&nbsp;&nbsp;&nbsp;称：</label>
				<input type="text" class="wxmp-issue-text" id="enName" name="enName" placeholder="请输入昵称" value="${userInfo.enName}">
			</div>
			<div class="am-panel-bd" style="padding:15px 0 2px 10px;border-bottom: 1px solid #ccc;">
				<label>地&nbsp;&nbsp;&nbsp;&nbsp;址：</label>
				<input type="text" class="wxmp-issue-text" id="address" name="address" placeholder="请输入地址" value="${userInfo.address}">
			</div>
			<div class="am-panel-bd" style="padding:15px 0 2px 10px;">
				<label>邮箱地址：</label>
				<input type="text" class="wxmp-issue-text" id="email" name="email" placeholder="请输入邮箱地址" value="${userInfo.email}">
			</div>
			<hr data-am-widget="divider" style="margin:10px auto;" class="am-divider am-divider-default" />
			<div class="am-panel-bd" style="padding:5px 0 10px 10px;">
				<label>公司名称：</label>
				<input type="text" class="am-form-field wxmp-issue-text" style="width: 65% !important;" id="company" name="company" placeholder="请输入公司名称"  value="${userInfo.company}">
			</div>
		</div>
	
		<button id="btn_ok" name="btn_ok" class="am-btn am-btn-primary am-btn-block" type="button" onclick="doSubmit();" data-am-loading="{loadingText: '保存中...'}">
			保存
			<i class="am-icon-check"></i>
		</button>
	</form>
	
	<c:if test="${errorMsg !=''}">
		<div style="text-align: center;color: #FF5722;" id="wxmp-info-div">
			<label style="font-weight: 200 !important;" id="wxmp-info-text">${errorMsg}</label>
		</div>
	</c:if>
	
	

	<!-- Alert -->
	<jsp:include page="/WEB-INF/jsp/inc/alertWindow.jsp"></jsp:include>
	
</body>
</html>