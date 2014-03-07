package com.suiding.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.adapter.AlbumPagerAdapter;
import com.suiding.application.AppExceptionHandler;
import com.suiding.constant.ImageFolderEnum;
import com.suiding.domain.IPhotoDomain;
import com.suiding.model.Page;
import com.suiding.model.Photo;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.ExtraUtil;
import com.suiding.util.UUIDUtil;

public class AlbumActivity extends ActivityBase implements Callback,
		OnPageChangeListener, OnTouchListener {

	// ����һ���Ѵ��ڵ�����б���������������б�
	public static final String EXTRA_LIST = "EXTRA_LIST"; // �б�
	public static final String EXTRA_INDEX = "EXTRA_INDEX";// Ĭ�ϲ鿴
	// ����һ�����ķ��棨�������������ݣ�
	public static final String EXTRA_FORID = "EXTRA_FORID";
	public static final String EXTRA_HEADURL = "EXTRA_HEADURL";
	public static final String EXTRA_TYPE = "EXTRA_TYPE";
	public static final String EXTRA_NAME = "EXTRA_NAME";
	public static final String EXTRA_DESCRIBE = "EXTRA_DESCRIBE";

	// ����һ�ŵ�����ͼƬ��������ظ���ͼ��
	// ͼƬURL�����ڼ��ظ���ͼ��
	public static final String EXTRA_SINGLE_URL = "EXTRA_SINGLE_URL";
	// СͼURL����������ʾԤ����
	public static final String EXTRA_SINGLE_SMALL = "EXTRA_SINGLE_SMALL";
	// ͼƬ�ļ���
	public static final String EXTRA_SINGLE_FOLDER = "EXTRA_SINGLE_FOLDER";

	public static final int TYPE_NULL = -1;
	public static final int TYPE_USER = ImageFolderEnum.PHOTO_USER;
	public static final int TYPE_STORE = ImageFolderEnum.PHOTO_STORE;
	public static final int TYPE_PRODUCT = ImageFolderEnum.PHOTO_PRODUCT;
	public static final int TYPE_OTHER = ImageFolderEnum.PHOTO_OHTER;

	private static final int PAGESIZE = 100;

	public TextView mTvTip = null;
	public TextView mTvName = null;
	public TextView mTvSize = null;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;

	private UUID AlbumID = null;
	private String mPhotoName = "�������";
	private String mDescribe = "���Ļ���������Ϣ���������";
	private Integer mPhotoType = TYPE_NULL;

	private Photo mHeader = null;
	private List<Photo> mltPhoto = new ArrayList<Photo>();

	private AlbumPagerAdapter mAdapter = null;

	@Override
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.album_main);
		mViewPager = findViewPagerById(R.id.pager);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setOnTouchListener(this);

		mTvTip = findTextViewById(R.id.album_tip);
		mTvName = findTextViewById(R.id.album_name);
		mTvSize = findTextViewById(R.id.album_size);

		Object list = ExtraUtil.getExtra(EXTRA_LIST, null);

		int index = 0;
		AlbumID = (UUID) ExtraUtil.getExtra(EXTRA_FORID, UUIDUtil.Empty);
		mPhotoType = (Integer) ExtraUtil.getExtra(EXTRA_TYPE, TYPE_NULL);
		mPhotoName = (String) ExtraUtil.getExtra(EXTRA_NAME, mPhotoName);
		mDescribe = (String) ExtraUtil.getExtra(EXTRA_DESCRIBE, mDescribe);
		String headurl = (String) ExtraUtil.getExtra(EXTRA_HEADURL, "null");
		String single = ExtraUtil.getExtraString(EXTRA_SINGLE_URL, null);

		if (single != null && single.length() > 0) {
			int folder = ExtraUtil.getExtraInt(EXTRA_SINGLE_FOLDER, 0);
			String small = ExtraUtil.getExtraString(EXTRA_SINGLE_SMALL, "");
			mPhotoType = folder;
			mHeader = new Photo(mPhotoName, single, mDescribe, small, AlbumID,
					AlbumID);
			mltPhoto.add(mHeader);
		} else if (list != null) {
			mltPhoto = (List<Photo>) list;
			index = ExtraUtil.getExtraInt(EXTRA_INDEX, 0);
			if(index >= mltPhoto.size()){
				index = 0;
			}
		} else if (AlbumID.equals(UUIDUtil.Empty) == false
				&& mPhotoType != TYPE_NULL && headurl.equals("null") == false) {
			mHeader = new Photo(mPhotoName, headurl, mDescribe, "", AlbumID,
					AlbumID);
			mltPhoto.add(mHeader);
			postTask(new LoadAlbumTask());
		} else {
			this.finish();
			return;
		}
		mAdapter = new AlbumPagerAdapter(this, mltPhoto, mPhotoType);
		mAdapter.setOnTouchListener(this);
		mViewPager.setAdapter(mAdapter);
		onPageSelected(0);
		mViewPager.setCurrentItem(index, false);
	}

	/**
	 * onTouchEvent ʵ��˫������ ��һ�ε����ʱ��
	 */
	private long mLastTouch = 0;
	private float mLastPosX = 0;
	private float mLastPosY = 0;
	private boolean mHandleing= false;

	@Override
	@SuppressLint("HandlerLeak")
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			mLastPosX = event.getRawX();
			mLastPosY = event.getRawY();
			mLastTouch = System.currentTimeMillis();
		} else if (action == MotionEvent.ACTION_MOVE) {
			float PosX = event.getRawX();
			float PosY = event.getRawY();
			float dPosX = PosX - mLastPosX;
			float dPosY = PosY - mLastPosY;
			double distance = Math.sqrt(dPosX*dPosX+dPosY*dPosY);
			if(distance > 20){
				mLastTouch = 0;
			}
		} else if (action == MotionEvent.ACTION_UP) {
			long current = System.currentTimeMillis();
			long dvalue = current - mLastTouch;
			if (dvalue < 200) {
				if(!mHandleing){
					mHandleing = true;
					Message msg = Message.obtain();
					msg.obj = mLastTouch;
					new Handler(){
						@Override
						public void handleMessage(Message msg) {
							// TODO Auto-generated method stub
							mHandleing = false;
							long last = (Long)msg.obj;
							if(last == mLastTouch){
								finish();
							}
						}
					}.sendMessageDelayed(msg, 300);
				}
			}
		}
		return false;
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		TaskBase tTask = (TaskBase) msg.obj;
		if (tTask.mResult == TaskBase.RESULT_FINISH) {
			if (msg.obj instanceof LoadAlbumTask) {
				LoadAlbumTask tLoadTask = (LoadAlbumTask) msg.obj;
				mAdapter.AddDataSet(tLoadTask.mltPhoto);
				onPageSelected(mViewPager.getCurrentItem());
			}
		} else {
			//Toast.makeText(this, tTask.mErrors, Toast.LENGTH_LONG).show();
		}
		return true;
	}

	private class LoadAlbumTask extends TaskBase {
		public List<Photo> mltPhoto = null;

		public LoadAlbumTask() {
			super(new Handler(AlbumActivity.this));
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onWorking(Message tMessage) throws Exception {
			// TODO Auto-generated method stub
			// PhotoEntityDao tDao = new PhotoEntityDao(getBaseContext());
			// List<PhotoEntity> ltEntity = tDao.getByForId(AlbumID);
			// if (ltEntity.size() > 0)
			// {
			// mltPhoto = PhotoEntity.toListPhoto(ltEntity);
			// return;
			// }
			IPhotoDomain tDomain = DomainFactory.getPhotoDomain();
			mltPhoto = tDomain.getListByForID(AlbumID, new Page(PAGESIZE, 0));
			try {
				for (int i = 0; i < mltPhoto.size(); i++) {
					if (mltPhoto.get(i).Url.equals(mHeader.Url)) {
						mltPhoto.remove(i);
						break;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.handler(e, "���ҳ�������ͬҳ�����");
			}
			// tDao.getUpdateForId(AlbumID,
			// PhotoEntity.listFormModel(mltPhoto));
		}

	}

	// arg0==1��ʱ���ʾ���ڻ�����
	// arg0==2��ʱ���ʾ��������ˣ�
	// arg0==0��ʱ���ʾʲô��û��������ͣ���ǡ�
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	// Ĭʾ��ǰһ��ҳ�滬������һ��ҳ���ʱ������ǰһ��ҳ�滬��ǰ���õİ취��
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	// currPage��Ĭʾ�㵱ǰѡ�е�ҳ�棬������������ҳ����ת��ϵ�ʱ�����õġ�
	@Override
	public void onPageSelected(int currPage) {
		// TODO Auto-generated method stub
		Photo photo = mltPhoto.get(currPage);
		if (photo.Describe != null && photo.Describe.length() > 0) {
			mTvTip.setText(photo.Describe);
		}else{
			mTvTip.setText(mDescribe);
		}
		if (photo.Name != null && photo.Name.length() > 0) {
			mTvName.setText(photo.Name);
		}else{
			mTvName.setText(mPhotoName);
		}
		mTvSize.setText((1 + currPage) + "/" + mltPhoto.size());
	}

}
