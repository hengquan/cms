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
							<header class="panel-heading">频道列表</header>
							<form action="${appRoot}/channel/getDataList" method="post"
								id="selectCheckMessage">
								<input type="hidden" name="channeltype" value="${channeltype }">
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
								<div style="float: left; position: relative; margin-top: 16px; margin-left: 20px;" id="thisRole">
                  <select name="roleId" id="roleId" class="btn" style="border:1px solid #ddd" onchange="selectDataList()"></select>
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
										<th class="hidden-phone">频道名称</th>
										<th class="hidden-phone">所属站点</th>
										<th class="hidden-phone">所属渠道</th>
										<th class="hidden-phone">所属地区</th>
										<th class="hidden-phone">频道描述</th>
										<th class="hidden-phone">创建时间</th>
										<th class="hidden-phone">操作</th>
									</tr>
								</thead>
								<tbody id="theTbody">
									<c:forEach items="${selectList}" var="u" varStatus="s">
										<tr class="odd gradeX theTr">
											<td><input type="checkbox" name="box" class="checkboxes"
												value="${u.id}" /></td>
											<td class="hidden-phone"><a href="#"
												onclick="doArticleList('${u.id}','${channeltype }')">${u.channelname}</a>
											</td>
											<td class="hidden-phone">${u.roleName}</td>
											<td class="hidden-phone">
											   <c:if test="${u.channeltype == 0}">暂无</c:if>
											   <c:if test="${u.channeltype == 1}">APP</c:if>
											   <c:if test="${u.channeltype == 2}">H5</c:if>
											   <c:if test="${u.channeltype == 3}">触摸板</c:if>
											   <c:if test="${u.channeltype == 4}">APP视频</c:if>
											</td>
											<td class="hidden-phone">${u.areaname}</td>
											<td class="hidden-phone">${u.descn}</td>
											<td class="hidden-phone"><fmt:formatDate
													value="${u.ctime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td class="hidden-phone">
												<button type="button"
													onclick="edit('${u.id}','${u.channelname}','${u.areaname}','${u.descn}','${u.roleId }')"
													class="btn btn-send thisEdit">修改频道</button> <a
												href="javascript:doAddArticle('${u.id }','${channeltype }','${u.roleId }');"
												class="btn btn-send">添加文章</a>
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
					<form action="${appRoot}/channel/add" method="post"
						class="form-horizontal" enctype="multipart/form-data" role="form"
						id="addMessage" name="itemForm">
						<input type="hidden" name="editId1" id="editId1">
						<input type="hidden" name="itemId" value="${itemId }"> <input
							type="hidden" name="positionId" value="${positionId }">
						<div class="form-group">
							<label class="col-lg-2 control-label pd-r5">所属渠道<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<select class="form-control" name="channeltype">
								  <option value="1" <c:if test="${channeltype == 1}">selected</c:if> >APP</option>
								  <option value="2" <c:if test="${channeltype == 2}">selected</c:if> >H5</option>
								  <option value="3" <c:if test="${channeltype == 3}">selected</c:if> >触摸板</option>
								  <option value="4" <c:if test="${channeltype == 4}">selected</c:if> >APP视频</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label pd-r5">频道名称<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="channelname1"
									name="channelname">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label pd-r5">所属站点<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<select id="roleId1" name="roleId" class="form-control"></select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label pd-r5">所属地区<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="areaname1"
									name="areaname">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label pd-r5">频道描述<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<textarea rows="5" cols="60" class="form-control" id="descn1"
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



	<!-- 更新信息 -->
	<div class="modal fade" id="editPage" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="modal-title">修改频道</h4>
				</div>
				<div class="modal-body">
					<form action="${appRoot}/channel/edit" method="post"
						class="form-horizontal" enctype="multipart/form-data" role="form"
						id="editMessage" name="itemForm">
						<input type="hidden" name="editId" id="editId"> 
						<input type="hidden" name="itemId" value="${itemId }"> <input
							type="hidden" name="positionId" value="${positionId }">
						<div class="form-group">
              <label class="col-lg-2 control-label pd-r5">所属渠道<font
                style="color: red;"></font></label>
              <div class="col-lg-10">
                <select class="form-control" name="channeltype">
                  <option value="1" <c:if test="${channeltype == 1}">selected</c:if> >APP</option>
                  <option value="2" <c:if test="${channeltype == 2}">selected</c:if> >H5</option>
                  <option value="3" <c:if test="${channeltype == 3}">selected</c:if> >触摸板</option>
                  <option value="4" <c:if test="${channeltype == 4}">selected</c:if> >APP视频</option>
                </select>
              </div>
            </div>
						<div class="form-group">
							<label class="col-lg-2 control-label pd-r5">频道名称<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="channelname"
									name="channelname">
							</div>
						</div>
						<div class="form-group">
              <label class="col-lg-2 control-label pd-r5">所属站点<font
                style="color: red;"></font></label>
              <div class="col-lg-10">
                <select id="roleId" name="roleId" class="form-control"></select>
              </div>
            </div>
						<div class="form-group">
							<label class="col-lg-2 control-label pd-r5">所属地区<font
								style="color: red;"></font></label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="areaname"
									name="areaname">
							</div>
						</div>
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
								<button type="button" onclick="submitUpdateData();"
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

		//修改频道
		function edit(id, channelname, areaname, descn,roleId) {
			$("#modal-title").val("修改频道");
			$("#channelname").val(channelname);
			$("#areaname").val(areaname);
			$("#descn").val(descn);
			$("#editId").val(id);
			addRolePage(roleId,2);
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
			//
			addUserRole();
			//隐藏一些东西
			if('${role.logogram }' != '0'){
				$("#thisRole").hide();
				$(".thisEdit").hide();
			}
			
		});
		
		function addUserRole(){
      $.ajax({
        type : 'post',
        data : "",
        url : '${appRoot}/role/getAllList',
        dataType : 'json',
        success : function(data) {
          if (data.msg == 0) {
            var html = '<option value="">全部站点</option>';
            var roleList = data.roleList;
            for(var i=0;i<roleList.length;i++){
              if(roleList[i].id == '${roleId}'){
                html += '<option value="'+ roleList[i].id +'"  selected>'+ roleList[i].roleName +'</option>'
              }else{
                html += '<option value="'+ roleList[i].id +'">'+ roleList[i].roleName +'</option>'
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
			addRolePage('',1);
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

		function doArticleList(channelId, channelType) {
			window.location.href = "${appRoot }/article/getDataList?articleType="
					+ channelId
					+ "&channelType="
					+ channelType
					+ "&itemId="
					+ '${itemId}' + "&positionId=" + '${positionId}';
		}
		
		//添加相关文章
		function doAddArticle(articleType,channelType,roleId){
			window.location.href = "${appRoot}/article/addPage?articleType="
		          + articleType + "&channelType=" + channelType + "&itemId="
		          + '${itemId}' + "&positionId=" + '${positionId}' + "&roleId=" + roleId;
		}
		
		//填充权限页面(type:1添加，2修改)
    function addRolePage(roleId,type){
      $.ajax({
        type : 'post',
        data : "",
        url : '${appRoot}/role/getAllList',
        dataType : 'json',
        success : function(data) {
          if (data.msg == 0) {
            var html;
            var roleList = data.roleList;
            for(var i=0;i<roleList.length;i++){
              if(roleList[i].id == roleId){
                html += '<option value="'+ roleList[i].id +'" selected>'+ roleList[i].roleName +'</option>'
              }else{
                html += '<option value="'+ roleList[i].id +'">'+ roleList[i].roleName +'</option>'
              }
            }
            if(type==1){
	            $("#roleId1").html(html);
	            var $modal = $('#addPage');
	            $modal.modal();
            }else{
	            $("#editPage").find("#roleId").html(html);
	            var $modal = $('#editPage');
	            $modal.modal();
            }
          } else if(data.msg == 1){
            windowShow("您不是该站点负责人", "");
          }else{
            windowShow("获取权限列表失败", "");
          }
        }
      });
    }
	</script>
</body>
</html>

