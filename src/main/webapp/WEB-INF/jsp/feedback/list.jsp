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
							<header class="panel-heading">反馈信息</header>
							<table class="table table-striped border-top" id="sample_1">
								<thead>
									<tr>
										<th style="width: 8px;"><input type="checkbox" name="box"
											class="group-checkable" data-set="#sample_1 .checkboxes"
											value="" /></th>
										<th class="hidden-phone">反馈人</th>
										<th class="hidden-phone">反馈</th>
										<th class="hidden-phone">反馈时间</th>
										<td class="hidden-phone">操作</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${tblFeedbackList}" var="info" varStatus="s">
										<tr class="odd gradeX">
											<td><input type="checkbox" name="box" class="checkboxes"
												value="${info.id}" /></td>
											<td>${info.nickname }</td>
											<td class="hidden-phone">
											<c:choose>
												<c:when test="${fn:length(info.context) > 30}">
													${fn:substring(info.context,0,30)}...
												</c:when>
												<c:otherwise>
													${info.context}
												</c:otherwise>
											</c:choose>
											</td>
											<td class="hidden-phone">${info.addTimeString}</td>
											<td class="hidden-phone">
												<button class="btn btn-danger btn-xs"
													onclick="doDel('${info.id}')">
													<i class="icon-trash "></i>
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
					<button data-dismiss="modal" class="btn btn-default" type="button"
						id="quxiao">取消</button>
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
						<button data-dismiss="modal" class="btn btn-default" type="button"
							id="quxiao">取消</button>
						<button class="btn btn-warning" type="button" onclick="Del()">
							确定</button>
					</div>
				</div>
			</div>
		</div>
		<!-- modal -->
	</div>

	<%-- 模板 end --%>

	<form action="${appRoot}/feedback/list" method="post" id="deleForm"
		name="deleForm">
		<input type="hidden" name="byid" id="byid"> <input
			type="hidden" name="boxeditId" id="boxeditId">
	</form>


	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>
	<script src="${appRoot}/static/js/jquery.sparkline.js"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="${appRoot}/static/js/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="${appRoot}/static/assets/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript"
		src="${appRoot}/static/assets/data-tables/DT_bootstrap.js"></script>
	<!--script for this page only-->
	<script src="${appRoot}/static/js/dynamic-table1.js"></script>
	<script src="${appRoot}/static/js/dialog_alert.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$(".js-add").hide();
	});

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
		var flag = checkbox();
		if (flag) {
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
		var id = $("#byid").val();
		deleteData(id);
	}

	function Delete() {
		var id = $("#boxeditId").val();
		if (id.indexOf(",") > 0) {
			id = id.substring(0, id.length - 1);
		}
		deleteData(id);
	}
	// 删除数据操作
	function deleteData(id) {
		var url = wxmp.appRoot + "/feedback/remove_sys_feedback";
		$.post(url, {
			"id" : id
		}, function(data) {
			if (data instanceof Object) {
				var code = data.code;
				if (code != "1") {
					windowShow(data.message);
					return;
				} else {
					$('#myModal2').hide();
					window.location.reload();
				}
			} else {
				var data = eval("(" + data + ")");
				var code = data.code;
				if (code != "1") {
					windowShow(data.message);
					return;
				} else {
					$('#myModal2').hide();
					window.location.reload();
				}
			}
			$('#quxiao').click();
			windowShow("删除成功", "deleparamForm");

		}, "json");
	}

	// 刷新列表
	function doRefresh() {
		$("#deleForm").submit();
	}
	// 提交数据 添加或者修改
	function submitParamData() {
		var url = wxmp.appRoot + "/sys_param/add_update?" + new Date();
		var paramName = $("#paramName").val();
		var paramCode = $("#paramCode").val();
		var paramVal = $("#paramVal").val();
		var remark = $('#remark').val();
		var id = $("#editId").val();
		if ($("#paramName").val() == null || $("#paramName").val() == '') {
			windowShow("参数名不能为空", "");
			return false;
		}
		if ($("#paramCode").val() == null || $("#paramCode").val() == '') {
			windowShow("参数编码不能为空", "");
			return false;
		}
		if ($("#paramVal").val() == null || $("#paramVal").val() == '') {
			windowShow("参数值不能为空", "");
			return false;
		}
		$.post(url, {
			id : id,
			paramName : paramName,
			paramCode : paramCode,
			paramVal : paramVal,
			remark : remark
		}, function(res) {
			if (res.code == "1") {
				var $modal = $('#myModal');
				$modal.hide();
				window.location.reload();
			} else {
				if (id != null && id != "") {
					windowShow(res.message, "");
				} else {
					windowShow(res.message, "");
				}
			}
		}, "json");

	}
	</script>

</body>
</html>
