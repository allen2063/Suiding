package com.suiding.layoutbind;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suiding.activity.ListCouponActivity;
import com.suiding.activity.R;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.Coupon;
import com.suiding.model.StoreBase;
import com.suiding.util.ExtraUtil;

public class ModuleCouponBar implements ILayout, OnClickListener {
	private TextView mTvName = null;
	private TextView mTvCount = null;
	private LinearLayout mLayout = null;

	private List<Coupon> mltCoupon = new ArrayList<Coupon>();

	private Context mContext = null;

	private boolean mIsValid = false;

	public ModuleCouponBar(View view) {
		// TODO Auto-generated constructor stub
		mLayout = (LinearLayout) view.findViewById(R.id.module_coupon);
		mTvName = (TextView) view.findViewById(R.id.module_coupon_name);
		mTvCount = (TextView) view.findViewById(R.id.store_coupon_count);
		initialize();
	}

	public ModuleCouponBar(Activity activity) {
		// TODO Auto-generated constructor stub
		mLayout = (LinearLayout) activity.findViewById(R.id.module_coupon);
		mTvName = (TextView) activity.findViewById(R.id.module_coupon_name);
		mTvCount = (TextView) activity.findViewById(R.id.store_coupon_count);
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		mIsValid = mLayout != null;
		if (mIsValid) {
			mContext = mLayout.getContext();
			mLayout.setOnClickListener(this);
			mTvCount.setVisibility(View.GONE);
		}
	}

	public void setCoupon(Coupon coupon, int count) {
		// TODO Auto-generated method stub
		mltCoupon.clear();
		mltCoupon.add(coupon);
		mTvName.setText(coupon.Name);
		mTvCount.setText(String.format("(%dÌõ)", count));
		mTvCount.setVisibility(View.VISIBLE);
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
		if (mltCoupon.size() > 0) {
			Intent intent = new Intent();
			StoreBase store = mltCoupon.get(0).StoreBase;
			ExtraUtil.putExtra(ListCouponActivity.EXTRA_STORE, store);
			ExtraUtil.putExtra(ListCouponActivity.EXTRA_LIST, mltCoupon);
			intent.setClass(mContext, ListCouponActivity.class);
			mContext.startActivity(intent);
		}
	}

}
