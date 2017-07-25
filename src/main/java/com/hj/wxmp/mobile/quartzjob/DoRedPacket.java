package com.hj.wxmp.mobile.quartzjob;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hj.wxmp.mobile.common.Weixin;
import com.hj.wxmp.mobile.dao.UserDao;
import com.hj.wxmp.mobile.mapping.UserInfoMapper;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.UserInfoService;

public class DoRedPacket {

	private final static Logger logger = LoggerFactory.getLogger(DoRedPacket.class);

	@Autowired
	UserInfoMapper sysUserMapper;
	@Autowired
	UserDao userDao;
	@Autowired
	UserInfoService userInfoService;
	@Resource
	IKeyGen keyGen;

	private Weixin weixin = Weixin.getInstance();

	public void doRedPacket() {

		logger.info("开始执行定时任务");

		try {
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
	}

}
