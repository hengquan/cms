package com.hj.web.quartzjob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OrderPastDue {

	private final static Logger logger = LoggerFactory.getLogger(OrderPastDue.class);

	
	public void doPastDue() {
		logger.info("开始执行定时任务");
		try {
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
	}
	
	
}
