package com.hj.web.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hj.wxmp.mobile.common.Configurations;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.entity.UserInfo;
import com.hj.wxmp.mobile.services.UserInfoService;
import com.spiritdata.framework.ext.spring.SpringGetBean;

@Component
public class QlWeixinMenuFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(QlWeixinMenuFilter.class);
    private HashSessions hashSession = HashSessions.getInstance();
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	 logger.debug("requestURL000","THIS IS OK");
    	HttpServletRequest req=(HttpServletRequest)request;
        HttpServletResponse res=(HttpServletResponse)response;

        String requestURL = req.getRequestURL().toString();
        String[] urls = requestURL.split("/wxpage");
        String realURL = "";
        for(String url : urls) {
        	realURL+=url;
        }
        logger.debug("requestURL001",requestURL);
        String siteName=Configurations.getSiteName();
        String url="";
        try {
            UserInfoService userInfoService=SpringGetBean.getBean("userInfoServiceImpl");
            if (userInfoService==null) {
            	url=siteName+"/wxmp.ql/wxfront/err.html?6000=服务器正在加载，请等待";
            } else {
                String openid=HashSessions.getInstance().getOpenId(req);
            	if (openid==null||"".equals(openid)) {
            		logger.debug("siteName:{}",siteName);
            		url=siteName+"/wxmp.ql/wx/api/redirectUrl.az?wx_url="+requestURL;
            		logger.debug("url:{}",url);
            	} else {
                    UserInfo userInfo=userInfoService.findByOpenid(openid);
                    if (userInfo==null) {
                    	hashSession.setOpenId(req, res, "");
                    	url=siteName+"/wxmp.ql/wx/api/redirectUrl.az?wx_url="+requestURL;
                    	//url=siteName+"/wxmp.ql/wxfront/user/register.html";
                    }
                    else {
                    	String realname = userInfo.getRealname();
                        String phone = userInfo.getMainphonenum();
                        String loginname = userInfo.getLoginname();
                        String password = userInfo.getPassword();
                        Integer isvalidate = userInfo.getIsvalidate();
                    	if(realname==null||phone==null||loginname==null||password==null) {
                    		url=siteName+"/wxmp.ql/wxfront/user/register.html";
                    	} else {
                    		if (isvalidate==0) {
                    			if (realURL.indexOf("info.html")==-1) url=siteName+"/wxmp.ql/wxfront/err.html?0002";
                    		}
                    		else if (isvalidate==2) url=siteName+"/wxmp.ql/wxfront/err.html?0003";
                    		else if (isvalidate==3) url=siteName+"/wxmp.ql/wxfront/err.html?0001";
                    	}
                    }
            	}
            }
        } catch(Exception e) {
        	url=siteName+"/wxmp.ql/wxfront/err.html?7000=过滤器异常<br/>"+e.getMessage();
        }
        logger.debug("url002",url);
        if (url.length()==0) url=realURL;
        logger.debug("url003",url);
    	res.sendRedirect(URLDecoder.decode(url, "UTF-8"));
//
//        
//        String requestURL = req.getRequestURL().toString();
//        String[] urls = requestURL.split("/wxpage");
//        String reqRUL = "";
//        for(String url : urls){
//        	reqRUL+=url;
//        }
//        try {
//        	DispatchClass dc=new DispatchClass();
//        	dc.verify(reqRUL);
//            String siteName = Configurations.getSiteName();
//            siteName += "/wxmp.ql/wx/api/verifyMsg?requestURL="+reqRUL;
//            res.sendRedirect(siteName);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        
    }

}