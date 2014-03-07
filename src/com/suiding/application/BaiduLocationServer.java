package com.suiding.application;

import java.util.Date;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.suiding.constant.FixedPositionEnum;
import com.suiding.thread.FixedCityTask;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.TimeSpan;

/**
 * 定位服务
 * @author SCWANG
 */
public class BaiduLocationServer implements BDLocationListener,Callback {

	//默认定位时间间隔	10分钟
	public static final int DURATION_NORMAL = 10*60*1000;
	//默认定位时间间隔	5分钟
	public static final int DURATION_SHORT = 5*60*1000;
	
	//定时 定位 Handle
	//百度定位主客户端
	private Handler mFixedHandle = null;
	private LocationClient mLocationClient = null;
	
	private static String mLastCityName = null;
	private static Location mLastLocation = null;
	private static BaiduLocationServer server = null;

	//上次定位时间
	private static Date mLastFixedCityNameTime = new Date(0);
	private static Date mLastFixedLocationTime = new Date(0);
	
	
	//定时定位时间间隔
	private static int mFixedDuration = DURATION_NORMAL;
	
	public static synchronized Location fixedLocation() throws Exception{
		TimeSpan span = TimeSpan.FromDate(mLastFixedLocationTime,new Date());
		if(span.Compare(TimeSpan.FromMinutes(1)) < 0){
			//如果距离上次定位时间小于1分钟
			//直接返回上次的定位结果
			return mLastLocation;
		}
		
		Location fixed = mLastLocation;//把 mLastLocation 暂存
		mLastLocation = null;//赋空，用作判断定位成功
		startUp();
		int count = 0;
		while (mLastLocation == null && count++ < 10) {
			Thread.sleep(500);
		}
		shutDown();
		if(mLastLocation == null){
			//如果定位不成功，恢复暂存数据
			mLastLocation = fixed;
		}
		return mLastLocation;
	}
	
	public static synchronized String fixedCityName() throws Exception{
		TimeSpan span = TimeSpan.FromDate(mLastFixedCityNameTime,new Date());
		if(span.Compare(TimeSpan.FromMinutes(30)) < 0){
			//如果距离上次定位时间小于30分钟
			//直接返回上次的定位结果
			return mLastCityName;
		}
		
		String fixed = mLastCityName;//把 mLastLocation 暂存
		mLastCityName = null;//赋空，用作判断定位成功
		startUp();
		int count = 0;
		while (mLastCityName == null && count++ < 10) {
			Thread.sleep(500);
		}
		shutDown();
		if(mLastCityName == null){
			//如果定位不成功，恢复暂存数据
			mLastCityName = fixed;
		}
		return mLastCityName;
	}
	/**
	 * 初始化
	 */
	public static void initialize(Context context) {
		if(server == null){
			server = new BaiduLocationServer(context);
		}
	}
	
	/**
	 * 启动服务
	 */
	private static void startUp() {
		if(server != null){
			server.start();
		}
	}
	/**
	 * 关闭服务
	 */
	private static void shutDown() {
		// TODO Auto-generated constructor stub
		if (server != null) {
			server.stop();
		}
	}
	
	public static String getLastCityName() {
		return mLastCityName;
	}
	
	public static Location getLastLocation() {
		return mLastLocation;
	}

	private BaiduLocationServer(Context context){
		mLocationClient = new LocationClient(context);
		mLocationClient.setAK("EF32e5611f4db2a28e52105b0d941b59");
		//mLocationClient.setAK("FBd226df2ead6369fe566f9a855d983e");//617
		//mLocationClient.setAK("EF32e5611f4db2a28e52105b0d941b59");//scwangpc
		LocationClientOption option = new LocationClientOption();
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(500);// 设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);// 禁止启用缓存定位
		option.setPoiNumber(5);// 最多返回POI个数
		option.setPoiDistance(1000);// poi查询距离
		option.setPoiExtraInfo(true);// 是否需要POI的电话和地址等详细信息
		
		//读取设置信息如果 设置使用GPS 就打开GPS
		AppSettings setting = AppSettings.getInstance();
		if(setting.isUserGpsFixed()){
			option.setOpenGps(true);
		}

		mLocationClient.setLocOption(option);
		// 注册监听函数
		mLocationClient.registerLocationListener(this);
		//启动定时定位
		mFixedHandle = new Handler(SuidingApp.getLooper(), this);
		mFixedHandle.sendMessageDelayed(Message.obtain(), mFixedDuration);//设置时间
	}
	
	private void start() {
		// TODO Auto-generated method stub
		if(!mLocationClient.isStarted()){
			mLocationClient.start();
			if (mLocationClient.isStarted())
				mLocationClient.requestLocation();
			else
				Log.d("LocSDK3", "locClient is null or not started");
		}
	}

	protected void stop() {
		// TODO Auto-generated method stub
		mLocationClient.stop();
	}
	
	public void onReceiveLocation(BDLocation location) {
		if (location == null)
			return;
		StringBuffer sb = new StringBuffer(256);
		sb.append("time : ");
		sb.append(location.getTime());
		sb.append("\nerror code : ");
		sb.append(location.getLocType());
		sb.append("\nlatitude : ");
		sb.append(location.getLatitude());
		sb.append("\nlontitude : ");
		sb.append(location.getLongitude());
		sb.append("\nradius : ");
		sb.append(location.getRadius());
		if (location.getLocType() == BDLocation.TypeGpsLocation) {
			sb.append("\nspeed : ");
			sb.append(location.getSpeed());
			sb.append("\nsatellite : ");
			sb.append(location.getSatelliteNumber());
		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
		}
		logMsg(sb.toString());
		
//		SuidingApp app = SuidingApp.getApp();
		
		if(location.getLocType() == 166){
			return;
		}
		
		if(location.getLocType() < 162){
			mLastLocation = new Location("Baidu");
			mLastLocation.setLatitude(location.getLatitude());
			mLastLocation.setLongitude(location.getLongitude());
			//保存定位成功时间
			mLastFixedLocationTime = new Date();
//			app.setFixedPosition(this, mLastLocation,FixedPositionEnum.FIXED);
		}
		
		String city = location.getCity();
		if(city != null && city.length() > 0){
			mLastCityName = city;
			//保存定位成功时间
			mLastFixedCityNameTime = new Date();
//			if(app.getFixedCityStatus() == FixedCityEnum.FIXED){
//				String cityname = CityNameUtil.SimplifyCityName(mLastCityName);
//				if(!cityname.equals(app.getCityName())){
//					//new FixedCityTask(mLastCityName).post();
//				}
//			}else{
//				new FixedCityTask(mLastCityName).post();
//			}
		}
	}

	public void onReceivePoi(BDLocation poiLocation) {
		if (poiLocation == null) {
			return;
		}
//		StringBuffer sb = new StringBuffer(256);
//		sb.append("Poi time : ");
//		sb.append(poiLocation.getTime());
//		sb.append("\nerror code : ");
//		sb.append(poiLocation.getLocType());
//		sb.append("\nlatitude : ");
//		sb.append(poiLocation.getLatitude());
//		sb.append("\nlontitude : ");
//		sb.append(poiLocation.getLongitude());
//		sb.append("\nradius : ");
//		sb.append(poiLocation.getRadius());
//		if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
//			sb.append("\naddr : ");
//			sb.append(poiLocation.getAddrStr());
//		}
//		if (poiLocation.hasPoi()) {
//			sb.append("\nPoi:");
//			sb.append(poiLocation.getPoi());
//		} else {
//			sb.append("noPoi information");
//		}
//		logMsg(sb.toString());
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		TaskBase task = TaskBase.getTask(msg);
		if(task == null){//没有任务项，是定时器发送的消息
			//启动定位任务
			SuidingApp.postTask(new FixedTask(mFixedHandle));
		}else if(task.mResult == TaskBase.RESULT_FINISH){//定位任务完成
			FixedTask ftask = (FixedTask)task;
			String cityname = ftask.mCityName;
			
			SuidingApp app = SuidingApp.getApp();
			if(cityname != null && cityname.length() > 0){
				new FixedCityTask(cityname).post();
			}
			Location location = ftask.mFoxedPos;
			if(location != null){
				app.setFixedPosition(this, location,FixedPositionEnum.FIXED);
			}
			//正常启动定时下次定位
			mFixedDuration = DURATION_NORMAL;
			mFixedHandle.sendMessageDelayed(Message.obtain(), mFixedDuration);
		}else{//定位失败
			//缩短定时间隔
			mFixedDuration = DURATION_SHORT;
			mFixedHandle.sendMessageDelayed(Message.obtain(), mFixedDuration);
		}
		return true;
	}
	
	private void logMsg(String string) {
		// TODO Auto-generated method stub
		//showToastShort(string);
		//Toast.makeText(SuidingApp.getApp(), string, Toast.LENGTH_SHORT).show();
	}
	
	private class FixedTask extends TaskBase{
		
		public String mCityName = null;
		public Location mFoxedPos = null;

		public FixedTask(Handler handler){
			super(handler);
			// TODO Auto-generated method stub
		}
		
		@Override
		protected void onWorking(Message msg) throws Exception {
			// TODO Auto-generated method stub
			mFoxedPos = fixedLocation(); 
			mCityName = fixedCityName();
		}
		
	}
}
