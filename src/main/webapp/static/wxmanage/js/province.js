var map = null;
var localSearch = null;

$(function() {
	baiduMapInit();

	searchByStationName();
});

function baiduMapInit() {
	map = new BMap.Map("container");
	map.centerAndZoom("福州", 12);
	map.enableScrollWheelZoom(); // 启用滚轮放大缩小，默认禁用
	map.enableContinuousZoom(); // 启用地图惯性拖拽，默认禁用

	map.addControl(new BMap.NavigationControl()); // 添加默认缩放平移控件
	map.addControl(new BMap.OverviewMapControl()); // 添加默认缩略地图控件
	map.addControl(new BMap.OverviewMapControl({
		isOpen : true,
		anchor : BMAP_ANCHOR_BOTTOM_RIGHT
	})); // 右下角，打开

	localSearch = new BMap.LocalSearch(map);
	localSearch.enableAutoViewport(); // 允许自动调节窗体大小
}

function searchByStationName() {
	map.clearOverlays();// 清空原来的标注
	var keyword = document.getElementById("name").value;
	localSearch.setSearchCompleteCallback(function(searchResult) {
		var poi = searchResult.getPoi(0);
		document.getElementById("longitude").value = poi.point.lng;
		document.getElementById("dim").value = poi.point.lat;
		map.centerAndZoom(poi.point, 13);
		var marker = new BMap.Marker(new BMap.Point(poi.point.lng,
				poi.point.lat)); // 创建标注，为要查询的地方对应的经纬度
		map.addOverlay(marker);
		// marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
	});
	localSearch.search(keyword);
}

function doSubmit(submitPage) {
	if (isValidate()) {
		$('#updateform').submit();
	}
}

function isValidate() {
	var title = $('#name').val().trim();
	if ("" == title) {
		alert("名称不能为空!");
		return false;
	}
	var longitude = $('#longitude').val().trim();
	if ("" == longitude) {
		alert("经度不能为空!");
		return false;
	}
	var dim = $('#dim').val().trim();
	if ("" == dim) {
		alert("维度不能为空!");
		return false;
	}
	return true;
}