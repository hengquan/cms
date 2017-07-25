<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>我的问题详情</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <link href="${appRoot}/static/wx/css/style.css?v=${sversion}" rel="stylesheet">
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <style>
        .zjda_tips{
            padding:10px;
            background-color: #ffffff;
            font-size: 14px;
        }
        .zjda_tips .tips_con{
            background-color: #ff8116;
            height:80px;
            border-radius: 5px;
            color: #ffffff;
            margin-top: 30px;
            overflow: hidden;
        }
        .zjda_tips .tips_con p{
            text-align: center;
            height: 1.5rem;
            vertical-align: middle;
            margin: 0;
            margin-bottom: 0px;
        }
        .zjda_tips .tips_con p.tips_1{
            margin-top:15px;
        }
        .zjda_tips .tips_con p img{
            vertical-align: top;
            margin-top: -5px;
            margin-right: 5px;
            width:1.5rem;
        }
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
<body onload="cc.programs.userQuestionDetail('${pm.id}')">
<main class="page-group">
    <section class="page page-current" id="questionDetail">
        <div class="content infinite-scroll infinite-scroll-bottom" data-distance="120">
            <div class="content-inner J_que_list">
            <!-- view start -->
                <que-item :item="item" offermoney="true" asked="true"></que-item>
                <article class="cc-common-list">
                    <h4 class="cc-common-title">答案</h4>
                    <que-item class="cc-answer" v-for="ans in answers" :item="ans" :zjda="item.zjda" userqd="true" com="true" transition="qlitem" stagger="160"></que-item>
                </article>
            <!-- view end -->
            	<%-- <c:if test="${pm.isFinish==0 && count!=1}">
            	<c:if test="${answerSize!=0}">
            	<div class="zjda_tips" onclick="cc.programs.hongBao('${pm.id}')">
                    <div class="tips_con">
                        <p class="tips_1"><img src="../../static/wx/imgs/cry.png" alt="cry">无最佳答案</p>
                        <p>${pm.offerMoney}元支招费让${answerSize}位热心的支招者来抢红包了！</p>
                    </div>
                </div>
                </c:if>
                </c:if> --%>
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
<script src="${appRoot}/static/wx/lib/vue.min.js"></script>
<script src="${appRoot}/static/wx/lib/vue-animated-list.js"></script>
<script src="${appRoot}/static/wx/lib/zepto.min.js"></script>
<script src="${appRoot}/static/wx/lib/sm.min.js"></script>
<script src="${appRoot}/static/wx/lib/sm-extend.min.js"></script>
<script src="${appRoot}/static/wx/js/io.js"></script>
<script src="${appRoot}/static/wx/js/main.js?v=${sversion}"></script>

<script type="text/javascript">
	wx.ready(function () {  
	    wx.onMenuShareTimeline({  
	        title: '${shareUserName}提了：${pm.contentText}', // 分享标题  
	        link: '${shareUrl}', // 分享链接  
	        imgUrl: '${appAccessUrl}/share_logo.jpg', // 分享图标  
	        success: function () {  
	        	  //alert('已分享');
		     },  
		     cancel: function () {  
		    	  alert('已取消');
		     },  
		     fail: function (res) {
	             alert(JSON.stringify(res));
	        }  
	    });  
	    
	    wx.onMenuShareAppMessage({  
	        title: '${shareUserName}提了：${pm.contentText}', // 分享标题  
	        desc: '点击学习或给他支招；教育有惑，上支招客', // 分享描述  
	        link: '${shareUrl}', // 分享链接  
	        imgUrl: '${appAccessUrl}/share_logo.jpg', // 分享图标  
	        type: 'link', // 分享类型,music、video或link，不填默认为link  
	        success: function (res) {
	              //alert('已分享');
	        },
	        cancel: function (res) {
	             alert('已取消');
	        },
	        fail: function (res) {
	             alert(JSON.stringify(res));
	        }
	   });  
	});  
	
</script>

</body>
</html>
