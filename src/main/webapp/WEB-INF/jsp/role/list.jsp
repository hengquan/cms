<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ include file="/WEB-INF/jsp/inc/sessions.jsp"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<%@ include file="/WEB-INF/jsp/inc/head_bootstrap.jsp"%>
<link
	href="${appRoot}/static/assets/jquery-file-upload/css/jquery.fileupload-ui.css"
	rel="stylesheet" type="text/css">
<!-- Custom styles for this template -->
<link href="${appRoot}/static/css/style.css" rel="stylesheet">
<link href="${appRoot}/static/css/style-responsive.css" rel="stylesheet" />
<title>${appTitle}</title>
<style type="text/css">
  @font-face {
    font-family: mFont;
    src: url('${appRoot}/static/fonts/MNR8102.ttf');
  }
</style>
</head>
<body>

	<section id="container" class="">
		<!--header start-->
		<%@ include file="/WEB-INF/jsp/inc/header.jsp"%>
		<!--header end-->

		<!--sidebar start-->
		<%@ include file="/WEB-INF/jsp/inc/sidebar.jsp"%>
		<!--sidebar end-->

		<!--main content start-->
		<section id="main-content">
			<section class="wrapper">
				<!-- page start-->
				<div class="row">
					<div class="col-lg-12">
						<section class="panel">
							<header class="panel-heading">站点管理 </header>
							<table class="table table-striped border-top" id="sample_1">
								<thead>
									<tr>
										<th style="width: 8px;"><input type="checkbox" name="box"
											class="group-checkable" data-set="#sample_1 .checkboxes"
											value="" /></th>
										<th>站点名称</th>
										<th class="hidden-phone">拼音</th>
										<th class="hidden-phone">站点语言</th>
										<th class="hidden-phone">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${roleList}" var="info" varStatus="s">
										<tr class="odd gradeX">
											<td><input type="checkbox" name="box" class="checkboxes"
												value="${info.id}" /></td>
											<td>${info.roleName}</td>
											<td class="hidden-phone">${info.pinyin}</td>
											<td class="hidden-phone">${info.languageName }</td>
											<td class="hidden-phone">
												<button class="btn btn-primary btn-xs"
													onclick="doEdit('${info.id}','${info.roleName}','${info.pinyin}','${info.remark}','${info.languageId }','${info.languages }')">
													<i class="icon-pencil"></i>
												</button> &nbsp;
												<button class="btn btn-danger btn-xs"
													onclick="doDel('${info.id}')">
													<i class="icon-trash"></i>
												</button> &nbsp; <a
												href="${appRoot}/roleItem/listItem?roleId=${info.id}&roleName=${info.roleName}&itemId=${itemId}&id=${positionId}">
													<button class="btn btn-danger btn-xs">
														<i class="icon-eye-open">查看站点权限</i>
													</button>
											</a>
											</td>
										</tr>
										<c:forEach items="${info.roleList}" var="role" varStatus="s">
                    <tr class="odd gradeX">
                      <td><input type="checkbox" name="box" class="checkboxes"
                        value="${role.id}" /></td>
                      <td>----${role.roleName}</td>
                      <td class="hidden-phone">${role.pinyin}</td>
                      <td class="hidden-phone">${role.languageName }</td>
                      <td class="hidden-phone">
                        <button class="btn btn-primary btn-xs"
                          onclick="doEdit('${role.id}','${role.roleName}','${role.pinyin}','${role.remark}','${role.languageId }','${role.languages }')">
                          <i class="icon-pencil"></i>
                        </button> &nbsp;
                        <button class="btn btn-danger btn-xs"
                          onclick="doDel('${role.id}')">
                          <i class="icon-trash"></i>
                        </button> &nbsp; <a
                        href="${appRoot}/roleItem/listItem?roleId=${role.id}&roleName=${role.roleName}&itemId=${itemId}&id=${positionId}">
                          <button class="btn btn-danger btn-xs">
                            <i class="icon-eye-open">查看站点权限</i>
                          </button>
                      </a>
                      </td>
                    </tr>
                  </c:forEach>
									</c:forEach>
								</tbody>
							</table>
						</section>
					</div>
				</div>
				<!-- page end-->
			</section>
		</section>

		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="modal-title">添加站点</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form" id="roleForm"
							name="roleForm">
							<input type="hidden" name="editId" id="editId">
							<input type="hidden" name="languageId" id="languageId"> 
							<input type="hidden" name="languages" id="languages"> 
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">站点名称<font
									style="color: red;">*</font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="roleName"
										name="roleName" placeholder="请输入站点名称">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">站点拼音<font
									style="color: red;">*</font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="pinyin"
										name="pinyin" placeholder="请输入站点拼音">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">备注<font
									style="color: red;"></font></label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="remark"
										name="remark" placeholder="请输入备注">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label pd-r5">站点语言<font
									style="color: red;"></font></label>
								<div class="col-lg-10" id="thisChannelLanguage">
								</div>
							</div>
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button type="button" onclick="submitData();"
										class="btn btn-send">提交</button>
								</div>
							</div>
						</form>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
	</section>



	<!-- Modal -->
	<div class="modal fade" id="myModal2" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">删除警告</h4>
				</div>
				<div class="modal-body">确定删除？</div>
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn btn-default" type="button"
						id="quxiao">取消</button>
					<button class="btn btn-warning" type="button" onclick="Delete()">确定</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->

	<div class="panel-body">
		<!-- Modal -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title">删除警告</h4>
					</div>
					<div class="modal-body">确定删除？</div>
					<div class="modal-footer">
						<button data-dismiss="modal" class="btn btn-default" type="button"
							id="quxiao">取消</button>
						<button class="btn btn-warning" type="button" onclick="Del()">确定</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->

	<div class="modal fade" id="myModalUpdatePwd" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">重置警告</h4>
				</div>
				<div class="modal-body">
					确定重置站点密码？</br>重置后密码为:123456
				</div>
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn btn-default" type="button"
						id="quxiao">取消</button>
					<button class="btn btn-warning" type="button"
						onclick="resetPassword()">确定</button>
				</div>
			</div>
		</div>
	</div>

	<form action="${appRoot}/role/del" method="post" id="deleForm"
		name="deleForm">
		<input type="hidden" name="byid" id="byid"> <input
			type="hidden" name="boxeditId" id="boxeditId">
	</form>


	<%@ include file="/WEB-INF/jsp/inc/foot_bootstrap.jsp"%>
	<script src="${appRoot}/static/js/jquery.sparkline.js"
		type="text/javascript"></script>

	<script type="text/javascript"
		src="${appRoot}/static/assets/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript"
		src="${appRoot}/static/assets/data-tables/DT_bootstrap.js"></script>
	<!--script for this page only-->
	<script src="${appRoot}/static/js/dynamic-table.js"></script>
	<script src="${appRoot}/static/js/dialog_alert.js"></script>
	<script type="text/javascript">
		function doAdd() {
			$("#thisChannelLanguage").html();
			addLanguage();
			//添加
			$("#editId").val('');
			$('#roleName').val('');
			$('#pinyin').val('');
			$('#remark').val('');
			var $modal = $('#myModal');
			$modal.modal();
		}

		function doEdit(id, roleName, pinyin, remark,languageId,languages) {
			$("#thisChannelLanguage").html();
			$.ajax({
        type : 'post',
        data : "",
        url : '${appRoot}/role/getLanguageData',
        dataType : 'json',
        success : function(data) {
          if (data.msg == "0") {
           var html = '';
           var dataList = data.dataList;
           for(var i=0;i<dataList.length;i++){
             if(languageId.indexOf(dataList[i].id) != -1){
               html += '<span class="btn thisLanguageArea">'
               +'<input type="checkbox" checked="true" class="languageIds btn" value="'+ dataList[i].id +'"><label class="btn">' + dataList[i].name + '</label>'
               +'<input type="hidden" class="languageTab" value="'+ dataList[i].tab +'">'
               +'<input type="hidden" class="languageName" value="'+ dataList[i].name +'">';
               if(languages!=""){
	               var languageList = languages.split(",");
	               for(var y=0; y<languageList.length; y++){
	                 var zu = languageList[y];
	                 var xiang = zu.split(":");
	                 var xiangTab = xiang[1];
	                 var xiangName = xiang[2];
	                 if(xiangTab == dataList[i].tab){
	                	 html += '<input type="text" style="text-align:center;font-family: mFont;" class="form-control roleLanguageName" value="'+ xiangName +'" placeholder="请输入'+ dataList[i].name +'名称">'
	                   +'</span>';
	                 }
	               }
               }else{
            	   html += '<input type="text" style="text-align:center;font-family: mFont;" class="form-control roleLanguageName" placeholder="请输入'+ dataList[i].name +'名称">'
                 +'</span>';
               }
             }else{
               html += '<span class="btn thisLanguageArea">'
                   +'<input type="checkbox" class="languageIds btn" value="'+ dataList[i].id +'"><label class="btn">' + dataList[i].name + '</label>'
                   +'<input type="hidden" class="languageTab" value="'+ dataList[i].tab +'">'
                   +'<input type="hidden" class="languageName" value="'+ dataList[i].name +'">'
                   +'<input type="text" style="text-align:center;font-family: mFont;" class="form-control roleLanguageName" placeholder="请输入'+ dataList[i].name +'名称">'
                   +'</span>';
             }
           }
           $("#thisChannelLanguage").html(html);
           $('#editId').val(id);
           $('#roleName').val(roleName);
           $('#pinyin').val(pinyin);
           $('#remark').val(remark);
           var $modal = $('#myModal');
           $('#modal-title').text("修改站点信息");
           $modal.modal();
          } else {
            windowShow("语言列表为空");
          }
        },
        error : function(data) {
          windowShow("获取语言列表信息失败", "");
        }
      });
		}

		function submitData() {
			var result = yanZheng();
			if(!result){
				return;
			}
			var languages = "";
			var languageId = "";
			$("#thisChannelLanguage").find(".thisLanguageArea").each(function (index,obj){
				var languageIds = $(obj).find(".languageIds");
				if (languageIds.is(':checked')) {
					var language = languageIds.val();
					languageId += "," + language;
					//组串串
					var languageName = $(obj).find(".languageName").val();
					var languageTab = $(obj).find(".languageTab").val();
					var roleLanguageName = $(obj).find(".roleLanguageName").val();
					languages += ","+languageName+":"+languageTab+":"+roleLanguageName;
        }
			})
			if(languageId != "") 
				languageId = languageId.substr(1);
			$("#languageId").val(languageId);
			if(languages != "") 
				languages = languages.substr(1);
      $("#languages").val(languages);
			//开始提交
			$.ajax({
        type : 'post',
        data : $('#roleForm').serialize(),
        url : '${appRoot}/role/addAndUpdate',
        dataType : 'json',
        success : function(data) {
          if (data.msg == "iscz") {
            windowShow("该站点名称已存在!请重新输入站点名称", "");
            return false;
          } else if (data.msg == "isc") {
            $('#myModal').modal('hide');
            windowShow("添加站点成功", "");
            location.reload();
          } else if (data.msg == "isupdate") {
            $('#myModal').modal('hide');
            windowShow("修改站点成功", "");
            location.reload();
          } else {
            $('#myModal').modal('hide');
            windowShow("操作失败", "");
          }
        },
        error : function(data) {
          windowShow("操作失败", "");
        }
      });
		}

		function checkbox() {
			var str = document.getElementsByName("box");
			var objarray = str.length;
			var chestr = "";
			var jy = false;
			for (i = 0; i < objarray; i++) {
				if (str[i].checked == true) {
					jy = true;
					chestr += str[i].value + ",";
				}
			}
			if (!jy) {
				windowShow("请选择要删除的数据！")
				$('#quxiao').click();
				return false;
			} else {
				$("#boxeditId").val(chestr);
				return true;
			}
		}

		function doDelete() {
			var flag = checkbox();
			if (flag) {
				var $modal = $('#myModal2');
				$modal.modal();
			}
		}

		function doDel(id) {
			var $modal = $('#myModal1');
			$("#byid").val(id);
			$modal.modal();
		}

		function Del() {
			$("#deleForm").submit();
		}

		function Delete() {
			$("#deleForm").submit();
		}
		
		$(function(){
			console.log('${roleList}')
			//addLanguage();
		})
		
		function addLanguage(){
			$.ajax({
        type : 'post',
        data : "",
        url : '${appRoot}/role/getLanguageData',
        dataType : 'json',
        success : function(data) {
          if (data.msg == "0") {
        	 var html = '';
           var dataList = data.dataList;
           for(var i=0;i<dataList.length;i++){
        	   html += '<span class="btn thisLanguageArea">'
        	   +'<input type="checkbox" class="languageIds btn" value="'+ dataList[i].id +'"><label class="btn">' + dataList[i].name + '</label>'
        	   +'<input type="hidden" class="languageTab" value="'+ dataList[i].tab +'">'
        	   +'<input type="hidden" class="languageName" value="'+ dataList[i].name +'">'
        	   +'<input type="text" style="text-align:center" class="form-control roleLanguageName" placeholder="请输入'+ dataList[i].name +'名称">'
        	   +'</span>';
           }
					 $("#thisChannelLanguage").html(html);
          } else {
            windowShow("语言列表为空");
          }
        },
        error : function(data) {
          windowShow("获取语言列表信息失败", "");
        }
      });
		}
		
		//验证
		function yanZheng(){
			var result = true;
      var result1 = true;
			if ($('#roleName').val() == null || $('#roleName').val() == '') {
        windowShow("站点名称不能为空", "");
        result = false;
      }
      if ($('#pinyin').val() == null || $('#pinyin').val() == '') {
        windowShow("站点拼音不能为空", "");
        result = false;
      }
      $("#thisChannelLanguage").find(".thisLanguageArea").each(function (index,obj){
        var languageIds = $(obj).find(".languageIds");
        if (languageIds.is(':checked')) {
          var languageName = $(obj).find(".languageName").val();
          var languageTab = $(obj).find(".languageTab").val();
          var roleLanguageName = $(obj).find(".roleLanguageName").val();
          if (languageName == null || languageName == '' || languageTab == null || languageTab == '' || roleLanguageName == null || roleLanguageName == '') {
            result1 = false;
          }
        }
      })
      if(!result1)
    	  windowShow("站点语言不允许为空，请仔细检查！", "");
      return result && result1;
		}
	</script>
</body>
</html>
