function orderInfo() {
	var url = "order_Info"
	var orderSn = $("#orderSn").html();
	var on_orderTime = $("#orderTime").html();
	var sumPrice = 0;
	$.ajax({
		url : url,
		async : false,
		type : "POST",
		data :  "orderSn="+orderSn,
		success : function(data) {

			$("#order_Infos").empty();
			$.each(data, function(i) {
					
				$("#order_Infos").append("<tr>");
				$("#order_Infos").append("<td class='hidden-phone'>"+data[i].id+"</td>");
				$("#order_Infos").append("<td>"+data[i].dishesName+"</td>");
				$("#order_Infos").append("<td><input type='number' value='"+data[i].dishesNum+"' class='form-control max100' /></td>");
				$("#order_Infos").append("</td>");
				$("#order_Infos").append("<td>￥"+data[i].dishesPrice+"</td>");
				$("#order_Infos").append("<td><button class='btn btn-default'> <i class='icon-trash'></i></button></td>");
				$("#order_Infos").append("</tr>");
				
				sumPrice += data[i].dishesPrice*data[i].dishesNum;
				
			});
			
			$("#SumPrice").html(sumPrice);
			$("#orderId").html(orderSn);
			$("#on_orderTime").html(on_orderTime);
			
		}
	});
}



