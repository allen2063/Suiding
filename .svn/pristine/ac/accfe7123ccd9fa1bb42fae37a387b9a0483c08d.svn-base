package com.suiding.activity;

import java.util.List;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import com.suiding.activity.framework.ListViewActivity;
import com.suiding.adapter.TwitterListAdapter;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.application.SuidingApp;
import com.suiding.dao.TwitterEntityDao;
import com.suiding.domain.ITwitterDomain;
import com.suiding.entity.TwitterEntity;
import com.suiding.model.Consumer;
import com.suiding.model.Page;
import com.suiding.model.Twitter;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.ListViewTask;


public class TwitterActivity extends ListViewActivity
{

    @Override
    protected void onCreate()
    {
        // TODO Auto-generated method stub
        mTitleOther.setTitle(R.string.title_activity_twitter);
    }

    @Override
    protected ListViewTask getTask(int task)
    {
        // TODO Auto-generated method stub
        return new TwitterTask(task);
    }

    @Override
    protected ListAdapterBase getAdapter(List<Object> ltdata)
    {
        // TODO Auto-generated method stub
        return new TwitterListAdapter(this, ltdata);
    }

    @Override
    public void onItemClick(AdapterView<?> absview, View view, int index,
            long id)
    {
        // TODO Auto-generated method stub
//        if (absview == mListView.getRefreshableView())
//        {
//        }
    }

    private class TwitterTask extends ListViewTask
    {

        public TwitterTask(int task)
        {
            super(new Handler(TwitterActivity.this), task);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected List<? extends Object> onLoad()
        {
            // TODO Auto-generated method stub
            TwitterEntityDao dao = new TwitterEntityDao(getBaseContext());
            return TwitterEntity.listToModel(dao.getAll());
        }

        @Override
        protected List<? extends Object> onRefresh() throws Exception
        {
            // TODO Auto-generated method stub
            if (SuidingApp.getLoginUser() == null)
            {
                Thread.sleep(1000);
                return mltData;
            }
            else
            {
                Consumer user = SuidingApp.getLoginUser();
                //获取数据接口
                ITwitterDomain domain = DomainFactory.getTwitterDomain();
                List<Twitter> ltData = domain.getListByUserID(user.getID(),
                        new Page(100, 0));
                //更新缓存
                TwitterEntityDao dao = new TwitterEntityDao(getBaseContext());
                dao.updateCache(TwitterEntity.listFormModel(ltData));
                return ltData;
            }
        }

    }
}
