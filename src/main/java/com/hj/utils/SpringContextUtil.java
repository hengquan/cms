package com.hj.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
* @ClassName: SpringContextUtil
* @Description: TODO(Spring配置文件读取管理)
* @author jun.hai
* @date 2015年1月31日 下午3:18:28
*
 */
public class SpringContextUtil implements ApplicationContextAware{
 
    private static ApplicationContext   applicationContext = null;
 
   
    public  void setApplicationContext(ApplicationContext applicationContext){
        SpringContextUtil.applicationContext = applicationContext;
    }
 
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
 
   
    public static Object getBean(String name) throws BeansException{
        return applicationContext.getBean(name);
    }
}