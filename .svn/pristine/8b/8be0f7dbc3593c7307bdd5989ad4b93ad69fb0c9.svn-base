package com.suiding.activity.framework;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.suiding.application.Background;
import com.suiding.application.SuidingApp;
import com.suiding.thread.framework.TaskBase;

public abstract class ActivityBase extends Activity implements Pageable {

	// private ThreadWorker mWorker;
	private ProgressDialog mProgress;

	protected ActivityBase() {
		// TODO Auto-generated method stub
	}

	protected ActivityBase(Boolean IsUserWorker) {
		// TODO Auto-generated method stub
		// if(IsUserWorker){
		// mWorker = new ThreadWorker(this.getClass().getSimpleName());
		// }
	}
	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return getBaseContext();
	}
	
	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void setSoftInputEnable(EditText editview, boolean enable) {
		// TODO Auto-generated method stub
		editview.setFocusable(true);
		InputMethodManager imm = null;
		String Server = Context.INPUT_METHOD_SERVICE;
		imm = (InputMethodManager) getSystemService(Server);
		if (enable) {
			editview.setFocusableInTouchMode(true);
			editview.requestFocus();
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		} else {
			imm.hideSoftInputFromWindow(editview.getWindowToken(), 0);
		}	
	}

	@Override
	public void showToastLong(int resid) {
		// TODO Auto-generated method stub
		Toast.makeText(this, resid, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void showToastShort(int resid) {
		// TODO Auto-generated method stub
		Toast.makeText(this, resid, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void showToastLong(String tip) {
		// TODO Auto-generated method stub
		Toast.makeText(this, tip, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void showToastShort(String tip) {
		// TODO Auto-generated method stub
		Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public final TextView findTextViewById(int id) {
		View view = findViewById(id);
		if (view instanceof TextView) {
			return (TextView) view;
		}
		return null;
	}

	@Override
	public final ImageView findImageViewById(int id) {
		View view = findViewById(id);
		if (view instanceof ImageView) {
			return (ImageView) view;
		}
		return null;
	}

	@Override
	public final Button findButtonById(int id) {
		View view = findViewById(id);
		if (view instanceof Button) {
			return (Button) view;
		}
		return null;
	}

	@Override
	public final EditText findEditTextById(int id) {
		View view = findViewById(id);
		if (view instanceof EditText) {
			return (EditText) view;
		}
		return null;
	}

	@Override
	public final CheckBox findCheckBoxById(int id) {
		View view = findViewById(id);
		if (view instanceof CheckBox) {
			return (CheckBox) view;
		}
		return null;
	}

	@Override
	public final RadioButton findRadioButtonById(int id) {
		View view = findViewById(id);
		if (view instanceof RadioButton) {
			return (RadioButton) view;
		}
		return null;
	}

	@Override
	public final ListView findListViewById(int id) {
		View view = findViewById(id);
		if (view instanceof ListView) {
			return (ListView) view;
		}
		return null;
	}

	@Override
	public final LinearLayout findLinearLayoutById(int id) {
		View view = findViewById(id);
		if (view instanceof LinearLayout) {
			return (LinearLayout) view;
		}
		return null;
	}

	@Override
	public final FrameLayout findFrameLayoutById(int id) {
		View view = findViewById(id);
		if (view instanceof FrameLayout) {
			return (FrameLayout) view;
		}
		return null;
	}

	@Override
	public final RelativeLayout findRelativeLayoutById(int id) {
		View view = findViewById(id);
		if (view instanceof RelativeLayout) {
			return (RelativeLayout) view;
		}
		return null;
	}

	@Override
	public final ScrollView findScrollViewById(int id) {
		View view = findViewById(id);
		if (view instanceof ScrollView) {
			return (ScrollView) view;
		}
		return null;
	}

	@Override
	public ViewPager findViewPagerById(int id) {
		// TODO Auto-generated method stub
		View view = findViewById(id);
		if (view instanceof ViewPager) {
			return (ViewPager) view;
		}
		return null;
	}
	
	@Override
	public ProgressBar findProgressBarById(int id) {
		// TODO Auto-generated method stub
		View view = findViewById(id);
		if (view instanceof ProgressBar) {
			return (ProgressBar) view;
		}
		return null;
	}
	
	@Override
	public RatingBar findRatingBarById(int id) {
		// TODO Auto-generated method stub
		View view = findViewById(id);
		if (view instanceof RatingBar) {
			return (RatingBar) view;
		}
		return null;
	}
	
	@Override
	public HorizontalScrollView findHorizontalScrollViewById(int id) {
		// TODO Auto-generated method stub
		View view = findViewById(id);
		if (view instanceof HorizontalScrollView) {
			return (HorizontalScrollView) view;
		}
		return null;
	}
	
	@Override
	public RadioGroup findRadioGroupById(int id) {
		// TODO Auto-generated method stub
		View view = findViewById(id);
		if (view instanceof RadioGroup) {
			return (RadioGroup) view;
		}
		return null;
	}
	// /**
	// * 获取线程Worker
	// * @return
	// */
	// public final ThreadWorker getWorker()
	// {
	// return mWorker;
	// }

	/**
	 * 抛送任务到Worker执行
	 * 
	 * @param task
	 */
	protected final TaskBase postTask(TaskBase task) {
		// TODO Auto-generated method stub
		// if(mWorker != null){
		// mWorker.getHandler().post(task);
		// }
		return Background.postTask(task);
	}

	/**
	 * 显示 进度对话框
	 * 
	 * @param message
	 *            消息
	 */
	public final void showProgressDialog(String message) {
		// TODO Auto-generated method stub
		showProgressDialog(message, false, 25);
	}

	/**
	 * 显示 进度对话框
	 * 
	 * @param message
	 *            消息
	 * @param cancel
	 *            是否可取消
	 */
	public final void showProgressDialog(String message, boolean cancel) {
		// TODO Auto-generated method stub
		showProgressDialog(message, cancel, 25);
	}

	/**
	 * 显示 进度对话框
	 * 
	 * @param message
	 *            消息
	 * @param cancel
	 *            是否可取消
	 * @param textsize
	 *            字体大小
	 */
	public final void showProgressDialog(String message, boolean cancel,
			int textsize) {
		// TODO Auto-generated method stub
		mProgress = new ProgressDialog(this);
		mProgress.setMessage(message);
		mProgress.setCancelable(cancel);
		mProgress.setOnCancelListener(null);
		mProgress.show();

		setDialogFontSize(mProgress, textsize);
	}

	/**
	 * 显示 进度对话框
	 * 
	 * @param message
	 *            消息
	 * @param cancel
	 *            是否可取消
	 * @param textsize
	 *            字体大小
	 */
	public final void showProgressDialog(String message,
			OnCancelListener listener) {
		// TODO Auto-generated method stub
		mProgress = new ProgressDialog(this);
		mProgress.setMessage(message);
		mProgress.setCancelable(true);
		mProgress.setOnCancelListener(listener);
		mProgress.show();

		setDialogFontSize(mProgress, 25);
	}

	/**
	 * 显示 进度对话框
	 * 
	 * @param message
	 *            消息
	 * @param cancel
	 *            是否可取消
	 * @param textsize
	 *            字体大小
	 */
	public final void showProgressDialog(String message,
			OnCancelListener listener, int textsize) {
		// TODO Auto-generated method stub
		mProgress = new ProgressDialog(this);
		mProgress.setMessage(message);
		mProgress.setCancelable(true);
		mProgress.setOnCancelListener(listener);
		mProgress.show();

		setDialogFontSize(mProgress, textsize);
	}

	/**
	 * 隐藏 进度对话框
	 */
	public final void hideProgressDialog() {
		// TODO Auto-generated method stub
		if (mProgress != null) {
			mProgress.dismiss();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SuidingApp.getApp().setCurActivity(this, this);
		this.onQueryChanged();
	}

	/**
	 * 查询系统数据变动
	 */
	protected void onQueryChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// if(mWorker != null){
		// mWorker.quit();
		// }
	}

	private void setDialogFontSize(Dialog dialog, int size) {
		Window window = dialog.getWindow();
		View view = window.getDecorView();
		setViewFontSize(view, size);
	}

	private void setViewFontSize(View view, int size) {
		if (view instanceof ViewGroup) {
			ViewGroup parent = (ViewGroup) view;
			int count = parent.getChildCount();
			for (int i = 0; i < count; i++) {
				setViewFontSize(parent.getChildAt(i), size);
			}
		} else if (view instanceof TextView) {
			TextView textview = (TextView) view;
			textview.setTextSize(size);
		}
	}

}
