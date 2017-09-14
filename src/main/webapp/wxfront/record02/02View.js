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

var data02={};
var customer={};
function initPage(data) {
  userInfo=data;
  _uUserId=data.userid;
  var url=_URL_BASE+"/wx/api/getRecord02?recordId="+recordId;
  $.ajax({type:"post", async:true, url:url, data:null, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        data02=json.data;
        var url=_URL_BASE+"/wx/api/getCustMsg";
        var _data={};
        _data.custId=data02.custid;
        _data.projId=data02.projid;
        $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
          success: function(json) {
            if (json.msg=='100') {
              customer=json.customer;
              fillData();
            }
          },
          error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("获得客户信息时出现系统错误：\nstatu="+XMLHttpRequest.status+"\nreadyState="+XMLHttpRequest.readyState+"\ntext="+textStatus+"\nerrThrown="+errorThrown);
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

function fillData() {
  var data=data02;
  if (!data) return;
  var gwmc=decodeURIComponent(getUrlParam(window.location.href, 'GWMC'));
  if (gwmc&&gwmc!='null') {
    $("#authorName").html(gwmc);
    $("#GW").show();
  } else $("#GW").hide();
  var needAudit=false;//是否需要审核
  if (data.custname) $("#custName").html(data.custname);
  if (data.custphonenum) {
    var phoneHtml="";
    var _flag=0;
    var phones=data.custphonenum.split(",");
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
  if (customer.visitcount) $("#visitCount").html(customer.visitcount);
  if (data.receptime) {
    var rTime=new Date();
    rTime.setTime(data.receptime.time);
    $("#recpTime").html(rTime.Format('yyyy-MM-dd'));
  }
  if (customer.ctime) {
    var rTime=new Date();
    rTime.setTime(data.ctime.time);
    $("#curTime").html(rTime.Format('yyyy-MM-dd'));
  }
  if (data.visitorcount) $("#visitorCount").html(data.visitorcount==5?data.visitorcount+"人以上":data.visitorcount+"人");
  if (data.decisionerin) $("#decisionerIn").html(data.decisionerin==1?"是":(data.outeduwill==-1?"无法了解":"否"));
  if (data.visitorrefs) {
    var _temp=data.visitorrefs;
    if (data.visitorrefs.indexOf('其他')!=-1) {
      if (data.visitorrefsdesc) {
        var _temp2="其他("+data.visitorrefsdesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    $("#visitorRefs").html(_temp);
  }
  if (data.childrennum) $("#childrenNum").html(data.childrennum);
  if (data.childagegroup) $("#childAgeGroup").html(data.childagegroup);
  if (data.schooltype) $("#schoolType").html(data.schooltype);
  if (data.schoolname) $("#schoolName").html(data.schoolname);
  if (data.childavocations) {
    var _temp=data.childavocations;
    if (data.childavocations.indexOf('其他')!=-1) {
      if (data.childavocationsdesc) {
        var _temp2="其他("+data.childavocationsdesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    $("#childAvocations").html(_temp);
  }
  if (data.outeduwill) $("#outEduWill").html(data.outeduwill==1?"是":(data.outeduwill==-1?"无法了解":"否"));
  if (data.outexperflag) $("#outExperFlag").html(data.outexperflag==1?"是":(data.outeduwill==-1?"无法了解":"否"));
  if (data.outexpercity) $("#outExperCity").html(data.outexpercity);
  if (data.childoutexperflag) $("#childOutExperFlag").html(data.childoutexperflag==1?"是":(data.outeduwill==-1?"无法了解":"否"));
  if (data.childoutexpercity) $("#childOutExperCity").html(data.childoutexpercity);
  if (data.fulltimewifeflag) $("#fullTimeWifeFlag").html(data.fulltimewifeflag==1?"是":(data.fulltimewifeflag==-1?"无法了解":"否"));
  if (data.nannyflag) $("#nannyFlag").html(data.nannyflag==1?"是":(data.nannyflag==-1?"无法了解":"否"));
  if (data.petflag) $("#petFlag").html(data.petflag==1?"是":(data.petflag==-1?"无法了解":"否"));
  if (data.livingradius) $("#livingRadius").html(data.livingradius);
  if (data.communityname) $("#communityName").html(data.communityname);
  if (data.housetype) $("#houseType").html(data.housetype);
  if (data.liveacreage) $("#liveAcreage").html(data.liveacreage);
  if (data.loanstatus) $("#loanStatus").html(data.loanstatus);
  if (data.enterprisename) $("#enterpriseName").html(data.enterprisename);
  if (data.enterpriseaddress) $("#enterpriseAddress").html(data.enterpriseaddress);
  if (data.enterprisepost) $("#enterprisePost").html(data.enterprisepost);
  if (data.housecount) $("#houseCount").html(data.housecount);
  if (data.carfamilycount) $("#carFamilyCount").html(data.carfamilycount);
  if (data.carbrand) $("#carBrand").html(data.carbrand);
  if (data.cartotalprice) $("#carTotalPrice").html(data.cartotalprice);
  if (data.attentwx) $("#attentWX").html(data.attentwx);
  if (data.appnames) $("#appNames").html(data.appnames);
  if (data.avocations) {
    var _temp=data.avocations;
    if (data.avocations.indexOf('其他')!=-1) {
      if (data.avocationsdesc) {
        var _temp2="其他("+data.avocationsdesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    $("#avocations").html(_temp);
  }
  if (data.resistpoint) {
    var _temp=data.resistpoint;
    if (data.resistpoint.indexOf('其他')!=-1) {
      if (data.resistpointdesc) {
        var _temp2="其他("+data.resistpointdesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    $("#resistPoint").html(_temp);
  }
  if (data.loveactivation) {
    var _temp=data.loveactivation;
    if (data.loveactivation.indexOf('其他')!=-1) {
      if (data.loveactivationdesc) {
        var _temp2="其他("+data.loveactivationdesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    $("#loveActivation").html(_temp);
  }
  if (data.freetimesection) $("#freeTimeSection").html(data.freetimesection);
  if (data.receptimesection) $("#recepTimeSection").html(data.receptimesection);
  if (data.custscore) $("#custScore").html(data.custscore);
  if (data.compareprojs) $("#compareProjs").html(data.compareprojs);
  if (data.descn) $("#custDescn").html(data.descn);
  if (userInfo.roleName=='项目负责人'&&data.status==1) needAudit=true;
  if (needAudit) $("#operArea").show();
  $('body').css("display", "block");
}

function auditOk() {
  var url=_URL_BASE+"/wx/api/addAudit";
  var _data={};
  _data.recordId=recordId;
  _data.recordType=2;
  _data.auditType=1;//通过
  $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        alert("审核通过");
        window.location.href=_URL_BASE+"/wxfront/record02/record02Search.html";
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
  _data.recordType=2;
  _data.auditType=2;//通过
  _data.auditMsg=$("#auditMsg").val();
  $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        alert("未通过审核，信息已退回顾问");
        window.location.href=_URL_BASE+"/wxfront/record02/record02Search.html";
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