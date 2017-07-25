<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>艾尼玩微信管理后台</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <link href="${appRoot}/static/wx/css/style.css" rel="stylesheet">
</head>
<body onload="$.init()">
<main class="page-group">
    <section class="page">
        <div class="content" data-type="js">
            <div class="content-inner">
            <!-- view start -->
                <div class="content-block-title">
                    <h1>Program Entry</h1>
                    <p>2016-9-17 15:41:56</p>
                </div>
                <div class="list-block">
                    <ul>
                        <li><a href="${appRoot}/wx/problem/problemList.az" class="item-link list-button external">问题列表</a></li>
                        <li><a href="${appRoot}/wx/problem/zhizhaoproblemList.az" class="item-link list-button external">我要支招</a></li>  
                        <li><a href="${appRoot}/wx/problem/problem.az" class="item-link list-button external">我要提问</a></li>
                        <li><a href="${appRoot}/wx/problem/caizilist.az" class="item-link list-button external">才子排行</a></li>
                        <li><a href="${appRoot}/wx/problem/caifulist.az" class="item-link list-button external">财富排行</a></li>
                        <li><a href="${appRoot}/wx/userinfo/user.az" class="item-link list-button external">用户中心</a></li>
                        <li><a href="${appRoot}/wx/expert/expertList.az" class="item-link list-button external">专家列表</a></li>
                        <li><a href="${appRoot}/wx/problem/hotproblemList.az" class="item-link list-button external">热门问题</a></li>
                    </ul>
                </div>
            <!-- view end -->
            </div>
        </div>
    </section>
</main>
<script src="${appRoot}/static/wx/js/io.js"></script>
<script src="${appRoot}/static/wx/lib/vue.min.js"></script>
<script src="${appRoot}/static/wx/lib/vue-animated-list.js"></script>
<script src="${appRoot}/static/wx/lib/zepto.min.js"></script>
<script src="${appRoot}/static/wx/lib/sm.min.js"></script>
<script src="${appRoot}/static/wx/lib/sm-extend.min.js"></script>
<script src="${appRoot}/static/wx/js/main.js"></script>
</body>
</html>
