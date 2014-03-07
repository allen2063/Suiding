package com.suiding.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.application.SuidingApp;
import com.suiding.constant.OrderStatus;
import com.suiding.dao.OrderEntityDao;
import com.suiding.entity.OrderEntity;
import com.suiding.layoutbind.ModuleReserveMenu;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.layoutbind.ReserveRoomItem;
import com.suiding.model.Consumer;
import com.suiding.model.Coupon;
import com.suiding.model.HotelOrder;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.DateFormatUtil;
import com.suiding.util.ExtraUtil;
import com.suiding.util.TimeSpan;

public class ReserveHotelOldActivity extends ActivityBase implements
		OnClickListener {
	public static final String EXTRA_STORE = "EXTRA_STORE";
	public static final String EXTRA_PRODUCT = "EXTRA_PRODUCT";
	public static final String EXTRA_COUPON = "EXTRA_COUPON";

	private ModuleTitleOther mLayoutTitle = null;

	// page1
	private View mBtSubmit = null;
	private View mBtMunus = null;
	private View mBtPlus = null;
	private TextView mTvNumber = null;
	private TextView mTvName = null;
	private TextView mTvNames = null;
	private TextView mTvPhone = null;
	private TextView mTvStore = null;
	private TextView mTvProduct = null;
	private TextView mTvTotalPrice = null;
	private TextView mTvDateIn = null;
	private TextView mTvDateOut = null;
	private EditText mEtPhone = null;
	private LinearLayout mContainer1 = null;

	//private LinearLayout mContainer2 = null;
	// private LinearLayout parent1=null;
	// private LinearLayout parent2=null;
	private LayoutInflater mInflater = null;
	private List<ReserveRoomItem> mltRoomItem = new ArrayList<ReserveRoomItem>();

	private int mCurpage = 1;

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
		mOrder.Count = 0;
		mOrder.OrderStatus = OrderStatus.WAITING;

		this.initializeLayout1();
		this.onClick(mBtPlus);

	}

	private void initializeLayout1() {
		setContentView(R.layout.reserve_hotel_1);
		mLayoutTitle = new ModuleTitleOther(this);

		mBtPlus = findViewById(R.id.reserve_hotel_bt_plus);
		mBtMunus = findViewById(R.id.reserve_hotel_bt_munus);
		mBtSubmit = findViewById(R.id.reserve_hotel_submit);
		mTvName = (TextView) findViewById(R.id.reserve_hotel_user_name);
		mTvPhone = (TextView) findViewById(R.id.reserve_hotel_user_phone);
		mEtPhone = (EditText) findViewById(R.id.reserve_hotel_et_phone);
		mTvNumber = (TextView) findViewById(R.id.reserve_hotel_tv_number);
		mTvProduct = (TextView) findViewById(R.id.reserve_hotel_product);
		mContainer1 = (LinearLayout) findViewById(R.id.reserve_hotel_1_stayman);

		Consumer user = SuidingApp.getLoginUser();
		if (user.Name == null || user.Name.length() == 0) {
			String tip = "请先完善您姓名再开始预定";
			Toast.makeText(this, tip, Toast.LENGTH_LONG).show();
			Intent intent = new Intent();
			ExtraUtil.putExtra(EditAccountActivity.EXTRA_OB_CONSUMER, user);
			intent.setClass(this, EditAccountActivity.class);
			startActivity(intent);
			this.finish();
			return;
		} else {
			mTvName.setText(user.Name);
		}

		if (user.IsPhoneVerifi == false) {
			String tip = "请先认证您的手机号码再开始预定";
			Toast.makeText(this, tip, Toast.LENGTH_LONG).show();
			Intent intent = new Intent();
			ExtraUtil.putExtra(EditAccountActivity.EXTRA_OB_CONSUMER, user);
			intent.setClass(this, EditAccountActivity.class);
			startActivity(intent);
			this.finish();
			return;
		} else {
			mTvPhone.setText(user.PhoneNo);
		}

		mTvProduct.setText(mProduct.Name);

		mContainer1.removeAllViews();
		for (int i = 0; i < mltRoomItem.size(); i++) {
			ReserveRoomItem reserve = mltRoomItem.get(i);
			View mLayout = reserve.mLayout;
			LinearLayout parent = (LinearLayout) mLayout.getParent();
			parent.removeView(mLayout);
			mContainer1.addView(mLayout);
		}

		mTvNumber.setText(String.valueOf(mOrder.Count));

		mBtSubmit.setOnClickListener(this);
		mBtMunus.setOnClickListener(this);
		mBtPlus.setOnClickListener(this);
		mLayoutTitle.setOnGoBackListener(this);

		mLayoutTitle.setTitle(R.string.title_activity_reserve);
	}

	private void initializeLayout2() {
		setContentView(R.layout.reserve_hotel_2);
		mLayoutTitle = new ModuleTitleOther(this);
		mLayoutTitle.setOnGoBackListener(this);
		mLayoutTitle.setTitle(R.string.title_activity_reserve);

		mBtSubmit = findViewById(R.id.reserve_hotel_submit);
		mBtSubmit.setOnClickListener(this);

		mTvName = (TextView) findViewById(R.id.reserve_hotel_user_name);
		mTvNames = (TextView) findViewById(R.id.reserve_hotel_names);
		mTvPhone = (TextView) findViewById(R.id.reserve_hotel_user_phone);
		mTvNumber = (TextView) findViewById(R.id.reserve_hotel_total);
		mTvStore = (TextView) findViewById(R.id.reserve_hotel_store);
		mTvProduct = (TextView) findViewById(R.id.reserve_hotel_product);
		mTvTotalPrice = (TextView) findViewById(R.id.reserve_hotel_totalprice);
		mTvDateIn = (TextView) findViewById(R.id.reserve_hotel_datein);
		mTvDateOut = (TextView) findViewById(R.id.reserve_hotel_dateout);
		
		mTvDateIn.setOnClickListener(this);
		mTvDateOut.setOnClickListener(this);

		mTvStore.setText(mStore.Name);
		mTvNames.setText(mOrder.Rooms);

		Consumer user = SuidingApp.getLoginUser();
		mTvName.setText(user.Name);
		mTvPhone.setText(user.PhoneNo);
		
		String content = mProduct.Name + " " + mOrder.Count + " 间";
		mTvProduct.setText(content);

		Calendar canlender = Calendar.getInstance();
		int year = canlender.get(Calendar.YEAR);
		int month = canlender.get(Calendar.MONTH);
		int day = canlender.get(Calendar.DAY_OF_MONTH);
		mOrder.InDate = new GregorianCalendar(year, month, day).getTime();
		mOrder.OutDate = new GregorianCalendar(year, month, day + 1).getTime();

		mTvDateIn.setText(DateFormatUtil.format("M-d", mOrder.InDate));
		mTvDateOut.setText(DateFormatUtil.format("M-d", mOrder.OutDate));
		
		mTvTotalPrice.setText(String.format("￥%.0f", mOrder.Count*mProduct.Price));
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (mCurpage == 2) {
				mCurpage = 1;
				initializeLayout1();
				return true;
			}
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.reserve_hotel_submit:
			if (mCurpage == 1) {
				if (this.checkData1()) {
					mCurpage = 2;
					initializeLayout2();
				}
			} else {
				postTask(new ReserveTask());
				showProgressDialog("提交订单中...");
			}
			break;
		case ModuleReserveMenu.ADDID:
			break;
		case ModuleTitleOther.ID_GOBACK:
			if (mCurpage == 2) {
				mCurpage = 1;
				initializeLayout1();
			} else {
				this.finish();
			}
			break;
		case R.id.reserve_hotel_bt_munus:
			if (mOrder.Count > 1) {
				mTvNumber.setText(String.valueOf(--mOrder.Count));
				mContainer1
						.removeView(mltRoomItem.get(mltRoomItem.size() - 1).mLayout);
				mltRoomItem.remove(mltRoomItem.size() - 1);
			}
			break;
		case R.id.reserve_hotel_bt_plus:
			if (mOrder.Count < 5) {
				mTvNumber.setText(String.valueOf(++mOrder.Count));
				View view = mInflater.inflate(R.layout.reserve_room_item, null);
				ReserveRoomItem rii = new ReserveRoomItem(view);
				rii.setText("房间" + mOrder.Count);
				rii.setEditText(SuidingApp.getLoginUser().Name);
				mltRoomItem.add(rii);
				mContainer1.addView(view);
			}
			break;
		case R.id.reserve_hotel_datein:{
			Calendar canlender = Calendar.getInstance();
			canlender.setTime(mOrder.InDate);
			int year = canlender.get(Calendar.YEAR);
			int month = canlender.get(Calendar.MONTH);
			int day = canlender.get(Calendar.DAY_OF_MONTH);
			Dialog tDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int month,int day) {
					// TODO Auto-generated method stub
					Date newdate = new GregorianCalendar(year, month, day).getTime();
					if(newdate.after(mOrder.OutDate) || newdate.equals(mOrder.InDate)){
						String tip = "住店时间不能晚于退房时间";
						Toast.makeText(getBaseContext(), tip, Toast.LENGTH_SHORT).show();
						return;
					}else{
						mOrder.InDate = newdate;		
						TimeSpan span = TimeSpan.FromDate(newdate,mOrder.OutDate);
						Double totalprice = mOrder.Count*mProduct.Price*span.getTotalDays();
						mTvDateIn.setText(DateFormatUtil.format("M-d", mOrder.InDate));
						mTvNumber.setText(" "+(int)span.getTotalDays()+" ");
						mTvTotalPrice.setText(String.format("￥%.0f", totalprice));
					}
				}
			}, year, month, day);
			tDialog.show();
			tDialog.setCancelable(true);
			break;
		}
		case R.id.reserve_hotel_dateout:{
			Calendar canlender = Calendar.getInstance();
			canlender.setTime(mOrder.OutDate);
			int year = canlender.get(Calendar.YEAR);
			int month = canlender.get(Calendar.MONTH);
			int day = canlender.get(Calendar.DAY_OF_MONTH);
			Dialog tDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int month,int day) {
					// TODO Auto-generated method stub
					Date newdate = new GregorianCalendar(year, month, day).getTime();
					if(newdate.before(mOrder.InDate) || newdate.equals(mOrder.InDate)){
						String tip = "退房时间不能早于住店时间";
						Toast.makeText(getBaseContext(), tip, Toast.LENGTH_SHORT).show();
						return;
					}else{
						mOrder.OutDate = newdate;		
						TimeSpan span = TimeSpan.FromDate(mOrder.InDate,newdate);
						Double totalprice = mOrder.Count*mProduct.Price*span.getTotalDays();
						mTvDateOut.setText(DateFormatUtil.format("M-d", mOrder.OutDate));
						mTvNumber.setText(" "+(int)span.getTotalDays()+" ");
						mTvTotalPrice.setText(String.format("￥%.0f", totalprice));
					}
				}
			}, year, month, day);
			tDialog.show();
			tDialog.setCancelable(true);
			break;
		}
		}
	}

	private boolean checkData1() {
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

		public ReserveTask(){
			super(SuidingApp.getLooper());
			// TODO Auto-generated method stub
		}
		
		@Override
		protected void onWorking(Message msg) throws Exception {
			// TODO Auto-generated method stub
			//网络提交
			//IOrderDomain domain = DomainFactory.getOrderDomain();
			//domain.Insert(mOrder);
			//本地缓存
			OrderEntityDao dao = new OrderEntityDao(getBaseContext());
			dao.add(new OrderEntity(mOrder));
		}

		@Override
		public boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if(mResult == RESULT_FINISH){
				Toast.makeText(getBaseContext(), "提交成功！", Toast.LENGTH_LONG).show();
				hideProgressDialog();
				finish();

				Intent intent = new Intent();
				String extra = ListOrderActivity.EXTRA_EM_FILTE;
				ExtraUtil.putExtra(extra, ListOrderActivity.FILTE_WAITING);
				intent.setClass(getBaseContext(), ListOrderActivity.class);
				startActivity(intent);
			}else{
				hideProgressDialog();
				showToastLong("网络不给力啊，再试一次吧~");
				//Toast.makeText(getBaseContext(), mErrors, Toast.LENGTH_LONG).show();
			}
			return true;
		}
	}
}
