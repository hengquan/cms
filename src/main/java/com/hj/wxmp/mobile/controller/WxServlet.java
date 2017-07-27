package com.hj.wxmp.mobile.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hj.utils.SpringContextUtil;
import com.hj.utils.XmlConverUtils;
import com.hj.wxmp.core.WxUser;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.common.Weixin;
import com.hj.wxmp.mobile.entity.FollowResp;
import com.hj.wxmp.mobile.entity.FollowRespEx;
import com.hj.wxmp.mobile.entity.KeywordResp;
import com.hj.wxmp.mobile.entity.KeywordRespEx;
import com.hj.wxmp.mobile.services.FollowRespServices;
import com.hj.wxmp.mobile.services.KeywordRespServices;
import com.hj.wxmp.mobile.services.WxLoginService;


/**
 * Servlet implementation class WxServlet
 */
@WebServlet("/service")
@Controller
public class WxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(WxServlet.class);
	
	private static Weixin weixin = Weixin.getInstance();
	
	
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WxServlet() {
        super();
        
        if(StringUtils.isBlank(weixin.token) || StringUtils.isBlank(weixin.appId) || StringUtils.isBlank(weixin.appSecret)){
        	Properties prop = new Properties();
            try {
    			prop.load(this.getClass().getResourceAsStream("/wx.app.properties"));
    			if(null != prop.getProperty("token")){
    				weixin.token = prop.getProperty("token").toString();  
    			}
    			if(null != prop.getProperty("appId")){
    				weixin.appId = prop.getProperty("appId").toString();  
    			}
    			if(null != prop.getProperty("appSecret")){
    				weixin.appSecret = prop.getProperty("appSecret").toString();  
    			}
    			if(null != prop.getProperty("siteName")){
    				//siteName= prop.getProperty("siteName").toString();
    			}
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }
    }
    
    public void init(ServletConfig servletConfig) throws ServletException {
    	ServletContext servletContext = servletConfig.getServletContext();
    	WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    	//AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
    	
    	new SpringContextUtil().setApplicationContext(webApplicationContext);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String signature = "";
	    String timestamp = "";
	    String nonce = "";
	    String echostr = "";
	    if(null != request.getParameter("signature"))
	    {
	    	signature = request.getParameter("signature");
	    }
	    if(null != request.getParameter("timestamp"))
	    {
	    	timestamp = request.getParameter("timestamp");
	    }
	    if(null != request.getParameter("nonce"))
	    {
	    	nonce = request.getParameter("nonce");
	    }
	    if(null != request.getParameter("echostr"))
	    {
	    	echostr = request.getParameter("echostr");
	    }

	    response.setContentType("text/html; charset=UTF-8");
	    response.setCharacterEncoding( "UTF-8" );
	    
	    logger.error("doGet-----Msg{}","sign:"+signature+" timestamp:"+timestamp+" nonce:"+nonce);
	    
	   //验证链接
	    if(weixin.valid(signature,timestamp,nonce)){
	    	logger.info("WxServlet valid pass (get)");
	    }else{
	    	logger.warn("WxServlet", "valid failed,sign:"+signature+" timestamp:"+timestamp+" nonce:"+nonce);
	    }
	    response.getWriter().println(echostr);
		response.flushBuffer();
	    
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.debug("doPost weixin");
		
	    //this.useView=false;
	    
	    String signature = "";
	    String timestamp = "";
	    String nonce = "";
	    if(null != request.getParameter("signature"))
	    {
	    	signature = request.getParameter("signature");
	    }
	    if(null != request.getParameter("timestamp"))
	    {
	    	timestamp = request.getParameter("timestamp");
	    }
	    if(null != request.getParameter("nonce"))
	    {
	    	nonce = request.getParameter("nonce");
	    }

	    logger.error("doPost-----Msg{}","sign:"+signature+" timestamp:"+timestamp+" nonce:"+nonce);

	    //验证链接
	    if(weixin.valid(signature,timestamp,nonce)){
	    	logger.info("WxServlet valid pass (post)");
	    }else{
	    	logger.warn("WxServlet", "valid failed,sign:"+signature+" timestamp:"+timestamp+" nonce:"+nonce);
	    }
	    
		//分析数据	    
	    StringBuffer jb = new StringBuffer();
	    String line = null;
	    try {
	      BufferedReader reader = request.getReader();
	      while ((line = reader.readLine()) != null)
	        jb.append(line);
	    } catch (Exception e) { 
	    	e.printStackTrace();
	    }
	    String requestContent = jb.toString();	   
	    logger.debug("WxServlet  requestContent :"+requestContent);
	    Map data = weixin.analysis(requestContent);
	    if(data.isEmpty()){
	    	logger.debug("requestContent  analysis empty:"+requestContent);
	    	return ;
	    }

	    //String url=this.appUrl;
	   	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
	   	String datatime =df.format(new Date(System.currentTimeMillis() ).getTime());
	    String msgType=""+ data.get("MsgType");
	    String toUsername =  ""+ data.get("FromUserName");
	    String fromUsername = ""+ data.get("ToUserName");
	    String content="";//strval(data["postObj"].Content);
	    if((null !=data.get("Content"))){
	    	content=data.get("Content").toString();
	    }else{
	    	 content=""+ data.get("postObjContent");
	    }
	    String event="";
	    String eventKey="";
	    if((null !=data.get("Event"))){
	    	event=data.get("Event").toString();
	    }else{
	    	event=""+ data.get("postObjEvent");
	    }
	    if((null !=data.get("EventKey"))){
	    	eventKey=data.get("EventKey").toString();
	    }else{
	    	eventKey=""+ data.get("postObjEventKey");
	    }
	    
	    response.setContentType("text/html; charset=UTF-8");
	    response.setCharacterEncoding( "UTF-8" );
	    
	    logger.debug("WxServlet msgType:"+msgType+" content:"+content+"--eventKey:"+eventKey+"---event:"+event);
	    
	    switch (msgType){
		    //事件推送
		    case "event":
		    {
			    //event值的类型：subscribe(订阅)、unsubscribe(取消订阅)、LOCATION(地理位置)、CLICK(菜单事件)
				//eventKey值的类型:如果要区分关注来源（搜索/二维码来源）搜索来源eventkey为空，二维码来源以qrscene_为前缀，后面为二维码的参数值（qrscene_value）
				if(0 == event.compareToIgnoreCase("subscribe")){//订阅事件	
					String xml2 = getsubscribeResp(fromUsername,toUsername,request);	
					response.getWriter().println(xml2);
					response.flushBuffer();
					
					String openid = toUsername;
					//绑定openid与paretnOpenId
					
					WxLoginService wxLoginService = null;
			    	if(null != SpringContextUtil.getBean("wxLoginServiceImpl")){
			    		wxLoginService = (WxLoginService)SpringContextUtil.getBean("wxLoginServiceImpl");
			    	}
			    	if(null!=wxLoginService){
			    		WxUser wxUser = weixin.getUserInfo(openid);
			    		wxUser.setOpenid(openid);
			    		wxLoginService.bandingUser(wxUser, "");
			    		//将openid存进cook
			    		HashSessions.getInstance().setOpenId(request, response, openid);
			    	}
					
					return;
				}else if(0==event.compareToIgnoreCase("CLICK")){//菜单事件
					String xml= getServiceResp(eventKey,fromUsername,toUsername,request);
					if(xml.isEmpty()){
						xml = getsubscribeResp(fromUsername,toUsername,request);
					}
					response.getWriter().println(xml);
				}
		    }
			break;
		    //接收被动信息
		    case "text":		
		    {
		    	String xml= getKeywordResp(content,fromUsername,toUsername,request);
		    	if(xml.isEmpty()){
		    		//针对zzk注释了下面的代码，找不到关键字时回复的内容
					/*String datacontent="请稍等哟，想看更多出发地目的地嘛？点击左下角【预约优选】按钮";
                	xml="<xml>"+
                        "<FromUserName><![CDATA[%s]]></FromUserName>"+
                        "<ToUserName><![CDATA[%s]]></ToUserName>"+
                        "<CreateTime>%s</CreateTime>"+
                        "<MsgType><![CDATA[text]]></MsgType>"+
                        "<Content><![CDATA[%s]]></Content>"+
                        "</xml>";
                	Formatter fmt = new Formatter();
                    xml = fmt.format(xml,fromUsername,toUsername,datatime,datacontent).toString();*/
				}
				logger.debug(xml);
				response.getWriter().println(xml);
		    }
            break;
	    }
    }
	
	protected String getsubscribeResp(String fromUsername, String toUsername,HttpServletRequest request){
		FollowRespServices followrespService = null;
    	if(null != SpringContextUtil.getBean("followRespServicesSupport")){
    		followrespService = (FollowRespServices)SpringContextUtil.getBean("followRespServicesSupport");
    	}
    	
    	if(null != followrespService){
    		List<FollowResp> list = new ArrayList<FollowResp>();
			try {
				list = followrespService.listEntity(new HashMap<String, Object>());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
        	if(list.size()>0){
        		FollowResp resp = null;
        		if(list.get(0) instanceof FollowResp){
        			resp = (FollowResp)list.get(0);
        		}
        		
        		if(null != resp){
        			if(2 == resp.getResptype()){
        				//多图文
        				String  xmlhead = "<xml>"+
                        		"<ToUserName><![CDATA[%s]]></ToUserName>"+
                        		"<FromUserName><![CDATA[%s]]></FromUserName>"+
                        		"<CreateTime>%s</CreateTime>"+
                        		"<MsgType><![CDATA[news]]></MsgType>"+
                        		"<ArticleCount>%d</ArticleCount>"+
                        		"<Articles>";
                        		
        				String  xmltail = "</Articles>"+
                        		"</xml>";
                    	
                    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                    	String datatime =df.format(new Date(System.currentTimeMillis() ).getTime());                	
                    	
                    	Formatter fmt = new Formatter();                	
                        String xml = fmt.format(xmlhead,toUsername,fromUsername,datatime,resp.getExlist().size()).toString();
                        for(FollowRespEx ex:resp.getExlist()){
                        	String imgUrl = handleImgUrl(request,ex.getImg());
                        	xml += "<item>"+
                             		"<Title><![CDATA["+ ex.getTitle()+"]]></Title>"+
                             		"<Description><![CDATA["+ ex.getDes()+"]]></Description>"+
                             		"<PicUrl><![CDATA["+imgUrl+"]]></PicUrl>"+
                             		"<Url><![CDATA["+ ex.getUrl()+"]]></Url>"+
                             		"</item>";
                        }
                        xml += xmltail;
                        logger.debug(xml);
                        return xml;
        				
        			}else if(1 == resp.getResptype()){
        				//单图文
        				String  xml = "<xml>"+
                        		"<ToUserName><![CDATA[%s]]></ToUserName>"+
                        		"<FromUserName><![CDATA[%s]]></FromUserName>"+
                        		"<CreateTime>%s</CreateTime>"+
                        		"<MsgType><![CDATA[news]]></MsgType>"+
                        		"<ArticleCount>1</ArticleCount>"+
                        		"<Articles>"+
                        		"<item>"+
                        		"<Title><![CDATA[%s]]></Title>"+
                        		"<Description><![CDATA[%s]]></Description>"+
                        		"<PicUrl><![CDATA[%s]]></PicUrl>"+
                        		"<Url><![CDATA[%s]]></Url>"+
                        		"</item>"+                    		
                        		"</Articles>"+
                        		"</xml>";
                    	
                    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                    	String datatime =df.format(new Date(System.currentTimeMillis() ).getTime());                	
                    	
                    	Formatter fmt = new Formatter();
                    	String imgUrl = handleImgUrl(request,resp.getImg());
                        xml = fmt.format(xml,toUsername,fromUsername,datatime,resp.getTitle(),resp.getDes(),imgUrl,resp.getUrl()).toString();
                        logger.debug(xml);
                        return xml;
        			}else {
        				//文字
        				String xml="<xml>"+
        						"<ToUserName><![CDATA[%s]]></ToUserName>"+
                                "<FromUserName><![CDATA[%s]]></FromUserName>"+                                
                                "<CreateTime>%s</CreateTime>"+
                                "<MsgType><![CDATA[text]]></MsgType>"+
                                "<Content><![CDATA[%s]]></Content>"+
                                "</xml>";
                    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                    	String datatime =df.format(new Date(System.currentTimeMillis() ).getTime());                	
                    	
                    	Formatter fmt = new Formatter();
                        xml = fmt.format(xml,toUsername,fromUsername,datatime,resp.getContent()).toString();
                        logger.debug(xml);
                        return xml;
        			}
        		}
        	}
    	}
    	
    	return "";
	}
	
    protected String getKeywordResp(String keyword, String fromUsername, String toUsername,HttpServletRequest request){
    	KeywordRespServices keywordService = null;
    	if(null != SpringContextUtil.getBean("keywordRespServicesSupport")){
    		keywordService = (KeywordRespServices)SpringContextUtil.getBean("keywordRespServicesSupport");
    	}
    	
    	if(null != keywordService){
    		KeywordResp resp = keywordService.findByKeywordLike(keyword);
    		if(null != resp){
    			if(2 == resp.getResptype()){
    				//多图文
    				String  xmlhead = "<xml>"+
                    		"<ToUserName><![CDATA[%s]]></ToUserName>"+
                    		"<FromUserName><![CDATA[%s]]></FromUserName>"+
                    		"<CreateTime>%s</CreateTime>"+
                    		"<MsgType><![CDATA[news]]></MsgType>"+
                    		"<ArticleCount>%d</ArticleCount>"+
                    		"<Articles>";
                    		
    				String  xmltail = "</Articles>"+
                    		"</xml>";
                	
                	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                	String datatime =df.format(new Date(System.currentTimeMillis() ).getTime());                	
                	
                	Formatter fmt = new Formatter();                	
                    String xml = fmt.format(xmlhead,toUsername,fromUsername,datatime,resp.getExlist().size()).toString();
                    for(KeywordRespEx ex:resp.getExlist()){
                    	String imgUrl = handleImgUrl(request,ex.getImg());
                    	xml += "<item>"+
                         		"<Title><![CDATA["+ ex.getTitle()+"]]></Title>"+
                         		"<Description><![CDATA["+ ex.getDesc()+"]]></Description>"+
                         		"<PicUrl><![CDATA["+imgUrl+"]]></PicUrl>"+
                         		"<Url><![CDATA["+ ex.getUrl()+"]]></Url>"+
                         		"</item>";
                    }
                    xml += xmltail;
                    logger.debug(xml);
                    return xml;
    				
    			}else if(1 == resp.getResptype()){
    				//单图文
    				String  xml = "<xml>"+
                    		"<ToUserName><![CDATA[%s]]></ToUserName>"+
                    		"<FromUserName><![CDATA[%s]]></FromUserName>"+
                    		"<CreateTime>%s</CreateTime>"+
                    		"<MsgType><![CDATA[news]]></MsgType>"+
                    		"<ArticleCount>1</ArticleCount>"+
                    		"<Articles>"+
                    		"<item>"+
                    		"<Title><![CDATA[%s]]></Title>"+
                    		"<Description><![CDATA[%s]]></Description>"+
                    		"<PicUrl><![CDATA[%s]]></PicUrl>"+
                    		"<Url><![CDATA[%s]]></Url>"+
                    		"</item>"+                    		
                    		"</Articles>"+
                    		"</xml>";
                	
                	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                	String datatime =df.format(new Date(System.currentTimeMillis() ).getTime());                	
                	
                	Formatter fmt = new Formatter();
                	String imgUrl = handleImgUrl(request,resp.getImg());
                    xml = fmt.format(xml,toUsername,fromUsername,datatime,resp.getTitle(),resp.getDesc(),imgUrl,resp.getUrl()).toString();
                    logger.debug(xml);
                    return xml;
    			}else {
    				//文字
    				String xml="<xml>"+
    						"<ToUserName><![CDATA[%s]]></ToUserName>"+
                            "<FromUserName><![CDATA[%s]]></FromUserName>"+                            
                            "<CreateTime>%s</CreateTime>"+
                            "<MsgType><![CDATA[text]]></MsgType>"+
                            "<Content><![CDATA[%s]]></Content>"+
                            "</xml>";
                	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                	String datatime =df.format(new Date(System.currentTimeMillis() ).getTime());                	
                	
                	Formatter fmt = new Formatter();
                    xml = fmt.format(xml,toUsername,fromUsername,datatime,resp.getContent()).toString();
                    logger.debug(xml);
                    return xml;
    			}
    		}
    	}
    	
    	return "";
    }
    
    protected String getServiceResp(String keyword, String fromUsername, String toUsername,HttpServletRequest request){
    	KeywordRespServices keywordService = null;
    	if(null != SpringContextUtil.getBean("keywordRespServicesSupport")){
    		keywordService = (KeywordRespServices)SpringContextUtil.getBean("keywordRespServicesSupport");
    	}
    	
    	if(null != keywordService){
    		KeywordResp resp = keywordService.findByKeyword(keyword);
    		if(null != resp){
    			if(2 == resp.getResptype()){
    				//多图文
    				String  xmlhead = "<xml>"+
                    		"<ToUserName><![CDATA[%s]]></ToUserName>"+
                    		"<FromUserName><![CDATA[%s]]></FromUserName>"+
                    		"<CreateTime>%s</CreateTime>"+
                    		"<MsgType><![CDATA[news]]></MsgType>"+
                    		"<ArticleCount>%d</ArticleCount>"+
                    		"<Articles>";
                    		
    				String  xmltail = "</Articles>"+
                    		"</xml>";
                	
                	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                	String datatime =df.format(new Date(System.currentTimeMillis() ).getTime());                	
                	
                	Formatter fmt = new Formatter();                	
                    String xml = fmt.format(xmlhead,toUsername,fromUsername,datatime,resp.getExlist().size()).toString();
                    for(KeywordRespEx ex:resp.getExlist()){
                    	String imgUrl = handleImgUrl(request,ex.getImg());
                    	xml += "<item>"+
                         		"<Title><![CDATA["+ ex.getTitle()+"]]></Title>"+
                         		"<Description><![CDATA["+ ex.getDesc()+"]]></Description>"+
                         		"<PicUrl><![CDATA["+imgUrl+"]]></PicUrl>"+
                         		"<Url><![CDATA["+ ex.getUrl()+"]]></Url>"+
                         		"</item>";
                    }
                    xml += xmltail;
                    logger.debug(xml);
                    return xml;
    				
    			}else if(1 == resp.getResptype()){
    				//单图文
    				String  xml = "<xml>"+
                    		"<ToUserName><![CDATA[%s]]></ToUserName>"+
                    		"<FromUserName><![CDATA[%s]]></FromUserName>"+
                    		"<CreateTime>%s</CreateTime>"+
                    		"<MsgType><![CDATA[news]]></MsgType>"+
                    		"<ArticleCount>1</ArticleCount>"+
                    		"<Articles>"+
                    		"<item>"+
                    		"<Title><![CDATA[%s]]></Title>"+
                    		"<Description><![CDATA[%s]]></Description>"+
                    		"<PicUrl><![CDATA[%s]]></PicUrl>"+
                    		"<Url><![CDATA[%s]]></Url>"+
                    		"</item>"+                    		
                    		"</Articles>"+
                    		"</xml>";
                	
                	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                	String datatime =df.format(new Date(System.currentTimeMillis() ).getTime());                	
                	
                	Formatter fmt = new Formatter();
                	String imgUrl = handleImgUrl(request,resp.getImg());
                    xml = fmt.format(xml,toUsername,fromUsername,datatime,resp.getTitle(),resp.getDesc(),imgUrl,resp.getUrl()).toString();
                    logger.debug(xml);
                    return xml;
    			}else {
    				//文字
    				String xml="<xml>"+
    						"<ToUserName><![CDATA[%s]]></ToUserName>"+
                            "<FromUserName><![CDATA[%s]]></FromUserName>"+                            
                            "<CreateTime>%s</CreateTime>"+
                            "<MsgType><![CDATA[text]]></MsgType>"+
                            "<Content><![CDATA[%s]]></Content>"+
                            "</xml>";
                	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                	String datatime =df.format(new Date(System.currentTimeMillis() ).getTime());                	
                	
                	Formatter fmt = new Formatter();
                    xml = fmt.format(xml,toUsername,fromUsername,datatime,resp.getContent()).toString();
                    logger.debug(xml);
                    return xml;
    			}
    		}
    	}
    	
    	return "";
    }
    
    private String handleImgUrl(HttpServletRequest request,String url){
    	url = StringUtils.trimToEmpty(url);
    	if(url.indexOf("http:") > -1) return url;
    	
    	String imgUrl = request.getRequestURL().toString();
    	imgUrl = imgUrl.replace("http://", "");
		imgUrl = imgUrl.substring(0, imgUrl.indexOf("/"));
		imgUrl = "http://"+imgUrl+url;
		
		return imgUrl;
    }
    
    public static void main(String[] args) {
		String ss = "<xml><ToUserName><![CDATA[olcZ7xDY4UHH1g3WhNbqO4HZBMPg]]></ToUserName><FromUserName><![CDATA[gh_37628f726b97]]></FromUserName><CreateTime>20161027 11:21:00</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[此公众号正在开发阶段，给您带来的不便尽情谅解>，我们会尽快研发完成为您服务。]]></Content></xml>";
		
		
		Map<String,Object> map = XmlConverUtils.xml2Map(ss);
		
		System.out.println(map.get("ToUserName"));
	}
}
