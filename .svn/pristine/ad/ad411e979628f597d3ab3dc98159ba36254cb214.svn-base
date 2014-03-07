package com.suiding.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.adapter.StoreBaseListAdapter;
import com.suiding.application.AppExceptionHandler;
import com.suiding.application.SuidingApp;
import com.suiding.application.SuidingApp.INotifyFixedCity;
import com.suiding.application.SuidingApp.INotifyFixedPosition;
import com.suiding.bean.Filter;
import com.suiding.constant.FixedCityEnum;
import com.suiding.constant.FixedPositionEnum;
import com.suiding.constant.StoreTypeEnum;
import com.suiding.dao.AreaEntityDao;
import com.suiding.domain.IAreaDomain;
import com.suiding.domain.IStoreBaseDomain;
import com.suiding.entity.AreaEntity;
import com.suiding.layoutbind.ModuleFilter;
import com.suiding.layoutbind.ModuleFilter.IFilter;
import com.suiding.layoutbind.ModuleListView;
import com.suiding.layoutbind.ModuleNodata;
import com.suiding.model.Address;
import com.suiding.model.Area;
import com.suiding.model.Page;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.thread.SortStoreTask;
import com.suiding.thread.framework.ListTask;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.ExtraUtil;
import com.suiding.util.XmlCacheUtil;
import com.suiding.widget.framework.PullToRefreshBase.OnRefreshListener;

public class SearchActivity extends ActivityBase implements
		OnItemClickListener, OnClickListener, INotifyFixedCity,
		INotifyFixedPosition, Callback, OnEditorActionListener, IFilter,
		OnRefreshListener {
	public static final String EXTRA_KEYWORD = "EXTRA_KEYWORD";
	public static final String EXTRA_IT_TYPE = "EXTRA_IT_TYPE";//大行业分类
	public static final String EXTRA_ST_TYPE = "EXTRA_ST_TYPE";//小行业分类
	
	private static final String CACHE_DATE = "SearchActivity_CACHE_DATE";

	//小行业类型
	private String mViceType = null;
	
	private View mBtGoBack = null;
	private View mTvSubmit = null;
	private EditText mEtKeyWord = null;
	private TextView mTvCityName = null;

	private ModuleFilter mModuleFilter = null;
	private ModuleListView mModuleListView = null;
	//private ModuleTitleSearch mModuleSearch = null;

	private List<StoreBase> mltStore = new ArrayList<StoreBase>();
	private StoreBaseListAdapter mAdapter = null;
	/**
	 * 选择器
	 */
	private Filter mSort = new Filter("默认排序", 0);
	private Filter mType = new Filter("全部", StoreTypeEnum.ALL);
	private Filter mArea = new Filter("全城", SuidingApp.getApp().getFixedArea());
	/**
	 * 筛选条件列表
	 */
	private List<Filter> mltArea = new ArrayList<Filter>() {
		private static final long serialVersionUID = 1L;
		{
			add(new Filter("本地", SuidingApp.getApp().getFixedArea()));
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
			add(new Filter("默认排序", 0));
			add(new Filter("价格最低", SortStoreTask.PRICE));
			// 离我最近需要 GPS 定位 在代码中判读再添加
			// add(new Filter("离我最近", SortStoreTask.DISTANCE));
			add(new Filter("评价最高", SortStoreTask.COMMENT));
			add(new Filter("最新发布", SortStoreTask.DATE));
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_listsearch);

		mBtGoBack = findViewById(R.id.search_goback);
		mTvSubmit = findViewById(R.id.search_submit);
		mEtKeyWord = findEditTextById(R.id.search_et_keyword);
		mTvCityName = findTextViewById(R.id.search_cityname);

		mTvSubmit.setOnClickListener(this);
		mBtGoBack.setOnClickListener(this);
		mTvCityName.setOnClickListener(this);
		mTvCityName.setText(SuidingApp.getApp().getCityName());

		mModuleFilter = new ModuleFilter(this, this);
		mModuleFilter.setFilter(ModuleFilter.TYPE, mltType);
		mModuleFilter.setFilter(ModuleFilter.AREA, mltArea);
		mModuleFilter.setFilter(ModuleFilter.SORT, mltSort);

		mModuleListView = new ModuleListView(this);
		mModuleListView.setOnItemClickListener(this);
		mModuleListView.setOnRefreshListener(this);
		//mModuleListView.setOnNodataRefreshListener(this);

		//mModuleSearch = new ModuleTitleSearch(this);
		
		mEtKeyWord.setOnEditorActionListener(this);

		SuidingApp app = SuidingApp.getApp();
		int status = app.getFixedCityStatus(true);
		onFixedCityChanged(app.getFixedArea(), status);
		onFixedPositionChanged(app.getFixedLocation(),
				app.getFixedPositionStatus(true));

		mModuleListView.SelectFrame(ModuleListView.NULLDATA);

		Intent tIntent = getIntent();
		mViceType = tIntent.getStringExtra(EXTRA_ST_TYPE);
		mType.setValue(tIntent.getIntExtra(EXTRA_IT_TYPE, mType.getInt()));
		mModuleFilter.selectFilter(ModuleFilter.TYPE, mType);
		mModuleListView.setLastUpdateTime(XmlCacheUtil.getDate(CACHE_DATE));

		String key = tIntent.getStringExtra(EXTRA_KEYWORD);
		if (key != null) {
			mEtKeyWord.setText(key);
			if (app.getFixedArea() == null) {
				showToastShort("定位失败！无法搜索身边信息！");
			} else {
				postTask(new SearchTask(SearchTask.TASK_REFRESH));
				mModuleListView.SelectFrame(ModuleListView.PRIGRESS);

				// mEtKeyWord.setFocusable(true);
				// InputMethodManager imm = null;
				// String Server = Context.INPUT_METHOD_SERVICE;
				// imm = (InputMethodManager) this.getSystemService(Server);
				// imm.hideSoftInputFromWindow(mEtKeyWord.getWindowToken(), 0);
			}
		}
		
		if(key != null || mType.getInt() != StoreTypeEnum.ALL){
			//关闭软键盘
			int state = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN;
			getWindow().setSoftInputMode(state);
		}
	}

	@Override
	public void onFilter(int type,int index, Filter filter) {
		// TODO Auto-generated method stub
		switch (type) {
		case ModuleFilter.TYPE:
			mType = filter;
			mViceType = null;//大类型改变，清空小类型
			onManualRefresh();
			break;
		case ModuleFilter.AREA:
			mArea = filter;
			onManualRefresh();
			break;
		case ModuleFilter.SORT:
			mSort = filter;
			if (mltStore != null && mltStore.size() > 0) {
				mModuleListView.SelectFrame(ModuleListView.PRIGRESS);
				postTask(new SearchTask(mltStore));
			}
			break;
		}
	}

	//搜索的区域限定改为 本地（定位城市） 全省 
	//注释掉加载 城市区域的筛选
	@Override
	public void onFixedCityChanged(Area area, int status) {
		// TODO Auto-generated method stub
		mTvCityName.setText(SuidingApp.getApp().getCityName());
		if (FixedCityEnum.FIXED == status) {
			//获取定位城市所在的省
			try {
				AreaEntityDao dao = new AreaEntityDao(this);
				AreaEntity entity = dao.getParent(new AreaEntity(area));
				mltArea.clear();
				mltArea.add(mArea = new Filter("本地", area));
				if(entity != null){
					mltArea.add(new Filter("全省", entity.getModel()));
					mModuleFilter.setFilter(ModuleFilter.AREA, mltArea);
				}else{
					//本地不存在省区域信息 从网络加载省份信息
					postTask(new SearchTask(SearchTask.TASK_AREAPARENT));
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.handler(e, "搜索页面，获取省级区域缓存失败");
			}
//			if (area != null && area.Children != null
//					&& area.Children.size() > 0) {
//				mltArea.clear();
//				mltArea.add(new Filter("全城", area));
//				for (Area tArea : area.Children) {
//					String simple = CityNameUtil.SimplifyCityName(tArea.Name);
//					mltArea.add(new Filter(simple, tArea));
//				}
//				mModuleFilter.setFilter(ModuleFilter.AREA, mltArea);
//			}
		} else if (FixedCityEnum.FAIL == status) {
			// 跳转到手动定位页面
			startActivity(new Intent(this, FixedCityActivity.class));
			// 并且提示 手动设置
			showToastLong("系统暂时无法定位您的位置信息，您需要手动定位。");
		}
	}

	@Override
	public void onFixedPositionChanged(Location location, int status) {
		// TODO Auto-generated method stub
		// 如果GPS定位成功天津唉离我最近排序功能
		if (mltSort.size() == 4 && FixedPositionEnum.FIXED == status) {
			mltSort.add(2,new Filter("离我最近", SortStoreTask.DISTANCE));
			mModuleFilter.setFilter(ModuleFilter.SORT, mltSort);
		}
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		if (SuidingApp.getApp().getFixedArea() == null) {
			Toast.makeText(this, "定位失败！无法搜索身边信息！", Toast.LENGTH_LONG).show();
		} else if (mEtKeyWord.getText().toString().length() == 0) {
			Toast.makeText(this, "请输入关键字！", Toast.LENGTH_LONG).show();
		} else {
			mEtKeyWord.setFocusable(true);
			InputMethodManager imm = null;
			String Server = Context.INPUT_METHOD_SERVICE;
			imm = (InputMethodManager) this.getSystemService(Server);
			imm.hideSoftInputFromWindow(mEtKeyWord.getWindowToken(), 0);
			onManualRefresh();
		}
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> absview, View view, int index,
			long id) {
		// TODO Auto-generated method stub
		index = mModuleListView.getIndex(index);
		if (index >= 0) {
			StoreBase store = mltStore.get(index);
			Intent intent = new Intent();
			intent.setClass(this, DetailStoreActivity.class);
			ExtraUtil.putExtra(DetailStoreActivity.EXTRA_DETAIL, store);
			startActivity(intent);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.search_goback:
			this.finish();
			break;
		case R.id.search_submit:			
			mEtKeyWord.setFocusable(true);
			InputMethodManager imm = null;
			String Server = Context.INPUT_METHOD_SERVICE;
			imm = (InputMethodManager) this.getSystemService(Server);
			imm.hideSoftInputFromWindow(mEtKeyWord.getWindowToken(), 0);
			onManualRefresh();
			break;
		case ModuleNodata.ID_BUTTON:
			this.onManualRefresh();
			break;
		case R.id.search_cityname:
			// 跳转到手动定位页面
			startActivity(new Intent(this, FixedCityActivity.class));
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onMore() {
		// TODO Auto-generated method stub
		postTask(new SearchTask(SearchTask.TASK_MORE));
		return true;
	}

	@Override
	public boolean onRefresh() {
		// TODO Auto-generated method stub
		return onManualRefresh(false);
	}

	private boolean onManualRefresh(boolean progress) {
		// TODO Auto-generated method stub
		if (mltArea.size() <= 1) {
			SuidingApp app = SuidingApp.getApp();
			int status = app.getFixedCityStatus(true);
			if (FixedCityEnum.FIXED == status) {
				onFixedCityChanged(app.getFixedArea(), status);
			} else if (FixedCityEnum.FIXEDING == status) {
				Toast.makeText(this, "正在定位，稍后再试。", Toast.LENGTH_LONG).show();
				return false;
			} else if (FixedCityEnum.FAIL == status) {
				Toast.makeText(this, "定位失败，稍后再试。", Toast.LENGTH_LONG).show();
				return false;
			}
		}
		postTask(new SearchTask(SearchTask.TASK_REFRESH));
		if (progress)
			mModuleListView.SelectFrame(ModuleListView.PRIGRESS);
		return true;
	}

	private boolean onManualRefresh() {
		// TODO Auto-generated method stub
		return onManualRefresh(true);
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		SearchTask tTask = (SearchTask) msg.obj;
		if (tTask.mResult == TaskBase.RESULT_FINISH) {

			if (tTask.mTask == SearchTask.TASK_REFRESH) {
				if (mModuleListView.isRefreshing()) {
					// 通知列表刷新完成
					mModuleListView.finishRefresh();
				} else {
					// 切换到列表页面
					mModuleListView.SelectFrame(ModuleListView.LISTVIEW);
				}
				// 把新数据添加到列表中
				mltStore = tTask.mltData;
				mAdapter = new StoreBaseListAdapter(this, mltStore);
				mModuleListView.setAdapter(mAdapter);
				if (mltStore.size() == 0) {
					mModuleListView.SelectFrame(ModuleListView.NULLDATA);
				}else if(mltStore.size() < ListTask.PAGESIZE){
					mModuleListView.removeFooterView();
				}else {
					mModuleListView.addFooterView();
				}
			} else if (tTask.mTask == SearchTask.TASK_MORE) {
				if (tTask.mltData.size() > 0) {
					// 通知列表刷新完成
					mModuleListView.finishLoadMore();
					// 更新列表
					mltStore.addAll(tTask.mltData);
					mAdapter.AddData(tTask.mltData);
				}
				if (tTask.mltData.size() < ListTask.PAGESIZE) {
					// 关闭更多选项
					mModuleListView.removeFooterView();
					Toast.makeText(getBaseContext(), "数据全部加载完毕！", Toast.LENGTH_SHORT)
							.show();
				}
			}else if (tTask.mTask == SearchTask.TASK_SORT) {
				// 把新数据添加到列表中
				mltStore = tTask.mltData;
				mAdapter = new StoreBaseListAdapter(this, mltStore);
				mModuleListView.setAdapter(mAdapter);
				mModuleListView.SelectFrame(ModuleListView.LISTVIEW);
			}else if (tTask.mTask == SearchTask.TASK_AREAPARENT) {
				// 把加载到的省份信息更新到界面
				if(mltArea.size() > 1){
					mModuleFilter.setFilter(ModuleFilter.AREA, mltArea);
				}
			}
		} else if (tTask.mResult == TaskBase.RESULT_FAIL) {
			if (mModuleListView.isRefreshing()) {
				// 通知列表刷新完成
				mModuleListView.finishRefreshFail();
			} else if (mltStore != null && mltStore.size() > 0) {
				mModuleListView.SelectFrame(ModuleListView.LISTVIEW);
			} else {
				mModuleListView.SelectFrame(ModuleListView.NULLDATA);
			}
			//Toast.makeText(this, tTask.mErrors, Toast.LENGTH_LONG).show();
		}
		return true;
	}

	private class SearchTask extends ListTask {

		// 枚举任务类型
		public static final int TASK_AREAPARENT = 1000; // 下拉刷新
		
		public List<StoreBase> mltData = new ArrayList<StoreBase>();

		public SearchTask(int task) {
			super(new Handler(SearchActivity.this), task);
			// TODO Auto-generated constructor stub
		}

		public SearchTask(List<StoreBase> ltData) {
			super(new Handler(SearchActivity.this), TASK_SORT);
			// TODO Auto-generated constructor stub
			mltData = ltData;
		}

		@Override
		protected void onWorking(Message tMessage) throws Exception {
			// TODO Auto-generated method stub
			super.onWorking(tMessage);
			switch (mTask) {
			case TASK_REFRESH:
			case TASK_MORE:{
				int type = (Integer) mType.getValue();
				int size = mTask == TASK_MORE?mltStore.size():0;
				Page page = new Page(PAGESIZE, size);
				String key = mEtKeyWord.getText().toString();
				if(mViceType != null && mViceType.length() > 0){
					key = mViceType + " " + key;
				}
				Address area = Address.fromArea((Area) mArea.getValue());
				IStoreBaseDomain domain = DomainFactory.getStoreBaseDomain();
				mltData.addAll(domain.GetListBy(key, area, type, page));
			}
			// 没有break 刷新完数据之后直接排序
			case TASK_SORT:
				try {
					Collections
							.sort(mltData, new SortStoreTask(mSort.getInt()));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();//handled
					AppExceptionHandler.handler(e, "搜索页面，排序失败");
				}
				break;
			case TASK_AREAPARENT:{
				IAreaDomain domain = DomainFactory.getAreaDomain();
				Area area = domain.getAreaByID(((Area)mArea.getValue()).Pid);
				try {
					if(area != null){
						mltArea.clear();
						mltArea.add(new Filter("本地",(Area)mArea.getValue()));
						mltArea.add(new Filter("全省",area));
						// 添加到本地数据库缓存
						AreaEntityDao dao = new AreaEntityDao(getBaseContext());
						dao.addWithCheckExistID(new AreaEntity(area));
						dao.addAllWithCheckExistID(AreaEntity.listFormModel(area.Children));
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			}
			}
		}
	}
}
