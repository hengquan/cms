var editor;
KindEditor.ready(function(K) {
    editor = K.create('textarea[name="content"]', {
        allowFileManager : false
    });
});
$(function(){
    $("#sub_btn").click(function(){
        if($("#title").length>0 && $.trim($("#title").val())==""){
            $("#title").focus().css("border","1px solid red");
            return false;
        }else if($("#name").length>0 && $.trim($("#name").val())==""){
            $("#name").focus().css("border","1px solid red");
            return false;
        } else if($("#category").length>0 && $("#category").val()==0){
            $("#category").css("border","1px solid red");
            return false;
        }else{
            editor.sync();
            $("form").submit();
        }
    })
})