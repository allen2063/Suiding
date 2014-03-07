package com.suiding.layoutbind;

import android.view.View;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.constant.StoreTypeEnum;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.PdtItem;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;

public class ModuleProductFacility implements ILayout {
	
	private View mLayout = null;
	
	private View mLlPeople = null;
	//private View mLlPark = null;
	private View mLlToilet = null;
	//private View mLlWifi = null;
	
	private TextView mTvPeople = null;
	private TextView mTvPark = null;
	private TextView mTvToilet = null;
	private TextView mTvWifi = null;

	// private PdtItem mPdtItem = null;
	private boolean mIsValid = false;

	public ModuleProductFacility()
	{
		
	}
	
	public ModuleProductFacility(Pageable page) {
		// TODO Auto-generated constructor stub
		//mLlWifi = page.findViewById(R.id.productfacility_layoutfwifi);
		mLlToilet = page.findViewById(R.id.productfacility_layoutftoilet);
		//mLlPark = page.findViewById(R.id.productfacility_layoutfpark);
		mLlPeople = page.findViewById(R.id.productfacility_layoutfpeople);
		mTvPeople = page.findTextViewById(R.id.productfacility_fpeople);
		mTvPark = page.findTextViewById(R.id.productfacility_fpark);
		mTvToilet = page.findTextViewById(R.id.productfacility_ftoilet);
		mTvWifi = page.findTextViewById(R.id.productfacility_fwifi);
		initialize();
	}

	public ModuleProductFacility(Pageable page,StoreBase store,Product product) {
		// TODO Auto-generated constructor stub
		this(page);
		this.setStore(store);
		this.setProduct(product);
	}

	public void setStore(StoreBase store) {
		// TODO Auto-generated method stub
		if(store != null && mIsValid){
			mTvPark.setText(store.HasPark?"有":"无");
			mTvWifi.setText(store.HasWifi?"有":"无");
			if(store.Type == StoreTypeEnum.HOTEL){
				mLlPeople.setVisibility(View.GONE);
				mLlToilet.setVisibility(View.GONE);
			}else{
				mLlPeople.setVisibility(View.VISIBLE);
				mLlToilet.setVisibility(View.VISIBLE);
			}
		}
	}

	public void setProduct(Product product) {
		// TODO Auto-generated method stub
		if(product != null && mIsValid){
			mTvPeople.setText(product.Galleryful);
			mTvToilet.setText(product.HasToilet?"有":"无");
		}
	}

	private void initialize() {
		// TODO Auto-generated method stub
		if (mTvWifi != null) {
			mLayout = (View) mTvWifi.getParent();
			mIsValid = mLayout != null;
			if (mIsValid) {
			}
		}
	}

	public void setPdtItem(PdtItem item) {
		// TODO Auto-generated method stub
		// mPdtItem = item;
		if (mIsValid) {
			mTvPeople.setText(item.Name);
			mTvToilet.setText(String.format("%.0f元", item.NowPrice));
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
