package com.suiding.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.suiding.activity.ListFavoriteActivity;
import com.suiding.activity.TwitterActivity;
import com.suiding.activity.BirthdayInviteActivity;
import com.suiding.activity.BirthdayRecordActivity;
import com.suiding.activity.BirthdayRemindActivity;
import com.suiding.activity.R;
import com.suiding.application.SuidingApp;
import com.suiding.application.SuidingApp.INotifyFixedCity;
import com.suiding.fragment.framework.FragmentBase;
import com.suiding.layoutbind.ModuleTitleMain;
import com.suiding.model.Area;

/**
 * 关注页面Fragment
 * @author SCWANG
 */
public class IndexAttentioinFragment extends FragmentBase implements 
	OnClickListener,INotifyFixedCity
{
    public static final String COLLECT = "收藏";
    public static final String BUSDISTRICT = "商圈";
    public static final String BIRINVITE = "生日邀请";
    public static final String BIRRECORD = "生日记录";
    public static final String BIRREMIND = "生日提提醒";

    RelativeLayout mCollect;//收藏
    RelativeLayout mBusDistrict;//商圈
    RelativeLayout mBirRecord;//生日记录
    RelativeLayout mBirRemind;//生日提醒
    RelativeLayout mBirInvite;//生日邀请

    TextView mCollectNumber;
    TextView mBusDistrictNumber;
    TextView mBirRecordNumber;
    TextView mBirRemindNumber;
    TextView mBirInviteNumber;

    private ModuleTitleMain mLayoutTitle = null;

	@Override
	protected final View onCreateView(LayoutInflater inflater, ViewGroup container) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.index_attention, container, false);
	}
	@Override
	protected final void onCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        mLayoutTitle = new ModuleTitleMain(this);
        
        mCollectNumber = findTextViewById(R.id.attention_collect_number);
        mBusDistrictNumber = findTextViewById(R.id.attention_budis_number);
        mBirRecordNumber = findTextViewById(R.id.attention_bir_record_number);
        mBirRemindNumber = findTextViewById(R.id.attention_bir_remind_number);
        mBirInviteNumber = findTextViewById(R.id.attention_bir_invite_number);

        mCollect = findRelativeLayoutById(R.id.attention_favorite);
        mBirRecord = findRelativeLayoutById(R.id.attention_bir_record);
        mBirRemind = findRelativeLayoutById(R.id.attention_bir_remind);
        mBirInvite = findRelativeLayoutById(R.id.attention_bir_invite);
        mBusDistrict = findRelativeLayoutById(R.id.attention_twitter);

        mCollect.setOnClickListener(this);
        mBusDistrict.setOnClickListener(this);
        mBirRecord.setOnClickListener(this);
        mBirRemind.setOnClickListener(this);
        mBirInvite.setOnClickListener(this);

        mBusDistrictNumber.setVisibility(View.INVISIBLE);
        mCollectNumber.setVisibility(View.INVISIBLE);
        mBirRecordNumber.setVisibility(View.INVISIBLE);
        mBirRemindNumber.setVisibility(View.INVISIBLE);
        mBirInviteNumber.setVisibility(View.INVISIBLE);
        
    }

    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        switch (v.getId())
        {
            case R.id.attention_favorite:
            {
                Intent intent = new Intent();
                intent.setClass(mActivity, ListFavoriteActivity.class);
                startActivity(intent);
            }
                break;
            case R.id.attention_twitter:
            {
                Intent intent = new Intent();
                intent.setClass(mActivity, TwitterActivity.class);
                startActivity(intent);
            }
                break;
//            case R.id.attention_active:
//            {
//                Intent intent = new Intent();
//                intent.setClass(mActivity, BirthdayRecordActivity.class);
//                intent.putExtra("ACTION", IndexAttentioinFragment.BIRRECORD);
//                startActivity(intent);
//                startActivity(new Intent(mActivity, AttentionCouponActivity.class));
//            }
//                break;
            case R.id.attention_bir_invite:
            {
                Intent intent = new Intent();
                intent.setClass(mActivity, BirthdayInviteActivity.class);
                startActivity(intent);
            }
                break;
            case R.id.attention_bir_record:
            {
                Intent intent = new Intent();
                intent.setClass(mActivity, BirthdayRecordActivity.class);
                intent.putExtra("ACTION", IndexAttentioinFragment.BIRRECORD);
                startActivity(intent);
            }
                break;
            case R.id.attention_bir_remind:
            {
                Intent intent = new Intent();
                intent.setClass(mActivity, BirthdayRemindActivity.class);
                intent.putExtra("ACTION", IndexAttentioinFragment.BIRREMIND);
                startActivity(intent);
            }
                break;
        }
    }

    @Override
	public void onFixedCityChanged(Area area, int status) {
		// TODO Auto-generated method stub
    	mLayoutTitle.setCityName(SuidingApp.getApp().getCityName());
	}
}