package com.jian.core.model.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.util.Random;

/**
 * 一个简单的md5加密的类
 * @author shen
 *
 */
public class Mdfive {

	public Mdfive() {
	}

	public static String md(String pwd) {
		
		String salt = "7038108885666460";
 		pwd = md5Hex(pwd + salt);
 		char[] cs = new char[48];
 		for (int i = 0; i < 48; i += 3) {
 			cs[i] = pwd.charAt(i / 3 * 2);
 			char c = salt.charAt(i / 3);
 			cs[i + 1] = c;
 			cs[i + 2] = pwd.charAt(i / 3 * 2 + 1);
 		}
		return new String(cs);
	}
	
		public static String randomMd5() {
			String token = "今天你要嫁给我!";
			Random r = new Random();
	 		StringBuilder sb = new StringBuilder(16);
	 		sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
	 		int len = sb.length();
	 		if (len < 16) {
	 			for (int i = 0; i < 16 - len; i++) {
	 				sb.append("0");
	 			}
	 		}
	 		String salt = sb.toString();
	 		token = md5Hex(token + salt);
	 		char[] cs = new char[48];
	 		for (int i = 0; i < 48; i += 3) {
	 			cs[i] = token.charAt(i / 3 * 2);
	 			char c = salt.charAt(i / 3);
	 			cs[i + 1] = c;
	 			cs[i + 2] = token.charAt(i / 3 * 2 + 1);
	 		}
			return new String(cs);
		}

		/**
		 * 获取十六进制字符串形式的MD5摘要
		 */
		private static String md5Hex(String src) {
			try {
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				byte[] bs = md5.digest(src.getBytes());
				return new String(new Hex().encode(bs));
			} catch (Exception e) {
				return null;
			}
		}

}