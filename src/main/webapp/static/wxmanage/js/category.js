//显示添加一级分类输入框
function addparent(){
    $("#addParent").removeAttr("style");
}
//显示添加二级分类输入框
function addchild(obj){
    var id = $(obj).parent().parent().attr("pid");
    $(obj).parent().parent().prev().removeAttr("style").attr("pid",id);
    var tid = $(obj).parent().parent().attr("tid");
    $(obj).parent().parent().prev().removeAttr("style").attr("tid",tid);
}
//显示或隐藏二级分类的内容
function showchild(obj,id){
    var objclass = $(obj).attr("class");
    if(objclass=='icon-open'){
        $(obj).attr("class","icon-close");
        $(obj).parent().parent().nextAll("[pid="+id+"]").removeAttr("style");
    }
    if(objclass=='icon-close'){
        $(obj).attr("class","icon-open");
        $(obj).parent().parent().nextAll("[pid="+id+"]").attr("style","display:none");;
    }
}
//添加一级分类
function saveparent(obj){
    var catName = $.trim($(obj).parents("#addParent").find("input[name='catName']").val());
    var sort = $(obj).parents("#addParent").find("input[name='sort']").val();
    var tid = $(obj).parents("#addParent").find("select[name='typeId']").val();

    //判空、判断编号格式
    if(catName==""||catName==null){
        alert("请填写分类名称");
        return;
    }
    if(sort==""||sort==null){
        alert("请填写编号");
        return;
    }else if(isNaN(sort) || !(/^\d$/).test(sort) ){
        alert("编号只能是01-99的两位数字");
        return;
    }

    $.ajax({
        url:"category/savecategory",
        type:"post",
        data:"catName="+catName+"&sort="+sort+"&tid="+tid,
        dataType:"json",
        success:function(rs){
            rs=eval(rs);
            if(rs.rs=="fail"){
                alert("添加失败，请查看编号是否重复");
            }
            if(rs.rs=="success"){
                alert("添加成功");
                var html=$(obj).parents("#addParent").html();
                $(obj).attr("onclick","update(this)");
                $(obj).next().attr("onclick","delcategory(this,'1')");
                $(obj).parent().parent().attr("id",rs.id);
                $(obj).parents("#addParent").find("tr:gt(0)").attr("pid",rs.id);
                $(obj).parents("#addParent").find(".icon-open").attr("onclick","showchild(this,"+rs.id+")");
                $(obj).parents("#addParent").removeAttr("id");
                $(obj).parents("tbody").after("<tbody id='addParent' style='display: none'>"+html+"</tbody>");
            }
        }
    })
}
//删除商品分类
function delcategory(obj,type){
    var id = $(obj).parent().parent().attr("id");
    if(id==0 || id==null){
        if(type!=null){
            var html = $(obj).parent().parent().parent().html();
            $(obj).parent().parent().parent().before("<tbody style='display: none' id='addParent'>"+html+"</tbody>");
            $(obj).parent().parent().parent().remove();
        }else{
            var html = $(obj).parent().parent().html();
            $(obj).parent().parent().before("<tr style='display: none'>"+html+"</tr>");
            $(obj).parent().parent().remove();
        }
    }else{
        $.ajax({
            url:"category/deletecategory",
            type:"post",
            data:"id="+id,
            dataType:"json",
            success:function(rs){
                rs = eval(rs);
                if(rs.rs=="success"){
                    if(type!=null){
                        $(obj).parent().parent().parent().remove();
                    }else{
                        $(obj).parent().parent().remove();
                    }
                }
            }
        })
    }
}

//添加二级分类
function savechild(obj){
    var catName = $(obj).parent().parent().find("input[name='catName']").val();
    var sort = $(obj).parent().parent().find("input[name='sort']").val();
    var pid = $(obj).parent().parent().attr("pid");
    var tid = $(obj).parent().parent().attr("tid");

    //判空、判断编号格式
    if(catName==""||catName==null){
        alert("请填写分类名称");
        exit;
    }
    if(sort==""||sort==null){
        alert("请填写编号");
        exit;
    }else if(isNaN(sort) || !(/^\d$/).test(sort) ){
        alert("编号只能是01-99的两位数字");
        exit;
    }

    $.ajax({
        url:"category/savecategory",
        type:"post",
        data:"catName="+catName+"&sort="+sort+"&pid="+pid+"&tid=" + tid,
        dataType:"json",
        success:function(rs){
            rs=eval(rs);
            if(rs.rs=="fail"){
                alert("添加失败，请查看编号是否重复");
            }
            if(rs.rs=="success"){
                alert("添加成功");
                var html=$(obj).parent().parent().html();
                $(obj).attr("onclick","update(this)");
                $(obj).parent().parent().attr("id",rs.id);
                $(obj).next().attr("onclick","delcategory(this)");
                $(obj).parent().parent().after("<tr style='display: none'>"+html+"</tr>");
            }
        }
    })
}
//更新商品分类
function update(obj){
    var id = $(obj).parent().parent().attr("id");
    var pid = $(obj).parent().parent().attr("pid");
    if(pid==null||pid=="") pid=0;
    var catName = $(obj).parent().parent().find("input[name='catName']").val();
    var sort = $(obj).parent().parent().find("input[name='sort']").val();

    //判空、判断编号格式
    if(catName==""||catName==null){
        alert("请填写分类名称");
        exit;
    }
    if(sort==""||sort==null){
        alert("请填写编号");
        exit;
    }else if(isNaN(sort) || !(/^\d$/).test(sort) ){
        alert("编号只能是01-99的两位数字");
        exit;
    }

    $.ajax({
        url:"category/savecategory",
        type:"post",
        data:"id="+id+"&catName="+catName+"&sort="+sort+"&pid="+pid,
        dataType:"json",
        success:function(rs){
            rs=eval(rs);
            if(rs.rs=="success"){
                alert("更新成功");
            }
            if(rs.rs=="fail"){
                alert("更新失败，请查看编号是否重复");
            }
        }
    })
}