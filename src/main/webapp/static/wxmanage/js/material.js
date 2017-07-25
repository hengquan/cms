/**
 * 素材库js
 */

function imagePreviewDisplay(){
	var imgUrl = $('#image1').val();
	if("null"!=imgUrl && ""!=imgUrl){
		$('#upload_preview').show();
	}else{
		$('#upload_preview').hide();
	}
}

function isValidate(){
	var title = $('#title').val().trim();
	if(""==title){
		alert("标题不能为空且长度不能超过64字!");
		return false;
	}
	var image1 = $('#image1').val();
	if(image1 ==null || ""==image1.trim()){
		alert("必须插入一张图片!");
		return false;
	}
	
	return true;
}

function doSubmit(submitPage){
	if(isValidate()){
		$('#updateform').submit();
	}
}

function doDeleteImage(type,name){
	var imgUrl =  $('#'+name+'1').val();
	
	var url = wxadmin.appRoot+"/stream/delete.do";
	$.post(url,{imgUrl:imgUrl}, function(msg) {
		var msg = eval("(" + msg + ")");
		if (msg.success) {
			cleanImageValue(type,name);
		} else {
			alert('删除图片有误，请检查！');
		}
	});
}

function cleanImageValue(type,name){
	if("1"==type){
		$('#'+name+'1').val('');
		$("#"+name+"2").attr("src",'');
		$("#upload_preview").hide();
	}else{
		$('#exlistimg'+name).val('');
		$("#img"+name).attr("src",'');
		$("#upload_preview"+name).hide();
	}
}


//上传控件
$(function(){
	imagePreviewDisplay();
	
	initUploadComponent("i_select_files","i_stream_files_queue","i_stream_message_container","upload_preview","image1","image2");
});

function initUploadComponent(selectFileId,fileQueue,messageContainer,previewId,imgInputId,imgId){
	var config = {
		browseFileId : selectFileId, /** 选择文件的ID, 默认: i_select_files */
		browseFileBtn : "<div></div>", /** 显示选择文件的样式, 默认: `<div>请选择文件</div>` */
		dragAndDropArea: selectFileId, /** 拖拽上传区域，Id（字符类型"i_select_files"）或者DOM对象, 默认: `i_select_files` */
		dragAndDropTips: "<span>上传图片</span>", /** 拖拽提示, 默认: `<span>把文件(文件夹)拖拽到这里</span>` */
		filesQueueId : fileQueue, /** 文件上传容器的ID, 默认: i_stream_files_queue */
		filesQueueHeight : 200, /** 文件上传容器的高度（px）, 默认: 450 */
		messagerId : messageContainer, /** 消息显示容器的ID, 默认: i_stream_message_container */
		multipleFiles: false, /** 多个文件一起上传, 默认: false */
        maxSize: 2097152, //2M/** 单个文件的最大大小，默认:2G */
		tokenURL : wxadmin.appRoot+"/tk.do", /** 根据文件名、大小等信息获取Token的URI（用于生成断点续传、跨域的令牌） */
		uploadURL : wxadmin.appRoot+"/stream/upload.do", /** HTML5上传的URI */
    	extFilters : [".png", ".bmp", ".jpg", ".jpeg", ".gif"], /** 允许的文件扩展名, 默认: [] */
		onExtNameMismatch: function(name, filters) {alert('文件的扩展名不匹配，请上传'+'[.png, .bmp, .jpg, .jpeg, .gif]')}, /** 文件的扩展名不匹配的响应事件 */
        onComplete: function(file) {
        	$("#"+previewId).show();
        	
        	var msg = eval("(" + file.msg + ")");
        	
        	$("#"+imgInputId).val(msg.imgUrl);
        	$("#"+imgId).attr("src",msg.imgUrl);
        }, /** 单个文件上传完毕的响应事件 */
		onUploadError: function(status, msg) {alert('图片上传中出现错误，请重新上传！')}, /** 文件上传出错的响应事件 */
		onDestroy: function() {alert('图片上传中出现错误，请重新上传！')} /** 文件上传出错的响应事件 */
	};
	var _t = new Stream(config);
}