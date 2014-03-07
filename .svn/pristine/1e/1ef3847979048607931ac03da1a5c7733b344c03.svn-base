package com.suiding.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.suiding.activity.BirthdayInviteActivity;
import com.suiding.activity.BirthdayRecordActivity;
import com.suiding.activity.BirthdayRemindActivity;
import com.suiding.activity.EditAccountActivity;
import com.suiding.activity.ListFavoriteActivity;
import com.suiding.activity.ListOrderActivity;
import com.suiding.activity.MyQuickMark;
import com.suiding.activity.R;
import com.suiding.activity.SettingsActivity;
import com.suiding.activity.UserLoginActivity;
import com.suiding.application.ImageService;
import com.suiding.application.OrderConsole;
import com.suiding.application.SuidingApp;
import com.suiding.application.SuidingApp.INotifyFixedCity;
import com.suiding.application.SuidingApp.INotifyLoginUser;
import com.suiding.constant.ImageFolderEnum;
import com.suiding.constant.ImageSize;
import com.suiding.dao.FavoriteEntityDao;
import com.suiding.domain.IOrderDomain;
import com.suiding.domain.IUserDomain;
import com.suiding.entity.FavoriteEntity;
import com.suiding.fragment.framework.FragmentBase;
import com.suiding.layoutbind.ModuleTitleMain;
import com.suiding.model.Area;
import com.suiding.model.Consumer;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.thread.SmoothRunnable;
import com.suiding.thread.SmoothRunnable.Smoothable;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.ExtraUtil;
import com.suiding.util.MeasureUtil;
import com.suiding.util.XmlCacheUtil;
import com.suiding.widget.PullDownLinearLayout;
import com.suiding.widget.PullDownLinearLayout.ReadyForPullDownable;

public class IndexAccountOldFragment extends FragmentBase implements
		OnClickListener, INotifyLoginUser, Smoothable, INotifyFixedCity,
		Callback, ReadyForPullDownable {

	private static final int REQUEST_INFO = 0;
	private static final int REQUEST_LOGIN = 1;
	private static final int REQUEST_FAVRITE = 2;

	private static final String CACHE_NUMWAITING = "Account_CACHE_NUMWAITING";
	private static final String CACHE_NUMCONFIRM = "Account_CACHE_NUMCONFIRM";
	private static final String CACHE_NUMCANCEL = "Account_CACHE_NUMCANCEL";

	private View mRlWaiting; // 等待确认
	private View mRlConfirmed; // 成功预定
	private View mRlCanceled; // 拒绝预定
	private View mRlFavorite; // 我的收藏
	private View mRlQRcode; // 我的二维码
	private View mRlLogout; // 退出账户
	private View mRlLogin; // 用户登录
	private View mRlSetting; // 退出账户
	private View mBirRecord;// 生日记录
	private View mBirRemind;// 生日提醒
	private View mBirInvite;// 生日邀请

	private TextView mTvConfirmNum; // 成功预定
	private TextView mTvWaitingNum; // 等待确认
	private TextView mTvRefusedNum; // 拒绝预定

	private TextView mTvFavoriteNum = null;

	private ImageView mIvHeader; // 头像
	private TextView mTvNickName; // 昵称
	private TextView mTvAutoGraph; // 签名
	private TextView mTvPraiseNumber; // 被赞次数

	private ScrollView mScrollView; //

	private View mLayoutInfo; // 性息面板
	private View mLayoutLogin; // 禁用面板
	private PullDownLinearLayout mLayout; // 面板

	private int mPaddingTopInfo = 0;
	private int mPaddingTopLogin = 0;

	private long mConfirmNum; // 成功预定
	private long mWaitingNum; // 等待确认
	private long mRefusedNum; // 拒绝预定
	private boolean mIsCounted = false;

	private ModuleTitleMain mLayoutTitle = null;
	private SmoothRunnable mSmooth = null;

	private Consumer mConsumer = null;

	@Override
	protected final View onCreateView(LayoutInflater inflater, ViewGroup container) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.index_accountold, container, false);
	}
	@Override
	protected final void onCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		mLayoutTitle = new ModuleTitleMain(this);

		mRlWaiting = findViewById(R.id.account_waitconfirm);
		mRlConfirmed = findViewById(R.id.account_orderconfirm);
		mRlCanceled = findViewById(R.id.account_refuseconfirm);
		mRlFavorite = findViewById(R.id.account_favorite);
		mRlQRcode = findViewById(R.id.account_myqrcode);
		mRlLogout = findViewById(R.id.account_logout);
		mRlLogin = findViewById(R.id.account_login);
		mRlSetting = findViewById(R.id.account_setting);

		mBirRecord = findViewById(R.id.account_birthrecord);
		mBirRemind = findViewById(R.id.account_birthremind);
		mBirInvite = findViewById(R.id.account_birthinvite);

		mLayoutInfo = findViewById(R.id.account_layout_info);
		mLayoutLogin = findViewById(R.id.account_layout_login);
		mLayout = (PullDownLinearLayout)findViewById(R.id.account_layout);
		
		Resources res = getResources();
		float height = res.getDimension(R.dimen.home_pulldownheight);
		mLayout.setPullDownable(this);
		mLayout.setMaxScorllHeight((int)height);

		mTvRefusedNum = findTextViewById(R.id.account_refusenum);
		mTvConfirmNum = findTextViewById(R.id.account_successnum);
		mTvWaitingNum = findTextViewById(R.id.account_waitconfirmnum);

		mPaddingTopInfo = -MeasureUtil.measureView(mLayoutInfo).y;
		mPaddingTopLogin = -MeasureUtil.measureView(mLayoutLogin).y;

		if (SuidingApp.getLoginUser() == null) {
			mLayout.setPadding(0, mPaddingTopInfo, 0, 0);
			mLayoutLogin.setPadding(0, mPaddingTopLogin, 0, 0);
		}

		mIvHeader = findImageViewById(R.id.account_iv_headicon);
		mTvNickName = findTextViewById(R.id.account_tv_nickname);
		mTvAutoGraph = findTextViewById(R.id.account_tv_autograph);
		mTvPraiseNumber = findTextViewById(R.id.account_tv_praisenumber);
		mTvFavoriteNum = findTextViewById(R.id.account_favoritenum);
		mScrollView = findScrollViewById(R.id.account_scrollview);

		mRlWaiting.setOnClickListener(this);
		mRlConfirmed.setOnClickListener(this);
		mRlCanceled.setOnClickListener(this);
		mRlFavorite.setOnClickListener(this);
		mRlQRcode.setOnClickListener(this);
		mTvPraiseNumber.setOnClickListener(this);
		mRlLogin.setOnClickListener(this);
		mRlLogout.setOnClickListener(this);
		mIvHeader.setOnClickListener(this);
		mRlSetting.setOnClickListener(this);
		mBirRecord.setOnClickListener(this);
		mBirRemind.setOnClickListener(this);
		mBirInvite.setOnClickListener(this);

		onLoginUserChanged(SuidingApp.getLoginUser());
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case REQUEST_INFO:
			case REQUEST_LOGIN:
				onLoginUserChanged(SuidingApp.getLoginUser());
				break;
			case REQUEST_FAVRITE:
				postTask(new AccountTask(AccountTask.TASK_FAVORITE));
				break;
			}
		}
	}

	@Override
	public void onFixedCityChanged(Area area, int status) {
		// TODO Auto-generated method stub
		mLayoutTitle.setCityName(SuidingApp.getApp().getCityName());
	}

	@Override
	public void onLoginUserChanged(Consumer user) {
		// TODO Auto-generated method stub
		mConsumer = user;
		if (user != null) {
			mTvNickName.setText(mConsumer.NickName);
			if (user.Qianming == null || user.Qianming.length() == 0) {
				mTvAutoGraph.setText("这家伙真的很懒，什么都没没有留下~~");
			} else {
				mTvAutoGraph.setText(mConsumer.Qianming);
			}
			mTvPraiseNumber.setText("" + mConsumer.GoodTimes);

			ImageService.bindImage(mConsumer.HeadImg, mIvHeader,
					ImageFolderEnum.HEAD_USER, ImageSize.SMALLER,
					R.drawable.image_person);

			postTask(new AccountTask(AccountTask.TASK_ORDERCOUNT));
		}
		postTask(new AccountTask(AccountTask.TASK_FAVORITE));
		smoothScrollLayout(mConsumer != null);
	}

	@SuppressLint("NewApi")
	private final void smoothScrollLayout(boolean visiable) {
		if (null != mSmooth) {
			mSmooth.stop();
		}
		int from = mLayout.getPaddingTop();
		int to = visiable ? 0 : mPaddingTopInfo;
		if (from != to) {
			mSmooth = new SmoothRunnable(this, from, to, 500);
			mSmooth.start();
		} else {
			mLayoutLogin.setPadding(0, visiable ? 0 : mPaddingTopLogin, 0, 0);
		}
		if (VERSION.SDK_INT >= 14) {
			mScrollView.setScrollY(0);
		}
		mRlLogin.setVisibility(visiable ? View.GONE : View.VISIBLE);
		mRlLogout.setVisibility(!visiable ? View.GONE : View.VISIBLE);
	}

	@Override
	public void onStart(int from, int to) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onFinish(int from, int to) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public final boolean onSmooth(int value, float percentt, int from, int to) {
		// TODO Auto-generated method stub
		float percent = Math.abs((float) (value - from))
				/ Math.abs((float) (to - from));
		mLayout.setPadding(0, value, 0, 0);
		if (from < to) {
			mLayoutLogin.setPadding(0,
					(int) (mPaddingTopLogin * (1 - percent)), 0, 0);
		} else {
			mLayoutLogin.setPadding(0, (int) (mPaddingTopLogin * (percent)), 0,
					0);
		}
		return true;
	}

	@Override
	public boolean isReadyForPullDown() {
		// TODO Auto-generated method stub
		return mConsumer!=null&&mScrollView.getScrollY() <= 0;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		Intent intent = new Intent();
		String extra = ListOrderActivity.EXTRA_EM_FILTE;
		switch (v.getId()) {
		case R.id.account_tv_praisenumber:
			// ss = "点击了“赞”";
			break;
		case R.id.account_waitconfirm:
			ExtraUtil.putExtra(extra, ListOrderActivity.FILTE_WAITING);
			intent.setClass(mActivity, ListOrderActivity.class);
			this.startActivity(intent);
			break;
		case R.id.account_orderconfirm:
			ExtraUtil.putExtra(extra, ListOrderActivity.FILTE_CONFIRM);
			intent.setClass(mActivity, ListOrderActivity.class);
			this.startActivity(intent);
			break;
		case R.id.account_refuseconfirm:
			ExtraUtil.putExtra(extra, ListOrderActivity.FILTE_CANCEL);
			intent.setClass(mActivity, ListOrderActivity.class);
			this.startActivity(intent);
			break;
		case R.id.account_favorite:
			intent.setClass(mActivity, ListFavoriteActivity.class);
			this.startActivityForResult(intent, REQUEST_FAVRITE);
			break;
		case R.id.account_myqrcode:
			intent.setClass(mActivity, MyQuickMark.class);
			this.startActivity(intent);
			break;
		case R.id.account_login:
			intent.setClass(mActivity, UserLoginActivity.class);
			this.startActivityForResult(intent, REQUEST_LOGIN);
			break;
		case R.id.account_setting:
			intent.setClass(mActivity, SettingsActivity.class);
			this.startActivity(intent);
			break;
		case R.id.account_logout:
			SuidingApp.getApp().setLoginUser(this, null);
			break;
		case R.id.account_iv_headicon:
			ExtraUtil.putExtra(EditAccountActivity.EXTRA_OB_CONSUMER, mConsumer);
			intent.setClass(mActivity, EditAccountActivity.class);
			this.startActivityForResult(intent, REQUEST_INFO);
			break;
		case R.id.account_birthinvite:
			intent.setClass(mActivity, BirthdayInviteActivity.class);
			startActivity(intent);
			break;
		case R.id.account_birthrecord:
			intent.setClass(mActivity, BirthdayRecordActivity.class);
			intent.putExtra("ACTION", IndexAttentioinFragment.BIRRECORD);
			startActivity(intent);
			break;
		case R.id.account_birthremind:
			intent.setClass(mActivity, BirthdayRemindActivity.class);
			intent.putExtra("ACTION", IndexAttentioinFragment.BIRREMIND);
			startActivity(intent);
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		AccountTask task = (AccountTask) msg.obj;
		if (task.mResult == TaskBase.RESULT_FINISH) {
			switch (task.mTask) {
			case AccountTask.TASK_FAVORITE:
				mTvFavoriteNum.setText("" + task.mltFavorite.size());
				break;
			case AccountTask.TASK_ORDERCOUNT:
				mTvConfirmNum.setText("" + mConfirmNum);
				mTvWaitingNum.setText("" + mWaitingNum);
				mTvRefusedNum.setText("" + mRefusedNum);
				break;
			}
		}
		return false;
	}

	private class AccountTask extends TaskBase {

		public static final int TASK_FAVORITE = 0;
		public static final int TASK_ORDERCOUNT = 1;

		public List<StoreBase> mltFavorite = new ArrayList<StoreBase>();

		public AccountTask(int task) {
			super(new Handler(IndexAccountOldFragment.this), task);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onWorking(Message tMessage) throws Exception {
			// TODO Auto-generated method stub
			Consumer user = SuidingApp.getLoginUser();
			switch (mTask) {
			case TASK_FAVORITE:
				FavoriteEntityDao dao = new FavoriteEntityDao(mActivity);
				if (user != null) {
					IUserDomain domain = DomainFactory.getUserDomain();
					mltFavorite = domain.getFavorateStoreByUserID(user.getID());
					if (mltFavorite.size() > dao.getCount()) {
						dao.updateCache(FavoriteEntity
								.listFormModel(mltFavorite));
					}
				} else {
					mltFavorite = FavoriteEntity.listToModel(dao.getAll());
				}
				break;
			case TASK_ORDERCOUNT:
				if(!mIsCounted){
					UUID id = mConsumer.getID();
					IOrderDomain domain = DomainFactory.getOrderDomain();

					mRefusedNum = XmlCacheUtil.getLong(CACHE_NUMCANCEL, 0);
					mWaitingNum = XmlCacheUtil.getLong(CACHE_NUMWAITING, 0);
					mConfirmNum = XmlCacheUtil.getLong(CACHE_NUMCONFIRM, 0);
					
					mConfirmNum = domain.GetCountByFilters(id,OrderConsole.FILTES_CONFIRM);
					mWaitingNum = domain.GetCountByFilters(id,OrderConsole.FILTES_WAITING);
					mRefusedNum = domain.GetCountByFilters(id,OrderConsole.FILTES_CANCEL);

					XmlCacheUtil.putLong(CACHE_NUMCANCEL, mRefusedNum);
					XmlCacheUtil.putLong(CACHE_NUMWAITING, mWaitingNum);
					XmlCacheUtil.putLong(CACHE_NUMCONFIRM, mConfirmNum);
					
					mIsCounted = true;
				}
				break;
			}
		}

	}
}
