package com.suiding.thread.framework;

import com.suiding.util.LeSouException;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public abstract class TaskBase implements Runnable, OnCancelListener {

	// Task 执行状态枚举
	public static final int RESULT_FAIL = 0;
	public static final int RESULT_FINISH = 1;
	// Task 执行类型枚举
	public static final int TASK_LOAD = 0; // 第一次加载数据

	public int mTask = -1;
	public int mResult = -1;
	public String mErrors;

	protected Handler mHandler;
	protected boolean mIsCanceled = false;

	protected TaskBase() {
	}

	protected TaskBase(Handler handler) {
		this.mHandler = handler;
	}

	protected TaskBase(Handler handler, int task) {
		this.mTask = task;
		this.mHandler = handler;
	}

	protected TaskBase(Looper looper) {
		this.mHandler = new TaskHandle(looper);
	}

	protected TaskBase(Looper looper, int task) {
		this.mTask = task;
		this.mHandler = new TaskHandle(looper);
	}

	@Override
	public final void run() {
		// TODO Auto-generated method stub
		Message tMessage = Message.obtain();
		try {
			tMessage.what = mResult = RESULT_FINISH;
			if (!mIsCanceled) {
				this.onWorking(tMessage);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			tMessage.what = mResult = RESULT_FAIL;
			if(e instanceof LeSouException){
				String message = e.getMessage();
				if(message == null || message.length() == 0){
					mErrors = "加载任务出现异常";
				}else if(message.length() < 20){
					mErrors = "出现异常："+e.getMessage();
				}else{
					mErrors = "出现异常："+e.getMessage().substring(0, 15);
				}
			}else{
				mErrors = e.getMessage();
				if (mErrors == null || mErrors.length() == 0) {
					mErrors = e.toString();
				}
			}
			if (!mIsCanceled) {
				this.onException(e);
			}
		}
		if (!mIsCanceled && mHandler != null) {
			tMessage.obj = this;
			mHandler.sendMessage(tMessage);
		}
	}

	@Override
	public final void onCancel(DialogInterface dialog) {
		// TODO Auto-generated method stub
		mIsCanceled = true;
		this.onCancel();
	}

	protected boolean onHandle(Message msg) {
		return false;
	}

	protected void onException(Exception e) {

	}

	protected void onCancel() {

	}

	protected abstract void onWorking(Message msg) throws Exception;

	public static TaskBase getTask(Message msg) {
		// TODO Auto-generated method stub
		if (msg.obj instanceof TaskBase) {
			return (TaskBase) msg.obj;
		}
		return null;
	}

	private static class TaskHandle extends Handler {

		public TaskHandle(Looper looper) {
			super(looper);
			// TODO Auto-generated method stub
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			TaskBase task = TaskBase.getTask(msg);
			if (task != null) {
				task.onHandle(msg);
			}
		}

	}
}
