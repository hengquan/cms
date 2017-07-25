$(document).ready(function() {
	allSelectEvent();
	resetButtonEvent();
	tipMessageEvent();
});

// -----------点击查询按钮事件------------
function queryButtonEvent() {
	var query_button = $('#query');
	var queConFrom = $('#queConFrom');
	$(query_button).click(function() {
		queConFrom.submit();
	});
}

// ------------点击重置按钮事件------------
function resetButtonEvent() {
	var reset_button = $('#reset');
	$(reset_button).click(function() {
		$('#queConFrom')[0].reset();
	});

}

// 删除单个是否启用的函数 start
// deleteDialogAlertEvent(appId);deleteAppByAppId(appId);
// ------------删除是否启用提示信息------------
function deleteDialogAlertEvent(id,url,jumpUrl) {
	$("#confirm").html('您确定要删除此记录吗?');
	$("#dialog-confirm").dialog({
		resizable : false,
		height : 160,
		width : 300,
		modal : true,
		buttons : {
			"确定删除" : function(event) {
				event.preventDefault();
				deleteEntityById(id,url,jumpUrl);
				$(this).dialog("close");
			},
			"取消" : function() {
				$(this).dialog("close");
			}
		}
	});
}

// ------------删除是否启用------------
function deleteEntityById(id,url,jumpUrl) {
	var tipMessage = $('#tipMessage');
	$.ajax({
		type : "post",
		url : url,
		data : "id=" + id,
		dataType : "json",
		timeout : 15000,
		success : function(data) {
			$(tipMessage).val(data);
			tipMessageEvent();
			window.setTimeout("window.location.href='" + jumpURL + "'", 2000);
		},
		error : function() {
			$(tipMessage).val("Ajax调用异常!");
			tipMessageEvent();
		}
	});
}
// 删除单个是否启用的函数 end

// 删除多个是否启用的函数 start
// 下面3个是删除选中的事件函数
// ------------删除选中的checkbox事件------------
function deleteSelectiveEvent(url,jumpUrl) {
	var all_checkbox_elements = $("input[type='checkbox']");
	var deleteSel = $('#deleteSel');
	$(deleteSel).bind('click', function() {
		var ids = "";
		$(all_checkbox_elements).each(function(i) { // 遍历每个checkbox
			if ($(this).attr('checked') == 'checked') { // 去除name中的'checkbox_'字符串，得到后面的字符串，即是否启用编号
				ids += $(this).attr('name').substring(9) + ',';
			}
		});
		if (ids.length > 0) {
			// 处理传递过来字符串的最前面的逗号
			if (',' == ids.substring(0, 1))
				ids = ids.substring(1, ids.length - 1);
			deleteMoreDialogAlertEvent(ids,url,jumpUrl);
		} else {
			deleteNoSelectiveAlertEvent();
		}
	});
}

// ------------删除选中的checkbox提示信息------------
function deleteMoreDialogAlertEvent(ids,url,jumpUrl) {
	$("#confirm").html('确定要删除选中的记录吗?');
	$("#dialog-confirm").dialog({
		resizable : false,
		height : 140,
		modal : true,
		buttons : {
			"确定删除" : function(event) {
				event.preventDefault();
				deleteMoreEntity(ids,url,jumpUrl);
				$(this).dialog("close");
			},
			"取消" : function() {
				$(this).dialog("close");
				window.setTimeout("window.location.href='" + jumpURL + "'", 0);
			}
		}
	});
}

// ------------调用Ajax，实际删除选中的某些记录------------
function deleteMoreEntity(ids,url,jumpUrl) {
	$.ajax({
		type : "post",
		url : url,
		data : "ids=" + ids,
		dataType : "json",
		timeout : 15000,
		success : function(data) {
			var msg = "";
			if(data == true){
				msg = "删除成功！";
			} else{
				msg = "删除失败！";
			}
			messageBox('#dialog-message', '#message_icon', '#message',
					msg, jumpURL);
			window.setTimeout("window.location.href='" + jumpURL + "'", 2000);
		},
		error : function() {
			messageBox('#dialog-message', '#message_icon', '#message',
					'Ajax调用异常!', jumpURL);
		}
	});
}

// -----------checkbox一个都没有选中的提示信息------------
function deleteNoSelectiveAlertEvent() {
	$("#confirm").html('您没有选择要删除的选项，请选择!');
	$("#dialog-confirm").dialog({
		resizable : false,
		height : 140,
		modal : true,
		buttons : {
			"确定" : function() {
				$(this).dialog("close");
			}
		}
	});
}

// 全部选中和取消全选
function allSelectEvent() {
	var allSelect = $('#allSelect');
	var all_checkbox_elements = $("input[type='checkbox']");
	$(allSelect).change(function(event) {
		// alert($(this).attr('checked'));
		if ($(this).attr('checked') == 'checked') {
			// alert('全部选中');
			$(all_checkbox_elements).attr('checked', 'checked');
			event.stopPropagation();
		} else {
			// alert('取消全选');
			$(all_checkbox_elements).removeAttr('checked');
			event.stopPropagation();
		}

	});
}
// 删除多个是否启用的函数 end


//------------新增应用事件------------
function saveButtonEvent() {
	var saveButton = $('#saveButton');
	$(saveButton).click(function() {
		submitEvent();
	});
}
//-------------submit事件-----------
function submitEvent() {
	var addConFrom = $('#addConFrom');
	addConFrom.submit();
}

// -----------提示信息事件------------
function tipMessageEvent() {
	var tipMessage = $('#tipMessage');
	var jumpURL = getJumpUrl();
	if ($(tipMessage).val() != '') {
		messageBox('#dialog-message', '#message_icon', '#message',
				$(tipMessage).val(), jumpURL);
	}
}
//------------生成下拉列表公用JS------------
function ajaxExecute(type, url, data, dataType, timeout, dropDownListId, spanId, tipMessage){
	$.ajax({
		type:type,  
		url:url,
		data:data,
		dataType:dataType,
		timeout:15000,
		success:function(data){
			$(dropDownListId).append("<option value=''>请选择</option>");
			$(data).each(function(index,object){
				var codeValue = object.code;
				var codeDescription = object.val;
				if($(spanId).val() == codeValue){
					$(dropDownListId).append("<option value="+codeValue+" selected>"+codeDescription+"</option>");
				}else{
					$(dropDownListId).append("<option value="+codeValue+">"+codeDescription+"</option>");
				}
				
			});
		}, 
		error:function() {
			$(tipMessage).val("Ajax调用异常！");
		}
	 });
}