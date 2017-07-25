<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%><!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title><c:if test="${hot!=null}">热门问题</c:if><c:if test="${zhizhao!=null}">我要支招</c:if><c:if test="${zhizhao==null && hot==null}">问题列表</c:if></title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <link href="${appRoot}/static/wx/css/style.css?v=${sversion}" rel="stylesheet">
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <style>
        .clear_input{
            position: absolute;
            top:3px;
            right:.6rem;
            color: #b4b4b4;
            font-size: .8rem;
            display: none;
        }
        .bar{
            z-index: 11000;
            top:0;
            height:2rem;
        }
        .pull-to-refresh-content{
            top:1.8rem;
        }
         .bar .searchbar{
            background: #fff;
            height:4rem;
        }
		.bar .searchbar .search-input{
            margin-bottom: .3rem;
        }
        .bar .searchbar .row{
            text-align: center;
            padding-top: .25rem;
            margin: 0 auto;
            /*margin-bottom: -.1rem;*/
            overflow: hidden;
            background-color: #fff;
            position: absolute;
            top:2rem;
            left: 0;
            width: 100%;
        }
        .bar .searchbar .row div{
            position: relative;
            color:#5c5c59;
            border-bottom: 1px solid transparent;
            font-size: .75rem;
            margin-top: .3rem;
            padding-bottom: .3rem;
            background-color: #fff;
        }
        .bar .searchbar .row div i{
            font-size: .65rem;
            padding-left: 3px;
            color:rgba(92,92,89,.65)
        }
        /*.bar .searchbar .row div::before{*/
            /*content: "";*/
            /*display: block;*/
            /*width: 100%;*/
            /*height: 1px;*/
            /*background-color: #ff8116;*/
            /*position: absolute;*/
            /*bottom:-3px;*/
            /*left: 0;*/
            /*z-index: 999;*/
        /*}*/
        .bar .searchbar .row div:not(:last-child)::after{
            content: "";
            display: block;
            width: 1px;
            height: 1rem;
            background-color: #cfcfcf;
            position: absolute;
            top:.1rem;
            right:-4%;
        }
        .content-block{
            margin: 0;
        }
        .content-block .que_title{
            width: 85%;
            float: left;
            text-align: center;
            font-size: 1.2rem;
        }
        .content-block .que_cancel{
            width: 12%;
            float: right;

        }
        .content-block .que_cancel a{
            color: #6d6d72;
        }
        .cc-card-footer a{
            text-align: left;
            text-indent: 10px;
        }
        .que_tag_list{
            position: absolute;
            display: block !important;
            top:4rem;
            left:0;
            width: 100%;
            list-style:none;
            margin:0;
            padding:0;
            height: 0;
            background: #fff;
            overflow: hidden;
            border-top: 1px solid #cfcfcf;
        }
        .que_tag_list li{
            padding: 6px 0;
            text-align: center;
            font-size: .7rem;
        }
        .que_tag_list:nth-child(4) li{
            float:left;
            width:33%;
        }
        .que_tag_list:nth-child(5) li{
            float:left;
            width:50%;
        }
        /*.que_tag_list:nth-child(6) li{*/
            /*width:50%;*/
        /*}*/

        .bar:after{
            background-color: #cfcfcf;
            height: 3px;
        }
        .que_tag_list li.que_checked{
            color:#ff8116;
        }
        /*图片浏览器*/
        .login-screen, .popup{
            z-index:11100;
        }
        .que_modal{
            background: rgba(0,0,0,0.6);
        }
    </style>
</head>
<body onload="cc.programs.questionList('${hot}','${zhizhao }').init()">
<div class="bar bar-header-secondary">
    <div class="searchbar">
        <a class="searchbar-cancel" @click.stop.prevent="addProblemList()">搜索</a>
        <div class="search-input">
            <label class="icon icon-search" for="search"></label>
            <input type="search" id='search' placeholder='请输入您要搜索的内容' v-model="searchValue"/>
            <i class="clear_input iconfont icon-failed"></i>
        </div>
        <!--<div class="que_tag" onclick="cc.programs.questionList().tagList()">-->
            <!--<span class="iconfont icon-xiangxia1"></span>-->
        <!--</div>-->
        <div class="row">
            <div class="col-33" data-index="0">孩子年龄<i class="iconfont icon-down"></i></div>
            <div class="col-33" data-index="1">问题属性<i class="iconfont icon-down"></i></div>
            <div class="col-33" data-index="2">最新问题<i class="iconfont icon-down"></i></div>
        </div>
        <ul class="que_tag_list clearfix">
            <li data-age="" class="que_checked">不限</li>
            <li data-age="1">1岁</li>
            <li data-age="2">2岁</li>
            <li data-age="3">3岁</li>
            <li data-age="4">4岁</li>
            <li data-age="5">5岁</li>
            <li data-age="6">6岁</li>
            <li data-age="7">7岁</li>
            <li data-age="8">8岁</li>
            <li data-age="9">9岁</li>
            <li data-age="10">10岁</li>
            <li data-age="11">11岁</li>
            <li data-age="12">12岁</li>
            <li data-age="13">13岁</li>
            <li data-age="14">14岁</li>
            <li data-age="15">15岁</li>
            <li data-age="16">16岁</li>
            <li data-age="17">17岁</li>
            <li data-age="18">18岁</li>
        </ul>
        <ul class="que_tag_list clearfix">
            <li data-id="" class="que_checked">不限</li>
            <c:forEach items="${problemType}" var="pt">
            <li data-id='${pt.id }'>${pt.typeName }</li>
            </c:forEach>
        </ul>
        <ul class="que_tag_list clearfix">
            <li data-hot='1'>热门问题</li>
            <li data-hot='2'  class="que_checked">最新问题</li>
        </ul>
    </div>
</div>
<main class="page-group">
    <section class="page" id="question_list">
        <div class="content infinite-scroll infinite-scroll-bottom pull-to-refresh-content" data-distance="120" data-ptr-distance="55">
            <div class="content-inner J_que_list">
            <!-- view start -->
                <div class="pull-to-refresh-layer">
                    <div class="preloader"></div>
                    <div class="pull-to-refresh-arrow"></div>
                </div>
                <!-- ctl="true" 禁用状态栏 track-by="id" 根据ID排重 -->
                <que-item v-for="item in problemList"
                               :item="item"
                               track-by="$index"
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
		arr:'',
};
</script>
<script src="${appRoot}/static/wx/lib/vue.min.js"></script>
<script src="${appRoot}/static/wx/lib/vue-animated-list.js"></script>
<script src="${appRoot}/static/wx/lib/zepto.min.js"></script>
<script src="${appRoot}/static/wx/lib/zepto-animate.js"></script>
<script src="${appRoot}/static/wx/lib/sm.min.js"></script>
<script src="${appRoot}/static/wx/lib/sm-extend.min.js"></script>
<script src="${appRoot}/static/wx/js/io.js"></script>
<script src="${appRoot}/static/wx/js/main.js?v=${sversion}"></script> 
</body>
</html>
