<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../inc/taglib.jsp"%>
<%-- <link href="${appRoot}/sysmanage/css/table.css" rel="stylesheet" /> --%>
<script src="${appRoot}/static/wxmanage/js/pagation.js" type="text/javascript"></script>

<c:if test="${pageUtil.totalPage >1}">
	<div class="JumpPage">
		<table style="width: 100%" class="tableCSS3" id="JumpPage">
			<tr>
				<td width="100" align="center">共${pageUtil.record}条记录，</td>
				<%-- <td width="100" align="center">每页显示</td>
				<td width="80" align="center"><div id="fudong">
						<div class="left_60_24">
							<select name="count" class="select_style_60_24" id="count" onchange="javascript:countChangeEvent();">
								<option value="-1">请选择</option>
								<option value="10" <c:if test="${pageUtil.count == 10 }">selected</c:if>>10</option>
								<option value="20" <c:if test="${pageUtil.count == 20 }">selected</c:if>>20</option>
								<option value="50" <c:if test="${pageUtil.count == 50 }">selected</c:if>>50</option>
								<option value="${pageUtil.record}" <c:if test="${pageUtil.count == pageUtil.record }">selected</c:if>>${pageUtil.record}</option>
							</select>
						</div>
						<div class="right"></div>
					</div></td>
				<td width="20" align="center">条，</td> --%>
				<td width="100" align="center">共${pageUtil.totalPage}页，</td>
				<td width="80" align="center">当前第${pageUtil.currentPage}页</td>
				<td width="320" align="left"><c:if test="${pageUtil.totalPage > pageUtil.firstPage}">
						<span class="is_operation"> 
							<a href="#" onclick="javascript:jumpPagation(${pageUtil.firstPage});" id="firstPage" style="width:30px;">首页</a> 
							
							<c:if test="${pageUtil.prePage >= pageUtil.firstPage and pageUtil.prePage != 0}">
								<a href="#" onclick="javascript:jumpPagation(${pageUtil.prePage});" id="prePage">上一页</a>
							</c:if> 
							
							<c:if test="${pageUtil.totalPage >= pageUtil.nextPage}">
								<a href="#" onclick="javascript:jumpPagation(${pageUtil.nextPage});" id="nextPage">下一页</a>
							</c:if> 
							
							<a href="#" onclick="javascript:jumpPagation(${pageUtil.lastPage});" id="lastPage">尾页</a>
						</span>
					</c:if>
				</td>
				<td width="157"></td>
				<td width="100" align="center">跳转到第</td>
				<td width="60"><input type="text" name="currentPage_1" id="currentPage_1" value="${pageUtil.currentPage}" class="input_style_60_24" style="height:22px !important;" onkeyup="javascript: $(this).val($(this).val().replace(/\D|^0/g,''));"/></td>
				<td width="20" align="center">页</td>
				<td width="23" valign="middle"><div class="round_redL_23_23">
						<a href="#" onclick="javascript:pagation(${pageUtil.currentPage});" id="go">GO</a>
					</div></td>
			</tr>
		</table>
	</div>
</c:if>