function deleteP(pid){
    if(confirm("确认删除该分类?")){
        if(confirm("是否删除该分类下的所有子分类?")){
            $.post('nav/delete',{pid:pid,flag:'yes'},function(){
                location.href='nav/index';
            })
        }else{
            $.post('nav/delete',{pid:pid,flag:'no'},function(){
                location.href='nav/index';
            })
        }
    }
}function deleteC(id){
    if(confirm("确认删除该分类?")){
        $.post('nav/delete',{pid:id,flag:'yes'},function(){
            location.href='nav/index';
        })
    }
}