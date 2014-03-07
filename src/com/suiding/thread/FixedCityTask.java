package com.suiding.thread;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Message;

import com.suiding.application.AppExceptionHandler;
import com.suiding.application.BaiduLocationServer;
import com.suiding.application.SuidingApp;
import com.suiding.constant.FixedCityEnum;
import com.suiding.dao.AreaEntityDao;
import com.suiding.domain.IAreaDomain;
import com.suiding.entity.AreaEntity;
import com.suiding.model.Area;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.CityNameUtil;
import com.suiding.util.TimeSpan;
import com.suiding.util.XmlCacheUtil;

/**
 * 城市定位
 * 
 * @author SCWANG 需要传入GPS定位参数
 */
public final class FixedCityTask extends TaskBase {

	// 定位结果
	private Area mArea = null;
	private String mCityName = "";
	// private Location mLocation = null;
	// private List<Address> mltAddress = null;

	// 任务逻辑
	public boolean mSuccess = false;

	// 标识是否按照规定的使用post函数 抛送 FixedPositionTask
	private Boolean mIsPostByRule = false;

	public FixedCityTask(String cityname) {
		super(SuidingApp.getLooper());
		mCityName = cityname;
	}
	public FixedCityTask(Location location) {
		super(SuidingApp.getLooper());
		// mLocation = location;
	}

	/**
	 * 把任务post到App的Worker执行
	 */
	public final void post() {
		// 标识已经按照post抛送 ImageTask
		mIsPostByRule = true;
		// 把任务发送到App线程中去执行
		SuidingApp.postTask(this);
	}

	@Override
	protected final void onWorking(Message tMessage) throws Exception {
		// TODO Auto-generated method stub
		if (mIsPostByRule == true) {
//			int mCount = 0;
//			do {
//				mCount++;
//				if(mCityName == null){
//					mCityName = BaiduLocationServer.getLastCityName();
//				}
//				// mLocation = LocationServer.getLocation();
//				// if (mLocation != null) {
//				// // 根据定位信息读取区域信息
//				// List<Address> mltAddress = LocationUtil
//				// .getAddressByLocation(mLocation,
//				// FixedCityActivity.this);
//				// mCityName = LocationUtil
//				// .getCityNameByAddress(mltAddress);
//				// mCityName = CityNameUtil.SimplifyCityName(mCityName);
//				// } else
//				if (mCityName == null) {
//					if (mCount > 15) {
//						throw new Exception("暂时无法获取你的位置信息");
//					}else{
//						Thread.sleep(1000);
//					}
//				}
//			} while (mCount <= 5 && mCityName == null);
			// SuidingApp tApp = SuidingApp.getApp();
			// Activity acvivity = tApp.getCurActivity();
			// Context context = acvivity == null ? tApp : acvivity
			// .getBaseContext();

			// mltAddress = LocationUtil.getAddressByLocation(mLocation,
			// context);
			// 根据定位信息读取区域信息
			// mCityName = LocationUtil.getCityNameByAddress(mltAddress);
			mCityName = BaiduLocationServer.fixedCityName();
			if (mCityName == null) {
				throw new Exception("获取城市信息失败");
			}
			mCityName = CityNameUtil.SimplifyCityName(mCityName);
			// 首先读取本地数据库缓存
			mArea = getFixedCityCaches(mCityName);

			if (mArea == null) {
				// 如果没有缓存读取服务器
				IAreaDomain tAreaDomain = DomainFactory.getAreaDomain();
				mArea = tAreaDomain.getAreaByNameLike(mCityName);
				if (mArea != null) {
					// 添加到本地数据库缓存
					//putFixedCityCaches(mArea);
				} else {
					// 服务器不存在区域信息 暂不处理
				}
			}
			mSuccess = true;
		} else {
			throw new Exception("请使用FixedCityTask.post()抛送任务！");
		}
	}

	@Override
	protected boolean onHandle(Message msg) {
		// TODO Auto-generated method stub
		this.submit();
		return false;
	}

	/**
	 * 把定位结果提交到APP中
	 */
	private void submit() {
		// TODO Auto-generated method stub
		SuidingApp tApp = SuidingApp.getApp();
//		Toast.makeText(SuidingApp.getApp(), "后台定位结果：" + mCityName,
//				Toast.LENGTH_SHORT).show();
		switch (tApp.getFixedCityStatus()) {
		default:
		case FixedCityEnum.FIXEDING:
		case FixedCityEnum.NONE:
		case FixedCityEnum.FAIL:
			if (mSuccess) {
				tApp.setFixedCity(this, mArea, FixedCityEnum.FIXED);
			} else {
				tApp.setFixedCity(this, mArea, FixedCityEnum.FAIL);
			}
			break;
		case FixedCityEnum.FIXED:
			// case FixedCityEnum.FIXEDCITY_SELECED:
			if (mSuccess) {
				if (tApp.getFixedArea().getID() != mArea.getID()) {
					this.affirmCityName(mCityName);
				}
			} else {
				// Toast.makeText(tApp, "更新位置信息失败", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}

	/**
	 * 验证城市定位缓存是否过期
	 * 
	 * @param cityname
	 * @return true 有效 false 过期2
	 */
	public static synchronized boolean getFixedCityCachesIsTimeout() {
		SharedPreferences tShared = XmlCacheUtil.getSharedPreferences();
		long tLongTime = tShared.getLong(XmlCacheUtil.FIXEDCITY_UPDATETIME, 0);
		if (TimeSpan.FromDate(new Date(tLongTime), new Date()).getTotalDays() > 2) {
			return false;
		}
		return true;
	}

	/**
	 * 获取城市缓存 名称
	 * 
	 * @return
	 */
	public static synchronized String getFixedCityNameCaches() {
		// TODO Auto-generated method stub
		SharedPreferences tShared = XmlCacheUtil.getSharedPreferences();
		return tShared.getString(XmlCacheUtil.FIXED_CITYNAME, null);
	}

	/**
	 * 获取城市缓存
	 * 
	 * @param cityname
	 * @return
	 */
	public static synchronized Area getFixedCityCaches(String cityname) {
		// 首先读取本地数据库缓存
		AreaEntityDao tDao = new AreaEntityDao(SuidingApp.getAppContext());
		AreaEntity tAreaEntity = tDao.getByNameLike(cityname);
		if (tAreaEntity != null) {
			List<AreaEntity> ltAreaEntity = tDao.getChildren(tAreaEntity);
			if (ltAreaEntity.size() > 0) {
				Area tArea = tAreaEntity.getModel();
				tArea.Children = AreaEntity.listToAreaModel(ltAreaEntity);
				return tArea;
			}
		}
		return null;
	}

	/**
	 * 保存城市缓存
	 * 
	 * @param area
	 */
	public static synchronized void putFixedCityCaches(Area area) {
		// 把数据更新到SharedPreferences
		try {
			// 把数据更新到数据库中
			AreaEntityDao tDao = new AreaEntityDao(SuidingApp.getAppContext());
			tDao.addWithCheckExistID(new AreaEntity(area));
			// 并把城市区域信息添加到数据库
			if (area.Children != null) {
				for (Area tArea : area.Children) {
					tDao.addWithCheckExistID(new AreaEntity(tArea));
				}
			}
			//更新时间
			XmlCacheUtil.putDate(XmlCacheUtil.FIXEDCITY_UPDATETIME,new Date());
			XmlCacheUtil.putString(XmlCacheUtil.FIXED_CITYNAME, area.Name);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();//handled
			AppExceptionHandler.handler(e, "切换城市任务，putFixedCityCaches 出现异常");
		}
	}

	/**
	 * 用于控制城市切换提问，程序启动后只问一次
	 */
	private static boolean mIsAffirmed = false; 
	/**
	 * 确认切换到城市
	 * 
	 * @param cityName
	 */
	private void affirmCityName(String cityName) {
		// TODO Auto-generated method stub
		Activity activity = SuidingApp.getApp().getCurActivity();
		
		if (activity != null && !mIsAffirmed) {
			mIsAffirmed = true;
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setIcon(android.R.drawable.ic_dialog_alert);
			builder.setTitle("定位信息");
			builder.setMessage("您当前所在的城市是【" + cityName + "】，是否要切换到该城市。");
			builder.setNegativeButton("切换",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							SuidingApp tApp = SuidingApp.getApp();
							tApp.setFixedCity(FixedCityTask.this, mArea,
									FixedCityEnum.FIXED);
						}
					});
			builder.setPositiveButton("不切换",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			builder.create();
			builder.show();
		}
	}
}
