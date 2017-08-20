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
							<header class="panel-heading">用户审核</header>
								<form action="${appRoot}/user/userCheck" method="post" id="selectCheckMessage">
									
									<div style="float: left;position: relative;margin-top: 16px;margin-left:20px;">
										<select class="form-control" style="width: 120px;" id="checkMessage" name="state" onchange="seeAllMsg()">
											<option value="-1" <c:if test="${state==-1}">selected</c:if>>全部</option>
											<option value="0" <c:if test="${state==0}">selected</c:if>>未审核</option>
											<option value="1" <c:if test="${state==1}">selected</c:if>>审核通过</option>
											<option value="2" <c:if test="${state==2}">selected</c:if>>审核不通过</option>
										</select>
									</div>
									<div style="float: left;position: relative;margin-top: 16px;margin-left:20px;">
											<input type="text" class="btn" style="width:500px;border:1px solid #ddd;text-align:left;" placeholder="请输入用户姓名/昵称" name="userName"><span>
											<button class="btn sr-btn-imp" style="float: right" onclick="seeAllMsg()">
											<i class="icon-search"></i>
											</button>
										</span>
									</div>
									
									<%-- <div style="float: right;position: relative;margin-top: 16px;margin-right: 100px;">									
										<select class="form-control" style="width: 120px;" id="pageSize" name="pageSize" onchange="seeAllMsg()">
											<option value="10" <c:if test="${pageSize==10}">selected</c:if>>10</option>
											<option value="25" <c:if test="${pageSize==25}">selected</c:if>>25</option>
											<option value="50" <c:if test="${pageSize==50}">selected</c:if>>50</option>
											<option value="100" <c:if test="${pageSize==100}">selected</c:if>>100</option>
										</select>
									</div>			 --%>						
									<input type="hidden" value="${nowPage}" id="nowPageNumber" name="nowPage">
									<input type="hidden" value="${totalPageNum }">
								</form>
							<table class="table table-striped border-top" id="sample_1">
								<thead>
									<tr>
										<th style="width: 8px;"><input type="checkbox" name="box" class="group-checkable" data-set="#sample_1 .checkboxes" value="" /></th>
										<th class="hidden-phone">微信头像</th>
										<th class="hidden-phone">姓名/昵称</th>
										<th class="hidden-phone">性别</th>
										<th class="hidden-phone">电话</th>
										<th class="hidden-phone">用户类型</th>
										<th class="hidden-phone">所属项目</th>
										<th class="hidden-phone">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${userList}" var="u" varStatus="s">
										<tr class="odd gradeX">
											<td><input type="checkbox" name="box" class="checkboxes" value="${u.id}" /></td>
											<td class="hidden-phone"><img src="${u.headimgurl}" height="50" width=""></td>
											<td class="hidden-phone">
												<c:choose>
												   <c:when test="${u.realname== '' || u.realname== undefined}">  
														${u.nickname}							   	
												   </c:when>
												   <c:otherwise> 
												   		${u.realname}
												   </c:otherwise>
												</c:choose>
											</td>
											<td class="hidden-phone">
												<c:if test="${u.sex==1}">男</c:if>
												<c:if test="${u.sex==2}">女</c:if>
											</td>
											<td class="hidden-phone">${u.mainphonenum}</td>
											<td class="hidden-phone">${u.userRole.role_name}</td>
											<td class="hidden-phone">${u.selfprojauth}</td>
											<td class="hidden-phone">
												<c:if test="${u.isvalidate == 0}">
													<button type="button" onclick="subUserMessage('${u.id}',${u.isvalidate},'${u.userRole.role_name}')" class="btn btn-send">审核</button>
												</c:if>
												<c:if test="${u.isvalidate == 1}">
													<button type="button" onclick="subUserMessage('${u.id}',${u.isvalidate},'${u.userRole.role_name}')" class="btn btn-send">审核通过</button>
												</c:if>
												<c:if test="${u.isvalidate == 2}">
													<button type="button" onclick="subUserMessage('${u.id}',${u.isvalidate},'${u.userRole.role_name}')" class="btn btn-send">审核未通过</button>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						<!-- <div class="panel-footer"> -->
							<%-- <nav class="clearfix">
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
							</nav> --%>
						<!-- </div> -->
						</section>
					</div>
				</div>
				<!-- page end-->
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
	
	
	
	
	
	<div class="modal fade" id="isCheckState" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="modal-title">确认更改审核状态警告</h4>
				</div>
				<div class="modal-body">
					<form action="${appRoot}/anwStock/edit" method="post"
						class="form-horizontal" enctype="multipart/form-data" role="form"
						id="updateMessage" name="itemForm">
						<input type="hidden" name="editId" id="editId">
						<input type="hidden" name="userSelectProjIds" id="userSelectProjIds">
						<input type="hidden" name="yesSubCheckMessage" id="yesSubCheckMessage">
						<div class="form-group">
							<label class="col-lg-3 control-label pd-r5">登录名<font
								style="color: red;"></font></label>
							<div class="col-lg-9">
								<input type="text" class="form-control" name="loginName" id="loginName" maxlength="10">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label pd-r5">手机号<font
								style="color: red;"></font></label>
							<div class="col-lg-9">
								<input type="text" class="form-control" name="phone" id="phone" maxlength="10">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label pd-r5">真实姓名<font
								style="color: red;"></font></label>
							<div class="col-lg-9">
								<input type="text" class="form-control" name="rename" id="rename" maxlength="10">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label pd-r5">角色<font
								style="color: red;"></font></label>
							<div class="col-lg-9">
								<select class="form-control" name="userRole" id="userRole" maxlength="10">
								
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label pd-r5">用户填选项目<font
								style="color: red;"></font></label>
							<div class="col-lg-9">
								<input type="text" class="form-control" name="userSelectProj" id="userSelectProj" maxlength="10">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label pd-r5">审核确认项目<font
								style="color: red;"></font></label>
							<div class="col-lg-9">
								<div class="dropdown">
								  <button class="btn btn-send dropdown-toggle" type="button" maxlength="10" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
								    请选择项目...
								    <span class="caret"></span>
								  </button>
								  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1" id="projectMsg">
								  	
								  </ul>
								</div>
							</div>
						</div>
						<hr/>
						<div class="form-group">
							<div class="col-lg-offset-2 col-lg-10">
								<button type="button" onclick="subUserStateMessage('1')" class="btn btn-send">审核通过</button>
								<button type="button" onclick="subUserStateMessage('2')" class="btn btn-send">审核不通过</button>
								<button data-dismiss="modal" class="btn btn-default" type="button" id="quxiao">取消</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	

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
		
		
	<div class="modal fade" id="seeOneCheckMessage" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="modal-title">举报信息</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form" id="itemForm" name="itemForm" >
							<input type="hidden" name="editId" id="editId">
							<div class="form-group">
								<!-- <label class="col-lg-2 control-label pd-r5">文字消息<font style="color:red;">*</font></label> -->
								<div class="col-lg-12">
									<textarea rows="5" cols="60" class="form-control" id="checkTextContent">
									
									</textarea>
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
		
	<form action="${appRoot}/user/delCheckMessage" method="post" id="deleForm" name="deleForm">
		<input type="hidden" name="byid" id="byid">
	    <input type="hidden" name="boxeditId" id="boxeditId">
	</form>

	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>
	<script src="${appRoot}/static/js/jquery.sparkline.js" type="text/javascript"></script>

	<script type="text/javascript" src="${appRoot}/static/assets/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${appRoot}/static/assets/data-tables/DT_bootstrap.js"></script>
	<!--script for this page only-->
	<script src="${appRoot}/static/js/zzk-shenhe.js"></script>
    <script src="${appRoot}/static/js/dialog_alert.js"></script>
	<script type="text/javascript">
	
	
	//查看该用户所有对应的项目
	function sellAllProject(userId){
		$.ajax({
			type:'post',
			data : {"userId":userId},  
			url:'${appRoot}/user/proJect',
			dataType:'json',
			success:function(data){
				var htmlmsg ='';
				console.log(data);
				if(data.msg==100){
					var pro = data.projectMessage;
					console.log(pro);
					if(pro.length>0){
						for(var i=0;i<pro.length;i++){
							htmlmsg+='<label class="col-lg-4 control-label pd-r5">'
							+'<button data-dismiss="modal" class="btn btn-send" type="button" '
							+'id="userProjectMsg" style="width:150px;">'+ pro[i].projName +'</button>'
							+'</label>';
						}
						$("#userProjectMsg").html(htmlmsg);
						var $modal = $('#myModal');
						$modal.modal();
					}else{
						windowShow("该用户暂无项目","");
					}
				}else{
					windowShow("操作失败","");
				}
			}
		});
	}
	
	
	
	$(function(){
		$('.input-group').hide();
		$("#sample_1_length .form-control").hide();
		$("#sample_1_length .js-add").hide();
		$("#sample_1_length .js-ref").hide();
		$("#sample_1_length .js-del").hide();
		
	});
	
	//查看举报信息
	function seeCheckMessage(obj){
		//清空
		$("checkTextContent").html("");
		
		var obj = $(obj);
		var msg = obj.attr("message");
		$("#checkTextContent").html(msg);
		var $modal = $('#seeOneCheckMessage');
		$modal.modal();
	}
	
	//选择不同的页数
	function doPanation(number){
		$("#nowPageNumber").val(number);
		seeAllMsg();
	}
	
	//根据选择查看信息
	function seeAllMsg(){
		$("#selectCheckMessage").submit();
	}
	
	
	
	
	
	//是否提交审核结果
	function subUserMessage(id,isPass,rolename){
		if(isPass!=0){
			windowShow("该用户已经过审核，请勿重复操作！","");
			return;
		}
		//获取待审核人的信息，所有项目信息，所有权限信息
		$.ajax({
			type:'post',
			data : {"id":id},  
			url:'${appRoot}/user/getRoleAndProjectMsg',
			dataType:'json',
			success:function(data){
				console.log(data);
				if(data.msg == 100){
					var roles = data.roles;
					var projects = data.projects;
					console.log(projects);
					var projectHtml = '';
					for(var i = 0 ;i<projects.length;i++){
						projectHtml += '<li><a href="#"><input type="checkbox" name="projbox" value="'+projects[i].id+'">'+ projects[i].projname +'</a></li>';
					}
					$("#projectMsg").html(projectHtml);
					var userRoleHtml = '';
					for(var i = 0 ;i<roles.length;i++){
						if(rolename == roles[i].roleName){
							userRoleHtml += '<option value="'+ roles[i].id +'" selected>'+ roles[i].roleName +'</option>';
						}else{
							userRoleHtml += '<option value="'+ roles[i].id +'">'+ roles[i].roleName +'</option>';
						}
					}
					$("#userRole").html(userRoleHtml);
					//赋值
					$("#loginName").val(data.loginname);
					$("#phone").val(data.mainphonenum);
					$("#rename").val(data.realname);
					$("#userSelectProj").val(data.projNames);
					$("#yesSubCheckMessage").val(id);
					var $modal = $('#isCheckState');
					$modal.modal();
				}else{
					windowShow("提交失败","");
				}
			}
		});
	}
	
	//设置审核的状态---确认提交
	function subUserStateMessage(state){
		//权限ID
		var userRole = $("#userRole").val();
		//用户审核所选定的项目列表
		var str = document.getElementsByName("projbox");
		var objarray = str.length;
		var checkProjIds = "";
		var jy = false;
		for (i = 0; i < objarray; i++) {
			if (str[i].checked == true) {
				jy = true;
				checkProjIds += str[i].value + ",";
			}
		}
		//state  1 审核通过   2审核不通过
		var checkedId = $("#yesSubCheckMessage").val();
		//需要传递的数据
		var datas = {
				"checkedId":checkedId,
				"state":state,
				"userRole":userRole,
				"checkProjIds":checkProjIds
		}
		$.ajax({
			type:'post',
			data : datas,  
			url:'${appRoot}/user/checkUser',
			dataType:'json',
			success:function(data){
				if(data.msg == 100){
					windowShow("提交成功","");
					seeAllMsg();
				}else{
					windowShow("提交失败","");
				}
			}
		});
		
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
		var flag=checkbox();
		if(flag){
		  var $modal = $('#myModal2');
		  $modal.modal();
		}
	}

	//查看举报信息
	function seeMessage(){
		$('#jubaoMessage').modal();
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
	
	function doRefresh(){
		 location.reload();
	}
	</script>
	<input type="hidden" value="" id="adminId"/>
</body>
</html>
