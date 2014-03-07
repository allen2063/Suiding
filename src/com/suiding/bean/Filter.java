package com.suiding.bean;

import java.util.HashMap;

public class Filter extends HashMap<String, Object> {
	
	private static final long serialVersionUID = 4274456590490256160L;

	public static final String NAME = "Name";
	public static final String VALUE = "Value";

	public Filter(String Name, Object value) {
		put(NAME, Name);
		put(VALUE, value);
	}

	public void setName(String Name) {
		put(NAME, Name);
	}

	public void setValue(Object value) {
		put(VALUE,value);
	}

	public String getName() {
		return get(NAME).toString();
	}

	public Object getValue() {
		return get(VALUE);
	}

	public int getInt() {
		Object obj = get(VALUE);
		if(obj instanceof Integer){
			return (Integer)obj;
		}
		return 0;
	}

	public long getLong() {
		Object obj = get(VALUE);
		if(obj instanceof Long){
			return (Long)obj;
		}
		return 0;
	}

}
