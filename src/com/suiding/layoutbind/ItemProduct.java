package com.suiding.layoutbind;

import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.application.ImageService;
import com.suiding.constant.ImageFolderEnum;
import com.suiding.constant.ImageSize;
import com.suiding.constant.StoreTypeEnum;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.layoutbind.framework.ILayoutItem;
import com.suiding.model.Product;

public class ItemProduct implements ILayoutItem,ILayout {
	
	public Product mProduct;
	public TextView mTvName = null;
	public TextView mTvMoney = null;
	public TextView mTvStatus = null;
	public TextView mTvDiscard = null;
	public TextView mTvDetail = null;
	private ImageView mIvHeadimg = null;

    private View mLayout = null;
	private boolean mIsValid = false;
	
	public int mType = StoreTypeEnum.ALL;

	private boolean mIsModule = false;

	public ItemProduct() {
	}

	public ItemProduct(boolean ismodule) {
		mIsModule = ismodule;
	}

	public void setType(int type) {
		mType = type;
	}

	/**
	 * 从视图中取出控件
	 * 
	 * @param view
	 */
	@Override
	public void Handle(View view) {
		mTvName = (TextView) view.findViewById(R.id.listitem_product_name);
		mTvMoney = (TextView) view.findViewById(R.id.listitem_product_price);
		mTvStatus = (TextView) view.findViewById(R.id.listitem_product_status);
		mTvDiscard = (TextView) view.findViewById(R.id.listitem_product_discard);
		mTvDetail = (TextView) view.findViewById(R.id.listitem_product_detail);
		mIvHeadimg = (ImageView) view.findViewById(R.id.listitem_product_headimg);
		mTvDiscard.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 删除线

		mIsValid = true;
		mLayout = (View)mIvHeadimg.getParent();
	}

	/**
	 * 将数据绑定到控件显示
	 * 
	 * @param review
	 */
	@Override
	public void Binding(Object data) {
		// TODO Auto-generated method stub
		if (data instanceof Product) {
			String format = mTvName.getContext().getString(
					R.string.listitem_product_money_format);
			mProduct = (Product) data;
			mTvName.setText(mProduct.Name);

			if(mProduct.NowPrice < mProduct.Price){
				if(mIsModule){
					mTvMoney.setText(String.format("%.0f", mProduct.NowPrice));
				}else{
					mTvMoney.setText(String.format(format, mProduct.NowPrice));
				}
				mTvDiscard.setText(String.format(format, (float) mProduct.Price));
				mTvDiscard.setVisibility(View.VISIBLE);
			}else{
				if(mIsModule){
					mTvMoney.setText(String.format("%.0f", mProduct.Price));
				}else{
					mTvMoney.setText(String.format(format, mProduct.Price));
				}
				mTvDiscard.setVisibility(View.GONE);
			}
			
			mProduct.NoBookedCount = mProduct.ProductCount-mProduct.BookedCount;
			mTvStatus.setText(mProduct.NoBookedCount>0?"有":"满");
			mTvStatus.setEnabled(mProduct.NoBookedCount>0);

			ImageService.bindImage(mProduct.HeadImg, mIvHeadimg,
					ImageFolderEnum.HEAD_PRODUCT, ImageSize.SMALLEST);
			
//			switch (mType) {
//			case StoreTypeEnum.HOTEL:
//				mTvStatus.setText("有房");
//				mTvStatus.setVisibility(View.VISIBLE);
//				break;
//			case StoreTypeEnum.FOODANDBEVERAGE:
//				mTvStatus.setText("有房");
//				mTvStatus.setVisibility(View.VISIBLE);
//				break;
//			default:
//				mTvStatus.setVisibility(View.GONE);
//				break;
//			}

			if (mTvDetail != null) {
				mTvDetail.setText(mProduct.Abstrct);
			}
		}
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return mIsModule ? R.layout.listitem_product_module
				: R.layout.listitem_product;
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		if(mLayout != null){
			mLayout.setVisibility(View.GONE);
		}		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		if(mLayout != null){
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
