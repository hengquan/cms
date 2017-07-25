/**
 * Created by toma
 * on 2016年5月31日17:28:16
 */

/**
 * @func 全选
 */
var checkbox = function () {
  $('#checkall').on('click', function() {
    $('.checkboxes').prop("checked", this.checked);
  });
  var $cel = $(".checkboxes");
  $cel.click(function() {
    $('#checkall').prop("checked", $cel.length == $(".checkboxes:checked").length ? true : false);
  });
}();

/**
 * @func 树形表格
 */
var treeview = function () {
  $('.ta-paren').click(function (e) {
    var _id = $(this).data('id');
    $(this).find('i').toggleClass('fa-rotate-90');
    $('.ta-child[data-child="'+_id+'"]').toggle();
  });
};