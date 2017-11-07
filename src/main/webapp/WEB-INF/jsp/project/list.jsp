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

<style>
.pic_upload ul {
	list-style: none;
	overflow: hidden;
	padding: 0;
}

.pic_upload ul li {
	float: left;
	line-height: 70px;
	position: relative;
	margin-right: 10px;
}

.pic_upload ul li i {
	font-size: 20px;
	color: #9D9D9D;
	border: 1px solid #9d9d9d;
	border-radius: 5px;
	width: 120px;
	height: 70px;
	text-align: center;
	line-height: 70px;
	cursor: pointer;
	position: relative;
	z-index: 0;
}

.pic_upload ul li input[type=file] {
	width: 120px;
	height: 70px;
	font-size: 100px;
	position: absolute;
	top: 0;
	left: 0;
	z-index: 2;
	opacity: 0;
	filter: Alpha(opacity = 0);
}

.form-group:first-child {
	margin: 50px auto 20px;
}

.tr {
	text-align: center
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
							<header class="panel-heading">项目列表</header>
							<form action="${appRoot}/pro/projectList" method="post"
								id="selectCheckMessage">
								<!-- 根据用户昵称查询 -->
								<div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<input type="text" class="btn"
										style="width: 500px; border: 1px solid #ddd; text-align: left;"
										placeholder="请输入项目名称" name="projectName" value="${mingzi }"><span>
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
										<th class="hidden-phone">项目名称</th>
										<th class="hidden-phone">地区</th>
										<th class="hidden-phone">项目描述</th>
										<th class="hidden-phone">创建时间</th>
										<th class="hidden-phone">操作</th>
									</tr>
								</thead>
								<tbody id="theTbody">
									<c:forEach items="${selectList}" var="u" varStatus="s">
										<tr class="odd gradeX theTr">
											<td><input type="checkbox" name="box" class="checkboxes"
												value="${u.id}" /></td>
											<td class="hidden-phone">${u.projname}</td>
											<td class="hidden-phone">${u.areaname}</td>
											<td class="hidden-phone">${u.descn}</td>
											<td class="hidden-phone">
												<fmt:formatDate value="${u.ctime}" pattern="yyyy-MM-dd HH:mm:ss"/>
											</td>
											<td class="hidden-phone">
												<button type="button" onclick="editProject('${u.id}','${u.projname}','${u.areaname}','${u.descn}')" 
												class="btn btn-send">修改</button>
												<%-- <button type="button" onclick="sellAllUser('${u.id}')" 
												class="btn btn-send">该项目工作人员</button>
												<button type="button" onclick="seeKeHuMsg('${u.id}')" 
												class="btn btn-send">该项目客户信息</button> --%>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
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
						</section>
					</div>
				</div>
				<!-- page end-->
			</section>
		</section>
		<!-- /.modal -->
	</section>









		<div class="modal fade" id="addProblemType" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="modal-title">添加项目</h4>
					</div>
					<div class="modal-body">
						<form action="${appRoot}/pro/addProject" method="post" class="form-horizontal"  enctype="multipart/form-data"  role="form" id="addMessage" name="itemForm" >
							<input type="hidden" name="editId1" id="editId1">
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">项目名称<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="projname1"
										name="projname"  >
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">地区<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<select class="form-control" name="areaname" id="areaname1">
											<option value="北京">北京</option>
											<option value="上海">上海</option>
											<option value="广州">广州</option>
											<option value="石家庄">石家庄</option>
										<%-- <c:forEach items="${categorys}" var="u" varStatus="s">
											<option value="${u.id }">${u.name }</option>
										</c:forEach> --%>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">项目描述<font
									style="color: red;"></font></label>
								<div class="col-lg-10">
									<textarea rows="5" cols="60" class="form-control"
										id="descn1" name="descn"></textarea>
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
			</div>
		</div>
		
		
		
		<!-- 更新信息 -->
		<div class="modal fade" id="updateProblemType" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="modal-title">添加项目</h4>
					</div>
					<div class="modal-body">
						<form action="${appRoot}/pro/editProject" method="post" class="form-horizontal"  enctype="multipart/form-data"  role="form" id="editMessage" name="itemForm" >
							<input type="hidden" name="editId" id="editId">
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">项目名称<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="projname"
										name="projname" maxlength="4" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">地区<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<select class="form-control" name="areaname" id="areaname">
											<option value="北京">北京</option>
											<option value="上海">上海</option>
											<option value="广州">广州</option>
											<option value="石家庄">石家庄</option>
										<%-- <c:forEach items="${categorys}" var="u" varStatus="s">
											<option value="${u.id }">${u.name }</option>
										</c:forEach> --%>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">项目描述<font
									style="color: red;"></font></label>
								<div class="col-lg-10">
									<textarea rows="5" cols="60" class="form-control"
										id="descn" name="descn"></textarea>
								</div>
							</div>
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button type="button" onclick="submitUpdateData();" class="btn btn-send">提交</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>







		<!-- 该项目中所有的人 -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="modal-title">所属项目列表</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form" id="itemForm" name="itemForm" >
							<input type="hidden" name="editId" id="editId">
							<div class="form-group" id="userProjectMsg">
								
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

	<form action="${appRoot}/pro/del" method="post"
		id="deleForm" name="deleForm">
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
		
	
	//选择不同的页数
	function doPanation(number){
		$("#nowPageNumber").val(number);
		seeAllMsg();
	}
	
	function seeAllMsg(){
		$("#selectCheckMessage").submit();
	}
	
	//查看项目所对应的用户
	function sellAllUser(proId){
		$.ajax({
			type:'post',
			data : {"proId":proId},  
			url:'${appRoot}/pro/userMsg',
			dataType:'json',
			success:function(data){
				var htmlmsg ='';
				console.log(data);
				if(data.msg==100){
					var pro = data.userMessage;
					console.log(pro);
					if(pro.length>0){
						for(var i=0;i<pro.length;i++){
							htmlmsg+='<label class="col-lg-4 control-label pd-r5">'
							+'<button data-dismiss="modal" class="btn btn-send" type="button" '
							+'id="userProjectMsg" style="width:150px;">'+ pro[i].realName +'</button>'
							+'</label>';
						}
						$("#userProjectMsg").html(htmlmsg);
						var $modal = $('#myModal');
						$modal.modal();
					}else{
						windowShow("该项目暂无用户信息","");
					}
				}else{
					windowShow("操作失败","");
				}
			}
		});
	}
	
	
	
	
	
		//修改项目
		function editProject(id,proname,areaname,descn){
			$("#modal-title").val("修改项目");
			$("#projname").val(proname);
			$("#areaname").val(areaname);
			$("#descn").val(descn);
			$("#editId").val(id);
			var $modal = $('#updateProblemType');
			$modal.modal(); 
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
		
		
		

		//添加菜单
		function doAdd() {
			//window.location.href="${appRoot}/anwProduct/addPage";
			var $modal = $('#addProblemType');
			$modal.modal(); 
		}

		//修改菜单
		function doEdit(id) {
			window.location.href="${appRoot}/anwProduct/editPage?id="+id;
		}
		
		
		
		//查看客户信息
		function seeKeHuMsg(id) {
			window.location.href="${appRoot}/pro/prokehu?proId="+id;
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



		function setExpert() {
			$("#checkExpert").submit();
		}

		function Delete() {
			$("#deleForm").submit();
		}
		
		//添加图片
		function addImg(object){
			if (object.files && object.files[0]) {
				var reader = new FileReader();
				reader.onload = function(evt) {
					if(evt.target.result!=""&&evt.target.result!=null){
						$("#imgURL1").attr("src",evt.target.result);
					}else{
						alert("图片上传出错!");
					}
				}
				reader.readAsDataURL(object.files[0]);
			} else {
				//alert(file.value);
			}
		}
		//修改图片
		function editImg(object){
			if (object.files && object.files[0]) {
				var reader = new FileReader();
				reader.onload = function(evt) {
					if(evt.target.result!=""&&evt.target.result!=null){
						$("#imgURL2").attr("src",evt.target.result);
					}else{
						alert("图片上传出错!");
					}
				}
				reader.readAsDataURL(object.files[0]);
			} else {
				//alert(file.value);
			}
		}
		
		//查看商品的评论
		function seeProduct(id){
			window.location.href="${appRoot}/anwProduct/seeComment?id="+id;
		}

	</script>
	<input type="hidden" value="" id="adminId" />
</body>
</html>

