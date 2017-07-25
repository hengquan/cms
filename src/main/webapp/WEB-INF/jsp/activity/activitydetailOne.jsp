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
							<header class="panel-heading">活动详情</header>
							<form id="activityForm" enctype="multipart/form-data" class="form-horizontal" role="form" >
							<input type="hidden" name="editId" id="editId" value="${acid}">
							<input type="hidden" name="flag" id="flag" value="${flag}">
							<input type="hidden" name="activeType" id="activeType" value="01">
							<div class="form-group">
								<label class="col-lg-1 control-label pd-r5">活动名称：</label>
								<div class="col-lg-3 control-label pd-r5">
									${tblActive.title}
								</div>
								<label class="col-lg-1 control-label pd-r5">抽奖次数：</label>
								<div class="col-lg-3 control-label pd-r5">
									${tblActive.eachTimes == null ? 1 : tblActive.eachTimes}
								</div>
								<label class="col-lg-1 control-label pd-r5">活动时间：</label>
								<div class="col-lg-3 control-label pd-r5" style="font-size: 14px;line-height: 1.428571429;">
									<fmt:formatDate value="${tblActive.startTime}" type="both" pattern="yyyy-MM-dd"/>至
									<fmt:formatDate value="${tblActive.endTime}" type="both" pattern="yyyy-MM-dd"/><!-- " readonly="readonly" style="width:163px;height: 34px;padding: 6px 12px;vertical-align: middle;background-color: #fff;border-radius: 4px;transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;"/> -->
								</div>
							</div>
							
							
							<div class="form-group">
								<label class="col-lg-1 control-label pd-r5">活动说明：</label>
								<div class="col-lg-8 control-label pd-r5">
									${tblActive.remark}
									
								</div>
							</div>
							 
							<div class="col-lg-3" style="width:25%;" id="leftDiv">
								<img src="${appAccessUrl}/${tblActive.imgUrl }" alt="图片预览" style="width:100%;">
							</div>
							<div class="col-lg-9" style="width:75%;" id="rightDiv">
								<table class="table table-striped border-top">
								<thead>
									<tr>
										<th>是否奖品</th>
										<th class="hidden-phone">奖品名称</th>
										<th class="hidden-phone">奖品数量</th>
										<th class="hidden-phone">奖品值</th>
										<th class="hidden-phone">奖品剩余数</th>
										<th class="hidden-phone">示意图</th>
									</tr>
								</thead>
								<tbody>
										<c:forEach begin="0" end="9" varStatus="status">
											<input type="hidden" name="sortId" id="sortId${status.index}" value="${status.index}"/>
											<tr class="odd gradeX">
												<td id="isPrize${status.index}"></td>
												<td id="prizeName${status.index}"></td>
												<td id="prizeCount${status.index}"></td>
												<td id="prizeVal${status.index}"></td>
												<td id="overCount${status.index}"></td>
												<td><img src="${appRoot}/static/wx/img/${status.index}.png"/></td>
											</tr>
										</c:forEach>
								</tbody>
							</table>
							</div>
							<c:if test="${flag == 1}">
								<input type="hidden" name="imgfuzhi" value="${tblActive.imgUrl}">
							</c:if>
						</form>
							
						</section>
					</div>
				</div>
				<!-- page end-->
			</section>
		</section>
	</section>	
		


	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>
	<script src="${appRoot}/static/js/jquery.sparkline.js" type="text/javascript"></script>

	<script type="text/javascript" src="${appRoot}/static/assets/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${appRoot}/static/assets/data-tables/DT_bootstrap.js"></script>
	<!--script for this page only-->
	<script src="${appRoot}/static/js/dynamic-table.js"></script>
    <script src="${appRoot}/static/js/dialog_alert.js"></script>
    <script src="${appRoot}/static/wx/js/lrz.bundle.js"></script>
	<script type="text/javascript">
	
		function changePrize(value,num){
			if(value == '0'){
				$('#prizeName'+num).val("谢谢惠顾");
				$('#prizeVal'+num).val(0);
				$('#prizeCount'+num).val(0);
			}else{
				$('#prizeName'+num).val('');
				$('#prizeVal'+num).val('');
				$('#prizeCount'+num).val('');
			}
		}
	
		$(function(){
			var acid = '${acid}';
			if(acid != null && acid != ''){
			$.ajax({
				type:'POST',
				data:{'acid':acid},
				url:'${appRoot}/activity/getActivityDetail',
				dataType:'json',
				success:function(data){
					for(var i=0;i<data.length;i++){
						if(data[i].isPrize==1){
							$('#isPrize'+i).text("奖品");
						}else{
							$('#isPrize'+i).text("谢谢惠顾");
						}
						
						$('#prizeName'+i).text(data[i].prizeName);
						$('#prizeCount'+i).text(data[i].prizeCount);
						$('#prizeVal'+i).text(data[i].prizeVal);
						$('#overCount'+i).text(data[i].prizeCount-data[i].overCount);
					}
				}
			});
			}	
			
			if('${flag}' == 1){
				$('#imgUrl').prop('disabled',true);
			}
		});		
		
		function validateDetail(){
			
			var flag = true;
			
			$('input[name="prizeName"]').each(function(){
				var v = $.trim($(this).val());
				if(v == null || v == ''){
					flag = false;
				}
			});
			
			$('input[name="prizeCount"]').each(function(){
				var v = $.trim($(this).val());
				if(v == null || v == ''){
					flag = false;
				}
			});
			$('input[name="prizeVal"]').each(function(){
				var v = $.trim($(this).val());
				if(v == null || v == ''){
					flag = false;
				}
			});
			
			return flag;
		}
		
		$('#imgUrl').on('change', function () {
			lrz(this.files[0])
					.then(function (rst) {
						// 处理成功会执行
						var img = new Image();
						img.src = rst.base64;
						img.onload = function () {
							$('#fileimg').attr('src',rst.base64);
							$('#rightDiv').css("width","75%");
							$('#leftDiv').css("width","25%");
							$('#leftDiv').show();
						};
						console.log(rst);
					})
		});
		
	</script>
</body>
</html>
