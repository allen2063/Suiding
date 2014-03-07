package com.suiding.layoutbind;

import android.view.View;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.Coupon;
import com.suiding.util.DateFormatUtil;


public class ModuleCouponPanel implements ILayout
{
	private View mLayout = null;
	private TextView mTvDate = null;
    private TextView mTvName = null;
    private TextView mTvContent = null;

    private boolean mIsValid = false;

    public ModuleCouponPanel(Pageable page)
    {
        // TODO Auto-generated constructor stub
    	mTvDate = page.findTextViewById(R.id.coupon_date);
        mTvName = page.findTextViewById(R.id.coupon_name);
        mTvContent = page.findTextViewById(R.id.coupon_content);
        initialize();
    }

    private void initialize() {
		// TODO Auto-generated method stub
        mIsValid = mTvContent != null;
        if(mIsValid){
        	mLayout = (View)mTvContent.getParent();
        }
	}

    public void setCoupon(Coupon coupon)
    {
        // TODO Auto-generated method stub
        if(coupon != null && mIsValid){
        	mTvName.setText(coupon.Name);
        	mTvContent.setText(coupon.Content);
        	String sdate = DateFormatUtil.formatDate(coupon.BegDate);
        	String edate = DateFormatUtil.formatDate(coupon.EndDate);
        	mTvDate.setText(sdate+" - "+ edate);
        }
    }

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		if(isValid()){
			mLayout.setVisibility(View.GONE);
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		if(isValid()){
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

}
