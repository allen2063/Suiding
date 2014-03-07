package com.suiding.application;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Environment;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.suiding.activity.EditAccountActivity;
import com.suiding.activity.FixedCityActivity;
import com.suiding.activity.IndexMainActivity;
import com.suiding.activity.UserLoginActivity;
import com.suiding.activity.UserRegisterActivity;
import com.suiding.activity.WelcomeActivity;
import com.suiding.activity.framework.ActivityBase;
import com.suiding.activity.framework.FragmentActivityBase;
import com.suiding.application.OrderConsole.INotifyOrderNumber;
import com.suiding.application.OrderConsole.INotifyRemindOrder;
import com.suiding.application.OrderConsole.OrderConsoleTask;
import com.suiding.application.UpdateService.CheckUpdateTask;
import com.suiding.application.UserCenter.INotifyFavoriteNumber;
import com.suiding.application.UserCenter.UserCenterTask;
import com.suiding.broadcast.ConnectionChangeReceiver;
import com.suiding.caches.ImageCaches;
import com.suiding.constant.FixedCityEnum;
import com.suiding.constant.FixedPositionEnum;
import com.suiding.database.DatabaseUtil;
import com.suiding.fragment.IndexAccountFragment;
import com.suiding.fragment.IndexAccountOldFragment;
import com.suiding.fragment.framework.FragmentBase;
import com.suiding.model.Area;
import com.suiding.model.Consumer;
import com.suiding.model.Order;
import com.suiding.thread.FixedCityTask;
import com.suiding.thread.FixedPositionTask;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.CityNameUtil;
import com.suiding.util.NetworkUtil;
import com.suiding.util.TimeSpan;
import com.suiding.util.VersionUtil;

/**
 * LeSouShopApp
 * 
 * @author SCWANG ���� ȫ�����ݵĴ洢 �� �¼�֪ͨ
 */
public class SuidingApp extends Application {

	/**
	 * interface INotifyNeedUpdate
	 *  
	 * @author SCWANG ��Ҫ����֪ͨ�ӿ�
	 */
	public interface INotifyNeedUpdate {
		void onNotifyNeedUpdate(String curver, String server);
	}

	/**
	 * interface INotifyNetworkStatus
	 *  
	 * @author SCWANG ����״̬�ı�֪ͨ�ӿ�
	 */
	public static interface INotifyNetworkStatus {
		void onNetworkStatusChanged(int networkStatus);
	}

	/**
	 * interface INotifyFixedPosition
	 * 
	 * @author SCWANG GPS��λ�ı�֪ͨ�ӿ�
	 */
	public static interface INotifyFixedPosition {
		void onFixedPositionChanged(Location location, int status);
	}

	/**
	 * interface INotifyFixedPosition
	 * 
	 * @author SCWANG ���ж�λ�ı�֪ͨ�ӿ�
	 */
	public static interface INotifyFixedCity {
		void onFixedCityChanged(Area area, int status);
	}

	/**
	 * interface INotifyLoginUser
	 * 
	 * @author SCWANG ��¼�û��ı�֪ͨ�ӿ�
	 */
	public static interface INotifyLoginUser {
		void onLoginUserChanged(Consumer user);
	}

	public static SuidingApp mLeSouShopApp = null;

	public static synchronized SuidingApp getApp() {
		return mLeSouShopApp;
	}

	public static synchronized Context getAppContext() {
		return mLeSouShopApp.getApplicationContext();
	}

	public static synchronized Looper getLooper() {
		// mLeSouShopApp.mWorker.getHandler().post(task);
		return mLeSouShopApp.mLooper;
	}

	public static synchronized TaskBase postTask(TaskBase task) {
		// mLeSouShopApp.mWorker.getHandler().post(task);
		return Background.postTask(task);
	}

	public static synchronized Consumer getLoginUser() {
		return mLeSouShopApp.mLoginUser;
	}

	public static synchronized int getNetworkStatus() {
		return mLeSouShopApp.mNetworkStatus;
	}
	/**
	 * ��ȡ�ڲ��汾
	 * 
	 * @return
	 */
	public static synchronized String getVersion() {
		// TODO Auto-generated method stub
		return mLeSouShopApp.mVersion;
	}

	/**
	 * ��ȡ�ڲ��汾����
	 * 
	 * @return
	 */
	public static synchronized int getVersionCode() {
		// TODO Auto-generated method stub
		return VersionUtil.transformVersion(mLeSouShopApp.mVersion);
	}
	
	// ��λ���
	private Area mArea = null;
	private Location mLocation = null;
	// �ϴζ�λ�¼�
	private Date mLastFixedCity = new Date(0);
	private Date mLastFixedPosition = new Date(0);
	// ��λ��� 5 ����
	private TimeSpan mFixedSpan = TimeSpan.FromMinutes(5);
	// ��ǰ��¼�û�
	private Consumer mLoginUser = null;
	// ��ǰ��λ״̬ Ĭ��Ϊ δ��λ
	private int mFixedCityStatus = FixedCityEnum.NONE;
	private int mFixedPositionStatus = FixedPositionEnum.NONE;
	// ��ǰ������������ Ĭ��Ϊ δ����
	private int mNetworkStatus = NetworkUtil.TYPE_NONE;
	// ��ǰ��ҳ��
	private Activity mCurActivity = null;
	// ��ǰ��ҳ��
	private Fragment mCurFragment = null;
	// ��ǰ�汾
	private String mVersion = "0.0.0.0";
	// ��ǰ�汾
	private String mServerVersion = "0.0.0.0";

	// ��������߳�Worker
	private Looper mLooper = null;
	// private ThreadWorker mWorker = null;
	private boolean mIsGoingHome = false;
	private boolean mIsInitialized = false;
	//���ǰ̨�Ƿ�������
	private boolean IsForegroundRunning = false;

	public SuidingApp() {
		// TODO Auto-generated constructor stub
		mLeSouShopApp = this;
		mLooper = Looper.myLooper();
		AppExceptionHandler.register();
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		try {
			//���� SharedPreferences ·��
			//setPreferencesPath();
			//��ʼ���𶯿���̨
			VibratorConsole.initialize(getAppContext());
			//��ʼ��AppSettings
			AppSettings.initialize(getAppContext());
			//��ʼ����ַ����
			AddressService.startUp(getAppContext());
			//��ʼ��֪ͨ����
			NotifyCenter.initailize(getAppContext());
			// ������λ����
			BaiduLocationServer.initialize(getAppContext());
			//����ͼƬ����·��
			ImageCaches.initialize(this,getCachesPath("image"));

			// ������ݿ�
			new DatabaseUtil(getAppContext()).checkDataBaseVersion();
			// App ������ʱ�� ��������������
			mNetworkStatus = NetworkUtil.getNetworkState(this);
			if(mNetworkStatus != NetworkUtil.TYPE_NONE){
				//������
				UpdateService.checkUpdate();
				//�Զ���¼
				UserLoginActivity.autoLodin();
			}
			//��ʼ���汾��
			getPackageVersion();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();//handled
			AppExceptionHandler.handler(e, "SuidingApp��onCreate ��ʼ��ʧ��");
		}
	}

	private void getPackageVersion() throws Exception {
		// TODO Auto-generated method stub
		int get = PackageManager.GET_CONFIGURATIONS;
		String tPackageName = getPackageName();
		PackageManager magager = getPackageManager();
		PackageInfo pinfo = magager.getPackageInfo(tPackageName,get);
		mVersion = pinfo.versionName;
	}

//	private void setPreferencesPath() throws Exception {
//		// TODO Auto-generated method stub
//		Field field;  
//		// ��ȡContextWrapper�����е�mBase�������ñ���������ContextImpl����  
//		field = ContextWrapper.class.getDeclaredField("mBase");  
//		field.setAccessible(true);  
//		// ��ȡmBase����  
//		Object obj = field.get(this);  
//		// ��ȡContextImpl.mPreferencesDir�������ñ��������������ļ��ı���·��  
//		field = obj.getClass().getDeclaredField("mPreferencesDir");  
//		field.setAccessible(true);  
//		// �����Զ���·��  
//		File file = new File(getWorkspacePath("Preferences"));  
//		// �޸�mPreferencesDir������ֵ  
//		field.set(obj, file);  
//	}

	/**
	 * ��ȡGPS��λ״̬
	 * 
	 * @return
	 */
	public synchronized int getFixedPositionStatus() {
		// TODO Auto-generated method stub
		initializeFixedPosition();
		return mFixedPositionStatus;
	}

	/**
	 * ��ȡGPS��λ״̬
	 * 
	 * @return
	 */
	public synchronized int getFixedPositionStatus(boolean update) {
		// TODO Auto-generated method stub
		if (update)
			initializeFixedPosition();
		return mFixedPositionStatus;
	}

	/**
	 * ��ȡ���ж�λ״̬
	 * 
	 * @return
	 */
	public synchronized int getFixedCityStatus() {
		// TODO Auto-generated method stub
		return mFixedCityStatus;
	}

	/**
	 * ��ȡ���ж�λ״̬ ��� ״̬Ϊʧ�� ������Ч ���¶�λ
	 * 
	 * @return
	 */
	public synchronized int getFixedCityStatus(boolean update) {
		// TODO Auto-generated method stub
		if (update)
			initializeFixedCity();
		return mFixedCityStatus;
	}

	/**
	 * ��ȡ��λArea
	 * 
	 * @return
	 */
	public synchronized Area getFixedArea() {
		// TODO Auto-generated method stub
		return mArea;
	}

	/**
	 * ��ȡ��λLocation
	 * 
	 * @return
	 */
	public synchronized Location getFixedLocation() {
		// TODO Auto-generated method stub
		return mLocation;
	}

	/**
	 * ��ȡCurActivity
	 * 
	 * @return
	 */
	public synchronized Activity getCurActivity() {
		// TODO Auto-generated method stub
		return mCurActivity;
	}

	/**
	 * ��ȡApp����Ŀ¼
	 * 
	 * @param type
	 */
	public synchronized String getWorkspacePath(String type) {
		// TODO Auto-generated method stub
		File workspace = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String sdcard = Environment.getExternalStorageDirectory().getPath();
			workspace = new File(sdcard + "/suiding/" + type);
		} else {
			workspace = new File(getCacheDir(), type);
		}
		if (!workspace.exists() && !workspace.isDirectory()) {
			workspace.mkdir();
		}
		return workspace.getPath();
	}

	/**
	 * ��ȡcachesĿ¼
	 * 
	 * @param type
	 */
	public synchronized String getCachesPath(String type) {
		// TODO Auto-generated method stub
		File caches = new File(getWorkspacePath("caches"));
		caches = new File(caches, type);
		if (!caches.exists() && !caches.isDirectory()) {
			caches.mkdir();
		}
		return caches.getPath();
	}

	/**
	 * ��ȡ��������
	 * 
	 * @param cityName
	 */
	public synchronized String getCityName() {
		switch (mFixedCityStatus) {
		default:
		case FixedCityEnum.NONE: {
			switch (mFixedPositionStatus) {
			case FixedPositionEnum.FAIL:
				return "��λʧ��";
			case FixedPositionEnum.FIXEDING:
				return "���ڶ�λ";
			}
			return "�޶�λ��Ϣ";
		}
		case FixedCityEnum.FIXEDING:
			return "���ڶ�λ";
		case FixedCityEnum.FAIL:
			return "��λʧ��";
		case FixedCityEnum.FIXED:
		// case FixedCityEnum.FIXEDCITY_SELECED:
		{
			return CityNameUtil.SimplifyCityName(mArea.Name);
		}
		}
	}
	/**
	 * ��ȡ���������°汾
	 * @return
	 */
	public String getServerVersion() {
		return mServerVersion;
	}
	/**
	 * ��ȡApp�Ƿ� ��Ҫ����
	 * @return
	 */
	public boolean isNeedUpdate() {
		// TODO Auto-generated method stub
		int curver = VersionUtil.transformVersion(mVersion);
		int server = VersionUtil.transformVersion(mServerVersion);
		return curver < server;
	}
	/**
	 * ��ȡApp�Ƿ� ִ�й� initialize
	 * @return
	 */
	public synchronized boolean isInitialize(){
		return mIsInitialized;
	}
	/**
	 * ��ÿ�γ���������ʱ���ʼ��һ��
	 * 
	 * @param power
	 *            ����Ȩ����֤
	 */
	public synchronized void initialize(Object power) {
		if (!mIsInitialized && (
				power instanceof WelcomeActivity
				|| power instanceof IndexMainActivity)) {
			try {
				initializeFixedPosition();
				initializeFixedCity();
				//��ʶ��ʼ�����
				mIsInitialized = true;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.handler(e, "SuidingApp��initialize ��ʼ��ʧ��");
			}
		}
	}
	/**
	 * ���õ�ǰ��ҳ��
	 * 
	 * @param type
	 *            ����Ȩ����֤
	 * @param activity
	 *            ��ǰ�� Activity
	 */
	public synchronized void setCurActivity(Object power, Activity activity) {
		if (power instanceof ActivityBase || power instanceof FragmentActivityBase) {
			//������ڷ�����ҳ��
			if(mIsGoingHome){
				//����Ѿ�������ҳ��
				if(activity instanceof IndexMainActivity){
					//�رշ�����ҳ�湦��
					mIsGoingHome = false;
				}else{
					//�ز���ǰҳ��ص���ҳ��
					activity.finish();
					return;
				}
			}
			
			mCurActivity = activity;
			//if(mLoginUser!= null){
			//	notifyLoginUser(mCurActivity, mLoginUser);
			//	notifyRemindOrder(mCurActivity,mLoginUser.getID(),UserCenter.getListOrderChanged(this));
			//}
			//if(mArea!= null)
			//notifyFixedCity(mCurActivity, mArea, mFixedCityStatus);
			//if(mLocation!= null)
			//notifyFixedPosition(mCurActivity, mLocation, mFixedPositionStatus);
			//notifyNetworkStatus(mCurActivity, mNetworkStatus);
		}
	}

	/**
	 * ���õ�ǰ��ҳ��
	 * 
	 * @param type
	 *            ����Ȩ����֤
	 * @param fragment
	 *            ��ǰ�� Fragment
	 */
	public synchronized void setCurFragment(Object power, Fragment fragment) {
		if (power instanceof FragmentBase) {
			mCurFragment = fragment;
			//if(mLoginUser!= null)
			//notifyLoginUser(mCurFragment, mLoginUser);
			//if(mArea!= null)
			//notifyFixedCity(mCurFragment, mArea, mFixedCityStatus);
			//if(mLocation!= null)
			//notifyFixedPosition(mCurFragment, mLocation, mFixedPositionStatus);
			//notifyNetworkStatus(mCurFragment, mNetworkStatus);
		}
	}

	/**
	 * ���õ�ǰ�ĵ�¼�û�
	 * 
	 * @param power
	 *            ����Ȩ����֤ ����this
	 * @param user
	 *            ��ǰ�� ��¼�û�
	 */
	public synchronized void setLoginUser(Object power, Consumer user) {
		if (power instanceof UserRegisterActivity
				|| power instanceof UserLoginActivity
				|| power instanceof IndexAccountFragment
				|| power instanceof IndexAccountOldFragment
				|| power instanceof EditAccountActivity) {
			mLoginUser = user;
			// ���͵� �û�����
			UserCenter.setLoginUser(this, user);
			// ���Ͷ�λ��Ϣ�ı�֪ͨ
			notifyLoginUser(mCurActivity, mLoginUser);
			notifyLoginUser(mCurFragment, mLoginUser);
		}
	}
	/**
	 * ���õ�ǰ�û����������ı�
	 * @param power
	 *            ����Ȩ����֤ ����this
	 * @param userid
	 * @param wait
	 * @param confirm
	 * @param none
	 */
	public synchronized void setOrderNumber(Object power,UUID userid,long wait,long confirm,long none) {
		// TODO Auto-generated method stub
		if (power instanceof UserCenterTask
				|| power instanceof OrderConsoleTask) {
			// ���Ͷ�λ��Ϣ�ı�֪ͨ
			notifyOrderNumber(mCurActivity, userid,wait,confirm,none);
			notifyOrderNumber(mCurFragment, userid,wait,confirm,none);
		}
	}
	/**
	 * ֪ͨAPP �����Ѿ��ı�
	 * @param power Ȩ�޶��� ����this
	 * @param user
	 * @param mltchanged
	 */
	public synchronized void setRemindOrder(Object power, UUID userid,List<Order> mltchanged) {
		// TODO Auto-generated method stub
		if(power instanceof UserCenterTask && IsForegroundRunning){
			// ���Ͷ�λ��Ϣ�ı�֪ͨ
			notifyRemindOrder(mCurActivity, userid,mltchanged);
			notifyRemindOrder(mCurFragment, userid,mltchanged);
		}
	}
	
	/**
	 * ���õ�ǰ�û��ղ������ı�
	 * @param power
	 *            ����Ȩ����֤ ����this
	 * @param favorite
	 */
	public synchronized void setFavoriteNumber(Object power,UUID userid,long favorite) {
		// TODO Auto-generated method stub
		if (power instanceof UserCenterTask) {
			// ���Ͷ�λ��Ϣ�ı�֪ͨ
			notifyFavoriteNumber(mCurActivity, userid,favorite);
			notifyFavoriteNumber(mCurFragment, userid,favorite);
		}
	}

	/**
	 * ����GPS��λ���
	 * 
	 * @param power
	 *            ����thisָ�� ������֤Ȩ��
	 * @param location
	 * @param status
	 *            ֻ�гɹ���ʧ������
	 */
	public synchronized void setFixedPosition(Object power, Location location, int status) {
		// TODO Auto-generated method stub
		if (power instanceof FixedPositionTask
				|| power instanceof BaiduLocationServer
				|| power instanceof FixedCityActivity) {

			// ���ö�λ��Ϣ
			mLocation = location;
			mFixedPositionStatus = status;
			// ����ɹ���λ�����涨λ��Ϣ
			if (mFixedPositionStatus == FixedPositionEnum.FIXED) {
				// �����ݸ��µ���������ݿ���
				FixedPositionTask.putFixedPositionCaches(mLocation);
				// ���GPS��λ֮�� ���г��ж�λ
				//if (mFixedCityStatus != FixedCityEnum.FIXED) {
				//	mFixedCityStatus = FixedCityEnum.FIXEDING;
				//}
				//new FixedCityTask(mLocation).post();
			}
			// ���GPS��λ���ɹ��������ж�λ�ǲ������ڶ�λ
//			else if (mFixedCityStatus == FixedCityEnum.FIXEDING) {
//				// ���ж�λ������GPS��λ��GPSʧ������ж�λҲʧ��
//				mFixedCityStatus = FixedCityEnum.FAIL;
//				// ���Ͷ�λ��Ϣ�ı�֪ͨ
//				notifyFixedCity(mCurActivity, null,
//						FixedCityEnum.FAIL);
//				notifyFixedCity(mCurFragment, null,
//						FixedCityEnum.FAIL);
//			}
			
			// ���Ͷ�λ��Ϣ�ı�֪ͨ
			notifyFixedPosition(mCurActivity, mLocation, status);
			notifyFixedPosition(mCurFragment, mLocation, status);
		}
	}

	/**
	 * ���ó��ж�λ���
	 * 
	 * @param power
	 *            ����thisָ�� ������֤Ȩ��
	 * @param area
	 * @param status
	 *            ֻ�гɹ���ʧ������
	 */
	public synchronized void setFixedCity(Object power, Area area, int status) {
		// TODO Auto-generated method stub
		if (power instanceof FixedCityTask || power instanceof FixedCityActivity) {
			// ���ö�λ��Ϣ
			mArea = area;
			mFixedCityStatus = status;
			// ���Ͷ�λ��Ϣ�ı�֪ͨ
			notifyFixedCity(mCurActivity, mArea, status);
			notifyFixedCity(mCurFragment, mArea, status);

			// ����ɹ���λ�����涨λ��Ϣ
			if (mFixedCityStatus == FixedCityEnum.FIXED
			/* || mFixedCityStatus == FixedCityEnum.FIXEDCITY_SELECED */) {
				// �����ݸ��µ���������ݿ���
				FixedCityTask.putFixedCityCaches(mArea);
			}
		}
	}

	/**
	 * ����App����״̬
	 * 
	 * @param power
	 *            ����thisָ�� ������֤Ȩ��
	 * @param networkState
	 *            ָ��������״̬
	 */
	public synchronized void setNetworkStatus(Object power, int networkState) {
		// TODO Auto-generated method stub
		if (power instanceof ConnectionChangeReceiver) {
			mNetworkStatus = networkState;
			
			UserCenter.setNetworkStatus(this, networkState);
			
			notifyNetworkStatus(mCurActivity, networkState);
			notifyNetworkStatus(mCurFragment, networkState);

			// �������������
			if (mNetworkStatus != NetworkUtil.TYPE_NONE) {
				// �����û�гɹ���λ
				if (mFixedPositionStatus != FixedPositionEnum.FIXED) {
					// ��ʼ����λ��Ϣ
					initializeFixedPosition();
				}
			}
		}
	}

	/**
	 * ���� ������ App �汾
	 * 
	 * @param power
	 *            ����thisָ�� ������֤Ȩ��
	 * @param version
	 *            �������汾
	 */
	public synchronized void setServerVersion(Object power, String version) {
		// TODO Auto-generated method stub
		if (power instanceof CheckUpdateTask) {
			mServerVersion = version;
			if(isNeedUpdate()){
				notifyNeedUpdate(mCurActivity,mVersion,mServerVersion);
				notifyNeedUpdate(mCurFragment,mVersion,mServerVersion);
			}
		}
	}

	/**
	 * �� power ���� ��Ҫ����֪ͨ
	 * 
	 * @param power
	 *            ֪ͨ�Ķ��� ����ʵ�� INotifyNeedUpdate �ӿ�
	 * @param networkState
	 *            ��ǰ����״̬
	 */
	private void notifyNeedUpdate(Object power, String curver,String server) {
		// TODO Auto-generated method stub
		if (power instanceof INotifyNeedUpdate) {
			try {
				INotifyNeedUpdate tINotify = (INotifyNeedUpdate) power;
				tINotify.onNotifyNeedUpdate(curver,server);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.handler(e, "SuidingApp��֪ͨ��Ҫ����ʱ�׳��쳣");
			}
		}
	}

	/**
	 * �� power ���� ����״̬�ı�֪ͨ
	 * 
	 * @param power
	 *            ֪ͨ�Ķ��� ����ʵ�� INotifyNetworkStatus �ӿ�
	 * @param networkState
	 *            ��ǰ����״̬
	 */
	private synchronized void notifyNetworkStatus(Object power, int networkState) {
		// TODO Auto-generated method stub
		if (power instanceof INotifyNetworkStatus) {
			try {
				INotifyNetworkStatus tINotify = (INotifyNetworkStatus) power;
				tINotify.onNetworkStatusChanged(networkState);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.handler(e, "SuidingApp��֪ͨ����״̬�ı�ʱ�׳��쳣");
			}
		}
	}

	/**
	 * �� power ���� �л�����֪ͨ
	 * 
	 * @param power
	 *            ֪ͨ�Ķ��� ����ʵ�� INotifySwitchOverCity �ӿ�
	 * @param status
	 * @param location
	 *            ��ǰ��λ��Ϣ
	 */
	private synchronized void notifyFixedCity(Object power, Area area, int status) {
		// TODO Auto-generated method stub
		if (power instanceof INotifyFixedCity) {
			try {
				INotifyFixedCity tINotify = (INotifyFixedCity) power;
				tINotify.onFixedCityChanged(area, status);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.handler(e, "SuidingApp��֪ͨ���ж�λ�ı�ʱ�׳��쳣");
			}
		}
	}

	/**
	 * �� power ���� ��λ��Ϣ�ı�֪ͨ
	 * 
	 * @param power
	 *            ֪ͨ�Ķ��� ����ʵ�� INotifyFixedPosition �ӿ�
	 * @param location
	 *            ��ǰ��λ��Ϣ
	 */
	private synchronized void notifyFixedPosition(Object power, Location location, int status) {
		// TODO Auto-generated method stub
		if (power instanceof INotifyFixedPosition) {
			try {
				INotifyFixedPosition tINotify = (INotifyFixedPosition) power;
				tINotify.onFixedPositionChanged(location, status);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.handler(e, "SuidingApp��֪ͨ�㶨λ�ı�ʱ�׳��쳣");
			}
		}
	}

	/**
	 * �� power ���� ��¼�û��ı�֪ͨ
	 * 
	 * @param power
	 *            ֪ͨ�Ķ��� ����ʵ�� INotifyLoginUser �ӿ�
	 * @param user
	 *            ��ǰ��¼�û�
	 */
	private synchronized void notifyLoginUser(Object power, Consumer user) {
		// TODO Auto-generated method stub
		if (power instanceof INotifyLoginUser) {
			try {
				INotifyLoginUser tINotify = (INotifyLoginUser) power;
				tINotify.onLoginUserChanged(user);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.handler(e, "SuidingApp��֪ͨ�û���¼�ı�ʱ�׳��쳣");
			}
		}
	}

	/**
	 * ���õ�ǰ�û����������ı�
	 * @param power
	 *            ����Ȩ����֤ ����this
	 * @param userid
	 * @param wait
	 * @param confirm
	 * @param none
	 */
	private synchronized void notifyOrderNumber(Object power,UUID userid,long wait,long confirm,long none) {
		// TODO Auto-generated method stub
		if (power instanceof INotifyOrderNumber) {
			try {
				INotifyOrderNumber tINotify = (INotifyOrderNumber) power;
				tINotify.onOrderNumberChanged(userid, wait, confirm, none);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.handler(e, "SuidingApp��֪ͨ���������ı�ʱ�׳��쳣");
			}
		}
	}
	/**
	 * ���õ�ǰ�û��ղ������ı�
	 * @param power
	 *            ����Ȩ����֤ ����this
	 * @param favorite
	 */
	private synchronized void notifyFavoriteNumber(Object power, UUID userid,long favorite) {
		// TODO Auto-generated method stub
		if (power instanceof INotifyFavoriteNumber) {
			try {
				INotifyFavoriteNumber tINotify = (INotifyFavoriteNumber) power;
				tINotify.onFavoriteNumberChanged(userid, favorite);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.handler(e, "SuidingApp��֪ͨ�ղ������ı�ʱ�׳��쳣");
			}
		}
	}

	/**
	 * ֪ͨAPP �����Ѿ��ı�
	 * @param power Ȩ�޶��� ����this
	 * @param user
	 * @param mltchanged
	 */
	private synchronized void notifyRemindOrder(Object power, UUID userid,List<Order> ltremind) {
		// TODO Auto-generated method stub
		if(power instanceof INotifyRemindOrder){
			try {
				INotifyRemindOrder tINotify = (INotifyRemindOrder) power;
				tINotify.onRemindOrderChanged(userid, ltremind);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.handler(e, "SuidingApp��֪ͨ�������Ѹı�ʱ�׳��쳣");
			}
		}
	}
	/**
	 * ��ʼ����λ��Ϣ
	 */
	private synchronized void initializeFixedPosition() {
		// TODO Auto-generated method stub
		// App ������ʱ�� ִ�ж�λ��ز���
		switch (mFixedPositionStatus) {
		case FixedPositionEnum.FIXEDING:
		case FixedPositionEnum.FIXED:
			break;
		default:
		case FixedPositionEnum.FAIL:
		case FixedPositionEnum.NONE:
			// ��λ��¼���� ���¶�λ
			if (FixedPositionTask.getFixedPositionCachesIsTimeout() == false) {
				// ��������ϴζ�λ ���� �涨ʱ��5����
				if (mFixedSpan.Compare(TimeSpan.FromDate(mLastFixedPosition,
						new Date())) < 0) {
					// ��ʼ���ж�λ
					mFixedPositionStatus = FixedPositionEnum.FIXEDING;
					new FixedPositionTask().post();
				}
			} else {
				mLocation = FixedPositionTask.getFixedPositionCaches();
				mFixedPositionStatus = FixedPositionEnum.FIXED;
			}
			break;

		}
	}

	/**
	 * ��ʼ����λ��Ϣ
	 */
	private synchronized void initializeFixedCity() {
		// App ������ʱ�� ִ�ж�λ��ز���
		switch (mFixedCityStatus) {
		case FixedCityEnum.FIXED:
		case FixedCityEnum.FIXEDING:
			break;
		default:
		case FixedCityEnum.FAIL:
		case FixedCityEnum.NONE:

			// ���жϳ��ж�λ��Ϣ�Ƿ����
			if (FixedCityTask.getFixedCityCachesIsTimeout() == false) {
				// ����������ж�GPS��λ��״̬
//				switch (mFixedPositionStatus) {
//				default:
//				case FixedPositionEnum.NONE:// GPS��λ��Ч
//					// ����GPS��λ
//					initializeFixedPosition();
//					// �����ж�λ״̬��ʱ ��Ϊ��Ч GPS������Զ�����
//					mFixedCityStatus = FixedCityEnum.NONE;
//					break;
//				case FixedPositionEnum.FAIL:// GPS��λʧ�ܣ�
//					// ����GPS��λ
//					initializeFixedPosition();
//					// �����ж�λ״̬��ʱ ��Ϊʧ�� GPS������Զ�����
//					mFixedCityStatus = FixedCityEnum.FAIL;
//					break;
//				case FixedPositionEnum.FIXEDING:
//					mFixedCityStatus = FixedCityEnum.FIXEDING;
//					// GPS��λ���ڶ�λ���ô��� GPS��λ�ɹ��Զ��������ж�λ
//					break;
//				case FixedPositionEnum.FIXED:// GPS��λ�ɹ�
//					// ��������ϴζ�λ ���� �涨ʱ��5����
//					if (mFixedSpan.Compare(TimeSpan.FromDate(mLastFixedCity,
//							new Date())) < 0) {
//						// ��ʼ���ж�λ
//						mFixedCityStatus = FixedCityEnum.FIXEDING;
//						new FixedCityTask(mLocation).post();
//					}
//					break;
//				}
				// ��������ϴζ�λ ���� �涨ʱ��5����
				if (mFixedSpan.Compare(TimeSpan.FromDate(mLastFixedCity,
						new Date())) < 0) {
					// ��ʼ���ж�λ
					mFixedCityStatus = FixedCityEnum.FIXEDING;
					new FixedCityTask(mLocation).post();
				}
			} else {
				String cityname = FixedCityTask.getFixedCityNameCaches();
				mArea = FixedCityTask.getFixedCityCaches(cityname);
				mFixedCityStatus = FixedCityEnum.FIXED;
			}
			break;

		}
	}

	/**
	 * �򿪳��ж�λҳ��
	 * 	���ڶ�λʧ�ܵ�ʱ���ֶ���λ��������ʾ��Ϣ
	 * 	�����ǰ��������ʣ�����ʾ��λʧ�ܣ�����ҳ��
	 * 	@param activity ��ǰҳ��
	 */
	public synchronized void InvokeFixedCityActivity(Activity activity) {
		// TODO Auto-generated method stub
		if(mNetworkStatus == NetworkUtil.TYPE_NONE){
			// ������ʾ �ֶ�����
			Toast.makeText(this,
					"ϵͳ��ʱ�޷���λ����λ����Ϣ��", Toast.LENGTH_SHORT).show();
		}else{
			// ��ת���ֶ���λҳ��
			activity.startActivity(new Intent(activity, FixedCityActivity.class));
			// ������ʾ �ֶ�����
			Toast.makeText(this,
					"ϵͳ��ʱ�޷���λ����λ����Ϣ������Ҫ�ֶ���λ��", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * �ӵ�ǰҳ�� activity ���ص�ǰ̨��ҳ��
	 * @param activity
	 */
	public synchronized void goBackToHome(Activity activity) {
		// TODO Auto-generated method stub
		if(activity instanceof IndexMainActivity){
			
		}else{
			mIsGoingHome = true;
			try {
				activity.finish();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.handler(e, "SuidingApp��������ҳ�棬�ر���ʱҳ�����");
			}
		}
	}

	/**
	 * ֪ͨAPP ������̨ҳ��
	 * @param activity 
	 */
	public synchronized void startForeground(Activity activity) {
		// TODO Auto-generated method stub
		if(IsForegroundRunning == false){
			IsForegroundRunning = true;
			activity.startActivity(new Intent(activity,IndexMainActivity.class));
		}
	}


	/**
	 * ��ȡ ǰ̨ҳ���Ƿ�������
	 * @return
	 */
	public synchronized boolean getIsForegroundRunning() {
		// TODO Auto-generated method stub
		return IsForegroundRunning;
	}

	/**
	 * ֪ͨAPP ǰ̨�Ѿ��ر�
	 * @param activity Ȩ�޶��� ����this
	 */
	public synchronized void notifyForegroundClosed(Activity activity) {
		// TODO Auto-generated method stub
		if(activity instanceof IndexMainActivity && IsForegroundRunning){
			// ��������Ϣ
			mCurActivity = null;
			mCurFragment = null;
			IsForegroundRunning = false;
		}
	}

}
