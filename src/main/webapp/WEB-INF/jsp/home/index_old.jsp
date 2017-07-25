<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ include file="/WEB-INF/jsp/inc/sessions.jsp"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/jsp/inc/head.jsp"%>
<script src="${appRoot}/static/js/main.js"></script>

<title>${appTitle}</title>
<link href="${appRoot}/static/css/admin.css" rel="stylesheet"
	type="text/css" media="screen">
<link href="${appRoot}/static/css/app.css" rel="stylesheet"
	type="text/css" media="screen">
<script>
	function infoDetail(id,tbtype){
		window.location.href="<%=request.getContextPath()%>/info/detail?id="+id+"&tbtype="+tbtype;
	}
	
	function openSearch(){
		window.location.href="<%=request.getContextPath()%>/home/search";
	}
	
	function infosByType(typeId,tbtype){
		window.location.href="<%=request.getContextPath()%>/home/typeSearch?typeId="+ typeId + "&tbtype=" + tbtype;
	}
</script>
</head>
<body style="overflow-y:hidden;">
	<header class="am-topbar admin-header header-bg">
		<div class="logo">
			<img src="${appRoot}/static/img/logo.png">
		</div>

		<button
			class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only"
			data-am-collapse="{target: '#topbar-collapse'}">
			<span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span>

		</button>

		<div class="am-collapse am-topbar-collapse" id="topbar-collapse">

			<ul
				class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list user">

				<li class="quit"><a href="javascript:;"> 退出 </a></li>

				<li class="am-hide-sm-only"><a href="javascript:;"
					id="admin-fullscreen"> <span class="am-icon-arrows-alt"></span>
						<span class="admin-fullText">开启全屏</span>
				</a></li>


				<li><a href="javascript:;"> 修改密码 </a></li>

				<li class="am-dropdown" data-am-dropdown><a
					class="am-dropdown-toggle" data-am-dropdown-toggle
					href="javascript:;"> 用户资料 </a></li>

				<li><a>您好，${userName}</a></li>

				<div class="nav2">
			       <hr class="split-line">
			       <ul>
			           <a class="act"> <li> 商户管理 </li>  </a>         
			           <a> <li> 数据统计 </li>  </a>
			           <a> <li> 数据分析 </li>  </a>
			       </ul>
			    </div>
			</ul>
		</div>
	</header>

	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
			<div class="shrink" id="shrink"></div>
			<div class="am-offcanvas-bar admin-offcanvas-bar sub-nav">
				<ul>
					<li><a> 订单已支付（ <i>11</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>11</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>11</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>11</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>11</i> ）
					</a></li>
				</ul>
				<ul>
					<li><a> 订单已支付（ <i>222</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>222</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>222</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>222</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>222</i> ）
					</a></li>
				</ul>
				<ul>
					<li><a> 订单已支付（ <i>333</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>333</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>333</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>333</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>333</i> ）
					</a></li>
				</ul>
				<ul>
					<li><a> 订单已支付（ <i>44</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>44</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>44</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>44</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>44</i> ）
					</a></li>
				</ul>
				<ul>
					<li><a> 订单已支付（ <i>55</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>55</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>55</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>55</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>55</i> ）
					</a></li>
				</ul>
				<ul>
					<li><a> 订单已支付（ <i>66</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>66</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>66</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>66</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>66</i> ）
					</a></li>
				</ul>
				<ul>
					<li><a> 订单已支付（ <i>77</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>77</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>77</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>77</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>77</i> ）
					</a></li>
				</ul>
				<ul>
					<li><a> 订单已支付（ <i>88</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>88</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>88</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>88</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>88</i> ）
					</a></li>
				</ul>
				<ul>
					<li><a> 订单已支付（ <i>999</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>999</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>999</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>999</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>999</i> ）
					</a></li>
				</ul>
				<ul>
					<li><a> 订单已支付（ <i>1010</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>1010</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>1010</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>1010</i> ）
					</a></li>
					<li><a> 订单已支付（ <i>1010</i> ）
					</a></li>
				</ul>
			</div>
		</div>
		<!-- sidebar end -->

		<!-- <div class="admin-content content-bg">
			<div class="am-panel am-panel-default">

				<div class="am-panel-bd am-collapse am-in am-cf"
					id="collapse-panel-3"></div>
			</div>
		</div> -->
		
		
		<!-- content start -->
	    <div class="admin-content">
	        <div class="am-g">
	            <div class="am-u-sm-12">
	                <form class="am-form">
	                    <table class="am-table am-table-striped am-table-hover table-main">
	                        <thead>
	                        <tr class="table-list">
	                            <th class="table-check"> <input class="all" type="checkbox" /> </th>
	                            <th class="table-img">菜品图片</th>
	                            <th class="table-title">菜品名称</th>
	                            <th class="table-type">菜品编码</th>
	                            <th class="table-author am-hide-sm-only">菜品价格</th>
	                            <th class="table-date am-hide-sm-only">活动折扣</th>
	                            <th class="table-set">操作</th>
	                        </tr>
	                        </thead>
	
	
	                        <tr class="table-text">
	                            <td><input class="check" type="checkbox" /></td>
	                            <td class="upload-img"> <img src="assets/img/img1.jpg"></td>
	                            <td class="dishes"><a href="#">Business management</a></td>
	                            <td class="coding">default</td>
	                            <td class="price">299.99元</td>
	                            <td class="activity">2014年9月4日 7:28:47</td>
	                            <td>
	                                <div class="am-btn-toolbar">
	                                    <div class="am-btn-group">
	                                        <button class="am-btn am-btn-default editor"><span class="am-icon-pencil-square-o"></span> 编辑</button>
	
	                                        <button class="am-btn am-btn-default clear"><span class="am-icon-trash-o"></span> 删除</button>
	                                    </div>
	                                </div>
	                            </td>
	                        </tr>
	
	
	                        <tr class="table-text">
	                            <td><input class="check" type="checkbox" /></td>
	                            <td class="upload-img"> <img src="assets/img/img1.jpg"></td>
	                            <td class="dishes"><a href="#">Business management</a></td>
	                            <td class="coding">default</td>
	                            <td class="price">299.99元</td>
	                            <td class="activity">2014年9月4日 7:28:47</td>
	                            <td>
	                                <div class="am-btn-toolbar">
	                                    <div class="am-btn-group">
	                                        <button class="am-btn am-btn-default editor"><span class="am-icon-pencil-square-o"></span> 编辑</button>
	
	                                        <button class="am-btn am-btn-default clear"><span class="am-icon-trash-o"></span> 删除</button>
	                                    </div>
	                                </div>
	                            </td>
	                        </tr>
	
	                        <tr class="table-function">
	                            <td> <button  class="increase"> <img src="${appRoot}/static/img/increase.png"> 增加</button> </td>
	                            <td> <button  class="delete"> <img src="${appRoot}/static/img/delete.png"> 删除所选菜品</button> </td>
	                            <td>  </td>
	                            <td>  </td>
	                            <td>  </td>
	                            <td>  </td>
	                            <td>  </td>
	                        </tr>
	
	                    </table>
	
	                </form>
	            </div>
	
	        </div>
	    </div>
	    <!-- content end -->
		
	</div>


	<a href="#" class="am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"> <span
		class="am-icon-btn am-icon-th-list"></span>
	</a>

</body>
</html>