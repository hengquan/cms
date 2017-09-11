var scrollMain;
var page=1;
var pageSize=8;
var userInfo={};
var searchStr="";

function loaded () {
  pullDownFlag = 0;
  pullUpFlag = 0;
  pullDown = document.getElementById("pullDown");
  pullUp = document.getElementById("pullUp");
  spinner = document.getElementById("spinner");
  searchStr=getUrlParam(window.location.href, 'searchStr');
  if (searchStr) searchStr=decodeURIComponent(searchStr);

  scrollMain=new IScroll('#wrapper', {
    probeType: 3,
    mouseWheel: true,
    interactiveScrollbars: true,
    shrinkScrollbars: 'scale',
    fadeScrollbars: true,
    useTransform: true,//CSS转化
    useTransition: true,//CSS过渡
    bounce: true,//反弹
    freeScroll: false,//只能在一个方向上滑动
    startX: 0,
    startY: 0,
    click: true
  });
  scrollMain.on('scroll',positionJudge);
  scrollMain.on("scrollEnd",action)

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
      window.location.href=_URL_BASE+"/wxfront/err.html?2000=获得人员信息时，出现系统错误<br/>status="
        +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
    }
  });
}
function initPage(data) {
  userInfo=data;
  loadPage();
  $('body').show();
}
function loadPage() {
  var url=_URL_BASE+"/wx/api/getRecord01List";
  var _data={};
  _data.pageSize=pageSize;
  _data.page=page;
  _data.userId=(userInfo?(userInfo.userid?userInfo.userid:""):"");
  _data.searchStr=(searchStr?searchStr:"");
  $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        fillList(json.data);
        scrollMain.refresh();
      } else {
        window.location.href=_URL_BASE+"/wxfront/err.html?1000=查询失败";
      }
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
      window.location.href=_URL_BASE+"/wxfront/err.html?2000=获得数据列表时，出现系统错误<br/>status="
        +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
    }
  });
  function fillList(data) {
    var _updateUrl=_URL_BASE+"/wxfront/record01/record01Input.html?type=update";
    var _viewUrl=_URL_BASE+"/wxfront/record01/record01View.html";
    if ((!data)||data==null||data.length==0||!(data instanceof Array)) {
      if (page==1) $("#scroller").html("没有记录");
      return;
    }
    page++;
    for (var i=0; i<data.length; i++) {
      var html="";
      var oneData=data[i];
      //名称
      var name=oneData.custName;
      if (oneData.custSex) name=name+"("+oneData.custSex+")";
      //接待时间
      var cTime=new Date();
      cTime.setTime(oneData.recepTime.time);
      var fTime="<span class='sftime'>首访："+cTime.Format('yyyy-MM-dd')+"</span>";
      //电话号码
      var phone="";
      if (oneData.custPhoneNum) {
        var phones=oneData.custPhoneNum.split(",");
        var _check1,_check2;
        for (var i=0; i<phones.length; i++) {
          var onePhone=$.trim(phones[i]);
          _check1=checkMPhone(onePhone);
          _check2=checkDPhone(onePhone);
          if (_check1==0||_check2==0) continue;
          if (_check1==1&&_check2==1) {
        	  phone=onePhone;
        	  break;
          }
        }
      }
      var _leftDiv="<a href='#'>"+name+"<br/><span>&nbsp;</span><br/>"+fTime+"</a>";
      if (phone) _leftDiv="<a href='tel:"+phone+"'>"+name+"<br/><span>"+phone+"</span><br/>"+fTime+"</a>";
      //顾问
      var _url=_viewUrl+"?recordId="+oneData.id;
      if (oneData.status==1) status="<span class='ysh'>审核中</span>";
      if (oneData.status==2) status="<span class='ysh'>已通过</span>";
      if (oneData.status==3) status="<span class='ysh'>已作废</span>";
      if (oneData.status==4) {//退回
        status="<span>未通过</span>";
        _url=_updateUrl+"&recordId="+oneData.id;
      }
      if (userInfo.roleName!='顾问') {
          status=" class='ysh'>已审核</span>";
          if (oneData.status==1) status="<span>待审核</span>";
          if (oneData.status==2) status="<span class='ysh'>已通过</span>";
          if (oneData.status==3) status="<span class='ysh'>未通过</span>";
          if (oneData.status==4) status="<span class='ysh'>未通过</span>";
      }
      var _GW="";
      if (userInfo.roleName!='顾问'&&oneData.authorName) {
        _GW="顾问:";
        if (oneData.authorValidate!=1) _GW+="<s>"+oneData.authorName+"</s>";
        else _GW+=oneData.authorName;
      }
      var _total=oneData.total;
      var _CJ=(oneData.isKnockdown&&oneData.isKnockdown==1)?"成交":"未成交";
      var _to02Url=_URL_BASE+"/wxfront/record02/record02Input.html?type=add"
          +"&custId="+oneData.custId+"&custName="+encodeURIComponent(oneData.custName)+"&custPhone="+oneData.custPhoneNum
          +"&userId="+oneData.userId+"&userName="+encodeURIComponent(oneData.authorName)
          +"&projId="+oneData.projId;
      html="<div class='scrollItem row examine'>"
    	  +  "<div class='col-40 item-name2'>"+_leftDiv+"</div>"
          +  "<div class='col-60'><div class='col-70 item-name' style='margin-left:30%'>"+_GW+"<br>总次："+_total+"次&nbsp;&nbsp;"+_CJ+"<br>"+status+(_to02Url?"<span style='margin-left:.5rem;'>添加复访</span>":"")+"</div>"
          +    "<div class='adopt' onclick=\"openNew('"+_url+"')\">&nbsp;</div>"
          +    (_to02Url?"<div class='to02' onclick=\"openNew('"+_to02Url+"')\">&nbsp;</div>":"")
          +  "</div>"
          +"</div>";
      $("#list").append(html);
    }
    if (pullUp.offsetTop>document.documentElement.clientHeight)  pullUp.style.color="black";
       else pullUp.style.color="white";
  }
}
document.addEventListener('touchmove', function (e) { e.preventDefault(); }, isPassive() ? {
  capture: false,
  passive: false
} : false);

var /*pullDownFlag=0,*/pullUpFlag=0;
var /*pullDown,*/pullUp;
var spinner;

function positionJudge() {
//  if(this.y>40){    //判断下拉
//    this.y=0;
    //pullDown.innerHTML = "放开刷新页面";
    //pullDownFlag = 1;
//  }else 
  if (this.y<(this.maxScrollY-40)){   //判断上拉
    pullUp.innerHTML = "放开刷新页面";
    pullUpFlag = 1;
  }
}
function action(){
//  if(pullDownFlag==1){
//    pullDown.innerHTML = "下拉刷新…";
//    pullDownFlag = 0;
//    pullDownAction();
//  }
  if(pullUpFlag==1){
    pullUp.innerHTML = "上拉刷新…";
    pullUpFlag = 0;
    pullUpAction();
  }
}
//function pullDownAction(){
//  spinner.style.display = "block";
//  setTimeout(function(){
//    var ul = document.getElementById("list");
//    var lis = ul.getElementsByTagName("li");
//    for(var i=0;i<lis.length;i++){
//      lis[i].innerHTML = "下拉刷新";
//    }
//    spinner.style.display = "none";
//    scrollMain.refresh();
//  },1000);
//}

function pullUpAction(){
  spinner.style.display = "block";
  setTimeout(function() {
    loadPage();
    spinner.style.display = "none";
    scrollMain.refresh();
  },5);
}
document.addEventListener('touchmove', function (e) {
  e.preventDefault();
}, false);

function openNew(url) {
  window.location.href=url;
}
function search() {
  var _searchStr=$("#searchStr").val();
  window.location.href=_URL_BASE+"/wxfront/record01/record01Search.html?searchStr="+encodeURIComponent(_searchStr);
}