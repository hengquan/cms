<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--sidebar start-->
<aside>
    <div id="sidebar"  class="nav-collapse ">
        <!-- sidebar menu start-->
        <ul class="sidebar-menu">
            <li class="<c:if test="${activeFlag=='home'}">active</c:if>">
                <a class="" href="${appRoot}/home/index?activeFlag=home">
                    <i class="icon-home"></i>
                    <span>首页</span>
                </a>
            </li>
            <li class="sub-menu <c:if test="${activeFlag=='order'}">active</c:if>">
                <a href="javascript:;" class="">
                    <i class="icon-book"></i>
                    <span>订单管理</span>
                    <span class="arrow"></span>
                </a>
                <ul class="sub">
                    <li class="<c:if test="${subActiveFlag=='takeout'}">active</c:if>"><a class="" href="${appRoot}/order/list?orderSource=01">外卖订单</a></li>
                    <li class="<c:if test="${subActiveFlag=='torestaurant'}">active</c:if>"><a class="" href="${appRoot}/order/list?orderSource=02">扫码订单</a></li>
                </ul>
            </li>
            <li class="<c:if test="${activeFlag=='businessSetting'}">active</c:if>">
                <a class="" href="${appRoot}/business/setting">
                    <i class="icon-shopping-cart"></i>
                    <span>商户设置</span>
                </a>
            </li>
            <li class="<c:if test="${activeFlag=='diningtable'}">active</c:if>">
                <a class="" href="${appRoot}/diningtable/index">
                    <i class="icon-food"></i>
                    <span>餐桌管理</span>
                </a>
            </li>
            <li class="sub-menu <c:if test="${activeFlag=='dishes'}">active</c:if>">
                <a href="javascript:;" class="">
                    <i class="icon-coffee"></i>
                    <span>菜品设置</span>
                    <span class="arrow"></span>
                </a>
                <ul class="sub">
                    <li class="<c:if test="${subActiveFlag=='dishesType'}">active</c:if>"><a class="" href="${appRoot}/dishes/type?activeFlag=dishes&subActiveFlag=dishesType">菜品类型</a></li>
                    <li class="<c:if test="${subActiveFlag=='dishesInfo'}">active</c:if>"><a class="" href="${appRoot}/dishes/info?activeFlag=dishes&subActiveFlag=dishesInfo">菜品信息</a></li>
                </ul>
            </li>
            <!-- <li class="">
                <a class="" href="index.html">
                    <i class="icon-money"></i>
                    <span>支付设置</span>
                </a>
            </li> -->
            <li class="<c:if test="${activeFlag=='printer'}">active</c:if>">
                <a class="" href="${appRoot}/printer/list?activeFlag=printer">
                    <i class="icon-print"></i>
                    <span>打印设置</span>
                </a>
            </li>
            <li class="<c:if test="${activeFlag=='activity'}">active</c:if>">
				<a class="" href="${appRoot}/activity/list?activeFlag=activity"> 
					<i class="icon-group"></i> <span>活动管理</span>
				</a>
			</li>
			<li class="<c:if test="${activeFlag=='announcement'}">active</c:if>">
				<a class="" href="${appRoot}/announcement/list?activeFlag=announcement"> 
					<i class="icon-group"></i> <span>系统公告</span>
				</a>
			</li>
			<li class="sub-menu <c:if test="${activeFlag=='accounts'}">active</c:if>">
                <a href="javascript:;" class="">
                    <i class="icon-book"></i>
                    <span>结账管理</span>
                    <span class="arrow"></span>
                </a>
                <ul class="sub">
                    <li class="<c:if test="${subActiveFlag=='apply'}">active</c:if>"><a class="" href="${appRoot}/business_accounts/list?activeFlag=statistic">结账申请</a></li>
                    <li class="<c:if test="${subActiveFlag=='complate'}">active</c:if>"><a class="" href="${appRoot}/business_accounts/finishlist?activeFlag=statistic">已完成</a></li>
                </ul>
            </li>
            <li class="sub-menu <c:if test="${activeFlag=='statistic'}">active</c:if>">
                <a href="javascript:;" class="">
                    <i class="icon-th"></i>
                    <span>数据统计</span>
                    <span class="arrow"></span>
                </a>
                <ul class="sub">
                    <li class="<c:if test="${subActiveFlag=='orderStatistics'}">active</c:if>"><a class="" href="${appRoot}/data/orderStatistics?activeFlag=statistic">订单统计</a></li>
                    <li class="<c:if test="${subActiveFlag=='capital'}">active</c:if>"><a class="" href="${appRoot}/data/capitalStatistics?activeFlag=statistic">资金统计</a></li>
                    <li class="<c:if test="${subActiveFlag=='score'}">active</c:if>"><a class="" href="${appRoot}/data/scoreStatistics?activeFlag=statistic">积分统计</a></li>
                </ul>
            </li>
           <li class="<c:if test="${activeFlag=='analysis'}">active</c:if>">
				<a class="" href="${appRoot}/analyze/analyze_business?activeFlag=analysis"> 
					<i class="icon-bar-chart"></i> <span>数据分析</span>
				</a>
			</li>
            <!-- <li class="">
                <a class="" href="index.html">
                    <i class="icon-user-md"></i>
                    <span>用户管理</span>
                </a>
            </li> -->
        </ul>
        <!-- sidebar menu end-->
    </div>
</aside>

<!--sidebar end-->