package com.suiding.adapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.bean.ContactBean;

/**
 * the contact adapter
 * @author jzhao
 *
 */
public class ContactAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<ContactBean> list;
	private String[] sections;
	private Context ctx;
	
	public ContactAdapter(Context context, List<ContactBean> list, HashMap<String, Integer> alpha) {
		
		this.ctx = context;
		this.inflater = LayoutInflater.from(context);
		this.list = list; 
		this.sections = new String[list.size()];
		
		Set<String> sectionLetters = alpha.keySet();
		ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
		Collections.sort(sectionList);
		sections = new String[sectionList.size()];
		sectionList.toArray(sections);
		
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void remove(int position){
		list.remove(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listitem_contact, null);
			holder = new ViewHolder();
			holder.qcb = (QuickContactBadge) convertView.findViewById(R.id.qcb);
			holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.number = (TextView) convertView
					.findViewById(R.id.number);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		ContactBean cb = list.get(position);
		String name = cb.getDisplayName();
		String number = cb.getPhoneNum();
		holder.name.setText(name);
		holder.number.setText(number);
	//	holder.qcb.assignContactUri(Contacts.getLookupUri(cb.getContactId(), cb.getLookUpKey()));
		if(0 == cb.getPhotoId()){
			holder.qcb.setImageResource(R.drawable.image_person);
		}else{
			Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, cb.getContactId());
			InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(ctx.getContentResolver(), uri); 
			Bitmap contactPhoto = BitmapFactory.decodeStream(input);
			holder.qcb.setImageBitmap(contactPhoto);
		}
		
		String currentStr = getAlpha(cb.getSortKey());
		
		String previewStr = (position - 1) >= 0 ? getAlpha(list.get(position - 1).getSortKey()) : " ";
		
		
		if (!previewStr.equals(currentStr)) { 
			holder.alpha.setVisibility(View.VISIBLE);
			holder.alpha.setText(currentStr);
		} else {
			holder.alpha.setVisibility(View.GONE);
		}
		return convertView;
	}
	
	public static class ViewHolder {
		QuickContactBadge qcb;
		TextView alpha;
		TextView name;
		TextView number;
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

}
