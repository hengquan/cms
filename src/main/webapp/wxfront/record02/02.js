var _uUserId="";
var _uProjName="";
var _uProjId="";

$(function() {
  $(".subNav").click(function(){
    $(this).toggleClass("currentDd").siblings(".subNav").removeClass("currentDd");
    $(this).toggleClass("currentDt").siblings(".subNav").removeClass("currentDt");
    $(this).next(".navContent").slideToggle(500).siblings(".navContent").slideUp(500);
  });
  $("#auditArea").hide();
  _TYPE=getUrlParam(window.location.href, 'type');
  if (_TYPE==null) _TYPE="add";
  if (_TYPE.toLocaleLowerCase()=='update') _TYPE="update";
  $(document).attr("title","客户数据中心-复访信息录入");

  if (_TYPE=='add') initData();
  else if (_TYPE=='update') {
    $("#auditArea").show();
    $(document).attr("title","客户数据中心-复访信息修改");
    //获得本条记录消息信息
    var recordId=getUrlParam(window.location.href, 'recordId');
    if (!recordId) window.location.href=_URL_BASE+"/wxfront/err.html?3000=无记录Id";
    else {
      var _data={};
      _data.recordId=recordId;
      var url=_URL_BASE+"/wx/api/getRecord01";
      $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
        success: function(json) {
          if (json.msg=='100') {
//            initData();
//            fillData(json.data);
          } else {
            window.location.href=_URL_BASE+"/wxfront/err.html?1000=抱歉<br/>无法获得复访录入信息";
          }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
          window.location.href=_URL_BASE+"/wxfront/err.html?2000=系统错误<br/>status="
            +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
        }
      });
    }
  }
});

function initData() {
  var url=_URL_BASE+"/wx/api/personalCenter";
  $.ajax({type:"post", async:true, url:url, data:null, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        initPage(json.userInfo);
        $("#step1").show();
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
function initPage(data) {
  cleanDate();
  var _cn=getUrlParam(window.location.href, 'custName');
  if (_cn) {
    $("input[name='custName']").val(decodeURIComponent(_cn));
    $("input[name='custName']").attr("readonly",true);
  }
  var _cm=getUrlParam(window.location.href, 'custPhone');
  if (_cm) {
    $("input[name='custPhone']").val(decodeURIComponent(_cn));
    $("input[name='custPhone']").attr("readonly",true);
  }
  var _cp=getUrlParam(window.location.href, 'projId');

  var _cs=getUrlParam(window.location.href, 'sex');
  
  $("input[name='user']").val(data.realname);
  _uUserId=data.userid;
  var canShowProj=false;
  var prjNames=""+data.checkProj;
  var lPrj=prjNames.split(',');
  var projHtml="";
  var projId,projName;
  for (var i=0;i<lPrj.length; i++) {
    if ($.trim(lPrj[i])!='') {
      if (lPrj[i].indexOf('-')!=-1) {
        _uProj=lPrj[i];
        if (!canShowProj) canShowProj=true;
        projId=lPrj[i].substring(0,lPrj[i].indexOf('-'));
        projName=lPrj[i].substring(lPrj[i].indexOf('-')+1);
        projHtml+='<label><input type="radio" name="proj" value="'+projId+'-'+projName+'" _text="'+projName+'" onclick="selProj()"/> '+projName+' </label>';
      }
    }
  }
  if (canShowProj&&lPrj.length>1) {
    $("#projData").html(projHtml);
    $("#projArea").show(); 
  } else {
    _uProjName=projName;
    _uProjId=projId;
  }

}

function cleanDate() {
  var nt=new Date();
  var str=""+nt.getFullYear()+"-";
  str+=((100+(nt.getMonth()+1))+"").substr(1)+"-";
  str+=((100+nt.getDate())+"").substr(1);
  $("input[name='curTime']").val(str);
}