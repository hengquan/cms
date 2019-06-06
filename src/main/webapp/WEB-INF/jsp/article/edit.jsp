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
<link rel="stylesheet" type="text/css"
	href="${appRoot}/static/js/css/layui.css">

<script type="text/javascript" charset="utf-8"
	src="${appRoot}/static/assets/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${appRoot}/static/assets/ueditor/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${appRoot}/static/js/layui.all.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${appRoot}/static/js/layui.js"></script>
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
						<header class="panel-heading" id="articleTitle">编辑文章</header>
						<div id="allLanguages">
							<ul class="nav nav-tabs">
								<c:forEach items="${languageList}" var="u" varStatus="s">
									<li
										<c:if test="${u.tab eq languageTab }">class="active"</c:if>>
										<input type="hidden" class="parentIdAndlanguageTab" languageTab = "${u.tab }" parentId="${articleId }">
										<a href="#" onclick="selLanguage('${articleId}','${u.tab }','${roleId }')">${u.name }</a></li>
								</c:forEach>
							</ul>
						</div>
						<form action="${appRoot}/article/save" method="post"
							id="articleForm">
							<input type="hidden" name="language" id="language" value="${article.language }">
							<input type="hidden" name="relevancyId" id="relevancyId" value="${article.relevancyId }"> 
							<input type="hidden" name="id" id="articleId"
								value="${article.id }"> <input type="hidden"
								name="channelType" id="channelType" value="${channelType}">
							<input type="hidden" name="isValidate" id="isValidate"
								value="${article.isValidate }"> <input type="hidden"
								name="itemId" value="${itemId }"> <input type="hidden"
								name="positionId" value="${positionId }">
							<div style="margin-top: 16px; margin-left: 20px;" class="row">
								<label class="btn col-lg-1">选择站点：</label> <select
									class="btn col-lg-10" style="border: 1px solid #ddd;"
									name="roleId" id="roleId" onchange="selRole(this)"></select>
							</div>
							<input type="hidden" name="articleType" id="articleType" value="${articleType }">
							<div id="manyTypeAndChannel"></div>
							<div style="margin-top: 16px; margin-left: 20px;" class="row">
								<label class="btn col-lg-1">文章标题：</label> <input type="text"
									class="btn col-lg-10"
									style="border: 1px solid #ddd; text-align: left;"
									placeholder="请输入文章标题" name="articleName" id="articleName"
									value="${article.articleName }">
							</div>
							<div style="margin-top: 16px; margin-left: 20px;" class="row">
								<label class="btn col-lg-1">文章摘要：</label>
								<textarea rows="5" class="btn col-lg-10" name="detail"
									id="detail" style="border: 1px solid #ddd; text-align: left;">${article.detail }</textarea>
							</div>
							<div style="margin-top: 16px; margin-left: 20px;" class="row">
								<label class="btn col-lg-1">视频地址：</label> <input type="text"
									class="btn col-lg-10"
									style="border: 1px solid #ddd; text-align: left;"
									placeholder="请输入视频" name="videoUrl" id="videoUrl"
									value="${article.videoUrl }">
							</div>
							<div style="margin-top: 16px; margin-left: 20px;" class="row">
								<label class="btn col-lg-1">关键词语：</label> <input type="text"
									class="btn col-lg-10"
									style="border: 1px solid #ddd; text-align: left;"
									placeholder="请输入关键词语已逗号隔开" name="keyword" id="keyword"
									value="${article.keyword }">
							</div>
							<div style="margin-top: 16px; margin-left: 20px;" class="row">
								<label class="btn col-lg-1">封面图片：</label>
								<div class="controls">
									<c:choose>
										<c:when test="${empty article.picUrl }">
											<img class="my-img" id="imgDJZS"
												style="width: 200px; height: 150px;" />
										</c:when>
										<c:otherwise>
											<img class="my-img btn" id="imgDJZS" src="${article.picUrl }"
												style="width: 200px; height: 150px;" />
										</c:otherwise>
									</c:choose>
									<span id="titleDJZS"
										style="margin-top: -30px; margin-left: -190px; color: #8e8e8e;">上传100*200缩略图,1M以内</span>
								</div>
								<input type="hidden" id="picUrl" name="picUrl" />
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
								<button class="btn btn-send"
									onclick="doArticleList('${articleType}','${channelType }')">返回文章列表</button>
								<button class="btn btn-send"
									onclick="javascript :history.back(-1);">返回</button>
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
		var articleType = '${articleType}';
		//渠道名称
		var channelType = '${channelType}';
		//判断是添加还是修改
		if ('${editOperation}') {
			//处理图片中的提示语
			$("#titleDJZS").html("");
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
			//获取所有一级站点
			addUserRole();
			if('${roleId}'){
				selChannel('${roleId}');
			}
		});

    function addUserRole(){
       $.ajax({
         type : 'post',
         data : "",
         url : '${appRoot}/role/getAllList',
         dataType : 'json',
         success : function(data) {
           if (data.msg == 0) {
             var html = '<option value="">--请选择站点--</option>';
             var roleList = data.roleList;
             for(var i=0;i<roleList.length;i++){
               if(roleList[i].id == '${roleId}'){
                 html += '<option value="'+ roleList[i].id +'"  selected>'+ roleList[i].roleName +'</option>'
               }else{
                 html += '<option value="'+ roleList[i].id +'">'+ roleList[i].roleName +'</option>'
               }
             }
           } 
           $("#roleId").html(html);
         }
       });
     }
		
		function doRefresh() {
			location.reload();
		}

		function toSubmit(isValidate) {
			//取语言和相关文章ID
			$("#allLanguages .active .parentIdAndlanguageTab").each(function(index,obj){
				var relevancyId = $(obj).attr("parentId");
				if(relevancyId == ''){
					relevancyId = '0';
					$("#relevancyId").val(relevancyId);
				}else if(relevancyId != '${article.id}'){
					$("#relevancyId").val(relevancyId);
				}
				var languageTab = $(obj).attr("languageTab");
				$("#language").val(languageTab);
			})
			$("#isValidate").val(isValidate);
			//获取多个不同类型下的站点
			var channelIds = "";
			$("#manyTypeAndChannel .channelIds").each(function(index,object){
				var obj = $(object);
				var channelId = obj.val();
				if(channelId != "" && channelId != null){
					channelIds += "," + channelId;
				}
			})
			if(channelIds != "")
         channelIds = channelIds.substr(1);
			$("#articleType").val(channelIds);
			//提交
			$("#articleForm").submit();
		}

		//layui处理文件上传
		layui.use([ 'form', 'upload' ], function() {
			form = layui.form;
			upload = layui.upload;
			initImgForGroup();
			form.render();
		});

		function initImgForGroup() {
			upload.render({
				elem : '#imgDJZS,#titleDJZS', //绑定元素
				url : '${appRoot}/apply/uploadFile',//上传接口
				size : '1024',
				before : function(obj) {
					console.log(obj);
					//预读本地文件示例，不支持ie8
					obj.preview(function(index, file, result) {
						$('#imgDJZS').attr('src', result); //图片链接（base64）
					});
					$("#titleDJZS").html("");
					$("#imgDJZS").attr("title", "点击更换缩略图");
				},
				data : {
					"tableName" : "hl_kj",
					"fileType" : "主图片"
				},
				done : function(res) {
					console.log(res);
					var fileName = res.fileName;
					$("#picUrl").val(fileName);
				}
			});
		}

		//选择不同语言的文章
		function selLanguage(id, languageTab,roleId) {
			window.location.href = "${appRoot}/article/editPage?languageTab=" + languageTab + "&articleType=" + articleType
					+ "&channelType=" + channelType + "&itemId=" + '${itemId}'
					+ "&positionId=" + '${positionId}' + "&roleId=" + roleId + "&id=" + id;
		}
		
		function selRole(obj){
			$("#articleType").html();
			var obj = $(obj);
			var roleId = obj.val();
			//获取该频道下所有顶级文章列表
      selChannel(roleId);
		}
		
		function selChannel(roleId){
			var channelIds = $("#articleType").val();
			$.ajax({
        type : 'post',
        data : {
          "channelType" : channelType,
          "roleId" : roleId
        },
        url : '${appRoot}/channel/getDataByUrserRole',
        dataType : 'json',
        success : function(data) {
          if (data.msg == 0) {
            var html = "";
            var app = data.app;
            var appVideo = data.appVideo;
            var chumo = data.chumo;
            var h5 = data.h5;
            if(h5 != null && h5 != undefined && h5.length>0){
            	 html += '<div style="margin-top: 16px; margin-left: 20px;" class="row">'
                    +'<label class="btn col-lg-1">H5：</label>'
                    +'<select  class="btn col-lg-10 channelIds" style="border: 1px solid #ddd;">'
                    +'<option value="">--请选择频道--</option>';
               for(var i=0;i<h5.length;i++){
            	   var channelId = h5[i].id;
            	   if(channelIds.indexOf(channelId) != -1){
	                 html += '<option value="'+h5[i].id+'" selected>'+h5[i].channelname+'</option>';
            	   }else{
	                 html += '<option value="'+h5[i].id+'">'+h5[i].channelname+'</option>';
            	   }
               }
               html += '</select></div>';
             }
            if(app != null && app != undefined && app.length>0){
            	  html += '<div style="margin-top: 16px; margin-left: 20px;" class="row">'
                    +'<label class="btn col-lg-1">APP：</label>'
                    +'<select  class="btn col-lg-10 channelIds" style="border: 1px solid #ddd;">'
                    +'<option value="">--请选择频道--</option>';
                for(var i=0;i<app.length;i++){
                	var channelId = app[i].id;
                  if(channelIds.indexOf(channelId) != -1){
                	  html += '<option value="'+app[i].id+'" selected>'+app[i].channelname+'</option>';
                  }else{
                	  html += '<option value="'+app[i].id+'">'+app[i].channelname+'</option>';
                  }
                }
                html += '</select></div>';
              }
            if(chumo != null && chumo != undefined && chumo.length>0){
            	  html += '<div style="margin-top: 16px; margin-left: 20px;" class="row">'
                    +'<label class="btn col-lg-1">触摸板：</label>'
                    +'<select  class="btn col-lg-10 channelIds" style="border: 1px solid #ddd;">'
                    +'<option value="">--请选择频道--</option>';
                for(var i=0;i<chumo.length;i++){
                	var channelId = chumo[i].id;
                  if(channelIds.indexOf(channelId) != -1){
	                	html += '<option value="'+chumo[i].id+'" selected>'+chumo[i].channelname+'</option>';
                  }else{
	                	html += '<option value="'+chumo[i].id+'">'+chumo[i].channelname+'</option>';
                  }
                }
                html += '</select></div>';
              }
            if(appVideo != null && appVideo != undefined && appVideo.length>0){
                html += '<div style="margin-top: 16px; margin-left: 20px;" class="row">'
                  +'<label class="btn col-lg-1">APP视频：</label>'
                  +'<select  class="btn col-lg-10 channelIds" style="border: 1px solid #ddd;">'
                  +'<option value="">--请选择频道--</option>';
                for(var i=0;i<appVideo.length;i++){
                	var channelId = appVideo[i].id;
                  if(channelIds.indexOf(channelId) != -1){
	                	html += '<option value="'+appVideo[i].id+'" selected>'+appVideo[i].channelname+'</option>';
                  }else{
	                	html += '<option value="'+appVideo[i].id+'">'+appVideo[i].channelname+'</option>';
                  }
                }
                html += '</select></div>';
              }
            $("#manyTypeAndChannel").html(html);
          } else {
            windowShow("获取频道列表失败", "");
          }
        }
      });
		}
		
    function doArticleList(articleType, channelType) {
       window.location.href = "${appRoot }/article/getDataList?articleType="
           + articleType
           + "&channelType="
           + channelType
           + "&itemId="
           + '${itemId}' + "&positionId=" + '${positionId}';
     }
	</script>
</body>
</html>

