package com.suiding.caches;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.suiding.application.SuidingApp;
import com.suiding.model.ExceptionHandler;


public class ExceptionCache extends SharedPreference
{
	private static final String CACHE_KEY = "ExceptionCache_KEY";
	private static final String CACHE_NAME = "ExceptionCache";

	public ExceptionCache() {
		super(SuidingApp.getApp(),getPath(), CACHE_NAME);
		// TODO Auto-generated constructor stub
	}

	private static String getPath() {
		// TODO Auto-generated method stub
		return SuidingApp.getApp().getCachesPath("Exception");
	}

	public Set<ExceptionHandler> getExceptionHandlerSet(Set<ExceptionHandler> defvalue) {
		// TODO Auto-generated method stub
		Set<String> strset = getStringSet(CACHE_KEY,null);
		if(strset == null){
			return defvalue;
		}
		Gson json = new Gson();
		Set<ExceptionHandler> setHandler = new HashSet<ExceptionHandler>();
		try {
			for (String jvalue : strset) {
				setHandler.add(json.fromJson(jvalue, ExceptionHandler.class));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return setHandler;
	}

	public void putExceptionHandlerSet(Set<ExceptionHandler> sthandler) {
		// TODO Auto-generated method stub
		Gson json = new Gson();
		Set<String> strset = new HashSet<String>();
		for (ExceptionHandler handler : sthandler) {
			strset.add(json.toJson(handler));
		}
		putStringSet(CACHE_KEY, strset);
	}

	public void addExceptionHandlerSet(ExceptionHandler handler) {
		// TODO Auto-generated method stub
		Gson json = new Gson();
		Set<String> strset = getStringSet(CACHE_KEY,new HashSet<String>());
		strset.add(json.toJson(handler));
		putStringSet(CACHE_KEY, strset);
	}
}
