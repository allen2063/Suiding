package com.suiding.activity;

import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.suiding.activity.framework.FragmentActivityBase;
import com.suiding.application.OrderConsole.INotifyRemindOrder;
import com.suiding.application.SuidingApp;
import com.suiding.application.SuidingApp.INotifyFixedCity;
import com.suiding.application.SuidingApp.INotifyNeedUpdate;
import com.suiding.application.SuidingApp.INotifyNetworkStatus;
import com.suiding.application.UpdateService;
import com.suiding.constant.FixedCityEnum;
import com.suiding.fragment.IndexAccountFragment;
import com.suiding.fragment.IndexHomeFragment;
import com.suiding.fragment.IndexNearbyFragment;
import com.suiding.fragment.IndexTwitterFragment;
import com.suiding.fragment.framework.FragmentBase;
import com.suiding.model.Area;
import com.suiding.model.Order;
import com.suiding.service.FileService;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.NetworkUtil;
import com.suiding.util.VersionUtil;

public class IndexMainActivity extends FragmentActivityBase implements
		CompoundButton.OnCheckedChangeListener, OnPageChangeListener,
		INotifyRemindOrder,INotifyNeedUpdate,
		OnClickListener, INotifyNetworkStatus, Callback, INotifyFixedCity{

	private int mRadioButtonIds[] = new int[] { R.id.rb_home, R.id.rb_location,
			R.id.rb_attention, R.id.rb_account };

	private RadioButton mRadioButtons[] = new RadioButton[mRadioButtonIds.length];

	private TextView mTvCityName;
	private TextView mTvNotifyManage;

	private long mExitTime;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private PagerAdapter mPagerAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_main);
		//���ж��Ƿ��ɶ���֪ͨ����ҳ��
		Intent intent = getIntent();
		if(intent != null){
			//����Ƕ�������
			if(intent.getBooleanExtra("EXTRA_BL_ISNOTFY", false)){
				//���û����Ļ�ȡ�ı�Ķ���
				intent = new Intent(this, ListOrderActivity.class);
				startActivity(intent);
			}
		}
		//ExceptionCache mExceptionCache = new ExceptionCache();
		//Exception ex1 = new Exception("�����쳣1");
		//Exception ex2 = new Exception("�����쳣2");
		//ExceptionHandler handler1 = AppExceptionHandler.getHandler(ex1, "���Ա�ע1");
		//ExceptionHandler handler2 = AppExceptionHandler.getHandler(ex2, "���Ա�ע2");
		//mExceptionCache.addExceptionHandlerSet(handler1);
		//mExceptionCache.addExceptionHandlerSet(handler2);
		//AppExceptionHandler.handler(ex1, "���Ա�ע1");
		//AppExceptionHandler.handler(ex2, "���Ա�ע2");

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), this);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(this);
		
		this.mTvNotifyManage = findTextViewById(R.id.index_notifymanage);
		this.mTvNotifyManage.setVisibility(View.GONE);

		this.mTvCityName = ((TextView) findViewById(R.id.title_cityname));
		this.mTvCityName.setOnClickListener(this);

		for (int i = 0; i < mRadioButtonIds.length; i++) {
			mRadioButtons[i] = (RadioButton) findViewById(mRadioButtonIds[i]);
			mRadioButtons[i].setOnCheckedChangeListener(this);
		}

		SuidingApp app = SuidingApp.getApp();
		// �������״̬��Ч
		if (SuidingApp.getNetworkStatus() == NetworkUtil.TYPE_NONE) {
			if (!app.isInitialize()) {
				app.initialize(this);
				Toast.makeText(this, "�������޸�", Toast.LENGTH_SHORT).show();
			} else {
				// ��ʾ���粻���öԻ���
				showNetworkInAvailable();
			}
		}else if(app.isNeedUpdate()){
			// ��ʾȥҪ���¶Ի���
			showNeedUpdate();
		}

		mTvCityName.setText(SuidingApp.getApp().getCityName());

		// �����λʧ��
		if (app.getFixedCityStatus() == FixedCityEnum.FAIL) {
			// ��ת���ֶ���λҳ��
			startActivity(new Intent(this, FixedCityActivity.class));
			// ������ʾ �ֶ�����
			Toast.makeText(this, R.string.fixed_need_hand_movement,
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * ֪ͨAPP ǰ̨�Ѿ��ر�
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		SuidingApp app = SuidingApp.getApp();
		// ֪ͨAPP �����Ѿ��˳�
		app.notifyForegroundClosed(this);
		super.onDestroy();
	}
	
	@Override
	public void onNotifyNeedUpdate(String curver, String server) {
		// TODO Auto-generated method stub
		// ��ʾȥҪ���¶Ի���
		showNeedUpdate();
	}
	
	@Override
	public void onNetworkStatusChanged(int networkStatus) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFixedCityChanged(Area area, int status) {
		// TODO Auto-generated method stub
		mTvCityName.setText(SuidingApp.getApp().getCityName());
	}
	
	@Override
	public void onRemindOrderChanged(UUID userid, List<Order> ltremind) {
		// TODO Auto-generated method stub
		mTvNotifyManage.setText(""+ltremind.size());
		if(ltremind.size() > 0){
			mTvNotifyManage.setVisibility(View.VISIBLE);
		}else{
			mTvNotifyManage.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.title_cityname:
			Intent tIntent = new Intent();
			tIntent.putExtra(FixedCityActivity.EXTRA_NEEDFIXED, true);
			tIntent.setClass(this, FixedCityActivity.class);
			startActivity(tIntent);
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		int item = 0, id = buttonView.getId();
		if (isChecked) {
			for (int i : mRadioButtonIds) {
				if (i == id) {
//					ExceptionCache mExceptionCache = new ExceptionCache();
//					Set<ExceptionHandler> set = mExceptionCache.getExceptionHandlerSet(null);
//					if(set != null){
//						showToastLong("size = " + set.size());
//					}else{
//						showToastLong("set = null");
//					}
//					VibratorConsole.vibrator();
					mViewPager.setCurrentItem(item, true);
					return;
				}
				item++;
			}
		}
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				this.finish();
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageSelected(int currPage) {
		// TODO Auto-generated method stub
		mRadioButtons[currPage].setChecked(true);
	}

	/**
	 * Handler
	 */
	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated constructor stub
		IndexTask tTask = (IndexTask) msg.obj;
		if (tTask.mResult == IndexTask.RESULT_FINISH) {
		} else if (tTask.mResult == IndexTask.RESULT_FAIL) {
			//Toast.makeText(this, tTask.mErrors,Toast.LENGTH_SHORT).show();
		}
		return true;
	}

	private class IndexTask extends TaskBase {

		@Override
		protected void onWorking(Message tMessage) throws Exception {
			// TODO Auto-generated method stub
		}

	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class PagerAdapter extends FragmentPagerAdapter {

		private Activity mContext = null;

		public PagerAdapter(FragmentManager fm, Activity context) {
			super(fm);
			this.mContext = context;
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			FragmentBase fragment = null;
			switch (position) {
			case 0:
				// fragment = new IndexAccountFragment();
				fragment = new IndexHomeFragment();
				break;
			case 1:
				// fragment = new IndexAccountFragment();
				fragment = new IndexNearbyFragment();
				break;
			case 2:
				// fragment = new IndexAccountFragment();
				fragment = new IndexTwitterFragment();
				break;
			case 3:
				fragment = new IndexAccountFragment();
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return "";
		}

		public Activity getContext() {
			// Show 3 total pages.
			return mContext;
		}
	}

	/**
	 * ��ʾ���粻���öԻ���
	 */
	private void showNetworkInAvailable() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle("��ǰ���粻����");
		builder.setMessage("��������������Ϣ���������������ӡ�");
		builder.setNegativeButton("��������",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// ���û���������ӣ�������������ý���
						startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
					}
				});
		builder.setPositiveButton("���������Ϣ",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		builder.create();
		builder.show();
	}


	private void showNeedUpdate() {
		// TODO Auto-generated method stub
		String curversion = SuidingApp.getVersion();
		String serversion = SuidingApp.getApp().getServerVersion();
		int curver = VersionUtil.transformVersion(curversion);
		int server = VersionUtil.transformVersion(serversion);
		if (curver < server) {
			Builder builder = new Builder(this);
			builder.setIcon(android.R.drawable.ic_dialog_info);
			builder.setTitle("���ø���");
			builder.setMessage("ϵͳ��鵽���ø���\r\n    ���°汾��"
					+ serversion + "\r\n    ��ǰ�汾��" + curversion);
			builder.setPositiveButton("���ظ���", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String serversion = SuidingApp.getApp().getServerVersion();
					String url = FileService.getApkUrl(serversion);
					UpdateService.startDownLoadUpate(getContext(), url, serversion);
				}
			});
			builder.setNegativeButton("�ݲ�����",
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
}
