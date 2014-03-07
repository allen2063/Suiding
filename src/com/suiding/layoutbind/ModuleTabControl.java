package com.suiding.layoutbind;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.layoutbind.framework.ILayout;


public class ModuleTabControl implements ILayout, OnCheckedChangeListener
{
    public interface ITabControlItem
    {
		String getName();
		void onCheckedChanged(boolean isChecked);
    }
    
    private View mLayout = null;
    private boolean mIsValid = false;
    
    private RadioGroup mRadioGroup = null;
    private HorizontalScrollView mScrollView = null;
    
    private Resources mResources = null;
    private RadioButton mLastButton = null;
    private LayoutInflater mInfalter = null;
    private List<ITabControlItem> mltItem = new ArrayList<ITabControlItem>();
    
	public ModuleTabControl(Pageable page)
    {
        // TODO Auto-generated constructor stub
    	mResources = page.getContext().getResources();
    	mInfalter = LayoutInflater.from(page.getContext());
    	mRadioGroup = page.findRadioGroupById(R.id.tabcontrol_radiogroup);
    	mScrollView = page.findHorizontalScrollViewById(R.id.tabcontrol_hscrollview);
    	initialize();
    }
	
	public void setItems(List<ITabControlItem> ltitem)
	{
		if(mIsValid){
			mltItem.clear();
			mltItem.addAll(ltitem);
			mRadioGroup.removeAllViews();
			mLastButton = null;
			int id = R.layout.module_tabcontrolitem;
			for (ITabControlItem item : ltitem) {
				RadioButton button = (RadioButton)mInfalter.inflate(id, null);
				button.setText(item.getName());
				button.setTag(item);
				button.setOnCheckedChangeListener(this);
				mRadioGroup.addView(button);
				if(mLastButton == null){
					button.setChecked(true);
					mLastButton = button;
				}
			}
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton button, boolean isChecked) {
		// TODO Auto-generated method stub
		if(isChecked){
			if(mLastButton != null){
				mLastButton.setBackgroundColor(0);
				ITabControlItem item = (ITabControlItem)mLastButton.getTag();
				item.onCheckedChanged(false);
			}
			mLastButton = (RadioButton)button;
			mLastButton.setBackgroundColor(mResources.getColor(R.color.theme_gray_dark));

			mScrollView.smoothScrollTo(
					mLastButton.getLeft() + mLastButton.getWidth() / 2
	                        - mScrollView.getWidth() / 2, 0);
			
			ITabControlItem item = (ITabControlItem)mLastButton.getTag();
			item.onCheckedChanged(isChecked);
		}
	}

	private void initialize() {
		// TODO Auto-generated method stub
    	if(mScrollView != null){
    		mLayout = (View)mScrollView.getParent();
    		mIsValid = mLayout != null;
    		if(mIsValid){
    			mRadioGroup.removeAllViews();
    		}
    	}
	}

    public View getLayout()
    {
        return mLayout;
    }

    public void hide()
    {
        // TODO Auto-generated constructor stub
        if (mLayout.getVisibility() == View.VISIBLE)
        {
            mLayout.setVisibility(View.GONE);
        }
    }

    public void show()
    {
        // TODO Auto-generated constructor stub
        if (mLayout.getVisibility() != View.VISIBLE)
        {
            mLayout.setVisibility(View.VISIBLE);
        }
    }

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return mIsValid;
	}

}
