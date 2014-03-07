package com.suiding.activity;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.application.AppSettings;
import com.suiding.application.SuidingApp;
import com.suiding.application.UpdateService;
import com.suiding.caches.ImageCaches;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.service.FileService;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.VersionUtil;

public class SettingsActivity extends ActivityBase implements OnClickListener,
		Callback, DialogInterface.OnClickListener {

	private View mLlClearCache;
	private View mLlFeedBack;
	private View mLlAboutApp;
	private View mLlCheckUpdate;
	private View mLlHelpToPay;
	private View mLlNoPicture;
	private View mLlPromotion;
	private View mLlNotifySound;
	private CheckBox mCbNoPicture;
	private CheckBox mCbNotifySound;
	private CheckBox mCbPromotion;
	private TextView mTvCacheSize;

	private String mServersion = "0.0.0.0";

	private ModuleTitleOther mLayoutTitle = null;

	private float mCacheSize = 0;
	
	private AppSettings mAppSettings = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_settings);

		mLayoutTitle = new ModuleTitleOther(this);

		mLlAboutApp = findViewById(R.id.settings_aboutapp);
		mLlFeedBack = findViewById(R.id.settings_feedback);
		mLlHelpToPay = findViewById(R.id.settings_helptopay);
		mLlClearCache = findViewById(R.id.settings_clear_cache);
		mLlCheckUpdate = findViewById(R.id.settings_chectupdate);
		mLlPromotion = findViewById(R.id.settings_promotion);
		mLlNoPicture = findViewById(R.id.settings_nopicture);
		mLlNotifySound = findViewById(R.id.settings_notifysound);

		mCbNoPicture = (CheckBox) findViewById(R.id.settings_cb_nopicture);
		mCbNotifySound = (CheckBox) findViewById(R.id.settings_cb_notifysound);
		mCbPromotion = (CheckBox) findViewById(R.id.settings_cb_promotion);

		mTvCacheSize = (TextView) findViewById(R.id.settings_cache_size);

		mCbNoPicture.setOnClickListener(this);
		mCbNotifySound.setOnClickListener(this);
		mCbPromotion.setOnClickListener(this);

		mLlFeedBack.setOnClickListener(this);
		mLlAboutApp.setOnClickListener(this);
		mLlHelpToPay.setOnClickListener(this);
		mLlClearCache.setOnClickListener(this);
		mLlCheckUpdate.setOnClickListener(this);
		mLlPromotion.setOnClickListener(this);
		mLlNoPicture.setOnClickListener(this);
		mLlNotifySound.setOnClickListener(this);

		mLayoutTitle.setTitle(R.string.title_activity_setting);

		this.initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		mAppSettings = AppSettings.getInstance(this);
		
		//读取缓存大小显示到页面
		this.updateCachesSize();

		mCbNoPicture.setChecked(mAppSettings.isNoImage());
		mCbNotifySound.setChecked(mAppSettings.isNotifySound());
		mCbPromotion.setChecked(mAppSettings.isIsReceive());
	}

	private void updateCachesSize() {
		// TODO Auto-generated method stub
		ImageCaches cache = ImageCaches.getInstance(this);
		mCacheSize = cache.getCachesSize();
		if (mCacheSize < 1024) {
			mTvCacheSize.setText((int) mCacheSize + "字节");
		} else if (mCacheSize < 1024 * 1024) {
			mTvCacheSize.setText((int) (mCacheSize / 1024) + "KB");
		} else if (mCacheSize < 1024 * 1024 * 1024) {
			mTvCacheSize.setText((int) (mCacheSize / 1024 / 1024) + "MB");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.settings_nopicture:
			mCbNoPicture.setChecked(!mCbNoPicture.isChecked());
		case R.id.settings_cb_nopicture:
			mAppSettings.setNoImage(mCbNoPicture.isChecked());
			mAppSettings.commit();
			break;
		case R.id.settings_notifysound:
			mCbNotifySound.setChecked(!mCbNotifySound.isChecked());
		case R.id.settings_cb_notifysound:
			mAppSettings.setNotifySound(mCbNotifySound.isChecked());
			mAppSettings.commit();
			break;
		case R.id.settings_promotion:
			mCbPromotion.setChecked(!mCbPromotion.isChecked());
		case R.id.settings_cb_promotion:
			mAppSettings.setIsReceive(mCbPromotion.isChecked());
			mAppSettings.commit();
			break;
		case R.id.settings_clear_cache:
			if (mCacheSize > 0) {
				postTask(new SettingsTask(SettingsTask.TASK_CLRARCACHES));
				showProgressDialog("正在清除缓存...", true);
			}
			break;
		case R.id.settings_helptopay:
			startActivity(new Intent(this, HelperActivity.class));
			break;
		case R.id.settings_chectupdate:
			TaskBase task = new SettingsTask(SettingsTask.TASK_CHECKUPDATE);
			showProgressDialog("正在检测...",postTask(task));
			break;
		case R.id.settings_aboutapp:
			startActivity(new Intent(this, AboutActivity.class));
			break;
		case R.id.settings_feedback:
			startActivity(new Intent(this, FeedBackActivity.class));
			break;
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		SettingsTask tTask = (SettingsTask) msg.obj;
		if (tTask.mResult == TaskBase.RESULT_FINISH) {
			switch (tTask.mTask) {
			case SettingsTask.TASK_CLRARCACHES:
				Toast.makeText(this, "清除缓存成功", Toast.LENGTH_SHORT).show();
				updateCachesSize();
				break;
			case SettingsTask.TASK_CHECKUPDATE:
				String version = SuidingApp.getVersion();
				int curversion = VersionUtil.transformVersion(version);
				int serversion = VersionUtil.transformVersion(tTask.serversion);
				if (curversion >= serversion) {
					Toast.makeText(this, "恭喜你，当前已经是最新版本。", Toast.LENGTH_SHORT)
							.show();
				} else {
					mServersion = tTask.serversion;
					Builder builder = new Builder(this);
					builder.setIcon(android.R.drawable.ic_dialog_info);
					builder.setTitle("可用更新");
					builder.setMessage("系统检查到可用更新" + "\r\n    更新版本："
							+ tTask.serversion + "\r\n    当前版本：" + version);
					builder.setPositiveButton("下载更新", this);
					builder.setNegativeButton("暂不更新",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							});
					builder.create();
					builder.show();
				}
			}
		} else if (tTask.mResult == TaskBase.RESULT_FAIL) {
			//Toast.makeText(this, tTask.mErrors, Toast.LENGTH_LONG).show();
		}
		hideProgressDialog();
		return false;
	}

	private class SettingsTask extends TaskBase {
		// Task 执行类型枚举
		public static final int TASK_CLRARCACHES = 0;
		public static final int TASK_CHECKUPDATE = 1;

		public String serversion = "0.0.0.0";

		public SettingsTask(int task) {
			super(new Handler(SettingsActivity.this), task);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onWorking(Message tMessage) throws Exception {
			// TODO Auto-generated method stub
			switch (mTask) {
			case TASK_CLRARCACHES:
				ImageCaches cache = ImageCaches
						.getInstance(SettingsActivity.this);
				cache.clear();
				break;
			case TASK_CHECKUPDATE:
				serversion = FileService.getLastApkVersion();
				break;

			}
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		String url = FileService.getApkUrl(mServersion);
		UpdateService.startDownLoadUpate(this, url, mServersion);
	}

}
