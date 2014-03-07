package com.suiding.layoutbind;

import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
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

public class ModuleRsrmenuItem implements ILayout, ILayoutItem, OnClickListener {
	
	private View mLayout = null;
	private TextView mTvName = null;
	private TextView mTvOldPrice = null;
	private TextView mTvNewPrice = null;
	private ImageView mTvHeadimg = null;

	private PdtItem mPdtItem = null;
	private boolean mIsValid = false;

	public ModuleRsrmenuItem()
	{
		
	}
	
	public ModuleRsrmenuItem(Pageable page) {
		// TODO Auto-generated constructor stub
		mTvName = page.findTextViewById(R.id.rsrmenulistitem_name);
		mTvOldPrice = page.findTextViewById(R.id.rsrmenulistitem_oldprice);
		mTvNewPrice = page.findTextViewById(R.id.rsrmenulistitem_newprice);
		mTvHeadimg = page.findImageViewById(R.id.rsrmenulistitem_headimg);
		initialize();
	}

	public ModuleRsrmenuItem(Pageable page, PdtItem item) {
		// TODO Auto-generated constructor stub
		this(page);
		this.setPdtItem(item);
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

	public void setPdtItem(PdtItem item) {
		// TODO Auto-generated method stub
		if (mIsValid && item != null) {
			mPdtItem = item;
			mTvName.setText(item.Name);
			mTvOldPrice.setText(String.format("%.0fԪ", item.Price));
			mTvNewPrice.setText(String.format("%.0fԪ", item.NowPrice));
			ImageService.bindImage(item.HeadImg, mTvHeadimg,
					ImageFolderEnum.HEAD_PRODUCT, ImageSize.SMALLEST);
			
			mTvHeadimg.setOnClickListener(this);
		}
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
		mTvName = (TextView) view.findViewById(R.id.rsrmenulistitem_name);
		mTvOldPrice = (TextView) view
				.findViewById(R.id.rsrmenulistitem_oldprice);
		mTvNewPrice = (TextView) view
				.findViewById(R.id.rsrmenulistitem_newprice);
		mTvHeadimg = (ImageView) view
				.findViewById(R.id.rsrmenulistitem_headimg);
		initialize();
	}

	@Override
	public void Binding(Object data) {
		// TODO Auto-generated method stub
		if(data instanceof PdtItem){
			setPdtItem((PdtItem)data);
		}
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.module_rsrmenulistitem;
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
		ExtraUtil.putExtra(AlbumActivity.EXTRA_HEADURL, headimage);
		ExtraUtil.putExtra(AlbumActivity.EXTRA_SINGLE_URL, item.HeadImg);
		ExtraUtil.putExtra(AlbumActivity.EXTRA_SINGLE_SMALL, headimage);
		ExtraUtil.putExtra(AlbumActivity.EXTRA_SINGLE_FOLDER,ImageFolderEnum.HEAD_PRODUCT);
		context.startActivity(new Intent(context, AlbumActivity.class));
	}
}
