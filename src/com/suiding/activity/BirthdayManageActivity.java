package com.suiding.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.fragment.IndexAttentioinFragment;
import com.suiding.layoutbind.ModuleTitleOther;

public class BirthdayManageActivity extends ActivityBase implements OnClickListener {

	private View mBirthRecord;// 直接添加
	private View mBirthRemind;// 从通讯录添加
	private View mBirthInvite;// 从好友添加

    private ModuleTitleOther mTitleLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_birthday_manage);

        mTitleLayout = new ModuleTitleOther(this);
        mTitleLayout.setTitle(R.string.title_activity_birthmanage);
        
        mBirthRecord = findViewById(R.id.birthmanage_record);
        mBirthRemind = findViewById(R.id.birthmanage_remind);
        mBirthInvite = findViewById(R.id.birthmanage_invite);
		
		mBirthRecord.setOnClickListener(this);
		mBirthRemind.setOnClickListener(this);
		mBirthInvite.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.birthmanage_record:
			intent.setClass(this, BirthdayRecordActivity.class);
			intent.putExtra("ACTION", IndexAttentioinFragment.BIRRECORD);
			startActivity(intent);
			break;
        case R.id.birthmanage_remind:
			intent.setClass(this, BirthdayRemindActivity.class);
			intent.putExtra("ACTION", IndexAttentioinFragment.BIRREMIND);
			startActivity(intent);
            break;
        case R.id.birthmanage_invite:
			intent.setClass(this, BirthdayInviteActivity.class);
			startActivity(intent);
        	break;
		}
	}

}
