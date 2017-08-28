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
										<td style="width: 320px;">性别：
											<input name="custsex" type="radio" value="002001-男" _text="男" style="width: 1rem" <c:if test="${accessRecord02.custsex == '男' }">checked</c:if>>男 
											<input name="custsex" type="radio" value="002001-女" _text="女" style="width: 1rem" <c:if test="${accessRecord02.custsex == '女' }">checked</c:if>>女
										</td>
										<td style="width: 270px">置业顾问：${name }</td>
									</tr>

									<tr>
										<td>联系方式：<input type="text" name="custphonenum"  value="${accessRecord02.custphonenum }"
											onblur="check_lianxi_ajax()" style="width: 100px">&nbsp;<span
											id="txt1" style="color: red"></span>
										</td>
										<td>本次到访时间：
											<input type="date" name="receptime1" value="<fmt:formatDate value="${accessRecord02.receptime}" pattern="yyyy-MM-dd"/>">
										</td>
										<td>首次到访时间：
											<input type = "date" name="firstknowtime1" value="<fmt:formatDate value="" pattern="yyyy-MM-dd"/>">
										</td>
									</tr>

									<tr>
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
											<input name="childagegroup" type="checkbox" value="020001-0-3岁" <c:if test="${fn:contains(accessRecord02.childagegroup, '0-3岁')}">checked</c:if> style="width: 1rem">0-3岁
											<input name="childagegroup" type="checkbox" value="020002-4-6岁" <c:if test="${fn:contains(accessRecord02.childagegroup, '4-6岁')}">checked</c:if> style="width: 1rem">4-6岁
											<input name="childagegroup" type="checkbox" value="020003-7-12岁" <c:if test="${fn:contains(accessRecord02.childagegroup, '7-12岁')}">checked</c:if> style="width: 1rem">7-12岁
											<input name="childagegroup" type="checkbox" value="020004-13-15岁" <c:if test="${fn:contains(accessRecord02.childagegroup, '13-15岁')}">checked</c:if> style="width: 1rem">13-15岁
											<input name="childagegroup" type="checkbox" value="020005-16-18岁" <c:if test="${fn:contains(accessRecord02.childagegroup, '16-18岁')}">checked</c:if> style="width: 1rem">16-18岁
											<input name="childagegroup" type="checkbox" value="020006-18岁以上" <c:if test="${fn:contains(accessRecord02.childagegroup, '18岁以上')}">checked</c:if> style="width: 1rem">18岁以上
											<input name="childagegroup" type="checkbox" value="020000-无法了解" <c:if test="${fn:contains(accessRecord02.childagegroup, '无法了解')}">checked</c:if> style="width: 1rem; color: red">
											<span style="color: red">无法了解</span></span>
										</td>
									</tr>

									<tr>
										<td colspan="2" id="td2"><span id="box2" style="display: block;">孩子在读学校类型：
											<input name="schooltype" type="checkbox" value="公立" <c:if test="${fn:contains(accessRecord02.schooltype, '公立')}">checked</c:if> style="width: 1rem" >公立
											<input name="schooltype" type="checkbox" value="私立" <c:if test="${fn:contains(accessRecord02.schooltype, '私立')}">checked</c:if> style="width: 1rem" >私立
											<input name="schooltype" type="checkbox" value="国际学校" <c:if test="${fn:contains(accessRecord02.schooltype, '国际学校')}">checked</c:if> style="width: 1rem" >国际学校
											<input name="schooltype" type="checkbox" value="国外学习" <c:if test="${fn:contains(accessRecord02.schooltype, '国外学习')}">checked</c:if> style="width: 1rem" >国外学习
											<input name="schooltype" type="checkbox" value="无法了解" <c:if test="${fn:contains(accessRecord02.schooltype, '无法了解')}">checked</c:if> style="width: 1rem; color: red" >
											<span style="color: red">无法了解</span></span>
										</td>
										<td id="td3"><span id="box3" style="display: block;">在读学校名称：
											<input type="text" style="width: 9rem" name="schoolname" value="${accessRecord02.schoolname }"></span>
										</td>
									</tr>

									<tr>
										<td colspan="3">您的生活半径：
											<input name="livingradius" type="checkbox" value="022001-二环以内" <c:if test="${fn:contains(accessRecord02.livingradius, '二环以内')}">checked</c:if> style="width: 1rem">二环以内
											<input name="livingradius" type="checkbox" value="022002-二环-三环" <c:if test="${fn:contains(accessRecord02.livingradius, '二环-三环')}">checked</c:if> style="width: 1rem">二环-三环 
											<input name="livingradius" type="checkbox" value="022003-三环-四环 " <c:if test="${fn:contains(accessRecord02.livingradius, '三环-四环')}">checked</c:if> style="width: 1rem">三环-四环
											<input name="livingradius" type="checkbox" value="022004-四环-五环" <c:if test="${fn:contains(accessRecord02.livingradius, '四环-五环')}">checked</c:if> style="width: 1rem">四环-五环 
											<input name="livingradius" type="checkbox" value="022005-五环以外" <c:if test="${fn:contains(accessRecord02.livingradius, '五环以外')}">checked</c:if> style="width: 1rem">五环以外
											<input name="livingradius" type="checkbox" value="022000-无法了解" <c:if test="${fn:contains(accessRecord02.livingradius, '无法了解')}">checked</c:if> style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>

									<tr>
										<td colspan="3">现居住社区名称：
											<input type="text" value="${accessRecord02.communityname }" style="width: 9rem" name="communityname">
										</td>
									</tr>

									<tr>
										<td colspan="3">目前的居住面积：
											<input name="liveacreage" type="radio" value="100㎡以下" <c:if test="${fn:contains(accessRecord02.liveacreage, '100㎡以下')}">checked</c:if> style="width: 1rem">100㎡以下 
											<input name="liveacreage" type="radio" value="200-300㎡" <c:if test="${fn:contains(accessRecord02.liveacreage, '200-300㎡以下')}">checked</c:if> style="width: 1rem">100-200㎡ 
											<input name="liveacreage" type="radio" value="300-400㎡" <c:if test="${fn:contains(accessRecord02.liveacreage, '300-400㎡')}">checked</c:if> style="width: 1rem">200-300㎡
											<input name="liveacreage" type="radio" value="400-500㎡" <c:if test="${fn:contains(accessRecord02.liveacreage, '400-500㎡')}">checked</c:if> style="width: 1rem">300-400㎡ 
											<input name="liveacreage" type="radio" value="500-600㎡" <c:if test="${fn:contains(accessRecord02.liveacreage, '500-600㎡')}">checked</c:if> style="width: 1rem">400-500㎡
											<input name="liveacreage" type="radio" value="700-800㎡" <c:if test="${fn:contains(accessRecord02.liveacreage, '700-800㎡')}">checked</c:if> style="width: 1rem">500㎡以上 
											<input name="liveacreage" type="radio" value="无法了解" <c:if test="${fn:contains(accessRecord02.liveacreage, '无法了解')}">checked</c:if> style="width: 1rem">
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
											<input name="quanzhitaitai" type="radio" value="是" style="width: 1rem">是 
											<input name="quanzhitaitai" type="radio" value="否" style="width: 1rem">否 
											<input name="quanzhitaitai" type="radio" value="无法了解" style="width: 1rem">
											<span style="color: red">无解</span>
										</td>
										<td>有保姆：<input name="baomu" type="radio" value="是" style="width: 1rem">是 
											<input name="baomu" type="radio" value="否" style="width: 1rem">否
											<input name="baomu" type="radio" value="无法了解" style="width: 1rem">
											<span style="color: red">无解</span>
										</td>
										<td>有宠物：
											<input name="chongwu" type="radio" value="是" style="width: 1rem">是 
											<input name="chongwu" type="radio" value="否" style="width: 1rem">否 
											<input name="chongwu" type="radio" value="无法了解" style="width: 1rem">
											<span style="color: red">无解</span>
										</td>
									</tr>

									<tr>
										<td>国际教育意愿：
											<input name="guojijiaoyu" type="radio" value="是" style="width: 1rem">是 
											<input name="guojijiaoyu" type="radio" value="否" style="width: 1rem">否
											<input name="guojijiaoyu" type="radio" value="无法了解" style="width: 1rem">
											<span style="color: red">无解</span>
										</td>
										<td>子女海外经历：
											<input name="zinvhaiwai" type="radio" value="是" style="width: 1rem">是 
											<input name="zinvhaiwai" type="radio" value="否" style="width: 1rem">否
											<input name="zinvhaiwai" type="radio" value="无法了解" style="width: 1rem">
											<span style="color: red">无解</span>
										</td>
										<td>业主海外经历：
											<input name="yezhuhaiwai" type="radio" value="是" style="width: 1rem">是 
											<input name="yezhuhaiwai" type="radio" value="否" style="width: 1rem">否
											<input name="yezhuhaiwai" type="radio" value="无法了解" style="width: 1rem">
											<span style="color: red">无解</span>
										</td>
									</tr>

									<tr>
										<td>名下房产：<select name="yigou_num">
												<option value="0">请选择</option>
												<option value="1套">1套</option>
												<option value="2套">2套</option>
												<option value="3套">3套</option>
												<option value="3套以上">3套以上</option>
												<option value="无房">无房</option>
												<option value="无法了解" style="color: red">无法了解</option>
										</select>


										</td>
										<td>家庭汽车数量：<input type="text" name="qiche_num"
											style="width: 3rem"
											onkeyup="value=value.replace()">辆
										</td>
										<td>汽车品牌：<input type="text" name="qiche_brand"
											style="width: 9rem">
										</td>
									</tr>

									<tr>
										<td colspan="3">驾车总价：
											<input name="cartotalprice" type="radio" value="30万以下" <c:if test="${fn:contains(accessRecord02.liveacreage, '30万以下')}">checked</c:if> style="width: 1rem">30万以下 
											<input name="cartotalprice" type="radio" value="30-50万" <c:if test="${fn:contains(accessRecord02.liveacreage, '30-50万')}">checked</c:if> style="width: 1rem">30-50万
											<input name="cartotalprice" type="radio" value="50-100万" <c:if test="${fn:contains(accessRecord02.liveacreage, '50-100万')}">checked</c:if> style="width: 1rem">50-100万 
											<input name="cartotalprice" type="radio" value="100-200万" <c:if test="${fn:contains(accessRecord02.liveacreage, '100-200万')}">checked</c:if> style="width: 1rem">100-200万
											<input name="cartotalprice" type="radio" value="200万以上" <c:if test="${fn:contains(accessRecord02.liveacreage, '200万以上')}">checked</c:if> style="width: 1rem">200万以上 
											<input name="cartotalprice" type="radio" value="200-300万" <c:if test="${fn:contains(accessRecord02.liveacreage, '200-300万')}">checked</c:if> style="width: 1rem">200-300万<br> 
											<input name="cartotalprice" type="radio" value="300-500万" <c:if test="${fn:contains(accessRecord02.liveacreage, '300-500万')}">checked</c:if> style="width: 1rem; margin-left: 95px">300-500万 
											<input name="cartotalprice" type="radio" value="500万以上" <c:if test="${fn:contains(accessRecord02.liveacreage, '500万以上')}">checked</c:if> style="width: 1rem">500万以上
											<input name="cartotalprice" type="radio" value="无法了解" <c:if test="${fn:contains(accessRecord02.liveacreage, '无法了解')}">checked</c:if> style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>

									<tr>
										<td colspan="1">关注的公众微信号：
											<input type="text" style="width: 7rem" name="gongzhong1">
											<input type="text" style="width: 7rem; display: none" name="gongzhong2">
											<input type="text" style="width: 7rem; display: none" name="gongzhong3">
										</td>

										<td colspan="2">常用APP(除微信外三个)：
											<input type="text" style="width: 6rem" name="app1">
											<input type="text" style="width: 6rem; display: none" name="app2">
											<input type="text" style="width: 6rem; display: none" name="app3">
										</td>
									</tr>

									<tr>
										<td colspan="3">您的业余爱好：
											<input name="aihao[]" type="checkbox" value="运动" style="width: 1rem">运动 
											<input name="aihao[]" type="checkbox" value="养生" style="width: 1rem">养生
											<input name="aihao[]" type="checkbox" value="步行" style="width: 1rem">美容 
											<input name="aihao[]" type="checkbox" value="阅读" style="width: 1rem">阅读 
											<input name="aihao[]" type="checkbox" value="理财" style="width: 1rem">理财
											<input name="aihao[]" type="checkbox" value="艺术" style="width: 1rem">艺术 
											<input name="aihao[]" type="checkbox" value="茶道" style="width: 1rem">茶道 
											<input name="aihao[]" type="checkbox" value="收藏" style="width: 1rem">收藏
											<input name="aihao[]" type="checkbox" value="购物" style="width: 1rem">购物<br> 
											<input name="aihao[]" type="checkbox" value="美食" style="width: 1rem; margin-left: 123px">美食 
											<input name="aihao[]" type="checkbox" value="园艺" style="width: 1rem">园艺
											<input name="aihao[]" type="checkbox" value="社交" style="width: 1rem">社交 
											<input name="aihao[]" type="checkbox" value="时尚" style="width: 1rem">时尚 
											<input name="aihao[]" type="checkbox" value="旅行" style="width: 1rem">旅行
											<input name="aihao[]" type="checkbox" value="教育" style="width: 1rem">教育 
											<input name="aihao[]" type="checkbox" value="慈善" style="width: 1rem">慈善 
											<input name="aihao[]" type="checkbox" value="无法了解" style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>

									<tr>
										<td colspan="3">您对本案的抗拒点：
											<input name="kangju[]" type="checkbox" value="位置" style="width: 1rem">位置 
											<input name="kangju[]" type="checkbox" value="产品类型" style="width: 1rem">产品类型 
											<input name="kangju[]" type="checkbox" value="交通" style="width: 1rem">交通 
											<input name="kangju[]" type="checkbox" value="区域环境" style="width: 1rem">区域环境 
											<input name="kangju[]" type="checkbox" value="价格" style="width: 1rem">价格 
											<input name="kangju[]" type="checkbox" value="户型" style="width: 1rem">户型 
											<input name="kangju[]" type="checkbox" value="花园面积" style="width: 1rem">花园面积<br>
											<input name="kangju[]" type="checkbox" value="园林环境" style="width: 1rem; margin-left: 151px">园林环境 
											<input name="kangju[]" type="checkbox" value="科技设备" style="width: 1rem">科技设备 
											<input name="kangju[]" type="checkbox" value="交房时间" style="width: 1rem">交房时间
											<input name="kangju[]" type="checkbox" value="社区配套" style="width: 1rem">社区配套 
											<input name="kangju[]" type="checkbox" value="交房时间" style="width: 1rem">交房时间
											<input name="kangju[]" type="checkbox" value="物业服务" style="width: 1rem">物业服务 
											<input name="kangju[]" type="checkbox" value="教育环境 " style="width: 1rem">教育环境<br>
											<input name="kangju[]" type="checkbox" value="开发品牌" style="width: 1rem; margin-left: 151px">开发品牌 
											<input name="kangju[]" type="checkbox" value="增值潜力" style="width: 1rem">增值潜力 
											<input name="kangju[]" type="checkbox" value="无法了解" style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>

									<tr>
										<td colspan="3">您喜欢参加的活动：
											<input name="huodong[]" type="checkbox" value="家庭教育" style="width: 1rem">家庭教育
											<input name="huodong[]" type="checkbox" value="国际学校教育" style="width: 1rem">国际学校教育 
											<input name="huodong[]" type="checkbox" value="亲子活动" style="width: 1rem">亲子活动
											<input name="huodong[]" type="checkbox" value="文化艺术" style="width: 1rem">文化艺术 
											<input name="huodong[]" type="checkbox" value="美食品鉴" style="width: 1rem">美食品鉴<br>
											<input name="huodong[]" type="checkbox" value="户外运动" style="width: 1rem; margin-left: 151px">户外运动 
											<input name="huodong[]" type="checkbox" value="养生保健" style="width: 1rem">养生保健 
											<input name="huodong[]" type="checkbox" value="理财规划" style="width: 1rem">理财规划
											<input name="huodong[]" type="checkbox" value="书刊杂志" style="width: 1rem">书刊杂志 
											<input name="huodong[]" type="checkbox" value="琴棋书画" style="width: 1rem">琴棋书画
											<input name="huodong[]" type="checkbox" value="无法了解" style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>

									<tr>
										<td colspan="3">可参加业主活动时间：
											<span style="margin-left: 20px">周一到周五（</span>
											<input name="huodongtime[]" type="checkbox" value="周一到周五上午9-11点" style="width: 1rem; margin-left: 5px">上午9-11点
											<input name="huodongtime[]" type="checkbox" value="周一到周五下午2-5点" style="width: 1rem">下午2-5点 
											<input name="huodongtime[]" type="checkbox" value="周一到周五晚上6-9点" style="width: 1rem">晚上6-9点 ）<br> 
											<span style="margin-left: 160px"> 周六到周日（</span> 
											<input name="huodongtime[]" type="checkbox" value="周六到周日上午9-11点" style="width: 1rem; margin-left: 5px">上午9-11点 
											<input name="huodongtime[]" type="checkbox" value="周六到周日下午2-5点" style="width: 1rem">下午2-5点 
											<input name="huodongtime[]" type="checkbox" value="周六到周日晚上6-9点" style="width: 1rem">晚上6-9点 ） 
											<input name="huodongtime[]" type="checkbox" value="无法了解" style="width: 1rem">
											<span style="color: red">无法了解</span>
										</td>
									</tr>


									<tr>
										<td colspan="3">来访人数：
											<input name="renshu" type="radio" value="一人" style="width: 1rem" >一人 
											<input name="renshu" type="radio" value="两人" style="width: 1rem" >两人 
											<input name="renshu" type="radio" value="三人" style="width: 1rem" >三人 
											<input name="renshu" type="radio" value="四人" style="width: 1rem" >四人 
											<input name="renshu" type="radio" value="五人及以上" style="width: 1rem" >五人及以上
										</td>
									</tr>

									<tr>
										<td colspan="3">
											<span id="box4" style="display: block;">来访人之间关系：
											<input name="guanxi[]" type="checkbox" value="夫妻" style="width: 1rem">夫妻 
											<input name="guanxi[]" type="checkbox" value="与父母同行" style="width: 1rem">与父母同行
											<input name="guanxi[]" type="checkbox" value="与子女同行" style="width: 1rem">与子女同行 
											<input name="guanxi[]" type="checkbox" value="朋友" style="width: 1rem">朋友 
											<input name="guanxi[]" type="checkbox" value="同事" style="width: 1rem">同事
										</span></td>
									</tr>

									<tr>
										<td colspan="3">本次参观接待时间：
											<input name="canguan" type="radio" value="15分钟以内" style="width: 1rem">15分钟以内
											<input name="canguan" type="radio" value="15-30分钟" style="width: 1rem">15-30分钟 
											<input name="canguan" type="radio" value="30-60分钟" style="width: 1rem">30-60分钟
											<input name="canguan" type="radio" value="60-120分钟" style="width: 1rem">60-120分钟 
											<input name="canguan" type="radio" value="120分钟以上" style="width: 1rem">120分钟以上
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
												name="miaoshu"></textarea>
										</td>
									</tr>

									<tr>
										<td colspan="3">复访未成交原因分析：<br>
										<textarea style="width: 800px; height: 100px; margin-left: 0.1rem"
												name="yuanyin"></textarea>
										</td>
									</tr>

									<tr>
										<td colspan="3">客户评级：
											<input name="pingji" type="radio" value="A" style="width: 1rem">A 
											<input name="pingji" type="radio" value="B" style="width: 1rem">B 
											<input name="pingji" type="radio" value="C" style="width: 1rem">C
											<input name="pingji" type="radio" value="D" style="width: 1rem">D
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

