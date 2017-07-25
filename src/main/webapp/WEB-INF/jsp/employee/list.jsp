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
							<header class="panel-heading">核销人员管理</header>
							<table class="table table-striped border-top" id="sample_1">
								<thead>
									<tr>
										<th>姓名</th>
										<th class="hidden-phone">企业名</th>
										<th class="hidden-phone">电话</th>
										<th class="hidden-phone">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${list}" var="info" varStatus="s">
										<tr class="odd gradeX">
											<td>${info.employeename}</td>
											<td class="hidden-phone">${info.orgname}</td>
											<td class="hidden-phone">${info.telephone}</td>
											<td class="hidden-phone">
												<c:if test="${info.flag == 'hx'}">
													<span style="cursor:pointer" class="label label-success" onclick="removeCancle('${info.employeeid}');">撤销核销人员</span>
												</c:if>
												<c:if test="${info.flag == 'fhx'}">
													<span style="cursor:pointer" class="label label-info" onclick="openResetPwd('${info.employeeid}');">成为核销人员</span>
												</c:if>
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
		
	</section>
	
	<div class="modal fade" id="myModalUpdatePwd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">设置核销密码</h4>
				</div>
				<div class="modal-body"><label class="col-lg-2 control-label">核销密码</label><input type="text" id="hxPassword" class="form-control"></div>
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn btn-default" type="button" id="quxiao">取消</button>
					<button class="btn btn-warning" type="button" onclick="doPassword()">确定</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="removeCancle" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">确定撤销核销人员?</h4>
				</div>
				<div class="modal-body">确定撤销核销人员?</div>
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn btn-default" type="button" id="quxiao">取消</button>
					<button class="btn btn-warning" type="button" onclick="removeHx()">确定</button>
				</div>
			</div>
		</div>
	</div>
	
	<form action="${appRoot}/employee/del" id="dForm">
		<input type="hidden" name="did" id="did">
	</form>

	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>
	<script src="${appRoot}/static/js/jquery.sparkline.js" type="text/javascript"></script>

	<script type="text/javascript" src="${appRoot}/static/assets/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${appRoot}/static/assets/data-tables/DT_bootstrap.js"></script>
	<!--script for this page only-->
	<script src="${appRoot}/static/js/dynamic-table.js"></script>
    <script src="${appRoot}/static/js/dialog_alert.js"></script>
	<script type="text/javascript">
	
	$(function(){
		$(".btn-group").hide();
	});
	
	function removeCancle(id){
		$('#did').val(id);
		var $modal = $('#removeCancle');
		$modal.modal();
	}
	
	function removeHx(){
		$('#dForm').submit();
	}
	
	
	function openResetPwd(hxid){
		$('#hxid').val(hxid);
		var $modal = $('#myModalUpdatePwd');
		$('#modal-title').text("重置密码");
		$modal.modal();
	}
		
	function doPassword(){
		if($('#hxPassword').val() == null || $('#hxPassword').val() == ''){
			windowShow("密码不能为空!","");
			return false;
		}
		$.ajax({
			type:'POST',
			data:{'hxid':$('#hxid').val(),'hxPassword':$('#hxPassword').val()},
			url:'${appRoot}/employee/dopwd',
			dataType:'json',
			success:function(data){
				if(data.result == 'ok'){
					var $modal = $('#myModalUpdatePwd');
					$modal.hide();
					windowShow("密码设置成功!","");
					window.location.reload();
				}else{
					windowShow("密码已被使用!","");
					return false;
				}
			},
			error:function(data){
				alert("重置失败!");
			}
		});
		
		
	}
	</script>
	<input type="hidden" id="hxid">
</body>
</html>
