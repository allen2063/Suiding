package com.suiding.activity;

import android.os.Handler;
import android.view.View;

public class DetailClubActivity extends DetailStoreActivity {

	
	@Override
	protected void onCreated(View page) {
		// TODO Auto-generated method stub
		super.onCreated(page);
	}

	@Override
	protected DetailStoreTask getDetailTask(int task) {
		// TODO Auto-generated method stub
		return new DetailClubTask(task);
	}

	private class DetailClubTask extends DetailStoreTask {

		public DetailClubTask(int task) {
			super(new Handler(DetailClubActivity.this), task);
			// TODO Auto-generated constructor stub
		}
	}
}
