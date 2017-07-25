<%@page import="com.hj.wxmp.mobile.common.HashSessions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/jsp/inc/head.jsp"%>
<title>登录</title>
<link href="${appRoot}/static/css/app.css" rel="stylesheet"
	type="text/css" media="screen">
<script type="text/javascript">
  	function doSubmit(){
  		if(isValidate()){
  			$('#btn_ok').button('loading');
  			
  			$("#myForm").submit();
  		}
  	}
  	
  	function isValidate(){
  		if(""==$('#userName').val().trim()){
  			showAlert("请输入用户名!");
  			return false;
  		}else if(""==$('#password').val().trim()){
  			showAlert("请输入密码!");
  			return false;
  		}
  		return true;
  	}
</script>
</head>
<body class="login-bg" style="overflow: hidden;">
	<div class="sssh-login">
		<p class="empty"></p>
		<h1>${appTitle}管理平台</h1>
		<form action="${appRoot}/login.ky" id="myForm" name="myForm" method="post">
			<div class="login">
				<p>
					<input type="text" placeholder="请输入用户名" alt="请输入用户名" name="userName" id="userName" value="${loginId}" onkeydown='if(event.keyCode==13){$("#password").focus();}'>
				</p>
				<p>
					<input class="eye" type="password" placeholder="请输入密码" alt="请输入密码" name="password" id="password" value="" onkeydown='if(event.keyCode==13){doSubmit();}'>
				</p>
				<p class="button">
					<button type="button" value="" name="btn_ok" id="btn_ok" onclick="doSubmit()" data-am-loading="{loadingText: '登录中...'}">登 录</button>
					<!-- <input type="button" name="btn_ok" id="btn_ok" onclick="doSubmit();" value="登 录" data-am-loading="{loadingText: 'loading...'}"/> -->
				</p>
			</div>
			
			<c:if test="${loginFaild !=''}">
				<div style="text-align: center;color: #FF5722;" id="wxmp-info-div">
					<label style="font-weight: 200 !important;" id="wxmp-info-text">${loginFaild}</label>
				</div>
			</c:if>
		</form>
	</div>
	
	<!-- Alert -->
    <jsp:include page="/WEB-INF/jsp/inc/alertWindow.jsp"></jsp:include>
</body>
</html>