package com.suiding.activity;

import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.application.SuidingApp;
import com.suiding.domain.IRsrOrderDomain;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.layoutbind.OrderInfoModuleBase;
import com.suiding.layoutbind.OrderInfoModulePrice;
import com.suiding.layoutbind.OrderInfoModuleRestaurant;
import com.suiding.layoutbind.OrderInfoModuleStore;
import com.suiding.layoutbind.OrderInfoModuleUser;
import com.suiding.model.Order;
import com.suiding.model.Page;
import com.suiding.model.RsrOrder;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.ExtraUtil;

public class OrderInfoResturantActivity extends ActivityBase implements
		OnClickListener {

	public static final String EXTRA_ORDER = "EXTRA_ORDER";

	private ModuleTitleOther mLayoutTitle = null;
	private OrderInfoModuleUser mModuleUser = null;
	private OrderInfoModuleBase mModuleBase = null;
	private OrderInfoModulePrice mModulePrice = null;
	private OrderInfoModuleStore mModuleStore = null;
	private OrderInfoModuleRestaurant mModuleRestaurant = null;

	private View mBtSubmit = null;

	private Order mOrderBase = null;
	private RsrOrder mOrder = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.initializeLayout();
		mOrderBase  = (Order) ExtraUtil.getExtra(EXTRA_ORDER);
		if(mOrderBase instanceof RsrOrder){
			mOrder = (RsrOrder)mOrderBase;
		}else{
			mBtSubmit.setVisibility(View.GONE);
			postTask(new OrderInfoTask(OrderInfoTask.TASK_LOAD));
		}
		this.bindOrder(mOrderBase);
	}

	private void bindOrder(Order order) {
		// TODO Auto-generated method stub
		if (order != null) {
			mModuleBase.setOrder(order);
			mModulePrice.setOrder(order);
			mModuleUser.setUser(SuidingApp.getLoginUser());
			mModuleStore.setStoreBase(order.getStoreBase());
			if(order instanceof RsrOrder){
				mModuleRestaurant.setOrder((RsrOrder)order);
			}
		}
	}

	private void initializeLayout() {
		setContentView(R.layout.orderinfo_resturant);

		mLayoutTitle = new ModuleTitleOther(this);
		mModuleUser = new OrderInfoModuleUser(this);
		mModuleBase = new OrderInfoModuleBase(this);
		mModulePrice = new OrderInfoModulePrice(this);
		mModuleStore = new OrderInfoModuleStore(this);
		mModuleRestaurant = new OrderInfoModuleRestaurant(this);

		mBtSubmit = findViewById(R.id.order_resturant_submit);

		mLayoutTitle.setTitle("订单详情");
		mBtSubmit.setOnClickListener(this);

	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.order_resturant_submit:
			SuidingApp.getApp().goBackToHome(this);
//			if (this.checkData()) {
//				postTask(new ReserveTask());
//				showProgressDialog("提交订单中...");
//			} else {
//				showToastShort("订单不完整，不能提交！");
//			}
			break;
		}
	}

	private class OrderInfoTask extends TaskBase {

		public OrderInfoTask(int task) {
			super(SuidingApp.getLooper(),task);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		protected void onWorking(Message msg) throws Exception {
			// TODO Auto-generated method stub
			switch (mTask) {
			case TASK_LOAD:
				IRsrOrderDomain domain = DomainFactory.getRsrOrderDomain();
				List<RsrOrder> ltOrder = domain.GetListByPage(" where Ode_ID = '" + mOrderBase.getID() + "'", new Page(1, 0));
				if(ltOrder.size() > 0){
					RsrOrder order = ltOrder.get(0);
					if(order != null){
						mOrder = order;
					}
				}
				
				break;
			}
		}
		
		@Override
		protected boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if(mResult == RESULT_FINISH){
				switch (mTask) {
				case TASK_LOAD:
					if(mOrder != null){
						bindOrder(mOrder);
					}
					break;
				}	
			}else{
				//showToastLong("数据加载失败："+mErrors);
			}
			return true;
		}
	}
//	private boolean checkData() {
//		// TODO Auto-generated method stub
//		boolean result = true;
//		result = result && mOrder.getUser() != null;
//		result = result && mOrder.getStoreBase() != null;
//		result = result && mOrder.getProductBase() != null;
//		return result;
//	}

//	private class ReserveTask extends TaskBase {
//
//		private static final int TASK_SUBMIT = 11;
//
//		public ReserveTask() {
//			super(SuidingApp.getLooper());
//			// TODO Auto-generated constructor stub
//		}
//
//		@Override
//		protected void onWorking(Message msg) throws Exception {
//			// TODO Auto-generated method stub
//			IRsrOrderDomain domain = DomainFactory.getRsrOrderDomain();
//			domain.Insert(mOrder);
//			// 本地缓存
//			OrderEntityDao dao = new OrderEntityDao(getBaseContext());
//			dao.add(new OrderEntity(mOrder));
//		}
//
//		@Override
//		public boolean onHandle(Message msg) {
//			// TODO Auto-generated method stub
//			if (mResult == RESULT_FINISH) {
//				switch (mTask) {
//				case TASK_SUBMIT:
//					showToastShort("提交成功！");
//					hideProgressDialog();
//					finish();
//
//					Intent intent = new Intent();
//					String extra = ListOrderActivity.EXTRA_FILTE;
//					ExtraUtil.putExtra(extra, ListOrderActivity.FILTE_WAITING);
//					intent.setClass(getBaseContext(), ListOrderActivity.class);
//					startActivity(intent);
//					break;
//				}
//			} else {
//				if (mTask == TASK_SUBMIT) {
//					hideProgressDialog();
//				}
//				Toast.makeText(getBaseContext(), mErrors, Toast.LENGTH_SHORT)
//						.show();
//			}
//			return true;
//		}
//	}
}
