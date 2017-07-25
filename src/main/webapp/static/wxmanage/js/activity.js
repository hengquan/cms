$(function(){
    //处理开始时间和结束时间
    $("#startTime").change(function(){
        if($(this).val()=="")
            return false;

        var index=$(this).find("option:selected").index();

        $("#endTime option").each(function() {
            if(index>=$(this).index())
                $(this).attr("disabled",true);
            else
                $(this).attr("disabled",false);
        });
    });
    var index=$("#startTime").find("option:selected").index();
    $("#endTime option").each(function(){
        if(index>=$(this).index())
            $(this).attr("disabled",true);
    })

    //保存餐段信息
    $("#sliderForm").validate({
        rules: {
            title:{
                required:true
            },
            startTime:{
                required:true
            },
            endTime:{
                required:true
            },
            activityId:{
                required:true
            }
        },
        messages: {
            title:{
                required:"请填写活动名称！"
            },
            startTime:{
                required:"请选择上线时间！"
            },
            endTime:{
                required:"请选择结束时间！"
            },
            activityId:{
                required:"请填写活动ID！"
            }
        },
        submitHandler:function(){
            $.ajax({
                url:"addactivity.php?t=save",
                type:"post",
                data:$("#sliderForm").serialize(),
                dataType:"json",
                success:function(rs){
                    rs=eval(rs);
                    if(rs.rs=="fail"){
                        alert("添加失败，请查看编号是否重复");
                    }
                    if(rs.rs=="success"){
                        location.href="index.php";
                    }
                }
            })
        }
    });
})
//删除餐段
function deltime(obj){
    var id = $(obj).parents("tr").find("input[name='id']").val();
    var name=$(obj).parent().siblings().eq(2).html();
    if(confirm('确定要删除 "'+name+'" 餐段吗？')){
        $.ajax({
            url:"deltime",
            data:"id="+id,
            type:"post",
            dataType:"json",
            success:function(rs){
                rs = eval(rs);
                if(rs.rs=="success")
                    $(obj).parents("tr").remove();
            }
        })
    }
}