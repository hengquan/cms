<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>用户中心</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <link href="${appRoot}/static/wx/css/style.css?v=${sversion}" rel="stylesheet">
    <style>
         .quizico{
            animation: scale 1.5s infinite;
            -webkit-animation: scale 1.5s infinite;
        }
        .quiztxt{
            animation: td 1s infinite;
            -webkit-animation: td 1s infinite;
        }
        @-webkit-keyframes scale {
            0%{
                transform:scale(1);
                -webkit-transform:scale(1);
            }
            50%{
                transform:scale(1.2);
                -webkit-transform:scale(1.2);
            }
            100%{
                transform:scale(1);
                -webkit-transform:scale(1);
            }
        }
        @-webkit-keyframes td {
            0% {
                transform: translate(0px, 0px);
                -webkit-transform: translate(0px, 0px);
            }
            50% {
                transform: translate(0px, 2px);
                -webkit-transform: translate(0px, 2px);
            }
            70% {
                transform: translate(0px, -2px);
                -webkit-transform: translate(0px, -2px);
            }
            80% {
                transform: translate(0px, 0px);
                -webkit-transform: translate(0px, 0px);
            }
            90% {
                transform: translate(0px, -2px);
                -webkit-transform: translate(0px, -2px);
            }
            100% {
                transform: translate(0px, 0px);
                -webkit-transform: translate(0px, 0px);
            }
        }
        .user-banner{
            background:#ff8116 url("../../static/wx/imgs/logo_white.png") center 30%/80px no-repeat;
        }
        
    </style>
</head>
<body onload="cc.programs.user()">
<main class="page-group">
    <section class="page page-current" id="userCenter">
        <div class="content" style="margin-bottom:2.5rem;">
            <div class="content-inner J_que_list">
            <!-- view start -->
                <header class="user-banner">
                    <%-- <a href="${appRoot}/wx/userinfo/projectuser.az" class="iconfont user-seting">&#xe61e;</a> --%>
                </header>
                <nav class="user-nav">
                    <a href="${appRoot}/wx/account/getaccount.az?openid=${user.openid}"><p class="iconfont u-ico">&#xe607;</p><span>我的收益</span></a>
                    <a href="${appRoot}/wx/userinfo/projectuser.az">
                        <i class="user-head-min max" style="background-image: url(${user.headimgurl})"></i>
                        <p class="u-name">${(user.userName == null || user.userName == '') ? user.nickname : user.userName}</p>
                    </a>
                    <a href="${appRoot}/wx/integral/getintegral.az?openid=${user.openid}"><p class="iconfont u-ico">&#xe608;</p><span>我的积分</span></a>
                </nav>
                <article class="user-context">
                    <div class="list-block contacts-block">
                        <div class="list-group">
                            <ul>
                                <li>
                                    <div class="item-content">
                                        <a class="item-inner external" href="${appRoot}/wx/problem/myproblem.az?openid=${user.openid}">
                                            <span class="item-title"><i class="iconfont red">&#xe614;</i> 我的提问</span>
                                        </a>
                                    </div>
                                </li>
                                <li>
                                    <div class="item-content">
                                        <a class="item-inner external" href="${appRoot}/wx/problem/myanswer.az?openid=${user.openid}">
                                            <span class="item-title"><i class="iconfont green">&#xe602;</i> 我的支招</span>
                                        </a>
                                    </div>
                                </li>
                                <li>
                                    <div class="item-content">
                                        <a class="item-inner external" href="${appRoot}/wx/problem/myeavesdrop.az?openid=${user.openid}">
                                            <span class="item-title"><i class="iconfont orange">&#xe606;</i> 我的偷听</span>
                                        </a>
                                    </div>
                                </li>
                                <li>
                                    <div class="item-content">
                                        <a class="item-inner external" href="${appRoot}/wx/problem/mycollect.az?openid=${user.openid}">
                                            <span class="item-title"><i class="iconfont purple">&#xe600;</i> 我的收藏</span>
                                        </a>
                                    </div>
                                </li>
                                <c:if test="${user.userType=='2' }">
                                <li>
                                    <div class="item-content">
                                        <a class="item-inner external" href="${appRoot}/wx/expert/userTime.az?openid=${user.openid}">
                                            <span class="item-title"><i class="iconfont pink">&#xe615;</i> 我的时间</span>
                                        </a>
                                    </div>
                                </li>
                                </c:if>
                                <c:if test="${user.userType=='1' }">
                                <li>
                                    <div class="item-content">
                                        <a class="item-inner external" href="${appRoot}/wx/expert/mySubscribe.az?openid=${user.openid}">
                                            <span class="item-title"><i class="iconfont water">&#xe61d;</i> 我的预约</span>
                                        </a>
                                    </div>
                                </li>
                                </c:if>
                                <li>
                                    <div class="item-content">
                                        <a class="item-inner external" href="${appRoot}/wx/expert/myQRcode.az?openid=${user.openid}">
                                            <span class="item-title"><i class="icon icon-code" style="color:#2E8B57;"></i> 我的二维码</span>
                                        </a>
                                    </div>
                                </li>
                                <%-- <li>
                                    <div class="item-content">
                                        <a class="item-inner external" href="${appRoot}/wx/problem/problem.az">
                                            <span class="item-title"><img src="../../static/wx/imgs/banner_bottom.png" alt="banner" height="42"></span>
                                        </a>
                                    </div>
                                </li> --%>
                            </ul>
                        </div>
                    </div>
                </article>
            <!-- view end -->
            </div>
        </div>
        <div class="row" style="overflow:visible;position: absolute;left:50%;bottom:10px;transform: translate(-50%,0);z-index:9999;width:90%;margin: 0">
            <a href="${appRoot}/wx/problem/problem.az" class="row col-50" style="overflow:visible;margin:0">
                <img class="quizico" src="../../static/wx/imgs/quiz_ico.png" alt="banner" style="float: left;width:20%">
                <img class="quiztxt" src="../../static/wx/imgs/quiz_text.png" alt="banner" style="float: left;width: 80%;margin: 5% 0">
            </a><!-- href="${appRoot}/wx/problem/problemList.az" -->
            <a class="row col-50 question_list" href="${appRoot}/wx/problem/hotproblemList.az" style="overflow:visible;margin-left: 8%">
                <img class="quizico" src="../../static/wx/imgs/questionList_ico.png" alt="banner" style="float: left;width:20%">
                <img class="quiztxt" src="../../static/wx/imgs/questionList_text.png" alt="banner" style="float: left;width: 80%;margin: 5% 0">
            </a>
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
<script type="text/javascript">
$.config = {router: false}
</script>
<script src="${appRoot}/static/wx/lib/sm.min.js"></script>
<script src="${appRoot}/static/wx/lib/sm-extend.min.js"></script>
<script src="${appRoot}/static/wx/js/main.js?v=${sversion}"></script>
</body>
</html>
