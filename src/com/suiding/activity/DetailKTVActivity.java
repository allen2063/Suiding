package com.suiding.activity;

import android.os.Handler;
import android.view.View;


public class DetailKTVActivity extends DetailStoreActivity
{
	@Override
	protected void onCreated(View page) {
		// TODO Auto-generated method stub
		super.onCreated(page);
	}
    @Override
    protected DetailStoreTask getDetailTask(int task)
    {
        // TODO Auto-generated method stub
        return new DetailKTVTask(task);
    }
    
    private class DetailKTVTask extends DetailStoreTask
    {

        public DetailKTVTask(int task)
        {
            super(new Handler(DetailKTVActivity.this),task);
            // TODO Auto-generated constructor stub
        }
    }
}
