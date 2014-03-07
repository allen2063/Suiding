package com.suiding.util;

import java.util.Random;

public class AuthCodeUtil {

	private static Random random = new Random();
	
	public static String NewCode(){
		StringBuilder sb = new StringBuilder();
		sb.append(random.nextInt(10));
		sb.append(random.nextInt(10));
		sb.append(random.nextInt(10));
		sb.append(random.nextInt(10));
		return sb.toString();
	}
}
