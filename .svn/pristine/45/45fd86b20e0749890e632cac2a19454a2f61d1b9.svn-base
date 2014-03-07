package com.suiding.activity.framework;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.view.View;
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

public interface Pageable {

	public Context getContext();

	public Activity getActivity();

	public Resources getResources();

	public View findViewById(int id);

	public TextView findTextViewById(int id);

	public ImageView findImageViewById(int id);

	public Button findButtonById(int id);

	public EditText findEditTextById(int id);

	public CheckBox findCheckBoxById(int id);

	public RadioButton findRadioButtonById(int id);

	public ListView findListViewById(int id);

	public LinearLayout findLinearLayoutById(int id);

	public FrameLayout findFrameLayoutById(int id);

	public RelativeLayout findRelativeLayoutById(int id);

	public ScrollView findScrollViewById(int id);

	public ViewPager findViewPagerById(int id);

	public ProgressBar findProgressBarById(int id);

	public RadioGroup findRadioGroupById(int id);

	public RatingBar findRatingBarById(int id);
	
	

	public HorizontalScrollView findHorizontalScrollViewById(int id);

	public void showToastLong(int resid);
	
	public void showToastShort(int resid);
	
	public void showToastLong(String tip);

	public void showToastShort(String tip);

	public void setSoftInputEnable(EditText editview, boolean enable);

	/**
	 * 显示 进度对话框
	 * 
	 * @param message
	 *            消息
	 */
	public void showProgressDialog(String message);

	/**
	 * 显示 进度对话框
	 * 
	 * @param message
	 *            消息
	 * @param cancel
	 *            是否可取消
	 */
	public void showProgressDialog(String message, boolean cancel);
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
	public void showProgressDialog(String message, boolean cancel,
			int textsize);

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
	public void showProgressDialog(String message,
			OnCancelListener listener);

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
	public void showProgressDialog(String message,
			OnCancelListener listener, int textsize);

	/**
	 * 隐藏 进度对话框
	 */
	public void hideProgressDialog();

}
