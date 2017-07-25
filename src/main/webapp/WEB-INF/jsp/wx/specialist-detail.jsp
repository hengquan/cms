<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>专家详情</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    
    <link href="${appRoot}/static/wx/css/style.css" rel="stylesheet">
    <style type="text/css">
    .cc-common-title{
            font-size: 18px;
        }
    	
    	.zx_info{
    		width:100%;
    		padding:10px;
    		border:0;
    		font-size:14px;
    	}
    	 .yuyue_tips{
            font-size: 12px;
            text-align: right;
            margin-right: 10px;
            margin-top: -30px;
        }
        .yuyue_tips i{
            display: inline-block;
            width:35px;
            height:10px;
            margin-left: 5px;
        }
        .yuyue_tips .no_yueyue{
            color:#e3e3e3;
        }
        .yuyue_tips .no_yueyue i{
           background-color: #e3e3e3;
        }
        .yuyue_tips .yi_yueyue{
            color:#808000;
        }
        .yuyue_tips .yi_yueyue i{
           background-color: #808000;
        }
        .yuyue_tips .ke_yueyue{
            color:#ff8116;
        }
        .yuyue_tips .ke_yueyue i{
           background-color: #ff8116;
        }
        .chooser-date .cho-item .yiyuyue{
            border-color:#808000 !important;
            color:#808000 !important;
        }
        #agePicker{
            margin: 10px;
            width:20%;
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
        .spe-inner{
        	border:0;
        }
        .zxType {
            padding-left: 22px;
            margin-left: 20px;
            font-size: .8rem;
           line-height: 50px;
        }

        .zxType1 {
            background: url("../../static/wx/imgs/dianhua.png") left 16px/6% no-repeat;
        }

        .zxType2 {
            background: url("../../static/wx/imgs/shipin.png") left 16px/6% no-repeat;
        }

        .zxType3 {
            background: url("../../static/wx/imgs/mianduimian.png") left 16px/6% no-repeat;
        }
    </style>
</head>

<body onload="cc.programs.specialist()">
<main class="page-group">
    <section class="page page-current" id="specialist">
        <nav class="bar bar-tab cc-bar"><a class="tab-item cc-com-btn" id="expertyuyue" onclick="expertyuyue()">预约</a></nav>
        <div class="content">
            <div class="content-inner J_que_list">
            <!-- view start -->
                <div class="expert-list">
                	<a href="javascript:" class="expert-item"><img src="${appAccessUrl}${user.expertDetailsImg}" alt="专家"></a>
                </div>
                <h4 class="tit-con">
                	 <c:choose>
						<c:when test="${expertMsg[0].userName== '' || expertMsg[0].userName== undefined}">  
							专家：${expertMsg[0].nickname }	
						</c:when>
						<c:otherwise> 
							专家：${expertMsg[0].userName}									   	
						</c:otherwise>
					</c:choose>
                </h4>
                <article class="spe-content">
                    <h4 class="cc-common-title">价格<small class="gray">(每次60分钟)</small></h4>
	                    <div class="spe-row">
	                    <c:if test="${empty user.favorableName}">
		                    <c:forEach items="${expertMsg}" var="u" varStatus="s">
		                    	<c:if test="${u.expert_type=='电话' }">
			                        <div class="zxType zxType1">
			                                	电话咨询：<span class="theme">${u.price }</span>元/次
			                        </div>
								</c:if>
								<c:if test="${u.expert_type=='视频'}">
			                        <div class="zxType zxType2">
			                                	视频咨询：<span class="theme">${u.price }</span>元/次
			                        </div>
			                     </c:if>
			                     <c:if test="${u.expert_type=='面谈' }">   
			                        <div class="zxType zxType3">
			                                	面对面咨询：<span class="theme">${u.price }</span>元/次
			                        </div>
		                        </c:if>
			               	</c:forEach>
	                    </c:if>
	                    <c:if test="${!empty user.favorableName}">
	                    	<c:forEach items="${expertMsg}" var="u" varStatus="s">
		                    	<c:if test="${u.expert_type=='电话' }">
			                            <div class="zxType zxType1">
			                                 	电话咨询<span class="theme">${u.favorable_price }</span>元/次 <span class="theme">${user.favorableName}：${u.price }</span>元/次
			                            </div>
								</c:if>
								<c:if test="${u.expert_type=='视频'}">
			                            <div class="zxType zxType2">
			                                	视频咨询<span class="theme">${u.favorable_price }</span>元/次 <span class="theme">${user.favorableName}：${u.price }</span>元/次
			                            </div>
			                     </c:if>
			                     <c:if test="${u.expert_type=='面谈' }">
			                            <div class="zxType zxType3">
			                                	面对面咨询<span class="theme">${u.favorable_price }</span>元/次 <span class="theme">${user.favorableName}：${u.price }</span>元/次
			                            </div>
		                        </c:if>
			               	</c:forEach>
	                    </c:if>
	                    
	                    </div>
                    <h4 class="cc-common-title">专家简介</h4>
                    <p class="spe-text">
                        ${expertMsg[0].expert_description }
                    </p>
                    <div class="expert-list">
                        <c:forEach items="${attachments}" var="u" varStatus="s">
                        	<a href="javascript:" class="expert-item"><img src="${appAccessUrl}${u.filename}" alt="专家"  style="width: 33.3%;padding: 2px;"></a>
                        </c:forEach>
                    </div>
                </article>
            <!-- view end -->
            </div>
        </div>
    </section>
    <section class="page" id="make">
        <div class="content">
            <div class="content-inner J_que_list" data-type="js">
                <!-- view start -->
                <header class="user-banner min" style="background-image: url(${appRoot}/static/wx/imgs/bg3.jpg)">
                    <span class="user-info spe"><a href="javascript:" class="user-head-min" style="background-image: url(${user.headimgurl})"></a>
                    <c:choose>
						<c:when test="${user.userName== '' || user.userName== undefined}">  
							${user.nickname}									   	
						</c:when>
						<c:otherwise> 
							${user.userName }	
						</c:otherwise>
					</c:choose>
                    </span>
                </header>
                <h4 class="cc-common-title">选择预约时间</h4>
                <form id="formContent">
                    <chooser-date :date="date" :times="times"></chooser-date>
                    <div class="yuyue_tips">
                        <span class="no_yueyue"><i></i>不可预约</span>
                        <span class="yi_yueyue"><i></i>已预约</span>
                        <span class="ke_yueyue"><i></i>可预约</span>
                    </div>
                    <div class="content-xw">
                        <h4 class="cc-common-title">咨询方式 <small class="gray">(每次60分钟)</small></h4>
                        <div class="spe-row">
                        	<c:if test="${empty user.favorableName}">
		                        <c:forEach items="${expertMsg}" var="u" varStatus="s">
			                    	<c:if test="${u.expert_type=='电话' }">
				                        <div class="spe-item">
				                            <div class="spe-inner spe-bg">
				                                <h4>电话咨询</h4>
				                                <p><span class="theme">${u.price }</span>元/次</p>
				                            </div>
				                        </div>
									</c:if>
									<c:if test="${u.expert_type=='视频'}">
				                        <div class="spe-item">
				                            <div class="spe-inner spe-bg">
				                                <h4>视频咨询</h4>
				                                <p><span class="theme">${u.price }</span>元/次</p>
				                            </div>
				                        </div>
				                     </c:if>
				                     <c:if test="${u.expert_type=='面谈' }">   
				                        <div class="spe-item">
				                            <div class="spe-inner spe-bg">
				                                <h4>面对面咨询</h4>
				                                <p><span class="theme">${u.price }</span>元/次</p>
				                            </div>
				                        </div>
			                        </c:if>
				               	</c:forEach>
                        	</c:if>
                        	<c:if test="${!empty user.favorableName}">
                        		<c:forEach items="${expertMsg}" var="u" varStatus="s">
			                    	<c:if test="${u.expert_type=='电话' }">
				                        <div class="spe-item">
				                            <div class="spe-inner spe-bg">
				                                <h4>电话咨询</h4>
				                                <p><span class="theme">${u.favorable_price }</span>元/次</p>
				                            </div>
				                            <p style="font-size:12px;"><span class="theme">${user.favorableName}:${u.price }</span>元/次</p>
				                        </div>
									</c:if>
									<c:if test="${u.expert_type=='视频'}">
				                        <div class="spe-item">
				                            <div class="spe-inner spe-bg">
				                                <h4>视频咨询</h4>
				                                <p><span class="theme">${u.favorable_price }</span>元/次</p>
				                            </div>
				                            <p style="font-size:12px;"><span class="theme">${user.favorableName}:${u.price }</span>元/次</p>
				                        </div>
				                     </c:if>
				                     <c:if test="${u.expert_type=='面谈' }">   
				                        <div class="spe-item">
				                            <div class="spe-inner spe-bg">
				                                <h4>面对面咨询</h4>
				                                <p><span class="theme">${u.favorable_price }</span>元/次</p>
				                            </div>
				                            <p style="font-size:12px;"><span class="theme">${user.favorableName}:${u.price }</span>元/次</p>
				                        </div>
			                        </c:if>
				               	</c:forEach>
                        	</c:if>
                        </div>
                        
                        <input type="hidden" value="${userMessage.mobile }" name="phone" id="userPhone">
                         
                        <h4 class="cc-common-title">孩子年龄</h4>
                        <div class="answer-text">
				            <input type="text" class="zx_info" readonly id='agePicker' placeholder="请选择" value="${userMessage.childAge==0 ? '' : userMessage.childAge}岁" name="age" />
						</div>
						<h4 class="cc-common-title">孩子性别(男/女)</h4>
						<div class="answer-text">
							<select name="sex" style="margin:10px;width:20%;" class="zx_info">
				                <option value="男" <c:if test="${userMessage.childSex==0 }">selected="selected"</c:if>>男</option>
				                <option value="女" <c:if test="${userMessage.childSex==1 }">selected="selected"</c:if>>女</option>
				            </select>
						</div>
						<h4 class="cc-common-title">所在地</h4>
						<div class="answer-text">
							<input type="text" class="zx_info" placeholder="请输入您孩子的所在地" name="address" value="${userMessage.address }">
						</div>
                        
                        
                        <h4 class="cc-common-title">咨询内容描述</h4>
                        <div class="answer-text"><textarea rows="4" placeholder="如：我家孩子是7岁的男孩，今年上二年级，吃饭偏食的现象很严重，只爱吃肉，青菜不动筷子，跟他碗里夹青菜他也不吃，请问专家我们父母该如何去引导孩子改掉偏食的坏习惯" name="ans" v-model="ans"></textarea></div>
                        <div class="common-com">
                            <label class="label-checkbox com-nim">
                                <input type="checkbox" name="tonyi" v-model="tonyi" value="tonyi">
                                <div class="item-media"><i class="icon icon-form-checkbox"></i></div>
                                <div class="item-media">同意<a href="#" onclick="seeTreaty()" class="theme">《支招客咨询协议》</a></div>
                            </label>
                            <button type="button" class="button button-fill button-warning cc-btn" id="sub">完成</button>
                        </div>
                    </div>
                </form>
                <!-- view end -->
            </div>
        </div>
    </section>
</main>
<script type="text/javascript">
	var userInfo={
		expertId:'${expertId}',
		openId:'${openid}'
	};
	
	//查看协议
	function seeTreaty(){
		window.location.href="${appRoot}/wx/expert/userTreaty.az";
	}
	
	
	
	
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
		wx.config({
		    debug: false,
		    appId: '${appId}',
		    timestamp:'${timestamp}',
		    nonceStr: '${nonceStr}',
		    signature: '${signature}',
		    jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage']
		});
	});
	
	
	function expertyuyue(){
		if(!$('#userPhone').val()){
			$.alert('请完善个人信息资料！',function(){
				window.location.href="../../wx/userinfo/projectuser.az";
			});
			return;
		}else{
			$("#expertyuyue").prop("href","#make").click();
		}
	}
	
	
	
	wx.ready(function () {  
	    wx.onMenuShareTimeline({  
	        title: '${user.expertShareTitle}', // 分享标题  
	        link: '${shareUrl}', // 分享链接  
	        imgUrl: '${appAccessUrl}${user.expertShareImg}', // 分享图标  
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
	        title: '${user.expertShareTitle}', // 分享标题  
	        desc: '${expertMsg[0].expert_description}', // 分享描述  
	        link: '${shareUrl}', // 分享链接  
	        imgUrl: '${appAccessUrl}${user.expertShareImg}', // 分享图标  
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
