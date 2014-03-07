package com.suiding.activity;

import com.suiding.application.SuidingApp;
import com.suiding.domain.IConsumerDomain;
import com.suiding.domain.IUserDomain;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.model.Consumer;
import com.suiding.model.User;
import com.suiding.service.DomainFactory;
import com.suiding.util.XmlCacheUtil;
import com.suiding.util.StringUtil;
import com.suiding.activity.framework.ActivityBase;
import com.suiding.thread.framework.TaskBase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class UserLoginActivity extends ActivityBase implements OnClickListener,
		Callback, OnLongClickListener {
	private static final String AUTOPASSWORD = "******";

	private EditText mEtUserName;
	private EditText mEtPassword;
	private Button mBtLogin;
	private Button mBtRegister;
	private CheckBox mCbAutoLogin;
	private ModuleTitleOther mTitleLayout;
	private boolean mIsDebug = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);

		mEtUserName = (EditText) findViewById(R.id.login_idedit);
		mEtPassword = (EditText) findViewById(R.id.login_passwordedit);
		mBtLogin = (Button) findViewById(R.id.user_login_login);
		mBtRegister = (Button) findViewById(R.id.user_login_register);
		mCbAutoLogin = (CheckBox) findViewById(R.id.user_login_autor);
		mTitleLayout = new ModuleTitleOther(this);

		String keyname = XmlCacheUtil.LOGIN_ACCOUNT;
		String keyauto = XmlCacheUtil.LOGIN_AUTOLOGIN;
		String username = XmlCacheUtil.getString(keyname, "");
		mEtUserName.setText(username);

		boolean isAuto = XmlCacheUtil.getBoolean(keyauto, false);
		if (isAuto) {
			mCbAutoLogin.setChecked(isAuto);
			mEtPassword.setText(AUTOPASSWORD);
		}

		mBtLogin.setOnClickListener(this);
		mBtLogin.setOnLongClickListener(this);
		mBtRegister.setOnClickListener(this);
		mTitleLayout.setOnGoBackListener(this);
		mTitleLayout.setTitle(R.string.title_activity_userlogin);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			XmlCacheUtil.putString(XmlCacheUtil.LOGIN_ACCOUNT,
					SuidingApp.getLoginUser().UserName);
			this.setResult(RESULT_OK);
			this.finish();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.user_login_login:
			this.Login();
			break;
		case ModuleTitleOther.ID_GOBACK:
			this.finish();
			break;
		case R.id.user_login_register:
			Intent intent = new Intent();
			intent.setClass(this, UserRegisterActivity.class);
			this.startActivityForResult(intent, 1);
			break;
		}
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		if (mBtLogin == v) {
			mIsDebug = true;
			String tAccount = "scwang";
			String tPassword = "12345678";
			// 把密码MD5加密
			tPassword = StringUtil.getMD5(tPassword.getBytes());
			// 显示进度条
			TaskBase task = new LoginTask(tAccount, tPassword);
			showProgressDialog("正在登录...", postTask(task));
		}
		return false;
	}

	private void Login() {
		// TODO Auto-generated method stub
		String tAccount = mEtUserName.getText().toString();
		String tPassword = mEtPassword.getText().toString();

		if (tAccount.length() == 0) {
			Toast.makeText(this, "账户不能为空！", Toast.LENGTH_LONG).show();
			return;
		} else if (tPassword.length() == 0) {
			Toast.makeText(this, "密码不能为空！", Toast.LENGTH_LONG).show();
			return;
		}
		// 把密码MD5加密
		if (AUTOPASSWORD.equals(tPassword)) {
			tPassword = XmlCacheUtil.getString(XmlCacheUtil.LOGIN_PASSWORD, "");
		} else {
			tPassword = StringUtil.getMD5(tPassword.getBytes());
		}
		// 显示进度条
		TaskBase task = new LoginTask(tAccount, tPassword);
		showProgressDialog("正在登录...", postTask(task));
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		LoginTask tTask = (LoginTask) msg.obj;
		if (tTask.mResult == LoginTask.RESULT_FINISH) {
			mIsDebug = !mIsDebug;
			SuidingApp tLeSouShopApp = SuidingApp.getApp();
			tLeSouShopApp.setLoginUser(this, tTask.mConsumer);
			// if(mIsDebug ==false){
			XmlCacheUtil.putString(XmlCacheUtil.LOGIN_ACCOUNT,
					tTask.mConsumer.UserName);
			XmlCacheUtil.putBoolean(XmlCacheUtil.LOGIN_AUTOLOGIN,
					mCbAutoLogin.isChecked());

			if (mCbAutoLogin.isChecked()) {
				XmlCacheUtil.putString(XmlCacheUtil.LOGIN_PASSWORD,
						tTask.mConsumer.Password);
			} else {
				XmlCacheUtil.putString(XmlCacheUtil.LOGIN_PASSWORD, "");
			}
			// }
			hideProgressDialog();
			setResult(RESULT_OK);
			finish();
			// }
		} else if (tTask.mResult == LoginTask.RESULT_FAIL) {
			//Toast.makeText(this, tTask.mErrors,Toast.LENGTH_SHORT).show();
		}
		hideProgressDialog();
		return true;
	}

	private class LoginTask extends TaskBase {

		public String mAccount = null;
		public String mPassword = null;

		public User mUser = null;
		public Consumer mConsumer = null;

		public LoginTask(String tAccount, String tPassword) {
			super(new Handler(UserLoginActivity.this));
			// TODO Auto-generated constructor stub
			mAccount = tAccount;
			mPassword = tPassword;
		}

		@Override
		protected void onWorking(Message tMessage) throws Exception {
			// TODO Auto-generated method stub
			IUserDomain tIUserDomain = DomainFactory.getUserDomain();
			mUser = tIUserDomain.Verify(mAccount, mPassword);
			if (mUser == null) {
				throw new Exception("用户名或者密码错误！");
			}
			IConsumerDomain tIConsumerDomain = DomainFactory
					.getConsumerDomain();
			mConsumer = tIConsumerDomain.getByUserID(mUser.getID());
			if (mConsumer == null) {
				throw new Exception("此账户数据信息不完整，登录失败!");
			}
		}

	}

	public static void autoLodin() {
		// TODO Auto-generated method stub
		if (XmlCacheUtil.getBoolean(XmlCacheUtil.LOGIN_AUTOLOGIN, false)) {
			SuidingApp.postTask(new AutoLoginTask());
		}
	}

	private static class AutoLoginTask extends TaskBase {

		public Consumer mUser = null;

		public AutoLoginTask() {
			super(SuidingApp.getLooper());
		}

		@Override
		protected boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if (mResult == RESULT_FINISH) {
				SuidingApp app = SuidingApp.getApp();
				app.setLoginUser(new UserLoginActivity(), mUser);
			} else {
				Toast.makeText(SuidingApp.getApp(), "自动登录失败",
						Toast.LENGTH_SHORT).show();
			}
			return true;
		}

		@Override
		protected void onWorking(Message msg) throws Exception {
			// TODO Auto-generated method stub
			String keyname = XmlCacheUtil.LOGIN_ACCOUNT;
			String keypass = XmlCacheUtil.LOGIN_PASSWORD;
			String username = XmlCacheUtil.getString(keyname, "");
			String password = XmlCacheUtil.getString(keypass, "");

			IUserDomain tIUserDomain = DomainFactory.getUserDomain();
			User user = tIUserDomain.Verify(username, password);
			if (user == null) {
				throw new Exception("用户名或者密码错误！");
			}
			IConsumerDomain tIConsumerDomain = DomainFactory
					.getConsumerDomain();
			mUser = tIConsumerDomain.getByUserID(user.getID());
		}

	}
}
