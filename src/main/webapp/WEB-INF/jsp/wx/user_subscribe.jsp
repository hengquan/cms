<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>我的预约</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <link href="${appRoot}/static/wx/css/style.css" rel="stylesheet">
</head>
<body onload="cc.programs.userSubscribe().init()">
<main class="page-group">
    <section class="page page-current">
        <header class="user-banner min" style="background: #ff8116;">
            <span class="user-info"><a href="javascript:" class="user-head-min" style="background-image: url(${user.headimgurl})"></a>
				<c:choose>
					<c:when test="${user.userName== '' || user.userName== undefined}">  
						${user.nickname}									   	
					</c:when>
					<c:otherwise> 
						${user.userName}	
					</c:otherwise>
				</c:choose>
			</span>
        </header>
        <div style="top:3.3rem;z-index:-1" class="content  infinite-scroll infinite-scroll-bottom pull-to-refresh-content" data-distance="120" data-ptr-distance="55">
            <div class="content-inner J_que_list">
            <!-- view start -->

                <div class="pull-to-refresh-layer">
                    <div class="preloader"></div>
                    <div class="pull-to-refresh-arrow"></div>
                </div>
                <!--<h3>我的预约</h3>-->
                <user-subscribe 
	                v-for="item in makeMsg" 
	                :item="item"  
	                :page="page"
	                :makemsg="makeMsg"
                ></user-subscribe>

                <!-- view end -->
            </div>
        </div>
    </section>
</main>
<script type="text/javascript">
var userInfo={
		openid:'${openid}'
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
