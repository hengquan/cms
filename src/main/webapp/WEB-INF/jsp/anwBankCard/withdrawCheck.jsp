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
							<header class="panel-heading">提现审核页面</header>
								<form action="${appRoot}/tblBankCardService/list" method="post" id="selectCheckMessage">
									
									<div style="float: left;position: relative;margin-top: 16px;margin-left:20px;">
										<select class="form-control" style="width: 120px;" id="checkMessage" name="state" onchange="seeAllMsg()">
											<option value="0" <c:if test="${state==0}">selected</c:if>>全部</option>
											<option value="1" <c:if test="${state==1}">selected</c:if>>未审核</option>
											<option value="2" <c:if test="${state==2}">selected</c:if>>审核通过</option>
											<option value="3" <c:if test="${state==3}">selected</c:if>>审核不通过</option>
										</select>
									</div>
									<div style="float: left;position: relative;margin-top: 16px;margin-left:20px;">
											<input type="text" class="btn" style="width:500px;border:1px solid #ddd;text-align:left;" placeholder="请输入用户姓名/昵称" name="userName"><span>
											<button class="btn sr-btn-imp" style="float: right" onclick="seeAllMsg()">
											<i class="icon-search"></i>
											</button>
										</span>
									</div>
									
									<%-- <div style="float: right;position: relative;margin-top: 16px;margin-right: 100px;">									
										<select class="form-control" style="width: 120px;" id="pageSize" name="pageSize" onchange="seeAllMsg()">
											<option value="10" <c:if test="${pageSize==10}">selected</c:if>>10</option>
											<option value="25" <c:if test="${pageSize==25}">selected</c:if>>25</option>
											<option value="50" <c:if test="${pageSize==50}">selected</c:if>>50</option>
											<option value="100" <c:if test="${pageSize==100}">selected</c:if>>100</option>
										</select>
									</div>			 --%>						
									<input type="hidden" value="${nowPage}" id="nowPageNumber" name="nowPage">
									<input type="hidden" value="${totalPageNum }">
								</form>
							<table class="table table-striped border-top" id="sample_1">
								<thead>
									<tr>
										<th style="width: 8px;"><input type="checkbox" name="box" class="group-checkable" data-set="#sample_1 .checkboxes" value="" /></th>
										<th class="hidden-phone">用户头像</th>
										<th>用户姓名/昵称</th>
										<th>开户人姓名</th>
										<th class="hidden-phone">联系方式</th>
										<th class="hidden-phone">提现金额</th>
										<th class="hidden-phone">审核状态</th>
										<th class="hidden-phone">开户行</th>
										<th class="hidden-phone">银行卡号</th>
										<th class="hidden-phone">创建时间</th>
										<th class="hidden-phone">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${checkMsgs}" var="info" varStatus="s">
										<tr class="odd gradeX">
											<td><input type="checkbox" name="box" class="checkboxes" value="${info.id}" /></td>
											<td class="hidden-phone"><img src="${info.headimgurl}" width="50px" height="50px"></td>
											<td>
												<c:choose>
												   <c:when test="${info.user_name== '' || info.user_name== undefined}">  
														${info.nickname}									   	
												   </c:when>
												   <c:otherwise> 
												   		${info.user_name}	
												   </c:otherwise>
												</c:choose>
											</td>
											<td class="hidden-phone">${info.name}</td>
											<td class="hidden-phone">${info.phone}</td>
											<td class="hidden-phone">${info.withdraw_money}</td>
											<c:if test="${info.state == 1}">
												<td class="hidden-phone">未审核</td>
											</c:if>
											<c:if test="${info.state == 2}">
												<td class="hidden-phone">审核通过</td>
											</c:if>
											<c:if test="${info.state == 3}">
												<td class="hidden-phone">审核不通过</td>
											</c:if>
											<td class="hidden-phone">${info.opening_bank}</td>
											<td class="hidden-phone">${info.bank_card_mark}</td>
											<td class="hidden-phone">${info.create_time}</td>
											<td class="hidden-phone">
												<c:if test="${info.state == 1}">
													<button type="button" onclick="subUserMessage('${info.id}',${info.state})" class="btn btn-send">审核</button>
												</c:if>
												<c:if test="${info.state == 2}">
													<button type="button" onclick="subUserMessage('${info.id}',${info.state})" class="btn btn-send">审核通过</button>
												</c:if>
												<c:if test="${info.state == 3}">
													<button type="button" onclick="subUserMessage('${info.id}',${info.state})" class="btn btn-send">审核未通过</button>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						<!-- <div class="panel-footer"> -->
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
						<!-- </div> -->
						</section>
					</div>
				</div>
				<!-- page end-->
			</section>
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
	<div class="panel-body">
		<!-- Modal -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title">删除警告</h4>
					</div>
					<div class="modal-body">确定删除？</div>
					<div class="modal-footer">
						<button data-dismiss="modal" class="btn btn-default" type="button" id="quxiao">取消</button>
						<button class="btn btn-warning" type="button" onclick="Del()">确定</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- 确认提交审核状态 -->
	<div class="panel-body">
		<!-- Modal -->
		<div class="modal fade" id="isCheckState" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title">确认更改审核状态警告</h4>
					</div>
					<div class="modal-body">您确定要更改审核状态吗？</div>
					<div class="modal-footer">
						<input type="hidden" id="yesSubCheckMessage" />
						<button type="button" onclick="subUserStateMessage('2')" class="btn btn-send">审核通过</button>
						<button type="button" onclick="subUserStateMessage('3')" class="btn btn-send">审核不通过</button>
						<button data-dismiss="modal" class="btn btn-default" type="button" id="quxiao">取消</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title titleMessage" id="modal-title">问题信息</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form" id="itemForm" name="itemForm" >
							<input type="hidden" name="editId" id="editId">
							<div class="form-group" id="textMessge">
								<label class="col-lg-2 control-label pd-r5">文字消息<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<textarea rows="5" cols="60" class="form-control" id="textContent">
									
									</textarea>
								</div>
							</div>
							<div class="form-group" id="phoneMessge">
								<label class="col-lg-2 control-label pd-r5">语音消息<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<audio src="" controls="controls" id="phoneContent">
									</audio>
								</div>
							</div>
							<div class="form-group" id="imgMessge">
								<label class="col-lg-2 control-label pd-r5">图片消息<font style="color:red;"></font></label>
								<div class="col-lg-10" id="imgContent">
								<!-- 图片放置区 -->
								</div>
							</div>
							<div class="form-group" id="videoMessge">
								<label class="col-lg-2 control-label pd-r5">视频地址(ogg、MP4)<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<video src="" controls="controls" width="430" height="330" id="videoContent">
									</video>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button data-dismiss="modal" type="button" id="quxiao" class="btn btn-send">返回</button>
								</div>
							</div>
						</form>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
	
	<div class="modal fade" id="seeOneCheckMessage" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="modal-title">举报信息</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form" id="itemForm" name="itemForm" >
							<input type="hidden" name="editId" id="editId">
							<div class="form-group">
								<!-- <label class="col-lg-2 control-label pd-r5">文字消息<font style="color:red;">*</font></label> -->
								<div class="col-lg-12">
									<textarea rows="5" cols="60" class="form-control" id="checkTextContent">
									
									</textarea>
								</div>
							</div>
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button data-dismiss="modal" type="button" id="quxiao" class="btn btn-send">返回</button>
								</div>
							</div>
						</form>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		
	<form action="${appRoot}/user/delCheckMessage" method="post" id="deleForm" name="deleForm">
		<input type="hidden" name="byid" id="byid">
	    <input type="hidden" name="boxeditId" id="boxeditId">
	</form>

	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>
	<script src="${appRoot}/static/js/jquery.sparkline.js" type="text/javascript"></script>

	<script type="text/javascript" src="${appRoot}/static/assets/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${appRoot}/static/assets/data-tables/DT_bootstrap.js"></script>
	<!--script for this page only-->
	<script src="${appRoot}/static/js/zzk-shenhe.js"></script>
    <script src="${appRoot}/static/js/dialog_alert.js"></script>
	<script type="text/javascript">
	
	$(function(){
		$('.input-group').hide();
		$('#sample_1_info').hide();
		$('.dataTables_paginate').hide();
		$("#sample_1_length .form-control").hide();
	});
	
	//查看举报信息
	function seeCheckMessage(obj){
		//清空
		$("checkTextContent").html("");
		
		var obj = $(obj);
		var msg = obj.attr("message");
		$("#checkTextContent").html(msg);
		var $modal = $('#seeOneCheckMessage');
		$modal.modal();
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
	
	
	
	
	
	//是否提交审核结果
	function subUserMessage(id,isPass){
		if(isPass!=1){
			windowShow("该用户已经过审核，请勿重复操作！","");
			return;
		}
		var $modal = $('#isCheckState');
		$modal.modal();
		$("#yesSubCheckMessage").val(id);
	}
	
	//设置审核的状态---确认提交
	function subUserStateMessage(state){
		//alert(state);
		//state  2 审核通过   3审核不通过
		var checkedId = $("#yesSubCheckMessage").val();
		//alert(checkedId);
		$.ajax({
			type:'post',
			data : {"checkedId":checkedId,"state":state},  
			url:'${appRoot}/tblBankCardService/updateState',
			dataType:'json',
			success:function(data){
				if(data.msg == 100){
					windowShow("提交成功","");
					seeAllMsg();
				}else{
					windowShow("提交失败","");
				}
			}
		});
		
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

	//查看举报信息
	function seeMessage(){
		$('#jubaoMessage').modal();
	}
	
	function doDel(id) {
		var $modal = $('#myModal1');
		$("#byid").val(id);
		$modal.modal();
	}

	function Del() {
		$("#deleForm").submit();
	}

	function Delete() {
			$("#deleForm").submit();
	}
	
	function doRefresh(){
		 location.reload();
	}
	</script>
	<input type="hidden" value="" id="adminId"/>
</body>
</html>
