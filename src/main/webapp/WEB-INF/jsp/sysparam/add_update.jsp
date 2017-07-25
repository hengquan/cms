<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/jsp/inc/head_bootstrap.jsp"%>

<link href="${appRoot}/static/assets/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css" >

<link rel="stylesheet" type="text/css" href="${appRoot}/static/assets/bootstrap-datepicker/css/datepicker.css" />
<link rel="stylesheet" type="text/css" href="${appRoot}/static/assets/bootstrap-colorpicker/css/colorpicker.css" />
<link rel="stylesheet" type="text/css" href="${appRoot}/static/assets/bootstrap-daterangepicker/daterangepicker.css" />
<link rel="stylesheet" type="text/css" href="${appRoot}/static/css/stream-v1.css" />
    
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
              <div class="row state-overview">

              <div class="col-sm-12">
                <div class="vga-wrapper">
                  <section class="panel vga-tit">
                      <div class="symbol text-left">
                        <h4>系统参数编辑</h4>
                      </div>
                      <div class="value">
                          <button onclick="submitForm()" type="button" class="btn btn-success" style="padding-left:30px;padding-right:30px;">保 存</button>
                          <button id="returnBtn" class="btn btn-default" type="button">返回列表</button>
                      </div>
                  </section>
                </div>
              </div>

                  <div class="col-lg-6" style="overflow-y:auto;">
                      <section class="panel">
                          <header class="panel-heading">
                              	参数信息
                          </header>
                          <div class="panel-body">
                              <form class="form-horizontal" role="form" id="sys_param" action="${appRoot}/sys_param/add_update" method="post">
                                  <div class="form-group">
                                      <label class="col-lg-2 control-label pd-r5">参数名称<font style="color:red;">*</font></label>
                                      <div class="col-lg-10">
                                          <input type="text" class="form-control" id="paramName" name="paramName" placeholder="" value="${sysParam.paramName}">
                                      </div>
                                  </div>
                                  <div class="form-group">
                                      <label class="col-lg-2 control-label pd-r5">参数编码<font style="color:red;">*</font></label>
                                      <div class="col-lg-10">
                                          <input type="text" class="form-control" id="paramCode" name="paramCode" placeholder="" value="${sysParam.paramCode}">
                                      </div>
                                  </div>
                                  <div class="form-group">
                                      <label class="col-lg-2 control-label pd-r5">参数值<font style="color:red;">*</font></label>
                                      <div class="col-lg-10">
                                          <input type="text" class="form-control" id="paramVal" name="paramVal" placeholder=""  value="${sysParam.paramVal}">
                                      </div>
                                  </div>
                                  <div class="form-group">
                                      <label class="col-lg-2 control-label pd-r5">备注<font style="color:red;">*</font></label>
                                      <div class="col-lg-10">
                                          <input type="text" class="form-control" id="remark" name="remark" placeholder=""  value="${sysParam.remark}">
                                      </div>
                                  </div>
          
                                  <input type="hidden" id="id" name="id" value="${sysParam.id}"/>
                              </form>
                          </div>
                      </section>
                  </div>
                  </div>
              </div>
              <!-- page end-->
          </section>
      </section>
      <!--main content end-->
 	 </section> 
 	 
	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>
    
    <!--script for this page only-->
    <script type="text/javascript" src="${appRoot}/static/js/jquery.validate.min.js"></script>
    <!--custom switch-->
    <script src="${appRoot}/static/js/bootstrap-switch.js"></script>
    <!--custom tagsinput-->
    <script src="${appRoot}/static/js/jquery.tagsinput.js"></script>

    <script type="text/javascript" src="${appRoot}/static/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
    <script type="text/javascript" src="${appRoot}/static/assets/bootstrap-daterangepicker/date.js"></script>
    <script type="text/javascript" src="${appRoot}/static/assets/bootstrap-daterangepicker/daterangepicker.js"></script>
    <script type="text/javascript" src="${appRoot}/static/assets/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
    <script type="text/javascript" src="${appRoot}/static/js/stream-v1.min.js"></script>
    
    <script src="${appRoot}/static/js/form-component.js"></script>

    <script>
      // 用于该页顶部标题栏自适应。
      function resizeTit(){
        var ww = $('.wrapper').width();
        $('.vga-tit').css('width',ww+'px');
      }
      $(window).resize(resizeTit);
      window.onload = resizeTit();

      // 当页面在移动端 标题会自动移动到顶部。
      var initTop = 0;
      function scroll(){
        if($(window).innerWidth() <= 768){
          var scrollTop = $(document).scrollTop();
          if(scrollTop > initTop){
            $('.vga-tit').css('top','15px');
          }else{
            $('.vga-tit').css('top','80px');
          }
          initTop = scrollTop;       
        }
      }
      $(window).on('scroll',scroll);
      
      function submitForm(){
    	 $('#sys_param').submit();
      }
 
    </script>
    
</body>
</html>