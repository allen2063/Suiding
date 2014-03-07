package com.suiding.activity;

import java.util.List;
import java.util.UUID;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.suiding.application.AddressService;
import com.suiding.application.ImageService;
import com.suiding.application.SuidingApp;
import com.suiding.application.UserCenter;
import com.suiding.constant.ImageFolderEnum;
import com.suiding.constant.ImageSize;
import com.suiding.dao.FavoriteEntityDao;
import com.suiding.domain.IBusinessDomain;
import com.suiding.domain.ICouponDomain;
import com.suiding.domain.IProductDomain;
import com.suiding.domain.IStoreBaseDomain;
import com.suiding.domain.IUserDomain;
import com.suiding.entity.FavoriteEntity;
import com.suiding.layoutbind.ModuleCouponBar;
import com.suiding.layoutbind.ModuleDatailPanel;
import com.suiding.layoutbind.ModuleMerchant;
import com.suiding.layoutbind.ModuleMerchant.OnMerchantClickListener;
import com.suiding.layoutbind.ModuleProduct;
import com.suiding.layoutbind.ModuleProduct.OnProductClickListener;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.model.Business;
import com.suiding.model.Consumer;
import com.suiding.model.Coupon;
import com.suiding.model.Page;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.service.FileService;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.CallPhoneUtil;
import com.suiding.util.ExtraUtil;
import com.suiding.util.UUIDUtil;

public class DetailStoreActivity extends DetailActivity implements
		OnProductClickListener, OnMerchantClickListener {

	private static final int REQUEST_PRODUCT = 10;
	
	protected TextView mTvName = null;
	protected TextView mTvPhone = null;
	protected TextView mTvAddress = null;
	protected TextView mTvComment = null;
	protected CheckBox mCbFavorite = null;

	protected ImageView mIvHeader = null;
	protected RatingBar mRbLevel = null;

	protected ModuleProduct mLayoutProduct = null;
	protected ModuleMerchant mLayoutBusiness = null;
	protected ModuleCouponBar mLayoutCoupon = null;
	protected ModuleDatailPanel mDatailPanel = null;

	protected List<Coupon> mltCoupon = null;
	protected List<Product> mltProduct = null;

	protected StoreBase mStore = null;
	protected boolean mIsCollected = false;
	protected boolean mIsCollecting = false;	//标记收藏正在处理

	@Override
	protected DetailStoreTask getDetailTask(int task) {
		// TODO Auto-generated method stub
		return new DetailStoreTask(task);
	}

	@Override
	protected View onCreateView(LayoutInflater inflater, Bundle bundle) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.detail_store_common, null);
	}

	@Override
	protected UUID onGetDetailID() {
		// TODO Auto-generated method stub
		return mStore == null ? UUIDUtil.Empty : mStore.getID();
	}

	@Override
	protected void onCreated(View page) {
		// TODO Auto-generated method stub
		mStore = (StoreBase) ExtraUtil.getExtra(EXTRA_DETAIL);

		mDatailPanel = new ModuleDatailPanel(this);
		mLayoutCoupon = new ModuleCouponBar(this);
		mLayoutProduct = new ModuleProduct(this);
		mLayoutBusiness = new ModuleMerchant(this);
		// mLayoutTitle.setFunction(ModuleTitleOther.FUNCTION_COLLECT);

		mTvName = findTextViewById(R.id.detail_store_title);
		mRbLevel = findRatingBarById(R.id.detail_store_level);
		mIvHeader = findImageViewById(R.id.detail_store_image);
		mTvAddress = findTextViewById(R.id.detail_store_address);
		mTvComment = findTextViewById(R.id.detail_store_comment);
		mTvPhone = findTextViewById(R.id.detail_store_phone);
		mCbFavorite = findCheckBoxById(R.id.detail_store_favorite);

		// mIvHeader.setOnClickListener(this);
		mTvPhone.setOnClickListener(this);
		mLayoutTitle.setOnCollectClickListener(this);
		mLayoutProduct.setOnProductClickListener(this);
		mLayoutBusiness.setOnMerchantClickListener(this);

		this.doBindData();
	}
	
	private void doBindData() {
		// TODO Auto-generated method stub
		mTvName.setText(mStore.Name);
		mLayoutTitle.setTitle(mStore.Name);
		mRbLevel.setVisibility(View.GONE);
		mTvPhone.setText(mStore.Telephone);
		mTvComment.setText(String.format("%.0f", mStore.Scores));

		if (mStore.Abstract != null && mStore.Abstract.length() > 0) {
			mDatailPanel.setTitle("商家信息");
			mDatailPanel.setContent(mStore.Abstract);
			mDatailPanel.show();
		} else {
			mDatailPanel.hide();
		}

		// 设置评论权限
		boolean power = UserCenter.getCommentPower(mStore);
		mLayoutComment.setCommentPower(power);

		// 绑定头像图片
		ImageService.bindImage(mStore.HeadImg, mIvHeader,
				ImageFolderEnum.HEAD_STORE, ImageSize.SMALLER);
		// 绑定地址
		AddressService.bindAddress(mStore.Address, mTvAddress);

		// 检测收藏
		if (SuidingApp.getLoginUser() != null) {
			FavoriteEntityDao dao = new FavoriteEntityDao(this);
			mIsCollected = dao.exist(new FavoriteEntity(mStore));
			mCbFavorite.setChecked(mIsCollected);
			mCbFavorite.setOnClickListener(this);// 先设置状态后在设置Listener
			mLayoutTitle.setCollect(mIsCollected);
			if(mIsCollected){
				mCbFavorite.setVisibility(View.VISIBLE);
			}else{
				mIsCollecting = true;
				//postTask(new DetailStoreTask(DetailStoreTask.TASK_CHECKFAVORITE));
			}
		} else {
			//mCbFavorite.setVisibility(View.GONE);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			if(requestCode == REQUEST_PRODUCT){
				Object result = ExtraUtil.getExtra(DetailProductActivity.RESULT_OB_PRODUCT);
				if(result instanceof Product){
					Product product = (Product)result;
					mLayoutProduct.updateProduct(product);
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		default:
			super.onClick(v);
			break;
		case R.id.detail_store_phone:
			String phone = mTvPhone.getText().toString().trim();
			CallPhoneUtil.call(this,phone);
			break;
		case R.id.detail_store_image:
			String headimage = mStore.HeadImg != null ? mStore.HeadImg : "";
			headimage = FileService.getLocalUrl(headimage,
					ImageFolderEnum.HEAD_STORE, ImageSize.SMALLER);
			ExtraUtil.putExtra(AlbumActivity.EXTRA_FORID, mStore.getID());
			ExtraUtil.putExtra(AlbumActivity.EXTRA_NAME, mStore.Name);
			ExtraUtil.putExtra(AlbumActivity.EXTRA_DESCRIBE, mStore.Abstract);
			ExtraUtil.putExtra(AlbumActivity.EXTRA_HEADURL, headimage);
			ExtraUtil.putExtra(AlbumActivity.EXTRA_TYPE,
					AlbumActivity.TYPE_STORE);
			this.startActivity(new Intent(this, AlbumActivity.class));
			break;
		case R.id.detail_store_favorite:
		case ModuleTitleOther.ID_COLLECT:
			if(mIsCollecting){
				showToastShort("正在处理中...请稍候");
				mCbFavorite.setChecked(mIsCollected);
				mLayoutTitle.setCollect(mIsCollected);
				break;
			}
			if (SuidingApp.getLoginUser() != null) {
				if (mIsCollected) {
					postTask(getDetailTask(DetailStoreTask.TASK_UNCOLLECT));
				} else {
					postTask(getDetailTask(DetailStoreTask.TASK_FAVORITE));
				}
				mCbFavorite.setChecked(mIsCollected);
				mLayoutTitle.setCollect(mIsCollected);
			} else {
				showToastShort("你还没有登录，不能收藏");
			}
			break;
		}
	}

	@Override
	public boolean onRefreshProductListener() {
		// TODO Auto-generated method stub
		postTask(getDetailTask(DetailStoreTask.TASK_LOADPRODUCT));
		return true;
	}

	@Override
	public boolean onRefreshMerchant() {
		// TODO Auto-generated method stub
		postTask(getDetailTask(DetailStoreTask.TASK_LOADBUSINESS));
		return true;
	}

	@Override
	public void onProductClickListener(Product product) {
		// TODO Auto-generated method stub
		ExtraUtil.putExtra(DetailProductActivity.EXTRA_DETAIL, product);
		ExtraUtil.putExtra(DetailProductActivity.EXTRA_OB_STORE, mStore);
		startActivityForResult(new Intent(this, DetailProductActivity.class),REQUEST_PRODUCT);
	}

	@Override
	public void onMoreProductListener(List<Product> ltproduct) {
		// TODO Auto-generated method stub
		ExtraUtil.putExtra(ListProductActivity.EXTRA_STORE, mStore);
		ExtraUtil.putExtra(ListProductActivity.EXTRA_LIST, mltProduct);
		this.startActivity(new Intent(this, ListProductActivity.class));
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		if (TaskBase.getTask(msg).mTask == DetailTask.TASK_LOADCOMMENT) {
			// 加载Comment结束后开始加载Coupon
			postTask(getDetailTask(DetailStoreTask.TASK_LOADCOUPON));
			return super.handleMessage(msg);
		}
		if (super.handleMessage(msg) == false) {
			DetailStoreTask tTask = (DetailStoreTask) msg.obj;
			if (tTask.mTask == DetailStoreTask.TASK_LOADCOUPON) {
				// 加载Coupon结束后开始加载Product
				postTask(getDetailTask(DetailStoreTask.TASK_LOADPRODUCT));
			} else if (tTask.mTask == DetailStoreTask.TASK_LOADPRODUCT) {
				// 加载Product结束后开始 检查收藏
				if(mIsCollecting){
					postTask(getDetailTask(DetailStoreTask.TASK_CHECKFAVORITE));
				}
			}

			if (tTask.mResult == TaskBase.RESULT_FINISH) {
				switch (tTask.mTask) {
				case DetailStoreTask.TASK_LOADBUSINESS:
					mLayoutBusiness.setMerchant(tTask.mBusiness);
					break;
				case DetailStoreTask.TASK_REFRESH:
					doBindData();
					//刷新完成之后，继续从加载评论开始加载其他任务
					postTask(new DetailStoreTask(DetailTask.TASK_LOADCOMMENT));
					break;
				case DetailStoreTask.TASK_LOADPRODUCT:
					mltProduct = tTask.mlProduct;
					mLayoutProduct.setProduct(mltProduct);
					break;
				case DetailStoreTask.TASK_LOADCOUPON:
					if (mltCoupon.size() > 0) {
						mLayoutCoupon.setCoupon(mltCoupon.get(0),
								mltCoupon.size());
					}
					break;
				case DetailStoreTask.TASK_FAVORITE:
					mIsCollecting = false;
					mLayoutTitle.setCollect(mIsCollected = true);
					mCbFavorite.setChecked(mIsCollected = true);
					Toast.makeText(this, "收藏成功！", Toast.LENGTH_SHORT).show();
					break;
				case DetailStoreTask.TASK_UNCOLLECT:
					mIsCollecting = false;
					Toast.makeText(this, "取消收藏成功！", Toast.LENGTH_SHORT).show();
					mLayoutTitle.setCollect(mIsCollected = false);
					mCbFavorite.setChecked(mIsCollected = false);
					break;
				case DetailStoreTask.TASK_CHECKFAVORITE:
					mIsCollecting = false;
					mCbFavorite.setChecked(mIsCollected);
					mCbFavorite.setVisibility(View.VISIBLE);
					break;
				}
			} else if (tTask.mResult == TaskBase.RESULT_FAIL) {
				if (tTask.mTask == DetailStoreTask.TASK_LOADPRODUCT) {
					mLayoutProduct.setLoadFail();
				}else if (tTask.mTask == DetailStoreTask.TASK_LOADBUSINESS) {
					mLayoutBusiness.setLoadFail();
				}else if(tTask.mTask == DetailStoreTask.TASK_FAVORITE){
					mIsCollecting = false;
				}else if(tTask.mTask == DetailStoreTask.TASK_UNCOLLECT){
					mIsCollecting = false;
				}else if(tTask.mTask == DetailStoreTask.TASK_CHECKFAVORITE){
					mIsCollecting = false;
				}
			}
		}
		return true;
	}

	protected class DetailStoreTask extends DetailTask {

		public static final int TASK_LOADPRODUCT = 1002;
		public static final int TASK_LOADCOUPON = 1003;
		public static final int TASK_LOADBUSINESS = 1004;
		public static final int TASK_FAVORITE = 1005;
		public static final int TASK_UNCOLLECT = 1006;
		public static final int TASK_CHECKFAVORITE = 1007;

		public Business mBusiness = null;
		public List<Coupon> mlCoupon = null;
		public List<Product> mlProduct = null;

		public DetailStoreTask(int task) {
			super(new Handler(DetailStoreActivity.this), task);
			// TODO Auto-generated constructor stub
		}

		public DetailStoreTask(Handler handler, int task) {
			super(handler, task);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onWorking(Message tMessage) throws Exception {
			// TODO Auto-generated method stub
			switch (mTask) {
			default:
				super.onWorking(tMessage);
				break;
			case TASK_REFRESH: {
				IStoreBaseDomain doamin = DomainFactory.getStoreBaseDomain();
				StoreBase store = doamin.GetByID(mStore.getID());
				if (store != null && store.getID().equals(mStore.getID())) {
					mStore = store;
				}
				break;
			}
			case TASK_LOADBUSINESS: {
				IBusinessDomain tDomain = DomainFactory.getBusinessDomain();
				mBusiness = tDomain.getByUserID(mStore.UserID);
				break;
			}
			case TASK_LOADPRODUCT: {
				Page page = new Page(ModuleProduct.MAX_COUNT, 0);
				IProductDomain tDomain = DomainFactory.getProductDomain();
				mlProduct = tDomain.GetListBySbID(mStore.getID(), page);
				break;
			}
			case TASK_LOADCOUPON: {
				Page page = new Page(1, 0);
				ICouponDomain tDomain = DomainFactory.getCouponDomain();
				mltCoupon = tDomain.getListBySbID(mStore.getID(), page);
				long count = tDomain.getCountBySbID(mStore.getID());
				while (mltCoupon.size() < count) {
					mltCoupon.add(new Coupon());
				}
				break;
			}
			case TASK_FAVORITE: {
				Consumer user = SuidingApp.getLoginUser();
				if (user != null) {
					IUserDomain domain = DomainFactory.getUserDomain();
					domain.FavoriteStoreBase(user.getID(), mStore);
				}
				FavoriteEntityDao dao = new FavoriteEntityDao(getBaseContext());
				dao.add(new FavoriteEntity(mStore));
				break;
			}
			case TASK_UNCOLLECT: {
				Consumer user = SuidingApp.getLoginUser();
				if (user != null) {
					IUserDomain domain = DomainFactory.getUserDomain();
					domain.UnFavoriteStoreBase(user.getID(), mStore.getID());
				}
				FavoriteEntityDao dao = new FavoriteEntityDao(getBaseContext());
				dao.remove(new FavoriteEntity(mStore));
				break;
			}
			case TASK_CHECKFAVORITE: {
				Consumer user = SuidingApp.getLoginUser();
				if (user != null) {
					IStoreBaseDomain domain = DomainFactory.getStoreBaseDomain();
					mIsCollected = domain.getIsFavoriteByUser(user.getID(), mStore.getID());
				}
				break;
			}
			}
		}
	}

}
