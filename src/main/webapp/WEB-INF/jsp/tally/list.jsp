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
							<header class="panel-heading">问题统计</header>
							<form class="form-inline" action="${appRoot}/problemtally/list" id="queryForm" method="POST">
							<input type="hidden" name="itemId" value="${itemId }">
                            <input type="hidden" name="id" value="${id }">
							  &nbsp;&nbsp;<div class="form-group">
							    <label for="userName">统计时间段:</label>
							    <input type="datetime-local" class="form-control" id="startTime" name="startTime" placeholder="请选择开始时间" style="width: 200px;" value="<%=request.getParameter("startTime") == null ? "" : request.getParameter("startTime")%>" />
							  </div>&nbsp;&nbsp;
							 <div class="form-group">
							    <label for="userName">至</label>
							    <input type="datetime-local" class="form-control" id="endTime" name="endTime" placeholder="请选择结束时间" style="width: 200px;" value="<%=request.getParameter("endTime") == null ? "" : request.getParameter("endTime")%>" />
							  </div>
							  &nbsp;&nbsp;
							  <div class="form-group">
							  	<button type="submit" class="btn btn-default">查询</button>
							  	<!-- <button type="button" class="btn btn-default" onclick="doAdd();">添加文章</button> -->
							  </div>
							  <input type="hidden" name="nowPage" id="nowPage" value="${nowPage}"/>
							</form>
							<hr>
							<table class="table table-striped border-top">
								<thead>
									<tr>
<!-- 										<th style="width: 8px;"><input type="checkbox" name="box" class="group-checkable" data-set="#sample_1 .checkboxes" value="" /></th> -->
										<th class="hidden-phone">提问人</th>
										<th class="hidden-phone">问题内容</th>
										<th class="hidden-phone">问题标签</th>
										<th class="hidden-phone">回答次数</th>
										<th class="hidden-phone">偷听次数</th>
										<th class="hidden-phone">提问时间</th>
										<th class="hidden-phone">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${list}" var="info" varStatus="s">
										<tr class="odd gradeX">
<%-- 											<td><input type="checkbox" name="box" class="checkboxes" value="${info.id}" /></td> --%>
											<td class="hidden-phone">
											${(info.userName == null || info.userName == '') ? info.nickname : info.userName}
											</td>
											<td class="hidden-phone"><button type="button" class="btn btn-primary" onclick="problemMessage('${info.contentText}','${info.id}')">查看问题信息</button></td>
											<td class="hidden-phone theTypeID">
											<c:set var="indexCount" value="1"/>
											<c:forEach items="${info.ptList }" var="pt" varStatus="s">
												${pt.typeName}<c:if test="${indexCount<fn:length(info.ptList)}">、</c:if>
            	 								<c:set var="indexCount" value="${indexCount+1}"/>
            								</c:forEach>
											</td>
											<td class="hidden-phone">${info.answerCount }</td>
											<td class="hidden-phone">${info.eavesdropCountOne }</td>
											<td class="hidden-phone">${info.createTimeOne }</td>
											<td class="hidden-phone">
												<!-- <button class="btn btn-primary btn-xs" onclick="doEdit('')">
													<i class="icon-pencil"></i>
												</button> &nbsp;
												<button class="btn btn-danger btn-xs" onclick="doDel('')">
													<i class="icon-trash"></i>
												</button> &nbsp; -->
												<button type="button" class="btn btn-primary" onclick="seeAnswer('${info.id}')">查看详情</button>
											</td> 
										</tr>
									</c:forEach> 
								</tbody>
							</table>
						</section>
					</div>
				</div>
			<c:if test="${totalPageNum != 0}">
			<div class="panel-footer">
							<nav class="clearfix">
								<ul class="pagination pull-left">
									<li><div class="dataTables_info" id="sample_1_info">共${totalPageNum } 页,当前为第${nowPage}页</div></li>
								</ul>
								<ul class="pagination pull-right">
									<li><a href="javascript:doPanation(1)">首页</a></li>
									<li>
										<a href="javascript:doPanation(${nowPage - 1 < 1 ? 1 : nowPage - 1})" aria-label="Previous">
											<span aria-hidden="true">&laquo;</span>
										</a>
									</li>
										<c:forEach begin="${nowPage - 5 > 0 ? nowPage - 5 : 1 }" end="${nowPage + 5 > totalPageNum ? totalPageNum : nowPage + 5}" var="t">
											<li <c:if test="${nowPage == t}">class="act"</c:if>><a href="javascript:doPanation(${t})">${t}</a></li>
										</c:forEach>
									<li>
										<a href="javascript:doPanation(${nowPage + 1 > totalPageNum ? totalPageNum : nowPage + 1})" aria-label="Next">
											<span aria-hidden="true">&raquo;</span>
										</a>
									</li>
									<li><a href="javascript:doPanation(${totalPageNum})">末页</a></li>
								</ul>
							</nav>
			</div>
			</c:if>
				<!-- page end-->
			</section>
		</section>
	</section>
	
	
	

		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title titleMessage" id="modal-title">问题信息</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form" id="itemForm" name="itemForm" >
							<input type="hidden" name="editId" id="editId">
							<div class="form-group" id="textMessge">
								<label class="col-lg-2 control-label pd-r5">文字消息<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<textarea rows="5" cols="60" class="form-control" id="textContent">
									
									</textarea>
								</div>
							</div>
							<div class="form-group" id="phoneMessge">
								<label class="col-lg-2 control-label pd-r5">语音消息<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<audio src="" controls="controls" id="phoneContent">
									</audio>
								</div>
							</div>
							<div class="form-group" id="imgMessge">
								<label class="col-lg-2 control-label pd-r5">图片消息<font style="color:red;"></font></label>
								<div class="col-lg-10" id="imgContent">
								<!-- 图片放置区 -->
								</div>
							</div>
							<div class="form-group" id="videoMessge">
								<label class="col-lg-2 control-label pd-r5">视频地址<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<video src="" controls="controls" width="430" height="330" id="videoContent">
									</video>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button data-dismiss="modal" type="button" id="quxiao" class="btn btn-send">返回</button>
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
	
	<form action="${appRoot}/article/intoAddOrUpdate" method="post" id="myForm">
		<input type="hidden" name="editValue" id="editValue">
		<input type="hidden" name="id" id="id" value="${id}">
		<input type="hidden" name="itemId" id="itemId" value="${itemId}">
		
	</form>
	
	<form action="${appRoot}/article/del" method="post" id="deleForm" name="deleForm">
		<input type="hidden" name="byid" id="byid">
	    <input type="hidden" name="boxeditId" id="boxeditId">
	    <input type="hidden" name="id" id="id" value="${id}">
		<input type="hidden" name="itemId" id="itemId" value="${itemId}">
	</form>
	
	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>
	<script src="${appRoot}/static/js/jquery.sparkline.js" type="text/javascript"></script>

	<script type="text/javascript" src="${appRoot}/static/assets/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${appRoot}/static/assets/data-tables/DT_bootstrap.js"></script>
	<!--script for this page only-->
<%-- 	<script src="${appRoot}/static/js/dynamic-table.js"></script> --%>
<%--     <script src="${appRoot}/static/js/dialog_alert.js"></script> --%>
    <script src="${appRoot}/static/js/article.js"></script>
    <script type="text/javascript">
  //查看问题的内容的信息
	function problemMessage(contentText,id){
		$("#textMessge").hide();
		$("#phoneMessge").hide();
		$("#imgMessge").hide();
		$("#videoMessge").hide();
		//清空
		$("#imgContent").html("");
		
		$(".titleMessage").html("问题信息");
		if(contentText != undefined){
			$("#textContent").val(contentText);
			$("#textMessge").show();
		}
		
		$.ajax({
			type:'post',
			data : {"id":id},  
			url:'${appRoot}/problem/message',
			dataType:'json',
			success:function(data){
				if(data.msg==0){
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
	  
		/* $("#textContent").val(contentText);
		$(".titleMessage").html("问题信息");
		$("#videoMessge").hide();
		var $modal = $('#myModal');
		$modal.modal(); */
	}
    
	//查看详情
	function seeAnswer(problemId){
		//alert(problemId);
		window.location.href="${appRoot}/answer/answerMessage?id="+problemId;
	}
    
	function doAdd() {
		$("#editValue").val('');
		$('#myForm').submit();
	}
	
	function doEdit(id) {
		$("#editValue").val(id);
		$('#myForm').submit();
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
	
	function doPanation(value){
		$('#nowPage').val(value);
		$('#queryForm').submit();
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
