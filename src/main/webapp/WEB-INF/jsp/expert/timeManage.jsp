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
				<div class="row">
					<div class="col-lg-12">
						<section class="panel">
							<header class="panel-heading" style="height:50px;">
							<div class="col-lg-2">时间管理</div>
							<a href="javascript:history.go(-1)" class="btn btn-send mini btn-white col-lg-1 pull-right" style="align:right;"><i class="glyphicon glyphicon-arrow-left"></i></a>
							</header>
						</section>
					</div>
					<table style="margin-left:15px;">
						<tr>
						<th class="hidden-phone" style="background-color:#91d1e6;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
							<th>未设置时间&nbsp;&nbsp;&nbsp;</th>
							<th class="hidden-phone" style="background-color:#f8aa1b;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
							<th>已设置时间（可以取消）&nbsp;&nbsp;&nbsp;</th>
							<th class="hidden-phone" style="background-color:#68c862;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
							<th>已预约（不可取消）&nbsp;&nbsp;&nbsp;</th>
						</tr>
					</table>
					
					<input type="hidden" value="${nowDate}" id="nowDate">
					
					<form action="${appRoot}/expert/addTimeManage" method="post" id="formOne" name="formOne">
						
						<input type="hidden" value="${openid }" id="userOpenId" name="userOpenid">
					
					
						<table class="table table-bordered border-top" id="dateTimeTable" style="margin-top:20px;background-color:#91d1e6;">
							<tr style="background-color:#FFFFFF;">
								<th class="hidden-phone"><font color=#ee7910>日期\时间</font></th>
								<th class="hidden-phone">9:00-10:00</th>
								<th class="hidden-phone">10:00-11:00</th>
								<th class="hidden-phone">11:00-12:00</th>
								<th class="hidden-phone">12:00-13:00</th>
								<th class="hidden-phone">13:00-14:00</th>
								<th class="hidden-phone">14:00-15:00</th>
								<th class="hidden-phone">15:00-16:00</th>
								<th class="hidden-phone">16:00-17:00</th>
								<th class="hidden-phone">17:00-18:00</th>
								<th class="hidden-phone">18:00-19:00</th>
								<th class="hidden-phone">19:00-20:00</th>
								<th class="hidden-phone">20:00-21:00</th>
							</tr>
							<tr class="odd gradeX theTr">
								<td style="background-color:#FFFFFF;"><b id="today"></b></td>
								<td theExpertTime = "9:00-10:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="9:00-10:00" /></td>
								<td theExpertTime = "10:00-11:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="10:00-11:00" /></td>
								<td theExpertTime = "11:00-12:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="11:00-12:00" /></td>
								<td theExpertTime = "12:00-13:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="12:00-13:00" /></td>
								<td theExpertTime = "13:00-14:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="13:00-14:00" /></td>
								<td theExpertTime = "14:00-15:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="14:00-15:00" /></td>
								<td theExpertTime = "15:00-16:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="15:00-16:00" /></td>
								<td theExpertTime = "16:00-17:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="16:00-17:00" /></td>
								<td theExpertTime = "17:00-18:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="17:00-18:00" /></td>
								<td theExpertTime = "18:00-19:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="18:00-19:00" /></td>
								<td theExpertTime = "19:00-20:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="19:00-20:00" /></td>
								<td theExpertTime = "20:00-21:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="20:00-21:00" /></td>
							</tr>
							<tr class="odd gradeX theTr">
								<td style="background-color:#FFFFFF;"><b id="tomorrow"></b></td>
								<td theExpertTime = "9:00-10:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="9:00-10:00" /></td>
								<td theExpertTime = "10:00-11:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="10:00-11:00" /></td>
								<td theExpertTime = "11:00-12:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="11:00-12:00" /></td>
								<td theExpertTime = "12:00-13:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="12:00-13:00" /></td>
								<td theExpertTime = "13:00-14:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="13:00-14:00" /></td>
								<td theExpertTime = "14:00-15:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="14:00-15:00" /></td>
								<td theExpertTime = "15:00-16:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="15:00-16:00" /></td>
								<td theExpertTime = "16:00-17:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="16:00-17:00" /></td>
								<td theExpertTime = "17:00-18:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="17:00-18:00" /></td>
								<td theExpertTime = "18:00-19:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="18:00-19:00" /></td>
								<td theExpertTime = "19:00-20:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="19:00-20:00" /></td>
								<td theExpertTime = "20:00-21:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="20:00-21:00" /></td>
							</tr>
							<tr class="odd gradeX theTr">
								<td style="background-color:#FFFFFF;"><b id="theDayAfterTomorrow"></b></td>
								<td theExpertTime = "9:00-10:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="9:00-10:00" /></td>
								<td theExpertTime = "10:00-11:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="10:00-11:00" /></td>
								<td theExpertTime = "11:00-12:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="11:00-12:00" /></td>
								<td theExpertTime = "12:00-13:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="12:00-13:00" /></td>
								<td theExpertTime = "13:00-14:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="13:00-14:00" /></td>
								<td theExpertTime = "14:00-15:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="14:00-15:00" /></td>
								<td theExpertTime = "15:00-16:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="15:00-16:00" /></td>
								<td theExpertTime = "16:00-17:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="16:00-17:00" /></td>
								<td theExpertTime = "17:00-18:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="17:00-18:00" /></td>
								<td theExpertTime = "18:00-19:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="18:00-19:00" /></td>
								<td theExpertTime = "19:00-20:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="19:00-20:00" /></td>
								<td theExpertTime = "20:00-21:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="20:00-21:00" /></td>
							</tr>
							<tr class="odd gradeX theTr">
								<td style="background-color:#FFFFFF;"><b id="threeDaysFromNow"></b></td>
								<td theExpertTime = "9:00-10:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="9:00-10:00" /></td>
								<td theExpertTime = "10:00-11:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="10:00-11:00" /></td>
								<td theExpertTime = "11:00-12:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="11:00-12:00" /></td>
								<td theExpertTime = "12:00-13:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="12:00-13:00" /></td>
								<td theExpertTime = "13:00-14:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="13:00-14:00" /></td>
								<td theExpertTime = "14:00-15:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="14:00-15:00" /></td>
								<td theExpertTime = "15:00-16:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="15:00-16:00" /></td>
								<td theExpertTime = "16:00-17:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="16:00-17:00" /></td>
								<td theExpertTime = "17:00-18:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="17:00-18:00" /></td>
								<td theExpertTime = "18:00-19:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="18:00-19:00" /></td>
								<td theExpertTime = "19:00-20:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="19:00-20:00" /></td>
								<td theExpertTime = "20:00-21:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="20:00-21:00" /></td>
							</tr>
							<tr class="odd gradeX theTr">
								<td style="background-color:#FFFFFF;"><b id="greatlyTheDayAfterTomorrow"></b></td>
								<td theExpertTime = "9:00-10:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="9:00-10:00" /></td>
								<td theExpertTime = "10:00-11:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="10:00-11:00" /></td>
								<td theExpertTime = "11:00-12:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="11:00-12:00" /></td>
								<td theExpertTime = "12:00-13:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="12:00-13:00" /></td>
								<td theExpertTime = "13:00-14:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="13:00-14:00" /></td>
								<td theExpertTime = "14:00-15:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="14:00-15:00" /></td>
								<td theExpertTime = "15:00-16:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="15:00-16:00" /></td>
								<td theExpertTime = "16:00-17:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="16:00-17:00" /></td>
								<td theExpertTime = "17:00-18:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="17:00-18:00" /></td>
								<td theExpertTime = "18:00-19:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="18:00-19:00" /></td>
								<td theExpertTime = "19:00-20:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="19:00-20:00" /></td>
								<td theExpertTime = "20:00-21:00"><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="20:00-21:00" /></td>
							</tr>
						</table>
					
					</form>
					<input type="button" class="btn btn-primary pull-right btn-lg" value="提交"   onclick="addWorkTime()" style="margin-right:5px;">
				<!-- page end-->
			</section>
		</section>
		<!-- /.modal -->
	</section>



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


	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>
	<script src="${appRoot}/static/js/jquery.sparkline.js"
		type="text/javascript"></script>

	<script type="text/javascript"
		src="${appRoot}/static/assets/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript"
		src="${appRoot}/static/assets/data-tables/DT_bootstrap.js"></script>
	<!--script for this page only-->
	<%-- <script src="${appRoot}/static/js/dynamic-table.js"></script> --%>
	<script src="${appRoot}/static/js/dialog_alert.js"></script>
	<script type="text/javascript">
		var allCheckedValue = "";
		
		
		
		//当前时间
		function currentDate(nowDate){
			
             var seperator1 = "-";
             for(var i=0;i<=4;i++){
				 var date = new Date(nowDate);
            	 date.setDate(date.getDate()+i);//N天后的日期(当天日期在内的五天日期)
	             var year = date.getFullYear();
	             var month = date.getMonth() + 1;
	             var strDate = date.getDate();
	             if (month >= 1 && month <= 9) {
	                 month = "0" + month;
	             }
	             if (strDate >= 0 && strDate <= 9) {
	                 strDate = "0" + strDate;
	             }
	             var currentdate = year + seperator1 + month + seperator1 + strDate;
	             if(i==0){
	            	getDate("#today")
	             }else if(i==1){
	            	 getDate("#tomorrow")
	             }else if(i==2){
	            	 getDate("#theDayAfterTomorrow")
	             }else if(i==3){
	            	 getDate("#threeDaysFromNow")
	             }else if(i==4){
	            	 getDate("#greatlyTheDayAfterTomorrow")
	             }
             }
             function getDate(txt){
            	 $(txt).html(currentdate);
            	 $(txt).parent('td').siblings().find('input[type=checkbox]').each(function(index,ele){
            		 var time = $(ele).val();
            		 var dateTime = currentdate+' '+time;
            		 $(ele).val(dateTime);
            	 })
             }
		}
		
		
         $(function () {
        	 var nowDate = $("#nowDate").val();
        	 
        	 currentDate(nowDate);
        	 
        	 var openid = $("#userOpenId").val();
        	 $("#dateTimeTable .theTr").each(function(trIndex,tr){
        		 var date = $(tr).find("td").eq(0).text();
        		 //alert(date);
        		 $.ajax({
 					type : 'post',
 					url : '${appRoot}/expert/seeExpertTime',
 					data : {"date":date,"openid":openid}, 
 					dataType : 'json',
 					success : function(data) {
 						if (data.msg == 0) {
 							$(tr).find("td").each(function(tdIndex,td){
 								var oneTime = $(td).attr("theExpertTime");
 								//专家的工作时间
 								$.each(data.expertTime,function(i,item){
 									if(oneTime == item.expertStartTime){
 										$(td).css("background-color","#f8aa1b");
 										$(td).find(".box").attr("checked","checked");
 										allCheckedValue += item.expertStartTime + ",";
 									}
 								});
 								//被预约的工作时间
 								$.each(data.expertOrders,function(i,item){
 									if(oneTime == item.expertStartTime){
 										if(item.userName!=undefined){
 											$(td).html(item.userName);
 											var msg = "姓名/昵称:" + item.userName +"   电话：" + item.mobile;
 											$(td).prop("title",msg);
 										}else{
 											$(td).html(item.nickname);
 											var msg = "姓名/昵称:" + item.nickname +"   电话：" + item.mobile;
 											$(td).prop("title",msg);
 										}
 										$(td).css("background-color","#68c862");
 										$(td).find(".box").hide();
 									}
 								});
 								
 			        		 });
 						} else {
 							windowShow("操作失败", "");
 						}
 					}
 				});
        	 })
        	 
         });
         
         
         
         //添加工作时间
         function addWorkTime(){
			var openid = $("#userOpenId").val();
			$("#formOne").submit();
         }
         
         
	</script>
	<input type="hidden" value="" id="adminId" />
	
</body>
</html>

