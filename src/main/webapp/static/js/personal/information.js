$(function() {
    //保存按钮
    $(".footer .btn-block").click(function() {
        window.location.href = "personal.html";
    });
    //模态框
    $("#myModal .modal-dialog").css({
        top: $(window).height()/2-100
    });
})
