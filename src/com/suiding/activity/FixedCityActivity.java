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

	private List<AreaEntity> mltAllCity = new ArrayList<AreaEntity>();// ��������ɵĳ����б�
	private List<AreaEntity> mltCurCity;// ����ʹ�õĳ����б�
	private List<String> pinyinHeadList = new ArrayList<String>();// ƴ������ĸ�б�
	//private HashMap<String, Integer> mLVIndexer = new HashMap<String, Integer>();// ���ٶ�λ����

	private LinearLayout mLlContainer = null;

	private AreaEntity[] mAreaDeveloped = new AreaEntity[] {
			new AreaEntity(new Area(408, "������", 2, 24)),
			new AreaEntity(new Area(406, "������", 2, 24)), };
	private AreaEntity[] mAreaDeveloping = new AreaEntity[] {
			new AreaEntity(new Area(409, "��˳��", 2, 24)),
			new AreaEntity(new Area(407, "����ˮ��", 2, 24)),
			new AreaEntity(new Area(410, "ͭ�ʵ���", 2, 24)),
			new AreaEntity(new Area(412, "�Ͻڵ���", 2, 24)),
			new AreaEntity(new Area(413, "ǭ�������嶱��������", 2, 24)),
			new AreaEntity(new Area(414, "ǭ�ϲ���������������", 2, 24)),
			new AreaEntity(new Area(411, "ǭ���ϲ���������������", 2, 24)), };

	/**
	 * onCreate
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_fixedcity);

		// ��ʼ��ListAdapter
		mLetterView = (SpellLetterView) findViewById(R.id.rightCharacterListView);
		mLetterView.setData(pinyinHeadList);
		mLetterView.setOnTouchingLetterChangedListener(this);

		mViewGoBack = findViewById(R.id.fixedpostion_bt_back);
		mViewGoBack.setOnClickListener(this);

		//mProgress = new ModuleProgress(this);
		// ��ȡListHeader
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
		mTvCurCity.setText("��ǰ����-" + SuidingApp.getApp().getCityName());

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
		String tipmsg = "�����л���[";
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
				mHeaderDetail.setText("���ڶ�λ...");
				postTask(mTask = new FixedPositionTask(FixedPositionTask.FIXED));
			} else {
				TaskBase task = postTask(new FixedPositionTask(mTask.mCityName));
				showProgressDialog("�����л���[" + mTask.mCityName + "]...", task,
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
	 * �б���
	 * 
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 */
	public void onItemClick(AdapterView<?> absview, View view, int index,
			long id) {
		// List�����Header֮��position ��Ҫ��ǰһλ�� �ܶ�λ�����������
		// index -= mLvAllCity.getHeaderViewsCount();
		// AreaEntity tArea = mltCurCity.get(index);
		// TaskBase task = postTask(new FixedPositionTask(tArea.Name));
		// String tipmsg = "�����л���[" + CityNameUtil.SimplifyCityName(tArea.Name)
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
				mHeaderDetail.setText("�����л���");
				mHeaderDetail.setText("");
				mHeaderButtonCity.setText(tTask.mCityName);
				mHeaderButtonCity.setVisibility(View.VISIBLE);
				mHeaderProgress.setVisibility(View.GONE);
			} else if (tTask.mResult == TaskBase.RESULT_FAIL) {
				mIsFixed = false;
				mHeaderDetail.setText("��λʧ��");
				mHeaderButtonCity.setText("���¶�λ");
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
				Toast.makeText(this,"���粻�����������س����б�ʧ�ܣ�", Toast.LENGTH_LONG).show();
			}
		} else if (tTask.mTask == FixedPositionTask.SELECT) {
			if (tTask.mResult == TaskBase.RESULT_FINISH) {
				SuidingApp tApp = SuidingApp.getApp();
				tApp.setFixedCity(this, tTask.mFixedArea, FixedCityEnum.FIXED);
				// FixedCityEnum.FIXEDCITY_SELECED);
				this.finish();
			} else if (tTask.mResult == TaskBase.RESULT_FAIL) {
				Toast.makeText(this, "���粻���������л�����ʧ�ܣ�", Toast.LENGTH_LONG).show();
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

		// ��λ��� ���� FIXED ����
		public String mCityName = null;
		// public Location mLocation = null;

		// �û�ѡ���Area ���� SELECT ����
		public String mSelecedArea = null;
		// ������ص�Area ���� SELECT ����
		public Area mFixedArea = null;

		// �����߼�
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
					throw new Exception("��ʱ�޷���ȡ���λ����Ϣ");
				}else{
					mCityName = CityNameUtil.SimplifyCityName(mCityName);
				}
//				do {
//					mConut++;
//					mCityName = BaiduLocationServer.getLastCityName();
//					// mLocation = LocationServer.getLocation();
//					// if (mLocation != null) {
//					// // ���ݶ�λ��Ϣ��ȡ������Ϣ
//					// List<Address> mltAddress = LocationUtil
//					// .getAddressByLocation(mLocation,
//					// FixedCityActivity.this);
//					// mCityName = LocationUtil
//					// .getCityNameByAddress(mltAddress);
//					// mCityName = CityNameUtil.SimplifyCityName(mCityName);
//					// } else
//					if (mCityName == null) {
//						if (mConut > 5) {
//							throw new Exception("��ʱ�޷���ȡ���λ����Ϣ");
//						} else {
//							Thread.sleep(1000);
//						}
//					} else {
//						mCityName = CityNameUtil.SimplifyCityName(mCityName);
//					}
//				} while (mConut <= 5 && mCityName == null);
				break;
			case ALLCITY: {
				// ��ȡ�������ӿ�
				IAreaDomain tAreaDomain = DomainFactory.getAreaDomain();
				// ��������ȡ���г���
				Area tArea = tAreaDomain.getAreaByNameLike("����");
				List<AreaEntity> ltArea = AreaEntity
						.listFormAreaModel(tArea.Children);
				if (ltArea.size() > 0) {
					//mltAllCity = ltArea;
				} else {
					throw new Exception("���س����б�ʧ�ܣ�");
				}
				// ת����ƴ��������
				//Collections.sort(mltAllCity, this);
				// ��ӵ��������ݿ⻺��
				try {
					AreaEntityDao dao = new AreaEntityDao(getBaseContext());
					dao.addAllWithCheckExistID(ltArea);
					dao.addWithCheckExistID(new AreaEntity(tArea));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();//handled
					AppExceptionHandler.handler(e, "���س����б���ӻ������");
				}
				break;
			}

			case SELECT: {
				// �ȶ�ȡ���򻺴�
				mFixedArea = FixedCityTask.getFixedCityCaches(mSelecedArea);
				// �����ȡ����ʧ�ܼ���������������
				if (mFixedArea == null) {
					// ��ȡ�������ӿ�
					IAreaDomain tAreaDomain = DomainFactory.getAreaDomain();
					mFixedArea = tAreaDomain.getAreaByNameLike(mSelecedArea);
					if (mFixedArea == null) {
						throw new Exception("ȴ����[" + mSelecedArea + "]ʧ�ܣ�");
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
				// ��ƴ������
				return arg0.Sell.compareTo(arg1.Sell);
				// �����Ƴ�������
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
	 * �Ҳർ��������б����ָ��λ��
	 */
	public void onTouchingLetterChanged(String ch, int action) {
//		if (action == MotionEvent.ACTION_UP) {
//			return;
//		}
//		if (!"#".equals(ch))// ����
//		{
//			ch = ch.toUpperCase(Locale.ENGLISH);
//
//			if (mLVIndexer.containsKey(ch)) {
//				int pos = mLVIndexer.get(ch);
//				if (mLvAllCity.getHeaderViewsCount() > 0) {
//					// ��ֹListView�б�������������û�С�
//					mLvAllCity.setSelectionFromTop(
//							pos + mLvAllCity.getHeaderViewsCount(), 0);
//				} else {
//					mLvAllCity.setSelectionFromTop(pos, 0);
//				}
//			}
//		}
	}

	/**
	 * �������� ����ƴ����ĸ��
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
		// mltLrtterHead.add(spellhead); // ���б����ƴ������ĸ
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
					// mltLrtterHead.add(pinyinHead); // ���б����ƴ������ĸ
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
