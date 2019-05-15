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
		<%@ include file="/WEB-INF/jsp/inc/header.jsp"%>
		<%@ include file="/WEB-INF/jsp/inc/sidebar.jsp"%>

		<section id="main-content">
			<section class="wrapper">
				<!-- page start-->
				<div class="row">
					<div class="col-lg-12">
						<section class="panel">
							<header class="panel-heading">文章列表</header>
							<form action="${appRoot}/article/getDataList" method="post"
								id="selectCheckMessage">
								<input type="hidden" name="itemId" value="${itemId }"> <input
									type="hidden" name="positionId" value="${positionId }">
								<!-- 根据用户昵称查询 -->
								<div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<input type="text" class="btn"
										style="width: 500px; border: 1px solid #ddd; text-align: left;"
										placeholder="请输入文章标题" name="keyword" value="${keyword }"><span>
										<button class="btn sr-btn-imp" style="float: right"
											onclick="selectDataList()">
											<i class="icon-search"></i>
										</button>
									</span>
								</div>
								<div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;"
									id="thisRole">
									<select name="roleId" id="roleId" class="btn"
										style="border: 1px solid #ddd" onchange="selectDataList()"></select>
								</div>
								<div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<a href="javascript:doRefresh();" class="btn mini btn-white"><i
										class="icon-refresh"></i></a>
								</div>
								<div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<a
										href="javascript:doAdd('${articleType }','${channelType }','${roleId }','');"
										class="btn mini btn-white"><i class="icon-plus"></i></a>
								</div>
								<div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<a href="javascript:doDelete();" class="btn mini btn-white"><i
										class="icon-trash"></i></a>
								</div>
								<input type="hidden" value="${nowPage}" id="nowPageNumber"
									name="nowPage"> <input type="hidden"
									value="${totalPageNum }">
							</form>
							<div style="clear: both"></div>
							<table class="table table-striped border-top" id="sample_1">
								<thead>
									<tr>
										<th style="width: 8px;"><input type="checkbox" name="box"
											class="group-checkable" data-set="#sample_1 .checkboxes"
											value="" /></th>
										<th class="hidden-phone">封面图</th>
										<th class="hidden-phone">标题</th>
										<th class="hidden-phone">站点</th>
										<th class="hidden-phone">频道</th>
										<th class="hidden-phone">语言</th>
										<th class="hidden-phone">排序</th>
										<th class="hidden-phone">作者</th>
										<th class="hidden-phone">状态</th>
										<th class="hidden-phone">浏览量</th>
										<th class="hidden-phone">创建时间</th>
										<th class="hidden-phone">操作</th>
									</tr>
								</thead>
								<tbody id="theTbody">
									<c:forEach items="${dataList}" var="u" varStatus="s">
										<tr class="odd gradeX theTr">
											<td><input type="checkbox" name="box" class="checkboxes"
												value="${u.id}" /></td>
											<td class="hidden-phone"><c:choose>
													<c:when test="${empty u.picUrl }">
														<img src="${appRoot }/static/img/zanwu1.png"
															style="height: 50px;">
													</c:when>
													<c:otherwise>
														<img src="${u.picUrl }" style="height: 50px;">
													</c:otherwise>
												</c:choose></td>
											<td class="hidden-phone">${u.articleName}</td>
											<td class="hidden-phone">${u.roleName}</td>
											<td class="hidden-phone">${u.setArticleTypeName}</td>
											<td class="hidden-phone">${u.language}</td>
											<td class="hidden-phone"><input type="number"
												class="btn"
												style="width: 100px; border: 1px solid #ddd; text-align: left;"
												value="${u.sort }"
												wzid="${u.id }" articleType="${u.articleType }" onchange="updateSort(this)"></td>
											<td class="hidden-phone">${u.userName}</td>
											<td class="hidden-phone"><c:if
													test="${u.isValidate == 0}">草稿</c:if> <c:if
													test="${u.isValidate == 1}">审核中</c:if> <c:if
													test="${u.isValidate == 2}">已发布</c:if> <c:if
													test="${u.isValidate == 3}">审核未通过</c:if> <c:if
													test="${u.isValidate == 4}">作废</c:if></td>
											<td class="hidden-phone">${u.views}</td>
											<td class="hidden-phone"><fmt:formatDate
													value="${u.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td class="hidden-phone">
												<button type="button"
													onclick="edit('${u.id}','${u.articleType }','${channelType }','${u.roleId }','${u.language }')"
													class="btn btn-send">修改</button>
												<button type="button"
													onclick="doAdd('${articleType }','${channelType }','${roleId }','${u.id }');"
													class="btn btn-send">添加相关文章</button>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<nav class="clearfix">
								<ul class="pagination pull-left">
									<li><div class="dataTables_info" id="sample_1_info">共${totalPageNum }
											页,当前为第${nowPage}页</div></li>
								</ul>
								<ul class="pagination pull-right">
									<li><a href="javascript:doPanation(1)">首页</a></li>
									<li><a
										href="javascript:doPanation(${nowPage - 1 < 1 ? 1 : nowPage - 1})"
										aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
									</a></li>
									<c:forEach begin="${nowPage - 5 > 0 ? nowPage - 5 : 1 }"
										end="${nowPage + 5 > totalPageNum ? totalPageNum : nowPage + 5}"
										var="t">
										<li <c:if test="${nowPage == t}">class="act"</c:if>><a
											href="javascript:doPanation(${t})">${t}</a></li>
									</c:forEach>
									<li><a
										href="javascript:doPanation(${nowPage + 1 > totalPageNum ? totalPageNum : nowPage + 1})"
										aria-label="Next"> <span aria-hidden="true">&raquo;</span>
									</a></li>
									<li><a href="javascript:doPanation(${totalPageNum})">末页</a></li>
								</ul>
							</nav>
						</section>
					</div>
				</div>
				<!-- page end-->
			</section>
		</section>
		<!-- /.modal -->
	</section>


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

	<form action="${appRoot}/article/del" method="post" id="deleForm"
		name="deleForm">
		<input type="hidden" name="itemId" value="${itemId }"> <input
			type="hidden" name="positionId" value="${positionId }"> <input
			type="hidden" name="boxeditId" id="boxeditId"> <input
			type="hidden" name="articleType" id="articleType"
			value="${articleType }"> <input type="hidden"
			name="channelType" id="channelType" value="${channelType }">
			<input type="hidden" name="roleId" value="${roleId }">
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
		//选择不同的页数
		function doPanation(number) {
			$("#nowPageNumber").val(number);
			selectDataList();
		}

		function selectDataList() {
			$("#selectCheckMessage").submit();
		}

		//添加文章
		function doAdd(articleType, channelType, roleId, articleId) {
			window.location.href = "${appRoot}/article/addPage?articleType="
					+ articleType + "&channelType=" + channelType + "&itemId="
					+ '${itemId}' + "&positionId=" + '${positionId}'
					+ "&roleId=" + roleId + "&articleId=" + articleId;
		}

		//修改文章
		function edit(id, articleType, channelType, roleId,languageTab) {
			window.location.href = "${appRoot}/article/editPage?id=" + id
					+ "&articleType=" + articleType + "&channelType="
					+ channelType + "&itemId=" + '${itemId}' + "&positionId="
					+ '${positionId}' + "&roleId=" + roleId + "&languageTab=" + languageTab;
		}

		//添加提交
		function submitData() {
			$("#addMessage").submit();
		}

		//更新数据
		function submitUpdateData() {
			$("#editMessage").submit();
		}

		$(function() {
			$('.input-group').hide();
			$('#sample_1_info').hide();
			$('.dataTables_paginate').hide();
			$("#sample_1_length .form-control").hide();
			$("#sample_1_length .js-add").hide();
			$("#sample_1_length .js-ref").hide();
			$("#sample_1_length .js-del").hide();
			addUserRole();
			//隐藏一些东西
			if ('${role.logogram }' != '0') {
				$("#thisRole").hide();
			}
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

		function addUserRole() {
			$.ajax({
				type : 'post',
				data : "",
				url : '${appRoot}/role/getAllList',
				dataType : 'json',
				success : function(data) {
					if (data.msg == 0) {
						var html = '<option value="">全部站点</option>';
						var roleList = data.roleList;
						for (var i = 0; i < roleList.length; i++) {
							if (roleList[i].id == '${roleId}') {
								html += '<option value="'+ roleList[i].id +'"  selected>'
										+ roleList[i].roleName
										+ '</option>'
							} else {
								html += '<option value="'+ roleList[i].id +'">'
										+ roleList[i].roleName
										+ '</option>'
							}
						}
					}
					$("#roleId").html(html);
				}
			});
		}

		function updateSort(obj) {
			var obj = $(obj);
			var id = obj.attr("wzid");
			var articleType = obj.attr("articleType");
			var sort = obj.val();
			var channelType = '${channelType}';
			window.location.href = "${appRoot }/article/save?articleType="
					+ articleType + "&channelType=" + channelType + "&itemId="
					+ '${itemId}' + "&positionId=" + '${positionId}' + "&id="
					+ id + "&sort=" + sort;
		}
	</script>
</body>
</html>

