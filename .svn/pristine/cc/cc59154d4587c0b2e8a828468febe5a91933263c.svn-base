package com.suiding.caches;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Set;

import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;


public class SharedPreference
{
    private SharedPreferences mShared = null;

    public SharedPreference(Context context,String name){
    	mShared = context.getSharedPreferences(name,Context.MODE_PRIVATE);
    }
    
    public SharedPreference(Context context,String path,String name){
    	File file = setPreferencesPath(context,new File(path));
    	mShared = context.getSharedPreferences(name,Context.MODE_PRIVATE);
    	setPreferencesPath(context,file);
    }
    
	private File setPreferencesPath(Context context,File file){
		// TODO Auto-generated method stub
		File oldfile = file;
		try {
			Field field;  
			// 获取ContextWrapper对象中的mBase变量。该变量保存了ContextImpl对象  
			field = ContextWrapper.class.getDeclaredField("mBase");  
			field.setAccessible(true);  
			// 获取mBase变量  
			Object obj = field.get(context);  
			// 获取ContextImpl.mPreferencesDir变量，该变量保存了数据文件的保存路径  
			field = obj.getClass().getDeclaredField("mPreferencesDir");  
			field.setAccessible(true);  
			// 创建自定义路径  
			// 修改mPreferencesDir变量的值  
			oldfile = (File) field.get(obj);
			field.set(obj, file);  
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return oldfile;
	}

    public SharedPreferences getSharedPreferences()
    {
    	return mShared;
    }

    public Editor getSharePrefereEditor()
    {
    	return mShared.edit();
    }

    public boolean getBoolean(String key, boolean value)
    {
        return mShared.getBoolean(key, value);
    }

    public String getString(String key, String value)
    {
        return mShared.getString(key, value);
    }

    public float getFloat(String key, float value)
    {
        return mShared.getFloat(key, value);
    }

    public int getInt(String key, int value)
    {
        return mShared.getInt(key, value);
    }

    public long getLong(String key, long value)
    {
        return mShared.getLong(key, value);
    }

    public Date getDate(String key)
    {
        return new Date(getLong(key, 0));
    }
    
    public Date getDate(String key, long value)
    {
        return new Date(getLong(key, value));
    }

    public Date getDate(String key, Date value)
    {
        return new Date(getLong(key, value.getTime()));
    }

	@SuppressLint("NewApi")
	@SuppressWarnings("unchecked")
	public Set<String> getStringSet(String key, Set<String> value)
    {
    	if(VERSION.SDK_INT < 11){
    		String jvalue = mShared.getString(key, null);
    		if(jvalue == null){
    			return value;
    		}
    		try {
				Gson json = new Gson();
				value = json.fromJson(jvalue, value.getClass());
			} catch (Exception e) {
				// TODO: handle exception
			}
    		return value;
    	}
        return mShared.getStringSet(key, value);
    }

    @SuppressLint("NewApi")
    public void putStringSet(String key, Set<String> value)
    {
    	Editor editor = getSharePrefereEditor();
    	if(VERSION.SDK_INT < 11){
    		String jvalue = "";
    		try {
				Gson json = new Gson();
				jvalue = json.toJson(value);
			} catch (Exception e) {
				// TODO: handle exception
			}
    		editor.putString(key, jvalue);
    	}else{
    		editor.putStringSet(key, value);
    	}
        editor.commit();
    }

    

    public void putBoolean(String key, boolean value)
    {
        Editor editor = getSharePrefereEditor();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putString(String key, String value)
    {
        Editor editor = getSharePrefereEditor();
        editor.putString(key, value);
        editor.commit();
    }

    public void putFloat(String key, float value)
    {
        Editor editor = getSharePrefereEditor();
        editor.putFloat(key, value);
        editor.commit();
    }

    public void putInt(String key, int value)
    {
        Editor editor = getSharePrefereEditor();
        editor.putInt(key, value);
        editor.commit();
    }

    public void putLong(String key, long value)
    {
        Editor editor = getSharePrefereEditor();
        editor.putLong(key, value);
        editor.commit();
    }

    public void putDate(String key, Date date)
    {
        // TODO Auto-generated method stub
        putLong(key, date.getTime());
    }
    

    public void clear()
    {
        Editor editor = getSharePrefereEditor();
        editor.clear();
        editor.commit();
    }
}
