var _uUserId="";
var userInfo={};

$(function() {
  var url=_URL_BASE+"/wx/api/personalCenter";
  $.ajax({type:"post", async:true, url:url, data:null, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        initPage(json.userInfo);
        $("#dataList").show();
      } else {
        window.location.href=_URL_BASE+"/wxfront/err.html?1000=抱歉<br/>无法获得您的个人信息<br/>禁止录入";
      }
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
      window.location.href=_URL_BASE+"/wxfront/err.html?2000=系统错误<br/>status="
        +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
    }
  });
});

function initPage(data) {
  userInfo=data;
  _uUserId=data.userid;
  var url=_URL_BASE+"/wx/api/getRecord01List";
  var _data={};
  _data.pageSize=7;
  _data.pag=1;
  _data.userId=_uUserId;
  $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        fillList(json.data);
        $("#dataList").show();
      } else {
        window.location.href=_URL_BASE+"/wxfront/err.html?1000=抱歉<br/>无法获得您的个人信息<br/>禁止录入";
      }
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
      window.location.href=_URL_BASE+"/wxfront/err.html?2000=系统错误<br/>status="
        +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
    }
  });
}

function fillList(data) {
  var _updateUrl=_URL_BASE+"/wxfront/input/record01.html?type=update";
  var _viewUrl=_URL_BASE+"/wxfront/view/record01.html";
  if (data==null||data.length==0||!(data instanceof Array)) {
    $("#dataList").html("没有记录");
    return;
  }
  var html="";
  for (var i=0; i<data.length; i++) {
    var oneData=data[i];
    var name=oneData.custName;
    if (oneData.custSex) name=name+"("+oneData.custSex+")";
    name=name+"<br/>";
    var phone="<span><a href='tel:"+oneData.custPhoneNum+"'>"+oneData.custPhoneNum+"</a></span><br/>";
    var cTime=new Date();
    cTime.setTime(oneData.cTime.time);
    var fTime="<span class='sftime'>首访时间："+cTime.Format('yyyy年MM月dd日')+"</span>";
    //顾问
    var status="<span class='ysh'>已审核</span>";
    var _url=_viewUrl+"?recordId="+oneData.id;
    if (oneData.status==1) status="<span class='ysh'>审核中</span>";
    if (oneData.status==2) status="<span class='ysh'>已通过</span>";
    if (oneData.status==3) status="<span class='ysh'>已作废</span>";
    if (oneData.status==4) {//退回
      status="<span>未通过</span>";
      _url=_updateUrl+"&recordId="+oneData.id;
    }
    if (userInfo.roleName!='顾问') {
        status="<span class='ysh'>已审核</span>";
        if (oneData.status==1) status="<span>待审核</span>";
        if (oneData.status==2) status="<span class='ysh'>已通过</span>";
        if (oneData.status==3) status="<span class='ysh'>未通过</span>";
        if (oneData.status==4) status="<span class='ysh'>未通过</span>";
    }
    html+="<div class='item_sflr row' onclick=\"openNew('"+_url+"')\"><div class='col-60 item-name2'>"+name+phone+fTime+"</div>"
      +"<div class='col-40 item-name'><br>总次：1次    未成交<br>"+status+"</div></div>";
  }
  if (html) $("#dataList").html(html);
  else $("#dataList").html("没有记录");
}

function openNew(url) {
  window.location.href=url;
}