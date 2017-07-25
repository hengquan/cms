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

.pic_upload ul li i.glyphicon {
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
							<header class="panel-heading" style="height:50px;">
							
							<div class="col-lg-1">修改商品信息</div>
							<a href="${appRoot}/anwProduct/list" class="btn btn-send mini btn-white col-lg-1 pull-right" style="align:right;"><i class="glyphicon glyphicon-arrow-left"></i></a>
							
							</header>
							
							<form action="${appRoot}/problem/list" method="post"
								id="selectCheckMessage">
								<input type="hidden" value="${nowPage}" id="nowPageNumber"
									name="nowPage"> <input type="hidden"
									value="${totalPageNum }">
							</form>

							<div style="clear: both"></div>

							<div class="modal-body">
								<form action="${appRoot}/anwProduct/edit" method="post" class="form-horizontal"  enctype="multipart/form-data"  role="form" id="addMessage" name="itemForm" >
									<input type="hidden" name="editId" id="editId" value="${anwProduct.id }">
									<div class="form-group">
										<label class="col-lg-1 control-label pd-r5">商品名称<font
											style="color: red;"></font></label>
										<div class="col-lg-4">
											<input type="text" class="form-control" id="productName" value="${anwProduct.productName }" name="productName" maxlength="10" >
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-1 control-label pd-r5">含量(袋)<font
											style="color: red;"></font></label>
										<div class="col-lg-4">
											<input type="text" class="form-control" id="unit" value="${anwProduct.unit }" name="unit" maxlength="4" >
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-1 control-label pd-r5">商品价格<font
											style="color: red;"></font></label>
										<div class="col-lg-4">
											<input type="text" class="form-control" id="price"
										name="price" maxlength="4" value="${anwProduct.price }" >
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-1 control-label pd-r5">内部价格<font
											style="color: red;"></font></label>
										<div class="col-lg-4">
											<input type="text" class="form-control" id="depthPrice"
										name="depthPrice" maxlength="4" value="${anwProduct.depthPrice }" >
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-1 control-label pd-r5">销量<font
											style="color: red;"></font></label>
										<div class="col-lg-4">
											<input type="text" class="form-control" id="salesVolume"
										name="salesVolume" maxlength="4" value="${anwProduct.salesVolume }" >
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-1 control-label pd-r5">商品运费<font
											style="color: red;"></font></label>
										<div class="col-lg-4">
											<input type="text" class="form-control" id="freight"
										name="freight" maxlength="4" value="${anwProduct.freight }" >
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-1 control-label pd-r5">商品类型<font
											style="color: red;"></font></label>
										<div class="col-lg-4">
											<select class="form-control" name="categoryId">
												<c:forEach items="${categorys}" var="u" varStatus="s">
													<option value="${u.id }" <c:if test="${anwProduct.categoryId == u.id }">selected</c:if> >${u.name }</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-1 control-label pd-r5">商品规格<font
											style="color: red;"></font></label>
										<div class="col-lg-4">
											<select class="form-control" name="standardId">
												<c:forEach items="${standards}" var="u" varStatus="s">
													<option value="${u.id }" <c:if test="${anwProduct.standardId == u.id }">selected</c:if> >${u.name }</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-1 control-label pd-r5">商品详情<font
											style="color: red;"></font></label>
										<div class="col-lg-10">
											<textarea name="productDetails" class="az-txtarea" id="content" style="width: 100%; height: 100px;font-size: 14px;">
											${anwProduct.productDetails }
											</textarea>
								 	  	</div>
									</div>
									<div class="form-group">
										<label class="col-lg-1 control-label pd-r5">商品图片-封面<font
											style="color: red;"></font></label>
										<div class="col-lg-10">
											<div class="form-group pic_upload clearfix">
												<ul id="hiddenImgUrl">
													<li><img src="${appAccessUrl}${anwProduct.img }" style="height: 70px;" id="imgURL2" /></li>
													<li><input type="file" name="imgfile2" onchange="addImg(this)" /> <i class="glyphicon glyphicon-plus"></i></li>
												</ul>
											</div>
										</div>
									</div>
									<div class="form-group pic_upload clearfix" style="width: 100%">
										<label for="inputPassword" class="col-sm-1 control-label tr">商品图片-轮播图：<br>(推荐212*282)</label>
										<div class="col-sm-10">
											<ul id="hiddenImgUrl">
												<c:if test="${!empty attachments}">
													<c:forEach var="attachment" items="${attachments}">  
														<li>
														<%-- <input type="checkbox"  class="glyphicon glyphicon-remove" style="position: absolute;right:0px;top:0px;" value="${attachment.id}" name="box" > --%>
														<i class="icon-trash" onclick="deleteImage('${attachment.id}')" style="position: absolute;right:0px;top:0px;font-size:18px;cursor:pointer;color:#888;"></i>
														<img src="${appAccessUrl}${attachment.accessurl}" style="height: 70px;" /></li>
													</c:forEach>  
												</c:if>
												<c:if test="${empty attachments}">
													<li><img src="${appRoot}/static/img/zanwu1.png" style="height: 70px;" id="imgURL1" /></li>
												</c:if>
												<li><input type="file" name="imgfile1" onchange="uploadImg(this)" imgIndex="1024" /> <i class="glyphicon glyphicon-plus"></i></li>
											</ul>
										</div>
									</div>
									<div class="form-group">
										<div class="col-lg-10">
											<button type="button" onclick="submitData();" class="btn btn-primary  btn-lg">提交</button>
										</div>
									</div>
								</form>
								<!-- <div class="form-group">
									<div class="col-sm-10">	
										<button class="btn btn-primary  btn-lg"  style="margin-left:60px;" onclick="doDelete()">删除图片</button>
									</div>
								</div> -->
							</div>

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

	<form action="${appRoot}/anwProduct/delete" method="post" id="deleForm"
		name="deleForm">
		<input type="hidden" name="productId" id="productId" value="${anwProduct.id  }">
		<input type="hidden" name="byid" id="byid"> 
		<input type="hidden" name="boxeditId" id="boxeditId">
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
	
	
	 <script type="text/javascript" charset="utf-8" src="${appRoot}/static/assets/ueditor/ueditor.config.js"></script>
	 <script type="text/javascript" charset="utf-8" src="${appRoot}/static/assets/ueditor/ueditor.all.min.js"> </script>
	
	
	<script type="text/javascript">
		//查看相关问题的偷听用户
		function seeEavesdropMessage(answerId){
			//alert(answerId);
			window.location.href="${appRoot}/answer/eavesdropMessage?id="+answerId;
		}
		
		
		//其他图片
		function uploadImg(object) {
			$("#addMessage").submit();
		}
		
		
		
		//添加提交
		function submitData() {
			var productName = $("#productName").val();
			if (productName == '' || productName == undefined) {
				windowShow("商品名称不允许为空！", "");
				return;
			}
			$("#addMessage").submit();
		}
		
		$(function(){
			$('.input-group').hide();
			$("#sample_1_length .form-control").hide();
			$("#sample_1_length .js-add").hide();
			$("#sample_1_length .js-ref").hide();
			$("#sample_1_length .js-del").hide();
			
			
			var ue = UE.getEditor('content',{  
                //默认的编辑区域高度  
                initialFrameHeight:390  
                //更多其他参数，请参考ueditor.config.js中的配置项  
            });
			var atype = '${tblArticle.articleType}';
			if(atype == null || atype == ''){
				$('#articleType').val('01');
			}else{
				$('#articleType').val('${tblArticle.articleType}');
			}
			$('#content').text('${anwProduct.productDetails }');
			$('#remark').text('${tblArticle.remark}');
			var contextType = '${tblArticle.articleUrl}';
			if(contextType == null || contextType == ''){
				$('#contextType').val(1);
			}else{
				if(contextType == 1){
					$('#lj').hide();
					$('#nr').show();
					$('#contextType').val(1);
				}else{
					$('#lj').show();
					$('#nr').hide();
					$('#contextType').val(2);
				}
			}
		});
		
		
		
		//添加图片
		function addImg(object){
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
		
		
		//删除单个图片
		function deleteImage(id){
			$("#boxeditId").val(id);
			var $modal = $('#myModal2');
			$modal.modal();
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
	</script>
	<input type="hidden" value="" id="adminId" />
</body>
</html>

