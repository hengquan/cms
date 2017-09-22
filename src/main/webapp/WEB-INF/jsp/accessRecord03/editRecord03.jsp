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

							<div class="tijiao" id="tijiao"
								style="height: 1.5rem; padding: 20px 0px; position: relative; top: 0px; background: white;">

								<input type="button" onclick="checkinput()" value="提交表单"
									class="btn-4"
									style="width: 100px; height: 30px; font-weight: bold; font-family: &amp; #39; 微软雅黑 &amp;#39;; margin-left: 10px">
							</div>


							<table id="orderinfo" cellpadding="0" cellspacing="0"
								bgcolor="#dddddd" border="1" style="margin-top:40px;">
								<tbody>
									<input type="hidden" name="id" value="${accessRecord03.id }" />
									<input type="hidden" name="custid" value="${accessRecord03.custid }" />
									<input type="hidden" name="projid" value="${accessRecord03.projid }" />
									<input type="hidden" name="userId" value="${userId }" />
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
											<input type="date" name="purchasedate1" value="${accessRecord03.purchasedate }" style="width: 10rem">
										<td>签约日期：
											<input type="date" name="signdate1" value="${accessRecord03.signdate }" style="width: 10rem">
									</tr>

									<tr>
										<td colspan="1">购买房号：
											<input type="text" name="housenum" value="${accessRecord03.housenum }" style="width: 7rem">
										</td>
										<td colspan="2">户籍：
											<input name="houseregitype" type="radio" value="京籍" <c:if test="${accessRecord03.houseregitype == '京籍'}">selected</c:if> style="width: 1rem">京籍 
											<input name="houseregitype" type="radio" value="外地" <c:if test="${accessRecord03.houseregitype == '外地'}">checked</c:if> style="width: 1rem">外地 
											<input name="houseregitype" type="radio" value="外国籍" <c:if test="${accessRecord03.houseregitype == '外国籍'}">selected</c:if> style="width: 1rem">外国籍
										</td>
									</tr>
									<tr>
										<td colspan="2">成交周期-到访：
											<input type="text" name="visitcycle" value="${accessRecord03.visitcycle }" style="width: 4rem">
											<span style="margin-right: 2rem">天</span> 成交周期-认购：
											<input type="text" name="purchasecycle" value="${accessRecord03.purchasecycle }" style="width: 4rem;">
											<span style="margin-right: 2rem">天</span> 成交周期-签约：
											<input type="text" name="signcycle" value="${accessRecord03.signcycle }" style="width: 4rem;">天
										</td>
										<td colspan="1">获知时间：
											<input type="date" name="firstknowtime1" value="${accessRecord03.firstknowtime }" style="width: 10rem">
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
										<td colspan="3">贷款银行：
											<input type="text" name="loanbank" value="${accessRecord03.loanbank }" style="width: 13rem">
										</td>
									</tr>
									<tr>
										<td colspan="3">您关注的产品类型：
											<span style="margin-left: 20px">别墅</span>
											（<input name="realtyproducttype" type="checkbox" value="009001001-独栋" _text="独栋" <c:if test="${fn:contains(accessRecord03.realtyproducttype, '独栋')}">checked</c:if> style="width: 1rem">独栋
											<input name="realtyproducttype" type="checkbox" value="009001002-类独栋" _text="类独栋" <c:if test="${fn:contains(accessRecord03.realtyproducttype, '类独栋')}">checked</c:if> style="width: 1rem">类独栋 
											<input name="realtyproducttype" type="checkbox" value="009001003-双拼" _text="双拼" <c:if test="${fn:contains(accessRecord03.realtyproducttype, '双拼')}">checked</c:if> style="width: 1rem">双拼 
											<input name="realtyproducttype" type="checkbox" value="009001004-联排" _text="联排" <c:if test="${fn:contains(accessRecord03.realtyproducttype, '联排')}">checked</c:if> style="width: 1rem">联排
											<input name="realtyproducttype" type="checkbox" value="009001005-上叠" _text="上叠" <c:if test="${fn:contains(accessRecord03.realtyproducttype, '上叠')}">checked</c:if> style="width: 1rem">上叠 
											<input name="realtyproducttype" type="checkbox" value="009001006-下叠" _text="下叠" <c:if test="${fn:contains(accessRecord03.realtyproducttype, '下叠')}">checked</c:if> style="width: 1rem">下叠&nbsp;&nbsp;&nbsp;&nbsp;）<br>
											<span style="margin-left: 145px">平层（</span>
											<input name="realtyproducttype" type="checkbox" value="009002001-两居及以下" _text="两居及以下"<c:if test="${fn:contains(accessRecord03.realtyproducttype, '两居及以下')}">checked</c:if> style="width: 1rem;">两居及以下 
											<input name="realtyproducttype" type="checkbox" value="009002002-三居" _text="三居" <c:if test="${fn:contains(accessRecord03.realtyproducttype, '三居')}">checked</c:if> style="width: 1rem">三居 
											<input name="realtyproducttype" type="checkbox" value="009002003-四居" _text="四居" <c:if test="${fn:contains(accessRecord03.realtyproducttype, '四居')}">checked</c:if> style="width: 1rem">四居
											<input name="realtyproducttype" type="checkbox" value="009002004-五居及以上叠" _text="五居及以上叠" <c:if test="${fn:contains(accessRecord03.realtyproducttype, '五居及以上叠')}">checked</c:if> style="width: 1rem">五居及以上叠&nbsp;&nbsp;&nbsp;&nbsp;）<br>
											<input name="realtyproducttype" type="checkbox" value="009003-商业" _text="商业" <c:if test="${fn:contains(accessRecord03.realtyproducttype, '商业')}">checked</c:if> style="width: 1rem; margin-left: 145px">商业 
											<input name="realtyproducttype" type="checkbox" value="00904-商业办公" _text="商业办公" <c:if test="${fn:contains(accessRecord03.realtyproducttype, '商务办公')}">checked</c:if> style="width: 1rem; margin-left: 16px">商务办公 
											<input name="realtyproducttype" type="checkbox" value="009999-其他" _text="其他" <c:if test="${fn:contains(accessRecord03.realtyproducttype, '其他')}">checked</c:if> style="width: 1rem">其他
											<input name="realtyproducttype" type="checkbox" value="009000-无法了解" _text="无法了解" <c:if test="${fn:contains(accessRecord03.realtyproducttype, '无法了解')}">checked</c:if> style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>

									<tr>
										<td colspan="3">通邮地址：
											<input type="text" name="addressmail" value="${accessRecord03.addressmail }" style="width: 60rem">
										</td>
									</tr>

									<tr>
										<td colspan="3">实际居住情况：
											<input name="livingstatus" type="radio" value="单身" <c:if test="${fn:contains(accessRecord03.livingstatus, '单身')}">checked</c:if> style="width: 1rem">单身 
											<input name="livingstatus" type="radio" value="夫妻" <c:if test="${fn:contains(accessRecord03.livingstatus, '夫妻')}">checked</c:if> style="width: 1rem">夫妻 
											<input name="livingstatus" type="radio" value="一孩家庭" <c:if test="${fn:contains(accessRecord03.livingstatus, '一孩家庭')}">checked</c:if> style="width: 1rem">一孩家庭
											<input name="livingstatus" type="radio" value="俩孩家庭" <c:if test="${fn:contains(accessRecord03.livingstatus, '俩孩家庭')}">checked</c:if> style="width: 1rem">俩孩家庭 
											<input name="livingstatus" type="radio" value="三孩及以上家庭" <c:if test="${fn:contains(accessRecord03.livingstatus, '三孩及以上家庭')}">checked</c:if> style="width: 1rem">三孩及以上家庭
											<input name="livingstatus" type="radio" value="三代同堂" <c:if test="${fn:contains(accessRecord03.livingstatus, '三代同堂')}">checked</c:if> style="width: 1rem">三代同堂 
											<input name="livingstatus" type="radio" value="无法了解" <c:if test="${fn:contains(accessRecord03.livingstatus, '无法了解')}">checked</c:if> style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>

									<tr>
										<td colspan="3">房屋使用人是谁：
											<input name="realusemen" type="checkbox" value="本人" <c:if test="${fn:contains(accessRecord03.realusemen, '本人')}">checked</c:if> style="width: 1rem" >本人 
											<input name="realusemen" type="checkbox" value="配偶" <c:if test="${fn:contains(accessRecord03.realusemen, '配偶')}">checked</c:if> style="width: 1rem" >配偶
											<input name="realusemen" type="checkbox" value="您的父母" <c:if test="${fn:contains(accessRecord03.realusemen, '您的父母')}">checked</c:if> style="width: 1rem" >您的父母
											<input name="realusemen" type="checkbox" value="您的子女" <c:if test="${fn:contains(accessRecord03.realusemen, '您的子女')}">checked</c:if> style="width: 1rem" >您的子女
											<input name="realusemen" type="checkbox" value="无法了解" <c:if test="${fn:contains(accessRecord03.realusemen, '无法了解')}">checked</c:if> style="width: 1rem; color: red" >
											<span style="color: red">无法了解</span>
										</td>
									</tr>

									<tr>
										<td colspan="3">房屋出资人是谁：
											<input name="realpaymen" type="checkbox" value="本人" <c:if test="${fn:contains(accessRecord03.realpaymen, '本人')}">checked</c:if> style="width: 1rem">本人
											<input name="realpaymen" type="checkbox" value="配偶" <c:if test="${fn:contains(accessRecord03.realpaymen, '配偶')}">checked</c:if> style="width: 1rem"  >配偶
											<input name="realpaymen" type="checkbox" value="您的父母" <c:if test="${fn:contains(accessRecord03.realpaymen, '您的父母')}">checked</c:if> style="width: 1rem"  >您的父母
											<input name="realpaymen" type="checkbox" value="您的子女" <c:if test="${fn:contains(accessRecord03.realpaymen, '您的子女')}">checked</c:if> style="width: 1rem"  >您的子女
											<input name="realpaymen" type="checkbox" value="无法了解" <c:if test="${fn:contains(accessRecord03.realpaymen, '无法了解')}">checked</c:if> style="width: 1rem; color: red">
											<span style="color: red">无法了解</span>
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
				url:'${appRoot}/wx/api/addTradeVisit',
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

