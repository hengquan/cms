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
							<header class="panel-heading">活动管理</header>
							<form id="activityForm" enctype="multipart/form-data" class="form-horizontal" role="form" method="POST" action="${appRoot}/activity/addOrUpdate">
							<input type="hidden" name="editId" id="editId" value="${acid}">
							<input type="hidden" name="flag" id="flag" value="${flag}">
							<input type="hidden" name="activeType" id="activeType" value="${activeType}">
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">活动名称<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="title"
										name="title" placeholder="请输入活动名称" value="${tblActive.title}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">抽奖次数<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<input type="number" class="form-control" id="eachTimes"
										name="eachTimes" placeholder="请输入活动次数" value="${tblActive.eachTimes == null ? 1 : tblActive.eachTimes}">
								</div>
							</div>
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-lg-2 control-label pd-r5">是否有效<font style="color:red;">*</font></label> -->
<!-- 								<div class="col-lg-10"> -->
<!-- 									<select class="form-control" id="isvalidate" name="isvalidate"> -->
<!-- 										<option value="1">有效</option> -->
<!-- 										<option value="0">无效</option> -->
<!-- 									</select> -->
<!-- 								</div> -->
<!-- 							</div> -->
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">活动时间<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<input type="date" class="form-control" id="startTime" name="startTime" placeholder="请输入开始时间" value="<fmt:formatDate value="${tblActive.startTime}" type="both" pattern="yyyy-MM-dd"/>"/>-
									<input type="date" class="form-control" id="endTime" name="endTime" placeholder="请输入结束时间" value="<fmt:formatDate value="${tblActive.endTime}" type="both" pattern="yyyy-MM-dd"/>"/>
								</div>
							</div>
							 <div class="form-group">
                                      <label class="col-lg-2 control-label pd-r5">上传图片<font style="color:red;">*</font></label>
                                      <div class="col-lg-10">
                                        <input type="file" class="form-control" id="imgUrl" name="imgUrl" placeholder="请选择文件" > 
                                      </div>
                             </div>
                             	<div class="form-group">
								<label class="col-lg-2 control-label">活动说明<font style="color:red;">*</font></label>
								<div class="col-lg-10">
									<textarea rows="2" cols="60" class="form-control" id="remark" name="remark" maxlength="121">${tblActive.remark}
									</textarea>
								</div>
							</div>
							<div class="col-lg-3" style="display: none;" id="leftDiv">
								<img src=" " alt="图片预览" id="fileimg" style="width:100%;">
							</div>
							<div class="col-lg-9" style="width:100%;" id="rightDiv">
								<table class="table table-striped border-top">
								<thead>
									<tr>
										<th>是否奖品</th>
										<th class="hidden-phone">奖品名称</th>
										<th class="hidden-phone">奖品数量</th>
										<th class="hidden-phone">奖品值</th>
										<th class="hidden-phone">剩余奖品</th>
										<th class="hidden-phone">示意图</th>
									</tr>
								</thead>
								<tbody>
										<c:forEach begin="0" end="9" varStatus="status">
											<input type="hidden" name="sortId" id="sortId${status.index}" value="${status.index}"/>
											<tr class="odd gradeX">
												<td>
													<select name="isPrize" id="isPrize${status.index}" class="form-control" onchange="changePrize(this.value,'${status.index}');">
														<option value="1">奖品</option>
														<option value="0">谢谢惠顾</option>
													</select>
												</td>
												<td><input class="form-control" type="text" name="prizeName" id="prizeName${status.index}"/></td>
												<td><input class="form-control" type="number" name="prizeCount" id="prizeCount${status.index}"/></td>
												<td><input class="form-control" type="text" name="prizeVal" id="prizeVal${status.index}"/></td>
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
							<button class="btn btn-default" onclick="submitData();" style="width: 3cm;height: 1.0cm;float: right;">提交</button>
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
						$('#isPrize'+i).val(data[i].isPrize);
						$('#prizeName'+i).val(data[i].prizeName);
						$('#prizeCount'+i).val(data[i].prizeCount);
						$('#prizeVal'+i).val(data[i].prizeVal);
					}
				}
			});
			}	
			
			if('${flag}' == 1){
				$('#imgUrl').prop('disabled',true);
			}
			if(acid != null && acid != ""){
				$('#fileimg').attr('src','${appAccessUrl}/${tblActive.imgUrl}');
				$('#rightDiv').css("width","75%");
				$('#leftDiv').css("width","25%");
				$('#leftDiv').show();
			}
			
			var activeType = '${activeType}';
			if(activeType == '02'){
				$('#eachTimes').prop('disabled',true);
			}
		});
		
	
		function submitData(){
			if($('#title').val() == null || $('#title').val() == ''){
				windowShow("标题不能为空","");
				return false;
			}
			if($('#eachTimes').val() < 0){
				windowShow("抽奖次数必须大于零!","");
				return false;
			}
			if($('#startTime').val() == null || $('#startTime').val() == ''){
				windowShow("开始时间不能为空","");
				return false;
			}
			if($('#endTime').val() == null || $('#endTime').val() == ''){
				windowShow("结束时间不能为空","");
				return false;
			}
			if($('#endTime').val() < $('#startTime').val()){
				windowShow("结束时间不能小于开始时间","");
				return false;
			}
			if($("#editId").val() == null || $("#editId").val() == ''){
				if($('#imgUrl').val() == null || $('#imgUrl').val() == ''){
					windowShow("图片不能为空","");
					return false;
				}
			}
			if($('#remark').val() == null || $('#remark').val() == ''){
				windowShow("活动详情不能为空","");
				return false;
			}
			if(validateDetail() == false){
				windowShow("活动详情内容不能为空","");
				return false;
			}
			
			var edid = $('#editId').val();
			var info = '${flag}';
			if(info == '1'){
				edid = "";
			}
		
			$.ajax({
				type:'POST',
				data:{
					'startTime':$('#startTime').val(),
					'endTime':$('#endTime').val(),
					'activeType':$('#activeType').val(),
					'editId':edid
					},
				url:'${appRoot}/activity/validateDate',
				dataType:'json',
				success:function(data){
					if(data.result == 'error'){
						windowShow("活动时间冲突","");
						return false;
					}else{
						$('#imgUrl').prop('disabled',false);
						$('#eachTimes').prop('disabled',false);
						$('#activityForm').submit();
					}
				},
				error:function(data){
					alert("服务器错误!");
				}
			});
		}		
		
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
