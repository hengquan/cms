<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/WEB-INF/jsp/inc/sessions.jsp"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/jsp/inc/head.jsp"%>
<meta name="format-detection" content="telephone=no">

<title>修改密码</title>
<link href="${appRoot}/static/css/app.css" rel="stylesheet" type="text/css" media="screen">

<style type="text/css">
.am-list>li{
	background-color: inherit !important;
}
</style>
 
<script>
	function isValidate(){
		if($('#oldPassword').val()==""){
			showAlert("请输入原密码！");
			return false;
		}else if($('#newPassword').val()==""){
			showAlert("请输入新密码！");
			return false;
		}else if($('#newPassword').val()!=$('#confirmPassword').val()){
			showAlert("两次输入的新密码不一致！");
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
	<form class="am-form" id="my-form" action="${appRoot}/my/changePassword/submit">
		<div class="am-panel am-panel-default" style="margin-bottom: 6px;font-size:14px;">
			<div class="am-panel-bd" style="padding:15px 0 2px 10px;border-bottom: 1px solid #ccc;">
				<label>手&nbsp;&nbsp;机&nbsp;&nbsp;号：</label>
				<input type="text" class="wxmp-issue-text" id="userName" name="userName" required value="${userName}" readonly="readonly" style="background: #fff;">
			</div>
			<!-- <hr data-am-widget="divider" style="margin:1px auto;" class="am-divider am-divider-default" /> -->
			<div class="am-panel-bd" style="padding:15px 0 2px 10px;border-bottom: 1px solid #ccc;">
				<label>原&nbsp;&nbsp;密&nbsp;&nbsp;码：</label>
				<input type="password" class="wxmp-issue-text" id="oldPassword" name="oldPassword" placeholder="请输入原密码" required>
			</div>
			<!-- <hr data-am-widget="divider" style="margin:3px auto;" class="am-divider am-divider-default" /> -->
			<div class="am-panel-bd" style="padding:15px 0 2px 10px;">
				<label>新&nbsp;&nbsp;密&nbsp;&nbsp;码：</label>
				<input type="password" class="wxmp-issue-text" id="newPassword" name="newPassword" placeholder="请输入新密码" required>
			</div>
			<hr data-am-widget="divider" style="margin:3px auto;" class="am-divider am-divider-default" />
			<div class="am-panel-bd" style="padding:5px 0 10px 10px;">
				<label>确认密码：</label>
				<input type="password" class="am-form-field wxmp-issue-text" style="width: 65% !important;" id="confirmPassword" name="confirmPassword" placeholder="请输入新密码" required>
			</div>
		</div>
	
		<button id="btn_ok" name="btn_ok" class="am-btn am-btn-primary am-btn-block" type="button" onclick="doSubmit();" data-am-loading="{loadingText: '修改密码中...'}">
			确定
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