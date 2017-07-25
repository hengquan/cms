function tree(data) {
    for (var i = 0; i < data.length; i++) {
        if (data[i].icon == "icon-th") {
            $("#rootUL").append("<li data-name='" + data[i].code + "'><span><i class='" + data[i].icon + "'></i>" + data[i].name + "<em class='num' data-id="+data[i].openid+">("+data[i].num+")</em></span></li>");
        } else {
            var children = $("li[data-name='" + data[i].parentCode + "']").children("ul");
            if (children.length == 0) {
                $("li[data-name='" + data[i].parentCode + "']").append("<ul></ul>")
            }
            $("li[data-name='" + data[i].parentCode + "'] > ul").append(
                "<li data-name='" + data[i].code + "'>" +
                "<span>" +
                "<i class='" + data[i].icon + "'></i>" +
                data[i].name +
                "<em class='num' data-id="+data[i].id+">("+data[i].num+")</em></span>" +
                "</li>")
        }
        if(data[i].num == "" || data[i].num == 0){
            //$("."+data[i].name).html("");
            $('em[data-id='+data[i].openid+']').html("");
        }
        for (var j = 0; j < data[i].child.length; j++) {
            var child = data[i].child[j];
            if(!child.num){
                child.num = "";
            }
            var children = $("li[data-name='" + child.parentCode + "']").children("ul");
            if (children.length == 0) {
                $("li[data-name='" + child.parentCode + "']").append("<ul></ul>")
            }
            $("li[data-name='" + child.parentCode + "'] > ul").append(
                "<li data-name='" + child.code + "'>" +
                "<span>" +
                "<i class='" + child.icon + "'></i>" +
                child.name +
                "<em class='num' data-id="+data[i].openid+">("+child.num+")</em></span>" +
                "</li>");
            if(child.num == ""){
                //$("."+child.name).html("");
                $('em[data-id='+child.id+']').html("");
            }
            var child2 = data[i].child[j].child;
            tree(child2)
        }
        tree(data[i]);
    }

}

function myFun(){
	$('.tree li').each(function(i){
        if($('.tree li').eq(i).find("ul").length<1){
            $('.tree li').eq(i).find("i").removeClass();
        }
    });
    $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', '关闭');
    $('.tree li.parent_li > span').on('click', function (e) {
        var children = $(this).parent('li.parent_li').find(' > ul > li');
        if (children.is(":visible")) {
            children.hide('fast');
            $(this).attr('title', '展开').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
        } else {
            children.show('fast');
            $(this).attr('title', '关闭').find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
        }
        children.each(function(i){
        	$(this)[0].style.overflow = "";
        })
        e.stopPropagation();
    });
    $('.tree li.parent_li > span').click();
    $('#rootUL').css("max-height",($(window).height() *.6));
    
}