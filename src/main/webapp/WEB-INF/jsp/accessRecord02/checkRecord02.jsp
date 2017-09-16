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
;top


:


3
px
;width


:


73
px
;height


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
								<div class="col-lg-2">客户复访信息</div>
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
						style="border: 4px solid #f0f0f0; background-color: #fff; padding-top: 10px; border-top: 0px;">

						<form action="http://kylindpc.cn/zz_user.php" method="post"
							name="theForm" id="theform" onsubmit="return checkinput()">

							<table id="orderinfo" cellpadding="0" cellspacing="0"
								bgcolor="#dddddd" border="1" >
								<tbody>
									<tr style="">
										<input type="hidden" name="id" value="${accessRecord02.id }" />
										<input type="hidden" name="custid" value="${accessRecord02.custid }" />
										<input type="hidden" name="projid" value="${accessRecord02.projid }" />
										<input type="hidden" name="userId" value="${userId }" />
										<td style="width: 330px;">姓名：
											<input type="text" name="custname" value="${accessRecord02.custname }" style="width: 100px">
										</td>
										<td>联系方式：<input type="text" name="custphonenum"  value="${accessRecord02.custphonenum }"
											onblur="check_lianxi_ajax()" style="width: 100px">&nbsp;<span
											id="txt1" style="color: red"></span>
										</td>
										<td style="width: 270px">置业顾问：${name }</td>
									</tr>

									<tr>
										<td>本次到访时间：
											<input type="text" style="width: 10rem" name="receptime1" value="<fmt:formatDate value="${accessRecord02.receptime }" pattern="yyyy-MM-dd"/>">
										</td>
										<td>首次到访时间：
											<input type = "text" style="width: 10rem" name="firstknowtime1" value="<fmt:formatDate value="${accessRecord02.receptime }" pattern="yyyy-MM-dd"/>">
										</td>
										<td colspan="3">来访次数：第2次</td>
									</tr>

									<tr>
										<td colspan="3">未成年子女数量（小于18岁）：
										<input name="childrennum" type="text" value="${accessRecord02.childrennum }" style="width: 10rem" >
										</td>
									</tr>

									<tr>
										<td colspan="3" id="td1">孩子年龄段：
											<input name="childagegroup" type="text" value="${accessRecord02.childagegroup }"  style="width: 10rem">
										</td>
									</tr>

									<tr>
										<td colspan="2" id="td2">孩子在读学校类型：
											<input name="schooltype" type="text" value="${accessRecord02.schooltype }" style="width: 10rem" >
										</td>
										<td id="td3"><span id="box3" style="display: block;">在读学校名称：
											<input type="text" style="width: 9rem" name="schoolname" value="${accessRecord02.schoolname }"></span>
										</td>
									</tr>

									<tr>
										<td colspan="3">您的生活半径：
											<input name="livingradius" type="text" value="${accessRecord02.livingradius }"  style="width: 30rem">
										</td>
									</tr>

									<tr>
										<td colspan="3">现居住社区名称：
											<input type="text" value="${accessRecord02.communityname }" style="width: 30rem" name="communityname">
										</td>
									</tr>
									<tr>
										<td colspan="3">住房性质：
											<input type="text" value="${accessRecord02.communityname }" style="width: 30rem" name="communityname">
										</td>
									</tr>
									<tr>
										<td colspan="3">公司名称：
											<input type="text" value="${accessRecord02.communityname }" style="width: 30rem" name="communityname">
										</td>
									</tr>
									<tr>
										<td colspan="3">公司地址：
											<input type="text" value="${accessRecord02.communityname }" style="width: 30rem" name="communityname">
										</td>
									</tr>
									<tr>
										<td colspan="3">公司职务：
											<input type="text" value="${accessRecord02.communityname }" style="width: 30rem" name="communityname">
										</td>
									</tr>

									<tr>
										<td colspan="3">目前的居住面积：
											<input name="liveacreage" type="text" value="${accessRecord02.liveacreage }" style="width: 30rem"> 
										</td>
									</tr>


									<tr>
										<td colspan="3">贷款记录：
										<select name="daikuan">
											<option value="0">请选择</option>
											<option value="有贷款（住宅）">有贷款（住宅）</option>
											<option value="有贷款（商业）">有贷款（商业）</option>
											<option value="有贷款（住宅和商业）">有贷款（住宅和商业）</option>
											<option value="无贷款">无贷款</option>
											<option value="无法了解" style="color: red">无法了解</option>
										</select>

										</td>
									</tr>



									<tr>
										<td>全职太太：
											<c:if test="${accessRecord02.fulltimewifeflag == 1 }">
												<input name="fulltimewifeflag" type="text" value="是" style="width: 5rem">
											</c:if>
											<c:if test="${accessRecord02.fulltimewifeflag == 2 }">
												<input name="fulltimewifeflag" type="text" value="否" style="width: 5rem">
											</c:if>
											<c:if test="${accessRecord02.fulltimewifeflag == -1 }">
												<input name="fulltimewifeflag" type="text" value="无法了解" style="width: 5rem">
											</c:if>
										</td>
										<td>有保姆：
											<c:if test="${accessRecord02.nannyflag == 1 }">
												<input name="nannyflag" type="text" value="是" style="width: 5rem">
											</c:if>
											<c:if test="${accessRecord02.nannyflag == 2 }">
												<input name="nannyflag" type="text" value="否" style="width: 5rem">
											</c:if>
											<c:if test="${accessRecord02.nannyflag == -1 }">
												<input name="nannyflag" type="text" value="无法了解" style="width: 5rem">
											</c:if>
										
										</td>
										<td>有宠物：
											<c:if test="${accessRecord02.petflag == 1 }">
												<input name="nannyflag" type="text" value="是" style="width: 5rem">
											</c:if>
											<c:if test="${accessRecord02.petflag == 2 }">
												<input name="nannyflag" type="text" value="否" style="width: 5rem">
											</c:if>
											<c:if test="${accessRecord02.petflag == -1 }">
												<input name="nannyflag" type="text" value="无法了解" style="width: 5rem">
											</c:if>
										</td>
									</tr>

									<tr>
										<td>国际教育意愿：
											<c:if test="${accessRecord02.outeduwill == 1 }">
												<input name="nannyflag" type="text" value="是" style="width: 5rem">
											</c:if>
											<c:if test="${accessRecord02.outeduwill == 2 }">
												<input name="nannyflag" type="text" value="否" style="width: 5rem">
											</c:if>
											<c:if test="${accessRecord02.outeduwill == -1 }">
												<input name="nannyflag" type="text" value="无法了解" style="width: 5rem">
											</c:if>
										</td>
										<td>子女海外经历：
											<c:if test="${accessRecord02.childoutexperflag == 1 }">
												<input name="nannyflag" type="text" value="是" style="width: 5rem">
											</c:if>
											<c:if test="${accessRecord02.childoutexperflag == 2 }">
												<input name="nannyflag" type="text" value="否" style="width: 5rem">
											</c:if>
											<c:if test="${accessRecord02.childoutexperflag == -1 }">
												<input name="nannyflag" type="text" value="无法了解" style="width: 5rem">
											</c:if>
										</td>
										<td>业主海外经历：
											<c:if test="${accessRecord02.outexperflag == 1 }">
												<input name="nannyflag" type="text" value="是" style="width: 5rem">
											</c:if>
											<c:if test="${accessRecord02.outexperflag == 2 }">
												<input name="nannyflag" type="text" value="否" style="width: 5rem">
											</c:if>
											<c:if test="${accessRecord02.outexperflag == -1 }">
												<input name="nannyflag" type="text" value="无法了解" style="width: 5rem">
											</c:if>
										</td>
									</tr>
									<tr>
										<td>决策人是否到场：
											<input name="decisionerin" type="radio" value="0" <c:if test="${accessRecord02.decisionerin == 0 }">checked</c:if> style="width: 1rem">不在场 
											<input name="decisionerin" type="radio" value="1" <c:if test="${accessRecord02.decisionerin == 1 }">checked</c:if> style="width: 1rem">在场
										</td>
										<td></td>
										<td></td>
									</tr>

									<tr>
										<td>名下房产：<select name="yigou_num">
												<option value="0">请选择</option>
												<option value="1">1套</option>
												<option value="2">2套</option>
												<option value="3">3套</option>
												<option value="3套以上">3套以上</option>
												<option value="无房">无房</option>
												<option value="无法了解" style="color: red">无法了解</option>
										</select>


										</td>
										<td>家庭汽车数量：
											<input type="text" name="carfamilycount" style="width: 3rem" value="${accessRecord02.carfamilycount }">辆
										</td>
										<td>汽车品牌：
											<input type="text" name="carbrand" style="width: 9rem" value="${accessRecord02.carbrand }">
										</td>
									</tr>

									<tr>
										<td colspan="3">驾车总价：
											<input name="cartotalprice" type="text" value="${accessRecord02.cartotalprice }"  style="width: 30rem">
										</td>
									</tr>

									<tr>
										<td colspan="1">关注的公众微信号：
											<input type="text" style="width: 7rem" name="attentwx" value="${accessRecord02.attentwx }">
										</td>

										<td colspan="2">常用APP(除微信外三个)：
											<input type="text" style="width: 6rem" name="appnames" value=${accessRecord02.appnames }>
										</td>
									</tr>

									<tr>
										<td colspan="3">您的业余爱好：
											<input name="avocations" type="text" value="${accessRecord02.avocations }" style="width: 30rem">
										</td>
									</tr>
									<tr>
										<td colspan="3">孩子的课余爱好：
											<input name="childavocations" type="text" value="${accessRecord02.childavocations }"  style="width: 30rem">
										</td>
									</tr>

									<tr>
										<td colspan="3">您对本案的抗拒点：
											<input name="resistpoint" type="text" value="${accessRecord02.resistpoint }" style="width: 30rem"> 
										</td>
									</tr>

									<tr>
										<td colspan="3">您喜欢参加的活动：
											<input name="loveactivation" type="text" value="${accessRecord02.loveactivation }" style="width: 30rem">
										</td>
									</tr>

									<tr>
										<td colspan="3">可参加业主活动时间：
											<input name="freetimesection" type="text" value="${accessRecord02.freetimesection }" style="width: 55rem; margin-left: 5px">
										</td>
									</tr>


									<tr>
										<td colspan="3">来访人数：
											<input name="visitorcount" type="text" value="${accessRecord02.visitorcount }" style="width: 10rem" >
										</td>
									</tr>

									<tr>
										<td colspan="3">
											<span id="box4" style="display: block;">来访人之间关系：
											<input name="visitorrefs" type="text" value="${accessRecord02.visitorrefs }" style="width: 20rem"> 
										</span></td>
									</tr>

									<tr>
										<td colspan="3">本次参观接待时间：
											<input name="receptimesection" type="text" value="${accessRecord02.receptimesection }" style="width: 10rem">
										</td>
									</tr>
									
									<tr>
										<td colspan="3">家庭状况：
											<input type="text" value="${accessRecord02.familystatus }" style="width: 30rem">
										</td>
									</tr>
	
									<tr>
										<td colspan="3">您的出行方式：
											<input type="text" value="${accessRecord02.traffictype }" style="width: 70rem">
										</td>
									</tr>
	
									<tr>
										<td colspan="3">您的从事行业：
											<input type="text" value="${accessRecord02.workindustry }" style="width: 70rem">
										</td>
									</tr>
	
									<tr>
										<td colspan="3">您的企业性质：
											<input type="text" value="${accessRecord02.enterprisetype }" style="width: 70rem">
										</td>
									</tr>
	
									<tr>
										<td colspan="3">您关注的产品类型：
											<input type="text" value="${accessRecord02.realtyproducttype }" style="width: 70rem">
										</td>
									</tr>
	
									<tr>
										<td colspan="3">您关注区域面积：
											<input type="text" value="${accessRecord02.attentacreage }" style="width: 70rem">
										</td>
									</tr>
	
									<tr>
										<td colspan="3">您接受的总房款：
											<input type="text" value="${accessRecord02.pricesection }" style="width: 70rem">
										</td>
									</tr>
	
									<tr>
										<td colspan="3">您的购房目的：
											<input type="text" value="${accessRecord02.buypurpose }" style="width: 70rem">
										</td>
									</tr>
	
									<tr>
										<td colspan="3">认知本案渠道：
											<input type="text" value="${accessRecord02.knowway }" style="width: 70rem">
										</td>
									</tr>
	
									<tr>
										<td colspan="3">您对本案的关注点：
											<input type="text" value="${accessRecord02.attentionpoint }" style="width: 70rem">
										</td>
									</tr>
	
									<tr>
										<td colspan="3">预估身价：
											<input type="text" value="${accessRecord02.estcustworth }" style="width: 70rem">
										</td>
									</tr>
	
	
	
	
									<tr>
										<td colspan="3">重点投资：
											<input type="text" value="${accessRecord02.investtype }" style="width: 70rem">
										</td>
									</tr>
	
									<tr>
										<td colspan="3">资金筹备期：
											<input type="text" value="${accessRecord02.capitalprepsection }" style="width: 70rem">
										</td>
									</tr>

									<tr style="display: none">
										<td colspan="3">您目前进行比选项目有：<br>
										<textarea style="width: 800px; height: 100px; margin-left: 0.1rem"
												name="bijiao"></textarea>
										</td>
									</tr>

									<tr>
										<td colspan="3">复访接待描述：<br>
										<textarea style="width: 800px; height: 100px; margin-left: 0.1rem"
												name="descn">${accessRecord02.descn }</textarea>
										</td>
									</tr>

									<tr>
										<td colspan="3">复访未成交原因分析：<br>
										<textarea style="width: 800px; height: 100px; margin-left: 0.1rem"
												name="faultdescn">${accessRecord02.faultdescn }</textarea>
										</td>
									</tr>

									<tr>
										<td colspan="3">客户评级：
											<input name="custscore" type="text" value="${accessRecord02.custscore }" style="width: 1rem"> 
										</td>
									</tr>
								</tbody>
							</table>

							<hr/>
							<input type="hidden" id="accessRecord02Id" value="${accessRecord02.id }">
							<div style="width: 100px; height: 30px; font-weight: bold; font-family: &amp; #39; 微软雅黑 &amp;#39;; margin-left: 30px">审核操作：</div>
							<span class="tijiao" id="tijiao"
								style="height: 1.5rem; padding: 20px 0px; position: relative; top: 0px; background: white;margin-bottom: 40px;">
								<input type="reset" onclick="subCheckStateMessage('2')" value="通过"
									class="btn-4"
									style="width: 100px; height: 30px; font-weight: bold; font-family: &amp; #39; 微软雅黑 &amp;#39;; margin-left: 20px">
								<input type="button" onclick="tuihui('4')" value="退回"
									class="btn-4"
									style="width: 100px; height: 30px; font-weight: bold; font-family: &amp; #39; 微软雅黑 &amp;#39;; margin-left: 10px">
								<!-- <input type="button" onclick="subCheckStateMessage('3')" value="作废"
									class="btn-4"
									style="width: 100px; height: 30px; font-weight: bold; font-family: &amp; #39; 微软雅黑 &amp;#39;; margin-left: 10px">
							 -->
							</span>

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




	<div class="modal fade" id="seeOneCheckMessage" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="modal-title">意见信息</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form" id="itemForm" name="itemForm" >
							<input type="hidden" name="editId" id="editId">
							<div class="form-group">
								<div class="col-lg-12">
									<textarea rows="5" cols="60" class="form-control" id="checkTextContent"></textarea>
								</div>
							</div>
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button data-dismiss="modal" type="button" class="btn btn-send" onclick="subCheckStateMessage(4)">提交</button>
									<button data-dismiss="modal" type="button" id="quxiao" class="btn btn-send">返回</button>
								</div>
							</div>
						</form>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>





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

		
		
		//退回
		function tuihui(type){
			var $modal = $('#seeOneCheckMessage');
			$modal.modal();
		}
		
		
		//设置审核的状态---确认提交
		function subCheckStateMessage(state){
			//state  2 审核通过   3作废
			var checkedId = $("#accessRecord02Id").val();
			var checkContent = $("#checkTextContent").val();
			$.ajax({
				type:'post',
				data : {"checkedId":checkedId,"state":state,"checkContent":checkContent},  
				url:'${appRoot}/accessRecord/recheckRecordCheck',
				dataType:'json',
				success:function(data){
					if(data.msg == 100){
						windowShow("提交成功","");
						window.location.href="${appRoot}/accessRecord/recheckRecord";
					}else{
						windowShow("提交失败","");
					}
				}
			});
			
		}
		
		
		
		
		//提交表单
		function checkinput() {
			var datamsg = $("#theform").serialize();
			$.ajax({
				type:'post',
				data: datamsg, 
				url:'${appRoot}/wx/api/updateRecord02',
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

