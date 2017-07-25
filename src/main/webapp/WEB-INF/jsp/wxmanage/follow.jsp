<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>关注时回复</title>
<link href="${appRoot}/static/wxmanage/css/stream-v1.css" rel="stylesheet" type="text/css" />
<%@ include file="inc/head.jsp"%>

<link href="${appRoot}/static/wxmanage/css/others/jquery.ui.all.css" rel="stylesheet" rev="stylesheet" charset="UTF-8" />
<script language="JavaScript" src="${appRoot}/static/wxmanage/js/others/jquery-1.8.3.js" type="text/javascript"></script>
<script language="JavaScript" src="${appRoot}/static/wxmanage/js/others/ui/jquery.ui.core.js" type="text/javascript"></script>
<script language="JavaScript" src="${appRoot}/static/wxmanage/js/others/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script language="JavaScript" src="${appRoot}/static/wxmanage/js/others/ui/jquery.ui.mouse.js" type="text/javascript"></script>
<script language="JavaScript" src="${appRoot}/static/wxmanage/js/others/ui/jquery.ui.draggable.js" type="text/javascript"></script>
<script language="JavaScript" src="${appRoot}/static/wxmanage/js/others/ui/jquery.ui.position.js" type="text/javascript"></script>
<script language="JavaScript" src="${appRoot}/static/wxmanage/js/others/ui/jquery.ui.resizable.js" type="text/javascript"></script>
<script language="JavaScript" src="${appRoot}/static/wxmanage/js/others/ui/jquery.ui.button.js" type="text/javascript"></script>
<script language="JavaScript" src="${appRoot}/static/wxmanage/js/others/ui/jquery.ui.dialog.js" type="text/javascript"></script>

<script src="${appRoot}/static/wxmanage/js/stream-v1.min.js" type="text/javascript"></script>
<script src="${appRoot}/static/wxmanage/js/follow-keyword.js" type="text/javascript"></script>

<script type="text/javascript">
	$(function(){
		var resptype = '${resptype}';
		doChangeType(resptype);
	});
</script>
</head>
<body>
	<div id="az-body">
		<div class="az-right">
			<table border="0" cellpadding="0" cellspacing="0" class="Mcase">
				<tr>
					<td class="l"><div style="padding: 5px 2px 0 6px">
							<h1 class="tit">关注时回复</h1>
							<form:form modelAttribute="follow" commandName="follow" action="follow-update.do" id="updateform" method="post">
								<input type="hidden" value="${follow.id}" name="id" />
								<div style="margin: 15px 15px;0px 0px"></div>
								
								<table width="100%" style="border-collapse: collapse; font-family: 微软雅黑; font-size: 16px;">
									<tr style="text-align: center; border-bottom: 1px solid rgb(204, 204, 204); height: 30px; line-height: 30px;">
										<td><form:radiobutton style="width:auto;height:auto;" path="resptype" value="0" onclick="doChangeType(0)"/>&nbsp;<span>文字消息</span></td>
										<td><form:radiobutton style="width:auto;height:auto;" path="resptype" value="1" onclick="doChangeType(1)"/>&nbsp;单图文消息</td>
										<td><form:radiobutton style="width:auto;height:auto;" path="resptype" value="2" onclick="doChangeType(2)"/>&nbsp;多图文消息</td>
									</tr>
									<tr id="type-tr0" style="display:none;">
										<td colspan="3">
											<textarea name="content" class="az-textarea" id="content" style="width: 99%; margin-top: 20px; height: 150px;">${follow.content}</textarea>
										</td>
									</tr>
									<tr id="type-tr1">
										<td colspan="3">
											<table class="pic_txt_table">
												<tr>
													<td>
														<span>标题</span>
														<span class="title_tips">（不能为空且长度不能超过64字）</span>
													</td>
													<td style="text-align:right;"><a class="btn btn_upload" onclick="openMaterialDiv('1','image');" href="javascript:void(0);" id="selectMaterial">从素材库选择</a></td>
												</tr>
												<tr>
													<td colspan="2">
														<span class="frm_input_box with_counter counter_in append count">
															<form:input type="text" name="title" class="frm_input js_title js_counter" id="title" path="title" value="${follow.title}" maxlength="64"/>
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
																	<input type="hidden" name="img" id="image1" value="${follow.img}"/> 
																	<img id="image2" src="${follow.img}" style="max-width:100px;max-height:100px;"/>
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
														<textarea name="des" class="az-txtarea" id="des" style="width: 99%; height: 50px;font-size: 14px;">${follow.des}</textarea>
													</td>
												</tr>
												<tr>
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
															<form:input type="text" name="url" path="url" class="frm_input js_title js_counter" id="url" value="${follow.url}" />
														</span>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr id="type-tr2">
										<td colspan="3">
											<!-- 第一条 -->
											<table class="pic_txt_table">
												<tr>
													<td>
														<span>标题</span>
														<span class="title_tips">（不能为空且长度不能超过64字）No.1</span>
														<input type="hidden" name="exlist[0].id" value="${follow.exlist[0].id}" />
													</td>
													<td style="text-align:right;"><a class="btn btn_upload" onclick="openMaterialDiv('2','0');" href="javascript:void(0);" id="selectMaterial0">从素材库选择</a></td>
												</tr>
												<tr>
													<td colspan="2">
														<span class="frm_input_box with_counter counter_in append count">
															<input type="text" name="exlist[0].title" class="frm_input js_title js_counter" id="title0" value="${follow.exlist[0].title}" maxlength="64"/>
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
															<div id="i_select_files0"></div>
															<div id="i_stream_files_queue0" style="display:none;"></div>
															<div id="i_stream_message_container0" class="stream-main-upload-box" style="overflow: auto;height:0;display:none;"></div>
														</label>
													</td>
												</tr>
												<tr id="upload_preview0" style="display:none;">
													<td colspan="2">
														<table>
															<tr>
																<td>
																	<input type="hidden" name="exlist[0].img" id="exlistimg0" value="${follow.exlist[0].img}"/>
																	<img id="img0" src="${follow.exlist[0].img}" style="max-width:100px;max-height:100px;"/> 
																</td>
																<td style="vertical-align: bottom;"><a style="margin-left: 5px;cusor:pointer;" href="###" onclick="doDeleteImage('2','0');">删除</a></td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td colspan="2">描述</td>
												</tr>
												<tr>
													<td colspan="2">
														<textarea name="exlist[0].des" class="az-txtarea" id="des0" style="width: 99%; height: 50px;font-size: 14px;">${follow.exlist[0].des}</textarea>
													</td>
												</tr>
												<tr>
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
															<input type="text" name="exlist[0].url" class="frm_input js_title js_counter" id="url0" value="${follow.exlist[0].url}" />
														</span>
													</td>
												</tr>
											</table>
											<!-- 第二条 -->
											<table class="pic_txt_table">
												<tr>
													<td>
														<span>标题</span>
														<span class="title_tips">（不能为空且长度不能超过64字）No.2</span>
														<input type="hidden" name="exlist[1].id" value="${follow.exlist[1].id}" />
													</td>
													<td style="text-align:right;"><a class="btn btn_upload" onclick="openMaterialDiv('2','1');" href="javascript:void(0);" id="selectMaterial1">从素材库选择</a></td>
												</tr>
												<tr>
													<td colspan="2">
														<span class="frm_input_box with_counter counter_in append count">
															<input type="text" name="exlist[1].title" class="frm_input js_title js_counter" id="title1" value="${follow.exlist[1].title}" maxlength="64"/>
															<em class="frm_input_append frm_counter">0/64</em>
														</span>
														<div style="display:none;" class="frm_msg fail js_title_error">标题不能为空且长度不能超过64字</div>
													</td>
												</tr>
												<tr>
													<td colspan="2">
														<label class="frm_label" for=""> 
															<span>缩略图</span>
															<span class="title_tips">（小图片建议尺寸：200像素 * 200像素）</span>
															<div id="i_select_files1"></div>
															<div id="i_stream_files_queue1" style="display:none;"></div>
															<div id="i_stream_message_container1" class="stream-main-upload-box" style="overflow: auto;height:0;display:none;"></div>
														</label>
													</td>
												</tr>
												<tr id="upload_preview1" style="display:none;">
													<td colspan="2">
														<table>
															<tr>
																<td>
																	<input type="hidden" name="exlist[1].img" id="exlistimg1" value="${follow.exlist[1].img}"/>
																	<img id="img1" src="${follow.exlist[1].img}" style="max-width:100px;max-height:100px;"/> 
																</td>
																<td style="vertical-align: bottom;"><a style="margin-left: 5px;cusor:pointer;" href="###" onclick="doDeleteImage('2','1');">删除</a></td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td colspan="2">描述</td>
												</tr>
												<tr>
													<td colspan="2">
														<textarea name="exlist[1].des" class="az-txtarea" id="des1" style="width: 99%; height: 50px;font-size: 14px;">${follow.exlist[1].des}</textarea>
													</td>
												</tr>
												<tr>
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
															<input type="text" name="exlist[1].url" class="frm_input js_title js_counter" id="url1" value="${follow.exlist[1].url}" />
														</span>
													</td>
												</tr>
											</table>
											
											<!-- 第三条 -->
											<table class="pic_txt_table" id="item_2">
												<tr>
													<td>
														<span>标题</span>
														<span class="title_tips">（不能为空且长度不能超过64字）No.3</span>
														<input type="hidden" name="exlist[2].id" value="${follow.exlist[2].id}" id="item_id_2"/>
													</td>
													<td style="text-align:right;">
														<a class="btn btn_upload btn_w60" onclick="doDeleteItem(2,'follow')" href="javascript:void(0);" id="del_a">删 除</a>
														<a class="btn btn_upload" onclick="openMaterialDiv('2','2');" href="javascript:void(0);" id="selectMaterial2">从素材库选择</a>
													</td>
												</tr>
												<tr>
													<td colspan="2">
														<span class="frm_input_box with_counter counter_in append count">
															<input type="text" name="exlist[2].title" class="frm_input js_title js_counter" id="title2" value="${follow.exlist[2].title}" maxlength="64"/>
															<em class="frm_input_append frm_counter">0/64</em>
														</span>
														<div style="display:none;" class="frm_msg fail js_title_error">标题不能为空且长度不能超过64字</div>
													</td>
												</tr>
												<tr>
													<td colspan="2">
														<label class="frm_label" for=""> 
															<span>缩略图</span>
															<span class="title_tips">（小图片建议尺寸：200像素 * 200像素）</span>
															<div id="i_select_files2"></div>
															<div id="i_stream_files_queue2" style="display:none;"></div>
															<div id="i_stream_message_container2" class="stream-main-upload-box" style="overflow: auto;height:0;display:none;"></div>
														</label>
													</td>
												</tr>
												<tr id="upload_preview2" style="display:none;">
													<td colspan="2">
														<table>
															<tr>
																<td>
																	<input type="hidden" name="exlist[2].img" id="exlistimg2" value="${follow.exlist[2].img}"/>
																	<img id="img2" src="${follow.exlist[2].img}" style="max-width:100px;max-height:100px;"/> 
																</td>
																<td style="vertical-align: bottom;"><a style="margin-left: 5px;cusor:pointer;" href="###" onclick="doDeleteImage('2','2');">删除</a></td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td colspan="2">描述</td>
												</tr>
												<tr>
													<td colspan="2">
														<textarea name="exlist[2].des" class="az-txtarea" id="des2" style="width: 99%; height: 50px;font-size: 14px;">${follow.exlist[2].des}</textarea>
													</td>
												</tr>
												<tr>
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
															<input type="text" name="exlist[2].url" class="frm_input js_title js_counter" id="url2" value="${follow.exlist[2].url}" />
														</span>
													</td>
												</tr>
											</table>
											
											<!-- 第四条 -->
											<table class="pic_txt_table" id="item_3">
												<tr>
													<td>
														<span>标题</span>
														<span class="title_tips">（不能为空且长度不能超过64字）No.4</span>
														<input type="hidden" name="exlist[3].id" value="${follow.exlist[3].id}" id="item_id_3"/>
													</td>
													<td style="text-align:right;">
														<a class="btn btn_upload btn_w60" onclick="doDeleteItem(3,'follow')" href="javascript:void(0);" id="del_a">删 除</a>
														<a class="btn btn_upload" onclick="openMaterialDiv('2','3');" href="javascript:void(0);" id="selectMaterial3">从素材库选择</a>
													</td>
												</tr>
												<tr>
													<td colspan="2">
														<span class="frm_input_box with_counter counter_in append count">
															<input type="text" name="exlist[3].title" class="frm_input js_title js_counter" id="title3" value="${follow.exlist[3].title}" maxlength="64"/>
															<em class="frm_input_append frm_counter">0/64</em>
														</span>
														<div style="display:none;" class="frm_msg fail js_title_error">标题不能为空且长度不能超过64字</div>
													</td>
												</tr>
												<tr>
													<td colspan="2">
														<label class="frm_label" for=""> 
															<span>缩略图</span>
															<span class="title_tips">（小图片建议尺寸：200像素 * 200像素）</span>
															<div id="i_select_files3"></div>
															<div id="i_stream_files_queue3" style="display:none;"></div>
															<div id="i_stream_message_container3" class="stream-main-upload-box" style="overflow: auto;height:0;display:none;"></div>
														</label>
													</td>
												</tr>
												<tr id="upload_preview3" style="display:none;">
													<td colspan="2">
														<table>
															<tr>
																<td>
																	<input type="hidden" name="exlist[3].img" id="exlistimg3" value="${follow.exlist[3].img}"/>
																	<img id="img3" src="${follow.exlist[3].img}" style="max-width:100px;max-height:100px;"/> 
																</td>
																<td style="vertical-align: bottom;"><a style="margin-left: 5px;cusor:pointer;" href="###" onclick="doDeleteImage('2','3');">删除</a></td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td colspan="2">描述</td>
												</tr>
												<tr>
													<td colspan="2">
														<textarea name="exlist[3].des" class="az-txtarea" id="des3" style="width: 99%; height: 50px;font-size: 14px;">${follow.exlist[3].des}</textarea>
													</td>
												</tr>
												<tr>
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
															<input type="text" name="exlist[3].url" class="frm_input js_title js_counter" id="url3" value="${follow.exlist[3].url}" />
														</span>
													</td>
												</tr>
											</table>
											
											<!-- 第五条 -->
											<table class="pic_txt_table" id="item_4">
												<tr>
													<td>
														<span>标题</span>
														<span class="title_tips">（不能为空且长度不能超过64字）No.5</span>
														<input type="hidden" name="exlist[4].id" value="${follow.exlist[4].id}" id="item_id_4"/>
													</td>
													<td style="text-align:right;">
														<a class="btn btn_upload btn_w60" onclick="doDeleteItem(4,'follow')" href="javascript:void(0);" id="del_a">删 除</a>
														<a class="btn btn_upload" onclick="openMaterialDiv('2','4');" href="javascript:void(0);" id="selectMaterial4">从素材库选择</a>
													</td>
												</tr>
												<tr>
													<td colspan="2">
														<span class="frm_input_box with_counter counter_in append count">
															<input type="text" name="exlist[4].title" class="frm_input js_title js_counter" id="title4" value="${follow.exlist[4].title}" maxlength="64"/>
															<em class="frm_input_append frm_counter">0/64</em>
														</span>
														<div style="display:none;" class="frm_msg fail js_title_error">标题不能为空且长度不能超过64字</div>
													</td>
												</tr>
												<tr>
													<td colspan="2">
														<label class="frm_label" for=""> 
															<span>缩略图</span>
															<span class="title_tips">（小图片建议尺寸：200像素 * 200像素）</span>
															<div id="i_select_files4"></div>
															<div id="i_stream_files_queue4" style="display:none;"></div>
															<div id="i_stream_message_container4" class="stream-main-upload-box" style="overflow: auto;height:0;display:none;"></div>
														</label>
													</td>
												</tr>
												<tr id="upload_preview4" style="display:none;">
													<td colspan="2">
														<table>
															<tr>
																<td>
																	<input type="hidden" name="exlist[4].img" id="exlistimg4" value="${follow.exlist[4].img}"/>
																	<img id="img4" src="${follow.exlist[4].img}" style="max-width:100px;max-height:100px;"/> 
																</td>
																<td style="vertical-align: bottom;"><a style="margin-left: 5px;cusor:pointer;" href="###" onclick="doDeleteImage('2','4');">删除</a></td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td colspan="2">描述</td>
												</tr>
												<tr>
													<td colspan="2">
														<textarea name="exlist[4].des" class="az-txtarea" id="des4" style="width: 99%; height: 50px;font-size: 14px;">${follow.exlist[4].des}</textarea>
													</td>
												</tr>
												<tr>
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
															<input type="text" name="exlist[4].url" class="frm_input js_title js_counter" id="url4" value="${follow.exlist[4].url}" />
														</span>
													</td>
												</tr>
											</table>
											
											<!-- 第六条 -->
											<table class="pic_txt_table" id="item_5">
												<tr>
													<td>
														<span>标题</span>
														<span class="title_tips">（不能为空且长度不能超过64字）No.6</span>
														<input type="hidden" name="exlist[5].id" value="${follow.exlist[5].id}" id="item_id_5"/>
													</td>
													<td style="text-align:right;">
														<a class="btn btn_upload btn_w60" onclick="doDeleteItem(5,'follow')" href="javascript:void(0);" id="del_a">删 除</a>
														<a class="btn btn_upload" onclick="openMaterialDiv('2','5');" href="javascript:void(0);" id="selectMaterial5">从素材库选择</a>
													</td>
												</tr>
												<tr>
													<td colspan="2">
														<span class="frm_input_box with_counter counter_in append count">
															<input type="text" name="exlist[5].title" class="frm_input js_title js_counter" id="title5" value="${follow.exlist[5].title}" maxlength="64"/>
															<em class="frm_input_append frm_counter">0/64</em>
														</span>
														<div style="display:none;" class="frm_msg fail js_title_error">标题不能为空且长度不能超过64字</div>
													</td>
												</tr>
												<tr>
													<td colspan="2">
														<label class="frm_label" for=""> 
															<span>缩略图</span>
															<span class="title_tips">（小图片建议尺寸：200像素 * 200像素）</span>
															<div id="i_select_files5"></div>
															<div id="i_stream_files_queue5" style="display:none;"></div>
															<div id="i_stream_message_container5" class="stream-main-upload-box" style="overflow: auto;height:0;display:none;"></div>
														</label>
													</td>
												</tr>
												<tr id="upload_preview5" style="display:none;">
													<td colspan="2">
														<table>
															<tr>
																<td>
																	<input type="hidden" name="exlist[5].img" id="exlistimg5" value="${follow.exlist[5].img}"/>
																	<img id="img5" src="${follow.exlist[5].img}" style="max-width:100px;max-height:100px;"/> 
																</td>
																<td style="vertical-align: bottom;"><a style="margin-left: 5px;cusor:pointer;" href="###" onclick="doDeleteImage('2','5');">删除</a></td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td colspan="2">描述</td>
												</tr>
												<tr>
													<td colspan="2">
														<textarea name="exlist[5].des" class="az-txtarea" id="des5" style="width: 99%; height: 50px;font-size: 14px;">${follow.exlist[5].des}</textarea>
													</td>
												</tr>
												<tr>
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
															<input type="text" name="exlist[5].url" class="frm_input js_title js_counter" id="url5" value="${follow.exlist[5].url}" />
														</span>
													</td>
												</tr>
											</table>
											
											<!-- 第七条 -->
											<table class="pic_txt_table" id="item_6">
												<tr>
													<td>
														<span>标题</span>
														<span class="title_tips">（不能为空且长度不能超过64字）No.7</span>
														<input type="hidden" name="exlist[6].id" value="${follow.exlist[6].id}" id="item_id_6"/>
													</td>
													<td style="text-align:right;">
														<a class="btn btn_upload btn_w60" onclick="doDeleteItem(6,'follow')" href="javascript:void(0);" id="del_a">删 除</a>
														<a class="btn btn_upload" onclick="return false;" href="openMaterialDiv('2','6');" id="selectMaterial6">从素材库选择</a>
													</td>
												</tr>
												<tr>
													<td colspan="2">
														<span class="frm_input_box with_counter counter_in append count">
															<input type="text" name="exlist[6].title" class="frm_input js_title js_counter" id="title6" value="${follow.exlist[6].title}" maxlength="64"/>
															<em class="frm_input_append frm_counter">0/64</em>
														</span>
														<div style="display:none;" class="frm_msg fail js_title_error">标题不能为空且长度不能超过64字</div>
													</td>
												</tr>
												<tr>
													<td colspan="2">
														<label class="frm_label" for=""> 
															<span>缩略图</span>
															<span class="title_tips">（小图片建议尺寸：200像素 * 200像素）</span>
															<div id="i_select_files6"></div>
															<div id="i_stream_files_queue6" style="display:none;"></div>
															<div id="i_stream_message_container6" class="stream-main-upload-box" style="overflow: auto;height:0;display:none;"></div>
														</label>
													</td>
												</tr>
												<tr id="upload_preview6" style="display:none;">
													<td colspan="2">
														<table>
															<tr>
																<td>
																	<input type="hidden" name="exlist[6].img" id="exlistimg6" value="${follow.exlist[6].img}"/>
																	<img id="img6" src="${follow.exlist[6].img}" style="max-width:100px;max-height:100px;"/> 
																</td>
																<td style="vertical-align: bottom;"><a style="margin-left: 5px;cusor:pointer;" href="###" onclick="doDeleteImage('2','6');">删除</a></td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td colspan="2">描述</td>
												</tr>
												<tr>
													<td colspan="2">
														<textarea name="exlist[6].des" class="az-txtarea" id="des6" style="width: 99%; height: 50px;font-size: 14px;">${follow.exlist[6].des}</textarea>
													</td>
												</tr>
												<tr>
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
															<input type="text" name="exlist[6].url" class="frm_input js_title js_counter" id="url6" value="${follow.exlist[6].url}" />
														</span>
													</td>
												</tr>
											</table>
											
											<!-- 第八条 -->
											<table class="pic_txt_table" id="item_7">
												<tr>
													<td>
														<span>标题</span>
														<span class="title_tips">（不能为空且长度不能超过64字）No.8</span>
														<input type="hidden" name="exlist[7].id" value="${follow.exlist[7].id}" id="item_id_7"/>
													</td>
													<td style="text-align:right;">
														<a class="btn btn_upload btn_w60" onclick="doDeleteItem(7,'follow')" href="javascript:void(0);" id="del_a">删 除</a>
														<a class="btn btn_upload" onclick="openMaterialDiv('2','7');" href="javascript:void(0);" id="selectMaterial7">从素材库选择</a>
													</td>
												</tr>
												<tr>
													<td colspan="2">
														<span class="frm_input_box with_counter counter_in append count">
															<input type="text" name="exlist[7].title" class="frm_input js_title js_counter" id="title7" value="${follow.exlist[7].title}" maxlength="64"/>
															<em class="frm_input_append frm_counter">0/64</em>
														</span>
														<div style="display:none;" class="frm_msg fail js_title_error">标题不能为空且长度不能超过64字</div>
													</td>
												</tr>
												<tr>
													<td colspan="2">
														<label class="frm_label" for=""> 
															<span>缩略图</span>
															<span class="title_tips">（小图片建议尺寸：200像素 * 200像素）</span>
															<div id="i_select_files7"></div>
															<div id="i_stream_files_queue7" style="display:none;"></div>
															<div id="i_stream_message_container7" class="stream-main-upload-box" style="overflow: auto;height:0;display:none;"></div>
														</label>
													</td>
												</tr>
												<tr id="upload_preview7" style="display:none;">
													<td colspan="2">
														<table>
															<tr>
																<td>
																	<input type="hidden" name="exlist[7].img" id="exlistimg7" value="${follow.exlist[7].img}"/>
																	<img id="img7" src="${follow.exlist[7].img}" style="max-width:100px;max-height:100px;"/> 
																</td>
																<td style="vertical-align: bottom;"><a style="margin-left: 5px;cusor:pointer;" href="###" onclick="doDeleteImage('2','7');">删除</a></td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td colspan="2">描述</td>
												</tr>
												<tr>
													<td colspan="2">
														<textarea name="exlist[7].des" class="az-txtarea" id="des7" style="width: 99%; height: 50px;font-size: 14px;">${follow.exlist[7].des}</textarea>
													</td>
												</tr>
												<tr>
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
															<input type="text" name="exlist[7].url" class="frm_input js_title js_counter" id="url7" value="${follow.exlist[7].url}" />
														</span>
													</td>
												</tr>
											</table>
											
											<!-- 第九条 -->
											<table class="pic_txt_table" id="item_8">
												<tr>
													<td>
														<span>标题</span>
														<span class="title_tips">（不能为空且长度不能超过64字）No.9</span>
														<input type="hidden" name="exlist[8].id" value="${follow.exlist[8].id}" id="item_id_8"/>
													</td>
													<td style="text-align:right;">
														<a class="btn btn_upload btn_w60" onclick="doDeleteItem(8,'follow')" href="javascript:void(0);" id="del_a">删 除</a>
														<a class="btn btn_upload" onclick="return false;" href="javascript:void(0);" id="selectMaterial">从素材库选择</a>
													</td>
												</tr>
												<tr>
													<td colspan="2">
														<span class="frm_input_box with_counter counter_in append count">
															<input type="text" name="exlist[8].title" class="frm_input js_title js_counter" id="title8" value="${follow.exlist[8].title}" maxlength="64"/>
															<em class="frm_input_append frm_counter">0/64</em>
														</span>
														<div style="display:none;" class="frm_msg fail js_title_error">标题不能为空且长度不能超过64字</div>
													</td>
												</tr>
												<tr>
													<td colspan="2">
														<label class="frm_label" for=""> 
															<span>缩略图</span>
															<span class="title_tips">（小图片建议尺寸：200像素 * 200像素）</span>
															<div id="i_select_files8"></div>
															<div id="i_stream_files_queue8" style="display:none;"></div>
															<div id="i_stream_message_container8" class="stream-main-upload-box" style="overflow: auto;height:0;display:none;"></div>
														</label>
													</td>
												</tr>
												<tr id="upload_preview8" style="display:none;">
													<td colspan="2">
														<table>
															<tr>
																<td>
																	<input type="hidden" name="exlist[8].img" id="exlistimg8" value="${follow.exlist[8].img}"/>
																	<img id="img8" src="${follow.exlist[8].img}" style="max-width:100px;max-height:100px;"/> 
																</td>
																<td style="vertical-align: bottom;"><a style="margin-left: 5px;cusor:pointer;" href="###" onclick="doDeleteImage('2','8');">删除</a></td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td colspan="2">描述</td>
												</tr>
												<tr>
													<td colspan="2">
														<textarea name="exlist[8].des" class="az-txtarea" id="des8" style="width: 99%; height: 50px;font-size: 14px;">${follow.exlist[8].des}</textarea>
													</td>
												</tr>
												<tr>
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
															<input type="text" name="exlist[8].url" class="frm_input js_title js_counter" id="url8" value="${follow.exlist[8].url}" />
														</span>
													</td>
												</tr>
											</table>
											
										</td>
									</tr>
									
									<tr style="height:25px;">
										<td colspan="3"></td>
									</tr>
									<tr style="border-top: 1px solid rgb(204, 204, 204); height: 50px; text-align: right;">
										<td colspan="3">
											<input type="button" value="提 交" class="btn btn_primary" id="sub_btn" onclick="doSubmit('follow')"/>
											<a class="btn btn_upload btn_w60" onclick="doAddItem();" href="javascript:void(0);" id="add_a" style="display:none;">添 加</a>
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
	
	<!--素材库管理-->
	<div id="material-dialog" title="从素材库选择" style="display:none;height: 15px;line-height: 15px;">
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
						<td><c:out value="${material.articleTypeName}" /></td>
						<td><c:out value="${material.des}" /></td>

						<td><a href="javascript:doSelect('${material.title}','${material.img}','${material.des}','${material.article_type}','${material.id}')">选择</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>
