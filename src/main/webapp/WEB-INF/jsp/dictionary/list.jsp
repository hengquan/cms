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
							<header class="panel-heading"> 数据字典管理 </header>
							<table class="table table-striped border-top" id="sample_1">
								<thead>
									<tr>
										<th style="width: 8px;" nowrap="nowrap"><input type="checkbox" name="box" class="group-checkable" data-set="#sample_1 .checkboxes" value="" /></th>
										<th class="hidden-phone" nowrap="nowrap">字典类型</th>
										<th class="hidden-phone" nowrap="nowrap">代码</th>
										<th class="hidden-phone" nowrap="nowrap">名称</th>
<!-- 										<th class="hidden-phone" nowrap="nowrap">上级字典</th> -->
										<th class="hidden-phone" nowrap="nowrap">层级</th>
										<th class="hidden-phone" nowrap="nowrap">描述</th>
										<th class="hidden-phone" nowrap="nowrap">状态</th>
										<th class="hidden-phone" nowrap="nowrap">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${list}" var="info" varStatus="s">
										<tr class="odd gradeX">
											<td nowrap="nowrap"><input type="checkbox" name="box" class="checkboxes" value="${info.rowid}" /></td>
											<td nowrap="nowrap">${info.fieldtype}</td>
											<td nowrap="nowrap">${info.idfieldvalue}</td>
											<td nowrap="nowrap">${info.descfieldvalue}</td>
<%-- 											<td>${info.parentCode}</td> --%>
											<td nowrap="nowrap">${info.level}</td>
											<td nowrap="nowrap">${info.itemDesc}</td>
											<c:if test="${info.state == 1}">
												<td nowrap="nowrap">有效</td>
											</c:if>
											<c:if test="${info.state == 0}">
												<td nowrap="nowrap">无效</td>
											</c:if>
											<td class="hidden-phone">
												<button class="btn btn-primary btn-xs" onclick="doEdit('${info.rowid}','${info.fieldtype}','${info.idfieldvalue}','${info.descfieldvalue}','${info.level}','${info.itemDesc}','${info.state}')">
													<i class="icon-pencil"></i>
												</button> &nbsp;
												<button class="btn btn-danger btn-xs" onclick="doDel('${info.rowid}')">
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
						<h4 class="modal-title" id="modal-title">字典信息</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form" method="post" action="${appRoot}/dictionary/addOrUpdate" id="dictionaryForm" name="dictionaryForm" onsubmit="return validateForm();" >
							<input type="hidden" name="editId" id="editId">
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">字典类型<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="fieldtype"
										name="fieldtype" placeholder="请输入字典类型">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">代码<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="idfieldvalue" name="idfieldvalue" placeholder="请输入字典代码">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">名称<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="descfieldvalue" name="descfieldvalue" placeholder="请输入名称">
								</div>
							</div>
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-lg-2 control-label">上级字典<font style="color:red;">*</font></label> -->
<!-- 								<div class="col-lg-10"> -->
<!-- 									<select class="form-control" id="parentCode" name="parentCode"> -->
<%-- 										<c:forEach items="${list}" var="info"> --%>
<%-- 											<c:if test="${info.parentCode == null}"> --%>
<%-- 												<option value="${info.idfieldvalue}">${info.descfieldvalue}</option> --%>
<%-- 											</c:if> --%>
<%-- 										</c:forEach> --%>
<!-- 											<option value="">NULL</option> -->
<!-- 									</select> -->
<!-- 								</div> -->
<!-- 							</div> -->
							<div class="form-group">
								<label class="col-lg-2 control-label">层级<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<input type="number" class="form-control" id="level" name="level" placeholder="请输入层级">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">描述<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<textarea rows="5" cols="60" id="itemDesc" name="itemDesc" class="form-control" maxlength="200"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">状态<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<select id="state" name="state" class="form-control">
										<option value="1">有效</option>
										<option value="0">无效</option>
									</select>
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
	<form action="${appRoot}/dictionary/del" method="post" id="deleForm" name="deleForm">
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
		if($('#fieldtype').val() == null || $('#fieldtype').val() == ''){
			windowShow("字典类型不能为空!","");
			return false;
		}
		if($('#idfieldvalue').val() == null || $('#idfieldvalue').val() == ''){
			windowShow("代码不能为空!","");
			return false;
		}
		if($('#descfieldvalue').val() == null || $('#descfieldvalue').val() == ''){
			windowShow("名称不能为空!","");
			return false;
		}
		if($('#level').val() == null || $('#level').val() == ''){
			windowShow("层级不能为空!","");
			return false;
		}
		/* if(getFlag() == false){
			windowShow("代码不能重复!","");
			return false;
		} */
		
		return true;
	}
	
	function getFlag(){
		var flag = false;
		$.ajax({
			type:'POST',
			data:{
					'idfieldvalue':$('#idfieldvalue').val(),
					'fieldtype':$('#fieldtype').val(),
					'editId':$('#editId').val()
				},
			url:'${appRoot}/dictionary/validate',
			dataType:'json',
			success:function(data){
				alert(data.result);
				if(data.result == 'ok'){
					flag = true;
				}else{
					flag = false;
				}
			}
		});
		
		return flag;
	}
		function doAdd() {
			$('#editId').val('');
			$('#idfieldvalue').val('');
			$('#descfieldvalue').val('');
// 			$('#parentCode').val('');
			$('#level').val('');
			$('#itemDesc').val('');
			$('#state').val('1');
			var $modal = $('#myModal');
			$modal.modal();
		}
		
		function doEdit(rowid,fieldtype,idfieldvalue,descfieldvalue,level,itemDesc,state){
			$('#editId').val(rowid);
			$('#fieldtype').val(fieldtype);
			$('#idfieldvalue').val(idfieldvalue);
			$('#descfieldvalue').val(descfieldvalue);
// 			$('#parentCode').val(parentCode);
			$('#level').val(level);
// 			$('#itemDesc').text(itemDesc);
			$('#state').val(state);
			var $modal = $('#myModal');
			$('#modal-title').text("修改公司信息");
			$modal.modal(); 
		}
		
		function submitData(){
			
			$('#dictionaryForm').submit();
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
