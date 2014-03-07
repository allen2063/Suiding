package com.suiding.layoutbind;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.application.AppExceptionHandler;
import com.suiding.layoutbind.ModuleReserveMenuItem.SelectorItemListener;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.PdtItem;
import com.suiding.model.RsrMenu;
import com.suiding.model.RsrOrder;
import com.suiding.model.RsrOrderItem;

public class ModuleOrderSelected implements ILayout, SelectorItemListener
{
    private View mLayout = null;
    private TextView mTvTitle = null;
    private TextView mTvPrice = null;
    private TextView mTvNumber = null;
    private LinearLayout mLlContainer = null;

	private boolean mIsValid = false;
	private LayoutInflater mInflater = null;
	private List<ModuleReserveMenuItem> mltMenuItem = null;
	
	private RsrOrder mOrder = null;
	
	private SelectorItemListener mListener = null;
    
    public ModuleOrderSelected(Pageable page)
    {
        // TODO Auto-generated constructor stub
    	mInflater = LayoutInflater.from(page.getActivity());
        mTvTitle = page.findTextViewById(R.id.ordermodule_selected_title);
        mTvPrice = page.findTextViewById(R.id.ordermodule_selected_price);
        mTvNumber = page.findTextViewById(R.id.ordermodule_selected_count);
        mLlContainer = page.findLinearLayoutById(R.id.ordermodule_selected_container);
        initialize();
    }

    private void initialize() {
		// TODO Auto-generated method stub
		if(mTvTitle != null){
			mLayout = (View) mTvTitle.getParent();
			mIsValid = mLayout != null;
			if(mIsValid){
				this.hide();
		        mLlContainer.removeAllViews();
		        mltMenuItem = new ArrayList<ModuleReserveMenuItem>();
			}
		}
	}
    
    private void doCountPrice(){
    	try {
        	int count = 0;
        	double price = 0;
        	for (ModuleReserveMenuItem item : mltMenuItem) {
        		RsrOrderItem oitem = item.mOrderItem;
        		count += oitem.Count;
        		price += oitem.RsrMenu.NowPrice*oitem.Count;
    		}
        	mTvPrice.setText((int)price+"元");
        	mTvNumber.setText(count+"");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();//handled
			AppExceptionHandler.handler(e, "下单点菜控件，统计金额 出现异常");
		}
    }

	public void addItem(PdtItem item) {
		// TODO Auto-generated method stub
		if(mIsValid && mOrder!= null){
			RsrOrderItem rsritem = new RsrOrderItem();
			rsritem.Count = 1;
			rsritem.setOde_ID(mOrder.getID());
			rsritem.setRsrMenu(new RsrMenu(item));
			this.addItem(rsritem);
			mOrder.RsrMenus.add(rsritem);
		}
	}

	public void removeItem(PdtItem item) {
		// TODO Auto-generated method stub
		if(mIsValid && mOrder!= null){
			RsrOrderItem remove = null;
			for (RsrOrderItem ritem : mOrder.RsrMenus) {
				if(item.getID().equals(ritem.getPi_ID())){
					remove = ritem;
					break;
				}
			}
			if(remove != null){
				mOrder.RsrMenus.remove(remove);
			}
			ModuleReserveMenuItem reitem = null;
			for (ModuleReserveMenuItem mitem : mltMenuItem) {
				if(mitem.mOrderItem == remove){
					reitem = mitem;
					break;
				}
			}
			if(reitem != null){
				mltMenuItem.remove(reitem);
		        removeContainerView(reitem.mLayout);
			}
		}
	}
	
	private void removeContainerView(View v){
		if(v != null){
			int index = mLlContainer.indexOfChild(v);
			if(index == 0){
				if(mLlContainer.getChildCount() > 2){
					mLlContainer.removeViewAt(1);
				}
				mLlContainer.removeViewAt(0);
			}else{
				if(index > 1){
					mLlContainer.removeViewAt(index-1);
				}
				mLlContainer.removeViewAt(index-1);
			}
			this.doCountPrice();
		}
	}

	public void setOrder(RsrOrder order) {
		// TODO Auto-generated method stub
		mOrder = order;
	}
	
    public void setmSelectorItemListener(SelectorItemListener mListener) {
		this.mListener = mListener;
	}

    public void setTitle(String name)
    {
        // TODO Auto-generated constructor stub
    	if(mIsValid)
        mTvTitle.setText(name);
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

    private void addItem(RsrOrderItem item)
    {
        // TODO Auto-generated method stub
    	ModuleReserveMenuItem RsrmenuItem = new ModuleReserveMenuItem();
        View view = mInflater.inflate(RsrmenuItem.getLayoutId(),null);
        View divider = mInflater.inflate(R.layout.module_divider_list, null);
        if(mLlContainer.getChildCount() > 0){
            mLlContainer.addView(divider);
        }
        mLlContainer.addView(view);
        RsrmenuItem.Handle(view);
        RsrmenuItem.Binding(item);
        RsrmenuItem.setmSelectorItemListener(this);
        mltMenuItem.add(RsrmenuItem);
		this.doCountPrice();
		this.show();
    }
    
    @Override
    public void onItemRemove(ModuleReserveMenuItem item) {
    	// TODO Auto-generated method stub
    	mltMenuItem.remove(item);
        removeContainerView(item.mLayout);
		if(item != null){
			mOrder.RsrMenus.remove(item.mOrderItem);
		}
        if(mListener != null){
        	mListener.onItemRemove(item);
        }
    }
    
    @Override
    public void onCountChanged(ModuleReserveMenuItem item, int count) {
    	// TODO Auto-generated method stub
        if(mListener != null){
        	mListener.onCountChanged(item,count);
        }
		this.doCountPrice();
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
