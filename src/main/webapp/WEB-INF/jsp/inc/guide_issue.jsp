<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<script type="text/javascript">
	function home_cust(){
		window.location.href="<%=request.getContextPath() %>/home/index";
	}
	
	function doIssue(){
		window.location.href="<%=request.getContextPath() %>/issue/index";
	}
	
	function myIndex(){
		window.location.href="<%=request.getContextPath() %>/my/index";
	}
	
</script>
<style type="text/css">
  	.wxmp-logo{background-color:#f2f2f1;}
  	.wxmp-b{color:#919191;}
</style>
</head>
	<!-- Navbar -->
	<div data-am-widget="navbar" class="am-navbar am-cf wxmp-logo" id="">
		<ul class="am-navbar-nav am-cf am-avg-sm-4">
			<li>
				<a class="wxmp-b" href="javascript:home_cust()"> <span class="am-icon-home"></span> <span class="am-navbar-label">首页</span>
				</a>
			</li>
			<li>
				<a class="wxmp-b" href="javascript:doIssue()" style="color:#10a0ea;"> <span class="am-icon-comment"></span> <span class="am-navbar-label">发布</span>
				</a>
			</li>
			<li>
				<a class="wxmp-b"  href="javascript:myIndex()"> <span class="am-icon-user"></span> <span class="am-navbar-label">我的</span>
				</a>
			</li>
		</ul>
	</div>
</html>