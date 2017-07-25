<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>新手指南</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <style>
        body{margin:0;}
        img{width: 100%;height:100%;}
        img:not(.active){display: none;}
        img.active{display: block;}
    </style>
</head>
<body>
<img class="active" src="${appRoot}/static/wx/imgs/guideImgs/zzk01.jpg" alt="新手指南">
<img src="${appRoot}/static/wx/imgs/guideImgs/zzk02.jpg" alt="新手指南">
<img src="${appRoot}/static/wx/imgs/guideImgs/zzk03.jpg" alt="新手指南">
<img src="${appRoot}/static/wx/imgs/guideImgs/zzk04.jpg" alt="新手指南">
<img src="${appRoot}/static/wx/imgs/guideImgs/zzk05.jpg" alt="新手指南">
<img src="${appRoot}/static/wx/imgs/guideImgs/zzk06.jpg" alt="新手指南">
<img src="${appRoot}/static/wx/imgs/guideImgs/zzk07.jpg" alt="新手指南">
<img src="${appRoot}/static/wx/imgs/guideImgs/zzk08.jpg" alt="新手指南">
<img src="${appRoot}/static/wx/imgs/guideImgs/zzk09.jpg" alt="新手指南">
<img src="${appRoot}/static/wx/imgs/guideImgs/zzk10.jpg" alt="新手指南">
<img src="${appRoot}/static/wx/imgs/guideImgs/zzk11.jpg" alt="新手指南">
<img src="${appRoot}/static/wx/imgs/guideImgs/zzk12.jpg" alt="新手指南">
<img src="${appRoot}/static/wx/imgs/guideImgs/zzk14.jpg" alt="新手指南">
<img src="${appRoot}/static/wx/imgs/guideImgs/zzk15.jpg" alt="新手指南">

<script src="${appRoot}/static/wx/lib/zepto.min.js"></script>
<script>
$('img').height(document.documentElement.clientHeight || document.body.clientHeight);
var imgIndex = 0;
$('img').each(function(i){
    $(this).click(function(){
        imgIndex++;
        if(imgIndex>$('img').length-1){
        	 window.location.href ="${appRoot}/wx/problem/problem.az";
        }
        $(this).removeClass('active');
        $('img').eq(imgIndex).addClass('active');
    })
})
</script>
</body>
</html>
