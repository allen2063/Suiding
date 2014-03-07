package com.suiding.activity;

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
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.ExtraUtil;

public class VerifyEmailActivity extends ActivityBase implements
		OnClickListener, Callback {

	public static final String EXTRA_VALUE = "EXTRA_VALUE";
	public static final String EXTRA_RESULT_BL = "EXTRA_RESULT_BL";
	
	private Button mBtnSendEmail = null; // 发送邮件按钮
	private EditText mEditEmail = null; // 邮箱地址输入框
	private TextView mTextPrompt = null; // 提示信息

	private ModuleTitleOther mTitleLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_verify_email);
		
		mTitleLayout = new ModuleTitleOther(this);
		mTitleLayout.setOnGoBackListener(this);
		mTitleLayout.setTitle(R.string.title_activity_verify_email);

		mEditEmail = (EditText) findViewById(R.id.verify_email_edit_mail);
		mBtnSendEmail = (Button) findViewById(R.id.verify_email_btn_send);
		mTextPrompt = (TextView) findViewById(R.id.verify_email_txt_prompt);

		String email = ExtraUtil.getExtraString(EXTRA_VALUE, "");
		mEditEmail.setText(email);
		
		mBtnSendEmail.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		switch (arg0.getId()) {
		case ModuleTitleOther.ID_GOBACK:
			this.finish();
			break;
		case R.id.verify_email_btn_send:
			this.send();
			break;
		default:
			break;
		}

	}

	private TaskBase getTask(int task) {

		return new EmailVerifyTask(task);
	}

	private void send() {
		// TODO Auto-generated method stub
		if (mEditEmail.getText().toString().trim().length() == 0) {
			Toast.makeText(this, "请输入邮箱号码！", Toast.LENGTH_SHORT).show();
			return;
		}
		// 发送
		postTask(getTask(EmailVerifyTask.TASK_SEND));
		showProgressDialog("正在发送邮件...");
	}

	private class EmailVerifyTask extends TaskBase {

		public static final int TASK_SEND = 0;

		public EmailVerifyTask(int task) {
			super(new Handler(VerifyEmailActivity.this), task);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onWorking(Message tMessage) throws Exception {
			// TODO Auto-generated method stub
			switch (mTask) {
			case TASK_SEND:
				Thread.sleep(1000);
				//User user = SuidingApp.getLoginUser();
				//String mail =  mEditEmail.getText().toString();
				//SMSService.PhoneVerification(user, phoneNo, vcode);
				break;
			default:
				break;
			}
		}

	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		EmailVerifyTask task = (EmailVerifyTask) msg.obj;
		if (task.mResult == TaskBase.RESULT_FINISH) {
			switch (task.mTask) {
			case EmailVerifyTask.TASK_SEND:
				String tip = "邮件发送成功，请及时查收。";
				Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
				mTextPrompt.setVisibility(View.VISIBLE);
				break;
			}
		} else if (task.mResult == TaskBase.RESULT_FINISH) {
			showToastShort("网络不给力啊，再试一次吧~");
			//Toast.makeText(this, task.mErrors, Toast.LENGTH_SHORT).show();
		}
		hideProgressDialog();
		return false;
	}
}
