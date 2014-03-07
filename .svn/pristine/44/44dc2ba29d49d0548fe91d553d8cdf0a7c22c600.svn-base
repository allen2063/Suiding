package com.suiding.util;

import java.util.HashMap;
import java.util.UUID;

public class ExtraUtil {

	private static HashMap<String, Object> mHashMap = new HashMap<String, Object>();
//	private static HashMap<String, Integer> mHashMapInteger = new HashMap<String, Integer>();
//	private static HashMap<String, Long> mHashMapLong = new HashMap<String, Long>();
//	private static HashMap<String, Float> mHashMapFloat = new HashMap<String, Float>();
//	private static HashMap<String, Integer> mHashMapInteger = new HashMap<String, Integer>();
//	private static HashMap<String, Short> mHashMapShort = new HashMap<String, Short>();

	public static void putExtra(String key, Object value) {
		mHashMap.put(key, value);
	}

	public static Object getExtra(String key) {
		return getExtra(key, null);
	}

	public static Object getExtra(String key, Object Default) {
		Object obj = mHashMap.get(key);
		if (obj != null) {
			mHashMap.remove(key);
			return obj;
		}
		return Default;
	}

	public static String getExtraString(String key, String Default) {
		Object obj = mHashMap.get(key);
		if (obj != null && obj instanceof String) {
			mHashMap.remove(key);
			return obj.toString();
		}
		return Default;
	}

	public static short getExtraShort(String key, short Default) {
		// TODO Auto-generated method stub
		Object obj = mHashMap.get(key);
		if (obj != null && obj instanceof Short) {
			mHashMap.remove(key);
			return (Short)obj;
		}
		return Default;
	}

	public static boolean getExtraBoolean(String key, boolean Default) {
		// TODO Auto-generated method stub
		Object obj = mHashMap.get(key);
		if (obj != null && obj instanceof Boolean) {
			mHashMap.remove(key);
			return (Boolean)obj;
		}
		return Default;
	}

	public static int getExtraInt(String key, int Default) {
		// TODO Auto-generated method stub
		Object obj = mHashMap.get(key);
		if (obj != null && obj instanceof Integer) {
			mHashMap.remove(key);
			return (Integer)obj;
		}
		return Default;
	}

	public static long getExtraLong(String key, long Default) {
		// TODO Auto-generated method stub
		Object obj = mHashMap.get(key);
		if (obj != null && obj instanceof Long) {
			mHashMap.remove(key);
			return (Long)obj;
		}
		return Default;
	}

	public static float getExtraFloat(String key, float Default) {
		// TODO Auto-generated method stub
		Object obj = mHashMap.get(key);
		if (obj != null && obj instanceof Float) {
			mHashMap.remove(key);
			return (Float)obj;
		}
		return Default;
	}

	public static double getExtraDouble(String key, double Default) {
		// TODO Auto-generated method stub
		Object obj = mHashMap.get(key);
		if (obj != null && obj instanceof Double) {
			mHashMap.remove(key);
			return (Double)obj;
		}
		return Default;
	}

	public static UUID getExtraUUID(String key, UUID Default) {
		// TODO Auto-generated method stub
		Object obj = mHashMap.get(key);
		if (obj != null && obj instanceof UUID) {
			mHashMap.remove(key);
			return (UUID)obj;
		}
		return Default;
	}

}
