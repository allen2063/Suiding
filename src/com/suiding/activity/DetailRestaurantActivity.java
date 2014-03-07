package com.suiding.activity;

import android.os.Handler;
import android.view.View;

import com.suiding.model.Restaurant;


public class DetailRestaurantActivity extends DetailStoreActivity
{
	private Restaurant mRestaurant = null;
    @Override
	protected void onCreated(View page) {
		// TODO Auto-generated method stub
		super.onCreated(page);
		
		if(mStore instanceof Restaurant)
		{
	        mRestaurant = (Restaurant) mStore;
	        mRbLevel.setRating(mRestaurant.Level);
	        mRbLevel.setVisibility(View.VISIBLE);
		}
	}

	@Override
    protected DetailStoreTask getDetailTask(int task)
    {
        // TODO Auto-generated method stub
        return new DetailRestaurantTask(task);
    }

    private class DetailRestaurantTask extends DetailStoreTask
    {
        public DetailRestaurantTask(int task)
        {
            super(new Handler(DetailRestaurantActivity.this),task);
            // TODO Auto-generated constructor stub
        }
    }

}
