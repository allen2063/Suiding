package com.suiding.thread.framework;


import java.util.ArrayList;
import java.util.List;

import com.suiding.application.AppExceptionHandler;
import com.suiding.model.Page;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public abstract class ListViewTask extends ListTask
{
    //枚举任务类型
    public static final int TASK_NULL = 1000;    //下拉刷新
    
	public List<Object> mltData = new ArrayList<Object>();
	
	public int mFirstResult = 0;

	public ListViewTask(Handler handle, int task) {
		super(handle, task);
		// TODO Auto-generated constructor stub
	}

	public ListViewTask(Looper looper, int task) {
		super(looper, task);
		// TODO Auto-generated constructor stub
	}

	protected abstract List<? extends Object> onLoad();

	protected abstract List<? extends Object> onRefresh() throws Exception;

	protected List<? extends Object> onMore(Page page) throws Exception {
		Thread.sleep(1000);
		return mltData;
	}
	
	protected boolean onWorking() throws Exception {
		return false;
	}

	@Override
	protected final void onWorking(Message tMessage) throws Exception {
		// TODO Auto-generated method stub
		super.onWorking(tMessage);
		switch (mTask) {
		case ListTask.TASK_LOAD:
			/**
			 * 为了安全考虑，系统框架规定 onLoad 不能抛出异常
			 * 	onLoad 主要用于 本地数据库加载缓存，就算本地没有数据
			 * 	也可返回空List 
			 * 		以防万一使用try catch 阻止异常
			 */
			try {
				mltData = new ArrayList<Object>(onLoad());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.handler(e, "列表页任务，onLoad 抛出异常 被过滤");
			}
			break;
		case ListTask.TASK_REFRESH:
			mltData = new ArrayList<Object>(onRefresh());
			break;
		case ListTask.TASK_MORE:
			mltData = new ArrayList<Object>(onMore(new Page(PAGESIZE,mFirstResult)));
			break;
		default :
			this.onWorking();
			break;
		}
	}
}
