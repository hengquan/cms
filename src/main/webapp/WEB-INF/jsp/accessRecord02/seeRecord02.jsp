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
								<input type="button" onclick="chongzhi()" value="重置表单"
									class="btn-4"
									style="width: 100px; height: 30px; font-weight: bold; font-family: &amp; #39; 微软雅黑 &amp;#39;; margin-left: 20px">
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
											<input name="liveacreage" type="radio" value="023001-100㎡以下" <c:if test="${fn:contains(accessRecord02.liveacreage, '100㎡以下')}">checked</c:if> style="width: 1rem">100㎡以下 
											<input name="liveacreage" type="radio" value="023002-100-200㎡" <c:if test="${fn:contains(accessRecord02.liveacreage, '100-200㎡')}">checked</c:if> style="width: 1rem">100-200㎡ 
											<input name="liveacreage" type="radio" value="023003-200-300㎡ " <c:if test="${fn:contains(accessRecord02.liveacreage, '200-300㎡')}">checked</c:if> style="width: 1rem">200-300㎡
											<input name="liveacreage" type="radio" value="023004-300-400㎡" <c:if test="${fn:contains(accessRecord02.liveacreage, '300-400㎡ ')}">checked</c:if> style="width: 1rem">300-400㎡ 
											<input name="liveacreage" type="radio" value="023005-400-500㎡" <c:if test="${fn:contains(accessRecord02.liveacreage, '400-500㎡')}">checked</c:if> style="width: 1rem">400-500㎡
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
											<input name="cartotalprice" type="radio" value="30万以下" <c:if test="${fn:contains(accessRecord02.cartotalprice, '30万以下')}">checked</c:if> style="width: 1rem">30万以下 
											<input name="cartotalprice" type="radio" value="30-50万" <c:if test="${fn:contains(accessRecord02.cartotalprice, '30-50万')}">checked</c:if> style="width: 1rem">30-50万
											<input name="cartotalprice" type="radio" value="50-100万" <c:if test="${fn:contains(accessRecord02.cartotalprice, '50-100万')}">checked</c:if> style="width: 1rem">50-100万 
											<input name="cartotalprice" type="radio" value="100-200万" <c:if test="${fn:contains(accessRecord02.cartotalprice, '100-200万')}">checked</c:if> style="width: 1rem">100-200万
											<input name="cartotalprice" type="radio" value="200万以上" <c:if test="${fn:contains(accessRecord02.cartotalprice, '200万以上')}">checked</c:if> style="width: 1rem">200万以上 
											<input name="cartotalprice" type="radio" value="200-300万" <c:if test="${fn:contains(accessRecord02.cartotalprice, '200-300万')}">checked</c:if> style="width: 1rem">200-300万<br> 
											<input name="cartotalprice" type="radio" value="300-500万" <c:if test="${fn:contains(accessRecord02.cartotalprice, '300-500万')}">checked</c:if> style="width: 1rem; margin-left: 95px">300-500万 
											<input name="cartotalprice" type="radio" value="500万以上" <c:if test="${fn:contains(accessRecord02.cartotalprice, '500万以上')}">checked</c:if> style="width: 1rem">500万以上
											<input name="cartotalprice" type="radio" value="无法了解" <c:if test="${fn:contains(accessRecord02.cartotalprice, '无法了解')}">checked</c:if> style="width: 1rem">
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
										<td colspan="3">您对本案的抗拒点：
											<input name="resistpoint" type="checkbox" value="014001-位置" <c:if test="${fn:contains(accessRecord02.resistpoint, '位置')}">checked</c:if> style="width: 1rem">位置 
											<input name="resistpoint" type="checkbox" value="014002-产品类型" <c:if test="${fn:contains(accessRecord02.resistpoint, '产品类型')}">checked</c:if> style="width: 1rem">产品类型 
											<input name="resistpoint" type="checkbox" value="014003-交通" <c:if test="${fn:contains(accessRecord02.resistpoint, '交通')}">checked</c:if> style="width: 1rem">交通 
											<input name="resistpoint" type="checkbox" value="014004-区域环境" <c:if test="${fn:contains(accessRecord02.resistpoint, '区域环境')}">checked</c:if> style="width: 1rem">区域环境 
											<input name="resistpoint" type="checkbox" value="014005-价格" <c:if test="${fn:contains(accessRecord02.resistpoint, '价格')}">checked</c:if> style="width: 1rem">价格 
											<input name="resistpoint" type="checkbox" value="014006-户型" <c:if test="${fn:contains(accessRecord02.resistpoint, '户型')}">checked</c:if> style="width: 1rem">户型 
											<input name="resistpoint" type="checkbox" value="014007-花园面积" <c:if test="${fn:contains(accessRecord02.resistpoint, '花园面积')}">checked</c:if> style="width: 1rem">花园面积<br>
											<input name="resistpoint" type="checkbox" value="014008-园林环境" <c:if test="${fn:contains(accessRecord02.resistpoint, '园林环境')}">checked</c:if> style="width: 1rem; margin-left: 151px">园林环境 
											<input name="resistpoint" type="checkbox" value="014009-科技设备" <c:if test="${fn:contains(accessRecord02.resistpoint, '科技设备')}">checked</c:if> style="width: 1rem">科技设备 
											<input name="resistpoint" type="checkbox" value="014010-交房时间" <c:if test="${fn:contains(accessRecord02.resistpoint, '交房时间')}">checked</c:if> style="width: 1rem">交房时间
											<input name="resistpoint" type="checkbox" value="014011-社区配套" <c:if test="${fn:contains(accessRecord02.resistpoint, '社区配套')}">checked</c:if> style="width: 1rem">社区配套 
											<input name="resistpoint" type="checkbox" value="014012-物业服务" <c:if test="${fn:contains(accessRecord02.resistpoint, '物业服务')}">checked</c:if> style="width: 1rem">物业服务 
											<input name="resistpoint" type="checkbox" value="014013-开发品牌" <c:if test="${fn:contains(accessRecord02.resistpoint, '开发品牌')}">checked</c:if> style="width: 1rem; margin-left: 151px">开发品牌 
											<input name="resistpoint" type="checkbox" value="014014-增值潜力" <c:if test="${fn:contains(accessRecord02.resistpoint, '增值潜力')}">checked</c:if> style="width: 1rem">增值潜力 
											<input name="resistpoint" type="checkbox" value="014000-无法了解" <c:if test="${fn:contains(accessRecord02.resistpoint, '无法了解')}">checked</c:if> style="width: 1rem">
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
											<input name="freetimesection" type="checkbox" value="009001001-上午9-11点" <c:if test="${fn:contains(accessRecord02.freetimesection, '上午9-11点')}">checked</c:if> style="width: 1rem; margin-left: 5px">上午9-11点
											<input name="freetimesection" type="checkbox" value="009001002-下午2-5点" <c:if test="${fn:contains(accessRecord02.freetimesection, '下午2-5点')}">checked</c:if> style="width: 1rem">下午2-5点 
											<input name="freetimesection" type="checkbox" value="009001003-晚上6-9点" <c:if test="${fn:contains(accessRecord02.freetimesection, '晚上6-9点')}">checked</c:if> style="width: 1rem">晚上6-9点 ）<br> 
											<span style="margin-left: 160px"> 周六到周日（</span> 
											<input name="freetimesection" type="checkbox" value="009002001-上午9-11点" <c:if test="${fn:contains(accessRecord02.freetimesection, '上午9-11点')}">checked</c:if> style="width: 1rem; margin-left: 5px">上午9-11点 
											<input name="freetimesection" type="checkbox" value="009002002-下午2-5点" <c:if test="${fn:contains(accessRecord02.freetimesection, '下午2-5点')}">checked</c:if> style="width: 1rem">下午2-5点 
											<input name="freetimesection" type="checkbox" value="009002004-晚上6-9点" <c:if test="${fn:contains(accessRecord02.freetimesection, '晚上6-9点')}">checked</c:if> style="width: 1rem">晚上6-9点 ） 
											<input name="freetimesection" type="checkbox" value="009000-无法了解" <c:if test="${fn:contains(accessRecord02.freetimesection, '无法了解')}">checked</c:if> style="width: 1rem">
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
											<input name="receptimesection" type="radio" value="018002-15~30分钟-15~30" <c:if test="${fn:contains(accessRecord02.receptimesection, '15-30分钟')}">checked</c:if> style="width: 1rem">15-30分钟 
											<input name="receptimesection" type="radio" value="018003-30^60分钟-30~60" <c:if test="${fn:contains(accessRecord02.receptimesection, '30-60分钟')}">checked</c:if> style="width: 1rem">30-60分钟
											<input name="receptimesection" type="radio" value="018004-60~120分钟-60~120" <c:if test="${fn:contains(accessRecord02.receptimesection, '60-120分钟')}">checked</c:if> style="width: 1rem">60-120分钟 
											<input name="receptimesection" type="radio" value="018005-120分钟以上-120~" <c:if test="${fn:contains(accessRecord02.receptimesection, '120分钟以上')}">checked</c:if> style="width: 1rem">120分钟以上
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

