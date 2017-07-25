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
<body>
<main class="page-group">
    <section class="page page-current">
        <div class="content">
        <c:if test="${makeMsg[0].order_state==0 }">
	        <div id='bottomDiv' style='postion:relative;bottom:0px;margin:0px;float:left;width:100%;'>
		        <nav class="bar bar-tab cc-bar"><a class="tab-item cc-com-btn" href="${appRoot}/pay/getPrepayId?orderSn=${orderSn }&totalFee=${totalFee}">去支付</a></nav>
	        </div>
        </c:if>
       	<input type="hidden" id="time" value="${dateTime }">
        <c:if test="${makeMsg[0].order_state==1 && userTypeNumber==2}">
	        <div id='dateTimeMsg' style='postion:relative;bottom:0px;margin:0px;float:left;width:100%;'>
		        <nav class="bar bar-tab cc-bar"><a class="tab-item cc-com-btn" href="#" onclick="affirmFinish('${makeMsg[0].order_sn }','${openid}');">确认完成</a></nav>
	        </div>
        </c:if>
            <div class="content-inner J_que_list">
            <!-- view start -->
                <header class="user-banner min" style="background: #ff8116;">
                    <span class="user-info"><a href="javascript:" class="user-head-min" style="background-image: url(${makeMsg[0].headimgurl})"></a>
					<c:choose>
						<c:when test="${makeMsg[0].experUserName== '' || makeMsg[0].experUserName== undefined}">  
							${makeMsg[0].expertNickname}									   	
						</c:when>
						<c:otherwise> 
							${makeMsg[0].experUserName}	
						</c:otherwise>
					</c:choose>
					</span>
                </header>
                <h4 class="cc-common-title">预约时间</h4>
                <ul class="cc-list-con">
                	<c:forEach items="${makeMsg}" var="u" varStatus="s">
                    <li>
                        <span class="cc-li-l">${u.s_time}</span>
                        <span class="cc-li-r"><span class="gray">${u.type_name}</span>${u.price}<small>元</small></span>
                    </li>
                   </c:forEach>
                </ul>
                <c:if test="${!empty orderSn}">
	                <h4 class="cc-common-title">订单号</h4>
	                <p class="cc-txt">${orderSn}</p>
                </c:if>
                <c:if test="${!empty makeMsg[0].pay_time}">
	                <h4 class="cc-common-title">支付时间</h4>
	                <p class="cc-txt">${makeMsg[0].pay_time}</p>
                </c:if>
                <h4 class="cc-common-title">咨询内容</h4>
                <p class="cc-txt">${makeMsg[0].expert_content}</p>
            <!-- view end -->
            </div>
        </div>
    </section>
</main>
<script type="text/javascript">
function affirmFinish(orderid,openid){
	//alert(orderid);
	window.location.href="${appRoot}/wx/expert/affirmFinish.az?orderid="+orderid+"&openid="+openid;
}
</script>
<script src="${appRoot}/static/wx/js/io.js"></script>
<script src="${appRoot}/static/wx/lib/vue.min.js"></script>
<script src="${appRoot}/static/wx/lib/vue-animated-list.js"></script>
<script src="${appRoot}/static/wx/lib/zepto.min.js"></script>
<script src="${appRoot}/static/wx/lib/sm.min.js"></script>
<script src="${appRoot}/static/wx/lib/sm-extend.min.js"></script>
<script src="${appRoot}/static/wx/js/main.js"></script>
<script>
$(function(){
	$("#dateTimeMsg").hide();
	var time = $("#time").val();
	time = time.split(" ");
	var day = time[0];
	var hour = time[1];
	hour = hour.split("-")[1];
	//拼时间
	var data = day+" "+hour;
	var endLogTimeDate = new Date(Date.parse(data.replace(/-/g, "/")));  
	//当前时间
	var today=new Date();
	console.log(Date.parse(today) - Date.parse(endLogTimeDate));
	
	if((Date.parse(today) - Date.parse(endLogTimeDate))>0)   
    {   
		$("#dateTimeMsg").show();
    }   
});
</script>
</body>
</html>
