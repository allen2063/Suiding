package com.suiding.layoutbind;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.application.SuidingApp;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.thread.framework.TaskBase;

public class ModuleProgress extends TaskBase implements ILayout {

	public View mLayout = null;
	public TextView mTvDescription = null;

	private int mCount = 0;
	private boolean mIsValid = false;
	private Drawable[] mDrawables = null;

	private static final int DELAYMILLIS = 300;
	private static final Handler mHandler = new Handler(SuidingApp.getLooper());

	public ModuleProgress(Pageable page) {
		// TODO Auto-generated constructor stub
		Resources resource = page.getResources();
		mDrawables = new Drawable[] {
				resource.getDrawable(R.drawable.image_person),
				resource.getDrawable(R.drawable.image_person),
				resource.getDrawable(R.drawable.image_person), };
		mTvDescription = page.findTextViewById(R.id.module_progress_loadinfo);
		if(mTvDescription != null){
			mIsValid = true;
			//mHandler.post(this);
			mLayout = (View) mTvDescription.getParent();
		}
	}

	public void setDescription(String description) {
		// TODO Auto-generated constructor stub
		mTvDescription.setText(description);
	}

	public void setDescription(int resid) {
		// TODO Auto-generated constructor stub
		mTvDescription.setText(resid);
	}

	public View getLayout() {
		return mLayout;
	}

	public void hide() {
		// TODO Auto-generated constructor stub
		if (mIsValid && mLayout.getVisibility() == View.VISIBLE) {
			mLayout.setVisibility(View.GONE);
		}
	}

	public void show() {
		// TODO Auto-generated constructor stub
		if (mIsValid && mLayout.getVisibility() != View.VISIBLE) {
			mLayout.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onWorking(Message tMessage) throws Exception {
		// TODO Auto-generated method stub
		Drawable drawable = mDrawables[mCount];
		mTvDescription.setCompoundDrawablesWithIntrinsicBounds(null, drawable,
				null, null);
		mHandler.postDelayed(this, DELAYMILLIS);
		mCount = ++mCount % 3;
	}

	@Override
	protected void finalize() {
		// add something....................
		mHandler.removeCallbacks(this);
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return mIsValid;
	}
}
