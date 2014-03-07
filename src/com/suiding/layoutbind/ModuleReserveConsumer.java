package com.suiding.layoutbind;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.Consumer;
import com.suiding.model.Order;

public class ModuleReserveConsumer implements ILayout, TextWatcher {
	
	private View mLayout = null;
	private TextView mTvName = null;
	private TextView mTvPhone = null;
	private EditText mEtBack = null;
	
	private Order mOrder = null;

	private boolean mIsValid = false;

	public ModuleReserveConsumer(Pageable page) {
		// TODO Auto-generated constructor stub
		mTvName = page.findTextViewById(R.id.reserve_consumer_name);
		mTvPhone = page.findTextViewById(R.id.reserve_consumer_phone);
		mEtBack = page.findEditTextById(R.id.reserve_consumer_back);
		mIsValid = mTvName != null;
		if (mIsValid) {
			mLayout = (View) mTvName.getParent();

		}
	}

	public void setName(String name) {
		// TODO Auto-generated constructor stub
		mTvName.setText(name);
	}

	public void setPhone(String phone) {
		// TODO Auto-generated constructor stub
		mTvPhone.setText(phone);
	}

	public String getPhone() {
		// TODO Auto-generated constructor stub
		return mEtBack.getText().toString();
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
		mOrder.Phone = mEtBack.getText().toString();
	}
	
	public void setOrder(Order order){
		if (mIsValid && order != null) {
			mOrder = order;
			mEtBack.addTextChangedListener(this);
		}
	}

	public void setConsumer(Consumer loginUser) {
		// TODO Auto-generated method stub
		if (mIsValid) {
			if (loginUser == null) {
				setName("(Î´µÇÂ¼)");
				setPhone("(Î´µÇÂ¼)");
			} else {
				if (loginUser.Name != null
						&& loginUser.Name.trim().length() > 0) {
					setName(loginUser.Name);
				} else {
					setName("Î´ÈÏÖ¤");
				}
				if (loginUser.PhoneNo != null
						&& loginUser.PhoneNo.trim().length() > 0) {
					setPhone(loginUser.PhoneNo);
				} else {
					setPhone("Î´°ó¶¨");
				}
			}
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
