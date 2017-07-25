$(function(){
    //页面跳转
    $("#zzAnswer .version,#zzAnswer .speech").click(function() {
        window.location.href = "../qaa/answer.html";
    });
    //导航背景
    $(".headerNav li").click(function() {
        $(".headerNav .active").removeClass("active");
        $(this).toggleClass("active");
    });

    var toggleList = false;
    $(".selectList b").click(function() {
        toggleList = !toggleList;
        if(toggleList){
            $(".selectList .clearfix").css("opacity","1");
        }else{
            $(".selectList .clearfix").css("opacity","0");
        }
    });

})
