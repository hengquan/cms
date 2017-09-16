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

							<div class="tijiao" id="tijiao"
								style="background: white; height: 1.5rem; padding: 20px 0; position: relative;">
								<!-- <input type="button" onclick="chongzhi()" value="重置表单"
									class="btn-4"
									style="width: 100px; height: 30px; font-weight: bold; font-family: &amp; #39; 微软雅黑 &amp;#39;; margin-left: 20px">
								 -->
								<input type="button" onclick="checkinput()" value="提交表单"
									class="btn-4"
									style="width: 100px; height: 30px; font-weight: bold; font-family: &amp; #39; 微软雅黑 &amp;#39;; margin-left: 10px">
							</div>


							<table id="orderinfo" cellpadding="0" cellspacing="0"
								bgcolor="#dddddd" border="1" style="margin-top:40px;">
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
											<input type="date" name="receptime1" value="<fmt:formatDate value="${accessRecord02.receptime }" pattern="yyyy-MM-dd"/>">
										</td>
										<td>首次到访时间：
											<input type = "date" name="firstknowtime1" value="<fmt:formatDate value="${accessRecord02.receptime }" pattern="yyyy-MM-dd"/>">
										</td>
										<td colspan="3">来访次数：第2次</td>
									</tr>

									<tr>
										<td colspan="3">未成年子女数量（小于18岁）：
										<input name="childrennum" type="radio" value="0" <c:if test="${fn:contains(accessRecord02.childrennum, '0')}">checked</c:if> style="width: 1rem" >0位 
										<input name="childrennum" type="radio" value="1" <c:if test="${fn:contains(accessRecord02.childrennum, '1')}">checked</c:if> style="width: 1rem" >1位 
										<input name="childrennum" type="radio" value="2" <c:if test="${fn:contains(accessRecord02.childrennum, '2')}">checked</c:if> style="width: 1rem" >2位 
										<input name="childrennum" type="radio" value="3" <c:if test="${fn:contains(accessRecord02.childrennum, '3')}">checked</c:if> style="width: 1rem" >3位 
										<input name="childrennum" type="radio" value="-1" <c:if test="${fn:contains(accessRecord02.childrennum, '-1')}">checked</c:if> style="width: 1rem" >
										<span style="color: red">无法了解</span>
										</td>
									</tr>

									<tr>
										<td colspan="3" id="td1"><span id="box1" style="display: block;">孩子年龄段：
											<input name="childagegroup" type="checkbox" value="020001-0~3岁" <c:if test="${fn:contains(accessRecord02.childagegroup, '0~3岁')}">checked</c:if> style="width: 1rem">0-3岁
											<input name="childagegroup" type="checkbox" value="020002-4~6岁" <c:if test="${fn:contains(accessRecord02.childagegroup, '4~6岁')}">checked</c:if> style="width: 1rem">4-6岁
											<input name="childagegroup" type="checkbox" value="020003-7~12岁" <c:if test="${fn:contains(accessRecord02.childagegroup, '7~12岁')}">checked</c:if> style="width: 1rem">7-12岁
											<input name="childagegroup" type="checkbox" value="020004-13~15岁" <c:if test="${fn:contains(accessRecord02.childagegroup, '13~15岁')}">checked</c:if> style="width: 1rem">13-15岁
											<input name="childagegroup" type="checkbox" value="020005-16~18岁" <c:if test="${fn:contains(accessRecord02.childagegroup, '16~18岁')}">checked</c:if> style="width: 1rem">16-18岁
											<input name="childagegroup" type="checkbox" value="020006-18岁以上" <c:if test="${fn:contains(accessRecord02.childagegroup, '18岁以上')}">checked</c:if> style="width: 1rem">18岁以上
											<input name="childagegroup" type="checkbox" value="020000-无法了解" <c:if test="${fn:contains(accessRecord02.childagegroup, '无法了解')}">checked</c:if> style="width: 1rem; color: red">
											<span style="color: red">无法了解</span></span>
										</td>
									</tr>

									<tr>
										<td colspan="2" id="td2"><span id="box2" style="display: block;">孩子在读学校类型：
											<input name="schooltype" type="checkbox" value="020001-公立" <c:if test="${fn:contains(accessRecord02.schooltype, '公立')}">checked</c:if> style="width: 1rem" >公立
											<input name="schooltype" type="checkbox" value="020002-私立" <c:if test="${fn:contains(accessRecord02.schooltype, '私立')}">checked</c:if> style="width: 1rem" >私立
											<input name="schooltype" type="checkbox" value="020003-国际学校" <c:if test="${fn:contains(accessRecord02.schooltype, '国际学校')}">checked</c:if> style="width: 1rem" >国际学校
											<input name="schooltype" type="checkbox" value="020004-国外学习" <c:if test="${fn:contains(accessRecord02.schooltype, '国外学习')}">checked</c:if> style="width: 1rem" >国外学习
											<input name="schooltype" type="checkbox" value="020000-无法了解" <c:if test="${fn:contains(accessRecord02.schooltype, '无法了解')}">checked</c:if> style="width: 1rem; color: red" >
											<span style="color: red">无法了解</span></span>
										</td>
										<td id="td3"><span id="box3" style="display: block;">在读学校名称：
											<input type="text" style="width: 9rem" name="schoolname" value="${accessRecord02.schoolname }"></span>
										</td>
									</tr>

									<tr>
										<td colspan="3">您的生活半径：
											<input name="livingradius" type="checkbox" value="022001-二环以内" <c:if test="${fn:contains(accessRecord02.livingradius, '二环以内')}">checked</c:if> style="width: 1rem">二环以内
											<input name="livingradius" type="checkbox" value="022002-二环~三环" <c:if test="${fn:contains(accessRecord02.livingradius, '二环-三环')}">checked</c:if> style="width: 1rem">二环-三环 
											<input name="livingradius" type="checkbox" value="022003-三环~四环 " <c:if test="${fn:contains(accessRecord02.livingradius, '三环-四环')}">checked</c:if> style="width: 1rem">三环-四环
											<input name="livingradius" type="checkbox" value="022004-四环~五环" <c:if test="${fn:contains(accessRecord02.livingradius, '四环-五环')}">checked</c:if> style="width: 1rem">四环-五环 
											<input name="livingradius" type="checkbox" value="022005-五环以外" <c:if test="${fn:contains(accessRecord02.livingradius, '五环以外')}">checked</c:if> style="width: 1rem">五环以外
											<input name="livingradius" type="checkbox" value="022000-无法了解" <c:if test="${fn:contains(accessRecord02.livingradius, '无法了解')}">checked</c:if> style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>

									<tr>
										<td colspan="3">现居住社区名称：
											<input type="text" value="${accessRecord02.communityname }" style="width: 30rem" name="communityname">
										</td>
									</tr>
									<%-- <tr>
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
									</tr> --%>

									<tr>
										<td colspan="3">目前的居住面积：
											<input name="liveacreage" type="radio" value="023001-100㎡以下" <c:if test="${fn:contains(accessRecord02.liveacreage, '100㎡以下')}">checked</c:if> style="width: 1rem">100㎡以下 
											<input name="liveacreage" type="radio" value="023002-100~200㎡" <c:if test="${fn:contains(accessRecord02.liveacreage, '100-200㎡')}">checked</c:if> style="width: 1rem">100-200㎡ 
											<input name="liveacreage" type="radio" value="023003-200~300㎡ " <c:if test="${fn:contains(accessRecord02.liveacreage, '200-300㎡')}">checked</c:if> style="width: 1rem">200-300㎡
											<input name="liveacreage" type="radio" value="023004-300~400㎡" <c:if test="${fn:contains(accessRecord02.liveacreage, '300-400㎡ ')}">checked</c:if> style="width: 1rem">300-400㎡ 
											<input name="liveacreage" type="radio" value="023005-400~500㎡" <c:if test="${fn:contains(accessRecord02.liveacreage, '400-500㎡')}">checked</c:if> style="width: 1rem">400-500㎡
											<input name="liveacreage" type="radio" value="023008-500㎡以上" <c:if test="${fn:contains(accessRecord02.liveacreage, '500㎡以上')}">checked</c:if> style="width: 1rem">500㎡以上 
											<input name="liveacreage" type="radio" value="023000-无法了解" <c:if test="${fn:contains(accessRecord02.liveacreage, '无法了解')}">checked</c:if> style="width: 1rem">
											<span style="color: red">无法了解</span>
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
											<input name="fulltimewifeflag" type="radio" value="1" <c:if test="${accessRecord02.fulltimewifeflag == 1 }">checked</c:if> style="width: 1rem">是 
											<input name="fulltimewifeflag" type="radio" value="2" <c:if test="${accessRecord02.fulltimewifeflag == 2 }">checked</c:if> style="width: 1rem">否 
											<input name="fulltimewifeflag" type="radio" value="-1" <c:if test="${accessRecord02.fulltimewifeflag == -1 }">checked</c:if> style="width: 1rem">
											<span style="color: red">无解</span>
										</td>
										<td>有保姆：<input name="nannyflag" type="radio" value="1" <c:if test="${accessRecord02.nannyflag == 1 }">checked</c:if> style="width: 1rem">是 
											<input name="nannyflag" type="radio" value="2" <c:if test="${accessRecord02.nannyflag == 2 }">checked</c:if> style="width: 1rem">否
											<input name="nannyflag" type="radio" value="-1" <c:if test="${accessRecord02.nannyflag == -1 }">checked</c:if> style="width: 1rem">
											<span style="color: red">无解</span>
										</td>
										<td>有宠物：
											<input name="petflag" type="radio" value="1" <c:if test="${accessRecord02.petflag == 1 }">checked</c:if> style="width: 1rem">是 
											<input name="petflag" type="radio" value="2" <c:if test="${accessRecord02.petflag == 2 }">checked</c:if> style="width: 1rem">否 
					 						<input name="petflag" type="radio" value="-1" <c:if test="${accessRecord02.petflag == -1 }">checked</c:if> style="width: 1rem">
											<span style="color: red">无解</span>
										</td>
									</tr>

									<tr>
										<td>国际教育意愿：
											<input name="outeduwill" type="radio" value="1" <c:if test="${accessRecord02.outeduwill == 1 }">checked</c:if> style="width: 1rem">是 
											<input name="outeduwill" type="radio" value="2" <c:if test="${accessRecord02.outeduwill == 2 }">checked</c:if> style="width: 1rem">否
											<input name="outeduwill" type="radio" value="-1" <c:if test="${accessRecord02.outeduwill == -1 }">checked</c:if> style="width: 1rem">
											<span style="color: red">无解</span>
										</td>
										<td>子女海外经历：
											<input name="childoutexperflag" type="radio" value="1" <c:if test="${accessRecord02.childoutexperflag == 1 }">checked</c:if> style="width: 1rem">是 
											<input name="childoutexperflag" type="radio" value="2" <c:if test="${accessRecord02.childoutexperflag == 2 }">checked</c:if> style="width: 1rem">否
											<input name="childoutexperflag" type="radio" value="-1" <c:if test="${accessRecord02.childoutexperflag == -1 }">checked</c:if> style="width: 1rem">
											<span style="color: red">无解</span>
										</td>
										<td>业主海外经历：
											<input name="outexperflag" type="radio" value="1" <c:if test="${accessRecord02.outexperflag == 1 }">checked</c:if> style="width: 1rem">是 
											<input name="outexperflag" type="radio" value="2" <c:if test="${accessRecord02.outexperflag == 2 }">checked</c:if> style="width: 1rem">否
											<input name="outexperflag" type="radio" value="-1" <c:if test="${accessRecord02.outexperflag == -1 }">checked</c:if> style="width: 1rem">
											<span style="color: red">无解</span>
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
											<input name="cartotalprice" type="radio" value="025001-30万以下" <c:if test="${fn:contains(accessRecord02.cartotalprice, '30万以下')}">checked</c:if> style="width: 1rem">30万以下 
											<input name="cartotalprice" type="radio" value="025002-30~50万" <c:if test="${fn:contains(accessRecord02.cartotalprice, '30~50万')}">checked</c:if> style="width: 1rem">30-50万
											<input name="cartotalprice" type="radio" value="025003-50~100万 " <c:if test="${fn:contains(accessRecord02.cartotalprice, '50~100万')}">checked</c:if> style="width: 1rem">50-100万 
											<input name="cartotalprice" type="radio" value="025004-100~200万" <c:if test="${fn:contains(accessRecord02.cartotalprice, '100~200万')}">checked</c:if> style="width: 1rem">100-200万
											<input name="cartotalprice" type="radio" value="025005-200~300万" <c:if test="${fn:contains(accessRecord02.cartotalprice, '200~300万')}">checked</c:if> style="width: 1rem">200万以上 
											<input name="cartotalprice" type="radio" value="025006-300~500万" <c:if test="${fn:contains(accessRecord02.cartotalprice, '300~500万')}">checked</c:if> style="width: 1rem">200-300万<br> 
											<input name="cartotalprice" type="radio" value="025007-500万以上" <c:if test="${fn:contains(accessRecord02.cartotalprice, '500万以上')}">checked</c:if> style="width: 1rem; margin-left: 95px">300-500万 
											<input name="cartotalprice" type="radio" value="025000-无法了解" <c:if test="${fn:contains(accessRecord02.cartotalprice, '无法了解')}">checked</c:if> style="width: 1rem">
											<span style="color: red">无法了解</span>
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
											<input name="avocations" type="checkbox" value="026001-运动" <c:if test="${fn:contains(accessRecord02.avocations, '运动')}">checked</c:if> style="width: 1rem">运动 
											<input name="avocations" type="checkbox" value="026002-养生" <c:if test="${fn:contains(accessRecord02.avocations, '养生')}">checked</c:if> style="width: 1rem">养生
											<input name="avocations" type="checkbox" value="026003-美容" <c:if test="${fn:contains(accessRecord02.avocations, '步行')}">checked</c:if> style="width: 1rem">美容 
											<input name="avocations" type="checkbox" value="026004-阅读" <c:if test="${fn:contains(accessRecord02.avocations, '阅读')}">checked</c:if> style="width: 1rem">阅读 
											<input name="avocations" type="checkbox" value="026005-理财" <c:if test="${fn:contains(accessRecord02.avocations, '理财')}">checked</c:if> style="width: 1rem">理财
											<input name="avocations" type="checkbox" value="026006-艺术" <c:if test="${fn:contains(accessRecord02.avocations, '艺术')}">checked</c:if> style="width: 1rem">艺术 
											<input name="avocations" type="checkbox" value="026007-茶道" <c:if test="${fn:contains(accessRecord02.avocations, '茶道')}">checked</c:if> style="width: 1rem">茶道 
											<input name="avocations" type="checkbox" value="026008-收藏" <c:if test="${fn:contains(accessRecord02.avocations, '收藏')}">checked</c:if> style="width: 1rem">收藏
											<input name="avocations" type="checkbox" value="026009-购物" <c:if test="${fn:contains(accessRecord02.avocations, '购物')}">checked</c:if> style="width: 1rem">购物<br> 
											<input name="avocations" type="checkbox" value="026010-美食" <c:if test="${fn:contains(accessRecord02.avocations, '美食')}">checked</c:if> style="width: 1rem; margin-left: 123px">美食 
											<input name="avocations" type="checkbox" value="026011-园艺"  <c:if test="${fn:contains(accessRecord02.avocations, '园艺')}">checked</c:if>style="width: 1rem">园艺
											<input name="avocations" type="checkbox" value="026012-社交" <c:if test="${fn:contains(accessRecord02.avocations, '社交')}">checked</c:if>style="width: 1rem">社交 
											<input name="avocations" type="checkbox" value="026013-时尚" <c:if test="${fn:contains(accessRecord02.avocations, '时尚')}">checked</c:if>style="width: 1rem">时尚 
											<input name="avocations" type="checkbox" value="026014-旅行" <c:if test="${fn:contains(accessRecord02.avocations, '旅行')}">checked</c:if>style="width: 1rem">旅行
											<input name="avocations" type="checkbox" value="026015-教育" <c:if test="${fn:contains(accessRecord02.avocations, '教育')}">checked</c:if>style="width: 1rem">教育 
											<input name="avocations" type="checkbox" value="026016-慈善" <c:if test="${fn:contains(accessRecord02.avocations, '慈善')}">checked</c:if>style="width: 1rem">慈善 
											<input name="avocations" type="checkbox" value="026000-无法了解" <c:if test="${fn:contains(accessRecord02.avocations, '无法了解')}">checked</c:if>style="width: 1rem">
											<span style="color: red">无法了解</span>
											<input name="avocations" type="checkbox" value="026999-其他" <c:if test="${fn:contains(accessRecord02.avocationsdesc, '其他')}">checked</c:if>style="width: 1rem">其他
											<input type="text" placeholder="" name="avocationsDesc" style="display:none;"/> 
										</td>
									</tr>
									<tr>
										<td colspan="3">孩子的课余爱好：
											<input name="childavocations" type="checkbox" value="020001-音乐类" <c:if test="${fn:contains(accessRecord02.childavocations, '音乐类')}">checked</c:if> style="width: 1rem">音乐类 
											<input name="childavocations" type="checkbox" value="020002-体育类" <c:if test="${fn:contains(accessRecord02.childavocations, '体育类')}">checked</c:if> style="width: 1rem">体育类
											<input name="childavocations" type="checkbox" value="020003-文学类" <c:if test="${fn:contains(accessRecord02.childavocations, '文学类')}">checked</c:if> style="width: 1rem">文学类
											<input name="childavocations" type="checkbox" value="020004-专业类" <c:if test="${fn:contains(accessRecord02.childavocations, '专业类')}">checked</c:if> style="width: 1rem">专业类
											<input name="childavocations" type="checkbox" value="020000-无法了解" <c:if test="${fn:contains(accessRecord02.childavocations, '无法了解')}">checked</c:if>style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>

									<tr>
										<td colspan="3">您喜欢参加的活动：
											<input name="loveactivation" type="checkbox" value="022001-家庭教育" <c:if test="${fn:contains(accessRecord02.loveactivation, '家庭教育')}">checked</c:if> style="width: 1rem">家庭教育
											<input name="loveactivation" type="checkbox" value="022002-国际学校教育" <c:if test="${fn:contains(accessRecord02.loveactivation, '国际学校教育')}">checked</c:if> style="width: 1rem">国际学校教育 
											<input name="loveactivation" type="checkbox" value="022003-亲子活动" <c:if test="${fn:contains(accessRecord02.loveactivation, '亲子活动')}">checked</c:if> style="width: 1rem">亲子活动
											<input name="loveactivation" type="checkbox" value="022004-文化艺术" <c:if test="${fn:contains(accessRecord02.loveactivation, '文化艺术')}">checked</c:if> style="width: 1rem">文化艺术 
											<input name="loveactivation" type="checkbox" value="022005-美食品鉴" <c:if test="${fn:contains(accessRecord02.loveactivation, '美食品鉴')}">checked</c:if> style="width: 1rem">美食品鉴<br>
											<input name="loveactivation" type="checkbox" value="022005-户外运动" <c:if test="${fn:contains(accessRecord02.loveactivation, '户外运动')}">checked</c:if> style="width: 1rem; margin-left: 151px">户外运动 
											<input name="loveactivation" type="checkbox" value="022005-养生保健" <c:if test="${fn:contains(accessRecord02.loveactivation, '养生保健')}">checked</c:if> style="width: 1rem">养生保健 
											<input name="loveactivation" type="checkbox" value="022005-理财规划" <c:if test="${fn:contains(accessRecord02.loveactivation, '理财规划')}">checked</c:if> style="width: 1rem">理财规划
											<input name="loveactivation" type="checkbox" value="022005-书刊杂志" <c:if test="${fn:contains(accessRecord02.loveactivation, '书刊杂志')}">checked</c:if> style="width: 1rem">书刊杂志 
											<input name="loveactivation" type="checkbox" value="022005-琴棋书画" <c:if test="${fn:contains(accessRecord02.loveactivation, '琴棋书画')}">checked</c:if> style="width: 1rem">琴棋书画
											<input name="loveactivation" type="checkbox" value="022000-无法了解" <c:if test="${fn:contains(accessRecord02.loveactivation, '无法了解')}">checked</c:if> style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>

									<tr>
										<td colspan="3">可参加业主活动时间：
											<span style="margin-left: 20px">周一到周五（</span>
											<input name="freetimesection" type="checkbox" value="109001001-上午9~11点" <c:if test="${fn:contains(accessRecord02.freetimesection, '上午9~11点')}">checked</c:if> style="width: 1rem; margin-left: 5px">上午9-11点
											<input name="freetimesection" type="checkbox" value="109001002-下午2~5点" <c:if test="${fn:contains(accessRecord02.freetimesection, '下午2~5点')}">checked</c:if> style="width: 1rem">下午2-5点 
											<input name="freetimesection" type="checkbox" value="109001003-晚上6~9点" <c:if test="${fn:contains(accessRecord02.freetimesection, '晚上6~9点')}">checked</c:if> style="width: 1rem">晚上6-9点 ）<br> 
											<span style="margin-left: 160px"> 周六到周日（</span> 
											<input name="freetimesection" type="checkbox" value="109002001-上午9~11点" <c:if test="${fn:contains(accessRecord02.freetimesection, '上午9~11点')}">checked</c:if> style="width: 1rem; margin-left: 5px">上午9-11点 
											<input name="freetimesection" type="checkbox" value="109002002-下午2~5点" <c:if test="${fn:contains(accessRecord02.freetimesection, '下午2~5点')}">checked</c:if> style="width: 1rem">下午2-5点 
											<input name="freetimesection" type="checkbox" value="109002004-晚上6~9点" <c:if test="${fn:contains(accessRecord02.freetimesection, '晚上6~9点')}">checked</c:if> style="width: 1rem">晚上6-9点 ） 
											<input name="freetimesection" type="checkbox" value="109000-无法了解" <c:if test="${fn:contains(accessRecord02.freetimesection, '无法了解')}">checked</c:if> style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>


									<tr>
										<td colspan="3">来访人数：
											<input name="visitorcount" type="radio" value="1" <c:if test="${fn:contains(accessRecord02.visitorcount, '1')}">checked</c:if> style="width: 1rem" >一人 
											<input name="visitorcount" type="radio" value="2" <c:if test="${fn:contains(accessRecord02.visitorcount, '2')}">checked</c:if> style="width: 1rem" >两人 
											<input name="visitorcount" type="radio" value="3" <c:if test="${fn:contains(accessRecord02.visitorcount, '3')}">checked</c:if> style="width: 1rem" >三人 
											<input name="visitorcount" type="radio" value="4" <c:if test="${fn:contains(accessRecord02.visitorcount, '4')}">checked</c:if> style="width: 1rem" >四人 
											<input name="visitorcount" type="radio" value="5" <c:if test="${fn:contains(accessRecord02.visitorcount, '5')}">checked</c:if> style="width: 1rem" >五人及以上
										</td>
									</tr>

									<tr>
										<td colspan="3">
											<span id="box4" style="display: block;">来访人之间关系：
											<input name="visitorrefs" type="checkbox" value="028001-夫妻" <c:if test="${fn:contains(accessRecord02.visitorrefs, '夫妻')}">checked</c:if> style="width: 1rem">夫妻 
											<input name="visitorrefs" type="checkbox" value="028002-与父母同行" <c:if test="${fn:contains(accessRecord02.visitorrefs, '与父母同行')}">checked</c:if> style="width: 1rem">与父母同行
											<input name="visitorrefs" type="checkbox" value="028003-与子女同行" <c:if test="${fn:contains(accessRecord02.visitorrefs, '与子女同行')}">checked</c:if> style="width: 1rem">与子女同行 
											<input name="visitorrefs" type="checkbox" value="028004-朋友" <c:if test="${fn:contains(accessRecord02.visitorrefs, '朋友')}">checked</c:if> style="width: 1rem">朋友 
											<input name="visitorrefs" type="checkbox" value="028005-同事" <c:if test="${fn:contains(accessRecord02.visitorrefs, '同事')}">checked</c:if> style="width: 1rem">同事
										</span></td>
									</tr>

									<tr>
										<td colspan="3">本次参观接待时间：
											<input name="receptimesection" type="radio" value="018001-15分钟以内-0~15" <c:if test="${fn:contains(accessRecord02.receptimesection, '15分钟以内')}">checked</c:if> style="width: 1rem">15分钟以内
											<input name="receptimesection" type="radio" value="018002-15~30分钟-15~30" <c:if test="${fn:contains(accessRecord02.receptimesection, '15~30分钟')}">checked</c:if> style="width: 1rem">15-30分钟 
											<input name="receptimesection" type="radio" value="018003-30^60分钟-30~60" <c:if test="${fn:contains(accessRecord02.receptimesection, '30~60分钟')}">checked</c:if> style="width: 1rem">30-60分钟
											<input name="receptimesection" type="radio" value="018004-60~120分钟-60~120" <c:if test="${fn:contains(accessRecord02.receptimesection, '60~120分钟')}">checked</c:if> style="width: 1rem">60-120分钟 
											<input name="receptimesection" type="radio" value="018005-120分钟以上-120~" <c:if test="${fn:contains(accessRecord02.receptimesection, '120分钟以上')}">checked</c:if> style="width: 1rem">120分钟以上
										</td>
									</tr>
									
									<tr>
										<td colspan="3">家庭状况：
											<input name="familystatus" type="radio" <c:if test="${fn:contains(accessRecord02.familystatus, '单身')}">checked</c:if> value="005001-单身" _text="单身" style="width: 1rem">单身 
											<input name="familystatus" type="radio" <c:if test="${fn:contains(accessRecord02.familystatus, '夫妻')}">checked</c:if> value="005002-夫妻" _text="夫妻" style="width: 1rem">夫妻
											<input name="familystatus" type="radio" <c:if test="${fn:contains(accessRecord02.familystatus, '一孩家庭')}">checked</c:if> value="005003-一孩家庭" _text="一孩家庭" style="width: 1rem">一孩家庭 
											<input name="familystatus" type="radio" <c:if test="${fn:contains(accessRecord02.familystatus, '俩孩家庭')}">checked</c:if> value="005004-俩孩家庭" _text="俩孩家庭" style="width: 1rem">俩孩家庭 
											<input name="familystatus" type="radio" <c:if test="${fn:contains(accessRecord02.familystatus, '三孩及以上家庭')}">checked</c:if> value="005005-三孩及以上家庭" _text="三孩及以上家庭" style="width: 1rem">三孩及以上家庭
											<input name="familystatus" type="radio" <c:if test="${fn:contains(accessRecord02.familystatus, '三代同堂')}">checked</c:if> value="005006-三代同堂" _text="三代同堂" style="width: 1rem">三代同堂 
											<input name="familystatus" type="radio" <c:if test="${fn:contains(accessRecord02.familystatus, '无法了解')}">checked</c:if> value="005000-无法了解" style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>
	
									<tr>
										<td colspan="3">您的出行方式：
											<input name="traffictype"type="checkbox" value="006001-自驾车" _text="自驾车" <c:if test="${fn:contains(accessRecord02.traffictype, '自驾车')}">checked</c:if> style="width: 1rem">自驾车 
											<input name="traffictype" type="checkbox" value="006002-公交" _text="公交" <c:if test="${fn:contains(accessRecord02.traffictype, '公交')}">checked</c:if> style="width: 1rem">公交 
											<input name="traffictype" type="checkbox" value="006003-地铁" _text="地铁" <c:if test="${fn:contains(accessRecord02.traffictype, '地铁')}">checked</c:if> style="width: 1rem">地铁 
											<input name="traffictype" type="checkbox" value="006004-步行" _text="步行" <c:if test="${fn:contains(accessRecord02.traffictype, '步行')}">checked</c:if> style="width: 1rem">步行 
											<input name="traffictype" type="checkbox" value="006005-打车" _text="打车" <c:if test="${fn:contains(accessRecord02.traffictype, '打车')}">checked</c:if> style="width: 1rem">打车 
											<input name="traffictype" type="checkbox" value="006000-无法了解" _text="无法了解" <c:if test="${fn:contains(accessRecord02.traffictype, '无法了解')}">checked</c:if> style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>
	
									<tr>
										<td colspan="3">您的从事行业：
											<input name="workindustry" type="radio" value="007001-建筑房地产/装修" _text="建筑房地产/装修" <c:if test="${fn:contains(accessRecord02.workindustry, '建筑房地产/装修')}">checked</c:if> style="width: 1rem">建筑房地产/装修 
											<input name="workindustry" type="radio" value="007002-旅游/文化/媒体" _text="旅游/文化/媒体" <c:if test="${fn:contains(accessRecord02.workindustry, '旅游/文化/媒体')}">checked</c:if> style="width: 1rem">旅游/文化/媒体
											<input name="workindustry" type="radio" value="007003-能源" _text="能源" <c:if test="${fn:contains(accessRecord02.workindustry, '能源')}">checked</c:if> style="width: 1rem">能源 
											<input name="workindustry" type="radio" value="007004-IT" <c:if test="${fn:contains(accessRecord02.workindustry, 'IT')}">checked</c:if> style="width: 1rem">IT 
											<input name="workindustry" type="radio" value="007005-金融投资" <c:if test="${fn:contains(accessRecord02.workindustry, '金融投资')}">checked</c:if> style="width: 1rem">金融投资
											<input name="workindustry" type="radio" value="007006-外贸" _text="外贸" <c:if test="${fn:contains(accessRecord02.workindustry, '外贸')}">checked</c:if> style="width: 1rem">外贸
											<input name="workindustry" type="radio" value="007007-交通/物流/零售" _text="交通/物流/零售" <c:if test="${fn:contains(accessRecord02.workindustry, '交通/物流/零售')}">checked</c:if> style="width: 1rem">交通/物流/零售<br>
											<input name="workindustry" type="radio" value="007008-餐饮" _text="餐饮" <c:if test="${fn:contains(accessRecord02.workindustry, '餐饮')}">checked</c:if> style="width: 1rem; margin-left: 123px">餐饮 
											<input name="workindustry" type="radio" value="007009-医疗" _text="医疗" <c:if test="${fn:contains(accessRecord02.workindustry, '医疗')}">checked</c:if> style="width: 1rem">医疗
											<input name="workindustry" type="radio" value="007010-教育" _text="教育" <c:if test="${fn:contains(accessRecord02.workindustry, '教育')}">checked</c:if> style="width: 1rem">教育 
											<input name="workindustry" type="radio" value="007011-通讯" _text="通讯" <c:if test="${fn:contains(accessRecord02.workindustry, '通讯')}">checked</c:if> style="width: 1rem">通讯 
											<input name="workindustry" type="radio" value="007012-律师/法务" _text="律师/法务"<c:if test="${fn:contains(accessRecord02.workindustry, '律师/法务')}">checked</c:if> style="width: 1rem">律师/法务
											<input name="workindustry" type="radio" value="007013-退休" _text="退休"<c:if test="${fn:contains(accessRecord02.workindustry, '退休')}">checked</c:if> style="width: 1rem">退休 
											<input name="workindustry" type="radio" value="007999-其他" _text="其他" <c:if test="${fn:contains(accessRecord02.workindustry, '其它')}">checked</c:if> style="width: 1rem">其它 
											<input name="workindustry" type="radio" value="007000-无法了解" _text="无法了解" <c:if test="${fn:contains(accessRecord02.workindustry, '无法了解')}">checked</c:if> style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>
	
									<tr>
										<td colspan="3">您的企业性质：
											<input name="enterprisetype" type="radio" value="008001-国家行政机关及国企" _text="国家行政机关及国企" <c:if test="${fn:contains(accessRecord02.enterprisetype, '国家行政机关及国企')}">checked</c:if> style="width: 1rem">国家行政机关及国企
											<input name="enterprisetype" type="radio" value="008002-民营企业" _text="民营企业" <c:if test="${fn:contains(accessRecord02.enterprisetype, '民营企业')}">checked</c:if> style="width: 1rem">民营企业
											<input name="enterprisetype" type="radio" value="008003-外资企业" _text="外资企业" <c:if test="${fn:contains(accessRecord02.enterprisetype, '外贸企业')}">checked</c:if> style="width: 1rem">外资企业 
											<input name="enterprisetype" type="radio" value="008004-合资企业" _text="合资企业" <c:if test="${fn:contains(accessRecord02.enterprisetype, '合资企业')}">checked</c:if> style="width: 1rem">合资企业 
											<input name="enterprisetype" type="radio" value="008005-私营" _text="私营" <c:if test="${fn:contains(accessRecord02.enterprisetype, '私营')}">checked</c:if> style="width: 1rem">私营
											<input name="enterprisetype" type="radio" value="008999-其他" _text="其他" <c:if test="${fn:contains(accessRecord02.enterprisetype, '其它')}">checked</c:if> style="width: 1rem">其它 
											<input name="enterprisetype" type="radio" value="008000-无法了解" _text="无法了解" <c:if test="${fn:contains(accessRecord02.enterprisetype, '无法了解')}">checked</c:if> style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>
	
									<tr>
										<td colspan="3">您关注的产品类型：
											<span style="margin-left: 20px">别墅</span>
											（<input name="realtyproducttype" type="checkbox" value="009001001-独栋" _text="独栋" <c:if test="${fn:contains(accessRecord02.realtyproducttype, '独栋')}">checked</c:if> style="width: 1rem">独栋
											<input name="realtyproducttype" type="checkbox" value="009001002-类独栋" _text="类独栋" <c:if test="${fn:contains(accessRecord02.realtyproducttype, '类独栋')}">checked</c:if> style="width: 1rem">类独栋 
											<input name="realtyproducttype" type="checkbox" value="009001003-双拼" _text="双拼" <c:if test="${fn:contains(accessRecord02.realtyproducttype, '双拼')}">checked</c:if> style="width: 1rem">双拼 
											<input name="realtyproducttype" type="checkbox" value="009001004-联排" _text="联排" <c:if test="${fn:contains(accessRecord02.realtyproducttype, '联排')}">checked</c:if> style="width: 1rem">联排
											<input name="realtyproducttype" type="checkbox" value="009001005-上叠" _text="上叠" <c:if test="${fn:contains(accessRecord02.realtyproducttype, '上叠')}">checked</c:if> style="width: 1rem">上叠 
											<input name="realtyproducttype" type="checkbox" value="009001006-下叠" _text="下叠" <c:if test="${fn:contains(accessRecord02.realtyproducttype, '下叠')}">checked</c:if> style="width: 1rem">下叠&nbsp;&nbsp;&nbsp;&nbsp;）<br>
											<span style="margin-left: 145px">平层（</span>
											<input name="realtyproducttype" type="checkbox" value="009002001-两居及以下" _text="两居及以下"<c:if test="${fn:contains(accessRecord02.realtyproducttype, '两居及以下')}">checked</c:if> style="width: 1rem;">两居及以下 
											<input name="realtyproducttype" type="checkbox" value="009002002-三居" _text="三居" <c:if test="${fn:contains(accessRecord02.realtyproducttype, '三居')}">checked</c:if> style="width: 1rem">三居 
											<input name="realtyproducttype" type="checkbox" value="009002003-四居" _text="四居" <c:if test="${fn:contains(accessRecord02.realtyproducttype, '四居')}">checked</c:if> style="width: 1rem">四居
											<input name="realtyproducttype" type="checkbox" value="009002004-五居及以上叠" _text="五居及以上叠" <c:if test="${fn:contains(accessRecord02.realtyproducttype, '五居及以上叠')}">checked</c:if> style="width: 1rem">五居及以上叠&nbsp;&nbsp;&nbsp;&nbsp;）<br>
											<input name="realtyproducttype" type="checkbox" value="009003-商业" _text="商业" <c:if test="${fn:contains(accessRecord02.realtyproducttype, '商业')}">checked</c:if> style="width: 1rem; margin-left: 145px">商业 
											<input name="realtyproducttype" type="checkbox" value="00904-商业办公" _text="商业办公" <c:if test="${fn:contains(accessRecord02.realtyproducttype, '商务办公')}">checked</c:if> style="width: 1rem; margin-left: 16px">商务办公 
											<input name="realtyproducttype" type="checkbox" value="009999-其他" _text="其他" <c:if test="${fn:contains(accessRecord02.realtyproducttype, '其他')}">checked</c:if> style="width: 1rem">其他
											<input name="realtyproducttype" type="checkbox" value="009000-无法了解" _text="无法了解" <c:if test="${fn:contains(accessRecord02.realtyproducttype, '无法了解')}">checked</c:if> style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>
	
									<tr>
										<td colspan="3">您关注区域面积：
											<input name="attentacreage" type="radio" value="010001-200㎡以下-0~200" _text="200㎡以下" <c:if test="${fn:contains(accessRecord02.attentacreage, '200㎡以下')}">checked</c:if> style="width: 1rem">200㎡以下 
											<input name="attentacreage" type="radio" value="010002-200~300㎡-200~300" _text="200~300㎡" <c:if test="${fn:contains(accessRecord02.attentacreage, '200~300㎡')}">checked</c:if> style="width: 1rem">200-300㎡ 
											<input name="attentacreage" type="radio" value="010003-300~400㎡-300~400" _text="300~400㎡" <c:if test="${fn:contains(accessRecord02.attentacreage, '300~400㎡')}">checked</c:if> style="width: 1rem">300-400㎡
											<input name="attentacreage" type="radio" value="010004-400~500㎡-400~500" _text="400~500㎡" <c:if test="${fn:contains(accessRecord02.attentacreage, '400~500㎡')}">checked</c:if> style="width: 1rem">400-500㎡ 
											<input name="attentacreage" type="radio" value="010005-500~600㎡-500~600" <c:if test="${fn:contains(accessRecord02.attentacreage, '500~600㎡')}">checked</c:if> style="width: 1rem">500-600㎡<br>
											<input name="attentacreage" type="radio" value="010006-600~700㎡-600~700" <c:if test="${fn:contains(accessRecord02.attentacreage, '600~700㎡')}">checked</c:if> style="width: 1rem; margin-left: 137px">600-700㎡ 
											<input name="attentacreage" type="radio" value="010007-700~800㎡-700~800" <c:if test="${fn:contains(accessRecord02.attentacreage, '700~800㎡')}">checked</c:if> style="width: 1rem">700-800㎡ 
											<input name="attentacreage" type="radio" value="010008-800㎡以上-800~" _text="800㎡以上" <c:if test="${fn:contains(accessRecord02.attentacreage, '800㎡以上')}">checked</c:if> style="width: 1rem">800㎡以上
											<input name="attentacreage" type="radio" value="010000-无法了解" _text="无法了解" <c:if test="${fn:contains(accessRecord02.attentacreage, '无法了解')}">checked</c:if> style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>
	
									<tr>
										<td colspan="3">您接受的总房款：
											<input name="pricesection" type="radio" value="011001-500万以下-0~500" _text="500万以下" <c:if test="${fn:contains(accessRecord02.pricesection, '500万以下')}">checked</c:if> style="width: 1rem">500万以下
											<input name="pricesection" type="radio" value="011002-500~1000万-500~1000" _text="500~1000万" <c:if test="${fn:contains(accessRecord02.pricesection, '500~1000万')}">checked</c:if> style="width: 1rem">500-1000万 
											<input name="pricesection" type="radio" value="011003-1000~1500万-1000~1500" _text="1000~1500万" <c:if test="${fn:contains(accessRecord02.pricesection, '1000~1500万')}">checked</c:if> style="width: 1rem">1000-1500万
											<input name="pricesection" type="radio" value="011004-1500~2000万-1500~2000" _text="1500~2000万" <c:if test="${fn:contains(accessRecord02.pricesection, '1500~2000万')}">checked</c:if> style="width: 1rem">1500-2000万
											<input name="pricesection" type="radio" value="011005-2000~3000万-2000~3000" _text="2000~3000万" <c:if test="${fn:contains(accessRecord02.pricesection, '2000~3000万')}">checked</c:if> style="width: 1rem">2000-3000万<br>
											<input name="pricesection" type="radio" value="011006-3000~4000万-3000~4000" _text="3000~4000万" <c:if test="${fn:contains(accessRecord02.pricesection, '3000~4000万')}">checked</c:if> style="width: 1rem; margin-left: 137px">3000-4000万 
											<input name="pricesection" type="radio" value="011007-4000~5000万-4000~5000" _text="4000~5000万" <c:if test="${fn:contains(accessRecord02.pricesection, '4000~5000万')}">checked</c:if> style="width: 1rem;">4000-5000万 
											<input name="pricesection" type="radio" value="011008-5000~6000万-5000~6000" _text="5000~6000万" <c:if test="${fn:contains(accessRecord02.pricesection, '5000~6000万')}">checked</c:if> style="width: 1rem">5000-6000万
											<input name="pricesection" type="radio" value="011009-6000~8000万-6000~8000" _text="6000~8000万" <c:if test="${fn:contains(accessRecord02.pricesection, '6000~8000万')}">checked</c:if> style="width: 1rem">6000-8000万 
											<input name="pricesection" type="radio" value="011010-8000万以上-8000~" _text="8000万以上" <c:if test="${fn:contains(accessRecord02.pricesection, '8000万以上')}">checked</c:if> style="width: 1rem">8000万以上
											<input name="pricesection" type="radio" value="011000-无法了解" _text="无法了解" <c:if test="${fn:contains(accessRecord02.pricesection, '无法了解')}">checked</c:if> style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>
	
									<tr>
										<td colspan="3">您的购房目的：
											<input name="buypurpose" type="radio" value="012001-自住第一居所" <c:if test="${fn:contains(accessRecord02.buypurpose, '自住第一居所')}">checked</c:if> style="width: 1rem">自住第一居所 
											<input name="buypurpose" type="radio" value="012002-自住第二居所" <c:if test="${fn:contains(accessRecord02.buypurpose, '自住第二居所')}">checked</c:if> style="width: 1rem">自住第二居所
											<input name="buypurpose" type="radio" value="012003-为子女购房" <c:if test="${fn:contains(accessRecord02.buypurpose, '为子女购房')}">checked</c:if> style="width: 1rem">为子女购房 
											<input name="buypurpose" type="radio" value="012004-为父母购房" <c:if test="${fn:contains(accessRecord02.buypurpose, '为父母购房')}">checked</c:if> style="width: 1rem">为父母购房 
											<input name="buypurpose" type="radio" value="012005-自住兼投资" <c:if test="${fn:contains(accessRecord02.buypurpose, '自住兼投资')}">checked</c:if> style="width: 1rem">自住兼投资
											<input name="buypurpose" type="radio" value="012006-仅投资" <c:if test="${fn:contains(accessRecord02.buypurpose, '仅投资')}">checked</c:if> style="width: 1rem">仅投资 
											<input name="buypurpose" type="radio" value="012999-其他" <c:if test="${fn:contains(accessRecord02.buypurpose, '其它')}">checked</c:if> style="width: 1rem">其它<br>
											<input name="buypurpose" type="radio" value="012000-无法了解" <c:if test="${fn:contains(accessRecord02.buypurpose, '无法了解')}">checked</c:if> style="width: 1rem; margin-left: 123px">
											<span style="color: red">无法了解</span>
										</td>
									</tr>
	
									<tr>
										<td colspan="3">认知本案渠道：
											<input name="knowway" type="checkbox" value="013001-户外广告牌" <c:if test="${fn:contains(accessRecord02.knowway, '户外广告牌')}">checked</c:if> style="width: 1rem">户外广告牌&nbsp;
											<input type="text" name="qudao_guanggao" style="width: 10rem"> 
											<input name="knowway" type="checkbox" value="013002-网络" <c:if test="${fn:contains(accessRecord02.knowway, '网络')}">checked</c:if> style="width: 1rem">网络
											<input name="knowway" type="checkbox" value="013003-短信" <c:if test="${fn:contains(accessRecord02.knowway, '短信')}">checked</c:if> style="width: 1rem">短信 
											<input name="knowway" type="checkbox" value="013004-纸媒" <c:if test="${fn:contains(accessRecord02.knowway, '纸媒')}">checked</c:if> style="width: 1rem">纸媒 
											<input name="knowway" type="checkbox" value="013005-路过" <c:if test="${fn:contains(accessRecord02.knowway, '路过')}">checked</c:if> style="width: 1rem">路过
											<input name="knowway" type="checkbox" value="013006-朋友介绍" <c:if test="${fn:contains(accessRecord02.knowway, '朋友介绍')}">checked</c:if> style="width: 1rem">朋友介绍<br> 
											<input name="knowway" type="checkbox" value="013007-渠道介绍" <c:if test="${fn:contains(accessRecord02.knowway, '渠道介绍')}">checked</c:if> style="width: 1rem; margin-left: 123px;">渠道介绍
											<input type="text" name="qudao_qudao" style="width: 10rem">公司 
											<input name="knowway" type="checkbox" value="013008-巡展" <c:if test="${fn:contains(accessRecord02.knowway, '巡展')}">checked</c:if> style="width: 1rem">巡展
											<input name="knowway" type="checkbox" value="013009-广播" <c:if test="${fn:contains(accessRecord02.knowway, '广播')}">checked</c:if> style="width: 1rem;">广播 
											<input name="knowway" type="checkbox" value="013010-电CALL"<c:if test="${fn:contains(accessRecord02.knowway, '电CALL')}">checked</c:if> style="width: 1rem">电CALL
											<input name="knowway" type="checkbox" value="013011-直投"<c:if test="${fn:contains(accessRecord02.knowway, '直投')}">checked</c:if> style="width: 1rem">直投 
											<input name="knowway" type="checkbox" value="013012-活动"<c:if test="${fn:contains(accessRecord02.knowway, '活动')}">checked</c:if> style="width: 1rem">活动 
											<input name="knowway" type="checkbox" value="013013-DM单"<c:if test="${fn:contains(accessRecord02.knowway, 'DM单')}">checked</c:if> style="width: 1rem;">DM单 
											<input name="knowway" type="checkbox" value="013014-外联"<c:if test="${fn:contains(accessRecord02.knowway, '外联')}">checked</c:if> style="width: 1rem">外联<br>
											<input name="knowway" type="checkbox" value="013015-老带新"<c:if test="${fn:contains(accessRecord02.knowway, '老带新')}">checked</c:if> style="width: 1rem; margin-left: 123px;">老带新 
											<input name="knowway" type="checkbox" value="013999-其他"<c:if test="${fn:contains(accessRecord02.knowway, '其它')}">checked</c:if> style="width: 1rem;">其它
											<input name="knowway" type="checkbox" value="013000-无法了解"<c:if test="${fn:contains(accessRecord02.knowway, '无法了解')}">checked</c:if> style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>
	
									<tr>
										<td colspan="3">您对本案的关注点：
											<input name="attentionpoint" type="checkbox" value="014001-位置"<c:if test="${fn:contains(accessRecord02.attentionpoint, '位置')}">checked</c:if> style="width: 1rem">位置 
											<input name="attentionpoint" type="checkbox" value="014002-产品类型"<c:if test="${fn:contains(accessRecord02.attentionpoint, '产品类型')}">checked</c:if> style="width: 1rem">产品类型 
											<input name="attentionpoint" type="checkbox" value="014003-交通"<c:if test="${fn:contains(accessRecord02.attentionpoint, '交通')}">checked</c:if> style="width: 1rem">交通 
											<input name="attentionpoint" type="checkbox" value="014004-区域环境"<c:if test="${fn:contains(accessRecord02.attentionpoint, '区域环境')}">checked</c:if> style="width: 1rem">区域环境 
											<input name="attentionpoint" type="checkbox" value="014005-价格"<c:if test="${fn:contains(accessRecord02.attentionpoint, '价格')}">checked</c:if> style="width: 1rem">价格 
											<input name="attentionpoint" type="checkbox" value="014006-户型"<c:if test="${fn:contains(accessRecord02.attentionpoint, '户型')}">checked</c:if> style="width: 1rem">户型 
											<input name="attentionpoint" type="checkbox" value="014007-花园面积"<c:if test="${fn:contains(accessRecord02.attentionpoint, '花园面积')}">checked</c:if> style="width: 1rem">花园面积<br>
											<input name="attentionpoint" type="checkbox" value="014008-园林环境"<c:if test="${fn:contains(accessRecord02.attentionpoint, '园林环境')}">checked</c:if> style="width: 1rem; margin-left: 151px">园林环境 
											<input name="attentionpoint" type="checkbox" value="014009-科技设备"<c:if test="${fn:contains(accessRecord02.attentionpoint, '科技设备')}">checked</c:if> style="width: 1rem">科技设备 
											<input name="attentionpoint" type="checkbox" value="014010-交房时间"<c:if test="${fn:contains(accessRecord02.attentionpoint, '交房时间')}">checked</c:if> style="width: 1rem">交房时间
											<input name="attentionpoint" type="checkbox" value="014011-社区配套"<c:if test="${fn:contains(accessRecord02.attentionpoint, '社区配套')}">checked</c:if> style="width: 1rem">社区配套 
											<input name="attentionpoint" type="checkbox" value="014012-物业服务"<c:if test="${fn:contains(accessRecord02.attentionpoint, '物业服务')}">checked</c:if> style="width: 1rem">物业服务
											<input name="attentionpoint" type="checkbox" value="014013-教育环境"<c:if test="${fn:contains(accessRecord02.attentionpoint, '教育环境')}">checked</c:if> style="width: 1rem">教育环境 
											<input name="attentionpoint" type="checkbox" value="014014-开发品牌"<c:if test="${fn:contains(accessRecord02.attentionpoint, '开发品牌')}">checked</c:if> style="width: 1rem">开发品牌<br>
											<input name="attentionpoint" type="checkbox" value="014015-增值潜力"<c:if test="${fn:contains(accessRecord02.attentionpoint, '增值潜力')}">checked</c:if> style="width: 1rem; margin-left: 151px">增值潜力 
											<input name="attentionpoint" type="checkbox" value="012999-其他"<c:if test="${fn:contains(accessRecord02.attentionpoint, '其它')}">checked</c:if> style="width: 1rem;">其它 
											<input name="attentionpoint" type="checkbox" value="012000-无法了解"<c:if test="${fn:contains(accessRecord02.attentionpoint, '无法了解')}">checked</c:if> style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>
	
									<tr>
										<td colspan="3">预估身价：
											<input name="estcustworth" type="radio" value="015001-1千万以下-0~1000"<c:if test="${fn:contains(accessRecord02.estcustworth, '1千万以下')}">checked</c:if> style="width: 1rem">1千万以下 
											<input name="estcustworth" type="radio" value="015002-1千~5千万-1000~5000"<c:if test="${fn:contains(accessRecord02.estcustworth, '1千~5千万')}">checked</c:if> style="width: 1rem">1千-5千万
											<input name="estcustworth" type="radio" value="015003-5千万~1亿-5000~10000"<c:if test="${fn:contains(accessRecord02.estcustworth, '5千万~1亿')}">checked</c:if> style="width: 1rem">5千万-1亿 
											<input name="estcustworth" type="radio" value="015004-1~3亿-10000~30000"<c:if test="${fn:contains(accessRecord02.estcustworth, '1~3亿')}">checked</c:if> style="width: 1rem">1-3亿 
											<input name="estcustworth" type="radio" value="015005-3亿以上-30000~"<c:if test="${fn:contains(accessRecord02.estcustworth, '3亿以上')}">checked</c:if> style="width: 1rem">3亿以上
											<input name="estcustworth" type="radio" value="015000-无法了解"<c:if test="${fn:contains(accessRecord02.estcustworth, '无法了解')}">checked</c:if> style="width: 1rem;">
											<span style="color: red">无法了解</span>
										</td>
									</tr>
	
	
	
	
									<tr>
										<td colspan="3">重点投资：
											<input name="investtype" type="checkbox" value="016001-股票"<c:if test="${fn:contains(accessRecord02.investtype, '股票')}">checked</c:if> style="width: 1rem">股票 
											<input name="investtype" type="checkbox" value="016002-基金"<c:if test="${fn:contains(accessRecord02.investtype, '基金')}">checked</c:if> style="width: 1rem">基金
											<input name="investtype" type="checkbox" value="016003-黄金"<c:if test="${fn:contains(accessRecord02.investtype, '黄金')}">checked</c:if> style="width: 1rem">黄金 
											<input name="investtype" type="checkbox" value="016004-股指期货"<c:if test="${fn:contains(accessRecord02.investtype, '股脂期货')}">checked</c:if> style="width: 1rem">股指期货
											<input name="investtype" type="checkbox" value="016005-外汇"<c:if test="${fn:contains(accessRecord02.investtype, '外汇')}">checked</c:if> style="width: 1rem">外汇 
											<input name="investtype" type="checkbox" value="016006-艺术品"<c:if test="${fn:contains(accessRecord02.investtype, '艺术品')}">checked</c:if> style="width: 1rem">艺术品 
											<input name="investtype" type="checkbox" value="016007-保险"<c:if test="${fn:contains(accessRecord02.investtype, '保险')}">checked</c:if> style="width: 1rem">保险
											<input name="investtype" type="checkbox" value="016008-民间投资"<c:if test="${fn:contains(accessRecord02.investtype, '民间投资')}">checked</c:if> style="width: 1rem">民间投资 
											<input name="investtype" type="checkbox" value="016999-其他"<c:if test="${fn:contains(accessRecord02.investtype, '其它')}">checked</c:if> style="width: 1rem">其它 
											<input name="investtype" type="checkbox" value="016000-无法了解"<c:if test="${fn:contains(accessRecord02.investtype, '无法了解')}">checked</c:if> style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>
	
									<tr>
										<td colspan="3">资金筹备期：
											<input name="capitalprepsection" type="radio" value="017001-1个月内-0~1"<c:if test="${fn:contains(accessRecord02.capitalprepsection, '1个月内')}">checked</c:if> style="width: 1rem">1个月内 
											<input name="capitalprepsection" type="radio" value="017002-1~3月-1~3"<c:if test="${fn:contains(accessRecord02.capitalprepsection, '1~3个月')}">checked</c:if> style="width: 1rem">1-3月
											<input name="capitalprepsection" type="radio" value="017003-3~6月-3~6"<c:if test="${fn:contains(accessRecord02.capitalprepsection, '3~6个月')}">checked</c:if> style="width: 1rem">3-6月 
											<input name="capitalprepsection" type="radio" value="017004-6~12月-6~12"<c:if test="${fn:contains(accessRecord02.capitalprepsection, '6~12个月')}">checked</c:if> style="width: 1rem">6-12月 
											<input name="capitalprepsection" type="radio" value="017005-12月以上-12~"<c:if test="${fn:contains(accessRecord02.capitalprepsection, '12月以上')}">checked</c:if> style="width: 1rem">12月以上
											<input name="capitalprepsection" type="radio" value="017000-无法了解"<c:if test="${fn:contains(accessRecord02.capitalprepsection, '无法了解')}">checked</c:if> style="width: 1rem;">
											<span style="color: red">无法了解</span>
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
											<input name="custscore" type="radio" value="019001-A" <c:if test="${accessRecord02.custscore == 'A' }">checked</c:if> style="width: 1rem">A 
											<input name="custscore" type="radio" value="019002-B" <c:if test="${accessRecord02.custscore == 'B' }">checked</c:if> style="width: 1rem">B 
											<input name="custscore" type="radio" value="019003-C" <c:if test="${accessRecord02.custscore == 'C' }">checked</c:if> style="width: 1rem">C
											<input name="custscore" type="radio" value="019004-D" <c:if test="${accessRecord02.custscore == 'D' }">checked</c:if> style="width: 1rem">D
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
				url:'${appRoot}/wx/api/updateRecord02',
				//url:'${appRoot}/wx/api/addAfterVisit',
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

