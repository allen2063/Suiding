package com.suiding.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.application.AppExceptionHandler;
import com.suiding.application.ImageService;
import com.suiding.application.SuidingApp;
import com.suiding.caches.ImageCaches;
import com.suiding.constant.ImageFolderEnum;
import com.suiding.constant.ImageSize;
import com.suiding.domain.IConsumerDomain;
import com.suiding.domain.IUserDomain;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.model.Consumer;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.ExtraUtil;

public class EditAccountActivity extends ActivityBase implements
		OnClickListener, Callback, OnDateSetListener,
		DialogInterface.OnClickListener {
	public static final String EXTRA_IT_ACTION = "EXTRA_ACTION";
	public static final String EXTRA_OB_CONSUMER = "EXTRA_CONSUMER";
	public static final String EXTRA_BL_LOGOUT = "EXTRA_BL_LOGOUT";

	public static final int ACTION_NONE = 0;
	public static final int ACTION_NAME = 1;
	public static final int ACTION_PHONE = 2;
	public static final int ACTION_EMAIL = 3;
	
	private static final int REQUEST_PHOTO = 0;
	private static final int REQUEST_EDIT = 1;
	private static final int REQUEST_VERIFYEMAIL = 2;
	private static final int REQUEST_VERIFYPHONE = 3;


	private String mHeadImgPath = null;

	private Consumer mConsumer = null;

	private ModuleTitleOther mLayoutTitle = null;

	private View mLayoutHeader = null;
	private View mLayoutName = null;
	private View mLayoutNickName = null;
	private View mLayoutSex = null;
	private View mLayoutBirthday = null;
	private View mLayoutAutograph = null;
	private View mLayoutEnlishName = null;
	private View mLayoutCellPhone = null;
	private View mLayoutEmail = null;
	private View mLayoutLogout = null;

	private TextView mTvSex = null;
	private TextView mTvName = null;
	private TextView mTvNickName = null;
	private TextView mTvBirthday = null;
	private TextView mTvAutograph = null;
	private TextView mTvEnglishName = null;
	private TextView mTvPhone = null;
	private TextView mTvEmail = null;
	private TextView mTvApprovePhone = null;
	private TextView mTvApproveEmail = null;
	private ImageView mHeaderImage = null;

	private int mAction = ACTION_NONE;
	private boolean mIsWorking = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_account_info);

		mLayoutTitle = new ModuleTitleOther(this);

		mLayoutSex = findViewById(R.id.editaccount_bar_sex);
		mLayoutName = findViewById(R.id.editaccount_bar_name);
		mLayoutHeader = findViewById(R.id.editaccount_bar_headimg);
		mLayoutNickName = findViewById(R.id.editaccount_bar_nickname);
		mLayoutBirthday = findViewById(R.id.editaccount_bar_birthday);
		mLayoutAutograph = findViewById(R.id.editaccount_bar_autograph);
		mLayoutEnlishName = findViewById(R.id.editaccount_bar_englishname);
		mLayoutCellPhone = findViewById(R.id.editaccount_bar_phone);
		mLayoutEmail = findViewById(R.id.editaccount_bar_email);
		mLayoutLogout = findViewById(R.id.editaccount_bar_logout);

		mTvSex = findTextViewById(R.id.editaccount_sex);
		mTvName = findTextViewById(R.id.editaccount_name);
		mTvNickName = findTextViewById(R.id.editaccount_nickname);
		mTvBirthday = findTextViewById(R.id.editaccount_birthday);
		mTvAutograph = findTextViewById(R.id.editaccount_autograph);
		mTvEnglishName = findTextViewById(R.id.editaccount_englishname);
		mTvPhone = findTextViewById(R.id.editaccount_phone);
		mTvEmail = findTextViewById(R.id.editaccount_email);
		mTvApprovePhone = findTextViewById(R.id.editaccount_approve_phone);
		mTvApproveEmail = findTextViewById(R.id.editaccount_approve_email);
		mHeaderImage = findImageViewById(R.id.editaccount_headimg);

		mLayoutSex.setOnClickListener(this);
		mLayoutName.setOnClickListener(this);
		mLayoutHeader.setOnClickListener(this);
		mLayoutNickName.setOnClickListener(this);
		mLayoutBirthday.setOnClickListener(this);
		mLayoutAutograph.setOnClickListener(this);
		mLayoutCellPhone.setOnClickListener(this);
		mLayoutEmail.setOnClickListener(this);
		mLayoutEnlishName.setOnClickListener(this);
		mLayoutLogout.setOnClickListener(this);

		File cachepath = ImageCaches.getInstance(this).getCachePath();
		mHeadImgPath = cachepath.getAbsolutePath() + File.separatorChar
				+ "heagimage.jpg";
		mConsumer = (Consumer) ExtraUtil.getExtra(EXTRA_OB_CONSUMER);

		mLayoutTitle.setTitle(R.string.title_activity_editaccount);

		BindingConsumer(mConsumer);
		
		if(ExtraUtil.getExtraBoolean(EXTRA_BL_LOGOUT, false)){
			mLayoutLogout.setVisibility(View.VISIBLE);			
		}else{
			mLayoutLogout.setVisibility(View.GONE);
		}

		mAction = ExtraUtil.getExtraInt(EXTRA_IT_ACTION, ACTION_NONE);
		if(mAction == ACTION_NAME){
			onClick(mLayoutName);
		}else if(mAction == ACTION_PHONE){
			srartApprovePhone();
		}else if(mAction == ACTION_EMAIL){
			srartApproveEMail();
		}
	}

	private void BindingConsumer(Consumer consumer) {
		
		Date early = new GregorianCalendar(1901, 1, 1).getTime();
		if(consumer.Birth == null || consumer.Birth.before(early)){
			consumer.Birth = early;
		}
		
		mTvSex.setText(consumer.Sex ? "男" : "女");
		mTvName.setText(consumer.Name);
		mTvNickName.setText(consumer.NickName);
		mTvBirthday.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.US)
				.format(consumer.Birth));
		mTvAutograph.setText(consumer.Qianming);
		mTvEnglishName.setText(consumer.EnName);
		mTvPhone.setText(consumer.PhoneNo);
		mTvEmail.setText(consumer.Email);
		
		if(consumer.IsEmailVerifi){
			mTvApproveEmail.setVisibility(View.VISIBLE);
		}else if(consumer.Email==null||consumer.Email.length()==0){
			mTvApproveEmail.setVisibility(View.GONE);
		}

		if(consumer.IsEmailVerifi){
			mTvApproveEmail.setVisibility(View.VISIBLE);
		}else /*if(consumer.Email==null||consumer.Email.length()==0)*/{
			mTvApproveEmail.setVisibility(View.GONE);
		}
		if(consumer.IsPhoneVerifi){
			mTvApprovePhone.setVisibility(View.VISIBLE);
		}else/* if(consumer.Phone==null||consumer.Phone.length()==0)*/{
			mTvApprovePhone.setVisibility(View.GONE);
		}

		ImageService.bindImage(mConsumer.HeadImg, mHeaderImage,
				ImageFolderEnum.HEAD_USER, ImageSize.SMALLER,
				R.drawable.image_person);
	}

	@Override
	public final void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.editaccount_bar_headimg:
			// 调用系统选择图片并剪辑操作
			intent = new Intent(Intent.ACTION_GET_CONTENT, null);
			intent.setType("image/*");
			intent.putExtra("crop", "true");
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 150);
			intent.putExtra("outputY", 150);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);
			// intent.putExtra(MediaStore.EXTRA_OUTPUT,mHeadImgPath);
			intent.putExtra("outputFormat",
					Bitmap.CompressFormat.JPEG.toString());
			intent.putExtra("noFaceDetection", true);
			startActivityForResult(intent, REQUEST_PHOTO);

			break;
		case R.id.editaccount_bar_sex:
		{
			// 弹出性别切换对话框
			Builder dialog = new Builder(this);
			dialog.setTitle("请选择性别");
			dialog.setIcon(android.R.drawable.ic_dialog_info);
			 dialog.setSingleChoiceItems(new String[]
			 { "男", "女" }, mConsumer.Sex ? 0 : 1, this);
			dialog.show();
			break;
		}
		case R.id.editaccount_bar_name:
		{
			intent.setClass(this, EditTextActivity.class);
			ExtraUtil
					.putExtra(EditTextActivity.EXTRA_VALUE, mConsumer.Name);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_HINT,
					R.string.editaccount_hint_name);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_TITLE,
					R.string.editaccount_tv_name);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_FUNCTION,
					EditTextActivity.FUNCTION_SINGLE);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_CALLID,
					R.id.editaccount_bar_name);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_ALLOWNULL, true);
			startActivityForResult(intent, REQUEST_EDIT);
			break;
		}
		case R.id.editaccount_bar_nickname:
			intent.setClass(this, EditTextActivity.class);
			ExtraUtil
					.putExtra(EditTextActivity.EXTRA_VALUE, mConsumer.NickName);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_HINT,
					R.string.editaccount_hint_nickname);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_TITLE,
					R.string.editaccount_tv_nickname);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_FUNCTION,
					EditTextActivity.FUNCTION_SINGLE);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_CALLID,
					R.id.editaccount_bar_nickname);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_ALLOWNULL, false);
			startActivityForResult(intent, REQUEST_EDIT);
			break;
		case R.id.editaccount_bar_birthday:
			Calendar canlender = Calendar.getInstance();
			canlender.setTime(mConsumer.Birth);
			int year = canlender.get(Calendar.YEAR);
			int month = canlender.get(Calendar.MONTH);
			int day = canlender.get(Calendar.DAY_OF_MONTH);
			Dialog tDialog = new DatePickerDialog(this, this, year, month, day);
			tDialog.show();
			tDialog.setCancelable(true);
			break;
		case R.id.editaccount_bar_autograph:
			intent.setClass(this, EditTextActivity.class);
			ExtraUtil
					.putExtra(EditTextActivity.EXTRA_VALUE, mConsumer.Qianming);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_HINT,
					R.string.editaccount_hint_autograph);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_TITLE,
					R.string.editaccount_tv_autograph);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_FUNCTION,
					EditTextActivity.FUNCTION_MULTI);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_CALLID,
					R.id.editaccount_bar_autograph);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_ALLOWNULL, true);
			startActivityForResult(intent, REQUEST_EDIT);
			break;
		case R.id.editaccount_bar_englishname:
			intent.setClass(this, EditTextActivity.class);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_VALUE, mConsumer.EnName);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_HINT,
					R.string.editaccount_hint_englishname);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_TITLE,
					R.string.editaccount_tv_englishname);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_FUNCTION,
					EditTextActivity.FUNCTION_SINGLE);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_CALLID,
					R.id.editaccount_bar_englishname);
			ExtraUtil.putExtra(EditTextActivity.EXTRA_ALLOWNULL, true);
			startActivityForResult(intent, REQUEST_EDIT);
			break;
		case R.id.editaccount_bar_phone:
			if(mConsumer.PhoneNo != null && mConsumer.PhoneNo.length()>0
					&& mConsumer.IsPhoneVerifi == false){
				Builder dialog = new Builder(this);
				dialog.setTitle("选择操作");
				dialog.setIcon(android.R.drawable.ic_dialog_info);
				dialog.setItems(new String[] { "更改号码", "认证手机" }, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if(which == 0){
							srartEditPhone();
						}else{
							srartApprovePhone();
						}
					}
				});
				dialog.show();
			}else{
				this.srartEditPhone();
			}
			break;
		case R.id.editaccount_bar_email:
			if(mConsumer.Email != null && mConsumer.Email.length()>0
					&& mConsumer.IsEmailVerifi == false){
				Builder dialog = new Builder(this);
				dialog.setTitle("选择操作");
				dialog.setIcon(android.R.drawable.ic_dialog_info);
				dialog.setItems(new String[] { "更改邮箱", "认证邮箱" }, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if(which == 0){
							srartEditEMail();
						}else{
							srartApproveEMail();
						}
					}
				});
				dialog.show();
			}else{
				this.srartEditEMail();
			}
			break;
		case R.id.editaccount_bar_logout:
			SuidingApp.getApp().setLoginUser(this,null);
			this.finish();
			break;
		}
	}

	private void srartApproveEMail() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, VerifyEmailActivity.class);
		ExtraUtil.putExtra(VerifyEmailActivity.EXTRA_VALUE, mConsumer.Email);
		startActivityForResult(intent, REQUEST_VERIFYEMAIL);
	}

	private void srartApprovePhone() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, VerifyPhoneActivity.class);
		ExtraUtil.putExtra(VerifyPhoneActivity.EXTRA_VALUE, mConsumer.PhoneNo);
		startActivityForResult(intent, REQUEST_VERIFYPHONE);
	}
	
	private void srartEditEMail() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, EditTextActivity.class);
		ExtraUtil.putExtra(EditTextActivity.EXTRA_VALUE, mConsumer.Email);
		ExtraUtil.putExtra(EditTextActivity.EXTRA_HINT,
				R.string.editaccount_hint_email);
		ExtraUtil.putExtra(EditTextActivity.EXTRA_TITLE,
				R.string.editaccount_tv_email);
		ExtraUtil.putExtra(EditTextActivity.EXTRA_FUNCTION,
				EditTextActivity.FUNCTION_SINGLE);
		ExtraUtil.putExtra(EditTextActivity.EXTRA_CALLID,
				R.id.editaccount_bar_email);
		ExtraUtil.putExtra(EditTextActivity.EXTRA_ALLOWNULL, true);
		startActivityForResult(intent, REQUEST_EDIT);
	}
	
	private void srartEditPhone() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, EditTextActivity.class);
		ExtraUtil.putExtra(EditTextActivity.EXTRA_VALUE, mConsumer.PhoneNo);
		ExtraUtil.putExtra(EditTextActivity.EXTRA_HINT,
				R.string.editaccount_hint_phone);
		ExtraUtil.putExtra(EditTextActivity.EXTRA_TITLE,
				R.string.editaccount_tv_phone);
		ExtraUtil.putExtra(EditTextActivity.EXTRA_FUNCTION,
				EditTextActivity.FUNCTION_SINGLE);
		ExtraUtil.putExtra(EditTextActivity.EXTRA_CALLID,
				R.id.editaccount_bar_phone);
		ExtraUtil.putExtra(EditTextActivity.EXTRA_ALLOWNULL, true);
		startActivityForResult(intent, REQUEST_EDIT);
	}

	/**
	 * 性别对话框选择点击事件
	 */
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		Consumer tConsumer = new Consumer(mConsumer);
		if (tConsumer.Sex != (which == 0)) {
			tConsumer.Sex = which == 0;
			postTask(new EditAccountTask(EditAccountTask.UPDATECONSUMER,
					tConsumer));
			showProgressDialog("正在提交编辑...");
		}
	}

	/**
	 * 生日时间选择事件
	 */
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// TODO Auto-generated method stub
		if (mIsWorking == false)// 小米2S 会发送两次Set mIsWorking 过来后面一次
		{
			Consumer tConsumer = new Consumer(mConsumer);
			tConsumer.Birth = new GregorianCalendar(year, month, day).getTime();
			postTask(new EditAccountTask(EditAccountTask.UPDATECONSUMER,
					tConsumer));
			showProgressDialog("正在提交编辑...");
			mIsWorking = true;
		}
	}

	@Override
	protected final void onActivityResult(int requestCode, int resultCode,
			Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_EDIT) {
				String result = data
						.getStringExtra(EditTextActivity.EXTRA_RESULT);
				Consumer tConsumer = new Consumer(mConsumer);
				switch (data.getIntExtra(EditTextActivity.EXTRA_CALLID, 0)) {
				case R.id.editaccount_bar_name:
					tConsumer.Name = result;
					postTask(new EditAccountTask(EditAccountTask.UPDATECONSUMER,
							tConsumer));
					showProgressDialog("正在提交编辑...");
					break;
				case R.id.editaccount_bar_nickname:
					tConsumer.NickName = result;
					postTask(new EditAccountTask(EditAccountTask.UPDATEUSER,
							tConsumer));
					showProgressDialog("正在提交编辑...");
					break;
				case R.id.editaccount_bar_email:
					tConsumer.Email = result;
					tConsumer.IsEmailVerifi = false;
					mTvApproveEmail.setVisibility(View.GONE);
					postTask(new EditAccountTask(EditAccountTask.UPDATEUSER,
							tConsumer));
					showProgressDialog("正在提交编辑...");
					break;
				case R.id.editaccount_bar_autograph:
					tConsumer.Qianming = result;
					postTask(new EditAccountTask(
							EditAccountTask.UPDATECONSUMER, tConsumer));
					showProgressDialog("正在提交编辑...");
					break;
				case R.id.editaccount_bar_phone:
					tConsumer.PhoneNo = result;
					tConsumer.IsPhoneVerifi = false;
					mTvApprovePhone.setVisibility(View.GONE);
					postTask(new EditAccountTask(
							EditAccountTask.UPDATEUSER, tConsumer));
					showProgressDialog("正在提交编辑...");
					break;
				case R.id.editaccount_bar_englishname:
					tConsumer.EnName = result;
					postTask(new EditAccountTask(
							EditAccountTask.UPDATECONSUMER, tConsumer));
					showProgressDialog("正在提交编辑...");
					break;
				}
			} else if (requestCode == REQUEST_VERIFYEMAIL) {
				boolean result = ExtraUtil.getExtraBoolean(VerifyEmailActivity.EXTRA_RESULT_BL,false);
				if(result == true){
					mTvApproveEmail.setVisibility(View.VISIBLE);
				}
				if(mAction != ACTION_NONE){
					this.finish();
				}
			} else if (requestCode == REQUEST_VERIFYPHONE) {
				boolean result = ExtraUtil.getExtraBoolean(VerifyPhoneActivity.EXTRA_RESULT_BL,false);
				if(result == true){
					mTvApprovePhone.setVisibility(View.VISIBLE);
				}
				if(mAction != ACTION_NONE){
					this.finish();
				}
			} else if (requestCode == REQUEST_PHOTO) {
				if (data != null) {
					Bitmap headimg = data.getParcelableExtra("data");
					try {
						FileOutputStream output = new FileOutputStream(
								mHeadImgPath);
						if (headimg.compress(CompressFormat.JPEG, 100, output)) {
							postTask(new EditAccountTask(
									EditAccountTask.UPDATEDEADIMG,
									new Consumer(mConsumer)));
							showProgressDialog("正在上传头像...");
						}
						output.flush();
						output.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						showToastLong("获取图片失败："+e.getMessage());
						e.printStackTrace();//handled
						AppExceptionHandler.handler(e, "上传头像获取图片错误");
					}
				}
			}
		}else if(mAction != ACTION_NONE){
			this.finish();
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		EditAccountTask tTask = (EditAccountTask) msg.obj;
		if (tTask.mResult == TaskBase.RESULT_FINISH) {
			mConsumer = tTask.mConsumer;
			BindingConsumer(mConsumer);
			SuidingApp tLeSouShopApp = SuidingApp.getApp();
			tLeSouShopApp.setLoginUser(this, tTask.mConsumer);
			if(mAction != ACTION_NONE){
				this.finish();
			}
		} else if (tTask.mResult == TaskBase.RESULT_FAIL) {
			//Toast.makeText(this, tTask.mErrors, Toast.LENGTH_LONG).show();
		}
		mIsWorking = false;
		hideProgressDialog();
		return false;
	}

	private class EditAccountTask extends TaskBase {
		public static final int UPDATEUSER = 0;
		public static final int UPDATECONSUMER = 1;
		public static final int UPDATEDEADIMG = 2;

		public Consumer mConsumer = null;

		public EditAccountTask(int task, Consumer consumer) {
			super(new Handler(EditAccountActivity.this), task);
			// TODO Auto-generated constructor stub
			mConsumer = consumer;
		}

		@Override
		protected void onWorking(Message tMessage) throws Exception {
			// TODO Auto-generated method stub
			switch (mTask) {
			case UPDATEUSER: {
				IUserDomain tDomain = DomainFactory.getUserDomain();
				boolean bool = tDomain.Update(mConsumer);
				if (bool == false) {
					throw new Exception("Update return false");
				}
				break;
			}
			case UPDATECONSUMER: {
				IConsumerDomain tDomain = DomainFactory.getConsumerDomain();
				boolean bool = tDomain.Update(mConsumer);
				if (bool == false) {
					throw new Exception("Update return false");
				}
				break;
			}
			case UPDATEDEADIMG: {
				IConsumerDomain domain = DomainFactory.getConsumerDomain();
				domain.uploadHeadImg(mConsumer, mHeadImgPath);
				break;
			}
			}
		}

	}

}
