package com.suiding.activity;

import java.util.List;
import java.util.UUID;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.suiding.activity.DetailStoreActivity.DetailStoreTask;
import com.suiding.activity.framework.ActivityBase;
import com.suiding.application.SuidingApp;
import com.suiding.domain.ICommentDomain;
import com.suiding.layoutbind.ModuleComment;
import com.suiding.layoutbind.ModuleComment.OnCommentClickListener;
import com.suiding.layoutbind.ModuleCommentEditbar;
import com.suiding.layoutbind.ModuleCommentEditbar.CommentEditListener;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.model.Comment;
import com.suiding.model.Page;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.ExtraUtil;
import com.suiding.util.UUIDUtil;
import com.suiding.widget.RefreshScorllView;
import com.suiding.widget.framework.PullToRefreshBase.OnRefreshListener;

public abstract class DetailActivity extends ActivityBase implements
		OnRefreshListener, OnClickListener, Callback, OnCommentClickListener, CommentEditListener {

	public static final String EXTRA_DETAIL = "EXTRA_DETAIL";

	protected View mLatoutPage = null;
	protected ModuleTitleOther mLayoutTitle = null;
	protected RefreshScorllView mScorllView = null;
	protected ModuleComment mLayoutComment = null;
	protected ModuleCommentEditbar mComEditbar = null;

	protected List<Comment> mltComment = null;

	protected abstract View onCreateView(LayoutInflater inflater, Bundle bundle);

	protected abstract DetailTask getDetailTask(int task);

	protected void onCreated(View page) {

	}

	protected UUID onGetDetailID() {
		return UUIDUtil.Empty;
	}

	@Override
	protected final void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.detail_main);

		mLatoutPage = onCreateView(LayoutInflater.from(this),bundle);

		mScorllView = (RefreshScorllView) findViewById(R.id.detail_scorllview);
		mScorllView.addChildToScrollView(mLatoutPage);
		mScorllView.setOnRefreshListener(this);

		mLayoutTitle = new ModuleTitleOther(this);
		mLayoutTitle.setOnGoBackListener(this);

		mLayoutComment = new ModuleComment(this);
		mLayoutComment.setListener(this);
		
		mComEditbar = new ModuleCommentEditbar(this);
		mComEditbar.setCoverView(R.id.detail_cover);
		mComEditbar.setListener(this);

		this.onCreated(mLatoutPage);

		mComEditbar.setCommentInfo(SuidingApp.getLoginUser(), onGetDetailID());

		postTask(getDetailTask(DetailStoreTask.TASK_LOADCOMMENT));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case ModuleTitleOther.ID_GOBACK:
			this.finish();
			break;
		}
	}

	@Override
	public boolean onMore() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onRefresh() {
		// TODO Auto-generated method stub
		postTask(getDetailTask(DetailTask.TASK_REFRESH));
		return true;
	}
	
	@Override
	public void onPublishComment() {
		// TODO Auto-generated method stub
		mComEditbar.show();
	}
	
	@Override
	public void onPublishComment(Comment comment) {
		// TODO Auto-generated method stub
		mLayoutComment.publishComment(comment);
	}

	@Override
	public void onCommentClick(Comment product) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onRefreshComment() {
		// TODO Auto-generated method stub
		postTask(getDetailTask(DetailTask.TASK_LOADCOMMENT));
		return true;
	}

	@Override
	public void onMoreComment(List<Comment> ltcomment) {
		// TODO Auto-generated method stub
		ExtraUtil.putExtra(ListCommentActivity.EXTRA_FOTID, onGetDetailID());
		ExtraUtil.putExtra(ListCommentActivity.EXTRA_LIST, ltcomment);
		this.startActivity(new Intent(this, ListCommentActivity.class));
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		DetailTask tTask = (DetailTask) msg.obj;
		if (tTask.mResult == TaskBase.RESULT_FINISH) {
			if (tTask.mTask == DetailStoreTask.TASK_LOADCOMMENT) {
				mltComment = tTask.mlComment;
				mLayoutComment.setComment(mltComment);
			} else if (tTask.mTask == DetailStoreTask.TASK_REFRESH) {
				mScorllView.finishRefresh();
			}
		} else if (tTask.mResult == TaskBase.RESULT_FAIL) {
			if (mScorllView.isRefreshing()) {
				mScorllView.finishRefresh();
			} else if (tTask.mTask == DetailStoreTask.TASK_LOADCOMMENT) {
				mLayoutComment.setLoadFail();
			}
			//Toast.makeText(this, tTask.mErrors, Toast.LENGTH_SHORT).show();
		}
		return tTask.mResult == DetailStoreTask.TASK_LOADCOMMENT;
	}

	protected abstract class DetailTask extends TaskBase {

		public static final int TASK_REFRESH = 100;
		public static final int TASK_LOADCOMMENT = 101;

		public List<Comment> mlComment = null;

		public DetailTask(Handler handler, int task) {
			super(handler, task);
			// TODO Auto-generated constructor stub
		}

		public DetailTask(Looper looper, int task) {
			super(looper, task);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onWorking(Message tMessage) throws Exception {
			// TODO Auto-generated method stub
			switch (mTask) {
			case TASK_REFRESH: {
				Thread.sleep(1000);
				break;
			}
			case TASK_LOADCOMMENT: {
				Page page = new Page(ModuleComment.MAX_COUNT, 0);
				ICommentDomain tDomain = DomainFactory.getCommentDomain();
				mlComment = tDomain.GetListByForID(onGetDetailID(), page);
				break;
			}
			}
		}

		@Override
		protected boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if (mResult == TaskBase.RESULT_FINISH) {
				if (mTask == DetailStoreTask.TASK_LOADCOMMENT) {
					mltComment = mlComment;
					mLayoutComment.setComment(mltComment);
				} else if (mTask == DetailStoreTask.TASK_REFRESH) {
					mScorllView.finishRefresh();
				}
			} else if (mResult == TaskBase.RESULT_FAIL) {
				if (mScorllView.isRefreshing()) {
					mScorllView.finishRefresh();
				} else if (mTask == DetailStoreTask.TASK_LOADCOMMENT) {
					mLayoutComment.setLoadFail();
				}
				showToastShort(mErrors);
			}
			return mResult == DetailStoreTask.TASK_REFRESH;
		}
	}
}
