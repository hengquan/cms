<%@page import="com.hj.web.common.HashSessions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/jsp/inc/head.jsp"%>
<title>注册</title>
<link href="${appRoot}/static/css/app.css" rel="stylesheet" type="text/css" media="screen">
<script src="${appRoot}/static/js/app-validator.js"></script>

<script type="text/javascript">
  	function doSubmit(){
  		if(isValidate()){
  			$('#btn_ok').button('loading');
  			
  			$("#loginForm").submit();
  		}
  	}
  	
  	function doFocus(){
  		$('#wxmp-info-text').text('');
  	}
  	
  	function isValidate(){
  		if(!$("input[type='checkbox']").is(':checked')){
  			showAlert("请先阅读注册协议并同意");
  			return false;
  		}
  		if(""==$('#userName').val().trim()){
  			showAlert("请输入手机号!");
  			return false;
  		}
  		else if(!isMobile($('#userName').val())){
  			showAlert("手机号码格式不对!");
  			return false;
  		}
  		else if(""==$('#password').val().trim()){
  			showAlert("请输入密码!");
  			return false;
  		}else if($('#password').val().trim() != $('#confirmPassword').val().trim()){
  			showAlert("两次输入的密码不一致!");
  			return false;
  		}
  			
  		return true;
  	}
  	
  	function openUrl(id){
		window.location.href="http://cxzj.dingshengjishu.com/jcadmin/PhoneService/viewNotice.do?id="+id;
	}
  	
</script>
</head>
<body class="body_bg">
	<!-- login form -->
	
	<form action="${appRoot}/register/submit" id="loginForm" name="loginForm" method="post" data-am-validator>
		<div data-am-widget="list_news" class="am-list-news am-list-news-default div_body_form">
			<table style="width:100%;font-size: 16px; text-align: center;" id="form_table">
				<tr>
					<td class="wxmp-homeBase-label-b"><span style="color: #0e90d2;font-size: 20px;" class="am-icon-user"></span></td>
					<td><input class="wxmp-input-text" type="text" name="userName" id="userName" placeholder="请输入手机号" value="${userName}" required/></td>
				</tr>
				<tr>
					<td class="wxmp-homeBase-label-b"><span style="color: #0e90d2;font-size: 20px;" class="am-icon-lock"></span></td>
					<td><input class="wxmp-input-text" type="password" name="password" id="password" placeholder="密码"/></td>
				</tr>
				<tr>
					<td class="wxmp-homeBase-label-b"><span style="color: #0e90d2;font-size: 20px;" class="am-icon-lock"></span></td>
					<td><input class="wxmp-input-text" type="password" name="confirmPassword" id="confirmPassword" placeholder="确认密码"/></td>
				</tr>
			</table>
		</div>
		
		<div class="am-list-news am-list-news-default" style="margin-right: 10px !important;">
			<input class="am-btn am-btn-primary am-btn-block  am-radius am-btn-loading" style="margin-left: 10px;margin-top: 10px;width: 98%;" type="button" name="btn_ok" id="btn_ok" onclick="doSubmit();" value="注册" data-am-loading="{loadingText: '注册中...'}" />
		</div>
		
		<label class="am-checkbox" style="margin-left: 10px;margin-top:15px;">
	      <input type="checkbox" id="xyCheck" name="xyCheck" checked="checked" value="" data-am-ucheck checked>
	      	我已阅读并同意<span onclick="openUrl(44)" style="color:#3F51B5;">《城乡之间注册协议》</span>
	    </label>
		 
	</form>
	
	<c:if test="${errMsg !=''}">
		<div style="text-align: center;color: #FF5722;margin-top:10px;" id="wxmp-info-div">
			<label style="font-weight: 200 !important;" id="wxmp-info-text">${errMsg}</label>
		</div>
    </c:if>
	
	<!-- Alert -->
	<jsp:include page="/WEB-INF/jsp/inc/alertWindow.jsp"></jsp:include>
</body>
</html>