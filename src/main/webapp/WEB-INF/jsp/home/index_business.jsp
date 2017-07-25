<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/sessions.jsp"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/jsp/inc/head_bootstrap.jsp"%>
<link href="${appRoot}/static/assets/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css" >
<!-- Custom styles for this template -->
<link href="${appRoot}/static/css/style.css" rel="stylesheet">
<link href="${appRoot}/static/css/style-responsive.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${appRoot}/static/css/jquery.editable-select.min.css" />

<title>${appTitle}</title>
</head>
<body>
	<section id="container" class="">
      <!--header start-->
      <%@ include file="/WEB-INF/jsp/inc/header_business.jsp"%>
      <!--header end-->
      
      <!--sidebar start-->
      <%@ include file="/WEB-INF/jsp/inc/sidebar_business.jsp"%>
      <!--sidebar end-->
      
      <!--main content start-->
      <section id="main-content">
          <section class="wrapper">
                <div class="panel clearfix">
                  <header class="panel-heading pull-left no-border">首页</header>
                  <div class="shili pull-right state-overview">
                    <span><i class="red"></i>&nbsp;未支付</span>
                    <span><i class="blue"></i>&nbsp;下单成功</span>
                    <span><i class="yellow"></i>&nbsp;已接单</span>
                    <span><i class="terques"></i>&nbsp;已完成</span>
                  </div>
                </div>
                <!--餐桌 start-->
                <div class="row state-overview">

                	<c:forEach items="${tables}" var="tb">
	                	<div class="col-lg-3 col-sm-3 col-xs-6">
	                        <section class="panel gate">
	                             <a class="panel-heading gate-heading clearfix blue" data-toggle="modal" href="#">
	                              <h1 class="pull-left">${tb.code}<small>桌</small></h1>
	                              			 <p class="pull-right"></p>
	                           	 </a>
	                            <ul class="gate-list">
	                            	<c:forEach items="${tb.orderList}" var="t">
	                            	<c:if test="${t.orderStatus == '00'}">
	                            		<li><a class="weifuk clearfix" data-toggle="modal" href="#" onclick="doMyModel('${t.orderStatus}','${t.orderSource}','${t.orderRemark}','${t.orderId}','${tb.code}','<fmt:formatDate value="${t.orderTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>','${t.orderSn}');">
				                                <b class="pull-right"><i class="icon-remove"></i>&nbsp;未支付</b>
				                                ${t.orderSn}
                              			</a></li>
                              		</c:if>
                              		<c:if test="${t.orderStatus == '01'}">
	                            		<li><a class="yixiadan clearfix" data-toggle="modal" href="#" onclick="doMyModel('${t.orderStatus}','${t.orderSource}','${t.orderRemark}','${t.orderId}','${tb.code}','<fmt:formatDate value="${t.orderTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>','${t.orderSn}');">
				                                <b class="pull-right"><i class="icon-remove"></i>&nbsp;下单成功</b>
				                                ${t.orderSn}
                              			</a></li>
                              		</c:if>
                              		<c:if test="${t.orderStatus == '02'}">
	                            		<li><a class="yijiedan clearfix" data-toggle="modal" href="#" onclick="doMyModel('${t.orderStatus}','${t.orderSource}','${t.orderRemark}','${t.orderId}','${tb.code}','<fmt:formatDate value="${t.orderTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>','${t.orderSn}');">
				                                <b class="pull-right"><i class="icon-ok"></i>&nbsp;已接单</b>
				                                ${t.orderSn}
                              			</a></li>
                              		</c:if>	
                              		<c:if test="${t.orderStatus == '04'}">
	                            		<li><a class="yifuk clearfix" data-toggle="modal" href="#" onclick="doMyModel('${t.orderStatus}','${t.orderSource}','${t.orderRemark}','${t.orderId}','${tb.code}','<fmt:formatDate value="${t.orderTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>','${t.orderSn}');">
				                                <b class="pull-right"><i class="icon-ok"></i>&nbsp;已完成</b>
				                                ${t.orderSn}
                              			</a></li>
                              		</c:if>	
                              			
	                                </c:forEach>
	                            </ul>
	                        </section>
	                    </div>
                	</c:forEach>
                </div>
                <!--餐桌 end-->
              <!-- page end-->
          </section>
      </section>
      <!--main content end-->
 	 </section> 
 	 
 	 
 	 <!-- Modal -->
	 <div class="modal fade bs-example-modal-lg" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	      <div class="modal-dialog modal-lg">
	          <div class="modal-content">
	              <div class="modal-header">
	                  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                  <h4 class="modal-title">订单详情</h4>
	              </div>
	              <div class="modal-body">
	                <div class="log-info">
	                    <div class="row">
	                      <div class="col-sm-6 text-left">桌号：<strong id="tableCode"></strong></div>
	                      <div class="col-sm-6 text-left" >下单时间&nbsp;:&nbsp;<strong id="modelOrderTime"></strong></div>
	                      <div class="col-sm-12">订单号：<strong id="orderSn"></strong></div>
	                      <div class="col-sm-12">添加菜品：
	                          <div class="input-group">
	                           <select id="editable-select" class="form-control"> <!-- //id="editable-select"  -->
								   	<c:forEach items="${wxmDishesList}" var="w">
								   		 <option value="${w.id}">${w.name}</option>
								   	</c:forEach>
								</select>
	                            <span class="input-group-btn">
	                              <button onclick="addOrderDetail();" class="btn btn-info" type="button"><i class="icon-plus"></i></button>
	                            </span>
	                          </div>
	                      </div>
	                    </div>
	                </div>
	                <div class="table-box">
	                  <table class="table" id="dishesTables"></table>
	                  </div>
	                  <div class="row" style="margin-top:10px;">
	                  	<div class="col-sm-6 text-left"><strong id="hdmc"></strong></div>
	                  	<div class="col-sm-6 text-right"><strong id="youhui"></strong></div>
	                  </div>
	                  <div class="row" style="margin-top:10px;">
	                  	<div class="col-sm-4 text-left"><strong id="jfgz"></strong></div>
	                  	<div class="col-sm-4 text-center"><strong id="kcjf"></strong></div>
	                  	<div class="col-sm-4 text-right"><strong id="kcje"></strong></div>
	                  </div>
	                  <h4 class="heji text-right">合计：<strong id="allPrice" style="color:red;"></strong></h4>
	                  <div class="ctrl-box">
	                      <button type="button" class="btn btn-shadow btn-info crl-btn" id="OrderjieDan" onclick="doJieDan();">接单</button>
	                      <button type="button" class="btn btn-shadow btn-info crl-btn" id="OrderjieSun" onclick="jieSuan();">结算</button>
	                      <button type="button" class="btn btn-shadow btn-default crl-btn" data-dismiss="modal" aria-hidden="true">取消</button>
	                  </div>
	              </div>
	          </div><!-- /.modal-content -->
	      </div><!-- /.modal-dialog -->
	 </div><!-- /.modal -->
	 
<!-- 	wxm_dishes_id,dishes_type_id,dishes_name -->
<!-- 	dishes_num,dishes_price,discount_price,dishes_remark, -->
<!-- 	pint_flag-->
	 <form action="${appRoot}/home/jieDan" id="jieDanForm" method="POST">
	 	<input type="hidden" id="twxmOrderId" name="wxmOrderId" />
	 	<input type="hidden" id="tdiningtableCode" name="diningtableCode" />
	 	<input type="hidden" id="torderSn" name="orderSn" />
	 	<input type="hidden" id="torderTime" name="orderTime" />
	 	<input type="hidden" id="torderRemark" name="orderRemark" />
	 	<input type="hidden" id="torderSource" name="orderSource" />
	 </form>
	 
	 <form id="scoreForm">
	 	<input type="hidden" id="score_value" name="scoreVal"/>
	 	<input type="hidden" id="order_id" name="orderId"/>
	 	<input type="hidden" id="score_rule" name="score_rule"/>
	 	<input type="hidden" id="score_money" name="score_money"/>
	 	<input type="hidden" id="money" name="money"/>
	 </form>
	 
	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>
	<script type="text/javascript" src="${appRoot}/static/js/jquery.editable-select.min.js"></script>
	<script type="text/javascript">
// 	$('#editable-select').editableSelect({
// 		effects: 'slide'
// 	});

	function jieSuan(){
		
// 		var oi = $('#orderIdValue').val();
		$.ajax({
			type:'POST',
			data: $('#scoreForm').serialize(),
			url:wxmp.appRoot+'/home/jieSuan',
			dataType:'json',
			success:function(data){
				window.location.reload();
			}
		});	
	}

	function doJieDan(){
		$('#jieDanForm').submit();
	}
	
	function doMyModel(orderStatus,orderSource,orderRemark,orderId,tableCode,orderTime,orderSn){
		if(orderStatus == '02' || orderStatus == '03' || orderStatus == '04'){
			$("#OrderjieDan").attr("disabled","disabled");
		}else{
			$("#OrderjieDan").removeAttr("disabled");
		}
		if(orderStatus == '00'||orderStatus == '01'||orderStatus == '04'){
			$("#OrderjieSun").attr("disabled","disabled");
		}else{
			$("#OrderjieSun").removeAttr("disabled");
		}
		$('#twxmOrderId').val(orderId);
		$('#tdiningtableCode').val(tableCode);
		$('#torderSn').val(orderSn);
		$('#torderTime').val(orderTime);
		$('#torderRemark').val(orderRemark);
		$('#torderSource').val(orderSource);
		/*-----------------*/
		$('#orderIdValue').val('');
		$('#tableCode').html(tableCode);
		$('#modelOrderTime').html(orderTime);
		$('#orderSn').html(orderSn);
		$('#orderIdValue').val(orderId);
		getOrderDetail(orderId);
		getOrderDetailAllPrice(orderId);
		getScoreAndActivity(orderTime);
		var $modal = $('#myModal');
		$('#modal-title').text("订单详情");
		$modal.modal();
	}
	
	function getScoreAndActivity(orderTime){
		$('#hdmc').html('');
		$('#youhui').html('');
		$('#jfgz').html('');
		$('#kcje').html('');
		$('#kcjf').html('');
		$.ajax({
			type:'POST',
			data:{"orderId":$('#orderIdValue').val(),"orderTime":orderTime},
			url:wxmp.appRoot+'/home/getScoreAndActivity',
			dataType:'json',
			success:function(data){
				if(data != null){
					if(data.activityName !=null && data.discountPrice != null){
						$('#hdmc').html(data.activityName);
						$('#youhui').html("活动优惠 ：￥"+data.discountPrice);
					}
					if(data.scoreRule !=null && data.scorePrice !=null && data.scoreVal != null){
						$('#jfgz').html("积分规则 ："+data.scoreRule);
						$('#kcje').html("扣除金额 ：￥"+data.scorePrice);
						$('#kcjf').html("扣除积分 ："+data.scoreVal);
					}
					 	$('#score_value').val(data.scoreVal);
					 	$('#order_id').val($('#orderIdValue').val());
					 	$('#score_rule').val(data.scoreRule);
					 	$('#score_money').val(data.scorePrice);
					getOrderDetailAllPrice($('#orderIdValue').val());
				}
			}
		});	
	}
	
	function getOrderDetailAllPrice(orderId){
		$.ajax({
			type:'POST',
			data:{"orderId":orderId},
			url:wxmp.appRoot+'/home/getOrderDetailAllPrice',
			dataType:'json',
			success:function(data){
				$('#allPrice').html(data);
				$('#money').val(data);
			}
		});	
	}
		
	
	function getOrderDetail(orderId){
		$.ajax({
			type:'POST',
			data:{"orderId":orderId},
			url:wxmp.appRoot+'/home/getorderDetail',
			dataType:'json',
			success:function(data){
				var strs = "<thead><tr><th class='hidden-phone'>#</th>"
				+"<th>菜品名称</th><th>数量</th><th>单价</th><th>操作</th></tr></thead><tbody>";
				for(var i=0; i<data.length;i++){
					strs+="<tr><td class='hidden-phone'>"+(i+1)+"</td>"
							+"<td>"+data[i].dishesName+"</td>"
							+"<td><input type='number' value='"+data[i].dishesNum+"' class='form-control'  min='1' max='100' id='numDishes'  onchange='updateDishesNum(this.value,\""+data[i].id+"\");'/></td>" 
							+"<td>"+data[i].dishesPrice+"</td><td><button onclick='deleteOrderDetail(\""+data[i].id+"\",\""+orderId+"\");' class='btn btn-default'><i class='icon-trash'></i></button></td></tr>";
				}
				strs+="</tbody>";
				$('#dishesTables').html(strs);
			}
		});
	}
	
		function updateDishesNum(numValue,orderDetailId){
			$.ajax({
				type:'POST',
				data:{"dishesNum":numValue,'orderDetailId':orderDetailId},
				url:wxmp.appRoot+'/home/updateDishesNum',
				dataType:'json',
				success:function(data){
					if(data.code == 'ok'){
						getOrderDetail($('#orderIdValue').val());
						getOrderDetailAllPrice($('#orderIdValue').val());
					}
				}
			});
		}
	
		function addOrderDetail(){
			$.ajax({
				type:'POST',
				data:{"dishesid":$('#editable-select').val(),'orderId':$('#orderIdValue').val()},
				url:wxmp.appRoot+'/home/addOrderDetail',
				dataType:'json',
				success:function(data){
					if(data.code == 'ok'){
						getOrderDetail($('#orderIdValue').val());
						getOrderDetailAllPrice($('#orderIdValue').val());
					}
				}
			});
		}
	
	
	function deleteOrderDetail(orderDetailId,orderId){
		$.ajax({
			type:'POST',
			data:{"orderDetailId":orderDetailId},
			url:wxmp.appRoot+'/home/deleteOrderDetail',
			dataType:'json',
			success:function(data){
				if(data.code == 'ok'){
					getOrderDetail(orderId);
					getOrderDetailAllPrice(orderId);
				}
				
			}
		});	
	}
	
	
	</script>
   <input type="hidden" id="orderIdValue"/> 
</body>
</html>