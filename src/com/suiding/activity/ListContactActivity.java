package com.suiding.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.adapter.ContactAdapter;
import com.suiding.bean.ContactBean;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.widget.SpellLetterView;
import com.suiding.widget.SpellLetterView.OnTouchingLetterChangedListener;

/**
 * ��ϵ��activity
 * 
 * @author jzhao
 * 
 */
public class ListContactActivity extends ActivityBase implements OnItemClickListener,
		OnTouchingLetterChangedListener {

	private static final String INTENT_ACTION = "com.suiding.info_from_contact";

	private ModuleTitleOther mLayoutTitle;
	private ContactAdapter mAdapter;
	private ListView mLvPerson;
	private List<ContactBean> list;
	private AsyncQueryHandler asyncQuery;
	private Map<String, ContactBean> contactIdMap = null;
	private SpellLetterView mLetterView; // ���ٶ�λ��
	private HashMap<String, Integer> mLVIndexer = new HashMap<String, Integer>();// ���ٶ�λ����
	private List<String> mLVHeadList = new ArrayList<String>();// ���ٶ�λ����ĸ�б�

	private Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;// ��ϵ�˵�Uri

	private String[] projection = { ContactsContract.CommonDataKinds.Phone._ID,
			ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
			ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key",
			ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
			ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
			ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY }; // ��ѯ����

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_listcontact);

		mLvPerson = (ListView) findViewById(R.id.contact_list);
		mLetterView = (SpellLetterView) findViewById(R.id.rightCharacterListView);
		mLetterView.setData(mLVHeadList);
		mLetterView.setOnTouchingLetterChangedListener(this);

		asyncQuery = new MyAsyncQueryHandler(getContentResolver(), this);

		mLayoutTitle = new ModuleTitleOther(this);
		mLvPerson.setOnItemClickListener(this);

		mLayoutTitle.setTitle(R.string.title_activity_contact);

		initialize();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.initialize();
	}

	@Override
	public void onItemClick(AdapterView<?> absview, View view, int index,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, BirthdayAddActivity.class);
		intent.setAction(INTENT_ACTION);
		Long photoId = list.get(index).getPhotoId();
		String phoneNum = list.get(index).getPhoneNum().toString();
		String displayName = list.get(index).getDisplayName().toString();
		intent.putExtra("name", displayName);
		intent.putExtra("phoneNum", phoneNum);
		intent.putExtra("photoId", photoId);
		startActivity(intent);
		this.finish();
	}

	private void initialize() {
		asyncQuery.startQuery(0, null, uri, projection, null, null,
				"sort_key COLLATE LOCALIZED asc"); // ����sort_key�����ѯ
	}

	/**
	 * ���ݿ��첽��ѯ��AsyncQueryHandler
	 * 
	 * @author jzhao
	 * 
	 */
	public static class MyAsyncQueryHandler extends AsyncQueryHandler {

		public ListContactActivity mActivity = null;

		public MyAsyncQueryHandler(ContentResolver cr, ListContactActivity activity) {
			super(cr);
			mActivity = activity;
		}

		/**
		 * ��ѯ�����Ļص�����
		 */
		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			if (cursor != null && cursor.getCount() > 0) {

				mActivity.contactIdMap = new HashMap<String, ContactBean>();

				mActivity.list = new ArrayList<ContactBean>();
				cursor.moveToFirst();
				for (int i = 0; i < cursor.getCount(); i++) {
					cursor.moveToPosition(i);
					String name = cursor.getString(1);
					String number = cursor.getString(2);
					String sortKey = cursor.getString(3);
					int contactId = cursor.getInt(4);
					Long photoId = cursor.getLong(5);
					String lookUpKey = cursor.getString(6);

					if (mActivity.contactIdMap.containsKey(contactId)) {

					} else {

						ContactBean cb = new ContactBean();
						cb.setDisplayName(name);
						// if (number.startsWith("+86")) {//
						// ȥ��������й����������־�����������û��Ӱ�졣
						// cb.setPhoneNum(number.substring(3));
						// } else {
						cb.setPhoneNum(number);
						// }
						cb.setSortKey(sortKey);
						cb.setContactId(contactId);
						cb.setPhotoId(photoId);
						cb.setLookUpKey(lookUpKey);
						mActivity.list.add(cb);

						mActivity.contactIdMap.put(contactId + "", cb);
					}
				}
				if (mActivity.list.size() > 0) {
					mActivity.setAdapter(mActivity.list);

				} else {
					Toast.makeText(mActivity, "����ͨѶ¼�ǿյ�Ŷ��", Toast.LENGTH_LONG)
							.show();
				}
			}
		}

	}

	private void setAdapter(List<ContactBean> list) {
		setListIndexer();
		mLetterView.setData(mLVHeadList);

		mAdapter = new ContactAdapter(this, list, mLVIndexer);
		mAdapter.notifyDataSetChanged();
		mLvPerson.setAdapter(mAdapter);
		// alpha.init(ContactActivity.this);
		// alpha.setListView(mLvPerson);
		// alpha.setHight(alpha.getHeight());
		// mLetterView.setVisibility(View.VISIBLE);
	}

	private void setListIndexer() {

		for (int i = 0; i < list.size(); i++) {
			String name = getAlpha(list.get(i).getSortKey());
			if (!mLVIndexer.containsKey(name)) {
				mLVIndexer.put(name, i);
				mLVHeadList.add(name);
			}
		}
	}

	private String getAlpha(String str) {
		if (str == null || str.trim().length() == 0) {
			return "#";
		}
		char c = str.trim().substring(0, 1).charAt(0);

		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase(Locale.ENGLISH);
		} else {
			return "#";
		}
	}

	@Override
	public void onTouchingLetterChanged(String ch, int action) {
		// TODO Auto-generated method stub
		if (action == MotionEvent.ACTION_UP) {
			return;
		}

		if (!"#".equals(ch))// ����
		{
			ch = ch.toUpperCase(Locale.ENGLISH);

			if (mLVIndexer.containsKey(ch)) {
				int pos = mLVIndexer.get(ch);
				if (mLvPerson.getHeaderViewsCount() > 0) {
					// ��ֹListView�б�������������û�С�
					mLvPerson.setSelectionFromTop(
							pos + mLvPerson.getHeaderViewsCount(), 0);
				} else {
					mLvPerson.setSelectionFromTop(pos, 0);
				}
			}
		}
	}
}
