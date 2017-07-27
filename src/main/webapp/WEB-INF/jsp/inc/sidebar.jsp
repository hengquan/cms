<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.hj.wxmp.mobile.entity.*" %>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ include file="/WEB-INF/jsp/inc/sessions.jsp"%>
	
	<%
	Object obj1 = request.getSession().getAttribute("adminSession");
	UserInfo userinfo = (UserInfo)obj1;  
	userinfo.getId();
	%>
<style>
ul.sidebar-menu{
	margin-top: 60px;
}
</style>
<aside>
	<div id="sidebar" class="nav-collapse ">
		<!-- sidebar menu start-->
		<ul class="sidebar-menu">
			<c:forEach items="${lst }"  var="info" varStatus="s">
				<c:choose>
					<c:when test="${info.itemUrl==null || info.itemUrl==''}">
						<li class="sub-menu <c:if test="${id==info.itemId}">active</c:if>">
							<a class="" href="javascript:;"> 
							<i class="${info.iconImg}"></i> <span>${info.itemName }</span>
							<span class="arrow"></span>
							</a>
							<ul class="sub">
                				<c:forEach items="${itemNamesss}"  var="ttt" varStatus="b">
                				<c:if test="${info.itemId==ttt.parentId}">
                    				<li class="<c:if test="${itemId==ttt.itemId}">active</c:if>"><a href="${appRoot}${ttt.itemUrl}<c:choose><c:when test="${fn:contains(ttt.itemUrl, '?')}">&</c:when><c:otherwise>?</c:otherwise></c:choose>itemId=${ttt.itemId}&id=${info.itemId}"><i class="${ttt.iconImg}"></i>${ttt.itemName }</a></li>
                    			</c:if>
                    			</c:forEach>
               				</ul>
               				
						</li>
					</c:when>
					<c:otherwise>
						<li class="<c:if test="${id==info.itemId}">active</c:if>">
							<a class="" href="${appRoot}${info.itemUrl}?itemId=&id=${info.itemId}"> 
								<i class="${info.iconImg}"></i> <span>${info.itemName }</span>
							</a>
						</li>	
					</c:otherwise>
				</c:choose>
				
			</c:forEach>	
		
		
			<%-- <li class="sub-menu <c:if test="${activeFlag=='system'}">active</c:if>">
                <a href="javascript:;" class="">
                    <i class="icon-book"></i>
                    <span>系统管理</span>
                    <span class="arrow"></span>
                </a>
                <ul class="sub">
                    <li class="<c:if test="${subactiveFlag=='usermanager'}">active</c:if>"><a href="${appRoot}/admin/list">用户管理</a></li>
                    <li class="<c:if test="${subActiveFlag=='roleManager'}">active</c:if>"><a class="" href="${appRoot}/role/list">角色管理</a></li>
                    <li class="<c:if test="${subActiveFlag=='itemManager'}">active</c:if>"><a class="" href="${appRoot}/item/list">菜单管理</a></li>
                </ul>
            </li>
            
			<li class="sub-menu <c:if test="${activeFlag=='system'}">active</c:if>">
                <a href="javascript:;" class="">
                    <i class="icon-book"></i>
                    <span>用户管理</span>
                    <span class="arrow"></span>
                </a>
                <ul class="sub">
                    <li class="<c:if test="${subactiveFlag=='usermanager'}">active</c:if>"><a href="${appRoot}/user/check">审核管理</a></li>
                </ul>
            </li>
            
            
            <li class="sub-menu <c:if test="${activeFlag=='system'}">active</c:if>">
                <a href="javascript:;" class="">
                    <i class="icon-book"></i>
                    <span>问题管理</span>
                    <span class="arrow"></span>
                </a>
                <ul class="sub">
                    <li class="<c:if test="${subactiveFlag=='usermanager'}">active</c:if>"><a href="${appRoot}/problem/list">问题列表</a></li>
                </ul>
            </li> --%> 
			
		</ul>
	</div>
</aside>