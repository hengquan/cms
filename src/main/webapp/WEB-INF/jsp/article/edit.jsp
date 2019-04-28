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
<link href="${appRoot}/static/css/style.css" rel="stylesheet">
<link href="${appRoot}/static/css/style-responsive.css" rel="stylesheet" />
<script type="text/javascript" charset="utf-8"
	src="${appRoot}/static/assets/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${appRoot}/static/assets/ueditor/ueditor.all.js">
	
</script>

<title>${appTitle}</title>
</head>
<body>

	<section id="container" class="">
		<%@ include file="/WEB-INF/jsp/inc/header.jsp"%>
		<%@ include file="/WEB-INF/jsp/inc/sidebar.jsp"%>


		<section id="main-content">
			<section class="wrapper">
				<!-- page start-->
				<div class="col-lg-12">
					<section class="panel">
						<header class="panel-heading" id="articleTitle">${sourceName }文章--添加</header>
						<form action="${appRoot}/article/save" method="post"
							id="articleForm">
							<input type="hidden" name="id" id="articleId" value="${article.id }">
							<input type="hidden" name="isValidate" id="isValidate" value="${article.isValidate }">
							<div style="margin-top: 16px; margin-left: 20px;" class="row">
								<label class="btn col-lg-1">选择频道：</label> 
								<select class="btn col-lg-10" style="border: 1px solid #ddd;"
									name="articleType" id="articleType"></select>
							</div>
							<div style="margin-top: 16px; margin-left: 20px;" class="row">
								<label class="btn col-lg-1">文章标题：</label> 
								<input type="text" class="btn col-lg-10"
									style="border: 1px solid #ddd; text-align: left;"
									placeholder="请输入文章标题" name="articleName" id="articleName" value="${article.articleName }">
							</div>
							<div style="margin-top: 16px; margin-left: 20px;" class="row">
								<label class="btn col-lg-1">文章摘要：</label>
								<textarea rows="5" class="btn col-lg-10" name="detail" id="detail"
									style="border: 1px solid #ddd; text-align: left;">${article.detail }</textarea>
							</div>
							<div style="margin-top: 16px; margin-left: 20px;" class="row">
								<label class="btn col-lg-1">区域语言：</label> 
								<input type="text" class="btn col-lg-10"
									style="border: 1px solid #ddd; text-align: left;"
									placeholder="请输入区域语言" name="language" id="language" value="${article.language }">
							</div>
							<div style="margin-top: 16px; margin-left: 20px;" class="row">
								<label class="btn col-lg-1">视频地址：</label> 
								<input type="text" class="btn col-lg-10"
									style="border: 1px solid #ddd; text-align: left;"
									placeholder="请输入视频" name="videoUrl" id="videoUrl" value="${article.videoUrl }">
							</div>
							<div style="margin-top: 16px; margin-left: 20px;" class="row">
								<label class="btn col-lg-1">关键词语：</label> 
								<input type="text" class="btn col-lg-10"
									style="border: 1px solid #ddd; text-align: left;"
									placeholder="请输入关键词语已逗号隔开" name="keyword" id="keyword" value="${article.keyword }">
							</div>
							<div style="margin-top: 16px; margin-left: 20px;" class="row">
								<label class="btn col-lg-1">封面图片：</label> 
								<input type="text" class="btn col-lg-10"
									style="border: 1px solid #ddd; text-align: left;"
									placeholder="请输入文章标题" name="picUrl" id="picUrl" value="${article.picUrl }">
							</div>
							<div style="margin-top: 16px; margin-left: 20px;" class="row">
								<label class="btn col-lg-1">文章内容：</label>
								<textarea class="col-lg-10" name="article" id="article"
									style="margin-left: -15px; border: 0px;">${article.article }</textarea>
							</div>
						</form>
						<div style="clear: both"></div>
						<hr>
						<nav class="clearfix">
							<div class="pull-right"
								style="margin-bottom: 20px; margin-right: 80px;">
								<button class="btn btn-send" onclick="toSubmit(0)">保存为草稿</button>
								<button class="btn btn-send" onclick="toSubmit(2)">保存并发布</button>
								<button class="btn btn-send" onclick="javascript :history.back(-1);">返回</button>
							</div>
						</nav>
					</section>
				</div>
				<!-- page end-->
			</section>
		</section>





	</section>









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
	  //频道名称
	  var channelName = '${channelName}';
	  //判断是添加还是修改
	  if('${editOperation}'){
		  $("#articleTitle").html('${sourceName}'+"文章--修改");
	  }else{
		  $("#articleTitle").html('${sourceName}'+"文章--添加");
	  }
	  
		//添加提交
		function submitData() {
			$("#addMessage").submit();
		}

		//更新数据
		function submitUpdateData() {
			$("#editMessage").submit();
		}

		$(function() {
			var ue = UE.getEditor('article');
			$.ajax({
        type : 'post',
        data : "",
        url : '${appRoot}/channel/getAllData',
        dataType : 'json',
        success : function(data) {
          if (data.msg == 0) {
            var html;
            var channelList = data.dataList;
            for(var i=0;i<channelList.length;i++){
              if(channelList[i].channelname == channelName){
                html += '<option value="'+ channelList[i].id +'" selected>'+ channelList[i].channelname +'</option>'
              }else{
                html += '<option value="'+ channelList[i].id +'">'+ channelList[i].channelname +'</option>'
              }
            }
            $("#articleType").html(html);
          } else {
            windowShow("获取权限列表失败", "");
          }
        }
      });
		});

		function doRefresh() {
			location.reload();
		}
		
		function toSubmit(isValidate){
			$("#isValidate").val(isValidate);
			$("#articleForm").submit();
		}
	</script>
</body>
</html>

