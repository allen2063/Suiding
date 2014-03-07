package com.suiding.application;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.widget.RemoteViews;

import com.suiding.activity.R;
import com.suiding.service.FileService;
import com.suiding.thread.framework.TaskBase;


public class UpdateService implements Callback
{
    private RemoteViews mRemoteViews;
    private Notification mNotification;
    private NotificationManager mManager;

    private Context mContext = null;

    private int notificationId = 1234;

    public UpdateService(Context context)
    {
        // TODO Auto-generated constructor stub
        mContext = context;
        initialize(context);
    }

    private void initialize(Context context)
    {
        //设置任务栏中下载进程显示的views
        mRemoteViews = new RemoteViews(context.getPackageName(),
                R.layout.taskbar_update);

        mNotification = new Notification();
        mNotification.icon = android.R.drawable.stat_sys_download;
        //notification.icon=android.R.drawable.stat_sys_download_done;
        mNotification.tickerText = context.getString(R.string.app_name) + "更新";
        mNotification.when = System.currentTimeMillis();
        mNotification.defaults = Notification.DEFAULT_LIGHTS;

        mNotification.contentView = mRemoteViews;

        //        PendingIntent Intent = PendingIntent.getActivity(this, 0,
        //                new Intent(this, City.class), 0);
        //        mNotification.setLatestEventInfo(this, "", "", Intent);

        //将下载任务添加到任务栏中

        //        myHandler = new MyHandler(Looper.myLooper(), this);
        //
        //        //初始化下载任务内容views
        //        Message message = myHandler.obtainMessage(3, 0);
        //        myHandler.sendMessage(message);

        //启动线程开始执行下载任务
        //        this.downFile(url);

    }

    @Override
    public boolean handleMessage(Message msg)
    {
        // TODO Auto-generated method stub
        DownloadTask task = (DownloadTask) msg.obj;
        if (task.mResult == TaskBase.RESULT_FINISH)
        {
            if (task.mTask == DownloadTask.TASK_UPDATEPROGRESS)
            {
                //更新状态栏上的下载进度信息
                mRemoteViews.setTextViewText(R.id.taskbar_tvProcess, "已下载"
                        + task.mPrecent + "%");
                mRemoteViews.setProgressBar(R.id.taskbar_pbDownload, 100,
                        task.mPrecent, false);
                mNotification.contentView = mRemoteViews;
                mManager.notify(notificationId, mNotification);
            }
            else if (task.mTask == DownloadTask.TASK_DOWNLOADFINISH)
            {
                mManager.cancel(notificationId);
                Instanll(task.mTempFile, mContext);
            }
        }
        else if (task.mResult == TaskBase.RESULT_FAIL)
        {
            mManager.cancel(notificationId);
        }
        return false;
    }

    public static void startDownLoadUpate(Context context, String url,
            String version)
    {
        String path = SuidingApp.getApp().getWorkspacePath("update");
        File upadate = new File(path + "/" + version + ".apk");
        if (upadate.exists() == false)
        {
            UpdateService tUpdateService = new UpdateService(context);
            tUpdateService.start(context, url, version);
        }
        else
        {
            Instanll(upadate, context);
        }
    }

    private void start(Context context, String url, String version)
    {
        // TODO Auto-generated method stub
        String path = SuidingApp.getApp().getWorkspacePath("update");
        Background.postTask(new DownloadTask(url, path, version + ".apk"));

        String server = Context.NOTIFICATION_SERVICE;
        mManager = (NotificationManager) context.getSystemService(server);
        mManager.notify(notificationId, mNotification);
    }

    //安装下载后的apk文件
    private static void Instanll(File file, Context context)
    {
        try
        {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
            context.startActivity(intent);
        }
        catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();//handled
			AppExceptionHandler.handler(e, "更新服务，Instanll 调用安装失败");
        }
    }

	public static void checkUpdate() {
		// TODO Auto-generated method stub
		SuidingApp.postTask(new CheckUpdateTask());
	}

	public static class CheckUpdateTask extends TaskBase implements OnClickListener {

		public static String mServersion = "0.0.0.0";
		
		public CheckUpdateTask() {
			super(SuidingApp.getLooper());
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onWorking(Message tMessage) throws Exception {
			// TODO Auto-generated method stub
			if(mServersion.equals("0.0.0.0")){
				mServersion = FileService.getLastApkVersion();
			}
		}
		
		@Override
		protected boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if(mResult == TaskBase.RESULT_FINISH){
				SuidingApp app = SuidingApp.getApp();
				app.setServerVersion(this, mServersion);
			}
			return super.onHandle(msg);
		}
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			String url = FileService.getApkUrl(mServersion);
			startDownLoadUpate(SuidingApp.getApp(), url, mServersion);
		}
	}

    private class DownloadTask extends TaskBase
    {
        private static final int TASK_UPDATEPROGRESS = 1;
        private static final int TASK_DOWNLOADFINISH = 2;

        public int mPrecent = 0;
        public File mTempFile = null;
        public String mDownloadUrl = null;
        public String mDownloadPath = null;
        public String mDownloadFile = null;

        public DownloadTask(String url, String path, String file)
        {
            super(new Handler(UpdateService.this));
            // TODO Auto-generated constructor stub
            mDownloadUrl = url;
            mDownloadPath = path;
            mDownloadFile = file;
        }

        @Override
        protected void onWorking(Message tMessage) throws Exception
        {
            // TODO Auto-generated method stub
            mTask = TASK_UPDATEPROGRESS;
            mHandler.sendMessage(mHandler.obtainMessage(mResult, this));

            HttpGet get = new HttpGet(mDownloadUrl);
            HttpResponse response = new DefaultHttpClient().execute(get);
            HttpEntity entity = response.getEntity();

            InputStream is = entity.getContent();
            File path = new File(mDownloadPath);
            if (!path.exists() && !path.isDirectory())
            {
                path.mkdir();
            }

            mTempFile = new File(path, mDownloadFile);
            if (mTempFile.exists())
            {
                mTempFile.delete();
            }
            mTempFile.createNewFile();

            //创建一个新的写入流，讲读取到的图像数据写入到文件中
            FileOutputStream fos = new FileOutputStream(mTempFile);
            //已读出流作为参数创建一个带有缓冲的输出流
            BufferedInputStream bis = new BufferedInputStream(is);
            //已写入流作为参数创建一个带有缓冲的写入流
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            int read = 0, precent = 0;
            byte[] buffer = new byte[1024];
            long count = 0, length = entity.getContentLength();
            while ((read = bis.read(buffer)) != -1 && !mIsCanceled)
            {
                count += read;
                bos.write(buffer, 0, read);
                precent = (int) (((double) count / length) * 100);

                //每下载完成5%就通知任务栏进行修改下载进度
                if (precent - mPrecent >= 5)
                {
                    mPrecent = precent;
                    mHandler.sendMessage(mHandler.obtainMessage(mResult, this));
                }
            }
            bos.flush();
            fos.flush();
            bos.close();
            fos.close();
            bis.close();
            is.close();

            if (mIsCanceled)
            {
                mTempFile.delete();
            }
            mTask = TASK_DOWNLOADFINISH;
        }

    }
}
