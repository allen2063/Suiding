package com.suiding.activity;

import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.os.Handler;

import com.suiding.activity.framework.ListViewActivity;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.domain.ICommentDomain;
import com.suiding.layoutbind.ItemComment;
import com.suiding.layoutbind.framework.ILayoutItem;
import com.suiding.model.Comment;
import com.suiding.model.Page;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.ListViewTask;
import com.suiding.util.ExtraUtil;


public class ListCommentActivity extends ListViewActivity
{
    public static final String EXTRA_FOTID = "comment_extra_forid";
    public static final String EXTRA_LIST = "comment_extra_list";

    public UUID mForID;
    public List<Comment> mlComment;

    @Override
    @SuppressWarnings("unchecked")
	protected void onCreate() {
		// TODO Auto-generated method stub
        mForID = (UUID) ExtraUtil.getExtra(EXTRA_FOTID);
        mlComment = (List<Comment>) ExtraUtil.getExtra(EXTRA_LIST);
	}

	@Override
	protected ListViewTask getTask(int task) {
		// TODO Auto-generated method stub
		return new ReviewTask(task);
	}

	@Override
	protected ListAdapterBase getAdapter(List<Object> ltdata) {
		// TODO Auto-generated method stub
		return new ReviewListAdapter(this, ltdata);
	}

    private class ReviewTask extends ListViewTask
    {
        public ReviewTask(int task)
        {
            super(new Handler(ListCommentActivity.this),task);
            // TODO Auto-generated constructor stub
        }

		@Override
		protected List<? extends Object> onLoad() {
			// TODO Auto-generated method stub
			return mlComment;
		}

		@Override
		protected List<? extends Object> onRefresh() throws Exception {
			// TODO Auto-generated method stub
            ICommentDomain tDomain = DomainFactory.getCommentDomain();
            Page tPage = new Page(mlComment.size() + 1, mlComment.size()
                    + PAGESIZE, Page.PAGEMODE_ADDITIAONAL);
			return tDomain.GetListByForID(mForID, tPage);
		}

    }
    
    public static class ReviewListAdapter extends ListAdapterBase
    {
        public ReviewListAdapter(Context context, List<Object> ltdata)
        {
        	super(context, ltdata);
        }
        
        @Override
		protected ILayoutItem getItemLayout() {
			// TODO Auto-generated method stub
			return new ItemComment();
		}
    }

}
