<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>我的支招</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <link href="${appRoot}/static/wx/css/style.css?v=${sversion}" rel="stylesheet">
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
     <style>
        .red-packer.red-packer-detail .modal-inner {
            background: url(../../static/wx/imgs/red_packer_detail.png) no-repeat top/contain;
            padding-top: 159%;
            background-color: #fff;
        }

        .other {
            /*position: absolute;top: 50%;left: 0;width:100%;color:#0966a3;font-size: .7rem;*/
            text-align: left;
        }

        .other i {
            float: right;
            padding-right: 20px;
            font-style: normal;
        }

        .red-packer .user-head-min {
            width: 70px;
            height: 70px;
            margin-left: -31px;
        }

        .red-packer .user-name {
            top: 115px;
        }

        .other_box {
            position: absolute;
            top: 67%;
            left: 0;
            width: 100%;
            background-color: #fff;
            border-radius: .35rem;
            height: 52%;
            overflow: scroll;
        }

        .red-packer.red-packer-detail .other_box .user-head-min {
            margin-left: 10px;
            margin-right: 10px;
            width: 42px;
            height: 42px;
        }
    </style>
</head>
<body onload="cc.programs.userZhizhao().init()">
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
				<div class="buttons-tab cc-tab">
                    <a href="#tab1" class="tab-link J_tab1 active button">最佳支招</a>
                    <a href="#tab2" class="tab-link J_tab2 button">幸运支招</a>
                    <a href="#tab3" class="tab-link J_tab3 button">被偷听支招</a>
                    <a href="#tab4" class="tab-link J_tab4 button">其他支招</a>
                </div>
                <div class="tabs">
                    <div id="tab1" class="tab active">
                        <que-ans v-for="item in listBest" :ele="item" track-by="$index" transition="qlitem" stagger="160"></que-ans>
                    </div>
                    <div id="tab2" class="tab">
                        <que-ans v-for="item in listLuck" :ele="item" track-by="$index" luckhandle="true" transition="qlitem" stagger="160"></que-ans>
                    </div>
                    <div id="tab3" class="tab">
                        <que-ans v-for="item in listListen" :ele="item" track-by="$index" lihandle="true" transition="qlitem" stagger="160"></que-ans>
                    </div>
                    <div id="tab4" class="tab">
                        <que-ans v-for="item in listOther" :ele="item" track-by="$index" transition="qlitem" stagger="160"></que-ans>
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
