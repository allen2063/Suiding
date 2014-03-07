package com.suiding.fragment.framework;

import java.util.List;

import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

import com.suiding.activity.R;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.bean.Filter;
import com.suiding.layoutbind.FrameSelector;
import com.suiding.layoutbind.ModuleFilter;
import com.suiding.layoutbind.ModuleFilter.IFilter;
import com.suiding.layoutbind.ModuleNodata;
import com.suiding.layoutbind.ModuleProgress;
import com.suiding.layoutbind.ModuleTitleMain;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.thread.framework.ListTask;
import com.suiding.thread.framework.ListViewTask;
import com.suiding.thread.framework.TaskBase;
import com.suiding.widget.RefreshListView;
import com.suiding.widget.framework.PullToRefreshBase.OnRefreshListener;

public abstract class ListViewFragment extends FragmentBase implements
		OnClickListener, OnItemClickListener, Callback, OnRefreshListener,
		OnItemLongClickListener, IFilter {

	protected ModuleFilter mModuleFilter = null;
	protected FrameSelector mFrameSelector;
	protected ModuleNodata mLayoutNodata;
	protected ModuleProgress mLayoutProgress;
	protected ModuleTitleOther mTitleOther;
	protected ModuleTitleMain mTitleMain;
	protected RefreshListView mListView = null;

	// 标识是否分页
	protected boolean mIsPaging = false;

	protected List<Object> mltData = null;
	protected ListAdapterBase mAdapter = null;

	// 正在加载数据标识
	protected Boolean mIsLoading = false;

	/**
	 * 为了防止子类直接实现 Activity.onCreate 把它设置final 为了子类能得到onCreate 消息通知 在
	 * Activity.onCreate 中调用 onCreate 子类可以在 onCreate 中执行其它特殊的初始化操作 如修改标题
	 */
	protected void onCreate() {
	}

	/**
	 * 当服务器加载数据为空的时候调用 处理信息提示 如果不重写这个函数 默认的提示是 抱歉，附近无相关数据
	 */
	protected void onNodata(ModuleNodata nodata) {
		// 设置空数据页面
		nodata.setDescription(ModuleNodata.TEXT_NODATA);
		nodata.setButtonText(ModuleNodata.TEXT_TOREFRESH);
	}

	protected abstract ListViewTask getTask(int task);

	protected abstract ListAdapterBase getAdapter(List<Object> ltdata);


	@Override
	protected final View onCreateView(LayoutInflater inflater, ViewGroup container) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.layout_listview, container, false);
	}
	@Override
	protected final void onCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		// 控件初始化
		mModuleFilter = new ModuleFilter(this, this);
		mTitleMain = new ModuleTitleMain(this);
		mTitleOther = new ModuleTitleOther(this);
		mLayoutNodata = new ModuleNodata(this);
		mFrameSelector = new FrameSelector(this);
		mLayoutProgress = new ModuleProgress(this);
		mListView = (RefreshListView) mRootView
				.findViewById(R.id.module_listview);
		// 事件绑定
		mListView.setOnRefreshListener(this);
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this);
		mTitleOther.setOnGoBackListener(this);
		mLayoutNodata.setOnRefreshListener(this);

		// 执行子类创建
		this.onCreate();

		// 显示正在加载数据
		if (mltData == null) {
			mFrameSelector.SelectFrame(mLayoutProgress.getLayout());
		} else if (mltData.size() == 0) {
			if (mIsLoading == true) {
				mFrameSelector.SelectFrame(mLayoutProgress.getLayout());
			} else {
				this.onNodata(mLayoutNodata);
				mFrameSelector.SelectFrame(mLayoutNodata.getLayout());
			}
		} else {
			mListView.setAdapter(mAdapter);
			mFrameSelector.SelectFrame(mListView);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> absview, View view, int index,
			long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> absview, View view,
			int index, long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onFilter(int type, int index, Filter filter) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 第一次切换到本页面
	 */
	@Override
	protected final void onFirstSwitchOver() {
		// TODO Auto-generated method stub
		postTask(getTask(TaskBase.TASK_LOAD));
	}

	@Override
	public final boolean onMore() {
		// TODO Auto-generated method stub
		// 抛送更多任务
		ListViewTask task = getTask(ListTask.TASK_MORE);
		task.mFirstResult = mltData.size();
		postTask(task);
		return true;
	}

	@Override
	public final boolean onRefresh() {
		// TODO Auto-generated method stub
		// 抛送刷新任务
		postTask(getTask(ListTask.TASK_REFRESH));
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case ModuleNodata.ID_BUTTON:
		case ModuleNodata.TEXT_TOREFRESH:
			// 设置正在加载页面
			mFrameSelector.SelectFrame(mLayoutProgress.getLayout());
			// 抛送刷新任务
			postTask(getTask(ListTask.TASK_REFRESH));
			break;
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		ListViewTask tTask = (ListViewTask) msg.obj;
		if (tTask.mTask == TaskBase.TASK_LOAD) {
			mltData = tTask.mltData;
			mAdapter = getAdapter(mltData);
			mListView.setAdapter(mAdapter);
			if (tTask.mltData.size() > 0) {
				// 设置ListView页面
				mFrameSelector.SelectFrame(mListView);
			} else {
				// 本地加载为空 执行网络刷新
				tTask.mTask = ListTask.TASK_REFRESH;
				postTask(tTask);
			}
			return false;
		} else if (tTask.mResult == TaskBase.RESULT_FINISH) {
			switch (tTask.mTask) {
			case ListTask.TASK_REFRESH:
				// 如果是列表刷新 通知列表刷新完成
				if (mListView.isRefreshing()) {
					mListView.finishRefresh();
				}
				// 有数据更新列表
				mltData = tTask.mltData;
				mAdapter = getAdapter(mltData);
				mListView.setAdapter(mAdapter);
				if (tTask.mltData.size() > 0) {
					// 恢复列表页面
					mFrameSelector.SelectFrame(mListView);
					// 如果开启分页
					if (mIsPaging
							&& tTask.mltData.size() >= ListViewTask.PAGESIZE) {
						mListView.addFooterView();
					}
				}
				// 无数据设置空页面
				else if (mltData == null || mltData.size() == 0) {
					this.onNodata(mLayoutNodata);
					mFrameSelector.SelectFrame(mLayoutNodata.getLayout());
				}
				break;
			case ListTask.TASK_MORE:
				if (tTask.mltData.size() > 0) {
					// 通知列表刷新完成
					mListView.finishLoadMore();
					// 更新列表
					mltData.addAll(tTask.mltData);
					mAdapter.AddData(tTask.mltData);
				}
				if (tTask.mltData.size() < ListViewTask.PAGESIZE) {
					// 关闭更多选项
					mListView.removeFooterView();
					Toast.makeText(mActivity, "数据全部加载完毕！", Toast.LENGTH_SHORT)
							.show();
				}
				break;
			}
		} else if (tTask.mResult == TaskBase.RESULT_FAIL) {
			if (mListView.isRefreshing()) {
				mListView.finishRefreshFail();
			}
			if (mltData == null || mltData.size() == 0) {
				// 设置空数据页面
				mLayoutNodata.setDescription(tTask.mErrors);
				mLayoutNodata.setButtonText(ModuleNodata.TEXT_TOREFRESH);
				mFrameSelector.SelectFrame(mLayoutNodata.getLayout());
			} else {
				// 恢复列表页面
				mFrameSelector.SelectFrame(mListView);
			}
			//Toast.makeText(mActivity, tTask.mErrors, Toast.LENGTH_LONG).show();
		}
		return tTask.mTask == ListTask.TASK_REFRESH
				|| tTask.mTask == ListTask.TASK_MORE;
	}

}
