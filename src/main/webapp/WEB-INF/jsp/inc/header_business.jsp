<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%> 
<script src="${appRoot}/static/js/jquery.js"></script>
<header class="header purple-bg">
	<div class="sidebar-toggle-box">
		<div class="icon-reorder tooltips"></div>
	</div>
	<!--logo start-->
	<a href="${appRoot}/home/index" class="logo"> 
		<img alt="" src="${appRoot}/static/img/logo.png" style="height: 24px; width: 24px;"> 
		<span>随食生活<label class="hidden-phone" style="font-weight: normal;">商户后台管理系统</label></span>
	</a>
	<!--logo end-->

	<div class="top-nav ">
		<ul class="nav pull-right top-menu">
      <!-- notification dropdown start-->
      <li id="header_notification_bar" class="dropdown">
          <a data-toggle="dropdown" class="dropdown-toggle mt5" href="#">
              <i class="icon-bell-alt"></i>
              <span class="badge bg-warning slsright" ><strong id="showSzie">0</strong></span>
          </a>
          <ul class="dropdown-menu extended notification" id="messagelist" style="overflow-y :auto;height:300px;><!-- style="overflow-y :auto;height: 200px;" -->
              <div class="notify-arrow notify-arrow-yellow" ></div>
          </ul>
      </li>
      <!-- notification dropdown end -->
<!-- 			<li> -->
<!-- 				<input type="text" class="form-control search" placeholder="Search"> -->
<!-- 			</li> -->
			<!-- user login dropdown start-->
			<li class="dropdown">
				<a data-toggle="dropdown" class="dropdown-toggle" href="#"> 
					<img alt=""src="" id="headImg" style="width: 30px;height: 30px"> <!-- ${appRoot}/static/img/avatar1_small.jpg -->
					<span class="username hidden-phone">${currentUser.userName}</span> <b class="caret"></b>
				</a>
				<ul class="dropdown-menu extended logout">
					<div class="log-arrow-up"></div>
					<li><a onclick="updatePassword()"><i class=" icon-suitcase"></i>修改密码</a></li>
          <!-- href="${appRoot}/user/wpPwd" -->
					<li><a href="#"><i class="icon-cog"></i> 设置</a></li>
					<li><a href="#" onclick="updateLogo();"><i class="icon-bell-alt"></i> 修改头像</a></li>
					<li><a href="${appRoot}/logout.ky"><i class="icon-key"></i>退出</a></li>
				</ul>
			</li>
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
  
    <!-- Modal -->
  <div class="modal fade" id="myModalcallWaiter" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">呼叫服务员信息</h4>
        </div>
        <div class="modal-body">
				<h2 id="callContent"></h2>
				<h5 id="callTime"></h5>
        </div>
        <div class="modal-footer">
          <button data-dismiss="modal" class="btn btn-default" type="button" id="quxiao" onclick="remindUpdate();">取消</button>
        </div>
      </div>
    </div>
  </div>
  
    <!-- Modal -->
  <div class="modal fade" id="myModalOrder" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">订单信息</h4>
        </div>
        <div class="modal-body">
				<h2 id="orderContent"></h2>
				<h5 id="orderTime"></h5>
        </div>
        <div class="modal-footer">
          <button data-dismiss="modal" class="btn btn-default" type="button" id="quxiao" onclick="remindUpdate();">取消</button>
        </div>
      </div>
    </div>
  </div>
  
  
    <!-- Modal -->
  <div class="modal fade" id="myModalUpdatelOGO" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">上传头像</h4>
        </div>
        <div class="modal-body">
        	<form action="<%=request.getContextPath()%>/businesslogo/update" id="uplogo"  enctype="multipart/form-data" method="POST">
				<input type="file" id="business_logo" name="business_logo">
			</form>	
        </div>
        <div class="modal-footer">
          <button data-dismiss="modal" class="btn btn-default" type="button" id="quxiao">取消</button>
       	  <button class="btn btn-warning" type="button" onclick="submitUpdateLogo();">确定</button>
        </div>
      </div>
    </div>
  </div>
<script type="text/javascript">
			$(document).ready(function(){
				getImg();
				myMessage();
			});

			function getImg(){
				$.ajax({
					type:'POST',
					url:'<%=request.getContextPath()%>/businesslogo/getimg',
					dataType:'json',
					success:function(data){
						$('#headImg').attr("src",data.headimgurl);
					}
				});
			}
			

		
  			function updateLogo(){
  				var $modal = $('#myModalUpdatelOGO');
				$('#modal-title').text("修改头像");
				$modal.modal();
  			}
  			
  			function submitUpdateLogo(){
  				$('#uplogo').submit();
  			}

			function remindUpdate(){
				myMessage();
			}
	
 			setInterval("myMessage()",5000);//1000为1秒钟	
			function myMessage(){
			$.ajax({
				type:'POST',
				url:'<%=request.getContextPath()%>/remind/list',
				dataType:'json',
				success:function(data){
					var strs = "<li><p class='yellow'>你有<strong id='showSzie01'></strong>条新消息</p></li>";
					if(data.length == 0){
						$('#messagelist').empty();
						$('#messagelist').append(strs);
						$('#showSzie').html(data.length);
						$('#showSzie01').html(data.length);
					}else{
					for(var i = 0 ; i < data.length ; i ++){
						if(data[i].remindType == '01'){
							strs+="<li><a href='#' onclick='callWaiter(\""+data[i].id+"\",\""+data[i].remindContent+"\",\""+data[i].remindTimeString+"\");'><span class='label label-warning'>"
							+"<i class='icon-bell'></i></span>&nbsp;"+data[i].remindContent+"<span class='small italic'>"+data[i].remindTimeString+"</span>"
							+"</a></li>";
						}else if(data[i].remindType == '02'){
							strs+="<li><a href='#' onclick='callOrder(\""+data[i].id+"\",\""+data[i].remindContent+"\",\""+data[i].diningtableCode+"\");'><span class='label label-success'>"
							+"<i class='icon-plus'></i></span>&nbsp;"+data[i].remindContent+"<span class='small italic'>"+data[i].remindTimeString+"</span>"
							+"</a></li>";
						}else{
							strs+="<li><a href='#' onclick='callOrderWM(\""+data[i].id+"\",\""+data[i].orderSn+"\");'><span class='label label-success'>"
							+"<i class='icon-plus'></i></span>&nbsp;"+data[i].remindContent+"<span class='small italic'>"+data[i].remindTimeString+"</span>"
							+"</a></li>";
						}
					$('#messagelist').empty();
					$('#messagelist').append(strs);
					$('#showSzie').html(data.length);
					$('#showSzie01').html(data.length);
					}
				  }
				}
			});
		}

		function callOrderWM(id,orderSn){
			$.ajax({
				type:'POST',
				data:{'id':id},
				url:'<%=request.getContextPath()%>/remind/updateRemindType',
				dataType:'json',
				success:function(data){
					if(data.code == 'ok'){
						window.location.href = wxmp.appRoot + '/order/order_detail?adminValue=0&orderSn='+orderSn;
					}
				}
			});
		}	
			
		/* 呼叫服务员 */
		function callWaiter(id,content,value){
			$("#callContent").html(content);
			$("#callTime").html(value);
			$.ajax({
				type:'POST',
				data:{'id':id},
				url:'<%=request.getContextPath()%>/remind/updateRemindType',
				dataType:'json',
				success:function(data){
					if(data.code == 'ok'){
						var $modal = $('#myModalcallWaiter');
						$('#modal-title').text("消息提示");
						$modal.modal();
					}
				}
			});
		}
		
		function callOrder(id,content,value){
			$("#orderContent").html(content);
			$("#orderTime").html("<a href='../home/index'>去桌面  GO!</a>");
			$.ajax({
				type:'POST',
				data:{'id':id},
				url:'<%=request.getContextPath()%>/remind/updateRemindType',
				dataType:'json',
				success:function(data){
					if(data.code == 'ok'){
						var $modal = $('#myModalOrder');
						$('#modal-title').text("消息提示");
						$modal.modal();
					}
				}
			});
		}

		function updatePassword(){
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
				alert("两次输入的新密码不相同!");
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