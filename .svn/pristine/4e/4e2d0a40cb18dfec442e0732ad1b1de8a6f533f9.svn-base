package com.suiding.application;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;

import com.suiding.thread.framework.TaskBase;
import com.suiding.util.DateGuid;
/**
 * Background 后台线程管理
 * @author SCWANG
 *
 */
public class Background
{
    //定义运行的最大线程数量
    private static final int MAXTHREADNUM = 5;
    //定义等待任务队列
    private static Queue<TaskBase> mqeTask = new LinkedList<TaskBase>();
    //定义线程列表容器
    private static List<ThreadWorker> mltWorker = new ArrayList<ThreadWorker>();

    //往后台抛送一个 Task 任务
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

    //一个线程任务执行完成
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
