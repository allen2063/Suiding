package com.suiding.layoutbind;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.layoutbind.ModuleTabControl.ITabControlItem;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.PdtItem;
import com.suiding.model.Product;

public class ModuleRsrmenu implements ILayout, OnCheckedChangeListener
{
    public static final int ADDID = R.id.reserve_selector_add;
    
    private View mLayout = null;
    private TextView mTvTitle = null;
    private CheckBox mCbCheck = null;
    private TextView mTvShowall = null;

	private boolean mIsValid = false;
	private LinearLayout mLlContainer = null;
	private List<PdtItem> mltPdtItem = new ArrayList<PdtItem>();
	private List<ITabControlItem> mlPdtItemType = new ArrayList<ITabControlItem>();

	private List<ModuleRsrmenuItem> mltMenuItem = new ArrayList<ModuleRsrmenuItem>();


	private ModuleTabControl mTabControl = null;
    
    private LayoutInflater mInflater = null;
    
    
    public ModuleRsrmenu(Pageable page)
    {
        // TODO Auto-generated constructor stub
    	mInflater = LayoutInflater.from(page.getActivity());
    	mTabControl = new ModuleTabControl(page);
        mTvTitle = page.findTextViewById(R.id.module_rsrmenu_title);
        mCbCheck = page.findCheckBoxById(R.id.module_rsrmenu_expand);
        mTvShowall = page.findTextViewById(R.id.module_rsrmenu_more);
        mLlContainer = page.findLinearLayoutById(R.id.module_rsrmenu_container);
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

    public void setProduct(Product mProduct)
    {
        // TODO Auto-generated method stub
        
    }
    
    public void setLoading(){
    	mltPdtItem.clear();
    	mLlContainer.removeAllViews();
    	mLlContainer.setVisibility(View.GONE);
    }
    
    public void setLoadedFail(){
    	if(mLlContainer.getVisibility() != View.VISIBLE){
    		mTvShowall.setText("加载失败");
    		mLlContainer.addView(mTvShowall);
        	mLlContainer.setVisibility(View.VISIBLE);
    	}
    }
    
    @Override
    public void onCheckedChanged(CompoundButton view, boolean isChecked) {
    	// TODO Auto-generated method stub
    	if(isChecked){
    		for (int i = 5; i < mltPdtItem.size(); i++) {
    			PdtItem item = mltPdtItem.get(i);
    			//addItem(item,mltSelected.indexOf(item)!=-1);
    			addItem(item);
			}
    	}else{
    		while (mltMenuItem.size() > 5) {
    			ModuleRsrmenuItem item = mltMenuItem.get(5);
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
    public void setPdtItems(List<PdtItem> ltitem)
    {
    	//还原下拉按钮Listener(null)防止setChecked(false) 通知
		mCbCheck.setOnCheckedChangeListener(null);
		mCbCheck.setChecked(false);
    	
    	mltPdtItem.clear();
    	mltPdtItem.addAll(ltitem);
    	mltMenuItem.clear();
    	mLlContainer.removeAllViews();
    	mLlContainer.setVisibility(View.VISIBLE);
    	for (PdtItem item : ltitem) {
    		if(mltMenuItem.size() < 5){
    			addItem(item);
    		}
		}
    	if(ltitem.size() > 5){
    		mCbCheck.setVisibility(View.VISIBLE);
    		mCbCheck.setOnCheckedChangeListener(this);
    	}else{
    		mCbCheck.setVisibility(View.GONE);
    	}
    	if(ltitem.size() == 0){
    		mTvShowall.setText("暂无数据");
    		mLlContainer.addView(mTvShowall);
    	}
    }

    public void setPdtItemTypes(List<?extends ITabControlItem> ltitem)
    {
    	mlPdtItemType.clear();
    	mlPdtItemType.addAll(ltitem);
    	if(ltitem != null && ltitem.size() > 0){
    		mTabControl.show();
    		mTabControl.setItems(mlPdtItemType);
    	}
    }
    
    public void addItem(PdtItem item)
    {
        // TODO Auto-generated method stub
		ModuleRsrmenuItem RsrmenuItem = new ModuleRsrmenuItem();
        View view = mInflater.inflate(RsrmenuItem.getLayoutId(),null);
        View divider = mInflater.inflate(R.layout.module_divider_list, null);
        if(mLlContainer.getChildCount() > 0){
            mLlContainer.addView(divider);
        }
        mLlContainer.addView(view);
        RsrmenuItem.Handle(view);
        RsrmenuItem.Binding(item);
        mltMenuItem.add(RsrmenuItem);
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
