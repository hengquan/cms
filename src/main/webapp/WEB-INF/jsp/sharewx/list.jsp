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
							<header class="panel-heading"> 微信分享信息管理 </header>
							<table class="table table-striped border-top" id="sample_1">
								<thead>
									<tr>
										<th style="width: 8px;"><input type="checkbox" name="box" class="group-checkable" data-set="#sample_1 .checkboxes" value="" /></th>
										<th>图片</th>
										<th class="hidden-phone">标题</th>
										<th class="hidden-phone">描述</th>
										<th class="hidden-phone">创建时间</th>
										<th class="hidden-phone">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${list}" var="info" varStatus="s">
										<tr class="odd gradeX">
											<td><input type="checkbox" name="box" class="checkboxes" value="${info.id}" /></td>
											<td><img src="${appAccessUrl}/${info.shareImg}" width="50px" height="50px"></td>
											<td class="hidden-phone">${info.shareTitle}</td>
											<td class="hidden-phone">${info.remark}</td>
											<td class="hidden-phone"><fmt:formatDate value="${info.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											<td class="hidden-phone">
												<button class="btn btn-primary btn-xs" onclick="doEdit('${info.id}','${info.shareTitle}','${info.remark}')">
													<i class="icon-pencil"></i>
												</button> &nbsp;
												<button class="btn btn-danger btn-xs" onclick="doDel('${info.id}')">
													<i class="icon-trash"></i>
												</button> &nbsp;
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
						<h4 class="modal-title" id="modal-title">添加分享</h4>
					</div>
					<div class="modal-body">
						<form id="wxForm" enctype="multipart/form-data" class="form-horizontal" role="form" method="post" action="${appRoot}/sharewx/addOrUpdate">
							<input type="hidden" name="editId" id="editId">
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">标题<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="shareTitle"
										name="shareTitle" placeholder="请输入标题">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">描述<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="remark" name="remark" placeholder="请输入描述">
								</div>
							</div>
							 <div class="form-group">
                                      <label class="col-lg-2 control-label pd-r5">上传图片<font style="color:red;">*</font></label>
                                      <div class="col-lg-10">
                                        <input type="file" class="form-control" id="shareImg" name="shareImg" placeholder="请选择文件">
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
	
	<form action="${appRoot}/sharewx/del" method="post" id="deleForm" name="deleForm">
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
		if($('#shareTitle').val() == null || $('#shareTitle').val() == ''){
			windowShow("标题不能为空","");
			return false;
		}
		if($('#remark').val() == null || $('#remark').val() == ''){
			windowShow("描述不能为空","");
			return false;
		}
		if($('#shareImg').val() == null || $('#shareImg').val() == ''){
			windowShow("图片不能为空","");
			return false;
		}
	}
		
		function doAdd() {
			$("#editId").val('');
			$('#shareTitle').val('');
			$('#remark').val('');
			var $modal = $('#myModal');
			$modal.modal();
		}
		
		function doEdit(id,title,remark) {
			$('#editId').val(id);
			$('#shareTitle').val(title);
			$('#remark').val(remark);
			var $modal = $('#myModal');
			$('#modal-title').text("修改分享信息");
			$modal.modal(); 
		}
		
		function submitData(){
			if($('#shareTitle').val() == null || $('#shareTitle').val() == ''){
				windowShow("标题不能为空","");
				return false;
			}else{
				if($('#remark').val() == null || $('#remark').val() == ''){
					windowShow("描述不能为空","");
					return false;
				}else{
					if($('#shareImg').val() == null || $('#shareImg').val() == ''){
						windowShow("图片不能为空","");
						return false;
					}else{
						$('#wxForm').submit();
					}
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
</body>
</html>
