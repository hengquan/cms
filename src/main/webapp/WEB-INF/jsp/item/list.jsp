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
							<header class="panel-heading">菜单信息管理 </header>
							<table class="table table-striped border-top" id="sample_1">
								<thead>
									<tr>
										<th style="width: 8px;"><input type="checkbox" name="box" class="group-checkable" data-set="#sample_1 .checkboxes" value="" /></th>
										<th>菜单名称</th>
										<th class="hidden-phone">链接URL</th>
										<th class="hidden-phone">图标样式</th>
										<th class="hidden-phone">是否可见</th>
										<th class="hidden-phone">顺序</th>
										<th class="hidden-phone">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${itemList}" var="info" varStatus="s">
										<tr class="odd gradeX">
											<td><input type="checkbox" name="box" class="checkboxes" value="${info.id}" /></td>
											<td>${info.itemName}</td>
											<td class="hidden-phone">${info.itemUrl}</td>
										
											<td class="hidden-phone">${info.iconImg}</td>
											<c:if test="${info.visibleFlag==1}">
												<td class="hidden-phone">是</td>
											</c:if>
											<c:if test="${info.visibleFlag==0}">
												<td class="hidden-phone">否</td>
											</c:if>
											<td class="hidden-phone">${info.seqNum}</td>
											<td class="hidden-phone">
												<button class="btn btn-primary btn-xs" onclick="doEdit('${info.id}','${info.itemName}','${info.itemUrl}','${info.parentId}','${info.iconImg}','${info.visibleFlag}','${info.seqNum}','${info.remark}')">
													<i class="icon-pencil"></i>
												</button> &nbsp;
												<button class="btn btn-danger btn-xs" onclick="doDel('${info.id}')">
													<i class="icon-trash"></i>
												</button> 
											</td>
										</tr>
										<c:forEach items="${info.childList}" var="childItem" varStatus="s">
											<tr class="odd gradeX">
											<td><input type="checkbox" name="box" class="checkboxes" value="${childItem.id}" /></td>
											<td>----${childItem.itemName}</td>
											<td class="hidden-phone">${childItem.itemUrl}</td>
											
											<td class="hidden-phone">${childItem.iconImg}</td>
											<c:if test="${childItem.visibleFlag==1}">
												<td class="hidden-phone">是</td>
											</c:if>
											<c:if test="${childItem.visibleFlag==0}">
												<td class="hidden-phone">否</td>
											</c:if>
											<td class="hidden-phone">${childItem.seqNum}</td>
											<td class="hidden-phone">
												<button class="btn btn-primary btn-xs" onclick="doEdit('${childItem.id}','${childItem.itemName}','${childItem.itemUrl}','${childItem.parentId}','${childItem.iconImg}','${childItem.visibleFlag}','${childItem.seqNum}','${childItem.remark}')">
													<i class="icon-pencil"></i>
												</button> &nbsp;
												<button class="btn btn-danger btn-xs" onclick="doDel('${childItem.id}')">
													<i class="icon-trash"></i>
												</button> 
											</td>
										</tr>
										</c:forEach>
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
						<h4 class="modal-title" id="modal-title">添加菜单</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form" id="itemForm" name="itemForm" >
							<input type="hidden" name="editId" id="editId">
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">菜单名称<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="itemName"
										name="itemName" placeholder="请输入菜单名称">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">链接URL<!-- <font style="color:red;">*</font> --></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="itemUrl"
										name="itemUrl" placeholder="请输入菜单链接">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">上级菜单<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<select class="form-control" name="parentId" id="parentId" title=>
										<option value="" >选择我表示没有上级菜单</option>
									<c:forEach items="${itemList}" var="item" varStatus="s">
										<option value="${item.id}" >${item.itemName}</option>
									</c:forEach>
									</select>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label">图标样式<!-- <font style="color:red;">*</font> --></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="iconImg" name="iconImg" placeholder="请选择菜单图标图片">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">是否可见<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<input type="radio" class="form-radio" id="visibleFlag" name="visibleFlag" value="1" checked="checked">是
									<input type="radio" class="form-radio" id="visibleFlag" name="visibleFlag" value="0">否
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">顺序<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<input type="number" class="form-control" id="seqNum" name="seqNum" value="0" min="0">
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
	
	<form action="${appRoot}/item/del" method="post" id="deleForm" name="deleForm">
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
		if($('#itemName').val() == null || $('#itemName').val() == ''){
			windowShow("菜单名称不能为空","");
			return false;
		}
		/* if($('#itemUrl').val() == null || $('#itemUrl').val() == ''){
			windowShow("菜单链接不能为空","");
			return false;
		} */
	}
	
	
	
	
		function doAdd() {
			$("#editId").val('');
			$('#itemName').val('');
			$('#itemUrl').val('');
			$('#parentId').val('');
			$('#iconImg').val('');
			$('#visibleFlag[value="1"]').prop('checked',true);
			$('#seqNum').val(0);
			$('#remark').val(''); 
			var $modal = $('#myModal');
			$modal.modal();
		}
		
		function doEdit(id,itemName,itemUrl,parentId,iconImg,visibleFlag,seqNum,remark) {
			
			$('#editId').val(id);
			$('#itemName').val(itemName);
			$('#itemUrl').val(itemUrl);
			$('#parentId').val(parentId);
			$('#iconImg').val(iconImg);
			$('#visibleFlag[value="'+visibleFlag+'"]').prop('checked',true);
			$('#seqNum').val(seqNum);
			$('#remark').val(remark);
			var $modal = $('#myModal');
			$('#modal-title').text("修改菜单信息");
			$modal.modal(); 
		}
		
		function submitData(){
			//$('#itemForm').submit();
			if($('#itemName').val() == null || $('#itemName').val() == ''){
				windowShow("菜单名称不能为空","");
				return false;
			}else{
				$.ajax({
					type:'post',
					data:$('#itemForm').serialize(),
					url:'${appRoot}/item/addAndUpdate',
					dataType:'json',
					success:function(data){
						if(data.msg=="iscz"){
							windowShow("该角色名称已存在!请重新输入角色名称","");
							return false;
						}else if(data.msg=="isc"){
							$('#myModal').modal('hide');
							windowShow("添加角色成功","");
							location.reload();
						}else if(data.msg=="isupdate"){
							$('#myModal').modal('hide');
							windowShow("修改角色成功","");
							location.reload();
						}else{
							$('#myModal').modal('hide');
							windowShow("操作失败","");
						}
						
					},
					error:function(data){
						windowShow("操作失败","");
					}
					
				});
				
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
