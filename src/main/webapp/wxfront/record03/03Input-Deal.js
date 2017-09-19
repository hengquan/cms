var _TYPE="add";
var recordId="";//记录Id只有当_TYPE=update时，此变量才有值。
var custId="";
var curUserInfo={};

//填充数据,并初始化
$(function() {
  $(".subNav").click(function(){
    $(this).toggleClass("currentDd").siblings(".subNav").removeClass("currentDd");
    $(this).toggleClass("currentDt").siblings(".subNav").removeClass("currentDt");
    $(this).next(".navContent").slideToggle(500).siblings(".navContent").slideUp(500);
  });
  _TYPE=getUrlParam(window.location.href, 'type');
  if (_TYPE==null) _TYPE="add";
  if (_TYPE.toLocaleLowerCase()=='update') _TYPE="update";
  if (_TYPE=='add') $(document).attr("title","客户数据中心-成交信息录入");
  else
  if (_TYPE=='update') $(document).attr("title","客户数据中心-成交信息修改");

  if (_TYPE=='add') {//处理带入的参数
      var data={};
//    var _projId=getUrlParam(window.location.href, 'projId');
//    var _userId=getUrlParam(window.location.href, 'userId');
//    var _userName=getUrlParam(window.location.href, 'userName');
//    var _custId=getUrlParam(window.location.href, 'custId');
//    var _custName=getUrlParam(window.location.href, 'custName');
//    var _custPhone=getUrlParam(window.location.href, 'custPhone');
//    var data=null;
//    if (_projId&&_userId&&_userName&&_custId&&_custName&&_custPhone) {
//      data={};
//      data.from01Flag=1;
//      data.projId=_projId;
//      data.userId=_userId;
//      data.userName=decodeURIComponent(_userName);
//      data.custId=_custId;
//      data.custName=decodeURIComponent(_custName);
//      data.custPhone=_custPhone;
//    }
    initData(data);
  } else if (_TYPE=='update') {
    //获得本条记录消息信息
    var recordId=getUrlParam(window.location.href, 'recordId');
    if (!recordId) window.location.href=_URL_BASE+"/wxfront/err.html?3000=无记录Id";
    else {
      var _data={};
      _data.recordId=recordId;
      var url=_URL_BASE+"/wx/api/getRecord03";
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
 * @param data 若是修改，此data是单条数据；若新增，则data为空；若带入，则data为带入的信息。
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
    curUserInfo=userInfo;
    cleanData(2);
    //初始化项目选择
    var canShowProj=false;
    var prjNames=""+userInfo.checkProj;
    var lPrj=prjNames.split(',');
    var projHtml="";
    var _uProj,projId,projName;
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
        $("span[name='userInput']").html("请先选择项目");
      }
    }
    var nt=new Date();
    fillTime("recpTime", nt);
    fillTime("purchaseDate", nt);
    fillTime("signDate", nt);
    if (_TYPE=='update'&&data) {
      fillData(data);
      getAudit(recordId);
    }
    step2Prev();
    //按传入处理
    if (_TYPE=='add'&&data&&data.from01Flag&&data.from01Flag==1) {
      _uProjId=data.projId;
      $("#projArea").hide();
      if (!_uProjName) {
        for (var i=0;i<lPrj.length; i++) {
          if ($.trim(lPrj[i])!='') {
            if (lPrj[i].indexOf('-')!=-1) {
              _uProj=lPrj[i];
              projId=lPrj[i].substring(0,lPrj[i].indexOf('-'));
              projName=lPrj[i].substring(lPrj[i].indexOf('-')+1);
              if (projId==_uProjId) {
                _uProjName=projName;
                break;
              }
            }
          }
        }
      }
      $("#_SELUSER").hide();
      $("#_SHOWUSER").show();
      _uUserId=data.userId;
      _uUserName=data.userName;
      $("span[name='userInput']").html(_uUserName);
      custId=data.custId;
      $("input[name='custName']").val(data.custName);
      $("input[name='custPhone']").val(data.custPhone);
    }
  }
}
function loadProjUser(projId) {//加载顾问
  $("#userData").html("");
  $("span[name='userInput']").html("加载顾问...");
  _uUserId="";
  _uUserName="";
  var url=_URL_BASE+"/wx/api/getUserList?projId="+projId;
  $.ajax({type:"post", async:true, url:url, data:null, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        if (json.users&&json.users.length>0) {
          $("#_SELUSER").show();
          $("#_SHOWUSER").hide();
          for (var i=0; i<json.users.length; i++) {
            var oneUser=json.users[i];
            if (oneUser.id==curUserInfo.userid) continue;
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
  var url=_URL_BASE+"/wx/api/getCheckReason?recordType=3&recordId="+id;
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
function cleanData(type) {
  //清除所有数据
  $("input[type='text']").val("");
  $("input[type='radio']").attr("checked", false);
  $("input[type='checkbox']").attr("checked", false);
  $("textareaa").html("");
  $(".item_sflr.row").find("span").each(function(){$(this).html("&nbsp;");});
  $(".modal-footer").find("button").each(function(){
    if ((($(this).attr("id"))+"").indexOf('Btn')>0) $(this).hide();
  });
  var _type=1;
  if (type) _type=type;
  if (_type==1) {
    _uProjId="";
    _uProjName="";
    _uUserId="";
    _uUserName="";
    _uUserRole="";
  }
  _uSex="";
  _uHouseRegiType="";
  _uPaymentType="";
  _uPaymentTypeDesc="";
  _uRealtyProductType="";
  _uRealtyProductTypeDesc="";
  _uLivingStatus="";
  _uRealUseMen="";
  _uRealPayMenDesc="";
  _uRealPayMen="";
  _uRealPayMenDesc="";
}

function checkField(id){
  return !(!$.trim($("[name='"+id+"']").val()));
}

//翻页切换
function step1Next() {//要判断是否应该进行成交录入
  var err=checkStep1();
  if (err) {
    alert(err);
    return;
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

function checkPhone(docId) {
  var temp=$("input[name='"+docId+"']").val();
  if (!temp) return "请录入客户电话号码";
  var phones=temp.split(",");
  var _errPhone="";
  var _okPhones="";
  var _check1,_check2;
  for (var i=0; i<phones.length; i++) {
    var onePhone=$.trim(phones[i]);
    _check1=checkMPhone(onePhone);
    if (_check1==0) continue;
    _check2=checkDPhone(onePhone);
    if (_check1!=1&&_check2!=1) {
      _errPhone=onePhone;
      break;
    }
  }
  if (_errPhone) return "客户电话号码["+_errPhone+"]不合法";
  return "";
}

function checkStep1() {
  if (!_uProjId) return "请选择项目!";
  if (!_uUserId) return "请选择置业顾问!";
  if (!checkField("custName")) return '请录入房屋买受人姓名!';
  var temp=checkPhone('custPhone');
  if (temp) return temp;
  if (!_uSex) return "请选择客户性别!";
  if (!checkField("recpTime")) return '请录入到访时间!';
  if (!checkField("purchaseDate")) return '请录入认购日期!';
  if (!checkField("signDate")) return '请填录入签约日期!';
  if (!checkField("houseNum")) return '请填录入购买房号!';
  temp=$("[name='visitCycle']").val();
  if (!temp) return '请填录入认知-到访天数!';
  if(isNaN(temp)) return '认知-到访天数须是大于0的自然数!';
  if(temp<=0) return '认知-到访天数须是大于0的自然数!';
  temp=$("[name='purchaseCycle']").val();
  if (!temp) return '请填录入到访-认购天数!';
  if(isNaN(temp)) return '到访-认购天数须是大于0的自然数!';
  if(temp<=0) return '到访-认购天数须是大于0的自然数!';
  temp=$("[name='signCycle']").val();
  if (!temp) return '请填录入认购-签约天数!';
  if(isNaN(temp)) return '认购-签约天数须是大于0的自然数!';
  if(temp<=0) return '认购-签约天数须是大于0的自然数!';
  if (!_uHouseRegiType) return "请选择户籍!";
  if (!checkField("houseAcreage")) return '请录入成交面积!';
  if (!checkField("unitPrice")) return '请录入成交单价!';
  if (!checkField("totalPrice")) return '请录入成交总价!';
  if (!_uPaymentType) return "请选择付款方式!";
  if (!checkField("loanBank")) return '请录入贷款银行!';
  return "";
}
function checkStep2() {
  if (!_uRealtyProductType) return "请选择关注产品类型!";
  if (!checkField("addressMail")) return '请录入通邮地址!';
  if (!_uLivingStatus) return "请选择实际居住情况!";
  if (!_uRealUseMen) return "请选择房屋使用人!";
  if (!_uRealPayMen) return "请选择购房出资人!";
  return "";
}
function checkStep3() {
  if (!($("textarea[name='sumDescn']").val())) return "请录入客户综合描述";
  return "";
}

//=以下为提交，包括修改和删除====================================
function commitData() {
  var err="";
  if (!($("#step4").is(":hidden"))) {
    err=checkStep3();
    if (err) {
      alert(err);
      return;
    }
  }
  //遮罩
  $("#mask").show();
  //按钮致为灰色
  $("div[_type='BTN']").each(function(){
    $(this).attr("style", "margin-top:1.5rem;background-color:#dedede;color:#c7c7c7");
  });
  var commitData=getData(_TYPE);
  if (_TYPE=='add') commitInsert(commitData);
  else
  if (_TYPE='update') commitUpdate(commitData);

  function getData(type) {
    var retData={};
    var temp="";
    //获取项目id
    if (_uProjId) retData.projid=_uProjId;
    if (type=='add') {
      if (_uUserId) retData.authorid=_uUserId;
      if (_uUserId) retData.creatorid=_uUserId;
    }
    temp=$("input[name='recpTime']").val();
    if (temp) retData.receptime1=temp;
    if (custId) retData.custid=custId;
    //用户名称-房屋买受人姓名
    temp=$("input[name='custName']").val();
    if (temp) retData.custname=temp;
    temp=$("input[name='custPhone']").val();
    if (temp) retData.custphonenum=temp;
    if (_uSex) retData.custsex=_uSex;
    temp=$("input[name='purchaseDate']").val();
    if (temp) retData.purchasedate1=temp;
    temp=$("input[name='signDate']").val();
    if (temp) retData.signdate1=temp;
    temp=$("input[name='houseNum']").val();
    if (temp) retData.housenum=temp;
    temp=$("input[name='visitCycle']").val();
    if (temp) retData.visitcycle=temp;
    temp=$("input[name='purchaseCycle']").val();
    if (temp) retData.purchasecycle=temp;
    temp=$("input[name='signCycle']").val();
    if (temp) retData.signcycle=temp;
    if (_uHouseRegiType) retData.houseregitype=_uHouseRegiType;
    temp=$("input[name='houseAcreage']").val();
    if (temp) retData.houseacreage=temp;
    temp=$("input[name='unitPrice']").val();
    if (temp) retData.unitprice=temp;
    temp=$("input[name='totalPrice']").val();
    if (temp) retData.totalprice=temp;
    if (_uPaymentType) retData.paymenttype=_uPaymentType;
    if (_uPaymentTypeDesc) retData.paymenttypedesc=_uPaymentTypeDesc;
    temp=$("input[name='loanBank']").val();
    if (temp) retData.loanbank=temp;
    if (_uRealtyProductType) retData.realtyproducttype=_uRealtyProductType;
    if (_uRealtyProductTypeDesc) retData.realtyproducttypedesc=_uRealtyProductTypeDesc;
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
    return retData;
  }
  function commitInsert(_data) {
    var url=_URL_BASE+"/wx/api/addTradeVisit";
    $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
      success: function(json) {
        //遮罩
        $("#mask").hide();
        //按钮致为兰色
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
        //按钮致为兰色
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

//=以下客户处理====================================
var _thisProjId="";
var _thisUserId="";
function openSelCust() {
  if (!_uProjId) {
    alert("未确定具体项目，无法选择客户");
    return ;
  }
  if (!_uUserId&&_uUserRole=='顾问') {
    alert("未确定置业顾问，无法选择客户");
    return ;
  }
  if (_thisProjId!=_uProjId||_thisUserId!=_uUserId) {
    var url=_URL_BASE+"/wx/api/getCustList";
    var _data={};
    _data.projId=_uProjId;
    if (_uUserId) _data.userId=_uUserId;
    $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
      success: function(json) {
        if (json.msg!='100') {
          alert("未获得任何客户信息");
        } else {
          $("#custData").html("");
          if (json.customers.length==0) {
            alert("["+_uProjName+"]项目还没有接待任何客户，可直接录入该客户的信息进行成交录入!");
            return;
          }
          for (var i=0; i<json.customers.length; i++) {
            var oneCust=json.customers[i];
            var _phones=oneCust.custPhone;
            _phones=$.trim(_phones.split(",")[0]);
            var _innerHtml=oneCust.custName+"<span>（"+oneCust.custSex+"）</span><span>"+_phones+"</span><span>"+oneCust.projName+"</span>";
            var userHtml="<label><input type='radio' name='selectCustomers' value='"+oneCust.custId+"' _text='"+oneCust.custName+"' _userSex='"+oneCust.custSex+"' _userId='"+oneCust.userId+"' _userName='"+oneCust.realName+"' _phone='"+_phones+"' onclick='selCust()'/>"+_innerHtml+"</label>";
            if (i<(json.customers.length-1)) userHtml+="<br>";
            $("#custData").append(userHtml);
          }
          $('#selectCustomersModal').modal('show');
        }
      },
      error: function(XMLHttpRequest, textStatus, errorThrown) {
        alert("获得客户信息时出现系统错误：\nstatu="+XMLHttpRequest.status+"\nreadyState="+XMLHttpRequest.readyState+"\ntext="+textStatus+"\nerrThrown="+errorThrown);
      }
    });
    _thisProjId=_uProjId;
    _thisUserId=_uUserId;
  } else {
    var choose=document.getElementsByName('selectCustomers');
    if (choose&&choose.length>0) $('#selectCustomersModal').modal('show');
    else alert("["+_uProjName+"]项目还没有接待任何客户，可直接录入该客户的信息进行成交录入!");
  }
}
function cleanCust() {
  $("input[name='custName']").val("");
  $("input[name='custPhone']").val("");
  custId="";
  var choose=document.getElementsByName('selectCustomers');
  for (var i=0; i<choose.length; i++) choose[i].checked=false;
}
function selCust() {
  var oldCustId=custId;
  var choose=document.getElementsByName('selectCustomers');
  for (var i=0; i<choose.length; i++) {
    if (choose[i].checked) {
      $("input[name='custName']").val(choose[i].getAttribute("_text"));
      $("input[name='custPhone']").val(choose[i].getAttribute("_phone"));
      custId=choose[i].value;
      $("span[name='userInput']").html(choose[i].getAttribute("_userName"));
      _uUserId=choose[i].getAttribute("_userId");
      _uUserName=choose[i].getAttribute("_userName");
      fillSelectField('sex', choose[i].getAttribute("_userSex"), true);
    }
  }
  $("#selectCustomersModal").modal('hide');
  if (custId!="") $("#cleanCustBtn").show();
  if (oldCustId!=custId&&custId) {
    var url=_URL_BASE+"/wx/api/getCustMsg";
    var _data={};
    _data.custId=custId;
    _data.projId=_uProjId;
    $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
      success: function(json) {
        if (json.msg=='100') {
          customer=json.customer;
          if (customer) {
            if (customer.visitcount) $("#visitCount").html(customer.visitcount);
          }
        }
      },
      error: function(XMLHttpRequest, textStatus, errorThrown) {
        alert("获得客户信息时出现系统错误：\nstatu="+XMLHttpRequest.status+"\nreadyState="+XMLHttpRequest.readyState+"\ntext="+textStatus+"\nerrThrown="+errorThrown);
      }
    });
  }
}

//=====================================
//以下填充方法
function fillTime(id, _time) {
  var str=""+_time.getFullYear()+"-";
  str+=((100+(_time.getMonth()+1))+"").substr(1)+"-";
  str+=((100+_time.getDate())+"").substr(1);
  $("input[name='"+id+"']").val(str);
}
function fillData(data) {//填数据
  if (!data) return;
  if (data.projid) _uProjId=data.projid;
  if (data.projname) {
    _uProjName=data.projname;
    $("#proj").html(_uProjName);
  }
  if (data.custid) custId=data.custid;
  if (data.custname) $("input[name='custName']").val(data.custname);
  if (data.custphonenum) $("input[name='custPhone']").val(data.custphonenum);
  if (data.custsex) fillSelectField("sex", data.custsex, true);
  if (data.receptime.time) {
    var rTime=new Date();
    rTime.setTime(data.receptime.time);
    fillTime("recpTime", rTime);
  }
  if (data.purchasedate.time) {
    var rTime=new Date();
    rTime.setTime(data.purchasedate.time);
    fillTime("purchaseDate", rTime);
  }
  if (data.signdate.time) {
    var rTime=new Date();
    rTime.setTime(data.signdate.time);
    fillTime("signDate", rTime);
  }
  if (data.housenumup) $("input[name='houseNumup']").val(data.housenumup);
  if (data.visitcycle) $("input[name='visitCycle']").val(data.visitcycle);
  if (data.purchasecycle) $("input[name='purchaseCycle']").val(data.purchasecycle);
  if (data.signcycle) $("input[name='signCycle']").val(data.signcycle);
  if (data.houseregitype) fillSelectField("houseRegiType", data.houseregitype, true);
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
  if (data.realusemen) fillSelectField("realUseMen", data.realusemen, true);
  if (data.realpaymen) fillSelectField("realPayMen", data.realpaymen, true);
  if (data.suggestion) $("textarea[name='suggestion']").val(data.suggestion);
  if (data.talkqands) $("textarea[name='talkQandS']").val(data.talkqands);
  if (data.signqands) $("textarea[name='signQandS']").val(data.signqands);
  if (data.sumdescn) $("textarea[name='sumDescn']").val(data.sumdescn);
}