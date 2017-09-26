/**
 * 关注时回复、关键字回复页面上的js
 */

function doChangeType(resptype){
	for(var i=0;i<3;i++){
		$('#type-tr'+i).hide();
	}
	
	$('#type-tr'+resptype).show();
	
	if(2==resptype){
		$("#add_a").show();
	}
	
	imagePreviewDisplay(resptype);
	
	itemTableDisplay(resptype);
}

function imagePreviewDisplay(resptype){
	if(resptype==1){
		var imgUrl = $('#image1').val();
		if("null"!=imgUrl && ""!=imgUrl){
			$('#upload_preview').show();
		}else{
			$('#upload_preview').hide();
		}
	}else if(resptype==2){
		for(var i=0;i<9;i++){
			var imgUrl =  $("#exlistimg"+i).val();
			if("null"!=imgUrl && ""!=imgUrl){
				$('#upload_preview'+i).show();
			}else{
				$('#upload_preview'+i).hide();
			}
		}
	}
}

function itemTableDisplay(resptype){
	if(resptype!=2)return;
		
	for(var i=2;i<9;i++){
		var id =  $("#item_id_"+i).val();
		if("null"!=id && ""!=id && '0'!=id ){
			$('#item_'+i).show();
		}else{
			$('#item_'+i).hide();
		}
	}
}

function isValidate(){
	var resptype = $("input[name='resptype']:checked").val();
	if(0==resptype){
		var content = $('#content').val().trim();
		if(""==content){
			alert("文字回复内容不能为空!");
			return false;
		}
	}else if(1==resptype){
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
	}else if(2==resptype){
		var title = $('#title0').val().trim();
		if(""==title){
			alert("标题不能为空且长度不能超过64字!");
			return false;
		}
		var image = $('#exlistimg0').val();
		if(image ==null || ""==image.trim()){
			alert("封面图片不能为空!");
			return false;
		}
		title = $('#title1').val().trim();
		if(""==title){
			alert("标题不能为空且长度不能超过64字!");
			return false;
		}
		image = $('#exlistimg1').val();
		if(image ==null || ""==image.trim()){
			alert("缩略图不能为空!");
			return false;
		}
		
		var isNull = false;
		for(var i=2;i<9;i++){
			if(!$("#item_"+i).is(":hidden")){
				title = $('#title'+i).val().trim();
				if(""==title){
					alert("标题 No."+(i+1)+" 不能为空且长度不能超过64字!");
					isNull = true;
					break;
				}
				
				image = $('#exlistimg'+i).val();
				if(image ==null || ""==image.trim()){
					alert("缩略图 No."+(i+1)+" 不能为空!");
					isNull = true;
					break;
				}
			}
		}
		if(isNull)return false;
	}
	
	return true;
}

function doSubmit(submitPage){
	if("keyword"==submitPage){
		if($('#keyword').val().trim()==''){
			alert('请输入关键字！');
			$('#keyword').focus();
			return;
		}
	}
	if(isValidate()){
		$('#updateform').submit();
	}
}

function doDeleteImage(type,name){
	var imgUrl = "";
	if('1'==type){//单图文
		imgUrl = $('#'+name+'1').val();
	}else{//多图文
		imgUrl = $('#exlistimg'+name).val();
	}
	
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

function doDeleteItem(index,delPage){
	if(window.confirm('确定删除该标题吗？')){
		var id = $("#item_id_"+index).val();
		if(null==id || id=='' || id=='0'){
			$("#item_"+index).hide();
			return;
		}
		
		var url = wxadmin.appRoot+"/wxmanage/jsp/deleteEx.do";
		if("keyword"==delPage){
			url = wxadmin.appRoot+"/wxmanage/jsp/keyword-deleteEx.do";
		}
		$.post(url,{id:id}, function(msg) {
			var msg = eval("(" + msg + ")");
			if (msg.success) {
				cleanItemValue(index);
				$("#item_"+index).hide();
			} else {
				alert('删除有误，请检查！');
			}
		});
	}
}

function cleanItemValue(index){
	$("#item_id_"+index).val('0');
	$("#title"+index).val('');
	$("#exlistimg"+index).val('');
	$("#img"+index).attr("src","");
	$("#des"+index).val('');
	$("#url"+index).val('');
	$("#upload_preview"+index).hide();
}

function doAddItem(){
	var flag = 0;
	for(var i=2; i<9; i++){
		if($("#item_"+i).is(":hidden")){
			$("#item_"+i).show();
			flag = 1;
			break;
		}
	}
	if(0==flag){
		alert("最多只能添加9个图文消息！");
	}
}

//上传控件
$(function(){
	initUploadComponent("i_select_files","i_stream_files_queue","i_stream_message_container","upload_preview","image1","image2");
	
	for(var i =0;i<9;i++){
		initUploadComponent("i_select_files"+i,"i_stream_files_queue"+i,"i_stream_message_container"+i,"upload_preview"+i,"exlistimg"+i,"img"+i);
	}
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


//------------从素材库选择------------
var respType = "0";
var componentName = "";
function openMaterialDiv(type,name) {
	//$("#material-dialog").load(wxadmin.appRoot+"/wxmanage/jsp/material-select.do");
	
	respType =  type;
	componentName = name;
	
	$("#material-dialog").dialog({
		resizable : false,
		height : 400,
		width :  750,
		modal : true
		/*buttons : {
			"选择" : function(event) {
					uploadFile11();
					$(this).dialog("close");
			},
			"返回" : function() {
				$(this).dialog("close");
			}
		}*/
	});
}

function doSelect(title,imgUrl,desc,articleType,articleId){
	var url = "http://wl.weechao.com/wxmp.ql/article/analysis.do?articleType="+articleType+"&articleId="+articleId;
	
	if("1"==respType){
		$('#title').val(title);
		$('#'+componentName+'1').val(imgUrl);
		$("#"+componentName+"2").attr("src",imgUrl);
		$('#des').val(desc);
		$('#desc').val(desc);
		$('#url').val(url);
		
		$("#upload_preview").show();
	}else{
		$('#title'+componentName).val(title);
		$('#exlistimg'+componentName).val(imgUrl);
		$("#img"+componentName).attr("src",imgUrl);
		$('#des'+componentName).val(desc);
		$('#desc'+componentName).val(desc);
		$('#url'+componentName).val(url);
		
		$("#upload_preview"+componentName).show();
	}
	
	$("#material-dialog").dialog("close");
}