package com.suiding.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.suiding.dao.IndustryEntityDao;
import com.suiding.domain.IClubDomain;
import com.suiding.domain.IHotelDomain;
import com.suiding.domain.IKTVDomain;
import com.suiding.domain.IRestaurantDomain;
import com.suiding.domain.IStallDomain;
import com.suiding.entity.IndustryEntity;
import com.suiding.layoutbind.ModuleFilter;
import com.suiding.layoutbind.ModuleFilter.IFilter;
import com.suiding.layoutbind.ModuleListView;
import com.suiding.layoutbind.ModuleNodata;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.layoutbind.ModuleTitleSearch;
import com.suiding.model.Area;
import com.suiding.model.Page;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.thread.SortStoreTask;
import com.suiding.thread.framework.ListTask;
import com.suiding.util.CityNameUtil;
import com.suiding.util.ExtraUtil;
import com.suiding.util.XmlCacheUtil;
import com.suiding.widget.framework.PullToRefreshBase.OnRefreshListener;

public class IndustryActivity extends ActivityBase implements
		OnItemClickListener, OnClickListener, INotifyFixedCity,
		INotifyFixedPosition, IFilter, OnRefreshListener {

	public static final String EXTRA_TYPE = "EXTRA_KEY";

	private static final String CACHE_TYPE = "INDUSTRY_CACHE_TYPE";
	private static final String CACHE_DATE = "INDUSTRY_CACHE_DATE";
	private static final String CACHE_SORT = "INDUSTRY_CACHE_SORT";

	private ModuleFilter mModuleFilter = null;
	private ModuleTitleOther mModuleTitle = null;
	private ModuleTitleSearch mModuleSearch = null;
	private ModuleListView mModuleListView = null;

	private StoreBaseListAdapter mAdapter = null;
	private List<StoreBase> mltStore = new ArrayList<StoreBase>();

	/**
	 * ѡ����
	 */
	private Filter mSort = new Filter("Ĭ������", SortStoreTask.NONE);
	private Filter mType = new Filter("ȫ��", StoreTypeEnum.HOTEL);
	private Filter mArea = new Filter("ȫ��", SuidingApp.getApp().getFixedArea());
	/**
	 * ɸѡ�����б�
	 */
	private List<Filter> mltArea = new ArrayList<Filter>() {
		private static final long serialVersionUID = 1L;
		{
			add(new Filter("ȫ��", SuidingApp.getApp().getFixedArea()));
		}
	};

	private List<Filter> mltType = new ArrayList<Filter>() {
		private static final long serialVersionUID = 1L;
		{
			add(new Filter("ȫ��", StoreTypeEnum.ALL));
			add(new Filter("����", StoreTypeEnum.FOODANDBEVERAGE));
			add(new Filter("�Ƶ�", StoreTypeEnum.HOTEL));
			add(new Filter("����", StoreTypeEnum.LIFE));
			add(new Filter("����", StoreTypeEnum.ENTERTAINMENT));
			add(new Filter("��Ů", StoreTypeEnum.GIRL));
		}
	};

	private List<Filter> mltSort = new ArrayList<Filter>() {
		private static final long serialVersionUID = 1L;
		{
			add(new Filter("Ĭ������", SortStoreTask.NONE));
			add(new Filter("�۸����", SortStoreTask.PRICE));
			// ���������Ҫ GPS ��λ �ڴ������ж������
			// add(new Filter("�������", SortStoreTask.DISTANCE));
			add(new Filter("�������", SortStoreTask.COMMENT));
			add(new Filter("���·���", SortStoreTask.DATE));
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_listindustry);

		mModuleTitle = new ModuleTitleOther(this);
		mModuleTitle.setFunction(ModuleTitleOther.FUNCTION_FIXED);
		
		mModuleSearch = new ModuleTitleSearch(this);
		mModuleSearch.toString();

		mModuleFilter = new ModuleFilter(this, this);
		mModuleFilter.setFilter(ModuleFilter.TYPE, mltType);
		mModuleFilter.setFilter(ModuleFilter.AREA, mltArea);
		mModuleFilter.setFilter(ModuleFilter.SORT, mltSort);

		mModuleListView = new ModuleListView(this);
		mModuleListView.setOnRefreshListener(this);
		mModuleListView.setOnItemClickListener(this);
		mModuleListView.setOnNodataRefreshListener(this);
		mModuleListView.SelectFrame(ModuleListView.PRIGRESS);

		SuidingApp app = SuidingApp.getApp();
		int status = app.getFixedCityStatus(true);
		onFixedCityChanged(app.getFixedArea(),status);
		onFixedPositionChanged(app.getFixedLocation(),
				app.getFixedPositionStatus(true));

		mType.setValue(ExtraUtil.getExtraInt(EXTRA_TYPE, mType.getInt()));
		mModuleFilter.selectFilter(ModuleFilter.TYPE, mType);
		mModuleListView.setLastUpdateTime(XmlCacheUtil.getDate(CACHE_DATE));

		int sort = XmlCacheUtil.getInt(CACHE_SORT, 0);
		if (sort < mltSort.size()) {
			mSort = mltSort.get(sort);
			mModuleFilter.selectFilter(ModuleFilter.SORT, mSort);
		}

		postTask(new IndustryTask(IndustryTask.TASK_LOAD));
	}

	@Override
	public void onFilter(int type,int index, Filter filter) {
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
			if (mltStore != null && mltStore.size() > 0) {
				mModuleListView.SelectFrame(ModuleListView.PRIGRESS);
				postTask(new IndustryTask(IndustryTask.TASK_SORT));
			}
			break;
		}
	}

	@Override
	public boolean onMore() {
		// TODO Auto-generated method stub
		postTask(new IndustryTask(IndustryTask.TASK_MORE));
		return true;
	}

	@Override
	public boolean onRefresh() {
		// TODO Auto-generated method stub
		return onManualRefresh(false);
	}

	@Override
	public void onFixedCityChanged(Area area, int status) {
		// TODO Auto-generated method stub
		mModuleTitle.setCityName(SuidingApp.getApp().getCityName());
		if (FixedCityEnum.FIXED == status) {
			if (area != null && area.Children != null
					&& area.Children.size() > 0) {
				mltArea.clear();
				mltArea.add(mArea = new Filter("ȫ��", area));
				for (Area tArea : area.Children) {
					String simple = CityNameUtil.SimplifyCityName(tArea.Name);
					mltArea.add(new Filter(simple, tArea));
				}
				mModuleFilter.setFilter(ModuleFilter.AREA, mltArea);
			}
		} else if (FixedCityEnum.FAIL == status) {
			SuidingApp app = SuidingApp.getApp();
			app.InvokeFixedCityActivity(this);
		}
	}

	@Override
	public void onFixedPositionChanged(Location location, int status) {
		// TODO Auto-generated method stub
		// ���GPS��λ�ɹ�����������������
		if (mltSort.size() == 4 && FixedPositionEnum.FIXED == status) {
			mltSort.add(2,new Filter("�������", 4));
			mModuleFilter.setFilter(ModuleFilter.SORT, mltSort);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case ModuleNodata.ID_BUTTON:
			onManualRefresh();
			break;
		}
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

	private boolean onManualRefresh(boolean progress) {
		// TODO Auto-generated method stub
		if (mltArea.size() <= 1) {
			SuidingApp app = SuidingApp.getApp();
			int status = app.getFixedCityStatus(true);
			if (FixedCityEnum.FIXED == status) {
				onFixedCityChanged(app.getFixedArea(), status);
			} else if (FixedCityEnum.FIXEDING == status) {
				Toast.makeText(this, "���ڶ�λ���Ժ����ԡ�", Toast.LENGTH_LONG).show();
				return false;
			} else if (FixedCityEnum.FAIL == status) {
				Toast.makeText(this, "��λʧ�ܣ��Ժ����ԡ�", Toast.LENGTH_LONG).show();
				return false;
			}
		}
		postTask(new IndustryTask(IndustryTask.TASK_REFRESH));
		if (progress)
			mModuleListView.SelectFrame(ModuleListView.PRIGRESS);
		return true;
	}

	private boolean onManualRefresh() {
		// TODO Auto-generated method stub
		return onManualRefresh(true);
	}

	private class IndustryTask extends ListTask {

		public List<StoreBase> mltData = new ArrayList<StoreBase>();

		public IndustryTask(int task) {
			super(SuidingApp.getLooper(), task);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onWorking(Message msg) throws Exception {
			// TODO Auto-generated method stub
			switch (mTask) {
			// ���ݳ��л�ȡ������Ϣ
			case TASK_LOAD:
				try {
					int type = XmlCacheUtil.getInt(CACHE_TYPE,StoreTypeEnum.ALL);
					if (type == mType.getInt()) {
						IndustryEntityDao dao = new IndustryEntityDao(
								getBaseContext());
						mltData.addAll(IndustryEntity.listToModel(dao.getAll()));
						Collections.sort(mltData,
								new SortStoreTask(mSort.getInt()));
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();//handled
					AppExceptionHandler.handler(e, "��ҵ�б�ҳ�棬Load�������");
				}
				break;
			case TASK_REFRESH: {
				Page page = new Page(PAGESIZE, 0);
				Area area = (Area) mArea.getValue();

				this.loadData(mType.getInt(), area, page);
				try {
					Collections
							.sort(mltData, new SortStoreTask(mSort.getInt()));
					IndustryEntityDao dao = new IndustryEntityDao(
							getBaseContext());
					dao.updateCache(IndustryEntity.listFormModel(mltData));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			}
			case TASK_MORE: {
				Area area = (Area) mArea.getValue();
				Page page = new Page(PAGESIZE, mltStore.size());

				this.loadData(mType.getInt(), area, page);
				try {
					Collections
							.sort(mltData, new SortStoreTask(mSort.getInt()));
					IndustryEntityDao dao = new IndustryEntityDao(
							getBaseContext());
					dao.appendCache(IndustryEntity.listFormModel(mltData));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			}
			case TASK_SORT:
				Collections.sort(mltStore, new SortStoreTask(mSort.getInt()));
				break;
			}
		}

		private void loadData(int type, Area area, Page page) throws Exception {
			// TODO Auto-generated method stub
			switch (type) {
			case StoreTypeEnum.ENTERTAINMENT: {
				IKTVDomain domain = DomainFactory.getKTVDomain();
				mltData.addAll(domain.GetAllByArea(area, page));
				break;
			}
			case StoreTypeEnum.FOODANDBEVERAGE: {
				IRestaurantDomain domain = DomainFactory.getRestaurantDomain();
				mltData.addAll(domain.GetAllByArea(area, page));
				break;
			}
			case StoreTypeEnum.GIRL: {
				IClubDomain domain = DomainFactory.getClubDomain();
				mltData.addAll(domain.GetAllByArea(area, page));
				break;
			}
			case StoreTypeEnum.HOTEL: {
				IHotelDomain domain = DomainFactory.getHotelDomain();
				mltData.addAll(domain.GetAllByArea(area, page));
				break;
			}
			case StoreTypeEnum.LIFE: {
				IStallDomain domain = DomainFactory.getStallDomain();
				mltData.addAll(domain.GetAllByArea(area, page));
				break;
			}
			}
		}

		@Override
		public boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if (mResult == RESULT_FINISH) {
				switch (mTask) {
				case TASK_LOAD: {
					// �����ص������� tTask.mltData ��ӵ� ������ ��
					mltStore = mltData;
					// Ϊ�б���������
					mAdapter = new StoreBaseListAdapter(getBaseContext(),
							mltStore);
					mModuleListView.setAdapter(mAdapter);
					if (mltStore.size() == 0) {
						// �״μ�������ʧ�ܴӷ�������ˢ������
						mModuleListView.SelectFrame(ModuleListView.NULLDATA);
						onManualRefresh();
					} else {
						// �л����б�ҳ��
						mModuleListView.SelectFrame(ModuleListView.LISTVIEW);
					}
					break;
				}
				case TASK_REFRESH: {
					if (mModuleListView.isRefreshing()) {
						// ֪ͨ�б�ˢ�����
						mModuleListView.finishRefresh();
					} else {
						// �л����б�ҳ��
						mModuleListView.SelectFrame(ModuleListView.LISTVIEW);
					}
					// �����ص������� tTask.mltData ��ӵ� ������ ��
					mltStore = mltData;
					// Ϊ�б���������
					mAdapter = new StoreBaseListAdapter(getBaseContext(),
							mltStore);
					mModuleListView.setAdapter(mAdapter);
					// �������һ�θ���ʱ��
					XmlCacheUtil.putInt(CACHE_TYPE, mType.getInt());
					XmlCacheUtil.putDate(CACHE_DATE, new Date());
					if (mltData.size() == 0) {
						mModuleListView.SelectFrame(ModuleListView.NULLDATA);
					} else if (mltData.size() < PAGESIZE) {
						mModuleListView.removeFooterView();
					} else {
						mModuleListView.addFooterView();
					}
					break;
				}
				case TASK_MORE: {
					if (mltData.size() > 0) {
						// ֪ͨ�б�ˢ�����
						mModuleListView.finishLoadMore();
						// �����б�
						mltStore.addAll(mltData);
						mAdapter.AddData(mltData);
					}
					if (mltData.size() < PAGESIZE) {
						// �رո���ѡ��
						mModuleListView.removeFooterView();
						Toast.makeText(getBaseContext(), "����ȫ��������ϣ�",
								Toast.LENGTH_SHORT).show();
					}
					break;
				}
				case TASK_SORT: {
					// Ϊ�б���������
					mAdapter = new StoreBaseListAdapter(getBaseContext(),
							mltStore);
					mModuleListView.setAdapter(mAdapter);
					mModuleListView.SelectFrame(ModuleListView.LISTVIEW);
					break;
				}// TASK_SORT
				}// switch
			} else if (mResult == RESULT_FAIL) {
				if (mModuleListView.isRefreshing()) {
					// ֪ͨ�б�ˢ�����
					mModuleListView.finishRefreshFail();
				} else if (mltStore == null || mltStore.size() == 0) {
					mModuleListView.SelectFrame(ModuleListView.NULLDATA);
				} else {
					mModuleListView.SelectFrame(ModuleListView.LISTVIEW);
				}
//				Toast.makeText(getBaseContext(), mErrors, Toast.LENGTH_LONG)
//						.show();
			}
			return true;
		}
	}
}
