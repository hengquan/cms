<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ include file="/WEB-INF/jsp/inc/sessions.jsp"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/jsp/inc/head.jsp"%>
<title>${appTitle}</title>
</head>

<body>
	<div class="container">

		<header class="inside-head">
			<span></span> <em><p>微信支付</p></em> <i></i>
		</header>

		<br><br><br><br><br><br><br>
  		<div style="text-align:center;size:30px;"><input type="button" style="width:200px;height:80px;" value="微信支付" onclick="callpay()"></div>
		
	</div>
	
	<script type="text/javascript">

		//调用微信JS api 支付
		function jsApiCall()
		{
			WeixinJSBridge.invoke(
				'getBrandWCPayRequest',
				{
					"appId" : "${appId}",
					"timeStamp":"${timeStamp}",
					"nonceStr" : "${nonceStr}",
					"package" : "${packageStr}",
					"signType" : "${signType}", 
					"paySign" : "${paySign}"
				},
				function(res){
					WeixinJSBridge.log(res.err_msg);
					alert(res.err_code+res.err_desc+res.err_msg);
				}
			);
		}

		function callpay()
		{
			if (typeof WeixinJSBridge == "undefined"){
			    if( document.addEventListener ){
			        document.addEventListener('WeixinJSBridgeReady', jsApiCall, false);
			    }else if (document.attachEvent){
			        document.attachEvent('WeixinJSBridgeReady', jsApiCall); 
			        document.attachEvent('onWeixinJSBridgeReady', jsApiCall);
			    }
			}else{
			    jsApiCall();
			}
		}
	</script>
</body>
</html>