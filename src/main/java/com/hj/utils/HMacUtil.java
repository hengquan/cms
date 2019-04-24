package com.hj.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HMacUtil {

    private static final Logger logger = LoggerFactory.getLogger(HMacUtil.class);
	
	public static String generateKey() {
		try {
			KeyGenerator gen = KeyGenerator.getInstance("HmacSHA1");
			SecretKey key = gen.generateKey();
			return base64Encode(key.getEncoded());
		} catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
		}
		return null;
	}

	private static String base64Encode(byte[] data) {
		return Base64.encodeBase64String(data);
	}

	private static byte[] base64Decode(String data) {
		return Base64.decodeBase64(data);
	}

	public static String encryptHMAC(String data, String key) {
		SecretKey secretKey;
		byte[] bytes = null;

		secretKey = new SecretKeySpec(base64Decode(key), "HmacSHA1");
		try {
			Mac mac = Mac.getInstance(secretKey.getAlgorithm());
			mac.init(secretKey);
			bytes = mac.doFinal(data.getBytes());
			return DigestUtils.sha1Hex(bytes);
		} catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
		} catch (InvalidKeyException e) {
            logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println("result:"+encryptHMAC("1234567","p3Bo9HQfdVTqij/gWTrkPbLK2oIB15KCsmDLOUM//M9YCgCEuU/Jv6gkbBMtcO2WbO39HjCjfMSYduRwwTPO0g=="));
	}
}
