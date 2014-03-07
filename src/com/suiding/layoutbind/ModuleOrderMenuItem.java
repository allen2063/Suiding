package com.suiding.layoutbind;

import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.suiding.activity.AlbumActivity;
import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.application.ImageService;
import com.suiding.constant.ImageFolderEnum;
import com.suiding.constant.ImageSize;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.layoutbind.framework.ILayoutItem;
import com.suiding.model.PdtItem;
import com.suiding.service.FileService;
import com.suiding.util.ExtraUtil;
import com.suiding.util.UUIDUtil;

public class ModuleOrderMenuItem implements ILayout, ILayoutItem, OnClickListener, OnCheckedChangeListener {
	
	private View mLayout = null;
	private TextView mTvName = null;
	private TextView mTvOldPrice = null;
	private TextView mTvNewPrice = null;
	private ImageView mTvHeadimg = null;
	private CheckBox mTvChecked = null;

	private PdtItem mPdtItem = null;
	private boolean mIsValid = false;
	
	private OrderMenuItemListener mListener = null;
	
	public interface OrderMenuItemListener{
		void onPdtItemCheckedChanged(PdtItem item, boolean isChecked);
	}

	public ModuleOrderMenuItem()
	{
		
	}
	
	public ModuleOrderMenuItem(Pageable page) {
		// TODO Auto-generated constructor stub
		mTvName = page.findTextViewById(R.id.ordermenuitem_name);
		mTvOldPrice = page.findTextViewById(R.id.ordermenuitem_oldprice);
		mTvNewPrice = page.findTextViewById(R.id.ordermenuitem_newprice);
		mTvHeadimg = page.findImageViewById(R.id.ordermenuitem_headimg);
		mTvChecked = page.findCheckBoxById(R.id.ordermenuitem_checkbox);
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		if (mTvName != null) {
			mLayout = (View) mTvName.getParent();
			mIsValid = mLayout != null;
			if (mIsValid) {
				mTvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			}
		}
	}

	public ModuleOrderMenuItem(Pageable page, PdtItem item,boolean checked) {
		// TODO Auto-generated constructor stub
		this(page);
		this.setPdtItem(item,checked);
	}
	
	public void setOrderMenuItemListener(OrderMenuItemListener mListener) {
		this.mListener = mListener;
	}
	
	@Override
	public void onCheckedChanged(CompoundButton view, boolean isChecked) {
		// TODO Auto-generated method stub
		if(this.mListener != null){
			mListener.onPdtItemCheckedChanged(mPdtItem, isChecked);
		}
	}

	public void setPdtItem(PdtItem item,boolean checked) {
		// TODO Auto-generated method stub
		if (mIsValid) {
			mPdtItem = item;
			mTvName.setText(item.Name);
			mTvOldPrice.setText(String.format("%.0fԪ", item.Price));
			mTvNewPrice.setText(String.format("%.0fԪ", item.NowPrice));
			ImageService.bindImage(item.HeadImg, mTvHeadimg,
					ImageFolderEnum.HEAD_PRODUCT, ImageSize.SMALLEST);
			
			mTvHeadimg.setOnClickListener(this);
			mTvChecked.setChecked(checked);
			mTvChecked.setOnCheckedChangeListener(this);
		}
	}

	public UUID getID() {
		// TODO Auto-generated method stub
		if(mPdtItem == null){
			return UUIDUtil.Empty;
		}
		return mPdtItem.getID();
	}

	public void setChecked(boolean checked) {
		// TODO Auto-generated method stub
		mTvChecked.setOnCheckedChangeListener(null);
		mTvChecked.setChecked(checked);
		mTvChecked.setOnCheckedChangeListener(this);
	}
	
	public void setName(String name) {
		// TODO Auto-generated constructor stub
		mTvName.setText(name);
	}

	public void hide() {
		// TODO Auto-generated constructor stub
		mLayout.setVisibility(View.GONE);
	}

	public void show() {
		// TODO Auto-generated constructor stub
		mLayout.setVisibility(View.VISIBLE);
	}

	@Override
	public View getLayout() {
		// TODO Auto-generated method stub
		return mLayout;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return mIsValid;
	}

	@Override
	public void Handle(View view) {
		// TODO Auto-generated method stub
		mIsValid = false;
		mTvName = (TextView) view.findViewById(R.id.ordermenuitem_name);
		mTvOldPrice = (TextView) view
				.findViewById(R.id.ordermenuitem_oldprice);
		mTvNewPrice = (TextView) view
				.findViewById(R.id.ordermenuitem_newprice);
		mTvHeadimg = (ImageView) view
				.findViewById(R.id.ordermenuitem_headimg);
		mTvChecked = (CheckBox)view.findViewById(R.id.ordermenuitem_checkbox);
		initialize();
	}

	@Override
	public void Binding(Object data) {
		// TODO Auto-generated method stub
		if(data instanceof PdtItem){
			setPdtItem((PdtItem)data,false);
		}
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.module_ordermenu_item;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		PdtItem item = mPdtItem;
		Context context = v.getContext();
		String headimage = FileService.getLocalUrl(item.HeadImg,
					ImageFolderEnum.HEAD_PRODUCT, ImageSize.SMALLEST);
		ExtraUtil.putExtra(AlbumActivity.EXTRA_FORID, UUID.randomUUID());
		ExtraUtil.putExtra(AlbumActivity.EXTRA_NAME, item.Name);
		ExtraUtil.putExtra(AlbumActivity.EXTRA_DESCRIBE, item.Remark);
		ExtraUtil.putExtra(AlbumActivity.EXTRA_HEADURL, item.HeadImg);
		ExtraUtil.putExtra(AlbumActivity.EXTRA_SINGLE_URL, item.HeadImg);
		ExtraUtil.putExtra(AlbumActivity.EXTRA_SINGLE_SMALL, headimage);
		ExtraUtil.putExtra(AlbumActivity.EXTRA_SINGLE_FOLDER,ImageFolderEnum.HEAD_PRODUCT);
		context.startActivity(new Intent(context, AlbumActivity.class));
	}
}
