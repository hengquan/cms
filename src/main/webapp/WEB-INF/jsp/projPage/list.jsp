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
							<header class="panel-heading" style="height:50px;">
							<div class="col-lg-2">项目列表</div>
							<a href="javascript:history.go(-1)" class="btn btn-send mini btn-white col-lg-1 pull-right" style="align:right;"><i class="glyphicon glyphicon-arrow-left"></i></a>
							</header>
							
							<div class="modal-body">
								<form class="form-horizontal" action="${appRoot}/user/belongToProj"
								 role="form" id="itemForm" name="itemForm">
									<input type="hidden" name="userId" value="${userInfo.id }">
									<div class="form-group">
										<label class="col-lg-1 control-label pd-r5">登录名<font
											style="color: red;"></font></label>
										<div class="col-lg-2">
											<input class="form-control" value ="${userInfo.loginname }"/>
										</div>
										<label class="col-lg-1 control-label pd-r5">姓名<font
											style="color: red;"></font></label>
										<div class="col-lg-2">
											<input class="form-control" value ="${userInfo.realname }"/>
										</div>
										<label class="col-lg-1 control-label pd-r5">电话<font
											style="color: red;"></font></label>
										<div class="col-lg-2">
											<input class="form-control" value ="${userInfo.mainphonenum }"/>
										</div>
										<label class="col-lg-1 control-label pd-r5">用户类型<font
											style="color: red;"></font></label>
										<div class="col-lg-2">
											<input class="form-control" value ="${roleName }"/>
										</div>
									</div>
									
									<input type="hidden" value="${nowPage}" id="nowPageNumber" name="nowPage">
									<input type="hidden" value="${totalPageNum }">
								</form>
							</div>
							

							<table class="table table-striped border-top" id="sample_1">
								<thead>
									<tr>
										<th style="width: 8px;"><input type="checkbox" name="box"
											class="group-checkable" data-set="#sample_1 .checkboxes"
											value="" /></th>
										<th class="hidden-phone">项目名称</th>
										<th class="hidden-phone">所在地区</th>
										<th class="hidden-phone">项目描述</th>
										<th class="hidden-phone">创建时间</th>
									</tr>
								</thead>
								<tbody id="theTbody">
									<c:forEach items="${projData}" var="u" varStatus="s">
										<tr class="odd gradeX theTr">
											<td><input type="checkbox" name="box" class="checkboxes"
												value="${u.id}" /></td>
											<td class="hidden-phone">${u.projName}</td>
											<td class="hidden-phone">${u.areaName}</td>
											<td class="hidden-phone">${u.descn}</td>
											<td class="hidden-phone">
												<fmt:formatDate value="${u.cTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
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
						<h4 class="modal-title" id="modal-title">添加商品</h4>
					</div>
					<div class="modal-body">
						<form action="${appRoot}/anwProduct/add" method="post" class="form-horizontal"  enctype="multipart/form-data"  role="form" id="addMessage" name="itemForm" >
							<input type="hidden" name="editId123" id="editId123">
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">商品名称<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="productName"
										name="productName" maxlength="4" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">商品价格<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="price"
										name="price" maxlength="4" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">销量<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="salesVolume"
										name="salesVolume" maxlength="4" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">运费<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="freight"
										name="freight" maxlength="4" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">商品类型<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<select class="form-control" name="categoryId">
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
										id="productDetails" name="productDetails">
										
										</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">商品图片<font style="color:red;"></font></label>
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
	
	
	//更新排序值
	function updateIndexValue(obj){
		var msg = $(obj);
		var id = msg.attr("id");
		var index = msg.val();
		window.location.href="${appRoot}/anwProduct/updateIndex?id="+id+"&index="+index;
	}
	
	
	
		//添加提交
		function submitData() {
			var productName = $("#productName").val();
			if (productName == '' || productName == undefined) {
				windowShow("商品名称不允许为空！", "");
				return;
			}
			$("#addMessage").submit();
		}

		//更新数据
		function submitUpdateData() {
			var productName1 = $("#productName1").val();
			alert(productName1);
			if (productName1 == '' || productName1 == undefined) {
				windowShow("类型名称不允许为空！", "");
				return;
			}
			$("#updateMessage").submit();
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
		
		//查看商品的评论
		function seeProduct(id){
			window.location.href="${appRoot}/anwProduct/seeComment?id="+id;
		}
		
		//选择不同的页数
		function doPanation(number){
			$("#nowPageNumber").val(number);
			seeAllMsg();
		}
		
		//根据选择查看信息
		function seeAllMsg(){
			$("#itemForm").submit();
		}
		

	</script>
	<input type="hidden" value="" id="adminId" />
</body>
</html>

