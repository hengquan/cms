var homeContentHeight = 0;
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

//初始化页面信息
function openHome() {
	//判断站点信息
	isOkRole();
	//获取该站点的模块列表
	getModuleList();
	//给页面控制高度范围
	disposeHeight();
	//首页大图
	getHomePicUrl();
	//显示和隐藏
	$("#otherPageContent").hide();
	$("#homePage").show();
}

//处理高度自适应
function disposeHeight() {
	//获取头部高度
	var topTitle = $("#topTitle").height();
	//console.log("头" + topTitle);
	//获取底部高度
	var bottomHeight = $("#bottomTitle").height();
	//console.log("底" + bottomHeight);
	//获取屏幕高度
	var middleHeight = $(window).height() - (bottomHeight + 4)
			- (topTitle + 4);
	//console.log("中间" + middleHeight);
	//首页图片内容区
	document.getElementById("homePageImg").style.height = (middleHeight / 3 * 2)
			+ "px";
	//给详情赋值高度
	homeContentHeight = (middleHeight / 4)+"px";
	//左侧导航
	document.getElementById("leftDaoHang").style.height = middleHeight
			+ "px";
	//右侧内容
	document.getElementById("rightContent").style.height = middleHeight
			+ "px";
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
		language = getQueryString("language");
		if(language == "" || language == null || language == undefined){
			// 初始化口岸名称和语言
			getHomeData(tab, selLanguage);
		}else{
			// 初始化口岸名称和语言
			getHomeData(tab, language);
		}
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
					languageHtml += '&nbsp;<img width="40px" height="25px" src="'+languageList[i].picUrl+'" onclick=selLanguage("'+ languageList[i].tab + '")>';
				}
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
	var tab = window.sessionStorage.getItem("tab");
	//获取站点和语言信息
	getHomeData(tab, selLanguage);
	if (selLanguage == 'Mongolian') {
		//打开其他信息
		openHome();
	}else{
		window.location.href="../gqmd/home.html?language="+selLanguage;
	}
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
				var textHtml = "";
				var dataList = data.dataList;
				for (var i = 0; i < dataList.length; i++) {
					imgHtml = '<img src="'+dataList[i].picUrl+'" width="100%" height="100%" onerror="excptionUrl(this)">';
					textHtml = dataList[i].detail;
				}
				$("#homePageImg").html(imgHtml);
				textHtml = '<div class="content2" style="font-family: mFont;height:'+homeContentHeight+'">'+textHtml+'</div>';
				$("#homePageDetail").html(textHtml);
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

// 处理访问不到的图片小图。。。
function excptionUrl1(obj) {
	var obj = $(obj);
	obj.attr("src", 'img/zanwu.jpg');
}

//获取文章列表根据频道ID
function getArticleList(channelId,channelName,paramObj) {
	if(channelName == "" && paramObj != null){
		channelName = $(paramObj).attr("theChannelName");
	}
	$("#articleList").html("");
	$(".channelTitle").html("");
	var nowPage = $("#nowPage").val();
	var pageSize = $("#pageSize").val();
	var language = window.sessionStorage.getItem("language");
	//给返回页赋值
	$("#goBeforePage").attr("onclick","getArticleList('"+channelId+"','"+channelName+"',"+paramObj+")");
	//请求数据
	var data = {
		"language" : language,
		"channelId" : channelId,
		"nowPage" : nowPage,
		"pageSize" : "1000"
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
				//渲染标题
				$(".channelTitle").html('<div style="font-family: mFont;">'+channelName+'</div>');
				//渲染首页频道列表
				var html = "";
				var dataList = data.dataList;
				for (var i = 0; i < dataList.length; i++) {
					var articleId = dataList[i].id;
					var articleName = dataList[i].articleName;
					html += '<div class="col-md-3" style="padding-top: 5px; padding-bottom: 5px; text-align: center">'
						+'<img width="100%" height="100%" articleName="'+articleName+'" articleId="'+articleId+'" onclick=getArticle(this) src="'+dataList[i].picUrl+'" onerror="excptionUrl(this)">' 
						+'<label class="content2" style="height:50px;margin-top: 10px;font-family: mFont;">'+dataList[i].articleName+'</label>'
						+'</div>';
				}
				$("#articleList").html(html);
				//暂时不组分页
				//compoundPage(data);
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
function getChannelList(object) {
	var obj = $(object);
	//如果这个按钮不等于返回上一页在操作。
	if(obj.attr("id") != "goBeforePage"){
		//移动到当前模块
		var model = obj.parent();
		model.find("li").each(function(index, liObj) {
			$(liObj).attr("class", "");
		})
		obj.attr("class", "active");
		//切换到模块页
		var moduleId = obj.attr("moduleId");
		if (moduleId == "goHomePage") {
			$("#otherPageContent").hide();
			$("#homePage").show();
		}else{
			//模块名字
			var moduleName = obj.attr("moduleName");
			//请求数据
			var roleId = window.sessionStorage.getItem("roleId");
			var language = window.sessionStorage.getItem("language");
			var channelType = window.sessionStorage.getItem("channelType");
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
						console.log(dataList);
						for (var i = 0; i < dataList.length; i++) {
							html += '<div class="channelListDiv">';
							html += '<ul class="nav nav-pills nav-stacked">';
							var ziChannelList = dataList[i].channelList;
							if(ziChannelList != null){
								for (var y = 0; y < ziChannelList.length; y++){
									var oneChannel = {
										"id" : ziChannelList[y].id,
										"channelname" : ziChannelList[y].channelname
									}
									html += '<li theChannelName = "'+oneChannel.channelname+'" role="presentation" onclick=getArticleList("'+oneChannel.id+'","",this) class="active"><a href="#" style="font-family: mFont;">'+ziChannelList[y].channelname+'</a></li>';
								}
							}
							html += '</ul>';
							html += '<label class="btn" style="color: #fefefe; font-size: 22px;"><b style="font-family: mFont;">'+dataList[i].channelname+'</b></label>'
							html += '</div>';
							html += '<div style="clear:both"></div>';
						}
						$("#leftDaoHang").html(html);
						//处理子频道
						var paramChannel;
						if(dataList.length>0){
							var thisChannelList = dataList[0].channelList;
							if(thisChannelList != null && thisChannelList.length>0){
								paramChannel = thisChannelList[0];
							}
						}
						//默认取第一个频道下的文章列表
						if(paramChannel != null && paramChannel != ''){
							getArticleList(paramChannel.id,paramChannel.channelname,null);
						}
					} else {
						console.log(data.msg);
					}
				}
			});
			$("#otherPageContent").show();
			$("#homePage").hide();
			//计算距离
			chuliDistance()
		}
	}
}

var divHeight = 0;
//计算距离
function chuliDistance(){
	//左侧频道列表
	$("#leftDaoHang .channelListDiv").each(function(index,obj){
		var height = $(obj).height();
		var width = $(obj).width();
		$(obj).css("left", height);
		if(index != 0)
			$(obj).css("margin-top", divHeight+"px");
		$(obj).addClass("content1");
		divHeight = height;
	})
	$("#leftDaoHang").html($("#leftDaoHang").html());
}

//获取文章内容根据文章ID
function getArticle(obj) {
	var articleId = $(obj).attr("articleId");
	var articleName = $(obj).attr("articleName");
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
				html += '<div class="col-md-12" style="font-size: 18px;font-family: mFont;">'+data.article+'</div>';
				$("#articleList").html(html);
				//赋值标题
				articleName = '<div style="font-family: mFont;">'+articleName+'</div>';
				$(".channelTitle").html(articleName);
			} else {
				console.log(data.msg);
			}
		}
	});
	//处理蒙文
	chuliMeng();
}

//处理蒙文
function chuliMeng(){
	//适配蒙文
	$("#articleList span").each(function(index,obj){
		$(obj).css("font-family","mFont");  
	})
	//适配蒙文
	$("#articleList p").each(function(index,obj){
		$(obj).css("font-family","mFont");  
	})
	//var height = $("#articleList .content1").height();
	//var width = $("#articleList .content1").width();
	//$("#articleList .content1").css("left", height);
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
				var html = '<li role="presentation" class="active" moduleId="goHomePage"'
					+'onclick="getChannelList(this)"><a href="#">'
					+'<img height="35px" src="img/123.gif" alt=""></a></li>';
				var dataList = data.dataList;
				for (var i = 0; i < dataList.length; i++) {
					html += '<li role="presentation" class="" moduleId="'+dataList[i].id+'" moduleName="'+dataList[i].moduleName+'"'
						+'onclick="getChannelList(this)"><a href="#">'
						+'<img height="35px" src="'+dataList[i].picUrl+'" onerror="excptionUrl1(this)"><br> <label class="content2" style="font-family: mFont;">'+dataList[i].moduleName+'</label></a></li>';
				}
				html += '<li role="presentation" class="" moduleId="goBeforePage" id="goBeforePage"'
					+'onclick="getChannelList(this)">'
					+'<img height="35px" src="img/123.gif" alt=""></li>';
				$("#moduleList").html(html);
			} else {
				console.log(data.msg);
			}
		}
	});
}

