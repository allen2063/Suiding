package com.suiding.layoutbind;

import android.view.View;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.Order;

public class OrderInfoModulePrice implements ILayout {
	private View mLayout = null;
	private TextView mTvPrice = null;

	private boolean mIsValid = false;

	public OrderInfoModulePrice(Pageable page) {
		// TODO Auto-generated constructor stub
		mTvPrice = page.findTextViewById(R.id.orderinfo_price_orderprice);
		mIsValid = mTvPrice != null;
		if (mIsValid) {
			mLayout = (View) mTvPrice.getParent();

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

	public void setOrder(Order order) {
		// TODO Auto-generated method stub
		if (mIsValid && order != null) {
			mTvPrice.setText("гд"+(int)order.NowTotalPrice);
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
