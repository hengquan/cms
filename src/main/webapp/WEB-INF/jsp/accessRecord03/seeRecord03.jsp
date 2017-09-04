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
<link rel="shortcut icon" href="http://kylindpc.cn/favicon.ico">
<link rel="icon" href="http://kylindpc.cn/animated_favicon.gif"
	type="image/gif">
<title>${appTitle}</title>


<style>
.td1 a {
	margin-right: 13px
}

#orderinfo td {
	text-align: left;
	padding-left: 10px;
	background: white;
	height: 28px;
	line-height: 28px;
	font-size: 14px;
	font-family: '微软雅黑';
}

#orderinfo input[type=text] {
	border: 0;
	border-bottom: 1px solid #000000;
	width: 390px;
	margin-left: 10px
}

#orderinfo input[type=radio] {
	margin-left: 25px
}

#orderinfo input[type=checkbox] {
	margin-left: 25px
}

#orderinfo textarea {
	margin-bottom: 6px
}

{
position




:absolute




;
border




:




1
px


 


solid


 


#DDD




;
background




:




#fff




;
-moz-box-shadow




:




0
0
10
px


 


rgba




(0
,
0,0,
.12




);
-webkit-box-shadow




:




0
0
10
px


 


rgba




(0
,
0,0,
.12




);
box-shadow




:




0
0
10
px


 


rgba




(0
,
0,0,
.12




);
z-index




:




-1;
left




:




1115
px


;
top




:




3
px


;
width




:




73
px


;
height




:




28
px




}
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

.biaoqian a {
	float: left;
	width: 140px;
	height: 50px;
	text-align: center;
	line-height: 50px;
	font-size: 16px;
	color: #FFFFFF;
	margin-right: 20px
}

.biaoqian a:hover {
	text-decoration: none;
	background: red;
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
							<header class="panel-heading" style="height: 50px;">

								<div class="col-lg-2">客户成交信息</div>
								<a href="javascript:history.go(-1)"
									class="btn btn-send mini btn-white col-lg-1 pull-right"
									style="align: right;"><i
									class="glyphicon glyphicon-arrow-left"></i></a>

							</header>
						</section>
					</div>
				</div>



				<div>
					<div
						style="border: 4px solid #f0f0f0; background-color: #fff; padding-top: 0px; border-top: 0px; position: relative; padding-top: 10px">

						<form action="http://kylindpc.cn/zz_user.php" method="post"
							name="theForm" id="theform" onsubmit="return checkinput()">



							<table id="orderinfo" cellpadding="0" cellspacing="0"
								bgcolor="#dddddd" border="1" style="margin-top:0px;">
								<tbody>
									<tr style="">
										<td style="width: 330px;">房屋买卖人姓名：<input type="text"
											name="xingming" value="${accessRecord03.custname }" style="width: 10rem"></td>
										<td style="width: 320px;">性别：<input name="sex"
											type="text" value="${accessRecord03.custsex }" style="width: 10rem" >
										</td>
										<td style="width: 270px">置业顾问：${name }</td>
									</tr>

									<tr>
										<td>联系方式：
											<input type="text" name="custphonenum" value="${accessRecord03.custphonenum }" style="width: 10rem"></td>
										<td>认购日期：
											<input type="text" name="rengou_time" value="2017-07-15" style="width: 10rem"></td>
										<td>签约日期：
											<input type="text" name="qianyue_time" value="2017-07-15" style="width: 10rem"></td>
									</tr>

									<tr>
										<td colspan="1">购买房号：
											<input type="text" name="housenum" value="${accessRecord03.housenum }" style="width: 7rem">
										</td>
										<td colspan="2">户籍：
											<input name="houseregitype" type="text" value="${accessRecord03.houseregitype}" style="width: 10rem">
										</td>
									</tr>

									<tr>
										<td colspan="2">成交面积：
											<input type="text" name="houseacreage" value="${accessRecord03.houseacreage }" style="width: 4rem">
											<span style="margin-right: 2rem">平米</span> 成交单价：
											<input type="text" name="unitprice" value="${accessRecord03.unitprice }" style="width: 4rem;">
											<span style="margin-right: 2rem">万</span> 成交总价：
											<input type="text" name="totalprice" value="${accessRecord03.totalprice }" style="width: 4rem;">万
										</td>
										<td>付款方式：
											<select name="fukuan">
												<option value="0">请选择</option>
												<option value="银行贷款（首套按揭）">银行贷款（首套按揭）</option>
												<option value="银行贷款（二套按揭）">银行贷款（二套按揭）</option>
												<option value="分期">分期</option>
												<option value="一次性">一次性</option>
												<option value="公积金贷款">公积金贷款</option>
												<option value="无法了解" style="color: red">无法了解</option>
											</select>
										</td>
									</tr>

									<tr>
										<td colspan="3">您关注的产品类型：
											<input name="realtyproducttype" type="text" value="${accessRecord03.realtyproducttype}" style="width: 10rem"> 
										</td>
									</tr>

									<tr>
										<td colspan="3">通邮地址：
											<input type="text" name="addressmail" value="${accessRecord03.addressmail }" style="width: 11rem">
										</td>
									</tr>

									<tr>
										<td colspan="3">实际居住情况：
											<input name="livingstatus" type="text" value="${accessRecord03.livingstatus}" style="width: 10rem">
										</td>
									</tr>

									<tr>
										<td colspan="3">房屋使用人是谁：
											<input name="realusemen" type="text" value="${accessRecord03.realusemen}" style="width: 10rem" > 
										</td>
									</tr>

									<tr>
										<td colspan="3">房屋出资人是谁：
											<input name="realpaymen" type="text" value="${accessRecord03.realpaymen}" style="width: 10rem">
										</td>
									</tr>

									<tr>
										<td colspan="3">客户对项目的意见及建议：<br> 
										<textarea style="width: 800px; height: 100px; margin-left: 0.1rem"
												name="suggestion">${accessRecord03.suggestion }</textarea>
										</td>
									</tr>

									<tr>
										<td colspan="3">客户谈判过程中遇到的问题及解决方案：<br> 
											<textarea style="width: 800px; height: 100px; margin-left: 0.1rem"
												name="talkqands">${accessRecord03.talkqands }</textarea>
										</td>
									</tr>

									<tr>
										<td colspan="3">客户签约过程中遇到的问题及解决方案：<br> 
											<textarea style="width: 800px; height: 100px; margin-left: 0.1rem"
												name="signqands">${accessRecord03.signqands }</textarea>
										</td>
									</tr>

									<tr>
										<td colspan="3">对客户的综合描述：<br> 
											<textarea style="width: 800px; height: 100px; margin-left: 0.1rem"
												name="sumdescn">${accessRecord03.sumdescn }</textarea>
										</td>
									</tr>

								</tbody>
							</table>


						</form>
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

	<form action="${appRoot}/expert/delCheckMessage" method="post"
		id="deleForm" name="deleForm">
		<input type="hidden" value="${openid}" name="expertOpenid"> <input
			type="hidden" name="byid" id="byid"> <input type="hidden"
			name="boxeditId" id="boxeditId">
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

		//提交表单
		function checkinput() {
			var datamsg = $("#theform").serialize();
			$.ajax({
				type:'post',
				data: datamsg, 
				url:'${appRoot}/wx/api/updateRecord01',
				dataType:'json',
				success:function(data){
					if(data.msg==100){
						windowShow("修改成功","");
					}else{
						windowShow("操作失败","");
					}
				}
			});
		}

	</script>
	<input type="hidden" value="" id="adminId" />
</body>
</html>

