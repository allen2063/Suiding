package com.suiding.layoutbind;

import java.util.UUID;

import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RatingBar;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.application.SuidingApp;
import com.suiding.domain.ICommentDomain;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.Comment;
import com.suiding.model.Consumer;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.UUIDUtil;

public class ModuleCommentEditbar implements ILayout, OnClickListener {
	private View mLayout = null;
	private boolean mIsValid = false;

	private View mVwCover = null;
	private View mTvSubmit = null;
	private EditText mEtComment = null;

	private UUID mForID = null;
	private Consumer mUser = null;
	private Comment mComment = null;
	private RatingBar mRatingBar = null;

	private Pageable mPageable = null;
	private CommentEditListener mListener = null;

	public static interface CommentEditListener {
		void onPublishComment(Comment comment);
	}

	public ModuleCommentEditbar(Pageable page) {
		// TODO Auto-generated constructor stub
		mTvSubmit = page.findTextViewById(R.id.detail_comment_submit);
		mEtComment = page.findEditTextById(R.id.detail_comment_input);
		mRatingBar = page.findRatingBarById(R.id.detail_comment_grade);
		if (mEtComment != null) {
			mIsValid = true;
			mPageable = page;
			mLayout = (View) mEtComment.getParent();
			mRatingBar.setMax(5);
			mRatingBar.setRating(4);
		}
	}
	public void setCoverView(View cover) {
		this.mVwCover = cover;
		if(cover != null){
			int visibility = mLayout.getVisibility();
			if(cover.getVisibility() != visibility){
				cover.setVisibility(visibility);
			}
			mVwCover.setOnClickListener(this);
		}
	}
	public void setCoverView(Pageable page,int id) {
		if(page != null){
			setCoverView(page.findViewById(id));
		}
	}
	public void setCoverView(int id) {
		if(mPageable != null){
			setCoverView(mPageable.findViewById(id));
		}
	}

	public void setCommentInfo(Consumer user, UUID ForID) {
		if (user != null && user.IsPhoneVerifi && ForID != null
				&& !ForID.equals(UUIDUtil.Empty)) {
			mUser = user;
			mForID = ForID;
			mTvSubmit.setOnClickListener(this);
		}
	}

	public void setListener(CommentEditListener mListener) {
		this.mListener = mListener;
	}

	public Comment getComment() {
		return mComment;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == mVwCover){
			this.hide();
			return;
		}
		String comment = mEtComment.getText().toString();
		if (comment.trim().length() == 0) {
			mPageable.showToastShort("请填写评论内容！");
		} else {
			mComment = new Comment();
			mComment.Content = comment;
			mComment.User = mUser;
			mComment.User_ID = mUser.getID();
			mComment.For_ID = mForID;
			mComment.Score = (int)mRatingBar.getRating();
			SuidingApp.postTask(new CommentTask(TaskBase.TASK_LOAD));
			
			this.hide();
		}
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		if (mIsValid && mLayout != null) {
			mLayout.setVisibility(View.GONE);
			if(mVwCover!=null){
				mVwCover.setVisibility(View.GONE);
			}
			//隐藏输入键盘
			mPageable.setSoftInputEnable(mEtComment,false);
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		if (mIsValid && mLayout != null) {
			mLayout.setVisibility(View.VISIBLE);
			if(mVwCover!=null){
				mVwCover.setVisibility(View.VISIBLE);
			}
			//弹出输入键盘 弹出输入键盘
			mPageable.setSoftInputEnable(mEtComment,true);
		}
	}

	@Override
	public View getLayout() {
		// TODO Auto-generated method stub
		return mLayout;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return mIsValid;
	}

	private class CommentTask extends TaskBase {

		public CommentTask(int task) {
			super(SuidingApp.getLooper(), task);
			// TODO Auto-generated constructor stub
			switch (task) {
			case TASK_LOAD:
				mPageable.showProgressDialog("正在提交评论...");
				break;
			}
		}

		@Override
		protected void onWorking(Message msg) throws Exception {
			// TODO Auto-generated method stub
			switch (mTask) {
			case TASK_LOAD:
				ICommentDomain domain = DomainFactory.getCommentDomain();
				domain.Insert(mComment);
				break;
			}
		}

		@Override
		protected boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if (mResult == RESULT_FINISH) {
				if (mListener != null) {
					mListener.onPublishComment(mComment);
				}
				mPageable.showToastShort("发表评论成功");
			} else {
				mPageable.showToastLong("网络不给力啊，再试一次吧~");
			}
			mPageable.hideProgressDialog();
			return true;
		}
	}
}
