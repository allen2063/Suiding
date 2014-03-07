package com.suiding.application;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.suiding.activity.ListOrderActivity;
import com.suiding.constant.OrderStatus;
import com.suiding.dao.OrderEntityDao;
import com.suiding.domain.IOrderDomain;
import com.suiding.entity.OrderEntity;
import com.suiding.model.Consumer;
import com.suiding.model.Order;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.NetworkUtil;

public class OrderConsole {

	/**
	 * interface IOrderNumber
	 * @author SCWANG 订单数量统计改变通知
	 */
	public static interface INotifyOrderNumber {
		void onOrderNumberChanged(UUID userid,long wait,long confirm,long none);
	}
	/**
	 * interface IRemindOrder
	 * @author SCWANG 订单提醒
	 */
	public static interface INotifyRemindOrder {
		void onRemindOrderChanged(UUID userid,List<Order> ltremind);
	}
	
	
	public static final int[] FILTES_WAITING = new int[]{
		OrderStatus.WAITING
	};
	public static final int[] FILTES_CONFIRM = new int[]{
		OrderStatus.CONFIRM
	};
	public static final int[] FILTES_CANCEL = new int[]{
		OrderStatus.CANCEL,OrderStatus.REFUSE,OrderStatus.TIMEOUT
	};
	
	// private static final String CACHE_NUMWAITING =
	// "UserCenter_CACHE_NUMWAITING";
	// private static final String CACHE_NUMCONFIRM =
	// "UserCenter_CACHE_NUMCONFIRM";
	// private static final String CACHE_NUMCANCEL =
	// "UserCenter_CACHE_NUMCANCEL";
	//
	public static boolean isOrderConfirm(Order order)
	{
		for (int status : FILTES_CONFIRM) {
			if(order.OrderStatus == status){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isOrderCancel(Order order)
	{
		for (int status : FILTES_CANCEL) {
			if(order.OrderStatus == status){
				return true;
			}
		}
		return false;
	}
	// 默认监听时间间隔 2分钟
	public static final int DURATION_SHORT = 1 * 60 * 1000;
	//定时器任务 统计等待订单个数
	public static final int TIMER_TASK_COUNT = 0;
	//定时器任务 获取等待列表（由于第一次失败，定时加载）
	public static final int TIMER_TASK_LOADLIST = 1;

	private static long mConfirmNum; // 成功预定
	private static long mWaitingNum; // 等待确认
	private static long mRefusedNum; // 拒绝预定
	
	// 实时的等待订单列表
	private static List<Order> mltOrder = new ArrayList<Order>();
	// 上次的等待订单列表（同于暂存和比较）
	private static List<Order> mltOrderLast = new ArrayList<Order>();
	// 改变的等待订单列表（同于通知）
	private static List<Order> mltOrderChanged = new ArrayList<Order>();
	//当前登录用户
	private static Consumer mConsumer = null;
	//定时当前器消息
	private static Message mHandlerMessage = null;
	
	public static long getConfirmNum() {
		return mConfirmNum;
	}
	public static long getRefusedNum() {
		return mRefusedNum;
	}
	public static long getWaitingNum() {
		return mWaitingNum;
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
			if(status != NetworkUtil.TYPE_NONE){//如果是有效网络
				if(mConsumer != null){//如果用户已经登录
					//如果没有开启定时器 并且存在订单
					if(mHandlerMessage == null && mWaitingNum > 0){
						//开启定时器
						doReadyTimer(TIMER_TASK_COUNT, mConsumer);
					}
				}
			}
		}
	}

	/**
	 * 用户提交订单（添加到等待列表）
	 *  注意：必须在主线程中调用
	 * @param order
	 */
	public synchronized static void submitNewOrder(Order order){
		if(mConsumer != null && order != null 
				&& order.OrderStatus == OrderStatus.WAITING){
			if(mHandlerMessage == null){
				//开启定时器 统计数量变化
				doReadyTimer(TIMER_TASK_COUNT,mConsumer);
			}
			mWaitingNum++;
			mltOrder.add(order);
		}
	}
	
	/**
	 * 页面主动刷新等待列表
	 *  注意：必须在主线程中调用
	 * @param order
	 */
	public synchronized static void refreshWaitOrder(List<Order> ltorder) {
		// TODO Auto-generated method stub
		//如果定时器正在运行 有等待订单 页面数据不为空
		if(mHandlerMessage != null && mltOrder.size() > 0 && ltorder != null){
			mltOrderLast = mltOrder;
			mltOrder = new ArrayList<Order>(ltorder);
			if(mltOrderLast.size() > mltOrder.size()){
				//更新等待订单统计
				mWaitingNum = mltOrder.size();
				if(mltOrder.size() > 0){//如果还有等待的订单
					//正常逻辑 mltOrderLast 会比 mltOrder 多若干订单 多出的就是被拒绝或超时或者接受的
					//找出多出的订单(在mltOrderLast中删除mltOrder有的订单，留下的就是...)
					for (int i = 0; i < mltOrderLast.size(); i++) {
						for (int j = 0; j < mltOrder.size(); j++) {
							if(mltOrderLast.get(i).getID().equals(mltOrder.get(j).getID())){
								mltOrderLast.remove(i--);
								break;
							}
						}
					}
				}else{//如果没有等待订单 关闭定时器
					mHandlerMessage = null;
					mHandler.removeMessages(TIMER_TASK_COUNT);
					mHandler.removeMessages(TIMER_TASK_LOADLIST);
				}
				if(mltOrderLast.size() > 0){//正常逻辑会大于0 的
					//剩下就是被变的订单，需要重新加载了解其状态再做缓存更新并提示用户
					//抛送加载 获取改变状态任务
					int work = OrderConsoleTask.TASK_GETORDERSTATUS;
					TaskBase task = new OrderConsoleTask(mConsumer,work);
					SuidingApp.postTask(task);
				}
			}
		}
	}

	/**
	 * 页面主动刷新 确认订单之后 通知后台 处理
	 *  注意：必须在主线程中调用
	 * @param ltconfirm
	 */
	public static void refreshConfirmOrder(List<Order> ltconfirm) {
		// TODO Auto-generated method stub
		//如果定时器正在运行 有等待订单 页面数据不为空
		if(mHandlerMessage != null && mltOrder.size() > 0 && ltconfirm != null){
			if(ltconfirm.size() > 0){
				//在等待列表  mltOrder 中删除 确认的 ltorder 订单列表
				for (int i = 0; i < mltOrder.size(); i++) {
					for (int j = 0; j < ltconfirm.size(); j++) {
						if(ltconfirm.get(i).getID().equals(mltOrder.get(j).getID())){
							mltOrder.remove(i--);
							break;
						}
					}
				}
				//如果比较之后发现 等待订单变少
				if(mWaitingNum > mltOrder.size()){
					//重新统计
					mConfirmNum += mWaitingNum - mltOrder.size();
					mWaitingNum = mltOrder.size();
					//通知App
					SuidingApp app = SuidingApp.getApp();
					OrderConsoleTask power = new OrderConsoleTask(null, 0);
					app.setOrderNumber(power,mConsumer.getID(),mWaitingNum, mConfirmNum, mRefusedNum);
					app.setRemindOrder(power,mConsumer.getID(),mltOrderLast);
					//如果没有等待的订单 关闭定时器
					if(mWaitingNum == 0){
						mHandlerMessage = null;
						mHandler.removeMessages(TIMER_TASK_COUNT);
						mHandler.removeMessages(TIMER_TASK_LOADLIST);
					}
				}
			}
		}
	}

	/**
	 * 页面主动刷新 拒绝订单之后 通知后台 处理
	 *  注意：必须在主线程中调用
	 * @param ltrefused
	 */
	public static void refreshRefusedOrder(List<Order> ltrefused) {
		// TODO Auto-generated method stub
		//如果定时器正在运行 有等待订单 页面数据不为空
		if(mHandlerMessage != null && mltOrder.size() > 0 && ltrefused != null){
			if(ltrefused.size() > 0){
				//在等待列表  mltOrder 中删除 确认的 ltorder 订单列表
				for (int i = 0; i < mltOrder.size(); i++) {
					for (int j = 0; j < ltrefused.size(); j++) {
						if(ltrefused.get(i).getID().equals(mltOrder.get(j).getID())){
							mltOrder.remove(i--);
							break;
						}
					}
				}
				//如果比较之后发现 等待订单变少
				if(mWaitingNum > mltOrder.size()){
					//重新统计
					mRefusedNum += mWaitingNum - mltOrder.size();
					mWaitingNum = mltOrder.size();
					//通知App
					SuidingApp app = SuidingApp.getApp();
					OrderConsoleTask power = new OrderConsoleTask(null, 0);
					app.setOrderNumber(power,mConsumer.getID(),mWaitingNum, mConfirmNum, mRefusedNum);
					app.setRemindOrder(power,mConsumer.getID(),mltOrderLast);
					//如果没有等待的订单 关闭定时器
					if(mWaitingNum == 0){
						mHandlerMessage = null;
						mHandler.removeMessages(TIMER_TASK_COUNT);
						mHandler.removeMessages(TIMER_TASK_LOADLIST);
					}
				}
			}
		}
	}
	/**
	 * 获取改变的订单列表（获取之后，原数据被清空）
	 *  注意：必须在主线程中调用
	 * @return
	 */
	public static List<Order> getListOrderChanged(Context context) {
		List<Order> ltorder = new ArrayList<Order>();
		synchronized (mltOrderChanged) {
			ltorder.addAll(mltOrderChanged);
			if(!(context instanceof SuidingApp)){
				mltOrderChanged.clear();
				SuidingApp app = SuidingApp.getApp();
				OrderConsoleTask power = new OrderConsoleTask(null, 0);
				app.setRemindOrder(power,mConsumer.getID(),new ArrayList<Order>());
				try {
					NotifyCenter.cancel(NotifyCenter.ID_ORDER);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();//handled
					AppExceptionHandler.handler(e, "订单中心，getListOrderChanged 取消通知失败");
				}
			}
		}
		return ltorder;
	}

	/**
	 * 查询改变的订单列表（获取之后，原数据不会被清空）
	 *  注意：必须在主线程中调用
	 * @return
	 */
	public static List<Order> pekListOrderChanged() {
		List<Order> ltorder = new ArrayList<Order>();
		synchronized (mltOrderChanged) {
			ltorder.addAll(mltOrderChanged);
		}
		return ltorder;
	}

	/**
	 * 获取改变的订单列表中确认的项（获取之后，元数据被清空）
	 *  注意：必须在主线程中调用
	 * @return
	 */
	public static List<Order> getListOrderChangedConfirm(Context context) {
		// TODO Auto-generated method stub
		List<Order> ltorder = new ArrayList<Order>();
		synchronized (mltOrderChanged) {
			for (int i = 0; i < mltOrderChanged.size(); i++) {
				Order order = mltOrderChanged.get(i);
				if(OrderConsole.isOrderConfirm(order)){
					ltorder.add(order);
					mltOrderChanged.remove(i--);
				}
			}
			if(ltorder.size() > 0){
				SuidingApp app = SuidingApp.getApp();
				OrderConsoleTask power = new OrderConsoleTask(null, 0);
				app.setRemindOrder(power,mConsumer.getID(),mltOrderChanged);
				if(mltOrderChanged.size() == 0){//如果没有剩下改变的订单 取消之前的通知
					try {
						NotifyCenter.cancel(NotifyCenter.ID_ORDER);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();//handled
						AppExceptionHandler.handler(e, "订单中心，getListOrderChangedConfirm 取消通知失败");
					}
				}
			}
		}
		return ltorder;
	}
	/**
	 * 获取改变的订单列表中拒绝的项（获取之后，元数据被清空）
	 *  注意：必须在主线程中调用
	 * @return
	 */
	public static List<Order> getListOrderChangedRefused(Context context) {
		// TODO Auto-generated method stub
		List<Order> ltorder = new ArrayList<Order>();
		synchronized (mltOrderChanged) {
			for (int i = 0; i < mltOrderChanged.size(); i++) {
				Order order = mltOrderChanged.get(i);
				if(OrderConsole.isOrderCancel(order)){
					ltorder.add(order);
					mltOrderChanged.remove(i--);
				}
			}
			if(ltorder.size() > 0){
				SuidingApp app = SuidingApp.getApp();
				OrderConsoleTask power = new OrderConsoleTask(null, 0);
				app.setRemindOrder(power,mConsumer.getID(),mltOrderChanged);
				if(mltOrderChanged.size() == 0){//如果没有剩下改变的订单 取消之前的通知
					try {
						NotifyCenter.cancel(NotifyCenter.ID_ORDER);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();//handled
						AppExceptionHandler.handler(e, "订单中心，getListOrderChangedRefused 取消通知失败");
					}
				}
			}
		}
		return ltorder;
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
		int work = OrderConsoleTask.TASK_LOADORDERLIST;
		TaskBase task = new OrderConsoleTask(mConsumer,work);
		SuidingApp.postTask(task);
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
		mHandler.removeMessages(TIMER_TASK_COUNT);
		mHandler.removeMessages(TIMER_TASK_LOADLIST);
	}

	private static void doClearUserData() {
		// TODO Auto-generated method stub
		//清空数据
		mWaitingNum = 0;
		mConfirmNum = 0;
		mRefusedNum = 0;
		mHandlerMessage = null;
		mltOrder.clear();
		mltOrderLast.clear();
		mltOrderChanged.clear();
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
	
	private static void doOrderChanged(List<Order> ltChanged, Consumer user) {
		// TODO Auto-generated method stub
		//验证对象是不是当前的 mConsumer 不是就忽略
		if(user != null && mConsumer != null && user.getID().equals(mConsumer.getID())){
			int finish = 0,cancel = 0;
			mWaitingNum = mltOrder.size();
			for (Order order : ltChanged) {
				if(OrderConsole.isOrderCancel(order)){
					cancel++;
					mRefusedNum++;
				}
				else{
					finish++;
					mConfirmNum++;
				}
			}		
			SuidingApp app = SuidingApp.getApp();
			OrderConsoleTask power = new OrderConsoleTask(null,0);
			app.setOrderNumber(power,user.getID(), mWaitingNum, mConfirmNum, mRefusedNum);
			
			try {
				OrderEntityDao dao = new OrderEntityDao(app);
				dao.updateOrderStatus(OrderEntity.listFormModel(ltChanged));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.handler(e, "订单中心，doOrderChanged 更新缓存失败");
			}

			doNotification(user,finish,cancel);
		}
	}

	private static void doNotification(Consumer user,int finish,int cancel) {
		// TODO Auto-generated method stub
		SuidingApp app = SuidingApp.getApp();
		if(app.getIsForegroundRunning()){
			OrderConsoleTask power = new OrderConsoleTask(null,0);
			app.setRemindOrder(power,user.getID(),mltOrderChanged);
		}else{
			String context = "你的订单 ";
			if(finish > 0){
				context += finish + " 条已处理 ";
			}
			if(cancel > 0){
				context += cancel + " 条已取消 ";
			}
			
			Intent intent = new Intent(app,ListOrderActivity.class);
			intent.putExtra(ListOrderActivity.EXTRA_BL_NOTFY, true);
			Notification notify = NotifyCenter.getNotification(context, intent);
			NotifyCenter.notify(notify, NotifyCenter.ID_ORDER);
		}
	}
	
	private static void doOrderInValid(List<Order> ltinvalid) {
		// TODO Auto-generated method stub
		for (Order order : ltinvalid) {
			order.OrderStatus = OrderStatus.NONE;
		}
		try {
			OrderEntityDao dao = new OrderEntityDao(SuidingApp.getApp());
			dao.updateOrderStatus(OrderEntity.listFormModel(ltinvalid));
		} catch (Exception e) {
			// TODO: handle exception
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
				switch (msg.what) {
				case TIMER_TASK_COUNT:{
					//定时器 - 统计等待订单数量
					int work = OrderConsoleTask.TASK_COUNTWAITORDER;
					TaskBase task = new OrderConsoleTask(user,work);
					SuidingApp.postTask(task);
				}
					break;
				case TIMER_TASK_LOADLIST:{
					//定时器 - 获取等待列表（由于第一次失败，定时加载）
					int work = OrderConsoleTask.TASK_LOADORDERLIST;
					TaskBase task = new OrderConsoleTask(user,work);
					SuidingApp.postTask(task);
				}
					break;
				}
			}
		}
	};

	public static class OrderConsoleTask extends TaskBase {

		//用户登录第一次加载等待列表
		public static final int TASK_LOADORDERLIST = 10;
		//定时器发起的 等待数量统计
		public static final int TASK_COUNTWAITORDER = 11;		
		//统计发现改变后 再次获取全部的订单 来和上一次比较找出被改变的订单
		public static final int TASK_GETNEWORDERLIST = 12;	
		//统计发现改变后 重新获取被改变的订单
		public static final int TASK_GETORDERSTATUS = 13;	
		
		public Consumer mUser = null;

		public OrderConsoleTask(Consumer user,int task) {
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
			if(mUser == null){
				mUser = new Consumer();
				throw new Exception("任务失败 - OrderConsoleTask 没有绑定用户");
			}
			switch (mTask) {
			case TASK_LOADORDERLIST: {//用户登录第一次加载等待列表
				IOrderDomain domain = DomainFactory.getOrderDomain();
				try {
					mConfirmNum = domain.GetCountByFilters(mUser.getID(),
							OrderConsole.FILTES_CONFIRM);
					mRefusedNum = domain.GetCountByFilters(mUser.getID(),
							OrderConsole.FILTES_CANCEL);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();//handled
					AppExceptionHandler.handler(e, "订单中心任务 执行，TASK_LOADORDERLIST 统计订单出现异常");
				}
				synchronized (mltOrder) {
					mltOrder = domain.GetWaitOrdersByUser(mUser.getID());
					mWaitingNum = mltOrder.size();
				}
				break;
			}
			case TASK_COUNTWAITORDER: {//定时器发起的 等待数量统计
				IOrderDomain domain = DomainFactory.getOrderDomain();
				mWaitingNum = domain.CountWaitOrdersByUser(mUser.getID());
//				try {
//					mConfirmNum = domain.GetCountByFilters(mUser.getID(),
//							ListOrderActivity.FILTES_FINISH);
//					mRefusedNum = domain.GetCountByFilters(mUser.getID(),
//							ListOrderActivity.FILTES_CANCEL);
//					IUserDomain sdomain = DomainFactory.getUserDomain();
//					mFavoriteNum = sdomain.getFavoriteStoreCount(mUser.getID());
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
				break;
			}
			case TASK_GETNEWORDERLIST: {//再次获取全部的订单 用来比较找出被改变的订单
				synchronized (mltOrder) {
					IOrderDomain domain = DomainFactory.getOrderDomain();
					List<Order> ltOrder = domain.GetWaitOrdersByUser(mUser.getID());
					if(ltOrder.size() > 0){
						mltOrder = ltOrder;
					}else{//正常逻辑不会出现
						try {
							throw new Exception("任务失败 - 再次获取全部的订单 用来比较找出被改变的订单");
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();//handled
							AppExceptionHandler.handler(e, "订单中心任务 执行，TASK_GETNEWORDERLIST 出现异常");
						}
					}
				}
				break;
			}
			case TASK_GETORDERSTATUS: {//统计发现改变后 重新获取被改变的订单
				synchronized (mltOrderLast) {
					IOrderDomain domain = DomainFactory.getOrderDomain();
					List<Order> ltOrder = domain.GetOrdersStatus(mltOrderLast);
					//正常逻辑应该相等 并且大于0
					if(ltOrder.size() == ltOrder.size() && ltOrder.size() > 0){
						mltOrderChanged.addAll(ltOrder);
					}else{//正常逻辑不会出现
						try {
							throw new Exception("任务失败 - 统计发现改变后 重新获取被改变的订单");
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();//handled
							AppExceptionHandler.handler(e, "订单中心任务 执行，TASK_GETORDERSTATUS 出现异常");
						}
					}
				}
				break;
			}
			}
		}

		/**
		 * 主线程接收任务
		 */
		@Override
		protected boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if(mResult != RESULT_FINISH){
				//Toast.makeText(SuidingApp.getApp(),mErrors, Toast.LENGTH_SHORT).show();
			}
			if(mConsumer == null || !mConsumer.getID().equals(mUser.getID())){//执行任务过程中用户已经退出
				return true;
			}
			
			switch (mTask) {
			case TASK_LOADORDERLIST://任务结束 - 用户登录第一次加载等待列表
				//不管成功失败，先通知界面，因为可能成功了一半的数据
				SuidingApp app = SuidingApp.getApp();
				app.setOrderNumber(this,mUser.getID(),mWaitingNum,mConfirmNum,mRefusedNum);
				//检查任务是否成功
				if(mResult != RESULT_FINISH){//任务失败
					//开启定时器 下次在次加载列表
					doReadyTimer(TIMER_TASK_LOADLIST,mUser);
					return true;
				}
				if (mltOrder.size() > 0) {// 如果存在等待的订单
					//开启定时器 统计数量变化
					doReadyTimer(TIMER_TASK_COUNT,mUser);
				}//没有等待的订单，不开启定时器
				break;
			case TASK_COUNTWAITORDER://任务结束 - 定时器发起的 等待数量统计
				if(mResult != RESULT_FINISH){//任务失败
					//开启定时器 下次继续统计
					doReadyTimer(TIMER_TASK_COUNT,mUser);
					return true;
				}
				if (mltOrder.size() == mWaitingNum && mWaitingNum > 0) {//如果统计订单数量没有改变
					//开启定时器 下次继续统计
					doReadyTimer(TIMER_TASK_COUNT,mUser);
				}
				else if(mltOrder.size() > mWaitingNum){//发现等待订单有变化
					mltOrderLast = new ArrayList<Order>(mltOrder);//转移原来的列表到旧列表
					if(mWaitingNum == 0){//如果等待列表为空
						//清空等待订单
						mltOrder.clear();
						//抛送 读取这个订单现在的状态 任务
						//抛送加载 获取改变状态任务
						int work = OrderConsoleTask.TASK_GETORDERSTATUS;
						TaskBase task = new OrderConsoleTask(mUser,work);
						SuidingApp.postTask(task);
					}else{//还有剩余的等待订单
						//抛送加载新的等待列表
						int work = OrderConsoleTask.TASK_GETNEWORDERLIST;
						TaskBase task = new OrderConsoleTask(mUser,work);
						SuidingApp.postTask(task);
					}
				}else{//正常逻辑不会出现
					try {
						throw new Exception("网络加载的等待订单多余本地的订单");
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();//handled
						AppExceptionHandler.handler(e, "订单中心任务结束，TASK_COUNTWAITORDER 逻辑之外");
					}
				}
				break;
			case TASK_GETNEWORDERLIST://任务结束 - 再次获取全部的订单 用来比较找出被改变的订单
				if(mResult != RESULT_FINISH){//任务失败
					//还原上一步操作
					mWaitingNum = mltOrder.size();
					mltOrderLast = new ArrayList<Order>();
					try {
						throw new Exception("TASK_GETNEWORDERLIST 还原上一步操作");
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();//handled
						AppExceptionHandler.handler(e, "订单中心任务结束，TASK_GETNEWORDERLIST 逻辑预测");
					}
					return true;
					//失败分析 
					//再次获取全部等待订单的 前一步是统计成功并且 部分订单被改变
					//上一步所做的操作就是 mltOrderLast = mltOrder;//转移原来的列表到旧列表
					//mltOrderLast = new ArrayList<Order>();就可以还原操作
				}
				synchronized (mltOrderLast) {
					if(mltOrder.size() > 0){
						//正常逻辑 mltOrderLast 会比 mltOrder 多若干订单 多出的就是被拒绝或超时或者接受的
						//找出多出的订单(在mltOrderLast中删除mltOrder有的订单，留下的就是...)
						for (int i = 0; i < mltOrderLast.size(); i++) {
							for (int j = 0; j < mltOrder.size(); j++) {
								if(mltOrderLast.get(i).getID().equals(mltOrder.get(j).getID())){
									mltOrderLast.remove(i--);
									break;
								}
							}
						}
						if(mltOrderLast.size() > 0){//正常逻辑会大于0 的
							//剩下就是被变的订单，需要重新加载了解其状态再做缓存更新并提示用户
							//抛送加载 获取改变状态任务
							int work = OrderConsoleTask.TASK_GETORDERSTATUS;
							TaskBase task = new OrderConsoleTask(mUser,work);
							SuidingApp.postTask(task);
						}else{//正常逻辑不会出现
							try {
								throw new Exception("订单比较之后，找不到改变的订单");
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();//handled
								AppExceptionHandler.handler(e, "订单中心任务结束，TASK_GETNEWORDERLIST 逻辑之外");
							}
						}
					}else{//正常逻辑不会出现
						try {
							throw new Exception("加载不到新的订单来比较");
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();//handled
							AppExceptionHandler.handler(e, "订单中心任务结束，TASK_GETNEWORDERLIST 逻辑之外");
						}
					}
				}
				break;
			case TASK_GETORDERSTATUS: //任务结束 - 统计发现改变后 重新获取被改变的订单
				if(mResult != RESULT_FINISH){//任务失败
					//通知订单无效
					doOrderInValid(mltOrderLast);
					try {
						throw new Exception("无效订单产生");
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();//handled
						AppExceptionHandler.handler(e, "订单中心任务结束，TASK_GETORDERSTATUS 逻辑预测");
					}
					return true;
				}
				synchronized (mltOrderChanged) {
					//通知订单改变
					doOrderChanged(mltOrderChanged,mUser);
				}
				break;
			}
			return true;
		}

	}
}
