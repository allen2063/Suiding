package com.suiding.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.suiding.activity.R;
import com.suiding.util.ScrollBarUtil;
import com.suiding.widget.framework.PullToRefreshBase;


public class RefreshListView extends PullToRefreshBase<ListView>
{
    private ListAdapter mAdapter = null;
    private boolean isNeedFooter = false;

    public RefreshListView(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public RefreshListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    
    public final Object getData(int positon)
    {
        if(mAdapter != null)
        {
            return mAdapter.getItem(positon - mTargetView.getHeaderViewsCount());
        }
        return null;
    }

    public final void setAdapter(ListAdapter adapter)
    {
        mTargetView.setAdapter(mAdapter = adapter);
    }

    public final void setOnItemClickListener(OnItemClickListener listener)
    {
        mTargetView.setOnItemClickListener(listener);
    }

    public final void setOnItemLongClickListener(OnItemLongClickListener listener)
    {
        mTargetView.setOnItemLongClickListener(listener);
    }

    @Override
    protected final ListView createRefreshableView(Context context, AttributeSet attrs)
    {
        // TODO Auto-generated method stub
        ListView listview = new ListView(context);
        View view = new View(context);
        listview.addHeaderView(view, null, false);
        listview.addFooterView(view, null, false);
        listview.setHeaderDividersEnabled(false);
        listview.setFooterDividersEnabled(true);
        
        //解决listview在拖动的时候背景图片消失变成黑色背景
        listview.setCacheColorHint(0);
        listview.setScrollingCacheEnabled(false);
        //解决listview的上边和下边有黑色的阴影
        listview.setFadingEdgeLength(0);
        // TODO Auto-generated method stub
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.PullToRefresh);
        listview.setCacheColorHint(array.getInteger(R.styleable.PullToRefresh_cacheColorHint,0));
        listview.setDividerHeight((int)array.getDimension(R.styleable.PullToRefresh_dividerHeight, 1));
        listview.setDivider(array.getDrawable(R.styleable.PullToRefresh_divider));
        array.recycle();
        
        ScrollBarUtil.bindScrollBar(listview, R.drawable.shape_scrollbar);
        
        return listview;
    }

    @Override
    protected final boolean isReadyForPullDown()
    {
        // TODO Auto-generated method stub
        //targetview.getOverScrollMode();
        return mTargetView.getFirstVisiblePosition() == 0
                && mTargetView.getScrollY() <= 0;
    }

    @Override
    protected final boolean isReadyForPullUp()
    {
        // TODO Auto-generated method stub
        if(isNeedFooter){
            int index = mTargetView.getLastVisiblePosition();
            int postion = mTargetView.getCount() - 1;
            return index == postion;
        }
        return false;
        
    }

    public final void addFooterView()
    {
        // TODO Auto-generated method stub
        isNeedFooter = true;
    }

    public final void removeFooterView()
    {
        // TODO Auto-generated method stub
        onRefreshComplete();
        isNeedFooter = false;
    }
    /**
     * Returns the number of header views in the list. 
     * Header views are special views at the top of the list that should not 
     * be recycled during a layout.
     * @return
     */
    public final int getHeaderViewsCount()
    {
        // TODO Auto-generated method stub
        return mTargetView.getHeaderViewsCount();
    }

	public final int getIndex(int index) {
		// TODO Auto-generated method stub
		return index - 1;
	}
    
}
