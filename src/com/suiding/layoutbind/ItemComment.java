package com.suiding.layoutbind;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.layoutbind.framework.ILayoutItem;
import com.suiding.model.Comment;
import com.suiding.util.DateFormatUtil;

public class ItemComment implements ILayoutItem
{
    public TextView mTvUserName = null;
    public TextView mTvContent = null;
    public TextView mTvDate = null;
    public RatingBar mRbGrade = null;
    
    public Comment mComment = null;
    /**
     * 从视图中取出控件
     * @param view
     */
    public void Handle(View view){
        mTvUserName = (TextView)view.findViewById(R.id.listitem_review_username);
        mTvContent = (TextView)view.findViewById(R.id.listitem_review_content);
        mTvDate = (TextView)view.findViewById(R.id.listitem_review_date);
        mRbGrade = (RatingBar)view.findViewById(R.id.listitem_review_grade);
    }

    /**
     * 将数据绑定到控件显示
     * @param comment
     */
	@Override
	public void Binding(Object data) {
		// TODO Auto-generated method stub
		if(data instanceof Comment)
		{
			mComment = (Comment) data;
			if(mComment.User != null){
		        if(mComment.User.NickName != null && mComment.User.NickName.length() > 0){
		            mTvUserName.setText(mComment.User.NickName);
		        }else{
		            mTvUserName.setText(mComment.User.UserName);
		        }
			}
	        
	        mTvContent.setText(mComment.Content);
	        mTvDate.setText(DateFormatUtil.DATE.format(mComment.Date));
	        mRbGrade.setRating(mComment.Score);
		}
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.listitem_comment;
	}
}
