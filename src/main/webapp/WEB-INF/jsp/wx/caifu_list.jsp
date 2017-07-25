<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>财富榜</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <link href="${appRoot}/static/wx/css/style.css" rel="stylesheet">
</head>
<body onload="cc.programs.paiHang('caifu')">
<main class="page-group">
    <section class="page" id="caifu_list">
        <div class="content  infinite-scroll infinite-scroll-bottom" data-distance="120" data-ptr-distance="55">
            <div class="content-inner J_que_list">
            <!-- view start -->
                <div class="buttons-tab cc-tab">
                    <a href="#tab1" class="tab-link active button">收益排行</a>
                    <a href="#tab2" class="tab-link button">积分排行</a>
                </div>
                <div class="tabs">
                    <div id="tab1" class="tab active">
                        <div class="cc-list-hd">
                            <span class="cc-li-l">我目前排名：<c:forEach items="${moneyLst }" var="m" varStatus="s"><c:if test="${openid==m.openid }">${s.count }</c:if></c:forEach></span>
                            <span class="cc-li-r">￥${user.money }</span>
                        </div>
                        <ul class="cc-list-con">
                        	<c:forEach items="${moneyList }" var="m" varStatus="s"> 
                            	<li>
                                	<span class="cc-li-l"><i class="user-head-min sx" style="background-image: url(${m.headimgurl})"></i> ${(m.userName == null || m.userName == '') ? m.nickname : m.userName}</span>
                                	<span class="cc-li-r">￥${m.money }</span>
                            	</li>
              				</c:forEach>
                        </ul>
                    </div>
                    <div id="tab2" class="tab">
                        <div class="cc-list-hd">
                            <span class="cc-li-l">我目前排名：<c:forEach items="${integralLst }" var="i" varStatus="s"><c:if test="${openid==i.openid }">${s.count }</c:if></c:forEach></span>
                            <span class="cc-li-r">${user.integral }分</span>
                        </div>
                        <ul class="cc-list-con">
                        <c:forEach items="${integralList }" var="i" varStatus="s"> 
                            <li>
                                <span class="cc-li-l"><i class="user-head-min sx" style="background-image: url(${i.headimgurl})"></i> ${(i.userName == null || i.userName == '') ?i.nickname : i.userName}</span>
                                <span class="cc-li-r">${i.integral }分</span>
                            </li>
                        </c:forEach>
                        </ul>
                    </div>
                    <div class="infinite-scroll-preloader">
                        <div class="preloader" style="display: none"></div>
                    </div>
                </div>
            <!-- view end -->
            </div>
        </div>
    </section>
</main>
<script type="text/javascript">
var userInfo={
		id:'${appRoot}',
		openid:'${user.openid}',
		name:'${user.userName?user.userName : user.nickname}',
		diz:'',
		arr:''
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
