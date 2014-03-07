package com.suiding.util;

import java.util.Date;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.suiding.application.SuidingApp;
import com.suiding.caches.SharedPreference;


public class XmlCacheUtil
{
	public final static String SHARE_NAME = "XmlCacheUtil";
    //新单列表最后更新时间
    public final static String NEWS_LAST_UPDATE = "newsLastUpdateTime";
    //酒店列表最后更新时间
    public final static String HOTEL_LAST_UPDATE = "hotelLastUpdateTime";
    //餐厅列表最后更新时间
    public final static String RESTAURANT_LAST_UPDATE = "restaurantLastUpdateTime";
    //小摊列表最后更新时间
    public final static String STALL_LAST_UPDATE = "stallLastUpdateTime";
    //娱乐列表最后更新时间
    public final static String KTV_LAST_UPDATE = "ktvLastUpdateTime";
    //会所列表最后更新时间
    public final static String CLUB_LAST_UPDATE = "clubLastUpdateTime";

    //收藏列表最后更新时间
    public final static String ATTENTION_COLLECT_LAST_UPDATE = "attentionCollectUpdateTime";

    //城市定位最后时间
    public final static String FIXEDCITY_UPDATETIME = "FixedPositionUpdateTime";
    //GPS定位最后时间
    public final static String FIXEDPOSITRION_UPDATETIME = "FixedGPSUpdateTime";
    //城市定所属地
    public final static String FIXED_CITYNAME = "FixedCityName";
    //城市定精度
    public final static String FIXEDPOSITRION_LATITUDE = "FixedLatitude";
    //城市定纬度
    public final static String FIXEDPOSITRION_LONGITUDE = "FixedLongitude";

    //上次登录账户
    public final static String LOGIN_ACCOUNT = "loginaccount";
    //自动登录密码
    public final static String LOGIN_PASSWORD = "loginpassword";
    //自动登录
    public final static String LOGIN_AUTOLOGIN = "autologin";
    
    //2/3G流量时候下载图片
    public final static String SETTING_NOPICTURE = "setting_nopicture";
    // 提示音设置
    public final static String SETTING_NOTIFYSOUND = "setting_notifysound";
    // 接收商家推广
    public final static String SETTING_PROMOTION = "setting_promotion";

    private static SharedPreference mInstance = new SharedPreference(SuidingApp.getApp(), XmlCacheUtil.SHARE_NAME);
    
    public static SharedPreferences getSharedPreferences()
    {
        return mInstance.getSharedPreferences();
    }

    public static Editor getSharePrefereEditor()
    {
        return mInstance.getSharePrefereEditor();
    }

    public static boolean getBoolean(String key, boolean value)
    {
        return mInstance.getBoolean(key, value);
    }

    public static String getString(String key, String value)
    {
        return mInstance.getString(key, value);
    }

    public static float getFloat(String key, float value)
    {
        return mInstance.getFloat(key, value);
    }

    public static int getInt(String key, int value)
    {
        return mInstance.getInt(key, value);
    }

    public static long getLong(String key, long value)
    {
        return mInstance.getLong(key, value);
    }

    public static Date getDate(String key)
    {
        return new Date(getLong(key, 0));
    }
    
    public static Date getDate(String key, long value)
    {
        return new Date(getLong(key, value));
    }
    
    public static Date getDate(String key, Date value)
    {
        return new Date(getLong(key, value.getTime()));
    }
    

    public static void putBoolean(String key, boolean value)
    {
        mInstance.putBoolean(key, value);
    }

    public static void putString(String key, String value)
    {
        mInstance.putString(key, value);
    }

    public static void putFloat(String key, float value)
    {
        mInstance.putFloat(key, value);
    }

    public static void putInt(String key, int value)
    {
        mInstance.putInt(key, value);
    }

    public static void putLong(String key, long value)
    {
        mInstance.putLong(key, value);
    }

    public static void putDate(String key, Date date)
    {
        // TODO Auto-generated method stub
        putLong(key, date.getTime());
    }
    

    public static void clear()
    {
        mInstance.clear();
    }
}
