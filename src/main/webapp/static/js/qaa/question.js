$(function(){
    //语音
    progress.init();
    //页面跳转
    $("#myModal .submitQuestion a").click(function(){
        window.location.href = "answer.html";

    })

    //提交问题
    $(".axQuestion .submitQuestion").click(function() {
        if($(".axQuestion textarea").val()||$(".axQuestion .transmitter").css("display") == "none"){
            if($(".axQuestion .bt-tag s").hasClass("active")){
                $("#myModal").modal("toggle");
            }
        }
    })






































})
