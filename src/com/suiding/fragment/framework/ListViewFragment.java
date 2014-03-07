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

	// ��ʶ�Ƿ��ҳ
	protected boolean mIsPaging = false;

	protected List<Object> mltData = null;
	protected ListAdapterBase mAdapter = null;

	// ���ڼ������ݱ�ʶ
	protected Boolean mIsLoading = false;

	/**
	 * Ϊ�˷�ֹ����ֱ��ʵ�� Activity.onCreate ��������final Ϊ�������ܵõ�onCreate ��Ϣ֪ͨ ��
	 * Activity.onCreate �е��� onCreate ��������� onCreate ��ִ����������ĳ�ʼ������ ���޸ı���
	 */
	protected void onCreate() {
	}

	/**
	 * ����������������Ϊ�յ�ʱ����� ������Ϣ��ʾ �������д������� Ĭ�ϵ���ʾ�� ��Ǹ���������������
	 */
	protected void onNodata(ModuleNodata nodata) {
		// ���ÿ�����ҳ��
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

		// �ؼ���ʼ��
		mModuleFilter = new ModuleFilter(this, this);
		mTitleMain = new ModuleTitleMain(this);
		mTitleOther = new ModuleTitleOther(this);
		mLayoutNodata = new ModuleNodata(this);
		mFrameSelector = new FrameSelector(this);
		mLayoutProgress = new ModuleProgress(this);
		mListView = (RefreshListView) mRootView
				.findViewById(R.id.module_listview);
		// �¼���
		mListView.setOnRefreshListener(this);
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this);
		mTitleOther.setOnGoBackListener(this);
		mLayoutNodata.setOnRefreshListener(this);

		// ִ�����ഴ��
		this.onCreate();

		// ��ʾ���ڼ�������
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
	 * ��һ���л�����ҳ��
	 */
	@Override
	protected final void onFirstSwitchOver() {
		// TODO Auto-generated method stub
		postTask(getTask(TaskBase.TASK_LOAD));
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
			mFrameSelector.SelectFrame(mLayoutProgress.getLayout());
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
			mListView.setAdapter(mAdapter);
			if (tTask.mltData.size() > 0) {
				// ����ListViewҳ��
				mFrameSelector.SelectFrame(mListView);
			} else {
				// ���ؼ���Ϊ�� ִ������ˢ��
				tTask.mTask = ListTask.TASK_REFRESH;
				postTask(tTask);
			}
			return false;
		} else if (tTask.mResult == TaskBase.RESULT_FINISH) {
			switch (tTask.mTask) {
			case ListTask.TASK_REFRESH:
				// ������б�ˢ�� ֪ͨ�б�ˢ�����
				if (mListView.isRefreshing()) {
					mListView.finishRefresh();
				}
				// �����ݸ����б�
				mltData = tTask.mltData;
				mAdapter = getAdapter(mltData);
				mListView.setAdapter(mAdapter);
				if (tTask.mltData.size() > 0) {
					// �ָ��б�ҳ��
					mFrameSelector.SelectFrame(mListView);
					// ���������ҳ
					if (mIsPaging
							&& tTask.mltData.size() >= ListViewTask.PAGESIZE) {
						mListView.addFooterView();
					}
				}
				// ���������ÿ�ҳ��
				else if (mltData == null || mltData.size() == 0) {
					this.onNodata(mLayoutNodata);
					mFrameSelector.SelectFrame(mLayoutNodata.getLayout());
				}
				break;
			case ListTask.TASK_MORE:
				if (tTask.mltData.size() > 0) {
					// ֪ͨ�б�ˢ�����
					mListView.finishLoadMore();
					// �����б�
					mltData.addAll(tTask.mltData);
					mAdapter.AddData(tTask.mltData);
				}
				if (tTask.mltData.size() < ListViewTask.PAGESIZE) {
					// �رո���ѡ��
					mListView.removeFooterView();
					Toast.makeText(mActivity, "����ȫ��������ϣ�", Toast.LENGTH_SHORT)
							.show();
				}
				break;
			}
		} else if (tTask.mResult == TaskBase.RESULT_FAIL) {
			if (mListView.isRefreshing()) {
				mListView.finishRefreshFail();
			}
			if (mltData == null || mltData.size() == 0) {
				// ���ÿ�����ҳ��
				mLayoutNodata.setDescription(tTask.mErrors);
				mLayoutNodata.setButtonText(ModuleNodata.TEXT_TOREFRESH);
				mFrameSelector.SelectFrame(mLayoutNodata.getLayout());
			} else {
				// �ָ��б�ҳ��
				mFrameSelector.SelectFrame(mListView);
			}
			//Toast.makeText(mActivity, tTask.mErrors, Toast.LENGTH_LONG).show();
		}
		return tTask.mTask == ListTask.TASK_REFRESH
				|| tTask.mTask == ListTask.TASK_MORE;
	}

}
