package com.suiding.layoutbind;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.layoutbind.ModuleReserveMenuItem.SelectorItemListener;
import com.suiding.model.Product;
import com.suiding.model.RsrOrder;
import com.suiding.model.RsrOrderItem;

public class ModuleReserveMenu implements SelectorItemListener
{
    public static final int ADDID = R.id.reserve_selector_add;
    
    private View mLayout = null;
    private TextView mTvName = null;
    //private TextView mTvShowall = null;
    private TextView mBtAdd = null;

	private RsrOrder mOrder = null;
	private LinearLayout mLlContainer = null;
    
    private LayoutInflater mInflater = null;
    
    
    public ModuleReserveMenu(Activity activity,RsrOrder order)
    {
        // TODO Auto-generated constructor stub
        mBtAdd = (TextView)activity.findViewById(R.id.reserve_selector_add);
        mTvName = (TextView)activity.findViewById(R.id.reserve_selector_status);
        //mTvShowall = (TextView)activity.findViewById(R.id.reserve_selector_showall);
        mLlContainer = (LinearLayout)activity.findViewById(R.id.reserve_selector_cell_container);
        mLayout = (View) mTvName.getParent();
        mInflater = LayoutInflater.from(activity);
        mLlContainer.removeAllViews();
        setOrder(order);
    }

    public ModuleReserveMenu(View view,RsrOrder order)
    {
        // TODO Auto-generated constructor stub
        mBtAdd = (TextView)view.findViewById(R.id.reserve_selector_add);
        mTvName = (TextView)view.findViewById(R.id.reserve_selector_status);
        //mTvShowall = (TextView)view.findViewById(R.id.reserve_selector_showall);
        mLlContainer = (LinearLayout)view.findViewById(R.id.reserve_selector_cell_container);
        mLayout = (View) mTvName.getParent();
        mInflater = LayoutInflater.from(view.getContext());
        mLlContainer.removeAllViews();
        setOrder(order);
    }

    public void setOrder(RsrOrder order)
    {
    	mOrder = order;
    }
    
    public void setName(String name)
    {
        // TODO Auto-generated constructor stub
        mTvName.setText(name);
    }
    
    public void setOnSelectorAddListener(OnClickListener listener)
    {
        // TODO Auto-generated constructor stub
        mBtAdd.setOnClickListener(listener);
    }

    public void hide()
    {
        // TODO Auto-generated constructor stub
        mLayout.setVisibility(View.GONE);
    }
    
    public void show()
    {
        // TODO Auto-generated constructor stub
        mLayout.setVisibility(View.VISIBLE);
    }

    public void setProduct(Product mProduct)
    {
        // TODO Auto-generated method stub
        
    }

    public void addItem(RsrOrderItem item)
    {
        // TODO Auto-generated method stub
        View view = mInflater.inflate(R.layout.reserve_menu_item,null);
        view.setTag(new ModuleReserveMenuItem(view,this,item));
        mLlContainer.addView(view);
        mOrder.RsrMenus.add(item);
    }

    @Override
    public void onItemRemove(ModuleReserveMenuItem item)
    {
        // TODO Auto-generated method stub
        mLlContainer.removeView(item.mLayout);
        mOrder.RsrMenus.remove(item.mOrderItem);
    }

    @Override
    public void onCountChanged(ModuleReserveMenuItem item, int count) {
    	// TODO Auto-generated method stub
    	
    }
}
