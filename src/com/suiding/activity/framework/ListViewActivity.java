package com.suiding.activity.framework;

import java.util.List;

import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

import com.suiding.activity.R;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.bean.Filter;
import com.suiding.layoutbind.ModuleFilter;
import com.suiding.layoutbind.ModuleFilter.IFilter;
import com.suiding.layoutbind.ModuleListView;
import com.suiding.layoutbind.ModuleNodata;
import com.suiding.layoutbind.ModuleTitleMain;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.thread.framework.ListTask;
import com.suiding.thread.framework.ListViewTask;
import com.suiding.thread.framework.TaskBase;
import com.suiding.widget.framework.PullToRefreshBase.OnRefreshListener;

public abstract class ListViewActivity extends ActivityBase implements
		OnClickListener, OnItemClickListener, Callback, OnRefreshListener,
		OnItemLongClickListener, IFilter {

	// protected FrameSelector mFrameSelector;
	// protected LayoutNodata mLayoutNodata;
	// protected LayoutProgress mLayoutProgress;
	// protected RefreshListView mListView = null;
	protected ModuleFilter mModuleFilter = null;
	protected ModuleTitleMain mTitleMain = null;
	protected ModuleTitleOther mTitleOther = null;
	protected ModuleListView mModuleListView = null;

	// 标识是否分页
	protected boolean mIsPaging = false;

	protected List<Object> mltData = null;
	protected ListAdapterBase mAdapter = null;

	/**
	 * 为了防止子类直接实现 Activity.onCreate 把它设置final 为了子类能得到onCreate 消息通知 在
	 * Activity.onCreate 中调用 onCreate 子类可以在 onCreate 中执行其它特殊的初始化操作 如修改标题
	 */
	protected void onCreate() {
	}

	/**
	 * 当服务器加载数据为空的时候调用 处理信息提示 如果不重写这个函数 默认的提示是 抱歉，附近无相关数据
	 * 
	 * @param nodata
	 */
	protected void onNodata(ModuleNodata nodata) {
		// 设置空数据页面
		nodata.setDescription(ModuleNodata.TEXT_NODATA);
		nodata.setButtonText(ModuleNodata.TEXT_TOREFRESH);
	}

	// protected void onNodata(ModuleListView nodata) {
	// // 设置空数据页面
	// mModuleListView.setNoDataText(LayoutNodata.TEXT_NODATA);
	// mModuleListView.setNoDataButtonText(LayoutNodata.TEXT_TOREFRESH);
	// }

	protected abstract ListViewTask getTask(int task);

	protected abstract ListAdapterBase getAdapter(List<Object> ltdata);

	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_listview);

		// 控件初始化
		mModuleListView = new ModuleListView(this);
		// mLayoutNodata = new LayoutNodata(this);
		mTitleMain = new ModuleTitleMain(this);
		mTitleOther = new ModuleTitleOther(this);
		mModuleFilter = new ModuleFilter(this, this);
		// mFrameSelector = new FrameSelector(this);
		// mLayoutProgress = new LayoutProgress(this);
		// mListView = (RefreshListView) findViewById(R.id.module_listview);

		// 事件绑定
		// mListView.setOnItemClickListener(this);
		// mListView.setOnRefreshListener(this);
		// mListView.setOnItemLongClickListener(this);
		mModuleListView.setOnItemClickListener(this);
		mModuleListView.setOnRefreshListener(this);
		mModuleListView.setOnItemLongClickListener(this);
		// mLayoutNodata.setOnRefreshListener(this);
		mModuleListView.setOnNodataRefreshListener(this);

		// 设置正在加载页面
		// mFrameSelector.SelectFrame(mLayoutProgress.getLayout());
		mModuleListView.SelectFrame(ModuleListView.PRIGRESS);

		// 执行子类创建
		this.onCreate();

		// 抛送加载任务
		postTask(getTask(ListTask.TASK_LOAD));
	}

	@Override
	public void onFilter(int type, int index, Filter filter) {
		// TODO Auto-generated method stub

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
			// mFrameSelector.SelectFrame(mLayoutProgress.getLayout());
			mModuleListView.SelectFrame(ModuleListView.PRIGRESS);
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
			// mListView.setAdapter(mAdapter);
			mModuleListView.setAdapter(mAdapter);
			if (tTask.mltData.size() > 0) {
				// 设置ListView页面
				// mFrameSelector.SelectFrame(mListView);
				mModuleListView.SelectFrame(ModuleListView.LISTVIEW);
			} else {
				// 本地加载为空 执行网络刷新
				tTask.mTask = ListTask.TASK_REFRESH;
				postTask(tTask);
			}
			return true;
		} else if (tTask.mResult == TaskBase.RESULT_FINISH) {
			switch (tTask.mTask) {
			case ListTask.TASK_REFRESH:
				// 如果是列表刷新 通知列表刷新完成
				// if (mListView.isRefreshing()) {
				// mListView.finishRefresh();
				// }
				if (mModuleListView.isRefreshing()) {
					mModuleListView.finishRefresh();
				}
				// 有数据更新列表
				mltData = tTask.mltData;
				mAdapter = getAdapter(mltData);
				mModuleListView.setAdapter(mAdapter);
				if (tTask.mltData.size() > 0) {
					// 恢复列表页面
					// mFrameSelector.SelectFrame(mListView);
					mModuleListView.SelectFrame(ModuleListView.LISTVIEW);
					// 如果开启分页
					if (mIsPaging
							&& tTask.mltData.size() >= ListViewTask.PAGESIZE) {
						// mListView.addFooterView();
						mModuleListView.addFooterView();
					}
				}
				// 无数据设置空页面
				else if (mltData == null || mltData.size() == 0) {
					// this.onNodata(mLayoutNodata);
					this.onNodata(mModuleListView.getNoData());
					// mFrameSelector.SelectFrame(mLayoutNodata.getLayout());
					mModuleListView.SelectFrame(ModuleListView.NULLDATA);
				}
				break;
			case ListTask.TASK_MORE:
				if (tTask.mltData.size() > 0) {
					// 通知列表刷新完成
					// mListView.finishLoadMore();
					mModuleListView.finishLoadMore();
					// 更新列表
					mltData.addAll(tTask.mltData);
					mAdapter.AddData(tTask.mltData);
				}
				if (tTask.mltData.size() < ListViewTask.PAGESIZE) {
					// 关闭更多选项
					// mListView.removeFooterView();
					mModuleListView.removeFooterView();
					Toast.makeText(this, "数据全部加载完毕！", Toast.LENGTH_SHORT)
							.show();
				}
				break;
			}
		} else if (tTask.mResult == TaskBase.RESULT_FAIL) {
			// if (mListView.isRefreshing()) {
			// mListView.finishRefreshFail();
			// }
			if (mModuleListView.isRefreshing()) {
				mModuleListView.finishRefreshFail();
			}
			if (mltData == null || mltData.size() == 0) {
				// 设置空数据页面
				// mLayoutNodata.setDescription(tTask.mErrors);
				// mLayoutNodata.setButtonText(LayoutNodata.TEXT_TOREFRESH);
				mModuleListView.setNoDataText(tTask.mErrors);
				mModuleListView
						.setNoDataButtonText(ModuleNodata.TEXT_TOREFRESH);
				// mFrameSelector.SelectFrame(mLayoutNodata.getLayout());
				mModuleListView.SelectFrame(ModuleListView.NULLDATA);
			} else {
				// 恢复列表页面
				// mFrameSelector.SelectFrame(mListView);
				mModuleListView.SelectFrame(ModuleListView.LISTVIEW);
			}
			//Toast.makeText(this, tTask.mErrors, Toast.LENGTH_LONG).show();
		}
		return tTask.mTask == ListTask.TASK_REFRESH
				|| tTask.mTask == ListTask.TASK_MORE;
	}

}
