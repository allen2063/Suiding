package com.suiding.fragment;

import java.util.List;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.suiding.activity.BirthdayManageActivity;
import com.suiding.activity.EditAccountActivity;
import com.suiding.activity.ListPromotionActivity;
import com.suiding.activity.ListFavoriteActivity;
import com.suiding.activity.ListOrderActivity;
import com.suiding.activity.R;
import com.suiding.activity.SettingsActivity;
import com.suiding.activity.UserLoginActivity;
import com.suiding.application.AppSettings;
import com.suiding.application.ImageService;
import com.suiding.application.OrderConsole;
import com.suiding.application.OrderConsole.INotifyOrderNumber;
import com.suiding.application.OrderConsole.INotifyRemindOrder;
import com.suiding.application.SuidingApp;
import com.suiding.application.SuidingApp.INotifyFixedCity;
import com.suiding.application.SuidingApp.INotifyLoginUser;
import com.suiding.application.UserCenter;
import com.suiding.application.UserCenter.INotifyFavoriteNumber;
import com.suiding.constant.ImageFolderEnum;
import com.suiding.constant.ImageSize;
import com.suiding.fragment.framework.FragmentBase;
import com.suiding.layoutbind.ModuleTitleMain;
import com.suiding.model.Area;
import com.suiding.model.Consumer;
import com.suiding.model.Order;
import com.suiding.thread.SmoothRunnable;
import com.suiding.thread.SmoothRunnable.Smoothable;
import com.suiding.util.ExtraUtil;
import com.suiding.widget.PullDownLinearLayout;
import com.suiding.widget.PullDownLinearLayout.ReadyForPullDownable;

public class IndexAccountFragment extends FragmentBase implements
		OnClickListener, INotifyLoginUser, Smoothable, INotifyFixedCity,
		Callback, ReadyForPullDownable,INotifyOrderNumber,INotifyFavoriteNumber,
		INotifyRemindOrder{

	private static final int REQUEST_INFO = 0;
	private static final int REQUEST_LOGIN = 1;
	private static final int REQUEST_FAVRITE = 2;

//	private static final String CACHE_NUMWAITING = "Account_CACHE_NUMWAITING";
//	private static final String CACHE_NUMCONFIRM = "Account_CACHE_NUMCONFIRM";
//	private static final String CACHE_NUMCANCEL = "Account_CACHE_NUMCANCEL";

	private View mRlOrderManage; // 等待确认
	private View mRlWaiting; // 等待确认
	private View mRlConfirmed; // 成功预定
	private View mRlCanceled; // 拒绝预定
	private View mRlFavorite; // 我的收藏
	private View mRlCoupon; // 我的收藏
	private View mRlLogin; // 用户登录
	private View mRlLogout; // 退出账户
	private View mRlSetting; // 退出账户
	private View mBirManage;// 生日记录
	private View mToRight;// 生日记录

	private TextView mTvManagerNum; // 订单管理
	private TextView mTvConfirmNum; // 成功预定
	private TextView mTvWaitingNum; // 等待确认
	private TextView mTvRefusedNum; // 拒绝预定

	private TextView mTvFavoriteNum = null;
	private TextView mTvRemindOrderWaiting = null;
	private TextView mTvRemindOrderConfirm = null;
	private TextView mTvRemindOrderRefused = null;

	private ImageView mIvHeader; // 头像
	private TextView mTvNickName; // 昵称
	private TextView mTvAutoGraph; // 签名
	private TextView mTvPraiseNumber; // 被赞次数

	private ScrollView mScrollView; //

	private View mLayoutInfo; // 性息面板
	private PullDownLinearLayout mLayout; // 面板

//	private long mConfirmNum; // 成功预定
//	private long mWaitingNum; // 等待确认
//	private long mRefusedNum; // 拒绝预定
//	private boolean mIsCounted = false;

	private ModuleTitleMain mLayoutTitle = null;
	private SmoothRunnable mSmooth = null;

	private Consumer mConsumer = null;

	@Override
	protected final View onCreateView(LayoutInflater inflater,
			ViewGroup container) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.index_account, container, false);
	}

	@Override
	protected final void onCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		mLayoutTitle = new ModuleTitleMain(this);

		mToRight = findViewById(R.id.account_toright);
		mRlWaiting = findViewById(R.id.account_waitconfirm);
		mRlConfirmed = findViewById(R.id.account_orderconfirm);
		mRlOrderManage = findViewById(R.id.account_ordermanage);
		mRlCanceled = findViewById(R.id.account_refuseconfirm);
		mRlFavorite = findViewById(R.id.account_favorite);
		mRlCoupon = findViewById(R.id.account_coupon);
		mRlLogin = findViewById(R.id.account_login);
		mRlLogout = findViewById(R.id.account_logout);
		mRlSetting = findViewById(R.id.account_setting);

		mBirManage = findViewById(R.id.account_birthmanage);

		mLayoutInfo = findViewById(R.id.account_layout_info);
		mLayout = (PullDownLinearLayout) findViewById(R.id.account_layout);

		Resources res = getResources();
		float height = res.getDimension(R.dimen.home_pulldownheight);
		mLayout.setPullDownable(this);
		mLayout.setMaxScorllHeight((int) height);

		mTvRefusedNum = findTextViewById(R.id.account_refusenum);
		mTvConfirmNum = findTextViewById(R.id.account_successnum);
		mTvWaitingNum = findTextViewById(R.id.account_waitconfirmnum);
		mTvManagerNum = findTextViewById(R.id.account_ordermanagenum);

		mIvHeader = findImageViewById(R.id.account_iv_headicon);
		mTvNickName = findTextViewById(R.id.account_tv_nickname);
		mTvAutoGraph = findTextViewById(R.id.account_tv_autograph);
		mTvPraiseNumber = findTextViewById(R.id.account_tv_praisenumber);
		mTvFavoriteNum = findTextViewById(R.id.account_favoritenum);
		mTvRemindOrderWaiting = findTextViewById(R.id.account_notifyorderwaiting);
		mTvRemindOrderConfirm = findTextViewById(R.id.account_notifyorderconfirm);
		mTvRemindOrderRefused = findTextViewById(R.id.account_notifyorderefused);
		mScrollView = findScrollViewById(R.id.account_scrollview);

		mTvRemindOrderWaiting.setVisibility(View.GONE);
		mTvRemindOrderConfirm.setVisibility(View.GONE);
		mTvRemindOrderRefused.setVisibility(View.GONE);
		
		mRlCoupon.setOnClickListener(this);
		mRlWaiting.setOnClickListener(this);
		mRlConfirmed.setOnClickListener(this);
		mRlCanceled.setOnClickListener(this);
		mRlFavorite.setOnClickListener(this);
		mTvPraiseNumber.setOnClickListener(this);
		mRlLogin.setOnClickListener(this);
		mRlLogout.setOnClickListener(this);
		mIvHeader.setOnClickListener(this);
		mRlSetting.setOnClickListener(this);
		mBirManage.setOnClickListener(this);
		mLayoutInfo.setOnClickListener(this);
		mRlOrderManage.setOnClickListener(this);

		onLoginUserChanged(SuidingApp.getLoginUser());
	}
	
	@Override
	protected void onQueryChanged() {
		// TODO Auto-generated method stub
		if(mConsumer != null){
			mTvConfirmNum.setText("" + OrderConsole.getConfirmNum());
			mTvWaitingNum.setText("" + OrderConsole.getWaitingNum());
			mTvRefusedNum.setText("" + OrderConsole.getRefusedNum());
			mTvFavoriteNum.setText("" + UserCenter.getFavoriteNum());
			
			List<Order> ltchanged =	OrderConsole.pekListOrderChanged();
			onRemindOrderChanged(mConsumer.getID(), ltchanged);
		}
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
				mTvFavoriteNum.setText("" + UserCenter.getFavoriteNum());
				//postTask(new AccountTask(AccountTask.TASK_FAVORITE));
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
	public void onOrderNumberChanged(UUID userid, long wait, long confirm,
			long none) {
		// TODO Auto-generated method stub
		if(mConsumer != null && mConsumer.getID().equals(userid)){
//			mWaitingNum = wait;
//			mConfirmNum = confirm;
//			mRefusedNum = none;
			mTvConfirmNum.setText("" + confirm);
			mTvWaitingNum.setText("" + wait);
			mTvRefusedNum.setText("" + none);
			mTvManagerNum.setText("" + (confirm + wait + none));
		}
	}
	
	@Override
	public void onFavoriteNumberChanged(UUID userid, long favorite) {
		// TODO Auto-generated method stub
		if(mConsumer != null && mConsumer.getID().equals(userid)){
			mTvFavoriteNum.setText("" + favorite);
		}
	}
	
	@Override
	public void onRemindOrderChanged(UUID userid, List<Order> ltremind) {
		// TODO Auto-generated method stub
		int confirm = 0,refused = 0;
		for (Order order : ltremind) {
			if(OrderConsole.isOrderConfirm(order)){
				confirm++;
			}else{
				refused++;
			}
		}
		if(confirm > 0){
			mTvRemindOrderConfirm.setText(""+confirm);
			mTvRemindOrderConfirm.setVisibility(View.VISIBLE);
		}else{
			mTvRemindOrderConfirm.setVisibility(View.GONE);
		}
		if(refused > 0){
			mTvRemindOrderRefused.setText(""+refused);
			mTvRemindOrderRefused.setVisibility(View.VISIBLE);
		}else{
			mTvRemindOrderRefused.setVisibility(View.GONE);
		}
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

			mTvConfirmNum.setText("" + OrderConsole.getConfirmNum());
			mTvWaitingNum.setText("" + OrderConsole.getWaitingNum());
			mTvRefusedNum.setText("" + OrderConsole.getRefusedNum());
			mTvFavoriteNum.setText("" + UserCenter.getFavoriteNum());
			//postTask(new AccountTask(AccountTask.TASK_ORDERCOUNT));
			//postTask(new AccountTask(AccountTask.TASK_FAVORITE));
		}
		smoothScrollLayout(mConsumer != null);
	}

	@SuppressLint("NewApi")
	private final void smoothScrollLayout(boolean visiable) {
		if (null != mSmooth) {
			mSmooth.stop();
		}
		int from = visiable ? 0 : 100;
		int to = visiable ? 100 : 0;
		if (VERSION.SDK_INT >= 11) {
			mSmooth = new SmoothRunnable(this, from, to, 500);
			mSmooth.start();
		} else {
			onFinish(from, to);
		}
		if (VERSION.SDK_INT >= 14) {
			mScrollView.setScrollY(0);
		}
	}

	@Override
	@SuppressLint("NewApi")
	public final boolean onSmooth(int value, float percent, int from, int to) {
		// TODO Auto-generated method stub
		percent = (float)value / Math.abs(to - from);
		mRlLogin.setAlpha(1 - percent);
		mIvHeader.setAlpha(percent);
		mTvAutoGraph.setAlpha(percent);
		mTvNickName.setAlpha(percent);
		mToRight.setAlpha(percent);
		return true;
	}

	@Override
	public void onStart(int from, int to) {
		// TODO Auto-generated method stub
		mRlLogin.setVisibility(View.VISIBLE);
		mIvHeader.setVisibility(View.VISIBLE);
		mTvAutoGraph.setVisibility(View.VISIBLE);
		mTvNickName.setVisibility(View.VISIBLE);
		mToRight.setVisibility(View.VISIBLE);
	}

	@Override
	public void onFinish(int from, int to) {
		// TODO Auto-generated method stub
		if (from < to) {
			mRlLogin.setVisibility(View.GONE);
			mIvHeader.setVisibility(View.VISIBLE);
			mTvAutoGraph.setVisibility(View.VISIBLE);
			mTvNickName.setVisibility(View.VISIBLE);
			mRlLogout.setVisibility(View.VISIBLE);
			mToRight.setVisibility(View.VISIBLE);
		} else {
			mRlLogin.setVisibility(View.VISIBLE);
			mIvHeader.setVisibility(View.GONE);
			mTvAutoGraph.setVisibility(View.GONE);
			mTvNickName.setVisibility(View.GONE);
			mRlLogout.setVisibility(View.GONE);
			mToRight.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean isReadyForPullDown() {
		// TODO Auto-generated method stub
		return mConsumer != null && mScrollView.getScrollY() <= 0;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		Intent intent = new Intent();
		if (v.getId() == R.id.account_setting) {
			intent.setClass(mActivity, SettingsActivity.class);
			this.startActivity(intent);
		} else {
			if(mConsumer == null){
				intent.setClass(mActivity, UserLoginActivity.class);
				this.startActivity(intent);
				return;
			}
			String extra = ListOrderActivity.EXTRA_EM_FILTE;
			switch (v.getId()) {
			case R.id.account_tv_praisenumber:
				// ss = "点击了“赞”";
				break;
			case R.id.account_ordermanage:
				ExtraUtil.putExtra(extra, ListOrderActivity.FILTE_NONE);
				intent.setClass(mActivity, ListOrderActivity.class);
				this.startActivity(intent);
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
			case R.id.account_login:
				intent.setClass(mActivity, UserLoginActivity.class);
				this.startActivityForResult(intent, REQUEST_LOGIN);
				break;
			case R.id.account_layout_info:
			case R.id.account_iv_headicon:
				ExtraUtil.putExtra(EditAccountActivity.EXTRA_BL_LOGOUT,true);
				ExtraUtil.putExtra(EditAccountActivity.EXTRA_OB_CONSUMER,mConsumer);
				intent.setClass(mActivity, EditAccountActivity.class);
				this.startActivityForResult(intent, REQUEST_INFO);
				break;
			case R.id.account_birthmanage:
				intent.setClass(mActivity, BirthdayManageActivity.class);
				startActivity(intent);
				break;
			case R.id.account_coupon:
				AppSettings setting = AppSettings.getInstance();
				if(setting.isIsReceive()){
					intent.setClass(mActivity, ListPromotionActivity.class);
					startActivity(intent);
				}else{
					showToastLong("请在设置页面中开启接受商家推送");
				}
				break;
			case R.id.account_logout:
				SuidingApp.getApp().setLoginUser(this, null);
				break;
			}
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
//		AccountTask task = (AccountTask) msg.obj;
//		if (task.mResult == TaskBase.RESULT_FINISH) {
//			switch (task.mTask) {
//			case AccountTask.TASK_FAVORITE:
//				mTvFavoriteNum.setText("" + task.mltFavorite.size());
//				break;
//			case AccountTask.TASK_ORDERCOUNT:
//				mTvConfirmNum.setText("" + mConfirmNum);
//				mTvWaitingNum.setText("" + mWaitingNum);
//				mTvRefusedNum.setText("" + mRefusedNum);
//				break;
//			}
//		}
		return true;
	}

//	private class AccountTask extends TaskBase {
//
//		public static final int TASK_FAVORITE = 0;
//		public static final int TASK_ORDERCOUNT = 1;
//
//		public List<StoreBase> mltFavorite = new ArrayList<StoreBase>();
//
//		public AccountTask(int task) {
//			super(new Handler(IndexAccountFragment.this), task);
//			// TODO Auto-generated constructor stub
//		}
//
//		@Override
//		protected void onWorking(Message tMessage) throws Exception {
//			// TODO Auto-generated method stub
//			Consumer user = SuidingApp.getLoginUser();
//			switch (mTask) {
//			case TASK_FAVORITE:
//				FavoriteEntityDao dao = new FavoriteEntityDao(mActivity);
//				if (user != null) {
//					IUserDomain domain = DomainFactory.getUserDomain();
//					mltFavorite = domain.getFavorateStoreByUserID(user.getID());
//					if (mltFavorite.size() > dao.getCount()) {
//						dao.updateCache(FavoriteEntity
//								.listFormModel(mltFavorite));
//					}
//				} else {
//					mltFavorite = FavoriteEntity.listToModel(dao.getAll());
//				}
//				break;
//			case TASK_ORDERCOUNT:
//				if (!mIsCounted) {
//					UUID id = mConsumer.getID();
//					IOrderDomain domain = DomainFactory.getOrderDomain();
//
//					mRefusedNum = XmlCacheUtil.getLong(CACHE_NUMCANCEL, 0);
//					mWaitingNum = XmlCacheUtil.getLong(CACHE_NUMWAITING, 0);
//					mConfirmNum = XmlCacheUtil.getLong(CACHE_NUMCONFIRM, 0);
//
//					mConfirmNum = domain.GetCountByFilters(id,
//							ListOrderActivity.FILTES_FINISH);
//					mWaitingNum = domain.GetCountByFilters(id,
//							ListOrderActivity.FILTES_WAITING);
//					mRefusedNum = domain.GetCountByFilters(id,
//							ListOrderActivity.FILTES_CANCEL);
//
//					XmlCacheUtil.putLong(CACHE_NUMCANCEL, mRefusedNum);
//					XmlCacheUtil.putLong(CACHE_NUMWAITING, mWaitingNum);
//					XmlCacheUtil.putLong(CACHE_NUMCONFIRM, mConfirmNum);
//
//					mIsCounted = true;
//				}
//				break;
//			}
//		}
//
//	}
}
