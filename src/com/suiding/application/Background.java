package com.suiding.application;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;

import com.suiding.thread.framework.TaskBase;
import com.suiding.util.DateGuid;
/**
 * Background ��̨�̹߳���
 * @author SCWANG
 *
 */
public class Background
{
    //�������е�����߳�����
    private static final int MAXTHREADNUM = 5;
    //����ȴ��������
    private static Queue<TaskBase> mqeTask = new LinkedList<TaskBase>();
    //�����߳��б�����
    private static List<ThreadWorker> mltWorker = new ArrayList<ThreadWorker>();

    //����̨����һ�� Task ����
    public static TaskBase postTask(TaskBase task)
    {
        synchronized(mltWorker){
            if(mltWorker.size() < MAXTHREADNUM){
                ThreadWorker tWorker = new ThreadWorker(DateGuid.NewID(),task);
                mltWorker.add(tWorker);
                tWorker.start();
                return task;
            }
        }
        synchronized(mqeTask){
            mqeTask.offer(task);
        }
        return task;
    }

    //һ���߳�����ִ�����
    private static void onTaskFinish(ThreadWorker worker)
    {
        synchronized(mltWorker){
            mltWorker.remove(worker);
        }
        synchronized(mqeTask){
            if(mqeTask.size() > 0){
                postTask(mqeTask.poll());
            }
        }
    }
    
    
    private static class ThreadWorker extends Thread
    {
        private Runnable mRunnable = null;
        
        public ThreadWorker(String name,Runnable runnable)
        {
            super(name);
            mRunnable = runnable;
        }
        
        @Override
        public void run()
        {
            // TODO Auto-generated method stub
            try
            {
                mRunnable.run();
            }
            catch (Exception e)
            {
                // TODO: handle exception
                e.printStackTrace();
            }
            onTaskFinish(this);
        }
        
        
    }
}
