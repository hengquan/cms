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
	<!-- <div class="container">
		<header class="inside-head">
			<span></span> <em><p>微信支付</p></em> <i></i>
		</header>
	</div> -->
	
	<form action="${appRoot}/pay/index/" method="post" id="payForm" name="payForm">
		<input type="hidden" id="prepay_id" name="prepay_id" value="${prepay_id}"/> 
	</form>
	
	<script type="text/javascript">
		$(function(){
			//window.history.forward(1);
			
			$("#payForm").submit();
		});
	</script>
	
</body>
</html>