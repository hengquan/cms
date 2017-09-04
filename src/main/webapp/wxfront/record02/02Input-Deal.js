/*!
 * 复访记录录入|修改
 * 页面业务处理部分
 */
var _TYPE="add";
var recordId="";//记录Id只有当_TYPE=update时，此变量才有值。
var custId="";

//填充数据,并初始化
$(function() {
  _TYPE=getUrlParam(window.location.href, 'type');
  if (_TYPE==null) _TYPE="add";
  if (_TYPE.toLocaleLowerCase()=='update') _TYPE="update";
  if (_TYPE=='add') $(document).attr("title","客户数据中心-复访信息录入");
  else
  if (_TYPE=='update') $(document).attr("title","客户数据中心-复访信息修改");

  if (_TYPE=='add') initData(null);
  else if (_TYPE=='update') {
    //设置不能修改的字段:项目名称，客户名称，客户电话号码
    $(document).attr("title","客户数据中心-复访信息修改");
    //获得本条记录消息信息
    var recordId=getUrlParam(window.location.href, 'recordId');
    if (!recordId) window.location.href=_URL_BASE+"/wxfront/err.html?3000=无记录Id";
    else {
      var _data={};
      _data.recordId=recordId;
      var url=_URL_BASE+"/wx/api/getRecord02";
      $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
        success: function(json) {
          if (json.msg=='100') initData(json.data);
          else window.location.href=_URL_BASE+"/wxfront/err.html?1000=抱歉<br/>无法获得复访录入信息";
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
          window.location.href=_URL_BASE+"/wxfront/err.html?2000=系统错误<br/>status="
            +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
        }
      });
    }
  }
});

/**
 * 初始化数据
 * @param data 若是修改，此data是单条数据；若新增，则data为空。
 */
function initData(data) {
  //获取人员信息
  var url=_URL_BASE+"/wx/api/personalCenter";
  $.ajax({type:"post", async:true, url:url, data:null, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        initPage(json.userInfo, data);
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
  function initPage(userInfo, data) {
    cleanData();
    //根据用户信息处理相关的内容
    //初始化项目选择
    var canShowProj=false;
    var prjNames=""+userInfo.checkProj;
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
    //处理顾问
    _uUserRole=userInfo.roleName;
    if (userInfo.roleName=='顾问') {//顾问
      $("#_SELUSER").hide();
      $("#_SHOWUSER").show();
      $("span[name='userInput']").html(userInfo.realname);
      _uUserId=userInfo.userid;
    } else if (_uUserRole=='项目负责人') {//负责人
      if (_uProjId) loadProjUser(_uProjId);
      else {
        $("#_SELUSER").hide();
        $("#_SHOWUSER").show();
        $("span[name='userInput']").html("先选项目");
      }
    }
    if (_TYPE=='add') {
    }
    else if (data) {
      fillData(data);
    }
  }
}

/**
 * 加载顾问
 */
function loadProjUser(projId) {
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

function cleanData() {//清除数据
  //清除所有数据
  $("input[type='text']").val("");
  $("input[type='radio']").attr("checked", false);
  $("input[type='checkbox']").attr("checked", false);
  $("textareaa").html("");
  $(".item_sflr.row").find("span").each(function(){$(this).html("&nbsp;");});
  $(".modal-footer").find("button").each(function(){
    if ((($(this).attr("id"))+"").indexOf('Btn')>0) $(this).hide();
  });
  _uProjId="";
  _uProjName="";
  _uUserId="";
  _uUserRole="";
  _uSex="";
  _uVisitorCount="";
  _uDecisionerIn="";
  _uVisitorRefs="";
  _uVisitorRefsDesc="";
  _uChildrenNum="";
  _uSchoolType="";
  _uChildAvocations="";
  _uChildAvocationsDesc="";
  _uOutEduWill="";
  _uOutExperFlag="";
  _uChildOutExperFlag="";
  _uLivingRadius="";
  _uLiveAcreage="";
  _uCarTotalPrice="";
  _uAvocations="";
  _uAvocationsDesc="";
  _uResistPoint="";
  _uResistPointDesc="";
  _uLoveActivation="";
  _uLoveActivationDesc="";
  _uFreeTimeSection="";
  _uRecepTimeSection="";
  _uCustScore="";

  _uFamilyStatus="";
  _uTrafficType="";
  _uTrafficTypeDesc="";
  _uBuyQualify="";
  _uWorkIndustry="";
  _uWorkIndustryDesc="";
  _uEnterpriseType="";
  _uEnterpriseTypeDesc="";
  _uKnowWay="";
  _uKnowWayDesc="";
  _uEstCustWorth="";
  _uInvestType="";
  _uInvestTypeDesc="";
  _uCapitalPrepSection="";
  _uRealtyProductType="";
  _uRealtyProductTypeDesc="";
  _uAttentAcreage="";
  _uPriceSection="";
  _uBuyPurposeDesc="";
  _uAttentionPoint="";
  _uAttentionPointDesc="";
}

function fillData(data) {//填数据，包括所有页面
  if (!data) return;
  if (data.custid) custId=data.custid;
  if (data.custname) $("input[name='custName']").val(data.custname);
  if (data.custphonenum) $("input[name='custPhone']").val(data.custphonenum);
  if (data.custsex) fillSelectField("sex", data.custsex, true);
  if (data.receptime.time) {
    var rTime=new Date();
    rTime.setTime(data.receptime.time);
    fillTime("curTime", rTime);
  }
  if (data.visitorcount) fillSelectField("visitorCount", data.visitorcount, true);
  if (data.decisionerin) fillSelectField("decisionerIn", data.decisionerin, true);
  if (data.visitorrefs) {
    var _temp=data.visitorrefs;
    if (data.visitorrefs.indexOf('其他')!=-1) {
      if (data.visitorrefsdesc) {
        var _temp2="其他("+data.visitorrefsdesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    fillSelectField("visitorRefs", _temp, true);
  }
  if (data.childrennum) fillSelectField("childrenNum", data.childrennum, true);
  if (data.childagegroup) fillSelectField("childAgeGroup", data.childagegroup, true);
  if (data.schooltype) fillSelectField("schoolType", data.schooltype, true);
  if (data.schoolname) $("input[name='schoolName']").val(data.schoolname);
  if (data.childavocations) {
    var _temp=data.childavocations;
    if (data.childavocations.indexOf('其他')!=-1) {
      if (data.childavocationsdesc) {
        var _temp2="其他("+data.childavocationsdesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    fillSelectField("childAvocations", _temp, true);
  }
  if (data.outeduwill) fillSelectField("outEduWill", data.outeduwill, true);
  if (data.outexperflag) fillSelectField("outExperFlag", data.outexperflag, true);
  if (data.outexpercity) $("input[name='outExperCity']").val(data.outexpercity);
  if (data.childoutexperflag) fillSelectField("childOutExperFlag", data.childoutexperflag, true);
  if (data.childoutexpercity) $("input[name='childOutExperCity']").val(data.childoutexpercity);
  if (data.livingradius) $("input[name='livingRadius']").val(data.livingradius);
  if (data.communityname) $("input[name='communityName']").val(data.communityname);
  if (data.housetype) $("input[name='houseType']").val(data.housetype);
  if (data.liveacreage) fillSelectField("liveAcreage", data.liveacreage, true);
  if (data.enterprisename) $("input[name='enterpriseName']").val(data.enterprisename);
  if (data.enterpriseaddress) $("input[name='enterpriseAddress']").val(data.enterpriseaddress);
  if (data.enterprisepost) $("input[name='enterprisePost']").val(data.enterprisepost);
  if (data.housecount) $("input[name='houseCount']").val(data.housecount);
  if (data.carfamilycount) $("input[name='carFamilyCount']").val(data.carfamilycount);
  if (data.carbrand) $("input[name='carBrand']").val(data.carbrand);
  if (data.cartotalprice) fillSelectField("carTotalPrice", data.cartotalprice, true);
  if (data.attentwx) $("input[name='attentWX']").val(data.attentwx);
  if (data.appnames) $("input[name='appNames']").val(data.appnames);
  if (data.avocations) {
    var _temp=data.avocations;
    if (data.avocations.indexOf('其他')!=-1) {
      if (data.avocationsdesc) {
        var _temp2="其他("+data.avocationsdesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    fillSelectField("avocations", _temp, true);
  }
  if (data.resistpoint) {
    var _temp=data.resistpoint;
    if (data.resistpoint.indexOf('其他')!=-1) {
      if (data.resistpointdesc) {
        var _temp2="其他("+data.resistpointdesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    fillSelectField("resistPoint", _temp, true);
  }
  if (data.resistpoint) {
    var _temp=data.resistpoint;
    if (data.resistpoint.indexOf('其他')!=-1) {
      if (data.resistpointdesc) {
        var _temp2="其他("+data.resistpointdesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    fillSelectField("resistPoint", _temp, true);
  }
  if (data.loveactivation) {
    var _temp=data.loveactivation;
    if (data.loveactivation.indexOf('其他')!=-1) {
      if (data.loveactdesc) {
        var _temp2="其他("+data.loveactdesc+")";
        _temp=_temp.replace("其他", _temp2);
      }
    }
    fillSelectField("loveActivation", _temp, true);
  }
  if (data.freetimesection) fillSelectField("freeTimeSection", data.freetimesection, true);
  if (data.receptimesection) fillSelectField("recepTimeSection", data.receptimesection, true);
  if (data.custscore) fillSelectField("custScore", data.custscore, true);
  if (data.compareprojs) $("textarea[name='compareProjs']").val(data.compareprojs);
  if (data.custdescn) $("textarea[name='custDescn']").val(data.custdescn);

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

  function fillTime(id, value) {
    var str=""+_time.getFullYear()+"-";
    str+=((100+(_time.getMonth()+1))+"").substr(1)+"-";
    str+=((100+_time.getDate())+"").substr(1);
    $("input[name='"+id+"']").val(str);
  }
}

//翻页切换
function step1Next() {//要判断是否应该进行首访录入
//  $("#step1").hide(0);
//  $("#step2").show(0);
//  $("#step3").hide(0);
//  $("#step4").hide(0);
//  $("#step5").hide(0);
//  return ;
  if (_TYPE=='update') {
    $("#step1").hide(0);
    $("#step2").show(0);
    $("#step3").hide(0);
    $("#step4").hide(0);
    $("#step5").hide(0);
  } else if (_TYPE=='add') {//要判断是否应该进行首访录入
    //获得参数
    var _data={};
    _data.custName=$("input[name='custName']").val();
    _data.phoneNum=$("input[name='custPhone']").val();
    var errMsg="";
    if (!$.trim(_data.custName)) errMsg+="\n请输入客户姓名！";
    if (!$.trim(_data.phoneNum)) errMsg+="\n请输入客户电话号码！";
    if (!$.trim(_uProjId)) errMsg+="\n请选择项目！";
    if (_data.phoneNum.length<11) errMsg+="\n手机号码不符合规则"; 
    _data.projId=_uProjId;
    if (errMsg) {
      alert(errMsg.substr(1));
      return;
    }
    var canNext=false;
    var url=_URL_BASE+"wx/api/existRecord01";
    $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
      success: function(json) {
        if (json.msg=='100') {//有就已存在，可正常录入复访
          if (json.authorId==_uUserId) {
            canNext=true;
          } else {
            alert("此客户已由其他顾问处理，您无法录入其复访信息");
          }
        } else {//转首访
          alert("不存在该用户，将转到首访");
          window.location.href=_URL_BASE+"/wxfront/record01/record01Input.html?type=add&custName="+encodeURIComponent(_data.custName)+"&custPhone="+encodeURIComponent(_data.custPhone)+"&projId="+encodeURIComponent(_data.projId);
        }
        if (canNext) {
          $("#step1").hide(0);
          $("#step2").show(0);
          $("#step3").hide(0);
          $("#step4").hide(0);
          $("#step5").hide(0);
        }
      },
      error: function(XMLHttpRequest, textStatus, errorThrown) {
        window.location.href=_URL_BASE+"/wxfront/err.html?2000=系统错误<br/>status="
          +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
      }
    });
  }
}
function step2Prev() {
  $("#step1").show(0);
  $("#step2").hide(0);
  $("#step3").hide(0);
  $("#step4").hide(0);
  $("#step5").hide(0);
}
function step2Next() {
  $("#step1").hide(0);
  $("#step2").hide(0);
  $("#step3").show(0);
  $("#step4").hide(0);
  $("#step5").hide(0);
}
function step3Prev() {
  $("#step1").hide(0);
  $("#step2").show(0);
  $("#step3").hide(0);
  $("#step4").hide(0);
  $("#step5").hide(0);
}
function step3Next() {
  $("#step1").hide(0);
  $("#step2").hide(0);
  $("#step3").hide(0);
  $("#step4").show(0);
  $("#step5").hide(0);
}
function step4Prev() {
  $("#step1").hide(0);
  $("#step2").hide(0);
  $("#step3").show(0);
  $("#step4").hide(0);
  $("#step5").hide(0);
}
function step4Next() {
  $("#step1").hide(0);
  $("#step2").hide(0);
  $("#step3").hide(0);
  $("#step4").hide(0);
  $("#step5").show(0);
}
function step5Prev() {
  $("#step1").hide(0);
  $("#step2").hide(0);
  $("#step3").hide(0);
  $("#step4").show(0);
  $("#step5").hide(0);
}

//=以下为提交，包括修改和删除====================================
function commitData() {
  var commitData=getData(_TYPE);
  var msg=validate(comitData, _TYPE);
  if (msg.err) {
    if (msg.returnTo==1) step2Prev();
    alert(err);
    return;
  }
  if (_TYPE=='add') commitInsert(commitData);
  else
  if (_TYPE='update') commitUpdate(commitData);

  function getData(type) {
    var retData={};
    var temp="";
    if (_uProjId) retData.projid=_uProjId;
    if (type=='add') {
      if (_uUserId) retData.authorid=_uUserId;
      if (_uUserId) retData.creatorid=_uUserId;
    }
    temp=$("input[name='recpTime']").val();
    if (temp) retData.receptime1=temp;
    if (custId) retData.custid=custId;
    temp=$("input[name='custName']").val();
    if (temp) retData.custname=temp;
    temp=$("input[name='custPhone']").val();
    if (temp) retData.custphonenum=temp;
    if (_uSex) retData.custsex=_uSex;
    if (_uVisitorCount) retData.visitorcount=_uVisitorCount;
    if (_uDecisionerIn) retData.decisionerin=_uDecisionerIn;
    if (_uVisitorRefs) retData.visitorrefs=_uVisitorRefs;
    if (_uVisitorRefsDesc) retData.visitorrefsdesc=_uVisitorRefsDesc;
    if (_uChildrenNum) retData.childrennum=_uChildrenNum;
    if (_uChildAgeGroup) retData.childagegroup=_uChildAgeGroup;
    if (_uSchoolType) retData.schooltype=_uSchoolType;
    if (_uChildAvocations) retData.childavocations=_uChildAvocations;
    if (_uChildAvocationsDesc) retData.childavocationsdesc=_uChildAvocationsDesc;
    if (_uOutEduWill) retData.outeduwill=_uOutEduWill;
    if (_uOutExperFlag) retData.outexperflag=_uOutExperFlag;
    temp=$("input[name='outExperCity']").val();
    if (temp) retData.outexpercity=temp;
    if (_uChildOutExperFlag) retData.childoutexperflag=_uChildOutExperFlag;
    temp=$("input[name='childOutExperCity']").val();
    if (temp) retData.childoutexpercity=temp;
    if (_uLivingRadius) retData.livingradius=_uLivingRadius;
    temp=$("input[name='communityName']").val();
    if (temp) retData.communityname=temp;
    temp=$("input[name='houseType']").val();
    if (temp) retData.housetype=temp;
    if (_uLiveAcreage) retData.liveacreage=_uLiveAcreage;
    temp=$("input[name='enterpriseName']").val();
    if (temp) retData.enterprisename=temp;
    temp=$("input[name='enterpriseAddress']").val();
    if (temp) retData.enterpriseaddress=temp;
    temp=$("input[name='enterprisePost']").val();
    if (temp) retData.enterprisepost=temp;
    temp=$("input[name='houseCount']").val();
    if (temp) retData.housecount=temp;
    temp=$("input[name='carFamilyCount']").val();
    if (temp) retData.carfamilycount=temp;
    temp=$("input[name='carBrand']").val();
    if (temp) retData.carbrand=temp;
    if (_uCarTotalPrice) retData.cartotalprice=_uCarTotalPrice;
    temp=$("input[name='attentWX']").val();
    if (temp) retData.attentwx=temp;
    temp=$("input[name='appNames']").val();
    if (temp) retData.appnames=temp;
    if (_uAvocations) retData.avocations=_uAvocations;
    if (_uAvocationsDesc) retData.avocationsdesc=_uAvocationsDesc;
    if (_uResistPoint) retData.resistpoint=_uResistPoint;
    if (_uResistPointDesc) retData.resistpointdesc=_uResistPointDesc;
    if (_uLoveActivation) retData.loveactivation=_uLoveActivation;
    if (_uLoveActivationDesc) retData.loveactdesc=_uLoveActivationDesc;
    if (_uFreeTimeSection) retData.freetimesection=_uFreeTimeSection;
    if (_uRecepTimeSection) retData.receptimesection=_uRecepTimeSection;
    if (_uCustScore) retData.custscore=_uCustScore;
    temp=$("textarea[name='compareProjs']").val();
    if (temp) retData.compareprojs=temp;
    temp=$("textarea[name='custDescn']").val();
    if (temp) retData.custdescn=temp;

    if (_uFamilyStatus) retData.familystatus=_uFamilyStatus;
    if (_uTrafficType) retData.traffictype=_uTrafficType;
    if (_uTrafficTypeDesc) retData.traffictypedesc=_uTrafficTypeDesc;
    if (_uBuyQualify) retData.buyqualify=_uBuyQualify;
    if (_uWorkIndustry) retData.workindustry=_uWorkIndustry;
    if (_uWorkIndustryDesc) retData.workindustrydesc=_uWorkIndustryDesc;
    if (_uEnterpriseType) retData.enterprisetype=_uEnterpriseType;
    if (_uEnterpriseTypeDesc) retData.enterprisetypedesc=_uEnterpriseTypeDesc;
    if (_uKnowWay) retData.knowway=_uKnowWay;
    if (_uKnowWayDesc) retData.knowwaydesc=_uKnowWayDesc;
    if (_uEstCustWorth) retData.estcustworth=_uEstCustWorth;
    if (_uInvestType) retData.investtype=_uInvestType;
    if (_uInvestTypeDesc) retData.investtypedesc=_uInvestTypeDesc;
    if (_uCapitalPrepSection) retData.capitalprepsection=_uCapitalPrepSection;
    if (_uRealtyProductType) retData.realtyproducttype=_uRealtyProductType;
    if (_uRealtyProductTypeDesc) retData.realtyproducttypedesc=_uRealtyProductTypeDesc;
    if (_uAttentAcreage) retData.attentacreage=_uAttentAcreage;
    if (_uPriceSection) retData.pricesection=_uPriceSection;
    if (_uBuyPurpose) retData.buypurpose=_uBuyPurpose;
    if (_uBuyPurposeDesc) retData.buypurposedesc=_uBuyPurposeDesc;
    if (_uAttentionPoint) retData.attentionpoint=_uAttentionPoint;
    if (_uAttentionPointDesc) retData.attentionpointdesc=_uAttentionPointDesc;

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
    if (!data.projid) ret.err+=";/n无法获得项目Id";
    if (!data.custid&&type=='update') ret.err+=";/n无法获得客户Id";
    if (!data.custname) {
      ret.err+=";/n无法获得客户名称";
      ret.turnTo=1;
    }
    if (!data.custphonenum) {
      ret.err+=";/n无法获得客户手机";
      ret.turnTo=1;
    }
    if (data.custphonenum.length<11) {
      ret.err+=";/n无法获得客户手机";
      ret.turnTo=1;
    }
    return ret;
  }
  function commitInsert(_data) {
    var url=_URL_BASE+"wx/api/addHisFirstRecord";
    alert(url);
    $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
      success: function(json) {
        if (json.msg!='100') {
          window.location.href=_URL_BASE+"/wxfront/err.html?8001=录入复访记录错误！";
        } else {
          if (confirm("录入成功，要录入下一条复访记录吗？")) {
            step2Prev();
            cleanData();
          } else {
            window.location.href=_URL_BASE+"/wxfront/search/record01.html";
          }
        }
      },
      error: function(XMLHttpRequest, textStatus, errorThrown) {
        window.location.href=_URL_BASE+"/wxfront/err.html?2000=系统错误<br/>status="
          +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
      }
    });
  }
  function commitUpdate(data) {
    var url=_URL_BASE+"wx/api/updateRecord01";
    alert(url);
    $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
      success: function(json) {
        if (json.msg!='100') {
          window.location.href=_URL_BASE+"/wxfront/err.html?9001=修改首访记录错误！";
        } else {
          alert("修改复访记录成功!");
          window.location.href=_URL_BASE+"/wxfront/search/record01.html";
        }
      },
      error: function(XMLHttpRequest, textStatus, errorThrown) {
        window.location.href=_URL_BASE+"/wxfront/err.html?2000=系统错误<br/>status="
          +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
      }
    });
  }
}
