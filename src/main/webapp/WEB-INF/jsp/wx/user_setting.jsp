<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>用户中心</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <link href="${appRoot}/static/wx/css/style.css?v=${sversion}" rel="stylesheet">
    <style>
    	.birthMonth{
   		    -webkit-appearance: none;
		    -moz-appearance: none;
		    -ms-appearance: none;
		    appearance: none;
		    box-sizing: border-box;
		    border: none;
		    background: 0 0;
		    border-radius: 0;
		    box-shadow: none;
		    display: block;
		    padding: 0 0 0 .25rem;
		    margin: 0;
		    width: 100%;
		    color: #3d4145;
		    font-size: .85rem;
		    font-family: inherit;
    	}
    	.cc-setting .item-title.label{
    		width:36%;
    	}
    </style>
</head>
<body onload="cc.programs.user()">
<main class="page-group">
    <section class="page page-current" id="userShouyi">
        <nav class="bar bar-tab cc-bar"><a class="tab-item cc-com-btn" href="javascript:" onclick="projectSubmit()" >保存</a></nav>
        <div class="content">
            <div class="content-inner J_que_list">
            <!-- view start -->
                <header class="user-banner min" style="background: #ff8116;">
                    <span class="user-info"><a href="javascript:" class="user-head-min" style="background-image: url(${user.headimgurl})"></a> ${(user.userName == null || user.userName == '') ? user.nickname : user.userName}</span>
                </header>
				<form action="${appRoot}/wx/userinfo/project.az" id="myForm" method="post">
				<input type="hidden" name="openid" value="${user.openid }">
                <div class="list-block cc-setting">
                    <ul>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">真实姓名</div>
                                    <div class="item-input">
                                        <input type="text" placeholder="真实姓名" name="userName" id="userName" value="${user.userName}"> 
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">性别</div>
                                    <div class="item-input">
                                        <select name="sex">
                                            <option value="1" <c:if test="${user.sex==1 }">selected="selected"</c:if>>男</option>
                                            <option value="2" <c:if test="${user.sex==2 }">selected="selected"</c:if>>女</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">所在地</div>
                                    <div class="item-input">
                                       <input type="text" id="address" placeholder="所在地" name="address" value="<c:if test="${user.address!=''}">${user.address}</c:if><c:if test="${user.address==null || user.address==''}">${user.province}${user.city}</c:if>"> 
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">手机号码</div>
                                    <div class="item-input change-num">
                                        
                                        <c:choose>
                                        	<c:when test="${user.mobile!=null &&  user.mobile!=''}">
                                        		<a href="javascript:" class="" id="btnChangePho">更改号码</a>
                                        		<input type="number" id="mobile" placeholder="手机号码" name="mobile" value="${user.mobile }" readonly="readonly">
                                        	</c:when>
                                        	<c:otherwise>
                                        		<input type="number" id="mobile" placeholder="手机号码" name="mobile" value="${user.mobile }">
                                        	</c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </li>
                      	<c:if test="${user.mobile==null ||  user.mobile=='' }">
                      	<li>
                      		<div class="item-content">
                              <div class="item-inner">
                                  <div class="item-title label">验证码</div>
                                  <div class="item-input change-num">
                                     	<a style="width: 4rem;text-decoration: none;" href="javascript:" class="button button-warning cp-a" onclick="cc.programs.user().cPhone(event)">获取验证码</a>
                                      <input type="number" placeholder="验证码" id="code" name="code" value="">
                                  </div>
                              </div>
                            </div>
                         </li>
                      	 </c:if>
                         <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">孩子出生年月</div>
                                    <!--  <div class="item-input">-->
                                        <input class="birthMonth" id="birthday" type="month" placeholder="孩子出生年月" name="birthday" value="${user.birthdayString }">
                                    <!--  </div>-->
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">孩子性别</div>
                                    <div class="item-input">
                                        <select name="childSex" id="childSex">
                                        	<c:if test="${user.childSex==null }">
                                        	<option value=""></option>
                                        	</c:if>
                                            <option value="1" <c:if test="${user.childSex==1 }">selected="selected"</c:if>>男</option>
                                            <option value="2" <c:if test="${user.childSex==2 }">selected="selected"</c:if>>女</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
			</form>
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
	function projectSubmit(){
		if($('#userName').val()==null || $('#userName').val()==''){
			$.alert("真实姓名不能为空！");
			$('#userName').focus();
			return false;
		}
		if($('#address').val()==null || $('#address').val()==''){
			$.alert("所在地不能为空！");
			$('#address').focus();
			return false;
		}
		if($('#mobile').val()==null || $('#mobile').val()==''){
			$.alert("手机号码不能为空！");
			$('#mobile').focus();
			return false;
		}else{
			if(checkMobile($('#mobile').val()) == false){
				$('#mobile').focus();
				return false;
			}
		}
		if($('#code').length > 0){
			if($('#code').val()==null || $('#code').val()==''){
				$.alert("验证码不能为空！");
				$('#code').focus();
				return false;
			}
		}
		
		if($('#birthday').val()==null || $('#birthday').val()==''){
			$.alert("孩子出生年月不能为空！");
			$('#birthday').focus();
			return false;
		}
		
		if($('#childSex ').val()==null || $('#childSex').val()==''){
			$.alert("请选择孩子的性别！");
			$('#childSex').focus();
			return false;
		}
		if($('#code').length > 0){
			$.ajax({
	            url:"../../wx/userinfo/validate",
	            type: 'post',
	            data:{
	          	  mobile:$('#mobile').val(),
	          	  code:$('#code').val()
	            },
	            dataType: 'json',
	            success: function (data) {
	            	if(data.resultCode==1000){
	            		$("#myForm").submit();
	            	}else if(data.resultCode==1001){
	            		$.alert(data.message);
	            		$('#code').focus();
	            	}
	            	
	            },
	            error: function () {
	              $.alert('请求超时');
	            }
	        });
		}else{
			$("#myForm").submit();
		}
		
	} 
	
	
	function checkMobile(str) {
	    var re = /^1\d{10}$/
	    if (!re.test(str)) {
	    	 $.alert("手机号不合法!");
	        return false;
	    }
	}
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
