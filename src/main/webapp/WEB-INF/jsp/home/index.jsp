<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
							<header class="panel-heading"> 商户管理 </header>
							<table class="table table-striped border-top" id="sample_1">
								<thead>
									<tr>
										<th style="width: 8px;"><input type="checkbox" name="box" class="group-checkable" data-set="#sample_1 .checkboxes" value="" /></th>
										<th>商户名称</th>
										<th class="hidden-phone">属性</th>
										<th class="hidden-phone">联系人</th>
										<th class="hidden-phone">联系电话</th>
										<th class="hidden-phone">支持外卖</th>
										<th class="hidden-phone">外卖状态</th>
										<th class="hidden-phone">支持积分</th>
										<th class="hidden-phone">登录名</th>
										<th class="hidden-phone">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${businessList}" var="info" varStatus="s">
										<tr class="odd gradeX">
											<td><input type="checkbox" name="box" class="checkboxes" value="${info.id}" /></td>
											<td>${info.name}</td>
											<td class="hidden-phone">${info.attributesString}</td>
											<td class="hidden-phone">${info.contactName}</td>
											<td class="hidden-phone">${info.contactTel}</td>
											<td class="center hidden-phone tlc">${info.takeoutString}</td>
											<td class="center hidden-phone tlc">${info.takeoutPauseString}</td>
											<td class="center hidden-phone tlc">${info.integralString}</td>
											<td class="center hidden-phone tlc">${info.loginId}</td>
											<td class="hidden-phone">
												<button class="btn btn-primary btn-xs" onclick="doEdit('${info.id}')">
													<i class="icon-pencil"></i>
												</button> &nbsp;
												<button class="btn btn-danger btn-xs" onclick="doDel('${info.id}')">
													<i class="icon-trash"></i>
												</button> &nbsp;
												<button class="btn btn-danger btn-xs" onclick="openResetPwd('${info.id}');">
													<i class="icon-check-sign"></i>
												</button>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</section>
					</div>
				</div>
				<!-- page end-->
			</section>
		</section>
		<!--main content end-->
		<!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="modal-title">添加商户</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form">
							<div class="form-group">
								<label class="col-lg-2 control-label">To</label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="inputEmail1" placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">Cc / Bcc</label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="cc" placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">Subject</label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="inputPassword1" placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">Message</label>
								<div class="col-lg-10">
									<textarea name="" id="" class="form-control" cols="30" rows="10"></textarea>
								</div>
							</div>

							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<span class="btn green fileinput-button"> <i class="icon-plus icon-white"></i> <span>Attachment</span> <input type="file" multiple=""
										name="files[]">
									</span>
									<button type="submit" class="btn btn-send">Send</button>
								</div>
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
	<!-- modal -->
	
	
	<div class="modal fade" id="myModalUpdatePwd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">重置警告</h4>
				</div>
				<div class="modal-body">确定重置该商户密码？</br>重置后密码为:123456</div>
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn btn-default" type="button" id="quxiao">取消</button>
					<button class="btn btn-warning" type="button" onclick="resetPassword()">确定</button>
				</div>
			</div>
		</div>
	</div>


	<form action="${appRoot}/business/edit" method="post" id="myForm" name="myForm">
		<input type="hidden" name="editId" id="editId">
	</form>
	<form action="${appRoot}/business/dele" method="post" id="deleForm" name="deleForm">
		<input type="hidden" name="byid" id="byid"> <input type="hidden" name="boxeditId" id="boxeditId">
	</form>


	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>
	<script src="${appRoot}/static/js/jquery.sparkline.js" type="text/javascript"></script>

	<script type="text/javascript" src="${appRoot}/static/assets/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${appRoot}/static/assets/data-tables/DT_bootstrap.js"></script>
	<!--script for this page only-->
	<script src="${appRoot}/static/js/dynamic-table.js"></script>
    <script src="${appRoot}/static/js/dialog_alert.js"></script>
	<script type="text/javascript">
	
		function openResetPwd(businessId){
			$('#busid').val(businessId);
			var $modal = $('#myModalUpdatePwd');
			$('#modal-title').text("重置密码");
			$modal.modal();
		}
			
		function resetPassword(){
			$.ajax({
				type:'POST',
				data:{'businessId':$('#busid').val()},
				url:'<%=request.getContextPath()%>/home/resetpwd',
				dataType:'json',
				success:function(data){
					if(data.code == 'ok'){
						var $modal = $('#myModalUpdatePwd');
						$modal.hide();
						alert("该商户密码重置成功!");
						window.location.reload();
					}
				},
				error:function(data){
					alert("重置失败!");
				}
			});
			
			
		}
		
	
		function doAdd() {
			$("#editId").val("");
			$("#myForm").submit();
		}
		function doEdit(id) {
			/* var $modal = $('#myModal');
			$('#modal-title').text("修改商户信息");
			$modal.modal(); */
			$("#editId").val(id);
			$("#myForm").submit();
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

		function doDel(id) {
			var $modal = $('#myModal1');
			$("#byid").val(id);
			$modal.modal();
		}

		function Del() {
			$("#deleForm").submit();
		}

		function Delete() {
			/* var jy = true;
			jy = checkbox();
			if (jy) { */
				$("#deleForm").submit();
			/* } */
		}
		
	</script>
	
	<input type="hidden" id="busid">
</body>
</html>
