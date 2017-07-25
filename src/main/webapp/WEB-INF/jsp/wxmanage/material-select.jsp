<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" />
<title>素材库管理</title>
</head>

<body>
	<div>
		<div >
			<table border="0" cellpadding="0" cellspacing="0" class="Mcase">
				<tr>
					<td class="l">
						<div style="padding: 5px 2px 0 6px">
							<form id="searchForm" action="material-search.do" method="post">
								<input type="hidden" name="t" value="33" />
								<table border="0" cellpadding="2" cellspacing="0" width="100%">
									<tr>
										<td width="100%"><h1 class="tit">素材库-列表</h1></td>
										<td>
											<select id="article_type" name="article_type" style="height:25px !important;">
												<option value="">选择类型</option>
												<c:forEach items="${articleList}" var="articleDic" varStatus="s">
													<c:choose>
														<c:when test="${articleDic.code==article_type}">
															<option value="${articleDic.code}" selected="selected">${articleDic.val}</option>
														</c:when>
														<c:otherwise>
															<option value="${articleDic.code}">${articleDic.val}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
										</td>
										<td>
											<input type="text" name="title" id="title" value="${title}" placeholder="标题" style="width:165px;height:20px !important;"/>
										</td>
										<td><input type="button" id="btn_search" value="搜索" style="cursor:pointer;"/></td>
										<td>
											<div style="width: 50px"></div>
										</td>
									</tr>
								</table>
							</form>
							<form action="material-del.do" id="delform" method="get">
								<input type="hidden" name="t" value="33" />
								<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tb">
									<tr>
										<th style="width:15%;">标题</th>
										<th style="width: 85px;">类型</th>
										<th>描述</th>
										<th style="width:80px;">操作</th>
									</tr>

									<tbody>
										<c:forEach items="${materialList}" var="material" varStatus="loopCounter">
											<tr class="Mtr">
												<td><c:out value="${material.title}" /></td>
												<td><c:out value="${material.article_type}" /></td>
												<td><c:out value="${material.des}" /></td>

												<td><a href="javascript:doSelect('${material.id}')">选择</a></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</form>
						</div>
					</td>
					<td class="r"></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
