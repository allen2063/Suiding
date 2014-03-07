package com.suiding.application;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.suiding.activity.R;

public class NotifyCenter{

	public static final int ID_ORDER = 1000;
	
	private static NotificationManager mManager = null;
	
//	private static Handler mHandler = new Handler(SuidingApp.getLooper()){
//		@Override
//		public void handleMessage(Message msg) {
//			// TODO Auto-generated method stub
//			String context = "你有"+msg.toString()+"条订单已处理";
////			SuidingApp app = SuidingApp.getApp();
//			Intent intent = null;
////			if(app.getIsForegroundRunning()){
//				intent = new Intent(SuidingApp.getApp(),ListOrderActivity.class);
////			}else{
////				intent = new Intent(SuidingApp.getApp(),IndexMainActivity.class);
////				
////			}
//			
//			intent.putExtra(ListOrderActivity.EXTRA_BL_NOTFY, true);
//			Notification notify = NotifyCenter.getNotification(context, intent);
//			NotifyCenter.notify(notify, NotifyCenter.ID_ORDER);
//			mHandler.sendMessageDelayed(Message.obtain(),10000);
//		}
//	}; 
	
	public static void initailize(Context context) {
		if(mManager == null){
			String service = Context.NOTIFICATION_SERVICE;
			mManager = (NotificationManager)context.getSystemService(service);
			//mHandler.sendMessageAtTime(Message.obtain(),SystemClock.uptimeMillis()+10000);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static Notification getNotification(String content,Intent intent)
	{
		SuidingApp app = SuidingApp.getApp();
		
		Notification notify = new Notification();
		notify.icon = R.drawable.suiding_logo;			//显示图标
		notify.flags|=Notification.FLAG_AUTO_CANCEL;	//自动终止
		notify.tickerText = app.getString(R.string.app_name);
		
		AppSettings setting = AppSettings.getInstance();
		if(setting.isNotifySound()){
			notify.defaults = Notification.DEFAULT_SOUND;
		}

		int piflag = PendingIntent.FLAG_UPDATE_CURRENT;
		PendingIntent pi = PendingIntent.getActivity(app , 0, intent,piflag);

		notify.setLatestEventInfo(app,notify.tickerText,content,pi);
		return notify;
	}

	public static void notify(Notification notify,int id) {
		notify.when = System.currentTimeMillis();		//显示时间
		AppSettings setting = AppSettings.getInstance();
		if(!setting.isNotifySound()){
			VibratorConsole.vibrator();
		}
		mManager.notify(id,notify);
	}

	public static void cancel(int id) {
		// TODO Auto-generated method stub
		mManager.cancel(id);
	}

}
