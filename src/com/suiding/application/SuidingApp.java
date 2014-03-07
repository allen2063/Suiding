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
 * @author SCWANG 负责 全局数据的存储 和 事件通知
 */
public class SuidingApp extends Application {

	/**
	 * interface INotifyNeedUpdate
	 *  
	 * @author SCWANG 需要更新通知接口
	 */
	public interface INotifyNeedUpdate {
		void onNotifyNeedUpdate(String curver, String server);
	}

	/**
	 * interface INotifyNetworkStatus
	 *  
	 * @author SCWANG 网络状态改变通知接口
	 */
	public static interface INotifyNetworkStatus {
		void onNetworkStatusChanged(int networkStatus);
	}

	/**
	 * interface INotifyFixedPosition
	 * 
	 * @author SCWANG GPS定位改变通知接口
	 */
	public static interface INotifyFixedPosition {
		void onFixedPositionChanged(Location location, int status);
	}

	/**
	 * interface INotifyFixedPosition
	 * 
	 * @author SCWANG 城市定位改变通知接口
	 */
	public static interface INotifyFixedCity {
		void onFixedCityChanged(Area area, int status);
	}

	/**
	 * interface INotifyLoginUser
	 * 
	 * @author SCWANG 登录用户改变通知接口
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
	 * 获取内部版本
	 * 
	 * @return
	 */
	public static synchronized String getVersion() {
		// TODO Auto-generated method stub
		return mLeSouShopApp.mVersion;
	}

	/**
	 * 获取内部版本代码
	 * 
	 * @return
	 */
	public static synchronized int getVersionCode() {
		// TODO Auto-generated method stub
		return VersionUtil.transformVersion(mLeSouShopApp.mVersion);
	}
	
	// 定位结果
	private Area mArea = null;
	private Location mLocation = null;
	// 上次定位事件
	private Date mLastFixedCity = new Date(0);
	private Date mLastFixedPosition = new Date(0);
	// 定位间隔 5 分钟
	private TimeSpan mFixedSpan = TimeSpan.FromMinutes(5);
	// 当前登录用户
	private Consumer mLoginUser = null;
	// 当前定位状态 默认为 未定位
	private int mFixedCityStatus = FixedCityEnum.NONE;
	private int mFixedPositionStatus = FixedPositionEnum.NONE;
	// 当前网络连接类型 默认为 未连接
	private int mNetworkStatus = NetworkUtil.TYPE_NONE;
	// 当前主页面
	private Activity mCurActivity = null;
	// 当前主页面
	private Fragment mCurFragment = null;
	// 当前版本
	private String mVersion = "0.0.0.0";
	// 当前版本
	private String mServerVersion = "0.0.0.0";

	// 主程序的线程Worker
	private Looper mLooper = null;
	// private ThreadWorker mWorker = null;
	private boolean mIsGoingHome = false;
	private boolean mIsInitialized = false;
	//标记前台是否在运行
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
			//设置 SharedPreferences 路劲
			//setPreferencesPath();
			//初始化震动控制台
			VibratorConsole.initialize(getAppContext());
			//初始化AppSettings
			AppSettings.initialize(getAppContext());
			//初始化地址服务
			AddressService.startUp(getAppContext());
			//初始化通知中心
			NotifyCenter.initailize(getAppContext());
			// 启动定位服务
			BaiduLocationServer.initialize(getAppContext());
			//设置图片缓存路径
			ImageCaches.initialize(this,getCachesPath("image"));

			// 检查数据库
			new DatabaseUtil(getAppContext()).checkDataBaseVersion();
			// App 启动的时候 检查网络相关设置
			mNetworkStatus = NetworkUtil.getNetworkState(this);
			if(mNetworkStatus != NetworkUtil.TYPE_NONE){
				//检测更新
				UpdateService.checkUpdate();
				//自动登录
				UserLoginActivity.autoLodin();
			}
			//初始化版本号
			getPackageVersion();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();//handled
			AppExceptionHandler.handler(e, "SuidingApp，onCreate 初始化失败");
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
//		// 获取ContextWrapper对象中的mBase变量。该变量保存了ContextImpl对象  
//		field = ContextWrapper.class.getDeclaredField("mBase");  
//		field.setAccessible(true);  
//		// 获取mBase变量  
//		Object obj = field.get(this);  
//		// 获取ContextImpl.mPreferencesDir变量，该变量保存了数据文件的保存路径  
//		field = obj.getClass().getDeclaredField("mPreferencesDir");  
//		field.setAccessible(true);  
//		// 创建自定义路径  
//		File file = new File(getWorkspacePath("Preferences"));  
//		// 修改mPreferencesDir变量的值  
//		field.set(obj, file);  
//	}

	/**
	 * 获取GPS定位状态
	 * 
	 * @return
	 */
	public synchronized int getFixedPositionStatus() {
		// TODO Auto-generated method stub
		initializeFixedPosition();
		return mFixedPositionStatus;
	}

	/**
	 * 获取GPS定位状态
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
	 * 获取城市定位状态
	 * 
	 * @return
	 */
	public synchronized int getFixedCityStatus() {
		// TODO Auto-generated method stub
		return mFixedCityStatus;
	}

	/**
	 * 获取城市定位状态 如果 状态为失败 或者无效 更新定位
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
	 * 获取定位Area
	 * 
	 * @return
	 */
	public synchronized Area getFixedArea() {
		// TODO Auto-generated method stub
		return mArea;
	}

	/**
	 * 获取定位Location
	 * 
	 * @return
	 */
	public synchronized Location getFixedLocation() {
		// TODO Auto-generated method stub
		return mLocation;
	}

	/**
	 * 获取CurActivity
	 * 
	 * @return
	 */
	public synchronized Activity getCurActivity() {
		// TODO Auto-generated method stub
		return mCurActivity;
	}

	/**
	 * 获取App工作目录
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
	 * 获取caches目录
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
	 * 获取城市名称
	 * 
	 * @param cityName
	 */
	public synchronized String getCityName() {
		switch (mFixedCityStatus) {
		default:
		case FixedCityEnum.NONE: {
			switch (mFixedPositionStatus) {
			case FixedPositionEnum.FAIL:
				return "定位失败";
			case FixedPositionEnum.FIXEDING:
				return "正在定位";
			}
			return "无定位信息";
		}
		case FixedCityEnum.FIXEDING:
			return "正在定位";
		case FixedCityEnum.FAIL:
			return "定位失败";
		case FixedCityEnum.FIXED:
		// case FixedCityEnum.FIXEDCITY_SELECED:
		{
			return CityNameUtil.SimplifyCityName(mArea.Name);
		}
		}
	}
	/**
	 * 获取服务器最新版本
	 * @return
	 */
	public String getServerVersion() {
		return mServerVersion;
	}
	/**
	 * 获取App是否 需要更新
	 * @return
	 */
	public boolean isNeedUpdate() {
		// TODO Auto-generated method stub
		int curver = VersionUtil.transformVersion(mVersion);
		int server = VersionUtil.transformVersion(mServerVersion);
		return curver < server;
	}
	/**
	 * 获取App是否 执行过 initialize
	 * @return
	 */
	public synchronized boolean isInitialize(){
		return mIsInitialized;
	}
	/**
	 * 在每次程序启动的时候初始化一遍
	 * 
	 * @param power
	 *            用于权限验证
	 */
	public synchronized void initialize(Object power) {
		if (!mIsInitialized && (
				power instanceof WelcomeActivity
				|| power instanceof IndexMainActivity)) {
			try {
				initializeFixedPosition();
				initializeFixedCity();
				//标识初始化完成
				mIsInitialized = true;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.handler(e, "SuidingApp，initialize 初始化失败");
			}
		}
	}
	/**
	 * 设置当前的页面
	 * 
	 * @param type
	 *            用于权限验证
	 * @param activity
	 *            当前的 Activity
	 */
	public synchronized void setCurActivity(Object power, Activity activity) {
		if (power instanceof ActivityBase || power instanceof FragmentActivityBase) {
			//如果正在返回主页面
			if(mIsGoingHome){
				//如果已经到达主页面
				if(activity instanceof IndexMainActivity){
					//关闭返回主页面功能
					mIsGoingHome = false;
				}else{
					//关不当前页面回到主页面
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
	 * 设置当前的页面
	 * 
	 * @param type
	 *            用于权限验证
	 * @param fragment
	 *            当前的 Fragment
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
	 * 设置当前的登录用户
	 * 
	 * @param power
	 *            用于权限验证 传入this
	 * @param user
	 *            当前的 登录用户
	 */
	public synchronized void setLoginUser(Object power, Consumer user) {
		if (power instanceof UserRegisterActivity
				|| power instanceof UserLoginActivity
				|| power instanceof IndexAccountFragment
				|| power instanceof IndexAccountOldFragment
				|| power instanceof EditAccountActivity) {
			mLoginUser = user;
			// 发送到 用户中心
			UserCenter.setLoginUser(this, user);
			// 发送定位信息改变通知
			notifyLoginUser(mCurActivity, mLoginUser);
			notifyLoginUser(mCurFragment, mLoginUser);
		}
	}
	/**
	 * 设置当前用户订单数量改变
	 * @param power
	 *            用于权限验证 传入this
	 * @param userid
	 * @param wait
	 * @param confirm
	 * @param none
	 */
	public synchronized void setOrderNumber(Object power,UUID userid,long wait,long confirm,long none) {
		// TODO Auto-generated method stub
		if (power instanceof UserCenterTask
				|| power instanceof OrderConsoleTask) {
			// 发送定位信息改变通知
			notifyOrderNumber(mCurActivity, userid,wait,confirm,none);
			notifyOrderNumber(mCurFragment, userid,wait,confirm,none);
		}
	}
	/**
	 * 通知APP 订单已经改变
	 * @param power 权限对象 传入this
	 * @param user
	 * @param mltchanged
	 */
	public synchronized void setRemindOrder(Object power, UUID userid,List<Order> mltchanged) {
		// TODO Auto-generated method stub
		if(power instanceof UserCenterTask && IsForegroundRunning){
			// 发送定位信息改变通知
			notifyRemindOrder(mCurActivity, userid,mltchanged);
			notifyRemindOrder(mCurFragment, userid,mltchanged);
		}
	}
	
	/**
	 * 设置当前用户收藏数量改变
	 * @param power
	 *            用于权限验证 传入this
	 * @param favorite
	 */
	public synchronized void setFavoriteNumber(Object power,UUID userid,long favorite) {
		// TODO Auto-generated method stub
		if (power instanceof UserCenterTask) {
			// 发送定位信息改变通知
			notifyFavoriteNumber(mCurActivity, userid,favorite);
			notifyFavoriteNumber(mCurFragment, userid,favorite);
		}
	}

	/**
	 * 设置GPS定位结果
	 * 
	 * @param power
	 *            传入this指针 用于验证权限
	 * @param location
	 * @param status
	 *            只有成功和失败两种
	 */
	public synchronized void setFixedPosition(Object power, Location location, int status) {
		// TODO Auto-generated method stub
		if (power instanceof FixedPositionTask
				|| power instanceof BaiduLocationServer
				|| power instanceof FixedCityActivity) {

			// 设置定位信息
			mLocation = location;
			mFixedPositionStatus = status;
			// 如果成功定位，保存定位信息
			if (mFixedPositionStatus == FixedPositionEnum.FIXED) {
				// 把数据更新到缓存和数据库中
				FixedPositionTask.putFixedPositionCaches(mLocation);
				// 完成GPS定位之后 进行城市定位
				//if (mFixedCityStatus != FixedCityEnum.FIXED) {
				//	mFixedCityStatus = FixedCityEnum.FIXEDING;
				//}
				//new FixedCityTask(mLocation).post();
			}
			// 如果GPS定位不成功，检查城市定位是不是正在定位
//			else if (mFixedCityStatus == FixedCityEnum.FIXEDING) {
//				// 城市定位依赖于GPS定位，GPS失败则城市定位也失败
//				mFixedCityStatus = FixedCityEnum.FAIL;
//				// 发送定位信息改变通知
//				notifyFixedCity(mCurActivity, null,
//						FixedCityEnum.FAIL);
//				notifyFixedCity(mCurFragment, null,
//						FixedCityEnum.FAIL);
//			}
			
			// 发送定位信息改变通知
			notifyFixedPosition(mCurActivity, mLocation, status);
			notifyFixedPosition(mCurFragment, mLocation, status);
		}
	}

	/**
	 * 设置城市定位结果
	 * 
	 * @param power
	 *            传入this指针 用于验证权限
	 * @param area
	 * @param status
	 *            只有成功和失败两种
	 */
	public synchronized void setFixedCity(Object power, Area area, int status) {
		// TODO Auto-generated method stub
		if (power instanceof FixedCityTask || power instanceof FixedCityActivity) {
			// 设置定位信息
			mArea = area;
			mFixedCityStatus = status;
			// 发送定位信息改变通知
			notifyFixedCity(mCurActivity, mArea, status);
			notifyFixedCity(mCurFragment, mArea, status);

			// 如果成功定位，保存定位信息
			if (mFixedCityStatus == FixedCityEnum.FIXED
			/* || mFixedCityStatus == FixedCityEnum.FIXEDCITY_SELECED */) {
				// 把数据更新到缓存和数据库中
				FixedCityTask.putFixedCityCaches(mArea);
			}
		}
	}

	/**
	 * 设置App网络状态
	 * 
	 * @param power
	 *            传入this指针 用于验证权限
	 * @param networkState
	 *            指定的网络状态
	 */
	public synchronized void setNetworkStatus(Object power, int networkState) {
		// TODO Auto-generated method stub
		if (power instanceof ConnectionChangeReceiver) {
			mNetworkStatus = networkState;
			
			UserCenter.setNetworkStatus(this, networkState);
			
			notifyNetworkStatus(mCurActivity, networkState);
			notifyNetworkStatus(mCurFragment, networkState);

			// 如果网络连接上
			if (mNetworkStatus != NetworkUtil.TYPE_NONE) {
				// 如果还没有成功定位
				if (mFixedPositionStatus != FixedPositionEnum.FIXED) {
					// 初始化定位信息
					initializeFixedPosition();
				}
			}
		}
	}

	/**
	 * 设置 服务器 App 版本
	 * 
	 * @param power
	 *            传入this指针 用于验证权限
	 * @param version
	 *            服务器版本
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
	 * 向 power 发送 需要更新通知
	 * 
	 * @param power
	 *            通知的对象 必须实现 INotifyNeedUpdate 接口
	 * @param networkState
	 *            当前网络状态
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
				AppExceptionHandler.handler(e, "SuidingApp，通知需要更新时抛出异常");
			}
		}
	}

	/**
	 * 向 power 发送 网络状态改变通知
	 * 
	 * @param power
	 *            通知的对象 必须实现 INotifyNetworkStatus 接口
	 * @param networkState
	 *            当前网络状态
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
				AppExceptionHandler.handler(e, "SuidingApp，通知网络状态改变时抛出异常");
			}
		}
	}

	/**
	 * 向 power 发送 切换城市通知
	 * 
	 * @param power
	 *            通知的对象 必须实现 INotifySwitchOverCity 接口
	 * @param status
	 * @param location
	 *            当前定位信息
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
				AppExceptionHandler.handler(e, "SuidingApp，通知城市定位改变时抛出异常");
			}
		}
	}

	/**
	 * 向 power 发送 定位信息改变通知
	 * 
	 * @param power
	 *            通知的对象 必须实现 INotifyFixedPosition 接口
	 * @param location
	 *            当前定位信息
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
				AppExceptionHandler.handler(e, "SuidingApp，通知点定位改变时抛出异常");
			}
		}
	}

	/**
	 * 向 power 发送 登录用户改变通知
	 * 
	 * @param power
	 *            通知的对象 必须实现 INotifyLoginUser 接口
	 * @param user
	 *            当前登录用户
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
				AppExceptionHandler.handler(e, "SuidingApp，通知用户登录改变时抛出异常");
			}
		}
	}

	/**
	 * 设置当前用户订单数量改变
	 * @param power
	 *            用于权限验证 传入this
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
				AppExceptionHandler.handler(e, "SuidingApp，通知订单数量改变时抛出异常");
			}
		}
	}
	/**
	 * 设置当前用户收藏数量改变
	 * @param power
	 *            用于权限验证 传入this
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
				AppExceptionHandler.handler(e, "SuidingApp，通知收藏数量改变时抛出异常");
			}
		}
	}

	/**
	 * 通知APP 订单已经改变
	 * @param power 权限对象 传入this
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
				AppExceptionHandler.handler(e, "SuidingApp，通知订单提醒改变时抛出异常");
			}
		}
	}
	/**
	 * 初始化定位信息
	 */
	private synchronized void initializeFixedPosition() {
		// TODO Auto-generated method stub
		// App 启动的时候 执行定位相关操作
		switch (mFixedPositionStatus) {
		case FixedPositionEnum.FIXEDING:
		case FixedPositionEnum.FIXED:
			break;
		default:
		case FixedPositionEnum.FAIL:
		case FixedPositionEnum.NONE:
			// 定位记录过期 重新定位
			if (FixedPositionTask.getFixedPositionCachesIsTimeout() == false) {
				// 如果距离上次定位 超过 规定时间5分钟
				if (mFixedSpan.Compare(TimeSpan.FromDate(mLastFixedPosition,
						new Date())) < 0) {
					// 开始城市定位
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
	 * 初始化定位信息
	 */
	private synchronized void initializeFixedCity() {
		// App 启动的时候 执行定位相关操作
		switch (mFixedCityStatus) {
		case FixedCityEnum.FIXED:
		case FixedCityEnum.FIXEDING:
			break;
		default:
		case FixedCityEnum.FAIL:
		case FixedCityEnum.NONE:

			// 先判断城市定位信息是否过期
			if (FixedCityTask.getFixedCityCachesIsTimeout() == false) {
				// 如果过期在判断GPS定位的状态
//				switch (mFixedPositionStatus) {
//				default:
//				case FixedPositionEnum.NONE:// GPS定位无效
//					// 激活GPS定位
//					initializeFixedPosition();
//					// 将城市定位状态暂时 定为无效 GPS激活后自动更改
//					mFixedCityStatus = FixedCityEnum.NONE;
//					break;
//				case FixedPositionEnum.FAIL:// GPS定位失败，
//					// 激活GPS定位
//					initializeFixedPosition();
//					// 将城市定位状态暂时 定为失败 GPS激活后自动更改
//					mFixedCityStatus = FixedCityEnum.FAIL;
//					break;
//				case FixedPositionEnum.FIXEDING:
//					mFixedCityStatus = FixedCityEnum.FIXEDING;
//					// GPS定位正在定位不用处理 GPS定位成功自动开启城市定位
//					break;
//				case FixedPositionEnum.FIXED:// GPS定位成功
//					// 如果距离上次定位 超过 规定时间5分钟
//					if (mFixedSpan.Compare(TimeSpan.FromDate(mLastFixedCity,
//							new Date())) < 0) {
//						// 开始城市定位
//						mFixedCityStatus = FixedCityEnum.FIXEDING;
//						new FixedCityTask(mLocation).post();
//					}
//					break;
//				}
				// 如果距离上次定位 超过 规定时间5分钟
				if (mFixedSpan.Compare(TimeSpan.FromDate(mLastFixedCity,
						new Date())) < 0) {
					// 开始城市定位
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
	 * 打开城市定位页面
	 * 	用于定位失败的时候手动定位，并且提示信息
	 * 	如果当前无网络访问，就提示定位失败，不打开页面
	 * 	@param activity 当前页面
	 */
	public synchronized void InvokeFixedCityActivity(Activity activity) {
		// TODO Auto-generated method stub
		if(mNetworkStatus == NetworkUtil.TYPE_NONE){
			// 并且提示 手动设置
			Toast.makeText(this,
					"系统暂时无法定位您的位置信息。", Toast.LENGTH_SHORT).show();
		}else{
			// 跳转到手动定位页面
			activity.startActivity(new Intent(activity, FixedCityActivity.class));
			// 并且提示 手动设置
			Toast.makeText(this,
					"系统暂时无法定位您的位置信息，您需要手动定位。", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 从当前页面 activity 返回到前台主页面
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
				AppExceptionHandler.handler(e, "SuidingApp，返回主页面，关闭临时页面出错");
			}
		}
	}

	/**
	 * 通知APP 启动天台页面
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
	 * 获取 前台页面是否在运行
	 * @return
	 */
	public synchronized boolean getIsForegroundRunning() {
		// TODO Auto-generated method stub
		return IsForegroundRunning;
	}

	/**
	 * 通知APP 前台已经关闭
	 * @param activity 权限对象 传入this
	 */
	public synchronized void notifyForegroundClosed(Activity activity) {
		// TODO Auto-generated method stub
		if(activity instanceof IndexMainActivity && IsForegroundRunning){
			// 清空相关信息
			mCurActivity = null;
			mCurFragment = null;
			IsForegroundRunning = false;
		}
	}

}
