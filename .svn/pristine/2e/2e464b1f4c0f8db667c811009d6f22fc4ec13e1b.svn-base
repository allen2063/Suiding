package com.suiding.layoutbind;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.util.MeasureUtil;

public class ModuleSetNumber implements ILayout,OnClickListener {

	public interface OnNumberChangedListener {
		void onNumberChanged(ModuleSetNumber setnumber, int number);
	}

	public View mLayout = null;
	public TextView mTvName = null;
	public TextView mTvNumber = null;
	public View mBtMinus = null;
	public View mBtPlus = null;

	public int min = 1;
	public int max = 100;
	public Integer mNumber = 1;
	public Integer mInterval = -1;
	
	private boolean mIsValid = false;

	public OnNumberChangedListener mListener = null;

	public ModuleSetNumber(Pageable page) {
		// TODO Auto-generated constructor stub
		mTvName = page.findTextViewById(R.id.reserve_item_name);
		mBtPlus = page.findViewById(R.id.reserve_item_bt_plus);
		mBtMinus = page.findViewById(R.id.reserve_hotel_bt_munus);
		mTvNumber = page.findTextViewById(R.id.reserve_item_et_number);
		initialize();
	}

	public ModuleSetNumber(View view) {
		// TODO Auto-generated constructor stub
		mTvName = (TextView) view.findViewById(R.id.reserve_item_name);
		mBtPlus = view.findViewById(R.id.reserve_item_bt_plus);
		mBtMinus = view.findViewById(R.id.reserve_item_bt_munus);
		mTvNumber = (TextView) view.findViewById(R.id.reserve_item_et_number);
		initialize();
	}
	
	public ModuleSetNumber(Pageable page, int id) {
		// TODO Auto-generated constructor stub
		this(page.findViewById(id));
	}

	public ModuleSetNumber(View view, int id) {
		// TODO Auto-generated constructor stub
		this(view.findViewById(id));
	}
	
	public ModuleSetNumber(View minus, TextView number, View plus) {
		// TODO Auto-generated constructor stub
		mBtPlus = plus;
		mBtMinus = minus;
		mTvNumber = number;
		mBtPlus.setOnClickListener(this);
		mBtMinus.setOnClickListener(this);
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		if(!mIsValid && mTvName != null){
			mIsValid = true;
			mLayout = (View) mTvName.getParent();
			mBtPlus.setOnClickListener(this);
			mBtMinus.setOnClickListener(this);
			mTvNumber.setText(mNumber.toString());
		}
		if (mBtMinus != null) {
			setButtonEnable(mBtMinus, mNumber != min);
		} 
		if (mBtPlus != null) {
			setButtonEnable(mBtPlus, mNumber != max);
		}
	}

	public void setOnNumberChangedListener(OnNumberChangedListener listener) {
		this.mListener = listener;
	}
	
	public void setInterval(int interval) {
		if(mIsValid && mTvNumber != null && mInterval < 0){
			mInterval = mTvNumber.getWidth();
			if(mInterval < 10){
				mInterval = MeasureUtil.measureView(mTvNumber).x;
			}
			if(mInterval > 1 && interval > mInterval){
				//int dvalue = interval - mInterval;
				//mTvNumber.setPadding(dvalue/2, 0, dvalue/2, 0);
				LayoutParams layout = mTvNumber.getLayoutParams();
				layout.width = interval;
				mTvNumber.setLayoutParams(layout);
			}else{
				mInterval = -1;
			}
		}
	}

	public void setLimit(int min, int max) {
		// TODO Auto-generated method stub
		if (/*min != max && min > 0 && */max >= min) {
			this.min = min;
			this.max = max;
			mNumber = mNumber < min ? min : mNumber;
			mNumber = mNumber > max ? max : mNumber;
			if (mTvNumber != null) {
				mTvNumber.setText(mNumber.toString());
			}
		}
		initialize();
	}

	public void setNumber(int number) {
		// TODO Auto-generated method stub
		mNumber = number;
		mNumber = mNumber < min ? min : mNumber;
		mNumber = mNumber > max ? max : mNumber;
		if (mTvNumber != null) {
			mTvNumber.setText(mNumber.toString());
		}
		initialize();
	}
	
	public int getMax() {
		return max;
	}
	
	public int getMin() {
		return min;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == mBtMinus){
			if (mNumber > min) {
				mNumber--;
				if (mTvNumber != null) {
					mTvNumber.setText(mNumber.toString());
				}
				if (mListener != null) {
					mListener.onNumberChanged(this, mNumber);
				}
				if (mNumber == min) {
					// int back = mBtMinus.getDrawingCacheBackgroundColor();
					// int gray = mBtMinus.getResources().getColor(
					// R.color.theme_gray_dark);
					// mBtMinus.setBackgroundColor(gray);
					// mBtMinus.setTag(back);
					// mBtMinus.setEnabled(false);
					setButtonEnable(mBtMinus, false);
				} else if (mNumber == max - 1 && mBtPlus != null) {
					// int back = (Integer) mBtPlus.getTag();
					// mBtPlus.setEnabled(true);
					// mBtPlus.setBackgroundColor(back);
					setButtonEnable(mBtPlus, true);
				}
			}
		}else if(v == mBtPlus){
			if (mNumber < max) {
				mNumber++;
				if (mTvNumber != null) {
					mTvNumber.setText(mNumber.toString());
				}
				if (mListener != null) {
					mListener.onNumberChanged(this, mNumber);
				}
				if (mNumber == max) {
					// int back = mBtPlus.getDrawingCacheBackgroundColor();
					// int gray =
					// mBtPlus.getResources().getColor(R.color.theme_gray_dark);
					// mBtPlus.setBackgroundColor(gray);
					// mBtPlus.setTag(back);
					// mBtPlus.setEnabled(false);
					setButtonEnable(mBtPlus, false);
				} else if (mNumber == min + 1 && mBtMinus != null) {
					// int back = (Integer)mBtMinus.getTag();
					// mBtMinus.setEnabled(true);
					// mBtMinus.setBackgroundColor(back);
					setButtonEnable(mBtMinus, true);
				}
			}
		}
		// switch (v.getId()) {
		// case R.id.reserve_item_bt_munus:
		// break;
		// case R.id.reserve_item_bt_plus:
		// break;
		// }
	}

	private void setButtonEnable(View view, boolean enable) {
		if (enable) {
			Object back = view.getTag();
			if(back instanceof Integer){
				//view.setBackgroundColor((Integer)back);
			}
			view.setEnabled(true);
		} else {
			//int back = view.getDrawingCacheBackgroundColor();
			//int gray = view.getResources().getColor(R.color.theme_gray_dark);
			//view.setBackgroundColor(gray);
			//view.setTag(back);
			view.setEnabled(false);
		}
	}

	public void setName(String name) {
		// TODO Auto-generated constructor stub
		if(mIsValid && mTvName != null){
			mTvName.setText(name);
		}
	}

	public void hide() {
		// TODO Auto-generated constructor stub
		if(mIsValid && mLayout != null){
			mLayout.setVisibility(View.GONE);
		}
	}

	public void show() {
		// TODO Auto-generated constructor stub
		if(mIsValid && mLayout != null){
			mLayout.setVisibility(View.VISIBLE);
		}
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

}
