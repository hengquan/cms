<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ include file="/WEB-INF/jsp/inc/sessions.jsp"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<!DOCTYPE html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/jsp/inc/head_bootstrap.jsp"%>

<link href="${appRoot}/static/assets/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css">
<!-- Custom styles for this template -->
<link href="${appRoot}/static/css/style.css" rel="stylesheet">
<link href="${appRoot}/static/css/style-responsive.css" rel="stylesheet"/>

<title>推荐抽奖管理</title>
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
							<header class="panel-heading">推荐抽奖管理</header>
							<table class="table table-striped border-top" id="sample_1">
								<thead>
									<tr>
										<th>图片</th>
										<th class="hidden-phone">活动名称</th>
										<th class="hidden-phone">活动时间</th>
										<th class="hidden-phone">创建时间</th>
										<th class="hidden-phone">活动说明</th>
										<th class="hidden-phone">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${list}" var="info" varStatus="s">
										<tr class="odd gradeX">
											<fmt:formatDate value="${currentTime}" type="both" var="ct" pattern="yyyy-MM-dd"/>
											<fmt:formatDate value="${info.startTime}" type="both" var="st" pattern="yyyy-MM-dd"/>
											<fmt:formatDate value="${info.endTime}" type="both" var="et" pattern="yyyy-MM-dd"/>
											<td><img src="${appAccessUrl}/${info.imgUrl}" width="50px" height="50px"></td>
											<td class="hidden-phone">${info.title}</td>
											<td class="hidden-phone">${st}到&nbsp;${et}</td>
											<td class="hidden-phone"><fmt:formatDate value="${info.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											<c:if test="${fn:length(info.remark) > 20}">
												<td class="hidden-phone">${fn:substring(info.remark,0,20)}...</td>
											</c:if>
											<c:if test="${fn:length(info.remark) <= 20}">
												<td class="hidden-phone">${info.remark}</td>
											</c:if>
											<c:choose>
												<c:when test="${st <= ct}">
													<td class="hidden-phone">
														<button disabled="disabled" class="btn btn-primary btn-xs" onclick="doEdit('${info.id}')">
															<i class="icon-pencil"></i>
														</button> &nbsp;
														<button disabled="disabled" class="btn btn-danger btn-xs" onclick="doDel('${info.id}')">
															<i class="icon-trash"></i>
														</button> &nbsp;
														<span style="cursor:pointer" class="label label-info" onclick="doFuZhi('${info.id}','${info.title}','${info.remark}','<fmt:formatDate value="${info.startTime}" type="both" pattern="yyyy-MM-dd"/>','<fmt:formatDate value="${info.endTime}" type="both" pattern="yyyy-MM-dd"/>','${info.isvalidate}')">复制</span>
													</td>
													
												</c:when>
												<c:otherwise>
													<td class="hidden-phone">
														<button class="btn btn-primary btn-xs" onclick="doEdit('${info.id}')">
															<i class="icon-pencil"></i>
														</button> &nbsp;
														<button class="btn btn-danger btn-xs" onclick="doDel('${info.id}')">
															<i class="icon-trash"></i>
														</button> &nbsp;
														<span style="cursor:pointer" class="label label-info" onclick="doFuZhi('${info.id}','${info.title}','${info.remark}','<fmt:formatDate value="${info.startTime}" type="both" pattern="yyyy-MM-dd"/>','<fmt:formatDate value="${info.endTime}" type="both" pattern="yyyy-MM-dd"/>','${info.isvalidate}')">复制</span>
													</td>
												</c:otherwise>
											</c:choose>
											
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
		
<!-- 		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" -->
<!-- 			aria-labelledby="myModalLabel" aria-hidden="true"> -->
<!-- 			<div class="modal-dialog"> -->
<!-- 				<div class="modal-content"> -->
<!-- 					<div class="modal-header"> -->
<!-- 						<button type="button" class="close" data-dismiss="modal" -->
<!-- 							aria-hidden="true">&times;</button> -->
<!-- 						<h4 class="modal-title" id="modal-title">添加活动</h4> -->
<!-- 					</div> -->
<!-- 					<div class="modal-body"> -->
<%-- 						<form id="activityForm" enctype="multipart/form-data" class="form-horizontal" role="form" method="POST" action="${appRoot}/activity/addOrUpdate"> --%>
<!-- 							<input type="hidden" name="editId" id="editId"> -->
<!-- 							<input type="hidden" name="activeType" id="activeType" value="01"> -->
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-lg-2 control-label pd-r5">标题<font style="color:red;">*</font></label> -->
<!-- 								<div class="col-lg-10"> -->
<!-- 									<input type="text" class="form-control" id="title" -->
<!-- 										name="title" placeholder="请输入标题"> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-lg-2 control-label pd-r5">是否有效<font style="color:red;">*</font></label> -->
<!-- 								<div class="col-lg-10"> -->
<!-- 									<select class="form-control" id="isvalidate" name="isvalidate"> -->
<!-- 										<option value="1">有效</option> -->
<!-- 										<option value="0">无效</option> -->
<!-- 									</select> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-lg-2 control-label pd-r5">活动时间<font style="color:red;">*</font></label> -->
<!-- 								<div class="col-lg-10"> -->
<!-- 									<input type="date" class="form-control" id="startTime" name="startTime" placeholder="请输入开始时间" />- -->
<!-- 									<input type="date" class="form-control" id="endTime" name="endTime" placeholder="请输入结束时间" /> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							 <div class="form-group"> -->
<!--                                       <label class="col-lg-2 control-label pd-r5">上传图片<font style="color:red;">*</font></label> -->
<!--                                       <div class="col-lg-10"> -->
<!--                                         <input type="file" class="form-control" id="imgUrl" name="imgUrl" placeholder="请选择文件"> -->
<!--                                       </div> -->
<!--                              	</div> -->
<!--                              	<div class="form-group"> -->
<!-- 								<label class="col-lg-2 control-label">备注<font style="color:red;"></font></label> -->
<!-- 								<div class="col-lg-10"> -->
<!-- 									<textarea rows="5" cols="60" class="form-control" id="remark" name="remark"> -->
<!-- 									</textarea> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="form-group"> -->
<!-- 								<div class="col-lg-offset-2 col-lg-10"> -->
<!-- 									<button type="button" onclick="submitData();" class="btn btn-send">提交</button> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</form> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				/.modal-content -->
<!-- 			</div> -->
<!-- 			<!-- /.modal-dialog --> -->
<!-- 		</div> -->
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
	
	<form action="${appRoot}/activity/del" method="post" id="deleForm" name="deleForm">
		<input type="hidden" name="byid" id="byid">
	    <input type="hidden" name="boxeditId" id="boxeditId">
	    <input type="hidden" name="activeType" value="01">
	</form>


	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>
	<script src="${appRoot}/static/js/jquery.sparkline.js" type="text/javascript"></script>

	<script type="text/javascript" src="${appRoot}/static/assets/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${appRoot}/static/assets/data-tables/DT_bootstrap.js"></script>
	<!--script for this page only-->
	<script src="${appRoot}/static/js/dynamic-table.js"></script>
    <script src="${appRoot}/static/js/dialog_alert.js"></script>
	<script type="text/javascript">
	
		var activeType = '${activeType}';
	 	
		$('.js-del').hide();
	
		function doAdd() {
			window.location.href="${appRoot}/activity/gotoActivityDetail?flag=2&activeType="+activeType+"&aid=${id}&itemId=${itemId}";
		}
		
		function doEdit(id) {
			window.location.href="${appRoot}/activity/gotoActivityDetail?id="+id+"&flag=2&activeType="+activeType+"&aid=${id}&itemId=${itemId}";
		}
		
		function doFuZhi(id,title,remark,startTime,endTime,isvalidate){
			window.location.href="${appRoot}/activity/gotoActivityDetail?id="+id+"&flag=1&activeType="+activeType;
		}
		
// 		function submitData(){
// 			if($('#title').val() == null || $('#title').val() == ''){
// 				windowShow("标题不能为空","");
// 				return false;
// 			}
// 			if($('#startTime').val() == null || $('#startTime').val() == ''){
// 				windowShow("开始时间不能为空","");
// 				return false;
// 			}
// 			if($('#endTime').val() == null || $('#endTime').val() == ''){
// 				windowShow("结束时间不能为空","");
// 				return false;
// 			}
// 			if($('#endTime').val() < $('#endTime').val()){
// 				windowShow("结束时间不能比开始时间小","");
// 				return false;
// 			}
// 			if($("#editId").val() == null || $("#editId").val() == ''){
// 				if($('#imgUrl').val() == null || $('#imgUrl').val() == ''){
// 					windowShow("图片不能为空","");
// 					return false;
// 				}
// 			}
// 			$.ajax({
// 				type:'POST',
// 				data:{
// 					'startTime':$('#startTime').val(),
// 					'endTime':$('#endTime').val(),
// 					'activeType':$('#activeType').val(),
// 					'editId':$('#editId').val()
// 					},
// 				url:'${appRoot}/activity/validateDate',
// 				dataType:'json',
// 				success:function(data){
// 					if(data.result == 'error'){
// 						windowShow("活动时间冲突","");
// 						return false;
// 					}else{
// 						$('#activityForm').submit();
// 					}
// 				},
// 				error:function(data){
// 					alert("服务器错误!");
// 				}
// 			});
// 		}	
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
