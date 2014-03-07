package com.suiding.layoutbind;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.suiding.activity.FixedCityActivity;
import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.application.SuidingApp;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.util.ExtraUtil;


public class ModuleTitleMain implements OnClickListener,ILayout
{
    private static final int ID_CITYNAME = R.id.title_cityname;

    private View mLayout = null;
    private Boolean mIsValid = false;
    private TextView mTvAppName = null;
    private TextView mTvCityName = null;
    private Activity mActivity = null;

    public ModuleTitleMain(Pageable page)
    {
        // TODO Auto-generated constructor stub
    	mActivity = page.getActivity();
    	mTvAppName = page.findTextViewById(R.id.title_appname);
        mTvCityName = page.findTextViewById(R.id.title_cityname);
        initialize();
    }

    private void initialize() {
		// TODO Auto-generated method stub
    	if(mTvCityName != null){
            mIsValid = true;
    		mLayout = (View) mTvCityName.getParent();
            mTvCityName.setOnClickListener(this);
            mTvCityName.setText(SuidingApp.getApp().getCityName());
    	}
	}

    public void setTitle(String description)
    {
        // TODO Auto-generated constructor stub
    	mTvAppName.setText(description);
    }

    public void setTitle(int id)
    {
        // TODO Auto-generated constructor stub
    	mTvAppName.setText(id);
    }

    public void setCityName(String cityname)
    {
        // TODO Auto-generated constructor stub
    	mTvCityName.setText(cityname);
    }

    public View getLayout()
    {
        return mLayout;
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == ID_CITYNAME && mActivity != null){
			ExtraUtil.putExtra(FixedCityActivity.EXTRA_NEEDFIXED, true);
			mActivity.startActivity(new Intent(mActivity,FixedCityActivity.class));
		}
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return mIsValid;
	}
    
    
}
