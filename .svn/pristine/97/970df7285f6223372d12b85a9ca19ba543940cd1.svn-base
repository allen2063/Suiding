package com.suiding.activity;

import android.os.Handler;
import android.view.View;

import com.suiding.model.Hotel;


public class DetailHotelActivity extends DetailStoreActivity 
{
    private Hotel mHotel = null;

    @Override
	protected void onCreated(View page) {
		// TODO Auto-generated method stub
		super.onCreated(page);
		
		if(mStore instanceof Hotel)
		{
			mHotel = (Hotel) mStore;
	        mRbLevel.setRating(mHotel.Level);
	        mRbLevel.setVisibility(View.VISIBLE);
		}
	}

//    @Override
//    public void onProductClickListener(Product product)
//    {
//        // TODO Auto-generated method stub
//        ExtraUtil.putExtra(ReserveActivity.EXTRA_PRODUCT, product);
//        this.startActivity(new Intent(this, ReserveHotelActivity.class));
//    }

    @Override
    protected DetailStoreTask getDetailTask(int task)
    {
        // TODO Auto-generated method stub
        return new DetailHotelTask(task);
    }

    private class DetailHotelTask extends DetailStoreTask
    {
        public DetailHotelTask(int task)
        {
            super(new Handler(DetailHotelActivity.this),task);
            // TODO Auto-generated constructor stub
        }
    }

}
