package com.hj.wxmp.mobile.quartzjob;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.hj.wxmp.mobile.dao.UserDao;
import com.hj.wxmp.mobile.entity.AccessRecord01;
import com.hj.wxmp.mobile.entity.AccessRecord02;
import com.hj.wxmp.mobile.entity.AccessRecord03;
import com.hj.wxmp.mobile.mapping.UserInfoMapper;
import com.hj.wxmp.mobile.services.AccessRecord01Service;
import com.hj.wxmp.mobile.services.AccessRecord02Service;
import com.hj.wxmp.mobile.services.AccessRecord03Service;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.UserInfoService;

public class DoQuarzt {

	private final static Logger logger = LoggerFactory.getLogger(DoQuarzt.class);

	@Autowired
	UserInfoMapper sysUserMapper;
	@Autowired
	UserDao userDao;
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	AccessRecord01Service accessRecord01Service;
	@Autowired
	AccessRecord02Service accessRecord02Service;
	@Autowired
	AccessRecord03Service accessRecord03Service;
	@Resource
	IKeyGen keyGen;
	
	
	public void doWork() {
		logger.info("开始执行定时任务");
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = format.format(new Date());
			//当天所有首访记录
			AccessRecord01 accessRecord01 = accessRecord01Service.selectByRecepTime(date);
			//当天所有复访记录
			AccessRecord02 accessRecord02 = accessRecord02Service.selectByRecepTime(date);
			//当天所有成交记录
			AccessRecord03 accessRecord03 = accessRecord03Service.selectByRecepTime(date);
			
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
	}
	
}
