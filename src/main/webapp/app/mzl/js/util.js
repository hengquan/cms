//html获取链接上面的参数
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return r[2];
	else
		return '';
}

//日期格式化
function crtTimeFtt(millisecond) {
	if (millisecond != null) {
		var date = new Date(millisecond);
		return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
				+ date.getDate();
	}
}

function openHome() {
	//判断站点信息
	isOkRole();
	//获取该站点的模块列表
	getModuleList();
	//获取首面的频道列表
	getHomeChannelList();
	//处理首页频道下面显示的文章
	getHomeArticleList();
	//通知APP
	messageAPP(window.sessionStorage.getItem("language"));
}

//通知APP
function messageAPP(language){
	$("#roleName").attr("onclick","kouan.jsSetTabLanguage('"+language+"')");
	document.getElementById("roleName").click();
}

// 获取站点信息
function getRoleData(tab, selLanguage) {
	// 站点名称
	var roleName = window.sessionStorage.getItem("roleName");
	if (roleName != "" || roleName != null || roleName != undefined) {
		// 渲染口岸名称
		//$("#roleName").html(roleName);
	}
	// 站点所属所有语言列表
	var languageList = window.sessionStorage.getItem("languageList");
	if (languageList != "" || languageList != null || languageList != undefined) {
		// 渲染语言列表
		$("#nationalFlag").html(languageList);
	}
	// 站点ID
	var roleId = window.sessionStorage.getItem("roleId");
	// 语言
	var language = window.sessionStorage.getItem("language");
	if ((roleId == "" || roleId == null || roleId == undefined)
			&& (language == "" || language == null || language == undefined)) {
		// 口岸标识
		var tab = tab;
		// 所选语言
		var selLanguage = selLanguage;
		// 初始化口岸名称和语言
		getHomeData(tab, selLanguage);
	}
}

// 获取口岸名称和言语
function getHomeData(tab, language) {
	$.ajax({
		type : 'post',
		data : {
			"tab" : tab,
			"language" : language
		},
		url : '../../api/getHomeData',
		dataType : 'json',
		async : false,
		success : function(data) {
			if (data.code == "200") {
				// 渲染站点语言
				var languageHtml = "";
				var languageList = data.languageList;
				var chineseHtml = "";
				var koreanHtml = "";
				for (var i = 0; i < languageList.length; i++) {
					if(languageList[i].tab == 'Russian'){
						koreanHtml += '&nbsp;<img style="width: 97px;height: 20px;" src="'+languageList[i].picUrl+'" onclick=selLanguage("'+ languageList[i].tab + '")>';
					}else{
						chineseHtml += '&nbsp;<img style="width:30px;height:20px;margin-right:10px" src="'+languageList[i].picUrl+'" onclick=selLanguage("'+ languageList[i].tab + '")>';
					}
				}
				languageHtml = chineseHtml + koreanHtml;
				$("#nationalFlag").html(languageHtml);
				// 渲染口岸名称
				//$("#roleName").html(data.roleName);
				// 存session--站点名称
				window.sessionStorage.setItem("roleName", data.roleName);
				// 存session--站点标识
				window.sessionStorage.setItem("tab", tab);
				// 存session--站点所属语言列表
				window.sessionStorage.setItem("languageList", languageHtml);
				// 存session--口岸ID
				window.sessionStorage.setItem("roleId", data.roleId);
				// 存session--语言
				window.sessionStorage.setItem("language", data.language);
			} else {
				console.log(data.msg);
				return;
			}
		}
	});
}

function selLanguage(selLanguage){
	//遮罩层
    var loadIndex = layer.load(0, {
      shade: [0.1,'#fff'] //0.1透明度的白色背景
    });
	var requestUrl = window.location.href;
	var tab = window.sessionStorage.getItem("tab");
	//获取站点和语言信息
	getHomeData(tab, selLanguage);
	//打开其他信息
	if (selLanguage == 'Mongolian') {
		requestUrl = requestUrl.replace("gqmd","mengwen");
	}
	var languageList = requestUrl.split("language");
	if(languageList.length > 1 ){
		requestUrl = languageList[0]+"language="+selLanguage;
	}
	window.location.href = requestUrl;
	if(requestUrl.indexOf("home.html") != -1 ){
		openHome();
	}else{
		messageAPP(selLanguage);
	}
	//关闭遮罩
    layer.close(loadIndex);
}

// 判断站点信息是否为空-空的话去主页重新刷新站点信息
function isOkRole() {
	var roleId = window.sessionStorage.getItem("roleId");
	if (!roleId) {
		window.location.href = "./home.html";
	}else{
		// 站点名称
		var roleName = window.sessionStorage.getItem("roleName");
		if (roleName != "" || roleName != null || roleName != undefined) {
			// 渲染口岸名称
			//$("#roleName").html(roleName);
		}
		// 站点所属所有语言列表
		var languageList = window.sessionStorage.getItem("languageList");
		if (languageList != "" || languageList != null || languageList != undefined) {
			// 渲染语言列表
			$("#nationalFlag").html(languageList);
		}
	}
}

// 获取首页轮播图
function getHomePicUrl() {
	var imgNumber = $("#imgNumber").val();
	var roleId = window.sessionStorage.getItem("roleId");
	var language = window.sessionStorage.getItem("language");
	var channelType = window.sessionStorage.getItem("channelType");
	$.ajax({
		type : 'post',
		data : {
			"imgNumber" : imgNumber,
			"channelType" : channelType,
			"language" : language,
			"roleId" : roleId
		},
		url : '../../api/getArticlePicUrlList',
		dataType : 'json',
		async : false,
		success : function(data) {
			if (data.code == "200") {
				// 渲染站点轮播图
				var imgHtml = "";
				var imgJiaodian = "";
				var dataList = data.dataList;
				for (var i = 0; i < dataList.length; i++) {
					if (i == 0) {
						imgJiaodian += '<li data-target="#myCarousel" data-slide-to="'
								+ i + '" class="active"></li>';
						imgHtml += '<div class="item active">'
							+ '<img style="width:100%;height:200px" onclick=openArticleContent("'
							+ dataList[i].id
							+ '") src="'
							+ dataList[i].picUrl
							+ '" alt="First slide" onerror="excptionUrl(this)">'
							+ '<div id="contentTitle">'+dataList[i].articleName+'</div>'
							+ '</div>';
					} else {
						imgJiaodian += '<li data-target="#myCarousel" data-slide-to="'
								+ i + '"></li>';
						imgHtml += '<div class="item">'
							+ '<img style="width:100%;height:200px" onclick=openArticleContent("'
							+ dataList[i].id
							+ '") src="'
							+ dataList[i].picUrl
							+ '" alt="First slide" onerror="excptionUrl(this)">'
							+ '<div id="contentTitle">'+dataList[i].articleName+'</div>'
							+ '</div>';
					}
				}
				$(".carousel-indicators").html(imgJiaodian);
				$(".carousel-inner").html(imgHtml);
			} else {
				console.log(data.msg);
			}
		}
	});
}

// 获取首面的频道列表
function getHomeChannelList() {
	var channelNumber = $("#channelNumber").val();
	var roleId = window.sessionStorage.getItem("roleId");
	var language = window.sessionStorage.getItem("language");
	var channelType = window.sessionStorage.getItem("channelType");
	$.ajax({
		type : 'post',
		data : {
			"channelNumber" : channelNumber,
			"channelType" : channelType,
			"language" : language,
			"roleId" : roleId
		},
		url : '../../api/getHomeChannelList',
		dataType : 'json',
		async : false,
		success : function(data) {
			if (data.code == "200") {
				// 渲染首页频道列表
				var channelHtml = "";
				var dataList = data.channelList;
				for (var i = 0; i < dataList.length; i++) {
					channelHtml += '<div class="row" style="background: #fefefe; margin-top: 5px">'
							+ '<div class="col-md-12" style="border: 1px solid #eeeeee">'
							+ '<label class="col-md-6 btn thisHomeChannelName" style="font-weight:bold;text-align: left;">'
							+ dataList[i].channelname
							+ '</label> '
							+ '<label class="col-md-6 pull-right btn" style="text-align: right;"><b><a href="#" onclick=openArticleList("'
							+ dataList[i].id
							+ '","")>>></a></b></label>' + '</div>';
					channelHtml += '<div class="col-xs-12 col-lg-12 col-md-12" style="text-align: left; margin-top: 5px;">'
							+ '<input type="hidden" class="channelId" value="'
							+ dataList[i].id
							+ '">'
							+ '<input type="hidden" class="articleNumber" value="3">'
							+ '<ul class="theChannelList"></ul>'
							+ '</div></div>';
				}
				$("#channelListData").html(channelHtml);
			} else {
				console.log(data.msg);
			}
		}
	});
}

// 处理首页频道下面显示的文章
function getHomeArticleList() {
	$("#channelListData .row").each(function(index, obj) {
		var theChannelList = $(obj).find(".theChannelList");
		var channelId = $(obj).find(".channelId").val();
		var articleNumber = $(obj).find(".articleNumber").val();
		var language = window.sessionStorage.getItem("language");
		$.ajax({
			type : 'post',
			data : {
				"language" : language,
				"channelId" : channelId,
				"pageSize" : articleNumber
			},
			url : '../../api/getArticleList',
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.code == "200") {
					// 渲染首页频道列表
					var html = "";
					var dataList = data.dataList;
					for (var i = 0; i < dataList.length; i++) {
						html += '<li><a href="#" onclick=openArticleContent("'
								+ dataList[i].id
								+ '")>'
								+ dataList[i].articleName
								+ '</a></li>';
					}
					theChannelList.html(html);
				} else {
					console.log(data.msg);
				}
			}
		});
	})
}

// 处理访问不到的图片给个默认图
function excptionUrl(obj) {
	var obj = $(obj);
	obj.attr("src", 'img/1.jpg');
}

// 打开文章列表页
function openArticleList(channelId,hrefUrl) {
	if(hrefUrl !=null && hrefUrl != ""){
		window.location.href = hrefUrl;
	}else{
		var language = $("#selLanguage").val();
		window.location.href = "./articleList.html?channelId=" + channelId
		+ "&language=" + language;
	}
}

// 打开文章
function openArticleContent(articleId) {
	window.location.href = "./content.html?articleId=" + articleId;
}

//获取文章列表根据频道ID
function getArticleList() {
	var nowPage = $("#nowPage").val();
	var pageSize = $("#pageSize").val();
	var channelId = $("#channelId").val();
	var language = window.sessionStorage.getItem("language");
	if(channelId == null || channelId == "" || channelId == undefined)
		channelId = getQueryString("channelId");
	$("#channelId").val(channelId);
	//请求数据
	var data = {
		"language" : language,
		"channelId" : channelId,
		"nowPage" : nowPage,
		"pageSize" : pageSize
	};
	$.ajax({
		type : 'post',
		data : data,
		url : '../../api/getArticleList',
		dataType : 'json',
		async : false,
		success : function(data) {
			console.log(data);
			if (data.code == "200") {
				//渲染首页频道列表
				var html = "";
				var dataList = data.dataList;
				for (var i = 0; i < dataList.length; i++) {
					var pushTime = crtTimeFtt(dataList[i].pushTime);
					var picUrl = dataList[i].picUrl;
					if(picUrl == "" || picUrl == null){
						if(language=="Russian"){
							html += '<div class="oneArticle" onclick=openArticleContent("'
								+ dataList[i].id
								+ '")>'
								+ '<div class="col-md-12 col-xs-12 col-sm-12" style="font-size: 10px;line-height:1.0">'
								+ dataList[i].articleName
								+ '<p style="font-size: 10px; color: #277ce1;line-height:1.5">Время выпуска:'+pushTime+'</p>'
								+ '</div>'
								+ '<div style="clear:both"></div>'
								+ '</div><hr>';
						}else{
							html += '<div class="oneArticle" onclick=openArticleContent("'
								+ dataList[i].id
								+ '")>'
								+ '<div class="col-md-12 col-xs-12 col-sm-12" style="font-size: 15px;line-height:1.5">'
								+ dataList[i].articleName
								+ '<p style="font-size: 10px; color: #277ce1;line-height:1.5">发布于:'+pushTime+'</p>'
								+ '</div>'
								+ '<div style="clear:both"></div>'
								+ '</div><hr>';
						}
					}
					if(picUrl !="" && picUrl != null){
						if(language=="Russian"){
							html += '<div class="oneArticle" onclick=openArticleContent("'
								+ dataList[i].id
								+ '")>'
								+ '<div class="col-md-7 col-xs-7 col-sm-7" style="font-size: 10px;line-height:1.0">'
								+ dataList[i].articleName
								+ '<p style="font-size: 10px; color: #277ce1;line-height:1.5">Время выпуска:'+pushTime+'</p>'
								+ '</div>'
								+ '<img class="col-md-5 col-xs-5 col-sm-5" style="width:150px;height:75px" src="'+picUrl+'">'
								+ '<div style="clear:both"></div>'
								+ '</div><hr>';
						}else{
							html += '<div class="oneArticle" onclick=openArticleContent("'
								+ dataList[i].id
								+ '")>'
								+ '<div class="col-md-7 col-xs-7 col-sm-7" style="font-size: 15px;line-height:1.5">'
								+ dataList[i].articleName
								+ '<p style="font-size: 10px; color: #277ce1;line-height:1.5">发布于:'+pushTime+'</p>'
								+ '</div>'
								+ '<img class="col-md-5 col-xs-5 col-sm-5" style="width:150px;height:75px" src="'+picUrl+'">'
								+ '<div style="clear:both"></div>'
								+ '</div><hr>';
						}
					}
				}
				$(".articleContent").html(html);
				//组分页
				compoundPage(data);
			} else {
				console.log(data.msg);
			}
		}
	});
}

//组分页
function compoundPage(data){
	//当前页数
	var nowPage = data.nowPage;
	$("#nowPage").val(nowPage);
	//页面条数
	var pageSize = data.pageSize;
	$("#pageSize").val(pageSize);
	//总页数
	var totalPageNum = data.totalPageNum;
	$("#totalPageNum").val(totalPageNum);
	var html = "";
	html += '<li><a href="#" onclick=doAppointPage("1")><<</a></li>'
		+'<li><a href="#" onclick=doUpPage("'+nowPage+'","'+totalPageNum+'")><</a></li>';
	//组中间页数
	//之间的差值
	var begin = nowPage;
	var errand = totalPageNum - nowPage;
	if(errand < 3){
		errand = 3 - errand;
		begin = nowPage - errand < 1 ? 1 : nowPage - errand;
	}
	var end = nowPage+3>totalPageNum?totalPageNum:nowPage+3;
	for(begin;begin<=end;begin++){
		if(nowPage == begin){
			html += '<li class="active"><a href="#" onclick=doAppointPage("'+begin+'")>'+begin+'</a></li>';
		}else{
			html += '<li><a href="#" onclick=doAppointPage("'+begin+'")>'+begin+'</a></li>';
		}
	}
	//组尾页面
	html += '<li><a href="#" onclick=doNextPage("'+nowPage+'","'+totalPageNum+'")>></a></li>'
	+'<li><a href="#" onclick=doAppointPage("'+totalPageNum+'")>>></a></li>';
	//填充页面
	$(".pagination").html(html);
}

//上翻
function doUpPage(nowPage,totalPageNum){
	nowPage = nowPage - 1 < 1 ? 1 : nowPage - 1;
	$("#nowPage").val(nowPage);
	getArticleList();
}

//下翻
function doNextPage(nowPage,totalPageNum){
	nowPage = parseInt(nowPage + 1) > totalPageNum ? totalPageNum : parseInt(nowPage + 1);
	$("#nowPage").val(nowPage);
	getArticleList();
}

//跳转指定页面
function doAppointPage(nowPage){
	$("#nowPage").val(nowPage);
	getArticleList();
}

//获取该站点下所有的频道列表
function getChannelList() {
	var roleId = window.sessionStorage.getItem("roleId");
	var language = window.sessionStorage.getItem("language");
	var channelType = window.sessionStorage.getItem("channelType");
	//父频道名称
	var moduleId = getQueryString("moduleId");
	$.ajax({
		type : 'post',
		data : {
			"moduleId" : moduleId,
			"channelType" : channelType,
			"language" : language,
			"roleId" : roleId
		},
		url : '../../api/getChannelList',
		dataType : 'json',
		async : false,
		success : function(data) {
			if (data.code == "200") {
				var html = "";
				var dataList = data.dataList;
				for (var i = 0; i < dataList.length; i++) {
					html += '<div class="col-xs-12 col-lg-12 col-md-12">'
							+ '<a href="#" onclick=openArticleList("'+dataList[i].id+'","'+dataList[i].hrefUrl+'")>'
							+ '<img src="'+dataList[i].picUrl+'" alt="" onerror="excptionUrl(this)"><br> <label>'+dataList[i].channelname+'</label>'
							+ '</a>' + '</div>';
				}
				$("#main_icon").html(html);
			} else {
				console.log(data.msg);
			}
		}
	});
}

//获取文章内容根据文章ID
function getArticle() {
	//文章ID
	var articleId = getQueryString("articleId");
	var language = window.sessionStorage.getItem("language");
	//请求数据
	var data = {
		"language" : language,
		"articleId" : articleId
	};
	$.ajax({
		type : 'post',
		data : data,
		url : '../../api/getArticle',
		dataType : 'json',
		async : false,
		success : function(data) {
			if (data.code == "200") {
				//渲染首页频道列表
				var html = "";
				var data = data.data;
				//频道名称
				var articleTypeName = data.setArticleTypeName;
				//外链地址
				var videoUrl = data.videoUrl;
				if(videoUrl != "" && videoUrl != null){
					window.location.href = videoUrl;
				}else{
					var pushTime = crtTimeFtt(data.pushTime);
					html += '<div class="col-md-12" style="font-size: 24px;">'+data.articleName+'</div>'
					+'<div class="col-md-12" style="font-size: 16px; color: #277ce1;">发布于:'+pushTime+'&emsp;&emsp;分类：'+articleTypeName+'</div>'
					+'<hr>'
					+'<div class="col-md-12" style="font-size: 18px;">'+data.article+'</div>';
					$(".articleContent").html(html);
				}
			} else {
				console.log(data.msg);
			}
		}
	});
}

//获取站点下所有的模块
function getModuleList() {
	var roleId = window.sessionStorage.getItem("roleId");
	var language = window.sessionStorage.getItem("language");
	var channelType = window.sessionStorage.getItem("channelType");
	$.ajax({
		type : 'post',
		data : {
			"roleId" : roleId,
			"language" : language,
			"channelType" : channelType
		},
		url : '../../api/getModuleList',
		dataType : 'json',
		async : false,
		success : function(data) {
			if (data.code == "200") {
				var html = "";
				var dataList = data.dataList;
//				for (var i = 0; i < dataList.length; i++) {
//					html += '<span style="width:20%">'
//					+'<a href="#" onclick=gotoChannelPage("'+dataList[i].id+'")>'
//					+'<img style="width:75px;height:75px;border-radius:50%; overflow:hidden;" src="'+dataList[i].picUrl+'" onerror="excptionUrl(this)"><br> <label style="word-break : break-all;overflow:hidden;">'+dataList[i].moduleName+'</label>'
//				  +'</a></span>';
//				}
				//写上固定的吃住游娱购
				for (var i = 0; i < dataList.length; i++) {
					//频道ID
					var channelId = "";
					var moduleName = dataList[i].moduleName;
					if(moduleName == "吃" || moduleName == "Русская кухня"){
						channelId = "4172B1B3FFA801697ADD3725823C02B4";
					}else if(moduleName == "住" || moduleName == "Жить в Маньчжурии"){
						channelId = "41721BFAFFA801692A19D3F90DC04219";
					}else if(moduleName == "游" || moduleName == "Очаровательная Маньчжурия"){
						channelId = "41732EF7FFA8016953FC5AC02EA94B85";
					}else if(moduleName == "娱" || moduleName == "Путешествие по Китаю"){
						channelId = "4173BA7AFFA8016903CF14EB431C677C";
					}else if(moduleName == "购" || moduleName == "Магазины"){
						channelId = "41741EEBFFA80169596142D61926472F";
					}
					html += '<span style="width:20%">'
						+'<a href="#" onclick=openArticleList("'+channelId+'")>'
						+'<img style="width:75px;height:75px;border-radius:50%; overflow:hidden;" src="'+dataList[i].picUrl+'" onerror="excptionUrl(this)"><br> <label style="word-break : break-all;overflow:hidden;">'+moduleName+'</label>'
						+'</a></span>';
				}
				$("#main_icon").html(html);
			} else {
				console.log(data.msg);
			}
		}
	});
}

//打开频道列表
function gotoChannelPage(moduleId) {
	window.location.href = "./channelList.html?moduleId=" + moduleId;
}

