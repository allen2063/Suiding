package com.suiding.layoutbind;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.application.ImageService;
import com.suiding.constant.ImageFolderEnum;
import com.suiding.constant.ImageSize;
import com.suiding.layoutbind.ModuleSetNumber.OnNumberChangedListener;
import com.suiding.layoutbind.framework.ILayoutItem;
import com.suiding.model.RsrMenu;
import com.suiding.model.RsrOrderItem;

public class ModuleReserveMenuItem implements OnClickListener,
		OnNumberChangedListener,ILayoutItem {
	public interface SelectorItemListener {
		void onItemRemove(ModuleReserveMenuItem item);
		void onCountChanged(ModuleReserveMenuItem item,int count);
	}

	public View mLayout = null;
	public TextView mTvPrice = null;
	public TextView mBtDelete = null;
	private ImageView mIvHeadimg = null;

	public RsrOrderItem mOrderItem = null;

	private ModuleSetNumber mSetNamber = null;
	private SelectorItemListener mListener = null;

	private ModuleReserveMenuItem(Pageable page) {
		// TODO Auto-generated constructor stub
		mSetNamber = new ModuleSetNumber(page);

		mTvPrice = page.findTextViewById(R.id.reserve_item_totalprice);
		mBtDelete = page.findTextViewById(R.id.reserve_item_bt_delete);
		mIvHeadimg = page.findImageViewById(R.id.reserve_item_headimg);
		mLayout =  (View) page.findViewById(R.id.reserve_item_layout).getParent();
		mBtDelete.setOnClickListener(this);
		mSetNamber.setOnNumberChangedListener(this);
	}

	private ModuleReserveMenuItem(View view) {
		// TODO Auto-generated constructor stub
		mSetNamber = new ModuleSetNumber(view);
		mTvPrice = (TextView) view.findViewById(R.id.reserve_item_totalprice);
		mBtDelete = (TextView) view.findViewById(R.id.reserve_item_bt_delete);
		mIvHeadimg = (ImageView) view.findViewById(R.id.reserve_item_headimg);
		mLayout =  (View) view.findViewById(R.id.reserve_item_layout).getParent();
		mBtDelete.setOnClickListener(this);
		mSetNamber.setOnNumberChangedListener(this);
	}

	public ModuleReserveMenuItem(Pageable page,
			SelectorItemListener listener, RsrOrderItem item) {
		// TODO Auto-generated constructor stub
		this(page);
		mOrderItem = item;
		mListener = listener;
		initialize();
	}

	public ModuleReserveMenuItem(View view, SelectorItemListener listener,
			RsrOrderItem item) {
		// TODO Auto-generated constructor stub
		this(view);
		mOrderItem = item;
		mListener = listener;
		initialize();
	}
	
	public void setmSelectorItemListener(SelectorItemListener mListener) {
		this.mListener = mListener;
	}

	public ModuleReserveMenuItem() {
		// TODO Auto-generated constructor stub
	}

	private void initialize() {
		// TODO Auto-generated method stub
		RsrMenu tRsrMenu = mOrderItem.RsrMenu;
		setPrice(mOrderItem.Count * mOrderItem.RsrMenu.NowPrice);
		mSetNamber.setName(String.format("%s(%.0fԪ)", tRsrMenu.Name,tRsrMenu.NowPrice));
		ImageService.bindImage(tRsrMenu.HeadImg, mIvHeadimg, ImageFolderEnum.HEAD_PRODUCT, ImageSize.SMALLEST);
	}

	@Override
	public void onNumberChanged(ModuleSetNumber setnumber, int number) {
		// TODO Auto-generated method stub
		mOrderItem.Count = number;
		setPrice(number * mOrderItem.RsrMenu.NowPrice);
		if(mListener != null){
			mListener.onCountChanged(this, number);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.reserve_item_bt_delete:
			if (mListener != null) {
				mListener.onItemRemove(this);
			}
			break;
		}
	}

	public void setName(String name) {
		// TODO Auto-generated constructor stub
		mSetNamber.setName(name);
	}

	public void setPrice(double price) {
		// TODO Auto-generated constructor stub
		mTvPrice.setText(String.format("%.0fԪ", price));
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
	public void Handle(View view) {
		// TODO Auto-generated method stub
		mSetNamber = new ModuleSetNumber(view);
		mTvPrice = (TextView) view.findViewById(R.id.reserve_item_totalprice);
		mBtDelete = (TextView) view.findViewById(R.id.reserve_item_bt_delete);
		mIvHeadimg = (ImageView) view.findViewById(R.id.reserve_item_headimg);
		mLayout =  (View) view.findViewById(R.id.reserve_item_layout).getParent();
		mBtDelete.setOnClickListener(this);
		mSetNamber.setOnNumberChangedListener(this);
	}

	@Override
	public void Binding(Object data) {
		// TODO Auto-generated method stub
		if(data instanceof RsrOrderItem){
			mOrderItem = (RsrOrderItem)data;
			RsrMenu tRsrMenu = mOrderItem.RsrMenu;
			setPrice(mOrderItem.Count * mOrderItem.RsrMenu.NowPrice);
			mSetNamber.setName(String.format("%s(%.0fԪ)", tRsrMenu.Name,tRsrMenu.NowPrice));
			ImageService.bindImage(tRsrMenu.HeadImg, mIvHeadimg, ImageFolderEnum.HEAD_PRODUCT, ImageSize.SMALLEST);
		}
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.reserve_menu_item;
	}

}
