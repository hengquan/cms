<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ include file="/WEB-INF/jsp/inc/sessions.jsp"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/jsp/inc/head_bootstrap.jsp"%>

<link
	href="${appRoot}/static/assets/jquery-file-upload/css/jquery.fileupload-ui.css"
	rel="stylesheet" type="text/css">
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
							<header class="panel-heading">订单列表</header>
							<form action="${appRoot}/order/list" method="post"
								id="selectCheckMessage">
								<div style="float: left;position: relative;margin-top: 16px;margin-left:20px;">
									<select class="form-control" style="width: 120px;" id="checkMessage" name="selectState" onchange="seeAllMsg()">
										<option value="-1" <c:if test="${state==-1}">selected</c:if>>全部</option>
										<option value="0" <c:if test="${state==0}">selected</c:if>>未支付</option>
										<option value="1" <c:if test="${state==1}">selected</c:if>>已支付</option>
										<option value="2" <c:if test="${state==2}">selected</c:if>>待评价</option>
										<option value="3" <c:if test="${state==3}">selected</c:if>>已完成</option>
									</select>
								</div>
								<!-- 根据用户昵称查询 -->
								<div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<input type="text" class="btn"
										style="width: 500px; border: 1px solid #ddd; text-align: left;"
										placeholder="请输入需要查询的用户昵称" name="userName" value=""><span>
										<button class="btn sr-btn-imp" style="float: right"
											onclick="seeAllMsg()">
											<i class="icon-search"></i>
										</button>
									</span>
								</div>


								<div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<a href="javascript:doRefresh();" class="btn mini btn-white"><i
										class="icon-refresh"></i></a>
								</div>
								
								<!-- <div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<a href="javascript:redPack();" class="btn mini btn-white">红包</i></a>
								</div> -->

								<!-- <div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<a href="javascript:doDelete();" class="btn mini btn-white"><i
										class="icon-trash"></i></a>
								</div> -->


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
										<th style="width: 8px;"><input type="checkbox" name="box"
											class="group-checkable" data-set="#sample_1 .checkboxes"
											value="" /></th>
										<th class="hidden-phone">订单号</th>
										<th class="hidden-phone">支付时间</th>
										<th class="hidden-phone">专家名称</th>
										<th class="hidden-phone">预约人名称</th>
										<th class="hidden-phone">预约日期</th>
										<th class="hidden-phone">咨询类型</th>
										<th class="hidden-phone">价格</th>
										<th class="hidden-phone">订单状态</th>
										<th class="hidden-phone">咨询内容描述</th>
										<th class="hidden-phone">操作</th>
									</tr>
								</thead>
								<tbody id="theTbody">
									<c:forEach items="${messageList}" var="u" varStatus="s">
										<tr class="odd gradeX theTr">
											<td><input type="checkbox" name="box" class="checkboxes"
												value="${u.id}" /></td>
												
											<td class="hidden-phone">${u.order_sn}</td>
											<td class="hidden-phone">${u.pay_time}</td>	
											<td class="hidden-phone"><c:choose>
													<c:when test="${u.expertUserName== '' || u.expertUserName== undefined}">  
												${u.expertNickname}									   	
											</c:when>
													<c:otherwise> 
												${u.expertUserName}	
											</c:otherwise>
												</c:choose></td>	
											
											<td class="hidden-phone"><c:choose>
													<c:when test="${u.userName== '' || u.userName== undefined}">  
												${u.nickname}									   	
											</c:when>
													<c:otherwise> 
												${u.userName}	
											</c:otherwise>
												</c:choose></td>
											
											<td class="hidden-phone">${u.s_time}</td>
											
											<td class="hidden-phone">${u.type_name}</td>
											
											<td class="hidden-phone">${u.price}</td>
											
											<td class="hidden-phone">
												<c:if test="${u.order_state==0}">未支付</c:if>
												<c:if test="${u.order_state==1}">已支付</c:if>
												<c:if test="${u.order_state==2}">待评价</c:if>
												<c:if test="${u.order_state==3}">已完成</c:if>
											</td>
											<td class="hidden-phone">
												<button type="button" class="btn btn-primary" onclick="seeParticulars('${u.expert_content}')">查看内容描述</button>
											</td>
											<td class="hidden-phone">
												<button type="button" class="btn btn-primary" onclick="particularsMsg('${u.openid}')">查看评价</button>
												<button type="button" class="btn btn-primary" onclick="cancelOrder('${u.order_sn}')">取消订单</button>
											</td>
										</tr>
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
		<!-- /.modal -->
	</section>



		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title titleMessage" id="modal-title">专家简介</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form" id="itemForm" name="itemForm" >
							<input type="hidden" name="editId" id="editId">
							<div class="form-group">
								<div class="col-lg-12">
									<textarea rows="5" cols="60" class="form-control" id="textContent">
									
									</textarea>
								</div>
							</div>
						</form>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>






		<div class="modal fade" id="expertEvaluate" tabindex="-1" role="dialog"
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
							
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">姓名/昵称<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="evaluateName" />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">评价星数<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="evaluateStarNum" />
								</div>
							</div>
							
							<!-- <div class="form-group">
								<label class="col-lg-2 control-label pd-r5">评价时间<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="evaluateTime" />
								</div>
							</div> -->
							
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">评价内容<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<textarea rows="5" cols="60" class="form-control" id="evaluateContent">
									
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


	<!-- Modal -->
	<div class="modal fade" id="myModal2" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">删除警告</h4>
				</div>
				<div class="modal-body">确定删除？</div>
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn btn-default" type="button"
						id="quxiao">取消</button>
					<button class="btn btn-warning" type="button" onclick="Delete()">确定</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->

	<div class="panel-body">
		<!-- Modal -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title">设置专家警告</h4>
					</div>
					<div class="modal-body">是否确定要将选中用户设置成专家？</div>
					<div class="modal-footer">
						<button data-dismiss="modal" class="btn btn-default" type="button"
							id="quxiao">取消</button>
						<button class="btn btn-warning" type="button"
							onclick="setExpert()">确定</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->

	<div class="modal fade" id="myModalUpdatePwd" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">重置警告</h4>
				</div>
				<div class="modal-body">
					确定重置管理员密码？</br>重置后密码为:123456
				</div>
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn btn-default" type="button"
						id="quxiao">取消</button>
					<button class="btn btn-warning" type="button"
						onclick="resetPassword()">确定</button>
				</div>
			</div>
		</div>
	</div>

	<form action="${appRoot}/problem/delProblemMessage" method="post" id="deleForm"
		name="deleForm">
		<input type="hidden" name="byid" id="byid"> <input
			type="hidden" name="boxeditId" id="boxeditId">
	</form>

	<form action="${appRoot}/user/setExpert" method="post" id="checkExpert"
		name="checkExpert">
		<input type="hidden" name="setExpertId" id="setExpertId"> <input
			type="hidden" name="setExpertIds" id="setExpertIds">
	</form>

	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>
	<script src="${appRoot}/static/js/jquery.sparkline.js"
		type="text/javascript"></script>

	<script type="text/javascript"
		src="${appRoot}/static/assets/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript"
		src="${appRoot}/static/assets/data-tables/DT_bootstrap.js"></script>
	<!--script for this page only-->
	<script src="${appRoot}/static/js/dynamic-table.js"></script>
	<script src="${appRoot}/static/js/dialog_alert.js"></script>
<script type="text/javascript">
	
	//取消订单
	function cancelOrder(ordersn){
		alert(ordersn);
		window.location.href="${appRoot}/order/cancelOrder?ordersn=" + ordersn;
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
		
		
	//查看咨询评价
	function particularsMsg(orderid){
		$(".titleMessage").html("咨询订单评价");
		//$("#textContent").val(experMsg);
		$.ajax({
			type : 'post',
			data : {"id" : orderid},
			url : '${appRoot}/order/expertEvaluate',
			dataType : 'json',
			success : function(data) {
				if (data.msg == 0) {
					$("#evaluateName").val(data.theExpertMsg.nickname);
					$("#evaluateStarNum").val(data.theExpertMsg.eva_num);
					//$("#evaluateTime").val(data.theExpertMsg.create_time);
					$("#evaluateContent").html(data.theExpertMsg.eva_content);
					
					var $modal = $('#expertEvaluate');
					$modal.modal();
				} else {
					windowShow("操作失败", "");
				}
			}
		});
		
		
		

	}
	
	//查看用户简介
	function seeParticulars(experMsg){
		$(".titleMessage").html("咨询内容描述");
		$("#textContent").val(experMsg);
		var $modal = $('#myModal');
		$modal.modal();
	}
	
	
	//发送红包
	function redPack(){
		window.location.href="${appRoot}/wx/sendRedPack";
	}
	
	
	$(function() {
		$('.input-group').hide();
		$('#sample_1_info').hide();
		$('.dataTables_paginate').hide();
		$("#sample_1_length .form-control").hide();
		$("#sample_1_length .js-add").hide();
		$("#sample_1_length .js-ref").hide();
		$("#sample_1_length .js-del").hide();
	});

	function doRefresh() {
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
		var flag = checkbox();
		if (flag) {
			var $modal = $('#myModal2');
			$modal.modal();
		}
	}

	function Del() {
		$("#deleForm").submit();
	}

	function Delete() {
		$("#deleForm").submit();
	}
</script>
	<input type="hidden" value="" id="adminId" />
</body>
</html>

