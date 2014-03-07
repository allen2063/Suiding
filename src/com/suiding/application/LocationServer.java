package com.suiding.application;

import java.util.ArrayList;
import java.util.List;

import com.suiding.constant.FixedPositionEnum;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * 定位服务
 * 
 * @author SCWANG
 */
public class LocationServer {
	private static String mProvider = null;
	private static LocationManager mManager = null;
	private static List<String> ltDisableProvider = new ArrayList<String>();
	private static SuidingLocationListener mListenerGps = new SuidingLocationListener(
			LocationManager.GPS_PROVIDER);
	private static SuidingLocationListener mListenerNet = new SuidingLocationListener(
			LocationManager.NETWORK_PROVIDER);

	/**
	 * 定位获取 Location
	 */
	public static Location getLocation() {
		if (mManager != null) {
			if (mListenerGps.mIsValid) {
				return mManager.getLastKnownLocation(mListenerGps.mProvider);
			}
			return mManager.getLastKnownLocation(mListenerNet.mProvider);
		}
		return null;
	}

	/**
	 * 启动服务
	 */
	public static void startUp(Context context) {
		// TODO Auto-generated constructor stub
		if (mManager == null) {
			// 实例化一个LocationManager对象
			mManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);

			// 使用标准集合，让系统自动选择可用的最佳位置提供器，提供位置
			Criteria tCriteria = new Criteria();
			tCriteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
			tCriteria.setAltitudeRequired(false); // 不要求海拔
			tCriteria.setBearingRequired(false); // 不要求方位
			tCriteria.setCostAllowed(false); // 不允许有话费
			tCriteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

			mProvider = mManager.getBestProvider(tCriteria, true);
			if (mProvider == null) {
				mProvider = LocationManager.NETWORK_PROVIDER;
			}
			if (mProvider.equals(LocationManager.GPS_PROVIDER)) {
				/*
				 * 第二个参数表示更新的周期，单位为毫秒；第三个参数的含义表示最小距离间隔，单位是米 设定每30秒进行一次自动定位
				 */
				mListenerGps.mIsValid = true;
				// mManager.requestLocationUpdates(mProvider, 30000,
				// 50,mListenerGps);
			} else {
				mListenerNet.mIsValid = true;
			}
			// mProvider = mProvider ==
			// null?LocationManager.NETWORK_PROVIDER:mProvider;

			/*
			 * 第二个参数表示更新的周期，单位为毫秒；第三个参数的含义表示最小距离间隔，单位是米 设定每30秒进行一次自动定位
			 */
			try {
				mManager.requestLocationUpdates(mListenerNet.mProvider, 30000,
						50, mListenerNet);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
	}

	/**
	 * 关闭服务
	 */
	public static void shutDown() {
		// TODO Auto-generated constructor stub
		if (mManager != null) {
			mManager.removeUpdates(mListener);
			mManager = null;
		}
	}

	private static class SuidingLocationListener implements LocationListener {
		public String mProvider = null;
		public boolean mIsValid = false;

		public SuidingLocationListener(String provider) {
			mProvider = provider;
		}

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			SuidingApp app = SuidingApp.getApp();
			app.setFixedPosition(new LocationServer(), location,
					FixedPositionEnum.FIXED);
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			if (mProvider.equals(provider)) {
				mIsValid = false;
				mListenerNet.mIsValid = true;
			}
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			if (mProvider.equals(provider)) {
				mIsValid = true;
				mProvider = provider;
				if (provider.equals(LocationManager.GPS_PROVIDER)) {
					mListenerNet.mIsValid = false;
				}
			}
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * 方位改变时触发，进行调用
	 */
	private final static LocationListener mListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			SuidingApp app = SuidingApp.getApp();
			app.setFixedPosition(new LocationServer(), location,
					FixedPositionEnum.FIXED);
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			Boolean isHasDisable = false;
			for (int i = 0; i < ltDisableProvider.size(); i++) {
				String tProvider = ltDisableProvider.get(i);
				if (tProvider.equals(provider)) {
					isHasDisable = true;
				}
			}
			if (isHasDisable == false) {
				ltDisableProvider.add(provider);
			}
			if (mProvider.equals(provider)) {
				this.setBestProvider();
			}
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			for (int i = 0; i < ltDisableProvider.size(); i++) {
				String tProvider = ltDisableProvider.get(i);
				if (tProvider.endsWith(provider)) {
					ltDisableProvider.remove(i);
					break;
				}
			}
			this.setBestProvider();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		private void setBestProvider() {
			// TODO Auto-generated method stub
			Boolean isDisableNet = false;
			Boolean isDisableGps = false;
			for (int i = 0; i < ltDisableProvider.size(); i++) {
				String tProvider = ltDisableProvider.get(i);
				if (tProvider.endsWith(LocationManager.NETWORK_PROVIDER)) {
					isDisableNet = true;
				}
				if (tProvider.endsWith(LocationManager.GPS_PROVIDER)) {
					isDisableGps = true;
				}
			}
			if (isDisableGps == false
					&& !mProvider.equals(LocationManager.GPS_PROVIDER)) {
				// 移除监听器，在只有一个widget的时候，这个还是适用的
				mManager.removeUpdates(mListener);
				mProvider = LocationManager.GPS_PROVIDER;
				/*
				 * 第二个参数表示更新的周期，单位为毫秒；第三个参数的含义表示最小距离间隔，单位是米 设定每30秒进行一次自动定位
				 */
				mManager.requestLocationUpdates(mProvider, 30000, 50, mListener);

			} else if (isDisableNet == false
					&& !mProvider.equals(LocationManager.NETWORK_PROVIDER)) {
				// 移除监听器，在只有一个widget的时候，这个还是适用的
				mManager.removeUpdates(mListener);
				mProvider = LocationManager.NETWORK_PROVIDER;
				/*
				 * 第二个参数表示更新的周期，单位为毫秒；第三个参数的含义表示最小距离间隔，单位是米 设定每30秒进行一次自动定位
				 */
				mManager.requestLocationUpdates(mProvider, 30000, 50, mListener);
			}
		}
	};
}
