<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>我的时间</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <link href="${appRoot}/static/wx/css/style.css" rel="stylesheet">
</head>
<body onload="cc.programs.userTimes()">
<main class="page-group">
    <section class="page page-current" id="userTimes">
        <nav class="bar bar-tab cc-bar"><a class="tab-item cc-com-btn" href="#yuyue" onclick = "cc.programs.sendDate()">我的预约单</a></nav>
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
                <chooser-date :date="date" :times="times" sel="true"></chooser-date>

            <!-- view end -->
            </div>
        </div>
    </section>
    <section class="page" id="yuyue">
        <div class="content">
            <div class="content-inner J_que_list">
            <!-- view start -->
            <%-- <c:forEach items="${makeMsg}" var="u" varStatus="s">
                <div class="cc-u-item" onclick="window.location.href ='${appRoot}/wx/expert/userMsg.az?openid=${u.openid}&&orderSn=${u.order_sn }'">
                    <div class="cc-u-il">
                        <a href="javascript:" class="user-head-min" style="background-image: url(${makeMsg[0].headimgurl})"></a>
                        <p>
                        <c:choose>
							<c:when test="${u.experUserName== '' || u.experUserName== undefined}">  
								${u.expertNickname}									   	
							</c:when>
							<c:otherwise> 
								${u.experUserName}	
							</c:otherwise>
						</c:choose>
						</p>
                    </div>
                    <div class="cc-u-ir">
                        <h4 class="cc-u-inhead">${u.s_time }</h4>
                        <div class="cc-u-inbody">
                            <span>${u.sumPrice } <small>元</small></span>
                           ${u.typeName }
                        </div>
                    </div>
                </div>
           	</c:forEach> --%>
            <!-- view end -->
            </div>
        </div>
    </section>
</main>
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
<script src="${appRoot}/static/wx/js/main.js"></script>
<script type="text/javascript">
$(function(){
	window.onpopstate = function(){
		location.reload();
	}
})
$(document).on('pageInit',function(){
    //window.location.reload();
})

function getMessage(obj){
	
	//var msg = $(obj);
	var openid = obj.data().openid;
	var ordersn = obj.data().ordersn;
	console.log(obj)
	  window.location.href ="${appRoot}/wx/expert/userMsg.az?openid=" + openid + "&orderSn=" + ordersn+ "&userRole=user";
	 
}

</script>
</body>
</html>
