package com.suiding.application;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Set;

import android.os.Message;

import com.suiding.caches.ExceptionCache;
import com.suiding.domain.IExceptionHandlerDomain;
import com.suiding.model.Consumer;
import com.suiding.model.ExceptionHandler;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.DeviceUtil;
import com.suiding.util.NetworkUtil;

public class AppExceptionHandler implements UncaughtExceptionHandler {
	public static final String TAG = "CrashHandler";
	private static AppExceptionHandler INSTANCE = new AppExceptionHandler();
	private Thread.UncaughtExceptionHandler mDefaultHandler;

	private AppExceptionHandler() {
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
		if(mDefaultHandler != null){
			mDefaultHandler.toString();
		}
	}
	
	public static void register() {
	}

	public static AppExceptionHandler getInstance() {
		return INSTANCE;
	}
	
	public static ExceptionHandler getHandler(Throwable ex,String remark) {
		ExceptionHandler ehandler = new ExceptionHandler();
		Thread cthread = Thread.currentThread();
		ehandler.Thread = "异常线程：" + cthread;
		
		ehandler.Remark = remark;
		ehandler.Name = getExceptionName(ex);
		ehandler.Message = getExceptionMessage(ex);
		ehandler.Stack = getAllStackTraceInfo(ex);
		ehandler.Version = SuidingApp.getVersion();
		ehandler.Device = DeviceUtil.getDeviceMessage();

		Consumer user = SuidingApp.getLoginUser();
		if(user != null){
			ehandler.User_ID = user.getID();
		}
		
		return ehandler;
	}

	public static void handler(Throwable ex,String remark) {
		ExceptionHandler ehandler = getHandler(ex,remark);
		Background.postTask(new ExceptionHandlerTask(ehandler));
	}

	@Override
	public final void uncaughtException(Thread thread, Throwable ex) {
		ExceptionHandler ehandler = new ExceptionHandler();
		Thread cthread = Thread.currentThread();

		ehandler.Thread = "捕获线程：" + cthread;
		ehandler.Thread += "\r\n异常线程：" + thread;
		
		ehandler.Name = getExceptionName(ex);
		ehandler.Message = getExceptionMessage(ex);
		ehandler.Stack = getAllStackTraceInfo(ex);
		ehandler.Version = SuidingApp.getVersion();
		ehandler.Device = DeviceUtil.getDeviceMessage();
		ehandler.Remark = "全局捕获异常";

		Consumer user = SuidingApp.getLoginUser();
		if(user != null){
			ehandler.User_ID = user.getID();
		}
		
		Background.postTask(new ExceptionHandlerTask(ehandler));
		
		if(mDefaultHandler != null){
			mDefaultHandler.uncaughtException(thread, ex);
		}else{
			
		}
//		Activity activity = SuidingApp.getApp().getCurActivity();
//		if (activity != null) {
//			AlertDialog.Builder tDialog = new AlertDialog.Builder(activity);
//			tDialog.setTitle("提示");
//			tDialog.setCancelable(false);
//			tDialog.setMessage("程序崩溃了...\r\n" + ex.toString() + "\r\n"
//					+ getStackTraceInfo(ex));
//			tDialog.setNeutralButton("我知道了", new OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					System.exit(0);
//				}
//			});
//			tDialog.create();
//			tDialog.show();
//			Looper.loop();
//			mDefaultHandler.uncaughtException(thread, ex);
//		}
	}

	private static String getAllStackTraceInfo(Throwable ex) {
		// TODO Auto-generated method stub
		String allstack = getSuidingStackTraceInfo(ex);
		allstack += getStackTraceInfo(ex);
		return allstack;
	}

	private static String getExceptionMessage(Throwable ex) {
		// TODO Auto-generated method stub
		String message = ex.getMessage();
		ex = ex.getCause();
		if (ex != null) {
			message = String.format("%s -> %s", getExceptionMessage(ex),message);
		}
		return message;
	}

	private static String getExceptionName(Throwable ex) {
		// TODO Auto-generated method stub
		String name = ex.getClass().toString();
		ex = ex.getCause();
		if (ex != null) {
			name = String.format("%s -> %s", getExceptionName(ex),name);
		}
		return name;
	}

	private static String getSuidingStackTraceInfo(Throwable ex) {
		// TODO Auto-generated method stub
		String TraceInfo = "";
		for (StackTraceElement stack : ex.getStackTrace()) {
			if (stack.getClassName().startsWith("com.suiding.")) {
				TraceInfo += stack.toString() + "\r\n";
			}
		}
		if(TraceInfo.length() > 0){
			return TraceInfo;
		}
		if (ex.getCause() != null) {
			return getStackTraceInfo(ex.getCause());
		}
		return getStackTraceInfo(ex);
	}
	
	private static String getStackTraceInfo(Throwable ex) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		for (StackTraceElement stack : ex.getStackTrace()) {
			builder.append(stack);
			builder.append("\r\n");
		}
		String TraceInfo = builder.toString();
		ex = ex.getCause();
		if (ex != null) {
			String format = "%s\r\n----------------------------------\r\n%s";
			TraceInfo = String.format(format,TraceInfo,getStackTraceInfo(ex));
		}
		return TraceInfo;
	}
	
	private static class ExceptionHandlerTask extends TaskBase{

		public ExceptionHandler mHandler = null;
		public ExceptionCache mExceptionCache = new ExceptionCache();
		
		public ExceptionHandlerTask(ExceptionHandler handler) {
			// TODO Auto-generated constructor stub
			mHandler = handler;
		}
		
		@Override
		protected void onWorking(Message msg) throws Exception {
			// TODO Auto-generated method stub
			//如果存在网络 发送信息
			if(SuidingApp.getNetworkStatus() != NetworkUtil.TYPE_NONE){
				IExceptionHandlerDomain domain = DomainFactory.getExceptionHandlerDomain();
				domain.Insert(mHandler);
				try {
					Set<ExceptionHandler> sethanlder = mExceptionCache.getExceptionHandlerSet(null);
					for (ExceptionHandler handler : sethanlder) {
						domain.Insert(handler);
					}
					sethanlder.clear();
					mExceptionCache.putExceptionHandlerSet(sethanlder);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}else{
				throw new Exception("");
			}
		}
		
		@Override
		protected void onException(Exception e) {
			// TODO Auto-generated method stub
			mExceptionCache.addExceptionHandlerSet(mHandler);
		}
	}
}
