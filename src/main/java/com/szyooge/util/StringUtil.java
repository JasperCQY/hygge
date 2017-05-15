package com.szyooge.util;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {
	private static Logger logger = LoggerFactory.getLogger(StringUtil.class);
	private static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
            'a', 'b', 'c', 'd', 'e', 'f'};
	
	public static byte[] getBytes(String str) {
		if(str != null) {
			try {
				return str.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("系统不支持UTF-8",e);
			}
		}
		return null;
	}
	/**
	 * 转16进制字符串
	 * @param bytes
	 * @return
	 */
	public static String toHexString(byte[] bytes){
		if(bytes == null) {
			return null;
		}
        int j = bytes.length;
        char[] buf = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = bytes[i];
            buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
            buf[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(buf);
	}
}
