package com.suiding.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.application.AppExceptionHandler;
import com.suiding.application.SuidingApp;
import com.suiding.util.XmlCacheUtil;


public class WelcomeActivity extends ActivityBase implements Callback
{
    private TextView mTvAppName = null;
    private ImageView mIvEnjoy = null;

    //统计值
    private int mCount = 0;
    //定时器
    private Handler mHandler = null;
    //定时器间隔(200毫秒)
    private int mDuration = 200;
    //标记是否已经启动前台
    private boolean mIsStarted = false;
    /**
     * 欢迎界面中横着的颜色条ID
     * 
     */
    private int[] mImgIds = new int[]
    { R.id.welcome_block_1, R.id.welcome_block_2, R.id.welcome_block_3,
            R.id.welcome_block_4, R.id.welcome_block_5, R.id.welcome_block_6, };
    private View[] mIvColorBlocks = new View[mImgIds.length];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);
        
        //通知APP程序启动，并初始化
        SuidingApp.getApp().initialize(this);
        
        mIvEnjoy = (ImageView) findViewById(R.id.welcome_enjoy);
        mTvAppName = (TextView) findViewById(R.id.welcome_app_name);

        for (int i = 0; i < mImgIds.length; i++)
        {
            mIvColorBlocks[i] = findViewById(mImgIds[i]);
        }

        
        int version = XmlCacheUtil.getInt(WelcomeTrendsActivity.HAVERUN_VERSION, -1);
        if(version == SuidingApp.getVersionCode()){
            AnimationSet animationSet = new AnimationSet(true);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
            alphaAnimation.setDuration(1800);
            animationSet.addAnimation(alphaAnimation);
            mTvAppName.setAnimation(animationSet);
            mIvEnjoy.setAnimation(animationSet);
            mHandler = new Handler(SuidingApp.getLooper(), this);
            mHandler.sendMessageDelayed(Message.obtain(), mDuration);
        }else{
        	startActivity(new Intent(this,WelcomeTrendsActivity.class));
        	finish();
        }
    }

    @Override
    public boolean handleMessage(Message msg)
    {
        // TODO Auto-generated method stub
    	mCount++;
    	if(mCount < 7  && mIsStarted == false){
            mIvColorBlocks[mCount - 1].setVisibility(View.VISIBLE);
            if (mCount == 6)
            {
                finish();
                mIsStarted = true;
                try {
                    SuidingApp.getApp().startForeground(this);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();//handled
					AppExceptionHandler.handler(e, "欢迎页面，启动前台失败");
				}
            }
            else
            {
                mHandler.sendMessageDelayed(Message.obtain(), mDuration);
            }
    	}
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            this.finish();
            SuidingApp.getApp().startForeground(this);
            this.mIsStarted = true;
        }
        return true;
    }

}
