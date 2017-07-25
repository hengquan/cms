<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>预约评价</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <link href="${appRoot}/static/wx/css/style.css" rel="stylesheet">
</head>
<body onload="cc.programs.userSubscribe().start()">
<main class="page-group">
    <section class="page page-current" id="evaluate">
    	<c:if test="${empty expertEvaluate.evaNum}">
        	<nav class="bar bar-tab cc-bar"><a class="tab-item cc-com-btn" href="javascript:" id="sub">发表评价</a></nav>
    	</c:if>
        <div class="content">
            <div class="content-inner J_que_list">
            <!-- view start -->
                <div class="cc-u-item m-b">
                    <div class="cc-u-il">
                        <a href="javascript:" class="user-head-min" style="background-image: url(${user.headimgurl})"></a>
                        <p><c:choose>
						<c:when test="${user.userName== '' || user.userName== undefined}">  
							${user.nickname}									   	
						</c:when>
						<c:otherwise> 
							${user.userName}	
						</c:otherwise>
					</c:choose></p>
                    </div>
                    <div class="cc-u-ir">
                        <h4 class="cc-u-inhead">${makeMsg.s_time }</h4>
                        <div class="cc-u-inbody">
                            <span>${makeMsg.sumPrice } <small>元</small></span>
                            ${makeMsg.typeName }
                        </div>
                    </div>
                </div>

                <div class="cc-eva">
                    <h4 class="cc-common-title">点评</h4>
                    <div class="start-box" data-size="${empty expertEvaluate.evaNum ? 0 : expertEvaluate.evaNum}" id="start">
                    	<c:if test="${expertEvaluate.evaNum==1 }">
                    		<i class="iconfont icon-biaoxingfill" data-no="1"></i>
	                        <i class="icon-favor iconfont" data-no="2"></i>
	                        <i class="icon-favor iconfont" data-no="3"></i>
	                        <i class="icon-favor iconfont" data-no="4"></i>
	                        <i class="icon-favor iconfont" data-no="5"></i>
                    	</c:if>
                    	<c:if test="${expertEvaluate.evaNum==2 }">
                    		<i class="iconfont icon-biaoxingfill" data-no="1"></i>
	                        <i class="iconfont icon-biaoxingfill" data-no="2"></i>
	                        <i class="icon-favor iconfont" data-no="3"></i>
	                        <i class="icon-favor iconfont" data-no="4"></i>
	                        <i class="icon-favor iconfont" data-no="5"></i>
                    	</c:if>
                    	<c:if test="${expertEvaluate.evaNum==3 }">
                    		<i class="iconfont icon-biaoxingfill" data-no="1"></i>
	                        <i class="iconfont icon-biaoxingfill" data-no="2"></i>
	                        <i class="iconfont icon-biaoxingfill" data-no="3"></i>
	                        <i class="icon-favor iconfont" data-no="4"></i>
	                        <i class="icon-favor iconfont" data-no="5"></i>
                    	</c:if>
                    	<c:if test="${expertEvaluate.evaNum==4 }">
                    		<i class="iconfont icon-biaoxingfill" data-no="1"></i>
	                        <i class="iconfont icon-biaoxingfill" data-no="2"></i>
	                        <i class="iconfont icon-biaoxingfill" data-no="3"></i>
	                        <i class="iconfont icon-biaoxingfill" data-no="4"></i>
	                        <i class="icon-favor iconfont" data-no="5"></i>
                    	</c:if>
                    	<c:if test="${expertEvaluate.evaNum==5 }">
                    		<i class="iconfont icon-biaoxingfill" data-no="1"></i>
	                        <i class="iconfont icon-biaoxingfill" data-no="2"></i>
	                        <i class="iconfont icon-biaoxingfill" data-no="3"></i>
	                        <i class="iconfont icon-biaoxingfill" data-no="4"></i>
	                        <i class="iconfont icon-biaoxingfill" data-no="5"></i>
                    	</c:if>
                    	<c:if test="${expertEvaluate.evaNum==0}">
	                        <i class="icon-favor iconfont" data-no="1"></i>
	                        <i class="icon-favor iconfont" data-no="2"></i>
	                        <i class="icon-favor iconfont" data-no="3"></i>
	                        <i class="icon-favor iconfont" data-no="4"></i>
	                        <i class="icon-favor iconfont" data-no="5"></i>
                    	</c:if>
                    	<c:if test="${empty expertEvaluate.evaNum}">
	                        <i class="icon-favor iconfont" data-no="1"></i>
	                        <i class="icon-favor iconfont" data-no="2"></i>
	                        <i class="icon-favor iconfont" data-no="3"></i>
	                        <i class="icon-favor iconfont" data-no="4"></i>
	                        <i class="icon-favor iconfont" data-no="5"></i>
                    	</c:if>
                    </div>
                    <h4 class="cc-common-title">评价描述</h4>
                    <div class="answer-text"><textarea rows="6" placeholder="清输入评价描述" name="ans" id="ans" <c:if test="${!empty expertEvaluate.evaNum}">readonly</c:if>>${expertEvaluate.evaContent }</textarea></div>
                </div>

            <!-- view end -->
            </div>
        </div>
    </section>
</main>
<script type="text/javascript">
var userInfo={
		makeOrderId:'${makeOrderId}',
	};
</script>
<script src="${appRoot}/static/wx/js/io.js"></script>
<script src="${appRoot}/static/wx/lib/vue.min.js"></script>
<script src="${appRoot}/static/wx/lib/vue-animated-list.js"></script>
<script src="${appRoot}/static/wx/lib/zepto.min.js"></script>
<script src="${appRoot}/static/wx/lib/sm.min.js"></script>
<script src="${appRoot}/static/wx/lib/sm-extend.min.js"></script>
<script src="${appRoot}/static/wx/js/main.js"></script>
</body>
</html>
