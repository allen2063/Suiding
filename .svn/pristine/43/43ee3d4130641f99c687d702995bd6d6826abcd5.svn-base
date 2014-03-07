package com.suiding.application;

import java.util.UUID;

import android.os.Handler;
import android.os.Message;

import com.suiding.dao.OrderEntityDao;
import com.suiding.domain.IUserDomain;
import com.suiding.model.Consumer;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.NetworkUtil;

public class UserCenter {

	/**
	 * interface INotifyNetworkStatus
	 * @author SCWANG 订单数量统计改变通知
	 */
	public static interface INotifyFavoriteNumber {
		void onFavoriteNumberChanged(UUID userid,long favorite);
	}
	
	// 默认监听时间间隔 2分钟
	public static final int DURATION_SHORT = 1 * 60 * 1000;

	private static long mFavoriteNum; // 收藏数量
	
	//当前登录用户
	private static Consumer mConsumer = null;
	//定时当前器消息
	private static Message mHandlerMessage = null;
	
	public static long getFavoriteNum() {
		return mFavoriteNum;
	}
	/**
	 * 设置当前的登录用户
	 * 
	 * @param obj
	 *            用于权限验证 传入this
	 * @param user
	 *            当前的 登录用户
	 */
	public synchronized static void setLoginUser(Object obj, Consumer user) {
		if (obj instanceof SuidingApp) {
			OrderConsole.setLoginUser(obj, user);
			if (user != null) {// 用户登录 或者更新
				if (mConsumer == null || !mConsumer.getID().equals(user.getID())) {
					mConsumer = user;
					doUserLogin(user);
				}else{//用户信息更新
					mConsumer = user;
					doUserUpdate(user);
				}
			}else{//用户退出
				doUserLogout();
			}
		}
	}

	/**
	 * 设置App网络状态
	 * 
	 * @param obj
	 *            传入this指针 用于验证权限
	 * @param networkState
	 *            指定的网络状态
	 */
	public synchronized static void setNetworkStatus(Object obj, int status) {
		// TODO Auto-generated method stub
		if (obj instanceof SuidingApp) {
			OrderConsole.setNetworkStatus(obj, status);
		}
	}

	/**
	 * 
	 * @param store
	 * @return
	 */
	public synchronized static boolean getCommentPower(StoreBase store) {
		if (mConsumer == null) {
			return false;
		}
		OrderEntityDao dao = new OrderEntityDao(SuidingApp.getApp());
		return dao.hasOrderFinishFor(mConsumer, store);
	}

	/**
	 * 
	 * @param product
	 * @return
	 */
	public synchronized static boolean getCommentPower(Product product) {
		if (mConsumer == null) {
			return false;
		}
		OrderEntityDao dao = new OrderEntityDao(SuidingApp.getApp());
		return dao.hasOrderFinishFor(mConsumer, product);
	}

	private static void doUserLogin(Consumer user) {
		// TODO Auto-generated method stub
		//清空数据
		doClearUserData();
		//赋值用户
		mConsumer = user;
		//用户登录第一次加载等待订单任务
	}

	private static void doUserLogout() {
		// TODO Auto-generated method stub
		mConsumer = null;
		//清空数据
		doClearUserData();
		//删除通知
		NotifyCenter.cancel(NotifyCenter.ID_ORDER);
		//关闭定时器
		mHandlerMessage = null;
	}

	private static void doClearUserData() {
		// TODO Auto-generated method stub
		//清空数据
		mFavoriteNum = 0;
		mHandlerMessage = null;
	}
	
	private static void doUserUpdate(Consumer user) {
		// TODO Auto-generated method stub
		
	}
	
	private static void doReadyTimer(int task, Consumer user) {
		// TODO Auto-generated method stub
		//验证定时器 的对象是不是当前的 mConsumer 不是就忽略
		if(user != null && mConsumer != null && user.getID().equals(mConsumer.getID())){
			if(mHandlerMessage == null){
				Message msg = Message.obtain(mHandler, task,mConsumer);
				mHandler.sendMessageDelayed(msg, DURATION_SHORT);
				mHandlerMessage = msg;
			}else{
				//获取定时器绑定的用户对象
				Consumer tuser = (Consumer)mHandlerMessage.obj;
				//如果绑定的用户不是本次要绑定的用户
				if(!user.getID().equals(tuser.getID())){
					Message msg = Message.obtain(mHandler, task,mConsumer);
					mHandler.sendMessageDelayed(msg, DURATION_SHORT);
					mHandlerMessage = msg;
				}
			}
		}
	}
	
	// 定时器接收 Handler
	private static Handler mHandler = new Handler(SuidingApp.getLooper()) {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(mHandlerMessage == null){
				return ;//如果定时器已经关闭，返回
			}
			//获取定时器绑定的用户对象
			Consumer user = (Consumer)msg.obj;
			//标记定时器结束
			mHandlerMessage = null;
			//验证定时器 的对象是不是当前的 mConsumer 不是就忽略
			if(user != null && mConsumer != null && user.getID().equals(mConsumer.getID())){
				//如果定时器达到时 无网络访问
				if(SuidingApp.getNetworkStatus() == NetworkUtil.TYPE_NONE){
					doReadyTimer(msg.what, user);//准备下一次定时器
					return ;//本次不执行定时器操作
				}
				
			}
		}
	};

	public static class UserCenterTask extends TaskBase {

		private static final int TASK_COUNTFAVORITE = 10;
		
		public Consumer mUser = null;

		public UserCenterTask(Consumer user,int task) {
			super(SuidingApp.getLooper(), task);
			// TODO Auto-generated constructor stub
			mUser = user;
		}

		/**
		 * 异步线程执行网络任务
		 */
		@Override
		protected void onWorking(Message msg) throws Exception {
			// TODO Auto-generated method stub
			switch (mTask) {
			case TASK_COUNTFAVORITE:
				IUserDomain domain = DomainFactory.getUserDomain();
				mFavoriteNum = domain.getFavoriteStoreCount(mUser.getID());
				break;

			default:
				break;
			}
		}

		/**
		 * 主线程接收任务
		 */
		@Override
		protected boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if(mConsumer == null || !mConsumer.getID().equals(mUser.getID())){//执行任务过程中用户已经退出
				return true;
			}
			switch (mTask) {
			case TASK_COUNTFAVORITE:
				if(mResult != RESULT_FINISH){
					
				}else{
					SuidingApp app = SuidingApp.getApp();
					app.setFavoriteNumber(this,mUser.getID(), mFavoriteNum);
				}
				break;
			}
			return true;
		}

	}
}
