<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>问题详情</title>
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
            margin: 0 auto;
            background: url("../../static/wx/imgs/jifei.png") center/100% no-repeat;
        }
        .jfsmtit{
            position: relative;
            text-align: center;
            font-size: 1.2rem;
        }
        .jfsmBox .modal-inner{
            background-color: #fff;
        }
        pre {
            white-space: pre-wrap;
            white-space: -moz-pre-wrap;
            white-space: -pre-wrap;
            white-space: -o-pre-wrap;
            font-size: 14px;
            margin: 0;
        }
    </style>
</head>
<body onload="cc.programs.questionDetail('${pm.id}').init()">
<main class="page-group">
    <section class="page page-current" id="question_detail"><!-- external  -->
        <c:if test="${pm.userId!=openid && count!=1 && pm.isFinish==0}"><nav class="bar bar-tab cc-bar"><a class="tab-item cc-com-btn" href="#zhizhao">我来支招</a></nav></c:if>
        <div class="content infinite-scroll infinite-scroll-bottom" data-distance="120" >
            <div class="content-inner J_que_list">
            <!-- view start -->
                <que-item :item="item" offermoney="true"></que-item>
                <article class="cc-common-list">
                    <h4 class="cc-common-title">答案</h4>
                    <que-item class="cc-answer" v-for="ans in answers" :item="ans" :zjda="item.zjda" userqd="true" com="true" transition="qlitem" stagger="160"></que-item>
                </article>
            <!-- view end -->
            <%-- <c:if test="${pm.userId==openid && pm.isFinish==0 && count!=1}">
            	<c:if test="${answerSize!=0}">
            	<div class="zjda_tips" onclick="cc.programs.honhBao('${pm.id}')">
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
     <section class="page" id="zhizhao">
        <div class="content" data-toggle="scroller">
            <div class="content-inner J_que_list">
                <article class="common-ctl">
                 <form id="formZhizhao">
                 	<input type="hidden" id="problemId" name="problemId" v-model="problemId" value="${pm.id}">
	 			 	<!-- 悬赏金额 -->
                    <h4 class="cc-common-title">文字回答</h4><!--  name="contentText" -->
                    <div class="answer-text"><textarea rows="4" name="contentText" placeholder="请输入您的答案(不少于20字)" v-model="ans"></textarea></div>
                    <h4 class="cc-common-title">语音回答</h4>
                    <div class="answer-voice">
                        <div class="cc-audio J_paly_voice" hidden><span class="J_time">0s</span> <i class="iconfont">&#xe603;</i> <i class="iconfont J_del_voice">&#xe609;</i></div>
                        <a href="javascript:" class="iconfont J_startRecord" ontouchstart = "return false;">&#xe61f;</a>
                    </div>
                    <h4 class="cc-common-title">添加图片</h4>
                    <div class="answer-imgs">
                        <a href="javascript:" v-for="img in imglist" class="ans-img-item" style="background-image: {{'url('+ img +')'}}"></a>
                        <label href="javascript:" class="ans-img-item J_addimg" @click="addimg($event)"></label>
                        <span class="answer-imgs-tag">最多添加6张</span>
                    </div>
                    <h4 class="cc-common-title">添加视频地址<span style="font-size: 14px;color:#a9a9a9;font-weight:100;">(提供相关视频素材给提问者参考)</span></h4>
                    <div class="common-com">
                        <input type="text" class="cc-input" placeholder="视频地址后缀：mp4或ogg。如：http://.../app.mp4" name="videourl" v-model="videoUrl"><!-- name="contentVideo" -->
                        <label class="label-checkbox com-nim">
                            <!-- <input type="checkbox" name="isSecret" > -->
                             <input type="checkbox" name="nimin" v-model="nimin">
                             <div class="item-media"><i class="icon icon-form-checkbox"></i></div>
                             <div class="item-media">匿名发表</div>
                        </label>
                        <button type="button" id="sub" class="button button-fill button-warning cc-btn">提交答案</button>
                    </div>
                </form>
                </article>
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
