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

<style>
.pic_upload ul {
	list-style: none;
	overflow: hidden;
	padding: 0;
}

.pic_upload ul li {
	float: left;
	line-height: 70px;
	position: relative;
	margin-right: 10px;
}

.pic_upload ul li i {
	font-size: 20px;
	color: #9D9D9D;
	border: 1px solid #9d9d9d;
	border-radius: 5px;
	width: 120px;
	height: 70px;
	text-align: center;
	line-height: 70px;
	cursor: pointer;
	position: relative;
	z-index: 0;
}

.pic_upload ul li input[type=file] {
	width: 120px;
	height: 70px;
	font-size: 100px;
	position: absolute;
	top: 0;
	left: 0;
	z-index: 2;
	opacity: 0;
	filter: Alpha(opacity = 0);
}

.form-group:first-child {
	margin: 50px auto 20px;
}

.tr {
	text-align: center
}
</style>

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
							<form action="${appRoot}/customer/customerList" method="post"
								id="selectCheckMessage" >
								<div class="panel-heading" style="height: 50px;">
									<div class="col-lg-2" style="line-height:37px">客户列表</div>
									<!-- 根据用户昵称查询 -->
									<div
										style="float: left; position: relative;">
										<input type="text" class="btn"
											style="width: 500px; border: 1px solid #ddd; text-align: left;"
											placeholder="请输入客户姓名" name="custName" value="${name }"><span>
											<button class="btn sr-btn-imp" style="float: right"
												onclick="seeAllMsg()">
												<i class="icon-search"></i>
											</button>
										</span>
									</div>
									<input type="hidden" name="isValidate" id="isValidate" value="${isValidate }">
									<a href="javascript:seeCancellationUserMsg();" style="margin-left:20px;" class="btn mini btn-white" id="seeCancellationUserMsg">
										<c:choose>
										   <c:when test="${isValidate==null || isValidate==''}">  
										         查看所有已作废顾问的客户     
										   </c:when>
										   <c:otherwise> 
										         查看所有客户
										   </c:otherwise>
										</c:choose>
									</a>
									<!-- <a href="javascript:gaojichaxun();" class="btn mini btn-white pull-right" id="gaoji">高级查询</a> -->
								</div>
								<!-- 分页信息 -->
								<input type="hidden" value="${nowPage}" id="nowPageNumber"
									name="nowPage"> <input type="hidden"
									value="${totalPageNum }">
							</form>

							<div style="clear: both"></div>

							<table class="table table-striped border-top" >
								<thead>
									<tr>
										<th style="width: 8px;">
										</th>
										<th class="hidden-phone" style="line-height:37px">客户姓名</th>
										<th class="hidden-phone" style="line-height:37px">客户电话</th>
										<th class="hidden-phone" style="line-height:37px">性别</th>
										<th class="hidden-phone" style="line-height:37px">项目名称</th>
										<th class="hidden-phone" style="line-height:37px">关注时间</th>
										<th class="hidden-phone" style="line-height:37px">顾问姓名</th>
										<th class="hidden-phone">
											<a href="javascript:selectUsers();" class="btn mini btn-white">批量分配</a>
											<a href="javascript:doRefresh();" class="btn mini btn-white"><i
												class="icon-refresh"></i></a>
										</th>
										
									</tr>
								</thead>
								<tbody id="theTbody">
									<c:forEach items="${userMsg}" var="u" varStatus="s">
										<tr class="odd gradeX theTr">
											<td><input type="checkbox" name="box" class="checkboxes"
												value="${u.id}" /></td>
											<td class="hidden-phone">${u.custName}</td>
											<td class="hidden-phone">${u.phoneNum}</td>
											<td class="hidden-phone">${u.custSex}</td>
											<td class="hidden-phone">${u.projName}</td>
											<td class="hidden-phone">
												<fmt:formatDate value="${u.cTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
											</td>
											<td class="hidden-phone">${u.realName}</td>
											<td class="hidden-phone">
												<button type="button" onclick="editJurisdiction('${u.projId}','${u.custId }')" 
												class="btn btn-send">为客户分配顾问</button>
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









		<div class="modal fade" id="addProblemType" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="modal-title">转移客户信息</h4>
					</div>
					<div class="modal-body">
						<form action="${appRoot}/customer/transferCustMsg" method="post" class="form-horizontal"  enctype="multipart/form-data"  role="form" id="addMessage" name="itemForm" >
							<input type="hidden" name="projId" id="projId">
							<input type="hidden" name="custId" id="custId">
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">请选择用户<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<select class="form-control" name="userId" id="userData">
										
									</select>
								</div>
							</div>
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button type="submit" class="btn btn-send">确定</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>









		<div class="modal fade" id="editProblemType" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="modal-title">修改商品</h4>
					</div>
					<div class="modal-body">
						<form action="${appRoot}/anwProduct/edit" method="post" class="form-horizontal"  enctype="multipart/form-data"  role="form" id="updateMessage" name="itemForm" >
							<input type="hidden" name="editId" id="editId">
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">商品名称<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="productName1"
										name="productName" maxlength="4" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">商品价格<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="price1"
										name="price" maxlength="4" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">销量<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="salesVolume1"
										name="salesVolume" maxlength="4" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">运费<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="freight1"
										name="freight" maxlength="4" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">商品类型<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<select class="form-control doEditSelect" name="categoryId">
										<c:forEach items="${categorys}" var="u" varStatus="s">
											<option value="${u.id }">${u.name }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">商品详情<font
									style="color: red;"></font></label>
								<div class="col-lg-10">
									<textarea rows="5" cols="60" class="form-control"
										id="productDetails1" name="productDetails">
										
										</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">商品图片<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<div class="form-group pic_upload clearfix">
										<ul id="hiddenImgUrl">
											<li><img src="${appRoot}/static/img/zanwu1.png" style="height: 70px;" id="imgURL2" /></li>
											<li><input type="file" name="imgfile2" onchange="editImg(this)" /> <i class="glyphicon glyphicon-plus"></i></li>
										</ul>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button type="button" onclick="submitUpdateData();" class="btn btn-send">提交</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
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

	<form action="${appRoot}/anwProduct/del" method="post"
		id="deleForm" name="deleForm">
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
	//下载EXCEL
	function downloadExcel(){
		var datamsg = $("#selectCheckMessage").serialize();
		$.ajax({
			type:'post',
			data: datamsg, 
			url:'${appRoot}/customer/customerMsgExcel',
			dataType:'json',
			success:function(data){
				if(data.msg==100){
					windowShow("导出成功","");
				}else{
					windowShow("导出失败","");
				}
			}
		});
	}
	
	//查看所有已作废顾问下的客户信息
	function seeCancellationUserMsg(){
		var isValidate = $("#isValidate").val();
		if(isValidate==undefined||isValidate==null||isValidate==""){
			$("#isValidate").val(1);
			seeAllMsg();
		}else{
			$("#isValidate").val(null);
			var b = $("#isValidate").val();
			seeAllMsg();
		}
	}
	
	
	//修改客户的操作权限用户单个
	function editJurisdiction(projId,custId){
		$.ajax({
			type:'post',
			data : {"projId":projId},  
			url:'${appRoot}/customer/getUserList',
			dataType:'json',
			success:function(data){
				console.log(data);
				if(data.msg == 100){
					var htmldata = "";
					var usermsg = data.userData;
					for(var i=0;i<usermsg.length;i++){
						htmldata += '<option value="'+ usermsg[i].id +'">'+ usermsg[i].realname +'</option>';
					}
					$("#userData").html(htmldata);
					//项目id
					var projId = data.projId;
					$("#projId").val(projId);
					//原权限人
					$("#custId").val(custId);
					var $modal = $('#addProblemType');
					$modal.modal();
				}else{
					windowShow("获取用户列表失败","");
				}
			}
		});
		
	}
	
	
	//多选客户信息转移用户
	function selectUsers(){
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
			windowShow("请选择要转移的客户！")
			$('#quxiao').click();
			return false;
		} 
		//调用接口
		$.ajax({
			type:'post',
			data : {"userCustIds":chestr},  
			url:'${appRoot}/customer/getUserLists',
			dataType:'json',
			success:function(data){
				console.log(data);
				if(data.msg == 100){
					var htmldata = "";
					var usermsg = data.userData;
					for(var i=0;i<usermsg.length;i++){
						htmldata += '<option value="'+ usermsg[i].id +'">'+ usermsg[i].realname +'</option>';
					}
					$("#userData").html(htmldata);
					//项目id
					var projId = data.projId;
					$("#projId").val(projId);
					//原权限人
					var custId = data.custId;
					$("#custId").val(custId);
					var $modal = $('#addProblemType');
					$modal.modal();
				}else if(data.msg == 105){
					windowShow("您所选择的用户没有在同一个项目","");
				}else{
					windowShow("获取用户列表失败","");
				}
			}
		});
	}
	
	
	
	
	
	//选择不同的页数
	function doPanation(number){
		$("#nowPageNumber").val(number);
		seeAllMsg();
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
	
	
	//根据选择查看信息
	function seeAllMsg() {
		$("#selectCheckMessage").submit();
	}
	

	//添加菜单
	function doAdd() {
		window.location.href="${appRoot}/anwProduct/addPage";
		/* var $modal = $('#addProblemType');
		$modal.modal(); */
	}

	//修改菜单
	function doEdit(id) {
		window.location.href="${appRoot}/anwProduct/editPage?id="+id;
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



	function setExpert() {
		$("#checkExpert").submit();
	}

	function Delete() {
		$("#deleForm").submit();
	}
		

	</script>
	<input type="hidden" value="" id="adminId" />
</body>
</html>

