package com.suiding.layoutbind;

import java.util.Calendar;
import java.util.Date;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.layoutbind.ModuleSetNumber.OnNumberChangedListener;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.RsrOrder;
import com.suiding.model.StoreBase;
import com.suiding.util.DateFormatUtil;
import com.suiding.util.MeasureUtil;

public class ModuleOrderRestaurant implements ILayout, TextWatcher, OnNumberChangedListener{
	
	public interface OrderRestaurantListener{
		void onOrderCountChanged(ModuleOrderRestaurant module,int count);
	}

	//可调时间间隔 20 分钟
	private static final int STEP_MINUTE = 20; 
	//单位小时可调次数
	private static final float STEP_HOURCONUT = 60f/STEP_MINUTE;
	
    private View mLayout = null;
    private EditText mTvPeoples = null;
    private TextView mTvArrival = null;

    private ModuleSetNumber mNumberPeople = null;
    private ModuleSetNumber mNumberProduct = null;
    private ModuleSetNumber mNumberArrival = null;

	private boolean mIsValid = false;

	private RsrOrder mOrder = null;

	private OrderRestaurantListener mListener = null;
    
    public ModuleOrderRestaurant(Pageable page)
    {
        // TODO Auto-generated constructor stub
    	mLayout = page.findViewById(R.id.reservemodule_resturant_contianer);
    	mTvPeoples = page.findEditTextById(R.id.reservemodule_resturant_peoples);
        mTvArrival = page.findTextViewById(R.id.reservemodule_resturant_arrival);
    	mIsValid = mLayout != null;
		if(mIsValid){
			mLayout = (View) mLayout.getParent();
			View vplus = page.findViewById(R.id.reservemodule_resturant_arrival_plus);
			View vminus = page.findViewById(R.id.reservemodule_resturant_arrival_munus);
			View vpeople = page.findViewById(R.id.reservemodule_resturant_numpeople);
			View vprodoct = page.findViewById(R.id.reservemodule_resturant_numproduct);
			mNumberArrival = new ModuleSetNumber(vminus,null,vplus);
			mNumberPeople = new ModuleSetNumber(vpeople);
			mNumberProduct = new ModuleSetNumber(vprodoct);
			mNumberPeople.setName("到店人数");
			mNumberProduct.setName("预定数量");
			
			int interval = MeasureUtil.measureView(mTvArrival).x;
			mNumberPeople.setInterval(interval);
			mNumberProduct.setInterval(interval);
		}
    }
    
    public void setRestaurantListener(OrderRestaurantListener mListener) {
		this.mListener = mListener;
	}

    public void setOrder(RsrOrder order) {
		this.mOrder = order;
		if(mIsValid && order != null){
			Calendar canlender = Calendar.getInstance();
			int hour = canlender.get(Calendar.HOUR_OF_DAY);
			canlender.set(Calendar.HOUR_OF_DAY, hour + 2);
			canlender.set(Calendar.MINUTE, 0);

			mTvPeoples.addTextChangedListener(this);
			
			mOrder.Count = 1;
			mOrder.PeopleNumber = 1;
			mOrder.ArrivalTime = canlender.getTime();

			mNumberPeople.setOnNumberChangedListener(this);
			mNumberProduct.setOnNumberChangedListener(this);
			mNumberArrival.setOnNumberChangedListener(this);
		}
	}
    
    @Override
    public void onNumberChanged(ModuleSetNumber setnumber, int number) {
    	// TODO Auto-generated method stub
    	if(setnumber == mNumberPeople){
    		mOrder.PeopleNumber = number;
    	}else if(setnumber == mNumberProduct){
    		mOrder.Count = number;
    		if(mListener != null){
    			mListener.onOrderCountChanged(this, number);
    		}
    	}else if(setnumber == mNumberArrival){
			showArrivalTime(number);
    	}
    }
    
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    	// TODO Auto-generated method stub
//    	int people = 1;
//    	String speople = mTvArrival.getText().toString();
//    	try {
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
    }
    
    @Override
    public void afterTextChanged(Editable s) {
    	// TODO Auto-generated method stub
    	
    }
    
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
    		int after) {
    	// TODO Auto-generated method stub
    	
    }

    public void hide()
    {
        // TODO Auto-generated constructor stub
    	if(mIsValid)
        mLayout.setVisibility(View.GONE);
    }
    
    public void show()
    {
        // TODO Auto-generated constructor stub
    	if(mIsValid)
        mLayout.setVisibility(View.VISIBLE);
    }

	@Override
	public View getLayout() {
		// TODO Auto-generated method stub
		return mLayout;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return mIsValid;
	}

	public void setData(RsrOrder order, StoreBase store) {
		// TODO Auto-generated method stub
		if(mIsValid && order != null && store != null){
			setOrder(order);
			Calendar calendar = Calendar.getInstance();
			int limit = store.getLimit();
			int curhour = calendar.get(Calendar.HOUR_OF_DAY);
			int curminute = calendar.get(Calendar.MINUTE);
			switch (store.getLimitType()) {
			case StoreBase.LIMIT_RANGE:{
				float arrival = STEP_HOURCONUT*(limit-curhour-1f);
				arrival += (60f-curminute)/STEP_MINUTE;
				if(arrival < 0){
					arrival = STEP_HOURCONUT*(24+limit-curhour-1);
					arrival += (60f-curminute)/STEP_MINUTE;
				}
				if(arrival > 0){
					mNumberArrival.setLimit(0,(int)arrival);
					mNumberArrival.setNumber((int)arrival);
				}else{
					mNumberArrival.setLimit((int)arrival,(int)arrival);
					mNumberArrival.setNumber((int)arrival);
				}
				showArrivalTime((int)arrival);
				break;
			}
			case StoreBase.LIMIT_SPAN:{
				float arrival = STEP_HOURCONUT*(limit-1f);
				arrival += (60f-curminute)/STEP_MINUTE;
				mNumberArrival.setLimit(0,(int)arrival);
				mNumberArrival.setNumber((int)arrival);
				showArrivalTime((int)arrival);
				break;
			}
			}
		}
	}

	private void showArrivalTime(int time) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		int curday = calendar.get(Calendar.DAY_OF_MONTH);
		Date date = new Date(new Date().getTime()+time*20*60*1000);
		if(mNumberArrival.getMax() == time){
			int curminute = calendar.get(Calendar.MINUTE);
			time = time + 3 - (60-curminute)/STEP_MINUTE;
			calendar.set(Calendar.MINUTE, 0);
			date = new Date(calendar.getTime().getTime()+time*20*60*1000);
		}
		mOrder.ArrivalTime = date;
		calendar.setTime(mOrder.ArrivalTime);
		if(calendar.get(Calendar.DAY_OF_MONTH) == curday){
			String stime = DateFormatUtil.format("HH:mm", date);
			mTvArrival.setText(stime);
		}else{
			String stime = DateFormatUtil.format("明日 HH:mm", date);
			mTvArrival.setText(stime);
		}
	}

}
