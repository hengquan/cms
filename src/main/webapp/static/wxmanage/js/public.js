var dialog;

$(document).ready(function() {
    //百度地图
    $("#az-mapSet").click(function(){
        Lat=$(this).attr("lat");
        Lng=$(this).attr("lng");

        $("head").append("<script id='bdmapjs' type='text/javascript' src='http://api.map.baidu.com/getscript?v=1.4&ak=&services=&t=20130906090653'></script>");

        $.dialog({
            title:'设置地图坐标',
            content:'<div id="tmp-bdmap" style="width:600px;height:480px;overflow:hidden"><div style="margin:200px auto; text-align: center"><img src="/image/loading.gif" /></div></div><input type="hidden" id="map-lat" value="'+Lat+'"><input type="hidden" id="map-lng" value="'+Lng+'">',
            ok: function(){
                Lat=$("#map-lat").val();
                Lng=$("#map-lng").val();
                if(Lat!="" || Lat!="0" || Lng!="" || Lng!="0")
                    $("#az-mapSet").attr("lat",Lat).attr("lng",Lng).val(Lat+","+Lng);
            },
            width:600,height:480,padding:0,lock:true
        });

        setTimeout(function(){mapShow("tmp-bdmap",Lng,Lat)},1500);
    })

	//上传控件
	$(".az-upload").each(function(){
		var o=$(this).attr("target");
		var editor;
		KindEditor.ready(function(K) {
			var editor = K.editor({
				allowFileManager : false,
				uploadJson : "/keditor/php/upload_json.php?action=resize"
			});

			K('.az-upload').click(function() {
				editor.loadPlugin('image', function() {
					editor.plugin.imageDialog({
						showRemote : false,
						imageUrl : K('#'+o).val(),
						clickFn : function(url, title, width, height, border, align) {
							K('#'+o).val(url);
							editor.hideDialog();
						}
					});
				});
			});
		});
	});
	$(".az-upload2").each(function(){
		var o=$(this).attr("target");
		var editor;
		KindEditor.ready(function(K) {
			var editor = K.editor({
				allowFileManager : false,
				uploadJson : "/keditor/php/upload_json.php?action=resize"
			});

			K('.az-upload2').click(function() {
				editor.loadPlugin('image', function() {
					editor.plugin.imageDialog({
						showRemote : false,
						imageUrl : K('#'+o).val(),
						clickFn : function(url, title, width, height, border, align) {
							K('#'+o).val(url);
							editor.hideDialog();
						}
					});
				});
			});
		});
	});

	//上传并裁切
	$("#az-uploadCut").click(function(){
		cutObj=$(this).attr("target");
		cutObj=$("#"+cutObj);
		var imgUrl=cutObj.val();
		var imgW=$(this).attr("width");
		var imgH=$(this).attr("height");
		var imgSource=$(this).attr("source");
		if(!parseInt(imgW) || !parseInt(imgH))
		{
			alert("参数错误！");
			return false;
		}

		dialogContent='<input type="hidden" id="jcropX"><input type="hidden" id="jcropY"><input type="hidden" id="jcropW"><input type="hidden" id="jcropH"><input type="hidden" id="imgW" value="'+imgW+'"><input type="hidden" id="imgH" value="'+imgH+'">';
		if(imgUrl!="" && imgUrl!=undefined)
		{
			dialogContent+='<table id="jcropViewCase"><tr><td>当前图片</td><td align="right"><input type="button" value="编辑图片" onclick="changeJcropCase()"></td></tr><tr><td colspan="2"><img src="'+imgUrl+'" /></td></tr></table>';
			dialogContent+='<table id="jcropEditCase" style="display: none">';
			//预加载图片
			imgReady(imgUrl,function(){});
			sourceImage = '<img id="sourceImage" src="'+imgUrl+'" />';
			previewImage= '<img id="previewImage" src="'+imgUrl+'" />';
		}else{
			dialogContent+='<table>';
			sourceImage ='';
			previewImage='';
		}
		dialogContent+='<tr><td colspan="2"><input type="button" id="uploadCutImageBtn" value="上传图片" /></td></tr><tr><td>原始图片</td><td>预览图片</td></tr><tr><td width="300" height="300"><div class="jcrop-bimg">'+sourceImage+'</div></td><td valign="top"><div class="jcrop-simg">'+previewImage+'</div><br><input type="button" value="保存图片" onclick="checkCoords()" /></td></tr></table>';

		//延迟加载是为了让图片先预加载一下，得到图片的高宽，这样dialog就可以居中显示
		setTimeout(function(){
			dialog=$.dialog(
				{
					title:'编辑图片',
					content:dialogContent,
					lock:true,padding:5
				}
			);

			//绑定上传事件
			var editor = KindEditor.editor({
				allowFileManager : false,
				uploadJson : "/keditor/php/upload_json.php?action=resize"
			});

			KindEditor('#uploadCutImageBtn').click(function() {
				editor.loadPlugin('image', function() {
					editor.plugin.imageDialog({
						showRemote : false,
						clickFn : function(url, title, width, height, border, align) {
							//保留原始图片
							if(imgSource!=undefined)
								$("#"+imgSource).val(url);
							//判断是否已经有过jcrop，如果有过，要先注销
							if($('#sourceImage').next().length>0)
							{
								jcrop_api.destroy();
							}
							$(".jcrop-bimg").html('<img id="sourceImage" src="'+url+'" />');
							$(".jcrop-simg").html('<img id="previewImage" src="'+url+'" />');

							setTimeout("setJcropImg()",300);

							editor.hideDialog();
						}
					});
				});
			});
		},200);
	});
})

//显示地图
function mapShow(id,lng,lat)
{
	var map = new BMap.Map(id);
	var marker;
	map.enableScrollWheelZoom();//启用滚轮放大缩小，默认禁用
	map.addEventListener("click", showInfo);

	if(lat=="" || lat=="0" || lng=="" || lng=="0")
	{
		var _city=$("#cityId").find("option:selected").text();
		if(_city!="" && _city!="请选择城市")
			map.centerAndZoom(_city, 12);
		else
			map.centerAndZoom("北京", 12);
	}else{
		var point = new BMap.Point(lng, lat);
		marker = new BMap.Marker(point);
		map.centerAndZoom(point, 15);
		map.addOverlay(marker);//将标注添加到地图中
		marker.setAnimation(BMAP_ANIMATION_BOUNCE);//跳动的动画
	}
	function showInfo(e){
		lng=e.point.lng;
		lat=e.point.lat;
		$("#map-lng").val(lng);
		$("#map-lat").val(lat);

		map.removeOverlay(marker);
		var point = new BMap.Point(lng, lat);
		marker = new BMap.Marker(point);
		map.addOverlay(marker);// 将标注添加到地图中
		marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
	}
}



//
function changeJcropCase()
{
	$("#jcropViewCase").css("display","none");
	$("#jcropEditCase").css("display","");
	setJcropImg();
}

