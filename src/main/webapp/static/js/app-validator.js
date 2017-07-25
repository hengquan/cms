var aCity = {
	11 : "北京",
	12 : "天津",
	13 : "河北",
	14 : "山西",
	15 : "内蒙古",
	21 : "辽宁",
	22 : "吉林",
	23 : "黑龙江",
	31 : "上海",
	32 : "江苏",
	33 : "浙江",
	34 : "安徽",
	35 : "福建",
	36 : "江西",
	37 : "山东",
	41 : "河南",
	42 : "湖北",
	43 : "湖南",
	44 : "广东",
	45 : "广西",
	46 : "海南",
	50 : "重庆",
	51 : "四川",
	52 : "贵州",
	53 : "云南",
	54 : "西藏",
	61 : "陕西",
	62 : "甘肃",
	63 : "青海",
	64 : "宁夏",
	65 : "新疆",
	71 : "台湾",
	81 : "香港",
	82 : "澳门",
	91 : "国外"
}

function isCardID(sId) {
	var iSum = 0;
	var info = "";
	if (!/^\d{17}(\d|x)$/i.test(sId))
		return false;//"你输入的身份证长度或格式错误"
	sId = sId.replace(/x$/i, "a");
	if (aCity[parseInt(sId.substr(0, 2))] == null)
		return false;//"你的身份证地区非法"
	sBirthday = sId.substr(6, 4) + "-" + Number(sId.substr(10, 2)) + "-"
			+ Number(sId.substr(12, 2));
	var d = new Date(sBirthday.replace(/-/g, "/"));
	if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d
			.getDate()))
		return false;//"身份证上的出生日期非法"
	for (var i = 17; i >= 0; i--)
		iSum += (Math.pow(2, i) % 11) * parseInt(sId.charAt(17 - i), 11);
	if (iSum % 11 != 1)
		return false;//"你输入的身份证号非法"
	return true;// aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"男":"女")
}

function isEmail(email) {
	var patten = new RegExp(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+(com|cn)$/);
	if(!patten.test(email)){
		return false;
	}
	return true;
}

function isMobile(mobile){
	var patten = new RegExp(/^(?:13\d|15\d|18\d)\d{5}(\d{3}|\*{3})$/); 
	if(!patten.test(mobile)){
		return false;
	}
	return true;
}

function isDateFormat(mystring) {
	if (mystring == "")return false;
	var reg = new RegExp(/^(\d{4})-(\d{2})-(\d{2})$/);
	
	if (!reg.test(mystring))return false;
	
	return true;
} 


/*安卓4.0以上不兼容此脚本
 * Date.prototype.Format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}  

// 获取年龄
function getAge(idnNum){
	var myDate = new Date(); 
	var month = myDate.getMonth() + 1; 
	var day = myDate.getDate();

	var age = myDate.getFullYear() - idnNum.substring(6, 10) - 1; 
	if (idnNum.substring(10, 12) < month || idnNum.substring(10, 12) == month && idnNum.substring(12, 14) <= day) { 
		age++; 
	}
	return age;
}*/

function isChildAge(certNo){
	var result = true;
	var url = tour.appRoot+"/web/weixin/validator/childAge.do";
    $.ajax({  
        url : url,  
        async : false, // 注意此处需要同步，因为返回完数据后，下面才能让结果的第一条selected  
        type : "POST",
        data : "certNo="+certNo,
        success : function(msg) {  
        	var msg = eval("(" + msg + ")");
        	if (!msg.success) {
        		result = false;
        	}
        }  
    });
    return result;
}

function isChildAgeByBrithday(brithday){
	var result = true;
	var url = tour.appRoot+"/web/weixin/validator/childAgeBrithday.do";
    $.ajax({  
        url : url,  
        async : false,   
        type : "POST",
        data : "brithday="+brithday,
        success : function(msg) {  
        	var msg = eval("(" + msg + ")");
        	if (!msg.success) {
        		result = false;
        	}
        }  
    });
    return result;
}

function isCheckDate(sdate){
	var result = true;
	var url = tour.appRoot+"/web/weixin/validator/checkDate.do";
    $.ajax({  
        url : url,  
        async : false, // 注意此处需要同步，因为返回完数据后，下面才能让结果的第一条selected  
        type : "POST",
        data : "sdate="+sdate,
        success : function(msg) {  
        	var msg = eval("(" + msg + ")");
        	if (!msg.success) {
        		result = false;
        	}
        }  
    });
    return result;
}

function isGreaterDate(sdate){
	var result = true;
	var url = tour.appRoot+"/web/weixin/validator/greaterDate.do";
    $.ajax({  
        url : url,  
        async : false,   
        type : "POST",
        data : "sdate="+sdate,
        success : function(msg) {  
        	var msg = eval("(" + msg + ")");
        	if (!msg.success) {
        		result = false;
        	}
        }  
    });
    return result;
}

//验证姓名输入是否合法
function checkName(id) {
	var url = tour.appRoot+"/web/weixin/validator/validateName.do";
    var names = $("#"+id).val();
    var showStr = null;

    $.ajax({  
        url : url,  
        async : false, 
        type : "POST",
        data : "names="+names,
        success : function(data) {  
        	if (data != '' && data.length > 0) {
                var dateArray = data.split("@");
                for (var i = 0; i < dateArray.length; i++) {
                    if (dateArray[i] != undefined && dateArray[i] != null && dateArray[i] != "") {
                        var temp = dateArray[i].split("|");
                        if (showStr != undefined && showStr != null && showStr != "") {
                            showStr = showStr + temp[0] + "中：【" + temp[1] + "】，";
                        } else {
                            showStr = temp[0] + "中：【" + temp[1] + "】，";
                        }
                    }
                }
                //$("#"+id+"_check").attr("class", "tips-cyr");
    			//$("#"+id+"_check").html("<i></i>乘机人姓名" + showStr + "系统无法识别，请采用汉语拼音代替");
    			//$("#"+id).focus();
                //alert("温馨提示：对不起，乘机人姓名" + showStr + "系统无法识别，请采用汉语拼音代替，重新预订！");
                return false;
            }else{
    			//$("#"+id+"_check").attr("class", "");
    			//$("#"+id+"_check").html("");
            	return true;
            } 
        }  
    });  
    
    if (showStr != undefined && showStr != null && showStr != "") {
    	return false;
    }else{
    	return true;
    }
}