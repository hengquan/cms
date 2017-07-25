<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>我的收藏</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <link href="${appRoot}/static/wx/css/style.css?v=${sversion}" rel="stylesheet">
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body onload="cc.programs.userCollect()">
<main class="page-group">
    <section class="page page-current" id="userCollect">
        <div class="content infinite-scroll infinite-scroll-bottom pull-to-refresh-content" data-distance="120" data-ptr-distance="55">
            <div class="content-inner J_que_list">
            <!-- view start -->
            	<div class="pull-to-refresh-layer">
                    <div class="preloader"></div>
                    <div class="pull-to-refresh-arrow"></div>
                </div>
                <header class="user-banner min" style="background: #ff8116;">
                    <span class="user-info"><a href="javascript:" class="user-head-min" style="background-image: url(${user.headimgurl})"></a> ${(user.userName == null || user.userName == '') ? user.nickname : user.userName}</span>
                </header>
                <que-item v-for="item in problemList"
                          :item="item"
                          track-by="$index"
                          collect="true"
                          :index="$index"
                          ctl="true"
                          transition="qlitem"
                          stagger="160"
                          @click.stop.prevent="saveStr(item.url)"></que-item>
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
