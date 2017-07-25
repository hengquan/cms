<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>我的偷听</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <link href="${appRoot}/static/wx/css/style.css?v=${sversion}" rel="stylesheet">
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body onload="cc.programs.userListen().init()">
<main class="page-group">
    <section class="page page-current" id="userZhizhao">
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
                <!-- <div class="buttons-tab cc-tab">
                    <a href="#tab1" class="tab-link J_tab1 active button">我的偷听</a>
                    <a href="#tab2" class="tab-link J_tab2 button">幸运偷听</a>
                </div> -->
                <div class="tabs">
                    <div id="tab1" class="tab active">
                        <que-ans v-for="item in listMine" :ele="item" track-by="$index" xytt="true" transition="qlitem" stagger="160"></que-ans>
                    </div>
                    <!-- <div id="tab2" class="tab">
                        <que-ans v-for="item in listLuck" :ele="item" track-by="$index" xytt="true" transition="qlitem" stagger="160"></que-ans>
                    </div> -->
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
