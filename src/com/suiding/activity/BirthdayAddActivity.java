package com.suiding.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.application.AppExceptionHandler;
import com.suiding.application.ImageService;
import com.suiding.application.SuidingApp;
import com.suiding.caches.ImageCaches;
import com.suiding.constant.ImageFolderEnum;
import com.suiding.constant.ImageSize;
import com.suiding.dao.BirthdayEntityDao;
import com.suiding.domain.IBirthFavoriteDomain;
import com.suiding.entity.BirthdayEntity;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.layoutbind.SwitchCheckBox;
import com.suiding.model.BirthFavorite;
import com.suiding.model.Consumer;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.DateFormatUtil;
import com.suiding.util.ExtraUtil;

public class BirthdayAddActivity extends ActivityBase implements
		OnClickListener, DialogInterface.OnClickListener, OnDateSetListener,
		Callback, OnCheckedChangeListener {

	public static final String EXTRA_DATA = "EXTRA_DATA";
	public static final String EXTRA_TYPE = "EXTRA_TYPE";

	public static final int REQUEST_PHOTO = 0;

	public static final int ADD = 0;
	public static final int EDIT = 1;

	private Integer mType = ADD;
	private boolean isAdded = false;	//是否已经添加 mType=EDIT 时为true

	private View mUploadHead = null;// 上传头像
	private ImageView mIvHeadImg = null;// 上传头像
	private EditText mEditName = null;// 昵称输入框
	private TextView mTvSex = null;// 性别选择
	private EditText mEditPhone = null;// 手机号码输入框
	private TextView mBirthday = null;// 生日日期
	private CheckBox mChooseLunar = null;// 农历/公历选择
	private EditText mEditCity = null;// 城市输入框
	private EditText mEditArea = null;// 区域输入框

	private SwitchCheckBox mSwitch = null;
	private ModuleTitleOther mTitleLayout;

	private String mHeadImgPath = null;
	private BirthFavorite mBirthFavorite = new BirthFavorite();

	private final static String INTENT_ACTION_GET = "com.suiding.info_from_contact";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_addbirthday);

		mTitleLayout = new ModuleTitleOther(this);
		mTitleLayout.setTitle(R.string.title_activity_birthdayadd);
		mTitleLayout.setFunction(ModuleTitleOther.FUNCTION_OK);
		mTitleLayout.setOnGoBackListener(this);
		mTitleLayout.setOnOkListener(this);

		mUploadHead = findViewById(R.id.addbirthday_uploadedhead);
		mIvHeadImg = (ImageView) findViewById(R.id.addbirthday_headimg);
		mEditName = (EditText) findViewById(R.id.addbirthday_et_name);
		mTvSex = (TextView) findViewById(R.id.addbirthday_choosesex);
		mEditPhone = (EditText) findViewById(R.id.addbirthday_et_phone);
		mBirthday = (TextView) findViewById(R.id.addbirthday_birthday);
		mEditCity = (EditText) findViewById(R.id.addbirthday_et_city);
		mEditArea = (EditText) findViewById(R.id.addbirthday_et_area);
		mChooseLunar = (CheckBox) findViewById(R.id.addbirthday_cb_chooselunar);
		mSwitch = new SwitchCheckBox(mChooseLunar);

		mSwitch.setValueId(true, R.string.addbirthday_lunar);
		mSwitch.setValueId(false, R.string.addbirthday_solar);
		mSwitch.setOnCheckedChangeListener(this);

		mUploadHead.setOnClickListener(this);
		mEditName.setOnClickListener(this);
		mTvSex.setOnClickListener(this);
		mEditPhone.setOnClickListener(this);
		mBirthday.setOnClickListener(this);
		mChooseLunar.setOnClickListener(this);
		mEditCity.setOnClickListener(this);
		mEditArea.setOnClickListener(this);

		this.initializeBirthInfo();

		// get information from the contact
		this.getInfoFromContact();

		this.bindBirthInfo();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case ModuleTitleOther.ID_GOBACK:
			this.finish();
			break;
		case ModuleTitleOther.ID_OK:
			this.addBirthdayFavorite();
			break;
		case R.id.addbirthday_birthday:
			Calendar canlender = Calendar.getInstance();
			canlender.setTime(mBirthFavorite.Birth);
			int year = canlender.get(Calendar.YEAR);
			int month = canlender.get(Calendar.MONTH);
			int day = canlender.get(Calendar.DAY_OF_MONTH);
			Dialog tDialog = new DatePickerDialog(this, this, year, month, day);
			tDialog.show();
			tDialog.setCancelable(true);
			break;
		case R.id.addbirthday_choosesex:
			// 弹出性别切换对话框
			Builder dialog = new Builder(this);
			dialog.setTitle("请选择性别");
			dialog.setIcon(android.R.drawable.ic_dialog_info);
			dialog.setSingleChoiceItems(new String[] { "男", "女" },
					mBirthFavorite.Sex ? 0 : 1, this);
			dialog.show();
			break;
		case R.id.addbirthday_uploadedhead:
			// 调用系统选择图片并剪辑操作
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
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

		}
	}

	@Override
	public void onCheckedChanged(CompoundButton button, boolean checked) {
		// TODO Auto-generated method stub

	}

	private void addBirthdayFavorite() {
		// TODO Auto-generated method stub
		try {
			int task = isAdded ? AddBirthdayTask.TASK_EDIT
					: AddBirthdayTask.TASK_ADD;
			this.readBirthInfo();
			this.postTask(new AddBirthdayTask(task));
			this.showProgressDialog("正在提交数据...", true);
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}

	}

	private void readBirthInfo() throws Exception {
		// TODO Auto-generated method stub
		mBirthFavorite.Name = mEditName.getText().toString();
		if (mBirthFavorite.Name.equals("")) {
			throw new Exception("请输入昵称！");
		}
		mBirthFavorite.PhoneNo = mEditPhone.getText().toString();
		mBirthFavorite.City = mEditCity.getText().toString();
		mBirthFavorite.Area = mEditArea.getText().toString();
		mBirthFavorite.IsLunarCalendar = mChooseLunar.isChecked();

	}

	private void initializeBirthInfo() {
		// TODO Auto-generated method stub
		mType = ExtraUtil.getExtraInt(EXTRA_TYPE, ADD);
		if (mType == EDIT) {
			isAdded = true;
			Object data = ExtraUtil.getExtra(EXTRA_DATA, null);
			mBirthFavorite = (BirthFavorite) data;
			mTitleLayout.setTitle(R.string.title_activity_birthdayedit);
		} else {
			// 性别预设为男士
			mBirthFavorite.Sex = true;
			mBirthFavorite.User_ID = SuidingApp.getLoginUser().getID();
		}
	}

	private void bindBirthInfo() {
		// TODO Auto-generated method stub
		mEditName.setText(mBirthFavorite.Name);
		mEditPhone.setText(mBirthFavorite.PhoneNo);
		mEditCity.setText(mBirthFavorite.City);
		mEditArea.setText(mBirthFavorite.Area);
		mChooseLunar.setChecked(mBirthFavorite.IsLunarCalendar);
		mBirthday.setText(DateFormatUtil.format("yyyy-MM-dd",
				mBirthFavorite.Birth));
		mTvSex.setText(mBirthFavorite.Sex ? R.string.sex_male : R.string.sex_female);

		ImageService.bindImage(mBirthFavorite.HeadImg, mIvHeadImg,
				ImageFolderEnum.HEAD_USER, ImageSize.SMALLEST,
				R.drawable.image_person);
	}

	/**
	 * get information from the contact
	 * 
	 * @author jzhao
	 */
	private void getInfoFromContact() {
		String intentAction = getIntent().getAction();
		if (intentAction != null && INTENT_ACTION_GET.equals(intentAction)) {
			mBirthFavorite.Name = getIntent().getStringExtra("name").toString();
			mBirthFavorite.PhoneNo = getIntent().getStringExtra("phoneNum")
					.toString();
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		mBirthFavorite.Sex = (which == 0);
		mTvSex.setText(which == 0 ? R.string.sex_male : R.string.sex_female);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// TODO Auto-generated method stub
		Calendar calendar = new GregorianCalendar(year, month, day);
		mBirthFavorite.Birth = calendar.getTime();
		mBirthday.setText(DateFormatUtil.format("yyyy-MM-dd",
				mBirthFavorite.Birth));
	}

	@Override
	protected final void onActivityResult(int requestCode, int resultCode,
			Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_PHOTO) {
				if (data != null) {
					Bitmap headimg = data.getParcelableExtra("data");
					try {
						File cachepath = ImageCaches.getInstance(this)
								.getCachePath();
						mHeadImgPath = cachepath.getAbsolutePath()
								+ File.separatorChar + "birthheagimage.jpg";

						FileOutputStream output = new FileOutputStream(
								mHeadImgPath);
						if (headimg.compress(CompressFormat.JPEG, 100, output)) {
							mIvHeadImg.setImageBitmap(headimg);
						}
						output.flush();
						output.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();//handled
						AppExceptionHandler.handler(e, "添加生日获取图片错误");
						mHeadImgPath = null;
						showToastLong("获取图片错误："+e.getMessage());
					}
				}
			}
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		AddBirthdayTask task = (AddBirthdayTask) msg.obj;
		if (task.mResult == TaskBase.RESULT_FINISH) {
			if (task.mTask == AddBirthdayTask.TASK_HEADIMG) {
				hideProgressDialog();
				this.onFinish();
			} else {
				if (mHeadImgPath == null) {
					hideProgressDialog();
					this.onFinish();
				} else {
					task.mTask = AddBirthdayTask.TASK_HEADIMG;
					postTask(task);
				}
			}
		} else if (task.mResult == TaskBase.RESULT_FAIL) {
			if (task.mTask == AddBirthdayTask.TASK_HEADIMG) {
				isAdded = true;
				Toast.makeText(this, "网络不给力啊，上传头像失败~",
						Toast.LENGTH_LONG).show();
			} else {
				//Toast.makeText(this, task.mErrors, Toast.LENGTH_LONG).show();
			}
			hideProgressDialog();
		}
		return false;
	}

	private void onFinish() {
		// TODO Auto-generated method stub
		String tip = "编辑成功！";
		if (mType == EDIT) {
			ExtraUtil.putExtra(EXTRA_DATA, mBirthFavorite);
			this.setResult(RESULT_OK);
		} else {
			tip = "添加成功！";
		}
		Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
		this.finish();
	}

	private class AddBirthdayTask extends TaskBase {

		private static final int TASK_ADD = 10;
		private static final int TASK_EDIT = 11;
		private static final int TASK_HEADIMG = 12;

		public AddBirthdayTask(int task) {
			super(new Handler(BirthdayAddActivity.this), task);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onWorking(Message tMessage) throws Exception {
			// TODO Auto-generated method stub
			Consumer user = SuidingApp.getLoginUser();
			BirthdayEntity entity = new BirthdayEntity(mBirthFavorite);
			if (user != null) {
				IBirthFavoriteDomain domain = DomainFactory
						.getBirthFavoriteDomain();
				BirthdayEntityDao dao = new BirthdayEntityDao(getBaseContext());
				switch (mTask) {
				case TASK_ADD:
					domain.Insert(mBirthFavorite);
					dao.add(entity);
					break;
				case TASK_EDIT:
					domain.Update(mBirthFavorite);
					dao.update(entity);
					break;
				case TASK_HEADIMG:
					domain.uploadHeadImg(mBirthFavorite, mHeadImgPath);
					dao.update(new BirthdayEntity(mBirthFavorite));
					break;
				}
			}

		}
	}
}
