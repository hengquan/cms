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
							<header class="panel-heading"> 管理员信息管理 </header>
							<table class="table table-striped border-top" id="sample_1">
								<thead>
									<tr>
										<th style="width: 8px;"><input type="checkbox" name="box" class="group-checkable" data-set="#sample_1 .checkboxes" value="" /></th>
										<th>登录名</th>
										<th class="hidden-phone">用户名</th>
										<th class="hidden-phone">用户角色</th>
										<th class="hidden-phone">所属站点</th>
										<th class="hidden-phone">创建时间</th>
										<th class="hidden-phone">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${adminList}" var="info" varStatus="s">
										<tr class="odd gradeX">
											<td><input type="checkbox" name="box" class="checkboxes" value="${info.id}" /></td>
											<td>${info.loginId}</td>
											<td class="hidden-phone">${info.userName}</td>
											<c:forEach items="${roleList}" var="r" varStatus="s">
												<c:if test="${info.userType==r.id}">
													<td class="hidden-phone">${r.roleName}</td>
												</c:if>
												
											</c:forEach>
											<td class="hidden-phone">${info.storeroomName}</td>
											<td class="hidden-phone"><fmt:formatDate value="${info.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											<td class="hidden-phone">
												<button class="btn btn-primary btn-xs" onclick="doEdit('${info.id}','${info.loginId}','${info.userName}','${info.remark}','${info.userType}','${info.storeroomId }')">
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
		
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="modal-title">添加管理员</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form" id="adminForm" name="adminForm">
							<input type="hidden" name="editId" id="editId">
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">登录名<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="loginId"
										name="loginId" placeholder="请输入登录名">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">用户角色<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<select class="form-control" name="userType" id="userType">
									<c:forEach items="${roleList}" var="role" varStatus="s">
										<option value="${role.id }">${role.roleName }</option>
										<!-- <option value="02">中级用户</option>
										<option value="01">高级用户</option> -->
									</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">用户名<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="userName" name="userName" placeholder="请输入用户名">
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">库房<font
									style="color: red;"></font></label>
								<div class="col-lg-10">
									<select class="form-control doEditSelect1" name="storeroomId">
										<c:forEach items="${anwStorerooms}" var="u" varStatus="s">
											<option value="${u.id }">${u.name }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							
							 <div class="form-group">
                                      <label class="col-lg-2 control-label pd-r5">备注<font style="color:red;"></font></label>
                                      <div class="col-lg-10">
                                        <input type="text" class="form-control" id="remark" name="remark" placeholder="请输入备注">
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
				<div class="modal-body">确定重置管理员密码？</br>重置后密码为:123456</div>
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn btn-default" type="button" id="quxiao">取消</button>
					<button class="btn btn-warning" type="button" onclick="resetPassword()">确定</button>
				</div>
			</div>
		</div>
	</div>
	
	<form action="${appRoot}/admin/del" method="post" id="deleForm" name="deleForm">
		<input type="hidden" name="byid" id="byid">
	    <input type="hidden" name="boxeditId" id="boxeditId">
	</form>


	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>
	<script src="${appRoot}/static/js/jquery.sparkline.js" type="text/javascript"></script>

	<script type="text/javascript" src="${appRoot}/static/assets/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${appRoot}/static/assets/data-tables/DT_bootstrap.js"></script>
	<!--script for this page only-->
	<script src="${appRoot}/static/js/dynamic-table.js"></script>
    <script src="${appRoot}/static/js/dialog_alert.js"></script>
	<script type="text/javascript">
	
	function validateForm(){
		if($('#loginId').val() == null || $('#loginId').val() == ''){
			windowShow("登录名不能为空","");
			return false;
		}
		if($('#userName').val() == null || $('#userName').val() == ''){
			windowShow("用户名不能为空","");
			return false;
		}
	}
	
	function openResetPwd(adminId){
		$('#adminId').val(adminId);
		var $modal = $('#myModalUpdatePwd');
		$('#modal-title').text("重置密码");
		$modal.modal();
	}
		
	function resetPassword(){
		$.ajax({
			type:'POST',
			data:{'adminId':$('#adminId').val()},
			url:'<%=request.getContextPath()%>/admin/resetpwd',
			dataType:'json',
			success:function(data){
				if(data.code == 'ok'){
					var $modal = $('#myModalUpdatePwd');
					$modal.hide();
					alert("密码重置成功!");
					window.location.reload();
				}
			},
			error:function(data){
				alert("重置失败!");
			}
		});
		
		
	}
	
	
		function doAdd() {
			$("#editId").val('');
			$('#loginId').val('');
			$('#userName').val('');
			$('#remark').val('');
			//$('#userType').val('');
			var $modal = $('#myModal');
			$modal.modal();
		}
		
		function doEdit(id,loginId,userName,remark,userType,storeroomId) {
			$('#editId').val(id);
			$('#loginId').val(loginId);
			$('#userName').val(userName);
			$('#userType').val(userType);
			$('#remark').val(remark);
			$(".doEditSelect1").find("option[value="+storeroomId+"]").attr("selected","selected");
			
			var $modal = $('#myModal');
			$('#modal-title').text("修改管理员信息");
			$modal.modal(); 
		}
		
		function submitData(){
			//$('#adminForm').submit();
			if($('#loginId').val() == null || $('#loginId').val() == ''){
				windowShow("登录名不能为空","");
				return false;
			}else{
				if($('#userName').val() == null || $('#userName').val() == ''){
					windowShow("用户名不能为空","");
					return false;
				}else{
					$.ajax({
						type:'post',
						data:$('#adminForm').serialize(),
						url:'${appRoot}/admin/addAndUpdate',
						dataType:'json',
						success:function(data){
							if(data.msg=="error"){
								windowShow("登录名已被使用!请重新输入登录名","");
								return false;
							}else{
								$('#myModal').modal('hide');
								windowShow("操作成功","");
								window.location.reload();
							}
						},
						error:function(data){
							windowShow("操作失败!","");
						}
					});
				}
				
			}
			
			
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
				$("#deleForm").submit();
		}
		
	</script>
	<input type="hidden" value="" id="adminId"/>
</body>
</html>
