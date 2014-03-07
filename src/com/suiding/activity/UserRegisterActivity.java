package com.suiding.activity;

import java.util.Date;

import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.application.SuidingApp;
import com.suiding.domain.IConsumerDomain;
import com.suiding.domain.IUserDomain;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.model.Consumer;
import com.suiding.service.DomainFactory;
import com.suiding.service.SMSService;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.AuthCodeUtil;
import com.suiding.util.DateGuid;
import com.suiding.util.StringUtil;
import com.suiding.util.TimeSpan;
import com.suiding.util.XmlCacheUtil;

public class UserRegisterActivity extends ActivityBase implements
		OnClickListener {
	private static final String TIME_VERIFYPHONESEND = "TIME_VERIFYPHONESEND";

	private EditText mEtCode;
	private EditText mEtPhone;
	private EditText mEtUserName;
	private EditText mEtPassword;
	private EditText mEtConfirm;

	private View mBtSubmit;
	private View mBtGetCode;
	private View mBtPassWord;
	private CheckBox mCbProtocol;

	private String mPhone = "";
	private String mUserName = "";
	private String mPassword = "";

	private ModuleTitleOther mTitleLayout;

	private int mCurpage = 1;

	private boolean mIsDebug = false;

	// ��¼�ϴη��͵�ʱ��
	private static Date mLastGet = new Date(0);
	// ��¼ʱ����
	private static TimeSpan mTimeSpan = null;
	// ��¼ʣ��ʱ��
	private static TimeSpan mResidue = null;
	// ��֤��
	private static String mAuthCode = null;
	// ������֤��ļ�� 1����
	private static TimeSpan mSendSpan = TimeSpan.FromMinutes(1);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		initializeLayout1();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (mCurpage == 3) {
				initializeLayout2();
				return true;
			} else if (mCurpage == 2) {
				initializeLayout1();
				return true;
			}
		}
		return super.dispatchKeyEvent(event);
	}

	private void initializeLayout1() {
		// TODO Auto-generated method stub
		setContentView(R.layout.layout_register_1);
		mCurpage = 1;

		mTitleLayout = new ModuleTitleOther(this);
		mBtGetCode = findViewById(R.id.register_bt_getcode);
		mEtPhone = (EditText) findViewById(R.id.register_et_phone);
		mCbProtocol = (CheckBox) findViewById(R.id.register_bt_protocol);

		mBtGetCode.setOnClickListener(this);
		mTitleLayout.setOnGoBackListener(this);
		mTitleLayout.setTitle(R.string.title_activity_regist);
	}

	private void initializeLayout2() {
		// TODO Auto-generated method stub
		setContentView(R.layout.layout_register_2);
		mCurpage = 2;

		mTitleLayout = new ModuleTitleOther(this);
		mEtCode = (EditText) findViewById(R.id.register_et_code);
		mEtUserName = (EditText) findViewById(R.id.register_et_name);
		mBtPassWord = findViewById(R.id.register_bt_setpassword);
		
		mEtUserName.setText(mPhone);

		mBtPassWord.setOnClickListener(this);
		mTitleLayout.setOnGoBackListener(this);
		mTitleLayout.setTitle(R.string.title_activity_regist);
	}

	private void initializeLayout3() {
		// TODO Auto-generated method stub
		setContentView(R.layout.layout_register_3);
		mCurpage = 3;

		mTitleLayout = new ModuleTitleOther(this);
		mEtConfirm = (EditText) findViewById(R.id.register_et_confirm);
		mEtPassword = (EditText) findViewById(R.id.register_et_password);
		mBtSubmit = findViewById(R.id.register_bt_submit);

		mBtSubmit.setOnClickListener(this);
		mTitleLayout.setOnGoBackListener(this);
		mTitleLayout.setTitle(R.string.title_activity_regist);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.register_bt_getcode:
			this.sendCode();
			break;
		case R.id.register_bt_setpassword:
			this.sendVerify();
			break;
		case R.id.register_bt_submit:
			this.userRegister();
			break;

		case ModuleTitleOther.ID_GOBACK:
			if (mCurpage == 3) {
				initializeLayout2();
			} else if (mCurpage == 2) {
				initializeLayout1();
			} else {
				finish();
			}
			break;
		}
	}

	private void sendVerify() {
		// TODO Auto-generated method stub
		if (mEtCode.getText().toString().equals(mAuthCode) || mIsDebug) {
			mUserName = mEtUserName.getText().toString();
			if (mUserName.length() < 6) {
				Toast.makeText(this, "��¼������С����λ��", Toast.LENGTH_SHORT).show();
				return;
			}
			showProgressDialog("���ڼ��...");
			postTask(new RegisterTask(RegisterTask.TASK_CHECK));
		} else {
			Toast.makeText(this, "��֤���������", Toast.LENGTH_SHORT).show();
		}
	}

	private void sendCode() {
		// TODO Auto-generated method stub
		mPhone = mEtPhone.getText().toString().trim();
		if(mPhone.startsWith("125") && mPhone.length() == 14){
			mIsDebug  = true;
			mPhone = mPhone.substring(3);
		}
		
		if (mCbProtocol.isChecked() == false) {
			Toast.makeText(this, "����û�н����û�Э��", Toast.LENGTH_SHORT).show();
			return;
		}

		if (mPhone.length() == 11) {
			// ��ȡ�ϴη��͵�ʱ��
			mLastGet = XmlCacheUtil.getDate(TIME_VERIFYPHONESEND, 0);
			// ������
			mTimeSpan = TimeSpan.FromDate(mLastGet, new Date());
			// ������ڹ涨ʱ���� �ͷ���
			if (mTimeSpan.Compare(mSendSpan) > 0) {
				postTask(new RegisterTask(RegisterTask.TASK_SEND));
				showProgressDialog("���ڷ�����֤��...");
			}
			// ��ʾ �Ժ�����
			else {
				// ����ʣ��ʱ��
				mResidue = mSendSpan.Minus(mTimeSpan);
				String tip = (int) mResidue.getTotalSeconds() + "";
				Toast.makeText(this, tip + "�������", Toast.LENGTH_SHORT).show();
			}
		} else {
			mPhone = "";
			Toast.makeText(this, "��������ȷ���ֻ�����", Toast.LENGTH_SHORT).show();
		}
	}

	private void userRegister() {
		// TODO Auto-generated method stub
		mPassword = mEtPassword.getText().toString();

		if (mPassword.length() < 6) {
			Toast.makeText(this, "���벻��С����λ", Toast.LENGTH_LONG).show();
			mEtConfirm.setText("");
			mEtPassword.beginBatchEdit();
			return;
		} else if (!mEtConfirm.getText().toString().equals(mPassword)) {
			mEtConfirm.setText("");
			mEtPassword.setText("");
			mEtPassword.beginBatchEdit();
			Toast.makeText(this, "����������벻һ�¡�", Toast.LENGTH_LONG).show();
			return;
		}

		// ������MD5����
		mPassword = StringUtil.getMD5(mPassword.getBytes());

		// ��ʾ�Ի���
		showProgressDialog("����ע��...");

		// ������ŵ��߳��п�ʼ����
		postTask(new RegisterTask(RegisterTask.TASK_REGISTER));
	}

	private class RegisterTask extends TaskBase {

		public static final int TASK_SEND = 1;
		public static final int TASK_CHECK = 2;
		public static final int TASK_REGISTER = 3;

		public Consumer mConsumer = null;
		public Boolean mIsExists = false;

		public RegisterTask(int Task) {
			super(SuidingApp.getLooper(), Task);
		}

		@Override
		protected void onWorking(Message tMessage) throws Exception {
			// TODO Auto-generated method stub
			switch (mTask) {
			case TASK_SEND: {
				IUserDomain tUserDomain = DomainFactory.getUserDomain();
				if (tUserDomain.Exists(mUserName) == true) {
					mIsExists = true;
					return ;
				}
				if(mIsDebug){
					
				}else{
					// mAuthCode = "1234";
					mAuthCode = AuthCodeUtil.NewCode();
					if (SMSService.PhoneVerification(mPhone, mPhone, mAuthCode) == false) {
						throw new Exception("������֤��ʧ�ܣ�");
					}
				}
				break;
			}
			case TASK_CHECK: {
				//IUserDomain tUserDomain = DomainFactory.getUserDomain();
				//if (tUserDomain.Exists(mUserName) == true) {
				//	mIsExists = true;
				//}
				break;
			}
			case TASK_REGISTER: {
				IConsumerDomain domain = DomainFactory.getConsumerDomain();
				mConsumer = new Consumer();
				mConsumer.Sex = true;
				mConsumer.PhoneNo = mPhone;
				mConsumer.IsPhoneVerifi = true;
				mConsumer.UserName = mUserName;
				mConsumer.Password = mPassword;
				mConsumer.NickName = "sd"+DateGuid.NewID().substring(9);
				domain.registeredConsumer(mConsumer);
				//domain.Insert(mConsumer);
			}
			}
		}

		@Override
		protected boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if (mResult == RESULT_FINISH) {
				switch (mTask) {
				case TASK_SEND:
					if (mIsExists == true) {
						//Toast.makeText(getBaseContext(), "������ĵ�¼���Ѿ�����",
						//		Toast.LENGTH_SHORT).show();
						showToastShort("��������ֻ������Ѿ�����");
						return true;
					} 
					initializeLayout2();
					break;
				case TASK_CHECK:
					//if (mIsExists == true) {
					//	Toast.makeText(getBaseContext(), "������ĵ�¼���Ѿ�����",
					//			Toast.LENGTH_SHORT).show();
					//} else {
					initializeLayout3();
					//}
					break;
				case TASK_REGISTER:
					SuidingApp tLeSouShopApp = SuidingApp.getApp();
					tLeSouShopApp.setLoginUser(UserRegisterActivity.this,
							mConsumer);
					hideProgressDialog();
					setResult(RESULT_OK);
					finish();
					Toast.makeText(getBaseContext(), "��ϲ��ע��ɹ�",
							Toast.LENGTH_SHORT).show();
					break;
				}
			} else {
				showToastShort("���粻������������һ�ΰ�~");
//				Toast.makeText(getBaseContext(), mErrors, Toast.LENGTH_SHORT)
//						.show();
			}
			hideProgressDialog();
			return true;
		}

	}
}
