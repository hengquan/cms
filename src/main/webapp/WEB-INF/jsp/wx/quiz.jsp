<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>提问</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <link href="${appRoot}/static/wx/css/style.css?v=${sversion}" rel="stylesheet">
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<style type="text/css">
		.tag-list {
            height: auto;
        }

        label.label-checkbox input[type=checkbox]:checked + .item-media i.icon-square {
            color: #0894ec;
        }

        i.icon-square {
            color: #c7c7cc;
        }

        .active {
            background: #ff8116;
            color: #ffffff;
            box-shadow: inset 0 0 0 3px #fff;
        }

        .logo {
            text-align: center;
            padding-top: 10px;
            margin-bottom: -8px;
        }

        .logo img {
            width: 70px;
        }

        #agePicker {
            width: 25%;
            margin-left: 10px;
            font-size: 14px;
        }
        .tag-list{
            display: block;
        }
        .tag-list label{
            display: block;
            width: 33%;
            margin: 0;
            margin-bottom: 10px;
            float: left;
            text-indent: 5px;
        }
        #agePicker{
            margin: 10px;
        }
        .picker-modal.picker-columns.remove-on-close.modal-in{
            background-color: #f3f3f3;
        }
        .picker-center-highlight{
            border-top: 1px solid #F5DEB3;
            border-bottom: 1px solid #F5DEB3;
        }
        .picker-item.picker-selected{
            color:  #ff8116;
        }
        .picker-item{
            padding:0 6rem;
        }
        .jfsm{
            width: 72px;
            height:25px;
            float: right;
            margin-right: 10px;
            background: url("../../static/wx/imgs/jifei.png") center/100% no-repeat;
            margin-top: -42px;
        }
        .jfsmtit{
            position: relative;
            text-align: center;
            font-size: 1.2rem;
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
            margin: 0;
        }
	</style>
</head>
<body onload="cc.programs.issue()">
<main class="page-group">
    <section class="page" id="quiz">
        <div class="content">
            <div class="content-inner J_que_list">
            <!-- view start -->
                <article class="common-ctl">
                <div class="logo"><img src="../../static/wx/imgs/logo.png" alt="LOGO"></div>
                <form id="formZhizhao">
	 			 	<!-- 悬赏金额 -->
	 			 	<h4 class="cc-common-title">孩子年龄</h4>
                        <input type="text" id='agePicker' v-model="age" placeholder="请选择"/>
                    <h4 class="cc-common-title">问题属性</h4>
                    <div class="tag-list">
                       
                        <c:forEach items="${problemType }" var="pt" varStatus="s">
                        	<label class="label-checkbox com-nim tag-item">
                                <input type="checkbox" name="tag" value="${pt.id }" v-model="tag">
                                <div class="item-media"><i class="icon icon-form-checkbox"></i></div>
                                <div class="item-media"> ${pt.typeName }</div>
                            </label>
                        </c:forEach>
                       
                    </div>
                    <h4 class="cc-common-title">请输入提问内容</h4>
                    <div class="answer-text"><textarea rows="4" name="answer" placeholder="请描述您的问题(不少于20字)。比如“男孩，7岁，一年级，现在的情况：我家孩子吃饭一直让我们家长操心”" v-model="ans"></textarea></div>
                    <h4 class="cc-common-title">语音输入</h4>
                    <div class="answer-voice">
                    	<span style="font-size: 12px;position: absolute;top: -60px;left: 50%;transform: translate(-50%,0);color:#a9a9a9;font-weight:100;">（语音不能超过60s）</span>
                    	<div class="cc-audio J_paly_voice" hidden><span class="J_time">0s</span> <i class="iconfont">&#xe603;</i> <i class="iconfont J_del_voice">&#xe609;</i></div>
                        <a href="javascript:" class="iconfont J_startRecord" ontouchstart = "return false;">&#xe61f;</a>
                    </div>
                    <h4 class="cc-common-title">添加图片</h4>
                    <div class="answer-imgs">
                        <a href="javascript:" v-for="img in imglist" class="ans-img-item" style="background-image: {{'url('+img +')'}}"></a>
                        <label href="javascript:" class="ans-img-item J_addimg" @click="addimg($event)"></label>
                        <span class="answer-imgs-tag">最多添加6张</span>
                    </div>
                    <div class="common-com">
                        <label class="label-checkbox com-nim" style="width:30%;">
                            <input type="checkbox" name="nimin" v-model="nimin">
                            <div class="item-media"><i class="icon-form-checkbox iconfont icon-square" style="border:0;border-radius: 0;font-size: 1rem;font-weight: 100"></i></div>
                            <div class="item-media"> 匿名发表</div>
                           
                        </label>
                         <div class="jfsm" onclick="jfsm()"></div>
                        <button type="button" id="sub" class="button button-fill button-warning cc-btn">提交问题</button>
                    </div>
                </form>
                </article>
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
var weixiparm={
		appId: '${appId}', // 必填，公众号的唯一标识
		timestamp:'${timestamp}', // 必填，生成签名的时间戳req.timestamp
		nonceStr:'${nonceStr}', // 必填，生成签名的随机串req.nonceStr
		signature:'${signature}',
}
//$(function(){
	 wx.config({
		debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId: '${appId}', // 必填，公众号的唯一标识
		timestamp:'${timestamp}', // 必填，生成签名的时间戳req.timestamp
		nonceStr:'${nonceStr}', // 必填，生成签名的随机串req.nonceStr
		signature:'${signature}',
		jsApiList: [
	            'checkJsApi',
	            'onMenuShareTimeline',
	            'onMenuShareAppMessage',
	            'onMenuShareQQ',
	            'onMenuShareWeibo',
	            'chooseImage',
	            'startRecord',
	            'stopRecord',
	            'playVoice',
	            'pauseVoice',
	            'stopVoice',
	            'onVoicePlayEnd',
	            'translateVoice',
	            'uploadVoice',
	            'uploadImage',
	            'previewImage'
    	] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	}); 
//});
</script>
<script src="${appRoot}/static/wx/lib/zepto.min.js"></script>
<script src="${appRoot}/static/wx/lib/vue.min.js"></script>
<script src="${appRoot}/static/wx/lib/vue-animated-list.js"></script>
<script src="${appRoot}/static/wx/lib/sm.min.js"></script>
<script src="${appRoot}/static/wx/lib/sm-extend.min.js"></script>
<script src="${appRoot}/static/wx/js/io.js"></script>
<script src="${appRoot}/static/wx/js/main.js?v=${sversion}"></script>
 
</body>
</html>
