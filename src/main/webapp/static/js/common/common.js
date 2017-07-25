//语音
var progress = {
    progressWidth : 100,
    starti: 59,
    endi: 0,
    timer: null,
    progressTimer: null,
    stopProgress: null,
    broadcastWidth: 6,
    broadcastTime: 0,
    init: function() {
        this.start();
        this.end();
        this.close();
        this.broadcast();
    },
    start: function() {
        var self = this;
        $(".transmitter").on('touchstart',function(){
            $(".msgd").show();
            self.timer = setInterval(function(){
                self.starti--;
                self.endi++;
                $(".msgd u").html(self.starti+"S");
                self.progressWidth -= 100/60;
                $(".msgd b").css("width",self.progressWidth+"px");
                if(self.starti == 0){
                    clearInterval(self.timer);
                }
            },1000);
        });
    },
    end:function() {
        var self = this;
        $(".transmitter").on("touchend",function() {
            clearInterval(self.timer);
            $(".speech u").html(self.endi+" S");
            $(".msgd").hide();
            $(".transmitter").hide();
            $(".speech").show();
        });
    },
    close: function() {
        var self = this;
        $(".speech s").click(function() {
            clearInterval(self.progressTimer);
            clearInterval(self.stopProgress);
            $(".speech em").css({width: "30px"});
            $(".speech,.msgd").hide();
            $(".transmitter").show();
            self.starti = 59;
            self.progressWidth = 100;
            $(".msgd u").html(self.starti+"S");
            $(".msgd b").css("width",self.progressWidth+"px");
        })
    },
    broadcast: function() {
        var self = this;
        self.broadcastTime = parseInt($(".speech u").html());
        $(".speech b").click(function() {
            $(this).find("em").css({
                width: self.broadcastWidth
            });
            self.progressTimer = setInterval(function(){
                self.broadcastWidth++;
                $(".speech em").css({
                    width: self.broadcastWidth + "px"
                });
                if(self.broadcastWidth == 18){
                    self.broadcastWidth = 6;
                }
            },50);
            self.stopProgress = setInterval(function(){
                self.broadcastTime--;
                if(self.broadcastTime == 0){
                    clearInterval(self.progressTimer);
                    clearInterval(self.stopProgress);
                    $(".speech em").css({
                        width: "18px"
                    });
                }
            },1000);
        })
    }
}

$(function(){
    //头部header
    $(".header li").click(function() {
        $(".header .active").removeClass("active");
        $(this).addClass("active");
    })

    //模态框
    $("#myModal .modal-dialog").css({
        top:  $(window).height()/2-100
    });

    //下拉菜单、模拟复选框
    $(".bt-tag em,.bt-tag u,.checkboxs em").click(function() {
        this.nodeName == "EM"?$(this).find("s").toggleClass("active"):
            $(this).toggleClass("active");
    });
})


