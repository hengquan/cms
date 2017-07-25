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
<link href="${appRoot}/static/css/lq.datetimepick.css" rel="stylesheet"
	type="text/css" />
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
					<div class="col-sm-22">
						<section class="panel">
							<header class="panel-heading" style="height:50px;">
							
							<div class="col-lg-1">专家信息</div>
							<a href="javascript:history.go(-1)" class="btn btn-send mini btn-white col-lg-1 pull-right" style="align:right;"><i class="glyphicon glyphicon-arrow-left"></i></a>
							
							</header>
						</section>
					</div>
				</div>
				
				
				<form enctype="multipart/form-data" method="post" action="${appRoot}/expert/addAndUpdate" id="videotypeForm">
					<!-- 专家用户的openid -->
					<input type="hidden" value="${openid}" name="openid" >
					
					<div class="row">
						<label class="col-sm-2  control-label tr">姓名/昵称<font
							style="color: red;">*</font></label>
						<div class="col-sm-3">
							<input type="text" class="form-control" 
								name="itemName" placeholder="姓名/昵称" disabled="disabled" value="${user.nickname}">
						</div>
						<label class="col-sm-2  control-label tr">性别<font
							style="color: red;">*</font></label>
						<div class="col-sm-3">
							<input type="text" class="form-control" 
								name="itemName" placeholder="性别" disabled="disabled" value="${user.sex ==1?'男':'女'}">
						</div>
					</div>
					
					<div class="row" style="margin-top:20px;">
						<label class="col-sm-2  control-label tr">电话<font
							style="color: red;">*</font></label>
						<div class="col-sm-3">
							<input type="text" class="form-control" 
								name="itemName" placeholder="电话" disabled="disabled" value="${user.mobile}">
						</div>
					</div>
					
					
					<div class="row" style="margin-top:20px;">
						<label class="col-sm-2  control-label tr">专家分享标题<font
							style="color: red;"></font></label>
						<div class="col-sm-3">
							<input type="text" class="form-control" 
								name="shareTitle" placeholder="请输入专家分享标题" value="${user.expertShareTitle}">
						</div>
						
						<label class="col-sm-2  control-label tr">优惠名称<font
							style="color: red;"></font></label>
						<div class="col-sm-3">
							<input type="text" class="form-control" 
								name="favorableName" placeholder="请输入优惠活动名称" value="${user.favorableName}">
						</div>
					</div>
					
					<div class="row" style="margin-top:20px;">
						<label class="col-sm-2  control-label tr">优惠-电话咨询（元/次）<font
							style="color: red;">*</font></label>
						<div class="col-sm-3">
							<input type="number" class="form-control" 
								name="phone" placeholder="请输入电话咨询所需费用" value="${ZzkExpertPrice[0].price}">
						</div>
						
						<label class="col-sm-2  control-label tr">原价-电话咨询（元/次）<font
							style="color: red;">*</font></label>
						<div class="col-sm-3">
							<input type="number" class="form-control" 
								name="phoneFavorablePrice" placeholder="请输入电话咨询所需费用" value="${ZzkExpertPrice[0].favorablePrice}">
						</div>
					</div>
					
					<div class="row" style="margin-top:20px;">
						<label class="col-sm-2  control-label tr">优惠-视频咨询（元/次）<font
							style="color: red;">*</font></label>
						<div class="col-sm-3">
							<input type="number" class="form-control" 
								name="video" placeholder="请输入视频咨询所需费用" value="${ZzkExpertPrice[1].price}">
						</div>
					
						<label class="col-sm-2  control-label tr">原价-视频咨询（元/次）<font
							style="color: red;">*</font></label>
						<div class="col-sm-3">
							<input type="number" class="form-control" 
								name="videoFavorablePrice" placeholder="请输入视频咨询所需费用" value="${ZzkExpertPrice[1].favorablePrice}">
						</div>
					</div>
					
					<div class="row" style="margin-top:20px;">
						<label class="col-sm-2  control-label tr">优惠-面对面咨询（元/次）<font
							style="color: red;">*</font></label>
						<div class="col-sm-3">
							<input type="number" class="form-control" 
								name="faceToFace" placeholder="请输入面对面咨询所需费用" value="${ZzkExpertPrice[2].price}">
						</div>
					
						<label class="col-sm-2  control-label tr">原价-面对面咨询（元/次）<font
							style="color: red;">*</font></label>
						<div class="col-sm-3">
							<input type="number" class="form-control" 
								name="faceToFaceFavorablePrice" placeholder="请输入面对面咨询所需费用" value="${ZzkExpertPrice[2].favorablePrice}">
						</div>
					</div>
					
					<div class="row" style="margin-top:20px;">					
						<label class="col-sm-2 control-label tr">专家简介<font
							style="color: red;">*</font></label>
						<div class="col-sm-8">
							<textarea rows="8" cols="80" class="form-control" id="textContent" name="synopsis">${user.expertDescription}</textarea>
						</div>
					</div>
					
	
					<div class="row">	
						<div class="form-group pic_upload clearfix" style="width: 100%">
							<label for="inputPassword" class="col-sm-2 control-label tr">上传封面图：<br>(推荐750*505)</label>
							<div class="col-sm-3">
								<ul>
									<c:if test="${empty user.expertImg}">
										<li><img src="${appRoot}/static/img/zanwu1.png" style="height: 70px;" id="imgURL" /></li>
									</c:if>
									<c:if test="${!empty user.expertImg}">
										<li><img src="${appAccessUrl}${user.expertImg}" style="height: 70px;" id="imgURL" /></li>
									</c:if>
									<li><input type="file" name="imgfile" onchange="coverImg(this)"/> <i class="glyphicon glyphicon-plus"></i></li>
									
								</ul>
							</div>
							
							<label for="inputPassword" class="col-sm-2 control-label tr">上传分享图：<br>(推荐750*505)</label>
							<div class="col-sm-4">
								<ul>
									<c:if test="${empty user.expertShareImg}">
										<li><img src="${appRoot}/static/img/zanwu1.png" style="height: 70px;" id="imgURL" /></li>
									</c:if>
									<c:if test="${!empty user.expertShareImg}">
										<li><img src="${appAccessUrl}${user.expertShareImg}" style="height: 70px;" id="imgURL" /></li>
									</c:if>
									<li><input type="file" name="imgfile1" onchange="coverImg(this)"/> <i class="glyphicon glyphicon-plus"></i></li>
								</ul>
							</div>
						</div>
					</div>
					
					<div class="row">	
						<div class="form-group pic_upload clearfix" style="width: 100%">
							<label for="inputPassword" class="col-sm-2 control-label tr">上传专家详情图：<br>(推荐750*300)</label>
							<div class="col-sm-9">
								<ul>
									<c:if test="${empty user.expertDetailsImg}">
										<li><img src="${appRoot}/static/img/zanwu1.png" style="height: 70px;" id="imgURL" /></li>
									</c:if>
									<c:if test="${!empty user.expertDetailsImg}">
										<li><img src="${appAccessUrl}${user.expertDetailsImg}" style="height: 70px;" id="imgURL" /></li>
									</c:if>
									<li><input type="file" name="imgfile3" onchange="coverImg(this)"/> <i class="glyphicon glyphicon-plus"></i></li>
									
								</ul>
							</div>
						</div>
					</div>
	
	
					<div class="row">	
						<div class="form-group pic_upload clearfix" style="width: 100%">
							<label for="inputPassword" class="col-sm-2 control-label tr">上传资质认证图：<br>(推荐212*282)</label>
							<div class="col-sm-9">
								<ul id="hiddenImgUrl">
									<c:if test="${!empty attachment}">
										<c:forEach var="experImg" items="${attachment}">  
											<li><input type="checkbox" style="position: absolute;right:0px;top:0px;" value="${experImg.id}" name="box" ><img src="${experImg.filenamestring}" style="height: 70px;" /></li>
										</c:forEach>  
									</c:if>
									<c:if test="${empty attachment}">
										<li><img src="${appRoot}/static/img/zanwu1.png" style="height: 70px;" id="imgURL1" /></li>
									</c:if>
									<li><input type="file" name="imgfile2" onchange="uploadImg(this)" imgIndex="1024" /> <i class="glyphicon glyphicon-plus"></i></li>
								</ul>
							</div>
						</div>
					</div>
	
					<div class="row">
						<div class="col-sm-10">	
							<input type="submit" class="btn btn-primary btn-lg pull-right" value="提交" />
						</div>
					</div>
				</form>
					<div class="row">
						<div class="col-sm-10">	
							<button class="btn btn-primary  btn-lg"  style="margin-left:60px;" onclick="doDelete()">删除资质认证图</button>
							<button class="btn btn-primary  btn-lg"  style="margin-left:25px;" onclick="seeWorkTime('${openid}')">查看工作时间</button>
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

	<form action="${appRoot}/expert/delCheckMessage" method="post" id="deleForm" name="deleForm">
		<input type="hidden" value="${openid}" name="expertOpenid" >
		<input type="hidden" name="byid" id="byid">
	    <input type="hidden" name="boxeditId" id="boxeditId">
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

		$(function() {
			
		});
		
		
		//查看工作时间
		function seeWorkTime(openid){
			//alert(openid);
			window.location.href="${appRoot}/expert/timeManage?openid="+openid;
		}
		
		//删除图片
		function doDelete() {
			var flag=checkbox();
			if(flag){
			  var $modal = $('#myModal2');
			  $modal.modal();
			}
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
		
		//删除
		function Delete(){
			deleForm.submit();
		}
		
		//其他图片
		function uploadImg(object) {
			
			
			$("#videotypeForm").submit();
			
			/* var index = $(object).attr("imgIndex");
			var newindex = parseInt(index)+1;
			$(object).attr("imgIndex",newindex);
			var image = '<li><img src="${appRoot}/static/img/zanwu1.png" style="width: 120px; height: 70px;" id="imgURL'+ index+ '" /><input type="checkbox" style="position: absolute;right:0px;top:0px;"></li>';
			
			if (object.files && object.files[0]) {
				var reader = new FileReader();
				reader.onload = function(evt) {
					if(evt.target.result!=""&&evt.target.result!=null){
						$("#imgURL1").remove();
						$("#hiddenImgUrl li").eq(-1).before(image);
						$("#imgURL"+index).attr("src",evt.target.result);
					}else{
						alert("图片上传出错!");
					}
				}
				reader.readAsDataURL(object.files[0]);
			} else {
				//alert(file.value);
			} */
		}

		//封面图片
		function coverImg(object){
			$("#videotypeForm").submit();
			
			
			/* if (object.files && object.files[0]) {
				var reader = new FileReader();
				reader.onload = function(evt) {
					if(evt.target.result!=""&&evt.target.result!=null){
						$("#imgURL").attr("src",evt.target.result);
					}else{
						alert("图片上传出错!");
					}
				}
				reader.readAsDataURL(object.files[0]);
			} else {
				//alert(file.value);
			} */
		}
	</script>
	<input type="hidden" value="" id="adminId" />
</body>
</html>

