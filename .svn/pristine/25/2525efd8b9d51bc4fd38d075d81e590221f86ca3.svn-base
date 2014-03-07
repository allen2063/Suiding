
package com.suiding.layoutbind;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.suiding.activity.DetailStoreActivity;
import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.application.AddressService;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.StoreBase;
import com.suiding.util.CallPhoneUtil;
import com.suiding.util.ExtraUtil;

public class OrderInfoModuleStore implements ILayout, OnClickListener {
	private View mLayout = null;
	private TextView mTvTitle = null;
	private TextView mTvName = null;
	private TextView mTvPhone = null;
	private TextView mTvAddress = null;

	private boolean mIsValid = false;
	
	private String mPhone = "";
	private Context mContext = null;
	private StoreBase mStore = null;

	public OrderInfoModuleStore(Pageable page) {
		// TODO Auto-generated constructor stub
		mContext = page.getActivity();
		mTvTitle = page.findTextViewById(R.id.orderinfo_store_title);
		mTvName = page.findTextViewById(R.id.orderinfo_store_name);
		mTvPhone = page.findTextViewById(R.id.orderinfo_store_telephone);
		mTvAddress = page.findTextViewById(R.id.orderinfo_store_address);
		mIsValid = mTvTitle != null;
		if (mIsValid) {
			mLayout = (View) mTvTitle.getParent();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == mLayout){
			Intent intent = new Intent();
			intent.setClass(mContext, DetailStoreActivity.class);
			ExtraUtil.putExtra(DetailStoreActivity.EXTRA_DETAIL, mStore);
			mContext.startActivity(intent);
		}else if(v == mTvPhone){
			CallPhoneUtil.call(mContext, mPhone);
		}
	}
	
	public void setName(String name) {
		// TODO Auto-generated constructor stub
		if (mIsValid) {
			mTvName.setText(name);
		}
	}

	public void setPhone(String phone) {
		// TODO Auto-generated constructor stub
		if (mIsValid) {
			if (phone != null && phone.length() > 0) {
				mPhone = phone.replace("-", "");
				mTvPhone.setText(phone);
				mTvPhone.setOnClickListener(this);
			}
		}
	}

	public void hide() {
		// TODO Auto-generated constructor stub
		if (mIsValid) {
			mLayout.setVisibility(View.GONE);
		}
	}

	public void show() {
		// TODO Auto-generated constructor stub
		if (mIsValid) {
			mLayout.setVisibility(View.VISIBLE);
		}
	}

	public void setStoreBase(StoreBase store) {
		// TODO Auto-generated method stub
		if (mIsValid && store != null) {
			mStore = store;
			setName(store.Name);
			setPhone(store.Telephone);
			mLayout.setOnClickListener(this);
			AddressService.bindAddress(store.Address, mTvAddress);
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
}
