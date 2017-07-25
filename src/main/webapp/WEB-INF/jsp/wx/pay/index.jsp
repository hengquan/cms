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

<body onload="callpay()">
	<!-- <div class="container">
		<header class="inside-head">
			<span></span> <em><p>微信支付</p></em> <i></i>
		</header>
	</div> -->
	
	<form action="${appRoot}/${url }" method="post" id="orderForm" name="orderForm">
		<input type="hidden" id="prepay_id" name="prepay_id" value="${prepay_id}"/> 
	</form>
	
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
					//alert(res.err_code+res.err_desc+res.err_msg);
					if(res.err_msg != "get_brand_wcpay_request：ok" ) {
						//暂时不用
						//$("#orderForm").submit();
						window.location.href="http://beijingainiwan.com/anwar/orders.html";
					}else{
						alert("支付成功！");
					}
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