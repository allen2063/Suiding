package com.suiding.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.adapter.FixedPositionListAdapter;
import com.suiding.adapter.FixedPositionListAdapter.CityItem;
import com.suiding.adapter.FixedPositionListAdapter.CityItem.CityItemListener;
import com.suiding.application.AppExceptionHandler;
import com.suiding.application.BaiduLocationServer;
import com.suiding.application.SuidingApp;
import com.suiding.constant.FixedCityEnum;
import com.suiding.dao.AreaEntityDao;
import com.suiding.domain.IAreaDomain;
import com.suiding.entity.AreaEntity;
import com.suiding.model.Area;
import com.suiding.service.DomainFactory;
import com.suiding.thread.FixedCityTask;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.CityNameUtil;
import com.suiding.util.ExtraUtil;
import com.suiding.widget.SpellLetterView;
import com.suiding.widget.SpellLetterView.OnTouchingLetterChangedListener;

public class FixedCityActivity extends ActivityBase implements
		OnItemClickListener, TextWatcher, OnTouchingLetterChangedListener,
		OnClickListener, Callback, CityItemListener {
	public static final String EXTRA_NEEDFIXED = "EXTRA_NEEDFIXED";

	private View mViewGoBack;
	//private View mViewDeveloped;
	private ListView mLvAllCity;
	private EditText mEtCityName;
	//private ModuleProgress mProgress;
	private SpellLetterView mLetterView;
	private FixedPositionListAdapter mListAdapter;

	private TextView mTvCurCity = null;
	/**
	 * Header Views
	 */
	private View mHeaderView = null;
	private TextView mHeaderDetail = null;
	private Button mHeaderButtonCity = null;
	private ProgressBar mHeaderProgress = null;

	private Boolean mIsFixed = false;
	private FixedPositionTask mTask = null;

	private List<AreaEntity> mltAllCity = new ArrayList<AreaEntity>();// 已排序完成的城市列表
	private List<AreaEntity> mltCurCity;// 正在使用的城市列表
	private List<String> pinyinHeadList = new ArrayList<String>();// 拼音首字母列表
	//private HashMap<String, Integer> mLVIndexer = new HashMap<String, Integer>();// 快速定位索引

	private LinearLayout mLlContainer = null;

	private AreaEntity[] mAreaDeveloped = new AreaEntity[] {
			new AreaEntity(new Area(408, "遵义市", 2, 24)),
			new AreaEntity(new Area(406, "贵阳市", 2, 24)), };
	private AreaEntity[] mAreaDeveloping = new AreaEntity[] {
			new AreaEntity(new Area(409, "安顺市", 2, 24)),
			new AreaEntity(new Area(407, "六盘水市", 2, 24)),
			new AreaEntity(new Area(410, "铜仁地区", 2, 24)),
			new AreaEntity(new Area(412, "毕节地区", 2, 24)),
			new AreaEntity(new Area(413, "黔东南苗族侗族自治州", 2, 24)),
			new AreaEntity(new Area(414, "黔南布依族苗族自治州", 2, 24)),
			new AreaEntity(new Area(411, "黔西南布依族苗族自治州", 2, 24)), };

	/**
	 * onCreate
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_fixedcity);

		// 初始化ListAdapter
		mLetterView = (SpellLetterView) findViewById(R.id.rightCharacterListView);
		mLetterView.setData(pinyinHeadList);
		mLetterView.setOnTouchingLetterChangedListener(this);

		mViewGoBack = findViewById(R.id.fixedpostion_bt_back);
		mViewGoBack.setOnClickListener(this);

		//mProgress = new ModuleProgress(this);
		// 获取ListHeader
		mHeaderView = LayoutInflater.from(this).inflate(
				R.layout.fixedcity_list_header, null);

		mLvAllCity = (ListView) findViewById(R.id.listInfo);
		mLvAllCity.setOnItemClickListener(this);
		mLvAllCity.addHeaderView(mHeaderView);

		mHeaderDetail = findTextViewById(R.id.fixed_list_header_tv_fixing);
		mHeaderProgress = findProgressBarById(R.id.fixed_list_header_progress);
		mHeaderView = findLinearLayoutById(R.id.fixed_list_header_layout);

		mHeaderButtonCity = findButtonById(R.id.fixed_list_header_bt_city);
		mHeaderButtonCity.setOnClickListener(this);

		mLlContainer = findLinearLayoutById(R.id.fixed_list_header_container);
		//mViewDeveloped = findViewById(R.id.fixed_list_header_developedlayout);

		mEtCityName = findEditTextById(R.id.fixedposition_entercityname);
		mEtCityName.addTextChangedListener(this);

		mTvCurCity = findTextViewById(R.id.fixedpostion_tv_city);
		mTvCurCity.setText("当前城市-" + SuidingApp.getApp().getCityName());

		AreaEntityDao tDao = new AreaEntityDao(this);
		mltAllCity = tDao.getAllCity();
		if (mltAllCity.size() < 5) {
			//postTask(new FixedPositionTask(FixedPositionTask.ALLCITY));
			if (mltAllCity.size() == 0) {
				//mLvAllCity.setVisibility(View.INVISIBLE);
			}
		}
		if (mltAllCity.size() > 0) {
			Collections.sort(mltAllCity, new FixedPositionTask(0));
			// makeLetterList(mltAllCity);
		}
		//
		initializeDeveloped();
		initializeDeveloping();

		Intent tIntent = getIntent();
		Boolean isNeedFixed = tIntent.getBooleanExtra(EXTRA_NEEDFIXED, false);
		if (isNeedFixed == false) {
			isNeedFixed = ExtraUtil.getExtraBoolean(EXTRA_NEEDFIXED, false);
		}
		if (isNeedFixed == true) {
			onClick(mHeaderButtonCity);
		}

	}

	private void initializeDeveloping() {
		// TODO Auto-generated method stub
		mltAllCity = new ArrayList<AreaEntity>();
		for (int i = 0; i < mAreaDeveloping.length; i++) {
			mltAllCity.add(mAreaDeveloping[i]);
		}
		setAreaList(mltAllCity);
	}

	private void initializeDeveloped() {
		// TODO Auto-generated method stub
		mLlContainer.removeAllViews();
		LayoutInflater inflater = LayoutInflater.from(this);
		for (int i = 0; i < mAreaDeveloped.length; i++) {
			AreaEntity area = mAreaDeveloped[i];
			CityItem item = new CityItem();
			View v = inflater.inflate(item.getLayoutId(), null);
			item.Handle(v);
			item.Binding(area);
			item.setCityItemListener(this);
			mLlContainer.addView(v);
		}
	}

	private void setAreaList(List<AreaEntity> ltcity) {
		// TODO Auto-generated method stub
		mltCurCity = ltcity;
		mListAdapter = new FixedPositionListAdapter(this, mltCurCity);
		mLvAllCity.setAdapter(mListAdapter);
		mLvAllCity.setVisibility(View.VISIBLE);
		makeLetterList(mltCurCity);
	}

	@Override
	public void onCityItemClick(CityItem item) {
		// TODO Auto-generated method stub
		AreaEntity tArea = item.mAreaEntity;
		TaskBase task = postTask(new FixedPositionTask(tArea.Name));
		String tipmsg = "正在切换到[";
		tipmsg += CityNameUtil.SimplifyCityName(tArea.Name);
		tipmsg += "]...";
		showProgressDialog(tipmsg, task, 20);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fixed_list_header_bt_city:
			if (mIsFixed == false) {
				mHeaderProgress.setVisibility(View.VISIBLE);
				mHeaderButtonCity.setVisibility(View.GONE);
				mHeaderDetail.setText("正在定位...");
				postTask(mTask = new FixedPositionTask(FixedPositionTask.FIXED));
			} else {
				TaskBase task = postTask(new FixedPositionTask(mTask.mCityName));
				showProgressDialog("正在切换到[" + mTask.mCityName + "]...", task,
						15);
			}
			break;
		case R.id.fixedpostion_bt_back:
			this.finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 列表点击
	 * 
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 */
	public void onItemClick(AdapterView<?> absview, View view, int index,
			long id) {
		// List添加了Header之后position 需要向前一位才 能定位到点击的数据
		// index -= mLvAllCity.getHeaderViewsCount();
		// AreaEntity tArea = mltCurCity.get(index);
		// TaskBase task = postTask(new FixedPositionTask(tArea.Name));
		// String tipmsg = "正在切换到[" + CityNameUtil.SimplifyCityName(tArea.Name)
		// + "]...";
		// showProgressDialog(tipmsg, task, 20);
	}

	/**
	 * handleMessage
	 */
	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		FixedPositionTask tTask = (FixedPositionTask) msg.obj;
		if (tTask.mTask == FixedPositionTask.FIXED) {
			if (tTask.mResult == TaskBase.RESULT_FINISH) {
				mIsFixed = true;
				mHeaderDetail.setText("马上切换到");
				mHeaderDetail.setText("");
				mHeaderButtonCity.setText(tTask.mCityName);
				mHeaderButtonCity.setVisibility(View.VISIBLE);
				mHeaderProgress.setVisibility(View.GONE);
			} else if (tTask.mResult == TaskBase.RESULT_FAIL) {
				mIsFixed = false;
				mHeaderDetail.setText("定位失败");
				mHeaderButtonCity.setText("重新定位");
				mHeaderButtonCity.setVisibility(View.VISIBLE);
				mHeaderProgress.setVisibility(View.GONE);
				Toast.makeText(this, tTask.mErrors, Toast.LENGTH_LONG).show();
			}
		} else if (tTask.mTask == FixedPositionTask.ALLCITY) {
			if (tTask.mResult == TaskBase.RESULT_FINISH) {
				// mProgress.hide();
				// mltCurCity = mltAllCity;
				// mListAdapter = new FixedPositionListAdapter(this,
				// mltAllCity);
				// mLvAllCity.setAdapter(mListAdapter);
				// mLvAllCity.setVisibility(View.VISIBLE);
				// makeLetterList(mltCurCity);
			} else if (tTask.mResult == TaskBase.RESULT_FAIL) {
				Toast.makeText(this,"网络不给力啊，下载城市列表失败！", Toast.LENGTH_LONG).show();
			}
		} else if (tTask.mTask == FixedPositionTask.SELECT) {
			if (tTask.mResult == TaskBase.RESULT_FINISH) {
				SuidingApp tApp = SuidingApp.getApp();
				tApp.setFixedCity(this, tTask.mFixedArea, FixedCityEnum.FIXED);
				// FixedCityEnum.FIXEDCITY_SELECED);
				this.finish();
			} else if (tTask.mResult == TaskBase.RESULT_FAIL) {
				Toast.makeText(this, "网络不给力啊，切换城市失败！", Toast.LENGTH_LONG).show();
			}
			hideProgressDialog();
		}
		return true;
	}

	private final class FixedPositionTask extends TaskBase implements
			Comparator<AreaEntity> {
		public static final int FIXED = 0;
		public static final int ALLCITY = 1;
		public static final int SELECT = 2;

		// 定位结果 用于 FIXED 任务
		public String mCityName = null;
		// public Location mLocation = null;

		// 用户选择的Area 用于 SELECT 任务
		public String mSelecedArea = null;
		// 网络加载的Area 用于 SELECT 任务
		public Area mFixedArea = null;

		// 任务逻辑
		//private int mConut = 0;

		public FixedPositionTask(int task) {
			super(new Handler(FixedCityActivity.this), task);
			// TODO Auto-generated constructor stub
		}

		public FixedPositionTask(String tArea) {
			super(new Handler(FixedCityActivity.this), SELECT);
			// TODO Auto-generated constructor stub
			mSelecedArea = tArea;
		}

		@Override
		protected void onWorking(Message tMessage) throws Exception {
			// TODO Auto-generated method stub

			switch (mTask) {
			case FIXED:
				mCityName = BaiduLocationServer.fixedCityName();
				if(mCityName == null){
					throw new Exception("暂时无法获取你的位置信息");
				}else{
					mCityName = CityNameUtil.SimplifyCityName(mCityName);
				}
//				do {
//					mConut++;
//					mCityName = BaiduLocationServer.getLastCityName();
//					// mLocation = LocationServer.getLocation();
//					// if (mLocation != null) {
//					// // 根据定位信息读取区域信息
//					// List<Address> mltAddress = LocationUtil
//					// .getAddressByLocation(mLocation,
//					// FixedCityActivity.this);
//					// mCityName = LocationUtil
//					// .getCityNameByAddress(mltAddress);
//					// mCityName = CityNameUtil.SimplifyCityName(mCityName);
//					// } else
//					if (mCityName == null) {
//						if (mConut > 5) {
//							throw new Exception("暂时无法获取你的位置信息");
//						} else {
//							Thread.sleep(1000);
//						}
//					} else {
//						mCityName = CityNameUtil.SimplifyCityName(mCityName);
//					}
//				} while (mConut <= 5 && mCityName == null);
				break;
			case ALLCITY: {
				// 获取区域服务接口
				IAreaDomain tAreaDomain = DomainFactory.getAreaDomain();
				// 服务器读取所有城市
				Area tArea = tAreaDomain.getAreaByNameLike("贵州");
				List<AreaEntity> ltArea = AreaEntity
						.listFormAreaModel(tArea.Children);
				if (ltArea.size() > 0) {
					//mltAllCity = ltArea;
				} else {
					throw new Exception("下载城市列表失败！");
				}
				// 转换成拼音和排序
				//Collections.sort(mltAllCity, this);
				// 添加到本地数据库缓存
				try {
					AreaEntityDao dao = new AreaEntityDao(getBaseContext());
					dao.addAllWithCheckExistID(ltArea);
					dao.addWithCheckExistID(new AreaEntity(tArea));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();//handled
					AppExceptionHandler.handler(e, "下载城市列表，添加缓存出错");
				}
				break;
			}

			case SELECT: {
				// 先读取区域缓存
				mFixedArea = FixedCityTask.getFixedCityCaches(mSelecedArea);
				// 如果读取缓存失败加载网络区域数据
				if (mFixedArea == null) {
					// 获取区域服务接口
					IAreaDomain tAreaDomain = DomainFactory.getAreaDomain();
					mFixedArea = tAreaDomain.getAreaByNameLike(mSelecedArea);
					if (mFixedArea == null) {
						throw new Exception("却换到[" + mSelecedArea + "]失败！");
					}
					try {
						AreaEntityDao dao = new AreaEntityDao(getBaseContext());
						dao.addWithCheckExistID(new AreaEntity(mFixedArea));
						List<AreaEntity> list = AreaEntity.listFormModel(mFixedArea.Children);
						dao.addAllWithCheckExistID(list);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				break;
			}
			}
		}

		@Override
		protected void onException(Exception e) {
			// TODO Auto-generated method stub
			if (mErrors == null || mErrors.length() == 0) {
				mErrors = e.toString();
			}
		}

		@Override
		public int compare(AreaEntity arg0, AreaEntity arg1) {
			// TODO Auto-generated method stub
			if (arg0.Sell != null && arg1.Sell != null) {
				// 按拼音排序
				return arg0.Sell.compareTo(arg1.Sell);
				// 按名称长度排序
//				int len0 = arg0.Name.length();
//				int len1 = arg1.Name.length();
//				if (len0 == len1) {
//					return 0;
//				}
//				return len1 > len0 ? 1 : 0;
			}
			return 0;
		}

	}

	/**
	 * 右侧导航条点击列表滚动指定位置
	 */
	public void onTouchingLetterChanged(String ch, int action) {
//		if (action == MotionEvent.ACTION_UP) {
//			return;
//		}
//		if (!"#".equals(ch))// 顶部
//		{
//			ch = ch.toUpperCase(Locale.ENGLISH);
//
//			if (mLVIndexer.containsKey(ch)) {
//				int pos = mLVIndexer.get(ch);
//				if (mLvAllCity.getHeaderViewsCount() > 0) {
//					// 防止ListView有标题栏，本例中没有。
//					mLvAllCity.setSelectionFromTop(
//							pos + mLvAllCity.getHeaderViewsCount(), 0);
//				} else {
//					mLvAllCity.setSelectionFromTop(pos, 0);
//				}
//			}
//		}
	}

	/**
	 * 根据区域 构造拼音字母表
	 * 
	 * @param ltArea
	 * @return
	 */
	private void makeLetterList(List<AreaEntity> ltArea) {
		// List<String> mltLrtterHead = new ArrayList<String>();
		// mltLrtterHead.add("#");
		// for (int i = 0; i < ltArea.size(); i++) {
		// AreaEntity tEntity = ltArea.get(i);
		// String spellhead = tEntity.Sell;
		// if (spellhead != null && spellhead.length() > 0) {
		// spellhead = spellhead.substring(0, 1).toUpperCase(
		// Locale.ENGLISH);
		// if (!mLVIndexer.containsKey(spellhead)
		// && SpellUtil.isWord(spellhead)) {
		// mLVIndexer.put(spellhead, i);
		// }
		// if (!mltLrtterHead.contains(spellhead)
		// && SpellUtil.isWord(spellhead)) {
		// mltLrtterHead.add(spellhead); // 此列表添加拼音首字母
		// }
		// }
		// }
		// mLetterView.setData(mltLrtterHead);
		mLetterView.setVisibility(View.GONE);
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		String tCityName = mEtCityName.getText().toString();
		// List<String> mltLrtterHead = new ArrayList<String>();
		List<AreaEntity> tltSearch = new ArrayList<AreaEntity>();
		// mltLrtterHead.add("#");
		if (tCityName.length() > 0) {
			for (AreaEntity tEntity : mltAllCity) {
				if (tEntity.Name.contains(tCityName)) {
					tltSearch.add(tEntity);
					// String pinyinHead = tEntity.Sell.substring(0, 1);
					// if (!mltLrtterHead.contains(pinyinHead)
					// && SpellUtil.isWord(pinyinHead)) {
					// mltLrtterHead.add(pinyinHead); // 此列表添加拼音首字母
					// }
				}
			}
			mltCurCity = tltSearch;
			// mLetterView.setData(mltLrtterHead);
			mHeaderView.setVisibility(View.GONE);
		} else {
			mltCurCity = mltAllCity;
			// makeLetterList(mltCurCity);
			mHeaderView.setVisibility(View.VISIBLE);
		}
		setAreaList(mltCurCity);
		// mLvAllCity.setAdapter(new FixedPositionListAdapter(this,
		// mltCurCity));
	}

}
