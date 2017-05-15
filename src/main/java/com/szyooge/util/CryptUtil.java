package com.szyooge.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptUtil {
	private static Logger logger = LoggerFactory.getLogger(CryptUtil.class);

	public static byte[] SHA1(byte[] bytes) {
		MessageDigest mdTemp;
		try {
			mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(bytes);
			return mdTemp.digest();
		} catch (NoSuchAlgorithmException e) {
			logger.error("系统不支持SHA1算法", e);
			return null;
		}
	}

	public static byte[] SHA1(String str) {
		return SHA1(StringUtil.getBytes(str));
	}

	public static String SHA1ToHex(String str) {
		return StringUtil.toHexString(SHA1(StringUtil.getBytes(str)));
	}
}
