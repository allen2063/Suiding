package com.suiding.layoutbind;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.Order;
import com.suiding.model.Product;
import com.suiding.util.DateFormatUtil;

public class ModuleReserveTime implements ILayout, OnClickListener,
		OnDateSetListener {
	private View mLayout = null;
	private TextView mTvArrival = null;

	private boolean mIsValid = false;

	private Order mOrder = null;

	public ModuleReserveTime(Activity activity) {
		// TODO Auto-generated constructor stub
		mLayout = activity.findViewById(R.id.reserve_time_contianer);
		mTvArrival = (TextView) activity
				.findViewById(R.id.reserve_time_arrival);
		if (mLayout != null) {
			mIsValid = true;
			mLayout = (View) mLayout.getParent();
			mTvArrival.setText("");
		}
	}

	public ModuleReserveTime(View view) {
		// TODO Auto-generated constructor stub
		mLayout = view.findViewById(R.id.reserve_time_contianer);
		mTvArrival = (TextView) view.findViewById(R.id.reserve_price_total);
		if (mLayout != null) {
			mIsValid = true;
			mLayout = (View) mLayout.getParent();
			mTvArrival.setText("");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Calendar canlender = Calendar.getInstance();
		canlender.setTime(mOrder.ArrivalTime);
		int year = canlender.get(Calendar.YEAR);
		int month = canlender.get(Calendar.MONTH);
		int day = canlender.get(Calendar.DAY_OF_MONTH);
		Context ontext = v.getContext();
		Dialog tDialog = new DatePickerDialog(ontext, this, year, month, day);
		tDialog.show();
		tDialog.setCancelable(true);
	}

	/**
	 * 生日时间选择事件
	 */
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// TODO Auto-generated method stub
		Calendar calender = new GregorianCalendar(year, month, day);
		mOrder.ArrivalTime = calender.getTime();
	}

	public void setTotal(double value) {
		// TODO Auto-generated constructor stub
		mTvArrival.setText(String.format("%.0f￥", value));
	}

	public void setOrder(Order order) {
		// TODO Auto-generated constructor stub
		if (mIsValid && order != null) {
			mOrder = order;
			mTvArrival.setOnClickListener(this);
			mTvArrival.setText(DateFormatUtil.DATE.format(order.ArrivalTime));
		}
	}

	public void hide() {
		// TODO Auto-generated constructor stub
		mLayout.setVisibility(View.GONE);
	}

	public void show() {
		// TODO Auto-generated constructor stub
		mLayout.setVisibility(View.VISIBLE);
	}

	public void setProduct(Product mProduct) {
		// TODO Auto-generated method stub

	}

	@Override
	public View getLayout() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}
}
