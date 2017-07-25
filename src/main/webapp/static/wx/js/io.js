/**
 * Created by Toma on 2016/9/17.
 * 后台接口,微信接口http://localhost/zhizhaoke
 */
var io = window.io || {};
// 请求信息 - 每次请求都会装载此数据
io.userInfo = {
  'openId':userInfo.openid,
  'test':{
	    name:userInfo.name,
	    diz: 132,
	    arr: ['sdf','sd4']
   },
  'makeOrderId':userInfo.makeOrderId,
  'expertId':userInfo.expertId
};

io.questionList = {
  // 列表页 - 分页返回全部问题
  content: '../../wx/api/problem.az'
};
io.questionDetail = {
  // 问题详情页
  content: '../../wx/api/problembyid.az',
  // 加载更多的答案
  answers: '../../wx/api/answerbyid.az',
  // 提交答案
  submit: '../../wx/api/answersubmit.az'
};
io.question = {
  // 问题 - 点赞 收藏 举报 - 返回状态码
  collectCount: '../../wx/api/collectproblem.az',
  praiseCount: '../../wx/api/collectproblem.az',
  reportCount: '../../wx/api/problem.az'
};

io.quiz = {
  // 提交问题
  content: '../../wx/api/problemsubmit.az'
};

io.specialist = {
	// 获取可用时间
	times: '../../wx/api/expertWorkTime.az',
	// 提交地址
	form: '../../wx/api/subOrder.az'
};

io.userSubscribe = {
	// 我的订单
	content: '../../wx/api/delOrder.az',
	// 订单评价
	evaluate: '../../wx/api/goComment.az',
	//订单JOSN
	orderMsg: '../../wx/api/mySubscribeMsg.az',
}

io.user = {
	// 收藏
	userCollect: '../../wx/api/mycollect.az',
	// 移除收藏
	userRmoveCollect: '../../wx/api/delmycollect.az'
};

io.userQuestionList = {
	// 我的问题列表
	content: '../../wx/api/myproblem.az'
};
io.userQuestionDetail = {
	// 我的问题详情
	content: '../../wx/api/problembyid.az',
	// 设置最佳答案
	zjda: '../../wx/api/setzjda.az',
	// 追问
	asked: '../../wx/api/questionasked.az'
};
io.userZhizhao = {
	// 我的支招
	content: '../../wx/api/myanswer.az'
}
io.userListen = {
	// 我的偷听
	content: '../../wx/api/myeavesdrop.az'
}


/* ----------------------- 微信 remove start ----------------------------------*/

// $.ajax({
//   url: 'http://cloud.bmob.cn/bfc3efa954f1495f/signature',
//   data: {url: location.href},
//   dataType: 'jsonp',
//   jsonp: 'callback',
//   success: function (result) {
//		     init(result)
//   }
// });

function init(req) {
	//alert(weixiparm.appId);
	wx.config({
		debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId: weixiparm.appId, // 必填，公众号的唯一标识
		timestamp:req.timestamp, // 必填，生成签名的时间戳req.timestamp    weixiparm.timestamp
		nonceStr:req.nonceStr, // 必填，生成签名的随机串req.nonceStr       weixiparm.nonceStr
		signature:req.signature, // 必填，签名，见附录1req.signature		weixiparm.signature
		jsApiList: [
		            'checkJsApi',
		            'onMenuShareTimeline',
		            'onMenuShareAppMessage',
		            'onMenuShareQQ',
		            'onMenuShareWeibo',
		            'chooseImage',
		            'startRecord',
		            'stopRecord',
		            'playVoice',
		            'pauseVoice',
		            'stopVoice',
		            'onVoicePlayEnd',
		            'translateVoice',
		            'uploadVoice',
		            'uploadImage',
		            'previewImage'
	    ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});

	window.share_config = {
			"share": {
				"imgUrl": window.location.href + 'img/co.jpg', //分享图，默认当相对路径处理，所以使用绝对路径的的话，“http://”协议前缀必须在。
				"desc": '你对页面的描述', //摘要,如果分享到朋友圈的话，不显示摘要。
				"title": '分享卡片的标题', //分享卡片标题
				"link": window.location.href, //分享出去后的链接，这里可以将链接设置为另一个页面。
				"success": function () {
					//分享成功后的回调函数
				},
				'cancel': function () {
					// 用户取消分享后执行的回调函数
				}
			}
	};

	wx.ready(function () {
		// 获取“分享给朋友”按钮点击状态及自定义分享内容接口
		wx.onMenuShareAppMessage(share_config.share);//分享给好友
		wx.onMenuShareTimeline(share_config.share);//分享到朋友圈
		wx.onMenuShareQQ(share_config.share);//分享给手机QQ
	});

	wx.error(function (res) {
		alert(JSON.stringify(res))
	});
}

/* ----------------------- 微信 remove end   ----------------------------------*/