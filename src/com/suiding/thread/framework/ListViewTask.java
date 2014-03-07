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
    //ö����������
    public static final int TASK_NULL = 1000;    //����ˢ��
    
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
			 * Ϊ�˰�ȫ���ǣ�ϵͳ��ܹ涨 onLoad �����׳��쳣
			 * 	onLoad ��Ҫ���� �������ݿ���ػ��棬���㱾��û������
			 * 	Ҳ�ɷ��ؿ�List 
			 * 		�Է���һʹ��try catch ��ֹ�쳣
			 */
			try {
				mltData = new ArrayList<Object>(onLoad());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.handler(e, "�б�ҳ����onLoad �׳��쳣 ������");
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
