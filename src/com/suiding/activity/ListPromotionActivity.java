package com.suiding.activity;

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

import com.suiding.activity.framework.ListViewActivity;
import com.suiding.adapter.TwitterListAdapter;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.application.AppExceptionHandler;
import com.suiding.application.SuidingApp;
import com.suiding.application.SuidingApp.INotifyFixedCity;
import com.suiding.application.SuidingApp.INotifyFixedPosition;
import com.suiding.bean.Filter;
import com.suiding.dao.CouponEntityDao;
import com.suiding.domain.ICouponDomain;
import com.suiding.entity.CouponEntity;
import com.suiding.layoutbind.ModuleListView;
import com.suiding.layoutbind.ModuleNodata;
import com.suiding.model.Area;
import com.suiding.model.Consumer;
import com.suiding.model.Coupon;
import com.suiding.model.Page;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.thread.SortStoreTask;
import com.suiding.thread.framework.ListViewTask;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.DistanceUtil;
import com.suiding.util.ExtraUtil;
import com.suiding.util.LeSouException;
import com.suiding.util.TimeSpan;
import com.suiding.util.XmlCacheUtil;

public class ListPromotionActivity extends ListViewActivity implements
		INotifyFixedCity,INotifyFixedPosition {

	//private static final String CACHE_TYPE = "BusyCoupon_CACHE_TYPE";
	private static final String CACHE_DATE = "BusyCoupon_CACHE_DATE";
	//private static final String CACHE_SORT = "BusyCoupon_CACHE_SORT";
	
	//����ʱ����
	private static final TimeSpan mLoadSpan = TimeSpan.FromMinutes(30);
	/**
	 * ѡ����
	 */
	//private Filter mSort = new Filter("Ĭ������", SortStoreTask.NONE);
	//private Filter mType = new Filter("ȫ��", StoreTypeEnum.ALL);
	//private Filter mArea = new Filter("ȫ��", SuidingApp.getApp().getFixedArea());
	/**
	 * ɸѡ�����б�
	 */
//	private List<Filter> mltArea = new ArrayList<Filter>() {
//		private static final long serialVersionUID = 1L;
//		{
//			add(new Filter("ȫ��", SuidingApp.getApp().getFixedArea()));
//		}
//	};

//	private List<Filter> mltType = new ArrayList<Filter>() {
//		private static final long serialVersionUID = 1L;
//		{
//			add(new Filter("ȫ��", StoreTypeEnum.ALL));
//			add(new Filter("����", StoreTypeEnum.FOODANDBEVERAGE));
//			add(new Filter("�Ƶ�", StoreTypeEnum.HOTEL));
//			add(new Filter("����", StoreTypeEnum.LIFE));
//			add(new Filter("����", StoreTypeEnum.ENTERTAINMENT));
//			add(new Filter("��Ů", StoreTypeEnum.GIRL));
//		}
//	};

//	private List<Filter> mltSort = new ArrayList<Filter>() {
//		private static final long serialVersionUID = 1L;
//		{
//			add(new Filter("Ĭ������", SortStoreTask.NONE));
//			add(new Filter("�۸����", SortStoreTask.PRICE));
//			// ���������Ҫ GPS ��λ �ڴ������ж������
//			// add(new Filter("�������", SortStoreTask.DISTANCE));
//			add(new Filter("�������", SortStoreTask.COMMENT));
//			add(new Filter("���·���", SortStoreTask.DATE));
//		}
//	};

	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		mIsPaging = true; // �򿪷�ҳ
		
		mModuleFilter.hide();
//		mModuleFilter.setFilter(ModuleFilter.TYPE, mltType);
//		mModuleFilter.setFilter(ModuleFilter.AREA, mltArea);
//		mModuleFilter.setFilter(ModuleFilter.SORT, mltSort);
		mTitleOther.setTitle(R.string.title_activity_promotion);
		
		Date lastTime = XmlCacheUtil.getDate(CACHE_DATE,new Date());
		mModuleListView.setLastUpdateTime(lastTime);
		
	}
	
	@Override
	protected void onNodata(ModuleNodata nodata) {
		// TODO Auto-generated method stub		// ���ÿ�����ҳ��
		nodata.setDescription(ModuleNodata.TEXT_NOPROMOTION);
	}

	@Override
	protected ListViewTask getTask(int task) {
		// TODO Auto-generated method stub
		return new BusyCouponTask(task);
	}

	@Override
	protected ListAdapterBase getAdapter(List<Object> ltdata) {
		// TODO Auto-generated method stub
		return new TwitterListAdapter(getActivity(), ltdata);
	}

	@Override
	public void onFilter(int type, int index, Filter filter) {
		// TODO Auto-generated method stub
//		switch (type) {
//		case ModuleFilter.TYPE:
//			mType = filter;
//			onManualRefresh();
//			break;
//		case ModuleFilter.AREA:
//			mArea = filter;
//			onManualRefresh();
//			break;
//		case ModuleFilter.SORT:
//			mSort = filter;
//			XmlCacheUtil.putInt(CACHE_SORT, index);
//			if (mltData != null && mltData.size() > 0) {
//				mModuleListView.SelectFrame(ModuleListView.PRIGRESS);
//				postTask(new BusyCouponTask(BusyCouponTask.TASK_SORT));
//			}
//			break;
//		}
	}

	@Override
	public void onFixedCityChanged(Area area, int status) {
		// TODO Auto-generated method stub
		mTitleMain.setCityName(SuidingApp.getApp().getCityName());
//		if (FixedCityEnum.FIXED == status) {
//			if (area != null && area.Children != null
//					&& area.Children.size() > 0) {
//				mltArea.clear();
//				mltArea.add(mArea = new Filter("ȫ��", area));
//				for (Area tArea : area.Children) {
//					String simple = CityNameUtil.SimplifyCityName(tArea.Name);
//					mltArea.add(new Filter(simple, tArea));
//				}
//				mModuleFilter.setFilter(ModuleFilter.AREA, mltArea);
//			}
//		} else if (FixedCityEnum.FAIL == status) {
//			SuidingApp app = SuidingApp.getApp();
//			app.InvokeFixedCityActivity(getActivity());
//		}
	}

	@Override
	public void onFixedPositionChanged(Location location, int status) {
		// TODO Auto-generated method stub
		// ���GPS��λ�ɹ�����������������
//		if (mltSort.size() == 4 && FixedPositionEnum.FIXED == status) {
//			mltSort.add(2,new Filter("�������", 4));
//			mModuleFilter.setFilter(ModuleFilter.SORT, mltSort);
//		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> absview, View view, int index,
			long id) {
		// TODO Auto-generated method stub
		index = mModuleListView.getIndex(index);
		Coupon coupon = (Coupon) mltData.get(index);
		if (coupon.StoreBase != null) {
			if (coupon.Product != null) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), DetailProductActivity.class);
				ExtraUtil.putExtra(DetailProductActivity.EXTRA_DETAIL, coupon.Product);
				ExtraUtil.putExtra(DetailProductActivity.EXTRA_OB_STORE, coupon.StoreBase);
				ExtraUtil.putExtra(DetailProductActivity.EXTRA_IT_TYPE, coupon.StoreBase.Type);
				startActivity(intent);
			}
			else{
				Intent intent = new Intent();
				intent.setClass(getActivity(), DetailStoreActivity.class);
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
			case BusyCouponTask.TASK_SORT:
				if(task.mResult == TaskBase.RESULT_FINISH){
					// Ϊ�б���������
					mAdapter = getAdapter(mltData);
					mModuleListView.setAdapter(mAdapter);
					mModuleListView.SelectFrame(ModuleListView.LISTVIEW);
				}
				break;
			}
		}
		return true;
	}

	private class BusyCouponTask extends ListViewTask implements Comparator<Object>{

	    //ö����������
	    public static final int TASK_SORT = 10000;    //����ˢ��
		
		public BusyCouponTask(int task) {
			super(new Handler(ListPromotionActivity.this), task);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected List<? extends Object> onLoad() {
			// TODO Auto-generated method stub
			List<Coupon> ltData = new ArrayList<Coupon>();
			Date last = XmlCacheUtil.getDate(CACHE_DATE, 0);
			Consumer user = SuidingApp.getLoginUser();
			if (user != null) {
				//����ϴθ���ʱ�䵽���� ���ڹ涨ʱ��
				if(TimeSpan.FromLast(last).Compare(mLoadSpan) > 0){
					// ��ȡ���ݽӿ�
					ICouponDomain domain = DomainFactory.getCouponDomain();
					try {
						ltData = domain.getPromotionByUserIDAfter(user.getID(), last);
						if (ltData.size() > 0) {
							CouponEntityDao dao = new CouponEntityDao(getActivity());
							dao.appendCache(CouponEntity.listFormModel(ltData));
							XmlCacheUtil.putDate(CACHE_DATE, new Date());
						}
					} catch (LeSouException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();//handled
						AppExceptionHandler.getHandler(e, "�̼һ�����ػ���³����쳣");
					}
				}
			}
//			try {
//				CouponEntityDao dao = new CouponEntityDao(getContext());
//				ltData.addAll(CouponEntity.listToModel(dao.getAll()));
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();//handled
//				AppExceptionHandler.getHandler(e, "�̼һ�����ر��ػ�������쳣");
//			}
			return ltData;
		}

		@Override
		protected List<? extends Object> onRefresh() throws Exception {
			// TODO Auto-generated method stub
			Consumer user = SuidingApp.getLoginUser();
			if (user != null) {
				Page page = new Page(PAGESIZE, 0);
				// ��ȡ���ݽӿ�
				ICouponDomain domain = DomainFactory.getCouponDomain();
				List<Coupon> ltData = domain.getPromotionByUserID(user.getID(), page);
				//.getListByAddress(address, page);
//				try {
//					if (ltData.size() > 0) {
//						CouponEntityDao dao = new CouponEntityDao(getContext());
//						dao.updateCache(CouponEntity.listFormModel(ltData));
//					}
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();//handled
//					AppExceptionHandler.handler(e, "�̼һ�����»��� �����쳣");
//				}
				XmlCacheUtil.putDate(CACHE_DATE, new Date());
				return ltData;
			} else {
				throw new Exception(
						getActivity().getString(ModuleNodata.TEXT_NOFIXED));
			}
		}

		@Override
		protected List<? extends Object> onMore(Page page) throws Exception {
			// TODO Auto-generated method stub

			Consumer user = SuidingApp.getLoginUser();
			if (user != null) {
				// ��ȡ���ݽӿ�
				//ICouponDomain domain = DomainFactory.getCouponDomain();
				//List<Coupon> ltData = domain.getListByAddress(address, page);
				// ��ȡ���ݽӿ�
				ICouponDomain domain = DomainFactory.getCouponDomain();
				List<Coupon> ltData = domain.getPromotionByUserID(user.getID(), page);
//				try {
//					if (ltData.size() > 0) {
//						CouponEntityDao dao = new CouponEntityDao(getContext());
//						dao.appendCache(CouponEntity.listFormModel(ltData));
//					}
//				} catch (Exception e) {
//					// TODO: handle exception//handled
//					AppExceptionHandler.handler(e, "�̼һ��׷�ӻ��� �����쳣");
//				}
				return ltData;
			} else {
				throw new Exception(
						getActivity().getString(ModuleNodata.TEXT_NOFIXED));
			}
		}

		@Override
		protected boolean onWorking() throws Exception {
			// TODO Auto-generated method stub
			switch (mTask) {
			case TASK_SORT:
				try {
					Collections.sort(ListPromotionActivity.this.mltData, this);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();//handled
					AppExceptionHandler.handler(e, "�̼һ������� �����쳣");
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
				boolean inverted = false;//�Ƿ��򣨵�����ָ�����ǰ�棩
				long lvalue = 0,rvalue = 0;
				switch (SortStoreTask.DATE) {
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
					if(inverted){//����
						return rvalue > lvalue ? 1 : -1;
					}else{//����
						return rvalue > lvalue ? -1 : 1;
					}
				}
			}
			return 0;
		}
	}

//	private boolean onManualRefresh(boolean progress) {
//		// TODO Auto-generated method stub
//		postTask(new BusyCouponTask(BusyCouponTask.TASK_REFRESH));
//		if (progress)
//			mModuleListView.SelectFrame(ModuleListView.PRIGRESS);
//		return true;
//	}
//
//	private boolean onManualRefresh() {
//		// TODO Auto-generated method stub
//		return onManualRefresh(true);
//	}
}
