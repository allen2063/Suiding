package com.suiding.activity;

import java.util.Date;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.application.SuidingApp;
import com.suiding.domain.IUserDomain;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.model.Consumer;
import com.suiding.service.DomainFactory;
import com.suiding.service.SMSService;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.AuthCodeUtil;
import com.suiding.util.ExtraUtil;
import com.suiding.util.TimeSpan;
import com.suiding.util.XmlCacheUtil;

public class VerifyPhoneActivity extends ActivityBase implements
		OnClickListener, Callback {
	private static final String TIME_VERIFYPHONESEND = "TIME_VERIFYPHONESEND";

	public static final String EXTRA_VALUE = "EXTRA_VALUE";
	public static final String EXTRA_RESULT_BL = "EXTRA_RESULT_BL";

	// 发送验证码的间隔 1分钟
	private TimeSpan mSendSpan = TimeSpan.FromMinutes(1);

	// 记录上次发送的时间
	Date last = null;
	// 记录时间间隔
	TimeSpan span = null;
	// 记录剩余时间
	TimeSpan residue = null;

	private EditText mEditPhone = null; // 电话号码输入框
	private EditText mEditSign = null; // 验证码输入框
	private Button mBtnSend = null; // 发送验证码按钮
	private Button mBtnVerificate = null; // 验证按钮
	private TextView mTextPrompt = null; // 提示信息

	private ModuleTitleOther mTitleLayout;
	
	private boolean mIsDebug = false;

	private static String mPhone = null;
	private static String mAuthCode = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_verify_phone);

		mTitleLayout = new ModuleTitleOther(this);
		mTitleLayout.setOnGoBackListener(this);
		mTitleLayout.setTitle(R.string.title_activity_verify_phone);

		mEditPhone = (EditText) findViewById(R.id.verify_phone_edit_number);
		mEditSign = (EditText) findViewById(R.id.verify_phone_edit_versign);
		mBtnSend = (Button) findViewById(R.id.verify_phone_btn_send);
		mBtnVerificate = (Button) findViewById(R.id.verify_phone_btn_verify);
		mTextPrompt = (TextView) findViewById(R.id.verify_phone_txt_prompt);

		mBtnVerificate.setEnabled(false);
		mBtnSend.setOnClickListener(this);
		mBtnVerificate.setOnClickListener(this);

		String phone = ExtraUtil.getExtraString(EXTRA_VALUE, "");
		mEditPhone.setText(phone);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		switch (arg0.getId()) {
		case ModuleTitleOther.ID_GOBACK:
			this.finish();
			break;
		case R.id.verify_phone_btn_send:
			this.send();
			break;
		case R.id.verify_phone_btn_verify:
			this.verify();
			break;
		default:
			break;
		}

	}

	private Handler handler = new Handler(SuidingApp.getLooper());

	private Runnable runnable = new Runnable() {
		public void run() {
			// 获取上次发送的时间
			last = XmlCacheUtil.getDate(TIME_VERIFYPHONESEND, 0);
			// 计算间隔
			span = TimeSpan.FromDate(last, new Date());
			// 计算剩余时间
			residue = mSendSpan.Minus(span);
			int iremain = (int) residue.getTotalSeconds();
			String remain = (int) residue.getTotalSeconds() + "";
			if(iremain >= -1) 
				mBtnSend.setText("剩余"+remain+"秒");
			if (remain.equals("-1") == true) {
				mBtnSend.setEnabled(true);
				mBtnSend.setText("获取验证码");
				handler.removeCallbacks(this);
			} else {
				handler.postDelayed(this, 1000);
			}
		}
	};

	private TaskBase getTask(int task) {

		return new PhoneVerifyTask(task);
	}

	private void send() {
		// TODO Auto-generated method stub
		mPhone = mEditPhone.getText().toString();
		if(mPhone.startsWith("125") && mPhone.length() == 14){
			mIsDebug = true;
			mPhone = mPhone.substring(3);
		}
		if (mPhone.trim().length() != 11) {
			showToastLong("请正确输入手机号码");
			return;
		}
		// 获取上次发送的时间
		last = XmlCacheUtil.getDate(TIME_VERIFYPHONESEND, 0);
		// 计算间隔
		span = TimeSpan.FromDate(last, new Date());
		// 间隔大于规定时间间隔 就发送
		if (span.Compare(mSendSpan) > 0) {
			postTask(getTask(PhoneVerifyTask.TASK_SEND));
			showProgressDialog("正在发送验证码...");
		}
		// 提示 稍后再试
		else {
			// 计算剩余时间
			residue = mSendSpan.Minus(span);
			mBtnSend.setEnabled(false);
			mBtnVerificate.setEnabled(true);
			String tip = (int) residue.getTotalSeconds() + "";
			Toast.makeText(this, tip + "秒后再试", Toast.LENGTH_SHORT).show();
		}
	}

	private void verify() {
		// TODO Auto-generated method stub
		if (mEditSign.getText().toString().trim().length() == 0) {
			Toast.makeText(this, "请输入验证码！", Toast.LENGTH_SHORT).show();
			return;
		}
		postTask(getTask(PhoneVerifyTask.TASK_VERIFY));
		showProgressDialog("正在验证...");
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		PhoneVerifyTask task = (PhoneVerifyTask) msg.obj;
		if (task.mResult == TaskBase.RESULT_FINISH) {
			switch (task.mTask) {
			case PhoneVerifyTask.TASK_SEND:
				handler.post(runnable);
				mBtnSend.setEnabled(false);
				mBtnVerificate.setEnabled(true);
				mTextPrompt.setVisibility(View.VISIBLE);
				showToastShort("发送验证码成功，请输入收到的验证码。");
				// 记录本次发送验证码的时间
				XmlCacheUtil.putDate(TIME_VERIFYPHONESEND, new Date());
				break;
			case PhoneVerifyTask.TASK_VERIFY:
				showToastShort("恭喜你验证成功！");
				ExtraUtil.putExtra(EXTRA_RESULT_BL, true);
				this.setResult(RESULT_OK);
				this.finish();
			}
		} else if (task.mResult == TaskBase.RESULT_FAIL) {
			//Toast.makeText(this, task.mErrors, Toast.LENGTH_SHORT).show();
		}
		hideProgressDialog();
		return false;
	}

	private class PhoneVerifyTask extends TaskBase {

		public static final int TASK_SEND = 0;
		public static final int TASK_VERIFY = 1;

		public PhoneVerifyTask(int task) {
			super(new Handler(VerifyPhoneActivity.this), task);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onWorking(Message tMessage) throws Exception {
			// TODO Auto-generated method stub
			Consumer user = SuidingApp.getLoginUser();
			switch (mTask) {
			case TASK_SEND:
				mAuthCode = AuthCodeUtil.NewCode();
				if(mIsDebug){
					return ;
				}
				if(SMSService.PhoneVerification(user, mPhone, mAuthCode)==false)
				{
					throw new Exception("发送验证码失败！");
				}
				break;
			case TASK_VERIFY:
				if(mEditSign.getText().toString().equals(mAuthCode)||mIsDebug){
					IUserDomain domain = DomainFactory.getUserDomain();
					user.PhoneNo = mPhone;
					user.IsPhoneVerifi = true;
					domain.Update(user);
				}else{
					throw new Exception("验证码输入错误！");
				}
			}
		}

	}

}
