/*!
 * 复访记录录入|修改
 * 页面业务处理部分
 */

//=以下为提交，包括修改和删除====================================
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
          else window.location.href=_URL_BASE+"/wxfront/err.html?1000=抱歉<br/>无法获得首访录入信息";
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
          window.location.href=_URL_BASE+"/wxfront/err.html?2000=系统错误<br/>status="
            +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
        }
      });
    }
  }
});

function fillData(data) {//填数据，包括所有页面
  if (!data) return;
  if (data.custname) $("input[name='custName']").val(data.custname);
  if (data.custphonenum) $("input[name='custPhone']").val(data.custphonenum);
  if (data.custsex) fillSelectField("sex", data.custsex, true);
  if (data.firstTime.time) fillTime("firstTime", data.firstTime.time);
  if (data.visitorCount) fillSelectField("visitorCount", data.visitorCount, true);

  function fillTime(id, value) {
    var choose=document.getElementsByName(''+id);
    for (var i=0; i<choose.length; i++) {
      if (choose[i].getAttribute("_text")==value) {
        choose[i].checked=true;
        $("#"+id).val(value);
      }
    }
  }
}
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
    //设置日期默认值
    var nt=new Date();
    var str=""+nt.getFullYear()+"-";
    str+=((100+(nt.getMonth()+1))+"").substr(1)+"-";
    str+=((100+nt.getDate())+"").substr(1);
    $("input[name='curTime']").val(str);
    //如果是
    $("input[name='user']").val(userInfo.realname);
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
}

function cleanData() {//清除数据
  //清除所有数据
  //所有的Input
  $("input").val("");
  $("input[type='radio']").checked=false;
  $("input[type='checkbox']").checked=false;
  var _uUserId="";
  var _uProjId="";
  var _uProjName="";
  var _uSex="";
  var _uUser="";
  var _uVisitorCount="";
  var _uVisitorCount="";
  var _uDecisionerIn="";
  var _uChildrenNum="";
  var _uOutEduWill="";
  var _uOutExperFlag="";
  var _uChildOutExperFlag="";
  var _uLiveAcreage="";
  var _uCarTotalPrice="";
  var _uCustScore="";
  var _uVisitorRefs="";
  var _uChildAgeGroup="";
  var _uSchoolType="";
  var _uAvocations="";
  var _uLivingRadius="";
  var _uLiveAcreage="";
  var _uAvocationsDesc="";
  var _uResistPoint="";
  var _uLoveActivation="";
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
  var _uAttentionPoint="";
  var _uAttentionPointDesc="";
}

//翻页切换
function step1Next() {//要判断是否应该进行首访录入
  $("#step1").hide(0);
  $("#step2").show(0);
  $("#step3").hide(0);
  $("#step4").hide(0);
  $("#step5").hide(0);
  return ;
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
    alert(url);
    $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
      success: function(json) {
        if (json.msg=='103') {
          window.location.href=_URL_BASE+"/wxfront/err.html?1000=抱歉<br/>系统出现未知问题，不能录入";
        } else if (json.msg=='100') { //有就已存在
          if (json.authorId==_uUserId) {//转复方
            alert(_URL_BASE+"/wxfront/input/record02.html?type=add&custName="+encodeURIComponent(_data.custName)+"&custPhone="+encodeURIComponent(_data.custPhone)+"&projId="+encodeURIComponent(_data.projId));
            window.location.href=_URL_BASE+"/wxfront/input/record02.html?type=add&custName="+encodeURIComponent(_data.custName)+"&custPhone="+encodeURIComponent(_data.custPhone)+"&projId="+encodeURIComponent(_data.projId);
          } else {
            window.location.href=_URL_BASE+"/wxfront/err.html?4000=此客户已由【"+json.authorName+"】进行接待<br/>您无权录入！";
            return;
          }
        } else canNext=true;
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
  alert(_TYPE);
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
    if (custId) retData.custid=custId;
    temp=$("input[name='custName']").val();
    if (temp) retData.custname=temp;
    temp=$("input[name='custPhone']").val();
    if (temp) retData.custphonenum=temp;
    temp=$("input[name='curTime']").val();
    if (temp) retData.receptime=temp;
    temp=$("input[name='firstTime']").val();
    if (temp) retData.firstknowtime=temp;
    if (_uSex) retData.custsex=_uSex;
    if (_uAgeGroup) retData.agegroup=_uAgeGroup;
    if (_uBuyQualify) retData.buyqualify=_uBuyQualify;
    if (_uLocalResidence) retData.localresidence=_uLocalResidence;
    if (_uLocalWorkarea) retData.localworkarea=_uLocalWorkarea;
    if (_uOutResidence) retData.outresidence=_uOutResidence;
    if (_uOutWorkarea) retData.outworkarea=_uOutWorkarea;
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
    if (_uCaptalPrepSection) retData.captalprepsection=_uCaptalPrepSection;
    temp=$("textarea[name='compareProjs']").html();
    if (temp) retData.compareprojs=temp;
    if (_uRecepTimeSection) retData.receptimesection=_uRecepTimeSection;
    if (_uCustCore) retData.custscore=_uCustCore;
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
    temp=$("textarea[name='custDescn']").html();
    if (temp) retData.custdescn=temp;
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

