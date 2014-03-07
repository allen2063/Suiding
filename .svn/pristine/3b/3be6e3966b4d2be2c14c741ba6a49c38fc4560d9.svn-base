package com.suiding.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.application.AppExceptionHandler;
import com.suiding.application.OrderConsole;
import com.suiding.application.SuidingApp;
import com.suiding.constant.OrderStatus;
import com.suiding.dao.OrderEntityDao;
import com.suiding.domain.IProductDomain;
import com.suiding.domain.IRsrOrderDomain;
import com.suiding.entity.OrderEntity;
import com.suiding.layoutbind.ModuleOrderMenu;
import com.suiding.layoutbind.ModuleOrderMenuItem.OrderMenuItemListener;
import com.suiding.layoutbind.ModuleOrderRestaurant;
import com.suiding.layoutbind.ModuleOrderRestaurant.OrderRestaurantListener;
import com.suiding.layoutbind.ModuleOrderSelected;
import com.suiding.layoutbind.ModuleReserveConsumer;
import com.suiding.layoutbind.ModuleReserveInfomatioin;
import com.suiding.layoutbind.ModuleReserveMenu;
import com.suiding.layoutbind.ModuleReserveMenuItem;
import com.suiding.layoutbind.ModuleReserveMenuItem.SelectorItemListener;
import com.suiding.layoutbind.ModuleReservePrice;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.model.Consumer;
import com.suiding.model.Coupon;
import com.suiding.model.PdtItem;
import com.suiding.model.PdtItemType;
import com.suiding.model.Product;
import com.suiding.model.RsrOrder;
import com.suiding.model.RsrOrderItem;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.ExtraUtil;

public class ReserveGourmetActivity extends ActivityBase implements
		OnClickListener, OrderMenuItemListener, SelectorItemListener, OrderRestaurantListener {
	public static final String EXTRA_STORE = "EXTRA_STORE";
	public static final String EXTRA_PRODUCT = "EXTRA_PRODUCT";
	public static final String EXTRA_COUPON = "EXTRA_COUPON";

	public static final String EXTRA_MENULIST = "EXTRA_MENULIST";
	public static final String EXTRA_MENUTYPE = "EXTRA_MENUTYPE";

	public static final String RESULT_BL_ISFULL = "RESULT_BL_ISFULL";
	
	private ModuleOrderMenu mOrderMenu;
	private ModuleReservePrice mReservePrice;
	private ModuleOrderSelected mOrderSelected;
	private ModuleOrderRestaurant mOrderRestaurant;
	private ModuleReserveConsumer mReserveConsumer;
	private ModuleReserveInfomatioin mReserveInfomatioin;

	private ModuleTitleOther mLayoutTitle = null;

	private View mBtSubmit = null;

	private Coupon mCoupon = null;
	private RsrOrder mOrder = null;
	private StoreBase mStore = null;
	private Product mProduct = null;
	private List<PdtItem> mltRsrMenu = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.reserve_resturant);

		Object items = ExtraUtil.getExtra(EXTRA_MENULIST);
		Object types = ExtraUtil.getExtra(EXTRA_MENUTYPE);

		Consumer user = SuidingApp.getLoginUser();
		mCoupon = (Coupon) ExtraUtil.getExtra(EXTRA_COUPON);
		mProduct = (Product) ExtraUtil.getExtra(EXTRA_PRODUCT);
		mStore = (StoreBase) ExtraUtil.getExtra(EXTRA_STORE);
		mOrder = new RsrOrder();
		mOrder.setUser(user);
		mOrder.setCoupon(mCoupon);
		mOrder.setStoreBase(mStore);
		mOrder.setProductBase(mProduct);
		mOrder.Count = 1;
		mOrder.OrderStatus = OrderStatus.WAITING;
		mOrder.RsrMenus = new ArrayList<RsrOrderItem>();

		mOrderMenu = new ModuleOrderMenu(this);
		mOrderSelected = new ModuleOrderSelected(this);
		mOrderRestaurant = new ModuleOrderRestaurant(this);
		mLayoutTitle = new ModuleTitleOther(this);
		mReservePrice = new ModuleReservePrice(this);
		mReserveConsumer = new ModuleReserveConsumer(this);
		mReserveInfomatioin = new ModuleReserveInfomatioin(this);

		if (items != null && types != null) {
			@SuppressWarnings("unchecked")
			List<PdtItem> ltitem = (List<PdtItem>) items;
			@SuppressWarnings("unchecked")
			List<PdtItemType> lttype = (List<PdtItemType>) types;
			mOrderMenu.setOrderMenu(lttype, ltitem);
			mOrderMenu.setOrderMenuItemListener(this);
		}
		mOrderSelected.setOrder(mOrder);
		mOrderSelected.setmSelectorItemListener(this);
		mOrderRestaurant.setData(mOrder, mStore);
		mOrderRestaurant.setRestaurantListener(this);

		mReservePrice.setProduct(mProduct);
		mReserveInfomatioin.setProduct(mProduct);
		mReserveInfomatioin.setCoupon(mCoupon);
		mReserveConsumer.setOrder(mOrder);
		mReserveConsumer.setConsumer(user);

		mBtSubmit = findViewById(R.id.reserve_hotel_submit);
		mBtSubmit.setOnClickListener(this);

		mLayoutTitle.setTitle("消费预定");
		
		statistyPrice();
	}
	
	/**
	 * 预定产品数量改变
	 * @param module
	 * @param count
	 */
	@Override
	public void onOrderCountChanged(ModuleOrderRestaurant module, int count) {
		// TODO Auto-generated method stub
		this.statistyPrice();
	}

	@Override
	public void onItemRemove(ModuleReserveMenuItem item) {
		// TODO Auto-generated method stub
		this.statistyPrice();
		mOrderMenu.removeSelected(item.mOrderItem.RsrMenu.getID());
	}

	@Override
	public void onCountChanged(ModuleReserveMenuItem item, int count) {
		// TODO Auto-generated method stub
		this.statistyPrice();
	}

	@Override
	public void onPdtItemCheckedChanged(PdtItem item, boolean isChecked) {
		// TODO Auto-generated method stub
		if (isChecked) {
			mOrderSelected.addItem(item);
		} else {
			mOrderSelected.removeItem(item);
		}
		this.statistyPrice();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.reserve_hotel_submit:
//			 Intent intents = new Intent();
//			 intents.setClass(getActivity(),
//			 OrderInfoResturantActivity.class);
//			 ExtraUtil.putExtra(OrderInfoResturantActivity.EXTRA_ORDER,
//			 mOrder);
//			 startActivity(intents);
			
			submitReserve();
			break;
		case ModuleReserveMenu.ADDID: {
			Intent intent = new Intent();
			intent.setClass(this, ListPdtItemActivity.class);
			ExtraUtil.putExtra(ListPdtItemActivity.EXTRA_OB_STORE, mStore);
			ExtraUtil.putExtra(ListPdtItemActivity.EXTRA_LT_LIST, mltRsrMenu);
			ExtraUtil.putExtra(ListPdtItemActivity.EXTRA_BL_SELECTOR, true);
			startActivityForResult(intent, 0);
			break;
		}
		}
	}

	private void statistyPrice() {
		// TODO Auto-generated method stub
		mOrder.TotalPrice = mOrder.Count * mProduct.Price;
		mOrder.NowTotalPrice = mOrder.Count * mProduct.NowPrice;

		for (RsrOrderItem item : mOrder.RsrMenus) {
			mOrder.TotalPrice += item.RsrMenu.Price * item.Count;
			mOrder.NowTotalPrice += item.RsrMenu.NowPrice * item.Count;
		}

		mReservePrice.setTotal(mOrder.TotalPrice);
		mReservePrice.setPrivilege(mOrder.NowTotalPrice);
	}

	private void submitReserve() {
		// TODO Auto-generated method stub
		mOrder.Phone = mReserveConsumer.getPhone();
		postTask(new ReserveTask(ReserveTask.TASK_SUBMIT));
		showProgressDialog("提交订单中...");
	}

	private class ReserveTask extends TaskBase {

		private static final int TASK_SUBMIT = 11;

		private boolean mIsFull = false; 
		
		public ReserveTask(int task) {
			super(SuidingApp.getLooper(), task);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onWorking(Message msg) throws Exception {
			// TODO Auto-generated method stub
			switch (mTask) {
			case TASK_SUBMIT: {
				IProductDomain pdomain = DomainFactory.getProductDomain();
				Product product = pdomain.GetByID(mProduct.getID());
				if(product.ProductCount - product.BookedCount <= 0){
					mIsFull = true;
					throw new Exception("在您编辑订单的时候，别人已经抢先订满了");
				}
				IRsrOrderDomain domain = DomainFactory.getRsrOrderDomain();
				domain.Insert(mOrder);
				// 本地缓存
				try {
					OrderEntityDao dao = new OrderEntityDao(getBaseContext());
					dao.add(new OrderEntity(mOrder));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();//handled
					AppExceptionHandler.handler(e, "消费下单页，添加订单到缓存失败");
				}
				break;
			}
			}
		}

		@Override
		public boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if (mResult == RESULT_FINISH) {
				switch (mTask) {
				case TASK_SUBMIT:
					OrderConsole.submitNewOrder(mOrder);
					
					Intent intent = new Intent();
					intent.setClass(getActivity(),
							OrderInfoResturantActivity.class);
					ExtraUtil.putExtra(OrderInfoResturantActivity.EXTRA_ORDER,
							mOrder);
					startActivity(intent);

					showToastShort("提交成功！");
					hideProgressDialog();
					finish();
					break;
				}
			} else {
				showToastLong("网络不给力啊，再试一次吧~");
				if (mTask == TASK_SUBMIT) {
					hideProgressDialog();
				}
				if(mIsFull){
					Intent intent = new Intent();
					intent.putExtra(RESULT_BL_ISFULL, true);
					setResult(RESULT_OK,intent);
					finish();
				}
			}
			return true;
		}
	}
}
