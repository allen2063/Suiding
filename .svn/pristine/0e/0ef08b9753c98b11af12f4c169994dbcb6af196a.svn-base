package com.suiding.adapter;

import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.suiding.activity.AlbumActivity;
import com.suiding.activity.R;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.application.ImageService;
import com.suiding.constant.ImageFolderEnum;
import com.suiding.constant.ImageSize;
import com.suiding.layoutbind.framework.ILayoutItem;
import com.suiding.model.Coupon;
import com.suiding.model.Photo;
import com.suiding.model.StoreBase;
import com.suiding.service.FileService;
import com.suiding.util.DateFormatUtil;
import com.suiding.util.ExtraUtil;

/**
 * TwitterListAdapter
 * 
 * @author SCWANG
 * 
 */
public class TwitterListAdapter extends ListAdapterBase {

	public TwitterListAdapter(Context context, List<? extends Object> ltdata) {
		super(context, ltdata);
	}

	@Override
	protected ILayoutItem getItemLayout() {
		// TODO Auto-generated method stub
		return new TwitterListItem();
	}

	/**
	 * TwitterListItem 酒店数据页面绑定类
	 * 
	 * @author SCWANG 不需要访问上层数据的内部类都应该加上 static 减小系统开支 TwitterListItem
	 *         不只是简单是存储视图的控件 还负责 控件与数据的绑定
	 */
	private static class TwitterListItem implements ILayoutItem,
			OnClickListener {
		public TextView mTvTime = null;
		public TextView mTvTitle = null;
		public TextView mTvOrder = null;
		public TextView mTvDuration = null;
		public TextView mTvDescription = null;
		public ImageView mIvHeadImage = null;
		public ImageView mIvImage1 = null;
		public ImageView mIvImage2 = null;
		public ImageView mIvImage3 = null;

		public Coupon mCoupon = null;

		/**
		 * 从视图中取出控件
		 * 
		 * @param view
		 */
		public void Handle(View view) {
			mTvTitle = (TextView) view
					.findViewById(R.id.listitem_twitter_title);
			mIvHeadImage = (ImageView) view
					.findViewById(R.id.listitem_twitter_headimg);
			mTvDescription = (TextView) view
					.findViewById(R.id.listitem_twitter_describe);
			mTvDuration = (TextView) view
					.findViewById(R.id.listitem_twitter_duration);
			mTvOrder = (TextView) view
					.findViewById(R.id.listitem_twitter_order);
			mTvTime = (TextView) view.findViewById(R.id.listitem_twitter_time);
			mIvImage1 = (ImageView) view
					.findViewById(R.id.listitem_twitter_image_1);
			mIvImage2 = (ImageView) view
					.findViewById(R.id.listitem_twitter_image_2);
			mIvImage3 = (ImageView) view
					.findViewById(R.id.listitem_twitter_image_3);
		}

		/**
		 * 将数据绑定到控件显示
		 * 
		 * @param tData
		 */
		public void Binding(Object data) {
			if (data instanceof Coupon) {
				Coupon tData = (Coupon) data;
				mTvTime.setText(DateFormatUtil.formatTime(tData.Date));
				mTvTitle.setText(tData.Name);
				mTvDescription.setText(tData.Content);
				mTvDuration.setText(DateFormatUtil.formatDuration(tData.BegDate,tData.EndDate));

				mCoupon = tData;
				// 如果图片地址有效加载图片信息
				if (tData.StoreBase != null) {
					mTvTitle.setText(tData.StoreBase.Name);
					if (tData.Content.length() < tData.Name.length()) {
						if (tData.Name.length() < 5) {
							mTvDescription.setText(tData.StoreBase.Abstract);
						} else {
							mTvDescription.setText(tData.Name);
						}
					}
					mIvHeadImage.setOnClickListener(this);
					ImageService.bindImage(tData.StoreBase.HeadImg, mIvHeadImage,
							ImageFolderEnum.HEAD_STORE, ImageSize.SMALLEST);
				} else {
					mIvHeadImage.setImageBitmap(ImageService.getImageNotFind());
				}

				if (tData.OrderNumber > 0) {
					mTvOrder.setText(tData.OrderNumber + "人已订");
					mTvOrder.setVisibility(View.VISIBLE);
				} else {
					mTvOrder.setVisibility(View.GONE);
				}

				if (tData.Photos.size() > 0) {
					Photo photo = tData.Photos.get(0);
					ImageService.bindImage(photo.Url, mIvImage1,
							ImageFolderEnum.PHOTO_PRODUCT, ImageSize.SMALLEST);
					mIvImage1.setVisibility(View.VISIBLE);
					mIvImage1.setOnClickListener(this);
				} else {
					mIvImage1.setVisibility(View.GONE);
				}

				if (tData.Photos.size() > 1) {
					Photo photo = tData.Photos.get(1);
					ImageService.bindImage(photo.Url, mIvImage2,
							ImageFolderEnum.PHOTO_PRODUCT, ImageSize.SMALLEST);
					mIvImage2.setVisibility(View.VISIBLE);
					mIvImage2.setOnClickListener(this);
				} else {
					mIvImage2.setVisibility(View.GONE);
				}

				if (tData.Photos.size() > 2) {
					Photo photo = tData.Photos.get(2);
					ImageService.bindImage(photo.Url, mIvImage3,
							ImageFolderEnum.PHOTO_PRODUCT, ImageSize.SMALLEST);
					mIvImage3.setVisibility(View.VISIBLE);
					mIvImage3.setOnClickListener(this);
				} else {
					mIvImage3.setVisibility(View.GONE);
				}
			}
		}

		@Override
		public int getLayoutId() {
			// TODO Auto-generated method stub
			return R.layout.listitem_twitter;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == mIvHeadImage){
				StoreBase store = mCoupon.StoreBase;
	    		Context context = v.getContext();
	    		String headimage = FileService.getLocalUrl(store.HeadImg,
	    					ImageFolderEnum.HEAD_STORE, ImageSize.SMALLEST);
	    		ExtraUtil.putExtra(AlbumActivity.EXTRA_FORID, UUID.randomUUID());
	    		ExtraUtil.putExtra(AlbumActivity.EXTRA_NAME, store.Name);
	    		ExtraUtil.putExtra(AlbumActivity.EXTRA_DESCRIBE, store.Abstract);
	    		ExtraUtil.putExtra(AlbumActivity.EXTRA_HEADURL, store.HeadImg);
	    		ExtraUtil.putExtra(AlbumActivity.EXTRA_SINGLE_URL, store.HeadImg);
	    		ExtraUtil.putExtra(AlbumActivity.EXTRA_SINGLE_SMALL, headimage);
	    		ExtraUtil.putExtra(AlbumActivity.EXTRA_SINGLE_FOLDER,ImageFolderEnum.HEAD_STORE);
	    		context.startActivity(new Intent(context, AlbumActivity.class));	
			}else{
				int index = 0;
				if(v == mIvImage2){
					index = 1;
				}else if(v == mIvImage3){
					index = 2;
				}
				Context context = v.getContext();
				String headimage = v.toString();
				ExtraUtil.putExtra(AlbumActivity.EXTRA_INDEX, index);
				ExtraUtil.putExtra(AlbumActivity.EXTRA_LIST, mCoupon.Photos);
				ExtraUtil.putExtra(AlbumActivity.EXTRA_NAME, mTvTitle.getText().toString());
				ExtraUtil.putExtra(AlbumActivity.EXTRA_DESCRIBE, mTvDescription.getText().toString());
				ExtraUtil.putExtra(AlbumActivity.EXTRA_HEADURL, headimage);
				ExtraUtil.putExtra(AlbumActivity.EXTRA_TYPE,AlbumActivity.TYPE_PRODUCT);
				context.startActivity(new Intent(context, AlbumActivity.class));
			}
		}
	}
}
