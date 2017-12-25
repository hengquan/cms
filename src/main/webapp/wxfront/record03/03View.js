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

var data03={};
var customer={};
function initPage(data) {
  userInfo=data;
  var url=_URL_BASE+"/wx/api/getRecord03?recordId="+recordId;
  $.ajax({type:"post", async:true, url:url, data:null, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        data03=json.data;
        var ctime = data03.ctime;
        var auditRecord = json.auditRecord;
        var url=_URL_BASE+"/wx/api/getCustMsg";
        var _data={};
        _data.custId=data03.custid;
        _data.projId=data03.projid;
        $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
          success: function(json) {
            if (json.msg=='100') customer=json.customer;
            fillData(json.data,auditRecord,ctime);
          },
          error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("获得客户信息时出现系统错误：\nstatu="+XMLHttpRequest.status+"\nreadyState="+XMLHttpRequest.readyState+"\ntext="+textStatus+"\nerrThrown="+errorThrown);
            fillData();
          }
        });
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

function fillData(data,auditRecord,ctime) {
  var gwmc=decodeURIComponent(getUrlParam(window.location.href, 'authorName'));
  if (gwmc&&gwmc!='null') {
    $("#authorName").html(gwmc);
    $("#GW").show();
  } else $("#GW").hide();
  var needAudit=false;//是否需要审核
  if (data03.custname) $("#custName").html(data03.custname);
  if (data03.custphonenum) {
    var phoneHtml="";
    var _flag=0;
    var phones=data03.custphonenum.split(",");
    var _check1,_check2;
    for (var i=0; i<phones.length; i++) {
      var onePhone=$.trim(phones[i]);
      _check1=checkMPhone(onePhone);
      if (_check1==0) continue;
      _check2=checkDPhone(onePhone);
      if (_check1==1||_check2==1) {
        if (_flag++%2==0) phoneHtml+="<br/>";
        phoneHtml+="<span><a href='tel:"+onePhone+"'>"+onePhone+"</a></span>";
      }
    }
    $("#custPhone").html(phoneHtml.substring(5));
  }
  if (customer.custsex) $("#custSex").html(customer.custsex);

  if (data03.buyername) $("#buyerName").html(data03.buyername);
  if (data03.buyerphonenum) {
    var phoneHtml="";
    var _flag=0;
    var phones=data03.buyerphonenum.split(",");
    var _check1,_check2;
    for (var i=0; i<phones.length; i++) {
      var onePhone=$.trim(phones[i]);
      _check1=checkMPhone(onePhone);
      if (_check1==0) continue;
      _check2=checkDPhone(onePhone);
      if (_check1==1||_check2==1) {
        if (_flag++%2==0) phoneHtml+="<br/>";
        phoneHtml+="<span><a href='tel:"+onePhone+"'>"+onePhone+"</a></span>";
      }
    }
    $("#buyerPhone").html(phoneHtml.substring(5));
  }
  if (customer.buyersex) $("#buyerSex").html(customer.buyersex);

  if (customer.visitcount) $("#visitCount").html(customer.visitcount);
  if (data03.firstknowtime.time) {
    var rTime=new Date();
    rTime.setTime(data03.firstknowtime.time);
    $("#firstKnowTime").html(rTime.Format('yyyy-MM-dd'));
  }
  if (customer.firstvisittime.time) {
    var rTime=new Date();
    rTime.setTime(customer.firstvisittime.time);
    $("#firstVisitTime").html(rTime.Format('yyyy-MM-dd'));
  }
  if (data03.purchasedate.time) {
    var rTime=new Date();
    rTime.setTime(data03.purchasedate.time);
    $("#purchaseDate").html(rTime.Format('yyyy-MM-dd'));
  }
  if (data03.signdate.time) {
    var rTime=new Date();
    rTime.setTime(data03.signdate.time);
    $("#signDate").html(rTime.Format('yyyy-MM-dd'));
  }
  
  if (ctime) {
	    var rTime=new Date();
	    rTime.setTime(ctime.time);
	    $("#attentionTime").html(rTime.Format('yyyy-MM-dd'));
  }
  if (auditRecord!='') {
	  var rTime=new Date();
	  rTime.setTime(auditRecord.ctime.time);
	  $("#checkTime").html(rTime.Format('yyyy-MM-dd'));
  }
    
  if (customer.visitcount) $("#visitCount").html(customer.visitcount);
  if (data03.housenum) $("#houseNum").html(data03.housenum);
  if (data03.houseregitype) $("#houseRegiType").html(data03.houseregitype);
  if (data03.houseacreage) $("#houseAcreage").html(data03.houseacreage);
  if (data03.unitprice) $("#unitPrice").html(data03.unitprice);
  if (data03.totalprice) $("#totalPrice").html(data03.totalprice);
  if (data03.paymenttype) {
    var _temp=data03.paymenttype;
    if (data03.paymenttype.indexOf('其他')!=-1) {
      if (data03.paymenttypedesc) {
        var _temp2="其他("+data03.paymenttypedesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    $("#paymentType").html(_temp);
  }
  if (data03.loanbank) $("#loanBank").html(data03.loanbank);
  if (data03.realtyproducttype) {
    var _temp=data03.realtyproducttype;
    if (data03.realtyproducttype.indexOf('其他')!=-1) {
      if (data03.realtyproducttypedesc) {
        var _temp2="其他("+data03.realtyproducttypedesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    $("#realtyProductType").html(_temp);
  }
  if (data03.addressmail) $("#addressMail").html(data03.addressmail);
  if (data03.livingstatus) $("#livingStatus").html(data03.livingstatus);
  if (data03.realusemen) {
    var _temp=data03.realusemen;
    if (data03.realusemen.indexOf('其他')!=-1) {
      if (data03.realusemendesc) {
        var _temp2="其他("+data03.realusemendesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    $("#realUseMen").html(_temp);
  }
  if (data03.realpaymen) {
    var _temp=data03.realpaymen;
    if (data03.realpaymen.indexOf('其他')!=-1) {
      if (data03.realpaymendesc) {
        var _temp2="其他("+data03.realpaymendesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    $("#realPayMen").html(_temp);
  }
  if (data03.suggestion) $("#suggestion").html(data03.suggestion);
  if (data03.talkqands) $("#talkQandS").html(data03.talkqands);
  if (data03.signqands) $("#signQandS").html(data03.signqands);
  if (data03.sumdescn) $("#sumDescn").html(data03.sumdescn);
  if (userInfo.roleName=='项目负责人'&&data03.status==1) needAudit=true;
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
  var _auditMsg=$("#auditMsg").val();
  if (!_auditMsg) {
    alert("请填写退回理由！");
    return;
  }
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