<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="appRoot" value="${pageContext.request.contextPath}"/>
<c:set var="appTitle" value="<%=com.hj.wxmp.mobile.common.Configurations.getAppTitle()%>"/>
<c:set var="appAccessUrl" value="<%=com.hj.wxmp.mobile.common.Configurations.getAccessUrl()%>"/>
<c:set var="cdnSite" value="<%=com.hj.wxmp.mobile.common.Configurations.getCdnSite()%>"/>
<c:set var="sversion" value="<%=com.hj.wxmp.mobile.common.Configurations.getSVersion()%>"/>