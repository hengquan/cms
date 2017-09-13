var _uProjId="";
var _uProjName="";
var _uUserId="";
var _uUserName="";
var _uUserRole="";
var _uSex="";
var _uHouseRegiType="";
var _uPaymentType="";
var _uPaymentTypeDesc="";
var _uRealtyProductType="";
var _uRealtyProductTypeDesc="";
var _uLivingStatus="";
var _uRealUseMen="";
var _uRealPayMenDesc="";
var _uRealPayMen="";
var _uRealPayMenDesc="";

function selProj() {
  var choose=document.getElementsByName('proj');
  var changePrj=false;
  for (var i=0; i<choose.length; i++) {
    if (choose[i].checked) {
      $("#proj").html(choose[i].getAttribute("_text"));
      _uProjName=choose[i].getAttribute("_text");
      var oldProjId=_uProjId;
      _uProjId=choose[i].value.substring(0, choose[i].value.indexOf('-'));
      changePrj=(_uProjId!=oldProjId);
    }
  }
  $("#projModal").modal('hide');
  if (_uProjId!="") $("#cleanProjBtn").show();
  if (_uUserRole!='顾问'&&changePrj) loadProjUser(_uProjId);
  if (changePrj) cleanCust();
}
function selUser() {
  var choose=document.getElementsByName('user');
  for (var i=0; i<choose.length; i++) {
    if (choose[i].checked) {
      $("span[name='userInput']").html(choose[i].getAttribute("_text"));
      var oldUserId=_uUserId;
      _uUserId=choose[i].value.substring(0, choose[i].value.indexOf('-'));
      if (oldUserId!=_uUserId) {
        custId="";
        $("input[name='custName']").val("");
        $("input[name='custPhone']").val("");
      }
    }
  }
  $("#userModal").modal('hide');
  if (_uUserId!="") $("#cleanUserBtn").show();
}

var vueStep1=new Vue({
  el: "#step1",
  methods: {
    cleanProj: function() {
      $("#proj").html("&nbsp;");
      _uProjId="";
      _uProjName="";
      $("#cleanProjBtn").hide();
      fillSelectField('proj', "", false);
      this.cleanUser();
      $("span[name='userInput']").html("请先选择项目");
      $("#_SELUSER").hide();
      $("#_SHOWUSER").show();
    },
    cleanUser: function() {
      $("span[name='userInput']").html("&nbsp;");
      _uUserId="";
      _uUserName="";
      $("#cleanUserBtn").hide();
      fillSelectField('user', "", false);
      custId="";
      $("input[name='custName']").val("");
      $("input[name='custPhone']").val("");
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
    selHouseRegiType: function() {
      var choose=document.getElementsByName('houseRegiType');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#houseRegiType").html(choose[i].getAttribute("_text"));
          _uHouseRegiType=choose[i].value;
        }
      }
      $("#houseRegiTypeModal").modal('hide');
      if (_uHouseRegiType!="") $("#cleanHouseRegiTypeBtn").show();
    },
    cleanHouseRegiType: function() {
      $("#houseRegiType").html("&nbsp;");
      _uHouseRegiType="";
      $("#cleanHouseRegiTypeBtn").hide();
      fillSelectField('houseRegiType', "", false);
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
//    if (selOther&&_uRealPayMenDesc=="") alert("请录入“其他”关注产品类型");
//    else {
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

//通用方法=========================================
function fillSelectField(id, value, isSetValue) {
  var isLowOut=false;
  var _value="";
  var choose=document.getElementsByName(''+id);
  var _checkeds=value.split(",");
  var _fv="";
  var _fvOther="";
  $("input[name='"+id+"Desc']").val("");
  $("input[name='"+id+"Desc']").hide();
  if (id=="knowWay") {
    $("input[name='knowWayGGP']").val("");
    $("input[name='knowWayJS']").val("");
  }
  for (var i=0; i<choose.length; i++) {
    var _t=choose[i].getAttribute("_text");
    var j=0;
    for (; j<_checkeds.length; j++) {
      if (_checkeds[j].indexOf(_t)==0) break;
    }
    choose[i].checked=j<_checkeds.length;
    if (!choose[i].checked) {
      _t=choose[i].getAttribute("value");
      j=0;
      for (; j<_checkeds.length; j++) {
        if (_checkeds[j].indexOf(_t)==0) break;
      }
      choose[i].checked=j<_checkeds.length;
      if (choose[i].checked) {
        isLowOut=true;
        _value+=","+choose[i].getAttribute("_text");
      }
    }
    if (choose[i].checked) {
      if (isSetValue) _fv+=","+choose[i].value;
      if (choose[i].getAttribute("_text")=='其他'&&$("input[name='"+id+"Desc']")) {
        $("input[name='"+id+"Desc']").show();
        if (_checkeds[j].indexOf("(")>0) {
          var otherStr=_checkeds[j];
          var endIndex=otherStr.length-1;
          if (id=='knowWay') endIndex=endIndex-2;
          $("input[name='"+id+"Desc']").val(otherStr.substring(otherStr.indexOf("(")+1, endIndex));
          if (isSetValue) {
            _fvOther=$("input[name='"+id+"Desc']").val();
            if (id=='knowWay') _fvOther+=",其他="+_fvOther;
          }
        }
      }
      if (id=='knowWay') {
        if (choose[i].getAttribute("_text")=='户外广告牌'&&$("input[name='"+id+"GGP']")) {
          $("input[name='knowWayGGP']").attr("readonly",false);
          if (_checkeds[j].indexOf("(")>0) {
            var otherStr=_checkeds[j];
            var endIndex=otherStr.length-1;
            $("input[name='knowWayGGP']").val(otherStr.substring(otherStr.indexOf("(")+1, endIndex));
            if (isSetValue) {
              _fvOther+=",户外广告牌="+$("input[name='knowWayGGP']").val();
            }
          }
        }
        if (choose[i].getAttribute("_text")=='渠道介绍'&&$("input[name='"+id+"JS']")) {
          $("input[name='knowWayJS']").attr("readonly",false);
          if (_checkeds[j].indexOf("(")>0) {
            var otherStr=_checkeds[j];
            var endIndex=otherStr.length-3;
            $("input[name='knowWayJS']").val(otherStr.substring(otherStr.indexOf("(")+1, endIndex));
            if (isSetValue) {
              _fvOther+=",渠道介绍="+$("input[name='"+id+"Desc']").val();
            }
          }
        }
      }
    }
  }
  if (isSetValue) {
    if (_fv.length>0) _fv=_fv.substr(1);
    var _id=id.substr(0,1).toUpperCase()+id.substr(1);
    eval("_u"+_id+"=_fv");
    if (_fvOther) {
      if (id=='knowWay'&&_fvOther.length>0) _fvOther=_fvOther.substr(1);
      eval("_u"+_id+"Desc=_fv");
    }
    $("span[id='"+id+"']").html(isLowOut?_value.substring(1):value);
  }
}
