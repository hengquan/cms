<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/taglib.jsp"%>

<%-- <jsp:include page="/sysmanage/jsp/findhead.jsp" /> --%>

<title>自定义菜单</title>
<%@ include file="inc/head.jsp"%>

<link href="${appRoot}/static/wxmanage/css/amazeui.min.css" rel="stylesheet" type="text/css" />
<link href="${appRoot}/static/wxmanage/css/font-awesome.min.css" rel="stylesheet" type="text/css" />

<script src="${appRoot}/static/wxmanage/js/amazeui.min.js" type="text/javascript"></script>

<script type="text/javascript">
	$(function(){
	    $(".wx-menu-item-1").hover(
		     function(){
		         $(this).css("cursor","pointer");
		         var bgColor =  $(this).css("background-color");
		         if(bgColor != "rgb(231, 231, 235)"){
		        	 $(this).css("background-color","#f4f5f9");
			         $(this).children('td').eq(1).children(".wx-menu-icon-div").css("display","block");
		         }
		     },
		     function(){
		         $(this).css("cursor","pointer");
		         var bgColor =  $(this).css("background-color");
		         if(bgColor != "rgb(231, 231, 235)"){
		        	 $(this).css("background-color","#fff");
			         $(this).children('td').eq(1).children(".wx-menu-icon-div").css("display","none");
		         }
		     }
     	);
	    
	    $(".wx-menu-item-1").click(function(){
	    	var curItem = $(this);
	    	
	        $(".wx-menu-item-1").each(function(){
	        	 $(this).css("background-color","#fff");
		         $(this).children('td').eq(1).children(".wx-menu-icon-div").css("display","none");
	    	}); 
	        
	        curItem.css("background-color","#e7e7eb");
	        curItem.children('td').eq(1).children(".wx-menu-icon-div").css("display","block");
	    });
	    
	});    
	
	function doAddOne(){
		 $.ajax({
			type:"post",  
			url:"${appRoot}/wxmanage/jsp/isAddFirstLevelMenu.do",
			data:"level=0",
			success:function(data){
				if(data == "Y") {
					$('#menu-id').val('0');
					$('#menu-parentId').val('0');
					$('#menu-name').val('');
					$('#menu-level').val('0');
					$('#actionType').val('insert');
					$('#edit-modal').modal('open');
				} else if(data == "N") {
					$('#alert-modal-content').text('一级菜单最多可创建3个！');
					$('#alert-modal').modal('open');
				}
			 }, 
			error:function() {
				alert('Ajax调用异常!');
			}
		 });
	}
	
	function doAddTwoLevel(id){
		 $.ajax({
			type:"post",  
			url:"${appRoot}/wxmanage/jsp/isAddSecondLevelMenu.do",
			data:"parentId="+id,
			success:function(data){
				if(data == "Y") {
					$('#menu-id').val('0');
					$('#menu-parentId').val(id);
					$('#menu-name').val('');
					$('#menu-level').val('1');
					$('#actionType').val('insert');
					$('#edit-modal').modal('open');
				} else if(data == "N") {
					$('#alert-modal-content').text('二级菜单最多可创建5个！');
					$('#alert-modal').modal('open');
				}
			 }, 
			error:function() {
				alert('Ajax调用异常!');
			}
		 });
	}
	
	function doEditMenu(id,menuName,menuLevel,parentId){
		$('#menu-id').val(id);
		$('#menu-parentId').val(parentId);
		$('#menu-name').val(menuName);
		$('#menu-level').val(menuLevel);
		$('#actionType').val('edit');
		
		$('#edit-modal').modal('open');
	}
	
	function doCloseEditModal(){
		$('#edit-modal').modal('close');
	}
	
	function doDeleteMenuItem(id){
		$("#menu-del-id").val(id);
		$('#confirm-modal').modal({
	        relatedTarget: this,
	        onConfirm: function(options) {
	        	doCloseModal('confirm-modal');
	        	
	          	doDeleteMenuAjax($("#menu-del-id").val());
	        },
	        onCancel: function() {
	          //alert('算求，不弄了');
	        }
	     });
	}
	
	function doDeleteMenuAjax(id){
		 $.ajax({
			type:"post",  
			url:"${appRoot}/wxmanage/jsp/deleteMenuItem.do",
			data:"id="+id,
			success:function(data){
				if(data == "Y") {
					window.location.reload();
				} else if(data == "N") {
					$('#alert-modal-content').text('删除菜单有误，请检查！');
					$('#alert-modal').modal('open');
				}
			 }, 
			error:function() {
				alert('Ajax调用异常!');
			}
		 });
	}
	
	function doCloseModal(id){
		$('#'+id).modal('close');
	}
	
	function doSaveMenu(){
		var id = $("#menu-id").val();
		var menuName = $("#menu-name").val();
		var menuLevel = $("#menu-level").val();
		var actionType = $("#actionType").val();
		var menuParentId = $("#menu-parentId").val();
		
		if(""==menuName.trim()){
			alert('菜单名称不能为空！');
			
			return;
		}
		
		$.ajax({
			type:"post",  
			url:"${appRoot}/wxmanage/jsp/saveMenuItem.do",
			data:"id="+id+"&menuName="+menuName+"&menuLevel="+menuLevel+"&actionType="+actionType+"&menuParentId="+menuParentId,
			success:function(data){
				if(data == "Y") {
					window.location.reload();
				} else if(data == "N") {
					$('#alert-modal-content').text('保存菜单有误，请检查！');
					$('#alert-modal').modal('open');
				}
			 }, 
			error:function() {
				alert('Ajax调用异常!');
			}
		});
	}
	
	function doSelectedMenuItem(level,id,index){
		$('#action-no-data').hide();
		$('#action-have-second-menu').hide();
		$('#action-content-div').hide();
		
		if(0==level && $(".js-second-menu_"+index).size()>0){
			$('#action-have-second-menu').show();
		}else{
			initActionContent();
			
			$.ajax({
				type:"post",  
				url:"${appRoot}/wxmanage/jsp/getMenuItem.do?rn="+Math.random(),
				data:"id="+id,
				success:function(data){
					if(data != "") {
						data = eval("(" + data + ")");
						$("#action-link").val(data.link);
						$("#action-menu-id").val(data.id);
						if(data.type==0){
							//$("#contentType0").attr('checked','true');
							document.getElementById("contentType1").checked=false;
							document.getElementById("contentType0").checked=true;
							$("#material-select").show();
							$("#keyword-select").hide();
						}else{
							//$("input[name=contentType][value=1]").checked = true;
							//$("input:radio[value=1]").attr('checked','true');
							//$("#contentType0").attr('checked','false');
							//$("#contentType1").attr('checked','true');
							//jQuery坑爹呀
							document.getElementById("contentType0").checked=false;
							document.getElementById("contentType1").checked=true;
							$("#material-select").hide();
							$("#keyword-select").show();
						}
						
					} else {
						$('#alert-modal-content').text('菜单动作有误，请检查！');
						$('#alert-modal').modal('open');
					}
				 }, 
				error:function() {
					alert('Ajax调用异常!');
				}
			});
			
			$('#action-content-div').show();
		}
	}
	
	function initActionContent(){
		$("#action-link").val("");
	}
	
	function doSaveActionInfo(){
		var id = $("#action-menu-id").val();
		var contentType = $('input[name="contentType"]:checked').val();
		var actionLink = $("#action-link").val();
		if(''==$.trim(actionLink)){
			alert('请输入连接地址或关键字!');
			return false;
		}
		
		$.ajax({
			type:"post",  
			url:"${appRoot}/wxmanage/jsp/saveMenuAction.do",
			data:"id="+id+"&contentType="+contentType+"&actionLink="+actionLink,
			success:function(data){
				if(data == "Y") {
					//window.location.reload();
					alert("保存成功!");
				} else if(data == "N") {
					$('#alert-modal-content').text('保存菜单动作有误，请检查！');
					$('#alert-modal').modal('open');
				}
			 }, 
			error:function() {
				alert('Ajax调用异常!');
			}
		});
	}
	
	function doSelectContentType(t){
		if(0==t){
			$("#material-select").show();
			$("#keyword-select").hide();
		}else{
			$("#material-select").hide();
			$("#keyword-select").show();
		}
	}
	
	function doOpenKeyword(){
		$('#keyword-modal').modal('open');
	}
	
	function doOpenMaterial(){
		$('#material-modal').modal('open');
	}
	
	function doSelectMaterial(articleType,articleId){
		var url = "http://wl.weechao.com/wxmp.ql/article/analysis.do?articleType="+articleType+"&articleId="+articleId;
		
		$("#action-link").val(url);
		
		$('#material-modal').modal('close');
	}
	
	function doSelectKeyword(keyword){
		$("#action-link").val(keyword);
		
		$('#keyword-modal').modal('close');
	}
	
	function doSortOne(){
		$('#one-sort-modal').modal('open');
	}
	
	function doSaveOneSort(btn){
		var vls = "";
		var ids = "";
		$(".js_one").each(function(){
			var vl = $(this).val();
			if(""==vl.trim()){
				alert("请输入顺序号！");
				return;
			}
			var id = this.id;
			id = id.replace("one_","");
			
			if(""==ids){
				ids =  id;
				vls = vl;
			}else{
				ids = ids + "," + id;
				vls = vls + "," + vl;
			}
		});
		
		btn.value="Saving...";
		btn.disabled = true;
		
		$.ajax({
			type:"post",  
			url:"${appRoot}/wxmanage/jsp/saveMenuSort.do",
			data:"ids="+ids+"&vls="+vls,
			success:function(data){
				if(data == "Y") {
					window.location.reload();
				} else if(data == "N") {
					$('#alert-modal-content').text('保存菜单顺序有误，请检查！');
					$('#alert-modal').modal('open');
				}
			 }, 
			error:function() {
				alert('Ajax调用异常!');
			}
		});
	}
</script>
</head>

<body>
	<div style="padding: 5px 2px 10px 6px">
		<p class="menu_setting_tips">可创建最多3个一级菜单，每个一级菜单下可创建最多5个二级菜单</p>
		<form:form action="menu-update.do" modelAttribute="rootMenu" method="post" id="wxmenuListForm">
			<table style="width:100%;" class="tb" id="wx-menu-setup">
				<tr style="background-color: #e7e7eb;">
					<td style="width:35%;padding:0px;">
						<table style="width:100%;border:0;font-size:14px;" id="wx-menu-manage">
							<tr><td style="text-align:left;border:0;font-weight:bold;">菜单管理</td><td style="border:0;width:65px;"><a title="添加一级菜单" onclick="doAddOne()"><span class="icon-plus"></span></a>&nbsp;&nbsp;&nbsp;<a title="排序" onclick="doSortOne()"><span class=" icon-list-ul"></span></a></td></tr>
						</table>
					</td>
					<td style="text-align:left;padding:0px;font-size:14px;padding-left:10px;font-weight:bold;">设置动作</td>
				</tr>
				<tr style="height:390px;vertical-align: top;">
					<td>
						<table class="wx-menu-content">
							<c:forEach items="${rootMenu.childs}" var="wxmenu" varStatus="menuCounter">
								<tr class="wx-menu-item-1" onclick="doSelectedMenuItem('0','${wxmenu.id}','${menuCounter.index}')">
									<td style="border:0;font-weight: bold;">${wxmenu.name}</td>
									<td class="wx-menu-icon-1">
										<div class="wx-menu-icon-div">
											<a title="添加二级菜单" onclick="doAddTwoLevel('${wxmenu.id}')"><span class="icon-plus"></span></a>
											<a title="编辑" style="margin-left:5px;" onclick="doEditMenu('${wxmenu.id}','${wxmenu.name}','${wxmenu.level}','${wxmenu.parentId}')"><span class="icon-pencil"></span></a>
											<a title="删除" style="margin-left:5px;" onclick="doDeleteMenuItem('${wxmenu.id}')"><span class="icon-trash"></span></a>
										</div>
									</td>
								</tr>
										
								<c:forEach items="${wxmenu.childs}" var="menuitem" varStatus="itemCounter">
									<tr class="wx-menu-item-1 js-second-menu_${menuCounter.index}" onclick="doSelectedMenuItem('1','${menuitem.id}','${menuCounter.index}')">
										<td style="border:0;padding-left:30px;"><i class="icon_dot">● &nbsp;</i>${menuitem.name}</td>
										<td class="wx-menu-icon-1">
											<div class="wx-menu-icon-div">
												<a title="编辑" style="margin-left:5px;" onclick="doEditMenu('${menuitem.id}','${menuitem.name}','${menuitem.level}','${menuitem.parentId}')"><span class="icon-pencil"></span></a>
												<a title="删除" style="margin-left:5px;" onclick="doDeleteMenuItem('${menuitem.id}')"><span class="icon-trash"></span></a>
											</div>
										</td>
									</tr>
								</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td style="vertical-align: middle;">
						<div id="action-div" style="text-align:center;font-size:14px;margin-left:20px;">
							<span id="action-no-data" class="menu_setting_tips">你可以先添加一个菜单/从左侧选择一菜单，然后开始为其设置响应动作</span>
							<span id="action-have-second-menu" class="menu_setting_tips" style="display:none;">已有子菜单，无法设置动作</span>
							<div id="action-content-div" style="display:none;">
								<table class="action-table">
									<tr>
										<td style="text-align:left;">
											<input type="radio" name="contentType" id="contentType0" value="0" onclick="doSelectContentType(0)"><span style="margin-left:5px;">跳转网页</span>
											<input type="radio" name="contentType" id="contentType1" style="margin-left: 80px;" value="1" onclick="doSelectContentType(1)"><span style="margin-left:5px;">关键字</span>
										</td>
									</tr>
									<tr>
										<td>
											<input type="text" id="action-link" name="action-link" value="" style="width: 100%;margin-top: 10px;">
											<input type="hidden" id="action-menu-id" name="action-menu-id" value="">
										</td>
									</tr>
									<tr>
										<td style="text-align:right;">
											<input type="button" id="keyword-select" value="从关键字列表选择" class="btn btn_default" onclick="doOpenKeyword()">
											<input type="button" id="material-select" value="从素材库选择" class="btn btn_default" onclick="doOpenMaterial()">
											<input type="button" id="action-save" value="保 存" class="btn btn_default" onclick="doSaveActionInfo()">
										</td>
									</tr>
								</table>
							</div>
						</div>
					</td>
				</tr>
			</table>
			
			<table style="width:100%;" style="margin-top:20px;">
				<tr style="border:0">
					<td height="60" colspan="2" align="left" style="padding-top:20px;">
						${errmsg}
					</td>
					<td height="60"  align="right" style="padding-top:20px;">
						<!-- <input type="submit" value="保存" class="btn btn_primary">&nbsp;&nbsp; &nbsp;&nbsp; --> 
						<input type="button" id="menu-setup" value="发布" class="btn btn_primary">
					</td>
				</tr>
			</table>
		</form:form>
	</div>

	<div class="am-modal am-modal-no-btn" tabindex="-1" id="alert-modal">
		<div class="am-modal-dialog">
			<div class="am-modal-hd wx-alert-modal-hd">
				<span>提示</span>
				<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd wx-alert-modal-content" id="alert-modal-content"></div>
		</div>
	</div>

	<div class="am-modal am-modal-confirm" tabindex="-1" id="confirm-modal">
		<div class="am-modal-dialog">
			<div class="am-modal-hd wx-alert-modal-hd">确认</div>
			<div class="am-modal-bd wx-alert-modal-content">你，确定要删除这条记录吗？</div>
			<!-- <div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span class="am-modal-btn" data-am-modal-confirm>确定</span>
			</div> -->
			<div id="modal-footer" class="wx-modal-footer">
				<input type="button" value="确认" class="btn btn_primary" data-am-modal-confirm>&nbsp;&nbsp; 
				<input type="button" id="menu-modal-cancle" value="取消" class="btn btn_default" onclick="doCloseModal('confirm-modal')">
			</div>
			<input type="hidden" id="menu-del-id" name="menu-del-id">
		</div>
	</div>

	<div class="am-modal am-modal-no-btn" tabindex="-1" id="edit-modal">
		<div class="am-modal-dialog">
			<div class="am-modal-hd wx-alert-modal-hd">
				<span>菜单管理</span>
				<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd wx-alert-modal-content" id="edit-modal-content">
				<span class="menu_setting_tips" id="menu-tips">菜单名称名字不多于8个汉字或16个字母</span>
				<br/>
				<input type="text" id="menu-name" name="menu-name" value="" style="width: 400px;margin-top: 10px;">
				<input type="hidden" id="menu-id" name="menu-id" value="">
				<input type="hidden" id="menu-level" name="menu-level" value="">
				<input type="hidden" id="actionType" name="actionType" value="">
				<input type="hidden" id="menu-parentId" name="menu-parentId" value="">
			</div>
			<div id="modal-footer" class="wx-modal-footer">
				<input type="button" value="保存" class="btn btn_primary" onclick="doSaveMenu()">&nbsp;&nbsp; 
				<input type="button" id="menu-modal-cancle" value="取消" class="btn btn_default" onclick="doCloseEditModal()">
			</div>
		</div>
	</div>
	
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="material-modal" style="width: 800px;">
		<div class="am-modal-dialog">
			<div class="am-modal-hd wx-alert-modal-hd">
				<span>从素材库选择</span>
				<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd wx-alert-modal-content" id="material-modal-content" style="padding-top:1px;height: 240px;overflow: auto;">
				<table style="width:100%;" class="tb">
					<tr style="font-size: 14px;">
						<th style="width:15%;">标题</th>
						<th style="width: 85px;">类型</th>
						<th>描述</th>
						<th style="width:60px;">操作</th>
					</tr>
					<tbody>
						<c:forEach items="${materialList}" var="material" varStatus="loopCounter">
							<tr class="Mtr" style="font-size: 14px;">
								<td><c:out value="${material.title}" /></td>
								<td><c:out value="${material.articleTypeName}" /></td>
								<td><c:out value="${material.des}" /></td>
		
								<td><a href="javascript:doSelectMaterial('${material.article_type}','${material.id}')">选择</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="modal-footer" class="wx-modal-footer" style="text-align: right;">
				<input type="button" id="material-modal-cancle" style="margin-right: 10px;" value="返回" class="btn btn_default" onclick="doCloseModal('material-modal')">
			</div>
		</div>
	</div>
	
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="keyword-modal" style="width: 800px;">
		<div class="am-modal-dialog">
			<div class="am-modal-hd wx-alert-modal-hd">
				<span>从关键字列表选择</span>
				<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd wx-alert-modal-content" id="keyword-modal-content" style="padding-top:1px;height: 240px;overflow: auto;">
				<table style="width:100%;" class="tb">
					<tr style="font-size: 14px;">
						<th style="width:15%;">关键字</th>
						<th style="width: 85px;">回复类型</th>
						<th>内容/描述</th>
						<th style="width:60px;">操作</th>
					</tr>
					<tbody>
						<c:forEach items="${keywordList}" var="keyword" varStatus="loopCounter">
							<tr class="Mtr" style="font-size: 14px;">
								<td><c:out value="${keyword.keyword}" /></td>
								<td><c:out value="${keyword.displayType}" /></td>
								<td><c:out value="${keyword.displayDesc}" /></td>
		
								<td><a href="javascript:doSelectKeyword('${keyword.keyword}')">选择</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="modal-footer" class="wx-modal-footer" style="text-align: right;">
				<input type="button" id="keyword-modal-cancle" style="margin-right: 10px;" value="返回" class="btn btn_default" onclick="doCloseModal('keyword-modal')">
			</div>
		</div>
	</div>
	
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="one-sort-modal">
		<div class="am-modal-dialog">
			<div class="am-modal-hd wx-alert-modal-hd">
				<span>菜单排序</span>
				<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd wx-alert-modal-content" id="one-modal-content" style="padding-top:1px;height: 300px;overflow: auto;">
				<table style="width:100%;" class="tb">
					<tr style="font-size: 14px;">
						<th>菜单名称</th>
						<th style="width: 85px;">顺序</th>
					</tr>
					<c:forEach items="${rootMenu.childs}" var="onemenu" varStatus="ct">
						<tr style="font-size: 14px;">
							<td>${onemenu.name}</td>
							<td style="width: 85px;">
								<input class="js_one" style="width:83px;border: 0;" id="one_${onemenu.id}" name="one_${onemenu.id}" value="${onemenu.seq}" onkeyup="javascript: $(this).val($(this).val().replace(/\D|^0/g,''));">
							</td>
						</tr>
						<c:forEach items="${onemenu.childs}" var="menuitem" varStatus="itemCounter">
							<tr style="font-size: 14px;">
								<td style="padding-left:20px;"><i class="icon_dot">● &nbsp;</i>${menuitem.name}</td>
								<td style="width: 85px;">
									<input class="js_one" style="width:83px;border: 0;" id="one_${menuitem.id}" name="one_${menuitem.id}" value="${menuitem.seq}" onkeyup="javascript: $(this).val($(this).val().replace(/\D|^0/g,''));">
								</td>
							</tr>
						</c:forEach>
					</c:forEach>
				</table>
			</div>
			<div id="modal-footer" class="wx-modal-footer">
				<input type="button" value="保存" class="btn btn_primary" onclick="doSaveOneSort(this)">&nbsp;&nbsp; 
				<input type="button" id="one-modal-cancle" style="margin-right: 10px;" value="返回" class="btn btn_default" onclick="doCloseModal('one-sort-modal')">
			</div>
		</div>
	</div>

</body>
</html>
