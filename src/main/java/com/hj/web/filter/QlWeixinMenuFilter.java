package com.hj.web.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hj.wxmp.core.WxUser;
import com.hj.wxmp.mobile.common.Configurations;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.common.Weixin;
import com.hj.wxmp.mobile.entity.UserInfo;
import com.hj.wxmp.mobile.services.UserInfoService;
import com.hj.wxmp.mobile.services.WxLoginService;

@Component 
public class QlWeixinMenuFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(QlWeixinMenuFilter.class);

    @Resource
    UserInfoService userInfoService;
    @Resource
    WxLoginService wxLoginService;

    // 根据openid去腾讯后台获取用户信息并更新本地userinfo
    private void updateUserInfo(String openid,HttpServletRequest req) {
        if (StringUtils.trimToNull(openid) != null) {
            WxUser wxUser = Weixin.getInstance().getUserInfo(openid);
            wxLoginService.bandingUser(wxUser, ObjectUtils.toString(
                    req.getSession().getAttribute("weixin_current_url")));
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        getBean(request);
        HttpServletRequest req=(HttpServletRequest)request;
        HttpServletResponse res=(HttpServletResponse)response;
        String requestURL = req.getRequestURL().toString();
        try {
            String openid = HashSessions.getInstance().getOpenId(req);
            openid = "oaBNt0xKNjXvStRlbKqMnk7QQ2Pw";
            logger.debug("this---openid:{}",openid);
            if (openid == null || "".equals(openid)) {
                this.getOpenid(res,requestURL,req);
            }else{
                Integer resultData = userMsg(openid);
                if(resultData==100){
                    res.sendRedirect(requestURL);
                }else if(resultData==103){
                    String siteName = Configurations.getSiteName();
                    siteName += "/wxmp.ql/wxfront/user/register.html";
                    res.sendRedirect(siteName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        
    }

    /*
     * 获得需要的Bean对象
     * @param request
     */
    private void getBean(ServletRequest request) {
        if (wxLoginService==null||userInfoService==null) {
            ServletContext sc=request.getServletContext();
            if (userInfoService==null) userInfoService=(UserInfoService)WebApplicationContextUtils.getWebApplicationContext(sc).getBean("userInfoService");
            if (wxLoginService==null) wxLoginService=(WxLoginService)WebApplicationContextUtils.getWebApplicationContext(sc).getBean("wxLoginService");
        }
    }

    //是否有openid
    public void getOpenid(HttpServletResponse response,String requetUrl, HttpServletRequest request) throws Exception {
        getBean(request);
        String openid = HashSessions.getInstance().getOpenId(request);
        if(openid != null && !openid.equals("")){
            updateUserInfo(openid,request);
        }
        String siteName = Configurations.getSiteName();
        String url = siteName+"/wxmp.ql/wx/api/redirectUrl.az?wx_url="+requetUrl;
        logger.debug("messageURL----asdfa{}",url);
        response.sendRedirect(URLDecoder.decode(url, "UTF-8"));
    }

    //通用接口(所有的接口都会来访问该接口看用户信息是否完整和审核是否通过)
    private Integer userMsg(String openid) throws Exception{
        Integer result = 0;
        UserInfo userInfo = userInfoService.findByOpenid(openid);
        String realname = userInfo.getRealname();
        String phone = userInfo.getMainphonenum();
        String loginname = userInfo.getLoginname();
        String password = userInfo.getPassword();
        Integer isvalidate = userInfo.getIsvalidate();
        if(realname!=null && phone !=null && loginname!=null && password!=null){
            if(isvalidate==0){
                result = 104;
            }else if(isvalidate==2){
                result = 105;
            }else{
                result = 100;
            }
        }else{
            result = 103;
        }
        return result;
    }
}