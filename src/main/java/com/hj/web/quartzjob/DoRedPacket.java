package com.hj.web.quartzjob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoRedPacket {

	private final static Logger logger = LoggerFactory.getLogger(DoRedPacket.class);

	public void doRedPacket() {

		logger.info("开始执行定时任务");

		try {
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
	}

}
