package com.suiding.layoutbind;

import java.util.Date;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.widget.RefreshListView;
import com.suiding.widget.framework.PullToRefreshBase.OnRefreshListener;

public class ModuleListView implements ILayout {

	public static final int LISTVIEW = 0;
	public static final int PRIGRESS = 1;
	public static final int NULLDATA = 2;
	
	private View mLayout = null;
	private boolean mIsValid = true;

	protected ModuleNodata mNodata;
	protected FrameSelector mSelector;
	protected ModuleProgress mProgress;
	protected RefreshListView mListView = null;

	public ModuleListView(Pageable page) {
		// TODO Auto-generated constructor stub
		// 控件初始化
		mNodata = new ModuleNodata(page);
		mSelector = new FrameSelector(page);
		mProgress = new ModuleProgress(page);
		mListView = (RefreshListView)page.findViewById(R.id.module_listview);
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		if (mListView != null && mSelector.isValid() 
				&& mSelector.isValid() && mProgress.isValid()) {
			mIsValid = true;
			mLayout = (View)mListView.getParent();
		}
	}

	public ModuleNodata getNoData() {
		// TODO Auto-generated method stub
		return mNodata;
	}
	
	public final View getLayout() {
		return mLayout;
	}

	public final void hide() {
		// TODO Auto-generated constructor stub
		if (mLayout.getVisibility() == View.VISIBLE) {
			mLayout.setVisibility(View.GONE);
		}
	}

	public final void show() {
		// TODO Auto-generated constructor stub
		if (mLayout.getVisibility() != View.VISIBLE) {
			mLayout.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return mIsValid;
	}
	
    public final void addFooterView()
    {
        // TODO Auto-generated method stub
		if(mIsValid){
			mListView.addFooterView();
		}
    }

    public final void removeFooterView()
    {
        // TODO Auto-generated method stub
		if(mIsValid){
			mListView.removeFooterView();
		}
    }

    public final int getHeaderViewsCount()
    {
        // TODO Auto-generated method stub
		if(mIsValid){
			return mListView.getHeaderViewsCount();
		}
		return 0;
    }

	public final int getIndex(int index) {
		// TODO Auto-generated method stub
		if(mIsValid){
			return mListView.getIndex(index);
		}
		return 0;
	}
    public final Object getData(int positon)
    {
		if(mIsValid){
			return mListView.getData(positon);
		}
		return null;
    }
    /**
     * 提交刷新完成 更新时间
     */
    public final void finishRefresh()
    {
        // TODO Auto-generated method stub
		if(mIsValid){
			mListView.finishRefresh();
		}
    }

    /**
     * 提交刷新完成 但是失败 不更新时间
     */
    public final void finishRefreshFail()
    {
        // TODO Auto-generated method stub
		if(mIsValid){
			mListView.finishRefreshFail();
		}
    }
    
    public final void finishLoadMore()
    {
        // TODO Auto-generated method stub
		if(mIsValid){
			mListView.finishLoadMore();
		}
    }

    public final void setLastUpdateTime(Date date)
    {
        // TODO Auto-generated method stub
		if(mIsValid){
			mListView.setLastUpdateTime(date);
		}
    }

    public final void setPullToRefreshEnabled(boolean enable) {
		if(mIsValid){
			mListView.setPullToRefreshEnabled(enable);
		}
    }
    
    public final ListView getRefreshableView() {
		if(mIsValid){
			return mListView.getRefreshableView();
		}
		return null;
    }
    public final boolean isRefreshing() {
		if(mIsValid){
			return mListView.isRefreshing();
		}
		return false;
    }
    
	public final void setAdapter(ListAdapter adapter) {
		if(mIsValid){
			mListView.setAdapter(adapter);
		}
	}

	public final void setOnRefreshListener(OnRefreshListener listener) {
		if(mIsValid){
			mListView.setOnRefreshListener(listener);
		}
	}

	public final void setOnItemClickListener(OnItemClickListener listener) {
		if(mIsValid){
			mListView.setOnItemClickListener(listener);
		}
	}

	public final void setOnItemLongClickListener(
			OnItemLongClickListener listener) {
		if(mIsValid){
			mListView.setOnItemLongClickListener(listener);
		}
	}
	
	public final void setOnNodataRefreshListener(OnClickListener listener) {
		// TODO Auto-generated constructor stub
		mNodata.setOnRefreshListener(listener);
	}


	public void setNoDataText(String description) {
		// TODO Auto-generated constructor stub
		mNodata.setDescription(description);
	}

	public void setNoDataText(int id) {
		// TODO Auto-generated constructor stub
		mNodata.setDescription(id);
	}

	public void setNoDataButtonText(String text) {
		// TODO Auto-generated constructor stub
		mNodata.setButtonText(text);
	}

	public void setNoDataButtonText(int id) {
		// TODO Auto-generated constructor stub
		mNodata.setButtonText(id);
	}
	
    public final boolean SelectFrame(int status)
    {
    	switch (status) {
		case LISTVIEW:
			return mSelector.SelectFrame(mListView);
		case PRIGRESS:
			return mSelector.SelectFrame(mProgress.getLayout());
		case NULLDATA:
			return mSelector.SelectFrame(mNodata.getLayout());
		}
    	return false;
    }

}
