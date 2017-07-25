var windowShow=function(content,id){
			/*弹窗背景层*/
			$("<div class='windowbg'></div>").appendTo("body");
			$(".windowbg").css({"width":"100%","height":"100%","position":"fixed","left":"0","top":"0","background":"rgba(0,0,0,0.5)","z-index":"9999"})
			/*弹窗*/
			$("<div class='Window'></div>").appendTo(".windowbg");
			var $Window = $(".Window");
			/*确定按钮*/
			$("<div class='title'>提示<button type='button' class='close' style='margin-top:8px;margin-left:2px' ata-dismiss='modal' aria-hidden='true'>×</button></div>").appendTo($Window);
			$("<div class='content'>"+content+"</div>").appendTo($Window);
			$("<div class='off'>确  定</div>").appendTo($Window);
			
			$Window.css({"background":"#FFFFFF","display":"none","border-radius":"5px"});
			$Window.css({"width":"300px","height":"180px","position":"absolute","left":"0","top":"0","right":"0","bottom":"0","margin":"auto"})
			
			$(".title").css({"background":"#1A1E24","width":"100%","padding":"0 2%","height":"40px","line-height":"40px","text-align":"cennter","float":"left","font-size":"16px"})
			$(".content").css({"width":"100%","height":"98px","border-top":"1px solid #4d97dd","border-bottom":"1px solid #4d97dd","float":"left","text-align":"center","line-height":"98px","font-size":"16px"})
			$(".off").css({"width":"100%","height":"40px","background":"#1A1E24","position":"absolute","left":"0","bottom":"0","text-align":"center","line-height":"40px","color":"#FFFFFF","font-size":"18px","border-radius":"0 0 5px 5px"});
				
			$Window.fadeIn(300)
			$(".off").on("click",function(){
				$(".windowbg").remove();
				if(id!=null && id!=""){
				  $('#quxiao').click();
				  $("#"+id).submit();
				}
			})
			
			$(".close").on("click",function(){
				$(".windowbg").remove();
			})
		};