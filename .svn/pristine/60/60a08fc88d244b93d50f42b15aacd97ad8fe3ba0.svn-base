package com.suiding.layoutbind;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.model.Product;

public class ModuleReservePrice
{
    public View mLayout = null;
    public TextView mTvTotal = null;
    public TextView mTvPrivilege = null;
    
    public ModuleReservePrice(Activity activity)
    {
        // TODO Auto-generated constructor stub
        mTvTotal = (TextView)activity.findViewById(R.id.reserve_price_total);
        mTvPrivilege = (TextView)activity.findViewById(R.id.reserve_price_privilege);
        mLayout = (View) mTvTotal.getParent();
    }

    public ModuleReservePrice(View view)
    {
        // TODO Auto-generated constructor stub
        mTvTotal = (TextView)view.findViewById(R.id.reserve_price_total);
        mTvPrivilege = (TextView)view.findViewById(R.id.reserve_price_privilege);
        mLayout = (View) mTvTotal.getParent();
    }

    public void setTotal(double value)
    {
        // TODO Auto-generated constructor stub
        mTvTotal.setText(String.format("%.0fԪ", value));
    }

    public void setPrivilege(double value)
    {
        // TODO Auto-generated constructor stub
    	mTvPrivilege.setText(String.format("%.0fԪ", value));
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

    public void setProduct(Product mProduct)
    {
        // TODO Auto-generated method stub
        
    }
}
