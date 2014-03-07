package com.suiding.layoutbind;

import android.view.View;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.constant.OrderStatus;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.Order;

public class OrderInfoModuleBase implements ILayout
{
	private View mLayout = null;
    private TextView mTvTitle = null;
    private TextView mTvNumber = null;
    private TextView mTvStatus = null;
    
    private boolean mIsValid = false;
    
    public OrderInfoModuleBase(Pageable page)
    {
        // TODO Auto-generated constructor stub
    	mTvTitle = page.findTextViewById(R.id.orderinfo_base_title);
        mTvNumber = page.findTextViewById(R.id.orderinfo_base_id);
        mTvStatus = page.findTextViewById(R.id.orderinfo_base_status);
        mIsValid = mTvTitle != null;
        if(mIsValid){
        	mLayout = (View) mTvTitle.getParent();
        	
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


    public void setOrder(Order order)
    {
        // TODO Auto-generated method stub
    	if(mIsValid && order != null){
    		mTvNumber.setText(order.OrderNumber);
    		mTvStatus.setText(OrderStatus.getStatusName(order.OrderStatus));
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
