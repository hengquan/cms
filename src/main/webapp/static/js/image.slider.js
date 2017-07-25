var azSlider = new Array();
var azTime = new Array();
var azEffect = new Array();

var azRoller = Array();
var azMap;
$(function(){
	//图片滚动
	//eachSlide();
	//滚动公告
	//eachRoll();

	//如果存在dp-maper，则加载百度地图js，然后再显示地图
	if($(".dp-maper").length>0)
	{
		//如地图无法显示，需要访问http://api.map.baidu.com/api?v=1.4获取最新地图地址
		if($("head").find("#bdmapjs").length<1)
			$("head").append("<script id='bdmapjs' type='text/javascript' src='http://api.map.baidu.com/getscript?v=1.4&ak=&services=&t=20130906090653'></script>")
		setTimeout("eachMap()",1500);
	}
	//$.getScript("http://api.map.baidu.com/getscript?v=1.4&ak=&services=&t=20130906090653",function(){setTimeout("eachMap()",1500);})

	//地图路线
	$("#dp-maptraffic-btn").click(function(){
		start=$("#dp-maptraffic-txt").val()
		destination=$("#dp-maptraffic-txt").attr("destination");
		if(destination=="")
		{
			alert("商户未设置店铺所在地！");
			return false;
		}
		if(start=="")
		{
			alert("请填写起始地点！");
			$("#dp-maptraffic-txt").focus();
			return false;
		}
		var transit = new BMap.TransitRoute(azMap, {
			renderOptions: {map: azMap,panel:"dp-maptraffic-result"},
			policy:BMAP_TRANSIT_POLICY_AVOID_SUBWAYS   //不乘地铁
			//BMAP_TRANSIT_POLICY_LEAST_TIME	最少时间。
			//BMAP_TRANSIT_POLICY_LEAST_TRANSFER	最少换乘。
			//BMAP_TRANSIT_POLICY_LEAST_WALKING	最少步行。
			//BMAP_TRANSIT_POLICY_AVOID_SUBWAYS	不乘地铁。(自 1.2 新增)
		});
		transit.search(start,destination);
	});
});

function eachSlide()
{
	var currentImage = new Array();
	var nextImage = new Array();

	$(".dp-slider").each(function(e){
		$(this).attr("num",e);

		height = $(this).attr("height");
		height = isNaN(height)?250:height;
		azTime[e] = $(this).attr("time");
		azTime[e] = isNaN(azTime[e])?3000:azTime[e]*1000;
		thumbs = $(this).attr("thumbs");
		azEffect[e] = $(this).attr("effect");

		count=$(this).css("height",height).find("img").length;

		if(thumbs=="1")
		{
			thu_str="";
			for(i=0;i<count;i++)
			{
				if(i==0)
					thu_str+="<li class='act'></li>";
				else
					thu_str+="<li></li>";
			}
			$(this).append("<ul class='dp-thumbs'>"+thu_str+"</ul>");
		}

		currentImage[e] = $(this).find("img").first().css("display","block");
		nextImage[e] = $(this).find("img:eq(1)");
		azSlider[e]	= setTimeout(function(){showImage(currentImage[e],nextImage[e],e)},azTime[e]);
		$(this).find("li").bind("click",function(){
			//如果点的是当前显示的，直接返回
			if($(this).hasClass("act"))
				return false
			//清setTimeout
			num = $(this).parent().parent().attr("num");
			window.clearTimeout(azSlider[num]);

			imgs=$(this).parent().parent().find("img");
			//取得当前图片
			index = $(this).siblings(".act").index();
			currentImage[num] = imgs.eq(index);
			//取得下一张图片
			index = $(this).index();
			count = $(this).siblings().length+1;
			index = index < (count) ? index : 0;
			nextImage[num] = imgs.eq(index);

			showImage(currentImage[num],nextImage[num],num);
		});
	});
}

function showImage(currentImage,nextImage,num)
{
	style1={opacity:"0"};
	style2={right:"+100%"};

	style=azEffect[num];
	switch (style)
	{
		case "1":
			style=style1;
			break;
		case "2":
			style=style2;
			break;
		default:
			style=style1;
			break;
	}

	nextImage.css("display","block").css("right","0").css("opacity",1);
	currentImage.css("z-index","2").animate(style,"slow", function() {
		currentImage.css({'display':'none','z-index':1});
		nextImage.css("z-index","2");

		currentImage = nextImage;
		parent=currentImage.parent().parent();
		imgs=parent.find("img");
		//小图选中
		index = currentImage.parent().index()
		parent.find("ul >li").removeClass().eq(index).addClass("act");
		//取得下一张图片
		count=imgs.length;
		index = index < (count-1) ? index+1 : 0;
		nextImage = imgs.eq(index).css("display","block");
		//间隔执行
		azSlider[num]=setTimeout(function(){showImage(currentImage,nextImage,num)},azTime[num]);
	});
}

//文字滚动
function eachRoll()
{
	$(".dp-roller").each(function(e) {
		if(!isNaN($(this).attr("num")))
			return true;

		height=$(this).wrapInner("<div class='dp-roll' />").attr("height");
		height = isNaN(height)?80:height;

		$(this).css("height",height+"px").attr("pos",height).attr("num",e).mouseenter(function(){
			num=$(this).attr("num");
			window.clearTimeout(azRoller[num]);
		}).mouseleave(function(){
				rollArea($(this));
			}).children().css("top",height+"px");

		rollArea($(this));
	});
}

function rollArea(o)
{
	speed=20;
	num=o.attr("num");
	pos=parseInt(o.attr("pos"));
	rollH=o.children().height();

	if(rollH+pos<-10)
		pos=parseInt($(this).attr("height"));

	pos--;

	o.attr("pos",pos).children().css("top",pos+"px");
	azRoller[num]=setTimeout(function(){rollArea(o)},speed);
}


//百度地图
function eachMap()
{
	$(".dp-maper").each(function(e){
		$(this).attr("id","bdmap"+e);
		lng=$(this).attr("lng");
		if(isNaN(lng))lng="";
		lat=$(this).attr("lat");
		if(isNaN(lng))lat="";
		mapShow("bdmap"+e,lng,lat);
	});
}

function mapShow(id,lng,lat)
{
	azMap = new BMap.Map(id);
	var marker;
	azMap.enableScrollWheelZoom();//启用滚轮放大缩小，默认禁用

	if(lat=="" || lng=="")
	{
		azMap.centerAndZoom("北京", 15);
	}else{
		var point = new BMap.Point(lng, lat);
		marker = new BMap.Marker(point);
		azMap.centerAndZoom(point, 15);
		azMap.addOverlay(marker);// 将标注添加到地图中
		//marker.setAnimation(BMAP_ANIMATION_BOUNCE);//跳动的动画
	}
}