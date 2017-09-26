
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
	var textVal = $("#nowDate").val(date);
	var textVal = $("#nowWeek").val(date);
	var textVal = $("#nowMonth").val(date);
	//点击按钮实现前一天后一天
	function preDay(){
		preclickDay++ 
		updateDate(preclickDay)		
	}
	function nextDay() {		
		preclickDay--
		updateDate(preclickDay)
	}
	//设置一个函数
	function updateDate(datatimeval){
		var date = new Date(); //获取当前时间  
		date.setDate(date.getDate() - datatimeval); //设置天数 -1 天  
		var time = date.Format("yyyy-MM-dd");
		$("#nowDate").val(time);			
	}
	
	//点击按钮实现前一周后一周
	function preWeek(){
		preclickWeek = preclickWeek+7;
		updateWeek(preclickWeek)		
	}
	function nextWeek() {		
		preclickWeek = preclickWeek -7;
		updateWeek(preclickWeek)
	}
	//设置一个函数
	function updateWeek(datatimeval){
		var date = new Date(); //获取当前时间  
		date.setDate(date.getDate() - datatimeval); //设置周数 -1 周  
		var time = date.Format("yyyy-MM-dd");
		var textVal = $("#nowWeek").val(time);			
	}

	//点击按钮实现前一月后一月
	function preMonth(){
		preclickMonth++;
		updateMonth(preclickMonth)		
	}
	function nextMonth() {		
		preclickMonth--;
		updateMonth(preclickMonth)
	}
	//设置一个函数
	function updateMonth(datatimeval){
		var date = new Date(); //获取当前时间  
		date.setMonth(date.getMonth() - datatimeval); //设置月数 -1 月  
		var time = date.Format("yyyy-MM-dd");
		var textVal = $("#nowMonth").val(time);			
	}