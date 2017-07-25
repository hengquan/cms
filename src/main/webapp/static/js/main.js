$(document).ready(function() {

	$("#shrink").click(function() {
		if ($(this).hasClass("act"))
			$(".sub-nav").show();
		else
			$(".sub-nav").hide();
		$(this).toggleClass("act");
	});// 左侧二级导航 点击出现或消失

	$("#mark").click(function() {
		$(".mark").show();
	});// 消息遮罩层 点击出现或消失

	$(".nav ul a:nth-child(1)").click(function() {
		$(".nav ul a:nth-child(1)").addClass("act");
		$(".nav ul a:nth-child(1)").siblings().removeClass("act");
		$(".sub-nav ul:nth-child(1)").siblings().hide();
		$(".sub-nav ul:nth-child(1)").show();
	});// 导航菜单切换

	$(".nav ul a:nth-child(2)").click(function() {
		$(".nav ul a:nth-child(2)").addClass("act");
		$(".nav ul a:nth-child(2)").siblings().removeClass("act");
		$(".sub-nav ul:nth-child(2)").siblings().hide();
		$(".sub-nav ul:nth-child(2)").show();
	});// 导航菜单切换

	$(".nav ul a:nth-child(3)").click(function() {
		$(".nav ul a:nth-child(3)").addClass("act");
		$(".nav ul a:nth-child(3)").siblings().removeClass("act");
		$(".sub-nav ul:nth-child(3)").siblings().hide();
		$(".sub-nav ul:nth-child(3)").show();
	});// 导航菜单切换

	$(".nav ul a:nth-child(4)").click(function() {
		$(".nav ul a:nth-child(4)").addClass("act");
		$(".nav ul a:nth-child(4)").siblings().removeClass("act");
		$(".sub-nav ul:nth-child(4)").siblings().hide();
		$(".sub-nav ul:nth-child(4)").show();
	});// 导航菜单切换

	$(".nav ul a:nth-child(5)").click(function() {
		$(".nav ul a:nth-child(5)").addClass("act");
		$(".nav ul a:nth-child(5)").siblings().removeClass("act");
		$(".sub-nav ul:nth-child(5)").siblings().hide();
		$(".sub-nav ul:nth-child(5)").show();
	});// 导航菜单切换

	$(".nav ul a:nth-child(6)").click(function() {
		$(".nav ul a:nth-child(6)").addClass("act");
		$(".nav ul a:nth-child(6)").siblings().removeClass("act");
		$(".sub-nav ul:nth-child(6)").siblings().hide();
		$(".sub-nav ul:nth-child(6)").show();
	});// 导航菜单切换

	$(".nav ul a:nth-child(7)").click(function() {
		$(".nav ul a:nth-child(7)").addClass("act");
		$(".nav ul a:nth-child(7)").siblings().removeClass("act");
		$(".sub-nav ul:nth-child(7)").siblings().hide();
		$(".sub-nav ul:nth-child(7)").show();
	});// 导航菜单切换

	$(".nav ul a:nth-child(8)").click(function() {
		$(".nav ul a:nth-child(8)").addClass("act");
		$(".nav ul a:nth-child(8)").siblings().removeClass("act");
		$(".sub-nav ul:nth-child(8)").siblings().hide();
		$(".sub-nav ul:nth-child(8)").show();
	});// 导航菜单切换

	$(".nav ul a:nth-child(9)").click(function() {
		$(".nav ul a:nth-child(9)").addClass("act");
		$(".nav ul a:nth-child(9)").siblings().removeClass("act");
		$(".sub-nav ul:nth-child(9)").siblings().hide();
		$(".sub-nav ul:nth-child(9)").show();
	});// 导航菜单切换

	$(".nav ul a:nth-child(10)").click(function() {
		$(".nav ul a:nth-child(10)").addClass("act");
		$(".nav ul a:nth-child(10)").siblings().removeClass("act");
		$(".sub-nav ul:nth-child(10)").siblings().hide();
		$(".sub-nav ul:nth-child(10)").show();
	});// 导航菜单切换

	$(".nav2 ul a:nth-child(1)").click(function() {
		$(".nav2 ul a:nth-child(1)").addClass("act");
		$(".nav2 ul a:nth-child(1)").siblings().removeClass("act");
		$(".sub-nav ul:nth-child(1)").siblings().hide();
		$(".sub-nav ul:nth-child(1)").show();
	});// 导航菜单切换

	$(".nav2 ul a:nth-child(2)").click(function() {
		$(".nav2 ul a:nth-child(2)").addClass("act");
		$(".nav2 ul a:nth-child(2)").siblings().removeClass("act");
		$(".sub-nav ul:nth-child(2)").siblings().hide();
		$(".sub-nav ul:nth-child(2)").show();
	});// 导航菜单切换

	$(".nav2 ul a:nth-child(3)").click(function() {
		$(".nav2 ul a:nth-child(3)").addClass("act");
		$(".nav2 ul a:nth-child(3)").siblings().removeClass("act");
		$(".sub-nav ul:nth-child(3)").siblings().hide();
		$(".sub-nav ul:nth-child(3)").show();
	});// 导航菜单切换

});

function validatePhone(phone,number1) {
	// || !(/^([0-9]{3,4}-)?[0-9]{7,8}$/.test(phone))

	if (!(/^1[3|4|5|7|8]\d{9}$/.test(phone))) {
		if (number1 == "contactTel") {
			alert("联系电话有误，请重填！");
			return false;
		}else if (number1 == "hotline") {
			alert("客户电话有误，请重填！");
			return false;
		}
		else if (number1 == "bnkPayeetel") {
			alert("收款电话有误，请重填！");
			return false;
		}
		return false;

	}
	return true;
	/*
	 * var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/; var isMob =
	 * /^1[3|4|5|7|8]\d{9}$/; if (!(isMob.test(phone)) ||
	 * !(isPhone.test(phone))) { alert("电话有误，请重填！"); return false; } else {
	 * alert("vvvvv"); return true; }
	 */
}
