/**
 * Created by Toma on 2016/9/19.
 */
var cc = window.cc || {};
cc.programs = new function () {
  "use strict";
  var pro = this;

  // 初始化页面
  $.init();
  // 动画完成后 重置页面
  Vue.use(VueAnimatedList);
  Vue.transition('qlitem', {
    afterEnter: function () { $.refreshScroller(); }
  });

  // 写人浏览器缓存中
  var Util = (function () {
    var prefix = 'ZZK_'
    return {
      sionFetch: function (key) {
        return JSON.parse(sessionStorage.getItem(prefix + key) || '{}');
      },
      sionSave: function (key, val) {
        return sessionStorage.setItem(prefix + key, JSON.stringify(val));
      },
      // 地址写入时间戳，防止再次加载时引用缓存
      state: function (title) {
          var cate = location.search;
          var hash = location.hash;
          var tag = /\?/.test(cate) && !/\?ref_c=/.test(cate) ? '&' : '?';
          cate = cate.replace(/(\?|&)ref_c=\d+/, '');
          history.replaceState({tit: title}, title, cate + tag + 'ref_c=' + new Date().getTime() + hash);
      },
      createURL: function (blob) {
          if (window.URL) {
            return window.URL.createObjectURL(blob);
          } else if (window.webkitURL) {
            return window.webkitURL.createObjectURL(blob);
          } else {
            return null;
          }
      },
       f: function (a, g) {
          var g = g || 'y-m-d',
            date = new Date(a),
            y = date.getFullYear(),
            m = date.getMonth() + 1,
            d = date.getDate(),
            m = m < 10 ? '0' + m : m,
            d = d < 10 ? '0' + d : d;
          return g.replace(/y/, y).replace(/m/, m).replace(/d/, d);
        },
     // 转换对象
        parseParam: function (par) {
          var pp = function (param, k) {
            var v = '';
            for(var p in param){
              if(param[p] instanceof Object){
                v+= pp(param[p], k ? k + '[' + p + ']' : p);
              }else{
                v+= '&' + (k ? k + '[' + p + ']=' : p + '=') + param[p];
              }
            }
            return v;
          };
          return encodeURI(pp(par));
          //return '&info='+encodeURIComponent(JSON.stringify(par));
        },
        hashTabs: function () {
            $('.tab-link').on('click', function () {
              window.location.hash = this.href.replace(/^.*#/,'');
            });
            $(window).on('hashchange', function () {
              hashed()
            });
            function hashed() {
              var ha = location.hash.substr(1) || 'tab1'
              $('.page').addClass('page-current');
              $('.J_' + ha).trigger('click');
            }
            hashed();
        }
    }
  })();

  /**
   * ====================================================================
   * 问题列表
   * ====================================================================
   */
  pro.questionList = function (hotOne,zhiZhao) {
    var self = {};
    var queItem = pro.que_item(pro.model.que_item_m);

    // 读取session中的数据
    var stor = Util.sionFetch('LIST');
    var total = stor.total||0;
    // 检查用户是否从详情页退回列表页
    var loadin = true;
    var search = stor.search || "";
    var render = stor.render || "";
    var value = "";
    var vue = new Vue({
      el: 'body',
      data: {
        page: stor.page || 1,
        //searchPage: stor.searchPage || 1,
        problemList: stor.problemList || [],
        searchValue: stor.searchValue || "",
        age : stor.age || "",
        typeid : stor.typeid || "",
        hot : hotOne || stor.hot || "",
        searchFlag : stor.searchFlag || false
      },
      components: [queItem],
      methods: {
    	addProblemList: function (callback) {
            $('.clear_input').hide();
            $('.que_tag_list').hide();
            var _this = this;
            
            if(this.searchValue || this.age || this.typeid || this.hot){
                if(!this.searchFlag || this.searchValue != value){
                    this.searchFlag = true;
                    this.page = 1;
                    this.problemList = [];
                    $('.content').scrollTop(0);
                }
            }
            
            loadin && self.fetchData(this.page, function (data) {
                 self.renderData(data);
                 typeof callback == 'function' && callback();
            });
            value = this.searchValue;
        },
        saveStr: function (url) {
          Util.sionSave('LIST', {
        	listFlag:true,
            top: $('.content').scrollTop(),
            page: this.page,
            problemList: this.problemList,
            searchFlag:this.searchFlag,
            age:this.age,
            typeid:this.typeid,
            hot:this.hot,
            agetext: $('.searchbar .row div').eq(0).html(),
            typeidtext: $('.searchbar .row div').eq(1).html(),
            hottext: $('.searchbar .row div').eq(2).html,
            agechecked: $('.que_tag_list').eq(0).find('li.que_checked').length>0? $('.que_tag_list').eq(0).find('li.que_checked').data().age:"",
            idchecked: $('.que_tag_list').eq(1).find('li.que_checked').length>0? $('.que_tag_list').eq(1).find('li.que_checked').data().id:"",
            hotchecked: $('.que_tag_list').eq(2).find('li.que_checked').length>0? $('.que_tag_list').eq(2).find('li.que_checked').data().hot:"",
            search: search,
            render: render,
            searchValue: this.searchValue,
            total:total
          });
          Util.state('问题列表');
          window.location = url;
        }
      },
      events: {
          removecollect: function (index) {
            this.problemList.splice(index, 1);
          }
      }
    });

    self.data = {};
    self.data.info = io.userInfo.openId;
    self.url = io.questionList.content;
    self.data.age = "";
    self.data.typeId = "";
    self.data.hot = "";
    
    self.fetchData = function (page, callback) {
      loadin = false;
      self.data.page=page;
      self.data.age = vue.age;
      self.data.typeId = vue.typeid;
      self.data.hot = vue.hot;
      self.data.searchValue = vue.searchValue;
      self.data.zhiZhao = zhiZhao;
      $.ajax({
        url: self.url,
        type: 'post',
        data: self.data,
        dataType: 'json',
        beforeSend: function () {
          $('.J_que_list').append('<p class="preloader-line"><i class="preloader"></i></p>');
          $.refreshScroller();
        },
        success: function (data) {
          loadin = true;
          total = data.total;
          $('.preloader-line').remove();
          data.resultCode <= 1000 ? callback(data) : $.alert(data.message);
        },
        error: function () {
          loadin = true;
          $('.preloader-line').remove();
          $.alert('请求超时');
        }
      });
    };

    self.renderData = function (data) {
      vue.page++;
      data.problemList.forEach(function (ele) {
        vue.problemList.push(ele);
        vue.$nextTick(function () {
          $.refreshScroller();
          /// /////////////////////////
          //stor.top && $('.content').scrollTop(stor.top);
          Util.sionSave('LIST', {});
        });

      });
    };

    self.bindEvents = function () {
      // 上拉加载更多
      $(document).on('infinite', '.infinite-scroll-bottom', function(){
          if(!(vue.page>total)){
              vue.addProblemList();
          }
      });
      // 普通下拉刷新
      $(document).on('refresh', '.pull-to-refresh-content', function (e) {
        vue.page = 1;
        setTimeout(function () {
        	vue.problemList = [];
        	vue.addProblemList();
          $.pullToRefreshDone('.pull-to-refresh-content');
        }, 200)
      });
    };

    self.init = function () {
      self.bindEvents();
      vue.$nextTick(function () {
          $.refreshScroller();
          stor.top && $('.content').scrollTop(stor.top);
          Util.sionSave('ZHIZHAO', {});
          /////////////////////////////////////////////////
          Util.sionSave('LIST', {});
      });
      if (!stor.listFlag) {
          if(hotOne){
              $('.que_tag_list').eq(2).find('li').eq(0).addClass("que_checked").siblings().removeClass("que_checked");
              $('.searchbar .row div').eq(2).html("热门问题"+'<i class="iconfont icon-down"></i>')
          }
          vue.addProblemList();
      }
      
      //  搜索栏
      $('.clear_input').click(function(){
    	 $(this).hide();
        $('#search').val("");
      });
      $('#search').click(function(){
        $('.clear_input').show();
      })
      
      $('.que_tag_list li').click(function(){
        $(this).addClass("que_checked").siblings().removeClass("que_checked");
        $('.searchbar .row div').eq(0).html($('.que_tag_list').eq(0).find('li.que_checked').length>0 ? $('.que_tag_list').eq(0).find('li.que_checked').html()+'<i class="iconfont icon-down"></i>':$('.searchbar .row div').eq(0).html());
        if($('.que_tag_list').eq(0).find('li.que_checked').attr("data-age")==""){
            $('.searchbar .row div').eq(0).html("孩子年龄"+'<i class="iconfont icon-down"></i>')
        }
        $('.searchbar .row div').eq(1).html($('.que_tag_list').eq(1).find('li.que_checked').length>0 ? $('.que_tag_list').eq(1).find('li.que_checked').html()+'<i class="iconfont icon-down"></i>':$('.searchbar .row div').eq(1).html());
        if($('.que_tag_list').eq(1).find('li.que_checked').attr("data-id")==""){
            $('.searchbar .row div').eq(1).html("问题属性"+'<i class="iconfont icon-down"></i>')
        }
        $('.searchbar .row div').eq(2).html($('.que_tag_list').eq(2).find('li.que_checked').length>0 ? $('.que_tag_list').eq(2).find('li.que_checked').html()+'<i class="iconfont icon-down"></i>':$('.searchbar .row div').eq(2).html());
        if($('.que_tag_list').eq(2).find('li.que_checked').attr("data-hot")==""){
            $('.searchbar .row div').eq(2).html("最新问题"+'<i class="iconfont icon-down"></i>')
        }
        vue.age = $('.que_tag_list').eq(0).find('li.que_checked').length>0? $('.que_tag_list').eq(0).find('li.que_checked').data().age:"";
        vue.typeid = $('.que_tag_list').eq(1).find('li.que_checked').length>0? $('.que_tag_list').eq(1).find('li.que_checked').data().id:"";
        vue.hot = $('.que_tag_list').eq(2).find('li.que_checked').length>0? $('.que_tag_list').eq(2).find('li.que_checked').data().hot:"";
        vue.page = 1;
        vue.searchFlag = false;
        vue.addProblemList();

        $('.searchbar .row').find('i').removeClass('icon-up').addClass('icon-down');
        $(this).parent('ul').animate({ height: 0}, 300,function(){
            $('.searchbar .row').children().css({"color":"#5c5c59"});
            $('.searchbar .row').find('i').css({"color":"#5c5c59"});
        });
        $('.que_modal').remove()
      });

      $('#search').focus(function(){
          $('.que_tag_list').hide();
          $('.searchbar .row').find('i').removeClass('icon-up').addClass('icon-down');
          $('.que_tag_list').animate({ height: 0}, 300,function(){
              $('.searchbar .row').children().css({"color":"#5c5c59"});
              $('.searchbar .row').find('i').css({"color":"#5c5c59"});
          });
          $('.que_modal').remove();
      });
      
      //    后退时回填搜索栏
      if(stor.agetext){
          $('.searchbar .row div').eq(0).html(stor.agetext)
      }
      if (stor.typeidtext) {
          $('.searchbar .row div').eq(1).html(stor.typeidtext)
      }
      if (stor.hottext) {
          $('.searchbar .row div').eq(2).html(stor.hottext)
      }
      if (stor.agechecked) {
          for(var i = 0;i<$('.que_tag_list').eq(0).find('li').length;i++){
              if($('.que_tag_list').eq(0).find('li').eq(i).attr("data-age") == stor.agechecked){
                  $('.que_tag_list').eq(0).find('li').eq(i).addClass('que_checked');
              }
          }
      }
      if (stor.idchecked) {
          for(var i = 0;i<$('.que_tag_list').eq(1).find('li').length;i++){
              if($('.que_tag_list').eq(1).find('li').eq(i).attr("data-id") == stor.idchecked){
                  $('.que_tag_list').eq(1).find('li').eq(i).addClass('que_checked');
              }
          }
      }
      if (stor.hotchecked) {
          for(var i = 0;i<$('.que_tag_list').eq(2).find('li').length;i++){
              if($('.que_tag_list').eq(2).find('li').eq(i).attr("data-hot") == stor.hotchecked){
                  $('.que_tag_list').eq(2).find('li').eq(i).addClass('que_checked');
              }
          }
      }

    };
    
    
    $('.searchbar .row').children().each(function(i){
        $(this).click(function () {
            $('.clear_input').hide();
            var _this = $('.que_tag_list').eq(i);
            var height = 0;
            var n=1;
            if(i==0){
                n=3
            }else if(i==1){
                n=2
            }
            height =  Math.ceil(_this.find('li').length/n)*_this.find('li').eq(0).height();
            _this.siblings("ul").css({ height: 0});
            if(parseInt(_this.css("height"))>1){
                _this.animate({ height: 0}, 300);
                $('.que_modal').remove()
            }else{
                _this.animate({ height: height}, 300);
                if($('.que_modal').length<=0){
                    $('body').append('<div class="que_modal modal-overlay modal-overlay-visible"></div>')
                }
            }
            $('.searchbar .row').children().eq(i).css({"color":"#ff8116"}).siblings().css({"color":"#5c5c59"});
            $('.searchbar .row').find('i').css({"color":"#5c5c59"});
            $('.searchbar .row').children().eq(i).find('i').css({"color":"#ff8116"});

            if($('.searchbar .row').children().eq(i).find('i').hasClass('icon-up')){
                $('.searchbar .row').children().eq(i).find('i').toggleClass("icon-down").toggleClass('icon-up');
                $('.searchbar .row').children().eq(i).css({"color":"#5c5c59"});
                $('.searchbar .row').find('i').css({"color":"#5c5c59"});
            }else{
                $('.searchbar .row').find('i').removeClass('icon-up').addClass('icon-down');
                $('.searchbar .row').children().eq(i).find('i').toggleClass("icon-down").toggleClass('icon-up');
            }
            $('.que_modal').click(function(){
                $('.que_modal').remove();
                $('.searchbar .row').find('i').removeClass('icon-up').addClass('icon-down');
                $('.que_tag_list').animate({ height: 0}, 300,function(){
                    $('.searchbar .row').children().eq(i).css({"color":"#5c5c59"});
                    $('.searchbar .row').find('i').css({"color":"#5c5c59"});
                });
            })
        });
    });

    return self;
  };

  var wentiId;
  /**
   * ====================================================================
   * 问题详情
   * ====================================================================
   */
  pro.questionDetail = function (problemId) {
	wentiId=problemId;
    var self = {};
    var queListItem = pro.que_item(pro.model.que_item_m);
    var loadin = true;
    var total = 0;
    // 主数据
    self.urlContent = io.questionDetail.content;
    
    var vue = new Vue({
      el: 'body',
      data: {
        page: 1,
        item: {},
        answers: [],
        imglist: [],
        imgsever: [],
        maxlen: 6,
        ans:'',
        videoUrl: '',
        nimin: false,
        voice: {}
      },
      components: [queListItem],
      methods: {
        addProblem: function () {
          var _this = this;
          self.fetchData(self.urlContent, function (data) {
            _this.item = data.problem;
            _this.answers = data.answers;
          });
        },
        // 更多的回答
        addAnswers: function () {
          var _this = this;
          this.page++;
          loadin && self.fetchData(io.questionDetail.answers, function (data) {
            data.answers.forEach(function (ele) { _this.answers.push(ele) });
          });
        },
        addimg: function () {
        	// todo 图片上传接口
            var _this = this;
            var len = _this.maxlen - _this.imglist.length;
            wx.chooseImage({
              count: len, // 默认9
              sizeType: ['original', 'compressed'],
              sourceType: ['album', 'camera'],
              success: function (res) {
                var localIds = res.localIds;
                localIds.forEach(function (ele) {
                  _this.imglist.push(ele)
                });
                if (_this.imglist.length >= 6) $('.J_addimg').hide();
              }
            });
         }
      }
    });
    self.vue = vue;

    self.vue.$on('zjda',function () {
    	this.item.zjda =true;
    });
    self.fetchData = function (url, callback) {
      loadin = false;
      $.ajax({
        url: url,
        type: 'post',
        data: {
        	info: io.userInfo.openId, 
        	answerspage: vue.page,
        	problemId:problemId,
        	queId:vue.item.id
        },
        dataType: 'json',
        beforeSend: function () {
          $('.J_que_list').append('<p class="preloader-line"><i class="preloader"></i></p>');
          $.refreshScroller();
        },
        success: function (data) {
          loadin = true;
          total = data.total;
          $('.preloader-line').remove();
          data.resultCode <= 1000 ? callback(data) : $.alert(data.message);
        },
        error: function () {
          loadin = true;
          $('.preloader-line').remove();
          $.alert('请求超时');
        }
      });
    };

    self.bindEvents = function () {
      $(document).on('infinite', '.infinite-scroll-bottom',function(){
                  if(!(vue.page>total || vue.searchPage>total)){
                	  vue.addAnswers();
                  }
              
      });
      // 录音
	  pro.voice(function (v) {
        vue.voice = v;
      },function (v) {
        vue.voice = {};
      });
      
      $('#sub').on('click', self.submit);
    };
    
    // 提交验证
    self.submit = function () {
      if(!$.trim(vue.ans) && !vue.voice.localId){
        $.alert('请使用文字或语音回答');
        return;
      }
      if($.trim(vue.ans) && vue.ans.length < 20){
        $.alert('文字长度不能少于20字');
        return;
      }
      $(this).prop('disabled', true);

      // 如果有录音先上传录音
      var canuse = 1;
      if(vue.voice.localId){
    	wx.stopVoice({
    	      localId: vue.voice.localId
    	  });
    	$('.answer-voice').find('.J_paly_voice').removeClass('cc-audio-bg');
        canuse++;
        wx.uploadVoice({
          localId: vue.voice.localId,
          isShowProgressTips: 1, // 默认为1，显示进度提示
          success: function (res) {
            vue.voice.serverId = res.serverId;
            canuseFoo();
          }
        });
      }
      // 如果有图片
      var i = 0;
      if(vue.imglist.length){
        canuse++;
        uploadImg();
      }

      function uploadImg() {
        wx.uploadImage({
          localId: vue.imglist[i],
          isShowProgressTips: 1,
          success: function (res) {
            i++;
            vue.imgsever.push(res.serverId);
            if (i < vue.imglist.length) {
              uploadImg();
            }else{
              canuseFoo();
            }
          },
          fail: function (res) {
            alert(JSON.stringify(res));
          }
        });
      }

      function canuseFoo() {
        canuse--;
        if(canuse == 0){
          self._resolveData(function () {
            // 提交完成
            $.alert('提交成功');
          });
        }
      }

      canuseFoo();
    };
    
    self._resolveData = function (callback) {
    	var img=vue.imgsever;
		var imgs = "";
		for(var i=0;i<vue.imgsever.length;i++){
			imgs += vue.imgsever[i]+",";
		}
		imgs = imgs.substring(0,imgs.length-1);
		  
        $.ajax({
          url: io.questionDetail.submit,
          type: 'post',
          data: {
        	  info: io.userInfo.openId,
	          answer: vue.ans,
	          voice: vue.voice.serverId,
	          voicelen: vue.voice.len,
	          images: imgs,
	          nimin: vue.nimin,
	          videoUrl:vue.videoUrl,
	          problemId:vue.problemId
          },
          dataType: 'json',
          beforeSend: function () {
            $.showIndicator();
            $.refreshScroller();
          },
          success: function (data) {
            $.hideIndicator();
            if(data.resultCode <= 1000){
	        	window.location.href="../../wx/problem/problembyid.az?problemId="+vue.problemId;
	        }else if(data.resultCode==1003){
	        	$.alert(data.message);
	        }else{
	        	$.alert(data.message);
	        }
          },
          error: function () {
            $.hideIndicator();
            $.alert('请求超时');
          }
        });
    };

    self.init = function () {
      self.bindEvents();
      vue.addProblem();
    };

    return self;
  };
  
  /**
   * ====================================================================
   * 录音接口
   * ====================================================================
   */
  pro.voice = function (add, remove) {
	  var rainAllowRecord  = localStorage.getItem("rainAllowRecord");
	  if(!rainAllowRecord || rainAllowRecord != "true"){
	         wx.startRecord({
	           success: function(){
	               localStorage.setItem("rainAllowRecord","true");
	               wx.stopRecord();
	           },
	          cancel: function () {
	              alert('用户拒绝授权录音');
	          }
	         });
	   }
	    var ele = '';
	    var time = '';
	    var touch = false;
	    var record = false;
	    var voice = {
	      localId: '',
	      serverId: '',
	      len: 0
	    };
	    var events = {
	      touchstart: function (event) {
	        event.preventDefault();
	          if(!touch){
	            touch = true;
	              if(!record){
	                wx.startRecord({
	                  success: function () {
	                      record = true;
	                      ele = $('.answer-voice');
	                      ele.find('.J_paly_voice').show();
	                      ele.addClass('play');
	                      $('.J_time').html('0s');
	                      voice.len = 0;
	                      time = setInterval(function (){
	                          voice.len++;
	                          if(voice.len>=59){
	                              touch = false;
	                              clearInterval(time);
	                              wx.stopRecord({
	                                  success: function (res) {
	                                      record = false;
	                                      voice.localId = res.localId;
	                                      ele.removeClass('play').addClass('min');
	                                      add(voice);
	                                  },
	                                  fail: function (res) {
	                                      record = false;
	                                      ele.removeClass('play').removeClass('min');
	                                      ele.find('.J_paly_voice').hide().removeClass('cc-audio-bg');
	                                  }
	                              });
	                        }
	                        $('.J_time').html(voice.len + 's');
	                        if(voice.len>=59){
	                        	setTimeout(function(){
	                        		$('.J_time').html('60s');
	                        		voice.len = 60;
	                        	},1000);
	                        }
	                      }, 1000);
	                  },
	                  cancel: function () {
	                      alert('已拒绝授权录音');
	                  }
	                });
	              }
	          }
	        },
	   touchend: function (event) {
	        setTimeout(function(){
	              event.preventDefault();
	              if(touch){
	                  touch = false;
	                  if(record){
	                  wx.stopRecord({
	                      success: function (res) {
	                          record = false;
	                          voice.localId = res.localId;
	                          clearInterval(time);
	                          ele.removeClass('play').addClass('min');
	                          add(voice);
	                      },
	                      fail: function (res) {
	                          record = false;
	                          clearInterval(time);
	                          ele.removeClass('play').removeClass('min');
	                          ele.find('.J_paly_voice').hide().removeClass('cc-audio-bg');
	                      }
	                  });
	                  }
	              }
	          },1000);
	      },
	      touchcancel: function (event) {
	            event.preventDefault();
	            if(touch){
	                touch = false;
	                if(record){
	                wx.stopRecord({
	                    success: function (res) {
	                        record = false;
	                        voice.localId = res.localId;
	                        clearInterval(time);
	                        ele.removeClass('play').addClass('min');
	                        add(voice);
	                    },
	                    fail: function (res) {
	                        record = false;
	                        clearInterval(time);
	                        ele.removeClass('play').removeClass('min');
	                        ele.find('.J_paly_voice').hide().removeClass('cc-audio-bg');
	                    }
	                });
	                }
	            }
	    }
	  };
    $(document).on(events, '.answer-voice a');

    // 播放
    var playFlag = true;
    $(document).on('click','.J_paly_voice', function () {
        if(playFlag){
            wx.playVoice({
                localId: voice.localId,
            });
            ele.find('.J_paly_voice').addClass('cc-audio-bg');
            playFlag = false;
        }else{
            wx.pauseVoice({
                localId:voice.localId,
            });
            ele.find('.J_paly_voice').removeClass('cc-audio-bg');
            playFlag = true;
        }
        wx.onVoicePlayEnd({
            success: function (res) {
                ele.find('.J_paly_voice').removeClass('cc-audio-bg');
                playFlag = true;
            }
        });
    });

    // 移除
    $(document).on('click','.J_del_voice', function (e) {
      wx.stopVoice({
        localId: voice.localId
      });
      voice.len = 0;
      ele.removeClass('min');
      $('.J_time').html('0s');
      ele.find('.J_paly_voice').hide().removeClass('cc-audio-bg');
      remove(voice);
    });
  };
  
  /**
   * ====================================================================
   * 评论回答 & 发布问题
   * ====================================================================
   */
  pro.issue = function () {
	  if(!localStorage.rainAllowRecord || localStorage.rainAllowRecord !== 'true'){
          wx.startRecord({
              success: function(){
                  localStorage.rainAllowRecord = 'true';
                  wx.stopRecord();
              },
              cancel: function () {
                  alert('用户拒绝授权录音');
              }
          });
      }
	  var self = {};
	  var vue=new Vue({
	      el: '#formZhizhao',
	      data:{
	        imglist:[],
	        imgsever: [],
	        maxlen: 6,
	        ans:'',
	        tag: [],
	        nimin: false,
	        voice: {},
	        offerMoney:0,
            age:""
	      },
	      methods:{
	        addimg: function (e) {
	        	var _this = this;
	            var len = _this.maxlen - _this.imglist.length;
	            wx.chooseImage({
	               count: len, // 默认9
	               sizeType: ['original', 'compressed'],
	               sourceType: ['album', 'camera'],
	               success: function (res) {
	                 var localIds = res.localIds;
	                 localIds.forEach(function (ele) {
	                   _this.imglist.push(ele)
	                 });
	                 if (_this.imglist.length >= 6) $('.J_addimg').hide();
	               }
	            });
	        }
	      }
	  });

	  self.bindEvents = function () {
	      $('.tag-showall').on('click',function () {
	        $('.tag-list').toggleClass('showall');
	      });
	      // 录音
	      pro.voice(function (v) {
	        vue.voice = v;
	      },function (v) {
	        vue.voice = {};
	      });
	      $('#sub').on('click', self.submit);
	  };

	  // 提交验证
	  self.submit = function () {
		  if ($("#agePicker").val()=="") {
              $.alert('请选择您孩子的年龄');
              return;
          }
		  if(!vue.tag.length){
		        $.alert('请添加问题标签');
		        return;
		  }
	      if($.trim(vue.ans)==''){
	        $.alert('问题描述不能为空');
	        return;
	      }
	      if(vue.ans.length < 20){
	        $.alert('文字长度不能少于20字');
	        return;
	      }
	      
	      //$(this).prop('disabled', true);

	      // 如果有录音先上传录音
	      var canuse = 1;
	      if(vue.voice.localId){
	    	wx.stopVoice({
	    	      localId: vue.voice.localId
	    	  });
	    	$('.answer-voice').find('.J_paly_voice').removeClass('cc-audio-bg');
	        canuse++;
	        wx.uploadVoice({
	          localId: vue.voice.localId,
	          isShowProgressTips: 1, // 默认为1，显示进度提示
	          success: function (res) {
	            vue.voice.serverId = res.serverId;
	            canuseFoo();
	          }
	        });
	      }
	      // 如果有图片
	      var i = 0;
	      if(vue.imglist.length){
	        canuse++;
	        uploadImg();
	      }

	      function uploadImg() {
	        wx.uploadImage({
	          localId: vue.imglist[i],
	          isShowProgressTips: 1,
	          success: function (res) {
	            i++;
	            vue.imgsever.push(res.serverId);
	            if (i < vue.imglist.length) {
	              uploadImg();
	            }else{
	              canuseFoo();
	            }
	          },
	          fail: function (res) {
	            alert(JSON.stringify(res));
	          }
	        });
	      }
	      self.submitFlag = false;
	      function canuseFoo() {
	        canuse--;
	        if(canuse == 0){
	        	if(self.submitFlag) return;
	        	self._resolveData();
	        }
	      }

	      canuseFoo();
	  };
	
	  self._resolveData = function (callback) {
		  $.confirm('悬赏金额:<input type="number" class="fz-m" name="offerMoney" id="offerMoney" v-model="offerMoney" placeholder="请输入正整数金额,如:1元"/>', '<i class="logo-ico"></i>', function () {
			 
			  if($("#offerMoney").val()==''){
				  $.alert("请输入金额");
				  self._resolveData();
				  
			  }else{
				  if(!/^[0-9]*$/.test($("#offerMoney").val())){  
				        $.alert("请输入正整数!");  
				        self._resolveData();
				  } else{
					  vue.offerMoney=$("#offerMoney").val();
					  var tag=vue.tag;
					  var img=vue.imgsever;
					  var strs = "";
					  var imgs = "";
					  for(var i=0;i<vue.tag.length;i++){
						  strs += vue.tag[i]+",";
					  }
					  for(var i=0;i<vue.imgsever.length;i++){
						  imgs += vue.imgsever[i]+",";
					  }
					  strs = strs.substring(0,strs.length-1);
					  imgs = imgs.substring(0,imgs.length-1);
					  self.submitFlag = true;
					  $.ajax({
						  url: io.quiz.content,
						  type: 'POST',
						  data: {
							  info: io.userInfo.openId,
							  answer: vue.ans,
							  voice: vue.voice.serverId,
							  voicelen: vue.voice.len,
							  images: imgs,
							  tag: strs,
							  nimin: vue.nimin,
							  offerMoney:vue.offerMoney,
							  age:parseInt(vue.age)+""
						  },
						  dataType: 'json',
						  beforeSend: function () {
							  $.showIndicator();
							  $.refreshScroller();
						  },
						  success: function (data) {
			        	
							  $.hideIndicator();
							  if(data.resultCode <= 1000){
								  //$.alert(data.order.orderType);
								  //调用 /pay/getPrepayId  1.传totalFee=vue.offerMoney  ；2.orderSn ； 3.变量payType="1"  0:购买专家时间，1：提问；2：偷听
								  window.location.href="../../pay/getPrepayId?totalFee="+vue.offerMoney+"&orderSn="+data.order.orderSn+"&payType="+data.order.orderType;
								  //window.location.href="../../wx/problem/problemList.az";
							  }else if(data.resultCode==1004){
								  $.alert(data.message);
							  }else{
								  $.alert(data.message);
							  }
						  },
						  error: function () {
							  $.hideIndicator();
							  $.alert('请求超时');
						  }
					  });
				  }
			  }
			  
		  });
	   };
	    
	   self.init = function () {
	      self.bindEvents();
	   };

	   //孩子年龄picker
       $("#agePicker").picker({
           toolbarTemplate: '<header class="bar bar-nav"><button class="button button-link pull-left"></button><button class="button button-link pull-right close-picker">确定</button><h1 class="title">请选择年龄</h1></header>',
           cols: [
               {
                   textAlign: 'center',
                   values: ['1岁','2岁','3岁','4岁','5岁','6岁','7岁','8岁','9岁','10岁','11岁','12岁','13岁','14岁','15岁','16岁','17岁','18岁']
               }
           ]
       });
       
	    self.init();
  };

  /**
   * ====================================================================
   * 专家支招
   * ====================================================================
   */
  pro.specialist = function () {
	    var self = {};
	    var chooseDate = pro.chooserDate();
	    var timesAll = function () {
	      var arr = {};
	      var MINT = 8;
	      var MAXT = 20;
	      for (var i = MINT; i <= MAXT; i++) {
	        arr[i + ':00-' + (i + 1) + ':00'] = {check: false, value: '', txt: ''};
	      }
	      return arr;
	    };

	    self.timeUrl = io.specialist.times;
	    var vue = new Vue({
	      el: 'body',
	      component: ['chooseDate'],
	      data: {
	        times: timesAll(),
	        date: Util.f(new Date()),
	        ans: '',
	        tonyi: true
	      },
	      methods:{
	        timesChange: function () {
	          var _this = this;
	          self.fetchData(self.timeUrl, {
	            date: _this.date,
	            info: io.userInfo.expertId
	          },function (data) {
	            $.extend(_this.times, data.times);
	          });
	        }
	      },
	      events: {
	        "date": function (de) {
	          this.date = de;
	          this.times = timesAll();
	          this.timesChange();
	        }
	      }
	    });

	    self.fetchData = function (url, data, callback) {
	      $.ajax({
	        url: url,
	        type: 'get',
	        data: data,
	        dataType: 'json',
	        beforeSend: function () {
	          $.showIndicator();
	          $.refreshScroller();
	        },
	        success: function (data) {
	          $.hideIndicator();
	          data.resultCode <= 1000 ? callback(data) : $.alert(data.message);
	        },
	        error: function () {
	          $.hideIndicator();
	          $.alert('请求超时');
	        }
	      });
	    };

	    var typeFlag = false;
	    self.bindEvents = function () {
	      $('.cho-date').calendar({
	        onClose:function () {
	          $('.picker-calendar').remove();
	        }
	      });
	      // 提交数据
	      $('#sub').on('click',function () {
	        var tr = 0;
	        $('.cho-item input').each(function (i, ele) {
	          var val = $(ele).val();
	          val && val !='on' && tr++;
	        });
	        if(!tr){
	          $.alert('请选择预约时间');
	          return;
	        }
	        if (!typeFlag) {
                $.alert('请选择咨询方式');
                return;
            }
	        if (!$('.zx_info').eq(0).val()) {
                $.alert('请选择孩子的年龄');
                return;
            }
	        if (!$('.zx_info').eq(1).val()) {
                $.alert('请填写您孩子的性别');
                return;
            }
	        if (!$('.zx_info').eq(2).val()) {
                $.alert('请填写您孩子的所在地');
                return;
            }
	        if(!vue.ans){
	          $.alert('请填写咨询内容');
	          return;
	        }
	        if(!vue.tonyi){
	          $.alert('请同意咨询协议');
	          return;
	        }
	        var formData = $('#formContent').serialize();
	        formData = formData + Util.parseParam({
	            info: io.userInfo.expertId,
	            date: vue.date
	          });
	        self.subFetchData(io.specialist.form, formData, function (data) {
	          location.href = 'specialist-pay.html';
	        });
	      });
	    };
	    //提交预约信息
	    self.subFetchData = function (url, data, callback) {
	      $.ajax({
	        url: url,
	        type: 'post',
	        data: data,
	        dataType: 'json',
	        beforeSend: function () {
	          $.showIndicator();
	          $.refreshScroller();
	        },
	        success: function (data) {
	          $.hideIndicator();
	          if(data.msg <= 1000){
	        	  var orderSn = data.orderSn;
	        	  var price = data.sumPrice;
	        	  window.location.href="../../pay/getPrepayId?totalFee="+price+"&orderSn="+orderSn;
	          }else{
	        	  $.alert("操作失败");
	          }
	        },
	        error: function () {
	          $.hideIndicator();
	          $.alert('请求超时');
	        }
	      });
	    };
	    
	    self.init = function () {
	      vue.timesChange();
	      self.bindEvents();
	    };
//	    选择预约方式
	    $("form .spe-item").click(function(){
            typeFlag = true;
            $(this).css("color","#fff").find('.spe-inner.spe-bg').css("background","#ff8116").find('span').css("color","#fff !important");
            $(this).siblings().css("color","#3d4145;").find('.spe-inner.spe-bg').css("background","#fff").find('span').css("color","#ff8116 !important");
            var type = "";
            switch ($(this).find('h4').text()) {
                case "电话咨询":
                    type = "phone";
                    break;
                case "视频咨询":
                    type = "video";
                    break;
                case "面对面咨询":
                    type = "toface";
                    break;
            }
           $(".cho-con.row").find('input').val(type)
        });
	    //孩子年龄
	    $("#agePicker").picker({
	           toolbarTemplate: '<header class="bar bar-nav"><button class="button button-link pull-left"></button><button class="button button-link pull-right close-picker">确定</button><h1 class="title">请选择年龄</h1></header>',
	           cols: [
	               {
	                   textAlign: 'center',
	                   values: ['1岁','2岁','3岁','4岁','5岁','6岁','7岁','8岁','9岁','10岁','11岁','12岁','13岁','14岁','15岁','16岁','17岁','18岁']
	               }
	           ]
	       });
	    self.init();
	  };
	  /**
	   * ====================================================================
	   * 个人中心-我的时间
	   * ====================================================================
	   */
	  pro.myTime = function () {
		  var self = {};
		    var chooseDate = pro.chooserDate();
		    var timesAll = function () {
		      var arr = {};
		      var MINT = 8;
		      var MAXT = 20;
		      for (var i = MINT; i <= MAXT; i++) {
		        arr[i + ':00-' + (i + 1) + ':00'] = {check: false, value: '', txt: ''};
		      }
		      return arr;
		    };

		    self.timeUrl = io.specialist.times;
		    self.vue = new Vue({
		      el: 'body',
		      component: ['chooseDate'],
		      data: {
		        times: timesAll(),
		        date: Util.f(new Date()),
		        ans: '',
		        tonyi: true
		      },
		      methods:{
		        timesChange: function () {
		          var _this = this;
		          self.fetchData(self.timeUrl, {
		            date: _this.date,
		            info: io.userInfo.openId
		          },function (data) {
		            $.extend(_this.times, data.times);
		          });
		        }
		      },
		      events: {
		        "date": function (de) {
		          this.date = de;
		          this.times = timesAll();
		          this.timesChange();
		        }
		      }
		    });

		    self.fetchData = function (url, data, callback) {
		      $.ajax({
		        url: url,
		        type: 'get',
		        data: data,
		        dataType: 'json',
		        beforeSend: function () {
		          $.showIndicator();
		          $.refreshScroller();
		        },
		        success: function (data) {
		          $.hideIndicator();
		          data.resultCode <= 1000 ? callback(data) : $.alert(data.message);
		        },
		        error: function () {
		          $.hideIndicator();
		          $.alert('请求超时');
		        }
		      });
		    };

		    self.bindEvents = function () {
		      $('.cho-date').calendar({
		        onClose:function () {
		          $('.picker-calendar').remove();
		        }
		      });
		      // 提交数据
		      $('#sub').on('click',function () {
		        var tr = 0;
		        $('.cho-item input').each(function (i, ele) {
		          var val = $(ele).val();
		          val && val !='on' && tr++;
		        });
		        if(!tr){
		          $.alert('请选择预约时间');
		          return;
		        }
		        if(!$.trim(self.vue.ans)){
		          $.alert('请填写咨询内容');
		          return;
		        }
		        if(!self.vue.tonyi){
		          $.alert('请同意咨询协议');
		          return;
		        }
		        var formData = $('#formContent').serialize();
		        formData = formData + Util.parseParam({
		            info: io.userInfo.openId,
		            date: self.vue.date
		          });
		        self.fetchData(io.specialist.form, formData, function (data) {
		          // location.href = 'specialist-pay.html';
		        });
		      });
		    };

		    self.init = function () {
		      self.vue.timesChange();
		      self.bindEvents();
		    };

		    return self;
	  };
  /**
   * ====================================================================
   * 我的问题
   * ====================================================================
   */
  pro.userQuestion = function () {
    var list = pro.questionList();
    list.url = io.userQuestionList.content;
    list.data.userQuestion = true;
    list.init();
  };
  var myproblemId="";
  
  //我的问题详情
  pro.userQuestionDetail = function (problemId) {
	myproblemId=problemId;
    var detail = pro.questionDetail(problemId);
    detail.urlContent = io.userQuestionDetail.content;
    // 监听用户设置最佳答案
    detail.vue.$on('zjda',function () {
    	this.item.zjda =true;
    });
    // 追问
    detail.vue.$on('tapAsked', function (que) {
      if(que && que.length < 20){
        alert('追加问题不能小于20字');
        return;
      }
      
      if(!que && !detail.vue.voice.localId){
        alert('请用文字或者语音追问~');
        return;
      }
      if (detail.vue.voice.localId) {
          wx.uploadVoice({
            localId: detail.vue.voice.localId,
            isShowProgressTips: 0, // 默认为1，显示进度提示
            success: function (res) {
              detail.vue.voice.serverId = res.serverId;
            }
          });
      }

      
      $.closeModal();
      moneyC();
      function moneyC(){
      $.confirm('悬赏金额:<input type="number" class="fz-m" name="money" id="money" placeholder="请输入正整数金额,如:1元"/>', '<i class="logo-ico"></i>', function () {
    	  if($("#money").val()==''){
			  $.alert("请输入金额");
			  moneyC();
		  }else{
			  if(!/^[0-9]*$/.test($("#money").val())){  
			      $.alert("请输入正整数!");  
			      moneyC();
			  } else{
				  var money=$("#money").val();
				  $.ajax({
					  url: io.userQuestionDetail.asked,
					  type: 'post',
					  data: {
						  info: io.userInfo.openId,
						  que: que,
						  id: detail.vue.item.id,
						  voice: detail.vue.voice,
						  voicelen: detail.vue.voice.len,
						  problemId:myproblemId,
						  money:money
					  },
					  dataType: 'json',
					  beforeSend: function () {
						  $.showIndicator();
						  $.refreshScroller();
					  },
					  success: function (data) {
						  $.hideIndicator();
						  if(data.resultCode <= 1000){
							  //$.closeModal();
							  //alert(data.order.orderType);
							  window.location.href="../../pay/getPrepayId?totalFee="+money+"&orderSn="+data.order.orderSn+"&payType="+data.order.orderType+"&problemId="+myproblemId;
							  // todo 显示追问结果？
							  //location.reload();
						  }else{
							  $.alert(data.message);
						  }
					  },
					  error: function () {
						  $.hideIndicator();
						  $.alert('请求超时');
					  }
				  });
			  }
			  
		  }
	  });
		  
      }
    });

    detail.init();
  };

  /**
   * ====================================================================
   * 我的支招
   * ====================================================================
   */
  pro.userZhizhao = function () {
	  
	  var self = {};
	  var queAns = pro.queAns();
	  var vue;
	  
	  self.loadin=true;
	  // 读取session中的数据
	  var store = Util.sionFetch('ZHIZHAO');
	  var total=store.total||0;
	  self.vue = function () {
	      return new Vue({
	        el: 'body',
	        data: {
	          page: store.page || 1,
	          listBest: store.listBest || [],
	          listLuck: store.listLuck || [],
	          listListen: store.listListen || [],
	          listOther: store.listOther || []
	        },
	        components: [queAns],
	        methods: {
	          addProblemList: function () {
	            var _this = this;
	            self.fetchData(this.page, function (data) {
	              //_this.listBest = data.list_best;
	              //_this.listLuck = data.list_luck;
	              //_this.listListen = data.list_listen;
	              //_this.listOther = data.list_other;
	            	data.list_best.forEach(function(ele){
	                    _this.listBest.push(ele);
	                });
	                data.list_luck.forEach(function (ele) {
	                    _this.listLuck.push(ele);
	                });
	                data.list_listen.forEach(function (ele) {
	                    _this.listListen.push(ele);
	                });
	                data.list_other.forEach(function (ele) {
	                    _this.listOther.push(ele);
	                });
	                _this.page++;
	              _this.$nextTick(function () {
	                $.refreshScroller();
	                store.top && $('.content').scrollTop(store.top);
	                Util.sionSave('ZHIZHAO', {});
	              });
	            });
	          }
	        },
	        events: {
	          linked: function (url) {
	            Util.sionSave('ZHIZHAO', {
	              listFlag:true,
	              top: $('.content').scrollTop(),
	              page: this.page,
	              listBest: this.listBest,
	              listLuck: this.listLuck,
	              listOther: this.listOther,
	              listListen: this.listListen,
	              total:total
	            });
	            Util.state('我的支招');
	            window.location.href = url;
	          }
	       }
	     });
	   };
	   
	   self.url = io.userZhizhao.content;
	   self.data = {};
	   self.fetchData = function (page, callback) {
		  self.loadin=false;
	      self.data.info = io.userInfo.openId;
	      self.data.page = page;
	      $.ajax({
	        url: self.url,
	        type: 'post',
	        data: self.data,
	        dataType: 'json',
	        beforeSend: function () {
	          $('.J_que_list').append('<p class="preloader-line"><i class="preloader"></i></p>');
	          $.refreshScroller();
	        },
	        success: function (data) {
	          total=data.total;
	          $('.preloader-line').remove();
	          data.resultCode <= 1000 ? callback(data) : $.alert(data.message);
	        },
	        error: function () {
	          $('.preloader-line').remove();
	          $.alert('请求超时');
	        }
	     });
	   };
	   
	   self.bindEvents = function () {
		 Util.hashTabs();
		 // 上拉加载更多
	     $(document).on('infinite', '.infinite-scroll-bottom', function(){
	          if(!(vue.page>total)){
	              vue.addProblemList();
	          }
	      });
	     // 普通下拉刷新
	     $(document).on('refresh', '.pull-to-refresh-content', function (e) {
	        vue.page = 1;
	        setTimeout(function () {
	          location.reload();
	          $.pullToRefreshDone('.pull-to-refresh-content');
	        }, 200)
	     });
	   };

	   self.init = function () {
		 vue = self.vue()
		 //vue.addProblemList();
		 vue.$nextTick(function () {
		     $.refreshScroller();
		     store.top && $('.content').scrollTop(store.top);
		     Util.sionSave('ZHIZHAO', {});
		 });
		 if(!store.listFlag){
		     vue.addProblemList();
		 }
		 self.bindEvents();
	  };

	  return self;
  };
  
  /**
   * ====================================================================
   * 我的偷听
   * ====================================================================
   */
  pro.userListen = function () {
	  
    var queAns = pro.queAns();
    //var zhi = pro.userZhizhao()
    var zhi={};
    var store = Util.sionFetch('LISTEN');
    var total=store.total||0;
    var vue;
    
    //zhi.url = io.userListen.content;
    zhi.vue = function () {
      return new Vue({
        el: 'body',
        data: {
          page: store.page || 1,
          listMine: store.listMine || [],
          listLuck: store.listLuck || [],
        },
        components: [queAns],
        methods: {
          addProblemList: function () {
            var _this = this;
            //zhi.fetchData(this.page, function (data) {
            zhi.loadin && zhi.fetchData(this.page, function (data) {
             // _this.listMine = data.list_minelisten;
             // _this.listLuck = data.list_lucklisten;
            	data.list_minelisten.forEach(function(ele){
                    _this.listMine.push(ele);
                  });
                  data.list_lucklisten.forEach(function (ele) {
                    _this.listLuck.push(ele);
                  });
                  _this.page++;
              _this.$nextTick(function () {
                $.refreshScroller();
                store.top && $('.content').scrollTop(store.top);
                Util.sionSave('LISTEN', {});
              });
            });
          }
        },
        events: {
          linked: function (url) {
            Util.sionSave('LISTEN', {
              top: $('.content').scrollTop(),
              page: this.page,
              listMine: this.listMine,
              listLuck: this.listLuck,
              total:total
            });
            Util.state('我的支招');
            window.location.href = url;
          }
        }
      });
    };
    
    
    //zhi.init();
    zhi.loadin = true;
    zhi.url = io.userListen.content;
    zhi.data = {};
    zhi.fetchData = function (page, callback) {
      zhi.loadin = false;
      zhi.data.info = io.userInfo.openId;
      zhi.data.page = page;
      $.ajax({
        url: zhi.url,
        type: 'post',
        data: zhi.data,
        dataType: 'json',
        beforeSend: function () {
          $('.J_que_list').append('<p class="preloader-line"><i class="preloader"></i></p>');
          $.refreshScroller();
        },
        success: function (data) {
        	total=data.total;
          zhi.loadin = true;
          $('.preloader-line').remove();
          data.resultCode <= 1000 ? callback(data) : $.alert(data.message);
        },
        error: function () {
          zhi.loadin = true;
          $('.preloader-line').remove();
          $.alert('请求超时');
        }
      });
    };

    zhi.bindEvents = function () {
      Util.hashTabs();
      // 上拉加载更多
      $(document).on('infinite', '.infinite-scroll-bottom', function(){
    	  if(!(vue.page>total || vue.searchPage>total)){
              vue.addProblemList();
          }
      });
      // 普通下拉刷新
      $(document).on('refresh', '.pull-to-refresh-content', function (e) {
        vue.page = 0;
        setTimeout(function () {
          location.reload();
          $.pullToRefreshDone('.pull-to-refresh-content');
        }, 200)
      });
    };
    zhi.init = function () {
      vue = zhi.vue();
      vue.$nextTick(function () {
        $.refreshScroller();
        store.top && $('.content').scrollTop(store.top);
        Util.sionSave('ZHIZHAO', {});
      });
      if(!store.listFlag){
        vue.addProblemList();
      }
      zhi.bindEvents();
    };
    return zhi;
  };
  
  /**
   * ====================================================================
   * 我的时间
   * ====================================================================
   */
  pro.sendDate="";
  pro.userTimes = function () {
    var spe = pro.myTime();
    spe.timeUrl = io.specialist.times;
    spe.init();
    pro.sendDate = function(){
    
	$('.content-inner.J_que_list').html();
	 $.ajax({
        url: "../../wx/expert/userTimePage.az",
        type: 'post',
        data: {"date":spe.vue.date},
        dataType: 'json',
        success: function (data) {
        	//console.log(data);
        	var html = "";
        	var makemsg = data.makeMsg;
        	for(var i = 0;i<data.makeMsg.length;i++){
        		var openid = makemsg[i].userOpenid; 
        		var ordersn = makemsg[i].order_sn;
        		html +='<div class="cc-u-item" data-openid ="'+ openid +'" data-ordersn="'+ ordersn +'" onclick="getMessage($(this))">' /*\""'+ makemsg[i].openid +'"\",\""'+ makemsg[i].order_sn +'"\"
*/                    +'<div class="cc-u-il">'
                        +'<a href="javascript:" class="user-head-min" style="background-image: url(' + makemsg[0].headimgurl + ')"></a>'
                        +'<p>'
							+ makemsg[i].expertNickname 								   	
						+'</p>'
                    +'</div>'
                    +'<div class="cc-u-ir">'
                        +'<h4 class="cc-u-inhead">' + makemsg[i].s_time  + '</h4>'
                        +'<div class="cc-u-inbody">'
                            +'<span>' + makemsg[i].sumPrice  + '<small>元</small></span>'
                           + makemsg[i].typeName 
                        +'</div>'
                    +'</div>'
                +'</div>'
        	}
        	$('.content-inner.J_que_list').append(html);
        },
        error: function () {
        	$.alert('请求超时');
	    }
	  });
	 
    }
   
  };
  
  /**
   * ====================================================================
   * 我的收藏
   * ====================================================================
   */
  pro.userCollect = function () {
    var self = {};
    var list = pro.questionList();
    list.url = io.user.userCollect;
    list.data.userCollect = true;
    list.init();
  };
  
  /**
   * ====================================================================
   * 我的预约
   * ====================================================================
   */
  pro.userSubscribe = function () {
	
	/*window.onpopstate=function () {
      window.location.reload();
    };*/
	
    var self = {};
    var stor = Util.sionFetch('YUYUE');
    var total=stor.total||0;
    var loading = true;
    self.data = {};
    var userSubscribe = Vue.component('user-subscribe', {
        template: pro.model.subscribe_m,
        props: ['item','page','makemsg'],
        methods: {
            del: function (e) {
                var box = $(e.currentTarget).parents('.J_box');
                var id = box.data('id');
                $.confirm('确定要取消该订单吗？', function () {
                    self._resolveData({
                        info: io.userInfo,
                        indentId: id,
                        type: 'remove'
                    }, function () {
                        box.remove();
                    });
                });
            },
            saveStr: function (page,makemsg,url) {
                Util.sionSave('YUYUE', {
                    top: $('.content').scrollTop(),
                    page: page,
                    makeMsg: makemsg,
                    makeMsgFlag:true,
                    total:total
                });
                Util.state('我的预约');
                window.location = url;
            }
           
        }
    });
    var vue = new Vue({
        el: 'body',
        data: {
            page: stor.page || 1,
            makeMsg: stor.makeMsg || []
        },
        components: [userSubscribe],
        methods: {
            addMakeMsg: function () {
                loading && self.fetchData(this.page, function (data) {
                    self.renderData(data);
                });
            }
        }
    });

    self.fetchData = function (page, callback) {
        loading = false;
        self.data.page = page;
        $.ajax({
            url: io.userSubscribe.orderMsg,
            type: 'get',
            data: self.data,
            dataType: 'json',
            beforeSend: function () {
                $('.J_que_list').append('<p class="preloader-line"><i class="preloader"></i></p>');
                $.refreshScroller();
            },
            success: function (data) {
                loading = true;
                total = data.totalPageNum;
                $('.preloader-line').remove();
                callback(data);
            },
            error: function () {
                loading = true;
                $('.preloader-line').remove();
                $.alert('请求超时');
            }
        });
    };

    self.renderData = function (data) {
        vue.page++;
        data.makeMsg.forEach(function (ele) {
            vue.makeMsg.push(ele);
            vue.$nextTick(function () {
                $.refreshScroller();
                Util.sionSave('YUYUE', {});
            });

        });
    };
    
    self._resolveData = function (data, callback) {
      data.info = io.userInfo.openId;
      $.ajax({
        url: io.userSubscribe.content,
        type: 'get',
        data: data,
        dataType: 'json',
        beforeSend: function () {
          $.refreshScroller();
        },
        success: function (data) {
          data.resultCode <= 1000 ? callback(data) : $.alert(data.message);
        },
        error: function () {
          $.alert('请求超时');
        }
      });
    };

    self.bindEvents = function () {
      Util.hashTabs();
    //滚动刷新
      $(document).on('infinite', '.infinite-scroll-bottom', function(){
          if(vue.page<=total){
              vue.addMakeMsg();
          }
      });
      // 普通下拉刷新
      $(document).on('refresh', '.pull-to-refresh-content', function (e) {
          vue.page = 1;
          vue.makeMsg = [];
          vue.addMakeMsg();
          $.pullToRefreshDone('.pull-to-refresh-content');

      });
    };

    self.start = function () {
      var size = $('.start-box').data('size');
      function resizeStart(z) {
        $('.start-box').data('size', z);
        for(var i = 1; i<= 5; i++){
          var start = $('.start-box i[data-no="'+i+'"]');
          if(i <= z){
            start.removeClass('icon-favor').addClass('icon-biaoxingfill');
          }else{
            start.removeClass('icon-biaoxingfill').addClass('icon-favor');
          }
        }
      }
      resizeStart(size);
      $(document).on('click','.start-box i',function (e) {
        resizeStart($(e.target).data('no'));
      });
      $(document).on('click','#sub', self.submit);
    };

    self.submit = function () {
      var ans = $.trim($('#ans').val());
      var start = $('#start').data('size');
      if(!ans){
        $.alert('评价描述不能为空');
        return;
      }
      self.sub({
        ans: ans,
        start: start,
        info: io.userInfo.makeOrderId
      },function () {
        $.alert('提交成功');
        setTimeout(function(){
        	window.location.href = "../../wx/expert/mySubscribe.az";
        },1000)
      });
    };

    self.sub = function (data, callback) {
      $.ajax({
        url: io.userSubscribe.evaluate,
        type: 'post',
        data: data,
        dataType: 'json',
        beforeSend: function () {
          $.refreshScroller();
        },
        success: function (data) {
          data.resultCode <= 1000 ? callback(data) : $.alert(data.message);
        },
        error: function () {
          $.alert('请求超时');
        }
      });
    };

    self.init = function () {
	  self.bindEvents();
      vue.$nextTick(function () {
          $.refreshScroller();
          stor.top && $('.content').scrollTop(stor.top);
          Util.sionSave('YUYUE', {});
      });
      if(!stor.makeMsgFlag){
          vue.addMakeMsg();
      }
    };

    return self;
  };
  
    
  /**
   * ====================================================================
   * 我的积分/收益
   * ====================================================================
   */
  pro.userMine = function (type,openid,total) {
    var addItems,url;
    if(type=="jifen"){
      url="../../wx/integral/getintegralpage.az";
      addItems = function(data) {
        var html = '';
        for (var i = 0; i < data.list.length; i++) {
          html += '<li class="item-content"><div class="item-inner"><div class="item-title"><span>'+data.list[i].getApproach+'</span><p>'+data.list[i].createTimeString+'</p></div><div class="item-after theme">+'+data.list[i].integral+'积分</div></div></li>';
        }
        $('.infinite-scroll-bottom .list-container').append(html);
      }
    }else if(type=="shouyi"){
      url="../../wx/account/getaccountpage.az";
      addItems = function(data) {
        var html = '';
        var title;
        for (var i = 0; i < data.aList.length; i++) {
          switch(data.aList[i].getApproach)
          {
            case "01":
              title = "偷听答案";
              break;
            case "02":
              title = "最佳支招";
              break;
            case "03":
              title = "参与红包";
              break;
          }
          html += '<li class="item-content"><div class="item-inner"><div class="item-title"><span>'+title+'</span><p>'+data.aList[i].createTimeString+'</p></div><div class="item-after">'+data.aList[i].money.toFixed(2)+'</div></div></li>';
        }
        $('.infinite-scroll-bottom .list-container').append(html);
      }
    }
    var page = 2;
    var loading = false;
    $(document).on('infinite', '.infinite-scroll-bottom',function() {
    	if(page<=total){
    		if (loading) return;
    		loading = true;
    		$('.preloader').attr("style","display:inline-block");
    	  //设置延时执行，模拟加载
    	  get();
    	}
    });
    function get(){
      
      $.ajax({
        url: url,
        type: 'post',
        data:{
          page:page,
          openid:openid
        },
        dataType: 'json',
        success: function (data) {
          addItems(data);
          page++;
          $('.preloader').attr("style","display:none");
        loading = false;
        },
        error: function () {
          $.toast("操作失败");
          $('.preloader').attr("style","display:none");
        loading = false;
        }
      });
      
    }
  };

  /**
   * ====================================================================
   * 排行
   * ====================================================================
   */
  pro.paiHang = function (type,total) {
      var addItems, url;
      if (type == "caizi") {
          url = "../../wx/api/caizilist.az";
          addItems = function (data) {
              var html1 = '';
              for (var i = 0; i < data.isoptimumList.length; i++) {
                  var name = data.isoptimumList[i].userName == null || data.isoptimumList[i].userName == "" ? data.isoptimumList[i].nickname : data.isoptimumList[i].userName;
                  var userType = data.isoptimumList[i].userType==2?true:false;
                  var zixun = data.isoptimumList[i].userType==2?'<i class="zixun" onclick="zxOnline('+data.isoptimumList[i].openid+')">在线咨询</i>':'';
                  html1 += '<li><span class="cc-li-l"><i class="user-head-min sx" style="background-image: url(' + data.isoptimumList[i].headimgurl + ')"></i>' + name + zixun + '</span><span class="cc-li-r">' + data.isoptimumList[i].isoptimumCount + '次</span></li>';
              }
              $('#tab1 .cc-list-con').append(html1);
              var html2 = '';
              for (var i = 0; i < data.problemList.length; i++) {
                  var name = data.problemList[i].userName == null || data.problemList[i].userName == "" ? data.problemList[i].nickname : data.problemList[i].userName;
                  var userType = data.problemList[i].userType==2?true:false;
                  var zixun = data.problemList[i].userType==2?'<i class="zixun" onclick="zxOnline('+data.problemList[i].openid+')">在线咨询</i>':'';
                  html2 += '<li><span class="cc-li-l"><i class="user-head-min sx" style="background-image: url(' + data.problemList[i].headimgurl + ')"></i>' + name +zixun+ '</span><span class="cc-li-r">' + data.problemList[i].problemNum + '次</span></li>';
              }
              $('#tab2 .cc-list-con').append(html2);
              var html3 = '';
              for (var i = 0; i < data.eavesdropList.length; i++) {
                  var name = data.eavesdropList[i].userName == null || data.eavesdropList[i].userName == "" ? data.eavesdropList[i].nickname : data.eavesdropList[i].userName;
                  var userType = data.eavesdropList[i].userType==2?true:false;
                  var zixun = data.eavesdropList[i].userType==2?'<i class="zixun" onclick="zxOnline('+data.eavesdropList[i].openid+')">在线咨询</i>':'';
                  html3 += '<li><span class="cc-li-l"><i class="user-head-min sx" style="background-image: url(' + data.eavesdropList[i].headimgurl + ')"></i>' + name+zixun + '</span><span class="cc-li-r">' + data.eavesdropList[i].eavesdropNum + '次</span></li>';
              }
              $('#tab3 .cc-list-con').append(html3);
              var html4 = '';
              for (var i = 0; i < data.zhizhaoList.length; i++) {
                  var name = data.zhizhaoList[i].userName == null || data.zhizhaoList[i].userName == "" ? data.zhizhaoList[i].nickname : data.zhizhaoList[i].userName;
                  var userType = data.zhizhaoList[i].userType==2?true:false;
                  var zixun = data.zhizhaoList[i].userType==2?'<i class="zixun" onclick="zxOnline('+data.zhizhaoList[i].openid+')">在线咨询</i>':'';
                  html4 += '<li><span class="cc-li-l"><i class="user-head-min sx" style="background-image: url(' + data.zhizhaoList[i].headimgurl + ')"></i>' + name+zixun + '</span><span class="cc-li-r">' + data.zhizhaoList[i].answerNum + '次</span></li>';
              }
              $('#tab4 .cc-list-con').append(html4);
          }
      } else if (type == "caifu") {
          url = "../../wx/api/caifulist.az";
          addItems = function (data) {
              var html1 = '';
              for (var i = 0; i < data.moneyList.length; i++) {
                  var name = data.moneyList[i].userName == null || data.moneyList[i].userName == "" ? data.moneyList[i].nickname : data.moneyList[i].userName;
                  var userType = data.moneyList[i].userType==2?true:false;
                  var zixun = data.moneyList[i].userType==2?'<i class="zixun" onclick="zxOnline('+data.moneyList[i].openid+')">在线咨询</i>':'';
                  html1 += '<li><span class="cc-li-l"><i class="user-head-min sx" style="background-image: url(' + data.moneyList[i].headimgurl + ')"></i>' + name+zixun + '</span><span class="cc-li-r">￥' + data.moneyList[i].money.toFixed(2) + '</span></li>';
              }
              $('#tab1 .cc-list-con').append(html1);
              var html2 = '';
              for (var i = 0; i < data.integralList.length; i++) {
                  var name = data.integralList[i].userName == null || data.integralList[i].userName == "" ? data.integralList[i].nickname : data.integralList[i].userName;
                  var userType = data.integralList[i].userType==2?true:false;
                  var zixun = data.integralList[i].userType==2?'<i class="zixun" onclick="zxOnline('+data.integralList[i].openid+')">在线咨询</i>':'';
                  html2 += '<li><span class="cc-li-l"><i class="user-head-min sx" style="background-image: url(' + data.integralList[i].headimgurl + ')"></i>' + name+zixun + '</span><span class="cc-li-r">' + data.integralList[i].integral + '分</span></li>';
              }
              $('#tab2 .cc-list-con').append(html2);
          }
      }
      var page = 2;
      var loading = false;
      $(document).on('infinite', '.infinite-scroll-bottom', function () {
    	  if(page<=total){
		      if (loading) return;
		      loading = true;
		      $('.preloader').attr("style", "display:inline-block");
		      get();
		      
    	  }

      });
      function get() {
          $.ajax({
              url: url,
              type: 'post',
              data: {
                  page: page,
                  openid: io.userInfo.openId
              },
              dataType: 'json',
              success: function (data) {
                  loading = false;
                  addItems(data);
                  page++;
                  $('.preloader').attr("style", "display:none");
              },
              error: function () {
                  loading = false;
                  $.toast("操作失败");
                  $('.preloader').attr("style", "display:none");

              }
          })

      }
  };
  
  /**
   * ====================================================================
   * 用户个人中心 - 设置
   * ====================================================================
   */
  pro.yzmFlag = true;
  pro.user = function () {
    var self = {};
    var modal = {
            extraClass: 'cc-modal',
            title: '更改手机号码 <i class="iconfont close-modal">&#xe61b;</i>',
            text: '<div class="input-pho-grop"><input type="tel" class="cc-input" name="phone" id="phone" placeholder="请输入新的手机号"></div>' +
                  '<div class="input-pho-grop"><div class="cc-cell"><input type="text" class="cc-input" id="codes" name="codes" placeholder="验证码"></div>' +
                  '<div class="cc-cell"><a style="width:5rem;" href="javascript:" class="button button-warning cp-a" onclick="cc.programs.user().uPhone(event)" id="code-btn">获取验证码</a></div></div>'+
	              '<div class="cc-cell" style="border-top:1px solid #eee;padding:10px;"><a style="border-radius: 0;border:0;font-size:1.2rem;" href="javascript:" class="button button-warning" onclick="cc.programs.user().updataMobile()">确认</a></div>',
          }
    self.updataMobile = function() { 
	if(!$("#phone").val()){
		$('#phone').focus().css("border-color","#ff0000");
		return false;
	}else{
		if(checkMobile($("#phone").val())){
			$('#phone').focus().css("border-color","#ff0000");
			return false;
		}
	}
	$('#phone').css("border-color","#eee");
	if(!$("#codes").val()){
		$("#codes").focus().css("border-color","#ff0000");
		return false;
	}
	$('#codes').css("border-color","#eee");
	  $.ajax({
        url:"../../wx/userinfo/updatevalidate",
        type: 'post',
        data:{
      	  openid:io.userInfo.openId,
      	  mobile:$('#phone').val(),
      	  code:$('#codes').val()
        },
        dataType: 'json',
        success: function (data) {
        	if(data.resultCode==1000){
        		$.closeModal();
        		$("#mobile").val($('#phone').val());
        		$.alert("修改成功");
        		
        	}else if(data.resultCode==1001){
        		$("#codes").focus().css("border-color","#ff0000");
        	}
        	
        },
        error: function () {
          $.alert('请求超时');
        }
    });
}
    self.cPhone = function(e){
    	var mobile=$("#mobile").val();
    	if(!mobile){
			$.alert("手机号码不能为空！");
			$('#mobile').focus();
			return false;
		}else{
			if(checkMobile(mobile) == false){
				$('#mobile').focus();
				return false;
			}
		}
    	if(pro.yzmFlag){
    		var time = 60;
    		pro.yzmFlag = false;
       	 	e.target.innerHTML = time+"s";
       	 	var timer = setInterval( function(){
                time--;
                if(time<=0){
                	pro.yzmFlag = true;
                    clearInterval(timer);
                    e.target.innerHTML = "获取验证码"
                }else{
                	e.target.innerHTML = time+"s";
                }
                
       	 	},1000);
    	}else{
    		return false;
    	}
    	
    	$.ajax({
            url:"../../wx/userinfo/validatecode",
            type: 'post',
            data:{
          	  phone:mobile
            },
            dataType: 'json',
            success: function (data) {
            	$.alert(data.message);
            },
            error: function () {
              $.alert('请求超时');
            }
        });
    }
   
     self.uPhone=function(e){
    	var phone=$("#phone").val();
    	if(!phone){
    		
			$('#phone').focus().css("border-color","#ff0000");
			return false;
		}else{
			if(checkMobile(phone) == false){
				$('#phone').focus().css("border-color","#ff0000");
				return false;
			}
		}
    	$('#phone').css("border-color","#eee");
    	
    	if(pro.yzmFlag){
    		var time = 60;
    		pro.yzmFlag = false;
       	 	e.target.innerHTML = time+"s";
       	 	var timer = setInterval( function(){
                time--;
                if(time<=0){
                	pro.yzmFlag = true;
                    clearInterval(timer);
                    e.target.innerHTML = "获取验证码"
                }else{
                	e.target.innerHTML = time+"s";
                }
                
       	 	},1000);
    	}else{
    		return false;
    	}
    	
   	 	
    	$.ajax({
            url:"../../wx/userinfo/validatecode",
            type: 'post',
            data:{
          	  phone:phone
            },
            dataType: 'json',
            success: function (data) {
            	//$.alert(data.message);
            },
            error: function () {
              $.alert('请求超时');
            }
        });
    }
    
    self.bindEvents = function () {
      $(document).on('click','#btnChangePho',function () {
    	  $.modal(modal);
    	 
      });
      $(document).on('click','.modal-overlay, .close-modal',function () {
        $.closeModal();
      });
    };
    self.init = function () {
      self.bindEvents();
    };
    self.init();
    $('.question_list').click(function(){
        window.location.href = "../../wx/problem/problemList.az";
    });
    return self;
  };

  /**
   * ====================================================================
   * vue 组件 1. 问题 item
   * ====================================================================
   */
  pro.que_item = function (queitemone) {
    // 保存上一个音频的播放状态
    window.lastMedia = {};
    // 列表页 item
    return Vue.component('que-item', {
      template: queitemone,
      data: function () {
        return {
          item: {}
        }
      },
      /**
       * item: 数据
       * ctl: 下面按钮是否可用
       * com: 评论区
       * offermoney: 显示赏金
       * collect: 显示可删除收藏的图标
       * index: 数据下标
       * asked: 是否显示追问按钮
       * zjda: 是否拥有最佳答案
       * userqd: 我的问题详情
       * luckhandle: 显示幸运红包
       * xytt: 显示幸运偷听
       */
      props: ['item','ctl','com','offermoney','collect','index','asked','zjda','userqd','luckhandle','xytt'],
      computed: {
    	  media: function () {
              var media = new Audio();
              media.src = this.item.attachment==null?"":(this.item.attachment.filenamestring || null);
              var arr = [];
              var list = this.item.mapList == null ? "" : (this.item.mapList || null)
              for(var i = 0;i<list.length;i++){
                  arr[i] = new Audio();
                  arr[i].src = list[i].accessurl;
              }
              return {media:media,arr:arr};
          }
      },
      methods: {
        // 音频播放接口
        playAudio: function (src, event) {
          var _this = this;
          var ele = event.currentTarget;
          if(this.media && this.media.media.src == src){
              var media = this.media.media;
          }else{
              for(var i = 0;i<this.media.arr.length;i++){
                  if(this.media.arr[i].src == src){
                      var media = this.media.arr[i]
                  }
              }
          }
          var events = {
            loadstart: function () {
            	if($(ele).children('.rotate-min').length<1){
                    $(ele).append('<i class="rotate-min iconfont">&#xe601;</i>');
            	}
            },
            playing: function () {
                $(ele).addClass('cc-audio-bg');
            },
            // canplaythrough
            canplay: function () {
              $(ele).find('.rotate-min').remove();
            },
            ended: function () {
              _this.pauseAudio(ele, media);
            }
          }

          if (media.paused) {
            lastMedia.media && _this.pauseAudio(lastMedia.ele, lastMedia.media);
            lastMedia.media = media;
            lastMedia.ele = ele;

            media.play();
            $(media).on(events);
          } else {
            _this.pauseAudio(ele, media);
          }
        },
        // 音频暂停
        pauseAudio: function (ele, media) {
          $(ele).removeClass('cc-audio-bg');
          media.pause();
        },
        // 图片浏览器
        photoBrowser: function (i) {
          var imgarr = []
          this.item.aList.forEach(function (el) {
            imgarr.push(el.filenamestring);
          });
          var photoBrowser = $.photoBrowser({
            photos: imgarr,
            initialSlide: i,
            navbar: false,
            toolbar: false,
            theme: 'exposed',
            type: 'popup',
            onOpen: function () {
                $('.swiper-container').append('<i class="iconfont close-swiper">&#xe61b;</i>');
            },
            onClose: function () {
              photoBrowser = null;
              var popup = $('.popup');
              popup.addClass('modal-out');
              $('.popup-overlay').remove();
              setTimeout(function () {
                popup.remove();
              },200);
            },
            onClick: function () {
              photoBrowser.close();
            }
          });
          photoBrowser.open();
        },
        // 收藏
        collectCount: function (e) {
          var _this = this;
          var ele = $(e.currentTarget);
          var id = ele.parent().data().id;
          if(_this.ctl) return false;
          if (!ele.hasClass('J_colled')) {
            ele.addClass('J_colled').find('i').html('&#xe622;');
            _this.item.collectCount++;
            _this.item.collect=1;
            _this.fetchData(io.question.collectCount,{ type:'collect', ctr: 1 },function () { });
          } else {
            ele.removeClass('J_colled').find('i').html('&#xe600;');
            _this.item.collectCount--;
            _this.item.collect=0;
            _this.fetchData(io.question.collectCount,{ type:'collect', ctr: 0 },function () { });
          }
          
          var stor = Util.sionFetch('LIST');
          for(var i= 0;i<stor.problemList.length;i++){
              if(stor.problemList[i].id == id){
                  stor.problemList[i].collectCount =  _this.item.collectCount;
                  stor.problemList[i].collect =  _this.item.collect;
              }
          }
          Util.sionSave('LIST',stor);
        },
        // 点赞
        praiseCount: function (e) {
          var _this = this;
          var ele = $(e.currentTarget);
          var id = ele.parent().data().id;
          if(_this.ctl) return false;
          if (!ele.hasClass('J_praised')) {
            ele.addClass('J_praised').find('i').html('&#xe620;');
            _this.item.praiseCount++;
            _this.item.praise=1;
            _this.fetchData(io.question.praiseCount,{ type:'praise', ctr: 1 },function () { });
          } else {
            ele.removeClass('J_praised').find('i').html('&#xe621;');
            _this.item.praiseCount--;
            _this.item.praise=0;
            _this.fetchData(io.question.praiseCount,{ type:'praise', ctr: 0 },function () { });
          }
          
          var stor = Util.sionFetch('LIST');
          for(var i= 0;i<stor.problemList.length;i++){
              if(stor.problemList[i].id == id){
                  stor.problemList[i].praiseCount =  _this.item.praiseCount;
                  stor.problemList[i].praise =  _this.item.praise;
              }
          }
          Util.sionSave('LIST',stor);
        },
        fetchData: function (url, date, callback) {
            date.info = io.userInfo.openId;
            date.id = this.item.id;
            $.ajax({
              url: url,
              type: 'post',
              data:{
            	  info:io.userInfo.openId,
            	  id:this.item.id,
            	  type:date.type,
            	  ctr:date.ctr
              },
              dataType: 'json',
              success: function (data) {
                data.resultCode <= 1000 ? callback(data) : $.alert(data.message);
              },
              error: function () {
                $.alert('请求超时');
              }
            });
        },
        // 举报
        reportCount: function (e,answer_id) {
        	var _this = this;
            if(_this.ctl) return false;
            $.modal({
              extraClass: 'cc-modal',
              title: '举报 <i class="iconfont close-modal">&#xe61b;</i>',
              text: pro.model.jubao_m,
              buttons: [
                {
                  text: '确认',
                  onClick: function() {
                    //$.alert('提交')
                	  //获得 单选选按钮name集合  
                	    var radios = document.getElementsByName("jb-text");
                	    var eavesdropContent=$("#jb-ms").val();
                	    var reportType="";
                	    //根据 name集合长度 遍历name集合  
                	    for(var i=0;i<radios.length;i++)  
                	    {   //判断那个单选按钮为选中状态  
                	        if(radios[i].checked)  
                	        {  //弹出选中单选按钮的值  
                	            reportType=radios[i].value;
                	        }   
                	    }
                	    if(reportType==""){
                	    	$.alert('请选择举报原因！');
                	    	
                	    }else{
                	    	//举报
                	    	$.ajax({
                	            url:"../../wx/api/addreport.az",
                	            type: 'post',
                	            data:{
                	            	  openid:io.userInfo.openId,
                	            	  problemId:wentiId,
                	            	  answerId:answer_id,
                	            	  reportType:reportType,
                	            	  eavesdropContent:eavesdropContent
                	             },
                	             dataType: 'json',
                	             success: function (data) {
                	                data.resultCode <= 1000 ? $.alert(data.message) : $.alert(data.message);
                	                location.reload();
                	             },
                	             error: function () {
                	                $.alert('请求超时');
                	             }
                	        });
                	    }
                  }
                },
              ]
            });
            $(document).on('click','.modal-overlay, .close-modal',function () {
              $.closeModal();
            });
        },
        // 解锁   微信支付接口
        lockon: function () {
        	var answerId=this.item.id;
        	var _this = this;
            $.confirm('<p class="fz-m">偷听需要一元费用，确认偷听吗？</p><div class="jfsm"  onclick="jfsm2()"></div>', '<i class="logo-ico"></i>', function () {
              //偷听者id，问题id，回答id
              //window.location.href="../../wx/eavesdrop/eavesdrop.az?openid="+io.userInfo.openId+"&problemId="+wentiId+"&answerId="+answerId;
            	$.ajax({
            		url:"../../wx/api/eavesdrop.az",
            		type: 'post',
            		data:{
  	            	  openid:io.userInfo.openId,
  	            	  problemId:wentiId,
  	            	  answerId:answerId
            		},
            		dataType: 'json',
            		success: function (data) {
            			if(data.resultCode <= 1000){
            				window.location.href="../../pay/getPrepayId?totalFee=1&orderSn="+data.order.orderSn+"&payType="+data.order.orderType+"&problemId="+wentiId;
            				
            			}else{
            				$.alert(data.message);
            			}
            			//data.resultCode <= 1000 ? $.alert(data.message) : $.alert(data.message);
            			// 异步解锁
            			//_this.item.lock = false;
                        //setTimeout( $.refreshScroller, 200);
            			//location.reload();
            		},
            		error: function () {
            			$.alert('请求超时');
            		}
            	});
              
            });
        },
        // 移除收藏
        removecollect: function (id) {
          var _this = this;
          $.confirm('确定要删除吗？', function () {
        	  _this.fetchData(io.user.userRmoveCollect, {type:'removecollect'}, function () {
        		  _this.$dispatch('removecollect',_this.index);
        		  window.location.href="../../wx/problem/mycollect.az?openid="+io.userInfo.openId;
        	  });
          });
        },
        // 设置最佳答案
        bestAns: function (answer_id) {
          var _this = this;
          $.confirm('<p class="fz-m">确定设为最佳答案吗？</p>', '<i class="logo-ico"></i>', function () {
        	_this.item.zjda = true;
            _this.$dispatch('zjda');
            _this.fetchData(io.userQuestionDetail.zjda,{ type:1, ctr:answer_id},function () {
            	//location.reload();//window.location.href="../../wx/problem/problembyid.az?problemId="+myproblemId;v-if="userqd && !zjda"
            });
            
          });
         
        },
        // 追问
        tapAsked: function () {
          var _this = this;
          $.modal({
            title: '追问 <i class="iconfont close-modal">&#xe61b;</i>',
            extraClass: 'cc-modal asked-box',
            text: pro.model.tapAsked_m
          });
          $('.modal.modal-in').css({marginTop: -Math.round($('.modal.modal-in').outerHeight() / 3) + "px"});
          $(document).on('click','.modal-overlay, .close-modal',function () {
            $.closeModal();
          });
          $(document).on('click','#sub',function () {
        	 var que = $.trim($('#queAsk').val());
             _this.$dispatch('tapAsked', que);
          });
        },
        // 打开红包
        openPacker: function () {
          var _this = this;
          //if(_this.item.luck.open) return;
          
          var dataInfo = "";
          $.ajax({
              url: "../../wx/api/lucykai.az",
              type: 'get',
              data: {
                  "openid":io.userInfo.openId,
                  "problemId":_this.item.problemid,
                  "open":_this.item.luck.open
              },
              dataType: 'json',
              success: function (data) {
                 dataInfo = data;
                 if(_this.item.luck.open == 1){
                	 $(document).on('click', '.modal-overlay, .J_closeModal', function () {
                         $.closeModal();
                     });
                     var otherUser = "";
                     for(var i = 0;i<dataInfo.account.length;i++){
                    	 var name = dataInfo.account[i].userName||dataInfo.account[i].nickname;
                    	 var userMoney = dataInfo.account[i].money.toFixed(2);
                         otherUser +='<p class="other"><a class="user-head-min" style="position: static;background-image: url(' + dataInfo.account[i].headimgurl + ')"></a><span>'+name+'</span><i>'+userMoney+'</i></p>'
                     }
                     $.modal({
                         extraClass: 'red-packer red-packer-detail',
                         text: '<a href="javascript:" class="user-head-min" style="background-image: url(' + _this.item.headimgurl + ')"></a>' +
                         '<span class="user-name">' + (_this.item.userName || _this.item.nickname) + '</span>' +
                         '<p style="position: absolute;top: 28%;left: 0;width:100%;font-size: 2rem">'+dataInfo.myAccount.money.toFixed(2)+'</p>' +
                         '<p style="position: absolute;top: 45%;left: 0;width:100%;color:#0966a3;font-size: .7rem;">详情请查看‘我的收益’</p>' +
                         '<p style="position: absolute;top: 50%;left: 0;width:100%;color:#0966a3;font-size: .7rem;">红包将于每晚9点统一发放</p>' +
                         '<p style="position: absolute;top: 58%;left: 0;width:100%;color:#0966a3;font-size: .7rem;text-align: left;background-color: #f3f3f3;color:#231815;padding:.1rem 10px;">'+dataInfo.moneytotal+'元红包</p>' +
                         '<div class="other_box">'+otherUser+'</div>' +
                         '<a href="javascript:" class="J_closeModal"></a>',
                     });
                 }else{
                     $.modal({
                         extraClass: 'red-packer',
                         text: '<a href="javascript:" class="user-head-min" style="background-image: url(' + _this.item.headimgurl + ')"></a>' +
                         '<span class="user-name">' + (_this.item.userName || _this.item.nickname) + '</span>' +
                         '<a href="javascript:" class="J_redpacker"></a><a href="javascript:" class="J_closeModal"></a>',
                     });
                     $(document).on('click', '.modal-overlay, .J_closeModal', function () {
                         $.closeModal();
                     });
                     $(document).on('click', '.J_redpacker', function () {
                         // todo 打开微信红包
                         //console.log('微信红包11');
                         $.closeModal();

                         var otherUser = "";
                         for(var i = 0;i<dataInfo.account.length;i++){
                        	 var name = dataInfo.account[i].userName||dataInfo.account[i].nickname;
                        	 var userMoney = dataInfo.account[i].money.toFixed(2);
                        	 otherUser +='<p class="other">'+
                        	 '<a class="user-head-min" style="position: static;background-image: url('+dataInfo.account[i].headimgurl+')"></a>'+
                        	 '<span>'+name+'</span>'+
                        	 '<i>'+userMoney+'</i></p>';
                         }
                         $.modal({
                             extraClass: 'red-packer red-packer-detail',
                             text: '<a href="javascript:" class="user-head-min" style="background-image: url(' + _this.item.headimgurl + ')"></a>' +
                             '<span class="user-name">' + (_this.item.userName || _this.item.nickname) + '</span>' +
                             '<p style="position: absolute;top: 28%;left: 0;width:100%;font-size: 2rem">'+dataInfo.myAccount.money.toFixed(2)+'</p>' +
                             '<p style="position: absolute;top: 45%;left: 0;width:100%;color:#0966a3;font-size: .7rem;">详情请查看‘我的收益’</p>' +
                             '<p style="position: absolute;top: 50%;left: 0;width:100%;color:#0966a3;font-size: .7rem;">红包将于每晚9点统一发放</p>' +
                             '<p style="position: absolute;top: 58%;left: 0;width:100%;color:#0966a3;font-size: .7rem;text-align: left;background-color: #f3f3f3;color:#231815;padding:.1rem 10px;">'+dataInfo.moneytotal+'元红包</p>' +
                             '<div class="other_box">'+otherUser+'</div>' +
                             '<a href="javascript:" class="J_closeModal"></a>',
                         });
                         _this.item.luck.open = 1;
                     });
                 }
              },
              error: function () {
                  $.alert('请求超时');
              }
          });
        
          
          
//          $.modal({
//            extraClass: 'red-packer',
//            text: '<a href="javascript:" class="user-head-min" style="background-image: url('+ _this.item.headimgurl +')"></a>' +
//                  '<span class="user-name">'+ (_this.item.userName || _this.item.nickname) +'</span>' +
//                  '<a href="javascript:" class="J_redpacker"></a><a href="javascript:" class="J_closeModal"></a>',
//          });
//          $(document).on('click','.modal-overlay, .J_closeModal',function () {
//            $.closeModal();
//          });
//          $(document).on('click','.J_redpacker',function () {
//        	  
//            // todo 打开微信红包
//            //console.log('微信红包');
//            $.closeModal();
//          });
        }
      }
    });
  };
  
  
//时间选择器
  pro.chooserDate = function () {
    return Vue.component('chooser-date', {
      template: pro.model.chooserDate_m,
      data: function () {
        return {
        };
      },
      props: ['date','times','sel'],
      methods: {
        dateChange: function (de) {
          this.$dispatch('date', de);
        },
        nextDate: function () {
          var date = new Date(this.date);
          var newDate = date.setDate(date.getDate() + 1);
          this.$dispatch('date', Util.f(newDate));
        },
        prevDate: function () {
          var date = new Date(this.date);
          var newDate = date.setDate(date.getDate() - 1);
          this.$dispatch('date', Util.f(newDate));
        },
        reser: function (e, use) {
          if(!use) return false;
          if(this.sel){
              // todo 点击时间
              console.log('链接');
              return;
          }
          var _this = $(e.currentTarget);
          var thischeck = _this.find('input');
          if(thischeck.prop('checked')){
            thischeck.val('').prop('checked', false);
            _this.find('.cho-text').html('');
            return;
          }
//          $.modal({
//            title: '选择咨询的类型 <i class="iconfont close-modal">&#xe61b;</i>',
//            extraClass: 'cc-modal',
//            text: pro.model.chooser_m,
//            buttons: [{
//                text: '确认',
//                onClick: function() {
//                  var check = $('.J_zixun .item-content').find('input:checked');
//                  var val = check.val();
//                  var txt = check.siblings('.item-inner').text();
//                  if(val){
//                    thischeck.val(val).prop('checked', true);
//                    _this.find('.cho-text').html(txt);
//                  }
//                }
//              }]
//          });
//          $(document).on('click','.modal-overlay, .close-modal',function () {
//            $.closeModal();
//          });
          thischeck.val(0).prop('checked', true);
        }
      }
    });
  };
  
//单个 问题+答案组合
  pro.queAns = function () {
    var queItem = pro.que_item(pro.model.que_item_m);//:item="ele.answer"
    return Vue.component('que-ans', {
      template: '<que-item :item="ele.question" ctl="true" class="next-ans" @click.stop.prevent="url(ele.question.url)"></que-item>' +
                '<que-item v-for="ans in ele.answer" :item="ans" ctl="true" com="true" :luckhandle="luckhandle" :xytt="xytt" :class="{ \'next-ans\': lihandle}"></que-item>' +
                '<div class="cc-minlist" v-if="lihandle"><div class="cc-list-hd"><span class="cc-li-l">偷听({{ele.listen.len}})</span> <span class="cc-li-r">共{{ele.listen.allmoney}}元</span></div>' +
                '<ul class="cc-list-con" :class="{ \'hidemore\' : showmore }">' +
                  '<li v-for="i in ele.listen.list">' +
                    '<span class="cc-li-l"><i class="user-head-min sx"style="background-image: {{\'url(\'+i.headimgurl+\')\'}}"></i> {{ i.userName || i.nickname }}</span>' +
                    '<span class="cc-li-r"><i class="iconfont">&#xe613;</i> {{i.money}}</span></li>' +
                  '' +
                '</ul><span v-if="ele.listen.len > 3" @click="showMore()" class="showmore">' +
                  '<i class="iconfont" v-if="hidemore">&#xe616;</i><i v-else class="iconfont">&#xe61a;</i></span></div>',
      data: function () {
        return {
          showmore: false,
          hidemore: true,
        }
      },
      computed: {
        showmore: function () {
          return this.ele.listen.len > 3 && this.hidemore;
        }
      },
      props: ['ele','luckhandle','lihandle','xytt'],
      component: ['queItem'],
      methods: {
        url: function (url) {
          this.$dispatch('linked', url);
        },
        showMore: function () {
          this.hidemore = !this.hidemore;
        }
      }
    })
  };
  
  
  pro.hongBao = function(id){
      $.ajax({
          url: "../../wx/api/sendhongbao.az",
          type: 'post',
          data: {
              problemId:id
          },
          dataType: 'json',
          success: function (data) {
             location.reload();
          },
          error: function () {
              $.alert('请求超时');
          }
      });
  }


  /**
   * ====================================================================
   * HTML 模板
   * ====================================================================
   */
  pro.model = {};
  // 问题列表
  pro.model.que_item_m =
	    '<article class="cc-card" :class="[{\'zjda\': item.zjda && com},{\'xytt\': item.xytt && com && xytt}]">' +
      '<div class="cc-card-hd">' +
        '<span v-if="item.isSecret!=1"><i class="user-head-min" v-if="item.headimgurl" style="{{\'background-image: url(\'+item.headimgurl+\')\'}}"></i>{{ item.userName || item.nickname }}<i v-if="item.userType==2" class="zixun" onclick="zxOnline(\'{{item.userId}}\')">在线咨询</i></span>' +//{{item.userName ? item.userName:item.nickname}} 
        '<span v-else><i class="user-head-min"></i> 匿名</span><span v-if="collect" @click.stop.prevent="removecollect(item.id)" class="iconfont cc-card-delet">&#xe60c;</span>' +
        '<time v-if="!com && !offermoney && item.offerMoney.toFixed(2)==0.00">{{item.createTimeString}}</time>' +
        '<span v-if="offermoney || item.offerMoney.toFixed(2)!=0.00" class="cc-card-row"><i class="iconfont">&#xe613;</i> {{item.offerMoney.toFixed(2)}}</span>'+
        '<span v-if="com && userqd && !zjda && item.tong==1 && item.isLucky==0 && item.isFinish==0" class="cc-card-row min" @click="bestAns(item.id)">设为最佳答案</span>' +
        '<span v-if="luckhandle && item.luck.own" class="cc-card-row min" @click="openPacker()"><i class="iconfont">&#xe612;</i>{{item.luck.open? item.luck.money+\'元包\': \'未开红包\'}}</span></div>' +
      '<div class="cc-card-con" v-if="!item.lock">' +
          '<p v-if="item.contentText" style="word-wrap:break-word;">{{item.contentText}}</p><video v-if="item.videoUrl"  style="width:100%" id="video" webkit-playsinline x-webkit-airplay controls preload="none" load="loaded" poster="imgs/bg.jpg"><source id="mp4" :src="item.videoUrl"><div style="height:342px;line-height:342px;text-align:center;font-size:18px;">您的浏览器不支持HTML5视频</div></video><a href="javascript:" class="cc-asked" v-if="asked && !item.zjda" @click="tapAsked()">追问</a>' +
        '<div class="cc-audio" v-if="item.attachment.filenamestring" @click.stop.prevent="playAudio(item.attachment.filenamestring, $event)">' +
          '<span>{{item.attachment.remark}}s</span> <i class="iconfont">&#xe603;</i></div>' +
          
          
          '<div class="cc-card-con" v-if="item.mapList.length > 0" v-for="qc in item.mapList">' +
          '<time v-if="qc && !qc.offermoney" style="text-align: left;width:100%;">追问：{{qc.createTimeString}}</time>' +
          '<span v-if="qc.offermoney" class="cc-card-row" style="text-align: right;width:100%;"><i class="iconfont">&#xe613;</i> {{qc.offermoney.toFixed(2)}}</span>'+
          '<div class="cc-card-con">'+
          	'<p v-if="qc.contentText">{{qc.contentText}}</p>' +
          	'<div class="cc-audio" v-if="qc.accessurl" @click.stop.prevent="playAudio(qc.accessurl, $event)">' +
          	'<span>{{qc.remark}}s</span> <i class="iconfont">&#xe603;</i></div>' +
          '</div>'+
          '</div>'+
        
        '<div class="cc-card-imglist" v-if="item.aList.length > 0">' +
          '<a class="imglist-i" v-for="img in item.aList" @click.stop.prevent="photoBrowser($index)" style="{{\'background-image: url(\'+img.filenamestring+\')\'}}" href="javascript:"></a>' +
        '</div>' +
          '<a class="cc-card-stu" href="javascript:" v-if="item.ptList.length>0"><i class="iconfont">&#xe619;</i><labal v-for="pt in item.ptList">{{pt.typeName}}<i v-if="$index+1<item.ptList.length">、</i></labal></a>' +
          '<a style="float: right;margin-bottom:0;" class="cc-card-stu child_age clearfix" href="javascript:" v-if="item.age"><img style="width:16px;position: relative;top:2px;" class="childAge_ico" src="../../static/wx/imgs/child.png" /> {{item.age}}岁</a>' +
      '</div>'+
      '<div v-else class="cc-lockin" @click="lockon()">' +
          '<span class="iconfont">&#xe623;</span>' +
      '</div>' +
      '<div class="cc-card-footer" data-id={{item.id}}>' +
        '<a href="javascript:" @click.stop.prevent="collectCount($event)" v-if="!com && item.collect==1" class="J_colled"><i class="iconfont">&#xe622;</i> {{item.collectCount}}</a>' +
        '<a href="javascript:" @click.stop.prevent="collectCount($event)" v-if="!com && item.collect!=1"><i class="iconfont">&#xe600;</i> {{item.collectCount}}</a>' +
        '<a href="javascript:" @click.stop.prevent="praiseCount($event)" v-if="item.praise==1" class="J_praised"><i class="iconfont">&#xe620;</i> {{item.praiseCount}}</a>' +
        '<a href="javascript:" @click.stop.prevent="praiseCount($event)" v-if="item.praise!=1"><i class="iconfont">&#xe621;</i> {{item.praiseCount}}</a>' +
        '<a href="javascript:" v-if="!com"><i class="iconfont">&#xe602;</i> {{item.replyCount}}</a>' +
        '<a href="javascript:"><i class="iconfont">&#xe606;</i> {{item.eavesdropCount}}</a>' +
        '<a href="javascript:" @click.stop.prevent="reportCount($event,item.id)">举报 {{item.reportCount}}</a>' +
      '</div>' +
    '</article>';
  
//举报弹窗
  pro.model.jubao_m =
    '<div class="list-block cc-list-b">' +
    '<h4 class="cc-common-title">请选择举报该用户的原因</h4><ul>' +
    '<li><label class="label-checkbox item-content"><input type="radio" id="jb-text" name="jb-text" value="01"><div class="item-media"><i class="icon icon-form-checkbox"></i></div><div class="item-inner"><div class="item-subtitle">发布不适当的内容对我造成骚扰</div></div></label></li>' +
    '<li><label class="label-checkbox item-content"><input type="radio" id="jb-text" name="jb-text" value="02"><div class="item-media"><i class="icon icon-form-checkbox"></i></div><div class="item-inner"><div class="item-subtitle">存在欺诈骗钱行为</div></div></label></li>' +
    '<li><label class="label-checkbox item-content"><input type="radio" id="jb-text" name="jb-text" value="03"><div class="item-media"><i class="icon icon-form-checkbox"></i></div><div class="item-inner"><div class="item-subtitle">此账号可能被盗用了</div></div></label></li>' +
    '<li><label class="label-checkbox item-content"><input type="radio" id="jb-text" name="jb-text" value="04"><div class="item-media"><i class="icon icon-form-checkbox"></i></div><div class="item-inner"><div class="item-subtitle">存在侵权行为</div></div></label></li>' +
    '<li><label class="label-checkbox item-content"><input type="radio" id="jb-text" name="jb-text" value="05"><div class="item-media"><i class="icon icon-form-checkbox"></i></div><div class="item-inner"><div class="item-subtitle">发布恶意广告信息</div></div></label></li></ul>' +
    '<h4 class="cc-common-title">举报内容描述</h4><textarea id="jb-ms" name="jb-ms" placeholder="请描述举报内容"></textarea></div>';
  
  
  // 时间选择器
  pro.model.chooserDate_m =
    '<div class="chooser-date">' +
      '<div class="cho-hd">' +
        '<input type="text" class="cho-date" v-model="date" readonly @change="dateChange(date)">' +
        '<a href="javascript:" class="prev iconfont" @click="prevDate()">&#xe611;</a>' +
        '<a href="javascript:" class="next iconfont" @click="nextDate()">&#xe610;</a>' +
      '</div><div class="cho-con row">' +
    '<label class="cho-item col-33" v-for="t in times" @click.stop.prevent="reser($event, t.usable)">' +
      '<input type="checkbox" :disabled="!t.usable" name="times[{{date}}][{{$key}}]" :checked="t.check" :value="t.value">' +
      '<span class="cho-chekbox" :class="{\'yiyuyue\': t.yiyuyue}">{{$key}}</span><span class="cho-text" v-html="t.txt"></span>' +
    '</label></div></div>';
  
//咨询弹窗
  pro.model.chooser_m = '<div class="list-block cc-list-b J_zixun">' +
    '<ul>' +
    '<li><label class="label-checkbox item-content"><input type="radio" name="jb-text" value="phone"><div class="item-media"><i class="icon icon-form-checkbox"></i></div><div class="item-inner"><div class="item-subtitle">电话咨询</div></div></label></li>' +
    '<li><label class="label-checkbox item-content"><input type="radio" name="jb-text" value="video"><div class="item-media"><i class="icon icon-form-checkbox"></i></div><div class="item-inner"><div class="item-subtitle">视频咨询</div></div></label></li>' +
    '<li><label class="label-checkbox item-content"><input type="radio" name="jb-text" value="toface"><div class="item-media"><i class="icon icon-form-checkbox"></i></div><div class="item-inner"><div class="item-subtitle">面对面咨询</div></div></label></li></ul>' +
    '</div>';
  
//追问 弹窗
  pro.model.tapAsked_m = '<h4 class="cc-common-title">输入提问的内容</h4>' +
    '<div class="answer-text"><textarea rows="5" name="que" id="queAsk" placeholder="详细描述您的问题(不少于20字)"></textarea></div><span></span>' +
    '<h4 class="cc-common-title">语音回答</h4><div class="answer-voice"><div class="cc-audio J_paly_voice" hidden>' +
    '<span class="J_time">0s</span><i class="iconfont">&#xe603;</i><span class="iconfont J_del_voice">&#xe609;</span></div>' +
    '<a href="javascript:" class="iconfont J_startRecord" ontouchstart = "return false;">&#xe61f;</a></div>' +
    '<div class="modal-buttons"><span class="cc-m-btn" id="sub">确认</span></div>';
//订单列表
  pro.model.subscribe_m = '<article class="cc-u-box J_box" data-id={{item.order_sn}}>' +
      '<div class="cc-u-item" @click.stop.prevent="saveStr(page,makemsg,item.urlAddress)"> ' +
      '<div class="cc-u-il">' +
      '<a href="javascript:" class="user-head-min" style="background-image: url({{item.headimgurl}})"></a> ' +
      '<p>{{item.expertNickname || item.experUserName}}</p> ' +
      '</div> ' +
      '<div class="cc-u-ir"> ' +
      '<h4 class="cc-u-inhead">{{item.s_time}}</h4> ' +
      '<div class="cc-u-inbody"> <span>{{item.sumPrice}}<small>元</small></span>{{item.typeName}} </div> ' +
      '</div> ' +
      '</div> ' +
      '<div class="btn-box clearfix" style="padding: 5px"> ' +
      '<a v-if="item.order_state==0" @click.stop.prevent="saveStr(page,makemsg,item.doPay)" href="javascript:" class="button button-warning">去支付</a> ' +
      '<a v-if="item.order_state==2" @click.stop.prevent="saveStr(page,makemsg,item.doComment)" href="javascript:" class="button button-warning external">去评价</a> ' +
      '<a v-if="item.order_state==3 || item.order_state==5 || item.order_state==6" @click.stop.prevent="saveStr(page,makemsg,item.seeComment)" href="javascript:" class="button button-warning external">查看评价</a> ' +
      '<a v-if="item.order_state==0" href="javascript:" class="button button-dark J_del" @click.stop.prevent="del($event)">取消订单</a> ' +
      '</div> ' +
      '</article>'
};
var zxOnline;
(function(){
    var zixunColor = ["#c8039c","#4169E1","#f86900"];
    var colorIndex = 0;
    setInterval(function(){
        if(colorIndex>zixunColor.length-1){
            colorIndex = 0;
        }
        $('.zixun').css({"color":zixunColor[colorIndex],"border-color":zixunColor[colorIndex]});
        colorIndex++;
    },650);
    zxOnline = function(id){
    	window.location.href="../../wx/expert/expertMsg.az?expertid="+id;
    }
    //积分说明
    function jfsm(){
        $.closeModal();
        $.modal({
            title: '',
            extraClass:"jfsmBox",
            text: '<div class="jfsm_modal">' +
        '<div class="jfsmtit">支招客计费说明</div>'+
        '<h4>一、问客</h4>'+
        '<pre>'+
        '您所提问的问题最低费用为1元起（所付费用需整额），向答客再次追问需重新付费。<br>'+
        '1、您需要在72小时内，从众多支招答案中选出问题的最佳答案，支招费会自动入库到该答客钱包中。<br>'+
        '2、如果您没能在72小时内从众多支招答案中确定出最佳答案，支招费将以抢红包形式随机分发给所有本问题的答客。<br>'+
        '3、如果您的问题在72小时内没有答客回答，支招费将自动退回到您的钱包中。'+
        '</pre>'+
        '<h4>二、答客</h4>'+
        '<pre>'+
        '1、您的答案若被问客确定为最佳答案，支招费将自动入库到您的钱包，届时微信会及时提示。<br>'+
        '2、如果您参与回答的问题，问客最终未选择最佳答案，该问题的支招费会以抢红包形式分发给所有答客，金额随机。<br>'+
        '3、如果您的答案被他人偷听，偷听费的50%会入库到您的钱包中。<br>'+
        '4、您的支招既没被确定为最佳答案也没被偷听，同时也没抢到红包，将不会产生任何收益。'+
        '</pre>'+
        '<h4>三、听客</h4>'+
        '<pre>'+
        '您偷听（看）答案需要付费1元，待您付费后，费用会由问客和答客平分，相同答案不需再次付费。<br>'+
        '温馨提示：<br>'+
        '1、低于1元的红包无法提现，待红包金额超出1元后再行结算。<br>'+
        '2、所有收入本平台扣除10%为收益，每日21点后自动结算。'+
        '</pre>'+
        '</div>'

        })
        $('.jfsmBox').css({
            "height":.9*(document.documentElement.clientHeight || document.body.clientHeight),
            "overflow":"auto",
            "margin-top":0,
            "top":"5%",
            "text-align":"left",
        })
        $('.jfsmBox h4').css("margin",".5rem 0")
        $('.modal-overlay').click(function(){
            $.closeModal();
            $('.iconfont.icon-failed').remove();
        })

        $('body').append('<i style="position:fixed;top:.3rem;right:.3rem;z-index: 11000;color:#fff;" class="iconfont icon-failed"></i>')
        $('.iconfont.icon-failed').click(function(){
            $(this).remove();
            $.closeModal();
        })


    }
    function jfsm2(){
        $('body').append('<div class="jfsm_modal">' +
                '<div class="jfsmtit">支招客计费说明</div>'+
                '<h4>一、问客</h4>'+
                '<pre>'+
                '您所提问的问题最低费用为1元起（所付费用需整额），向答客再次追问需重新付费。<br>'+
                '1、您需要在72小时内，从众多支招答案中选出问题的最佳答案，支招费会自动入库到该答客钱包中。<br>'+
                '2、如果您没能在72小时内从众多支招答案中确定出最佳答案，支招费将以抢红包形式随机分发给所有本问题的答客。<br>'+
                '3、如果您的问题在72小时内没有答客回答，支招费将自动退回到您的钱包中。'+
                '</pre>'+
                '<h4>二、答客</h4>'+
                '<pre>'+
                '1、您的答案若被问客确定为最佳答案，支招费将自动入库到您的钱包，届时微信会及时提示。<br>'+
                '2、如果您参与回答的问题，问客最终未选择最佳答案，该问题的支招费会以抢红包形式分发给所有答客，金额随机。<br>'+
                '3、如果您的答案被他人偷听，偷听费的50%会入库到您的钱包中。<br>'+
                '4、您的支招既没被确定为最佳答案也没被偷听，同时也没抢到红包，将不会产生任何收益。'+
                '</pre>'+
                '<h4>三、听客</h4>'+
                '<pre>'+
                '您偷听（看）答案需要付费1元，待您付费后，费用会由问客和答客平分，相同答案不需再次付费。<br>'+
                '温馨提示：<br>'+
                '1、低于1元的红包无法提现，待红包金额超出1元后再行结算。<br>'+
                '2、所有收入本平台扣除10%为收益，每日21点后自动结算。'+
                '</pre>'+
                '</div>'+
                '<i style="position:fixed;top:.3rem;right:.3rem;z-index: 11000;color:#fff;" class="iconfont icon-failed"></i>'
                )

         $('.jfsm_modal').css({
            "width":"13.5rem",
            "height":.9*(document.documentElement.clientHeight || document.body.clientHeight),
            "background":"rgba(255,255,255,1)",
            "position":"fixed",
            "top":"5%",
            "left":"50%",
            "transform":"translate(-50%,0)",
            "-webkit-transform":"translate(-50%,0)",
            "z-index":11100,
            "border-radius":".35rem",
            "overflow":"auto",
            "padding":"10px"
        })
        $('.jfsm_modal h4').css("margin",".5rem 0")
        $('.iconfont.icon-failed').click(function(){
            $('.jfsm_modal').remove();
            $(this).remove();
        })
        $('.modal-overlay').click(function(){
        	$('.jfsm_modal').remove();
            $('.iconfont.icon-failed').remove();
        })
    }
    window.jfsm = jfsm;
    window.jfsm2 = jfsm2;
})();
