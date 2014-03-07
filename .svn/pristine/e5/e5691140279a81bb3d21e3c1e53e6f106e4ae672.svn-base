package com.suiding.layoutbind;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.Comment;

public class ModuleComment implements ILayout, OnClickListener {
	
	public static final int MAX_COUNT = 5;
	
	private View mLayout = null;
	private boolean mPower = false;
	private boolean mIsFail = false;
	private boolean mIsValid = false;

	private TextView mTvStatus = null;
	private TextView mTvMore = null;
	private RatingBar mRatingBar = null;
	private ProgressBar mProgressBar = null;
	private LinearLayout mContainer = null;

	private LayoutInflater mInflater;

	private List<Comment> mltComment = new ArrayList<Comment>();

	private OnCommentClickListener mListener = null;

	public interface OnCommentClickListener {
		boolean onRefreshComment();
		void onPublishComment();
		void onCommentClick(Comment comment);
		void onMoreComment(List<Comment> ltcomment);
	}

	public ModuleComment(Pageable page) {
		// TODO Auto-generated constructor stub
		mLayout = page.findViewById(R.id.module_comment_header);
		mRatingBar = page.findRatingBarById(R.id.module_comment_grade);
		mContainer = page
				.findLinearLayoutById(R.id.module_comment_container);
		mTvMore = page.findTextViewById(R.id.module_comment_toall);
		mTvStatus = page.findTextViewById(R.id.module_comment_status);
		mProgressBar = page.findProgressBarById(R.id.module_comment_progress);
		mInflater = LayoutInflater.from(page.getContext());
		if (mLayout != null) {
			mIsValid = true;
			mLayout = (View) mLayout.getParent();
			mTvMore.setOnClickListener(this);
		}
	}

	public void setListener(OnCommentClickListener listener) {
		this.mListener = listener;
	}

	public void setCommentPower(boolean power) {
		// TODO Auto-generated method stub
		this.mPower = power;
		if (mIsValid && mProgressBar != null) {
			mProgressBar.setOnClickListener(this);
		}
	}

	public void setComment(List<Comment> ltComment) {
		// TODO Auto-generated method stub
		mltComment = ltComment;
		mContainer.removeAllViews();
		if (mltComment != null && mltComment.size() > 0) {
			int score = 0;
			for (Comment comment : ltComment) {
				score += comment.Score;
				AddCommentItem(comment);
			}
			if (mltComment.size() >= MAX_COUNT || mPower) {
				if (mPower) {
					mTvMore.setText("我也要评论");
				} else {
					mTvMore.setText("查看全部");
				}
				int dividerid = R.layout.module_divider_list;
				View listdivider = mInflater.inflate(dividerid, null);
				mContainer.addView(listdivider);
				mTvMore.setOnClickListener(this);
				mContainer.addView(mTvMore);
			}
			mRatingBar.setRating(score/mltComment.size());
			mContainer.setVisibility(View.VISIBLE);
		} else {
			mContainer.setVisibility(View.GONE);
		}
		if (mPower) {
			mTvStatus.setText(R.string.module_comment_publish);
		} else {
			mTvStatus.setText(R.string.module_comment_nodata);
		}
		mIsFail = false;
		mTvStatus.setOnClickListener(this);
		mProgressBar.setVisibility(View.GONE);
	}

	private void AddCommentItem(Comment comment) {
		// TODO Auto-generated method stub
		if (mIsValid) {
			View listitem = mInflater.inflate(R.layout.listitem_comment, null);
			if (mContainer.getChildCount() > 0) {
				int dividerid = R.layout.module_divider_list;
				View listdivider = mInflater.inflate(dividerid, null);
				mContainer.addView(listdivider);
			}
			ItemComment item = new ItemComment();
			item.Handle(listitem);
			item.Binding(comment);
			listitem.setTag(item);
			listitem.setOnClickListener(this);
			mContainer.addView(listitem);
		}
	}

	public void publishComment(Comment comment) {
		// TODO Auto-generated method stub
		if(comment == null){
			return ;
		}
		mContainer.setVisibility(View.VISIBLE);
		int count = mContainer.getChildCount();
		if(count > 2 && mContainer.getChildAt(count-1) == mTvMore){
			mContainer.removeViewAt(count - 2);
			mContainer.removeView(mTvMore);
			AddCommentItem(comment);
			int dividerid = R.layout.module_divider_list;
			View listdivider = mInflater.inflate(dividerid, null);
			mContainer.addView(listdivider);
			mTvMore.setOnClickListener(this);
			mContainer.addView(mTvMore);
		}else{
			AddCommentItem(comment);
		}
		mltComment.add(comment);
		doCountCommentScore();
	}

	private void doCountCommentScore() {
		// TODO Auto-generated method stub
		int score = 0;
		for (Comment comment : mltComment) {
			score += comment.Score;
		}
		mRatingBar.setRating(score/mltComment.size());
	}

	public void setLoadFail() {
		mContainer.removeAllViews();
		mContainer.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.GONE);
		mTvStatus.setOnClickListener(this);
		mTvStatus.setText(R.string.module_comment_error);
		mIsFail = true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (mListener != null) {
			switch (v.getId()) {
			case R.id.module_comment_status:
				if (mPower && !mIsFail) {
					mListener.onPublishComment();
				} else if (mListener.onRefreshComment()) {
					mTvStatus.setOnClickListener(null);
					mTvStatus.setText(R.string.module_comment_refreshing);
					mProgressBar.setVisibility(View.VISIBLE);
				}
				break;
			case R.id.module_comment_toall:
				if (mltComment.size() > 5) {
					mListener.onMoreComment(mltComment);
				} else {
					mListener.onPublishComment();
				}
				break;
			case R.id.module_comment_progress:
				mListener.onPublishComment();
				break;
			default:
				if (v.getTag() instanceof ItemComment) {
					ItemComment tItemComment = (ItemComment) v.getTag();
					mListener.onCommentClick(tItemComment.mComment);
				}
				break;
			}
		}
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		if (mLayout != null) {
			mLayout.setVisibility(View.GONE);
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		if (mLayout != null) {
			mLayout.setVisibility(View.VISIBLE);
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

}
