package com.hj.web.filter;

import java.io.IOException;

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

@Component
public class QlWeixinMenuFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(QlWeixinMenuFilter.class);

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)request;
        HttpServletResponse res=(HttpServletResponse)response;
        String requestURL = req.getRequestURL().toString();
        String[] urls = requestURL.split("/wxpage");
        String reqRUL = "";
        for(String url : urls){
        	reqRUL+=url;
        }
        try {
            String siteName = Configurations.getSiteName();
            siteName += "/wxmp.ql/wx/api/verifyMsg?requestURL="+reqRUL;
            res.sendRedirect(siteName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        
    }

   
}