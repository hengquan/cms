/**
 * 分页的公用JS
 * @author ecif-yanjingying
 */
//--------------每页显示记录数改变时触发事件------------------
function countChangeEvent(){
	var count = $('#count');
	var currentPage = $('#currentPage');
	$(count).bind('change',function(){
		$(currentPage).val('1');
		pagation($(currentPage).val());  //默认当前页是1
	});
}

//--------------点击GO的跳转分页函数------------------
function pagation(currentPage){
	var count = $('#count');
	var currentPage = $('#currentPage_1');
	var currentPageHide = $('#currentPage');
	var totalPage = $('#totalPage');  //总页数
	var queConFrom = $('#searchForm');
	//parseInt 强制转换
	if(parseInt($(currentPage).val()) < 0){
		messageBoxNoJump('#dialog-message','#message_icon','#message','当前页数不能为负数!');
		$(currentPage).val('');
		$(currentPageHide).val('');
	}
	if(parseInt($(currentPage).val()) == 0){
		messageBoxNoJump('#dialog-message','#message_icon','#message','当前页数不能等于'+$(currentPage).val()+'!');
		$(currentPage).val('');
		$(currentPageHide).val('');
	}
	if(parseInt($(currentPage).val()) >0 && (parseInt($(currentPage).val()) <= parseInt($(totalPage).val()))){  //如果当前页小于等于总页数，将进行查询
		$(currentPageHide).val($(currentPage).val());
		queConFrom.submit();
	}
	if(parseInt($(currentPage).val()) > parseInt($(totalPage).val())){
		messageBoxNoJump('#dialog-message','#message_icon','#message','总页数为:'+$(totalPage).val()+'，当前页数不能大于'+$(totalPage).val()+'!');
		$(currentPage).val('');
		$(currentPageHide).val('');
	}
}

//--------------上一页、下一页、第一页、最后一页的跳转函数------------------
function jumpPagation(currentPage){
	if(currentPage != null){
		var currentPage1 = $('#currentPage');
		$(currentPage1).val(currentPage);  //更改currentPage的值为上一页、下一页跳转的值
		$('#currentPage_1').val(currentPage);
	}
	pagation(currentPage);
}