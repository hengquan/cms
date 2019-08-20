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
<link rel="stylesheet" type="text/css"
	href="${appRoot}/static/js/css/layui.css">
<script src="${appRoot}/static/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8"
	src="${appRoot}/static/js/layui.all.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${appRoot}/static/js/layui.js"></script>
<title>${appTitle}</title>
<style type="text/css">
  @font-face {
    font-family: mFont;
    src: url('${appRoot}/static/fonts/MNR8102.ttf');
  }
</style>
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
							<header class="panel-heading">频道列表</header>
							<form action="${appRoot}/channel/getDataList" method="post"
								id="selectCheckMessage">
								<input type="hidden" name="channelType" value="${channeltype }">
								<input type="hidden" name="itemId" value="${itemId }"> <input
									type="hidden" name="positionId" value="${positionId }">
								<!-- 根据用户昵称查询 -->
								<div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<input type="text" class="btn"
										style="width: 500px; border: 1px solid #ddd; text-align: left;"
										placeholder="请输入频道名称" name="channelname"
										value="${channelname }"><span>
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
									<a href="javascript:doAdd();" class="btn mini btn-white"><i
										class="icon-plus"></i></a>
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
										<th class="hidden-phone">封面图片</th>
										<th class="hidden-phone">频道名称</th>
										<th class="hidden-phone">所属站点</th>
										<th class="hidden-phone">所属渠道</th>
										<th class="hidden-phone">所属地区</th>
										<th class="hidden-phone">所属模块</th>
										<th class="hidden-phone">外链地址</th>
										<th class="hidden-phone">排序</th>
										<th class="hidden-phone">创建时间</th>
										<th class="hidden-phone">操作</th>
									</tr>
								</thead>
								<tbody id="theTbody">
									<c:forEach items="${selectList}" var="u" varStatus="s">
										<tr class="odd gradeX theTr">
											<td><input type="checkbox" name="box" class="checkboxes"
												value="${u.id}" /></td>
											<td class="hidden-phone"><c:choose>
													<c:when test="${empty u.picUrl }">
														<img src="${appRoot }/static/img/zanwu1.png"
															style="height: 50px;">
													</c:when>
													<c:otherwise>
														<img src="${u.picUrl }" style="height: 50px;"
															onerror="excptionUrl(this)">
													</c:otherwise>
												</c:choose></td>
											<td class="hidden-phone"><a href="#"
												onclick="doArticleList('${u.id}','${channeltype }','${u.roleId }')">${u.channelname}</a>
											</td>
											<td class="hidden-phone">${u.roleName}</td>
											<td class="hidden-phone"><c:if
													test="${u.channeltype == 0}">暂无</c:if> <c:if
													test="${u.channeltype == 1}">APP</c:if> <c:if
													test="${u.channeltype == 2}">H5</c:if> <c:if
													test="${u.channeltype == 3}">触摸板</c:if> <c:if
													test="${u.channeltype == 4}">APP视频</c:if></td>
											<td class="hidden-phone">${u.areaname}</td>
											<td class="hidden-phone">${u.moduleName}</td>
											<td class="hidden-phone">${u.hrefUrl}</td>
											<td class="hidden-phone"><input type="number"
												class="btn"
												style="width: 100px; border: 1px solid #ddd; text-align: left;"
												value="${u.sort }" wzid="${u.id }"
												onchange="updateSort(this)"></td>
											<td class="hidden-phone"><fmt:formatDate
													value="${u.ctime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td class="hidden-phone">
												<button type="button"
													onclick="edit('${u.id}','${u.channelname}','${u.areaname}','${u.descn}','${u.roleId }','${u.languages }','${u.picUrl }','${u.moduleId }','${u.hrefUrl }','${u.parentId }','${u.channeltype }')"
													class="btn btn-send thisEdit">修改频道</button> <a
												href="javascript:doAddArticle('${u.id }','${channeltype }','${u.roleId }');"
												class="btn btn-send">添加文章</a>
											</td>
										</tr>
										<c:forEach items="${u.channelList}" var="newu" varStatus="s">
											<tr class="odd gradeX theTr">
												<td><input type="checkbox" name="box"
													class="checkboxes" value="${newu.id}" /></td>
												<td class="hidden-phone"><c:choose>
														<c:when test="${empty newu.picUrl }">
															<img src="${appRoot }/static/img/zanwu1.png"
																style="height: 50px;">
														</c:when>
														<c:otherwise>
															<img src="${newu.picUrl }" style="height: 50px;"
																onerror="excptionUrl(this)">
														</c:otherwise>
													</c:choose></td>
												<td class="hidden-phone"><a href="#"
													onclick="doArticleList('${newu.id}','${channeltype }','${newu.roleId }')">----${newu.channelname}</a>
												</td>
												<td class="hidden-phone">${newu.roleName}</td>
												<td class="hidden-phone"><c:if
														test="${newu.channeltype == 0}">暂无</c:if> <c:if
														test="${newu.channeltype == 1}">APP</c:if> <c:if
														test="${newu.channeltype == 2}">H5</c:if> <c:if
														test="${newu.channeltype == 3}">触摸板</c:if> <c:if
														test="${newu.channeltype == 4}">APP视频</c:if></td>
												<td class="hidden-phone">${newu.areaname}</td>
												<td class="hidden-phone">${newu.moduleName}</td>
												<td class="hidden-phone">${newu.hrefUrl}</td>
												<td class="hidden-phone"><input type="number"
													class="btn"
													style="width: 100px; border: 1px solid #ddd; text-align: left;"
													value="${u.sort }" wzid="${u.id }"
													onchange="updateSort(this)"></td>
												<td class="hidden-phone"><fmt:formatDate
														value="${newu.ctime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
												<td class="hidden-phone">
													<button type="button"
														onclick="edit('${newu.id}','${newu.channelname}','${newu.areaname}','${newu.descn}','${newu.roleId }','${newu.languages }','${newu.picUrl }','${newu.moduleId }','${newu.hrefUrl }','${newu.parentId }','${newu.channeltype }')"
														class="btn btn-send thisEdit">修改频道</button> <a
													href="javascript:doAddArticle('${newu.id }','${channeltype }','${newu.roleId }');"
													class="btn btn-send">添加文章</a>
												</td>
											</tr>
										</c:forEach>
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







	<!-- 更新信息 -->
	<div class="modal fade" id="addPage" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="modal-title">添加频道</h4>
				</div>
				<div class="modal-body">
					<form action="${appRoot}/channel/save" method="post"
						class="form-horizontal" enctype="multipart/form-data" role="form"
						id="addMessage" name="itemForm">
						<input type="hidden" name="id" id="editId"> <input
							type="hidden" name="itemId" value="${itemId }"> <input
							type="hidden" name="positionId" value="${positionId }"> <input
							type="hidden" name="languages" id="languages">
						<div class="form-group">
							<label class="col-lg-2 control-label pd-r5">所属渠道<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<select class="form-control" id="selChanneltype" name="channeltype" onchange="putChannelList('')">
									<option value="1"
										<c:if test="${channeltype == 1}">selected</c:if>>APP</option>
									<option value="2"
										<c:if test="${channeltype == 2}">selected</c:if>>H5</option>
									<option value="3"
										<c:if test="${channeltype == 3}">selected</c:if>>触摸板</option>
									<option value="4"
										<c:if test="${channeltype == 4}">selected</c:if>>APP视频</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label pd-r5">所属站点<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<select id="roleId" name="roleId" class="form-control selRoleId"
									onchange="eventHandling(this)"></select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label pd-r5">所属频道<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<select id="parentId" name="parentId" class="form-control">
									<option value="0">暂无</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label pd-r5">默认名称<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="channelname"
									name="channelname">
							</div>
						</div>
						<div id="thisChannelLanguage"></div>
						<div class="form-group">
							<label class="col-lg-2 control-label pd-r5">所属地区<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="areaname"
									name="areaname">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label pd-r5">外链地址<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="hrefUrl"
									name="hrefUrl" placeholder="请输入外链址,仅供前端使用">
							</div>
						</div>
						<div id="selModule"></div>
						<input type="hidden" name="moduleId" id="moduleId">
						<div style="">
							<label class="col-lg-2 control-label pd-r5"
								style="margin-left: -15px;">封面图片<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<div>
									<img class="my-img form-control btn" id="imgDJZS"
										style="float: left; width: 219px; height: 150px; margin-left: -6px; border: 0px; margin-bottom: 14px;"
										onerror="excptionUrl(this)" />
								</div>
								<div id="titleDJZS" style="float: left; margin-top: 122px;">
									<b>上传100*200缩略图,1M以内</b>
								</div>
							</div>
							<input type="hidden" id=picUrl name="picUrl" />
						</div>
						<div style="clear: both"></div>
						<div class="form-group">
							<label class="col-lg-2 control-label pd-r5">频道描述<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<textarea rows="5" cols="60" class="form-control" id="descn"
									name="descn"></textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-lg-offset-2 col-lg-10">
								<button type="button" onclick="submitData();"
									class="btn btn-send">提交</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
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

	<form action="${appRoot}/channel/del" method="post" id="deleForm"
		name="deleForm">
		<input type="hidden" name="itemId" value="${itemId }"> <input
			type="hidden" name="positionId" value="${positionId }"> <input
			type="hidden" name="channeltype" value="${channeltype }"> <input
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

	<%-- <script type="text/javascript"
		src="${appRoot}/static/assets/data-tables/jquery.dataTables.js"></script> --%>
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

		//修改频道
		function edit(id, channelname, areaname, descn, roleId, languages,
				picUrl, moduleId, hrefUrl, parentId,channelType) {
			$("#thisChannelLanguage").html("");
			$("#modal-title").val("修改频道");
			//处理频道类型
			var selHtml = "";
			if (channelType == 1) {
				selHtml = '<option value="1" selected>APP</option>'
						+ '<option value="2">H5</option>'
						+ '<option value="3">触摸板</option>'
						+ '<option value="4">APP视频</option>';
			} else if (channelType == 2) {
				selHtml = '<option value="1">APP</option>'
					+ '<option value="2" selected>H5</option>'
					+ '<option value="3">触摸板</option>'
					+ '<option value="4">APP视频</option>';
			} else if (channelType == 3) {
				selHtml = '<option value="1">APP</option>'
					+ '<option value="2">H5</option>'
					+ '<option value="3" selected>触摸板</option>'
					+ '<option value="4">APP视频</option>';
			} else if (channelType == 4) {
				selHtml = '<option value="1">APP</option>'
					+ '<option value="2">H5</option>'
					+ '<option value="3">触摸板</option>'
					+ '<option value="4" selected>APP视频</option>';
			}
			$("#selChanneltype").html(selHtml);
			//处理其他信息
			$("#channelname").val(channelname);
			$("#areaname").val(areaname);
			$("#descn").val(descn);
			$("#editId").val(id);
			$("#languages").val(languages);
			if (picUrl != null && picUrl != '') {
				$("#imgDJZS").attr("src", picUrl);
			}
			$("#moduleId").val(moduleId);
			$("#hrefUrl").val(hrefUrl);
			//处理父级频道
			putChannelList(parentId);
			//处理多语言
			addLanguage(languages);
			//处理显示
			addRolePage(roleId);
			//处理所属模块
			channelInModule(roleId);
		}

		//更新提交
		function submitData() {
			//组串串json
			var rows = "";
			$("#thisChannelLanguage .form-group").each(
					function(index, obj) {
						var relatedLanguage = $(obj).find(".relatedLanguage")
								.val();
						var relatedTab = $(obj).find(".relatedTab").val();
						var relatedName = $(obj).find(".relatedName").val();
						var row = relatedLanguage + ":" + relatedTab + ":"
								+ relatedName;
						rows += "," + row;
					})
			if (rows != "")
				rows = rows.substr(1);
			$("#languages").val(rows);
			//组所有模块的ID
			var moduleId = "";
			$("#selModule .selModuleId").each(function(index, obj) {
				var object = $(obj);
				if (object.is(':checked')) {
					moduleId += "," + object.val();
				}
			})
			if (moduleId != "")
				moduleId = moduleId.substr(1);
			$("#moduleId").val(moduleId);
			//提交
			$("#addMessage").submit();
		}

		$(function() {
			$('.input-group').hide();
			$('#sample_1_info').hide();
			$('.dataTables_paginate').hide();
			$("#sample_1_length .form-control").hide();
			$("#sample_1_length .js-add").hide();
			$("#sample_1_length .js-ref").hide();
			$("#sample_1_length .js-del").hide();
			//
			addUserRole();
			//隐藏一些东西
			if ('${role.logogram }' != '0') {
				$("#thisRole").hide();
				$(".thisEdit").hide();
			}

		});

		function addUserRole() {
			$
					.ajax({
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

		//添加菜单
		function doAdd() {
			document.getElementById("addMessage").reset();
			$("#editId").val("");
			$("#thisChannelLanguage").html("");
			//处理权限
			addRolePage('');
		}

		//处理该频道属于哪个模块
		function channelInModule(roleId) {
			var moduleType  = $("#selChanneltype option:checked").text();
			$
					.ajax({
						type : 'post',
						data : {
							"roleId" : roleId,
							"moduleType" : moduleType
						},
						url : '${appRoot}/module/getDataByRoleId',
						dataType : 'json',
						success : function(data) {
							var parentIds = $("#moduleId").val();
							var code = data.code;
							if (code == "200") {
								var dataList = data.moduleList;
								console.log(dataList);
								var html = '<div class="form-group">'
										+ '<label class="col-lg-2 control-label pd-r5">所属模块<font style="color: red;"></font></label>'
										+ '<div class="col-lg-10 control-label pd-r5">';
								for (var i = 0; i < dataList.length; i++) {
									var moduleName = dataList[i].moduleName;
									var moduleId = dataList[i].id;
									if (parentIds.indexOf(moduleId) != -1) {
										html += '<input type="checkbox" checked class="selModuleId" value="'+moduleId+'">&nbsp;<b>'
												+ moduleName + '</b>&nbsp;';
									} else {
										html += '<input type="checkbox" class="selModuleId" value="'+moduleId+'">&nbsp;<b>'
												+ moduleName + '</b>&nbsp;';
									}
								}
								html += '</div></div>';
								if (dataList.length > 0)
									$("#selModule").html(html);
							}
						}
					});
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

		function doArticleList(channelId, channelType, roleId) {
			window.location.href = "${appRoot }/article/getDataList?articleType="
					+ channelId
					+ "&channelType="
					+ channelType
					+ "&itemId="
					+ '${itemId}'
					+ "&positionId="
					+ '${positionId}'
					+ "&roleId=" + roleId;
		}

		//添加相关文章
		function doAddArticle(articleType, channelType, roleId) {
			window.location.href = "${appRoot}/article/addPage?articleType="
					+ articleType + "&channelType=" + channelType + "&itemId="
					+ '${itemId}' + "&positionId=" + '${positionId}'
					+ "&roleId=" + roleId;
		}

		//填充权限页面
		function addRolePage(roleId) {
			$
					.ajax({
						type : 'post',
						data : "",
						url : '${appRoot}/role/getAllList',
						dataType : 'json',
						success : function(data) {
							if (data.msg == 0) {
								var html = '<option value="0">--请选择站点--</option>';
								var roleList = data.roleList;
								for (var i = 0; i < roleList.length; i++) {
									if (roleList[i].id == roleId) {
										html += '<option value="'+ roleList[i].id +'" selected>'
												+ roleList[i].roleName
												+ '</option>'
									} else {
										html += '<option value="'+ roleList[i].id +'">'
												+ roleList[i].roleName
												+ '</option>'
									}
								}
								$("#addPage #roleId").html(html);
								var $modal = $('#addPage');
								$modal.modal();
							} else if (data.msg == 1) {
								windowShow("您不是该站点负责人", "");
							} else {
								windowShow("获取权限列表失败", "");
							}
						}
					});
		}

		function eventHandling(obj) {
			$("#thisChannelLanguage").html("");
			$("#selModule").html("");
			var obj = $(obj);
			var roleId = obj.val();
			//处理语言
			selLanguage(roleId);
			//处理所属模块
			channelInModule(roleId);
			//放置频道
			putChannelList('');
		}

		//添加时选择语言
		function selLanguage(roleId) {
			$
					.ajax({
						type : 'post',
						data : {
							"roleId" : roleId
						},
						url : '${appRoot}/role/getLanguageData',
						dataType : 'json',
						success : function(data) {
							var html = '';
							var dataList = data.dataList;
							if (dataList != null) {
								for (var i = 0; i < dataList.length; i++) {
									html += '<div class="form-group">'
											+ '<label class="col-lg-2 control-label pd-r5">'
											+ dataList[i].name
											+ '名称'
											+ '</label>'
											+ '<div class="col-lg-10 message">'
											+ '<input type="hidden" class="form-control relatedLanguage" value="'+ dataList[i].name +'">'
											+ '<input type="hidden" class="form-control relatedTab" value="'+ dataList[i].tab +'">'
											+ '<input type="text" style="font-family: mFont;" class="form-control relatedName">'
											+ '</div>' + '</div>';
								}
								$("#thisChannelLanguage").html(html);
							}
						}
					});
		}
		//修改时选择语言
		function addLanguage(languages) {
			$("#thisChannelLanguage").html("");
			if (languages != "") {
				var languageList = languages.split(",");
				var html = '';
				for (var i = 0; i < languageList.length; i++) {
					var languageMap = languageList[i];
					var result = languageMap.split(":");
					var language = result[0];
					var tab = result[1];
					var name = result[2];
					html += '<div class="form-group">'
							+ '<label class="col-lg-2 control-label pd-r5">'
							+ language
							+ '名称'
							+ '</label>'
							+ '<div class="col-lg-10 message">'
							+ '<input type="hidden" class="form-control relatedLanguage" value="'+ language +'">'
							+ '<input type="hidden" class="form-control relatedTab" value="'+ tab +'">'
							+ '<input type="text" style="font-family: mFont;" class="form-control relatedName" value="'+ name +'">'
							+ '</div>' + '</div>';
				}
				$("#thisChannelLanguage").html(html);
			}
		}

		//layui处理文件上传
		layui.use([ 'form', 'upload' ], function() {
			form = layui.form;
			upload = layui.upload;
			initImgForGroup();
			form.render();
		});

		function initImgForGroup() {
			upload.render({
				elem : '#imgDJZS,#titleDJZS', //绑定元素
				url : '${appRoot}/apply/uploadFile',//上传接口
				size : '1024',
				before : function(obj) {
					console.log(obj);
					//预读本地文件示例，不支持ie8
					obj.preview(function(index, file, result) {
						$('#imgDJZS').attr('src', result); //图片链接（base64）
					});
					$("#picUrl").attr("title", "点击封面图");
				},
				data : {
					"tableName" : "hl_kj",
					"fileType" : "主图片"
				},
				done : function(res) {
					console.log(res);
					var fileName = res.fileName;
					$("#picUrl").val(fileName);
				}
			});
		}

		function excptionUrl(obj) {
			var obj = $(obj);
			obj.attr("src", '${appRoot}/static/img/zanwu1.png');
		}

		function putChannelList(parentId) {
			var selChanneltype = $("#selChanneltype").val();
			var selRoleId = $(".selRoleId").val();
			$
					.ajax({
						type : 'post',
						data : {
							"roleId" : selRoleId,
							"channelType" : selChanneltype,
							"parentId" : "0"
						},
						url : '${appRoot}/channel/getDataByType',
						dataType : 'json',
						success : function(data) {
							var html = '<option value="0">暂无</option>';
							var msg = data.msg;
							if (msg == "0") {
								var dataList = data.dataList;
								if (dataList != null) {
									for (var i = 0; i < dataList.length; i++) {
										if (parentId == dataList[i].id) {
											html += '<option value="'+dataList[i].id+'" selected>'
													+ dataList[i].channelname
													+ '</option>';
										} else {
											html += '<option value="'+dataList[i].id+'">'
													+ dataList[i].channelname
													+ '</option>';
										}
									}
									$("#parentId").html(html);
								}
							}
						}
					});
		}

		function updateSort(obj) {
			var obj = $(obj);
			var id = obj.attr("wzid");
			var sort = obj.val();
			var channelType = '${channeltype}';
			var url = "${appRoot }/channel/save?channeltype=" + channelType
					+ "&itemId=" + '${itemId}' + "&positionId="
					+ '${positionId}' + "&id=" + id + "&sort=" + sort;
			window.location.href = url;
		}
	</script>
</body>
</html>

