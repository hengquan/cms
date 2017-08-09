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
						name="theForm" id="theform" onsubmit="return checkinput()">



						<div class="tijiao" id="tijiao"
							style="height: 1.5rem; padding: 20px 0px; position: relative; top: 0px; background: white;">
							<input type="button" onclick="chongzhi()" value="重置表单"
								class="btn-4"
								style="width: 100px; height: 30px; font-weight: bold; font-family: &amp; #39; 微软雅黑 &amp;#39;; margin-left: 20px">


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
									<td style="width: 330px;">姓名：<input type="text"
										name="xingming"></td>
									<td style="width: 320px;">性别：<input name="sex"
										type="radio" value="男" style="width: 1rem">男 <input
										name="sex" type="radio" value="女" style="width: 1rem">女
									</td>
									<td style="width: 270px">置业顾问：贺渝测试</td>
								</tr>

								<tr>
									<td>联系方式：<input type="text" name="lianxi"
										onblur="check_lianxi_ajax()" style="width: 100px">&nbsp;<span
										id="txt1" style="color: red"></span></td>
									<td>第一次获知本案时间： <input type="text" id="start_date"
										name="benan_time" value="" style="width: 100px">


									</td>
									<td>本次到访时间：<input type="text" name="daofang_time"
										value="2017-07-15" style="width: 100px"></td>
								</tr>

								<tr>
									<td colspan="3">年龄段：<input name="nianling" type="radio"
										value="25岁以下" style="width: 1rem">25岁以下 <input
										name="nianling" type="radio" value="26-35岁"
										style="width: 1rem">26-35岁 <input name="nianling"
										type="radio" value="36-45岁" style="width: 1rem">36-45岁
										<input name="nianling" type="radio" value="46-55岁"
										style="width: 1rem">46-55岁 <input name="nianling"
										type="radio" value="56岁以上" style="width: 1rem">56岁以上 <input
										name="nianling" type="radio" value="无法了解" style="width: 1rem"><span
										style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td>本地居住： <select name="juzhu_qu_id" id="selDistricts_1">
											<option value="0">请选择区</option>
											<option value="517">延庆县</option>
											<option value="516">密云县</option>
											<option value="515">大兴区</option>
											<option value="514">平谷区</option>
											<option value="513">怀柔区</option>
											<option value="512">昌平区</option>
											<option value="511">顺义区</option>
											<option value="510">通州区</option>
											<option value="509">门头沟区</option>
											<option value="508">房山区</option>
											<option value="507">石景山区</option>
											<option value="506">丰台区</option>
											<option value="503">朝阳区</option>
											<option value="502">海淀区</option>
											<option value="501">西城区</option>
											<option value="500">东城区</option>


									</select> <select name="juzhu_bankuai_id" id="selBankuai_1">
											<option value="0">请选择板块</option>

											<option value="3555">延庆</option>
									</select> <a onclick="chongzhi_juzhu_qu()"
										style="cursor: pointer; margin-left: 10px; color: #0000FF; text-decoration: none">清空</a>
									</td>

									<td>本地工作： <select name="work_qu_id" id="selDistricts_3">
											<option value="0">请选择区</option>
											<option value="517">延庆县</option>
											<option value="516">密云县</option>
											<option value="515">大兴区</option>
											<option value="514">平谷区</option>
											<option value="513">怀柔区</option>
											<option value="512">昌平区</option>
											<option value="511">顺义区</option>
											<option value="510">通州区</option>
											<option value="509">门头沟区</option>
											<option value="508">房山区</option>
											<option value="507">石景山区</option>
											<option value="506">丰台区</option>
											<option value="503">朝阳区</option>
											<option value="502">海淀区</option>
											<option value="501">西城区</option>
											<option value="500">东城区</option>


									</select> <select name="work_bankuai_id" id="selBankuai_3">
											<option value="0">请选择板块</option>

											<option value="3557">平谷</option>
									</select> <a onclick="chongzhi_work_qu()"
										style="cursor: pointer; margin-left: 10px; color: #0000FF; text-decoration: none">清空</a>
									</td>

									<td>有无购房资格：<select name="zige">
											<option value="0">请选择</option>
											<option value="有">有</option>
											<option value="无（换产权名解决）">无（换产权名解决）</option>
											<option value="无（房产过户解决）">无（房产过户解决）</option>
											<option value="无（离婚解决）">无（离婚解决）</option>
											<option value="无（公司产权解决）">无（公司产权解决）</option>
											<option value="无（公司产权解决）">无（其它解决）</option>
											<option value="无法了解" style="color: red">无法了解</option>
									</select>
									</td>
								</tr>

								<tr>
									<td>外埠居住： <select name="juzhu_province_id"
										id="selProvinces_2">
											<option value="0">请选择省</option>
											<option value="35">台湾</option>
											<option value="34">澳门</option>
											<option value="33">香港</option>
											<option value="32">重庆</option>
											<option value="31">浙江</option>
											<option value="30">云南</option>
											<option value="29">新疆</option>
											<option value="28">西藏</option>
											<option value="27">天津</option>
											<option value="26">四川</option>
											<option value="25">上海</option>
											<option value="24">陕西</option>
											<option value="23">山西</option>
											<option value="22">山东</option>
											<option value="21">青海</option>
											<option value="20">宁夏</option>
											<option value="19">内蒙古</option>
											<option value="18">辽宁</option>
											<option value="17">江西</option>
											<option value="16">江苏</option>
											<option value="15">吉林</option>
											<option value="14">湖南</option>
											<option value="13">湖北</option>
											<option value="12">黑龙江</option>
											<option value="11">河南</option>
											<option value="10">河北</option>
											<option value="9">海南</option>
											<option value="8">贵州</option>
											<option value="7">广西</option>
											<option value="6">广东</option>
											<option value="5">甘肃</option>
											<option value="4">福建</option>
											<option value="3">安徽</option>
											<option value="2">北京</option>
									</select> <select name="juzhu_city_id" id="selCities_2">
											<option value="0">请选择市</option>
											<option value="395">香港</option>
									</select> <a onclick="chongzhi_juzhu_waifu()"
										style="cursor: pointer; margin-left: 10px; color: #0000FF; text-decoration: none">清空</a>
									</td>

									<td>外埠工作： <select name="work_province_id"
										id="selProvinces_4">
											<option value="0">请选择省</option>
											<option value="35">台湾</option>
											<option value="34">澳门</option>
											<option value="33">香港</option>
											<option value="32">重庆</option>
											<option value="31">浙江</option>
											<option value="30">云南</option>
											<option value="29">新疆</option>
											<option value="28">西藏</option>
											<option value="27">天津</option>
											<option value="26">四川</option>
											<option value="25">上海</option>
											<option value="24">陕西</option>
											<option value="23">山西</option>
											<option value="22">山东</option>
											<option value="21">青海</option>
											<option value="20">宁夏</option>
											<option value="19">内蒙古</option>
											<option value="18">辽宁</option>
											<option value="17">江西</option>
											<option value="16">江苏</option>
											<option value="15">吉林</option>
											<option value="14">湖南</option>
											<option value="13">湖北</option>
											<option value="12">黑龙江</option>
											<option value="11">河南</option>
											<option value="10">河北</option>
											<option value="9">海南</option>
											<option value="8">贵州</option>
											<option value="7">广西</option>
											<option value="6">广东</option>
											<option value="5">甘肃</option>
											<option value="4">福建</option>
											<option value="3">安徽</option>
											<option value="2">北京</option>
									</select> <select name="work_city_id" id="selCities_4">
											<option value="0">请选择市</option>
											<option value="395">香港</option>
									</select> <a onclick="chongzhi_work_waifu()"
										style="cursor: pointer; margin-left: 10px; color: #0000FF; text-decoration: none">清空</a>
									</td>

									<td></td>
								</tr>

								<tr>
									<td colspan="3">家庭状况：<input name="jiating" type="radio"
										value="单身" style="width: 1rem">单身 <input
										name="jiating" type="radio" value="夫妻" style="width: 1rem">夫妻
										<input name="jiating" type="radio" value="一孩家庭"
										style="width: 1rem">一孩家庭 <input name="jiating"
										type="radio" value="俩孩家庭" style="width: 1rem">俩孩家庭 <input
										name="jiating" type="radio" value="三孩及以上家庭"
										style="width: 1rem">三孩及以上家庭 <input name="jiating"
										type="radio" value="三代同堂" style="width: 1rem">三代同堂 <input
										name="jiating" type="radio" value="无法了解" style="width: 1rem"><span
										style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">您的出行方式：<input name="chuxing[]"
										type="checkbox" value="自驾车" style="width: 1rem">自驾车 <input
										name="chuxing[]" type="checkbox" value="公交"
										style="width: 1rem">公交 <input name="chuxing[]"
										type="checkbox" value="地铁" style="width: 1rem">地铁 <input
										name="chuxing[]" type="checkbox" value="步行"
										style="width: 1rem">步行 <input name="chuxing[]"
										type="checkbox" value="打车" style="width: 1rem">打车 <input
										name="chuxing[]" type="checkbox" value="无法了解"
										style="width: 1rem"><span style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">您的从事行业：<input name="shiye" type="radio"
										value="建筑房地产/装修" style="width: 1rem">建筑房地产/装修 <input
										name="shiye" type="radio" value="旅游/文化/媒体" style="width: 1rem">旅游/文化/媒体
										<input name="shiye" type="radio" value="能源"
										style="width: 1rem">能源 <input name="shiye"
										type="radio" value="IT" style="width: 1rem">IT <input
										name="shiye" type="radio" value="金融投资" style="width: 1rem">金融投资
										<input name="shiye" type="radio" value="外贸"
										style="width: 1rem">外贸 <input name="shiye"
										type="radio" value="交通/物流/零售" style="width: 1rem">交通/物流/零售<br>
										<input name="shiye" type="radio" value="餐饮"
										style="width: 1rem; margin-left: 123px">餐饮 <input
										name="shiye" type="radio" value="医疗" style="width: 1rem">医疗
										<input name="shiye" type="radio" value="教育"
										style="width: 1rem">教育 <input name="shiye"
										type="radio" value="通讯" style="width: 1rem">通讯 <input
										name="shiye" type="radio" value="律师/法务" style="width: 1rem">律师/法务
										<input name="shiye" type="radio" value="退休"
										style="width: 1rem">退休 <input name="shiye"
										type="radio" value="其它" style="width: 1rem">其它 <input
										name="shiye" type="radio" value="无法了解" style="width: 1rem"><span
										style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">您的企业性质：<input name="xingzhi" type="radio"
										value="国家行政机关及国企" style="width: 1rem">国家行政机关及国企 <input
										name="xingzhi" type="radio" value="民营企业 " style="width: 1rem">民营企业
										<input name="xingzhi" type="radio" value="外资企业"
										style="width: 1rem">外资企业 <input name="xingzhi"
										type="radio" value="合资企业" style="width: 1rem">合资企业 <input
										name="xingzhi" type="radio" value="私营" style="width: 1rem">私营
										<input name="xingzhi" type="radio" value="其它"
										style="width: 1rem">其它 <input name="xingzhi"
										type="radio" value="无法了解" style="width: 1rem"><span
										style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">您关注的产品类型：<span style="margin-left: 20px">别墅</span>（<input
										name="leixing" type="radio" value="别墅独栋" style="width: 1rem">独栋
										<input name="leixing" type="radio" value="别墅类独栋"
										style="width: 1rem">类独栋 <input name="leixing"
										type="radio" value="别墅双拼" style="width: 1rem">双拼 <input
										name="leixing" type="radio" value="别墅联排" style="width: 1rem">联排
										<input name="leixing" type="radio" value="别墅上叠"
										style="width: 1rem">上叠 <input name="leixing"
										type="radio" value="别墅下叠" style="width: 1rem">下叠&nbsp;&nbsp;&nbsp;&nbsp;）<br>

										<span style="margin-left: 145px">平层（</span><input
										name="leixing" type="radio" value="平层两居及以下"
										style="width: 1rem;">两居及以下 <input name="leixing"
										type="radio" value="平层三居" style="width: 1rem">三居 <input
										name="leixing" type="radio" value="平层四居" style="width: 1rem">四居
										<input name="leixing" type="radio" value="平层五居及以上"
										style="width: 1rem">五居及以上叠&nbsp;&nbsp;&nbsp;&nbsp;）<br>
										<input name="leixing" type="radio" value="商业"
										style="width: 1rem; margin-left: 145px">商业 <input
										name="leixing" type="radio" value="商务办公"
										style="width: 1rem; margin-left: 16px">商务办公 <input
										name="leixing" type="radio" value="无法了解" style="width: 1rem"><span
										style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">您关注区域面积：<input name="mianji" type="radio"
										value="200㎡以下" style="width: 1rem">200㎡以下 <input
										name="mianji" type="radio" value="200-300㎡"
										style="width: 1rem">200-300㎡ <input name="mianji"
										type="radio" value="300-400㎡" style="width: 1rem">300-400㎡
										<input name="mianji" type="radio" value="400-500㎡"
										style="width: 1rem">400-500㎡ <input name="mianji"
										type="radio" value="500-600㎡" style="width: 1rem">500-600㎡<br>
										<input name="mianji" type="radio" value="500-600㎡"
										style="width: 1rem; margin-left: 137px">600-700㎡ <input
										name="mianji" type="radio" value="700-800㎡"
										style="width: 1rem">700-800㎡ <input name="mianji"
										type="radio" value="800㎡以上" style="width: 1rem">800㎡以上
										<input name="mianji" type="radio" value="无法了解"
										style="width: 1rem"><span style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">您接受的总房款：<input name="fangkuan"
										type="radio" value="500万以下" style="width: 1rem">500万以下
										<input name="fangkuan" type="radio" value="500-1000万"
										style="width: 1rem">500-1000万 <input name="fangkuan"
										type="radio" value="1000-1500万" style="width: 1rem">1000-1500万
										<input name="fangkuan" type="radio" value="1500-2000万"
										style="width: 1rem">1500-2000万 <input name="fangkuan"
										type="radio" value="2000-3000万" style="width: 1rem">2000-3000万<br>
										<input name="fangkuan" type="radio" value="3000-4000万"
										style="width: 1rem; margin-left: 137px">3000-4000万 <input
										name="fangkuan" type="radio" value="4000-5000万"
										style="width: 1rem;">4000-5000万 <input name="fangkuan"
										type="radio" value="5000-6000万" style="width: 1rem">5000-6000万
										<input name="fangkuan" type="radio" value="6000-8000万"
										style="width: 1rem">6000-8000万 <input name="fangkuan"
										type="radio" value="8000万以上" style="width: 1rem">8000万以上
										<input name="fangkuan" type="radio" value="无法了解"
										style="width: 1rem"><span style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">您的购房目的：<input name="mudi" type="radio"
										value="自住第一居所" style="width: 1rem">自住第一居所 <input
										name="mudi" type="radio" value="自住第二居所" style="width: 1rem">自住第二居所
										<input name="mudi" type="radio" value="为子女购房"
										style="width: 1rem">为子女购房 <input name="mudi"
										type="radio" value="为父母购房" style="width: 1rem">为父母购房 <input
										name="mudi" type="radio" value="自住兼投资" style="width: 1rem">自住兼投资
										<input name="mudi" type="radio" value="仅投资"
										style="width: 1rem">仅投资 <input name="mudi"
										type="radio" value="其它" style="width: 1rem">其它<br>
										<input name="mudi" type="radio" value="无法了解"
										style="width: 1rem; margin-left: 123px"><span
										style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">认知本案渠道：<input name="qudao[]"
										type="checkbox" value="户外广告牌" style="width: 1rem">户外广告牌&nbsp;<input
										type="text" name="qudao_guanggao"> <input
										name="qudao[]" type="checkbox" value="网络" style="width: 1rem">网络
										<input name="qudao[]" type="checkbox" value="短信"
										style="width: 1rem">短信 <input name="qudao[]"
										type="checkbox" value="纸媒" style="width: 1rem">纸媒 <input
										name="qudao[]" type="checkbox" value="路过" style="width: 1rem">路过
										<input name="qudao[]" type="checkbox" value="朋友介绍"
										style="width: 1rem">朋友介绍<br> <input
										name="qudao[]" type="checkbox" value="渠道介绍"
										style="width: 1rem; margin-left: 123px;">渠道介绍<input
										type="text" name="qudao_qudao">公司 <input
										name="qudao[]" type="checkbox" value="巡展" style="width: 1rem">巡展
										<input name="qudao[]" type="checkbox" value="广播"
										style="width: 1rem;">广播 <input name="qudao[]"
										type="checkbox" value="电CALL" style="width: 1rem">电CALL
										<input name="qudao[]" type="checkbox" value="直投"
										style="width: 1rem">直投 <input name="qudao[]"
										type="checkbox" value="活动" style="width: 1rem">活动 <input
										name="qudao[]" type="checkbox" value="DM单"
										style="width: 1rem;">DM单 <input name="qudao[]"
										type="checkbox" value="外联" style="width: 1rem">外联<br>
										<input name="qudao[]" type="checkbox" value="老带新"
										style="width: 1rem; margin-left: 123px;">老带新 <input
										name="qudao[]" type="checkbox" value="其它" style="width: 1rem;">其它
										<input name="qudao[]" type="checkbox" value="无法了解"
										style="width: 1rem"><span style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">您对本案的关注点：<input name="guanzhu[]"
										type="checkbox" value="位置" style="width: 1rem">位置 <input
										name="guanzhu[]" type="checkbox" value="产品类型"
										style="width: 1rem">产品类型 <input name="guanzhu[]"
										type="checkbox" value="交通" style="width: 1rem">交通 <input
										name="guanzhu[]" type="checkbox" value="区域环境"
										style="width: 1rem">区域环境 <input name="guanzhu[]"
										type="checkbox" value="价格" style="width: 1rem">价格 <input
										name="guanzhu[]" type="checkbox" value="户型"
										style="width: 1rem">户型 <input name="guanzhu[]"
										type="checkbox" value="花园面积" style="width: 1rem">花园面积<br>
										<input name="guanzhu[]" type="checkbox" value="园林环境"
										style="width: 1rem; margin-left: 151px">园林环境 <input
										name="guanzhu[]" type="checkbox" value="科技设备"
										style="width: 1rem">科技设备 <input name="guanzhu[]"
										type="checkbox" value="交房时间" style="width: 1rem">交房时间
										<input name="guanzhu[]" type="checkbox" value="社区配套"
										style="width: 1rem">社区配套 <input name="guanzhu[]"
										type="checkbox" value="物业服务" style="width: 1rem">物业服务
										<input name="guanzhu[]" type="checkbox" value="教育环境"
										style="width: 1rem">教育环境 <input name="guanzhu[]"
										type="checkbox" value="开发品牌" style="width: 1rem">开发品牌<br>
										<input name="guanzhu[]" type="checkbox" value="增值潜力"
										style="width: 1rem; margin-left: 151px">增值潜力 <input
										name="guanzhu[]" type="checkbox" value="其它"
										style="width: 1rem;">其它 <input name="guanzhu[]"
										type="checkbox" value="无法了解" style="width: 1rem"><span
										style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">预估身价：<input name="shouru" type="radio"
										value="1千万以下" style="width: 1rem">1千万以下 <input
										name="shouru" type="radio" value="1千-5千万" style="width: 1rem">1千-5千万
										<input name="shouru" type="radio" value="5千万-1亿"
										style="width: 1rem">5千万-1亿 <input name="shouru"
										type="radio" value="1-3亿" style="width: 1rem">1-3亿 <input
										name="shouru" type="radio" value="3亿以上" style="width: 1rem">3亿以上
										<input name="shouru" type="radio" value="无法了解"
										style="width: 1rem;"><span style="color: red">无法了解</span>
									</td>
								</tr>




								<tr>
									<td colspan="3">重点投资：<input name="touzi[]" type="checkbox"
										value="股票" style="width: 1rem">股票 <input
										name="touzi[]" type="checkbox" value="基金 " style="width: 1rem">基金
										<input name="touzi[]" type="checkbox" value="黄金"
										style="width: 1rem">黄金 <input name="touzi[]"
										type="checkbox" value="股指期货" style="width: 1rem">股指期货
										<input name="touzi[]" type="checkbox" value="外汇"
										style="width: 1rem">外汇 <input name="touzi[]"
										type="checkbox" value="艺术品" style="width: 1rem">艺术品 <input
										name="touzi[]" type="checkbox" value="保险" style="width: 1rem">保险
										<input name="touzi[]" type="checkbox" value="民间投资"
										style="width: 1rem">民间投资 <input name="touzi[]"
										type="checkbox" value="其它" style="width: 1rem">其它 <input
										name="touzi[]" type="checkbox" value="无法了解"
										style="width: 1rem"><span style="color: red">无法了解</span>
									</td>
								</tr>

								<tr>
									<td colspan="3">资金筹备期：<input name="choubei" type="radio"
										value="1个月内" style="width: 1rem">1个月内 <input
										name="choubei" type="radio" value="1-3月" style="width: 1rem">1-3月
										<input name="choubei" type="radio" value="3-6月"
										style="width: 1rem">3-6月 <input name="choubei"
										type="radio" value="6-12月" style="width: 1rem">6-12月 <input
										name="choubei" type="radio" value="12月以上" style="width: 1rem">12月以上
										<input name="choubei" type="radio" value="无法了解"
										style="width: 1rem;"><span style="color: red">无法了解</span>
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
									<td colspan="3">您本次置业比选项目： <input type="text"
										name="bijiao1" style="width: 160px"> <input
										type="text" name="bijiao2" style="width: 160px"> <input
										type="text" name="bijiao3" style="width: 160px"> <input
										type="text" name="bijiao4" style="width: 160px">
									</td>
								</tr>

								<tr>
									<td colspan="3">本次参观接待时间：<input name="canguan"
										type="radio" value="15分钟以内" style="width: 1rem">15分钟以内
										<input name="canguan" type="radio" value="15-30分钟"
										style="width: 1rem">15-30分钟 <input name="canguan"
										type="radio" value="30-60分钟" style="width: 1rem">30-60分钟
										<input name="canguan" type="radio" value="60-120分钟"
										style="width: 1rem">60-120分钟 <input name="canguan"
										type="radio" value="120分钟以上" style="width: 1rem">120分钟以上
									</td>
								</tr>

								<tr>
									<td colspan="3">首访客户描述：<br>
									<textarea
											style="width: 800px; height: 100px; margin-left: 0.1rem"
											name="miaoshu"></textarea>
									</td>
								</tr>

								<tr>
									<td colspan="3">客户评级：<input name="pingji" type="radio"
										value="A" style="width: 1rem">A <input name="pingji"
										type="radio" value="B" style="width: 1rem">B <input
										name="pingji" type="radio" value="C" style="width: 1rem">C
										<input name="pingji" type="radio" value="D"
										style="width: 1rem">D
									</td>
								</tr>
							</tbody>
						</table>




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

		//查看工作时间
		function seeWorkTime(openid) {
			//alert(openid);
			window.location.href = "${appRoot}/expert/timeManage?openid="
					+ openid;
		}

		//删除图片
		function doDelete() {
			var flag = checkbox();
			if (flag) {
				var $modal = $('#myModal2');
				$modal.modal();
			}
		}

		function checkbox() {
			var str = document.getElementsByName("box");
			var objarray = str.length;
			var chestr = "";
			var jy = false;
			for (i = 0; i < objarray; i++) {
				if (str[i].checked == true) {
					jy = true;
					chestr += str[i].value + ",";
				}
			}
			if (!jy) {
				windowShow("请选择要删除的数据！")
				$('#quxiao').click();
				return false;
			} else {
				$("#boxeditId").val(chestr);
				return true;
			}
		}

		//删除
		function Delete() {
			deleForm.submit();
		}

		//其他图片
		function uploadImg(object) {

			$("#videotypeForm").submit();

			/* var index = $(object).attr("imgIndex");
			var newindex = parseInt(index)+1;
			$(object).attr("imgIndex",newindex);
			var image = '<li><img src="${appRoot}/static/img/zanwu1.png" style="width: 120px; height: 70px;" id="imgURL'+ index+ '" /><input type="checkbox" style="position: absolute;right:0px;top:0px;"></li>';
			
			if (object.files && object.files[0]) {
				var reader = new FileReader();
				reader.onload = function(evt) {
					if(evt.target.result!=""&&evt.target.result!=null){
						$("#imgURL1").remove();
						$("#hiddenImgUrl li").eq(-1).before(image);
						$("#imgURL"+index).attr("src",evt.target.result);
					}else{
						alert("图片上传出错!");
					}
				}
				reader.readAsDataURL(object.files[0]);
			} else {
				//alert(file.value);
			} */
		}

		//封面图片
		function coverImg(object) {
			$("#videotypeForm").submit();

			/* if (object.files && object.files[0]) {
				var reader = new FileReader();
				reader.onload = function(evt) {
					if(evt.target.result!=""&&evt.target.result!=null){
						$("#imgURL").attr("src",evt.target.result);
					}else{
						alert("图片上传出错!");
					}
				}
				reader.readAsDataURL(object.files[0]);
			} else {
				//alert(file.value);
			} */
		}
	</script>
	<input type="hidden" value="" id="adminId" />
</body>
</html>

