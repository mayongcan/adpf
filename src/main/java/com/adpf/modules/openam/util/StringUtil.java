package com.adpf.modules.openam.util;

import java.io.UnsupportedEncodingException;

/**
 * 
 * @author chenzhenyong
 *
 */
public class StringUtil {

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 将十六进制文本转换为byte数组
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] hexToBytes(String str) {
		if (str == null) {
			return null;
		}
		char[] hex = str.toCharArray();
		int length = hex.length / 2;
		byte[] raw = new byte[length];
		for (int i = 0; i < length; i++) {
			int high = Character.digit(hex[i * 2], 16);
			int low = Character.digit(hex[i * 2 + 1], 16);
			int value = (high << 4) | low;
			if (value > 127)
				value -= 256;
			raw[i] = (byte) value;
		}
		return raw;
	}

	private final static char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	public static byte[] getBytes(String data) {
		String encoding = "UTF-8";
		byte[] bytes = new byte[] {};
		try {
			bytes = data.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return bytes;
	}

	private static final char digits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static String toHex(byte byteData[]) {
		return toHex(byteData, 0, byteData.length);
	}

	public static String toHex(String data, String encode) {
		try {
			return toHex(data.getBytes(encode));
		} catch (Exception e) {
		}
		return "";
	}

	public static String toHex(byte byteData[], int offset, int len) {
		char buf[] = new char[len * 2];
		int k = 0;
		for (int i = offset; i < len; i++) {
			buf[k++] = digits[(byteData[i] & 255) >> 4];
			buf[k++] = digits[byteData[i] & 15];
		}

		return new String(buf);
	}

}
