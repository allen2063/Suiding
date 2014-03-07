package com.suiding.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterCheck {
	/**
	 * ��֤����������ʽ�Ƿ����
	 * 
	 * @param email
	 * @return �Ƿ�Ϸ�
	 */
	public static boolean emailFormat(String email) {
		boolean tag = true;
		final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}

//	private static boolean getEmail(String line) {
//		Pattern p = Pattern
//				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
//		Matcher m = p.matcher(line);
//		return m.find();
//	}
}