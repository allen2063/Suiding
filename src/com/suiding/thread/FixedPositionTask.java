package com.suiding.thread;

import java.io.IOException;
import java.util.Date;

import android.content.SharedPreferences;
import android.location.Location;
import android.os.Handler;
import android.os.Message;

import com.suiding.application.AppExceptionHandler;
import com.suiding.application.BaiduLocationServer;
import com.suiding.application.SuidingApp;
import com.suiding.constant.FixedPositionEnum;
import com.suiding.dao.LocationEntityDao;
import com.suiding.entity.LocationEntity;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.TimeSpan;
import com.suiding.util.XmlCacheUtil;
/**
 * GPS定位 获取经纬度
 * @author SCWANG
 *
 */
public final class FixedPositionTask extends TaskBase{

    //定位结果
    private Location mLocation = null;

    //任务逻辑
    //private int mConut = 0;
    public boolean mMoreTime = false;
    public boolean mSuccess = false;
    
	//标识是否按照规定的使用post函数 抛送 FixedPositionTask
    private Boolean mIsPostByRule = false;
	
	private static Handler mHandler = new Handler(SuidingApp.getLooper()){

        @Override
        public void handleMessage(Message msg)
        {
            // TODO Auto-generated method stub
            FixedPositionTask tTask = (FixedPositionTask)msg.obj;
            SuidingApp tApp = SuidingApp.getApp();
            //如果任务成功执行完成
            if (msg.what == FixedPositionTask.RESULT_FINISH)
            {
                //如果请求再次定位
                if(tTask.mMoreTime){
                    //再次把 Task 抛送到 Worker
                    tTask.post();
                }else{
                    tApp.setFixedPosition(tTask, tTask.mLocation, FixedPositionEnum.FIXED);
                }
            }
            //如果任务执行失败
            else if (msg.what == FixedPositionTask.RESULT_FAIL)
            {    
                tApp.setFixedPosition(tTask, tTask.mLocation, FixedPositionEnum.FAIL);
            }
        }
	    
	};

	public FixedPositionTask() {
		super(mHandler);
	}
	
    /**
	 * 把任务post到App的Worker执行
	 */
	public synchronized final void post()
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
        if(mIsPostByRule == true){
//            mConut++;
//            mMoreTime = false;
//            //mLocation = LocationServer.getLocation();
//            mLocation = BaiduLocationServer.getLastLocation();
//            if(mLocation != null){
//                mSuccess = true;
//            }else{
//                if(mConut > 5){
//                    throw new Exception("暂时无法获取你的位置信息");
//                }else{
//                    Thread.sleep(1000);
//                    mMoreTime = true;
//                }
//            }
            mLocation = BaiduLocationServer.fixedLocation();
            if(mLocation == null){
        		throw new Exception("暂时无法获取你的位置信息");
            }
        }else{
            throw new Exception("请使用FixedPositionTask.post()抛送任务！");
        }
    }

    @Override
    protected final void onException(Exception e)
    {
        // TODO Auto-generated method stub
        if(e.getClass().equals(IOException.class)){
            mErrors = "您的网络出现了问题，请检查是否连接到网络！";
        }
    }

    /**
     * 验证城GPS位缓存是否过期
     * @param cityname
     * @return true 有效 false 过期 
     */
    public synchronized static boolean getFixedPositionCachesIsTimeout()
    {
        SharedPreferences tShared = XmlCacheUtil.getSharedPreferences();
        long tLongTime = tShared.getLong(XmlCacheUtil.FIXEDPOSITRION_UPDATETIME, 0);
        if(TimeSpan.FromDate(new Date(tLongTime),new Date()).getTotalMinutes() > 30){
            return false;
        }
        return true;
    }
    
    /**
     * 保存GPS缓存
     * @param location
     */
    public synchronized static void putFixedPositionCaches(Location location)
    {
    	try {
            //获取数据库操作接口
            LocationEntityDao tLocationDao = new LocationEntityDao(SuidingApp.getAppContext());
            //把数据添加到数据库中listFormAddress
            tLocationDao.updateCache(new LocationEntity(location));
            //更新时间
            XmlCacheUtil.putDate(XmlCacheUtil.FIXEDPOSITRION_UPDATETIME, new Date());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();//handled
			AppExceptionHandler.handler(e, "点定位任务，putFixedPositionCaches 出现异常");
		}
    }

    /**
     * 获取GPS定位缓存
     * @return
     */
    public synchronized static Location getFixedPositionCaches()
    {
        // TODO Auto-generated method stub
    	try {
        LocationEntityDao tLocationDao = new LocationEntityDao(SuidingApp.getAppContext());
        LocationEntity tEntity = tLocationDao.getCache();
        if(tEntity != null){
            return tEntity.getModel();
        }		
        } catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();//handled
			AppExceptionHandler.handler(e, "点定位任务，getFixedPositionCaches 出现异常");
		}
        return null;
    }
}
