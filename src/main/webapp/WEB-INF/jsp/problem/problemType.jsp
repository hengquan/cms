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
							<header class="panel-heading">问题类型管理</header>
							<form action="${appRoot}/problemType/list" method="post"
								id="selectCheckMessage">
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

								<div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<a href="javascript:doAdd();" class="btn mini btn-white"><i
										class="icon-plus"></i></a>
								</div>
								
								<div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<a href="javascript:doDelete();" class="btn mini btn-white"><i
										class="icon-trash"></i></a>
								</div>




								<%-- <div style="float: right;position: relative;margin-top: 16px;margin-right: 100px;">									
										<select class="form-control" style="width: 120px;" id="pageSize" name="pageSize" onchange="seeAllMsg()">
											<option value="10" <c:if test="${pageSize==10}">selected</c:if>>10</option>
											<option value="25" <c:if test="${pageSize==25}">selected</c:if>>25</option>
											<option value="50" <c:if test="${pageSize==50}">selected</c:if>>50</option>
											<option value="100" <c:if test="${pageSize==100}">selected</c:if>>100</option>
										</select>
									</div>			 --%>
								<input type="hidden" value="${nowPage}" id="nowPageNumber"
									name="nowPage"> <input type="hidden"
									value="${totalPageNum }">
							</form>

							<div style="clear: both"></div>

							<table class="table table-striped border-top" id="sample_1">
								<thead>
									<tr>
										<th style="width: 8px;"><input type="checkbox" name="box"
											class="group-checkable" data-set="#sample_1 .checkboxes"
											value="" /></th>
										<th class="hidden-phone">类型名称</th>
										<th class="hidden-phone">类型索引</th>
										<th class="hidden-phone">是否为热门(0否/1是)</th>
										<th class="hidden-phone">操作</th>
									</tr>
								</thead>
								<tbody id="theTbody">
									<c:forEach items="${problemTypes}" var="u" varStatus="s">
										<tr class="odd gradeX theTr">
											<td><input type="checkbox" name="box" class="checkboxes"
												value="${u.id}" /></td>
											<td class="hidden-phone">${u.typeName}</td>
											<td class="hidden-phone">${u.typeIndex}</td>
											<td class="hidden-phone">
												<c:if test="${u.isHot==0}">
													<input type="number" class="form-control" value="${u.isHot}" style="width:100px;" id="${u.id}" onBlur="updateIsHost(this)" ">
												</c:if>
												<c:if test="${u.isHot==1}">
													<input type="number" class="form-control" value="${u.isHot}" style="width:100px;" id="${u.id}" onBlur="updateIsHost(this)" ">
												</c:if>
											</td>
											<td><button type="button" class="btn btn-primary" onclick="doEdit('${u.id}','${u.typeName}','${u.typeIndex}','${u.hotPicture}','${u.hotDescribe}')">修改</button></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<%-- <nav class="clearfix">
								<ul class="pagination pull-right">
									<li><a href="javascript:doPanation(1)">首页</a></li>
									<li><a
										href="javascript:doPanation(${nowPage - 1 < 1 ? 1 : nowPage - 1})"
										aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
									</a></li>
									<c:forEach begin="${nowPage - 5 > 0 ? nowPage - 5 : 1 }"
										end="${nowPage + 5 > totalPageNum ? totalPageNum : nowPage + 5}"
										var="t">
										<li><a href="javascript:doPanation(${t})">${t}</a></li>
									</c:forEach>
									<li><a
										href="javascript:doPanation(${nowPage + 1 > totalPageNum ? totalPageNum : nowPage + 1})"
										aria-label="Next"> <span aria-hidden="true">&raquo;</span>
									</a></li>
									<li><a href="javascript:doPanation(${totalPageNum})">末页</a></li>
								</ul>
							</nav> --%>
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
					<h4 class="modal-title titleMessage" id="modal-title">回答信息</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="itemForm"
						name="itemForm">
						<input type="hidden" name="editId" id="editId">
						<div class="form-group">
							<label class="col-lg-2 control-label pd-r5">文字消息<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<textarea rows="5" cols="60" class="form-control"
									id="textContent">
									
									</textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label pd-r5">语音消息<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<audio src="${appRoot}/static/video/123.mp3" controls="controls">
								</audio>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label pd-r5">图片消息<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<img src="${appRoot}/static/img/1.jpg" alt="..."
									class="img-thumbnail" width="140" height="140"> <img
									src="${appRoot}/static/img/2.jpg" alt="..."
									class="img-thumbnail" width="140" height="140"> <img
									src="${appRoot}/static/img/4.jpg" alt="..."
									class="img-thumbnail" width="140" height="140"> <img
									src="${appRoot}/static/img/1.jpg" alt="..."
									class="img-thumbnail" width="140" height="140"> <img
									src="${appRoot}/static/img/3.jpg" alt="..."
									class="img-thumbnail" width="140" height="140"> <img
									src="${appRoot}/static/img/2.jpg" alt="..."
									class="img-thumbnail" width="140" height="140">
							</div>
						</div>
						<div class="form-group" id="videoMessge">
							<label class="col-lg-2 control-label pd-r5">视频地址<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<video src="${appRoot}/static/video/111.mp4" controls="controls"
									width="430" height="330">
								</video>
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-offset-2 col-lg-10">
								<button data-dismiss="modal" type="button" id="quxiao"
									class="btn btn-send">返回</button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>









		<div class="modal fade" id="addProblemType" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="modal-title">添加菜单</h4>
					</div>
					<div class="modal-body">
						<form action="${appRoot}/problemType/addMessage" method="post" class="form-horizontal"  enctype="multipart/form-data"  role="form" id="addMessage" name="itemForm" >
							<input type="hidden" name="editId" id="editId">
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">类型名称<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="problemTypeName"
										name="problemTypeName" maxlength="4" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">类型索引<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="problemTypeIndex"
										name="problemTypeIndex" >
								</div>
							</div>	
							
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">热门图片<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<div class="form-group pic_upload clearfix">
										<ul id="hiddenImgUrl">
											<li><img src="${appRoot}/static/img/zanwu1.png" style="height: 70px;" id="imgURL1" /></li>
											<li><input type="file" name="imgfile1" onchange="addImg(this)" /> <i class="glyphicon glyphicon-plus"></i></li>
										</ul>
									</div>
								</div>
							</div>	
							
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">描述信息<font
									style="color: red;"></font></label>
								<div class="col-lg-10">
									<textarea rows="5" cols="60" class="form-control"
										id="textContent" name="describe">
										
										</textarea>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button type="button" onclick="submitData();" class="btn btn-send">提交</button>
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
						<h4 class="modal-title" id="modal-title">修改菜单</h4>
					</div>
					<div class="modal-body">
						<form action="${appRoot}/problemType/updateMessage" method="post" class="form-horizontal" enctype="multipart/form-data" role="form" id="updateMessage" name="itemForm" >
							<input type="hidden" name="editId" id="editId">
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">类型名称<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="problemTypeName1"
										name="problemTypeName1" >
									<input type="hidden" id="problemTypeId1" name="problemTypeId1">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">类型索引<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="problemTypeIndex1"
										name="problemTypeIndex1" >
								</div>
							</div>	
							
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">热门图片<font style="color:red;"></font></label>
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
								<label class="col-lg-2 control-label pd-r5">描述信息<font
									style="color: red;"></font></label>
								<div class="col-lg-10">
									<textarea rows="5" cols="60" class="form-control"
										id="textContent2" name="describe1">
										
										</textarea>
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

	<form action="${appRoot}/problemType/delProblemType" method="post" id="deleForm"
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
	
	//更新排序值
	// function updateIndexValue(obj){
	//	var msg = $(obj);
	//	var id = msg.attr("id");
	//	var index = msg.val();
	//	window.location.href="${appRoot}/expertMessage/updateIndex?id="+id+"&index="+index;
	// }
	
	//更新是否为热门
	function updateIsHost(obj){
		var msg = $(obj);
		var id = msg.attr("id");
		var host = msg.val();
		if( host ==1 || host ==0){
			window.location.href="${appRoot}/problemType/isHost?id="+id+"&host="+host;
		}else{
			windowShow("请输入有效值！", "");
			return;
		}
	}
	
	
	
	
	
		//添加提交
		function submitData(){
			
			var problemTypeName = $("#problemTypeName").val();
			if(problemTypeName==''||problemTypeName==undefined){
				windowShow("类型名称不允许为空！", "");
				return;
			}
			var problemTypeIndex = $("#problemTypeIndex").val();
			if(problemTypeName==''||problemTypeName==undefined){
				windowShow("类型索引不允许为空！", "");
				return;
			}
			$("#addMessage").submit();
		}
	
		//更新数据
		function submitUpdateData(){
			var problemTypeName = $("#problemTypeName1").val();
			if(problemTypeName==''||problemTypeName==undefined){
				windowShow("类型名称不允许为空！", "");
				return;
			}
			var problemTypeIndex = $("#problemTypeIndex1").val();
			if(problemTypeName==''||problemTypeName==undefined){
				windowShow("类型索引不允许为空！", "");
				return;
			}
			$("#updateMessage").submit();
		}
	
	

		//根据选择查看信息
		function seeAllMsg() {
			$("#selectCheckMessage").submit();
		}

		//查看更多信息
		function manyMessage(userInfoId) {
			$.ajax({
				type : 'post',
				data : {
					"id" : userInfoId
				},
				url : '${appRoot}/user/singleMessage',
				dataType : 'json',
				success : function(data) {
					if (data.msg == 0) {
						var $modal = $('#myModal');
						$modal.modal();
					} else {
						windowShow("操作失败", "");
					}
				}
			});
		}

		//查看问题的内容的信息
		function problemMessage(contentText, id) {
			$("#textContent").val(contentText);
			$(".titleMessage").html("回答信息");
			var $modal = $('#myModal');
			$modal.modal();
		}

		$(function() {
			$('.input-group').hide();
			//$('#sample_1_info').hide();
			//$('.dataTables_paginate').hide();
			$("#sample_1_length .form-control").hide();
			$("#sample_1_length .js-add").hide();
			$("#sample_1_length .js-ref").hide();
			$("#sample_1_length .js-del").hide();

			$
					.ajax({
						type : 'post',
						url : '${appRoot}/problemType/allMessage',
						dataType : 'json',
						success : function(data) {
							if (data.msg == 0) {
								$("#theTbody .theTr")
										.each(
												function(index, li) {
													var typeName = '';
													var typeTitle = '';
													var typeId = $(
															".theIDType"
																	+ (index + 1))
															.val();
													var result = typeId
															.split(",");
													$
															.each(
																	data.problemTypes,
																	function(i,
																			item) {
																		for (var i = 0; i < result.length; i++) {
																			if (result[i] == item.id) {
																				typeTitle += item.typeName
																						+ "  ";
																				if (i < 2) {
																					typeName += item.typeName
																							+ "  ";
																				}
																			}
																		}
																	});
													//	alert(typeName);
													//	alert(typeTitle);
													//	alert(result.length);
													if (result.length > 2) {
														$(
																".typeId"
																		+ (index + 1))
																.html(
																		typeName
																				+ "...");
													} else {
														$(
																".typeId"
																		+ (index + 1))
																.html(typeName);
													}
													$(".typeId" + (index + 1))
															.attr("title",
																	typeTitle);

												});
							} else {
								windowShow("操作失败", "");
							}
						}
					});

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

		
		//添加菜单
		function doAdd(){
			var $modal = $('#addProblemType');
			$modal.modal();
		}
		
		//修改菜单
		function doEdit(id,name,typeIndex,picture,describe){
			//alert(picture);
			//alert(describe);
			
			if(picture==null || picture=="" || picture==undefined){
				$("#imgURL2").attr("src","${appRoot}/static/img/zanwu1.png");
			}else{
				$("#imgURL2").attr("src","${appAccessUrl}"+picture);
			}
			$("#textContent2").text(describe);
			
			$("#problemTypeId1").val(id);
			$("#problemTypeName1").val(name);
			$("#problemTypeIndex1").val(typeIndex);
			var $modal = $('#editProblemType');
			$modal.modal();
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

		//设置专家
		function doSelectUser() {
			var flag = checkUserBox();
			if (flag) {
				var $modal = $('#myModal1');
				$modal.modal();
			}
		}

		//是否选中用户
		function checkUserBox() {
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
			//alert(chestr);
			if (!jy) {
				windowShow("请选择要设置成专家的用户！")
				$('#quxiao').click();
				return false;
			} else {
				$("#setExpertIds").val(chestr);
				return true;
			}
		}

		function setExpert() {
			$("#checkExpert").submit();
		}

		function Delete() {
			$("#deleForm").submit();
		}
		
		//添加图片
		function addImg(object){
			if (object.files && object.files[0]) {
				var reader = new FileReader();
				reader.onload = function(evt) {
					if(evt.target.result!=""&&evt.target.result!=null){
						$("#imgURL1").attr("src",evt.target.result);
					}else{
						alert("图片上传出错!");
					}
				}
				reader.readAsDataURL(object.files[0]);
			} else {
				//alert(file.value);
			}
		}
		//修改图片
		function editImg(object){
			if (object.files && object.files[0]) {
				var reader = new FileReader();
				reader.onload = function(evt) {
					if(evt.target.result!=""&&evt.target.result!=null){
						$("#imgURL2").attr("src",evt.target.result);
					}else{
						alert("图片上传出错!");
					}
				}
				reader.readAsDataURL(object.files[0]);
			} else {
				//alert(file.value);
			}
		}
	</script>
	<input type="hidden" value="" id="adminId" />
</body>
</html>

