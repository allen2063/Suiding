package com.suiding.layoutbind;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.layoutbind.framework.ILayout;

public class ModuleNodata implements ILayout{
	
	public static final int ID_BUTTON = R.id.module_nodata_button;

	public static final int TEXT_NODATA = R.string.module_nodata_description;
	public static final int TEXT_NOFIXED = R.string.module_nodata_nofixed;
	public static final int TEXT_NOFAVORITE = R.string.module_nodata_nofavorite;
	public static final int TEXT_NOBIRTHDAY = R.string.module_nodata_nobirthday;
	public static final int TEXT_NOINVITE = R.string.module_nodata_noinvite;
	public static final int TEXT_NOREMIND = R.string.module_nodata_noremind;
	public static final int TEXT_NOORDER = R.string.module_nodata_noorder;
	public static final int TEXT_NOPROMOTION = R.string.module_nodata_promotion;

	public static final int TEXT_TOREFRESH = R.string.module_nodata_to_refresh;
	public static final int TEXT_TOADD = R.string.module_nodata_to_add;

	private View mLayout = null;
	private TextView mTvButton = null;
	private TextView mTvDescription = null;
	
	private boolean mIsValid = false;

	public ModuleNodata(Pageable page) {
		// TODO Auto-generated constructor stub
		mTvButton = page.findTextViewById(R.id.module_nodata_button);
		mTvDescription = page.findTextViewById(R.id.module_nodata_description);
		mIsValid = mTvButton != null;
		if(mIsValid){
			mLayout = (View) mTvButton.getParent();
			mTvButton.setVisibility(View.GONE);
		}
	}

	public void setDescription(String description) {
		// TODO Auto-generated constructor stub
		mTvDescription.setText(description);
	}

	public void setDescription(int id) {
		// TODO Auto-generated constructor stub
		mTvDescription.setText(id);
	}

	public void setButtonText(String text) {
		// TODO Auto-generated constructor stub
		mTvButton.setText(text);
	}

	public void setButtonText(int id) {
		// TODO Auto-generated constructor stub
		mTvButton.setId(id);
		mTvButton.setText(id);
	}

	public void setOnRefreshListener(OnClickListener listener) {
		// TODO Auto-generated constructor stub
		mTvButton.setOnClickListener(listener);
		if(listener != null){
			mTvButton.setVisibility(View.VISIBLE);
		}else{
			mTvButton.setVisibility(View.GONE);
		}
		
	}

	public View getLayout() {
		return mLayout;
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
	public boolean isValid() {
		// TODO Auto-generated method stub
		return mIsValid;
	}
}
