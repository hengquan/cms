
$(function() {

});

function doSubmit(submitPage) {
	if (isValidate()) {
		$('#updateform').submit();
	}
}

function isValidate() {
	var title = $('#addr').val().trim();
	if ("" == title) {
		alert("地址/姓名不能为空!");
		return false;
	}
	var telPhone = $('#telPhone').val().trim();
	if ("" == telPhone) {
		alert("联系电话不能为空!");
		return false;
	}
	
	return true;
}