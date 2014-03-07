package com.suiding.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.suiding.activity.DetailProductActivity;
import com.suiding.activity.DetailStoreActivity;
import com.suiding.adapter.TwitterListAdapter;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.application.AppExceptionHandler;
import com.suiding.application.SuidingApp;
import com.suiding.application.SuidingApp.INotifyFixedCity;
import com.suiding.application.SuidingApp.INotifyFixedPosition;
import com.suiding.bean.Filter;
import com.suiding.constant.FixedCityEnum;
import com.suiding.constant.FixedPositionEnum;
import com.suiding.constant.StoreTypeEnum;
import com.suiding.dao.CouponEntityDao;
import com.suiding.domain.ICouponDomain;
import com.suiding.entity.CouponEntity;
import com.suiding.fragment.framework.ListViewFragment;
import com.suiding.layoutbind.ModuleFilter;
import com.suiding.layoutbind.ModuleNodata;
import com.suiding.model.Address;
import com.suiding.model.Area;
import com.suiding.model.Coupon;
import com.suiding.model.Page;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.thread.SortStoreTask;
import com.suiding.thread.framework.ListViewTask;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.CityNameUtil;
import com.suiding.util.DistanceUtil;
import com.suiding.util.ExtraUtil;
import com.suiding.util.LeSouException;
import com.suiding.util.TimeSpan;
import com.suiding.util.XmlCacheUtil;

public class IndexTwitterFragment extends ListViewFragment implements
		INotifyFixedCity,INotifyFixedPosition {

	//private static final String CACHE_TYPE = "Twitter_CACHE_TYPE";
	private static final String CACHE_DATE = "Twitter_CACHE_DATE";
	private static final String CACHE_SORT = "Twitter_CACHE_SORT";
	
	//加载时间间隔
	private static final TimeSpan mLoadSpan = TimeSpan.FromMinutes(30);
	/**
	 * 选择器
	 */
	private Filter mSort = new Filter("默认排序", SortStoreTask.NONE);
	private Filter mType = new Filter("全部", StoreTypeEnum.ALL);
	private Filter mArea = new Filter("全城", SuidingApp.getApp().getFixedArea());
	/**
	 * 筛选条件列表
	 */
	private List<Filter> mltArea = new ArrayList<Filter>() {
		private static final long serialVersionUID = 1L;
		{
			add(new Filter("全城", SuidingApp.getApp().getFixedArea()));
		}
	};

	private List<Filter> mltType = new ArrayList<Filter>() {
		private static final long serialVersionUID = 1L;
		{
			add(new Filter("全部", StoreTypeEnum.ALL));
			add(new Filter("餐饮", StoreTypeEnum.FOODANDBEVERAGE));
			add(new Filter("酒店", StoreTypeEnum.HOTEL));
			add(new Filter("生活", StoreTypeEnum.LIFE));
			add(new Filter("娱乐", StoreTypeEnum.ENTERTAINMENT));
			add(new Filter("美女", StoreTypeEnum.GIRL));
		}
	};

	private List<Filter> mltSort = new ArrayList<Filter>() {
		private static final long serialVersionUID = 1L;
		{
			add(new Filter("默认排序", SortStoreTask.NONE));
			add(new Filter("价格最低", SortStoreTask.PRICE));
			// 离我最近需要 GPS 定位 在代码中判读再添加
			// add(new Filter("离我最近", SortStoreTask.DISTANCE));
			add(new Filter("订单最多", SortStoreTask.COMMENT));
			add(new Filter("最新发布", SortStoreTask.DATE));
		}
	};

	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		mIsPaging = true; // 打开分页
		mTitleOther.hide();
		mTitleMain.show();
		
		mModuleFilter.show();
		mModuleFilter.setFilter(ModuleFilter.TYPE, mltType);
		mModuleFilter.setFilter(ModuleFilter.AREA, mltArea);
		mModuleFilter.setFilter(ModuleFilter.SORT, mltSort);
		
		Date lastTime = XmlCacheUtil.getDate(CACHE_DATE,new Date());
		mListView.setLastUpdateTime(lastTime);
	}

	@Override
	protected ListViewTask getTask(int task) {
		// TODO Auto-generated method stub
		return new TwitterTask(task);
	}

	@Override
	protected ListAdapterBase getAdapter(List<Object> ltdata) {
		// TODO Auto-generated method stub
		return new TwitterListAdapter(mActivity, ltdata);
	}
	
	@Override
	protected void onQueryChanged() {
		// TODO Auto-generated method stub
		SuidingApp app = SuidingApp.getApp();
		int status  = app.getFixedCityStatus(true);
		onFixedCityChanged(app.getFixedArea(),status);
		status = app.getFixedPositionStatus(true);
		onFixedPositionChanged(app.getFixedLocation(),status);
	}

	@Override
	public void onFilter(int type, int index, Filter filter) {
		// TODO Auto-generated method stub
		switch (type) {
		case ModuleFilter.TYPE:
			mType = filter;
			onManualRefresh();
			break;
		case ModuleFilter.AREA:
			mArea = filter;
			onManualRefresh();
			break;
		case ModuleFilter.SORT:
			mSort = filter;
			XmlCacheUtil.putInt(CACHE_SORT, index);
			if (mltData != null && mltData.size() > 0) {
				mFrameSelector.SelectFrame(mLayoutProgress.mLayout);
				postTask(new TwitterTask(TwitterTask.TASK_SORT));
			}
			break;
		}
	}

	@Override
	public void onFixedCityChanged(Area area, int status) {
		// TODO Auto-generated method stub
		mTitleMain.setCityName(SuidingApp.getApp().getCityName());
		if (FixedCityEnum.FIXED == status) {
			if (area != null && area.Children != null
					&& area.Children.size() > 0) {
				mltArea.clear();
				mltArea.add(mArea = new Filter("全城", area));
				for (Area tArea : area.Children) {
					String simple = CityNameUtil.SimplifyCityName(tArea.Name);
					mltArea.add(new Filter(simple, tArea));
				}
				mModuleFilter.setFilter(ModuleFilter.AREA, mltArea);
			}
		} else if (FixedCityEnum.FAIL == status) {
			SuidingApp app = SuidingApp.getApp();
			app.InvokeFixedCityActivity(mActivity);
		}
	}

	@Override
	public void onFixedPositionChanged(Location location, int status) {
		// TODO Auto-generated method stub
		// 如果GPS定位成功天津唉离我最近排序功能
		if (mltSort.size() == 4 && FixedPositionEnum.FIXED == status) {
			mltSort.add(2,new Filter("离我最近", 4));
			mModuleFilter.setFilter(ModuleFilter.SORT, mltSort);
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> absview, View view, int index,
			long id) {
		// TODO Auto-generated method stub
		index = mListView.getIndex(index);
		Coupon coupon = (Coupon) mltData.get(index);
		if (coupon.StoreBase != null) {
			if (coupon.Product != null) {
				Intent intent = new Intent();
				intent.setClass(mActivity, DetailProductActivity.class);
				ExtraUtil.putExtra(DetailProductActivity.EXTRA_DETAIL, coupon.Product);
				ExtraUtil.putExtra(DetailProductActivity.EXTRA_OB_STORE, coupon.StoreBase);
				ExtraUtil.putExtra(DetailProductActivity.EXTRA_IT_TYPE, coupon.StoreBase.Type);
				startActivity(intent);
			}
			else{
				Intent intent = new Intent();
				intent.setClass(mActivity, DetailStoreActivity.class);
				ExtraUtil.putExtra(DetailStoreActivity.EXTRA_DETAIL, coupon.StoreBase);
				startActivity(intent);
			}
		}
	}
	
	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		if(super.handleMessage(msg) == false){
			TaskBase task = TaskBase.getTask(msg);
			switch (task.mTask) {
			case TwitterTask.TASK_SORT:
				if(task.mResult == TaskBase.RESULT_FINISH){
					// 为列表创建适配器
					mAdapter = getAdapter(mltData);
					mListView.setAdapter(mAdapter);
					mFrameSelector.SelectFrame(mListView);
				}
				break;
			}
		}
		return true;
	}

	private class TwitterTask extends ListViewTask implements Comparator<Object>{

	    //枚举任务类型
	    public static final int TASK_SORT = 10000;    //下拉刷新
		
		public TwitterTask(int task) {
			super(new Handler(IndexTwitterFragment.this), task);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected List<? extends Object> onLoad() {
			// TODO Auto-generated method stub
			List<Coupon> ltData = new ArrayList<Coupon>();
			Date last = XmlCacheUtil.getDate(CACHE_DATE, 0);
			SuidingApp app = SuidingApp.getApp();
			if (app.getFixedCityStatus(true) == FixedCityEnum.FIXED) {
				//如果上次更新时间到现在 大于规定时间
				if(TimeSpan.FromLast(last).Compare(mLoadSpan) > 0 && last.getTime() > 0){
					// 获取数据接口
					Address address = Address.fromArea((Area)mArea.getValue());
					ICouponDomain domain = DomainFactory.getCouponDomain();
					try {
						ltData = domain.getListByAddressAfter(address, last);
						if (ltData.size() > 0) {
							CouponEntityDao dao = new CouponEntityDao(mActivity);
							dao.appendCache(CouponEntity.listFormModel(ltData));
							XmlCacheUtil.putDate(CACHE_DATE, new Date());
						}
					} catch (LeSouException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();//handled
						AppExceptionHandler.getHandler(e, "商圈列表，加载活动更新出现异常");
					}
				}
			}
			try {
				CouponEntityDao dao = new CouponEntityDao(mActivity);
				ltData.addAll(CouponEntity.listToModel(dao.getAll()));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.getHandler(e, "商圈列表，加载本地缓存出现异常");
			}				
			try {
				Collections.sort(ltData, this);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.handler(e, "商圈页面，活动排序 出现异常");
			}
			return ltData;
		}

		@Override
		protected List<? extends Object> onRefresh() throws Exception {
			// TODO Auto-generated method stub
			SuidingApp app = SuidingApp.getApp();

			if (app.getFixedCityStatus(true) == FixedCityEnum.FIXED) {
				Page page = new Page(PAGESIZE, 0);
				int type = mType.getInt();
				Address address = Address.fromArea((Area)mArea.getValue());
				// 获取数据接口
				ICouponDomain domain = DomainFactory.getCouponDomain();
				List<Coupon> ltData = domain.getListByAddressStoreType(address, type, page);
				//.getListByAddress(address, page);
				try {
					if (ltData.size() > 0) {
						CouponEntityDao dao = new CouponEntityDao(mActivity);
						dao.updateCache(CouponEntity.listFormModel(ltData));
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();//handled
					AppExceptionHandler.handler(e, "商圈页面，更新缓存 出现异常");
				}
				try {
					Collections.sort(ltData, this);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();//handled
					AppExceptionHandler.handler(e, "商圈页面，活动排序 出现异常");
				}
				XmlCacheUtil.putDate(CACHE_DATE, new Date());
				return ltData;
			} else {
				throw new Exception(
						mActivity.getString(ModuleNodata.TEXT_NOFIXED));
			}
		}

		@Override
		protected List<? extends Object> onMore(Page page) throws Exception {
			// TODO Auto-generated method stub

			SuidingApp app = SuidingApp.getApp();
			if (app.getFixedCityStatus(true) == FixedCityEnum.FIXED) {
				// 获取数据接口
				//ICouponDomain domain = DomainFactory.getCouponDomain();
				//List<Coupon> ltData = domain.getListByAddress(address, page);
				int type = mType.getInt();
				Address address = Address.fromArea((Area)mArea.getValue());
				// 获取数据接口
				ICouponDomain domain = DomainFactory.getCouponDomain();
				List<Coupon> ltData = domain.getListByAddressStoreType(address, type, page);
				try {
					Collections.sort(ltData, this);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();//handled
					AppExceptionHandler.handler(e, "商圈页面，活动排序 出现异常");
				}
				try {
					if (ltData.size() > 0) {
						CouponEntityDao dao = new CouponEntityDao(mActivity);
						dao.appendCache(CouponEntity.listFormModel(ltData));
					}
				} catch (Exception e) {
					// TODO: handle exception//handled
					AppExceptionHandler.handler(e, "商圈页面，追加缓存 出现异常");
				}
				return ltData;
			} else {
				throw new Exception(
						mActivity.getString(ModuleNodata.TEXT_NOFIXED));
			}
		}

		@Override
		protected boolean onWorking() throws Exception {
			// TODO Auto-generated method stub
			switch (mTask) {
			case TASK_SORT:
				try {
					Collections.sort(IndexTwitterFragment.this.mltData, new TwitterTask(mSort.getInt()));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();//handled
					AppExceptionHandler.handler(e, "商圈页面，活动排序 出现异常");
				}
				return true;
			default:
				return super.onWorking();
			}
		}

		@Override
		public int compare(Object lhs, Object rhs) {
			// TODO Auto-generated method stub
			if(lhs instanceof Coupon && rhs instanceof Coupon){
				Coupon lcp = (Coupon)lhs;
				Coupon rcp = (Coupon)rhs;
				boolean inverted = false;//是否倒序（倒序是指大的在前面）
				long lvalue = 0,rvalue = 0;
				switch (mSort.getInt()) {
				case SortStoreTask.COMMENT:{
					inverted = true;
					lvalue = lcp.OrderNumber;
					rvalue = rcp.OrderNumber;
				}
					break;
				case SortStoreTask.PRICE:{
					Product lproduct = lcp.Product;
					Product rproduct = rcp.Product;
					if(lproduct != null && rproduct != null){
						lvalue = (long)lproduct.NowPrice;
						rvalue = (long)rproduct.NowPrice;
					}
				}
					break;
				case SortStoreTask.DISTANCE:{
					StoreBase lstore = lcp.StoreBase;
					StoreBase rstore = rcp.StoreBase;
					Location location = SuidingApp.getApp().getFixedLocation();
					if(lstore != null && rstore != null && location != null){
						lvalue = DistanceUtil.getDistance(location,lstore.Address);
						rvalue = DistanceUtil.getDistance(location,rstore.Address);
					}
				}
					break;
				case SortStoreTask.DATE:{
					Date ldate = lcp.Date;
					Date rdate = rcp.Date;
					if(ldate != null && rdate != null){
						inverted = true;
						lvalue = ldate.getTime();
						rvalue = rdate.getTime();
					}
				}
					break;
				}
				if(lvalue != rvalue){
					if(inverted){//正序
						return rvalue > lvalue ? 1 : -1;
					}else{//倒序
						return rvalue > lvalue ? -1 : 1;
					}
				}
			}
			return 0;
		}
	}

	private boolean onManualRefresh(boolean progress) {
		// TODO Auto-generated method stub
		if (mltArea.size() <= 1) {
			SuidingApp app = SuidingApp.getApp();
			int status = app.getFixedCityStatus(true);
			if (FixedCityEnum.FIXED == status) {
				onFixedCityChanged(app.getFixedArea(), status);
			} else if (FixedCityEnum.FIXEDING == status) {
				showToastShort("定位失败，稍后再试。");
				return false;
			} else if (FixedCityEnum.FAIL == status) {
				showToastShort("定位失败，稍后再试。");
				return false;
			}
		}
		postTask(new TwitterTask(TwitterTask.TASK_REFRESH));
		if (progress)
			mFrameSelector.SelectFrame(mLayoutProgress.mLayout);
		return true;
	}

	private boolean onManualRefresh() {
		// TODO Auto-generated method stub
		return onManualRefresh(true);
	}
}
