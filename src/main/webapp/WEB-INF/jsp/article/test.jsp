<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
							<header class="panel-heading"> 文章管理测试 </header>
							 
							<form action="${appRoot}/">
								<textarea name="content" class="az-txtarea" id="content" style="width: 100%; height: 100px;font-size: 14px;"></textarea>
							</form> 
						</section>
					</div>
				</div>
				<!-- page end-->
			</section>
		</section>
		<!--main content end-->
	</section>


	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>
	 
	 <script type="text/javascript" charset="utf-8" src="${appRoot}/static/assets/ueditor/ueditor.config.js"></script>
	 <script type="text/javascript" charset="utf-8" src="${appRoot}/static/assets/ueditor/ueditor.all.min.js"> </script>
	
	 <script type="text/javascript">
		$(function(){
			var ue = UE.getEditor('content');
		});
		
	 </script>

</body>
</html>
