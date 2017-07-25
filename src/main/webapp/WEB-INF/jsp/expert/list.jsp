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
							<header class="panel-heading">专家列表</header>
							<form action="${appRoot}/problem/list" method="post"
								id="selectCheckMessage">
								<!-- 根据用户昵称查询 -->
								<div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<input type="text" class="btn"
										style="width: 500px; border: 1px solid #ddd; text-align: left;"
										placeholder="请输入需要查询的用户昵称" name="userName" value=""><span>
										<button class="btn sr-btn-imp" style="float: right"
											onclick="seeAllMsg()">
											<i class="icon-search"></i>
										</button>
									</span>
								</div>


								<div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<a href="javascript:doRefresh();" class="btn mini btn-white"><i
										class="icon-refresh"></i></a>
								</div>

								<!-- <div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<a href="javascript:doDelete();" class="btn mini btn-white"><i
										class="icon-trash"></i></a>
								</div> -->


								<%-- <div style="float: right;position: relative;margin-top: 16px;margin-right: 100px;">									
										<select class="form-control" style="width: 120px;" id="pageSize" name="pageSize" onchange="seeAllMsg()">
											<option value="10" <c:if test="${pageSize==10}">selected</c:if>>10</option>
											<option value="25" <c:if test="${pageSize==25}">selected</c:if>>25</option>
											<option value="50" <c:if test="${pageSize==50}">selected</c:if>>50</option>
											<option value="100" <c:if test="${pageSize==100}">selected</c:if>>100</option>
										</select>
									</div>			 --%>
								<input type="hidden" value="${nowPage}" id="nowPageNumber"
									name="nowPage"> <input type="hidden"
									value="${totalPageNum }">
							</form>

							<table class="table table-striped border-top" id="sample_1">
								<thead>
									<tr>
										<th style="width: 8px;"><input type="checkbox" name="box"
											class="group-checkable" data-set="#sample_1 .checkboxes"
											value="" /></th>
										<th class="hidden-phone">姓名/昵称</th>
										<th class="hidden-phone">性别</th>
										<th class="hidden-phone">电话</th>
										<th class="hidden-phone">电话咨询</th>
										<th class="hidden-phone">视频咨询</th>
										<th class="hidden-phone">面对面咨询</th>
										<th class="hidden-phone">显示排序</th>
										<th class="hidden-phone">查看专家简介</th>
										<!-- <th class="hidden-phone">操作</th> -->
									</tr>
								</thead>
								<tbody id="theTbody">
									<c:forEach items="${experUsers}" var="u" varStatus="s">
										<tr class="odd gradeX theTr">
											
											<input type="hidden" class="theIDType${s.count}" value="${u.openid }">
											
											<td><input type="checkbox" name="box" class="checkboxes"
												value="${u.openid}" /></td>
											<td class="hidden-phone"><c:choose>
													<c:when test="${u.userName== '' || u.userName== undefined}">  
												${u.nickname}									   	
											</c:when>
													<c:otherwise> 
												${u.userName}	
											</c:otherwise>
												</c:choose></td>
												
											<td class="hidden-phone">
												<c:if test="${u.sex==1}">男</c:if>
												<c:if test="${u.sex==2}">女</c:if>
											</td>
											<td class="hidden-phone">${u.mobile}</td>	
											
											<td class="hidden-phone" id="phone${u.openid }"></td>
											<td class="hidden-phone" id="video${u.openid }"></td>
											<td class="hidden-phone" id="interview${u.openid }"></td>
											<%-- <td class="hidden-phone">
												<button type="button" class="btn btn-primary" onclick="seeParticulars('${u.expertDescription}')">查看简介</button>
											</td> --%>
											<td class="hidden-phone">
												<input type="number" class="form-control" value="${u.userIndex}" style="width:100px;" id="${u.id}" onBlur="updateIndexValue(this)" ">
											</td>
											<td class="hidden-phone">
												<button type="button" class="btn btn-primary" onclick="particularsMsg('${u.openid}')">查看详情</button>
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
		<!-- /.modal -->
	</section>



		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title titleMessage" id="modal-title">专家简介</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form" id="itemForm" name="itemForm" >
							<input type="hidden" name="editId" id="editId">
							<div class="form-group">
								<div class="col-lg-12">
									<textarea rows="5" cols="60" class="form-control" id="textContent">
									
									</textarea>
								</div>
							</div>
						</form>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
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

	<form action="${appRoot}/problem/delProblemMessage" method="post" id="deleForm"
		name="deleForm">
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
			window.location.href="${appRoot}/expertMessage/updateIndex?id="+id+"&index="+index;
		}
		
		
		//查看详情
		function particularsMsg(openid){
			//alert(openid);
			window.location.href="${appRoot}/expert/thisMsg?openid="+openid;
		}
		
		//查看用户简介
		function seeParticulars(experMsg){
			$(".titleMessage").html("专家简介");
			$("#textContent").val(experMsg);
			var $modal = $('#myModal');
			$modal.modal();
		}
		
		
		
		$(function() {
			$('.input-group').hide();
			//$('#sample_1_info').hide();
			//$('.dataTables_paginate').hide();
			$("#sample_1_length .form-control").hide();
			$("#sample_1_length .js-add").hide();
			$("#sample_1_length .js-ref").hide();
			$("#sample_1_length .js-del").hide();
			$("#theTbody .theTr").each(function(index, li) {
				var openid = $(".theIDType"+(index+1)).val();
				$.ajax({
					type : 'post',
					url : '${appRoot}/expert/message',
					data : {"openid":openid}, 
					dataType : 'json',
					success : function(data) {
						if (data.msg == 0) {
							$.each(data.experMsg,function(i,item){
								if((i+1)==1){
									var price = item.price + "元/次";
									//alert(item.price);
									$("#phone"+openid).html(price);
								}else if((i+1)==2){
									var price = item.price + "元/次";
									//alert(item.price);
									$("#video"+openid).html(price);
								}else if((i+1)==3){
									var price = item.price + "元/次";
									//alert(item.price);
									$("#interview"+openid).html(price);
								}
							});
						} else {
							windowShow("操作失败", "");
						}
					}
				});
			});
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


		//是否选中用户
		function checkUserBox() {
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
			//alert(chestr);
			if (!jy) {
				windowShow("请选择要设置成专家的用户！")
				$('#quxiao').click();
				return false;
			} else {
				$("#setExpertIds").val(chestr);
				return true;
			}
		}

		function setExpert() {
			$("#checkExpert").submit();
		}

		function Delete() {
			$("#deleForm").submit();
		}
	</script>
	<input type="hidden" value="" id="adminId" />
</body>
</html>

