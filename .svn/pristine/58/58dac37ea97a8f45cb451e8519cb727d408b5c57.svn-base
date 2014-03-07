package com.suiding.activity;

import com.suiding.application.SuidingApp;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.activity.framework.ActivityBase;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;


public class AboutActivity extends ActivityBase implements OnClickListener
{

    private TextView mTvVersion;
    private TextView mTvAgreement;
    private LinearLayout mLlServicePhone;
    private ModuleTitleOther mTitleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);
        mTvVersion = (TextView) findViewById(R.id.about_version);
        mTvAgreement = (TextView) findViewById(R.id.about_useragreement);
        mLlServicePhone = (LinearLayout) findViewById(R.id.about_servicephone);
        mTitleLayout = new ModuleTitleOther(this);
        
        mTvAgreement.setOnClickListener(this);
        mLlServicePhone.setOnClickListener(this);
        mTitleLayout.setOnGoBackListener(this);

        String format = getString(R.string.app_version);
        String version = SuidingApp.getVersion();
        mTvVersion.setText(String.format(format, version));
        mTitleLayout.setTitle(R.string.title_activity_aboutleso);
    }

    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        switch (v.getId())
        {
            case R.id.about_useragreement:
                break;
            case R.id.about_servicephone:
                break;
            case ModuleTitleOther.ID_GOBACK:
                this.finish();
                break;
        }
    }
}
