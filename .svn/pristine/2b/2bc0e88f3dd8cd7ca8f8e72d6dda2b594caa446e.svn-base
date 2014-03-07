package com.suiding.layoutbind;

import android.app.Activity;
import android.view.View;

import com.suiding.activity.R;
import com.suiding.layoutbind.ModuleSetNumber.OnNumberChangedListener;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.Order;
import com.suiding.model.RsrOrder;

public class ModuleReserveNumber implements ILayout, OnNumberChangedListener{
	
	private View mLayout = null;
	private View mContaint = null;
	private Boolean mIsVaild = false;

	private Order mOrder = null;
	
	private ModuleSetNumber mSerPeople = null;
	private ModuleSetNumber mSerProduct = null;

	public ModuleReserveNumber(Activity activity,RsrOrder order) {
		// TODO Auto-generated constructor stub
		mContaint = activity.findViewById(R.id.reserve_number_contianter);
		setOrder(order);
		initialize();
	}

	public ModuleReserveNumber(View view,RsrOrder order) {
		// TODO Auto-generated constructor stub
		mContaint = view.findViewById(R.id.reserve_number_contianter);
		setOrder(order);
		initialize();
	}
	
	private void initialize() {
		// TODO Auto-generated method stub
		mLayout = (View) mContaint.getParent();
		mIsVaild = mLayout != null;
		if(mIsVaild){
			mSerPeople = new ModuleSetNumber(mLayout,R.id.reserve_number_people);
			mSerProduct = new ModuleSetNumber(mLayout,R.id.reserve_number_product);
			mSerPeople.setName("设置人数：");
			mSerProduct.setName("预订数量：");
			mSerPeople.setOnNumberChangedListener(this);
			mSerProduct.setOnNumberChangedListener(this);
		}
	}

	@Override
	public void onNumberChanged(ModuleSetNumber setnumber, int number) {
		// TODO Auto-generated method stub
		if(mIsVaild){
			if(setnumber == mSerPeople){
			}else if(setnumber == mSerProduct){
				mOrder.Count = number;
			}
		}
	}

	public void setOrder(Order order) {
		// TODO Auto-generated method stub
		mOrder = order;
	}
	
	public int getPeople() {
		return mSerPeople.mNumber;
	}

	public int getProduct() {
		return mSerProduct.mNumber;
	}

	public void setProduct(int number) {
		// TODO Auto-generated method stub
		mSerProduct.setNumber(number);
	}

	public void setPeople(int number) {
		// TODO Auto-generated method stub
		mSerPeople.setNumber(number);
		
	}
	public void hide() {
		// TODO Auto-generated constructor stub
		mLayout.setVisibility(View.GONE);
	}

	public void show() {
		// TODO Auto-generated constructor stub
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
		return mIsVaild;
	}


}
