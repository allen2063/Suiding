package com.suiding.layoutbind;

import android.view.View;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.Coupon;
import com.suiding.model.Product;

public class ModuleReserveInfomatioin implements ILayout
{
    public View mLayout = null;
    public TextView mTvName = null;
    public TextView mTvPrice = null;
    public TextView mTvDiscount = null;
    
    private boolean mIsValid = false;
    
    public ModuleReserveInfomatioin(Pageable page)
    {
        // TODO Auto-generated constructor stub
        mTvName = page.findTextViewById(R.id.reserve_info_name);
        mTvPrice = page.findTextViewById(R.id.reserve_info_price);
        mTvDiscount = page.findTextViewById(R.id.reserve_info_discount);
        mIsValid = mTvName != null;
        if(mIsValid){
        	mLayout = (View) mTvName.getParent();
        	mTvDiscount.setVisibility(View.GONE);
        }
    }

    public void setName(String value)
    {
        // TODO Auto-generated constructor stub
        mTvName.setText(value);
    }

    public void setPrice(String value)
    {
        // TODO Auto-generated constructor stub
        mTvPrice.setText(value);
    }

    public void setDiscount(String value)
    {
        // TODO Auto-generated constructor stub
        mTvDiscount.setText(value);
    }
    
    
    public void hide()
    {
        // TODO Auto-generated constructor stub
        mLayout.setVisibility(View.GONE);
    }
    
    public void show()
    {
        // TODO Auto-generated constructor stub
        mLayout.setVisibility(View.VISIBLE);
    }

    public void setProduct(Product product)
    {
        // TODO Auto-generated method stub
        mTvName.setText(product.Name);
        mTvPrice.setText(String.format("%.0fԪ", product.NowPrice));
    }
	public void setCoupon(Coupon mCoupon) {
		// TODO Auto-generated method stub
		if(mCoupon != null){
			mTvDiscount.setText(mCoupon.Name);
			mTvDiscount.setVisibility(View.VISIBLE);
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
