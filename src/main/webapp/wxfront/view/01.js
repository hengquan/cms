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
  userInfo.roleName="项目负责人";
  _uUserId=data.userid;
  var url=_URL_BASE+"/wx/api/getRecord01?recordId="+recordId;
  $.ajax({type:"post", async:true, url:url, data:null, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        fillData(json.data);
        $("#oneData").show();
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
  var needAudit=false;//是否需要审核
  if (data.custname) $("#custName").html(data.custname);
  if (data.custphonenum) $("#custPhone").html("<a href='tel:"+data.custphonenum+"'>"+data.custphonenum+"</a>");
  if (data.custsex) $("#custSex").html(data.custsex);
  if (data.firstknowtime) {
    var fTime=new Date();
    fTime.setTime(data.firstknowtime.time);
    $("#firstKnowTime").html(fTime.Format('yyyy-MM-dd'));
  }
  if (data.receptime) {
    var rTime=new Date();
    rTime.setTime(data.receptime.time);
    $("#curTime").html(rTime.Format('yyyy-MM-dd'));
  }
  if (data.agegroup) $("#ageGroup").html(data.agegroup);
  if (data.localresidence) $("#localResidence").html(data.localresidence);
  if (data.outresidence) $("#outResidence").html(data.outresidence);
  if (data.localworkarea) $("#localWorkArea").html(data.localworkarea);
  if (data.outworkarea) $("#outWorkArea").html(data.outworkarea);
  if (data.familystatus) $("#familyStatus").html(data.familystatus);
  if (data.traffictype) {
    var _temp=data.traffictype;
    if (data.traffictype.indexOf('其他')!=-1) {
      if (data.traffictypedesc) {
        var _temp2="其他("+data.traffictypedesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    $("#trafficType").html(_temp);
  }
  if (data.buyqualify) $("#buyQualify").html(data.buyqualify);
  if (data.workindustry) {
    var _temp=data.workindustry;
    if (data.workindustry.indexOf('其他')!=-1) {
      if (data.workindustrydesc) {
        var _temp2="其他("+data.workindustrydesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    $("#workIndustry").html(_temp);
  }
  if (data.enterprisetype) {
    var _temp=data.enterprisetype;
    if (data.enterprisetype.indexOf('其他')!=-1) {
      if (data.enterprisetypedesc) {
        var _temp2="其他("+data.enterprisetypedesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    $("#enterpriseType").html(_temp);
  }
  if (data.knowway) {
    var _temp=data.knowway;
    if (data.knowwaydesc) {
      var s=(data.knowwaydesc).split(",");
      for (var j=0;j<s.length;j++) {
        var temp2=s[j];
        var s2=temp2.split("=");
        temp2=temp2.replace("=", "(");
        temp2+=")";
        _temp=_temp.replace(s2[0], temp2);
      }
    }
    $("#knowWay").html(_temp);
  }
  if (data.estcustworth) $("#estCustWorth").html(data.estcustworth);
  if (data.investtype) {
    var _temp=data.investtype;
    if (data.investtype.indexOf('其他')!=-1) {
      if (data.investtypedesc) {
        var _temp2="其他("+data.investtypedesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    $("#investType").html(_temp);
  }
  if (data.capitalprepsection) $("#capitalPrepSection").html(data.capitalprepsection);
  if (data.investtype) {
    var _temp=data.realtyproducttype;
    if (data.realtyproducttype.indexOf('其他')!=-1) {
      if (data.realtyproducttypedesc) {
        var _temp2="其他("+data.realtyproducttypedesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    $("#realtyProductType").html(_temp);
  }
  if (data.attentacreage) $("#attentAcreage").html(data.attentacreage);
  if (data.pricesection) $("#priceSection").html(data.pricesection);
  if (data.buypurpose) {
    var _temp=data.buypurpose;
    if (data.buypurpose.indexOf('其他')!=-1) {
      if (data.buypurposedesc) {
        var _temp2="其他("+data.buypurposedesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    $("#buyPurpose").html(_temp);
  }
  if (data.attentionpoint) {
    var _temp=data.attentionpoint;
    if (data.attentionpoint.indexOf('其他')!=-1) {
      if (data.attentionpointdesc) {
        var _temp2="其他("+data.attentionpointdesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    $("#attentionPoint").html(_temp);
  }
  if (data.receptimesection) $("#recepTimeSection").html(data.receptimesection);
  if (data.custscore) $("#custScore").html(data.custscore);
  if (data.compareprojs) $("#compareProjs").html(data.compareprojs);
  if (data.custdescn) $("#custDescn").html(data.custdescn);
  if (userInfo.roleName=='项目负责人'&&data.status==1) needAudit=true;
  if (needAudit) $("#operArea").show();
}

function auditOk() {
  var url=_URL_BASE+"/wx/api/addAudit";
  var _data={};
  _data.recordId=recordId;
  _data.recordType=1;
  _data.auditType=1;//通过
  $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        alert("审核通过");
        window.location.href=_URL_BASE+"/wxfront/search/record01.html";
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
  _data.recordType=1;
  _data.auditType=2;//通过
  _data.auditMsg=$("#auditMsg").val();
  $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        alert("记录已打回录入顾问");
        window.location.href=_URL_BASE+"/wxfront/search/record01.html";
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