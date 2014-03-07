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

	// ��ʶ�Ƿ��ҳ
	protected boolean mIsPaging = false;

	protected List<Object> mltData = null;
	protected ListAdapterBase mAdapter = null;

	/**
	 * Ϊ�˷�ֹ����ֱ��ʵ�� Activity.onCreate ��������final Ϊ�������ܵõ�onCreate ��Ϣ֪ͨ ��
	 * Activity.onCreate �е��� onCreate ��������� onCreate ��ִ����������ĳ�ʼ������ ���޸ı���
	 */
	protected void onCreate() {
	}

	/**
	 * ����������������Ϊ�յ�ʱ����� ������Ϣ��ʾ �������д������� Ĭ�ϵ���ʾ�� ��Ǹ���������������
	 * 
	 * @param nodata
	 */
	protected void onNodata(ModuleNodata nodata) {
		// ���ÿ�����ҳ��
		nodata.setDescription(ModuleNodata.TEXT_NODATA);
		nodata.setButtonText(ModuleNodata.TEXT_TOREFRESH);
	}

	// protected void onNodata(ModuleListView nodata) {
	// // ���ÿ�����ҳ��
	// mModuleListView.setNoDataText(LayoutNodata.TEXT_NODATA);
	// mModuleListView.setNoDataButtonText(LayoutNodata.TEXT_TOREFRESH);
	// }

	protected abstract ListViewTask getTask(int task);

	protected abstract ListAdapterBase getAdapter(List<Object> ltdata);

	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_listview);

		// �ؼ���ʼ��
		mModuleListView = new ModuleListView(this);
		// mLayoutNodata = new LayoutNodata(this);
		mTitleMain = new ModuleTitleMain(this);
		mTitleOther = new ModuleTitleOther(this);
		mModuleFilter = new ModuleFilter(this, this);
		// mFrameSelector = new FrameSelector(this);
		// mLayoutProgress = new LayoutProgress(this);
		// mListView = (RefreshListView) findViewById(R.id.module_listview);

		// �¼���
		// mListView.setOnItemClickListener(this);
		// mListView.setOnRefreshListener(this);
		// mListView.setOnItemLongClickListener(this);
		mModuleListView.setOnItemClickListener(this);
		mModuleListView.setOnRefreshListener(this);
		mModuleListView.setOnItemLongClickListener(this);
		// mLayoutNodata.setOnRefreshListener(this);
		mModuleListView.setOnNodataRefreshListener(this);

		// �������ڼ���ҳ��
		// mFrameSelector.SelectFrame(mLayoutProgress.getLayout());
		mModuleListView.SelectFrame(ModuleListView.PRIGRESS);

		// ִ�����ഴ��
		this.onCreate();

		// ���ͼ�������
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
		// ���͸�������
		ListViewTask task = getTask(ListTask.TASK_MORE);
		task.mFirstResult = mltData.size();
		postTask(task);
		return true;
	}

	@Override
	public final boolean onRefresh() {
		// TODO Auto-generated method stub
		// ����ˢ������
		postTask(getTask(ListTask.TASK_REFRESH));
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case ModuleNodata.ID_BUTTON:
		case ModuleNodata.TEXT_TOREFRESH:
			// �������ڼ���ҳ��
			// mFrameSelector.SelectFrame(mLayoutProgress.getLayout());
			mModuleListView.SelectFrame(ModuleListView.PRIGRESS);
			// ����ˢ������
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
				// ����ListViewҳ��
				// mFrameSelector.SelectFrame(mListView);
				mModuleListView.SelectFrame(ModuleListView.LISTVIEW);
			} else {
				// ���ؼ���Ϊ�� ִ������ˢ��
				tTask.mTask = ListTask.TASK_REFRESH;
				postTask(tTask);
			}
			return true;
		} else if (tTask.mResult == TaskBase.RESULT_FINISH) {
			switch (tTask.mTask) {
			case ListTask.TASK_REFRESH:
				// ������б�ˢ�� ֪ͨ�б�ˢ�����
				// if (mListView.isRefreshing()) {
				// mListView.finishRefresh();
				// }
				if (mModuleListView.isRefreshing()) {
					mModuleListView.finishRefresh();
				}
				// �����ݸ����б�
				mltData = tTask.mltData;
				mAdapter = getAdapter(mltData);
				mModuleListView.setAdapter(mAdapter);
				if (tTask.mltData.size() > 0) {
					// �ָ��б�ҳ��
					// mFrameSelector.SelectFrame(mListView);
					mModuleListView.SelectFrame(ModuleListView.LISTVIEW);
					// ���������ҳ
					if (mIsPaging
							&& tTask.mltData.size() >= ListViewTask.PAGESIZE) {
						// mListView.addFooterView();
						mModuleListView.addFooterView();
					}
				}
				// ���������ÿ�ҳ��
				else if (mltData == null || mltData.size() == 0) {
					// this.onNodata(mLayoutNodata);
					this.onNodata(mModuleListView.getNoData());
					// mFrameSelector.SelectFrame(mLayoutNodata.getLayout());
					mModuleListView.SelectFrame(ModuleListView.NULLDATA);
				}
				break;
			case ListTask.TASK_MORE:
				if (tTask.mltData.size() > 0) {
					// ֪ͨ�б�ˢ�����
					// mListView.finishLoadMore();
					mModuleListView.finishLoadMore();
					// �����б�
					mltData.addAll(tTask.mltData);
					mAdapter.AddData(tTask.mltData);
				}
				if (tTask.mltData.size() < ListViewTask.PAGESIZE) {
					// �رո���ѡ��
					// mListView.removeFooterView();
					mModuleListView.removeFooterView();
					Toast.makeText(this, "����ȫ��������ϣ�", Toast.LENGTH_SHORT)
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
				// ���ÿ�����ҳ��
				// mLayoutNodata.setDescription(tTask.mErrors);
				// mLayoutNodata.setButtonText(LayoutNodata.TEXT_TOREFRESH);
				mModuleListView.setNoDataText(tTask.mErrors);
				mModuleListView
						.setNoDataButtonText(ModuleNodata.TEXT_TOREFRESH);
				// mFrameSelector.SelectFrame(mLayoutNodata.getLayout());
				mModuleListView.SelectFrame(ModuleListView.NULLDATA);
			} else {
				// �ָ��б�ҳ��
				// mFrameSelector.SelectFrame(mListView);
				mModuleListView.SelectFrame(ModuleListView.LISTVIEW);
			}
			//Toast.makeText(this, tTask.mErrors, Toast.LENGTH_LONG).show();
		}
		return tTask.mTask == ListTask.TASK_REFRESH
				|| tTask.mTask == ListTask.TASK_MORE;
	}

}
