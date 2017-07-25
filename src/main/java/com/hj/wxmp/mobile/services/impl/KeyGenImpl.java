package com.hj.wxmp.mobile.services.impl;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.hj.utils.UUIDGenerator;
import com.hj.wxmp.mobile.services.IKeyGen;

@Component
public class KeyGenImpl implements IKeyGen {

	@Override
	public Long getLongKey() {
		return UUIDGenerator.getUniqueLong();
	}

	@Override
	public String getUUIDKey() {
		return UUIDGenerator.generate(new Date());
	}

	@Override
	public String getUUIDKey(Object obj) {
		return UUIDGenerator.generate(obj);
	}

}
