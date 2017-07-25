<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>专家列表</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <link href="${appRoot}/static/wx/css/style.css" rel="stylesheet">
    
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body>
<main class="page-group">
    <section class="page page-current" id="specialist">
        <div class="content">
            <div class="content-inner J_que_list">
            <!-- view start -->
                <div class="expert-list">
                	<c:forEach items="${expertMsgs}" var="u" varStatus="s">
	                    <a href="javascript:seeMsg('${u.openid}')" class="expert-item"><img src="${appAccessUrl}${u.expertImg }" alt="专家"></a>
	                    <%-- <a href="specialist-detail.html" class="expert-item"><img src="${appRoot}/static/wx/imgs/expert_2.jpg" alt="专家"></a>
	                    <a href="specialist-detail.html" class="expert-item"><img src="${appRoot}/static/wx/imgs/expert_3.jpg" alt="专家"></a>
	                    <a href="specialist-detail.html" class="expert-item"><img src="${appRoot}/static/wx/imgs/expert_4.jpg" alt="专家"></a> --%>
                	</c:forEach>
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

<script>
	var userInfo={
		openid:'${openid }',
	};
	//查看详情
	function seeMsg(expert){
		var userInfo={
			expertId:'expert'
		};
		window.location.href="${appRoot}/wx/expert/expertMsg.az?expertid="+expert;
	}
	
	$(function(){
		wx.config({
		    debug: false,
		    appId: '${appId}',
		    timestamp:'${timestamp}',
		    nonceStr: '${nonceStr}',
		    signature: '${signature}',
		    jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage']
		});
	});
	
	
	wx.ready(function () {  
	    wx.onMenuShareTimeline({  
	        title: '【专家名片】在线预约专家，轻松解决你的教育困惑', // 分享标题  
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
	        title: '【专家名片】在线预约专家，轻松解决你的教育困惑', // 分享标题  
	        desc: '各位专家擅长领域各有不同，点击查看详情。——教育有惑，上支招客', // 分享描述  
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
<script>
    $(document).on("pageInit", "#specialist", function(e, pageId, $page) {
        cc.programs.specialist();
    });
</script>
</body>
</html>
