package com.suiding.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.suiding.activity.FixedCityActivity;
import com.suiding.activity.PreSearchActivity;
import com.suiding.activity.R;
import com.suiding.activity.SearchActivity;
import com.suiding.application.SuidingApp;
import com.suiding.application.SuidingApp.INotifyFixedCity;
import com.suiding.constant.StoreTypeEnum;
import com.suiding.fragment.framework.FragmentBase;
import com.suiding.model.Area;
import com.suiding.thread.SmoothRunnable;
import com.suiding.thread.SmoothRunnable.Smoothable;
import com.suiding.util.ExtraUtil;
import com.suiding.util.MeasureUtil;
import com.suiding.widget.PullDownLinearLayout;
import com.suiding.widget.PullDownLinearLayout.ReadyForPullDownable;

/**
 * A dummy fragment representing a section of the app, but that simply displays
 * dummy text.
 */
public class IndexHomeFragment extends FragmentBase implements OnClickListener,
		INotifyFixedCity, OnEditorActionListener, Smoothable, OnTouchListener,
		ReadyForPullDownable {

	private View mItemHotel = null; // 酒店
	private View mItemRepast = null; // 餐饮
	private View mItemLive = null; // 生活
	private View mItemRecreate = null; // 娱乐
	private View mItemBelle = null; // 美女

	private ScrollView mScrollView = null;
	private PullDownLinearLayout mPullDown = null;

	private Button mBtKeyWord = null;
	private EditText mEtKeyWord = null;
	private TextView mTvSearch = null;
	private TextView mTvCityName = null;
	private TextView mTvCityNameEx = null;
	private FrameLayout mLayoutCover = null;
	private LinearLayout mLayoutSearch = null;
	private LinearLayout mLayoutKeyWord = null;

	private int maxalpha = 200;
	private int maxPaddingTop;
	private int maxPaddingLeft;
	private int maxPaddingRight;
	private int maxTvCityWidth;
	private int maxTvSearchWidth;
	private boolean mIsKeyWording = false;

	private SmoothRunnable mSmooth = null;

	private int itemids[] = new int[] { R.id.home_item_repast,
			R.id.home_item_hotel, R.id.home_item_live, R.id.home_item_recreate,
			R.id.home_item_belle, };

	private int itemtypes[] = new int[] { StoreTypeEnum.FOODANDBEVERAGE,
			StoreTypeEnum.HOTEL, StoreTypeEnum.LIFE,
			StoreTypeEnum.ENTERTAINMENT, StoreTypeEnum.GIRL };

	@Override
	protected final View onCreateView(LayoutInflater inflater, ViewGroup container) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.index_home, container, false);
	}
	@Override
	protected final void onCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mRootView.setOnTouchListener(this);

		mItemHotel = findViewById(R.id.home_item_hotel);
		mItemRepast = findViewById(R.id.home_item_repast);
		mItemLive = findViewById(R.id.home_item_live);
		mItemRecreate = findViewById(R.id.home_item_recreate);
		mItemBelle = findViewById(R.id.home_item_belle);

		mScrollView = findScrollViewById(R.id.home_scrollview);

		mLayoutSearch = findLinearLayoutById(R.id.home_searchbar_ex);
		mLayoutKeyWord = findLinearLayoutById(R.id.home_searchbar);
		mLayoutCover = findFrameLayoutById(R.id.home_cover);

		mBtKeyWord = findButtonById(R.id.home_bt_keyword);
		mEtKeyWord = findEditTextById(R.id.home_et_keyword);
		mTvCityName = findTextViewById(R.id.home_tv_cityname);
		mTvCityNameEx = findTextViewById(R.id.home_tv_cityname_ex);
		mTvSearch = findTextViewById(R.id.home_tv_search);

		mTvSearch.setOnClickListener(this);
		mLayoutCover.setOnClickListener(this);
		mBtKeyWord.setOnClickListener(this);
		mTvCityName.setOnClickListener(this);
		mItemHotel.setOnClickListener(this);
		mItemRepast.setOnClickListener(this);
		mItemLive.setOnClickListener(this);
		mItemRecreate.setOnClickListener(this);
		mItemBelle.setOnClickListener(this);
		mEtKeyWord.setOnEditorActionListener(this);
		
		Resources res = getResources();
		float height = res.getDimension(R.dimen.home_pulldownheight);
		mPullDown = (PullDownLinearLayout)findViewById(R.id.home_layout);
		mPullDown.setPullDownable(this);
		mPullDown.setMaxScorllHeight((int)height);
		
		maxPaddingTop = mLayoutCover.getPaddingTop();
		maxPaddingLeft = mLayoutCover.getPaddingLeft();
		maxPaddingRight = mLayoutCover.getPaddingRight();
		maxTvCityWidth = MeasureUtil.measureView(mTvCityName).x;
		maxTvSearchWidth = MeasureUtil.measureView(mTvSearch).x;
		mLayoutKeyWord.setPadding(0, 0,-maxTvSearchWidth, 0);
	}

	@Override
	public boolean isReadyForPullDown() {
		// TODO Auto-generated method stub
		return mScrollView.getScrollY() <= 0;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressLint("NewApi")
	private final void smoothScrollLayout(boolean visiable) {
		if (null != mSmooth) {
			mSmooth.stop();
		}
		int from = mLayoutCover.getPaddingTop();
		int to = visiable ? 0 : maxPaddingTop;
		if (from != to) {
			if (!mIsKeyWording && visiable) {
				mLayoutCover.setVisibility(View.VISIBLE);
				mLayoutKeyWord.setVisibility(View.VISIBLE);
				mLayoutSearch.setVisibility(View.INVISIBLE);
			}
			maxTvCityWidth = mTvCityName.getWidth();//MeasureUtil.measureView(mTvCityName).x;
			mSmooth = new SmoothRunnable(this, from, to, 250);
			mSmooth.start();
		} else {
			int alpha = visiable ? maxalpha : 0;
			int paddingLeft = visiable ? 0 : maxPaddingLeft;
			int paddingRight = visiable ? 0 : maxPaddingRight;
			int visibility = visiable ? View.VISIBLE : View.INVISIBLE;
			mLayoutCover.setPadding(paddingLeft, to, paddingRight, 0);
			mLayoutCover.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
			mLayoutCover.setVisibility(visibility);
			mLayoutKeyWord.setVisibility(visibility);
			mLayoutSearch.setVisibility(visibility);
		}
		mIsKeyWording = visiable;
		mEtKeyWord.setText("");
		
		setSoftInputEnable(mEtKeyWord, visiable);
//		mEtKeyWord.setFocusable(true);
//		InputMethodManager imm = null;
//		String Server = Context.INPUT_METHOD_SERVICE;
//		imm = (InputMethodManager) mActivity.getSystemService(Server);
//		if (visiable) {
//			mEtKeyWord.setFocusableInTouchMode(true);
//			mEtKeyWord.requestFocus();
//			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//		} else {
//			imm.hideSoftInputFromWindow(mEtKeyWord.getWindowToken(), 0);
//		}
	}
	@Override
	public void onStart(int from, int to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(int from, int to) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onSmooth(int value, float percent, int from, int to) {
		// TODO Auto-generated method stub
		float ratio = (float) value / maxPaddingTop;
		int alpha = (int) (maxalpha * (1 - ratio));
		int paddingLeft = (int) (maxPaddingLeft * ratio);
		int paddingRight = (int) (maxPaddingRight * ratio);
		mLayoutCover.setPadding(paddingLeft, value, paddingRight, 0);
		mLayoutCover.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
		
		paddingLeft = -(int)(maxTvCityWidth * (1-ratio));
		paddingRight = -(int)(maxTvSearchWidth * (ratio));
		//mLayoutKeyWord.scrollTo(value,0);
		mLayoutKeyWord.setPadding(paddingLeft, 0,paddingRight, 0);
		
		if (value == to && to != 0) {
			//收缩 结束时 对页面显示调整
			mLayoutSearch.setVisibility(View.VISIBLE);
			mLayoutKeyWord.setVisibility(View.INVISIBLE);
			mLayoutCover.setVisibility(View.INVISIBLE);
		}
		return true;
	}

	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// // TODO Auto-generated method stub
	// if (mIsChanged == true && keyCode == KeyEvent.KEYCODE_BACK) {
	// this.setResult(RESULT_OK,new Intent());
	// this.finish();
	// return true;
	// }
	// return super.onKeyDown(keyCode, event);
	// }

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		if (EditorInfo.IME_ACTION_SEARCH == actionId) {
			onClick(mTvSearch);
		}
		return false;
	}

	/**
	 * 切换到  页面
	 */
	@Override
	protected void onSwitchOver(int count) {
		// TODO Auto-generated method stub
		String format = getString(R.string.home_tv_cityname_format);
		String cityname = SuidingApp.getApp().getCityName();
		mTvCityName.setText(String.format(format, cityname));
		mTvCityNameEx.setText(String.format(format, cityname));
	}

	@Override
	public void onFixedCityChanged(Area area, int status) {
		// TODO Auto-generated method stub
		String format = getString(R.string.home_tv_cityname_format);
		String cityname = SuidingApp.getApp().getCityName();
		mTvCityName.setText(String.format(format, cityname));
		mTvCityNameEx.setText(String.format(format, cityname));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.home_tv_search:{
			Intent intent = new Intent();
			String keyword = mEtKeyWord.getText().toString();
			intent.setClass(mActivity, SearchActivity.class);
			intent.putExtra(SearchActivity.EXTRA_KEYWORD, keyword);
			smoothScrollLayout(false);
			startActivity(intent);
			break;
		}
		case R.id.home_bt_keyword:{
			Intent intent = new Intent();
			intent.setClass(mActivity, PreSearchActivity.class);
			startActivity(intent);
		}
//			if (mIsKeyWording == false) {
//				smoothScrollLayout(true);
//			}
			break;
		case R.id.home_cover:
			if (mIsKeyWording == true) {
				smoothScrollLayout(false);
			}
			break;
		case R.id.home_tv_cityname:
			// 跳转到手动定位页面
			ExtraUtil.putExtra(FixedCityActivity.EXTRA_NEEDFIXED, true);
			startActivity(new Intent(mActivity, FixedCityActivity.class));
			break;
		default:
			for (int i = 0, id = v.getId(); i < itemids.length; i++) {
				if (itemids[i] == id) {
//					Intent intent = new Intent();
//					ExtraUtil.putExtra(IndustryActivity.EXTRA_TYPE,
//							itemtypes[i]);
//					intent.setClass(mActivity, IndustryActivity.class);
//					startActivity(intent);
					Intent intent = new Intent();
					intent.setClass(mActivity, SearchActivity.class);
					intent.putExtra(SearchActivity.EXTRA_KEYWORD, "");
					intent.putExtra(SearchActivity.EXTRA_IT_TYPE, itemtypes[i]);
					startActivity(intent);
					break;
				}
			}
			break;
		}
	}

}