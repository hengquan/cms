package com.hj.web.services;

public interface IKeyGen {
	 public Long getLongKey();

	 public String getUUIDKey();

	 public String getUUIDKey(Object paramObject);
}
