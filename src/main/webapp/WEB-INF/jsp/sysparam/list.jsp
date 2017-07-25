<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
							<header class="panel-heading">系统参数 </header>
							<table class="table table-striped border-top" id="sample_1">
								<thead>
									<tr>
										<th style="width: 8px;"><input type="checkbox" name="box"
											class="group-checkable" data-set="#sample_1 .checkboxes"  value=""/></th>
										<th>参数名称</th>
										<th class="hidden-phone">参数编码</th>
										<th class="hidden-phone">参数值</th>
										<th class="hidden-phone">备注</th>
										<td class="hidden-phone">操作</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${sysParamList}" var="info" varStatus="s">
										<tr class="odd gradeX">
											<td><input type="checkbox" name="box" class="checkboxes"
												value="${info.id}" /></td>
											<td>${info.paramName}</td>
											<td class="hidden-phone">${info.paramCode}</td>
											<td class="hidden-phone">${info.paramVal}</td>
											<td class="hidden-phone">${info.remark}</td>
											<td class="hidden-phone">
												<button class="btn btn-primary btn-xs" onclick="doEdit('${info.id}','${info.paramName}','${info.paramCode}','${info.paramVal}','${info.remark}')"><i class="icon-pencil"></i></button> &nbsp;&nbsp;
												<button class="btn btn-danger btn-xs" onclick="doDel('${info.id}')"> <i class="icon-trash "></i></button>
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
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="modal-title">添加参数</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form" method="post" action="${appRoot}/sys_param/add_update" id="paramForm" name="paramForm">
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">参数名称<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="paramName" name="paramName"
										placeholder="请输入参数名称">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">参数编码<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="paramCode" name="paramCode" placeholder="请输入参数编码">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">参数值<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="paramVal" name="paramVal"
										placeholder="请输入参数值">
								</div>
							</div>
							<div class="form-group">
                                      <label class="col-lg-2 control-label pd-r5">备注<font style="color:red;"></font></label>
                                      <div class="col-lg-10">
                                          <input type="text" class="form-control" id="remark" name="remark" placeholder="请输入备注信息" maxlength="30"/>
                                      </div>
                             </div>
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button type="button" onclick="submitParamData();" class="btn btn-send">提交</button>
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

	<%-- 模板 start --%>
	
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
								<button data-dismiss="modal" class="btn btn-default"
									type="button" id="quxiao">取消</button>
								<button class="btn btn-warning" type="button" onclick="Delete()">
									确定</button>
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
								<h4 class="modal-title">删除警告</h4>
							</div>
							<div class="modal-body">确定删除？</div>
							<div class="modal-footer">
								<button data-dismiss="modal" class="btn btn-default"
									type="button" id="quxiao">取消</button>
								<button class="btn btn-warning" type="button" onclick="Del()">
									确定</button>
							</div>
						</div>
					</div>
				</div>
				<!-- modal -->
			</div>
		
	<%-- 模板 end --%>

	<form action="${appRoot}/sys_param/add_update" method="post" id="myForm"
		name="myForm">
		<input type="hidden" name="editId" id="editId">
	</form>
	<form action="${appRoot}/sys_param/list" method="post" id="deleForm"
		name="deleForm">
		<input type="hidden" name="byid"  id="byid">
		<input type="hidden" name="boxeditId" id="boxeditId" >
		<input type="hidden" name="activeFlag"  value="${activeFlag}"/>
	</form>


	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>
	<script src="${appRoot}/static/js/jquery.sparkline.js"
		type="text/javascript"></script>
 <script type="text/javascript" src="${appRoot}/static/js/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="${appRoot}/static/assets/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript"
		src="${appRoot}/static/assets/data-tables/DT_bootstrap.js"></script>
	<!--script for this page only-->
	 <script src="${appRoot}/static/js/form-component.js"></script>
	<script src="${appRoot}/static/js/dynamic-table.js"></script>
    <script src="${appRoot}/static/js/dialog_alert.js"></script>
	<script src="${appRoot}/static/js/wxm-param.js"></script>

</body>
</html>
