package com.suiding.thread;

import java.util.List;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

import com.suiding.application.SuidingApp;
import com.suiding.dao.AreaEntityDao;
import com.suiding.service.AdressService;
import com.suiding.thread.framework.TaskBase;


/**
 * Address地址任务
 * 
 * @author SCWANG
 *         
 */
public final class AddressTask extends TaskBase implements Callback
{
    //定位结果
    private List<String> mltAddress = null;

    //标识是否按照规定的使用post函数 抛送 FixedPositionTask
    private Boolean mIsPostByRule = false;

    @Override
    public boolean handleMessage(Message msg)
    {
        // TODO Auto-generated method stub    
        //如果任务成功执行完成        
        if (mResult == AddressTask.RESULT_FINISH)
        {
            AdressService.setAddressDB(mltAddress);
        }
        //如果任务执行失败
        else if (mResult == AddressTask.RESULT_FAIL)
        {
        }
        return true;
    }

    public AddressTask()
    {
        mHandler = new Handler(this);
    }

    /**
     * 把任务post到App的Worker执行
     */
    public final void post()
    {
        //标识已经按照post抛送 ImageTask
        mIsPostByRule = true;
        //把任务发送到App线程中去执行
        SuidingApp.postTask(this);
    }

    @Override
    protected final void onWorking(Message tMessage) throws Exception
    {
        // TODO Auto-generated method stub
        if (mIsPostByRule == true)
        {
            AreaEntityDao dao = new AreaEntityDao(SuidingApp.getApp());
            mltAddress = dao.getAddressServerList();
            while(mltAddress.size() < AdressService.LIST_LENGHT)
            {
                mltAddress.add("空地址");
            }
        }
        else
        {
            throw new Exception("请使用AddressTask.post()抛送任务！");
        }
    }

    @Override
    protected final void onException(Exception e)
    {
        // TODO Auto-generated method stub
    }

    /**
     * 更新地址服务
     */
    public static void updateAddressService()
    {
        new AddressTask().post();
    }
}
