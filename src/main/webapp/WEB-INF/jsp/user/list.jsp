<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ include file="/WEB-INF/jsp/inc/sessions.jsp"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/jsp/inc/head_bootstrap.jsp"%>

<link href="${appRoot}/static/assets/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css">
<!-- Custom styles for this template -->
<link href="${appRoot}/static/css/style.css" rel="stylesheet">
<link href="${appRoot}/static/css/style-responsive.css" rel="stylesheet" />

<title>${appTitle}</title>
</head>
<body>

	<section id="container" class="">
		<!--header start-->
		<%@ include file="/WEB-INF/jsp/inc/header.jsp"%>
		<!--header end-->

		<!--sidebar start-->
		<%@ include file="/WEB-INF/jsp/inc/sidebar.jsp"%>
		<!--sidebar end-->

		<!--main content start-->
		<section id="main-content">
			<section class="wrapper">
				<!-- page start-->
				<div class="row">
					<div class="col-lg-12">
						<section class="panel">
							<header class="panel-heading">用户列表 </header>
								<form action="${appRoot}/user/userList" method="post" id="selectCheckMessage">
									<!-- 选择不同的排行类型 -->
									<input type="hidden" id="selectState" name="selectState" value="${state}">
									<!-- 根据用户昵称查询 -->
									<div style="float: left;position: relative;margin-top: 16px;margin-left:20px;">
											<input type="text" class="btn" style="width:500px;border:1px solid #ddd;text-align:left;" placeholder="请输入需要查询的用户姓名/昵称/电话号码" name="userName" value="${userName }"><span>
											<button class="btn sr-btn-imp" style="float: right" onclick="seeAllMsg()">
											<i class="icon-search"></i>
											</button>
										</span>
									</div>
									
									
									<div style="float: left;position: relative;margin-top: 16px;margin-left:20px;">
										<a href="javascript:doRefresh();" class="btn mini btn-white" title="刷新"><i class="icon-refresh"></i></a>
									</div>
									
									<div style="float: left;position: relative;margin-top: 16px;margin-left:20px;">
										<a href="javascript:doDelete();" class="btn mini btn-white" title="删除"><i class="icon-trash"></i></a>
									</div> 
									
									<input type="hidden" value="${nowPage}" id="nowPageNumber" name="nowPage">
									<input type="hidden" value="${totalPageNum }">
								</form>
							
							<table class="table table-striped border-top" id="sample_1" >
								<thead>
								<tr>
									<th style="width: 8px;"><input type="checkbox" name="box" class="group-checkable" data-set="#sample_1 .checkboxes" value="" /></th>
									<th class="hidden-phone">微信头像</th>
									<th class="hidden-phone">姓名/昵称</th>
									<th class="hidden-phone">用户类型</th>
									<th class="hidden-phone">性别</th>
									<th class="hidden-phone">电话</th>
									<th class="hidden-phone">操作</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${userList}" var="u" varStatus="s">
								<c:if test="${!empty u.nickname }">
									<tr class="odd gradeX">
										<td><input type="checkbox" name="box" class="checkboxes" value="${u.openid}" /></td>
										<td class="hidden-phone"><img src="${u.headimgurl}" height="50" width=""></td>
										<td class="hidden-phone">
											<c:choose>
											   <c:when test="${u.realname== '' || u.realname== undefined}">  
													${u.nickname}							   	
											   </c:when>
											   <c:otherwise> 
											   		${u.realname}
											   </c:otherwise>
											</c:choose>
										</td>
										<td class="hidden-phone">${u.userRole.role_name}</td>
										<td class="hidden-phone">
											<c:if test="${u.sex==1}">男</c:if>
											<c:if test="${u.sex==2}">女</c:if>
										</td>
										<td class="hidden-phone">${u.mainphonenum}</td>
										<td><button type="button" class="btn btn-send"
													onclick="sellAllProject('${u.id}')">查看所属项目</button>
											<button type="button" class="btn btn-send"
													onclick="seeAllKeHu('${u.id}')">查看所有客户</button>
										</td>
						 			</tr>
								</c:if>
							</c:forEach>
							</tbody>
							</table>
							<nav class="clearfix">
								<ul class="pagination pull-left">
									<li><div class="dataTables_info" id="sample_1_info">共${totalPageNum } 页,当前为第${nowPage}页</div></li>
								</ul>
								<ul class="pagination pull-right">
									<li><a href="javascript:doPanation(1)">首页</a></li>
									<li>
										<a href="javascript:doPanation(${nowPage - 1 < 1 ? 1 : nowPage - 1})" aria-label="Previous">
											<span aria-hidden="true">&laquo;</span>
										</a>
									</li>
										<c:forEach begin="${nowPage - 5 > 0 ? nowPage - 5 : 1 }" end="${nowPage + 5 > totalPageNum ? totalPageNum : nowPage + 5}" var="t">
											<li <c:if test="${nowPage == t}">class="act"</c:if>><a href="javascript:doPanation(${t})">${t}</a></li>
										</c:forEach>
									<li>
										<a href="javascript:doPanation(${nowPage + 1 > totalPageNum ? totalPageNum : nowPage + 1})" aria-label="Next">
											<span aria-hidden="true">&raquo;</span>
										</a>
									</li>
									<li><a href="javascript:doPanation(${totalPageNum})">末页</a></li>
								</ul>
							</nav>
						</section>
					</div>
				</div>
				<!-- page end-->
			</section>
		</section>
		
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="modal-title">所属项目列表</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form" id="itemForm" name="itemForm" >
							<input type="hidden" name="editId" id="editId">
							<div class="form-group" id="userProjectMsg">
								
							</div>
						</form>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
	</section>

	
	

	<!-- Modal -->
	<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">删除警告</h4>
				</div>
				<div class="modal-body">确定删除？</div>
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn btn-default" type="button" id="quxiao">取消</button>
					<button class="btn btn-warning" type="button" onclick="Delete()">确定</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->
	
	
	
	<!-- Modal -->
	<div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">降级警告</h4>
				</div>
				<div class="modal-body">是否确定要将选中用户降级为普通用户？</div>
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn btn-default" type="button" id="quxiao">取消</button>
					<button class="btn btn-warning" type="button" onclick="queDing()">确定</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->
	
	
	
	

	<div class="panel-body">
		<!-- Modal -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title">设置专家警告</h4>
					</div>
					<div class="modal-body">是否确定要将选中用户设置成专家？</div>
					<div class="modal-body">(请确保该用户已有手机号，如果没有则不能成为专家用户)</div>
					<div class="modal-footer">
						<button data-dismiss="modal" class="btn btn-default" type="button" id="quxiao">取消</button>
						<button class="btn btn-warning" type="button" onclick="setExpert()">确定</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->
	
	<div class="modal fade" id="myModalUpdatePwd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">重置警告</h4>
				</div>
				<div class="modal-body">确定重置管理员密码？</br>重置后密码为:123456</div>
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn btn-default" type="button" id="quxiao">取消</button>
					<button class="btn btn-warning" type="button" onclick="resetPassword()">确定</button>
				</div>
			</div>
		</div>
	</div>
	
	<form action="${appRoot}/user/del" method="post" id="deleForm" name="deleForm">
		<input type="hidden" name="byid" id="byid">
	    <input type="hidden" name="boxeditId" id="boxeditId">
	</form>
	
	<!-- 专家降级为普通用户 -->
	<form action="${appRoot}/user/DemotionUser" method="post" id="demotionUser" name="demotionUser">
		<input type="hidden" name="userId" id="userId">
	    <input type="hidden" name="boxUserId" id="boxUserId">
	</form>

	<form action="${appRoot}/user/setExpert" method="post" id="checkExpert" name="checkExpert">
		<input type="hidden" name="setExpertId" id="setExpertId">
	    <input type="hidden" name="setExpertIds" id="setExpertIds">
	</form>

	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>
	<script src="${appRoot}/static/js/jquery.sparkline.js" type="text/javascript"></script>

	<script type="text/javascript" src="${appRoot}/static/assets/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${appRoot}/static/assets/data-tables/DT_bootstrap.js"></script>
	<!--script for this page only-->
	<script src="${appRoot}/static/js/dynamic-table.js"></script>
    <script src="${appRoot}/static/js/dialog_alert.js"></script>
	<script type="text/javascript">
	
	
	//查看该用户所有对应的项目
	function sellAllProject(userId){
		$.ajax({
			type:'post',
			data : {"userId":userId},  
			url:'${appRoot}/user/proJect',
			dataType:'json',
			success:function(data){
				var htmlmsg ='';
				console.log(data);
				if(data.msg==100){
					var pro = data.projectMessage;
					console.log(pro);
					if(pro.length>0){
						for(var i=0;i<pro.length;i++){
							htmlmsg+='<label class="col-lg-4 control-label pd-r5">'
							+'<button data-dismiss="modal" class="btn btn-send" type="button" '
							+'id="userProjectMsg" style="width:150px;">'+ pro[i].projName +'</button>'
							+'</label>';
						}
						$("#userProjectMsg").html(htmlmsg);
						var $modal = $('#myModal');
						$modal.modal();
					}else{
						windowShow("该用户暂无项目","");
					}
				}else{
					windowShow("操作失败","");
				}
			}
		});
	}
	
	
	
	
	//查看所有客户
	function seeAllKeHu(userId){
		
		window.location.href="${appRoot}/user/allkehu?userId="+userId;
	}
	
	
	
	//选择不同的类型
	function selectState(num){
		$("#selectState").val(num);
		seeAllMsg();
	}
	//选择不同的页数
	function doPanation(number){
		$("#nowPageNumber").val(number);
		seeAllMsg();
	}
	
	//根据选择查看信息
	function seeAllMsg(){
		$("#selectCheckMessage").submit();
	}
	
	//查看更多信息
	function manyMessage(userInfoId){
		$.ajax({
			type:'post',
			data : {"id":userInfoId},  
			url:'${appRoot}/user/singleMessage',
			dataType:'json',
			success:function(data){
				if(data.msg==0){
					var $modal = $('#myModal');
					$modal.modal();
				}else{
					windowShow("操作失败","");
				}
			}
		});
	}
	
	
	
	$(function(){
		$('.input-group').hide();
		$('#sample_1_info').hide();
		$('.dataTables_paginate').hide();
		$("#sample_1_length .form-control").hide();
		$("#sample_1_length .js-add").hide();
		$("#sample_1_length .js-ref").hide();
		$("#sample_1_length .js-del").hide();
	});
	
	
	function doRefresh(){
		 location.reload();
	}
	

	function checkbox() {
		var str = document.getElementsByName("box");
		var objarray = str.length;
		var chestr = "";
		var jy = false;
		for (i = 0; i < objarray; i++) {
			if (str[i].checked == true) {
				jy = true;
				chestr += str[i].value + ",";
			}
		}
		if (!jy) {
			windowShow("请选择要删除的数据！")
			$('#quxiao').click();
			return false;
		} else {
			$("#boxeditId").val(chestr);
			return true;
		}
	}

	function doDelete() {
		var flag=checkbox();
		if(flag){
		  var $modal = $('#myModal2');
		  $modal.modal();
		}
	}
	
	function Del() {
		$("#deleForm").submit();
	}
	
	//专家降级为普通用户
	function doDemotionUser(){
		var flag=checkboxUser();
		if(flag){
		  var $modal = $('#myModal3');
		  $modal.modal();
		}
	}
	
	function checkboxUser() {
		var str = document.getElementsByName("box");
		var objarray = str.length;
		var chestr = "";
		var jy = false;
		for (i = 0; i < objarray; i++) {
			if (str[i].checked == true) {
				jy = true;
				chestr += str[i].value + ",";
			}
		}
		if (!jy) {
			windowShow("请选择要降级的用户！")
			$('#quxiao').click();
			return false;
		} else {
			$("#boxUserId").val(chestr);
			return true;
		}
	}
	
	function queDing(){
		$("#demotionUser").submit();
	}
	
	
	//设置专家
	function doSelectUser() {
		var flag=checkUserBox();
		if(flag){
		  var $modal = $('#myModal1');
		  $modal.modal();
		}
	}
	
	
	function setExpert() {
		$("#checkExpert").submit();
	}
	
	function Delete() {
		$("#deleForm").submit();
	}
	

	
	
		
	</script>
	<input type="hidden" value="" id="adminId"/>
</body>
</html>

