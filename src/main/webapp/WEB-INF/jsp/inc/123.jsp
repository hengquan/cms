<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ page import="com.hj.web.entity.*"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ include file="/WEB-INF/jsp/inc/sessions.jsp"%>

<%
  Object obj1 = request.getSession().getAttribute("adminSession");
  UserInfo userinfo = (UserInfo) obj1;
  userinfo.getId();
%>
<style>
ul.sidebar-menu {
  margin-top: 60px;
}
</style>
<aside>
  <div id="sidebar" class="nav-collapse ">
    <!-- sidebar menu start-->
    <ul class="sidebar-menu">
      <c:forEach items="${lst }" var="info" varStatus="s">
        <c:choose>
          <c:when test="${info.itemUrl==null || info.itemUrl==''}">
            <li class="sub-menu <c:if test="${id==info.itemId}">active</c:if>">
              <a class="" href="javascript:;"> <i class="${info.iconImg}"></i>
                <span>${info.itemName }</span> <span class="arrow"></span>
            </a>
              <ul class="sub">
                <c:forEach items="${itemNamesss}" var="ttt" varStatus="b">
                  <c:if test="${info.itemId==ttt.parentId}">
                    <li class="<c:if test="${itemId==ttt.itemId}">active</c:if>"><a
                      href="${appRoot}${ttt.itemUrl}<c:choose><c:when test="${fn:contains(ttt.itemUrl, '?')}">&</c:when><c:otherwise>?</c:otherwise></c:choose>itemId=${ttt.itemId}&id=${info.itemId}"><i
                        class="${ttt.iconImg}"></i>${ttt.itemName }</a></li>
                  </c:if>
                </c:forEach>
              </ul>
            </li>
          </c:when>
          <c:otherwise>
            <li class="<c:if test="${id==info.itemId}">active</c:if>"><a
              class=""
              href="${appRoot}${info.itemUrl}?itemId=&id=${info.itemId}"> <i
                class="${info.iconImg}"></i> <span>${info.itemName }</span>
            </a></li>
          </c:otherwise>
        </c:choose>
      </c:forEach>
    </ul>
  </div>
</aside>