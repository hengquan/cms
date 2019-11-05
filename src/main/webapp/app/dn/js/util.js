//全局变量
var contentHtmlArticle = "";

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
	//getHomeChannelList();
	//处理首页视频新闻频道下面显示的文章
	getHomeArticleList();
	//获取该站点所有的文章
	getArticleAllList();
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
		$("#roleName").html(roleName);
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
				for (var i = 0; i < languageList.length; i++) {
					if(languageList[i].tab == "Chinese"){
						languageHtml += '&nbsp;<img width="40px" height="25px" src="'+languageList[i].picUrl+'" onclick=selLanguage("'+ languageList[i].tab + '")>';
					}else if(languageList[i].tab == "Russian"){
						languageHtml += '&nbsp;<img width="90px" height="25px" src="'+languageList[i].picUrl+'" onclick=selLanguage("'+ languageList[i].tab + '")>';
					}
				}
				$("#nationalFlag").html(languageHtml);
				// 渲染口岸名称
				$("#roleName").html(data.roleName);
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
			$("#roleName").html(roleName);
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

//获取首面的频道列表
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
							+ '<label class="col-md-6 btn thisHomeChannelName" style="font-weight:bold;">'
							+ dataList[i].channelname
							+ '</label> '
							+ '<label class="col-md-6 pull-right btn"><b><a href="#" onclick=openArticleList("'
							+ dataList[i].id
							+ '","")>>></a></b></label>' + '</div>';
					if ((i + 1) % 2 == 0) {
						channelHtml += '<div class="col-xs-12 col-lg-12 col-md-12" style="text-align: left; margin-top: 20px;">'
								+ '<input type="hidden" class="channelId" value="'
								+ dataList[i].id
								+ '">'
								+ '<input type="hidden" class="articleNumber" value="3">'
								+ '<div class="theChannelList"></div>'
								+ '</div></div>';
					} else {
						channelHtml += '<div class="col-xs-12 col-lg-12 col-md-12" style="text-align: left; margin-top: 5px;">'
								+ '<input type="hidden" class="channelId" value="'
								+ dataList[i].id
								+ '">'
								+ '<input type="hidden" class="articleNumber" value="3">'
								+ '<ul class="theChannelList"></ul>'
								+ '</div></div>';
					}
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
	var videoDiv = $(".theVideoList");
	var channelId = $(".videoChannelId").val();
	var videoNumber = $(".videoNumber").val();
	var language = window.sessionStorage.getItem("language");
	$.ajax({
		type : 'post',
		data : {
			"language" : language,
			"channelId" : channelId,
			"pageSize" : videoNumber
		},
		url : '../../api/getArticleList',
		dataType : 'json',
		async : false,
		success : function(data) {
			console.log(data);
			if (data.code == "200") {
				// 渲染首页频道列表
				var html = "";
				var dataList = data.dataList;
				for (var i = 0; i < dataList.length; i++) {
					html += '<span class="col-xs-4 col-lg-4 col-md-4" style="padding-right: 4px;padding-left: 4px;">'
					+'<a href="#" onclick=openArticleContent("'+ dataList[i].id +'")>'
					+'<img class="img-rounded" src="'+ dataList[i].picUrl +'" width="100%" alt="" onerror="excptionUrl(this)"><br>'
					+'<div class="imgText" style="padding: 5px;">'+ dataList[i].articleName +'</div>'
					+'</a>'
					+'</span>';
				}
				videoDiv.html(html);
			} else {
				console.log(data.msg);
			}
		}
	});
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

//获取该站点所有的文章列表
function getArticleAllList(){
	var nowPage = $("#nowPage").val();
	var pageSize = $("#pageSize").val();
	var roleId = window.sessionStorage.getItem("roleId");
	var language = window.sessionStorage.getItem("language");
	//请求数据
	var data = {
		"language" : language,
		"nowPage" : nowPage,
		"roleId" : roleId,
		"pageSize" : 1000
	};
	$.ajax({
		type : 'post',
		data : data,
		url : '../../api/getArticleAllList',
		async: false,
		dataType : 'json',
		async : false,
		success : function(data) {
			console.log(data);
			if (data.code == "200") {
				//清空
				contentHtmlArticle = "";
				// 渲染首页频道列表
				var html = "";
				var dataList = data.dataList;
				var numIndex = 1;
				for (var i = 0; i < dataList.length; i++) {
					var createTime = crtTimeFtt(dataList[i].createTime);
					var picUrl = dataList[i].picUrl;
					var contentImgList = dataList[i].contentImg;
					if(numIndex % 5 != 0){
						if(picUrl !="" && picUrl != null){
							html1(createTime,picUrl,contentImgList,dataList[i]);
							numIndex ++;
						}
					}else{
						if(contentImgList !=null && contentImgList.length > 2){
							html2(createTime,picUrl,contentImgList,dataList[i]);
							numIndex ++;
						}
					}
				}
				$("#channelListData").html(contentHtmlArticle);
			} else {
				console.log(data.msg);
			}
		}
	});
}

//第一种版式
function html1(createTime,picUrl,contentImgList,data){
	var html = '<div class="oneArticle" onclick=openArticleContent("'
		+ data.id
		+ '")>'
		+ '<div class="col-md-7 col-xs-7 col-sm-7" style="font-size: 16px;line-height:1.5;letter-spacing: 1px;padding-right: 0px;">'
		+ '<p style="word-wrap: break-word;">'
		+ data.articleName
		+ '</p>'
		+ '</div>'
		+ '<div class="col-md-5 col-xs-5 col-sm-5">'
		+ '<img style="width:100%;height:90px;margin-bottom: 10px;" src="'+picUrl+'">'
		+ '</div>'
		+ '<p style="color: #06b705;position: relative;bottom: 30px;">'
		+ '<img class="btn" src="img/icon.png" style="width: 110px;height: auto;">'
		+ '<span class="btn" style="margin-left: -23px;font-size: 13px;margin-top: 2.5px;">'+createTime+'</span></p>'
		+ '</div><hr style="margin-top: -33px;">';
		contentHtmlArticle += html;
}

//第二种版式
function html2(createTime,picUrl,contentImgList,data){
	var html = '<div style="font-size: 16px;line-height: 1.5;letter-spacing: 1px;padding-right: 0px;">'
		+'<a href="#" onclick=openArticleContent("'+data.id+'")>'
		+'<p class="col-xs-12 col-lg-12 col-md-12" style="margin-bottom: 8px;">'+data.articleName+'</p><br>';
	for(var y=0;y<contentImgList.length;y += 1){
		if(y>2)
			break;
		html += '<span class="col-xs-4 col-lg-4 col-md-4" style="padding-right:5px;padding-left:5px">'
	  +'<img style="width:100%;height:80px" src="'+contentImgList[y]+'" alt="" onerror="excptionUrl(this)"></span>'; 
	}
	html += '</a></div>'
		+ '<p style="color: #06b705;position: relative;bottom: -1px;">'
		+ '<img class="btn" src="img/icon.png" style="width: 110px;height: auto;">'
		+ '<span class="btn" style="margin-left: -23px;font-size: 13px;margin-top: 2.5px;">'+createTime+'</span></p>'
		+'<div style="clear: both"></div>'
		+'<div class="line"></div><hr style="margin-top: 0px;">';
	contentHtmlArticle += html;
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
					var createTime = crtTimeFtt(dataList[i].createTime);
					var picUrl = dataList[i].picUrl;
					if(picUrl == "" || picUrl == null){
						if(language=="Russian"){
							html += '<div class="oneArticle" onclick=openArticleContent("'
								+ dataList[i].id
								+ '")>'
								+ '<div class="col-md-12 col-xs-12 col-sm-12" style="font-size: 10px;line-height:1.0">'
								+ dataList[i].articleName
								+ '<p style="font-size: 10px; color: #277ce1;line-height:1.5">Время выпуска:'+createTime+'</p>'
								+ '</div>'
								+ '<div style="clear:both"></div>'
								+ '</div><hr>';
						}else{
							html += '<div class="oneArticle" onclick=openArticleContent("'
								+ dataList[i].id
								+ '")>'
								+ '<div class="col-md-12 col-xs-12 col-sm-12" style="font-size: 15px;line-height:1.5">'
								+ dataList[i].articleName
								+ '<p style="font-size: 10px; color: #277ce1;line-height:1.5">发布于:'+createTime+'</p>'
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
								+ '<p style="font-size: 10px; color: #277ce1;line-height:1.5">Время выпуска:'+createTime+'</p>'
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
								+ '<p style="font-size: 10px; color: #277ce1;line-height:1.5">发布于:'+createTime+'</p>'
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
	html += '<li><a href="#" onclick=doAppointPage("1")>首页</a></li>'
		+'<li><a href="#" onclick=doUpPage("'+nowPage+'","'+totalPageNum+'")>上翻</a></li>';
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
	html += '<li><a href="#" onclick=doNextPage("'+nowPage+'","'+totalPageNum+'")>下翻</a></li>'
	+'<li><a href="#" onclick=doAppointPage("'+totalPageNum+'")>尾页</a></li>';
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
					var createTime = crtTimeFtt(data.createTime);
					html += '<div class="col-md-12" style="font-size: 24px;">'+data.articleName+'</div>'
					+'<div class="col-md-12" style="font-size: 16px; color: #277ce1;">发布于:'+createTime+'&emsp;&emsp;分类：'+articleTypeName+'</div>'
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
				for (var i = 0; i < dataList.length; i++) {
					html += '<span style="width:20%">'
					+'<a href="#" onclick=gotoChannelPage("'+dataList[i].id+'")>'
					+'<img style="width:60px;height:60px;border-radius:50%; overflow:hidden;" src="'+dataList[i].picUrl+'" onerror="excptionUrl(this)"><br> <label style="word-break : break-all;overflow:hidden;">'+dataList[i].moduleName+'</label>'
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

