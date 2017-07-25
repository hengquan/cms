<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/jsp/inc/sessions.jsp"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/jsp/inc/head.jsp"%>
<title>我的</title>
<script type="text/javascript" src="${appRoot}/static/js/app.js"></script>
<link href="${appRoot}/static/css/app.css" rel="stylesheet"
	type="text/css" media="screen">
<style type="text/css">
.table-b table td {
	border: 1px solid #F00
}
.wxmp-widget-icon{
	margin-left: 10px;
}
.widget-name{
	font-size:15px;
}
</style>
<script>
	function detail(articleType){
		/* $("#articleType").val(articleType);
		$("#searchForm").submit(); */
		
		window.location.href="<%=request.getContextPath()%>/login.ky";
	}
	
	function doLogout(){
		$('#my-confirm').modal({
	        relatedTarget: this,
	        onConfirm: function(options) {
	        	window.location.href="<%=request.getContextPath()%>/logout.ky";},onCancel : function() {return;}
		});
	}
	
	function openUrl(id){
		//http://cxzj.dingshengjishu.com/jcadmin/PhoneService/viewNotice.do?id=39
		window.location.href="http://cxzj.dingshengjishu.com/jcadmin/PhoneService/viewNotice.do?id="+id;
	}
	
	function myIssueInfo(){
		window.location.href="<%=request.getContextPath()%>/my/myIssueInfo";
	}
	
	function changePassword(){
		$("#submitForm").submit();
	}
	
	function updateInfo(){
		window.location.href="<%=request.getContextPath()%>/my/updateInfo/index";
	}
	
</script>
</head>
<body class="body_bg">
	<div data-am-widget="list_news"
		class="am-list-news am-list-news-default">
		<div class="am-list-news-bd">
			<ul class="am-list">
				<!--缩略图在标题左边-->
				<li
					class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left">
					<div class="am-u-sm-4 am-list-thumb" style="padding-left: 15px;width: 28%;">
						<!-- <a href="javascript:void()" class="">  -->
							<c:choose>
								<c:when test="${resultMap.userInfo.column5==''}">
									<img class="am-circle" src="${appRoot}/static/img/wutu.png" alt="个人头像" style="width:80px;height:auto;"/>
								</c:when>
								<c:when test="${fn:indexOf(resultMap.userInfo.column5,'http:') > -1}">
									<img class="am-circle" src="${resultMap.userInfo.column5}" alt="个人头像" style="width:80px;height:auto;"/>
								</c:when>
								<c:otherwise>
									<img class="am-circle" src="http://cxzj.dingshengjishu.com/jcadmin${resultMap.userInfo.column5}" alt="个人头像" style="width:80px;height:auto;"/>
								</c:otherwise>							
							</c:choose>
						<!-- </a> -->
					</div>

					<div class="am-u-sm-8 am-list-main" style="width:50%;" onclick="updateInfo()">
						<h3 class="am-list-item-hd" style="margin-top: 4px;">
						    ${resultMap.userInfo.name}
							<%-- <a href="javascript:void()" class="">${resultMap.userInfo.name}</a> --%>
						</h3>

						<div class="am-list-item-text"></div>
						<div class="am-list-item-text">${resultMap.userInfo.userName}</div>
					</div>
					
					<div style="width:22%; float: right;text-align: right;" onclick="updateInfo()">
						<span class="am-icon-chevron-right wxmp-widget-icon" style="color: #ccc; margin-right: 10px; margin-top: 20px;"></span>
					</div>
				</li>
			</ul>
		</div>
	</div>

	<!-- List -->
	<div id="widget-list">
		<ul class="am-list m-widget-list"
			style="transition-timing-function: cubic-bezier(0.1, 0.57, 0.1, 1); transition-duration: 0ms; transform: translate(0px, 0px) translateZ(0px);">
			
			<li onclick="myIssueInfo()">
				<a data-rel="accordion" href="###"> 
				   <span class="am-icon-navicon wxmp-widget-icon" style="color: #34B382;"></span> <span class="widget-name">我的帖子</span>
		    	   <span class="am-icon-angle-right wxmp-widget-icon" style="color: #ccc; float:right;"></span>
				</a>
			</li>
			<li onclick="changePassword()">
				<a data-rel="accordion" href="###"> 
				   <span class="am-icon-edit wxmp-widget-icon" style="color: #B37134;"></span> <span class="widget-name">修改密码</span>
		    	   <span class="am-icon-angle-right wxmp-widget-icon" style="color: #ccc; float:right;"></span>
				</a>
			</li>
			<li onclick="openUrl('3')"><a data-rel="accordion"
				href="###"> <span class="am-icon-question-circle wxmp-widget-icon"
					style="color: #8700FF;"></span> <span class="widget-name">帮助中心</span>
					
					<span class="am-icon-angle-right wxmp-widget-icon" style="color: #ccc; float:right;"></span>
			</a></li>
			<li onclick="openUrl('39')"><a data-rel="accordion"
				href="###"> <span class="am-icon-info-circle wxmp-widget-icon"
					style="color: #FF7500;"></span> <span class="widget-name">关于我们</span>
					
					<span class="am-icon-angle-right wxmp-widget-icon" style="color: #ccc; float:right;"></span>
			</a></li>
			
			<li onclick="doLogout()"><a data-rel="accordion" href="###"
				style="color: #ccc;"> <span
					class="am-icon-sign-out wxmp-widget-icon" style="color: #ccc;"></span>
					<span class="widget-name">退出当前账号</span>
			</a></li>
		</ul>
	</div>

	<form action="${appRoot}/my/changePassword" method="post" id="submitForm" name="submitForm">
	</form>

	<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
		<div class="am-modal-dialog">
			<%-- <div class="am-modal-hd">${appTitle}</div> --%>
			<div class="am-modal-bd">您确定退出当前账号吗？</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
					class="am-modal-btn" data-am-modal-confirm>确定</span>
			</div>
		</div>
	</div>

	<!-- Navbar -->
	<jsp:include page="/WEB-INF/jsp/inc/guide_my.jsp"></jsp:include>
</body>
</html>