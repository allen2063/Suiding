package com.suiding.widget;

import java.util.ArrayList;
import java.util.List;

import com.suiding.activity.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

/**
 * 字母表定位工具条
 * @author hyang
 */
public class SpellLetterView extends View
{
    private int choose = -1;
    private Paint paint = new Paint();
    private boolean showBkg = false;
    private OnTouchingLetterChangedListener mListener;
    //用于悬浮窗的初始化
    private WindowManager mWmManager;
    private TextView mTvOverLayout;
    
    private Context mContext;
    
    private List<String> mltData = new ArrayList<String>();

    public void setData(List<String> ltdata)
    {
        this.mltData = ltdata;
        this.invalidate();
    }

    public SpellLetterView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.mContext=context;
        initializedTextOverLayout();
    }

    public SpellLetterView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext=context;
        initializedTextOverLayout();
    }

    public SpellLetterView(Context context)
    {
        super(context);
        this.mContext=context;
        initializedTextOverLayout();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        
        if (showBkg) {
            canvas.drawColor(Color.parseColor("#10000000"));
        }

        if(mltData.size() > 0)
        {
            int height = getHeight();
            int width = getWidth();
            int singleHeight = height / mltData.size();
            for (int i = 0; i < mltData.size(); i++) {
                paint.setColor(Color.parseColor("#FF33CCFF"));
                paint.setTypeface(Typeface.DEFAULT);
                paint.setTextSize(9*width/10);
                paint.setAntiAlias(true);
                if (i == choose) {
                    paint.setColor(Color.parseColor("#3399ff"));
                    paint.setFakeBoldText(true);
                }
                float xPos = width / 2 - paint.measureText(mltData.get(i)) / 2;
                float yPos = singleHeight * i + singleHeight;
                canvas.drawText(mltData.get(i), xPos, yPos, paint);
                paint.reset();
            }
        }
    }
    
    @Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
//    	mWmManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//    	mWmManager.removeView(mTvOverLayout);
		super.onDetachedFromWindow();
	}

    
	/**
     * 滚到悬浮字母
     */
    private void initializedTextOverLayout()
    {
        // 初始化首字母悬浮提示框
        mTvOverLayout = (TextView) LayoutInflater.from(mContext).inflate(
                R.layout.fixedcity_popchar, null);
        mTvOverLayout.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        mWmManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mWmManager.addView(mTvOverLayout, lp);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = mListener;
        final int c = (int) (y / getHeight() * mltData.size());    //字母位置

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                showBkg = true;
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c <= mltData.size()) { 
                        listener.onTouchingLetterChanged(mltData.get(c),action);
                        choose = c;                  //处理重复
                        invalidate();
                    }
                }

                mTvOverLayout.setVisibility(View.VISIBLE);
                mTvOverLayout.setText(mltData.get(choose));
                break;
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != c && listener != null) {
                    if (c > 0 && c <= mltData.size()) {  
                        listener.onTouchingLetterChanged(mltData.get(c),action);
                        choose = c;
                        invalidate();
                    }
                }
                mTvOverLayout.setVisibility(View.VISIBLE);
                mTvOverLayout.setText(mltData.get(choose));
                break;
            case MotionEvent.ACTION_UP:
                showBkg = false;
                choose = -1;
                listener.onTouchingLetterChanged("",action);                
                invalidate();

                mTvOverLayout.setVisibility(View.INVISIBLE);
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return super.onTouchEvent(event);
    }

    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener)
    {
        this.mListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener
    {
        public void onTouchingLetterChanged(String ch,int action);
    }

}