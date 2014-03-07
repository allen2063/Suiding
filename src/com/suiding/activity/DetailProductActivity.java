package com.suiding.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.suiding.application.ImageService;
import com.suiding.application.SuidingApp;
import com.suiding.constant.ImageFolderEnum;
import com.suiding.constant.ImageSize;
import com.suiding.constant.StoreTypeEnum;
import com.suiding.domain.ICouponDomain;
import com.suiding.domain.IPdtItemDomain;
import com.suiding.domain.IPdtItemTypeDomain;
import com.suiding.domain.IProductDomain;
import com.suiding.layoutbind.ModuleComment;
import com.suiding.layoutbind.ModuleComment.OnCommentClickListener;
import com.suiding.layoutbind.ModuleCouponPanel;
import com.suiding.layoutbind.ModuleDatailPanel;
import com.suiding.layoutbind.ModuleProductFacility;
import com.suiding.layoutbind.ModuleRsrmenu;
import com.suiding.layoutbind.ModuleStoreInfo;
import com.suiding.layoutbind.ModuleTabControl.ITabControlItem;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.model.Consumer;
import com.suiding.model.Coupon;
import com.suiding.model.Page;
import com.suiding.model.PdtItem;
import com.suiding.model.PdtItemType;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.service.FileService;
import com.suiding.util.ExtraUtil;
import com.suiding.util.UUIDUtil;

public class DetailProductActivity extends DetailActivity implements
		OnCommentClickListener {

	public static final String EXTRA_IT_TYPE = "EXTRA_TYPE";
	public static final String EXTRA_OB_STORE = "EXTRA_STORE";
	public static final String EXTRA_OB_COUPON = "EXTRA_COUPON";
	
	public static final String RESULT_OB_PRODUCT = "RESULT_OB_PRODUCT";

	private static final int REQUSET_RESERVE_HOTEL = 10;
	private static final int REQUSET_RESERVE_GOURMET = 11;
	

	protected TextView mBtReserve = null;

	protected TextView mTvPrice = null;
	protected TextView mTvPriceUnit = null;
	protected TextView mTvOldPrice = null;

	protected ImageView mIvHeader = null;

	protected Coupon mCoupon = null;
	protected Product mProduct = null;
	protected StoreBase mStore = null;

	protected ModuleRsrmenu mRsrmenu = null;
	protected ModuleStoreInfo mStoreInfo = null;
	protected ModuleDatailPanel mDatailPanel = null;
	protected ModuleCouponPanel mCouponPanel = null;
	protected ModuleProductFacility mFacility = null;

	protected TabItem mCurrentTabItem = null;
	protected List<TabItem> mltTabItem = new ArrayList<TabItem>();
	protected List<PdtItem> mltPdtItem = new ArrayList<PdtItem>();
	protected List<PdtItemType> mltPdtItemType = new ArrayList<PdtItemType>();

	protected int mType = StoreTypeEnum.ALL;
	//标记是否被订满
	private boolean mIsFull = false;

	@Override
	protected UUID onGetDetailID() {
		// TODO Auto-generated method stub
		return mProduct == null ? UUIDUtil.Empty : mProduct.getID();
	}

	@Override
	protected DetailProductTask getDetailTask(int task) {
		return new DetailProductTask(task);
	}

	@Override
	protected View onCreateView(LayoutInflater inflater, Bundle bundle) {
		return inflater.inflate(R.layout.detail_product_common, null);
	}
	
	@Override
	protected void onCreated(View page) {
		// TODO Auto-generated method stub
		super.onCreated(page);

		mType = ExtraUtil.getExtraInt(EXTRA_IT_TYPE, StoreTypeEnum.ALL);
		mCoupon = (Coupon) ExtraUtil.getExtra(EXTRA_OB_COUPON);
		mStore = (StoreBase) ExtraUtil.getExtra(EXTRA_OB_STORE);
		mProduct = (Product) ExtraUtil.getExtra(EXTRA_DETAIL);
		if (mType == StoreTypeEnum.ALL && mStore != null) {
			mType = mStore.Type;
		}

		mRsrmenu = new ModuleRsrmenu(this);
		mStoreInfo = new ModuleStoreInfo(this, mStore);
		mCouponPanel = new ModuleCouponPanel(this);
		mLayoutComment = new ModuleComment(this);
		mDatailPanel = new ModuleDatailPanel(this);
		mFacility = new ModuleProductFacility(this, mStore, mProduct);

		mTvPrice = findTextViewById(R.id.detail_product_newprice);
		mTvOldPrice = findTextViewById(R.id.detail_product_oldprice);
		mTvPriceUnit = findTextViewById(R.id.detail_product_priceunit);
		mIvHeader = findImageViewById(R.id.detail_product_image);

		mBtReserve = findTextViewById(R.id.detail_product_bt_order);

		mLayoutTitle.setOnGoBackListener(this);
		
		mRsrmenu.hide();
		initializeCoupon();
		
		this.doBindData();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK && data != null){
			if(requestCode == REQUSET_RESERVE_HOTEL){
				if(data.getBooleanExtra(ReserveHotelActivity.RESULT_BL_ISFULL, false)){
					mIsFull  = true;
					mProduct.BookedCount = mProduct.ProductCount;
				}
			}else if(requestCode ==REQUSET_RESERVE_GOURMET){
				if(data.getBooleanExtra(ReserveGourmetActivity.RESULT_BL_ISFULL, false)){
					mIsFull  = true;
					mProduct.BookedCount = mProduct.ProductCount;
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
		case ModuleTitleOther.ID_GOBACK:
			doFinishActicity();
			break;
		case R.id.detail_product_image:
			String headimage = FileService.getLocalUrl(mProduct.HeadImg,
					ImageFolderEnum.HEAD_PRODUCT, ImageSize.SMALLER);
			ExtraUtil.putExtra(AlbumActivity.EXTRA_FORID, mProduct.getID());
			ExtraUtil.putExtra(AlbumActivity.EXTRA_NAME, mProduct.Name);
			ExtraUtil.putExtra(AlbumActivity.EXTRA_DESCRIBE, mProduct.Abstrct);
			ExtraUtil.putExtra(AlbumActivity.EXTRA_HEADURL, headimage);
			ExtraUtil.putExtra(AlbumActivity.EXTRA_TYPE,AlbumActivity.TYPE_PRODUCT);
			this.startActivity(new Intent(this, AlbumActivity.class));
			break;
		case R.id.detail_product_bt_order:
			this.startReserve();
			break;
		}
	}

	/**
	 * 勾住键盘返回
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			doFinishActicity();
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	private void doFinishActicity() {
		// TODO Auto-generated method stub
		if(mIsFull){
			setResult(RESULT_OK);
			ExtraUtil.putExtra(RESULT_OB_PRODUCT, mProduct);
		}
		this.finish();
	}

	private void doBindData() {
		// TODO Auto-generated method stub
//		if (mType != StoreTypeEnum.FOODANDBEVERAGE
//				&& mType != StoreTypeEnum.HOTEL) {
//			mBtReserve.setVisibility(View.GONE);
//		}else{
		boolean isfull = mProduct.ProductCount-mProduct.BookedCount <= 0;
		mBtReserve.setEnabled(!isfull);
		if(isfull){
			int tcolor = getResources().getColor(R.color.theme_textcolor_text);
			mBtReserve.setText("已    满");
			mBtReserve.setTextColor(tcolor);
		}
//		}
		
		mIvHeader.setOnClickListener(this);
		mBtReserve.setOnClickListener(this);
		mLayoutComment.setListener(this);

		mTvPrice.setText(String.format("%.0f", mProduct.NowPrice));
		mTvOldPrice.setText(String.format("%.0f元", mProduct.Price));
		mLayoutTitle.setTitle(mProduct.Name);
		
		if(mProduct.Abstrct != null && mProduct.Abstrct.length() > 0){
			mDatailPanel.setContent(mProduct.Abstrct);
		}else{
			mDatailPanel.hide();
		}
		
		if (0 == (int) mProduct.NowPrice) {
			mTvPrice.setText("无最低消费");
			mTvOldPrice.setVisibility(View.GONE);
			mTvPriceUnit.setVisibility(View.GONE);
		} else {
			if (mProduct.NowPrice < mProduct.Price) {
				// 删除线
				mTvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
				mTvOldPrice.setTag(String.format("%.0f元", mProduct.NowPrice));
			} else {
				mTvOldPrice.setVisibility(View.GONE);
			}
		}
		ImageService.bindImage(mProduct.HeadImg, mIvHeader,
				ImageFolderEnum.HEAD_PRODUCT, ImageSize.SMALLER);
	}

	private void initializeCoupon() {
		// TODO Auto-generated method stub
		if (mCoupon != null) {
			mCouponPanel.setCoupon(mCoupon);
			mCouponPanel.show();
		} else {
			mCouponPanel.hide();
		}
	}

	private void startReserve() {
		// TODO Auto-generated method stub
		Consumer user = SuidingApp.getLoginUser();
		if (user == null) {
			this.showToastShort("请先登录再预定");
			Intent intent = new Intent();
			intent.setClass(this, UserLoginActivity.class);
			startActivity(intent);
			return;
		}

		Intent intent = new Intent();
		if (mType == StoreTypeEnum.HOTEL) {
			ExtraUtil.putExtra(ReserveHotelActivity.EXTRA_STORE, mStore);
			ExtraUtil.putExtra(ReserveHotelActivity.EXTRA_COUPON, mCoupon);
			ExtraUtil.putExtra(ReserveHotelActivity.EXTRA_PRODUCT, mProduct);
			intent.setClass(this, ReserveHotelActivity.class);
			startActivityForResult(intent, REQUSET_RESERVE_HOTEL);
		} else /*if (mType == StoreTypeEnum.FOODANDBEVERAGE) */{
			ExtraUtil.putExtra(ReserveGourmetActivity.EXTRA_STORE, mStore);
			ExtraUtil.putExtra(ReserveGourmetActivity.EXTRA_COUPON, mCoupon);
			ExtraUtil.putExtra(ReserveGourmetActivity.EXTRA_PRODUCT, mProduct);
			ExtraUtil.putExtra(ReserveGourmetActivity.EXTRA_MENULIST, mltPdtItem);
			ExtraUtil.putExtra(ReserveGourmetActivity.EXTRA_MENUTYPE, mltPdtItemType);
			intent.setClass(this, ReserveGourmetActivity.class);
			startActivityForResult(intent, REQUSET_RESERVE_GOURMET);
		}
	}

	protected class DetailProductTask extends DetailTask {

		public static final int TASK_LOADMENUTYPE = 1001;
		public static final int TASK_LOADMENU = 1002;
		public static final int TASK_LOADCOUPON = 1003;

		public List<PdtItem> ltPdtItem = null;
		public List<PdtItemType> ltItemType = null;

		public DetailProductTask(int task) {
			super(SuidingApp.getLooper(), task);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected final void onWorking(Message msg) throws Exception {
			// TODO Auto-generated method stub
			switch (mTask) {
			default:
				super.onWorking(msg);
				break;
			case TASK_REFRESH: {
				IProductDomain domain = DomainFactory.getProductDomain();
				Product	product = domain.GetByID(mProduct.getID());
				if(product != null && product.getID().equals(mProduct.getID())){
					mProduct = product;
				}
			}
			case TASK_LOADMENUTYPE: {
				Page page = new Page(20, 0);
				IPdtItemTypeDomain domain = DomainFactory.getPdtItemTypeDomain();
				ltItemType = domain.GetListBySbID(mStore.getID(), page);
				break;
			}
			case TASK_LOADMENU: {
				Page page = new Page(40, 0);
				IPdtItemDomain domain = DomainFactory.getPdtItemDomain();
				ltPdtItem = domain.GetListByPbID(mProduct.getID(), page);
				break;
			}
			case TASK_LOADCOUPON: {
				Page page = new Page(1, 0);
				ICouponDomain domain = DomainFactory.getCouponDomain();
				List<Coupon> ltCoupon = domain.getListByPbID(mProduct.getID(),page);
				if (ltCoupon != null && ltCoupon.size() > 0) {
					mCoupon = ltCoupon.get(0);
				}
				break;
			}
			}
		}

		@Override
		public boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if(mTask == TASK_LOADCOMMENT){
				//加载 评论完成之后 开始加载 活动
				if(mCoupon == null){
					postTask(getDetailTask(DetailProductTask.TASK_LOADCOUPON));
				}else if (mType == StoreTypeEnum.FOODANDBEVERAGE) {
					postTask(getDetailTask(DetailProductTask.TASK_LOADMENUTYPE));
				} 
				return super.onHandle(msg);
			}
			if (super.onHandle(msg) == false)
			{
				switch (mTask) {
				case TASK_REFRESH:
					doBindData();
					//刷新完成之后，继续从加载评论开始加载其他任务
					postTask(new DetailProductTask(DetailTask.TASK_LOADCOMMENT));
					break;
				case TASK_LOADMENUTYPE:
					if(ltItemType != null && ltItemType.size() > 0){
						//mRsrmenu.show();
						mltPdtItemType.clear();
						mltPdtItemType.addAll(ltItemType);
						for (PdtItemType item : ltItemType) {
							mltTabItem.add(new TabItem(item));
						}
						mRsrmenu.setPdtItemTypes(mltTabItem);
						mCurrentTabItem = mltTabItem.get(0);
					}
					postTask(getDetailTask(DetailProductTask.TASK_LOADMENU));
					break;
				case TASK_LOADMENU: {
					if(ltPdtItem != null && ltPdtItem.size() > 0){
						mRsrmenu.show();
						mltPdtItem = ltPdtItem;
						if(mCurrentTabItem != null){
							selectPdtItemType(mCurrentTabItem);
						}else{
							mRsrmenu.setPdtItems(mltPdtItem);
						}
					}
				}
				break;
				case TASK_LOADCOUPON:{
					initializeCoupon();
					if(mType == StoreTypeEnum.FOODANDBEVERAGE){
						postTask(getDetailTask(DetailProductTask.TASK_LOADMENUTYPE));
					}
				}
				break;
				}
			}
			return mTask == TASK_LOADMENUTYPE || mTask == TASK_LOADMENU ;
		}
	}

	protected class TabItem extends PdtItemType implements ITabControlItem {

		public TabItem(PdtItemType item) {
			super(item);
		}

		@Override
		public void onCheckedChanged(boolean isChecked) {
			// TODO Auto-generated method stub
			if (isChecked) {
				mCurrentTabItem = this;
				selectPdtItemType(this);
			}
		}
	}

	public void selectPdtItemType(TabItem type) {
		// TODO Auto-generated method stub
		List<PdtItem> ltPdtItem = new ArrayList<PdtItem>();
		for (PdtItem item : mltPdtItem) {
			if (item.Type_ID.equals(type.getID())) {
				ltPdtItem.add(item);
			}
		}
		mRsrmenu.setPdtItems(ltPdtItem);
	}

}
