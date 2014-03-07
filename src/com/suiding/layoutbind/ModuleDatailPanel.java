package com.suiding.layoutbind;

import android.view.View;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.layoutbind.framework.ILayout;

public class ModuleDatailPanel implements ILayout{

	private View mLayout = null;
	private View mDivider = null;
	private boolean mIsValid = false;

	private TextView mTvTitle = null;
	private TextView mTvContent = null;

	public ModuleDatailPanel(Pageable page) {
		// TODO Auto-generated constructor stub module_detail_divider
		mDivider = page.findViewById(R.id.module_detail_divider);
		mTvTitle = page.findTextViewById(R.id.module_detail_title);
		mTvContent = page.findTextViewById(R.id.module_detail_description);
		if (mTvTitle != null) {
			mIsValid = true;
			mLayout = (View) mTvTitle.getParent();
		}
	}
	
	public void setTitle(String title) {
		if(mIsValid){
			mTvTitle.setText(title);
			mTvTitle.setVisibility(View.VISIBLE);
			mDivider.setVisibility(View.VISIBLE);
		}
	}
	
	public void setContent(String content) {
		if(mIsValid){
			mTvContent.setText(content);
		}
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		if (mLayout != null) {
			mLayout.setVisibility(View.GONE);
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		if (mLayout != null) {
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
