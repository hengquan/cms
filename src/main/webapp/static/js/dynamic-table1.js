var Script = function() {
	var refBtn = '<div class="btn-group js-btn js-ref"><a href="javascript:doRefresh();" class="btn mini btn-white"><i class="icon-refresh"></i></a></div>&nbsp;&nbsp;';
	//var addBtn = '<div class="btn-group js-btn"><a href="javascript:doAdd();" class="btn mini btn-white"><i class="icon-plus"></i></a></div>&nbsp;&nbsp;';
	var delBtn = '<div class="btn-group js-btn js-del"><a href="javascript:doDelete();" class="btn mini btn-white"><i class="icon-trash"></i></a></div>&nbsp;&nbsp;';

	// begin first table
	$('#sample_1')
			.dataTable(
					{
						"sDom" : "<'row pa15'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
						"sPaginationType" : "bootstrap",
						"oLanguage" : {
							"sLengthMenu" : refBtn + delBtn
									+ "_MENU_ ",
							"oPaginate" : {
								"sPrevious" : "Prev",
								"sNext" : "Next"
							},
							"sSearch" : "_INPUT_",
							"sInfo" : "总共 _TOTAL_条,当前为_START_至 _END_条"
						},
						"aoColumnDefs" : [ {
							'bSortable' : false,
							'aTargets' : [ 0 ]
						} ]
					});

	// jQuery('#sample_1 .group-checkable').change(function () {
	// var set = jQuery(this).attr("data-set");
	// var checked = jQuery(this).is(":checked");
	// jQuery(set).each(function () {
	// if (checked) {
	// $(this).attr("checked", true);
	// } else {
	// $(this).attr("checked", false);
	// }
	// });
	// jQuery.uniform.update(set);
	// });

	//@func 全选
	$('#sample_1 .group-checkable').on('click', function() {
		$(".checkboxes").prop("checked", this.checked);
	});
	var $cel = $(".checkboxes");
	$cel.click(function() {
		$('#sample_1 .group-checkable').prop("checked",
				$cel.length == $(".checkboxes:checked").length ? true : false);
	});

	// @func 点击下一页清空所有复选框
	$('body').on('click','.dataTables_paginate li',function(){
		// alert();
		$(".checkboxes, .group-checkable").prop('checked', false);
		//var cl = $(this).attr('class') == 'active';
	});

	/* -- -- */

	jQuery('#sample_1_wrapper .dataTables_filter input').addClass(
			"form-control"); // modify table search input
	jQuery('#sample_1_wrapper .dataTables_length select').addClass(
			"form-control"); // modify table per page dropdown

	// begin second table
	$('#sample_2')
			.dataTable(
					{
						"sDom" : "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
						"sPaginationType" : "bootstrap",
						"oLanguage" : {
							"kkkkkkkkk" : "_MENU_ per page",
							"oPaginate" : {
								"sPrevious" : "Prev",
								"sNext" : "Next"
							}
						},
						"aoColumnDefs" : [ {
							'bSortable' : false,
							'aTargets' : [ 0 ]
						} ]
					});

	jQuery('#sample_2 .group-checkable').change(function() {
		var set = jQuery(this).attr("data-set");
		var checked = jQuery(this).is(":checked");
		jQuery(set).each(function() {
			if (checked) {
				$(this).attr("checked", true);
			} else {
				$(this).attr("checked", false);
			}
		});
		jQuery.uniform.update(set);
	});

	jQuery('#sample_2_wrapper .dataTables_filter input').addClass(
			"form-control"); // modify table search input
	jQuery('#sample_2_wrapper .dataTables_length select').addClass(
			"form-control"); // modify table per page dropdown

	// begin: third table
	$('#sample_3')
			.dataTable(
					{
						"sDom" : "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
						"sPaginationType" : "bootstrap",
						"oLanguage" : {
							"sLengthMenu" : "_MENU_ per page",
							"oPaginate" : {
								"sPrevious" : "Prev",
								"sNext" : "Next"
							}
						},
						"aoColumnDefs" : [ {
							'bSortable' : false,
							'aTargets' : [ 0 ]
						} ]
					});

	jQuery('#sample_3 .group-checkable').change(function() {
		var set = jQuery(this).attr("data-set");
		var checked = jQuery(this).is(":checked");
		jQuery(set).each(function() {
			if (checked) {
				$(this).attr("checked", true);
			} else {
				$(this).attr("checked", false);
			}
		});
		jQuery.uniform.update(set);
	});

	jQuery('#sample_3_wrapper .dataTables_filter input').addClass(
			"form-control"); // modify table search input
	jQuery('#sample_3_wrapper .dataTables_length select').addClass(
			"form-control"); // modify table per page dropdown

}();