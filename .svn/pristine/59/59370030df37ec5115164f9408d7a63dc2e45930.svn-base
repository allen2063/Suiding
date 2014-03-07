package com.suiding.layoutbind;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.layoutbind.ModuleOrderMenuItem.OrderMenuItemListener;
import com.suiding.layoutbind.ModuleTabControl.ITabControlItem;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.PdtItem;
import com.suiding.model.PdtItemType;

public class ModuleOrderMenu implements ILayout, OrderMenuItemListener, OnCheckedChangeListener
{
    private View mLayout = null;
    private TextView mTvTitle = null;
    private CheckBox mCbCheck = null;

	private boolean mIsValid = false;
	private LinearLayout mLlContainer = null;
	private List<PdtItem> mltSelected = new ArrayList<PdtItem>();
	private List<PdtItem> mltPdtItemTotal = new ArrayList<PdtItem>();
	private List<PdtItem> mltPdtItemShow = new ArrayList<PdtItem>();
	private List<ITabControlItem> mltPdtItemType = new ArrayList<ITabControlItem>();
	private List<ModuleOrderMenuItem> mltMenuItem = new ArrayList<ModuleOrderMenuItem>();

	private LayoutInflater mInflater = null;
	private ModuleTabControl mTabControl = null;

    private OrderMenuItemListener mListener = null;
	
    public ModuleOrderMenu(Pageable page)
    {
        // TODO Auto-generated constructor stub
    	mTabControl = new ModuleTabControl(page);
    	mInflater = LayoutInflater.from(page.getActivity());
        mTvTitle = page.findTextViewById(R.id.module_ordermenu_title);
        mCbCheck = page.findCheckBoxById(R.id.module_ordermenu_expand);
        mLlContainer = page.findLinearLayoutById(R.id.module_ordermenu_container);
        initialize();
    }

    private void initialize() {
		// TODO Auto-generated method stub
		if(mTvTitle != null){
			mLayout = (View) mTvTitle.getParent();
			mIsValid = mLayout != null && mTabControl.isValid();
			if(mIsValid){
				mTabControl.hide();
		        mLlContainer.removeAllViews();
		        mCbCheck.setVisibility(View.GONE);
		        mLlContainer.setVisibility(View.GONE);
			}
		}
	}

    public void setOrderMenuItemListener(OrderMenuItemListener mListener) {
		this.mListener = mListener;
	}
    
    public void setName(String name)
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
    
    @Override
    public void onCheckedChanged(CompoundButton view, boolean isChecked) {
    	// TODO Auto-generated method stub
    	if(isChecked){
    		for (int i = 5; i < mltPdtItemShow.size(); i++) {
    			PdtItem item = mltPdtItemShow.get(i);
    			addItem(item,mltSelected.indexOf(item)!=-1);
			}
    	}else{
    		while (mltMenuItem.size() > 5) {
    			ModuleOrderMenuItem item = mltMenuItem.get(5);
    			removeContainerView(item.getLayout());
    			mltMenuItem.remove(5);
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
		}
	}
    private void setPdtItems(List<PdtItem> ltitem,List<PdtItem> ltselected)
    {
    	//还原下拉按钮Listener(null)防止setChecked(false) 通知
		mCbCheck.setOnCheckedChangeListener(null);
		mCbCheck.setChecked(false);
    	
    	mltMenuItem.clear();
    	mLlContainer.removeAllViews();
    	mLlContainer.setVisibility(View.VISIBLE);
    	for (PdtItem item : ltitem) {
    		if(mltMenuItem.size() < 5){
    			addItem(item,ltselected.indexOf(item)!=-1);
    		}
		}
    	if(ltitem.size() > 5){
    		mCbCheck.setVisibility(View.VISIBLE);
    		mCbCheck.setOnCheckedChangeListener(this);
    	}else{
    		mCbCheck.setVisibility(View.GONE);
    	}
    }

    public void setOrderMenu(List<PdtItemType> lttype,List<PdtItem> ltitem)
    {
    	if(ltitem != null && ltitem.size() > 0){
        	mltPdtItemTotal = ltitem;
        	mltSelected.clear();
        	mltPdtItemType.clear();
        	if(lttype != null && lttype.size() > 0){
        		for (PdtItemType type : lttype) {
        			mltPdtItemType.add(new TabItem(type));
        		}
        		mTabControl.show();
        		mTabControl.setItems(mltPdtItemType);
        		mltPdtItemType.get(0).onCheckedChanged(true);
        	}else{
        		mTabControl.hide();
        		mltPdtItemShow = new ArrayList<PdtItem>(mltPdtItemTotal);
        		setPdtItems(mltPdtItemShow, mltSelected);
        	}
    	}else{
    		this.hide();
    	}
    }
    
    private void addItem(PdtItem item,boolean checked)
    {
        // TODO Auto-generated method stub
		ModuleOrderMenuItem RsrmenuItem = new ModuleOrderMenuItem();
        View view = mInflater.inflate(RsrmenuItem.getLayoutId(),null);
        View divider = mInflater.inflate(R.layout.module_divider_list, null);
        if(mLlContainer.getChildCount() > 0){
            mLlContainer.addView(divider);
        }
        mLlContainer.addView(view);
        RsrmenuItem.Handle(view);
        RsrmenuItem.setPdtItem(item,checked);
        RsrmenuItem.setOrderMenuItemListener(this);
        mltMenuItem.add(RsrmenuItem);
    }
    
    @Override
    public void onPdtItemCheckedChanged(PdtItem item, boolean isChecked) {
    	// TODO Auto-generated method stub
    	if(isChecked){
    		mltSelected.add(item);
    	}else{
    		mltSelected.remove(item);
    	}
    	if(mListener != null){
    		mListener.onPdtItemCheckedChanged(item, isChecked);
    	}
    }

	public void removeSelected(UUID uuid) {
		// TODO Auto-generated method stub
		for (PdtItem item : mltPdtItemTotal) {
			if(uuid.equals(item.getID())){
				if(mltSelected.indexOf(item) >= 0){
					mltSelected.remove(item);
				}
				break;
			}
		}
		for (ModuleOrderMenuItem mItem : mltMenuItem) {
			if(uuid.equals(mItem.getID())){
				mItem.setChecked(false);
				break;
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

	protected class TabItem extends PdtItemType implements ITabControlItem {

		public TabItem(PdtItemType item) {
			super(item);
		}

		@Override
		public void onCheckedChanged(boolean isChecked) {
			// TODO Auto-generated method stub
			if (isChecked) {
				mltPdtItemShow = new ArrayList<PdtItem>();
				for (PdtItem item : mltPdtItemTotal) {
					if (item.Type_ID.equals(this.getID())) {
						mltPdtItemShow.add(item);
					}
				}
				setPdtItems(mltPdtItemShow, mltSelected);
			}
		}

	}
}
