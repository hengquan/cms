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
		$(".head-language").html(languageList);
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
					languageHtml += '&nbsp;&nbsp;<span onclick="selLanguage('
							+ languageList[i].tab + ')">['
							+ languageList[i].name + ']</span>'
				}
				$(".head-language").html(languageHtml);
				// 渲染口岸名称
				$("#roleName").html(data.roleName);
				// 存session--站点名称
				window.sessionStorage.setItem("roleName", data.roleName);
				// 存session--站点所属语言列表
				window.sessionStorage.setItem("languageList", languageHtml);
				// 存session--口岸ID
				window.sessionStorage.setItem("roleId", data.roleId);
				// 存session--语言
				window.sessionStorage.setItem("language", data.language);
			} else {
				alert(data.msg);
			}
		}
	});
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
			$(".head-language").html(languageList);
		}
	}
}

// 获取首页轮播图
function getHomePicUrl() {
	var imgNumber = $("#imgNumber").val();
	var roleId = window.sessionStorage.getItem("roleId");
	$.ajax({
		type : 'post',
		data : {
			"imgNumber" : imgNumber,
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
					imgHtml += '<div class="item active">'
							+ '<img width="100%" height="100%" onclick=openArticleContent("'
							+ dataList[i].id
							+ '") src="'
							+ dataList[i].picUrl
							+ '" alt="First slide" onerror="excptionUrl(this)">'
							+ '</div>';
					if (i == 0) {
						imgJiaodian += '<li data-target="#myCarousel" data-slide-to="'
								+ i + '" class="active"></li>';
					} else {
						imgJiaodian += '<li data-target="#myCarousel" data-slide-to="'
								+ i + '"></li>';
					}
				}
				$(".carousel-inner").html(imgHtml);
				$(".carousel-indicators").html(imgJiaodian);
			} else {
				alert(data.msg);
			}
		}
	});
}

// 获取首面的频道列表
function getHomeChannelList() {
	var channelNumber = $("#channelNumber").val();
	var roleId = window.sessionStorage.getItem("roleId");
	$.ajax({
		type : 'post',
		data : {
			"channelNumber" : channelNumber,
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
							+ '<label class="col-md-6 btn"><b>'
							+ dataList[i].channelname
							+ '</b></label> '
							+ '<label class="col-md-6 pull-right btn"><b><a href="#" onclick=openArticleList("'
							+ dataList[i].id
							+ '","")>更多>></a></b></label>' + '</div>';
					if ((i + 1) % 2 == 0) {
						channelHtml += '<div class="col-xs-12 col-lg-12 col-md-12" style="text-align: left; margin-top: 20px;">'
								+ '<input type="hidden" class="channelId" value="'
								+ dataList[i].id
								+ '">'
								+ '<input type="hidden" class="channelId" value="3">'
								+ '<div class="theChannelList"></div>'
								+ '</div></div>';
					} else {
						channelHtml += '<div class="col-xs-12 col-lg-12 col-md-12" style="text-align: left; margin-top: 5px;">'
								+ '<input type="hidden" class="channelId" value="'
								+ dataList[i].id
								+ '">'
								+ '<input type="hidden" class="channelNumber" value="3">'
								+ '<ul class="theChannelList"></ul>'
								+ '</div></div>';
					}
				}
				$("#channelListData").html(channelHtml);
			} else {
				alert(data.msg);
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
		var language = $("#selLanguage").val();
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
						if ((index + 1) % 2 == 0) {
							html += '<span class="col-xs-4 col-lg-4 col-md-4" style="padding-right: 2px;padding-left: 2px;">'
									+ '<a href="#" onclick=openArticleContent("'
									+ dataList[i].id
									+ '")>'
									+ '<img src="'
									+ dataList[i].picUrl
									+ '" width="100%" alt="" onerror="excptionUrl(this)"><br>'
									+ '<div class="imgText">'
									+ dataList[i].articleName
									+ '</div>'
									+ '</a>'
									+ '</span>';
						} else {
							html += '<li><a href="#" onclick=openArticleContent("'
									+ dataList[i].id
									+ '")>'
									+ dataList[i].articleName
									+ '</a></li>';
						}
					}
					theChannelList.html(html);
				} else {
					alert(data.msg);
				}
			}
		});
	})
}

// 处理访问不到的图片给个默认图
function excptionUrl(obj) {
	var obj = $(obj);
	obj.attr("src", '../img/1.jpg');
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
	var language = $("#selLanguage").val();
	window.location.href = "./content.html?articleId=" + articleId
			+ "&language=" + language;
}

//获取文章列表根据频道ID
function getArticleList() {
	var nowPage = $("#nowPage").val();
	var pageSize = $("#pageSize").val();
	var language = window.sessionStorage.getItem("language");
	var channelId = getQueryString("channelId");
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
			if (data.code == "200") {
				//渲染首页频道列表
				var html = "";
				var dataList = data.dataList;
				for (var i = 0; i < dataList.length; i++) {
					var createTime = crtTimeFtt(dataList[i].createTime);
					html += '<div class="oneArticle" onclick=openArticleContent("'
							+ dataList[i].id
							+ '")>'
							+ '<div class="col-md-12" style="font-size: 24px;">'
							+ dataList[i].articleName
							+ '</div>'
							+ '<div class="col-md-12" style="font-size: 16px; color: #277ce1;">发布于:'
							+ createTime
							+ '</div>'
							+ '<div class="col-md-12" style="font-size: 16px;">'
							+ dataList[i].detail
							+ '</div>'
							+ '</div>';
				}
				$(".articleContent").html(html);
			} else {
				alert(data.msg);
			}
		}
	});
}

//获取该站点下所有的频道列表
function getChannelList() {
	var roleId = window.sessionStorage.getItem("roleId");
	var language = window.sessionStorage.getItem("language");
	//父频道名称
	var parentName = getQueryString("parentName");
	$.ajax({
		type : 'post',
		data : {
			"parentName" : parentName,
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
				alert(data.msg);
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
				var createTime = crtTimeFtt(data.createTime);
				html += '<div class="col-md-12" style="font-size: 24px;">'+data.articleName+'</div>'
	      +'<div class="col-md-12" style="font-size: 16px; color: #277ce1;">发布于:'+createTime+'&emsp;&emsp;分类:某某频道</div>'
	      +'<hr>'
	      +'<div class="col-md-12" style="font-size: 18px;">'+data.article+'</div>';
				$(".articleContent").html(html);
			} else {
				alert(data.msg);
			}
		}
	});
}