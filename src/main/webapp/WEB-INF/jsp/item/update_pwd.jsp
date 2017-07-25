<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ include file="/WEB-INF/jsp/inc/sessions.jsp"%>
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
                        <h4>密码修改</h4>
                      </div>
                      <div class="value">
                          <button  onclick="submitBtn();" type="button" class="btn btn-success" style="padding-left:30px;padding-right:30px;">保 存</button>
                      </div>
                  </section>
                </div>
              </div>

                  <div class="col-lg-6" style="overflow-y:auto;">
                      <section class="panel">
                          <header class="panel-heading">
                              	基本信息
                          </header>
                          <div class="panel-body">
                              <form class="form-horizontal" role="form" id="baseForm" action="${appRoot}/user/updatePwd" method="post">
                                  <div class="form-group">
                                      <label class="col-lg-2 control-label pd-r5">原密码<font style="color:red;">*</font></label>
                                      <div class="col-lg-10">
                                          <input type="password" class="form-control" id="oldpwd" name="oldpwd" placeholder="">
                                      </div>
                                  </div>
                                  <div class="form-group">
                                      <label class="col-lg-2 control-label pd-r5">新密码<font style="color:red;">*</font></label>
                                      <div class="col-lg-10">
                                          <input type="password" class="form-control" id="newpwd" name="newpwd" placeholder="">
                                      </div>
                                  </div>
                                  <div class="form-group">
                                      <label class="col-lg-2 control-label pd-r5">新密码<font style="color:red;">*</font></label>
                                      <div class="col-lg-10">
                                          <input type="password" class="form-control" id="newpwd1" name="newpwd1" placeholder="">
                                      </div>
                                  </div>
                              </form>
                          </div>
                      </section>	
                  </div>
              </div>
              <!-- page end-->
          </section>
      </section>
      <!--main content end-->
 	 </section> 
 	 
	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>
    
    <script type="text/javascript">
    	var takeout = "${wxmBusiness.takeout}";
    	var integral = "${wxmBusiness.integral}";
    	var delivery = "${wxmBusiness.delivery}";
    </script>
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

	<script type="text/javascript">
		function submitBtn(){
			if($('#oldpwd').val() == null || $('#oldpwd').val() == ''){
				alert("原密码不能为空!");
				return false;
			}
			if($('#newpwd').val() == null || $('#newpwd').val() == ''){
				alert("新密码不能为空!");
				return false;
			}
			if($('#newpwd1').val() == null || $('#newpwd1').val() == ''){
				alert("重复新密码不能为空!");
				return false;
			}
			if($('#newpwd').val() != $('#newpwd1').val()){
				alert("两边输入的新密码不相同!");
				return false;
			}
			$.ajax({
				type:'POST',
				data:{'oldpwd':$('#oldpwd').val(),'newpwd':$('#newpwd').val()},
				url:$('#baseForm').attr('action'),
				dataType:'json',
				success:function(data){
					if(data.code == 'error'){
						alert("原密码输入不正确");
						return false;
					}else{
						alert("密码修改成功!");
						window.location.href="${appRoot}/logout.ky";
					}
				}
			});
		}
		
	</script>
    
</body>
</html>