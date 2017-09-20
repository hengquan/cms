/*!
 * 复访记录录入|修改
 * 页面输入元素操作部分
 */
var _uProjId="";
var _uProjName="";
var _uUserId="";
var _uUserName="";
var _uUserRole="";
var _uSex="";
var _uVisitorCount="";
var _uDecisionerIn="";
var _uVisitorRefs="";
var _uVisitorRefsDesc="";
var _uChildrenNum="";
var _uChildAgeGroup="";
var _uSchoolType="";
var _uChildAvocations="";
var _uChildAvocationsDesc="";
var _uOutEduWill="";
var _uOutExperFlag="";
var _uChildOutExperFlag="";
var _uLivingRadius="";
var _uHouseType="";
var _uHouseTypeDesc="";
var _uLiveAcreage="";
var _uHoustCount="";
var _uCarFamilyCount="";
var _uCarTotalPrice="";
var _uAvocations="";
var _uAvocationsDesc="";
var _uResistPoint="";
var _uResistPointDesc="";
var _uLoveActivation="";
var _uLoveActivationDesc="";
var _uFreeTimeSection="";
var _uRecepTimeSection="";
var _uCustScore="";
var _uFamilyStatus="";
var _uTrafficType="";
var _uTrafficTypeDesc="";
var _uBuyQualify="";
var _uWorkIndustry="";
var _uWorkIndustryDesc="";
var _uEnterpriseType="";
var _uEnterpriseTypeDesc="";
var _uKnowWay="";
var _uKnowWayDesc="";
var _uEstCustWorth="";
var _uInvestType="";
var _uInvestTypeDesc="";
var _uCapitalPrepSection="";
var _uRealtyProductType="";
var _uRealtyProductTypeDesc="";
var _uAttentAcreage="";
var _uPriceSection="";
var _uBuyPurpose="";
var _uBuyPurposeDesc="";
var _uAttentionPoint="";
var _uAttentionPointDesc="";
var _uAgeGroup="";
var _uNannyFlag="";
var _uPetFlag="";
var _uLoanStatus="";
var _uFulltimeWifeFlag="";

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
    selVisitorCount: function() {
      var choose=document.getElementsByName('visitorCount');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#visitorCount").html(choose[i].getAttribute("_text"));
          _uVisitorCount=choose[i].value;
        }
      }
      $("#visitorCountModal").modal('hide');
      if (_uVisitorCount!="") $("#cleanVisitorCountBtn").show();
    },
    cleanVisitorCount: function() {
      $("#visitorCount").html("&nbsp;");
      _uVisitorCount="";
      $("#cleanVisitorCountBtn").hide();
      fillSelectField('visitorCount', "", false);
    },
    selDecisionerIn: function() {
      var choose=document.getElementsByName('decisionerIn');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#decisionerIn").html(choose[i].getAttribute("_text"));
          _uDecisionerIn=choose[i].value;
        }
      }
      $("#decisionerInModal").modal('hide');
      if (_uDecisionerIn!="") $("#cleanDecisionerInBtn").show();
    },
    cleanDecisionerIn: function() {
      $("#decisionerIn").html("&nbsp;");
      _uDecisionerIn="";
      $("#cleanDecisionerInBtn").hide();
      fillSelectField('decisionerIn', "", false);
    },
    selVisitorRefs: function() {
      _uVisitorRefs="";
      _uVisitorRefsDesc="";
      var choose=document.getElementsByName('visitorRefs');
      var ttArray="";
      var selOther=false;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          var oneText=choose[i].getAttribute("_text");
          if (choose[i].getAttribute("_text")=='其他') {
            selOther=true;
            if ($.trim($("input[name='visitorRefsDesc']").val())!="") {
              oneText=oneText+"("+$.trim($("input[name='visitorRefsDesc']").val())+")";
              _uVisitorRefsDesc=$.trim($("input[name='visitorRefsDesc']").val());
            }
          }
          ttArray+=","+oneText;
          _uVisitorRefs+=","+choose[i].value;
        } else {
          if (choose[i].getAttribute("_text")=='其他') $("input[name='visitorRefsDesc']").val("");
        }
      }
//      if (selOther&&_uVisitorRefsDesc=="") alert("请录入“其他”交通出行方式");
//      else {
        if (_uVisitorRefs.length>0) {
          _uVisitorRefs=_uVisitorRefs.substr(1);
          ttArray=ttArray.substr(1);
        }
        $("#visitorRefs").html(ttArray==""?"&nbsp;":ttArray);
        $("#visitorRefsModal").modal('hide');
        if (_uVisitorRefs!="") $("#cleanVisitorRefsBtn").show();
//      }
    },
    cleanVisitorRefs: function(type) {
      if (type==2) {
        _uVisitorRefs="";
        _uVisitorRefsDesc="";
        $("#visitorRefs").html("&nbsp;");
        $("#cleanVisitorRefsBtn").hide();
      }
      fillSelectField('visitorRefs', $("#visitorRefs").html(), false);
    },
    clickVisitorRefsCheck: function(flag) {
      var choose=document.getElementsByName('visitorRefs');
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
          $("input[name='visitorRefsDesc']").hide();
        }
      }
      if (flag==3) {
        var otherCheck=false;
        for (var i=0; i<choose.length; i++) {
          if (choose[i].checked&&choose[i].getAttribute("_text")=='其他') otherCheck=true;
        }
        if (otherCheck) $("input[name='visitorRefsDesc']").show(); else $("input[name='visitorRefsDesc']").hide();
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
$('#visitorRefsModal').on('hide.bs.modal', function () {
  vueStep1.cleanVisitorRefs(1);
});
var vueStep2=new Vue({
  el: "#step2",
  data: {
    u_tempKnowWayGGP: "",
    u_tempKnowWayJS: ""
  },
  methods: {
    selChildrenNum: function() {
      _uChildrenNum="";
      var choose=document.getElementsByName('childrenNum');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#childrenNum").html(choose[i].getAttribute("_text"));
          _uChildrenNum=choose[i].value;
        }
      }
      $("#childrenNumModal").modal('hide');
      if (_uChildrenNum!="") $("#cleanChildrenNumBtn").show();
    },
    cleanChildrenNum: function() {
      $("#childrenNum").html("&nbsp;");
      _uChildrenNum="";
      $("#cleanChildrenNumBtn").hide();
      fillSelectField('childrenNum', "", false);
    },
    selFulltimeWifeFlag: function() {
      _uFulltimeWifeFlag="";
      var choose=document.getElementsByName('fulltimeWifeFlag');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#fulltimeWifeFlag").html(choose[i].getAttribute("_text"));
          _uFulltimeWifeFlag=choose[i].value;
        }
      }
      $("#fulltimeWifeFlagModal").modal('hide');
      if (_uFulltimeWifeFlag!="") $("#cleanFulltimeWifeFlagBtn").show();
    },
    cleanFulltimeWifeFlag: function() {
      $("#fulltimeWifeFlag").html("&nbsp;");
      _uFulltimeWifeFlag="";
      $("#cleanFulltimeWifeFlagBtn").hide();
      fillSelectField('fulltimeWifeFlag', "", false);
    },
    selNannyFlag: function() {
      _uNannyFlag="";
      var choose=document.getElementsByName('nannyFlag');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#nannyFlag").html(choose[i].getAttribute("_text"));
          _uNannyFlag=choose[i].value;
        }
      }
      $("#nannyFlagModal").modal('hide');
      if (_uNannyFlag!="") $("#cleanNannyFlagBtn").show();
    },
    cleanNannyFlag: function() {
      $("#nannyFlag").html("&nbsp;");
      _uNannyFlag="";
      $("#cleanNannyFlagBtn").hide();
      fillSelectField('nannyFlag', "", false);
    },
    selPetFlag: function() {
      _uPetFlag="";
      var choose=document.getElementsByName('petFlag');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#petFlag").html(choose[i].getAttribute("_text"));
          _uPetFlag=choose[i].value;
        }
      }
      $("#petFlagModal").modal('hide');
      if (_uPetFlag!="") $("#cleanPetFlagBtn").show();
    },
    cleanPetFlag: function() {
      $("#petFlag").html("&nbsp;");
      _uPetFlag="";
      $("#cleanPetFlagBtn").hide();
      fillSelectField('petFlag', "", false);
    },
    selOutEduWill: function() {
      _uOutEduWill="";
      var choose=document.getElementsByName('outEduWill');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#outEduWill").html(choose[i].getAttribute("_text"));
          _uOutEduWill=choose[i].value;
        }
      }
      $("#outEduWillModal").modal('hide');
      if (_uOutEduWill!="") $("#cleanOutEduWillBtn").show();
    },
    cleanOutEduWill: function() {
      $("#outEduWill").html("&nbsp;");
      _uOutEduWill="";
      $("#cleanOutEduWillBtn").hide();
      fillSelectField('outEduWill', "", false);
    },
    selOutExperFlag: function() {
      _uOutExperFlag="";
      var choose=document.getElementsByName('outExperFlag');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#outExperFlag").html(choose[i].getAttribute("_text"));
          _uOutExperFlag=choose[i].value;
        }
      }
      $("#outExperFlagModal").modal('hide');
      if (_uOutExperFlag!="") $("#cleanOutExperFlagBtn").show();
    },
    cleanOutExperFlag: function() {
      $("#outExperFlag").html("&nbsp;");
      _uOutExperFlag="";
      $("#cleanOutExperFlagBtn").hide();
      fillSelectField('outExperFlag', "", false);
    },
    selChildOutExperFlag: function() {
      _uChildOutExperFlag="";
      var choose=document.getElementsByName('childOutExperFlag');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#childOutExperFlag").html(choose[i].getAttribute("_text"));
          _uChildOutExperFlag=choose[i].value;
        }
      }
      $("#childOutExperFlagModal").modal('hide');
      if (_uChildOutExperFlag!="") $("#cleanChildOutExperFlagBtn").show();
    },
    cleanChildOutExperFlag: function() {
      $("#childOutExperFlag").html("&nbsp;");
      _uChildOutExperFlag="";
      $("#cleanChildOutExperFlagBtn").hide();
      fillSelectField('childOutExperFlag', "", false);
    },  
    selChildAgeGroup: function() {
      _uChildAgeGroup="";
      _uChildAgeGroupDesc="";
      var choose=document.getElementsByName('childAgeGroup');
      var ttArray="";
      var selOther=false;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          var oneText=choose[i].getAttribute("_text");
          if (choose[i].getAttribute("_text")=='其他') {
            selOther=true;
            if ($.trim($("input[name='childAgeGroupDesc']").val())!="") {
              oneText=oneText+"("+$.trim($("input[name='childAgeGroupDesc']").val())+")";
              _uChildAgeGroupDesc=$.trim($("input[name='childAgeGroupDesc']").val());
            }
          }
          ttArray+=","+oneText;
          _uChildAgeGroup+=","+choose[i].value;
        } else {
          if (choose[i].getAttribute("_text")=='其他') $("input[name='childAgeGroupDesc']").val("");
        }
      }
//      if (selOther&&_uChildAgeGroupDesc=="") alert("请录入“其他”交通出行方式");
//      else {
        if (_uChildAgeGroup.length>0) {
          _uChildAgeGroup=_uChildAgeGroup.substr(1);
          ttArray=ttArray.substr(1);
        }
        $("#childAgeGroup").html(ttArray==""?"&nbsp;":ttArray);
        $("#childAgeGroupModal").modal('hide');
        if (_uChildAgeGroup!="") $("#cleanChildAgeGroupBtn").show();
//      }
    },
    cleanChildAgeGroup: function(type) {
      if (type==2) {
        _uChildAgeGroup="";
        _uChildAgeGroupDesc="";
        $("#childAgeGroup").html("&nbsp;");
        $("#cleanChildAgeGroupBtn").hide();
      }
      fillSelectField('childAgeGroup', $("#childAgeGroup").html(), false);
    },
    clickChildAgeGroupCheck: function(flag) {
      var choose=document.getElementsByName('childAgeGroup');
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
          $("input[name='childAgeGroupDesc']").hide();
        }
      }
      if (flag==3) {
        var otherCheck=false;
        for (var i=0; i<choose.length; i++) {
          if (choose[i].checked&&choose[i].getAttribute("_text")=='其他') otherCheck=true;
        }
        if (otherCheck) $("input[name='childAgeGroupDesc']").show(); else $("input[name='childAgeGroupDesc']").hide();
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
    selSchoolType: function() {
      _uSchoolType="";
      _uSchoolTypeDesc="";
      var choose=document.getElementsByName('schoolType');
      var ttArray="";
      var selOther=false;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          var oneText=choose[i].getAttribute("_text");
          if (choose[i].getAttribute("_text")=='其他') {
            selOther=true;
            if ($.trim($("input[name='schoolTypeDesc']").val())!="") {
              oneText=oneText+"("+$.trim($("input[name='schoolTypeDesc']").val())+")";
              _uSchoolTypeDesc=$.trim($("input[name='schoolTypeDesc']").val());
            }
          }
          ttArray+=","+oneText;
          _uSchoolType+=","+choose[i].value;
        } else {
          if (choose[i].getAttribute("_text")=='其他') $("input[name='schoolTypeDesc']").val("");
        }
      }
//      if (selOther&&_uSchoolTypeDesc=="") alert("请录入“其他”交通出行方式");
//      else {
        if (_uSchoolType.length>0) {
          _uSchoolType=_uSchoolType.substr(1);
          ttArray=ttArray.substr(1);
        }
        $("#schoolType").html(ttArray==""?"&nbsp;":ttArray);
        $("#schoolTypeModal").modal('hide');
        if (_uSchoolType!="") $("#cleanSchoolTypeBtn").show();
//      }
    },
    cleanSchoolType: function(type) {
      if (type==2) {
        _uSchoolType="";
        _uSchoolTypeDesc="";
        $("#schoolType").html("&nbsp;");
        $("#cleanSchoolTypeBtn").hide();
      }
      fillSelectField('schoolType', $("#schoolType").html(), false);
    },
    clickSchoolTypeCheck: function(flag) {
      var choose=document.getElementsByName('schoolType');
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
          $("input[name='schoolTypeDesc']").hide();
        }
      }
      if (flag==3) {
        var otherCheck=false;
        for (var i=0; i<choose.length; i++) {
          if (choose[i].checked&&choose[i].getAttribute("_text")=='其他') otherCheck=true;
        }
        if (otherCheck) $("input[name='schoolTypeDesc']").show(); else $("input[name='schoolTypeDesc']").hide();
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
    selChildAvocations: function() {
      _uChildAvocations="";
      _uChildAvocationsDesc="";
      var choose=document.getElementsByName('childAvocations');
      var ttArray="";
      var selOther=false;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          var oneText=choose[i].getAttribute("_text");
          if (choose[i].getAttribute("_text")=='其他') {
            selOther=true;
            if ($.trim($("input[name='childAvocationsDesc']").val())!="") {
              oneText=oneText+"("+$.trim($("input[name='childAvocationsDesc']").val())+")";
              _uChildAvocationsDesc=$.trim($("input[name='childAvocationsDesc']").val());
            }
          }
          ttArray+=","+oneText;
          _uChildAvocations+=","+choose[i].value;
        } else {
          if (choose[i].getAttribute("_text")=='其他') $("input[name='childAvocationsDesc']").val("");
        }
      }
//      if (selOther&&_uChildAvocationsDesc=="") alert("请录入“其他”交通出行方式");
//      else {
        if (_uChildAvocations.length>0) {
          _uChildAvocations=_uChildAvocations.substr(1);
          ttArray=ttArray.substr(1);
        }
        $("#childAvocations").html(ttArray==""?"&nbsp;":ttArray);
        $("#childAvocationsModal").modal('hide');
        if (_uChildAvocationsDesc!="") $("#cleanAvocationsBtn").show();
//      }
    },
    cleanChildAvocations: function(type) {
      if (type==2) {
        _uChildAvocations="";
        _uChildAvocationsDesc="";
        $("#childAvocations").html("&nbsp;");
        $("#cleanChildAvocationsBtn").hide();
      }
      fillSelectField('childAvocations', $("#childAvocations").html(), false);
    },
    clickChildAvocationsCheck: function(flag) {
      var choose=document.getElementsByName('childAvocations');
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
          $("input[name='childAvocationsDesc']").hide();
        }
      }
      if (flag==3) {
        var otherCheck=false;
        for (var i=0; i<choose.length; i++) {
          if (choose[i].checked&&choose[i].getAttribute("_text")=='其他') otherCheck=true;
        }
        if (otherCheck) $("input[name='childAvocationsDesc']").show(); else $("input[name='childAvocationsDesc']").hide();
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
$('#childAgeGroupModal').on('hide.bs.modal', function () {
  vueStep2.cleanChildAgeGroup(1);
});
$('#schoolTypeModal').on('hide.bs.modal', function () {
  vueStep2.cleanSchoolType(1);
});
$('#childAvocationsModal').on('hide.bs.modal', function () {
  vueStep2.cleanChildAvocations(1);
});
var vueStep3=new Vue({
  el: "#step3",
  methods: {
    selLivingRadius: function() {
      _uLivingRadius="";
      var choose=document.getElementsByName('livingRadius');
      var ttArray="";
      var selOther=false;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          ttArray+=","+choose[i].getAttribute("_text");
          _uLivingRadius+=","+choose[i].value;
        } else {
          if (choose[i].getAttribute("_text")=='其他') $("input[name='livingRadiusDesc']").val("");
        }
      }
//      if (selOther&&_uLivingRadiusDesc=="") alert("请录入“其他”交通出行方式");
//      else {
      if (_uLivingRadius.length>0) {
        _uLivingRadius=_uLivingRadius.substr(1);
        ttArray=ttArray.substr(1);
      }
      $("#livingRadius").html(ttArray==""?"&nbsp;":ttArray);
      $("#livingRadiusModal").modal('hide');
      if (_uLivingRadius!="") $("#cleanLivingRadiusBtn").show();
//      }
    },
    cleanLivingRadius: function(type) {
      if (type==2) {
        _uLivingRadius="";
        $("#livingRadius").html("&nbsp;");
        $("#cleanLivingRadiusBtn").hide();
      }
      fillSelectField('livingRadius', $("#livingRadius").html(), false);
    },
    clickLivingRadiusCheck: function(flag) {
      var choose=document.getElementsByName('livingRadius');
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
          $("input[name='livingRadiusDesc']").hide();
        }
      }
      if (flag==3) {
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
    selHouseType: function() {
      _uHouseType="";
      _uHouseTypeDesc="";
      var choose=document.getElementsByName('houseType');
      var ttArray="";
      var selOther=false;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          var oneText=choose[i].getAttribute("_text");
          if (choose[i].getAttribute("_text")=='其他') {
            selOther=true;
            if ($.trim($("input[name='houseTypeDesc']").val())!="") {
              oneText=oneText+"("+$.trim($("input[name='houseTypeDesc']").val())+")";
              _uHouseTypeDesc=$.trim($("input[name='houseTypeDesc']").val());
            }
          }
          ttArray+=","+oneText;
          _uHouseType+=","+choose[i].value;
        }
      }
//        if (selOther&&_uHouseTypeDesc=="") alert("请录入“其他”关注产品类型");
//        else {
      if (_uHouseType.length>0) {
        _uHouseType=_uHouseType.substr(1);
        ttArray=ttArray.substr(1);
      }
      $("#houseType").html(ttArray==""?"&nbsp;":ttArray);
      $("#houseTypeModal").modal('hide');
      if (_uHouseType!="") $("#cleanHouseTypeBtn").show();
//        }
    },
    cleanHouseType: function(type) {
      if (type==2) {
        _uHouseType="";
        _uHouseTypeDesc="";
        $("#houseType").html("&nbsp;");
        $("#cleanHouseTypeBtn").hide();
      }
      fillSelectField('houseType', $("#houseType").html(), false);
    },
    clickHouseTypeCheck: function(flag) {
      var choose=document.getElementsByName('houseType');
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
          $("input[name='houseTypeDesc']").hide();
        }
      }
      if (flag==3) {
        var otherCheck=false;
        for (var i=0; i<choose.length; i++) {
          if (choose[i].checked&&choose[i].getAttribute("_text")=='其他') otherCheck=true;
        }
        if (otherCheck) $("input[name='houseTypeDesc']").show(); else $("input[name='houseTypeDesc']").hide();
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
    selLiveAcreage: function() {
      _uLiveAcreage="";
      var choose=document.getElementsByName('liveAcreage');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#liveAcreage").html(choose[i].getAttribute("_text"));
          _uLiveAcreage=choose[i].value;
        }
      }
      $("#liveAcreageModal").modal('hide');
      if (_uLiveAcreage!="") $("#cleanLiveAcreageBtn").show();
    },
    cleanLiveAcreage: function() {
      $("#liveAcreage").html("&nbsp;");
      _uLiveAcreage="";
      $("#cleanLiveAcreageBtn").hide();
      fillSelectField('liveAcreage', "", false);
    },
    selLoanStatus: function() {
      _uLoanStatus="";
      var choose=document.getElementsByName('loanStatus');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#loanStatus").html(choose[i].getAttribute("_text"));
          _uLoanStatus=choose[i].value;
        }
      }
      $("#loanStatusModal").modal('hide');
      if (_uLoanStatus!="") $("#cleanLoanStatusBtn").show();
    },
    cleanLoanStatus: function() {
      $("#loanStatus").html("&nbsp;");
      _uLoanStatus="";
      $("#cleanLoanStatusBtn").hide();
      fillSelectField('loanStatus', "", false);
    },
    selHouseCount: function() {
      _uHouseCount="";
      var choose=document.getElementsByName('houseCount');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#houseCount").html(choose[i].getAttribute("_text"));
          _uHouseCount=choose[i].value;
        }
      }
      $("#houseCountModal").modal('hide');
      if (_uHouseCount!="") $("#cleanHouseCountBtn").show();
    },
    cleanHouseCount: function() {
      $("#houseCount").html("&nbsp;");
      _uHouseCount="";
      $("#cleanHouseCountBtn").hide();
      fillSelectField('houseCount', "", false);
    },
    selCarFamilyCount: function() {
      _uCarFamilyCount="";
      var choose=document.getElementsByName('carFamilyCount');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#carFamilyCount").html(choose[i].getAttribute("_text"));
          _uCarFamilyCount=choose[i].value;
        }
      }
      $("#carFamilyCountModal").modal('hide');
      if (_uCarFamilyCount!="") $("#cleanCarFamilyCountBtn").show();
    },
    cleanCarFamilyCount: function() {
      $("#carFamilyCount").html("&nbsp;");
      _uCarFamilyCount="";
      $("#cleanCarFamilyCountBtn").hide();
      fillSelectField('carFamilyCount', "", false);
    },
    selCarTotalPrice: function() {
      _uCarTotalPrice="";
      var choose=document.getElementsByName('carTotalPrice');
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          $("#carTotalPrice").html(choose[i].getAttribute("_text"));
          _uCarTotalPrice=choose[i].value;
        }
      }
      $("#carTotalPriceModal").modal('hide');
      if (_uCarTotalPrice!="") $("#cleanCarTotalPriceBtn").show();
    },
    cleanCarTotalPrice: function() {
      $("#carTotalPrice").html("&nbsp;");
      _uCarTotalPrice="";
      $("#cleanCarTotalPriceBtn").hide();
      fillSelectField('carTotalPrice', "", false);
    },
    selAvocations: function() {
      _uAvocations="";
      _uAvocationsDesc="";
      var choose=document.getElementsByName('avocations');
      var ttArray="";
      var selOther=false;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          var oneText=choose[i].getAttribute("_text");
          if (choose[i].getAttribute("_text")=='其他') {
            selOther=true;
            if ($.trim($("input[name='avocationsDesc']").val())!="") {
              oneText=oneText+"("+$.trim($("input[name='avocationsDesc']").val())+")";
              _uAvocationsDesc=$.trim($("input[name='avocationsDesc']").val());
            }
          }
          ttArray+=","+oneText;
          _uAvocations+=","+choose[i].value;
        } else {
          if (choose[i].getAttribute("_text")=='其他') $("input[name='avocationsDescDesc']").val("");
        }
      }
//      if (selOther&&_uAvocationsDesc=="") alert("请录入“其他”交通出行方式");
//      else {
        if (_uAvocations.length>0) {
          _uAvocations=_uAvocations.substr(1);
          ttArray=ttArray.substr(1);
        }
        $("#avocations").html(ttArray==""?"&nbsp;":ttArray);
        $("#avocationsModal").modal('hide');
        if (_uAvocations!="") $("#cleanAvocationsBtn").show();
//      }
    },
    cleanAvocations: function(type) {
      if (type==2) {
        _uAvocations="";
        _uAvocationsDesc="";
        $("#avocations").html("&nbsp;");
        $("#cleanAvocationsBtn").hide();
      }
      fillSelectField('avocations', $("#avocations").html(), false);
    },
    clickAvocationsCheck: function(flag) {
      var choose=document.getElementsByName('avocations');
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
          $("input[name='avocationsDesc']").hide();
        }
      }
      if (flag==3) {
        var otherCheck=false;
        for (var i=0; i<choose.length; i++) {
          if (choose[i].checked&&choose[i].getAttribute("_text")=='其他') otherCheck=true;
        }
        if (otherCheck) $("input[name='avocationsDesc']").show(); else $("input[name='avocationsDesc']").hide();
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
$('#livingRadiusModal').on('hide.bs.modal', function () {
  vueStep3.cleanLivingRadius(1);
});
$('#houseTypeModal').on('hide.bs.modal', function () {
  vueStep3.cleanHouseType(1);
});
$('#avocationsModal').on('hide.bs.modal', function () {
  vueStep3.cleanAvocations(1);
});
var vueStep4=new Vue({
  el: "#step4",
  methods: {
    selRecepTimeSection: function() {
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
      fillSelectField('custScore', "", false);
    },
    selResistPoint: function() {
      _uResistPoint="";
      _uResistPointDesc="";
      var choose=document.getElementsByName('resistPoint');
      var ttArray="";
      var selOther=false;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          var oneText=choose[i].getAttribute("_text");
          if (choose[i].getAttribute("_text")=='其他') {
            selOther=true;
            if ($.trim($("input[name='resistPointDesc']").val())!="") {
              oneText=oneText+"("+$.trim($("input[name='resistPointDesc']").val())+")";
              _uResistPointDesc=$.trim($("input[name='resistPointDesc']").val());
            }
          }
          ttArray+=","+oneText;
          _uResistPoint+=","+choose[i].value;
        } else {
          if (choose[i].getAttribute("_text")=='其他') $("input[name='resistPointDesc']").val("");
        }
      }
//      if (selOther&&_uResistPointDesc=="") alert("请录入“其他”交通出行方式");
//      else {
        if (_uResistPoint.length>0) {
          _uResistPoint=_uResistPoint.substr(1);
          ttArray=ttArray.substr(1);
        }
        $("#resistPoint").html(ttArray==""?"&nbsp;":ttArray);
        $("#resistPointModal").modal('hide');
        if (_uResistPoint!="") $("#cleanResistPointBtn").show();
//      }
    },
    cleanResistPoint: function(type) {
      if (type==2) {
        _uResistPoint="";
        _uResistPointDesc="";
        $("#resistPoint").html("&nbsp;");
        $("#cleanResistPointBtn").hide();
      }
      fillSelectField('resistPoint', $("#resistPoint").html(), false);
    },
    clickResistPointCheck: function(flag) {
      var choose=document.getElementsByName('resistPoint');
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
          $("input[name='resistPointDesc']").hide();
        }
      }
      if (flag==3) {
        var otherCheck=false;
        for (var i=0; i<choose.length; i++) {
          if (choose[i].checked&&choose[i].getAttribute("_text")=='其他') otherCheck=true;
        }
        if (otherCheck) $("input[name='resistPointDesc']").show(); else $("input[name='resistPointDesc']").hide();
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
    selLoveActivation: function() {
      _uLoveActivation="";
      _uLoveActivationDesc="";
      var choose=document.getElementsByName('loveActivation');
      var ttArray="";
      var selOther=false;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          var oneText=choose[i].getAttribute("_text");
          if (choose[i].getAttribute("_text")=='其他') {
            selOther=true;
            if ($.trim($("input[name='loveActivationDesc']").val())!="") {
              oneText=oneText+"("+$.trim($("input[name='loveActivationDesc']").val())+")";
              _uLoveActivationDesc=$.trim($("input[name='loveActivationDesc']").val());
            }
          }
          ttArray+=","+oneText;
          _uLoveActivation+=","+choose[i].value;
        } else {
          if (choose[i].getAttribute("_text")=='其他') $("input[name='loveActivationDesc']").val("");
        }
      }
//      if (selOther&&_uLoveActivationDesc=="") alert("请录入“其他”交通出行方式");
//      else {
        if (_uLoveActivation.length>0) {
          _uLoveActivation=_uLoveActivation.substr(1);
          ttArray=ttArray.substr(1);
        }
        $("#loveActivation").html(ttArray==""?"&nbsp;":ttArray);
        $("#loveActivationModal").modal('hide');
        if (_uLoveActivation!="") $("#cleanLoveActivationBtn").show();
//      }
    },
    cleanLoveActivation: function(type) {
      if (type==2) {
        _uLoveActivation="";
        _uLoveActivationDesc="";
        $("#loveActivation").html("&nbsp;");
        $("#cleanLoveActivationBtn").hide();
      }
      fillSelectField('loveActivation', $("#loveActivation").html(), false);
    },
    clickLoveActivationCheck: function(flag) {
      var choose=document.getElementsByName('loveActivation');
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
          $("input[name='loveActivationDesc']").hide();
        }
      }
      if (flag==3) {
        var otherCheck=false;
        for (var i=0; i<choose.length; i++) {
          if (choose[i].checked&&choose[i].getAttribute("_text")=='其他') otherCheck=true;
        }
        if (otherCheck) $("input[name='loveActivationDesc']").show(); else $("input[name='loveActivationDesc']").hide();
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
    selFreeTimeSection: function() {
      _uFreeTimeSection="";
      var choose=document.getElementsByName('freeTimeSection');
      var ttArray="";
      var selOther=false;
      for (var i=0; i<choose.length; i++) {
        if (choose[i].checked) {
          ttArray+=","+choose[i].getAttribute("_text");
          _uFreeTimeSection+=","+choose[i].value;
        } else {
          if (choose[i].getAttribute("_text")=='其他') $("input[name='freeTimeSectionDesc']").val("");
        }
      }
//      if (selOther&&_uFreeTimeSectionDesc=="") alert("请录入“其他”交通出行方式");
//      else {
        if (_uFreeTimeSection.length>0) {
          _uFreeTimeSection=_uFreeTimeSection.substr(1);
          ttArray=ttArray.substr(1);
        }
        $("#freeTimeSection").html(ttArray==""?"&nbsp;":ttArray);
        $("#freeTimeSectionModal").modal('hide');
        if (_uFreeTimeSection!="") $("#cleanFreeTimeSectionBtn").show();
//      }
    },
    cleanFreeTimeSection: function(type) {
      if (type==2) {
        _uFreeTimeSection="";
        $("#freeTimeSection").html("&nbsp;");
        $("#cleanFreeTimeSectionBtn").hide();
      }
      fillSelectField('freeTimeSection', $("#freeTimeSection").html(), false);
    },
    clickFreeTimeSectionCheck: function(flag) {
      var choose=document.getElementsByName('freeTimeSection');
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
          $("input[name='freeTimeSectionDesc']").hide();
        }
      }
      if (flag==3) {
        var otherCheck=false;
        for (var i=0; i<choose.length; i++) {
          if (choose[i].checked&&choose[i].getAttribute("_text")=='其他') otherCheck=true;
        }
        if (otherCheck) $("input[name='freeTimeSectionDesc']").show(); else $("input[name='freeTimeSectionDesc']").hide();
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
$('#resistPointModal').on('hide.bs.modal', function () {
  vueStep4.cleanResistPoint(1);
});
$('#loveActivationModal').on('hide.bs.modal', function () {
  vueStep4.cleanLoveActivation(1);
});
$('#freeTimeSectionModal').on('hide.bs.modal', function () {
  vueStep4.cleanFreeTimeSection(1);
});
var vueStep5=new Vue({
  el: "#step5",
  methods: {
    selFamilyStatus: function() {
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
//      if (selOther&&_uWorkIndustryDesc=="") alert("请录入“其他”从事行业方式");
//      else {
        if (_uWorkIndustry.length>0) {
          _uWorkIndustry=_uWorkIndustry.substr(1);
          ttArray=ttArray.substr(1);
        }
        $("#workIndustry").html(ttArray==""?"&nbsp;":ttArray);
        $("#workIndustryModal").modal('hide');
        if (_uWorkIndustry!="") $("#cleanWorkIndustryBtn").show();
//      }
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
//      if (selOther&&_uEnterpriseTypeDesc=="") alert("请录入“其他”企业性质");
//      else {
        if (_uEnterpriseType.length>0) {
          _uEnterpriseType=_uEnterpriseType.substr(1);
          ttArray=ttArray.substr(1);
        }
        $("#enterpriseType").html(ttArray==""?"&nbsp;":ttArray);
        $("#enterpriseTypeModal").modal('hide');
        if (_uEnterpriseType!="") $("#cleanEnterpriseTypeBtn").show();
//      }
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
//      if (selOther&&_uKnowWayDesc=="") alert("请录入“其他”从事行业方式");
//      else {
        if (_uKnowWay.length>0) {
          _uKnowWay=_uKnowWay.substr(1);
          ttArray=ttArray.substr(1);
        }
        if (_uKnowWayDesc.length>0) _uKnowWayDesc=_uKnowWayDesc.substr(1);
        $("#knowWay").html(ttArray==""?"&nbsp;":ttArray);
        $("#knowWayModal").modal('hide');
        if (_uKnowWay!="") $("#cleanKnowWayBtn").show();
//      }
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
//      if (selOther&&_uInvestTypeDesc=="") alert("请录入“其他”从事行业方式");
//      else {
        if (_uInvestType.length>0) {
          _uInvestType=_uInvestType.substr(1);
          ttArray=ttArray.substr(1);
        }
        $("#investType").html(ttArray==""?"&nbsp;":ttArray);
        $("#investTypeModal").modal('hide');
        if (_uInvestType!="") $("#cleanInvestTypeBtn").show();
//      }
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
    },
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
      fillSelectField('realtyProductType', $("#realtyProductType").html(), false);
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
//      if (selOther&&_uBuyPurposeDescDesc=="") alert("请录入“其他”购房目的");
//      else {
        if (_uBuyPurpose.length>0) {
          _uBuyPurpose=_uBuyPurpose.substr(1);
          ttArray=ttArray.substr(1);
        }
        $("#buyPurpose").html(ttArray==""?"&nbsp;":ttArray);
        $("#buyPurposeModal").modal('hide');
        if (_uBuyPurposeDesc!="") $("#cleanBuyPurposeBtn").show();
//      }
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
//      if (selOther&&_uAttentionPointDescDesc=="") alert("请录入“其他”购房目的");
//      else {
      if (_uAttentionPoint.length>0) {
        _uAttentionPoint=_uAttentionPoint.substr(1);
        ttArray=ttArray.substr(1);
      }
      $("#attentionPoint").html(ttArray==""?"&nbsp;":ttArray);
      $("#attentionPointModal").modal('hide');
      if (_uAttentionPointDesc!="") $("#cleanAttentionPointBtn").show();
//      }
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
      fillSelectField('attentionPoint', $("#trafficType").html(), false);
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
//      if (selOther&&_uTrafficTypeDesc=="") alert("请录入“其他”交通出行方式");
//      else {
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
$('#knowWayModal').on('hide.bs.modal', function () {
  vueStep5.cleanKnowWay(1);
});
$('#investTypeModal').on('hide.bs.modal', function () {
  vueStep5.cleanInvestType(1);
});
$('#realtyProductTypeModal').on('hide.bs.modal', function () {
  vueStep5.cleanRealtyProductType(1);
});
$('#attentionPointModal').on('hide.bs.modal', function () {
  vueStep5.cleanAttentionPoint(1);
});
$('#trafficTypeModal').on('hide.bs.modal', function () {
  vueStep5.cleanTrafficType(1);
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