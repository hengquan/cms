$(function(){
    //修改
    $(".headerLogo p").click(function() {
        window.location.href = "information.html";
    });
    //我的收益
    $(".header .col-xs-4:nth-child(1)").click(function() {
        window.location.href = "gather.html";
    });
    //我的积分
    $(".header .col-xs-4:nth-child(3)").click(function() {
        window.location.href = "integral.html";
    });
    //我的提问
    $(".personalList li:nth-child(1)").click(function() {
        window.location.href = "myTw.html";
    });
    //我来支招
    $(".personalList li:nth-child(2)").click(function() {
        window.location.href = "myZz.html";
    });
    //我的偷听
    $(".personalList li:nth-child(3)").click(function() {
        window.location.href = "myTt.html";
    });
    //我的收藏
    $(".personalList li:nth-child(4)").click(function() {
        window.location.href = "mySc.html";
    });
    //我的时间
    $(".personalList li:nth-child(2)").click(function() {

    });
    //我的预约
    $(".personalList li:nth-child(2)").click(function() {

    });
    //我要提问
    $(".personalListBottom span:nth-child(1)").click(function() {
        window.location.href = "../qaa/question.html";
    });
    //新手偷听
    $(".personalListBottom span:nth-child(2)").click(function() {
        window.location.href = "../give/answerList.html";
    });
})
