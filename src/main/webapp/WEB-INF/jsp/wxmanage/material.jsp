<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>素材库管理</title>
<link href="${appRoot}/static/wxmanage/css/stream-v1.css" rel="stylesheet" type="text/css" />
<%@ include file="inc/head.jsp"%>

<script src="${appRoot}/static/wxmanage/js/stream-v1.min.js" type="text/javascript"></script>
<script src="${appRoot}/static/wxmanage/js/material.js" type="text/javascript"></script>

<script type="text/javascript" charset="utf-8" src="${appRoot}/static/assets/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${appRoot}/static/assets/ueditor/ueditor.all.min.js"> </script>

<script type="text/javascript">
	$(function(){
		var ue = UE.getEditor('content');
	});
	
</script>
</head>
<body>
	<div id="az-body">
		<div class="az-right">
			<table border="0" cellpadding="0" cellspacing="0" class="Mcase">
				<tr>
					<td class="l"><div style="padding: 5px 2px 0 6px">
							<h1 class="tit">素材库管理</h1>
							<form:form modelAttribute="material" commandName="material" action="material-update.do" id="updateform" method="post">
								<input type="hidden" value="${material.id}" name="id" />
								<div style="margin: 15px 15px 0px 0px"></div>
								
								<table width="100%" style="border-collapse: collapse; font-family: 微软雅黑; font-size: 16px;">
									<tr id="type-tr1">
										<td colspan="3">
											<table class="pic_txt_table" style="margin-top:0px !important;">
												<tr>
													<td>
														<span>类型</span>
													</td>
													<td style="text-align:right;"></td>
												</tr>
												<tr>
													<td colspan="2">
														<form:select path="article_type" style="height: 30px; width: 100%; font-family: 微软雅黑; font-size: 14px;">
															<c:forEach items="${articleList}" var="articleDic" varStatus="s">
																<c:choose>
																	<c:when test="${articleDic.code==article_type}">
																		<form:option value="${articleDic.code}" selected="selected">${articleDic.val}</form:option>
																	</c:when>
																	<c:otherwise>
																		<form:option value="${articleDic.code}">${articleDic.val}</form:option>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
														</form:select>
													</td>
												</tr>
												<tr>
													<td>
														<span>标题</span>
														<span class="title_tips">（不能为空且长度不能超过64字）</span>
													</td>
													<td style="text-align:right;"></td>
												</tr>
												<tr>
													<td colspan="2">
														<span class="frm_input_box with_counter counter_in append count">
															<form:input type="text" name="title" class="frm_input js_title js_counter" id="title" path="title" value="${material.title}" maxlength="64"/>
															<em class="frm_input_append frm_counter">0/64</em>
														</span>
														<div style="display:none;" class="frm_msg fail js_title_error">标题不能为空且长度不能超过64字</div>
													</td>
												</tr>
												<tr>
													<td colspan="2">
														<label class="frm_label" for=""> 
															<span>封面</span>
															<span class="title_tips">（大图片建议尺寸：360像素 * 200像素）</span>
															<!-- <a class="btn btn_upload" href="javascript:uploadImage('11111');" id="js_imagedialog">上 传</a> -->
															<div id="i_select_files"></div>
															<div id="i_stream_files_queue" style="display:none;"></div>
															<div id="i_stream_message_container" class="stream-main-upload-box" style="overflow: auto;height:0;display:none;"></div>
														</label>
													</td>
												</tr>
												<tr id="upload_preview" style="display:none;">
													<td colspan="2">
														<table>
															<tr>
																<td>
																	<input type="hidden" name="img" id="image1" value="${material.img}"/> 
																	<img id="image2" src="${material.img}" style="max-width:100px;max-height:100px;"/>
																</td>
																<td style="vertical-align: bottom;"><a style="margin-left: 5px;cusor:pointer;" href="###" onclick="doDeleteImage('1','image');">删除</a></td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td colspan="2">描述</td>
												</tr>
												<tr>
													<td colspan="2">
														<textarea name="des" class="az-txtarea" id="des" style="width: 99%; height: 50px;font-size: 14px;">${material.des}</textarea>
													</td>
												</tr>
												<tr>
													<td colspan="2">内容</td>
												</tr>
												<tr>
													<td colspan="2">
														<textarea name="content" class="az-txtarea" id="content" style="width: 100%; height: 100px;font-size: 14px;">${material.content}</textarea>
													</td>
												</tr>
												<%-- <tr>
													<td colspan="2">
														<label class="frm_label" for=""> 
															<span>原文链接</span>
															<span class="title_tips">（选填）</span>
														</label>
													</td>
												</tr>
												<tr>
													<td colspan="2">
														<span class="frm_input_box with_counter counter_in append count">
															<form:input type="text" name="url" path="url" class="frm_input js_title js_counter" id="url" value="${material.url}" />
														</span>
													</td>
												</tr> --%>
											</table>
										</td>
									</tr>
									<tr style="height:25px;">
										<td colspan="3"></td>
									</tr>
									<tr style="border-top: 1px solid rgb(204, 204, 204); height: 50px; text-align: right;">
										<td colspan="3">
											<input type="button" value="提 交" class="btn btn_primary" id="sub_btn" onclick="doSubmit('material')"/>
										</td>
									</tr>
								</table>
							</form:form>
						</div></td>
					<td class="r"></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
