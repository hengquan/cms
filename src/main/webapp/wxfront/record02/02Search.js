var scrollMain;
var page=1;
var pageSize=8;
var userInfo={};

function loaded () {
  pullDownFlag = 0;
  pullUpFlag = 0;
  pullDown = document.getElementById("pullDown");
  pullUp = document.getElementById("pullUp");
  spinner = document.getElementById("spinner");

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
  console.dir(scrollMain);

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
}
function initPage(data) {
  userInfo=data;
  loadPage();
  $('body').show();
}
function loadPage() {
  var url=_URL_BASE+"/wx/api/getRecord02List";
  //var url=_URL_BASE+"/wx/api/testGet01List";
  var _data={};
  _data.pageSize=pageSize;
  _data.page=page;
  _data.userId=userInfo.userId;
  $.ajax({type:"post", async:true, url:url, data:_data, dataType:"json",
    success: function(json) {
      if (json.msg=='100') {
        fillList(json.data);
        scrollMain.refresh();
      } else {
        window.location.href=_URL_BASE+"/wxfront/err.html?1000=抱歉<br/>无法获得您的个人信息<br/>禁止录入";
      }
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
      window.location.href=_URL_BASE+"/wxfront/err.html?2000=系统错误<br/>status="
        +XMLHttpRequest.status+"<br/>readyState="+XMLHttpRequest.readyState+"<br/>text="+textStatus;
    }
  });
  function fillList(data) {
    var _updateUrl=_URL_BASE+"/wxfront/record02/record02Input.html?type=update";
    var _viewUrl=_URL_BASE+"/wxfront/record02/record02View.html";
    if ((!data)||data==null||data.length==0||!(data instanceof Array)) {
      if (page==1) $("#scroller").html("没有记录");
      return;
    }
    page++;
    for (var i=0; i<data.length; i++) {
      var html="";
      var oneData=data[i];
      var name=oneData.custName;
      if (oneData.custSex) name=name+"("+oneData.custSex+")";
      name=name+"<br/>";
      var phone="<span><a href='tel:"+oneData.custPhoneNum+"'>"+oneData.custPhoneNum+"</a></span><br/>";
      var cTime=new Date();
      cTime.setTime(oneData.recepTime.time);
      var fTime="<span class='sftime'>首访："+cTime.Format('yyyy-MM-dd')+"</span>";
      //顾问
      var status="<span class='ysh'>已审核</span>";
      var _url=_viewUrl+"?recordId="+oneData.id;
      if (oneData.status==1) status="<span class='ysh'>审核中</span>";
      if (oneData.status==2) status="<span class='ysh'>已通过</span>";
      if (oneData.status==3) status="<span class='ysh'>已作废</span>";
      if (oneData.status==4) {//退回
        status="<span>未通过</span>";
        _url=_updateUrl+"&recordId="+oneData.id;
      }
      if (userInfo.roleName!='顾问') {
          status="<span class='ysh'>已审核</span>";
          if (oneData.status==1) status="<span>待审核</span>";
          if (oneData.status==2) status="<span class='ysh'>已通过</span>";
          if (oneData.status==3) status="<span class='ysh'>未通过</span>";
          if (oneData.status==4) status="<span class='ysh'>未通过</span>";
      }
      var _GW="";
      if (userInfo.roleName!='顾问'&&oneData.authorName) {
      _GW="顾问:"+oneData.authorName;
      }
      var _total=oneData.total;
      var _CJ=(oneData.isKnockdown&&oneData.isKnockdown==1)?"成交":"未成交";
      html="<div class='scrollItem row'><div class='col-40 item-name2'>"+name+phone+fTime+"</div>"
        +"<div class='col-60'  onclick=\"openNew('"+_url+"')\"><div class='col-55 item-name' style='margin-left:40%'>"+_GW+"<br>总次："+_total+"次&nbsp;&nbsp;"+_CJ+"<br>"+status+"</div></div></div>";
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