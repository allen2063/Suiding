package com.suiding.activity;

import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RelativeLayout.LayoutParams;

import com.suiding.activity.framework.ListViewActivity;
import com.suiding.adapter.ResultSearchListAdapter;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.domain.IStoreBaseDomain;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.ListTask;
import com.suiding.thread.framework.ListViewTask;
import com.suiding.thread.framework.TaskBase;

public class ResultSearchActivity extends ListViewActivity implements
		OnCheckedChangeListener {
	// 标签滚动视图
	private int mLastLeft;
	private View mSelectView = null;
	private RadioButton mRbCurLabel = null;

	private int mRbLabelIds[] = new int[] {
			// R.id.result_rb_news,
			R.id.result_rb_default, R.id.result_rb_grade, R.id.result_rb_price, };

	private RadioButton mRbLabels[] = new RadioButton[mRbLabelIds.length];

	@Override
	protected final void onCreate() {
		// TODO Auto-generated method stub
		setContentView(R.layout.layout_result_search);
		// 控件初始化

//		mListView = (RefreshListView) findViewById(R.id.module_listview);
		mLastLeft = mSelectView.getLeft();

		// 事件绑定
//		mListView.setOnItemClickListener(this);
//		mListView.setOnRefreshListener(this);

		// 设置标题
		mTitleOther.setTitle(R.string.title_activity_resulthotel);

		for (int i = 0; i < mRbLabels.length; i++) {
			mRbLabels[i] = ((RadioButton) findViewById(mRbLabelIds[i]));
			mRbLabels[i].setOnCheckedChangeListener(this);
		}
		mRbCurLabel = mRbLabels[0];
		mRbCurLabel.setChecked(true);
		mSelectView = findViewById(R.id.result_search_iv_select);
		// 延迟半秒初始化
		new Handler().postDelayed(new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				MoveSelectTo(mRbCurLabel);
			}

		}), 500L);

	}

	@Override
	protected final ListViewTask getTask(int task) {
		// TODO Auto-generated method stub
		return new ResultSearchTask(task);
	}

	@Override
	protected final ListAdapterBase getAdapter(List<Object> ltdata) {
		// TODO Auto-generated method stub
		return new ResultSearchListAdapter(this, ltdata);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		RadioButton tRadioButton = (RadioButton) buttonView;
		int id = buttonView.getId();
		if (isChecked) {
			for (int i : mRbLabelIds) {
				if (i == id) {
					this.MoveSelectTo(tRadioButton);
					return;
				}
			}
		}
	}

	private void MoveSelectTo(View v) {
		LayoutParams layoutParams = (LayoutParams) mSelectView
				.getLayoutParams();
		layoutParams.width = v.getWidth();
		mSelectView.setLayoutParams(layoutParams);

		int iNewLeft = v.getLeft();

		if (mLastLeft != iNewLeft) {
			TranslateAnimation animation = new TranslateAnimation(mLastLeft,
					iNewLeft, 0, 0);
			animation.setDuration(400);
			animation.setFillAfter(true);
			mSelectView.startAnimation(animation);
			mLastLeft = iNewLeft;
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		ListViewTask tTask = (ListViewTask) msg.obj;
		if (tTask.mResult == TaskBase.RESULT_FINISH) {
			if (tTask.mTask == ListTask.TASK_REFRESH) {
				if (mltData == null || mltData.size() == 0) {
//					mLayoutNodata.setDescription(LayoutNodata.TEXT_NOINVITE);
//					mFrameSelector.SelectFrame(mLayoutNodata.getLayout());
				}
			}
		}
		return true;
	}

	private class ResultSearchTask extends ListViewTask {
		public ResultSearchTask(int task) {
			super(new Handler(ResultSearchActivity.this), task);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected List<? extends Object> onLoad() {
			// TODO Auto-generated method stub
			return mltData;
		}

		@Override
		protected List<? extends Object> onRefresh() throws Exception {
			// TODO Auto-generated method stub
			IStoreBaseDomain tDomain = DomainFactory.getStoreBaseDomain();
			List<StoreBase> mltData = null;
			mltData = tDomain.GetAll();
			return mltData;
		}
	}

}