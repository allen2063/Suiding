package com.suiding.layoutbind;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.suiding.activity.FixedCityActivity;
import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.application.SuidingApp;
import com.suiding.layoutbind.framework.ILayout;

public class ModuleTitleOther implements ILayout, OnClickListener {

	public static final int FUNCTION_NONE = 0;
	public static final int FUNCTION_FINISH = 1;
	public static final int FUNCTION_ADD = 2;
	public static final int FUNCTION_COLLECT = 3;
	public static final int FUNCTION_BACKUP = 4;
	public static final int FUNCTION_OK = 5;
	public static final int FUNCTION_FIXED = 6;

	public static final int ID_GOBACK = R.id.titlebar_other_goback;
	public static final int ID_FINISH = R.id.titlebar_other_finish;
	public static final int ID_ADD = R.id.titlebar_other_add;
	public static final int ID_COLLECT = R.id.titlebar_other_collect;
	public static final int ID_BACKUP = R.id.titlebar_other_backup;
	public static final int ID_OK = R.id.titlebar_other_ok;

	private View mLayout = null;
	private View mBtGoBack = null;
	private View mBtAdd = null;
	private View mBtFinish = null;
	private View mBtOK = null;
	private View mBtBackup = null;
	private TextView mTvTitle = null;
	private TextView mTvCityName = null;
	private CheckBox mCbCollect = null;

	private boolean mIsValid = false;

	private WeakReference<Activity> mWeakRefActivity = null;

	public ModuleTitleOther(Pageable page) {
		// TODO Auto-generated constructor stub
		this(page, FUNCTION_NONE);
	}

	public ModuleTitleOther(Pageable page, int function) {
		// TODO Auto-generated constructor stub
		mBtAdd = page.findViewById(R.id.titlebar_other_add);
		mBtGoBack = page.findViewById(R.id.titlebar_other_goback);
		mBtFinish = page.findViewById(R.id.titlebar_other_finish);
		mBtOK = page.findViewById(R.id.titlebar_other_ok);
		mBtBackup = page.findViewById(R.id.titlebar_other_backup);
		mTvTitle = page.findTextViewById(R.id.titlebar_other_title);
		mCbCollect = page.findCheckBoxById(R.id.titlebar_other_collect);
		mTvCityName = page.findTextViewById(R.id.title_other_cityname);
		mWeakRefActivity = new WeakReference<Activity>(page.getActivity());
		initialize(function);
	}

	private void initialize(int function) {
		// TODO Auto-generated method stub
		if (mTvTitle != null) {
			mIsValid = true;
			mLayout = (View) mTvTitle.getParent();
			mBtGoBack.setOnClickListener(this);
			mTvCityName.setOnClickListener(this);
			mTvCityName.setText(SuidingApp.getApp().getCityName());
			setFunction(function);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (mWeakRefActivity != null) {
			Activity activity = mWeakRefActivity.get();
			switch (v.getId()) {
			case R.id.titlebar_other_goback:
				if (activity != null) {
					activity.finish();
				}
				break;
			case R.id.title_cityname:
				activity.startActivity(new Intent(activity,
						FixedCityActivity.class));
				break;
			}
		}
	}

	public void setFunction(int function) {
		// TODO Auto-generated constructor stub
		switch (function) {
		default:
		case FUNCTION_NONE:
			mBtAdd.setVisibility(View.GONE);
			mBtFinish.setVisibility(View.GONE);
			mCbCollect.setVisibility(View.GONE);
			mBtBackup.setVisibility(View.GONE);
			mBtOK.setVisibility(View.GONE);
			mTvCityName.setVisibility(View.GONE);
			break;
		case FUNCTION_FINISH:
			mBtAdd.setVisibility(View.GONE);
			mBtFinish.setVisibility(View.VISIBLE);
			mCbCollect.setVisibility(View.GONE);
			mBtBackup.setVisibility(View.GONE);
			mBtOK.setVisibility(View.GONE);
			mTvCityName.setVisibility(View.GONE);
			break;
		case FUNCTION_ADD:
			mBtAdd.setVisibility(View.VISIBLE);
			mBtFinish.setVisibility(View.GONE);
			mCbCollect.setVisibility(View.GONE);
			mBtBackup.setVisibility(View.GONE);
			mBtOK.setVisibility(View.GONE);
			mTvCityName.setVisibility(View.GONE);
			break;
		case FUNCTION_COLLECT:
			mCbCollect.setVisibility(View.VISIBLE);
			mBtAdd.setVisibility(View.GONE);
			mBtFinish.setVisibility(View.GONE);
			mBtBackup.setVisibility(View.GONE);
			mBtOK.setVisibility(View.GONE);
			mTvCityName.setVisibility(View.GONE);
			break;
		case FUNCTION_OK:
			mBtOK.setVisibility(View.VISIBLE);
			mBtBackup.setVisibility(View.GONE);
			mCbCollect.setVisibility(View.GONE);
			mBtAdd.setVisibility(View.GONE);
			mBtFinish.setVisibility(View.GONE);
			mTvCityName.setVisibility(View.GONE);
			break;
		case FUNCTION_BACKUP:
			mBtBackup.setVisibility(View.VISIBLE);
			mBtOK.setVisibility(View.GONE);
			mCbCollect.setVisibility(View.GONE);
			mBtAdd.setVisibility(View.GONE);
			mBtFinish.setVisibility(View.GONE);
			mTvCityName.setVisibility(View.GONE);
		case FUNCTION_FIXED:
			mTvCityName.setVisibility(View.VISIBLE);
			mBtBackup.setVisibility(View.GONE);
			mBtOK.setVisibility(View.GONE);
			mCbCollect.setVisibility(View.GONE);
			mBtAdd.setVisibility(View.GONE);
			mBtFinish.setVisibility(View.GONE);
			break;

		}
	}

	public void setOnGoBackListener(OnClickListener listener) {
		// TODO Auto-generated constructor stub
		mBtGoBack.setOnClickListener(listener);
	}

	public void setOnFinishListener(OnClickListener listener) {
		// TODO Auto-generated constructor stub
		mBtFinish.setOnClickListener(listener);
	}

	public void setOnAddListener(OnClickListener listener) {
		// TODO Auto-generasted constructor stub
		mBtAdd.setOnClickListener(listener);
	}

	public void setOnOkListener(OnClickListener listener) {
		// TODO Auto-generasted constructor stub
		mBtOK.setOnClickListener(listener);
	}

	public void setOnBackupListener(OnClickListener listener) {
		// TODO Auto-generasted constructor stub
		mBtBackup.setOnClickListener(listener);
	}

	public void setOnCollectClickListener(OnClickListener listener) {
		// TODO Auto-generasted constructor stub
		mCbCollect.setOnClickListener(listener);
	}

	public void setOnCollectChangeListener(OnCheckedChangeListener listener) {
		// TODO Auto-generasted constructor stub
		mCbCollect.setOnCheckedChangeListener(listener);
	}

	public void setCollect(boolean collect) {
		mCbCollect.setChecked(collect);
	}

	public void setTitle(String description) {
		// TODO Auto-generated constructor stub
		mTvTitle.setText(description);
	}

	public void setTitle(int id) {
		// TODO Auto-generated constructor stub
		mTvTitle.setText(id);
	}

	public void setCityName(String cityname) {
		// TODO Auto-generated constructor stub
		mTvCityName.setText(cityname);
	}

	public View getLayout() {
		return mLayout;
	}

	public void hide() {
		// TODO Auto-generated constructor stub
		mLayout.setVisibility(View.GONE);
	}

	public void show() {
		// TODO Auto-generated constructor stub
		mLayout.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return mIsValid;
	}

}
