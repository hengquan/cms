<%-- <%
	String memberId=null;
	String mobile="";
	String emailSession="";
	String codeSession="";
	String openId = "";
	boolean isLogin = false;
	if (session.getAttribute("memberIdSession")!=null){
		 memberId=(String)session.getAttribute("memberIdSession");
		 mobile=(String)session.getAttribute("mobileSession");
		 emailSession=(String)session.getAttribute("emailSession");
		 codeSession=(String)session.getAttribute("codeSession");
		 isLogin = true;
		 Object obj = session.getAttribute("openId");
		 if(null!=obj)openId= (String)obj;
	}
	pageContext.setAttribute("memberIdSession", memberId);
	pageContext.setAttribute("isLogin", isLogin);
	pageContext.setAttribute("emailSession", emailSession);
	pageContext.setAttribute("codeSession", codeSession);
	pageContext.setAttribute("mobileSession", mobile);
	pageContext.setAttribute("openId", openId);
%>  --%>