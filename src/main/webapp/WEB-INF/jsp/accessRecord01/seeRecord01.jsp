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
	width: 520px;
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
					style="border: 4px solid #f0f0f0; background-color: #fff; padding-top: 0px; border-top: 0px; position: relative; padding-top: 0px">
					<form action="http://kylindpc.cn/zz_user.php" method="post"
						name="theForm" id="theform" onsubmit="return checkinput()">


						<table id="orderinfo" cellpadding="0" cellspacing="0"
							bgcolor="#dddddd" border="1" style="margin-top: 0px;">
							<tbody>
								<tr style="">
									<td style="width: 330px;">姓名：<input type="text"
										name="xingming" value="${accessRecord01.custname }" style="width: 10rem"></td>
									<td style="width: 320px;">性别：<input name="sex"
										type="text" value="${accessRecord01.custsex }" style="width: 10rem" >
									</td>
									<td style="width: 270px">置业顾问：贺渝测试</td>
								</tr>

								<tr>
									<td>联系方式：<input type="text" name="lianxi"  value="${accessRecord01.custphonenum }"
										onblur="check_lianxi_ajax()" style="width: 100px">&nbsp;<span
										id="txt1" style="color: red"></span></td>
									<td>第一次获知本案时间： 
										<fmt:formatDate value="${accessRecord01.firstknowtime}" pattern="yyyy-MM-dd"/>
									</td>
									<td>本次到访时间：
										<fmt:formatDate value="${accessRecord01.receptime}" pattern="yyyy-MM-dd"/>
									</td>
								</tr>

								<tr>
									<td colspan="3">年龄段：
									<input type="text" value="${accessRecord01.agegroup }" style="width: 10rem" >
									</td>
								</tr>

								<tr>
									<td>本地居住： 
										<input type="text" value="${accessRecord01.localresidence }" style="width: 10rem">
									</td>

									<td>本地工作：
										<input type="text" value="${accessRecord01.localworkarea }" style="width: 10rem">
									</td>

									<td>有无购房资格：
										<input type="text" value="${accessRecord01.buyqualify }" style="width: 10rem">
									</td>
								</tr>

								<tr>
									<td>外埠居住：
										<input type="text" value="${accessRecord01.outresidence }" style="width: 10rem">
									</td>

									<td>外埠工作： 
										<input type="text" value="${accessRecord01.outworkarea }" style="width: 10rem">
									</td>

									<td></td>
								</tr>

								<tr>
									<td colspan="3">家庭状况：
										<input type="text" value="${accessRecord01.familystatus }" style="width: 30rem">
									</td>
								</tr>

								<tr>
									<td colspan="3">您的出行方式：
										<input type="text" value="${accessRecord01.traffictypedesc }" style="width: 70rem">
									</td>
								</tr>

								<tr>
									<td colspan="3">您的从事行业：
										<input type="text" value="${accessRecord01.workindustry }" style="width: 70rem">
									</td>
								</tr>

								<tr>
									<td colspan="3">您的企业性质：
										<input type="text" value="${accessRecord01.enterprisetype }" style="width: 70rem">
									</td>
								</tr>

								<tr>
									<td colspan="3">您关注的产品类型：
										<input type="text" value="${accessRecord01.realtyproducttype }" style="width: 70rem">
									</td>
								</tr>

								<tr>
									<td colspan="3">您关注区域面积：
										<input type="text" value="${accessRecord01.attentacreage }" style="width: 70rem">
									</td>
								</tr>

								<tr>
									<td colspan="3">您接受的总房款：
										<input type="text" value="${accessRecord01.pricesection }" style="width: 70rem">
									</td>
								</tr>

								<tr>
									<td colspan="3">您的购房目的：
										<input type="text" value="${accessRecord01.buypurpose }" style="width: 70rem">
									</td>
								</tr>

								<tr>
									<td colspan="3">认知本案渠道：
										<input type="text" value="${accessRecord01.knowway }" style="width: 70rem">
									</td>
								</tr>

								<tr>
									<td colspan="3">您对本案的关注点：
										<input type="text" value="${accessRecord01.attentionpoint }" style="width: 70rem">
									</td>
								</tr>

								<tr>
									<td colspan="3">预估身价：
										<input type="text" value="${accessRecord01.estcustworth }" style="width: 70rem">
									</td>
								</tr>




								<tr>
									<td colspan="3">重点投资：
										<input type="text" value="${accessRecord01.investtype }" style="width: 70rem">
									</td>
								</tr>

								<tr>
									<td colspan="3">资金筹备期：
										<input type="text" value="${accessRecord01.capitalprepsection }" style="width: 70rem">
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
										<input type="text" value="${accessRecord01.compareprojs }" style="width: 70rem">
									</td>
								</tr>

								<tr>
									<td colspan="3">本次参观接待时间：
										<input type="text" value="${accessRecord01.receptimesection }" style="width: 70rem">
									</td>
								</tr>

								<tr>
									<td colspan="3">首访客户描述：<br>
									<textarea
											style="width: 800px; height: 100px; margin-left: 0.1rem"
											name="miaoshu">${accessRecord01.custdescn}</textarea>
									</td>
								</tr>

								<tr>
									<td colspan="3">客户评级：
										<input type="text" value="${accessRecord01.custscore }" style="width: 2rem">
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

