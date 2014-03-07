package com.suiding.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.suiding.activity.DetailStoreActivity;
import com.suiding.activity.R;
import com.suiding.adapter.StoreBaseListAdapter;
import com.suiding.application.SuidingApp;
import com.suiding.application.SuidingApp.INotifyFixedCity;
import com.suiding.application.SuidingApp.INotifyFixedPosition;
import com.suiding.bean.Filter;
import com.suiding.constant.FixedPositionEnum;
import com.suiding.constant.StoreTypeEnum;
import com.suiding.dao.NearbyEntityDao;
import com.suiding.domain.IStoreBaseDomain;
import com.suiding.entity.NearbyEntity;
import com.suiding.fragment.framework.FragmentBase;
import com.suiding.layoutbind.ModuleFilter;
import com.suiding.layoutbind.ModuleFilter.IFilter;
import com.suiding.layoutbind.ModuleListView;
import com.suiding.layoutbind.ModuleNodata;
import com.suiding.layoutbind.ModuleTitleMain;
import com.suiding.layoutbind.ModuleTitleSearch;
import com.suiding.model.Area;
import com.suiding.model.Page;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.thread.SortStoreTask;
import com.suiding.thread.framework.ListTask;
import com.suiding.thread.framework.ListViewTask;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.DistanceUtil;
import com.suiding.util.DistanceUtil.LocationRadius;
import com.suiding.util.ExtraUtil;
import com.suiding.util.XmlCacheUtil;
import com.suiding.widget.framework.PullToRefreshBase.OnRefreshListener;

/**
 * 身边页面Fragment
 * 
 * @author SCWANG
 */
public class IndexNearbyFragment extends FragmentBase implements
		OnClickListener, INotifyFixedPosition, Callback, OnItemClickListener,
		INotifyFixedCity, OnRefreshListener, IFilter {

	private static final String CACHE_TYPE = "Nearby_CACHE_TYPE";
	private static final String CACHE_AREA = "Nearby_CACHE_AREA";
	private static final String CACHE_SORT = "Nearby_CACHE_SORT";
	private static final String CACHE_DATE = "Nearby_CACHE_DATE";

	private ModuleFilter mModuleFilter = null;
	private ModuleTitleMain mLayoutTitle = null;
	private ModuleTitleSearch mLayoutSearch = null;
	private ModuleListView mModuleListView = null;
	/**
	 * 选择器
	 */
	private Filter mType = new Filter("全部", StoreTypeEnum.ALL);
	private Filter mSort = new Filter("离我最近", SortStoreTask.DISTANCE);
	private Filter mDistance = new Filter("１千米", 1000);

	// 正在加载数据标识
	private Boolean mIsLoading = false;

	// 定位的经纬度
	private Location mLocation = null;

	// 身边的数据
	private List<StoreBase> mltNearby = null;
	// 数据适配器
	private StoreBaseListAdapter mAdapter = null;
	/**
	 * 筛选条件列表
	 */
	private List<Filter> mltDistance = new ArrayList<Filter>() {
		private static final long serialVersionUID = 1L;
		{
			add(new Filter("１千米", 1000));
			add(new Filter("３千米", 3000));
			add(new Filter("５千米", 5000));
			add(new Filter("10千米", 10000));
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
			add(new Filter("离我最近", SortStoreTask.DISTANCE));
			add(new Filter("评价最高", SortStoreTask.COMMENT));
			add(new Filter("最新发布", SortStoreTask.DATE));
			add(new Filter("价格最低", SortStoreTask.PRICE));
		}
	};

	@Override
	protected final View onCreateView(LayoutInflater inflater,
			ViewGroup container) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.index_nearby, container, false);
	}

	@Override
	protected final void onCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		mLayoutTitle = new ModuleTitleMain(this);
		
		mLayoutSearch = new ModuleTitleSearch(this);
		mLayoutSearch.setGoBackEnable(false);

		mModuleFilter = new ModuleFilter(this, this);
		mModuleFilter.setFilter(ModuleFilter.TYPE, mltType);
		mModuleFilter.setFilter(ModuleFilter.AREA, mltDistance);
		mModuleFilter.setFilter(ModuleFilter.SORT, mltSort);

		mModuleListView = new ModuleListView(this);
		mModuleListView.setOnRefreshListener(this);
		mModuleListView.setOnItemClickListener(this);
		mModuleListView.setOnNodataRefreshListener(this);
		mModuleListView.SelectFrame(ModuleListView.PRIGRESS);

		int type = XmlCacheUtil.getInt(CACHE_TYPE, 0);
		if (type < mltType.size()) {
			mType = mltType.get(type);
			mModuleFilter.selectFilter(ModuleFilter.TYPE, mType);
		}

		int sort = XmlCacheUtil.getInt(CACHE_SORT, 0);
		if (sort < mltSort.size()) {
			mSort = mltSort.get(sort);
			mModuleFilter.selectFilter(ModuleFilter.SORT, mSort);
		}

		int dis = XmlCacheUtil.getInt(CACHE_AREA, 2);
		if (dis < mltDistance.size()) {
			mDistance = mltDistance.get(dis);
			mModuleFilter.selectFilter(ModuleFilter.AREA, mDistance);
		}

		// 显示正在加载数据
		if (mltNearby == null) {
			mModuleListView.SelectFrame(ModuleListView.PRIGRESS);
		} else if (mltNearby.size() == 0) {
			if (mIsLoading == true) {
				mModuleListView.SelectFrame(ModuleListView.PRIGRESS);
			} else {
				mModuleListView.SelectFrame(ModuleListView.NULLDATA);
				if (mLocation == null) {
					mModuleListView.setNoDataText(ModuleNodata.TEXT_NOFIXED);
				} else {
					mModuleListView.setNoDataText(ModuleNodata.TEXT_NODATA);
				}
			}
		} else {
			mModuleListView.setAdapter(mAdapter);
			mModuleListView.SelectFrame(ModuleListView.LISTVIEW);
		}

	}

	/**
	 * 第一次切换到本页面
	 */
	@Override
	protected final void onFirstSwitchOver() {
		// TODO Auto-generated method stub
		super.onFirstSwitchOver();

		// postTask(new NearbyTask(NearbyTask.TASK_REFRESH));

		SuidingApp tApp = SuidingApp.getApp();
		switch (tApp.getFixedPositionStatus(true)) {
		case FixedPositionEnum.FIXED:
			mLocation = tApp.getFixedLocation();
			postTask(new NearbyTask(NearbyTask.TASK_LOAD));
			break;
		case FixedPositionEnum.FIXEDING:
			mModuleListView.SelectFrame(ModuleListView.PRIGRESS);
			mModuleListView.setNoDataText(ModuleNodata.TEXT_NOFIXED);
			break;
		default:
		case FixedPositionEnum.FAIL:
			Toast.makeText(mActivity, R.string.fixed_failed_nodata,
					Toast.LENGTH_LONG).show();
			mModuleListView.setNoDataText(ModuleNodata.TEXT_NOFIXED);
			break;
		}
	}

	/**
	 * 每次切换到本页面
	 * 
	 * @param count
	 *            切换序号
	 */
	@Override
	protected final void onSwitchOver(int count) {
		// 确保不是第一次切换
		if (count > 0 && mLocation == null) {
			SuidingApp tApp = SuidingApp.getApp();
			if (tApp.getFixedPositionStatus(true) == FixedPositionEnum.FIXED) {
				mLocation = tApp.getFixedLocation();
				postTask(new NearbyTask(NearbyTask.TASK_LOAD));
				mModuleListView.SelectFrame(ModuleListView.PRIGRESS);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> absview, View view, int index,
			long id) {
		// TODO Auto-generated method stub
		if (absview == mModuleListView.getRefreshableView()) {
			index = mModuleListView.getIndex(index);
			if (index >= 0) {
				StoreBase store = mltNearby.get(index);
				Intent intent = new Intent();
				intent.setClass(mActivity, DetailStoreActivity.class);
				ExtraUtil.putExtra(DetailStoreActivity.EXTRA_DETAIL, store);
				startActivity(intent);
			}
		}
	}

	@Override
	public void onFixedPositionChanged(Location location, int status) {
		// TODO Auto-generated method stub
		if (status == FixedPositionEnum.FIXED) {
			if (mLocation == null) {
				mLocation = location;
				postTask(new NearbyTask(NearbyTask.TASK_REFRESH));
				mModuleListView.SelectFrame(ModuleListView.PRIGRESS);
			}
			mLocation = location;
		} else {
			showToastShort(R.string.fixed_failed_nodata);
			mModuleListView.setNoDataText(ModuleNodata.TEXT_NOFIXED);
			mModuleListView.SelectFrame(ModuleListView.NULLDATA);
		}
	}

	@Override
	public void onFixedCityChanged(Area area, int status) {
		// TODO Auto-generated method stub
		mLayoutTitle.setCityName(SuidingApp.getApp().getCityName());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case ModuleNodata.ID_BUTTON:
		case ModuleNodata.TEXT_TOREFRESH:
			// postTask(new NearbyTask(NearbyTask.TASK_REFRESH));
			if (mLocation == null) {
				int status = SuidingApp.getApp().getFixedPositionStatus(true);
				if (status == FixedPositionEnum.FIXED) {
					mLocation = SuidingApp.getApp().getFixedLocation();
					postTask(new NearbyTask(NearbyTask.TASK_REFRESH));
					mModuleListView.SelectFrame(ModuleListView.PRIGRESS);
				} else {
					showToastShort(ModuleNodata.TEXT_NOFIXED);
					mModuleListView.setNoDataText(ModuleNodata.TEXT_NOFIXED);
				}
			} else {
				postTask(new NearbyTask(NearbyTask.TASK_REFRESH));
				mModuleListView.SelectFrame(ModuleListView.PRIGRESS);
			}
			break;
		}
	}

	@Override
	public void onFilter(int type,int index, Filter filter) {
		// TODO Auto-generated method stub
		switch (type) {
		case ModuleFilter.TYPE:
			mType = filter;
			XmlCacheUtil.putInt(CACHE_TYPE, index);
			mModuleListView.SelectFrame(ModuleListView.PRIGRESS);
			postTask(new NearbyTask(NearbyTask.TASK_REFRESH));
			break;
		case ModuleFilter.AREA:
			mDistance = filter;
			XmlCacheUtil.putInt(CACHE_AREA, index);
			mModuleListView.SelectFrame(ModuleListView.PRIGRESS);
			postTask(new NearbyTask(NearbyTask.TASK_REFRESH));
			break;
		case ModuleFilter.SORT:
			mSort = filter;
			XmlCacheUtil.putInt(CACHE_SORT, index);
			if (mltNearby != null && mltNearby.size() > 0) {
				mModuleListView.SelectFrame(ModuleListView.PRIGRESS);
				postTask(new NearbyTask(NearbyTask.TASK_SORT));
			}
			break;
		}
	}

	@Override
	public boolean onMore() {
		// TODO Auto-generated method stub
		postTask(new NearbyTask(NearbyTask.TASK_MORE));
		return true;
	}

	@Override
	public boolean onRefresh() {
		// TODO Auto-generated method stub
		postTask(new NearbyTask(NearbyTask.TASK_REFRESH));
		return true;
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		NearbyTask tTask = (NearbyTask) msg.obj;
		if (tTask.mResult == TaskBase.RESULT_FINISH) {

			if (tTask.mTask == NearbyTask.TASK_LOAD) {
				// 将加载到的数据 tTask.mltData 添加到 主数据 中
				mltNearby = tTask.mltData;
				// 为列表创建适配器
				mAdapter = new StoreBaseListAdapter(mActivity, mltNearby);
				mModuleListView.setAdapter(mAdapter);
				if (mltNearby.size() == 0) {
					// 首次加载数据失败从服务器上刷新数据
					tTask.mTask = ListTask.TASK_REFRESH;
					postTask(tTask);
				} else {
					// 切换到列表页面
					mModuleListView.SelectFrame(ModuleListView.LISTVIEW);
				}
			} else if (tTask.mTask == NearbyTask.TASK_REFRESH) {
				if (mModuleListView.isRefreshing()) {
					// 通知列表刷新完成
					mModuleListView.finishRefresh();
				} else {
					// 切换到列表页面
					mModuleListView.SelectFrame(ModuleListView.LISTVIEW);
				}
				// 将加载到的数据 tTask.mltData 添加到 主数据 中
				mltNearby = tTask.mltData;
				// 为列表创建适配器
				mAdapter = new StoreBaseListAdapter(mActivity, mltNearby);
				mModuleListView.setAdapter(mAdapter);
				// 保存最后一次更新时间
				XmlCacheUtil.putDate(CACHE_DATE, new Date());
				if (tTask.mltData.size() > 0) {
					// 如果开启分页
					if (tTask.mltData.size() >= ListViewTask.PAGESIZE) {
						mModuleListView.addFooterView();
					}
				} else if (mltNearby == null || mltNearby.size() == 0) {
					mModuleListView.setNoDataText(ModuleNodata.TEXT_NODATA);
					mModuleListView.SelectFrame(ModuleListView.NULLDATA);
				}
			} else if (tTask.mTask == NearbyTask.TASK_MORE) {
				if (tTask.mltData.size() > 0) {
					// 通知列表刷新完成
					mModuleListView.finishLoadMore();
					// 更新列表
					mltNearby.addAll(tTask.mltData);
					mAdapter.AddData(tTask.mltData);
					// 对数据进行新的排序
					postTask(new NearbyTask(NearbyTask.TASK_SORT));
				}
				if (tTask.mltData.size() < ListViewTask.PAGESIZE) {
					// 关闭更多选项
					mModuleListView.removeFooterView();
					Toast.makeText(mActivity, "数据全部加载完毕！", Toast.LENGTH_SHORT)
							.show();
				}
			} else if (tTask.mTask == NearbyTask.TASK_SORT) {
				// 清空之前加载的数据 并把新数据添加到列表中
				mAdapter.setData(mltNearby);
			}
		} else if (tTask.mResult == TaskBase.RESULT_FAIL) {
			if (mModuleListView.isRefreshing()) {
				// 通知列表刷新完成
				mModuleListView.finishRefreshFail();
			} else if (mltNearby == null || mltNearby.size() == 0) {
				mModuleListView.SelectFrame(ModuleListView.NULLDATA);
			}else{
				mModuleListView.SelectFrame(ModuleListView.LISTVIEW);
			}
			//Toast.makeText(mActivity, tTask.mErrors, Toast.LENGTH_LONG).show();
		}
		return true;
	}

	private class NearbyTask extends ListTask {

		public List<StoreBase> mltData = new ArrayList<StoreBase>();

		public NearbyTask(int task) {
			super(new Handler(IndexNearbyFragment.this), task);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onWorking(Message msg) throws Exception {
			// TODO Auto-generated method stub
			switch (mTask) {
			// 根据城市获取区域信息
			case TASK_LOAD:
				NearbyEntityDao dao = new NearbyEntityDao(mActivity);
				mltData = NearbyEntity.listToModel(dao.getAll());
				break;
			case TASK_REFRESH: {
				int r = mDistance.getInt();
				int type = mType.getInt();
				Page page = new Page(PAGESIZE, 0);
				Location fixed = mLocation;
				LocationRadius radius = DistanceUtil.tranformRadius(fixed, r);
				double rx = radius.Longitude;
				double ry = radius.Latitude;
				double x = mLocation.getLongitude();
				double y = mLocation.getLatitude();

				IStoreBaseDomain domain = DomainFactory.getStoreBaseDomain();

				mltData = domain.getNearby(type, x, y, rx, ry, page);
				// mltData = domain.getNearby(type, 0, 0, 180, 90, page);

				Collections.sort(mltData, new SortStoreTask(mSort.getInt()));
				break;
			}
			case TASK_MORE: {
				int r = mDistance.getInt();
				int type = mType.getInt();
				Page page = new Page(PAGESIZE, mltNearby.size());
				Location fixed = mLocation;
				LocationRadius radius = DistanceUtil.tranformRadius(fixed, r);
				double rx = radius.Longitude;
				double ry = radius.Latitude;
				double x = mLocation.getLongitude();
				double y = mLocation.getLatitude();

				IStoreBaseDomain domain = DomainFactory.getStoreBaseDomain();

				mltData = domain.getNearby(type, x, y, rx, ry, page);
				// mltData = domain.getNearby(type, 0, 0, 180, 90, page);
				break;
			}
			case TASK_SORT:
				Collections.sort(mltNearby, new SortStoreTask(mSort.getInt()));
				break;
			}
		}
	}
}
