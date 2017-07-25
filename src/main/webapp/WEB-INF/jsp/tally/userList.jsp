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
<!-- tree -->
<link rel="stylesheet" type="text/css" href="${appRoot}/static/css/bootstrap.min.css"/>
<%-- <link rel="stylesheet" type="text/css" href="${appRoot}/static/css/style.css"/> --%>
<title>${appTitle}</title>
</head>
<style>
 ul{
 	margin: 0 0 10px 25px;
 }
.tree {
    margin-bottom:20px;
    background-color:#fbfbfb;
    border:1px solid #999;
    -webkit-border-radius:4px;
    -moz-border-radius:4px;
    border-radius:4px;
    -webkit-box-shadow:inset 0 1px 1px rgba(0, 0, 0, 0.05);
    -moz-box-shadow:inset 0 1px 1px rgba(0, 0, 0, 0.05);
    box-shadow:inset 0 1px 1px rgba(0, 0, 0, 0.05)
}
.tree li {
    list-style-type:none;
    margin:0;
    padding:10px 5px 0 5px;
    position:relative; 
}
.tree li::before, .tree li::after {
    content:'';
    left:-20px;
    position:absolute;
    right:auto
}
.tree li::before {
    border-left:1px solid #999;
    bottom:50px;
    height:100%;
    top:0;
    width:1px
}
.tree li::after {
    border-top:1px solid #999;
    height:20px;
    top:25px;
    width:25px
}
.tree li span {
    -moz-border-radius:5px;
    -webkit-border-radius:5px;
    border:1px solid #999;
    border-radius:5px;
    display:inline-block;
    padding:3px 8px;
    text-decoration:none
}
.tree li.parent_li>span {
    cursor:pointer
}
.tree>ul>li::before, .tree>ul>li::after {
    border:0
}
.tree li:last-child::before {
    height:30px
}
.tree li.parent_li>span:hover, .tree li.parent_li>span:hover+ul li span {
    background:#ddd;
    border:1px solid #94a0b4;
    color:#000
}
#rootUL{
    margin-left: 0;
}
.main{
    margin:20px;
}
.treeNav{
    margin-left: 0;
    position: relative;
    width:150px;
    border: 3px solid #4c4c4c;
    font-size: 24px;
}
.treeNav li{
    padding: 10px;
    cursor: pointer;
    border-bottom: 1px solid #1b1b1b;
    background-color: #262d36;
    color:#fff;
}
.tree{
    position: absolute;
    top:50%;
    left:50%;
    transform:translate(-50%,-50%);
    display: none;
    min-width: 400px;
    max-width:60%;
	margin-top:-100px;
}
.tree1{
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
#rootUL{
    overflow-y: auto;
    padding:20px;
}
.title{
    height:50px;
    padding-right: 100px;
    line-height: 50px;
    text-indent: 10px;
    background-color: #333c48;
    font-size: 24px;
    color: #fff;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
.closebtn{
    position: absolute;
    top:10px;
    right:10px;
    border: 0;
    cursor: pointer;
    outline: none;
    width:30px;
    height:30px;
    background-color: transparent;
    z-index: 999;
}
.closebtn:hover::after,.closebtn:hover::before{
    background-color: #888;
}
.closebtn::after,.closebtn::before{
    position: absolute;
    left: 0;
    top: 12px;
    content: '';
    width: 30px;
    height: 3px;
    background-color: #fff;
    transform: rotate(45deg);
    -webkit-transform: rotate(45deg);
    border-radius: 3px;
}
.closebtn::after{
    transform: rotate(-45deg);
    -webkit-transform: rotate(-45deg);
}
.num{
    color:#ff0000;
}
</style>
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
							<header class="panel-heading">推荐统计</header>
								<form action="${appRoot}/user/recommendCount" method="post" id="selectCheckMessage">
									<!-- 选择不同的排行类型 -->
									<input type="hidden" id="selectState" name="selectState" value="${state}">
									<!-- 根据用户昵称查询 -->
									<div style="float: left;position: relative;margin-top: 16px;margin-left:20px;">
											<input type="text" class="btn" style="width:500px;border:1px solid #ddd;text-align:left;" placeholder="请输入需要查询的用户姓名/昵称/电话号码" name="userName" value=""><span>
											<button class="btn sr-btn-imp" style="float: right" onclick="seeAllMsg()">
											<i class="icon-search"></i>
											</button>
										</span>
									</div>
									
									
									<div style="float: left;position: relative;margin-top: 16px;margin-left:20px;">
										<a href="javascript:doRefresh();" class="btn mini btn-white" title="刷新"><i class="icon-refresh"></i></a>
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
							
							<table class="table table-striped border-top" id="sample_1" >
								<thead>
								<tr>
									<th style="width: 8px;"><input type="checkbox" name="box" class="group-checkable" data-set="#sample_1 .checkboxes" value="" /></th>
									<th class="hidden-phone">姓名/昵称</th>
									<th class="hidden-phone">用户类型</th>
									<th class="hidden-phone">性别</th>
									<th class="hidden-phone">电话</th>
									<th class="hidden-phone">积分</th>
									<th class="hidden-phone">总收益</th>
									<th class="hidden-phone">提问次数</th>
									<th class="hidden-phone">回答次数</th>
									<th class="hidden-phone">最佳支招数</th>
									<th class="hidden-phone">幸运支招数</th>
									<th class="hidden-phone">偷听次数</th>
									<th class="hidden-phone">可偷听次数</th>
									<th class="hidden-phone">查看推荐</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${userList}" var="u" varStatus="s">
								<tr class="odd gradeX">
									<td><input type="checkbox" name="box" class="checkboxes" value="${u.openid}" /></td>
									<td class="hidden-phone">
										<c:choose>
										   <c:when test="${u.userName== '' || u.userName== undefined}">  
												${u.nickname}							   	
										   </c:when>
										   <c:otherwise> 
										   		${u.userName}
										   </c:otherwise>
										</c:choose>
									</td>
									<td class="hidden-phone">
										<c:if test="${u.userType==0}">黑名单</c:if>
										<c:if test="${u.userType==1}">普通</c:if>
										<c:if test="${u.userType==2}">专家</c:if>
									</td>
									<td class="hidden-phone">
										<c:if test="${u.sex==1}">男</c:if>
										<c:if test="${u.sex==2}">女</c:if>
									</td>
									<td class="hidden-phone">${u.mobile}</td>
									<td class="hidden-phone">${u.integral}</td>
									<td class="hidden-phone">${u.money}</td>
									<td class="hidden-phone">${u.problemNum}次</td>
									<td class="hidden-phone">${u.answerNum}次</td>
									<td class="hidden-phone">${u.answerNumber}次</td>
									<td class="hidden-phone">${u.luckyNumber}次</td>
									<td class="hidden-phone">${u.eavesdropNum}次</td>
									<td class="hidden-phone">${u.enEavesdropNum}次</td>
									<td class="hidden-phone">
										<a href="javascript:;" onclick="manyMessage('${u.openid}','${u.userName}','${u.countPerson}')" >${u.countPerson}人</a>
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
		

<div class="main">
    <div class="tree">
        <div class="title" id="userTitle"></div>
        <button class="closebtn"></button>
        <ul id="rootUL">
        </ul>
    </div>
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
						<h4 class="modal-title">设置专家警告</h4>
					</div>
					<div class="modal-body">是否确定要将选中用户设置成专家？</div>
					<div class="modal-footer">
						<button data-dismiss="modal" class="btn btn-default" type="button" id="quxiao">取消</button>
						<button class="btn btn-warning" type="button" onclick="setExpert()">确定</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->
	
	<div class="modal fade" id="myModalUpdatePwd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">重置警告</h4>
				</div>
				<div class="modal-body">确定重置管理员密码？</br>重置后密码为:123456</div>
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn btn-default" type="button" id="quxiao">取消</button>
					<button class="btn btn-warning" type="button" onclick="resetPassword()">确定</button>
				</div>
			</div>
		</div>
	</div>
	
	<form action="${appRoot}/user/del" method="post" id="deleForm" name="deleForm">
		<input type="hidden" name="byid" id="byid">
	    <input type="hidden" name="boxeditId" id="boxeditId">
	</form>

	<form action="${appRoot}/user/setExpert" method="post" id="checkExpert" name="checkExpert">
		<input type="hidden" name="setExpertId" id="setExpertId">
	    <input type="hidden" name="setExpertIds" id="setExpertIds">
	</form>

	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>
	<script src="${appRoot}/static/js/jquery.sparkline.js" type="text/javascript"></script>

	<script type="text/javascript" src="${appRoot}/static/assets/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${appRoot}/static/assets/data-tables/DT_bootstrap.js"></script>
	<!--script for this page only-->
	<script src="${appRoot}/static/js/dynamic-table.js"></script>
    <script src="${appRoot}/static/js/dialog_alert.js"></script>
    <!-- tree -->
    <script type="text/javascript" src="${appRoot}/static/js/jquery-1.8.3.min.js"></script>
	<script src="${appRoot}/static/js/tree.js"></script>
	<script type="text/javascript">
	
	
	//选择不同的类型
	function selectState(num){
		$("#selectState").val(num);
		seeAllMsg();
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
	
	//查看更多信息
	function manyMessage(openid,username,count){
		$("#userTitle").html(username+"("+count+")");
		
		$('#rootUL').html("");
        $('.closebtn').fadeIn();
        $(".tree").fadeIn("fast");
		$.ajax({
			type:'post',
			data : {"openid":openid},  
			url:'${appRoot}/user/seeRecommendPerson',
			dataType:'json',
			success:function(data){
				if(data.msg==0){
					tree(data.message);
					myFun();
				}else{
					windowShow("操作失败","");
				}
			}
		});
	}
	
	
	
	$(function(){
		$('.input-group').hide();
		$('#sample_1_info').hide();
		$('.dataTables_paginate').hide();
		$("#sample_1_length .form-control").hide();
		$("#sample_1_length .js-add").hide();
		$("#sample_1_length .js-ref").hide();
		$("#sample_1_length .js-del").hide();
		//分销树型结构
		//tree关闭按钮
        $('.closebtn').click(function(){
            $(this).css("display","none");
            $(this).parent().slideUp("fast");
            $('#rootUL').eq(0).html("");
        })
        
        
        
        
        

	});
	
	
	function doRefresh(){
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
		var flag=checkbox();
		if(flag){
		  var $modal = $('#myModal2');
		  $modal.modal();
		}
	}
	
	
	function Del() {
		$("#deleForm").submit();
	}
	
	
	//设置专家
	function doSelectUser() {
		var flag=checkUserBox();
		if(flag){
		  var $modal = $('#myModal1');
		  $modal.modal();
		}
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
	<input type="hidden" value="" id="adminId"/>
</body>
</html>

