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

.panel-heading{
	font-weight:normal;
}
.selectItem {
  font-weight:normal;
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
							<form action="${appRoot}/customer/integratedQuery" method="post"
								id="selectCheckMessage" >
								<div class="panel-heading" style="height: 50px;">
									<div class="col-lg-2" style="line-height:37px">综合查询</div>
									<!-- 根据用户昵称查询 -->
									<div
										style="float: left; position: relative;">
										<input type="text" class="btn"
											style="width: 500px; border: 1px solid #ddd; text-align: left;"
											placeholder="请输入客户姓名" name="custName" value="${name }"><span>
											<button class="btn sr-btn-imp" style="float: right"
												onclick="seeAllMsg()">
												<i class="icon-search"></i>
											</button>
										</span>
									</div>
									<!-- <div class="dropdown">
									  <button class="btn mini btn-white" type="button" id="dropdownMenu1" 
									  	data-toggle="dropdown" onclick="gaoji()" aria-haspopup="true" aria-expanded="true" style="margin-left:20px;">
									    	高级查询
									    <span class="caret"></span>
									  </button>
									</div>   -->
									
									<a href="javascript:gaoji();" style="margin-left:20px;" class="btn mini btn-white" id="gaoji">高级查询</a>
									
								    <div class="modal-dialog modal-lg" id="gaojitiaojian"  style="width:100%;margin-top:-23px;">
								    <div class="modal-content" style="border-color:#000;border-bottom:2px solid;border-left:2px solid;border-right:2px solid">
								   	
								   	<label class='selectItem'>
									   	<label style="margin-left:20px;">首访接待时间:</label>
										<input type="date" name="record01Begin" class="btn mini btn-white" value="${record01Begin }">至
										<input type="date" name="record01End" class="btn mini btn-white" value="${record01End }">
								   	</label>
									
									<label class='selectItem'>
									   	<label style="margin-left:100px;">复访接待时间:</label>
										<input type="date" name="record02Begin" class="btn mini btn-white" value="${record02Begin }">至
										<input type="date" name="record02End" class="btn mini btn-white" value="${record02End }">
									</label><br>
									
									<label class='selectItem'>
									   	<label style="margin-left:20px;">成交接待时间:</label>
										<input type="date" name="record03Begin" class="btn mini btn-white" value="${record03Begin }">至
										<input type="date" name="record03End" class="btn mini btn-white" value="${record03End }">
									</label><br>
								
								   	<label class='selectItem'>
								   		<input type="hidden" id="agegroups" name="agegroups" />
									   	<label style="margin-left:20px;">年龄段:</label>
									   	<input type="checkbox" name="agegroup" <c:if test="${fn:contains(agegroup,'25岁以下') }">checked</c:if> value="25岁以下"/>25岁以下 
									   	<input type="checkbox" name="agegroup" <c:if test="${fn:contains(agegroup,'26~35岁') }">checked</c:if> value="26~35岁">26~35岁
									   	<input type="checkbox" name="agegroup" <c:if test="${fn:contains(agegroup,'36~45岁')}">checked</c:if> value="36~45岁">36~45岁
									   	<input type="checkbox" name="agegroup" <c:if test="${fn:contains(agegroup,'46~55岁') }">checked</c:if> value="46~55岁">46~55岁
									   	<input type="checkbox" name="agegroup" <c:if test="${fn:contains(agegroup,'56岁以上') }">checked</c:if> value="56岁以上">56岁以上
									   	<input type="checkbox" name="agegroup" <c:if test="${fn:contains(agegroup,'无法了解') }">checked</c:if> value="无法了解">无法了解
								   	</label><br>
								   	
								
								   	<label class='selectItem'>
									   	<input type="hidden" name="knowways" id="knowways">
								   		<label style="margin-left:20px;">认知渠道:</label>
									   	<input type="checkbox" name="knowway" <c:if test="${fn:contains(knowway,'户外广告牌') }">checked</c:if> value="户外广告牌">户外广告牌
									   	<input type="checkbox" name="knowway" <c:if test="${fn:contains(knowway,'网络') }">checked</c:if> value="网络">网络
									   	<input type="checkbox" name="knowway" <c:if test="${fn:contains(knowway,'短信') }">checked</c:if> value="短信">短信
									   	<input type="checkbox" name="knowway" <c:if test="${fn:contains(knowway,'纸媒') }">checked</c:if> value="纸媒">纸媒
									   	<input type="checkbox" name="knowway" <c:if test="${fn:contains(knowway,'路过') }">checked</c:if> value="路过">路过
									   	<input type="checkbox" name="knowway" <c:if test="${fn:contains(knowway,'朋友介绍') }">checked</c:if> value="朋友介绍">朋友介绍
									   	<input type="checkbox" name="knowway" <c:if test="${fn:contains(knowway,'渠道介绍') }">checked</c:if> value="渠道介绍">渠道介绍
									   	<input type="checkbox" name="knowway" <c:if test="${fn:contains(knowway,'巡展') }">checked</c:if> value="巡展">巡展
									   	<input type="checkbox" name="knowway" <c:if test="${fn:contains(knowway,'广播') }">checked</c:if> value="广播">广播
									   	<input type="checkbox" name="knowway" <c:if test="${fn:contains(knowway,'电CALL') }">checked</c:if> value="电CALL">电CALL
									   	<input type="checkbox" name="knowway" <c:if test="${fn:contains(knowway,'直投') }">checked</c:if> value="直投">直投
									   	<input type="checkbox" name="knowway" <c:if test="${fn:contains(knowway,'活动') }">checked</c:if> value="活动">活动
									   	<input type="checkbox" name="knowway" <c:if test="${fn:contains(knowway,'DM单') }">checked</c:if> value="DM单">DM单
									   	<input type="checkbox" name="knowway" <c:if test="${fn:contains(knowway,'外联') }">checked</c:if> value="外联">外联
									   	<input type="checkbox" name="knowway" <c:if test="${fn:contains(knowway,'老带新') }">checked</c:if> value="老带新">老带新
									   	<input type="checkbox" name="knowway" <c:if test="${fn:contains(knowway,'其他') }">checked</c:if> value="其他">其他
									   	<input type="checkbox" name="knowway" <c:if test="${fn:contains(knowway,'无法了解') }">checked</c:if> value="无法了解">无法了解
									</label><br>
								   	
								   	<label class='selectItem'>
								   		<input type="hidden" name="liveacreages" id="liveacreages">
									   	<label style="margin-left:20px;">房屋面积:</label>
									   	<input type="checkbox" name="liveacreage" <c:if test="${fn:contains(liveacreage,'200㎡以下') }">checked</c:if> value="200㎡以下">200㎡以下
									   	<input type="checkbox" name="liveacreage" <c:if test="${fn:contains(liveacreage,'200~300㎡') }">checked</c:if> value="200~300㎡">200~300㎡
									   	<input type="checkbox" name="liveacreage" <c:if test="${fn:contains(liveacreage,'300~400㎡' )}">checked</c:if> value="300~400㎡">300~400㎡
									   	<input type="checkbox" name="liveacreage" <c:if test="${fn:contains(liveacreage,'400~500㎡') }">checked</c:if> value="400~500㎡">400~500㎡
									   	<input type="checkbox" name="liveacreage" <c:if test="${fn:contains(liveacreage,'500~600㎡') }">checked</c:if> value="500~600㎡">500~600㎡
									   	<input type="checkbox" name="liveacreage" <c:if test="${fn:contains(liveacreage,'600~700㎡') }">checked</c:if> value="600~700㎡">600~700㎡
									   	<input type="checkbox" name="liveacreage" <c:if test="${fn:contains(liveacreage,'700~800㎡') }">checked</c:if> value="700~800㎡">700~800㎡
									   	<input type="checkbox" name="liveacreage" <c:if test="${fn:contains(liveacreage,'800以上') }">checked</c:if> value="800以上">800以上
								   	</label><br>
								   	
									<label class='selectItem'>
										<input type="hidden" name="pricesections" id="pricesections">
									   	<label style="margin-left:20px;">接受价格区段:</label>
									   	<input type="checkbox" name="pricesection" <c:if test="${fn:contains(pricesection,'500万以下') }">checked</c:if> value="500万以下">500万以下
									   	<input type="checkbox" name="pricesection" <c:if test="${fn:contains(pricesection,'500~1000万') }">checked</c:if> value="500~1000万">500~1000万
									   	<input type="checkbox" name="pricesection" <c:if test="${fn:contains(pricesection,'1000~1500万') }">checked</c:if> value="1000~1500万">1000~1500万
									   	<input type="checkbox" name="pricesection" <c:if test="${fn:contains(pricesection,'1500~2000万' )}">checked</c:if> value="1500~2000万">1500~2000万
									   	<input type="checkbox" name="pricesection" <c:if test="${fn:contains(pricesection,'2000~3000万') }">checked</c:if> value="2000~3000万">2000~3000万
									   	<input type="checkbox" name="pricesection" <c:if test="${fn:contains(pricesection,'3000~4000万') }">checked</c:if> value="3000~4000万">3000~4000万
									   	<input type="checkbox" name="pricesection" <c:if test="${fn:contains(pricesection,'4000~5000万') }">checked</c:if> value="4000~5000万">4000~5000万
									   	<input type="checkbox" name="pricesection" <c:if test="${fn:contains(pricesection,'5000~6000万') }">checked</c:if> value="5000~6000万">5000~6000万
									   	<input type="checkbox" name="pricesection" <c:if test="${fn:contains(pricesection,'6000~8000万') }">checked</c:if> value="6000~8000万">6000~8000万
									   	<input type="checkbox" name="pricesection" <c:if test="${fn:contains(pricesection,'8000万以上') }">checked</c:if> value="8000万以上">8000万以上
									</label><br>								   	

									<label class='selectItem'>
										<input type="hidden" name="custscores" id="custscores">
									   	<label style="margin-left:20px;">客户评级:</label>
									   	<input type="checkbox" name="custscore" <c:if test="${fn:contains(custscore,'A') }">checked</c:if>  value="A">A
									   	<input type="checkbox" name="custscore" <c:if test="${fn:contains(custscore,'B') }">checked</c:if>  value="B">B
									   	<input type="checkbox" name="custscore" <c:if test="${fn:contains(custscore,'C') }">checked</c:if>  value="C">C
									   	<input type="checkbox" name="custscore" <c:if test="${fn:contains(custscore,'D') }">checked</c:if>  value="D">D
									</label>								   	
								    </div><!-- /.modal-content -->
								    </div>
								</div>
								<!-- 分页信息 -->
								<input type="hidden" value="${nowPage}" id="nowPageNumber"
									name="nowPage"> <input type="hidden"
									value="${totalPageNum }">
							</form>

							<div style="clear: both"></div>

							<table class="table table-striped border-top" >
								<thead>
									<tr>
										<th style="width: 8px;">
										</th>
										<th class="hidden-phone" style="line-height:37px">客户姓名</th>
										<th class="hidden-phone" style="line-height:37px">客户电话</th>
										<th class="hidden-phone" style="line-height:37px">性别</th>
										<th class="hidden-phone" style="line-height:37px">项目名称</th>
										<th class="hidden-phone" style="line-height:37px">到访时间</th>
										<th class="hidden-phone" style="line-height:37px">顾问姓名</th>
										<th class="hidden-phone">
											来访总数(次)
											<a href="javascript:doRefresh();" style="margin-left:2px;" class="btn mini btn-white"><i
												class="icon-refresh"></i></a>
											<a href="javascript:downloadExcel();" class="btn mini btn-white"><i
												class="icon-download-alt"></i></a>
										</th>
										
									</tr>
								</thead>
								<tbody id="theTbody">
									<c:forEach items="${userMsg}" var="u" varStatus="s">
										<tr class="odd gradeX theTr">
											<td><input type="checkbox" name="box" class="checkboxes"
												value="${u.id}" /></td>
											<td class="hidden-phone">${u.custName}</td>
											<td class="hidden-phone">${u.phoneNum}</td>
											<td class="hidden-phone">${u.custSex}</td>
											<td class="hidden-phone">${u.projName}</td>
											<td class="hidden-phone">
												<fmt:formatDate value="${u.recepTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
											</td>
											<td class="hidden-phone">${u.realName}</td>
											<td class="hidden-phone">${u.total}次</td>
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
						<h4 class="modal-title" id="modal-title">转移客户信息</h4>
					</div>
					<div class="modal-body">
						<form action="${appRoot}/customer/transferCustMsg" method="post" class="form-horizontal"  enctype="multipart/form-data"  role="form" id="addMessage" name="itemForm" >
							<input type="hidden" name="projId" id="projId">
							<input type="hidden" name="custId" id="custId">
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">请选择用户<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<select class="form-control" name="userId" id="userData">
										
									</select>
								</div>
							</div>
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button type="submit" class="btn btn-send">确定</button>
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

	<form action="${appRoot}/anwProduct/del" method="post"
		id="deleForm" name="deleForm">
		<input type="hidden" name="byid" id="byid"> <input
			type="hidden" name="boxeditId" id="boxeditId">
	</form>
	
	<form action="${appRoot}/customer/downLoadExcel" method="post"
		id="downLoadExcel" name="downLoadExcel">
		<input type="hidden" name="path" id="excelPath">
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
	//下载EXCEL
	function downloadExcel(){
		//年龄段
		var str = document.getElementsByName("agegroup");
		var objarray = str.length;
		var agegroup = "";
		for (i = 0; i < objarray; i++) {
			if (str[i].checked == true) {
				agegroup += str[i].value + ",";
			}
		}
		$("#agegroups").val(agegroup);
		//认知渠道
		var str = document.getElementsByName("knowway");
		var objarray = str.length;
		var knowway = "";
		for (i = 0; i < objarray; i++) {
			if (str[i].checked == true) {
				jy = true;
				knowway += str[i].value + ",";
			}
		}
		$("#knowways").val(knowway);
		//房屋面积
		var str = document.getElementsByName("liveacreage");
		var objarray = str.length;
		var liveacreage = "";
		for (i = 0; i < objarray; i++) {
			if (str[i].checked == true) {
				jy = true;
				liveacreage += str[i].value + ",";
			}
		}
		$("#liveacreages").val(liveacreage);
		//接受价格区段
		var str = document.getElementsByName("pricesection");
		var objarray = str.length;
		var pricesection = "";
		for (i = 0; i < objarray; i++) {
			if (str[i].checked == true) {
				jy = true;
				pricesection += str[i].value + ",";
			}
		}
		$("#pricesections").val(pricesection);
		//客户评级
		var str = document.getElementsByName("custscore");
		var objarray = str.length;
		var custscore = "";
		for (i = 0; i < objarray; i++) {
			if (str[i].checked == true) {
				jy = true;
				custscore += str[i].value + ",";
			}
		}
		$("#custscores").val(custscore);
		var datamsg = $("#selectCheckMessage").serialize();
		$.ajax({
			type:'post',
			data: datamsg, 
			url:'${appRoot}/customer/customerMsgExcel',
			dataType:'json',
			success:function(data){
				var path = data.path;
				$("#excelPath").val(path);
				$("#downLoadExcel").submit();
				//window.location.href="${appRoot}/customer/downLoadExcel?path="+path;
			}
		});
	}
	
	//查看所有已作废顾问下的客户信息
	function seeCancellationUserMsg(){
		var isValidate = $("#isValidate").val();
		if(isValidate==undefined||isValidate==null||isValidate==""){
			$("#isValidate").val(1);
			seeAllMsg();
		}else{
			$("#isValidate").val(null);
			var b = $("#isValidate").val();
			seeAllMsg();
		}
	}
	
	
	//修改客户的操作权限用户单个
	function editJurisdiction(projId,custId){
		$.ajax({
			type:'post',
			data : {"projId":projId},  
			url:'${appRoot}/customer/getUserList',
			dataType:'json',
			success:function(data){
				console.log(data);
				if(data.msg == 100){
					var htmldata = "";
					var usermsg = data.userData;
					for(var i=0;i<usermsg.length;i++){
						htmldata += '<option value="'+ usermsg[i].id +'">'+ usermsg[i].realname +'</option>';
					}
					$("#userData").html(htmldata);
					//项目id
					var projId = data.projId;
					$("#projId").val(projId);
					//原权限人
					$("#custId").val(custId);
					var $modal = $('#addProblemType');
					$modal.modal();
				}else{
					windowShow("获取用户列表失败","");
				}
			}
		});
		
	}
	
	
	//多选客户信息转移用户
	function selectUsers(){
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
			windowShow("请选择要转移的客户！")
			$('#quxiao').click();
			return false;
		} 
		//调用接口
		$.ajax({
			type:'post',
			data : {"userCustIds":chestr},  
			url:'${appRoot}/customer/getUserLists',
			dataType:'json',
			success:function(data){
				console.log(data);
				if(data.msg == 100){
					var htmldata = "";
					var usermsg = data.userData;
					for(var i=0;i<usermsg.length;i++){
						htmldata += '<option value="'+ usermsg[i].id +'">'+ usermsg[i].realname +'</option>';
					}
					$("#userData").html(htmldata);
					//项目id
					var projId = data.projId;
					$("#projId").val(projId);
					//原权限人
					var custId = data.custId;
					$("#custId").val(custId);
					var $modal = $('#addProblemType');
					$modal.modal();
				}else if(data.msg == 105){
					windowShow("您所选择的用户没有在同一个项目","");
				}else{
					windowShow("获取用户列表失败","");
				}
			}
		});
	}
	
	
	
	
	
	//选择不同的页数
	function doPanation(number){
		//年龄段
		var str = document.getElementsByName("agegroup");
		var objarray = str.length;
		var agegroup = "";
		for (i = 0; i < objarray; i++) {
			if (str[i].checked == true) {
				agegroup += str[i].value + ",";
			}
		}
		$("#agegroups").val(agegroup);
		//认知渠道
		var str = document.getElementsByName("knowway");
		var objarray = str.length;
		var knowway = "";
		for (i = 0; i < objarray; i++) {
			if (str[i].checked == true) {
				jy = true;
				knowway += str[i].value + ",";
			}
		}
		$("#knowways").val(knowway);
		//房屋面积
		var str = document.getElementsByName("liveacreage");
		var objarray = str.length;
		var liveacreage = "";
		for (i = 0; i < objarray; i++) {
			if (str[i].checked == true) {
				jy = true;
				liveacreage += str[i].value + ",";
			}
		}
		$("#liveacreages").val(liveacreage);
		//接受价格区段
		var str = document.getElementsByName("pricesection");
		var objarray = str.length;
		var pricesection = "";
		for (i = 0; i < objarray; i++) {
			if (str[i].checked == true) {
				jy = true;
				pricesection += str[i].value + ",";
			}
		}
		$("#pricesections").val(pricesection);
		//客户评级
		var str = document.getElementsByName("custscore");
		var objarray = str.length;
		var custscore = "";
		for (i = 0; i < objarray; i++) {
			if (str[i].checked == true) {
				jy = true;
				custscore += str[i].value + ",";
			}
		}
		$("#custscores").val(custscore);
		$("#nowPageNumber").val(number);
		seeAllMsg();
	}
	

	$(function() {
		$("#gaojitiaojian").hide();
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
	
	
	//根据选择查看信息
	function seeAllMsg() {
		//年龄段
		var str = document.getElementsByName("agegroup");
		var objarray = str.length;
		var agegroup = "";
		for (i = 0; i < objarray; i++) {
			if (str[i].checked == true) {
				agegroup += str[i].value + ",";
			}
		}
		$("#agegroups").val(agegroup);
		//认知渠道
		var str = document.getElementsByName("knowway");
		var objarray = str.length;
		var knowway = "";
		for (i = 0; i < objarray; i++) {
			if (str[i].checked == true) {
				jy = true;
				knowway += str[i].value + ",";
			}
		}
		$("#knowways").val(knowway);
		//房屋面积
		var str = document.getElementsByName("liveacreage");
		var objarray = str.length;
		var liveacreage = "";
		for (i = 0; i < objarray; i++) {
			if (str[i].checked == true) {
				jy = true;
				liveacreage += str[i].value + ",";
			}
		}
		$("#liveacreages").val(liveacreage);
		//接受价格区段
		var str = document.getElementsByName("pricesection");
		var objarray = str.length;
		var pricesection = "";
		for (i = 0; i < objarray; i++) {
			if (str[i].checked == true) {
				jy = true;
				pricesection += str[i].value + ",";
			}
		}
		$("#pricesections").val(pricesection);
		//客户评级
		var str = document.getElementsByName("custscore");
		var objarray = str.length;
		var custscore = "";
		for (i = 0; i < objarray; i++) {
			if (str[i].checked == true) {
				jy = true;
				custscore += str[i].value + ",";
			}
		}
		$("#custscores").val(custscore);
		
		$("#selectCheckMessage").submit();
	}
	

	//添加菜单
	function doAdd() {
		window.location.href="${appRoot}/anwProduct/addPage";
		/* var $modal = $('#addProblemType');
		$modal.modal(); */
	}

	//修改菜单
	function doEdit(id) {
		window.location.href="${appRoot}/anwProduct/editPage?id="+id;
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
		

	
	function gaoji(){
		var temp=$("#gaojitiaojian").is(":visible");//是否隐藏
		if(temp){
			$("#gaojitiaojian").hide();
		}else{
			$("#gaojitiaojian").show();
		}
		
	}
	</script>
	<input type="hidden" value="" id="adminId" />
</body>
</html>

