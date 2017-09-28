$(function () {

  'use strict';

  var $local_distpicker = $('#local_distpicker');

  $local_distpicker.distpicker({
    province: '福建省',
    city: '厦门市',
    district: '思明区'
  });

  $('#reset').click(function () {
    $local_distpicker.distpicker('reset');
  });

  $('#reset-deep').click(function () {
    $local_distpicker.distpicker('reset', true);
  });
  
  $('#destroy').click(function () {
    $local_distpicker.distpicker('destroy');
  });

  $('#local_distpicker1').distpicker();

  $('#local_distpicker2').distpicker({
    province: '---- 所在省 ----',
    city: '---- 所在市 ----',
    district: '---- 所在区 ----'
  });

  $('#local_distpicker3').distpicker({
    province: '浙江省',
    city: '杭州市',
    district: '西湖区'
  });

  $('#local_distpicker4').distpicker({
    placeholder: false
  });

  $('#local_distpicker5').distpicker({
    autoSelect: false
  });

});
