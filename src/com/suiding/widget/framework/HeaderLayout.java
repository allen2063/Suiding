package com.suiding.widget.framework;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.suiding.activity.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HeaderLayout extends FrameLayout 
{
    static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;

    private ImageView headerImage;
    private ProgressBar headerProgress;
    private TextView headerText;
    private TextView mUpdateText;
    private View mHeaderUnderLine2 = null;

    private String pullLabel;
    private String refreshingLabel;
    private String releaseLabel;

    private SimpleDateFormat mSimpleDateFormat;
    
    private Animation animation, animationreset;

    public HeaderLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.initailize(context);
        this.setLastUpdateTime(new Date());
    }
    
    public HeaderLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.initailize(context);
        this.initAttributeSet(context,attrs);
        this.setLastUpdateTime(new Date());
    }

    private void initAttributeSet(Context context, AttributeSet attrs)
    {
        // TODO Auto-generated method stub
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.PullToRefresh);
        ViewGroup.LayoutParams lp = mHeaderUnderLine2.getLayoutParams();
        lp.height = (int)array.getDimension(R.styleable.PullToRefresh_dividerHeight, 1);
        mHeaderUnderLine2.setLayoutParams(lp);
        int id = array.getResourceId(R.styleable.PullToRefresh_divider,Color.WHITE);
        if(id != Color.WHITE){
            mHeaderUnderLine2.setBackgroundResource(id);
        }
        array.recycle();
    }

    private void initailize(Context context)
    {
        mSimpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss",Locale.ENGLISH);
        
        View header = LayoutInflater.from(context).inflate(R.layout.refresh_list_header, this);
        headerText = (TextView) header.findViewById(R.id.refresh_list_header_text);
        mUpdateText = (TextView) header.findViewById(R.id.refresh_list_header_last_update);
        headerImage = (ImageView) header.findViewById(R.id.refresh_list_header_pull_down);
        headerProgress = (ProgressBar) header.findViewById(R.id.refresh_list_header_progressbar);
        mHeaderUnderLine2 = header.findViewById(R.id.refresh_list_header_underline2);
        
        final Interpolator interpolator = new LinearInterpolator();
        animation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        animation.setInterpolator(interpolator);
        animation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
        animation.setFillAfter(true);

        animationreset = new RotateAnimation(-180, 0, 
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animationreset.setInterpolator(interpolator);
        animationreset.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
        animationreset.setFillAfter(true);

        pullLabel = context.getString(R.string.regresh_header_pulldown);
        refreshingLabel = context.getString(R.string.regresh_footer_loading);
        releaseLabel = context.getString(R.string.regresh_header_release);

        headerImage.setImageResource(R.drawable.refresh_list_pull_down);
    }

    public void reset() {
        headerText.setText(pullLabel);
        headerImage.setVisibility(View.VISIBLE);
        headerProgress.setVisibility(View.GONE);
    }

    public void releaseToRefresh() {
        headerText.setText(releaseLabel);
        headerImage.clearAnimation();
        headerImage.startAnimation(animation);
    }

    public void setPullLabel(String pullLabel) {
        this.pullLabel = pullLabel;
    }

    public void refreshing() {
        headerText.setText(refreshingLabel);
        headerImage.clearAnimation();
        headerImage.setVisibility(View.INVISIBLE);
        headerProgress.setVisibility(View.VISIBLE);
    }

    public void setRefreshingLabel(String refreshingLabel) {
        this.refreshingLabel = refreshingLabel;
    }

    public void setReleaseLabel(String releaseLabel) {
        this.releaseLabel = releaseLabel;
    }

    public void pullToRefresh() {
        headerText.setText(pullLabel);
        headerImage.clearAnimation();
        headerImage.startAnimation(animationreset);
    }
    public void setTextColor(int color) {
        headerText.setTextColor(color);
    }
    public void setText(String text) {
        headerText.setText(text);
    }
    /**
     * 通知 ListView 设置最后刷新时间
     */
    public void setLastUpdateTime(Date tDate)
    {
        if(mUpdateText != null && mSimpleDateFormat != null){
            String UpdateText = getContext().getString(R.string.refresh_header_update);
            mUpdateText.setText(UpdateText+mSimpleDateFormat.format(tDate));
        }
    }
}
