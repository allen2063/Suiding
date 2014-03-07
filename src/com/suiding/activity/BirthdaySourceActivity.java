package com.suiding.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.layoutbind.ModuleTitleOther;

public class BirthdaySourceActivity extends ActivityBase implements OnClickListener {

	private View mStraightly;// 直接添加
	private View mFormContact;// 从通讯录添加
	private View mFormFriend;// 从好友添加

    private ModuleTitleOther mTitleLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_birthday_source);

        mTitleLayout = new ModuleTitleOther(this);
        mTitleLayout.setTitle(R.string.title_activity_addbirthsource);
        mTitleLayout.setOnGoBackListener(this);
        
        mStraightly = findViewById(R.id.bitthday_source_straight);
        mFormContact = findViewById(R.id.bitthday_source_contact);
        mFormFriend = findViewById(R.id.bitthday_source_friend);
		
		mStraightly.setOnClickListener(this);
		mFormContact.setOnClickListener(this);
		mFormFriend.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bitthday_source_straight:
			startActivity(new Intent(this, BirthdayAddActivity.class));
			this.finish();
			break;
        case ModuleTitleOther.ID_GOBACK:
            this.finish();
            break;
        case R.id.bitthday_source_contact:
        	//add birthday source from the contact
        	startActivity(new Intent(this,ListContactActivity.class));
        	this.finish();
        	break;
		}
	}

}
