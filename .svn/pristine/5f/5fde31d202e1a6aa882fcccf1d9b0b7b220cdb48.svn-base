package com.suiding.activity;

import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.suiding.activity.framework.ListViewActivity;
import com.suiding.adapter.BirthInvitationListAdapter;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.application.SuidingApp;
import com.suiding.domain.IBirthInvitationDomain;
import com.suiding.layoutbind.ModuleNodata;
import com.suiding.model.Consumer;
import com.suiding.model.Page;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.ListTask;
import com.suiding.thread.framework.ListViewTask;
import com.suiding.thread.framework.TaskBase;


public class BirthdayInviteActivity extends ListViewActivity
{
    private TextView mTvReminder;

    @Override
    protected final void onCreate()
    {
        // TODO Auto-generated method stub
        mTvReminder = (TextView) findViewById(R.id.layout_listview_remind);
        //设置标题
        mTitleOther.setTitle(R.string.title_activity_birinvite);
    }

    @Override
    protected final ListViewTask getTask(int task)
    {
        // TODO Auto-generated method stub
        return new BirthInvitationTask(task);
    }

    @Override
    protected final ListAdapterBase getAdapter(List<Object> ltdata)
    {
        // TODO Auto-generated method stub
        return new BirthInvitationListAdapter(this, ltdata);
    }

    /**
     * 当服务器加载数据为空的时候调用
     * 	处理信息提示 如果不重写这个函数
     * 	默认的提示是 抱歉，附近无相关数据
     */
	@Override
	protected final void onNodata(ModuleNodata nodata) {
		// TODO Auto-generated method stub
        //设置空数据页面
		nodata.setDescription(ModuleNodata.TEXT_NOINVITE);
	}
	
    @Override
    public boolean handleMessage(Message msg)
    {
        // TODO Auto-generated method stub
        super.handleMessage(msg);
        ListViewTask tTask = (ListViewTask) msg.obj;
        if (tTask.mResult == TaskBase.RESULT_FINISH)
        {
            if (tTask.mTask == ListTask.TASK_REFRESH)
            {
                if(tTask.mltData.size() > 0)
                {
                    String format = getString(R.string.layout_listview_remind);
                    mTvReminder.setText(String.format(format, tTask.mltData.size()));
                    mTvReminder.setVisibility(View.VISIBLE);
                }
            }
        }
        return true;
    }

    private class BirthInvitationTask extends ListViewTask
    {
        public BirthInvitationTask(int task)
        {
            super(new Handler(BirthdayInviteActivity.this), task);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected List<? extends Object> onLoad()
        {
            // TODO Auto-generated method stub
            return mltData;
        }

        @Override
        protected List<? extends Object> onRefresh() throws Exception
        {
            // TODO Auto-generated method stub
            Consumer user = SuidingApp.getLoginUser();
            if (user != null)
            {
                IBirthInvitationDomain domain = DomainFactory
                        .getBirthInvitationDomain();
                return domain.getListByUserID(user.getID(), new Page(100, 0));
            }
            else
            {
                Thread.sleep(1000);
            }
            return mltData;
        }
    }

}
