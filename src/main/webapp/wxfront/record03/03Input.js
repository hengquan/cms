var _uUserId="";
var _uProjId="";
var _uProjName="";
var _uSex="";
var _uUser="";
var _uHouseRegiType="";
var _uPaymentType="";
var _uPaymentType="";
var _uRealtyProductType="";
var _uLivingStatus="";
var _uRealUseMen="";
var _uRealPayMen="";


//复选单选处理
function selProj() {
  var choose=document.getElementsByName('proj');
  for (var i=0; i<choose.length; i++) {
    if (choose[i].checked) {
      $("#proj").html(choose[i].getAttribute("_text"));
      _uProjName=choose[i].getAttribute("_text");
      var oldProjId=_uProjId;
      _uProjId=choose[i].value.substring(0, choose[i].value.indexOf('-'));
      if (_uProjId!=oldProjId) loadProjUser(_uProjId);
    }
  }
  $("#projModal").modal('hide');
  if (_uProjId!="") $("#cleanProjBtn").show();
}
function selUser() {
  var choose=document.getElementsByName('user');
  for (var i=0; i<choose.length; i++) {
    if (choose[i].checked) {
      $("span[name='userInput']").html(choose[i].getAttribute("_text"));
      _uUserId=choose[i].value.substring(0, choose[i].value.indexOf('-'));
    }
  }
  $("#userModal").modal('hide');
  if (_uUserId!="") $("#cleanUserBtn").show();
}
var vueStep1=new Vue({
  el: "#step1",
  methods: {
    cleanUser: function() {
      $("span[name='userInput']").html("&nbsp;");
      _uUserId="";
      $("#cleanUserBtn").hide();
      fillSelectField('user', "", false);
    },
    cleanProj: function() {
      $("#proj").html("&nbsp;");
      _uProjId="";
      _uProjName="";
      $("#cleanProjBtn").hide();
      fillSelectField('proj', "", false);
      this.cleanUser();
    },
    selSex: function() {
      var choose=document.getElementsByName('sex');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#sex").html(choose[i].getAttribute("_text"));
          _uSex=choose[i].value;
        }
      }
      $("#sexModal").modal('hide');
      if (_uSex!="") $("#cleanSexBtn").show();
    },
    cleanSex: function() {
      $("#sex").html("&nbsp;");
      _uSex="";
      $("#cleanSexBtn").hide();
      fillSelectField('sex', "", false);
    },
    selUser: function() {
      var choose=document.getElementsByName('user');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#user").html(choose[i].getAttribute("_text"));
          _uUser=choose[i].value;
        }
      }
      $("#userModal").modal('hide');
      if (_uUser!="") $("#cleanUserBtn").show();
    },
    cleanUser: function() {
      $("#user").html("&nbsp;");
      _uUser="";
      $("#cleanUserBtn").hide();
      fillSelectField('user', "", false);
    },
    selHouseRegiType: function() {
      var choose=document.getElementsByName('houseRegiType');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#houseRegiType").html(choose[i].getAttribute("_text"));
          _uGgeGroup=choose[i].value;
        }
      }
      $("#houseRegiTypeModal").modal('hide');
      if (_uGgeGroup!="") $("#cleanHouseRegiTypeBtn").show();
    },
    cleanHouseRegiType: function() {
      $("#houseRegiType").html("&nbsp;");
      _uGgeGroup="";
      $("#cleanHouseRegiTypeBtn").hide();
      fillSelectField('houseRegiType', "", false);
    },
    selPaymentType: function() {
      var choose=document.getElementsByName('paymentType');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#paymentType").html(choose[i].getAttribute("_text"));
          _uGgeGroup=choose[i].value;
        }
      }
      $("#paymentTypeModal").modal('hide');
      if (_uGgeGroup!="") $("#cleanPaymentTypeBtn").show();
    },
    cleanPaymentType: function() {
      $("#paymentType").html("&nbsp;");
      _uGgeGroup="";
      $("#cleanPaymentTypeBtn").hide();
      fillSelectField('paymentType', "", false);
    },
    selPaymentType: function() {
      _uPaymentType="";
      _uPaymentTypeDesc="";
      var choose=document.getElementsByName('paymentType');
      var ttArray="";
      var selOther=false;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          var oneText=choose[i].getAttribute("_text");
          if (choose[i].getAttribute("_text")=='其他') {
            selOther=true;
            if ($.trim($("input[name='paymentTypeDesc']").val())!="") {
              oneText=oneText+"("+$.trim($("input[name='paymentTypeDesc']").val())+")";
              _uPaymentTypeDesc=$.trim($("input[name='paymentTypeDesc']").val());
            }
          } else {
            _uPaymentTypeDesc="";
            $("input[name='paymentTypeDesc']").val("");
            $("input[name='paymentTypeDesc']").hide();
          }
          ttArray+=","+oneText;
          _uPaymentType+=","+choose[i].value;
        }
      }
//      if (selOther&&_uPaymentTypeDesc=="") alert("请录入“其他”从事行业方式");
//      else {
        if (_uPaymentType.length>0) {
          _uPaymentType=_uPaymentType.substr(1);
          ttArray=ttArray.substr(1);
        }
        $("#paymentType").html(ttArray==""?"&nbsp;":ttArray);
        $("#paymentTypeModal").modal('hide');
        if (_uPaymentType!="") $("#cleanPaymentTypeBtn").show();
//      }
    },
    cleanPaymentType: function() {
      $("#paymentType").html("&nbsp;");
      _uPaymentType="";
      _uPaymentTypeDesc="";
      $("#cleanPaymentTypeBtn").hide();
      $("input[name='paymentTypeDesc']").val("");
      $("input[name='paymentTypeDesc']").hide();
      fillSelectField('paymentType', "", false);
    },
    clickPaymentType: function() {
      var otherCheck=false;
      var choose=document.getElementsByName('paymentType');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked&&choose[i].getAttribute("_text")=='其他') {
          otherCheck=true;
          break;
        }
      }
      if (otherCheck) $("input[name='paymentTypeDesc']").show(); else $("input[name='paymentTypeDesc']").hide();
    }
  }
});

var vueStep2=new Vue({
  el: "#step2",
  methods: {
  selRealtyProductType: function() {
      _uRealtyProductType="";
      _uRealtyProductTypeDesc="";
      var choose=document.getElementsByName('realtyProductType');
      var ttArray="";
      var selOther=false;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          var oneText=choose[i].getAttribute("_text");
          if (choose[i].getAttribute("_text")=='其他') {
            selOther=true;
            if ($.trim($("input[name='realtyProductTypeDesc']").val())!="") {
              oneText=oneText+"("+$.trim($("input[name='realtyProductTypeDesc']").val())+")";
              _uRealtyProductTypeDesc=$.trim($("input[name='realtyProductTypeDesc']").val());
            }
          }
          ttArray+=","+oneText;
          _uRealtyProductType+=","+choose[i].value;
        }
      }
//      if (selOther&&_uRealtyProductTypeDesc=="") alert("请录入“其他”关注产品类型");
//      else {
        if (_uRealtyProductType.length>0) {
          _uRealtyProductType=_uRealtyProductType.substr(1);
          ttArray=ttArray.substr(1);
        }
        $("#realtyProductType").html(ttArray==""?"&nbsp;":ttArray);
        $("#realtyProductTypeModal").modal('hide');
        if (_uRealtyProductType!="") $("#cleanRealtyProductTypeBtn").show();
//      }
    },
    cleanRealtyProductType: function(type) {
      if (type==2) {
        _uRealtyProductType="";
        _uRealtyProductTypeDesc="";
        $("#realtyProductType").html("&nbsp;");
        $("#cleanRealtyProductTypeBtn").hide();
      }
      //fillSelectField('realtyProductType', $("#realtyProductType").html(), false);
    },
    clickRealtyProductTypeCheck: function(flag) {
      var choose=document.getElementsByName('realtyProductType');
      var n=0;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) n++;
      }
      if (flag==1) {
        if (n>1) {
          for (var i=0; i<choose.length; i++) {
            if (choose[i].checked&&choose[i].getAttribute("_text")=='无法了解') {
              choose[i].checked=false;
              break;
            }
          }
        }
      }
      if (flag==2) {
        if (n>1) {
          for (var i=0; i<choose.length; i++) {
            if (choose[i].checked&&choose[i].getAttribute("_text")!='无法了解') choose[i].checked=false;
          }
          $("input[name='realtyProductTypeDesc']").hide();
        }
      }
      if (flag==3) {
        var otherCheck=false;
        for (var i=0; i<choose.length; i++) {
          if (choose[i].checked&&choose[i].getAttribute("_text")=='其他') otherCheck=true;
        }
        if (otherCheck) $("input[name='realtyProductTypeDesc']").show(); else $("input[name='realtyProductTypeDesc']").hide();
        if (n>1) {
          for (var i=0; i<choose.length; i++) {
            if (choose[i].checked&&choose[i].getAttribute("_text")=='无法了解') {
              choose[i].checked=false;
              break;
            }
          }
        }
      }
    },
    selLivingStatus: function() {
      _uLivingStatus="";
      var choose=document.getElementsByName('livingStatus');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#livingStatus").html(choose[i].getAttribute("_text"));
          _uLivingStatus=choose[i].value;
        }
      }
      $("#livingStatusModal").modal('hide');
      if (_uLivingStatus!="") $("#cleanLivingStatusBtn").show();
    },
    cleanLivingStatus: function() {
      $("#livingStatus").html("&nbsp;");
      _uLivingStatus="";
      $("#cleanLivingStatusBtn").hide();
      fillSelectField('livingStatus', "", false);
    },
  selRealUseMen: function() {
      _uRealUseMen="";
      _uRealUseMenDesc="";
      var choose=document.getElementsByName('realUseMen');
      var ttArray="";
      var selOther=false;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          var oneText=choose[i].getAttribute("_text");
          if (choose[i].getAttribute("_text")=='其他') {
            selOther=true;
            if ($.trim($("input[name='realUseMenDesc']").val())!="") {
              oneText=oneText+"("+$.trim($("input[name='realUseMenDesc']").val())+")";
              _uRealUseMenDesc=$.trim($("input[name='realUseMenDesc']").val());
            }
          }
          ttArray+=","+oneText;
          _uRealUseMen+=","+choose[i].value;
        }
      }
//      if (selOther&&_uRealUseMenDesc=="") alert("请录入“其他”关注产品类型");
//      else {
        if (_uRealUseMen.length>0) {
          _uRealUseMen=_uRealUseMen.substr(1);
          ttArray=ttArray.substr(1);
        }
        $("#realUseMen").html(ttArray==""?"&nbsp;":ttArray);
        $("#realUseMenModal").modal('hide');
        if (_uRealUseMen!="") $("#cleanRealUseMenBtn").show();
//      }
    },
    cleanRealUseMen: function(type) {
      if (type==2) {
        _uRealUseMen="";
        _uRealUseMenDesc="";
        $("#realUseMen").html("&nbsp;");
        $("#cleanRealUseMenBtn").hide();
      }
      //fillSelectField('realUseMen', $("#realUseMen").html(), false);
    },
    clickRealUseMenCheck: function(flag) {
      var choose=document.getElementsByName('realUseMen');
      var n=0;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) n++;
      }
      if (flag==1) {
        if (n>1) {
          for (var i=0; i<choose.length; i++) {
            if (choose[i].checked&&choose[i].getAttribute("_text")=='无法了解') {
              choose[i].checked=false;
              break;
            }
          }
        }
      }
      if (flag==2) {
        if (n>1) {
          for (var i=0; i<choose.length; i++) {
            if (choose[i].checked&&choose[i].getAttribute("_text")!='无法了解') choose[i].checked=false;
          }
          $("input[name='realUseMenDesc']").hide();
        }
      }
      if (flag==3) {
        var otherCheck=false;
        for (var i=0; i<choose.length; i++) {
          if (choose[i].checked&&choose[i].getAttribute("_text")=='其他') otherCheck=true;
        }
        if (otherCheck) $("input[name='realUseMenDesc']").show(); else $("input[name='realUseMenDesc']").hide();
        if (n>1) {
          for (var i=0; i<choose.length; i++) {
            if (choose[i].checked&&choose[i].getAttribute("_text")=='无法了解') {
              choose[i].checked=false;
              break;
            }
          }
        }
      }
    },
  selRealPayMen: function() {
      _uRealPayMen="";
      _uRealPayMenDesc="";
      var choose=document.getElementsByName('realPayMen');
      var ttArray="";
      var selOther=false;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          var oneText=choose[i].getAttribute("_text");
          if (choose[i].getAttribute("_text")=='其他') {
            selOther=true;
            if ($.trim($("input[name='realPayMenDesc']").val())!="") {
              oneText=oneText+"("+$.trim($("input[name='realPayMenDesc']").val())+")";
              _uRealPayMenDesc=$.trim($("input[name='realPayMenDesc']").val());
            }
          }
          ttArray+=","+oneText;
          _uRealPayMen+=","+choose[i].value;
        }
      }
//      if (selOther&&_uRealPayMenDesc=="") alert("请录入“其他”关注产品类型");
//      else {
        if (_uRealPayMen.length>0) {
          _uRealPayMen=_uRealPayMen.substr(1);
          ttArray=ttArray.substr(1);
        }
        $("#realPayMen").html(ttArray==""?"&nbsp;":ttArray);
        $("#realPayMenModal").modal('hide');
        if (_uRealPayMen!="") $("#cleanRealPayMenBtn").show();
//      }
    },
    cleanRealPayMen: function(type) {
      if (type==2) {
        _uRealPayMen="";
        _uRealPayMenDesc="";
        $("#realPayMen").html("&nbsp;");
        $("#cleanRealPayMenBtn").hide();
      }
      //fillSelectField('realPayMen', $("#realPayMen").html(), false);
    },
    clickRealPayMenCheck: function(flag) {
      var choose=document.getElementsByName('realPayMen');
      var n=0;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) n++;
      }
      if (flag==1) {
        if (n>1) {
          for (var i=0; i<choose.length; i++) {
            if (choose[i].checked&&choose[i].getAttribute("_text")=='无法了解') {
              choose[i].checked=false;
              break;
            }
          }
        }
      }
      if (flag==2) {
        if (n>1) {
          for (var i=0; i<choose.length; i++) {
            if (choose[i].checked&&choose[i].getAttribute("_text")!='无法了解') choose[i].checked=false;
          }
          $("input[name='realPayMenDesc']").hide();
        }
      }
      if (flag==3) {
        var otherCheck=false;
        for (var i=0; i<choose.length; i++) {
          if (choose[i].checked&&choose[i].getAttribute("_text")=='其他') otherCheck=true;
        }
        if (otherCheck) $("input[name='realPayMenDesc']").show(); else $("input[name='realPayMenDesc']").hide();
        if (n>1) {
          for (var i=0; i<choose.length; i++) {
            if (choose[i].checked&&choose[i].getAttribute("_text")=='无法了解') {
              choose[i].checked=false;
              break;
            }
          }
        }
      }
    }
  }
});
$('#realtyProductTypeModal').on('hide.bs.modal', function () {
  vueStep2.cleanRealtyProductType(1);
});
$('#realUseMenModal').on('hide.bs.modal', function () {
  vueStep2.cleanRealUseMen(1);
});
$('#realPayMenModal').on('hide.bs.modal', function () {
  vueStep2.cleanRealPayMen(1);
});

//=====================================================================


//=====================================================================
var _TYPE="add";
var recordId="";//记录Id只有当_TYPE=update时，此变量才有值。
var custId="";
var userInfo={};

//填充数据,并初始化
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
  $(document).attr("title","客户数据中心-成交信息录入");

  initData();
});

function fillData(data) {//填数据
  if (!data) return;
  var projOk=false;
  var sProjs=(userInfo.checkProj).split(",");
  for (var i=0; i<sProjs.length; i++) {
    var iS=sProjs[i].split("-");
    if (iS[0]==data.projid) {
      $("#proj").html(iS[1]);
      _uProjId=data.projid;
      _uProjName=iS[1];
      projOk=true;
      break;
    }
  }
  if (data.custid) custId=data.custid;
  if (userInfo.roleName=='管理员') projOk=true;
  if (!projOk)  {
    window.location.href=_URL_BASE+"/wxfront/err.html?4000=抱歉<br/>您的权限不足，无法浏览系统<br/>禁止录入";
    return;
  }
  if (data.custname) $("input[name='custName']").val(data.custname);
  if (data.custphonenum) $("input[name='custPhone']").val(data.custphonenum);
  if (data.custsex) fillSelectField("sex", data.custsex, true);

  if (data.firstknowtime.time) {
    var fTime=new Date();
    fTime.setTime(data.firstknowtime.time);
    fillTime("purchaseDate", fTime);
  }
  if (data.receptime.time) {
    var rTime=new Date();
    rTime.setTime(data.receptime.time);
    fillTime("signDate", rTime);
  }
  if (data.housenumup) $("input[name='houseNumup']").val(data.housenumup);
  if (data.visitcycle) $("input[name='visitCycle']").val(data.visitcycle);
  if (data.purchasecycle) $("input[name='purchaseCycle']").val(data.purchasecycle);
  if (data.signcycle) $("input[name='signCycle']").val(data.signcycle);
  if (data.houseregiType) fillSelectField("houseRegiType", data.houseregiType, true);
  if (data.houseacreage) $("input[name='houseAcreage']").val(data.houseacreage);
  if (data.unitprice) $("input[name='unitPrice']").val(data.unitprice);
  if (data.totalprice) $("input[name='totalPrice']").val(data.totalprice);
  if (data.paymenttype) {
    var _temp=data.paymenttype;
    if (data.paymenttype.indexOf('其他')!=-1) {
      if (data.paymenttypedesc) {
        var _temp2="其他("+data.paymenttypedesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    fillSelectField("paymentType", _temp, true);
  }
  if (data.loanbank) $("input[name='loanBank']").val(data.loanbank);
  if (data.realtyproducttype) {
    var _temp=data.realtyproducttype;
    if (data.realtyproducttype.indexOf('其他')!=-1) {
      if (data.realtyproducttypedesc) {
        var _temp2="其他("+data.realtyproducttypedesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    fillSelectField("realtyProductType", _temp, true);
  }
  if (data.addressmail) $("input[name='addressMail']").val(data.addressmail);
  if (data.livingstatus) fillSelectField("livingStatus", data.livingstatus, true);
  if (data.realusemen) {
    var _temp=data.realusemen;
    if (data.realusemen.indexOf('其他')!=-1) {
      if (data.realusemendesc) {
        var _temp2="其他("+data.realusemendesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    fillSelectField("realUseMen", _temp, true);
  }
  if (data.realpaymen) {
    var _temp=data.realpaymen;
    if (data.realpaymen.indexOf('其他')!=-1) {
      if (data.realpaymendesc) {
        var _temp2="其他("+data.realpaymendesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    fillSelectField("realPayMen", _temp, true);
  }
  if (data.suggestion) $("textarea[name='suggestion']").val(data.suggestion);
  if (data.talkqandS) $("textarea[name='talkQandS']").val(data.talkqandS);
  if (data.signqandS) $("textarea[name='signQandS']").val(data.signqandS);
  if (data.sumdescn) $("textarea[name='sumDescn']").val(data.sumdescn);
  function fillTime(id, _time) {
    var str=""+_time.getFullYear()+"-";
    str+=((100+(_time.getMonth()+1))+"").substr(1)+"-";
    str+=((100+_time.getDate())+"").substr(1);
    $("input[name='"+id+"']").val(str);
  }
}

function initData() {
  var url=_URL_BASE+"/wx/api/personalCenter";
  $.ajax({type:"post", async:true, url:url, data:null, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        initPage(json.userInfo);
        if (_TYPE=='add') $("#step1").show();
      } else {
        window.location.href=_URL_BASE+"/wxfront/err.html?1000=抱歉<br/>无法获得您的个人信息<br/>禁止录入";
      }
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
      window.location.href=_URL_BASE+"/wxfront/err.html?2000=系统错误<br/>status="
        +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
    }
  });
  function initPage(data) {
    userInfo=data;
    var url=_URL_BASE+"/wx/api/getLocalArea";
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
    cleanData();
    //处理顾问
    if (data.roleName=='顾问') {//顾问
      $("#_SELUSER").hide();
      $("#_SHOWUSER").show();
      $("span[name='userInput']").html(data.realname);
      _uUserId=data.userid;
    } else if (data.roleName=='项目负责人') {//负责人
      if (_uProjId) loadProjUser(_uProjId);
      else {
        $("#_SELUSER").hide();
        $("#_SHOWUSER").show();
        $("span[name='userInput']").html("先选项目");
      }
    }
    //如果是更新，则要获取记录内容
    if (_TYPE=='update') {
      $(document).attr("title","客户数据中心-成交信息修改");
      //获得本条记录消息信息
      recordId=getUrlParam(window.location.href, 'recordId');
      if (!recordId) window.location.href=_URL_BASE+"/wxfront/err.html?3000=无记录Id";
      else {
        var _data={};
        if (recordId.indexOf("#")==recordId.length-1) recordId=recordId.substr(0,32);
        _data.recordId=recordId;
        var url=_URL_BASE+"/wx/api/getRecord03";
        $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
          success: function(json) {
            if (json.msg=='100') {
              fillData(json.data);
              getAudit(recordId);
              $("#step1").show();
            } else {
              window.location.href=_URL_BASE+"/wxfront/err.html?1000=抱歉<br/>无法获得成交录入信息";
            }
          },
          error: function(XMLHttpRequest, textStatus, errorThrown) {
            window.location.href=_URL_BASE+"/wxfront/err.html?2000=系统错误<br/>status="
              +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
          }
        });
      }
    }
  }
}
function loadProjUser(projId) {//加载顾问
  $("#userData").html("");
  $("span[name='userInput']").html("加载顾问...");
  var url=_URL_BASE+"/wx/api/getUserList?projId="+projId;
  $.ajax({type:"post", async:true, url:url, data:null, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        if (json.users&&json.users.length>0) {
          $("#_SELUSER").show();
          $("#_SHOWUSER").hide();
          for (var i=0; i<json.users.length; i++) {
            var oneUser=json.users[i];
            var _innerHtml=oneUser.realName+"<span>（"+(oneUser.sex==1?"男":"女")+"）</span><span>"+oneUser.mainPhoneNum+"</span><span>"+oneUser.projName+"</span>";
            var userHtml="<label><input type='radio' name='user' value='"+oneUser.id+"-"+oneUser.realName+"' _text='"+oneUser.realName+"' onclick='selUser()'/>"+_innerHtml+"</label>";
            if (i<(json.users.length-1)) userHtml+="<br>";
            $("#userData").append(userHtml);
          }
        } else {
          alert('无法获得项目顾问列表');
        }
      }
      $("span[name='userInput']").html("&nbsp;");
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
      window.location.href=_URL_BASE+"/wxfront/err.html?2000=系统错误<br/>status="
        +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
    }
  });
}
function getAudit(id) {
  var url=_URL_BASE+"/wx/api/getCheckReason?recordType=1&recordId="+id;
  $.ajax({type:"post", async:true, url:url, data:null, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        $("#auditText").html(json.checkReason);
        if (json.checkReason) $("#auditArea").show();
      }
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
      window.location.href=_URL_BASE+"/wxfront/err.html?2000=系统错误<br/>status="
        +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
    }
  });
}
function cleanData() {
  var nt=new Date();
  var str=""+nt.getFullYear()+"-";
  str+=((100+(nt.getMonth()+1))+"").substr(1)+"-";
  str+=((100+nt.getDate())+"").substr(1);
  $("input[name='purchaseDate']").val(str);
  $("input[name='signDate']").val(str);
  //清除所有数据
  $("input[name='custName']").val("");
  $("input[name='custPhone']").val("");
  clean('sex');
  clean('ageGroup');
  cleanArea('localResidence');
  cleanArea('localWorkArea');
  cleanArea('outResidence');
  cleanArea('outWorkArea');
  clean('familyStatus');
  clean('trafficType');
  clean('buyQualify');
  clean('workIndustry');
  clean('enterpriseType');
  clean('knowWay');
  $("input[name='knowWayGGP']").val("");
  $("input[name='knowWayGGP']").attr("readonly",true);
  $("input[name='knowWayJS']").val("");
  $("input[name='knowWayJS']").attr("readonly",true);
  clean('estCustWorth');
  clean('investType');
  clean('capitalPrepSection');
  clean('realtyProductType');
  clean('attentAcreage');
  clean('priceSection');
  clean('buyPurpose');
  clean('attentionPoint');
  clean('recepTimeSection');
  clean('custScore');
  $("textarea[name='compareProjs']").val("");
  $("textarea[name='custDescn']").val("");

  function clean(otherId) {
    $("#"+otherId).html("&nbsp;");
    var choose=document.getElementsByName(''+otherId);
    for (var i=0; i<choose.length; i++) if (choose[i].checked) choose[i].checked=false;
    try {
      $("input[name='"+otherId+"Desc']").val("");
      $("input[name='"+otherId+"Desc']").hide();
    }catch(e){}
  }
  function cleanArea(id) {
    $("input[name='"+id+"']").val("");
    var _id=id.substr(0,1).toUpperCase()+id.substr(1);
    eval("_u"+_id+"=''");
  }
}

/**
 * 判断name为id的输入对象是否为空
 * @param id
 * @param type 类型：1=val;2=html
 * @returns true 不为空
 */
/*function checkField(id) {
 return !(!($.trim($("[name='"+id+"']").val())));

 }*/

function checkField(id){
  return !(!$.trim($("[name='"+id+"']").val()));
}

//翻页切换
function step1Next() {//要判断是否应该进行成交录入
  if (userInfo.roleName=='项目管理人') {
    window.location.href=_URL_BASE+"/wxfront/err.html?7000=作为项目管理人<br/>您无需录入成交记录！";
    return;
  }
  if (!checkField("custName")){
    alert('请填写房屋买受人姓名');
    return false;
  }
  var tempVal="";
  tempVal = $("[name='custPhone']").val();
  if (!checkField("custPhone")){
    alert('请填写电话');
    return false;
  }else if(!(/^1(3|4|5|7|8)\d{9}$/.test(tempVal))){
    alert('电话不符合规则');
    return false;
  }
  tempVal = $("#sex").html();
  if(tempVal=="" || tempVal=="&nbsp;"){
    alert('请填写性别');
    return false;
  };
  if (!checkField("houseNum")){
    alert('请填写购买房号');
    return false;
  }
  tempVal = $("[name='visitCycle']").val();
  if (!checkField("visitCycle")){
    alert('请填写认知-到访');
    return false;
  }else if(isNaN(tempVal)){
    alert('填写认知-到访(必须是数字)');
    return false;
  }else if(tempVal<=0){
    alert('必须是大于0的数字');
    return false;
  }
  tempVal = $("[name='purchaseCycle']").val();
  if (!checkField("purchaseCycle")){
    alert('请填写到访-认购');
    return false;
  }else if(isNaN(tempVal)){
    alert('填写到访-认购(必须是数字)');
    return false;
  }else if(tempVal<=0){
    alert('必须是大于0的数字');
    return false;
  }
  tempVal = $("[name='signCycle']").val();
  if (!checkField("signCycle")){
    alert('请填写认购-签约');
    return false;
  }else if(isNaN(tempVal)){
    alert('填写认购-签约(必须是数字)');
    return false;
  }else if(tempVal<=0){
    alert('必须是大于0的数字');
    return false;
  }
  tempVal = $("#houseRegiType").html();
  if(tempVal=="" || tempVal=="&nbsp;"){
    alert('请填写户籍');
    return false;
  };
  tempVal = $("[name='houseAcreage']").val();
  if (!checkField("houseAcreage")){
    alert('请填写成交面积');
    return false;
  }else if(isNaN(tempVal)){
    alert('填写成交面积(必须是数字)');
    return false;
  }else if(tempVal<=0){
    alert('必须是大于0的数字');
    return false;
  }
  tempVal = $("[name='unitPrice']").val();
  if (!checkField("unitPrice")){
    alert('请填写成交单价');
    return false;
  }else if(isNaN(tempVal)){
    alert('填写成交单价(必须是数字)');
    return false;
  }else if(tempVal<=0){
    alert('必须是大于0的数字');
    return false;
  }
  tempVal = $("[name='totalPrice']").val();
  if (!checkField("totalPrice")){
    alert('请填写成交总价');
    return false;
  }else if(isNaN(tempVal)){
    alert('填写成交总价(必须是数字)');
    return false;
  }else if(tempVal<=0){
    alert('必须是大于0的数字');
    return false;
  }
  tempVal = $("#paymentType").html();
  if(tempVal=="" || tempVal=="&nbsp;"){
    alert('请填写付款方式');
    return false;
  };
  if (!checkField("loanBank")){
    alert('请填写贷款银行');
    return false;
  }
  $("#step1").hide(0);
  $("#step2").show(0);
  $("#step3").hide(0);
}
function step2Prev() {
  $("#step1").show(0);
  $("#step2").hide(0);
  $("#step3").hide(0);
}
function step2Next() {
  var tempVal="";
  tempVal = $("#realtyProductType").html();
  if(tempVal=="" || tempVal=="&nbsp;"){
    alert('请填写关注产品类型');
    return false;
  }
  if (!checkField("addressMail")){
    alert('请填写通邮地址');
    return false;
  }
  tempVal = $("#livingStatus").html();
  if(tempVal=="" || tempVal=="&nbsp;"){
    alert('请填写实际居住情况');
    return false;
  }
  tempVal = $("#realUseMen").html();
  if(tempVal=="" || tempVal=="&nbsp;"){
    alert('请填写房屋使用人是谁');
    return false;
  }
  tempVal = $("#realPayMen").html();
  if(tempVal=="" || tempVal=="&nbsp;"){
    alert('购房出资人是谁');
    return false;
  }
  $("#step1").hide(0);
  $("#step2").hide(0);
  $("#step3").show(0);
}
function step3Prev() {
  $("#step1").hide(0);
  $("#step2").show(0);
  $("#step3").hide(0);
}
//=以下为提交，包括修改和删除====================================
function commitData() {
  var commitData=getData(_TYPE);
  var msg=validate(commitData, _TYPE);
  if (msg.err) {
    if (msg.turnTo==1) step2Prev();
    alert(msg.err);
    return;
  }
  //遮罩
  $("#mask").show();
  //按钮职位灰色
  $("div[_type='BTN']").each(function(){
    $(this).attr("style", "margin-top:1.5rem;background-color:#dedede;color:#c7c7c7");
  });
  if (_TYPE=='add') {
    commitInsert(commitData);
  } else if (_TYPE='update') {
    commitUpdate(commitData);
  }
  function getData(type) {
    var retData={};
    var temp="";
	//获取项目id
    if (_uProjId) retData.projid=_uProjId;
	//获取用户名称
    if (type=='update'&&custId) retData.custid=custId;
	//用户名称
    temp=$("input[name='custName']").val();
    if (temp) retData.custname=temp;
    temp=$("input[name='custPhone']").val();
    if (temp) retData.custphonenum=temp;
    temp=$("input[name='signDate']").val();
    if (temp) retData.receptime1=temp;
    temp=$("input[name='purchaseDate']").val();
    if (temp) retData.firstknowtime1=temp;
    if (_uSex) retData.custsex=_uSex;
	temp=$("input[name='houseNum']").val();
    if (temp) retData.housenum=temp;
	temp=$("input[name='visitCycle']").val();
    if (temp) retData.visitcycle=temp;
    temp=$("input[name='purchaseCycle']").val();
    if (temp) retData.purchasecycle=temp;
    temp=$("input[name='signCycle']").val();
    if (temp) retData.signcycle=temp;
	if (_uHouseRegiType) retData.houseRegiType=_uHouseRegiType;
	temp=$("input[name='houseAcreage']").val();
    if (temp) retData.houseacreage=temp;
	temp=$("input[name='unitPrice']").val();
    if (temp) retData.unitprice=temp;
	temp=$("input[name='totalPrice']").val();
    if (temp) retData.totalprice=temp;
    if (_uPaymentType) retData.paymentType=_uPaymentType;
	temp=$("input[name='loanBank']").val();
    if (temp) retData.loanbank=temp;
    if (_uRealtyProductType) retData.realtyproducttype=_uRealtyProductType;
	temp=$("input[name='addressMail']").val();
    if (temp) retData.addressmail=temp;
	if (_uLivingStatus) retData.livingstatus=_uLivingStatus;
    if (_uRealUseMen) retData.realusemen=_uRealUseMen;
    if (_uRealPayMen) retData.realpaymen=_uRealPayMen;
    temp=$("textarea[name='suggestion']").val();
    if (temp) retData.suggestion=temp;
	temp=$("textarea[name='talkQandS']").val();
    if (temp) retData.talkqands=temp;
	temp=$("textarea[name='signQandS']").val();
    if (temp) retData.signqands=temp;
	temp=$("textarea[name='sumDescn']").val();
    if (temp) retData.sumdescn=temp;
    if (type=='add') {
      if (_uUserId) retData.authorid=_uUserId;
      if (_uUserId) retData.creatorid=_uUserId;
    }
    return retData;
  }
  function validate(data, type) {
    var ret={};
    ret.err="";
    ret.turnTo=3;//到第几节
    var err="";
    if (!data) {
      err="请先输入数据";
      return ret;
    }
    if (!data.projid) ret.err+=";\n无法获得项目Id";
    if (!data.custid&&type=='update') ret.err+=";\n无法获得客户Id";
    if (!data.custname) {
      ret.err+=";\n无法获得客户名称";
      ret.turnTo=1;
    }
    if (!data.custphonenum) {
      ret.err+=";\n无法获得客户手机";
      ret.turnTo=1;
    }
    if (ret.err) ret.err=ret.err.substring(2);
    return ret;
  }
  function commitInsert(_data) {
    var url=_URL_BASE+"/wx/api/addTradeVisit";
    $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
      success: function(json) {
        //遮罩
        $("#mask").hide();
        //按钮职位灰色
        $("div[_type='BTN']").each(function(){
          $(this).attr("style", "margin-top:1.5rem;background-color:#19a6ee;color:#FFFFFF");
        });
        if (json.msg!='100') {
          alert("录入成交记录错误！");
        } else {
          if (confirm("录入成功，要录入下一条成交记录吗？")) {
            cleanData();
            step2Prev();
          } else {
            window.location.href=_URL_BASE+"/wxfront/record03/record03Search.html";
          }
        }
      },
      error: function(XMLHttpRequest, textStatus, errorThrown) {
        window.location.href=_URL_BASE+"/wxfront/err.html?2000=系统错误<br/>status="
          +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
      }
    });
  }
  function commitUpdate(_data) {
    _data.id=recordId;
    var url=_URL_BASE+"/wx/api/updateRecord03";
    $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
      success: function(json) {
        //遮罩
        $("#mask").hide();
        //按钮职位灰色
        $("div[_type='BTN']").each(function(){
          $(this).attr("style", "margin-top:1.5rem;background-color:#19a6ee;color:#FFFFFF");
        });
        if (json.msg!='100') {
          alert("修改成交记录错误！");
        } else {
          alert("修改成交记录成功!");
          window.location.href=_URL_BASE+"/wxfront/record03/record03Search.html";
        }
      },
      error: function(XMLHttpRequest, textStatus, errorThrown) {
        window.location.href=_URL_BASE+"/wxfront/err.html?2000=系统错误<br/>status="
          +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
      }
    });
  }
}