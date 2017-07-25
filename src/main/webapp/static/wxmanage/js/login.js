// JavaScript Document
$(function(){
	//光标定位
	if($("#password").val()!="")
		$(".vcode").focus();
	else
		$("#username").focus();
	
	$("#vcodeimg").attr("alt","看不清楚，换一张").attr("title","看不清楚，换一张").click(function(){
		$(this).attr("src",'login/getcode?'+ Math.random());
	});
	
	
	$("#loginFrm").submit(function(){
		username=$("#username").val();
		password=$("#password").val();
		vcode=$(".vcode").val();
		if(username=="")
		{
			$(".errmsg").html("请填写登录帐号！");
			$("#username").focus();
			return false;
		}
		if(password=="")
		{
			$(".errmsg").html("请填写登录密码！");
			$("#password").focus();
			return false;
		}
		if(vcode=="")
		{
			$(".errmsg").html("请填写验证码！");
			$(".vcode").focus();
			return false;
		}
	});
});