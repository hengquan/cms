<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ include file="/WEB-INF/jsp/inc/sessions.jsp"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/jsp/inc/head_bootstrap.jsp"%>

<link href="${appRoot}/static/assets/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css">
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
							<header class="panel-heading"> 文章管理 </header>
							<form action="${appRoot}/article/addOrUpdate" enctype="multipart/form-data" class="form-horizontal" role="form" method="post" onsubmit="return validate();">
							   <input type="hidden" name="editid" id="editId" value="${tblArticle.id}">
							   <input type="hidden" name="id" id="id" value="${id}">
		                       <input type="hidden" name="itemId" id="itemId" value="${itemId}">
								<div class="form-group">
									<label class="col-lg-2 control-label pd-r5">标题<font style="color:red;">*</font></label>
									<div class="col-lg-10">
										<input type="text" class="form-control" id=title
										name="title" placeholder="请输入标题" value="${tblArticle.title}">
									</div>
								</div>
								<div class="form-group">
									<label class="col-lg-2 control-label">文章类型<font style="color:red;">*</font></label>
									<div class="col-lg-10">
										<select name="articleType" id="articleType" class="form-control">
											<!-- <option value="01"  selected="selected">最新资讯</option>
											<option value="02">干货指导</option>
											<option value="03">大咖直播</option> -->
											<option value="关于我们" <c:if test="${tblArticle.articleType=='关于我们'}">selected="selected"</c:if> >关于我们</option>
											<option value="咨询协议" <c:if test="${tblArticle.articleType=='咨询协议'}">selected="selected"</c:if>>咨询协议</option>
										</select>
									</div>
								</div><!--baseUrl  -->
								 <div class="form-group">
                                      <label class="col-lg-2 control-label pd-r5">上传图片<font style="color:red;"></font></label>
                                      <div class="col-lg-10">
                                        <input type="file" class="form-control" id="imgUrl" name="imgUrl" placeholder="请选择文件" value="${tblArticle.imgUrl }">
                                        <span>建议图片大小:200 x 136px</span>
                                      </div>
                             	</div>
                             	 <%-- <div class="form-group">
                                      <label class="col-lg-2 control-label pd-r5">来源<font style="color:red;">*</font></label>
                                        	<div class="col-lg-10">
                                        		<input type="text" class="form-control" id="source" name="source" placeholder="请输入来源" value="${tblArticle.source}" />
                               				</div>
                                 </div> --%>
                            	 <div class="form-group">
                                      <label class="col-lg-2 control-label pd-r5">备注<font style="color:red;">*</font></label>
                                      <div class="col-lg-10">
                                      	<textarea rows="3" cols="60" class="form-control" id="remark" name="remark" maxlength="118">${tblArticle.remark}</textarea>
                                      </div>
                            	 </div>
                            	  <div class="form-group">
                                      <label class="col-lg-2 control-label pd-r5">实现方式<font style="color:red;">*</font></label>
                                      	<div class="col-lg-10">
                                        	<select class="form-control" onchange="changeMethod(this.value)" id="contextType" name="contextType">
                                        		<option value="1">内容</option>
                                        		<option value="2">链接</option>
                                        	</select>
                                        </div>	
                                 </div>
                            	 <div class="form-group" id="nr">
                                      <label class="col-lg-2 control-label pd-r5">文章内容<font style="color:red;">*</font></label>
                                      <div class="col-lg-10">
										<textarea name="content" class="az-txtarea" id="content" style="width: 100%; height: 100px;font-size: 14px;">${tblArticle.content}</textarea>
								 	  </div>
								 </div>
								 <div class="form-group" id="lj" style="display: none;">
                                      <label class="col-lg-2 control-label pd-r5">链接地址<font style="color:red;">*</font></label>
                                      <div class="col-lg-10">
                                        <input type="text" class="form-control" id="articleUrl" name="articleUrl" placeholder="请輸入链接地址" value="${tblArticle.articleUrl}">
                                 	  </div>
                                 </div>
                           	     <div class="form-group">
                           	 	 	<div class="col-lg-10 col-lg-offset-2">
								 	<button type="submit" class="btn btn-shadow btn-success" style="width: 3cm;">提交</button>
								 	</div>
							  	</div>
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
			var ue = UE.getEditor('content',{  
                //默认的编辑区域高度  
                initialFrameHeight:390  
                //更多其他参数，请参考ueditor.config.js中的配置项  
            });
			var atype = '${tblArticle.articleType}';
			if(atype == null || atype == ''){
				$('#articleType').val('01');
			}else{
				$('#articleType').val('${tblArticle.articleType}');
			}
			$('#content').text('${tblArticle.content}');
			$('#remark').text('${tblArticle.remark}');
			
			var contextType = '${tblArticle.articleUrl}';
			if(contextType == null || contextType == ''){
				$('#contextType').val(1);
			}else{
				if(contextType == 1){
					$('#lj').hide();
					$('#nr').show();
					$('#contextType').val(1);
				}else{
					$('#lj').show();
					$('#nr').hide();
					$('#contextType').val(2);
				}
			}
		});
		
		
		
		function validate(){
			if($('#title').val() == null || $('#title').val() == ''){
				alert("标题不能为空!");
				return false;
			}
			/* if($('#editId').val() == null || $('#editId').val() == ''){
				if($('#imgUrl').val() == null || $('#imgUrl').val() == ''){
					alert("图片不能为空!");
					return false;
				}
			} */
			if($('#remark').val() == null || $('#remark').val() == ''){
				alert("备注不能为空!");
				return false;
			}
			var contextType = $('#contextType').val();
			if(contextType == 1){
				if($('#content').text() == null || $('#content').text() == ''){
					alert("内容不能为空!");
					return false;
				}
			}else{
				if($('#articleUrl').val() == null || $('#articleUrl').val() == ''){
					alert("链接不能为空!");
					return false;
				}
			}
			
			
			
		}
		
		function changeMethod(value){
			if(value == 1){
				$('#lj').hide();
				$('#nr').show();
			}else{
				$('#lj').show();
				$('#nr').hide();
			}
		}
		
	 </script>

</body>
</html>
