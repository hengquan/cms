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
				<div class="row state-overview">

					<div class="col-lg-12">
						<div class="vga-wrapper">
							<section class="panel vga-tit">
								<div class="symbol text-left">
									<h4>查看角色权限</h4>
								</div>
								<div class="value">
									<button id="submitBtn" type="button" class="btn btn-success"
										style="padding-left: 30px; padding-right: 30px;">保 存</button>
									<button id="returnBtn" class="btn btn-default" type="button">返回列表</button>
								</div>
							</section>
						</div>
					</div>

					<div class="col-lg-12" style="overflow-y: auto;">
						<section class="panel">
							<header class="panel-heading"> 基本信息 </header>
							<div class="panel-body">
								<form class="form-horizontal" role="form" id="baseForm"
									name="baseForm" action="${appRoot}/roleItem/addAndUpdate"
									method="post">
									<input type="hidden" name="roleId" value="${roleId }">
									<input type="hidden" name="itemId" value="${itemId }">
									<input type="hidden" name="id" value="${id }"> <input
										type="hidden" name="itemIds" id="itemIds" value="">
									<div class="form-group">
										<label class="col-lg-3 control-label pd-r5">角色名称<font
											style="color: red;">*</font></label>
										<div class="col-lg-11">
											<input type="text" class="form-control" id="versionNum"
												name="versionNum" placeholder="" value="${roleName }"
												readonly="readonly">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-3 control-label pd-r5">角色权限<font
											style="color: red;">*</font></label>

										<div class="col-lg-11" id="cbox">
											<input type="checkbox" name="Fruit1" id="chkAll"
												class="group-checkable"
												onclick="checkEvent('Fruit','chkAll')" />全选
											<!-- <input type="checkbox" name="Fruit2" id="chkReserve" class="group-checkable"  value="" onclick="reserveCheck('Fruit','chkReserve')"/>反选<br> -->
											<c:forEach items="${itemList}" var="item" varStatus="s">
												<c:set value="1" var="num"></c:set>
												<div class="${item.id }">
													<label onclick="cke(this)" data-id="${num}"> <input
														name="Fruit" type="checkbox" value="${item.id}"
														<c:forEach items="${list}" var="info" varStatus="e">
                                        		<c:if test="${item.itemName == info.itemName }">checked="true"</c:if>
                                        	</c:forEach> />
														${item.itemName}
													</label>
													<c:forEach items="${item.childList}" var="childItem"
														varStatus="e">
														<c:set value="${num+1}" var="num"></c:set>
														<label data-id="${num}"> <input name="Fruit"
															class="sys-item" type="checkbox" value="${childItem.id}"
															<c:forEach items="${ls}" var="ifo" varStatus="e">
	                                        		<c:if test="${childItem.itemName == ifo.itemName }">checked="true"</c:if>
	                                        	</c:forEach> />
															${childItem.itemName}
														</label>
													</c:forEach>
												</div>
												<br />
											</c:forEach>
										</div>
									</div>
								</form>
							</div>
						</section>
					</div>
				</div>
				<!-- page end-->
			</section>
		</section>
		<!--main content end-->
	</section>



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

	<script>
		// 用于该页顶部标题栏自适应。
		function resizeTit() {
			var ww = $('.wrapper').width();
			$('.vga-tit').css('width', ww + 'px');
		}
		$(window).resize(resizeTit);
		window.onload = resizeTit();

		// 当页面在移动端 标题会自动移动到顶部。
		var initTop = 0;
		function scroll() {
			if ($(window).innerWidth() <= 768) {
				var scrollTop = $(document).scrollTop();
				if (scrollTop > initTop) {
					$('.vga-tit').css('top', '15px');
				} else {
					$('.vga-tit').css('top', '80px');
				}
				initTop = scrollTop;
			}
		}
		$(window).on('scroll', scroll);
		var submitBtn = $("#submitBtn");

		submitBtn.click(function() {
			checkbox();
			$("#baseForm").submit();
		});
		var returnBtn = $("#returnBtn");
		returnBtn.click(function() {

			window.location.href = wxmp.appRoot + "/role/list";
		});

		function checkEvent(name, allCheckId) {
			var allCk = document.getElementById(allCheckId);
			if (allCk.checked == true)
				checkAll(name);
			else
				checkAllNo(name);

		}

		//全选  
		function checkAll(name) {
			var names = document.getElementsByName(name);
			var len = names.length;
			if (len > 0) {
				var i = 0;
				for (i = 0; i < len; i++)
					names[i].checked = true;

			}
		}

		//全不选  
		function checkAllNo(name) {
			var names = document.getElementsByName(name);
			var len = names.length;
			if (len > 0) {
				var i = 0;
				for (i = 0; i < len; i++)
					names[i].checked = false;
			}
		}

		//反选  
		function reserveCheck(name) {
			var names = document.getElementsByName(name);
			var len = names.length;
			if (len > 0) {
				var i = 0;
				for (i = 0; i < len; i++) {
					if (names[i].checked)
						names[i].checked = false;
					else
						names[i].checked = true;

				}
			}
		}

		function checkbox() {
			var str = document.getElementsByName("Fruit");
			var objarray = str.length;
			var chestr = "";
			for (i = 0; i < objarray; i++) {
				if (str[i].checked == true) {

					chestr += str[i].value + ",";
				}
			}
			$("#itemIds").val(chestr);
			return true;

		}
		function cke(that) {
			var self = $(that);
			var flag = self.find('input').prop('checked');
			if (self.data('id') == 1) {
				self.siblings().find('input').prop('checked', flag);
			}
		}
		$('.sys-item').click(
				function() {
					var ck = $(this).parent();
					var len = $(this).prop('checked') ? 1 : 0;
					$(this).parent().siblings().each(
							function() {
								if ($(this).attr('data-id') != 1) {
									len += $(this).find('input')
											.prop('checked') ? 1 : 0;
								}
							});
					$(this).parent().siblings('[data-id="1"]').find('input')
							.prop('checked', len > 0 ? true : false);
					console.log(len);
				});
	</script>

</body>
</html>
