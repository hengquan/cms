<div id="cover" class="cover"></div>

<div class="window fade" id="alertDiv">
	<h2 class="window_title" id="msgWinTitle">
		<a href="javascript:doMsgWinClose();"></a>
	</h2>

	<div class="window_con">
		<p id="msgWinContent"></p>
	</div>

	<!-- <div class="window_btn">
		<a class="blue" href="javascript:doMsgWinClose();"><i id="window_close">Close</i></a>
	</div> -->
</div>

<div id="loadingDiv" class="loading_div">
	<img alt="loading..." src="${appRoot}/static/img/loading.gif">&nbsp;
	<% 
		String s = new String("努力加载中".getBytes("ISO8859-1"));
		out.println(s);
	%>
	...
</div>

<script type="text/javascript">
	function showDiv(msgObj){  
	    //var msgObj   = document.getElementById("alertDiv");  
	    msgObj.style.display  = "block";  
	    var msgWidth = msgObj.scrollWidth;  
	    var msgHeight= msgObj.scrollHeight;  
	    var bgTop=EV_myScrollTop();  
	    var bgLeft=EV_myScrollLeft();  
	    var bgWidth=EV_myClientWidth();  
	    var bgHeight=EV_myClientHeight();  
	    var msgTop=bgTop+Math.round((bgHeight-msgHeight)/2);  
	    var msgLeft=bgLeft+Math.round((bgWidth-msgWidth)/2);    
	    msgObj.style.position = "absolute";  
	    /* msgObj.style.top      = msgTop+"px";  
	    msgObj.style.left     = msgLeft+"px";  */
	    
	    msgObj.style.left = "50%";
	    msgObj.style.top = "50%"; 
	}  
	
	//网页被卷去的上高度  
	function EV_myScrollTop(){  
	    var n=window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;  
	    return n;  
	}  
	//网页被卷去的左宽度  
	function EV_myScrollLeft(){  
	    var n=window.pageXOffset || document.documentElement.scrollLeft || document.body.scrollLeft || 0;  
	    return n;  
	}  
	//网页可见区域宽  
	function EV_myClientWidth(){  
	    var n=document.documentElement.clientWidth || document.body.clientWidth || 0;  
	    return n;  
	}  
	//网页可见区域高  
	function EV_myClientHeight(){  
	    var n=document.documentElement.clientHeight || document.body.clientHeight || 0;  
	    return n;  
	}  

	function showMsgWindow(title,content,divTop){
		/* var top=window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0; 
		var left = window.pageXOffset || document.documentElement.scrollLeft || document.body.scrollLeft || 0;  
		alert("top:"+top+",left:"+left); */
		
		var winHeight = window.screen.availHeight;
		divTop = divTop?divTop:'-318';
		if(title!=null && title != ''){
			$("#msgWinTitle").html(title + '<a href="javascript:doMsgWinClose();"></a>');
		}
		
		$("#msgWinContent").text(content);
		$("#cover").show().css("opacity","0.5");
		//$(".fade").css("position","absolute");
		//$(".fade").show().css("top", parseInt(divTop));
		//$(".fade").show().css("top", parseInt(top)).css("left",left);
		var msgObj   = document.getElementById("alertDiv");  
		showDiv(msgObj);
	}

	function doMsgWinClose() {
		$("#cover").hide();
		$(".fade").hide();
	}
	
	function loadingBar(){
		var divTop = -318;
		$("#cover").show().css("opacity","0.5");
		//$("#loadingDiv").show().css("top", parseInt(divTop));
		var msgObj   = document.getElementById("loadingDiv");  
		showDiv(msgObj);
	}
	
	function closeLoadingBar(){
		$("#cover").hide();
		$("#loadingDiv").hide();
	}
</script>