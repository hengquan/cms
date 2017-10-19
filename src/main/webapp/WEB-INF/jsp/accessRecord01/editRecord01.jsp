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
;border
:
1px
 
solid
 
#DDD
;background
:
#fff
;-moz-box-shadow
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
);-webkit-box-shadow
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
);box-shadow
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
);z-index
:
-1;left
:
1115px;top
:
3px;width
:
73px;height
:
28px
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

								<div class="col-lg-2">客户首访信息</div>
								<a href="javascript:history.go(-1)"
									class="btn btn-send mini btn-white col-lg-1 pull-right"
									style="align: right;"><i
									class="glyphicon glyphicon-arrow-left"></i></a>

							</header>
						</section>
					</div>
				</div>

				<div
					style="border: 4px solid #f0f0f0; background-color: #fff; padding-top: 0px; border-top: 0px; position: relative; padding-top: 10px">
					<form action="http://kylindpc.cn/zz_user.php" method="post"
						name="theForm" id="theform" >



						<div class="tijiao" id="tijiao"
							style="height: 1.5rem; padding: 20px 0px; position: relative; top: 0px; background: white;">
							<!-- <input type="button" onclick="chongzhi()" value="重置表单"
								class="btn-4"
								style="width: 100px; height: 30px; font-weight: bold; font-family: &amp; #39; 微软雅黑 &amp;#39;; margin-left: 20px">
 -->

							<input type="button" onclick="checkinput()" value="提交表单"
								class="btn-4"
								style="width: 100px; height: 30px; font-weight: bold; font-family: &amp; #39; 微软雅黑 &amp;#39;; margin-left: 10px">


							<input type="hidden" name="act" value="shoufang_insert">
							<input type="hidden" name="xiangmu_id" value="25">

						</div>


						<table id="orderinfo" cellpadding="0" cellspacing="0"
							bgcolor="#dddddd" border="1" style="margin-top: 40px;">
							<tbody>
								<tr style="">
									<input type="hidden" name="id" value="${accessRecord01.id }" />
									<input type="hidden" name="custid" value="${accessRecord01.custid }" />
									<input type="hidden" name="projid" value="${accessRecord01.projid }" />
									<input type="hidden" name="userId" value="${userId }" />
									<td style="width: 330px;">姓名：
										<input type="text" name="custname" value="${accessRecord01.custname }" style="width: 100px">
									</td>
									<td style="width: 320px;">性别：
										<input name="custsex" type="radio" value="002001-男" _text="男" style="width: 1rem" <c:if test="${accessRecord01.custsex == '男' }">checked</c:if>>男 
										<input name="custsex" type="radio" value="002001-女" _text="女" style="width: 1rem" <c:if test="${accessRecord01.custsex == '女' }">checked</c:if>>女
									</td>
									<td style="width: 270px">置业顾问：${name }</td>
								</tr>

								<tr>
									<td>联系方式：<input type="text" name="custphonenum"  value="${accessRecord01.custphonenum }"
										onblur="check_lianxi_ajax()" style="width: 100px">&nbsp;<span
										id="txt1" style="color: red"></span></td>
									<td>第一次获知本案时间：
										<input type = "date" name="firstknowtime1" value="<fmt:formatDate value="${accessRecord01.firstknowtime}" pattern="yyyy-MM-dd"/>">
									</td>
									<td>本次到访时间：
										<input type="date" name="receptime1" value="<fmt:formatDate value="${accessRecord01.receptime}" pattern="yyyy-MM-dd"/>">
									</td>
								</tr>

								<tr>
									<td colspan="3">年龄段：
									<input name="agegroup" type="radio" value="003001-25岁以下-0~25" _text="25岁以下" <c:if test="${fn:contains(accessRecord01.agegroup, '25岁以下')}">checked</c:if> style="width: 1rem">25岁以下 
									<input name="agegroup" type="radio" value="003002-26~35岁-26~35" _text="26~35岁" <c:if test="${fn:contains(accessRecord01.agegroup, '26~35岁')}">checked</c:if>  style="width: 1rem">26-35岁 
									<input name="agegroup" type="radio" value="003003-36~45岁-36~45" _text="36~45岁" <c:if test="${fn:contains(accessRecord01.agegroup, '36~45岁')}">checked</c:if>  style="width: 1rem">36-45岁
									<input name="agegroup" type="radio" value="003004-46~55岁-46~55" _text="46~55岁"  <c:if test="${fn:contains(accessRecord01.agegroup, '46~55岁')}">checked</c:if> style="width: 1rem">46-55岁 
									<input name="agegroup" type="radio" value="003005-56岁以上-56~300" _text="56岁以上" <c:if test="${fn:contains(accessRecord01.agegroup, '56岁以上')}">checked</c:if>  style="width: 1rem">56岁以上 
									<input name="agegroup" type="radio" value="003000-无法了解" _text="无法了解" <c:if test="${fn:contains(accessRecord01.agegroup, '无法了解')}">checked</c:if>  style="width: 1rem">
									<span style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">有无购房资格：
										<select name="buyqualify">
											<option value="0">请选择</option>
											<option <c:if test="${accessRecord01.buyqualify == '有'}">selected</c:if> value="004001-有">有</option>
											<option <c:if test="${accessRecord01.buyqualify == '无(换产权名解决)'}">selected</c:if> value="004002-无(换产权名解决)">无（换产权名解决）</option>
											<option <c:if test="${accessRecord01.buyqualify == '无(房产过户解决)'}">selected</c:if> value="004003-无(房产过户解决)">无（房产过户解决）</option>
											<option <c:if test="${accessRecord01.buyqualify == '无(离婚解决)'}">selected</c:if> value="004004-无(离婚解决)">无（离婚解决）</option>
											<option <c:if test="${accessRecord01.buyqualify == '无(公司产权解决)'}">selected</c:if> value="004005-无(公司产权解决)">无（公司产权解决）</option>
											<option <c:if test="${accessRecord01.buyqualify == '无(其它解决)'}">selected</c:if> value="004006-无(其它解决)">无（其它解决）</option>
											<option <c:if test="${accessRecord01.buyqualify == '无法了解'}">selected</c:if> value="004000-无法了解" style="color: red">无法了解</option>
										</select>
									</td>
								</tr>
								<input type="hidden" value="${accessRecord01.localresidence }" id="bdjz">
								<input type="hidden" value="${accessRecord01.localworkarea }" id="bdgz">
								<input type="hidden" value="${accessRecord01.outresidence }" id="wfjz">
								<input type="hidden" value="${accessRecord01.outworkarea }" id="wfgz">
								
								<input type="hidden" value="" id="localresidence" name="localresidence">
								<input type="hidden" value="" id="localworkarea" name="localworkarea">
								<input type="hidden" value="" id="outresidence" name="outresidence">
								<input type="hidden" value="" id="outworkarea" name="outworkarea">
								<tr>
									<td colspan="3"><span style="float:left">本地居住：</span>
									  <div>
							          	<select id="city" name="city">
											<option value="-1">请选择</option>
										</select>
								
										<select name="area" id="area">
											<option value="-1">请选择</option>
										</select>
								      </div>
									</td>
								</tr>
								<tr>
									<td colspan="3"><span style="float:left">本地工作：</span>
								      <div>
							          	<select id="workCity" name="workCity">
											<option value="-1">请选择</option>
										</select>
								
										<select name="workArea" id="workArea">
											<option value="-1">请选择</option>
										</select>
								      </div>
								</tr>		
								<tr>
									<td colspan="3"><span style="float:left">外埠居住：</span>
								      <div>
							            <select id="province3" name="province3">
											<option value="-1">请选择</option>
										</select>
								
										<select id="city3" name="city3">
											<option value="-1">请选择</option>
										</select>
								
										<select name="area3" id="area3">
											<option value="-1">请选择</option>
										</select>
								      </div>
									</td>
								</tr>
								<tr>
									<td colspan="3"><span style="float:left">外埠工作：</span>
								      <div>
							            <select id="province1" name="province1">
											<option value="-1">请选择</option>
										</select>
								
										<select id="city1" name="city1">
											<option value="-1">请选择</option>
										</select>
								
										<select name="area1" id="area1">
											<option value="-1">请选择</option>
										</select>
								      </div>
									</td>
								</tr>
								
								<tr>
									<td colspan="3">家庭状况：
										<input name="familystatus" type="radio" <c:if test="${fn:contains(accessRecord01.familystatus, '单身')}">checked</c:if> value="005001-单身" _text="单身" style="width: 1rem">单身 
										<input name="familystatus" type="radio" <c:if test="${fn:contains(accessRecord01.familystatus, '夫妻')}">checked</c:if> value="005002-夫妻" _text="夫妻" style="width: 1rem">夫妻
										<input name="familystatus" type="radio" <c:if test="${fn:contains(accessRecord01.familystatus, '一孩家庭')}">checked</c:if> value="005003-一孩家庭" _text="一孩家庭" style="width: 1rem">一孩家庭 
										<input name="familystatus" type="radio" <c:if test="${fn:contains(accessRecord01.familystatus, '俩孩家庭')}">checked</c:if> value="005004-俩孩家庭" _text="俩孩家庭" style="width: 1rem">俩孩家庭 
										<input name="familystatus" type="radio" <c:if test="${fn:contains(accessRecord01.familystatus, '三孩及以上家庭')}">checked</c:if> value="005005-三孩及以上家庭" _text="三孩及以上家庭" style="width: 1rem">三孩及以上家庭
										<input name="familystatus" type="radio" <c:if test="${fn:contains(accessRecord01.familystatus, '三代同堂')}">checked</c:if> value="005006-三代同堂" _text="三代同堂" style="width: 1rem">三代同堂 
										<input name="familystatus" type="radio" <c:if test="${fn:contains(accessRecord01.familystatus, '无法了解')}">checked</c:if> value="005000-无法了解" style="width: 1rem">
										<span style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">您的出行方式：
										<input name="traffictypedesc"type="checkbox" value="006001-自驾车" _text="自驾车" <c:if test="${fn:contains(accessRecord01.traffictypedesc, '自驾车')}">checked</c:if> style="width: 1rem">自驾车 
										<input name="traffictypedesc" type="checkbox" value="006002-公交" _text="公交" <c:if test="${fn:contains(accessRecord01.traffictypedesc, '公交')}">checked</c:if> style="width: 1rem">公交 
										<input name="traffictypedesc" type="checkbox" value="006003-地铁" _text="地铁" <c:if test="${fn:contains(accessRecord01.traffictypedesc, '地铁')}">checked</c:if> style="width: 1rem">地铁 
										<input name="traffictypedesc" type="checkbox" value="006004-步行" _text="步行" <c:if test="${fn:contains(accessRecord01.traffictypedesc, '步行')}">checked</c:if> style="width: 1rem">步行 
										<input name="traffictypedesc" type="checkbox" value="006005-打车" _text="打车" <c:if test="${fn:contains(accessRecord01.traffictypedesc, '打车')}">checked</c:if> style="width: 1rem">打车 
										<input name="traffictypedesc" type="checkbox" value="006000-无法了解" _text="无法了解" <c:if test="${fn:contains(accessRecord01.traffictypedesc, '无法了解')}">checked</c:if> style="width: 1rem">
										<span style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">您的从事行业：
										<input name="workindustry" type="radio" value="007001-建筑房地产/装修" _text="建筑房地产/装修" <c:if test="${fn:contains(accessRecord01.workindustry, '建筑房地产/装修')}">checked</c:if> style="width: 1rem">建筑房地产/装修 
										<input name="workindustry" type="radio" value="007002-旅游/文化/媒体" _text="旅游/文化/媒体" <c:if test="${fn:contains(accessRecord01.workindustry, '旅游/文化/媒体')}">checked</c:if> style="width: 1rem">旅游/文化/媒体
										<input name="workindustry" type="radio" value="007003-能源" _text="能源" <c:if test="${fn:contains(accessRecord01.workindustry, '能源')}">checked</c:if> style="width: 1rem">能源 
										<input name="workindustry" type="radio" value="007004-IT" <c:if test="${fn:contains(accessRecord01.workindustry, 'IT')}">checked</c:if> style="width: 1rem">IT 
										<input name="workindustry" type="radio" value="007005-金融投资" <c:if test="${fn:contains(accessRecord01.workindustry, '金融投资')}">checked</c:if> style="width: 1rem">金融投资
										<input name="workindustry" type="radio" value="007006-外贸" _text="外贸" <c:if test="${fn:contains(accessRecord01.workindustry, '外贸')}">checked</c:if> style="width: 1rem">外贸
										<input name="workindustry" type="radio" value="007007-交通/物流/零售" _text="交通/物流/零售" <c:if test="${fn:contains(accessRecord01.workindustry, '交通/物流/零售')}">checked</c:if> style="width: 1rem">交通/物流/零售<br>
										<input name="workindustry" type="radio" value="007008-餐饮" _text="餐饮" <c:if test="${fn:contains(accessRecord01.workindustry, '餐饮')}">checked</c:if> style="width: 1rem; margin-left: 123px">餐饮 
										<input name="workindustry" type="radio" value="007009-医疗" _text="医疗" <c:if test="${fn:contains(accessRecord01.workindustry, '医疗')}">checked</c:if> style="width: 1rem">医疗
										<input name="workindustry" type="radio" value="007010-教育" _text="教育" <c:if test="${fn:contains(accessRecord01.workindustry, '教育')}">checked</c:if> style="width: 1rem">教育 
										<input name="workindustry" type="radio" value="007011-通讯" _text="通讯" <c:if test="${fn:contains(accessRecord01.workindustry, '通讯')}">checked</c:if> style="width: 1rem">通讯 
										<input name="workindustry" type="radio" value="007012-律师/法务" _text="律师/法务"<c:if test="${fn:contains(accessRecord01.workindustry, '律师/法务')}">checked</c:if> style="width: 1rem">律师/法务
										<input name="workindustry" type="radio" value="007013-退休" _text="退休"<c:if test="${fn:contains(accessRecord01.workindustry, '退休')}">checked</c:if> style="width: 1rem">退休 
										<input name="workindustry" type="radio" value="007999-其他" _text="其他" <c:if test="${fn:contains(accessRecord01.workindustry, '其它')}">checked</c:if> style="width: 1rem">其它 
										<input name="workindustry" type="radio" value="007000-无法了解" _text="无法了解" <c:if test="${fn:contains(accessRecord01.workindustry, '无法了解')}">checked</c:if> style="width: 1rem">
										<span style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">您的企业性质：
										<input name="enterprisetype" type="radio" value="008001-国家行政机关及国企" _text="国家行政机关及国企" <c:if test="${fn:contains(accessRecord01.enterprisetype, '国家行政机关及国企')}">checked</c:if> style="width: 1rem">国家行政机关及国企
										<input name="enterprisetype" type="radio" value="008002-民营企业" _text="民营企业" <c:if test="${fn:contains(accessRecord01.enterprisetype, '民营企业')}">checked</c:if> style="width: 1rem">民营企业
										<input name="enterprisetype" type="radio" value="008003-外资企业" _text="外资企业" <c:if test="${fn:contains(accessRecord01.enterprisetype, '外贸企业')}">checked</c:if> style="width: 1rem">外资企业 
										<input name="enterprisetype" type="radio" value="008004-合资企业" _text="合资企业" <c:if test="${fn:contains(accessRecord01.enterprisetype, '合资企业')}">checked</c:if> style="width: 1rem">合资企业 
										<input name="enterprisetype" type="radio" value="008005-私营" _text="私营" <c:if test="${fn:contains(accessRecord01.enterprisetype, '私营')}">checked</c:if> style="width: 1rem">私营
										<input name="enterprisetype" type="radio" value="008999-其他" _text="其他" <c:if test="${fn:contains(accessRecord01.enterprisetype, '其它')}">checked</c:if> style="width: 1rem">其它 
										<input name="enterprisetype" type="radio" value="008000-无法了解" _text="无法了解" <c:if test="${fn:contains(accessRecord01.enterprisetype, '无法了解')}">checked</c:if> style="width: 1rem">
										<span style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">您关注的产品类型：
										<span style="margin-left: 20px">别墅</span>
										（<input name="realtyproducttype" type="checkbox" value="009001001-独栋" _text="独栋" <c:if test="${fn:contains(accessRecord01.realtyproducttype, '独栋')}">checked</c:if> style="width: 1rem">独栋
										<input name="realtyproducttype" type="checkbox" value="009001002-类独栋" _text="类独栋" <c:if test="${fn:contains(accessRecord01.realtyproducttype, '类独栋')}">checked</c:if> style="width: 1rem">类独栋 
										<input name="realtyproducttype" type="checkbox" value="009001003-双拼" _text="双拼" <c:if test="${fn:contains(accessRecord01.realtyproducttype, '双拼')}">checked</c:if> style="width: 1rem">双拼 
										<input name="realtyproducttype" type="checkbox" value="009001004-联排" _text="联排" <c:if test="${fn:contains(accessRecord01.realtyproducttype, '联排')}">checked</c:if> style="width: 1rem">联排
										<input name="realtyproducttype" type="checkbox" value="009001005-上叠" _text="上叠" <c:if test="${fn:contains(accessRecord01.realtyproducttype, '上叠')}">checked</c:if> style="width: 1rem">上叠 
										<input name="realtyproducttype" type="checkbox" value="009001006-下叠" _text="下叠" <c:if test="${fn:contains(accessRecord01.realtyproducttype, '下叠')}">checked</c:if> style="width: 1rem">下叠&nbsp;&nbsp;&nbsp;&nbsp;）<br>
										<span style="margin-left: 145px">平层（</span>
										<input name="realtyproducttype" type="checkbox" value="009002001-两居及以下" _text="两居及以下"<c:if test="${fn:contains(accessRecord01.realtyproducttype, '两居及以下')}">checked</c:if> style="width: 1rem;">两居及以下 
										<input name="realtyproducttype" type="checkbox" value="009002002-三居" _text="三居" <c:if test="${fn:contains(accessRecord01.realtyproducttype, '三居')}">checked</c:if> style="width: 1rem">三居 
										<input name="realtyproducttype" type="checkbox" value="009002003-四居" _text="四居" <c:if test="${fn:contains(accessRecord01.realtyproducttype, '四居')}">checked</c:if> style="width: 1rem">四居
										<input name="realtyproducttype" type="checkbox" value="009002004-五居及以上叠" _text="五居及以上叠" <c:if test="${fn:contains(accessRecord01.realtyproducttype, '五居及以上叠')}">checked</c:if> style="width: 1rem">五居及以上叠&nbsp;&nbsp;&nbsp;&nbsp;）<br>
										<input name="realtyproducttype" type="checkbox" value="009003-商业" _text="商业" <c:if test="${fn:contains(accessRecord01.realtyproducttype, '商业')}">checked</c:if> style="width: 1rem; margin-left: 145px">商业 
										<input name="realtyproducttype" type="checkbox" value="00904-商业办公" _text="商业办公" <c:if test="${fn:contains(accessRecord01.realtyproducttype, '商务办公')}">checked</c:if> style="width: 1rem; margin-left: 16px">商务办公 
										<input name="realtyproducttype" type="checkbox" value="009999-其他" _text="其他" <c:if test="${fn:contains(accessRecord01.realtyproducttype, '其他')}">checked</c:if> style="width: 1rem">其他
										<input name="realtyproducttype" type="checkbox" value="009000-无法了解" _text="无法了解" <c:if test="${fn:contains(accessRecord01.realtyproducttype, '无法了解')}">checked</c:if> style="width: 1rem">
										<span style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">您关注区域面积：
										<input name="attentacreage" type="radio" value="010001-200㎡以下-0~200" _text="200㎡以下" <c:if test="${fn:contains(accessRecord01.attentacreage, '200㎡以下')}">checked</c:if> style="width: 1rem">200㎡以下 
										<input name="attentacreage" type="radio" value="010002-200~300㎡-200~300" _text="200~300㎡" <c:if test="${fn:contains(accessRecord01.attentacreage, '200-300㎡')}">checked</c:if> style="width: 1rem">200-300㎡ 
										<input name="attentacreage" type="radio" value="010003-300~400㎡-300~400" _text="300~400㎡" <c:if test="${fn:contains(accessRecord01.attentacreage, '300-400㎡')}">checked</c:if> style="width: 1rem">300-400㎡
										<input name="attentacreage" type="radio" value="010004-400~500㎡-400~500" _text="400~500㎡" <c:if test="${fn:contains(accessRecord01.attentacreage, '400-500㎡')}">checked</c:if> style="width: 1rem">400-500㎡ 
										<input name="attentacreage" type="radio" value="010005-500~600㎡-500~600" <c:if test="${fn:contains(accessRecord01.attentacreage, '500-600㎡')}">checked</c:if> style="width: 1rem">500-600㎡<br>
										<input name="attentacreage" type="radio" value="010006-600~700㎡-600~700" <c:if test="${fn:contains(accessRecord01.attentacreage, '600-700㎡')}">checked</c:if> style="width: 1rem; margin-left: 137px">600-700㎡ 
										<input name="attentacreage" type="radio" value="010007-700~800㎡-700~800" <c:if test="${fn:contains(accessRecord01.attentacreage, '700-800㎡')}">checked</c:if> style="width: 1rem">700-800㎡ 
										<input name="attentacreage" type="radio" value="010008-800㎡以上-800~" _text="800㎡以上" <c:if test="${fn:contains(accessRecord01.attentacreage, '800㎡以上')}">checked</c:if> style="width: 1rem">800㎡以上
										<input name="attentacreage" type="radio" value="010000-无法了解" _text="无法了解" <c:if test="${fn:contains(accessRecord01.attentacreage, '无法了解')}">checked</c:if> style="width: 1rem">
										<span style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">您接受的总房款：
										<input name="pricesection" type="radio" value="011001-500万以下-0~500" _text="500万以下" <c:if test="${fn:contains(accessRecord01.pricesection, '500万以下')}">checked</c:if> style="width: 1rem">500万以下
										<input name="pricesection" type="radio" value="011002-500~1000万-500~1000" _text="500~1000万" <c:if test="${fn:contains(accessRecord01.pricesection, '500-1000万')}">checked</c:if> style="width: 1rem">500-1000万 
										<input name="pricesection" type="radio" value="011003-1000~1500万-1000~1500" _text="1000~1500万" <c:if test="${fn:contains(accessRecord01.pricesection, '1000-1500万')}">checked</c:if> style="width: 1rem">1000-1500万
										<input name="pricesection" type="radio" value="011004-1500~2000万-1500~2000" _text="1500~2000万" <c:if test="${fn:contains(accessRecord01.pricesection, '1500-2000万')}">checked</c:if> style="width: 1rem">1500-2000万
										<input name="pricesection" type="radio" value="011005-2000~3000万-2000~3000" _text="2000~3000万" <c:if test="${fn:contains(accessRecord01.pricesection, '2000-3000万')}">checked</c:if> style="width: 1rem">2000-3000万<br>
										<input name="pricesection" type="radio" value="011006-3000~4000万-3000~4000" _text="3000~4000万" <c:if test="${fn:contains(accessRecord01.pricesection, '3000-4000万')}">checked</c:if> style="width: 1rem; margin-left: 137px">3000-4000万 
										<input name="pricesection" type="radio" value="011007-4000~5000万-4000~5000" _text="4000~5000万" <c:if test="${fn:contains(accessRecord01.pricesection, '4000-5000万')}">checked</c:if> style="width: 1rem;">4000-5000万 
										<input name="pricesection" type="radio" value="011008-5000~6000万-5000~6000" _text="5000~6000万" <c:if test="${fn:contains(accessRecord01.pricesection, '5000-6000万')}">checked</c:if> style="width: 1rem">5000-6000万
										<input name="pricesection" type="radio" value="011009-6000~8000万-6000~8000" _text="6000~8000万" <c:if test="${fn:contains(accessRecord01.pricesection, '6000-8000万')}">checked</c:if> style="width: 1rem">6000-8000万 
										<input name="pricesection" type="radio" value="011010-8000万以上-8000~" _text="8000万以上" <c:if test="${fn:contains(accessRecord01.pricesection, '8000万以上')}">checked</c:if> style="width: 1rem">8000万以上
										<input name="pricesection" type="radio" value="011000-无法了解" _text="无法了解" <c:if test="${fn:contains(accessRecord01.pricesection, '无法了解')}">checked</c:if> style="width: 1rem">
										<span style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">您的购房目的：
										<input name="buypurpose" type="radio" value="012001-自住第一居所" <c:if test="${fn:contains(accessRecord01.buypurpose, '自住第一居所')}">checked</c:if> style="width: 1rem">自住第一居所 
										<input name="buypurpose" type="radio" value="012002-自住第二居所" <c:if test="${fn:contains(accessRecord01.buypurpose, '自住第二居所')}">checked</c:if> style="width: 1rem">自住第二居所
										<input name="buypurpose" type="radio" value="012003-为子女购房" <c:if test="${fn:contains(accessRecord01.buypurpose, '为子女购房')}">checked</c:if> style="width: 1rem">为子女购房 
										<input name="buypurpose" type="radio" value="012004-为父母购房" <c:if test="${fn:contains(accessRecord01.buypurpose, '为父母购房')}">checked</c:if> style="width: 1rem">为父母购房 
										<input name="buypurpose" type="radio" value="012005-自住兼投资" <c:if test="${fn:contains(accessRecord01.buypurpose, '自住兼投资')}">checked</c:if> style="width: 1rem">自住兼投资
										<input name="buypurpose" type="radio" value="012006-仅投资" <c:if test="${fn:contains(accessRecord01.buypurpose, '仅投资')}">checked</c:if> style="width: 1rem">仅投资 
										<input name="buypurpose" type="radio" value="012999-其他" <c:if test="${fn:contains(accessRecord01.buypurpose, '其它')}">checked</c:if> style="width: 1rem">其它<br>
										<input name="buypurpose" type="radio" value="012000-无法了解" <c:if test="${fn:contains(accessRecord01.buypurpose, '无法了解')}">checked</c:if> style="width: 1rem; margin-left: 123px">
										<span style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">认知本案渠道：
										<input name="knowway" type="checkbox" value="013001-户外广告牌" <c:if test="${fn:contains(accessRecord01.knowway, '户外广告牌')}">checked</c:if> style="width: 1rem">户外广告牌&nbsp;
										<input type="text" name="qudao_guanggao" style="width: 10rem"> 
										<input name="knowway" type="checkbox" value="013002-网络" <c:if test="${fn:contains(accessRecord01.knowway, '网络')}">checked</c:if> style="width: 1rem">网络
										<input name="knowway" type="checkbox" value="013003-短信" <c:if test="${fn:contains(accessRecord01.knowway, '短信')}">checked</c:if> style="width: 1rem">短信 
										<input name="knowway" type="checkbox" value="013004-纸媒" <c:if test="${fn:contains(accessRecord01.knowway, '纸媒')}">checked</c:if> style="width: 1rem">纸媒 
										<input name="knowway" type="checkbox" value="013005-路过" <c:if test="${fn:contains(accessRecord01.knowway, '路过')}">checked</c:if> style="width: 1rem">路过
										<input name="knowway" type="checkbox" value="013006-朋友介绍" <c:if test="${fn:contains(accessRecord01.knowway, '朋友介绍')}">checked</c:if> style="width: 1rem">朋友介绍<br> 
										<input name="knowway" type="checkbox" value="013007-渠道介绍" <c:if test="${fn:contains(accessRecord01.knowway, '渠道介绍')}">checked</c:if> style="width: 1rem; margin-left: 123px;">渠道介绍
										<input type="text" name="qudao_qudao" style="width: 10rem">公司 
										<input name="knowway" type="checkbox" value="013008-巡展" <c:if test="${fn:contains(accessRecord01.knowway, '巡展')}">checked</c:if> style="width: 1rem">巡展
										<input name="knowway" type="checkbox" value="013009-广播" <c:if test="${fn:contains(accessRecord01.knowway, '广播')}">checked</c:if> style="width: 1rem;">广播 
										<input name="knowway" type="checkbox" value="013010-电CALL"<c:if test="${fn:contains(accessRecord01.knowway, '电CALL')}">checked</c:if> style="width: 1rem">电CALL
										<input name="knowway" type="checkbox" value="013011-直投"<c:if test="${fn:contains(accessRecord01.knowway, '直投')}">checked</c:if> style="width: 1rem">直投 
										<input name="knowway" type="checkbox" value="013012-活动"<c:if test="${fn:contains(accessRecord01.knowway, '活动')}">checked</c:if> style="width: 1rem">活动 
										<input name="knowway" type="checkbox" value="013013-DM单"<c:if test="${fn:contains(accessRecord01.knowway, 'DM单')}">checked</c:if> style="width: 1rem;">DM单 
										<input name="knowway" type="checkbox" value="013014-外联"<c:if test="${fn:contains(accessRecord01.knowway, '外联')}">checked</c:if> style="width: 1rem">外联<br>
										<input name="knowway" type="checkbox" value="013015-老带新"<c:if test="${fn:contains(accessRecord01.knowway, '老带新')}">checked</c:if> style="width: 1rem; margin-left: 123px;">老带新 
										<input name="knowway" type="checkbox" value="013999-其他"<c:if test="${fn:contains(accessRecord01.knowway, '其它')}">checked</c:if> style="width: 1rem;">其它
										<input name="knowway" type="checkbox" value="013000-无法了解"<c:if test="${fn:contains(accessRecord01.knowway, '无法了解')}">checked</c:if> style="width: 1rem">
										<span style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">您对本案的关注点：
										<input name="attentionpoint" type="checkbox" value="014001-位置"<c:if test="${fn:contains(accessRecord01.attentionpoint, '位置')}">checked</c:if> style="width: 1rem">位置 
										<input name="attentionpoint" type="checkbox" value="014002-产品类型"<c:if test="${fn:contains(accessRecord01.attentionpoint, '产品类型')}">checked</c:if> style="width: 1rem">产品类型 
										<input name="attentionpoint" type="checkbox" value="014003-交通"<c:if test="${fn:contains(accessRecord01.attentionpoint, '交通')}">checked</c:if> style="width: 1rem">交通 
										<input name="attentionpoint" type="checkbox" value="014004-区域环境"<c:if test="${fn:contains(accessRecord01.attentionpoint, '区域环境')}">checked</c:if> style="width: 1rem">区域环境 
										<input name="attentionpoint" type="checkbox" value="014005-价格"<c:if test="${fn:contains(accessRecord01.attentionpoint, '价格')}">checked</c:if> style="width: 1rem">价格 
										<input name="attentionpoint" type="checkbox" value="014006-户型"<c:if test="${fn:contains(accessRecord01.attentionpoint, '户型')}">checked</c:if> style="width: 1rem">户型 
										<input name="attentionpoint" type="checkbox" value="014007-花园面积"<c:if test="${fn:contains(accessRecord01.attentionpoint, '花园面积')}">checked</c:if> style="width: 1rem">花园面积<br>
										<input name="attentionpoint" type="checkbox" value="014008-园林环境"<c:if test="${fn:contains(accessRecord01.attentionpoint, '园林环境')}">checked</c:if> style="width: 1rem; margin-left: 151px">园林环境 
										<input name="attentionpoint" type="checkbox" value="014009-科技设备"<c:if test="${fn:contains(accessRecord01.attentionpoint, '科技设备')}">checked</c:if> style="width: 1rem">科技设备 
										<input name="attentionpoint" type="checkbox" value="014010-交房时间"<c:if test="${fn:contains(accessRecord01.attentionpoint, '交房时间')}">checked</c:if> style="width: 1rem">交房时间
										<input name="attentionpoint" type="checkbox" value="014011-社区配套"<c:if test="${fn:contains(accessRecord01.attentionpoint, '社区配套')}">checked</c:if> style="width: 1rem">社区配套 
										<input name="attentionpoint" type="checkbox" value="014012-物业服务"<c:if test="${fn:contains(accessRecord01.attentionpoint, '物业服务')}">checked</c:if> style="width: 1rem">物业服务
										<input name="attentionpoint" type="checkbox" value="014013-教育环境"<c:if test="${fn:contains(accessRecord01.attentionpoint, '教育环境')}">checked</c:if> style="width: 1rem">教育环境 
										<input name="attentionpoint" type="checkbox" value="014014-开发品牌"<c:if test="${fn:contains(accessRecord01.attentionpoint, '开发品牌')}">checked</c:if> style="width: 1rem">开发品牌<br>
										<input name="attentionpoint" type="checkbox" value="014015-增值潜力"<c:if test="${fn:contains(accessRecord01.attentionpoint, '增值潜力')}">checked</c:if> style="width: 1rem; margin-left: 151px">增值潜力 
										<input name="attentionpoint" type="checkbox" value="012999-其他"<c:if test="${fn:contains(accessRecord01.attentionpoint, '其它')}">checked</c:if> style="width: 1rem;">其它 
										<input name="attentionpoint" type="checkbox" value="012000-无法了解"<c:if test="${fn:contains(accessRecord01.attentionpoint, '无法了解')}">checked</c:if> style="width: 1rem">
										<span style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">预估身价：
										<input name="estcustworth" type="radio" value="015001-1千万以下-0~1000"<c:if test="${fn:contains(accessRecord01.estcustworth, '1千万以下')}">checked</c:if> style="width: 1rem">1千万以下 
										<input name="estcustworth" type="radio" value="015002-1千~5千万-1000~5000"<c:if test="${fn:contains(accessRecord01.estcustworth, '1千-5千万')}">checked</c:if> style="width: 1rem">1千-5千万
										<input name="estcustworth" type="radio" value="015003-5千万~1亿-5000~10000"<c:if test="${fn:contains(accessRecord01.estcustworth, '5千万-1亿')}">checked</c:if> style="width: 1rem">5千万-1亿 
										<input name="estcustworth" type="radio" value="015004-1~3亿-10000~30000"<c:if test="${fn:contains(accessRecord01.estcustworth, '1-3亿')}">checked</c:if> style="width: 1rem">1-3亿 
										<input name="estcustworth" type="radio" value="015005-3亿以上-30000~"<c:if test="${fn:contains(accessRecord01.estcustworth, '3亿以上')}">checked</c:if> style="width: 1rem">3亿以上
										<input name="estcustworth" type="radio" value="015000-无法了解"<c:if test="${fn:contains(accessRecord01.estcustworth, '无法了解')}">checked</c:if> style="width: 1rem;">
										<span style="color: red">无法了解</span>
									</td>
								</tr>




								<tr>
									<td colspan="3">重点投资：
										<input name="investtype" type="checkbox" value="016001-股票"<c:if test="${fn:contains(accessRecord01.investtype, '股票')}">checked</c:if> style="width: 1rem">股票 
										<input name="investtype" type="checkbox" value="016002-基金"<c:if test="${fn:contains(accessRecord01.investtype, '基金')}">checked</c:if> style="width: 1rem">基金
										<input name="investtype" type="checkbox" value="016003-黄金"<c:if test="${fn:contains(accessRecord01.investtype, '黄金')}">checked</c:if> style="width: 1rem">黄金 
										<input name="investtype" type="checkbox" value="016004-股指期货"<c:if test="${fn:contains(accessRecord01.investtype, '股脂期货')}">checked</c:if> style="width: 1rem">股指期货
										<input name="investtype" type="checkbox" value="016005-外汇"<c:if test="${fn:contains(accessRecord01.investtype, '外汇')}">checked</c:if> style="width: 1rem">外汇 
										<input name="investtype" type="checkbox" value="016006-艺术品"<c:if test="${fn:contains(accessRecord01.investtype, '艺术品')}">checked</c:if> style="width: 1rem">艺术品 
										<input name="investtype" type="checkbox" value="016007-保险"<c:if test="${fn:contains(accessRecord01.investtype, '保险')}">checked</c:if> style="width: 1rem">保险
										<input name="investtype" type="checkbox" value="016008-民间投资"<c:if test="${fn:contains(accessRecord01.investtype, '民间投资')}">checked</c:if> style="width: 1rem">民间投资 
										<input name="investtype" type="checkbox" value="016999-其他"<c:if test="${fn:contains(accessRecord01.investtype, '其它')}">checked</c:if> style="width: 1rem">其它 
										<input name="investtype" type="checkbox" value="016000-无法了解"<c:if test="${fn:contains(accessRecord01.investtype, '无法了解')}">checked</c:if> style="width: 1rem">
										<span style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">资金筹备期：
										<input name="capitalprepsection" type="radio" value="017001-1个月内-0~1"<c:if test="${fn:contains(accessRecord01.capitalprepsection, '1个月内')}">checked</c:if> style="width: 1rem">1个月内 
										<input name="capitalprepsection" type="radio" value="017002-1~3月-1~3"<c:if test="${fn:contains(accessRecord01.capitalprepsection, '1-3个月')}">checked</c:if> style="width: 1rem">1-3月
										<input name="capitalprepsection" type="radio" value="017003-3~6月-3~6"<c:if test="${fn:contains(accessRecord01.capitalprepsection, '3-6个月')}">checked</c:if> style="width: 1rem">3-6月 
										<input name="capitalprepsection" type="radio" value="017004-6~12月-6~12"<c:if test="${fn:contains(accessRecord01.capitalprepsection, '6-12个月')}">checked</c:if> style="width: 1rem">6-12月 
										<input name="capitalprepsection" type="radio" value="017005-12月以上-12~"<c:if test="${fn:contains(accessRecord01.capitalprepsection, '12月以上')}">checked</c:if> style="width: 1rem">12月以上
										<input name="capitalprepsection" type="radio" value="017000-无法了解"<c:if test="${fn:contains(accessRecord01.capitalprepsection, '无法了解')}">checked</c:if> style="width: 1rem;">
										<span style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3" style="display: none">您本次置业比选项目：<br>
									<textarea
											style="width: 800px; height: 100px; margin-left: 0.1rem"
											name="bijiao"></textarea>
									</td>
								</tr>

								<tr>
									<td colspan="3">您本次置业比选项目： 
										<input type="text" name="compareprojs" style="width: 160px" value="${accessRecord01.compareprojs }"> 
									</td>
								</tr>

								<tr>
									<td colspan="3">本次参观接待时间：
										<input name="receptimesection" type="radio" value="018001-15分钟以内-0~15"<c:if test="${fn:contains(accessRecord01.receptimesection, '15分钟以内')}">checked</c:if> style="width: 1rem">15分钟以内
										<input name="receptimesection" type="radio" value="018002-15~30分钟-15~30"<c:if test="${fn:contains(accessRecord01.receptimesection, '15-30分钟')}">checked</c:if> style="width: 1rem">15-30分钟 
										<input name="receptimesection" type="radio" value="018003-30^60分钟-30~60"<c:if test="${fn:contains(accessRecord01.receptimesection, '30-60分钟')}">checked</c:if> style="width: 1rem">30-60分钟
										<input name="receptimesection" type="radio" value="018004-60~120分钟-60~120"<c:if test="${fn:contains(accessRecord01.receptimesection, '60-120分钟')}">checked</c:if> style="width: 1rem">60-120分钟 
										<input name="receptimesection" type="radio" value="018005-120分钟以上-120~"<c:if test="${fn:contains(accessRecord01.receptimesection, '120分钟以上')}">checked</c:if> style="width: 1rem">120分钟以上
										<input name="receptimesection" type="radio" value="018000-无法了解"<c:if test="${fn:contains(accessRecord01.receptimesection, '无法了解')}">checked</c:if> style="width: 1rem">无法了解
									</td>
								</tr>

								<tr>
									<td colspan="3">首访客户描述：<br>
									<textarea style="width: 800px; height: 100px; margin-left: 0.1rem"
											name="custdescn">${accessRecord01.custdescn}</textarea>
									</td>
								</tr>

								<tr>
									<td colspan="3">客户评级：
										<input name="custscore" type="radio" value="019001-A"<c:if test="${fn:contains(accessRecord01.custscore, 'A')}">checked</c:if> style="width: 1rem">A 
										<input name="custscore" type="radio" value="019002-B"<c:if test="${fn:contains(accessRecord01.custscore, 'B')}">checked</c:if> style="width: 1rem">B 
										<input name="custscore" type="radio" value="019003-C"<c:if test="${fn:contains(accessRecord01.custscore, 'C')}">checked</c:if> style="width: 1rem">C
										<input name="custscore" type="radio" value="019004-D"<c:if test="${fn:contains(accessRecord01.custscore, 'D')}">checked</c:if> style="width: 1rem">D
									</td>
								</tr>
							</tbody>
						</table>
						
						<div class="tijiao" id="tijiao"
							style="height: 1.5rem; padding: 20px 0px; position: relative; top: 0px; background: white;margin-bottom: 40px;">
							<input type="button" onclick="checkinput()" value="提交表单"
								class="btn-4"
								style="width: 100px; height: 30px; font-weight: bold; font-family: &amp; #39; 微软雅黑 &amp;#39;; margin-left: 10px">
						</div>
						
						
					</form>
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
	<input type="hidden" value="" id="localCityValue" />
	<input type="hidden" value="" id="localAreaValue"/>
	<input type="hidden" value="" id="localCityValue1"/>
	<input type="hidden" value="" id="localAreaValue1"/>
	<input type="hidden" value="" id="outerProvinceValue1"/>
	<input type="hidden" value="" id="outerCityValue1"/>
	<input type="hidden" value="" id="outerAreaValue1"/>
	<input type="hidden" value="" id="outerProvinceValue"/>
	<input type="hidden" value="" id="outerCityValue"/>
	<input type="hidden" value="" id="outerAreaValue"/>
	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>
	<script type="text/javascript" src="${appRoot}/static/js/localArea.js"></script>
	<script type="text/javascript" src="${appRoot}/static/js/allArea.js"></script>
	<script type="text/javascript" src="${appRoot}/static/js/foreachArea.js"></script>
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

		//提交表单
		function checkinput() {
			var a = $("#localCityValue").val() +','+$("#localAreaValue").val();
			var b = $("#localCityValue1").val() +','+$("#localAreaValue1").val();
			var c = $("#outerProvinceValue1").val() +','+$("#outerCityValue1").val()+','+$("#outerAreaValue1").val();
			var d = $("#outerProvinceValue").val() +','+$("#outerCityValue").val()+','+$("#outerAreaValue").val();
			$("#localresidence").val(a);
			$("#localworkarea").val(b);
			$("#outresidence").val(c);
			$("#outworkarea").val(d);
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

