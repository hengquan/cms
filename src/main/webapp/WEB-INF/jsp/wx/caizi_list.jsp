<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>才子榜</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <link href="${appRoot}/static/wx/css/style.css?v=${sversion}" rel="stylesheet">
    <style type="text/css">
    .zixun{
    display: inline-block;
    font-style: normal;
    width:90px;
    line-height: 20px;
    text-align: center;
    margin-left: .5rem;
    border: 1px solid #4169E1;
    color:#4169E1;
    border-radius: 4px;
    font-size: 12px;
	}
    </style>
</head>
<body onload="cc.programs.paiHang('caizi','${total}')">
<main class="page-group">
    <section class="page" id="caizi_list">
        <div class="content infinite-scroll infinite-scroll-bottom" data-distance="120" data-ptr-distance="55">
            <div class="content-inner J_que_list">
            <!-- view start -->
                <div class="buttons-tab cc-tab">
                    <a href="#tab1" class="tab-link active button">支招排行</a>
                    <!-- <a href="#tab2" class="tab-link button">提问排行</a>
                    <a href="#tab3" class="tab-link button">偷听排行</a> -->
                    <a href="#tab4" class="tab-link button">贡献排行</a>
                </div>
                <div class="tabs">
                    <div id="tab1" class="tab active">
                        <div class="cc-list-hd">
                            <span class="cc-li-l">我目前排名：<c:forEach items="${isoptimumLst }" var="z" varStatus="s"><c:if test="${openid==z.openid }">${s.count }</c:if></c:forEach></span>
                            <span class="cc-li-r"><c:forEach items="${isoptimumList }" var="z" varStatus="s"><c:if test="${openid==z.openid }">${z.isoptimumCount }</c:if></c:forEach>次</span>
                        </div>
                        <ul class="cc-list-con">
              				<c:forEach items="${isoptimumList }" var="z" varStatus="s"> 
              					<li>
                                	<span class="cc-li-l"><i class="user-head-min sx" style="background-image: url(${z.headimgurl})"></i> ${(z.userName == null || z.userName == '') ? z.nickname : z.userName}<c:if test="${z.userType==2 }"><i onclick="zxOnline('${z.openid }')" class="zixun">在线咨询</i></c:if></span>
                                	<span class="cc-li-r">${z.isoptimumCount }次</span>
                            	</li>
              				</c:forEach>
                        </ul>
                    </div>
                    <%-- <div id="tab2" class="tab">
                        <div class="cc-list-hd">
                            <span class="cc-li-l">我目前排名：<c:forEach items="${problemLst }" var="p" varStatus="s"><c:if test="${openid==p.openid }">${s.count }</c:if></c:forEach></span>
                            <span class="cc-li-r">${user.problemNum }次</span>
                        </div>
                        <ul class="cc-list-con">
                        <c:forEach items="${problemList }" var="p" varStatus="s"> 
                            <li>
                                <span class="cc-li-l"><i class="user-head-min sx" style="background-image: url(${p.headimgurl})"></i> ${(p.userName == null || p.userName == '') ? p.nickname : p.userName}</span>
                                <span class="cc-li-r">${p.problemNum }次</span>
                            </li>
                        </c:forEach>
                        </ul>
                    </div>
                    <div id="tab3" class="tab">
                    	<div class="cc-list-hd">
                            <span class="cc-li-l">我目前排名：<c:forEach items="${eavesdropLst }" var="e" varStatus="s"><c:if test="${openid==e.openid }">${s.count }</c:if></c:forEach></span>
                            <span class="cc-li-r">${user.eavesdropNum }次</span>
                        </div>
                        <ul class="cc-list-con">
                        <c:forEach items="${eavesdropList }" var="e" varStatus="s"> 
                            <li>
                                <span class="cc-li-l"><i class="user-head-min sx" style="background-image: url(${e.headimgurl})"></i> ${(e.userName == null || e.userName == '') ? e.nickname : e.userName}</span>
                                <span class="cc-li-r">${e.eavesdropNum }次</span>
                            </li>
                        </c:forEach>
                        </ul>
                    </div> --%>
                    <div id="tab4" class="tab">
                    	<div class="cc-list-hd">
                            <span class="cc-li-l">我目前排名：<c:forEach items="${zhizhaoLst }" var="z" varStatus="s"><c:if test="${openid==z.openid }">${s.count }</c:if></c:forEach></span>
                            <span class="cc-li-r">${user.answerNum }次</span>
                        </div>
                        <ul class="cc-list-con">
              				<c:forEach items="${zhizhaoList }" var="z" varStatus="s"> 
              					<li>
                                	<span class="cc-li-l"><i class="user-head-min sx" style="background-image: url(${z.headimgurl})"></i> ${(z.userName == null || z.userName == '') ? z.nickname : z.userName}<c:if test="${z.userType==2 }"><i onclick="zxOnline('${z.openid }')" class="zixun">在线咨询</i></c:if></span>
                                	<span class="cc-li-r">${z.answerNum }次</span>
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
<script src="${appRoot}/static/wx/js/main.js?v=${sversion}"></script>
</body>
</html>
