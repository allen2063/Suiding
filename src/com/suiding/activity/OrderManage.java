package com.suiding.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.layoutbind.ModuleTitleOther;

public class OrderManage extends ActivityBase implements OnClickListener {

	private ModuleTitleOther mLayoutTitle;
	private View mAleady;//已确定
	private View mOrdered;//已预定
	private View mCanceled;//已取消

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_ordermanage);

		mLayoutTitle = new ModuleTitleOther(this);
		mLayoutTitle.setTitle(R.string.title_activity_ordermanage);
		mLayoutTitle.setOnGoBackListener(this);

		mAleady=findViewById(R.id.order_confirmed);
		mOrdered=findViewById(R.id.order_ordered);
		mCanceled=findViewById(R.id.order_canceled);

		mAleady.setOnClickListener(this);
		mOrdered.setOnClickListener(this);
		mCanceled.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case ModuleTitleOther.ID_GOBACK:
			this.finish();
			break;
			
		}
	}

}
