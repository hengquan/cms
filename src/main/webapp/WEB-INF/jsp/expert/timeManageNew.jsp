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
	td.settime{
		background-color:#f8aa1b;
	}  
	td.yytime{
		background-color:#68c862;
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
							<c:forEach items="${dateList}" var="u" varStatus="s">
								<tr class="odd gradeX theTr">
									<td style="background-color:#FFFFFF;"><b id="today">${u.dateKey}</b></td>
									<c:choose>
										<c:when test="${fn:contains(u.times,'9:00-10:00')}">
											<c:choose>
												<c:when test="${fn:contains(u.ordertimes,'9:00-10:00')}">  
													<c:forEach items="${u.expertOrders}" var="order" varStatus="ss">
														<c:if test="${order.expertStartTime == '9:00-10:00'}">
															<td class="yytime" title=${order.mobile }>${order.nickname }</td>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise> 
													<td class="settime"><input type="checkbox" name="box" checked="checked" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 9:00-10:00" /></td>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise> 
											<td><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 9:00-10:00" /></td>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${fn:contains(u.times,'10:00-11:00')}">
											<c:choose>
												<c:when test="${fn:contains(u.ordertimes,'10:00-11:00')}">   
													<c:forEach items="${u.expertOrders}" var="order" varStatus="ss">
														<c:if test="${order.expertStartTime == '10:00-11:00'}">
															<td class="yytime" title=${order.mobile }>${order.nickname }</td>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise> 
													<td class="settime"><input type="checkbox" name="box" checked="checked" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 10:00-11:00" /></td>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise> 
											<td><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 10:00-11:00" /></td>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${fn:contains(u.times,'11:00-12:00')}">
											<c:choose>
												<c:when test="${fn:contains(u.ordertimes,'11:00-12:00')}"> 
													<c:forEach items="${u.expertOrders}" var="order" varStatus="ss">
														<c:if test="${order.expertStartTime == '11:00-12:00'}">
															<td class="yytime" title=${order.mobile }>${order.nickname }</td>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise> 
													<td class="settime"><input type="checkbox" name="box" checked="checked" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 11:00-12:00" /></td>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise> 
											<td><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 11:00-12:00" /></td>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${fn:contains(u.times,'12:00-13:00')}">
											<c:choose>
												<c:when test="${fn:contains(u.ordertimes,'12:00-13:00')}"> 
													<c:forEach items="${u.expertOrders}" var="order" varStatus="ss">
														<c:if test="${order.expertStartTime == '12:00-13:00'}">
															<td class="yytime" title=${order.mobile }>${order.nickname }</td>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise> 
													<td class="settime"><input type="checkbox" name="box" checked="checked" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 12:00-13:00" /></td>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise> 
											<td><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 12:00-13:00" /></td>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${fn:contains(u.times,'13:00-14:00')}">
											<c:choose>
												<c:when test="${fn:contains(u.ordertimes,'13:00-14:00')}"> 
													<c:forEach items="${u.expertOrders}" var="order" varStatus="ss">
														<c:if test="${order.expertStartTime == '13:00-14:00'}">
															<td class="yytime" title=${order.mobile }>${order.nickname }</td>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise> 
													<td class="settime"><input type="checkbox" name="box" checked="checked" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 13:00-14:00" /></td>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise> 
											<td><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 13:00-14:00" /></td>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${fn:contains(u.times,'14:00-15:00')}">
											<c:choose>
												<c:when test="${fn:contains(u.ordertimes,'14:00-15:00')}">  
													<c:forEach items="${u.expertOrders}" var="order" varStatus="ss">
														<c:if test="${order.expertStartTime == '14:00-15:00'}">
															<td class="yytime" title=${order.mobile }>${order.nickname }</td>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise> 
													<td class="settime"><input type="checkbox" name="box" checked="checked" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 14:00-15:00" /></td>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise> 
											<td><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 14:00-15:00" /></td>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${fn:contains(u.times,'15:00-16:00')}">
											<c:choose>
												<c:when test="${fn:contains(u.ordertimes,'15:00-16:00')}"> 
													<c:forEach items="${u.expertOrders}" var="order" varStatus="ss">
														<c:if test="${order.expertStartTime == '15:00-16:00'}">
															<td class="yytime" title=${order.mobile }>${order.nickname }</td>
														</c:if>
													</c:forEach> 
												</c:when>
												<c:otherwise> 
													<td class="settime"><input type="checkbox" name="box" checked="checked" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 15:00-16:00" /></td>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise> 
											<td><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 15:00-16:00" /></td>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${fn:contains(u.times,'16:00-17:00')}">
											<c:choose>
												<c:when test="${fn:contains(u.ordertimes,'16:00-17:00')}">  
													<c:forEach items="${u.expertOrders}" var="order" varStatus="ss">
														<c:if test="${order.expertStartTime == '16:00-17:00'}">
															<td class="yytime" title=${order.mobile }>${order.nickname }</td>
														</c:if>
													</c:forEach> 
												</c:when>
												<c:otherwise> 
													<td class="settime"><input type="checkbox" name="box" checked="checked" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 16:00-17:00" /></td>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise> 
											<td><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 16:00-17:00" /></td>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${fn:contains(u.times,'17:00-18:00')}">
											<c:choose>
												<c:when test="${fn:contains(u.ordertimes,'17:00-18:00')}">  
													<c:forEach items="${u.expertOrders}" var="order" varStatus="ss">
														<c:if test="${order.expertStartTime == '17:00-18:00'}">
															<td class="yytime" title=${order.mobile }>${order.nickname }</td>
														</c:if>
													</c:forEach> 
												</c:when>
												<c:otherwise> 
													<td class="settime"><input type="checkbox" name="box" checked="checked" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 17:00-18:00" /></td>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise> 
											<td><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 17:00-18:00" /></td>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${fn:contains(u.times,'18:00-19:00')}">
											<c:choose>
												<c:when test="${fn:contains(u.ordertimes,'18:00-19:00')}">
													<c:forEach items="${u.expertOrders}" var="order" varStatus="ss">
														<c:if test="${order.expertStartTime == '18:00-19:00'}">
															<td class="yytime" title=${order.mobile }>${order.nickname }</td>
														</c:if>
													</c:forEach> 
												</c:when>
												<c:otherwise> 
													<td class="settime"><input type="checkbox" name="box" checked="checked" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 18:00-19:00" /></td>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise> 
											<td><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 18:00-19:00" /></td>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${fn:contains(u.times,'19:00-20:00')}">
											<c:choose>
												<c:when test="${fn:contains(u.ordertimes,'19:00-20:00')}"> 
													<c:forEach items="${u.expertOrders}" var="order" varStatus="ss">
														<c:if test="${order.expertStartTime == '19:00-20:00'}">
															<td class="yytime" title=${order.mobile }>${order.nickname }</td>
														</c:if>
													</c:forEach>   
												</c:when>
												<c:otherwise> 
													<td class="settime"><input type="checkbox" name="box" checked="checked" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 19:00-20:00" /></td>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise> 
											<td><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 19:00-20:00" /></td>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${fn:contains(u.times,'20:00-21:00')}">
											<c:choose>
												<c:when test="${fn:contains(u.ordertimes,'20:00-21:00')}">  
													<c:forEach items="${u.expertOrders}" var="order" varStatus="ss">
														<c:if test="${order.expertStartTime == '20:00-21:00'}">
															<td class="yytime" title=${order.mobile }>${order.nickname }</td>
														</c:if>
													</c:forEach> 
												</c:when>
												<c:otherwise> 
													<td class="settime"><input type="checkbox" name="box" checked="checked" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 20:00-21:00" /></td>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise> 
											<td><input type="checkbox" name="box" class="group-checkable box" data-set="#sample_1 .checkboxes" value="${u.dateKey} 20:00-21:00" /></td>
										</c:otherwise>
									</c:choose>
									</c:forEach>
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
		/* function currentDate(nowDate){
			
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
		 */
		
        /*  $(function () {
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
          */
         
         
         //添加工作时间
         function addWorkTime(){
			var openid = $("#userOpenId").val();
			$("#formOne").submit();
         }
         
         
	</script>
	<input type="hidden" value="" id="adminId" />
	
</body>
</html>

