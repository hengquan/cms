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
							<header class="panel-heading" style="height:50px;">
							
							<div class="col-lg-1">回复列表</div>
							<a href="javascript:history.go(-1)" class="btn btn-send mini btn-white col-lg-1 pull-right" style="align:right;"><i class="glyphicon glyphicon-arrow-left"></i></a>
							
							</header>
							
							<form action="${appRoot}/problem/list" method="post"
								id="selectCheckMessage">
								<!-- 根据用户昵称查询 -->
								<!-- <div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<input type="text" class="btn"
										style="width: 500px; border: 1px solid #ddd; text-align: left;"
										placeholder="请输入需要查询的用户昵称" name="userName" value=""><span>
										<button class="btn sr-btn-imp" style="float: right"
											onclick="seeAllMsg()">
											<i class="icon-search"></i>
										</button>
									</span>
								</div> -->


							<!-- 	<div
									style="float: right; position: relative; margin-top: 16px; margin-left: 20px;">
									<a href="javascript:doRefresh();" class="btn mini btn-white"><i
										class="icon-refresh"></i></a>
								</div> -->

							<!-- 	<div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<a href="javascript:doDelete();" class="btn mini btn-white"><i
										class="icon-trash"></i></a>
								</div> -->
								
								
							<!-- 	<div
									style="float: right; position: relative; margin-top: 16px; margin-left: 20px;">
									<a href="javascript:doDelete();" class="btn mini btn-white"><i
										class="glyphicon glyphicon-arrow-left"></i></a>
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

							<div style="clear: both"></div>

							<div class="modal-body">
								<form class="form-horizontal" role="form" id="itemForm"
									name="itemForm">
									<input type="hidden" name="editId" id="editId">
									<div class="form-group">
										<label class="col-lg-1 control-label pd-r5">文字消息<font
											style="color: red;"></font></label>
										<div class="col-lg-6">
											<textarea rows="5" cols="60" class="form-control">${proble.contentText}</textarea>
										</div>
									</div>
					
									<c:forEach items="${attachments}" var="u" varStatus="s">
										<c:if test="${u.attachtype==2}">
											<div class="form-group">
												<label class="col-lg-1 control-label pd-r5">语音消息<font
													style="color: red;"></font></label>
												<div class="col-lg-10">
													<audio src="${u.filenamestring}" controls="controls">
													</audio>   
												</div>
											</div>
										</c:if>
									</c:forEach>
										
									<c:if test="${!empty attachments}">
										<div class="form-group">
											<label class="col-lg-1 control-label pd-r5">图片消息<font
												style="color: red;"></font></label>
											<div class="col-lg-10">
												<c:forEach items="${attachments}" var="u" varStatus="s">
													<c:if test="${u.attachtype==1}">
														<img src="${u.filenamestring}" alt="..." class="img-thumbnail" width="140" height="140" /> 
													</c:if>
													<c:if test="${u.attachtype!=1 && u.attachtype!=2}">
														<img src="${appRoot}/static/img/zanwu1.png" alt="..." class="img-thumbnail" width="140" height="140" /> 
													</c:if>
												</c:forEach>
											</div>
										</div>
									</c:if>

								</form>
							</div>


							<table class="table table-striped border-top" id="sample_1">
								<thead>
									<tr>
										<th style="width: 8px;"><input type="checkbox" name="box"
											class="group-checkable" data-set="#sample_1 .checkboxes"
											value="" /></th>
										<th class="hidden-phone">姓名/昵称</th>
										<th class="hidden-phone">回答内容</th>
										<th class="hidden-phone">赞的数量</th>
										<th class="hidden-phone">举报的数量</th>
										<th class="hidden-phone">偷听的数量</th>
										<th class="hidden-phone">是否为最佳答案</th>
										<th class="hidden-phone">是否匿名支招</th>
										<th class="hidden-phone">是否为幸运支招</th>
										<th class="hidden-phone">创建时间</th>
									</tr>
								</thead>
								<tbody id="theTbody">
									<c:forEach items="${answer}" var="u" varStatus="s">
										<tr class="odd gradeX theTr">
											<td><input type="checkbox" name="box" class="checkboxes"
												value="${u.id}" /></td>
											<td class="hidden-phone"><c:choose>
													<c:when test="${u.userName== '' || u.userName== undefined}">  
												${u.nickname}									   	
											</c:when>
													<c:otherwise> 
												${u.userName}	
											</c:otherwise>
												</c:choose></td>
											<td><button type="button" class="btn btn-primary"
													onclick="problemMessage('${u.answer_content_text}','${u.id}')">查看回答信息</button></td>
											
											<td class="hidden-phone">${u.praise_count}次</td>
											<td class="hidden-phone">${u.report_count}次</td>
											<td class="hidden-phone"><a href="javascript:seeEavesdropMessage('${u.id}');" class="btn mini btn-white" title="查看偷听用户"><b>${u.eavesdrop_count}次</b></a></td>
											
											<td class="hidden-phone">
												<c:if test="${u.isoptimum==0}">否</c:if>
												<c:if test="${u.isoptimum==1}">是</c:if>
											</td>
											<td class="hidden-phone">
												<c:if test="${u.is_secret==0}">否</c:if>
												<c:if test="${u.is_secret==1}">是</c:if>
											</td>
											<td class="hidden-phone">
												<c:if test="${u.is_lucky==0}">否</c:if>
												<c:if test="${u.is_lucky==1}">是</c:if>
											</td>
											<td class="hidden-phone">${u.create_time}</td>

											<%-- 	<td class="hidden-phone">
										<button type="button" onclick="manyMessage('${u.id}')" class="btn btn-send">查看更多</button>
										<button type="button" onclick="" class="btn btn-send">提交</button>
									</td> --%>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<%-- <nav class="clearfix">
								<ul class="pagination pull-right">
									<li><a href="javascript:doPanation(1)">首页</a></li>
									<li><a
										href="javascript:doPanation(${nowPage - 1 < 1 ? 1 : nowPage - 1})"
										aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
									</a></li>
									<c:forEach begin="${nowPage - 5 > 0 ? nowPage - 5 : 1 }"
										end="${nowPage + 5 > totalPageNum ? totalPageNum : nowPage + 5}"
										var="t">
										<li><a href="javascript:doPanation(${t})">${t}</a></li>
									</c:forEach>
									<li><a
										href="javascript:doPanation(${nowPage + 1 > totalPageNum ? totalPageNum : nowPage + 1})"
										aria-label="Next"> <span aria-hidden="true">&raquo;</span>
									</a></li>
									<li><a href="javascript:doPanation(${totalPageNum})">末页</a></li>
								</ul>
							</nav> --%>
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
					<h4 class="modal-title titleMessage" id="modal-title">回答信息</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="itemForm"
						name="itemForm">
						<input type="hidden" name="editId" id="editId">
						<div class="form-group" id="textMessge">
							<label class="col-lg-2 control-label pd-r5">文字消息<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<textarea rows="5" cols="60" class="form-control"
									id="textContent">
									
									</textarea>
							</div>
						</div>
						<div class="form-group" id="phoneMessge">
							<label class="col-lg-2 control-label pd-r5">语音消息<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<audio src="" controls="controls" id="phoneContent">
								</audio>
							</div>
						</div>
						<div class="form-group" id="imgMessge">
							<label class="col-lg-2 control-label pd-r5">图片消息<font
								style="color: red;"></font></label>
							<div class="col-lg-10" id="imgContent">
							<!-- 图片放置区 -->
							</div>
						</div>
						<div class="form-group" id="videoMessge">
							<label class="col-lg-2 control-label pd-r5">视频地址(ogg、MP4)<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<video src="" controls="controls"
									width="430" height="330" id="videoContent">
								</video>
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-offset-2 col-lg-10">
								<button data-dismiss="modal" type="button" id="quxiao"
									class="btn btn-send">返回</button>
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

	<form action="${appRoot}/user/del" method="post" id="deleForm"
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
	
		//查看相关问题的偷听用户
		function seeEavesdropMessage(answerId){
			//alert(answerId);
			window.location.href="${appRoot}/answer/eavesdropMessage?id="+answerId;
		}
	
	
	
		//查看问题的内容的信息
		function problemMessage(contentText,id){
			$("#textMessge").hide();
			$("#phoneMessge").hide();
			$("#imgMessge").hide();
			$("#videoMessge").hide();
			//清空
			$("#imgContent").html("");
			
			$(".titleMessage").html("回答信息");
			if(contentText!=undefined){
				$("#textContent").val(contentText);
				$("#textMessge").show();
			}
			
			$.ajax({
				type:'post',
				data : {"id":id},  
				url:'${appRoot}/answer/message',
				dataType:'json',
				success:function(data){
					if(data.msg==0){
						if(data.answer.answerContentText!=undefined){
							$("#textContent").val(data.answer.answerContentText);
							$("#textMessge").show();
						}
						$(".titleMessage").html("回答信息");
						if(data.answer.answerContentVideo == null || data.answer.answerContentVideo == ''){
							
						}else{
							$("#videoContent").prop("src",data.answer.answerContentVideo);
							$("#videoMessge").show();
						}
						//取语音和图片信息
						$.each(data.attachments,function(i,item){
			                if(item.attachtype==2){
			                	$("#phoneContent").prop("src",item.filenamestring);
			                	$("#phoneMessge").show();
			                }else{
			                	var img = '<img src="'+ item.filenamestring +'" alt="..." class="img-thumbnail" width="140" height="140">';
			                	$("#imgContent").append(img);
			                	$("#imgMessge").show();
			                }
			            });
						var $modal = $('#myModal');
						$modal.modal();
					}else{
						windowShow("操作失败","");
					}
				}
			});
			
		}
		
		
		$(function() {
			$('.input-group').hide();
			//$('#sample_1_info').hide();
			//$('.dataTables_paginate').hide();
			$("#sample_1_length .form-control").hide();
			$("#sample_1_length .js-add").hide();
			$("#sample_1_length .js-ref").hide();
			$("#sample_1_length .js-del").hide();
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

		function Delete() {
			$("#deleForm").submit();
		}
	</script>
	<input type="hidden" value="" id="adminId" />
</body>
</html>

