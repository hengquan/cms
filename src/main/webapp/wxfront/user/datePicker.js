
    //实现天，周，月的增减
	var preclickDay="";
	var preclickWeek="";
	var preclickMonth="";
	Date.prototype.Format = function(fmt) {
		var o = {
			"M+": this.getMonth() + 1, //月份   
			"d+": this.getDate(), //日   
			"h+": this.getHours(), //小时   
			"m+": this.getMinutes(), //分   
			"s+": this.getSeconds(), //秒   
			"q+": Math.floor((this.getMonth() + 3) / 3), //季度   
			"S": this.getMilliseconds() //毫秒   
		};
		if(/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		for(var k in o)
			if(new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	}
	var date = new Date().Format("yyyy-MM-dd"); //Format("输入你想要的时间格式:yyyy-MM-dd,yyyyMMdd") 
	var dateMonth = new Date().Format("yyyy-MM");
	$("#nowDate").val(date);
	$("#nowWeek").val(date);
	$("#nowMonth").val(dateMonth);
	$("#beginDate").val(date);
	$("#endDate").val(date);
	$("#nowMonthText").html(dateMonth);
	$("#nowDayText").html(date);

	//点击按钮实现前一天后一天
	function preDay(){
		 preclickDay = 1
	     updateDate(preclickDay)
	}
	function nextDay() {		
		preclickDay = -1
        updateDate(preclickDay)
	}
	//设置一个函数
	function updateDate(datatimeval){
		var date = new Date($("#nowDate").val()); //获取当前时间  
		date.setDate(date.getDate() - datatimeval); //设置天数 -1 天  
		var time = date.Format("yyyy-MM-dd");
		$("#nowDate").val(time);	
		var nowDate = $("#nowDate").val();
		$("#beginDate").val(nowDate);
		$("#endDate").val(nowDate);
		$("#nowDayText").html(nowDate);
		getDaydata(begin,end,userid);
	}

	//文本边框每日日期改变
	function OnInput(event){
		var date = new Date($("#nowDate").val()); //获取当前时间  
		var time = date.Format("yyyy-MM-dd");
		$("#nowDate").val(time);	
		var nowDate = $("#nowDate").val();
		$("#beginDate").val(nowDate);
		$("#endDate").val(nowDate);
		$("#endDate").val(nowDate);
		$("#nowDayText").html(nowDate);
		getDaydata(begin,end,userid);
	}
	

	//获取周的第一天的最后一天
	function getWeekStartAndEnd(AddWeekCount) { 
	    //起止日期数组   
	    var startStop = new Array(); 
	    //一天的毫秒数   
	    var millisecond = 1000 * 60 * 60 * 24; 
	    //获取当前时间   
	    var currentDate = new Date($("#nowWeek").val());
	    //相对于当前日期AddWeekCount个周的日期
	    currentDate = new Date(currentDate.getTime() + (millisecond * 7*AddWeekCount));
	    //返回date是一周中的某一天
	    var week = currentDate.getDay(); 
	    //返回date是一个月中的某一天   
	    var month = currentDate.getDate();
	    //减去的天数   
	    var minusDay = week != 0 ? week - 1 : 6; 
	    //获得当前周的第一天   
	    var currentWeekFirstDay = new Date(currentDate.getTime() - (millisecond * minusDay)); 
	    //获得当前周的最后一天
	     var currentWeekLastDay = new Date(currentWeekFirstDay.getTime() + (millisecond * 6));
	    //添加至数组   
	    startStop.push(currentWeekFirstDay.Format("yyyy-MM-dd")); 
	    startStop.push(currentWeekLastDay.Format("yyyy-MM-dd")); 
	    $("#weekFirstDay").val(startStop[0]);
	    $("#weekLastDay").val(startStop[1]);
	    getWeekdata(begin,end,userid);
	    return startStop; 
	} 
	
	//点击按钮实现前一周后一周
	function preWeek(){
		preclickWeek = 7;
		updateWeek(preclickWeek);
	}
	function nextWeek() {		
		preclickWeek =  -7;
		updateWeek(preclickWeek);
	}
	//设置一个函数
	function updateWeek(datatimeval){
		var date = new Date($("#nowWeek").val()); //获取当前时间
		date.setDate(date.getDate() - datatimeval); //设置周数 -1 周  
		var time = date.Format("yyyy-MM-dd");
		$("#nowWeek").val(time);
		getWeekStartAndEnd(0);
	}
	
	
	//点击按钮实现前一月后一月
	function preMonth(){
		preclickMonth = 1;
		updateMonth(preclickMonth)		
	}
	function nextMonth() {		
		preclickMonth = -1;
		updateMonth(preclickMonth)
	}
	//设置一个函数
	function updateMonth(datatimeval){
		var date = new Date($("#nowMonth").val()); //获取当前文本框月的时间  
		date.setMonth(date.getMonth() - datatimeval); //设置月数 -1 月  
		var time = date.Format("yyyy-MM");
		$("#nowMonth").val(time);	
		var nowDate = $("#nowMonth").val();
		var year = date.getFullYear();
		var month = date.getMonth()+1;
		if(month == 0) {
			month = 12;
			year = year - 1;
		}
		if(month < 10) {
			month = "0" + month;
		}
		var monthFirstDay = year + "-" + month + "-" + "01"; //上个月的第一天  
		$("#monthFirstDay").val(monthFirstDay);
		var myDate = new Date(year, month, 0);
		var monthLastDay = year + "-" + month + "-" + myDate.getDate(); //上个月的最后一天
		$("#monthLastDay").val(monthLastDay);
		var showDate = year +"年" + month + "月";
		$("#nowMonthText").html(showDate);
		getMonthdata(begin,end,userid);
	}
	
	function changeMonth(){
		//改变月下边的事件
		var month = new Date($("#nowMonth").val()); //获取文本框时间  
		var time = month.Format("yyyy-MM");
		$("#nowMonth").val(time);	
		var nowDate = $("#nowMonth").val();
		var year = month.getFullYear();
		var month = month.getMonth()+1;
		if(month == 0) {
			month = 12;
			year = year - 1;
		}
		if(month < 10) {
			month = "0" + month;
		}
		var monthFirstDay = year + "-" + month + "-" + "01"; //上个月的第一天  
		$("#monthFirstDay").val(monthFirstDay);
		var myDate = new Date(year, month, 0);
		var monthLastDay = year + "-" + month + "-" + myDate.getDate(); //上个月的最后一天
		$("#monthLastDay").val(monthLastDay);
		var showDate = year +"年" + month + "月";
		$("#nowMonthText").html(showDate);
		getMonthdata(begin,end,userid);
	}
	//文本边框日期改变
	function OnInput(event){
		//改变日下边的事件
		var dayDate = new Date($("#nowDate").val()); //获取文本框时间  
		var daytime = dayDate.Format("yyyy-MM-dd");
		$("#nowDate").val(daytime);	
		var nowDate = $("#nowDate").val();
		$("#beginDate").val(nowDate);
		$("#endDate").val(nowDate);
		$("#endDate").val(nowDate);
		$("#nowDayText").html(nowDate);
		getDaydata(begin,end,userid);
		//改变周下边的事件
		getWeekStartAndEnd(0);
		//改变月下边的事件
		changeMonth();
	}
	$(document).ready(function(){
		getDaydata(begin,end,userid);
		getWeekStartAndEnd(0);
		//改变月下边的事件
		changeMonth()
	
	})