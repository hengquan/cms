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
							<header class="panel-heading">首访记录审核</header>
								<form action="${appRoot}/accessRecord/hisFirstRecord" method="post" id="selectCheckMessage">
									
									<div style="float: left;position: relative;margin-top: 16px;margin-left:20px;">
										<select class="form-control" style="width: 120px;" id="checkMessage" name="state" onchange="seeAllMsg()">
											<option value="-1" <c:if test="${state==-1}">selected</c:if>>全部</option>
											<option value="0" <c:if test="${state==1}">selected</c:if>>未审核</option>
											<option value="1" <c:if test="${state==2}">selected</c:if>>审核通过</option>
											<option value="2" <c:if test="${state==3}">selected</c:if>>作废</option>
										</select>
									</div>
									<div style="float: left;position: relative;margin-top: 16px;margin-left:20px;">
											<input type="text" class="btn" style="width:500px;border:1px solid #ddd;text-align:left;" placeholder="请输入用户姓名/昵称" name="userName" value=""><span>
											<button class="btn sr-btn-imp" style="float: right" onclick="seeAllMsg()">
											<i class="icon-search"></i>
											</button>
										</span>
									</div>
									
									<div
										style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
										<a href="javascript:doDelete();" class="btn mini btn-white"><i
											class="icon-trash"></i></a>
									</div>
									
									
									<input type="hidden" value="${nowPage}" id="nowPageNumber" name="nowPage">
									<input type="hidden" value="${totalPageNum }">
								</form>
							<table class="table table-striped border-top" id="sample_1">
								<thead>
									<tr>
										<th style="width: 8px;"><input type="checkbox" name="box" class="group-checkable" data-set="#sample_1 .checkboxes" value="" /></th>
										<th class="hidden-phone">项目名称</th>
										<th class="hidden-phone">顾问姓名</th>
										<th class="hidden-phone">客户姓名</th>
										<th class="hidden-phone">客户电话</th>
										<th class="hidden-phone">访问时间</th>
										<th class="hidden-phone">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${hisFirstRecordMsg}" var="u" varStatus="s">
										<tr class="odd gradeX">
											<td><input type="checkbox" name="box" class="checkboxes" value="${u.id}" /></td>
											<td class="hidden-phone">${u.projName}</td>
											<td class="hidden-phone">${u.realName}</td>
											<td class="hidden-phone">${u.custName}</td>
											<td class="hidden-phone">${u.custPhoneNum}</td>
											<td class="hidden-phone">
												<fmt:formatDate value="${u.cTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
											</td>
											<td class="hidden-phone">
												<c:if test="${u.status == 1}">
													<button type="button" onclick="seeCheckMessages('${u.id}')" class="btn btn-send">审核</button>
												</c:if>
												<button type="button" onclick="seeAllMessages('${u.id}')" class="btn btn-send">查看详细信息</button>
												<button type="button" onclick="updateFirstRecord('${u.id}')" class="btn btn-send">修改审核信息</button>
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
						<h4 class="modal-title">确认更改审核状态警告(审核不通过请填写原因)</h4>
					</div>
					<div class="modal-body">
						<textarea name="checkContent" class="form-control" id="checkContent" style="width: 100%; height: 100px;font-size: 14px;"></textarea>
					</div>
					<div class="modal-footer">
						<input type="hidden" id="yesSubCheckMessage" />
						<button type="button" onclick="subUserStateMessage('4')" class="btn btn-send">不通过</button>
						<button type="button" onclick="subUserStateMessage('2')" class="btn btn-send">审核通过</button>
						<button type="button" onclick="subUserStateMessage('3')" class="btn btn-send">作废</button>
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
		
	<form action="${appRoot}/accessRecord/firstRecordDel" method="post" id="deleForm" name="deleForm">
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
	
	
	function seeAllMessages(id){
		window.location.href="${appRoot}/accessRecord/firstRecordDetails?id="+id;
	}
	function updateFirstRecord(id){
		window.location.href="${appRoot}/updateFirstRecord?id="+id;
	}
	
	function seeCheckMessages(id){
		window.location.href="${appRoot}/accessRecord/checkDetails?id="+id;
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
		//state  2 审核通过   3作废
		var checkedId = $("#yesSubCheckMessage").val();
		var checkContent = $("#checkContent").val();
		//alert(checkedId);
		$.ajax({
			type:'post',
			data : {"checkedId":checkedId,"state":state,"checkContent":checkContent},  
			url:'${appRoot}/accessRecord/firstRecord',
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
	
	function doAdd(){
		alert("asdfasdfa");
	} 
	</script>
	<input type="hidden" value="" id="adminId"/>
</body>
</html>
