package com.suiding.layoutbind;

import android.view.View;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.Consumer;

public class OrderInfoModuleUser implements ILayout {
	private View mLayout = null;
	private TextView mTvTitle = null;
	private TextView mTvName = null;
	private TextView mTvPhone = null;

	private boolean mIsValid = false;

	public OrderInfoModuleUser(Pageable page) {
		// TODO Auto-generated constructor stub
		mTvTitle = page.findTextViewById(R.id.orderinfo_user_title);
		mTvName = page.findTextViewById(R.id.orderinfo_user_name);
		mTvPhone = page.findTextViewById(R.id.orderinfo_user_phone);
		mIsValid = mTvTitle != null;
		if (mIsValid) {
			mLayout = (View) mTvTitle.getParent();

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
			mTvPhone.setText(phone);
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

	public void setUser(Consumer user) {
		// TODO Auto-generated method stub
		if (mIsValid && user != null) {
			mTvName.setText(user.Name);
			mTvPhone.setText(user.PhoneNo);
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
