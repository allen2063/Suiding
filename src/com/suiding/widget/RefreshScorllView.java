package com.suiding.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.suiding.widget.framework.PullToRefreshBase;


public class RefreshScorllView extends PullToRefreshBase<ScrollView>
{
    public RefreshScorllView(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public RefreshScorllView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    

    @Override
    protected final ScrollView createRefreshableView(Context context, AttributeSet attrs)
    {
        // TODO Auto-generated method stub
    	ScrollView scrollview = new ScrollView(context, attrs);
//        ScrollBarUtil.bindScrollBar(scrollview, R.drawable.shape_scrollbar);
        //���scrollview���ϱߺ��±��к�ɫ����Ӱ
    	scrollview.setFadingEdgeLength(0);
        return scrollview;
    }

    @Override
    protected final boolean isReadyForPullDown()
    {
        // TODO Auto-generated method stub
        //targetview.getOverScrollMode();
        return mTargetView.getScrollY() <= 0;
    }

    @Override
    protected final boolean isReadyForPullUp()
    {
        // TODO Auto-generated method stub
        return false;
    }

    public final void addChildToScrollView(View child)
    {
        mTargetView.addView(child);
    }
//    @Override
//    public final void addView(View child, int width, int height)
//    {
//        // TODO Auto-generated method stub
//        if(!isInternalAddView(child)){
//            mTargetView.addView(child, width, height);
//        }else{
////            super.addView(child, width, height);
//        }
//    }
//
//    @Override
//    public final void addView(View child, int index,
//            android.view.ViewGroup.LayoutParams params)
//    {
//        // TODO Auto-generated method stub
//        if(!isInternalAddView(child)){
//            mTargetView.addView(child, index, params);
//        }else{
////            super.addView(child, index, params);
//        }
//    }
//
//    @Override
//    public final void addView(View child, int index)
//    {
//        // TODO Auto-generated method stub
//        if(!isInternalAddView(child)){
//            mTargetView.addView(child, index);
//        }else{
////            super.addView(child, index);
//        }
//    }
//
//    @Override
//    public final void addView(View child, android.view.ViewGroup.LayoutParams params)
//    {
//        // TODO Auto-generated method stub
//        if(!isInternalAddView(child)){
//            mTargetView.addView(child, params);
//        }else{
////            super.addView(child, params);
//        }
//    }
//
//    @Override
//    public final void addView(View child)
//    {
//        // TODO Auto-generated method stub
//        if(!isInternalAddView(child)){
//            mTargetView.addView(child);
//        }else{
////            super.addView(child);
//        }
//    }
//
//    private boolean isInternalAddView(View view)
//    {
//        // TODO Auto-generated method stub
//        return view == mTargetView ||view == mHeaderLayout ||view == mFooterLayout ;
//    }

}
