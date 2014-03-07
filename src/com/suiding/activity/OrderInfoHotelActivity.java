package com.suiding.activity;

import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.application.SuidingApp;
import com.suiding.domain.IHotelOrderDomain;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.layoutbind.OrderInfoModuleBase;
import com.suiding.layoutbind.OrderInfoModulePrice;
import com.suiding.layoutbind.OrderInfoModuleUser;
import com.suiding.model.HotelOrder;
import com.suiding.model.Order;
import com.suiding.model.Page;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.DateFormatUtil;
import com.suiding.util.ExtraUtil;
import com.suiding.util.TimeSpan;

public class OrderInfoHotelActivity extends ActivityBase implements
		OnClickListener {

	public static final String EXTRA_ORDER = "EXTRA_ORDER";

	private ModuleTitleOther mLayoutTitle = null;

	private View mBtSubmit = null;
	private TextView mTvNumber = null;
	private TextView mTvDays = null;
	private TextView mTvStoreName = null;
	private TextView mTvRoomName = null;
	private TextView mTvTotalPrice = null;
	private TextView mTvDateIn = null;
	private TextView mTvDateOut = null;
	
	private OrderInfoModuleUser mModuleUser = null;
	private OrderInfoModuleBase mModuleBase = null;
	private OrderInfoModulePrice mModulePrice = null;

	private Order mOrderBase = null;
	private HotelOrder mOrder = null;

	private TextView mTvHirer = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.initializeLayout();
		mOrderBase = (Order) ExtraUtil.getExtra(EXTRA_ORDER);
		
		if(mOrderBase instanceof HotelOrder){
			mOrder = (HotelOrder)mOrderBase;
		}else{
			mBtSubmit.setVisibility(View.GONE);
			postTask(new OrderInfoTask(OrderInfoTask.TASK_LOAD));
		}
		this.bindOrder(mOrderBase);
	}

	private void bindOrder(Order order) {
		// TODO Auto-generated method stub
		mModuleBase.setOrder(order);
		mModulePrice.setOrder(order);
		mModuleUser.setUser(SuidingApp.getLoginUser());
		
		mTvNumber.setText(String.valueOf(order.Count));

		StoreBase store = order.getStoreBase();
		if (store != null) {
			mTvStoreName.setText(store.Name);
		} else {
			mTvStoreName.setText("数据丢失");
		}
		Product product = order.getProductBase();
		if (product != null) {
			double totalprice = order.Count*product.NowPrice;
			mTvRoomName.setText(product.Name);
			mTvTotalPrice.setText(String.format("￥%.0f",totalprice));
		} else {
			mTvRoomName.setText("数据丢失");
			mTvTotalPrice.setText("数据丢失");
		}

		if(order instanceof HotelOrder){
			HotelOrder horder = (HotelOrder)order;
			TimeSpan span = TimeSpan.FromDate(horder.InDate,horder.OutDate);
			int days = (int)span.getTotalDays();
			
			mTvDays.setText("" + days);
			mTvDateIn.setText(DateFormatUtil.format("M-d", horder.InDate));
			mTvDateOut.setText(DateFormatUtil.format("M-d", horder.OutDate));
			mTvHirer.setText(horder.Rooms);
		}
	}

	private void initializeLayout() {
		setContentView(R.layout.orderinfo_hotel);
		mLayoutTitle = new ModuleTitleOther(this);

		mBtSubmit = findViewById(R.id.order_hotel_submit);
		mTvDays = findTextViewById(R.id.order_hotel_days);
		mTvDateIn = findTextViewById(R.id.order_hotel_datein);
		mTvDateOut = findTextViewById(R.id.order_hotel_dateout);
		mTvRoomName = findTextViewById(R.id.order_hotel_product);
		mTvNumber = findTextViewById(R.id.order_hotel_tv_number);
		mTvStoreName = findTextViewById(R.id.order_hotel_storename);
		mTvTotalPrice = findTextViewById(R.id.order_hotel_totalprice);

		mTvHirer = findTextViewById(R.id.order_hotel_hirer);
		
		mModuleUser = new OrderInfoModuleUser(this);
		mModuleBase = new OrderInfoModuleBase(this);
		mModulePrice = new OrderInfoModulePrice(this);

		mLayoutTitle.setTitle("订单详情");
		mBtSubmit.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.order_hotel_submit:
			SuidingApp.getApp().goBackToHome(this);
			//if (this.checkData()) {
			//	postTask(new ReserveTask());
			//	showProgressDialog("提交订单中...");
			//} else {
			//	showToastShort("订单不完整，不能提交！");
			//}
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
				IHotelOrderDomain domain = DomainFactory.getHotelOrderDomain();
				List<HotelOrder> ltOrder = domain.GetListByPage(" where Ode_ID = '" + mOrderBase.getID() + "'", new Page(1, 0));
				if(ltOrder.size() > 0){
					HotelOrder order = ltOrder.get(0);
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
//		public ReserveTask() {
//			super(SuidingApp.getLooper());
//			// TODO Auto-generated method stub
//		}
//
//		@Override
//		protected void onWorking(Message msg) throws Exception {
//			// TODO Auto-generated method stub
//			// 网络提交
//			IOrderDomain domain = DomainFactory.getOrderDomain();
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
//				showToastShort("提交成功！");
//				hideProgressDialog();
//				finish();
//
//				Intent intent = new Intent();
//				String extra = ListOrderActivity.EXTRA_FILTE;
//				ExtraUtil.putExtra(extra,ListOrderActivity.FILTE_WAITING);
//				intent.setClass(getBaseContext(), ListOrderActivity.class);
//				startActivity(intent);
//			} else {
//				hideProgressDialog();
//				showToastLong(mErrors);
//			}
//			return true;
//		}
//	}
}
