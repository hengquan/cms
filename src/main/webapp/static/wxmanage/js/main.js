// JavaScript Document
var editor;
$(function(){
    //自适应高度
    $(function(){
        $(window).resize(function(){
            winResize();
        });
        winResize();
    });

    //菜单切换
    $(".az-nav >li").click(function(){
        index=$(this).index();
        //$(".az-menu").css("display","none").eq(index).css("display","block");
        window.location = '/admin/?m='+ (Number(index) + 1);
    });

    //左刚菜单
    $(".az-menu >li").each(function(index){
        $(this).click(function(){
            tdCount = $(".az-menu>dl>dt").size();
            if(tdCount == 0){

            }else{
                $(".az-menu>dl").css("display","none").eq(index).css("display","block");
            }
        })
    });

	//提交搜索条件
	$("#btn_search").click(function(){
		if($('#keyword').val() != "关键字") $("#searchForm").submit();
	})

    //上传控件
if(typeof (KindEditor) != "undefined"){
 KindEditor.ready(function(K) {
         editor = K.editor({
             allowFileManager : false,
             uploadJson : "kindeditor/jsp/upload_json.jsp"
         });

         K('.az-upload').click(function() {
             var obj=$(this).attr("target");
             editor.loadPlugin('image', function() {
                 editor.plugin.imageDialog({
                     showRemote : false,
                     imageUrl : K('#'+obj).val(),
                     clickFn : function(url, title, width, height, border, align) {
                         K('#'+obj).val(url);
                         editor.hideDialog();
                     }
                 });
             });
     });
    });

}

    $(".az-file").each(function(){
        var obj=$(this).attr("target");
        var editor2;
        KindEditor.ready(function(K) {
            var editor2 = K.editor({
                allowFileManager : false,
                uploadJson : "kindeditor/jsp/upload_json.jsp"
            });

            K('.az-file').click(function() {
                editor2.loadPlugin('image', function() {
                    editor2.plugin.imageDialog({
                        showRemote : false,
                        imageUrl : K('#'+obj).val(),
                        clickFn : function(url, title, width, height, border, align) {
                            K('#'+obj).val(url);
                            editor2.hideDialog();
                        }
                    });
                });
            });
        });
    });

});

$(document).ready(function(){
    $(".kuang_1000").mouseover(function(){
        alert(1)
        var omsrc = $(this).attr("src");
        $("img",this).eq(0).attr("src",$(this).attr("msrc"));
        $("img",this).eq(0).attr("msrc",omsrc);
    });
    $(".kuang_1000").mouseout(function(){
        alert(2)
        var omsrc = $(this).attr("src");
        $("img",this).eq(0).attr("src",$(this).attr("msrc"));
        $("img",this).eq(0).attr("msrc",omsrc);
    });
	$("#addBtn").click(function(){
		var i = 2;
		$(".show").each(function(index, element) {
			//alert("index=" + index);alert("display=" + $(this).css('display'));
            if($(this).css('display') != "none"){
				i++;
			}else{
				return false;
			}
        });	
		n = i;
		//alert("n="+n);
		$(".news"+n).show();
	});
});

function delHtml(v){
	$(".news"+v).find("input").val('');
	$(".news"+v).hide();
}
//选择按钮
function sel()
{
	var el = document.getElementsByName("id[]");
	var len = el.length;
	for(var i=0; i<len; i++)
	{
		if(el[i].checked)
			el[i].checked = false;
		else
			el[i].checked = true;
	}
}

//删除按钮
function del()
{
	if($("input[name='id[]']:checked").val()==undefined)
	{
		alert("请您先选择要删除的项目！");
	}else{
		if(confirm("确定要删除所选项目吗？"))
		{
			$("<input />").attr("type","hidden").attr("name","action").attr("value","delete").appendTo("#delform");
			$("#delform").submit();
		}
	}
}
//删除一条
function delelte(obj){
    if(confirm("确定要删除所选项目吗？"))
    {
        $(obj).parent().parent().find("input").attr("checked","checked");
        $("#delform").submit();
    }
}

//窗口自适应函数
function winResize()
{
	var Height=$(window).height()-$(".az-top").height()-1;
	$("#az-body").css("height",Height);
}

$(function(){
    //提交表单验证
    /*$("#title,#name").keyup(function(){
        if($.trim($(this).val())!=""){
            $(this).css("border","1px solid #C8C8C8");
        }
    });*/
    $("#category").change(function(){
        if($(this).val()!=0){
            $(this).css("border","1px solid #C8C8C8");
        }
    });

    $("#cat_btn").click(function(){
        if($.trim($("#name").val())==""){
            $("#name").focus().css("border","1px solid red");
            return false;
        }else{
            $("form").submit();
        }
    })
    
    
    $("#menu-setup").click(function(){
    	if(confirm("更改当前公众号的微信菜单，确定发布吗？")){
	    	ndex=$(this).index();
	        window.location = 'menu-setup.do';
        }
    })
})
