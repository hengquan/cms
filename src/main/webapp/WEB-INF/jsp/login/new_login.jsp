<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ include file="/WEB-INF/jsp/inc/sessions.jsp"%>
<!DOCTYPE html>
<html lang="zh-Hans-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<title>后台登录</title>
<link rel="stylesheet"
	href="${appRoot}/static/new/css/bootstrap.font.min.css" />

<link rel="stylesheet" href="${appRoot}/static/new/lib/swiper.min.css" />
<link rel="stylesheet" href="${appRoot}/static/new/css/style.css" />
</head>
<body>
	<div class="swiper-container log-bg">
		<div class="swiper-wrapper">
			<div class="swiper-slide"
				style="background-image:url(${appRoot}/static/new/img/bg1.jpg)"></div>
			<div class="swiper-slide"
				style="background-image:url(${appRoot}/static/new/img/bg2.jpg)"></div>
			<div class="swiper-slide"
				style="background-image:url(${appRoot}/static/new/img/bg3.jpg)"></div>
		</div>
	</div>

	<div class="loginbox" style="height: 500px">
		<div class="carent-logo" style="font-family: 楷体; font-size: 45px;">
			<b>CMS管理系统</b>
		</div>
		<div class="login-content" style="margin-top: -20px;">
			<form action="${appRoot}/login.ky" id="myForm" name="myForm"
				method="post">

				<c:if test="${loginFaild !=''}">
					<div style="text-align: center; color: #FF5722;" id="wxmp-info-div">
						<label style="font-weight: 200 !important;" id="wxmp-info-text">${loginFaild}</label>
					</div>
				</c:if>
				<div class="form-group">
					<i class="fa fa-user"></i> <input type="text"
						class="form-control ms-inp" placeholder="请输入用户名" name="userName"
						id="userName">
				</div>
				<div class="form-group">
					<i class="fa fa-unlock-alt"></i> <input type="password"
						class="form-control ms-inp" placeholder="请输入密码" name="password"
						id="password">
				</div>
				<div class="form-group">
					<i class="fa fa-user"></i> <input type="text"
						class="form-control ms-inp" placeholder="请输入验证码" id="imgCode"
						name="imgCode" maxlength="4"> <span
						style="float: right; margin-top: -40px;"> <img
						class="fa fa-user" src="${appRoot}/api/iCode" id="id_iCode" /> <a
						id="kanbuq" href="javascript:changeIcode()">看不清，换一张</a>
					</span>
				</div>
		</div>
		<button onclick="doSubmit()" class="btn btn-login">登录</button>
		<div class="checkbox">
			<label> <input type="checkbox"> 记住我
			</label>
		</div>
		</form>
	</div>
	</div>

	<script src="${appRoot}/static/new/js/jquery.min.js"></script>
	<script src="${appRoot}/static/new/js/bootstrap.min.js"></script>
	<script src="${appRoot}/static/new/js/main.js"></script>

	<!-- Swiper JS -->
	<script src="${appRoot}/static/new/lib/swiper.jquery.min.js"></script>
	<script>
		var swiper = new Swiper('.swiper-container', {
			effect : 'fade',
			autoplay : 4000
		});

		function doSubmit() {
			if (isValidate()) {
				$("#myForm").submit();
			}
		}

		function isValidate() {
			var name = true;
			var pwd = true;
			var code = true;
			if ($('#userName').val() == null
					|| $('#userName').val().trim() == "") {
				alert("请输入用户名!");
				name = false;
				return false;
			}
			if ($('#password').val() == null
					|| $('#password').val().trim() == "") {
				alert("请输入密码!");
				pwd = false;
				return false;
			}
			if ($('#imgCode').val() == null || $('#imgCode').val().trim() == "") {
				alert("请输入验正码!");
				code = false;
				return false;
			}
			return name && pwd && code;
		}

		function changeIcode() {
			// 注意后面的time是随便给的，你也可以写成a或者b，值是个时间或随机数就行，这样子的目的是因为你发个请求时，浏览器发现url相同了的话，直接用缓存里的图片，导致验证码不会改变，所以加个参数让url不同，不同就重新请求新的验证码
			document.getElementById("id_iCode").src = "${appRoot}/api/iCode?time="
					+ new Date().getTime();
		}
	</script>
</body>
</html>