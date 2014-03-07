package com.suiding.layoutbind;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.application.AddressService;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.StoreBase;
import com.suiding.util.CallPhoneUtil;
import com.suiding.util.DateFormatUtil;

public class ModuleStoreInfo implements ILayout, OnClickListener {
	private TextView mTvTitle = null;
	private TextView mTvHours = null;
	private TextView mTvTelephone = null;
	private TextView mTvHoliday = null;
	private TextView mTvSituatio = null;
	private TextView mTvAddress = null;
	private TextView mTvService = null;
	private ImageView mIvPhone = null;

	private View mLayout = null;

	private StoreBase mStore = null;
	private boolean mIsValid = false;

	private String mPhone = "";
	private Context mContext = null;

	public ModuleStoreInfo(Pageable page) {
		// TODO Auto-generated constructor stub
		mContext = page.getActivity();
		mTvTitle = page.findTextViewById(R.id.storeinfo_title);
		mTvHours = page.findTextViewById(R.id.storeinfo_businesshours);
		mTvHoliday = page.findTextViewById(R.id.storeinfo_holiday);
		mTvSituatio = page.findTextViewById(R.id.storeinfo_situation);
		mTvTelephone = page.findTextViewById(R.id.storeinfo_telephone);
		mTvAddress = page.findTextViewById(R.id.storeinfo_address);
		mTvService = page.findTextViewById(R.id.storeinfo_surroundings);
		mIvPhone = page.findImageViewById(R.id.storeinfo_phone);
		initialize();
	}

	public ModuleStoreInfo(Pageable page, StoreBase store) {
		// TODO Auto-generated constructor stub
		this(page);
		setStore(store);
	}

	public void setStore(StoreBase store) {
		// TODO Auto-generated method stub
		mStore = store;
		if (mIsValid && mStore != null) {
			String begin = DateFormatUtil.format("HH:mm", store.BeginBss);
			String end = DateFormatUtil.format("HH:mm", store.EndBss);
			mTvHours.setText(String.format("营业时间：%s - %s", begin, end));
			mTvHoliday.setText(String.format("休假信息：%s", mStore.StopMSG));
			mTvSituatio.setText(String.format("营业情况：%s", mStore.IsOpen ? "正在营业"
					: "暂停营业"));
			mTvService.setText(String.format("店面主页：%s", mStore.NetServer));
			mTvTelephone.setText(String.format("联系电话：%s", mStore.Telephone));
			if (store.Telephone != null && store.Telephone.length() > 0) {
				mPhone = store.Telephone.replace("-", "");
				mIvPhone.setEnabled(true);
				mIvPhone.setOnClickListener(this);
			} else {
				mIvPhone.setEnabled(false);
			}
			AddressService.bindAddress(store.Address, mTvAddress, "地址信息：%s");
		}
	}

	private void initialize() {
		// TODO Auto-generated method stub
		if (mTvTitle != null && (mLayout = (View) mTvTitle.getParent()) != null) {
			mIsValid = true;
			mLayout.setOnClickListener(this);
		}
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		if (isValid()) {
			mLayout.setVisibility(View.GONE);
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		if (isValid()) {
			mLayout.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public View getLayout() {
		// TODO Auto-generated method stub
		return mLayout;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return mIsValid;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.storeinfo_phone) {
			CallPhoneUtil.call(mContext,mPhone);
		} else if (mLayout == v) {

		}
	}

}
