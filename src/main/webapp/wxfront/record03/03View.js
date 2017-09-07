var _uUserId="";
var userInfo={};
var recordId="";
$(function() {
  recordId=getUrlParam(window.location.href, 'recordId');
  if (!recordId) {
    window.location.href=_URL_BASE+"/wxfront/err.html?9000=系统错误<br/>无法获得记录Id";
    return;
  }
  var url=_URL_BASE+"/wx/api/personalCenter";
  $.ajax({type:"post", async:true, url:url, data:null, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        initPage(json.userInfo);
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
  var url=_URL_BASE+"/wx/api/getRecord03?recordId="+recordId;
  //var url=_URL_BASE+"/wx/api/getRecord03?recordId=123123123123";
  $.ajax({type:"post", async:true, url:url, data:null, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        fillData(json.data);
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

function fillData(data) {
  if (!data) return;
  var gwmc=decodeURIComponent(getUrlParam(window.location.href, 'GWMC'));
  if (gwmc&&gwmc!='null') {
    $("#authorName").html(gwmc);
    $("#GW").show();
  } else $("#GW").hide();
  var needAudit=false;//是否需要审核
  if (data.custname) $("#custName").html(data.custname);
  if (data.custphonenum) $("#custPhone").html("<a href='tel:"+data.custphonenum+"'>"+data.custphonenum+"</a>");
  if (data.custsex) $("#custSex").html(data.custsex);
  if (data.purchasedate) {
    var rTime=new Date();
    rTime.setTime(data.purchasedate.time);
    $("#purchaseDate").html(rTime.Format('yyyy-MM-dd'));
  }
  if (data.signdate) {
    var rTime=new Date();
    rTime.setTime(data.signdate.time);
    $("#signDate").html(rTime.Format('yyyy-MM-dd'));
  }
  if (data.housenum) $("#houseNum").html(data.housenum);
  if (data.houseregitype) $("#houseRegiType").html(data.houseregitype);
  if (data.houseacreage) $("#houseAcreage").html(data.houseacreage);
  if (data.unitprice) $("#unitPrice").html(data.unitprice);
  if (data.totalprice) $("#totalPrice").html(data.totalprice);
  if (data.paymenttype) {
    var _temp=data.paymenttype;
    if (data.paymenttype.indexOf('其他')!=-1) {
      if (data.paymenttypedesc) {
        var _temp2="其他("+data.paymenttypedesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    $("#paymentType").html(_temp);
  }
  if (data.loanbank) $("#loanBank").html(data.loanbank);
  if (data.realtyproducttype) {
    var _temp=data.realtyproducttype;
    if (data.realtyproducttype.indexOf('其他')!=-1) {
      if (data.realtyproducttypedesc) {
        var _temp2="其他("+data.realtyproducttypedesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    $("#realtyProductType").html(_temp);
  }
  if (data.addressmail) $("#addressMail").html(data.addressmail);
  if (data.livingstatus) $("#livingStatus").html(data.livingstatus);
    if (data.realusemen) {
    var _temp=data.realusemen;
    if (data.realusemen.indexOf('其他')!=-1) {
      if (data.realusemendesc) {
        var _temp2="其他("+data.realusemendesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    $("#realUseMen").html(_temp);
  }
  if (data.realpaymen) {
    var _temp=data.realpaymen;
    if (data.realpaymen.indexOf('其他')!=-1) {
      if (data.realpaymendesc) {
        var _temp2="其他("+data.realpaymendesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    $("#realPayMen").html(_temp);
  }
  if (data.suggestion) $("#suggestion").html(data.suggestion);
  if (data.talkqandS) $("#talkQandS").html(data.talkqandS);
  if (data.signqandS) $("#signQandS").html(data.signqandS);
  if (data.sumdescn) $("#sumDescn").html(data.sumdescn);
  if (userInfo.roleName=='项目负责人'&&data.status==1) needAudit=true;
  if (needAudit) $("#operArea").show();
  $('body').css("display", "block");
}

function auditOk() {
  var url=_URL_BASE+"/wx/api/addAudit";
  var _data={};
  _data.recordId=recordId;
  _data.recordType=3;
  _data.auditType=1;//通过
  $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        alert("审核通过");
        window.location.href=_URL_BASE+"/wxfront/record03/record03Search.html";
      } else {
        alert("审核通过提交失败");
      }
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
      window.location.href=_URL_BASE+"/wxfront/err.html?2000=系统错误<br/>status="
        +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
    }
  });
}

function auditNo() {
  var url=_URL_BASE+"/wx/api/addAudit";
  var _data={};
  _data.recordId=recordId;
  _data.recordType=3;
  _data.auditType=2;//通过
  _data.auditMsg=$("#auditMsg").val();
  $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        alert("未通过审核，信息已退回顾问");
        window.location.href=_URL_BASE+"/wxfront/record03/record03Search.html";
      } else {
        alert("审核未通过提交失败");
      }
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
      window.location.href=_URL_BASE+"/wxfront/err.html?2000=系统错误<br/>status="
        +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
    }
  });
}