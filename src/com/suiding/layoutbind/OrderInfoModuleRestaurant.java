
package com.suiding.layoutbind;

import java.util.Locale;

import android.view.View;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.application.AppExceptionHandler;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.Product;
import com.suiding.model.RsrOrder;
import com.suiding.model.RsrOrderItem;
import com.suiding.util.DateFormatUtil;

public class OrderInfoModuleRestaurant implements ILayout {
	
	private View mLayout = null;
	private TextView mTvTitle = null;
	private TextView mTvPeople = null;
	private TextView mTvProduct = null;
	private TextView mTvMenu = null;
	private TextView mTvArrival = null;

	private boolean mIsValid = false;

	public OrderInfoModuleRestaurant(Pageable page) {
		// TODO Auto-generated constructor stub
		mTvTitle = page.findTextViewById(R.id.orderinfo_resturant_title);
		mTvPeople = page.findTextViewById(R.id.orderinfo_resturant_peoples);
		mTvProduct = page.findTextViewById(R.id.orderinfo_resturant_product);
		mTvMenu = page.findTextViewById(R.id.orderinfo_resturant_menu);
		mTvArrival = page.findTextViewById(R.id.orderinfo_resturant_arrival);
		mIsValid = mTvTitle != null;
		if (mIsValid) {
			mLayout = (View) mTvTitle.getParent();

		}
	}

	public void hide() {
		// TODO Auto-generated constructor stub
		if (mIsValid) {
			mLayout.setVisibility(View.GONE);
		}
	}

	public void show() {
		// TODO Auto-generated constructor stub
		if (mIsValid) {
			mLayout.setVisibility(View.VISIBLE);
		}
	}


	public void setOrder(RsrOrder order) {
		// TODO Auto-generated method stub
		if (mIsValid && order != null) {
			mTvPeople.setText(order.PeopleNumber + " 人到店");
			mTvArrival.setText(DateFormatUtil.format("HH 时 mm 分", order.ArrivalTime));
			Product product = order.getProductBase();
			if(product != null){
				mTvProduct.setText(product.Name);
			}
			if(order.RsrMenus!=null&&order.RsrMenus.size()>0){
				try {
					int count = 0;
					double price = 0;
					for (RsrOrderItem oritem : order.RsrMenus) {
						count++;
						price +=oritem.RsrMenu.NowPrice*oritem.Count;
					}
					String format = "选择 %d 中产品，合计 %.0f 元";
					String menu = String.format(Locale.CHINESE,format,count,price);
					mTvMenu.setText(menu);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();//handled
					AppExceptionHandler.handler(e, "订单详细控件，统计金额 出现异常");
				}
			}
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
