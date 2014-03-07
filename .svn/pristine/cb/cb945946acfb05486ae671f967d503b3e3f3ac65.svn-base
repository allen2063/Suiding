package com.suiding.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.application.SuidingApp;
import com.suiding.constant.OrderStatus;
import com.suiding.dao.OrderEntityDao;
import com.suiding.domain.IPdtItemDomain;
import com.suiding.domain.IRsrOrderDomain;
import com.suiding.entity.OrderEntity;
import com.suiding.layoutbind.ModuleReserveConsumer;
import com.suiding.layoutbind.ModuleReserveInfomatioin;
import com.suiding.layoutbind.ModuleReserveNumber;
import com.suiding.layoutbind.ModuleReservePrice;
import com.suiding.layoutbind.ModuleReserveMenu;
import com.suiding.layoutbind.ModuleReserveTime;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.model.Consumer;
import com.suiding.model.Coupon;
import com.suiding.model.Page;
import com.suiding.model.PdtItem;
import com.suiding.model.Product;
import com.suiding.model.RsrMenu;
import com.suiding.model.RsrOrder;
import com.suiding.model.RsrOrderItem;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.ExtraUtil;

public class ReserveGourmetOldActivity extends ActivityBase implements
		OnClickListener {
	public static final String EXTRA_STORE = "EXTRA_STORE";
	public static final String EXTRA_PRODUCT = "EXTRA_PRODUCT";
	public static final String EXTRA_COUPON = "EXTRA_COUPON";

	private ModuleReserveTime mReserveTime;
	private ModuleReservePrice mReservePrice;
	private ModuleReserveMenu mReserveSelector;
	private ModuleReserveNumber mReserveNumber;
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
		
		mLayoutTitle = new ModuleTitleOther(this);
		mReserveTime = new ModuleReserveTime(this);
		mReservePrice = new ModuleReservePrice(this);
		mReserveNumber = new ModuleReserveNumber(this,mOrder);
		mReserveSelector = new ModuleReserveMenu(this,mOrder);
		mReserveConsumer = new ModuleReserveConsumer(this);
		mReserveInfomatioin = new ModuleReserveInfomatioin(this);

		mBtSubmit = findViewById(R.id.reserve_hotel_submit);

		mReserveSelector.hide();
		mBtSubmit.setOnClickListener(this);
		mReserveSelector.setOnSelectorAddListener(this);
		
		mLayoutTitle.setTitle(R.string.title_activity_reserve);

		mReserveNumber.setPeople(1);
		mReserveNumber.setProduct(1);
		mReserveTime.setOrder(mOrder);
		mReserveNumber.setOrder(mOrder);
		mReservePrice.setProduct(mProduct);
		mReserveSelector.setProduct(mProduct);
		mReserveInfomatioin.setProduct(mProduct);
		mReserveConsumer.setConsumer(user);
		statistyPrice();
		
		postTask(new ReserveTask(ReserveTask.TASK_LOADRSRMENU));
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.reserve_hotel_submit:
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

	private void statistyPrice(){
		// TODO Auto-generated method stub
		mOrder.TotalPrice = mOrder.Count*mProduct.Price;
		mOrder.NowTotalPrice = mOrder.Count*mProduct.NowPrice;
		
		for (RsrOrderItem item : mOrder.RsrMenus) {
			mOrder.TotalPrice += item.RsrMenu.Price*item.Count;
			mOrder.NowTotalPrice += item.RsrMenu.NowPrice*item.Count;
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			PdtItem item = (PdtItem) ExtraUtil.getExtra(
					ListPdtItemActivity.RESULT_OB_SELECTOR, null);
			if (item != null) {
				RsrOrderItem rsritem = new RsrOrderItem();
				rsritem.Count = 1;
				rsritem.setOde_ID(mOrder.getID());
				rsritem.setRsrMenu(new RsrMenu(item));
				mReserveSelector.addItem(rsritem);
			}
		}
	}

	private class ReserveTask extends TaskBase {

		private static final int TASK_LOADRSRMENU = 10;
		private static final int TASK_SUBMIT = 11;

		public ReserveTask(int task) {
			super(SuidingApp.getLooper(),task);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onWorking(Message msg) throws Exception {
			// TODO Auto-generated method stub
			switch (mTask) {
			case TASK_LOADRSRMENU: {
				Page page = new Page(40,0);
				IPdtItemDomain domain = DomainFactory.getPdtItemDomain();
				mltRsrMenu = domain.GetListByPbID(mProduct.getID(), page);
				break;
			}
			case TASK_SUBMIT:{
				IRsrOrderDomain domain = DomainFactory.getRsrOrderDomain();
				domain.Insert(mOrder);
				//本地缓存
				OrderEntityDao dao = new OrderEntityDao(getBaseContext());
				dao.add(new OrderEntity(mOrder));
				break;
			}
			}
		}

		@Override
		public boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if (mResult == RESULT_FINISH) {
				switch (mTask) {
				case TASK_LOADRSRMENU:
					mReserveSelector.show();
					break;
				case TASK_SUBMIT:
					Toast.makeText(getBaseContext(), "提交成功！", Toast.LENGTH_LONG).show();
					hideProgressDialog();
					finish();

					Intent intent = new Intent();
					String extra = ListOrderActivity.EXTRA_EM_FILTE;
					ExtraUtil.putExtra(extra, ListOrderActivity.FILTE_WAITING);
					intent.setClass(getBaseContext(), ListOrderActivity.class);
					startActivity(intent);
					break;
				}
			} else {
				if(mTask == TASK_SUBMIT){
					hideProgressDialog();
				}
				showToastShort("网络不给力啊，再试一次吧~");
//				Toast.makeText(getBaseContext(), mErrors, Toast.LENGTH_SHORT)
//						.show();
			}
			return true;
		}
	}
}
