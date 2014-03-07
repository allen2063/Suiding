package com.suiding.activity;

import android.os.Handler;
import android.view.View;

public class DetailStallActivity extends DetailStoreActivity {
	@Override
	protected void onCreated(View page) {
		// TODO Auto-generated method stub
		super.onCreated(page);
	}

	@Override
	protected DetailStoreTask getDetailTask(int task) {
		// TODO Auto-generated method stub
		return new DetailStallTask(task);
	}

	private class DetailStallTask extends DetailStoreTask {

		public DetailStallTask(int task) {
			super(new Handler(DetailStallActivity.this), task);
			// TODO Auto-generated constructor stub
		}
	}

}
