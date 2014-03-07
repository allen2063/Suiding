package com.suiding.layoutbind;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.Product;


public class ModuleProduct implements ILayout,OnClickListener, OnCheckedChangeListener
{
	public static final int MAX_COUNT = 5;
	
	private TextView mTvStatus = null;
	private TextView mTvMore = null;
    private CheckBox mCbCheck = null;
	private ProgressBar mProgressBar = null;
    private LinearLayout mLayoutProduct = null;
    
    private LayoutInflater mInflater;
    
    private List<Product> mltProduct;
    private List<ItemProduct> mltProductItem;
    
    private View mLayout = null;
    private boolean mIsValid = false;
    
    private OnProductClickListener mListener = null;
    
    public interface OnProductClickListener
    {
    	boolean onRefreshProductListener();
        void onProductClickListener(Product product);
        void onMoreProductListener(List<Product> ltproduct);
    }
    
    public ModuleProduct(Pageable page)
    {
        // TODO Auto-generated constructor stub
    	mLayout = page.findViewById(R.id.module_product_layout);
        mTvMore = page.findTextViewById(R.id.module_product_more);
        mCbCheck = page.findCheckBoxById(R.id.module_product_expand);
        mTvStatus = page.findTextViewById(R.id.module_product_status);
        mProgressBar = page.findProgressBarById(R.id.module_product_progress);
        mLayoutProduct = page.findLinearLayoutById(R.id.module_product_cell_container);
        mInflater = LayoutInflater.from(page.getContext());
        if(mLayout != null){
        	mIsValid = true;
        	mTvMore.setVisibility(View.GONE);
	        mCbCheck.setVisibility(View.GONE);
        }
    }

    public void setOnProductClickListener(OnProductClickListener listener)
    {
        this.mListener = listener;
    }

    public void setProduct(List<Product> ltProduct)
    {
        // TODO Auto-generated method stub
    	//还原下拉按钮Listener(null)防止setChecked(false) 通知
		mCbCheck.setOnCheckedChangeListener(null);
		mCbCheck.setChecked(false);
		
    	mltProduct = ltProduct;
    	mLayoutProduct.removeAllViews();
    	mltProductItem = new ArrayList<ItemProduct>();
    	if(ltProduct.size() == 0){
    		mLayoutProduct.setVisibility(View.GONE);
    	}else{
            for (Product product : mltProduct)
            {
            	if(mltProductItem.size() < 5){
            		addItemProduct(product);
            	}
            }
        	if(mltProduct.size() > 5){
        		mCbCheck.setVisibility(View.VISIBLE);
        		mCbCheck.setOnCheckedChangeListener(this);
        	}else{
        		mCbCheck.setVisibility(View.GONE);
        	}
    		mLayoutProduct.setVisibility(View.VISIBLE);
    	}
//        if(ltProduct.size() >= MAX_COUNT)
//        {
//        	mLayoutProduct.addView(mTvMore);
//        	mTvMore.setOnClickListener(this);
//        	mTvMore.setVisibility(View.VISIBLE);
//        }
        mProgressBar.setVisibility(View.GONE);
        mTvStatus.setOnClickListener(this);
        mTvStatus.setText(R.string.module_product_nogoods);
    }

    @Override
    public void onCheckedChanged(CompoundButton view, boolean isChecked) {
    	// TODO Auto-generated method stub
    	if(isChecked){
    		for (int i = 5; i < mltProduct.size(); i++) {
    			Product item = mltProduct.get(i);
    			//addItem(item,mltSelected.indexOf(item)!=-1);
    			addItemProduct(item);
			}
    	}else{
    		while (mltProductItem.size() > 5) {
    			ItemProduct item = mltProductItem.get(5);
    			mLayoutProduct.removeView(item.getLayout());
    			mltProductItem.remove(5);
			}
    	}
    }
    
    private void addItemProduct(Product product) {
		// TODO Auto-generated method stub
    	ItemProduct item = new ItemProduct(true);
        View listitem = mInflater.inflate(item.getLayoutId(), null);
        //View divider = mInflater.inflate(R.layout.module_divider_list, null);
        item.Handle(listitem);
        item.Binding(product);
        listitem.setTag(item);
        listitem.setOnClickListener(this);
        mLayoutProduct.addView(listitem);
        //mLayoutProduct.addView(divider);
        mltProductItem.add(item);
	}

	public void setLoadFail()
    {
    	mLayoutProduct.removeAllViews();
        mTvStatus.setOnClickListener(this);
        mProgressBar.setVisibility(View.GONE);
        mTvStatus.setText(R.string.module_product_error);
    }
    
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        if(mListener != null)
        {
        	if(v.getId() == R.id.module_product_status)
        	{
        		if(mListener.onRefreshProductListener()){
                    mTvStatus.setOnClickListener(null);
                    mTvStatus.setText(R.string.module_product_refreshing);
                    mProgressBar.setVisibility(View.VISIBLE);
        		}
        	}
        	else if(v.getId() == R.id.module_product_more)
        	{
        		mListener.onMoreProductListener(mltProduct);
        	}
        	else if(v.getTag() instanceof ItemProduct)
            {
                ItemProduct ItemProduct = (ItemProduct)v.getTag();
                mListener.onProductClickListener(ItemProduct.mProduct);
            } 
        }
    }

	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
		if(mIsValid && product != null && mltProductItem != null){
			for (ItemProduct item : mltProductItem) {
				if(item.mProduct != null && product.getID().equals(item.mProduct.getID())){
					item.Binding(product);
					break;
				}
			}
		}
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
