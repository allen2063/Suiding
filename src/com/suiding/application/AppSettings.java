package com.suiding.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppSettings {

	public static final int ID_NOIMAGEMODE = 100;		//2G/3G������ͼģʽ
	public static final int ID_ISRECEIVE = 101;		//�Ƿ�����̼��ƹ�
	public static final int ID_NOTIFYSOUND = 102;		//��ʾ����
	public static final int ID_USEPGSFIXED = 103;		//ʹ��GPS��λ
	public static final int ID_FIXEDDURATION = 104;	//��λʱ����
	public static final int ID_AUTOUPDATE = 105;	//��λʱ����

	private static final String  EM_SHARENAME = "SuidingSettings";

	//2G/3G������ͼģʽ
	private static final String KEY_NOIMAGEMODE = "KEY_NOIMAGEMODE";		
	//�Ƿ�����̼��ƹ�
	private static final String KEY_ISRECEIVE = "KEY_ISRECEIVE";	
	//��ʾ����
	private static final String KEY_NOTIFYSOUND = "KEY_NOTIFYSOUND";	
	//ʹ��GPS��λ
	private static final String KEY_USEPGSFIXED = "KEY_USEPGSFIXED";
	//��λʱ����
	private static final String KEY_FIXEDDURATION = "KEY_FIXEDDURATION";	
	//�Ƿ����Զ�����
	private static final String KEY_ISAUTOUPDATE = "KEY_ISAUTOUPDATE";	
	

	private boolean mBlNoImage = false;
	private boolean mBlIsReceive = true;
	private boolean mBlNotifySound = true;
	private boolean mBlUserGpsFixed = true;
	private boolean mBlAutoUpdate = true;
	private long mLgFixedDuration = 10*60*1000;

	private SharedPreferences mShared = null;
	
	private static AppSettings mAppSettings;

    private AppSettings(Context context) {
		// TODO Auto-generated constructor stub
    	int mode = Context.MODE_PRIVATE;
    	mShared = context.getSharedPreferences(EM_SHARENAME,mode);
    	mBlNoImage = mShared.getBoolean(KEY_NOIMAGEMODE, mBlNoImage);
    	mBlIsReceive = mShared.getBoolean(KEY_ISRECEIVE, mBlIsReceive);
    	mBlNotifySound = mShared.getBoolean(KEY_NOTIFYSOUND, mBlNotifySound);
    	mBlUserGpsFixed = mShared.getBoolean(KEY_USEPGSFIXED, mBlUserGpsFixed);
    	mLgFixedDuration = mShared.getLong(KEY_FIXEDDURATION, mLgFixedDuration);
	}

	public static void initialize(Context context){
		if(mAppSettings == null){
			mAppSettings = new AppSettings(context);
		}
	}

	public static AppSettings getInstance(Context context){
		if(mAppSettings == null){
			mAppSettings = new AppSettings(context);
			return mAppSettings;
		}
		return mAppSettings;
	}

	public static AppSettings getInstance(){
		return mAppSettings;
	}
	
	public boolean isNoImage() {
		return mBlNoImage;
	}

	public void setNoImage(boolean value) {
		this.mBlNoImage = value;
	}

	public boolean isIsReceive() {
		return mBlIsReceive;
	}

	public void setIsReceive(boolean value) {
		this.mBlIsReceive = value;
	}

	public boolean isNotifySound() {
		return mBlNotifySound;
	}

	public void setNotifySound(boolean value) {
		this.mBlNotifySound = value;
	}

	public boolean isUserGpsFixed() {
		return mBlUserGpsFixed;
	}
	
	public boolean isBlAutoUpdate() {
		return mBlAutoUpdate;
	}

	public void setUserGpsFixed(boolean value) {
		this.mBlUserGpsFixed = value;
	}

	public void setFixedDuration(long value) {
		this.mLgFixedDuration = value;
	}
	
	public void setAutoUpdate(boolean mBlAutoUpdate) {
		this.mBlAutoUpdate = mBlAutoUpdate;
	}

	public void commit()
	{
		Editor editor = mShared.edit();
		editor.putBoolean(KEY_NOIMAGEMODE, mBlNoImage);
		editor.putBoolean(KEY_NOTIFYSOUND, mBlNotifySound);
		editor.putBoolean(KEY_ISRECEIVE, mBlIsReceive);
		editor.putBoolean(KEY_USEPGSFIXED, mBlUserGpsFixed);
		editor.putBoolean(KEY_ISAUTOUPDATE, mBlAutoUpdate);
		editor.putLong(KEY_FIXEDDURATION, mLgFixedDuration);
		editor.commit();
	}
}
