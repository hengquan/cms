var _uUserId="";
var _uUserRole="";
var _uProjId="";
var _uProjName="";
var _uSex="";
var _uAgeGroup="";
var _uFamilyStatus="";
var _uTrafficType="";
var _uTrafficTypeDesc="";
var _uBuyQualify="";
var _uWorkIndustry="";
var _uWorkIndustryDesc="";
var _uEnterpriseType="";
var _uEnterpriseTypeDesc="";
var _uRealtyProductType="";
var _uRealtyProductTypeDesc="";
var _uAttentAcreage="";
var _uPriceSection=""
var _uBuyPurpose=""
var _uBuyPurposeDesc=""
var _uKnowWay="";
var _uKnowWayDesc="";
var _uAttentionPoint="";
var _uAttentionPointDesc="";
var _uEstCustWorth="";
var _uInvestType="";
var _uInvestTypeDesc="";
var _uCapitalPrepSection="";
var _uRecepTimeSection="";
var _uCustScore="";
var _uLocalResidence="";
var _uLocalWorkArea="";
var _uOutResidence="";
var _uOutWorkArea="";

//复选单选处理
function selProj() {
  var choose=document.getElementsByName('proj');
  for (var i=0; i<choose.length; i++) {
    if (choose[i].checked) {
      $("#proj").html(choose[i].getAttribute("_text"));
      _uProjName=choose[i].getAttribute("_text");
      var oldProjId=_uProjId;
      _uProjId=choose[i].value.substring(0, choose[i].value.indexOf('-'));
      if (_uUserRole!='顾问'&&_uProjId!=oldProjId) loadProjUser(_uProjId);
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
      $("span[name='userInput']").html("请先选择项目");
      $("#_SELUSER").hide();
      $("#_SHOWUSER").show();
    },
    selSex: function() {
      _uSex="";
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
    selAgeGroup: function() {
      _uAgeGroup="";
      var choose=document.getElementsByName('ageGroup');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#ageGroup").html(choose[i].getAttribute("_text"));
          _uAgeGroup=choose[i].value;
        }
      }
      $("#ageGroupModal").modal('hide');
      if (_uAgeGroup!="") $("#cleanAgeGroupBtn").show();
    },
    cleanAgeGroup: function() {
      $("#ageGroup").html("&nbsp;");
      _uAgeGroup="";
      $("#cleanAgeGroupBtn").hide();
      fillSelectField('ageGroup', "", false);
    },
    selFamilyStatus: function() {
      _uFamilyStatus="";
      var choose=document.getElementsByName('familyStatus');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#familyStatus").html(choose[i].getAttribute("_text"));
          _uFamilyStatus=choose[i].value;
        }
      }
      $("#familyStatusModal").modal('hide');
      if (_uFamilyStatus!="") $("#cleanFamilyStatusBtn").show();
    },
    cleanFamilyStatus: function() {
      $("#familyStatus").html("&nbsp;");
      _uFamilyStatus="";
      $("#cleanFamilyStatusBtn").hide();
      fillSelectField('familyStatus', "", false);
    },
    selTrafficType: function() {
      _uTrafficType="";
      _uTrafficTypeDesc="";
      var choose=document.getElementsByName('trafficType');
      var ttArray="";
      var selOther=false;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          var oneText=choose[i].getAttribute("_text");
          if (choose[i].getAttribute("_text")=='其他') {
            selOther=true;
            if ($.trim($("input[name='trafficTypeDesc']").val())!="") {
              oneText=oneText+"("+$.trim($("input[name='trafficTypeDesc']").val())+")";
              _uTrafficTypeDesc=$.trim($("input[name='trafficTypeDesc']").val());
            }
          }
          ttArray+=","+oneText;
          _uTrafficType+=","+choose[i].value;
        } else {
          if (choose[i].getAttribute("_text")=='其他') $("input[name='trafficTypeDesc']").val("");
        }
      }
//    if (selOther&&_uTrafficTypeDesc=="") alert("请录入“其他”交通出行方式");
//    else {
      if (_uTrafficType.length>0) {
        _uTrafficType=_uTrafficType.substr(1);
        ttArray=ttArray.substr(1);
      }
      $("#trafficType").html(ttArray==""?"&nbsp;":ttArray);
      $("#trafficTypeModal").modal('hide');
      if (_uTrafficType!="") $("#cleanTrafficTypeBtn").show();
//      }
    },
    cleanTrafficType: function(type) {
      if (type==2) {
        _uTrafficType="";
        _uTrafficTypeDesc="";
        $("#trafficType").html("&nbsp;");
        $("#cleanTrafficTypeBtn").hide();
      }
      fillSelectField('trafficType', $("#trafficType").html(), false);
    },
    clickTrafficCheck: function(flag) {
      var choose=document.getElementsByName('trafficType');
      var n=0;
      for (var i=0; i<choose.length; i++) if (choose[i].checked) n++;
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
          $("input[name='trafficTypeDesc']").hide();
        }
      }
      if (flag==3) {
        var otherCheck=false;
        for (var i=0; i<choose.length; i++) {
          if (choose[i].checked&&choose[i].getAttribute("_text")=='其他') otherCheck=true;
        }
        if (otherCheck) $("input[name='trafficTypeDesc']").show(); else $("input[name='trafficTypeDesc']").hide();
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
$('#trafficTypeModal').on('hide.bs.modal', function () {
  vueStep1.cleanTrafficType(1);
});
var vueStep2=new Vue({
  el: "#step2",
  data: {
    u_tempKnowWayGGP: "",
    u_tempKnowWayJS: ""
  },
  methods: {
    selBuyQualify: function() {
      _uBuyQualify="";
      var choose=document.getElementsByName('buyQualify');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#buyQualify").html(choose[i].getAttribute("_text"));
          _uBuyQualify=choose[i].value;
        }
      }
      $("#buyQualifyModal").modal('hide');
      if (_uBuyQualify!="") $("#cleanBuyQualifyBtn").show();
    },
    cleanBuyQualify: function() {
      $("#buyQualify").html("&nbsp;");
      _uBuyQualify="";
      $("#cleanBuyQualifyBtn").hide();
      fillSelectField('buyQualify', "", false);
    },
    selWorkIndustry: function() {
      _uWorkIndustry="";
      _uWorkIndustryDesc="";
      var choose=document.getElementsByName('workIndustry');
      var ttArray="";
      var selOther=false;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          var oneText=choose[i].getAttribute("_text");
          if (choose[i].getAttribute("_text")=='其他') {
            selOther=true;
            if ($.trim($("input[name='workIndustryDesc']").val())!="") {
              oneText=oneText+"("+$.trim($("input[name='workIndustryDesc']").val())+")";
              _uWorkIndustryDesc=$.trim($("input[name='workIndustryDesc']").val());
            }
          } else {
            _uWorkIndustryDesc="";
            $("input[name='workIndustryDesc']").val("");
            $("input[name='workIndustryDesc']").hide();
          }
          ttArray+=","+oneText;
          _uWorkIndustry+=","+choose[i].value;
        }
      }
//    if (selOther&&_uWorkIndustryDesc=="") alert("请录入“其他”从事行业方式");
//    else {
      if (_uWorkIndustry.length>0) {
        _uWorkIndustry=_uWorkIndustry.substr(1);
        ttArray=ttArray.substr(1);
      }
      $("#workIndustry").html(ttArray==""?"&nbsp;":ttArray);
      $("#workIndustryModal").modal('hide');
      if (_uWorkIndustry!="") $("#cleanWorkIndustryBtn").show();
//    }
    },
    cleanWorkIndustry: function() {
      $("#workIndustry").html("&nbsp;");
      _uWorkIndustry="";
      _uWorkIndustryDesc="";
      $("#cleanWorkIndustryBtn").hide();
      $("input[name='workIndustryDesc']").val("");
      $("input[name='workIndustryDesc']").hide();
      fillSelectField('workIndustry', "", false);
    },
    clickWorkIndustry: function() {
      var otherCheck=false;
      var choose=document.getElementsByName('workIndustry');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked&&choose[i].getAttribute("_text")=='其他') {
          otherCheck=true;
          break;
        }
      }
      if (otherCheck) $("input[name='workIndustryDesc']").show(); else $("input[name='workIndustryDesc']").hide();
    },
    selEnterpriseType: function() {
      _uEnterpriseType="";
      _uEnterpriseTypeDesc="";
      var choose=document.getElementsByName('enterpriseType');
      var ttArray="";
      var selOther=false;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          var oneText=choose[i].getAttribute("_text");
          if (choose[i].getAttribute("_text")=='其他') {
            selOther=true;
            if ($.trim($("input[name='enterpriseTypeDesc']").val())!="") {
              oneText=oneText+"("+$.trim($("input[name='enterpriseTypeDesc']").val())+")";
              _uEnterpriseTypeDesc=$.trim($("input[name='enterpriseTypeDesc']").val());
            }
          } else {
            _uEnterpriseTypeDesc="";
            $("input[name='enterpriseTypeDesc']").val("");
            $("input[name='enterpriseTypeDesc']").hide();
          }
          ttArray+=","+oneText;
          _uEnterpriseType+=","+choose[i].value;
        }
      }
//    if (selOther&&_uEnterpriseTypeDesc=="") alert("请录入“其他”企业性质");
//    else {
      if (_uEnterpriseType.length>0) {
        _uEnterpriseType=_uEnterpriseType.substr(1);
        ttArray=ttArray.substr(1);
      }
      $("#enterpriseType").html(ttArray==""?"&nbsp;":ttArray);
      $("#enterpriseTypeModal").modal('hide');
      if (_uEnterpriseType!="") $("#cleanEnterpriseTypeBtn").show();
//    }
    },
    cleanEnterpriseType: function() {
      $("#enterpriseType").html("&nbsp;");
      _uEnterpriseType="";
      _uEnterpriseTypeDesc="";
      $("#cleanEnterpriseTypeBtn").hide();
      $("input[name='enterpriseTypeDesc']").val("");
      $("input[name='enterpriseTypeDesc']").hide();
      fillSelectField('enterpriseType', "", false);
    },
    clickEnterpriseType: function() {
      var otherCheck=false;
      var choose=document.getElementsByName('enterpriseType');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked&&choose[i].getAttribute("_text")=='其他') {
          otherCheck=true;
          break;
        }
      }
      if (otherCheck) $("input[name='enterpriseTypeDesc']").show(); else $("input[name='enterpriseTypeDesc']").hide();
    },
    selKnowWay: function() {
      this.u_tempKnowWayGGP='';
      this.u_tempKnowWayJS='';
      _uKnowWay="";
      _uKnowWayDesc="";
      var choose=document.getElementsByName('knowWay');
      var ttArray="";
      var selOther=false;
      _uKnowWay="";
      _uKnowWayDesc="";
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          var oneText=choose[i].getAttribute("_text");
          if (choose[i].getAttribute("_text")=='其他') {
            selOther=true;
            if ($.trim($("input[name='knowWayDesc']").val())!="") {
              _uKnowWayDesc+=","+oneText+"="+$.trim($("input[name='knowWayDesc']").val());
              oneText=oneText+"("+$.trim($("input[name='knowWayDesc']").val())+")";
            }
          }
          if (choose[i].getAttribute("_text")=='户外广告牌') {
            if ($.trim($("input[name='knowWayGGP']").val())!="") {
              _uKnowWayDesc+=","+oneText+"="+$.trim($("input[name='knowWayGGP']").val());
              oneText=oneText+"("+$.trim($("input[name='knowWayGGP']").val())+")";
            }
          }
          if (choose[i].getAttribute("_text")=='渠道介绍') {
            if ($.trim($("input[name='knowWayJS']").val())!="") {
              _uKnowWayDesc+=","+oneText+"="+$.trim($("input[name='knowWayJS']").val());
              oneText=oneText+"("+$.trim($("input[name='knowWayJS']").val())+"公司)";
            }
          }
          ttArray+=","+oneText;
          _uKnowWay+=","+choose[i].value;
        }
      }
//    if (selOther&&_uKnowWayDesc=="") alert("请录入“其他”从事行业方式");
//    else {
      if (_uKnowWay.length>0) {
        _uKnowWay=_uKnowWay.substr(1);
        ttArray=ttArray.substr(1);
      }
      if (_uKnowWayDesc.length>0) _uKnowWayDesc=_uKnowWayDesc.substr(1);
      $("#knowWay").html(ttArray==""?"&nbsp;":ttArray);
      $("#knowWayModal").modal('hide');
      if (_uKnowWay!="") $("#cleanKnowWayBtn").show();
//    }
    },
    cleanKnowWay: function(type) {
      if (type==2) {
        _uKnowWay="";
        _uKnowWayDesc="";
        $("#knowWay").html("&nbsp;");
        $("#cleanKnowWayBtn").hide();
        $("input[name='knowWayDesc']").val("");
        $("input[name='knowWayDesc']").hide();
        $("input[name='knowWayGGP']").val("");
        $("input[name='knowWayGGP']").attr("readonly",true);
        $("input[name='knowWayJS']").val("");
        $("input[name='knowWayJS']").attr("readonly",true);
      }
      fillSelectField('knowWay', $("#knowWay").html(), false);
    },
    clickKnowWayCheck: function(flag) {
      var choose=document.getElementsByName('knowWay');
      var n=0;
      for (var i=0; i<choose.length; i++) if (choose[i].checked) n++;
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
          $("input[name='knowWayDesc']").hide();
          this.u_tempKnowWayGGP=$("input[name='knowWayGGP']").val();
          $("input[name='knowWayGGP']").val("");
          this.u_tempKnowWayJS=$("input[name='knowWayJS']").val();
          $("input[name='knowWayJS']").val("");
          $("input[name='knowWayGGP']").attr("readonly",true);
          $("input[name='knowWayJS']").attr("readonly",true);
        }
      }
      if (flag==3) {
        var otherCheck=false;
        for (var i=0; i<choose.length; i++) {
          if (choose[i].checked&&choose[i].getAttribute("_text")=='其他') otherCheck=true;
        }
        if (otherCheck) $("input[name='knowWayDesc']").show(); else $("input[name='knowWayDesc']").hide();
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
    clickKnowWayGGP: function() {
      var ggpCheck=false;
      var choose=document.getElementsByName('knowWay');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked&&choose[i].getAttribute("_text")=='户外广告牌') {
          ggpCheck=true;
          break;
        }
      }
      $("input[name='knowWayGGP']").attr("readonly",!ggpCheck);
      if (ggpCheck) $("input[name='knowWayGGP']").val(this.u_tempKnowWayGGP);
      else {
        this.u_tempKnowWayGGP=$("input[name='knowWayGGP']").val();
        $("input[name='knowWayGGP']").val("");
      }
      if (ggpCheck) {
        for (var i=0; i<choose.length; i++) {
          if (choose[i].checked&&choose[i].getAttribute("_text")=='无法了解') {
            choose[i].checked=false;
            break;
          }
        }
      }
    },
    clickKnowWayJS: function() {
      var jsCheck=false;
      var choose=document.getElementsByName('knowWay');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked&&choose[i].getAttribute("_text")=='渠道介绍') {
          jsCheck=true;
          break;
        }
      }
      $("input[name='knowWayJS']").attr("readonly",!jsCheck);
      if (jsCheck) $("input[name='knowWayJS']").val(this.u_tempKnowWayJS);
      else {
        this.u_tempKnowWayJS=$("input[name='knowWayJS']").val();
        $("input[name='knowWayJS']").val("");
      }
      if (jsCheck) {
        for (var i=0; i<choose.length; i++) {
          if (choose[i].checked&&choose[i].getAttribute("_text")=='无法了解') {
            choose[i].checked=false;
            break;
          }
        }
      }
    },
    selEstCustWorth: function() {
      _uEstCustWorth="";
      var choose=document.getElementsByName('estCustWorth');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#estCustWorth").html(choose[i].getAttribute("_text"));
          _uEstCustWorth=choose[i].value;
        }
      }
      $("#estCustWorthModal").modal('hide');
      if (_uEstCustWorth!="") $("#cleanEstCustWorthBtn").show();
    },
    cleanEstCustWorth: function() {
      $("#estCustWorth").html("&nbsp;");
      _uEstCustWorth="";
      $("#cleanEstCustWorthBtn").hide();
      fillSelectField('estCustWorth', "", false);
    },
    selInvestType: function() {
      _uInvestType="";
      _uInvestTypeDesc="";
      var choose=document.getElementsByName('investType');
      var ttArray="";
      var selOther=false;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          var oneText=choose[i].getAttribute("_text");
          if (choose[i].getAttribute("_text")=='其他') {
            selOther=true;
            if ($.trim($("input[name='investTypeDesc']").val())!="") {
              oneText=oneText+"("+$.trim($("input[name='investTypeDesc']").val())+")";
              _uInvestTypeDesc=$.trim($("input[name='investTypeDesc']").val());
            }
          }
          ttArray+=","+oneText;
          _uInvestType+=","+choose[i].value;
        }
      }
//    if (selOther&&_uInvestTypeDesc=="") alert("请录入“其他”从事行业方式");
//    else {
      if (_uInvestType.length>0) {
        _uInvestType=_uInvestType.substr(1);
        ttArray=ttArray.substr(1);
      }
      $("#investType").html(ttArray==""?"&nbsp;":ttArray);
      $("#investTypeModal").modal('hide');
      if (_uInvestType!="") $("#cleanInvestTypeBtn").show();
//    }
    },
    cleanInvestType: function(type) {
      if (type==2) {
        _uInvestType="";
        _uInvestTypeDesc="";
        $("#investType").html("&nbsp;");
        $("#cleanInvestTypeBtn").hide();
      }
      fillSelectField('investType', $("#investType").html(), false);
    },
    clickInvestTypeCheck: function(flag) {
      var choose=document.getElementsByName('investType');
      var n=0;
      for (var i=0; i<choose.length; i++) if (choose[i].checked) n++;
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
          $("input[name='investTypeDesc']").hide();
        }
      }
      if (flag==3) {
        var otherCheck=false;
        for (var i=0; i<choose.length; i++) {
          if (choose[i].checked&&choose[i].getAttribute("_text")=='其他') otherCheck=true;
        }
        if (otherCheck) $("input[name='investTypeDesc']").show(); else $("input[name='investTypeDesc']").hide();
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
    selCapitalPrepSection: function() {
      _uCapitalPrepSection="";
      var choose=document.getElementsByName('capitalPrepSection');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#capitalPrepSection").html(choose[i].getAttribute("_text"));
          _uCapitalPrepSection=choose[i].value;
        }
      }
      $("#capitalPrepSectionModal").modal('hide');
      if (_uCapitalPrepSection!="") $("#cleanCapitalPrepSectionBtn").show();
    },
    cleanCapitalPrepSection: function() {
      $("#capitalPrepSection").html("&nbsp;");
      _uCapitalPrepSection="";
      $("#cleanCapitalPrepSectionBtn").hide();
      fillSelectField('capitalPrepSection', "", false);
    }
  }
});
$('#workIndustryModal').on('hide.bs.modal', function () {
  fillSelectField('workIndustry', $("#workIndustry").html(), false);
});
$('#enterpriseTypeModal').on('hide.bs.modal', function () {
  fillSelectField('enterpriseType', $("#enterpriseType").html(), false);
});
$('#knowWayModal').on('hide.bs.modal', function () {
  vueStep2.cleanKnowWay(1);
});
$('#investTypeModal').on('hide.bs.modal', function () {
  vueStep2.cleanInvestType(1);
});
var vueStep3=new Vue({
  el: "#step3",
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
//    if (selOther&&_uRealtyProductTypeDesc=="") alert("请录入“其他”关注产品类型");
//    else {
      if (_uRealtyProductType.length>0) {
        _uRealtyProductType=_uRealtyProductType.substr(1);
        ttArray=ttArray.substr(1);
      }
      $("#realtyProductType").html(ttArray==""?"&nbsp;":ttArray);
      $("#realtyProductTypeModal").modal('hide');
      if (_uRealtyProductType!="") $("#cleanRealtyProductTypeBtn").show();
//    }
    },
    cleanRealtyProductType: function(type) {
      if (type==2) {
        _uRealtyProductType="";
        _uRealtyProductTypeDesc="";
        $("#realtyProductType").html("&nbsp;");
        $("#cleanRealtyProductTypeBtn").hide();
      }
      fillSelectField('realtyProductType', $("#realtyProductType").html(), false);
    },
    clickRealtyProductTypeCheck: function(flag) {
      var choose=document.getElementsByName('realtyProductType');
      var n=0;
      for (var i=0; i<choose.length; i++) if (choose[i].checked) n++;
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
    selAttentAcreage: function() {
      _uAttentAcreage="";
      var choose=document.getElementsByName('attentAcreage');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#attentAcreage").html(choose[i].getAttribute("_text"));
          _uAttentAcreage=choose[i].value;
        }
      }
      $("#attentAcreageModal").modal('hide');
      if (_uAttentAcreage!="") $("#cleanAttentAcreageBtn").show();
    },
    cleanAttentAcreage: function() {
      $("#attentAcreage").html("&nbsp;");
      _uAttentAcreage="";
      $("#cleanAttentAcreageBtn").hide();
      fillSelectField('attentAcreage', "", false);
    },
    selPriceSection: function() {
      _uPriceSection="";
      var choose=document.getElementsByName('priceSection');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#priceSection").html(choose[i].getAttribute("_text"));
          _uPriceSection=choose[i].value;
        }
      }
      $("#priceSectionModal").modal('hide');
      if (_uPriceSection!="") $("#cleanPriceSectionBtn").show();
    },
    cleanPriceSection: function() {
      $("#priceSection").html("&nbsp;");
      _uPriceSection="";
      $("#cleanPriceSectionBtn").hide();
      fillSelectField('priceSection', "", false);
    },
    selBuyPurpose: function() {
      _uBuyPurpose="";
      _uBuyPurposeDesc="";
      var choose=document.getElementsByName('buyPurpose');
      var selOther=false;
      var ttArray="";
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          var oneText=choose[i].getAttribute("_text");
          if (choose[i].getAttribute("_text")=='其他') {
            selOther=true;
            if ($.trim($("input[name='buyPurposeDesc']").val())!="") {
              oneText=oneText+"("+$.trim($("input[name='buyPurposeDesc']").val())+")";
              _uBuyPurposeDesc=$.trim($("input[name='buyPurposeDesc']").val());
            }
          } else {
            _uBuyPurposeDesc="";
            $("input[name='buyPurposeDesc']").val("");
            $("input[name='buyPurposeDesc']").hide();
          }
          ttArray+=","+oneText;
          _uBuyPurpose+=","+choose[i].value;
        }
      }
//    if (selOther&&_uBuyPurposeDescDesc=="") alert("请录入“其他”购房目的");
//    else {
      if (_uBuyPurpose.length>0) {
        _uBuyPurpose=_uBuyPurpose.substr(1);
        ttArray=ttArray.substr(1);
      }
      $("#buyPurpose").html(ttArray==""?"&nbsp;":ttArray);
      $("#buyPurposeModal").modal('hide');
      if (_uBuyPurposeDesc!="") $("#cleanBuyPurposeBtn").show();
//    }
    },
    cleanBuyPurpose: function() {
      $("#buyPurpose").html("&nbsp;");
      _uBuyPurpose="";
      _uBuyPurposeDesc="";
      $("#cleanBuyPurposeBtn").hide();
      $("input[name='buyPurposeDesc']").val("");
      $("input[name='buyPurposeDesc']").hide();
      fillSelectField('buyPurpose', "", false);
    },
    clickBuyPurpose: function() {
      var otherCheck=false;
      var choose=document.getElementsByName('buyPurpose');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked&&choose[i].getAttribute("_text")=='其他') {
          otherCheck=true;
          break;
        }
      }
      if (otherCheck) $("input[name='buyPurposeDesc']").show(); else $("input[name='buyPurposeDesc']").hide();
    },
    selAttentionPoint: function() {
      _uAttentionPoint="";
      _uAttentionPointDesc="";
      var choose=document.getElementsByName('attentionPoint');
      var ttArray="";
      var selOther=false;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          var oneText=choose[i].getAttribute("_text");
          if (choose[i].getAttribute("_text")=='其他') {
            selOther=true;
            if ($.trim($("input[name='attentionPointDesc']").val())!="") {
              oneText=oneText+"("+$.trim($("input[name='attentionPointDesc']").val())+")";
              _uAttentionPointDesc=$.trim($("input[name='attentionPointDesc']").val());
            }
          }
          ttArray+=","+oneText;
          _uAttentionPoint+=","+choose[i].value;
        }
      }
//    if (selOther&&_uAttentionPointDescDesc=="") alert("请录入“其他”购房目的");
//    else {
      if (_uAttentionPoint.length>0) {
        _uAttentionPoint=_uAttentionPoint.substr(1);
        ttArray=ttArray.substr(1);
      }
      $("#attentionPoint").html(ttArray==""?"&nbsp;":ttArray);
      $("#attentionPointModal").modal('hide');
      if (_uAttentionPointDesc!="") $("#cleanAttentionPointBtn").show();
//    }
    },
    cleanAttentionPoint: function(type) {
      if (type==2) {
        _uAttentionPoint="";
        _uAttentionPointDesc="";
        $("#attentionPoint").html("&nbsp;");
        $("#cleanAttentionPointBtn").hide();
        $("input[name='attentionPointDesc']").val("");
        $("input[name='attentionPointDesc']").hide();
      }
      fillSelectField('attentionPoint', $("#attentionPoint").html(), false);
    },
    clickAttentionPointCheck: function(flag) {
      var choose=document.getElementsByName('attentionPoint');
      var n=0;
      for (var i=0; i<choose.length; i++) if (choose[i].checked) n++;
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
          $("input[name='attentionPointDesc']").hide();
        }
      }
      if (flag==3) {
        var otherCheck=false;
        for (var i=0; i<choose.length; i++) {
          if (choose[i].checked&&choose[i].getAttribute("_text")=='其他') otherCheck=true;
        }
        if (otherCheck) $("input[name='attentionPointDesc']").show(); else $("input[name='attentionPointDesc']").hide();
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
    selRecepTimeSection: function() {
      _uRecepTimeSection="";
      var choose=document.getElementsByName('recepTimeSection');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#recepTimeSection").html(choose[i].getAttribute("_text"));
          _uRecepTimeSection=choose[i].value;
        }
      }
      $("#recepTimeSectionModal").modal('hide');
      if (_uRecepTimeSection!="") $("#cleanRecepTimeSectionBtn").show();
    },
    cleanRecepTimeSection: function() {
      $("#recepTimeSection").html("&nbsp;");
      _uRecepTimeSection="";
      $("#cleanRecepTimeSectionBtn").hide();
      fillSelectField('recepTimeSection', $("#recepTimeSection").html(), false);
    },
    selCustScore: function() {
      _uCustScore="";
      var choose=document.getElementsByName('custScore');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#custScore").html(choose[i].getAttribute("_text"));
          _uCustScore=choose[i].value;
        }
      }
      $("#custScoreModal").modal('hide');
      if (_uCustScore!="") $("#cleanCustScoreBtn").show();
    },
    cleanCustScore: function() {
      $("#custScore").html("&nbsp;");
      _uCustScore="";
      $("#cleanCustScoreBtn").hide();
      fillSelectField('custScore', $("#custScore").html(), false);
    }
  }
});
$('#realtyProductTypeModal').on('hide.bs.modal', function () {
  vueStep3.cleanRealtyProductType(1);
});
$('#buyPurposeModal').on('hide.bs.modal', function () {
  fillSelectField('buyPurpose', $("#buyPurpose").html(), false);
});
$('#attentionPointModal').on('hide.bs.modal', function () {
  vueStep3.cleanAttentionPoint(1);
});

function fillSelectField(id, value, isSetValue) {
  var _id=id.substr(0,1).toUpperCase()+id.substr(1);	
  var choose=document.getElementsByName(''+id);
  var _checkeds=value.split(",");
  var _fv="";
  var _fvOther="";
  var hadChecked=false;
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
  	  if (_checkeds[j].indexOf(_t)==0||_t.indexOf("-"+_checkeds[j])!=-1) {
        if (_t.indexOf("-"+_checkeds[j])!=-1) _checkeds[j]=_t;
        break;
  	  }
    }
    choose[i].checked=j<_checkeds.length;
    if (choose[i].checked) {
      if (!hadChecked) hadChecked=true;
      if (isSetValue) _fv+=","+choose[i].value;
      if (choose[i].getAttribute("_text")=='其他'&&$("input[name='"+id+"Desc']")) {
        $("input[name='"+id+"Desc']").show();
        if (_checkeds[j].indexOf("(")>0) {
          var otherStr=_checkeds[j];
          var endIndex=otherStr.length-1;
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
  if (hadChecked) $("#clean"+_id+"Btn").show();
  if (isSetValue) {
    if (_fv.length>0) _fv=_fv.substr(1);
    eval("_u"+_id+"=_fv");
    if (_fvOther) {
      if (id=='knowWay'&&_fvOther.length>0) _fvOther=_fvOther.substr(1);
      eval("_u"+_id+"Desc=_fv");
    }
    var _v="";
    for (var j=0; j<_checkeds.length; j++) _v+=","+_checkeds[j];
    $("span[id='"+id+"']").html(_v.substring(1));
  }
}
//=====================================================================


//=====================================================================
var _TYPE="add";
var recordId="";//记录Id只有当_TYPE=update时，此变量才有值。
var custId="";
var userInfo={};

//填充数据,并初始化
$(function() {
  $(".subNav").click(function() {
    $(this).toggleClass("currentDd").siblings(".subNav").removeClass("currentDd");
    $(this).toggleClass("currentDt").siblings(".subNav").removeClass("currentDt");
    $(this).next(".navContent").slideToggle(500).siblings(".navContent").slideUp(500);
  });
  $("#auditArea").hide();
  _TYPE=getUrlParam(window.location.href, 'type');
  if (_TYPE==null) _TYPE="add";
  if (_TYPE.toLocaleLowerCase()=='update') _TYPE="update";
  $(document).attr("title","客户数据中心-首访信息录入");

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
    fillTime("firstTime", fTime);
  }
  if (data.receptime.time) {
    var rTime=new Date();
    rTime.setTime(data.receptime.time);
    fillTime("curTime", rTime);
  }
  if (data.agegroup) fillSelectField("ageGroup", data.agegroup, true);
  if (data.localresidence) $("input[name='localResidence']").val(data.localresidence);
  if (data.localworkarea) $("input[name='localWorkArea']").val(data.localworkarea);
  if (data.outresidence) $("input[name='outResidence']").val(data.outresidence);
  if (data.outworkarea) $("input[name='outWorkArea']").val(data.outworkarea);
  if (data.custaddress) $("input[name='custAddress']").val(data.custaddress);

  if (data.familystatus) fillSelectField("familyStatus", data.familystatus, true);
  if (data.traffictype) {
    var _temp=data.traffictype;
    if (data.traffictype.indexOf('其他')!=-1) {
      if (data.traffictypedesc) {
        var _temp2="其他("+data.traffictypedesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    fillSelectField("trafficType", _temp, true);
  }
  if (data.buyqualify) fillSelectField("buyQualify", data.buyqualify, true);
  if (data.workindustry) {
    var _temp=data.workindustry;
    if (data.workindustry.indexOf('其他')!=-1) {
      if (data.workindustrydesc) {
        var _temp2="其他("+data.workindustrydesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    fillSelectField("workIndustry", _temp, true);
  }
  if (data.enterprisetype) {
    var _temp=data.enterprisetype;
    if (data.enterprisetype.indexOf('其他')!=-1) {
      if (data.enterprisetypedesc) {
        var _temp2="其他("+data.enterprisetypedesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    fillSelectField("enterpriseType", _temp, true);
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
    fillSelectField("knowWay", _temp, true);
  }
  if (data.estcustworth) fillSelectField("estCustWorth", data.estcustworth, true);
  if (data.investtype) {
    var _temp=data.investtype;
    if (data.investtype.indexOf('其他')!=-1) {
      if (data.investtypedesc) {
        var _temp2="其他("+data.investtypedesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    fillSelectField("investType", _temp, true);
  }
  if (data.capitalprepsection) fillSelectField("capitalPrepSection", data.capitalprepsection, true);
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
  if (data.attentacreage) fillSelectField("attentAcreage", data.attentacreage, true);
  if (data.pricesection) fillSelectField("priceSection", data.pricesection, true);
  if (data.buypurpose) {
    var _temp=data.buypurpose;
    if (data.buypurpose.indexOf('其他')!=-1) {
      if (data.buypurposedesc) {
        var _temp2="其他("+data.buypurposedesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    fillSelectField("buyPurpose", _temp, true);
  }
  if (data.attentionpoint) {
    var _temp=data.attentionpoint;
    if (data.attentionpoint.indexOf('其他')!=-1) {
      if (data.attentionpointdesc) {
        var _temp2="其他("+data.attentionpointdesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    fillSelectField("attentionPoint", _temp, true);
  }
  if (data.receptimesection) fillSelectField("recepTimeSection", data.receptimesection, true);
  if (data.custscore) fillSelectField("custScore", data.custscore, true);
  if (data.compareprojs) $("textarea[name='compareProjs']").val(data.compareprojs);
  if (data.custdescn) $("textarea[name='custDescn']").val(data.custdescn);

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
    if (userInfo.roleName!='顾问'&&userInfo.roleName!='项目负责人') {
      window.location.href=_URL_BASE+"/wxfront/err.html?9000=您是"+userInfo.roleName+"<br/>无法进行首访信息的"+(_TYPE=='add'?"录入":"修改")+"操作";
      return;
    }
    var url=_URL_BASE+"/wx/api/getLocalArea";

    var localResidenceArea=new LArea();
    localResidenceArea.init({
      'trigger': '#localRedisId', //触发选择控件的文本框，同时选择完毕后name属性输出到该位置
      'valueTo': '#localRedisVal', //选择完毕后id属性输出到该位置
      'keys': {
        id: 'id',
         name: 'name'
       }, //绑定数据源相关字段 id对应valueTo的value属性输出 name对应trigger的value属性输出
      'type': 1, //数据源类型
      'data': localArea.data.children//数据源
    
    });
    
    var localWorkArea=new LArea();
    localWorkArea.init({
      'trigger': '#localWorkAreaId', //触发选择控件的文本框，同时选择完毕后name属性输出到该位置
      'valueTo': '#localWorkAreaVal', //选择完毕后id属性输出到该位置
      'keys': {
        id: 'id',
        name: 'name'
       }, //绑定数据源相关字段 id对应valueTo的value属性输出 name对应trigger的value属性输出
       'type': 1, //数据源类型
       'data': localArea.data.children//数据源
    });
    var outResidenceArea=new LArea();
    outResidenceArea.init({
      'trigger': '#outRedisId', //触发选择控件的文本框，同时选择完毕后name属性输出到该位置
      'valueTo': '#outRedisVal', //选择完毕后id属性输出到该位置
      'keys': {
        id: 'id',
        name: 'name'
      }, //绑定数据源相关字段 id对应valueTo的value属性输出 name对应trigger的value属性输出
      'type': 1, //数据源类型
      'data': allArea.data.children//数据源
    });
    var outWorkArea=new LArea();
    outWorkArea.init({
      'trigger': '#outWorkAreaId', //触发选择控件的文本框，同时选择完毕后name属性输出到该位置
      'valueTo': '#outWorkAreaVal', //选择完毕后id属性输出到该位置
      'keys': {
        id: 'id',
        name: 'name'
      }, //绑定数据源相关字段 id对应valueTo的value属性输出 name对应trigger的value属性输出
      'type': 1, //数据源类型
      'data': allArea.data.children//数据源
    });
    var canShowProj=false;
    var prjNames=""+data.checkProj;
    var lPrj=prjNames.split(',');
    var projHtml="";
    var projId,projName;
    for (var i=0;i<lPrj.length; i++) {
      if ($.trim(lPrj[i])!=''&&lPrj[i].indexOf('-')!=-1) {
        _uProj=lPrj[i];
        if (!canShowProj) canShowProj=true;
        projId=lPrj[i].substring(0,lPrj[i].indexOf('-'));
        projName=lPrj[i].substring(lPrj[i].indexOf('-')+1);
        projHtml+='<label><input type="radio" name="proj" value="'+projId+'-'+projName+'" _text="'+projName+'" onclick="selProj()"/> '+projName+' </label>';
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
    _uUserRole=data.roleName;
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
        $("span[name='userInput']").html("请先选择项目");
      }
    }
    //如果是更新，则要获取记录内容
    if (_TYPE=='update') {
      $(document).attr("title","客户数据中心-首访信息修改");
      //获得本条记录消息信息
      recordId=getUrlParam(window.location.href, 'recordId');
      if (!recordId) window.location.href=_URL_BASE+"/wxfront/err.html?3000=无记录Id";
      else {
        var _data={};
        if (recordId.indexOf("#")==recordId.length-1) recordId=recordId.substr(0,32);
        _data.recordId=recordId;
        var url=_URL_BASE+"/wx/api/getRecord01";
        $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
          success: function(json) {
            if (json.msg=='100') {
              fillData(json.data);
              getAudit(recordId);
              $("#step1").show();
            } else {
              window.location.href=_URL_BASE+"/wxfront/err.html?1000=抱歉<br/>无法获得首访录入信息";
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
            if (oneUser.id==userInfo.userid) continue;
            if (oneUser.roleName!="顾问") continue;
            var _innerHtml=oneUser.realName+"<span>（"+(oneUser.sex==1?"男":"女")+"）</span><span>"+oneUser.mainPhoneNum+"</span>";
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
  $("input[name='curTime']").val(str);
  $("input[name='firstTime']").val(str);
  //清除所有数据
  $("input[name='custName']").val("");
  $("input[name='custPhone']").val("");
  clean('sex');
  clean('ageGroup');
  cleanArea('localResidence');
  cleanArea('localWorkArea');
  cleanArea('outResidence');
  cleanArea('outWorkArea');
  $("input[name='custAddress']").val("");
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

//翻页切换
function step1Next() {//要判断是否应该进行首访录入
  if (userInfo.roleName=='项目管理人') {
    window.location.href=_URL_BASE+"/wxfront/err.html?7000=作为项目管理人<br/>您无需录入首访记录！";
    return;
  }
  var id;
  if ($.trim($("#localRedisId").val())) {
    id=$("#localRedisVal").val();
    _uLocalResidence=$.trim(id.substr(id.lastIndexOf(",")+1))+"-"+($.trim($("#localRedisId").val())).replace(/,/g,"，");
 
  }
  if ($.trim($("#localWorkAreaId").val())) {
    id=$("#localWorkAreaVal").val();
    _uLocalWorkArea=$.trim(id.substr(id.lastIndexOf(",")+1))+"-"+($.trim($("#localWorkAreaId").val())).replace(/,/g,"，");
  }
  if ($.trim($("#outRedisId").val())) {
    id=$("#outRedisVal").val();
    _uOutResidence=$.trim(id.substr(id.lastIndexOf(",")+1))+"-"+($.trim($("#outRedisId").val())).replace(/,/g,"，");
  }
  if ($.trim($("#outWorkAreaId").val())) {
    id=$("#outWorkAreaVal").val();
    _uOutWorkArea=$.trim(id.substr(id.lastIndexOf(",")+1))+"-"+($.trim($("#outWorkAreaId").val())).replace(/,/g,"，");
  }
  if (_TYPE=='update') {
    var err=checkStep1();
    if (err) {
      alert(err);
      return;
    }
    $("#step1").hide(0);
    $("#step2").show(0);
    $("#step3").hide(0);
  } else if (_TYPE=='add') {//要判断是否应该进行首访录入
    //获得参数
    var _data={};
    if (!$.trim(_uProjId)) {
      alert("请选择项目！");
      return;
    }
    _data.projId=_uProjId;
    _data.custName=$("input[name='custName']").val();
    if (!$.trim(_data.custName)) {
      alert("请输入客户姓名！");
      return;
    }
    var errMsg=checkPhone('custPhone');
    if (errMsg) {
      alert(errMsg);
      return temp;
    }
    _data.custPhone=$("input[name='custPhone']").val();
    var canNext=false;
    var url=_URL_BASE+"/wx/api/existRecord01";
    $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
      success: function(json) {
        if (json.msg=='103') {
          window.location.href=_URL_BASE+"/wxfront/err.html?1000=抱歉<br/>系统出现未知问题，不能录入";
        } else if (json.msg=='100') { //有就已存在
          if (json.authorId==_uUserId) {//转复方
            alert("此用户已经到访过，将转入复访录入");
            window.location.href=_URL_BASE+"/wxfront/record02/record02Input.html?type=add&custName="+encodeURIComponent(_data.custName)+"&custPhone="+encodeURIComponent(_data.custPhone)+"&projId="+encodeURIComponent(_data.projId);
          } else {
            window.location.href=_URL_BASE+"/wxfront/err.html?4000=此客户已由【"+json.authorName+"】进行接待<br/>您无权录入！";
            return;
          }
        } else canNext=true;
        if (canNext) {
          var err=checkStep1();
          if (err) {
            alert(err);
            return;
          }
          $("#step1").hide(0);
          $("#step2").show(0);
          $("#step3").hide(0);
        }
      },
      error: function(XMLHttpRequest, textStatus, errorThrown) {
        window.location.href=_URL_BASE+"/wxfront/err.html?2000=系统错误<br/>status="
          +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
      }
    });
  }
}
function checkPhone(docId) {
  var temp=$("input[name='"+docId+"']").val();
  if (!temp) return "请录入客户电话号码";
  temp=temp.replace(/，/g, ",");
  var phones=temp.split(",");
  var _errPhone="";
  var _okPhones="";
  var _check1,_check2;
  for (var i=0; i<phones.length; i++) {
    var onePhone=$.trim(phones[i]);
    _check1=checkMPhone(onePhone);
    if (_check1==0) continue;
    _check2=checkDPhone(onePhone);
    if (_check1==1||_check2==1) _okPhones+=","+onePhone;
    else {
      if (!_errPhone) _errPhone=onePhone;
      _okPhones+=","+onePhone;
    }
  }
  $("input[name='"+docId+"']").val(_okPhones.substring(1));
  if (_errPhone) return "客户电话号码["+_errPhone+"]不合法";
  return "";
}
function checkStep1() {
  if (!_uProjId) return "请选择项目！";
  var temp=$("span[name='userInput']").html();
  if (!temp||temp=='&nbsp;'||temp=='加载顾问...') return "请录选择置业顾问！";
  if (!$("input[name='curTime']").val()) return "请录入本次访问时间！";
  if (!$("input[name='custName']").val()) return "请录入客户名称！";
  temp=checkPhone('custPhone');
  if (temp) return temp;
  if (!_uSex) return "请选择客户性别！";
  if (!$("input[name='firstTime']").val()) return "请输入第一次获知时！";
  if (!_uAgeGroup) return "请选择年龄段！";
  if ((!$("input[name='localResidence']").val())&&(!$("input[name='outResidence']").val())) return "请选择居住区域(本地或外阜)！";
  if ((!$("input[name='localWorkArea']").val())&&(!$("input[name='outWorkArea']").val())) return "请选择工作区域(本地或外阜)！";
  if (!_uFamilyStatus) return "请选择家庭状况！";
  if (!_uTrafficType) return "请选择出行方式！";
  return "";
}
function checkStep2() {
  if (!_uBuyQualify) return "请选择购房资格！";
  if (!_uWorkIndustry) return "请选择从事行业！";
  if (!_uEnterpriseType) return "请选择企业性质！";
  if (!_uKnowWay) return "请选择认知渠道！";
  if (!_uEstCustWorth) return "请选择预估身价！";
  if (!_uInvestType) return "请选择重点投资！";
  if (!_uCapitalPrepSection) return "请选择资金筹备期！";
  return "";
}
function checkStep3() {
  if (!_uRealtyProductType) return "请选择关注产品类型！";
  if (!_uAttentAcreage) return "请选择关注区间面积！";
  if (!_uPriceSection) return "请选择接受总房款！";
  if (!_uBuyPurpose) return "请选择购房目的！";
  if (!_uAttentionPoint) return "请选择对本案关注点！";
  if (!_uRecepTimeSection) return "请选择参观接待时间";
  if (!_uCustScore) return "请选择客户评级！";
  if (!$("textarea[name='custDescn']").val()) return "请录入接待描述！";
	return "";
}

function step2Prev() {
  $("#step1").show(0);
  $("#step2").hide(0);
  $("#step3").hide(0);
}
function step2Next() {
  var err=checkStep2();
  if (err) {
  alert(err);
  return;
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
  var err=checkStep3();
  if (err) {
    alert(err);
    return;
  }
  var commitData=getData(_TYPE);
  //遮罩
  $("#mask").show();
  //按钮致为灰色
  $("div[_type='BTN']").each(function(){
  $(this).attr("style", "margin-top:1.5rem;background-color:#dedede;color:#c7c7c7");
  });
  if (_TYPE=='add') commitInsert(commitData);
  else if (_TYPE='update') commitUpdate(commitData);

  function getData(type) {
    var retData={};
    var temp="";
    if (_uProjId) retData.projid=_uProjId;
    if (type=='update'&&custId) retData.custid=custId;
    temp=$("input[name='custName']").val();
    if (temp) retData.custname=temp;
    temp=$("input[name='custPhone']").val();
    if (temp) retData.custphonenum=temp;
    temp=$("input[name='curTime']").val();
    if (temp) retData.receptime1=temp;
    temp=$("input[name='firstTime']").val();
    if (temp) retData.firstknowtime1=temp;
    if (_uSex) retData.custsex=_uSex;
    if (_uAgeGroup) retData.agegroup=_uAgeGroup;
    if (_uBuyQualify) retData.buyqualify=_uBuyQualify;
    if (_uLocalResidence) retData.localresidence=_uLocalResidence;
    if (_uLocalWorkArea) retData.localworkarea=_uLocalWorkArea;
    if (_uOutResidence) retData.outresidence=_uOutResidence;
    if (_uOutWorkArea) retData.outworkarea=_uOutWorkArea;
    temp=$("input[name='custAddress']").val();
    if (temp) retData.custaddress=temp;
    if (_uFamilyStatus) retData.familystatus=_uFamilyStatus;
    if (_uTrafficType) retData.traffictype=_uTrafficType;
    if (_uWorkIndustry) retData.workindustry=_uWorkIndustry;
    if (_uEnterpriseType) retData.enterprisetype=_uEnterpriseType;
    if (_uRealtyProductType) retData.realtyproducttype=_uRealtyProductType;
    if (_uAttentAcreage) retData.attentacreage=_uAttentAcreage;
    if (_uPriceSection) retData.pricesection=_uPriceSection;
    if (_uBuyPurpose) retData.buypurpose=_uBuyPurpose;
    if (_uKnowWay) retData.knowway=_uKnowWay;
    if (_uAttentionPoint) retData.attentionpoint=_uAttentionPoint;
    if (_uEstCustWorth) retData.estcustworth=_uEstCustWorth;
    if (_uInvestType) retData.investtype=_uInvestType;
    if (_uCapitalPrepSection) retData.capitalprepsection=_uCapitalPrepSection;
    temp=$("textarea[name='compareProjs']").val();
    if (temp) retData.compareprojs=temp;
    if (_uRecepTimeSection) retData.receptimesection=_uRecepTimeSection;
    if (_uCustScore) retData.custscore=_uCustScore;
    if (type=='add') {
      if (_uUserId) retData.authorid=_uUserId;
      if (_uUserId) retData.creatorid=_uUserId;
    }
    if (_uTrafficTypeDesc) retData.traffictypedesc=_uTrafficTypeDesc;
    if (_uWorkIndustryDesc) retData.workindustrydesc=_uWorkIndustryDesc;
    if (_uEnterpriseTypeDesc) retData.enterprisetypedesc=_uEnterpriseTypeDesc;
    if (_uRealtyProductTypeDesc) retData.realtyproducttypedesc=_uRealtyProductTypeDesc;
    if (_uBuyPurposeDesc) retData.buypurposedesc=_uBuyPurposeDesc;
    if (_uKnowWayDesc) retData.knowwaydesc=_uKnowWayDesc;
    if (_uAttentionPointDesc) retData.attentionpointdesc=_uAttentionPointDesc;
    if (_uInvestTypeDesc) retData.investtypedesc=_uInvestTypeDesc;
    temp=$("textarea[name='custDescn']").val();
    if (temp) retData.custdescn=temp;

    return retData;
  }
  function commitInsert(_data) {
    var url=_URL_BASE+"/wx/api/addHisFirstRecord";
    $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
      success: function(json) {
        //遮罩
        $("#mask").css("display", "none");
        //按钮致为兰色
        $("div[_type='BTN']").each(function(){
          $(this).attr("style", "margin-top:1.5rem;background-color:#19a6ee;color:#FFFFFF");
        });
        if (json.msg!='100') alert("录入首访记录错误！");
        else {
          if (confirm("录入成功，要录入下一条首访记录吗？")) {
            cleanData();
            step2Prev();
          } else {
            window.location.href=_URL_BASE+"/wxfront/record01/record01Search.html";
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
    var url=_URL_BASE+"/wx/api/updateRecord01";
    $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
      success: function(json) {
        //遮罩
        $("#mask").css("display", "none");
        //按钮致为兰色
        $("div[_type='BTN']").each(function(){
          $(this).attr("style", "margin-top:1.5rem;background-color:#19a6ee;color:#FFFFFF");
        });
        if (json.msg!='100') alert("修改首访记录错误！");
        else {
          alert("修改首访记录成功!");
          window.location.href=_URL_BASE+"/wxfront/record01/record01Search.html";
        }
      },
      error: function(XMLHttpRequest, textStatus, errorThrown) {
        window.location.href=_URL_BASE+"/wxfront/err.html?2000=系统错误<br/>status="
          +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
      }
    });
  }
}