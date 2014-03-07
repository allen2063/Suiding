package com.suiding.util;

import java.util.Date;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.suiding.application.SuidingApp;
import com.suiding.caches.SharedPreference;


public class XmlCacheUtil
{
	public final static String SHARE_NAME = "XmlCacheUtil";
    //�µ��б�������ʱ��
    public final static String NEWS_LAST_UPDATE = "newsLastUpdateTime";
    //�Ƶ��б�������ʱ��
    public final static String HOTEL_LAST_UPDATE = "hotelLastUpdateTime";
    //�����б�������ʱ��
    public final static String RESTAURANT_LAST_UPDATE = "restaurantLastUpdateTime";
    //С̯�б�������ʱ��
    public final static String STALL_LAST_UPDATE = "stallLastUpdateTime";
    //�����б�������ʱ��
    public final static String KTV_LAST_UPDATE = "ktvLastUpdateTime";
    //�����б�������ʱ��
    public final static String CLUB_LAST_UPDATE = "clubLastUpdateTime";

    //�ղ��б�������ʱ��
    public final static String ATTENTION_COLLECT_LAST_UPDATE = "attentionCollectUpdateTime";

    //���ж�λ���ʱ��
    public final static String FIXEDCITY_UPDATETIME = "FixedPositionUpdateTime";
    //GPS��λ���ʱ��
    public final static String FIXEDPOSITRION_UPDATETIME = "FixedGPSUpdateTime";
    //���ж�������
    public final static String FIXED_CITYNAME = "FixedCityName";
    //���ж�����
    public final static String FIXEDPOSITRION_LATITUDE = "FixedLatitude";
    //���ж�γ��
    public final static String FIXEDPOSITRION_LONGITUDE = "FixedLongitude";

    //�ϴε�¼�˻�
    public final static String LOGIN_ACCOUNT = "loginaccount";
    //�Զ���¼����
    public final static String LOGIN_PASSWORD = "loginpassword";
    //�Զ���¼
    public final static String LOGIN_AUTOLOGIN = "autologin";
    
    //2/3G����ʱ������ͼƬ
    public final static String SETTING_NOPICTURE = "setting_nopicture";
    // ��ʾ������
    public final static String SETTING_NOTIFYSOUND = "setting_notifysound";
    // �����̼��ƹ�
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
