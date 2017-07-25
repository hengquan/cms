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
							<header class="panel-heading">优惠券管理</header>
							<form action="${appRoot}/anwCoupon/list" method="post"
								id="selectCheckMessage">
								<!-- 根据用户昵称查询 -->
								<div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<input type="text" class="btn"
										style="width: 500px; border: 1px solid #ddd; text-align: left;"
										placeholder="请输入金额" name="userName1" value="${mingzi }"><span>
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

								<!-- <div
									style="float: left; position: relative; margin-top: 16px; margin-left: 20px;">
									<a href="javascript:doDelete();" class="btn mini btn-white"><i
										class="icon-trash"></i></a>
								</div> -->



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
										<th class="hidden-phone">生成人姓名</th>
										<th class="hidden-phone">优惠券名称</th>
										<th class="hidden-phone">金额</th>
										<th class="hidden-phone">全部数量</th>
										<th class="hidden-phone">已用数量</th>
										<th class="hidden-phone">未用数量</th>
										<th class="hidden-phone">生成时间</th>
										<th class="hidden-phone">可用时间</th>
										<th class="hidden-phone">结束时间</th>
										<th class="hidden-phone">操作</th>
									</tr>
								</thead>
								<tbody id="theTbody">
									<c:forEach items="${anwCoupons}" var="u" varStatus="s">
										<tr class="odd gradeX theTr">
											<td><input type="checkbox" name="box" class="checkboxes"
												value="${u.id}" /></td>
											<td class="hidden-phone">${u.admin}</td>
											<td class="hidden-phone">${u.name}</td>
											<td class="hidden-phone">${u.money}</td>
											<td class="hidden-phone">${u.sumCount}</td>
											<td class="hidden-phone">${u.count}</td>
											<td class="hidden-phone">${u.unCount}</td>
											<td class="hidden-phone">${u.createTime}</td>
											<td class="hidden-phone">${u.starTime}</td>
											<td class="hidden-phone">${u.endTime}</td>
											<td><button type="button" class="btn btn-primary"
													onclick="editMsg('${u.id}','${u.name }','${u.money }','${u.sumCount }','${u.starTime }','${u.endTime }')">修改</button></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
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
						<h4 class="modal-title" id="modal-title">添加优惠券</h4>
					</div>
					<div class="modal-body">
						<form action="${appRoot}/anwCoupon/add" method="post" class="form-horizontal"  enctype="multipart/form-data"  role="form" id="addMessage" name="itemForm" >
							<input type="hidden" name="editId123" id="editId123">
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">优惠券名称<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="name"
										name="name" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">金额<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="number" class="form-control" id="couponMoney"
										name="couponMoney" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">数量<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="number" class="form-control" id="couponNumber"
										name="couponNumber" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">开始时间<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="date" class="form-control" id="starTime"
										name="starTime" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">结束时间<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="date" class="form-control" id="endTime"
										name="endTime" >
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









		<div class="modal fade" id="editCoupon" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="modal-title">修改优惠券</h4>
					</div>
					<div class="modal-body">
						<form action="${appRoot}/anwCoupon/edit" method="post" class="form-horizontal"  enctype="multipart/form-data"  role="form" id="editMessage" name="itemForm" >
							<input type="hidden" name="editId" id="editId">
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">优惠券名称<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="name1"
										name="name" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">金额<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="number" class="form-control" id="couponMoney1"
										name="couponMoney" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">数量<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="number" class="form-control" id="couponNumber1"
										name="couponNumber" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">开始时间<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="date" class="form-control" id="starTime1"
										name="starTime" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">结束时间<font style="color:red;"></font></label>
								<div class="col-lg-10">
									<input type="date" class="form-control" id="endTime1"
										name="endTime" >
								</div>
							</div>
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button type="button" onclick="submitData1();" class="btn btn-send">提交</button>
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
	
	Date.prototype.format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
	
		//修改信息
		function editMsg(id,name,money,count,starTime,endTime){
			var now1 = new Date(starTime); 
			var now2 = new Date(endTime); 
			var starTime = now1.format("yyyy-MM-dd"); 
			var endTime = now2.format("yyyy-MM-dd");
			
			$("#name1").val(name);
			$("#couponMoney1").val(money);
			$("#couponNumber1").val(count);
			$("#starTime1").val(starTime);
			$("#endTime1").val(endTime);
			$("#editId").val(id);
			var $modal = $('#editCoupon');
			$modal.modal();
		}
		
		//修改信息提交
		function submitData1() {
			$("#editMessage").submit();
		}
	
	
		//添加提交
		function submitData() {
			var couponMoney = $("#couponMoney").val();
			if (couponMoney == '' || couponMoney == undefined) {
				windowShow("优惠券金额不允许为空！", "");
				return;
			}
			var couponNumber = $("#couponNumber").val();
			if (couponNumber == '' || couponNumber == undefined) {
				windowShow("优惠券数量不允许为空！", "");
				return;
			}
			$("#addMessage").submit();
		}

		//更新数据
		function submitUpdateData() {
			var productName1 = $("#productName1").val();
			alert(productName1);
			if (productName1 == '' || productName1 == undefined) {
				windowShow("类型名称不允许为空！", "");
				return;
			}
			$("#updateMessage").submit();
		}


		$(function() {
			$('.input-group').hide();
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
			$("#selectCheckMessage").submit();
		}
		

		//添加菜单
		function doAdd() {
			var $modal = $('#addProblemType');
			$modal.modal();
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

