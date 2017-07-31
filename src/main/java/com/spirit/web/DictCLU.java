package com.spirit.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spirit.core.dict.mem._CacheDictionary;
import com.spirit.core.dict.service.DictService;
import com.spiritdata.framework.core.cache.AbstractCacheLifecycleUnit;
import com.spiritdata.framework.core.cache.CacheEle;
import com.spiritdata.framework.core.cache.SystemCache;

public class DictCLU extends AbstractCacheLifecycleUnit {
    private Logger logger=LoggerFactory.getLogger(this.getClass());

    @Resource
    private DictService dictService;

    @Override
    public void init() {
        //装载数据字典
        try {
            System.out.println("开始装载[系统字典]缓存");
            SystemCache.remove("CACHE_DICT");
            SystemCache.setCache(new CacheEle<_CacheDictionary>("CACHE_DICT", "系统字典", dictService.loadCache()));
        } catch (Exception e) {
            logger.info("启动时加载{Wt内容平台}缓存出错", e);
        }
    }

    @Override
    public void refresh(String key) {
    }
}