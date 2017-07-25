<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/taglib.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" />
<title>关键字回复</title>
<link href="${appRoot}/static/wxmanage/css/main.css" rel="stylesheet" type="text/css" />
<script src="${appRoot}/static/wxmanage/js/jquery.js" type="text/javascript"></script>
<script src="${appRoot}/static/wxmanage/js/main.js" type="text/javascript"></script>
</head>


<body>
	<div id="az-body">
		<div class="az-right">
			<table border="0" cellpadding="0" cellspacing="0" class="Mcase">
				<tr>
					<td class="l">
						<div style="padding: 5px 2px 0 6px">
							<form id="searchForm" action="keyword-search.do" method="post">
								<input type="hidden" name="totalPage" id="totalPage" value="<c:out value='${pageUtil.totalPage}'/>" title="总页数"/>
								<input type="hidden" name="currentPage" id="currentPage" value="${pageUtil.currentPage}" class="input_style_60_24" />

								<table border="0" cellpadding="2" cellspacing="0" width="100%">
									<tr>
										<td width="100%"><h1 class="tit">关键字回复 - 列表</h1></td>
										<td>
											<select id="resptype" name="resptype" style="height:25px !important;">
												<option value="">回复类型</option>
												<option value="0" <c:if test="${resptype==0}">selected</c:if> >文字回复</option>
												<option value="1" <c:if test="${resptype==1}">selected</c:if> >单图文回复</option>
												<option value="2" <c:if test="${resptype==2}">selected</c:if> >多图文回复</option>
											</select>
										</td>
										<td>
											<input type="text" name="keyword" id="keyword" value="${keyword}" placeholder="关键字" style="width:165px;height:22px !important;"/>
										</td>
										<td><a href="javascript:void(0)" class="btn" id="btn_search"><i class="view"></i><b>搜索</b></a></td>
										<td>
											<div style="width: 50px"></div>
										</td>
										<td><a href="keyword-insert.do" class="btn"><i class="add"></i><b>添加</b></a></td>
										<td><a href="javascript:void(0)" onclick="javascript:sel()" class="btn"><i class="sel"></i><b>全/反选</b></a></td>
										<td><a href="javascript:void(0)" onclick="javascript:del()" class="btn"><i class="del"></i><b>删除</b></a></td>
									</tr>
								</table>
							</form>
							<form modelAttribute="keyword" action="keyword-del.do" id="delform" method="get">
								<input type="hidden" name="t" value="33" />
								<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tb">
									<tr>
										<th width="30">选择</th>
										<th  style="width: 90px;">关键词</th>
										<th nowrap style="width: 65px;">回复类型</th>
										<th>内容/描述</th>
										<th>操作</th>
									</tr>

									<tbody>
										<c:forEach items="${keywordList}" var="keyword" varStatus="loopCounter">
											<tr class="Mtr">

												<td><input type="checkbox" name="id[]" value="${keyword.id}" style="width: auto;" /></td>
												<td nowrap><c:out value="${keyword.keyword}" /></td>
												<td><c:out value="${keyword.displayType}" /></td>
												<td><c:out value="${keyword.displayDesc}" /></td>

												<td><nobr> <a href="keyword-edit.do?id=${keyword.id}">编辑</a>&nbsp; <a class="pure-button pure-button-primary"
														onclick="return confirm('你确定删除本条记录?');" href="keyword-del.do?id=${keyword.id}"> <i class="fa fa-times"></i>删除
													</a> </nobr></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</form>
							
							<jsp:include page="inc/pagation.jsp" />
						</div>
					</td>
					<td class="r"></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
