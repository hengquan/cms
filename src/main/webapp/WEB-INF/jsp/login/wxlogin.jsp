<%@page import="com.hj.utils.HashSessions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/jsp/inc/head.jsp"%>
<title>登录中...</title>
<link href="${appRoot}/static/css/app.css" rel="stylesheet" type="text/css" media="screen">
<script type="text/javascript">
  	 
  	function doLoad(){
    	<%
    		String openId = HashSessions.getInstance().getOpenId(request);
    		pageContext.setAttribute("openId", openId);
    	%>
    	var openId = "${openId}";
    	//openId="212121212121";
		if(openId==null || ""==openId || "null"==openId){
			var uri = "${redirect_uri}";
	    	var params = "appid=${appid}&redirect_uri="+uri+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
	    	var url = "${weixinCodeUrl}";
	    	hj.open(url,null,params);
		}else{
			//var successUrl = "${successUrl}";
			//if(""==successUrl)successUrl="${appRoot}/home/index.az";
			//hj.open(successUrl,null,false);
			history.go(-1);
		}
    }
  	 
</script>
</head>
<body onload="doLoad();">
</body>
</html>