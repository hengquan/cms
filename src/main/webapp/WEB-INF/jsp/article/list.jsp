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
							<header class="panel-heading">文章管理 </header>
							<form class="form-inline" action="${appRoot}/article/list" id="queryForm" method="POST">
							<input type="hidden" name="itemId" value="${itemId }">
                            <input type="hidden" name="id" value="${id }">
							  <div class="form-group">
							    <label for="userName">文章标题:</label>
							    <input type="text" class="form-control" id="title" name="title" placeholder="请输入文章标题" style="width: 200px;" value="<%=request.getParameter("title") == null ? "" : request.getParameter("title")%>" />
							  </div>&nbsp;&nbsp;&nbsp;&nbsp;
							 <div class="form-group">
							    <label for="idnType">文章类型:</label>
							   			<select name="articleType" id="articleType" class="form-control" style="width: 200px;">
							   				<option value="-1" selected="selected">请选择文章类型</option>
											<!-- <option value="01">最新资讯</option>
											<option value="02">干货指导</option>
											<option value="03">大咖直播</option> -->
											<option value="关于我们">关于我们</option>
											<option value="咨询协议">咨询协议</option>
										</select>
							  </div>
							  &nbsp;&nbsp;
							  <div class="form-group">
							  	<button type="submit" class="btn btn-default">查询</button>
							  	<button type="button" class="btn btn-default" onclick="doAdd();">添加文章</button>
							  </div>
							  <input type="hidden" name="nowPage" id="nowPage" value="${nowPage}"/>
							</form>
							<hr>
							<table class="table table-striped border-top">
								<thead>
									<tr>
<!-- 										<th style="width: 8px;"><input type="checkbox" name="box" class="group-checkable" data-set="#sample_1 .checkboxes" value="" /></th> -->
										<th class="hidden-phone">标题</th>
										<th class="hidden-phone">文章类型</th>
										<th class="hidden-phone">添加时间</th>
										<th class="hidden-phone">备注</th>
										<th class="hidden-phone">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${list}" var="info" varStatus="s">
										<tr class="odd gradeX">
<%-- 											<td><input type="checkbox" name="box" class="checkboxes" value="${info.id}" /></td> --%>
											<td class="hidden-phone">${info.title}</td>
											<td class="hidden-phone">${info.articleType}</td>
											<%-- <c:choose>
												<c:when test="${fn:length(info.content) > 45}">
												<td class="hidden-phone">${fn:substring(info.content,0,45)}...</td>
												</c:when>
												<c:otherwise>
												<td class="hidden-phone">${info.content}</td>
												</c:otherwise>
											</c:choose> --%>
											<td class="hidden-phone"><fmt:formatDate value="${info.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											<c:choose>
												<c:when test="${fn:length(info.remark) > 10}">
												<td class="hidden-phone">${fn:substring(info.remark,0,10)}...</td>
												</c:when>
												<c:otherwise>
												<td class="hidden-phone">${info.remark}</td>
												</c:otherwise>
											</c:choose>
											<td class="hidden-phone">
												<button class="btn btn-primary btn-xs" onclick="doEdit('${info.id}')">
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
				<c:if test="${totalPageNum != 0}">
					<div class="panel-footer">
				<nav class="clearfix">
					<ul class="pagination pull-right">
						<li><a href="javascript:doPanation(1)">首页</a></li>
						<li>
							<a href="javascript:doPanation(${nowPage - 1 < 1 ? 1 : nowPage - 1})" aria-label="Previous">
								<span aria-hidden="true">&laquo;</span>
							</a>
						</li>
							<c:forEach begin="${nowPage - 5 > 0 ? nowPage - 5 : 1 }" end="${nowPage + 5 > totalPageNum ? totalPageNum : nowPage + 5}" var="t">
								<li><a href="javascript:doPanation(${t})">${t}</a></li>
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
    
    $(function (){
    	var ac = '${articleType}';
    	if(ac == null || ac == ''){
    		$('#articleType').val('-1');
    	}else{
    		$('#articleType').val(ac);
    	}
    	
    });
    	
    
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
