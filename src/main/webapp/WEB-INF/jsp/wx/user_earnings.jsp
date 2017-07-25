<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>我的收益</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <link href="${appRoot}/static/wx/css/style.css?v=${sversion}" rel="stylesheet">
    <style>
    	 .jfsm{
            width: 72px;
            height:25px;
            float: right;
            margin-right: 10px;
            margin-top: 45px;
            background: url("../../static/wx/imgs/jifei2.png") center/100% no-repeat;
        }
        .jfsmtit{
            position: relative;
            text-align: center;
            font-size: 1.2rem;
            margin-bottom: 1rem;
        }
        .jfsmtit i{
            position: absolute;
            top:-.6rem;
            right: 0;
            font-size: 16px;
        }
        .jfsmBox .modal-inner{
            background-color: #fff;
        }
        .jfsmBox pre {
            white-space: pre-wrap;
            white-space: -moz-pre-wrap;
            white-space: -pre-wrap;
            white-space: -o-pre-wrap;
            font-size: 14px;
        }
    </style>
</head>
<body onload="cc.programs.userMine('shouyi','${user.openid }','${total }')">
<main class="page-group">
    <section class="page page-current" id="userShouyi">
        <div class="content infinite-scroll infinite-scroll-bottom"  data-distance="100">
            <div class="content-inner J_que_list">
            <!-- view start -->
                <header class="user-banner min" style="background: #ff8116;">
                    <span class="user-info"><a href="javascript:" class="user-head-min" style="background-image: url(${user.headimgurl})"></a> ${(user.userName == null || user.userName == '') ? user.nickname : user.userName}</span>
                    <div class="jfsm" onclick="jfsm()"></div>
                </header>

                <div class="cc-inner">
                    <div class="item-title">${createTime }</div>
                    <div class="item-after">收益：<span class="theme">${money }</span></div>
                </div>
                <div class="list-block cc-earnings">
                    <ul class="list-container">
                    <c:forEach items="${aList }" var="a" varStatus="s">
                        <li class="item-content">
                            <div class="item-inner">
                                <div class="item-title">
                                    <span><c:if test="${a.getApproach=='01'}">偷听答案</c:if><c:if test="${a.getApproach=='02'}">最佳支招</c:if><c:if test="${a.getApproach=='03'}">参与红包</c:if></span>
                                    <p>${a.createTimeString}</p>
                                </div>
                                <div class="item-after">${a.proceedsMoney }</div>
                            </div>
                        </li>
                    </c:forEach>
                    </ul>
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
