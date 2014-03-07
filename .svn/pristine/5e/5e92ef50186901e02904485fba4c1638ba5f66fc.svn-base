package com.suiding.thread.framework;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.suiding.application.SuidingApp;
import com.suiding.util.NetworkUtil;

public abstract class ListTask extends TaskBase {
	// 加载单页条数
	public static final int PAGESIZE = 15;
	// 枚举任务类型
	public static final int TASK_REFRESH = 100; // 下拉刷新
	public static final int TASK_MORE = 101; // 点击更多
	public static final int TASK_SORT = 102; // 排序
	public static final int TASK_CACHES = 103; // 本地数据库缓存
	public static final int TASK_CACHESADD = 104; // 本地数据库缓存追加

	public ListTask(Handler handler, int task) {
		super(handler, task);
		// TODO Auto-generated constructor stub
	}

	public ListTask(Looper looper, int task) {
		super(looper, task);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onWorking(Message msg) throws Exception {
		// TODO Auto-generated method stub
		if (SuidingApp.getNetworkStatus() == NetworkUtil.TYPE_NONE
				&& mTask != TaskBase.TASK_LOAD) {
			throw new Exception("当前无可用网络！");
		}
	}

}
