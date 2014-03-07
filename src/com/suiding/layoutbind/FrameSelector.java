package com.suiding.layoutbind;

import android.view.View;
import android.widget.FrameLayout;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.layoutbind.framework.ILayout;

public class FrameSelector implements ILayout {
	private boolean mIsValid = false;
	private FrameLayout mFrameLayout = null;

	public FrameSelector(Pageable page) {
		// TODO Auto-generated constructor stub
		this(page, R.id.module_listview_frame);
	}

	public FrameSelector(Pageable page, int id) {
		// TODO Auto-generated constructor stub
		mFrameLayout = page.findFrameLayoutById(id);
		mIsValid = mFrameLayout != null;
	}

	public FrameSelector(FrameLayout frameLayout) {
		// TODO Auto-generated constructor stub
		mFrameLayout = frameLayout;
		mIsValid = mFrameLayout != null;
	}

	/**
	 * 在 FrameLayout 中选择 view 的布局 隐藏其他的布局
	 * 
	 * @param id
	 * @return
	 */
	public boolean SelectFrame(View view) {
		if (mIsValid) {
			int count = mFrameLayout.getChildCount();
			for (int i = 0; i < count; i++) {
				View tView = mFrameLayout.getChildAt(i);
				tView.setVisibility(tView == view ? View.VISIBLE : View.GONE);
			}
			return count > 0;
		}
		return false;
	}

	/**
	 * 在 FrameLayout 中选择 id 的布局 隐藏其他的布局
	 * 
	 * @param id
	 * @return
	 */
	public boolean SelectFrame(int id) {
		if (mIsValid) {
			View view = mFrameLayout.findViewById(id);
			if (view != null) {
				return SelectFrame(view);
			}
		}
		return false;
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		if (mIsValid) {
			mFrameLayout.setVisibility(View.GONE);
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		if (mIsValid) {
			mFrameLayout.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public View getLayout() {
		// TODO Auto-generated method stub
		return mFrameLayout;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return mIsValid;
	}
}
