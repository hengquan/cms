<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>我的二维码</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <link href="${appRoot}/static/wx/css/style.css?v=${sversion}" rel="stylesheet">
    
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body>
<main class="page-group">
    <section class="page page-current">
        <div class="content">
            <div class="content-inner J_que_list">
            <!-- view start -->
                <header class="user-banner min" style="background: #ff8116;">
                    <span class="user-info"><a href="javascript:" class="user-head-min" style="background-image: url(${user.headimgurl})"></a>
					<c:choose>
						<c:when test="${user.userName== '' || user.userName== undefined}">  
							${user.nickname}									   	
						</c:when>
						<c:otherwise> 
							${user.userName}	
						</c:otherwise>
					</c:choose>
					</span>
                </header>
                <!-- 二维码 -->
				<img src="${appAccessUrl}${user.qrCodeAddress}" style="width:60%;margin-left:20%;margin-top:20%;">
            <!-- view end -->
            </div>
        </div>
    </section>
</main>
<%-- <!-- ticket -->
<input type="hidden" id="ticket" value="${ticket }">
<!-- 二维码图片路径 -->
<input type="hidden" id="qrcodeImg" value="${user.qrCodeAddress }"> --%>
<script type="text/javascript">
	var userInfo={
		openid:'${openid}',
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
<script type="text/javascript">
/* 	$(function(){
		var qrcodeImg = $("#qrcodeImg").val();
		var ticket = $("#ticket").val();
		
		//alert(ticket);
		window.location.href="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket;
		
	}); */
	
</script>

<script>
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
	        title: '教育有困惑，就上支招客', // 分享标题  
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
	        title: '教育有困惑，就上支招客', // 分享标题  
	        desc: '我的困惑已经得到解答，好东西要分享，希望它也能对你有帮助！', // 分享描述  
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
</html>
