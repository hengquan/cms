<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%> 
<script src="${appRoot}/static/js/jquery.js"></script>
<header class="header purple-bg">
	<div class="sidebar-toggle-box">
		<div class="icon-reorder tooltips"></div>
	</div>
	<!--logo start-->
	<a href="${appRoot}/home/index" class="logo"> 
		<%-- <img alt="" src="${appRoot}/static/img/fit_logo.png" style="height: 24px; width: 39px;">  --%>
		<span>${appTitle}<label class="hidden-phone" style="font-weight: normal;"></label></span>
	</a>
	<!--logo end-->

	<div class="top-nav ">
		<ul class="nav pull-right top-menu">
      <!-- notification dropdown start-->
      <!-- <li id="header_notification_bar" class="dropdown">
          <a data-toggle="dropdown" class="dropdown-toggle mt5" href="#">
              <i class="icon-bell-alt"></i>
              <span class="badge bg-warning slsright">7</span>
          </a>
          <ul class="dropdown-menu extended notification">
              <div class="notify-arrow notify-arrow-yellow"></div>
              <li>
                  <p class="yellow">你有7条新消息</p>
              </li>
              <li>
                  <a href="#">
                      <span class="label label-danger"><i class="icon-bolt"></i></span>
                      Server #3 overloaded.
                      <span class="small italic">34 mins</span>
                  </a>
              </li>
              <li>
                  <a href="#">
                      <span class="label label-warning"><i class="icon-bell"></i></span>
                      Server #10 not respoding.
                      <span class="small italic">1 Hours</span>
                  </a>
              </li>
              <li>
                  <a href="#">
                      <span class="label label-danger"><i class="icon-bolt"></i></span>
                      Database overloaded 24%.
                      <span class="small italic">4 hrs</span>
                  </a>
              </li>
              <li>
                  <a href="#">
                      <span class="label label-success"><i class="icon-plus"></i></span>
                      New user registered.
                      <span class="small italic">Just now</span>
                  </a>
              </li>
              <li>
                  <a href="#">
                      <span class="label label-info"><i class="icon-bullhorn"></i></span>
                      Application error.
                      <span class="small italic">10 mins</span>
                  </a>
              </li>
              <li>
                  <a href="#">See all notifications</a>
              </li>
          </ul>
      </li> -->
      
      <!-- notification dropdown end -->
<!-- 			<li> -->
<!-- 				<input type="text" class="form-control search" placeholder="Search"> -->
<!-- 			</li> -->
			<!-- user login dropdown start-->
			<li class="dropdown">
				<a data-toggle="dropdown" class="dropdown-toggle" href="#"> 
					<img alt=""src="${appRoot}/static/img/ql.png" id="headImg" style="width: 30px;height: 30px"> 
					<span class="username hidden-phone"></span> <b class="caret"></b>
				</a>
				<ul class="dropdown-menu extended">
					<li><a onclick="" href="#"><i class=" icon-suitcase"></i>&nbsp;&nbsp;版本更新</a></li>
					<li><a onclick="showPassword();" href="#"><i class=" icon-suitcase"></i>&nbsp;&nbsp;修改密码</a></li>
					<li><a href="${appRoot}/logout.ky"><i class="icon-key"></i>&nbsp;&nbsp;退出</a></li>
				</ul>
			</li>
			<!-- user login dropdown end -->
		</ul>
	</div>
</header>

 <!-- Modal -->
  <div class="modal fade" id="myModalupdatePassword" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">修改密码</h4>
        </div>
        <div class="modal-body">

        <form class="am-form" id="passwordForm" action="${appRoot}/user/updatePwd"">
          <div class="am-panel am-panel-default" style="margin-bottom: 6px;font-size:14px;">
            <!-- <hr data-am-widget="divider" style="margin:1px auto;" class="am-divider am-divider-default" /> -->
            <div class="am-panel-bd form-group" >
              <label>原密码：</label>
               <input type="password" class="form-control" id="oldpwd" name="oldpwd" placeholder="">
            </div>
            <!-- <hr data-am-widget="divider" style="margin:3px auto;" class="am-divider am-divider-default" /> -->
            <div class="am-panel-bd form-group">
              <label>新密码：</label>
              <input type="password" class="form-control" id="newpwd" name="newpwd" placeholder="">
            </div>
            <div class="am-panel-bd form-group">
              <label>确认密码：</label>
              <input type="password" class="form-control" id="newpwd1" name="newpwd1" placeholder="">
            </div>
          </div>

        </form>

        </div>
        <div class="modal-footer">
          <button data-dismiss="modal" class="btn btn-default" type="button" id="quxiao">取消</button>
          <button class="btn btn-warning" type="button" name="btn_ok" onclick="submitBtn();" data-am-loading="{loadingText: '修改密码中...'}">确定</button>
        </div>
      </div>
    </div>
  </div>
  <!-- modal -->
  
  

<script type="text/javascript">

		$(document).ready(function(){
			getImg();
		});

		//setInterval("getImg()",5000);//1000为1秒钟	
		function getImg(){
			$.ajax({
				type:'post',
				url:'${appRoot}/loginMsg',
				dataType:'json',
				success:function(data){
					if(data.msg == 100){
						var user = data.userInfo;
						$(".username").html(user.realname);
						$("#headImg").attr("src",user.headimgurl);
					}
				}
			});
		}





		function showPassword(){
			var $modal = $('#myModalupdatePassword');
			$('#modal-title').text("修改密码");
			$modal.modal();
		}


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
				url:$('#passwordForm').attr('action'),
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