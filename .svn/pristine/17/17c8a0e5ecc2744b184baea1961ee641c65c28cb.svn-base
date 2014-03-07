package com.suiding.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.application.AppExceptionHandler;
import com.suiding.application.OrderConsole;
import com.suiding.application.SuidingApp;
import com.suiding.constant.OrderStatus;
import com.suiding.dao.OrderEntityDao;
import com.suiding.domain.IHotelOrderDomain;
import com.suiding.domain.IProductDomain;
import com.suiding.entity.OrderEntity;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.layoutbind.ReserveRoomItem;
import com.suiding.model.Consumer;
import com.suiding.model.Coupon;
import com.suiding.model.HotelOrder;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.DateFormatUtil;
import com.suiding.util.ExtraUtil;
import com.suiding.util.TimeSpan;

public class ReserveHotelActivity extends ActivityBase implements
		OnClickListener, DialogInterface.OnClickListener {
	public static final String EXTRA_STORE = "EXTRA_STORE";
	public static final String EXTRA_PRODUCT = "EXTRA_PRODUCT";
	public static final String EXTRA_COUPON = "EXTRA_COUPON";

	public static final String RESULT_BL_ISFULL = "RESULT_BL_ISFULL";
	
	private ModuleTitleOther mLayoutTitle = null;

	private View mBtMunus = null;
	private View mBtPlus = null;
	private View mBtSubmit = null;
	private TextView mTvNumber = null;
	private TextView mTvDays = null;
	private TextView mTvUserName = null;
	private TextView mTvUserPhone = null;
	private TextView mTvStoreName = null;
	private TextView mTvRoomName = null;
	private TextView mTvRooms = null;
	private TextView mTvTotalPrice = null;
	private TextView mTvDateIn = null;
	private TextView mTvDateOut = null;
	private EditText mEtPhone = null;
	private LinearLayout mContainer = null;

	private LayoutInflater mInflater = null;
	private List<ReserveRoomItem> mltRoomItem = new ArrayList<ReserveRoomItem>();

	private Coupon mCoupon = null;
	private StoreBase mStore = null;
	private Product mProduct = null;
	private HotelOrder mOrder = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		mInflater = LayoutInflater.from(this);

		Consumer user = SuidingApp.getLoginUser();

		mStore = (StoreBase) ExtraUtil.getExtra(EXTRA_STORE);
		mCoupon = (Coupon) ExtraUtil.getExtra(EXTRA_COUPON);
		mProduct = (Product) ExtraUtil.getExtra(EXTRA_PRODUCT);
		mOrder = new HotelOrder();
		mOrder.setUser(user);
		mOrder.setCoupon(mCoupon);
		mOrder.setStoreBase(mStore);
		mOrder.setProductBase(mProduct);
		mOrder.Count = 0;//后面的 this.onClick(mBtPlus); 会把 count 变成 1
		mOrder.OrderStatus = OrderStatus.WAITING;

		this.initializeLayout();
	}

	private void initializeLayout() {
		setContentView(R.layout.reserve_hotel);
		mLayoutTitle = new ModuleTitleOther(this);

		mBtPlus = findViewById(R.id.reserve_hotel_bt_plus);
		mBtMunus = findViewById(R.id.reserve_hotel_bt_munus);
		mBtSubmit = findViewById(R.id.reserve_hotel_submit);
		mTvUserName = findTextViewById(R.id.reserve_hotel_user_name);
		mTvUserPhone = findTextViewById(R.id.reserve_hotel_user_phone);
		mEtPhone = findEditTextById(R.id.reserve_hotel_et_phone);
		mTvNumber = findTextViewById(R.id.reserve_hotel_tv_number);
		mTvDays = findTextViewById(R.id.reserve_hotel_days);
		mTvRoomName = findTextViewById(R.id.reserve_hotel_product);
		mTvRooms = findTextViewById(R.id.reserve_hotel_products);
		mContainer = findLinearLayoutById(R.id.reserve_hotel_1_stayman);

		mTvStoreName = findTextViewById(R.id.reserve_hotel_store);
		mTvDateIn = findTextViewById(R.id.reserve_hotel_datein);
		mTvDateOut = findTextViewById(R.id.reserve_hotel_dateout);
		mTvTotalPrice = findTextViewById(R.id.reserve_hotel_totalprice);

		
		Consumer user = SuidingApp.getLoginUser();
		if (user.Name == null || user.Name.length() == 0) {
			Intent intent = new Intent();
			ExtraUtil.putExtra(EditAccountActivity.EXTRA_OB_CONSUMER, user);
			ExtraUtil.putExtra(EditAccountActivity.EXTRA_IT_ACTION, EditAccountActivity.ACTION_NAME);
			intent.setClass(this, EditAccountActivity.class);
			showToastLong("请先完善您姓名再开始预定");
			startActivity(intent);
			this.finish();
			return;
		} else {
			mTvUserName.setText(user.Name);
		}

		if (user.IsPhoneVerifi == false) {
			Intent intent = new Intent();
			ExtraUtil.putExtra(EditAccountActivity.EXTRA_OB_CONSUMER, user);
			ExtraUtil.putExtra(EditAccountActivity.EXTRA_IT_ACTION, EditAccountActivity.ACTION_PHONE);
			intent.setClass(this, EditAccountActivity.class);
			showToastLong("请先认证您的手机号码再开始预定");
			startActivity(intent);
			this.finish();
			return;
		} else {
			mTvUserPhone.setText(user.PhoneNo);
		}

		mContainer.removeAllViews();
		this.onClick(mBtPlus);

		mTvStoreName.setText(mStore.Name);
		mTvRoomName.setText(mProduct.Name);
		mTvNumber.setText(String.valueOf(mOrder.Count));

		String content = mProduct.Name + " " + mOrder.Count + " 间";
		mTvRooms.setText(content);

		Calendar canlender = Calendar.getInstance();
		int year = canlender.get(Calendar.YEAR);
		int month = canlender.get(Calendar.MONTH);
		int day = canlender.get(Calendar.DAY_OF_MONTH);
		mOrder.InDate = new GregorianCalendar(year, month, day).getTime();
		mOrder.OutDate = new GregorianCalendar(year, month, day + 1).getTime();

		mTvDateIn.setText(DateFormatUtil.format("M-d", mOrder.InDate));
		mTvDateOut.setText(DateFormatUtil.format("M-d", mOrder.OutDate));

		mLayoutTitle.setTitle("入住信息");
		mTvDateIn.setOnClickListener(this);
		mBtMunus.setOnClickListener(this);
		mBtPlus.setOnClickListener(this);
		mTvDateOut.setOnClickListener(this);
		mTvNumber.setOnClickListener(this);
		mBtSubmit.setOnClickListener(this);
		
		countTotalPrice();
	}


	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		mTvNumber.setText(String.valueOf(++which));
		mltRoomItem.clear();
		mContainer.removeAllViews();
		for (int i = 0; i < which; i++) {
			this.addRoomItem(i);
		}
		this.countTotalPrice();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.reserve_hotel_submit:
			if (this.checkData()) {
				//Intent intent = new Intent();
				//intent.setClass(this, OrderInfoHotelActivity.class);
				//ExtraUtil.putExtra(OrderInfoHotelActivity.EXTRA_ORDER, mOrder);
				//startActivity(intent);
				postTask(new ReserveTask());
				showProgressDialog("提交订单中...");
			}
			break;
		case R.id.reserve_hotel_tv_number:
			String[] items = new String[] {
					"1 间 "+mProduct.Name,
					"2 间 "+mProduct.Name,
					"3 间 "+mProduct.Name,
					"4 间 "+mProduct.Name,
					"5 间 "+mProduct.Name,
			};
			Builder builder = new Builder(this);
			builder.setTitle("房间数量");
			builder.setIcon(android.R.drawable.ic_dialog_info);
			builder.setItems(items, this);
			builder.show();
			break;
		case R.id.reserve_hotel_bt_munus:
			if (mOrder.Count > 1) {
				mTvNumber.setText(String.valueOf(--mOrder.Count));
				this.delRoomItem(mltRoomItem.size() - 1);
				this.countTotalPrice();
			}
			break;
		case R.id.reserve_hotel_bt_plus:
			if (mOrder.Count < 5) {
				mTvNumber.setText(String.valueOf(++mOrder.Count));
				this.addRoomItem(mOrder.Count);
				this.countTotalPrice();
			}
			break;
		case R.id.reserve_hotel_datein: {
			Calendar canlender = Calendar.getInstance();
			canlender.setTime(mOrder.InDate);
			int year = canlender.get(Calendar.YEAR);
			int month = canlender.get(Calendar.MONTH);
			int day = canlender.get(Calendar.DAY_OF_MONTH);
			Dialog tDialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, int year,
								int month, int day) {
							// TODO Auto-generated method stub
							Date newdate = new GregorianCalendar(year, month,
									day).getTime();
							if (newdate.after(mOrder.OutDate)
									|| newdate.equals(mOrder.InDate)) {
								showToastShort("住店时间不能晚于退房时间");
								return;
							} else {
								if(newdate.before(new Date())){
									showToastShort("住店时间不能早于今天");
									return;
								}
								mOrder.InDate = newdate;
								mTvDateIn.setText(DateFormatUtil.format("M-d",
										mOrder.InDate));
								countTotalPrice();
							}
						}
					}, year, month, day);
			tDialog.show();
			tDialog.setCancelable(true);
			break;
		}
		case R.id.reserve_hotel_dateout: {
			Calendar canlender = Calendar.getInstance();
			canlender.setTime(mOrder.OutDate);
			int year = canlender.get(Calendar.YEAR);
			int month = canlender.get(Calendar.MONTH);
			int day = canlender.get(Calendar.DAY_OF_MONTH);
			Dialog tDialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, int year,
								int month, int day) {
							// TODO Auto-generated method stub
							Date newdate = new GregorianCalendar(year, month,
									day).getTime();
							if (newdate.before(mOrder.InDate)
									|| newdate.equals(mOrder.InDate)) {
								showToastShort("退房时间不能早于住店时间");
								return;
							} else {
								mOrder.OutDate = newdate;
								mTvDateOut.setText(DateFormatUtil.format("M-d",mOrder.OutDate));
								countTotalPrice();
							}
						}
					}, year, month, day);
			tDialog.show();
			tDialog.setCancelable(true);
			break;
		}
		}
	}

	private void addRoomItem(int index) {
		// TODO Auto-generated method stub
		View view = mInflater.inflate(R.layout.reserve_room_item, null);
		ReserveRoomItem item = new ReserveRoomItem(view);
		item.setText("房间" + index);
		item.setEditText(SuidingApp.getLoginUser().Name);
		mltRoomItem.add(item);
		mContainer.addView(view);
	}

	private void delRoomItem(int index) {
		// TODO Auto-generated method stub
		if(index < mltRoomItem.size()){
			ReserveRoomItem item = mltRoomItem.get(index);
			mContainer.removeView(item.mLayout);
			mltRoomItem.remove(index);
		}
	}

	private void countTotalPrice() {
		// TODO Auto-generated method stub
		TimeSpan span = TimeSpan.FromDate(mOrder.InDate,mOrder.OutDate);
		int days = (int)span.getTotalDays();
		mOrder.TotalPrice = mOrder.Count * mProduct.Price * days;
		mOrder.NowTotalPrice = mOrder.Count * mProduct.NowPrice * days;
		mTvDays.setText(" " + days + " ");
		mTvTotalPrice.setText(String.format("￥%.0f",mOrder.NowTotalPrice));

		String content = mProduct.Name + " " + mOrder.Count + " 间";
		mTvRooms.setText(content);
	}
	
	private boolean checkData() {
		// TODO Auto-generated method stub
		mOrder.Rooms = "";
		for (ReserveRoomItem item : mltRoomItem) {
			if (item.getEditText().length() == 0) {
				String tip = "请按照要求输入姓名";
				Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
				return false;
			}
			mOrder.Rooms += item.getEditText().toString() + ",";
		}
		mOrder.Phone = mEtPhone.getText().toString();
		mOrder.Rooms = mOrder.Rooms.substring(0, mOrder.Rooms.length() - 1);
		return true;
	}

	private class ReserveTask extends TaskBase {

		private boolean mIsFull = false;

		public ReserveTask() {
			super(SuidingApp.getLooper());
			// TODO Auto-generated method stub
		}

		@Override
		protected void onWorking(Message msg) throws Exception {
			// TODO Auto-generated method stub
			IProductDomain pdomain = DomainFactory.getProductDomain();
			Product product = pdomain.GetByID(mProduct.getID());
			if(product.ProductCount - product.BookedCount <= 0){
				mIsFull = true;
				throw new Exception("在您编辑订单的时候，别人已经抢先订满了");
			}
			// 网络提交
			IHotelOrderDomain domain = DomainFactory.getHotelOrderDomain();
			domain.Insert(mOrder);
			// 本地缓存
			try {
				OrderEntityDao dao = new OrderEntityDao(getBaseContext());
				dao.add(new OrderEntity(mOrder));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.handler(e, "酒店下单页，添加订单到缓存失败");
			}
		}

		@Override
		public boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if (mResult == RESULT_FINISH) {
				OrderConsole.submitNewOrder(mOrder);

				Intent intent = new Intent();
				intent.setClass(getActivity(), OrderInfoHotelActivity.class);
				ExtraUtil.putExtra(OrderInfoHotelActivity.EXTRA_ORDER, mOrder);
				startActivity(intent);
				
				showToastShort("提交成功！");
				hideProgressDialog();
				finish();
			} else {
				hideProgressDialog();
				showToastLong("网络不给力啊，再试一次吧~");
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
