package com.suiding.adapter;

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.application.AddressService;
import com.suiding.application.ImageService;
import com.suiding.application.SuidingApp;
import com.suiding.constant.ImageFolderEnum;
import com.suiding.constant.ImageSize;
import com.suiding.layoutbind.framework.ILayoutItem;
import com.suiding.model.StoreBase;
import com.suiding.util.DistanceUtil;

public class ResultSearchListAdapter extends ListAdapterBase {

	public ResultSearchListAdapter(Context context,
			List<? extends Object> ltdata) {
		super(context, ltdata);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ILayoutItem getItemLayout() {
		// TODO Auto-generated method stub
		return new ResultSearchListItem();
	}

	private static class ResultSearchListItem implements ILayoutItem {

		private ImageView mTvHead;
		
		private TextView mTvTitle;
		private TextView mTvState;
		private TextView mTvPrice;
		private TextView mTvAddress;
		private TextView mTvDistance;
		private TextView mTvComment;
		private TextView mTvDescrip;

		private LinearLayout mLlFacility = null;
		
		@Override
		public void Handle(View view) {
			// TODO Auto-generated method stub
			mTvHead = (ImageView) view.findViewById(R.id.result_search_iv_head);
			mTvTitle = (TextView) view
					.findViewById(R.id.result_search_tv_title);
			mTvState = (TextView) view
					.findViewById(R.id.result_search_tv_state);
			mTvAddress = (TextView) view
					.findViewById(R.id.result_search_tv_address);
			mTvDistance = (TextView) view
					.findViewById(R.id.result_search_tv_distance);
			mTvComment = (TextView) view
					.findViewById(R.id.result_search_tv_comment);
			mTvPrice = (TextView) view
					.findViewById(R.id.result_search_tv_price);
			mTvDescrip = (TextView) view
					.findViewById(R.id.result_search_tv_descrip);
			mLlFacility = (LinearLayout)view.findViewById(R.id.result_search_facility);
		}

		@Override
		public void Binding(Object data) {
			// TODO Auto-generated method stub
			if (data instanceof StoreBase) {
				StoreBase tData = (StoreBase) data;
				mTvTitle.setText(tData.Name);
				mTvDescrip.setText(tData.Abstract);
				mTvPrice.setText(String.format("%.0f", tData.NowPrice));

				mTvState.setText("有房");
				mTvState.setVisibility(View.GONE);
				mLlFacility.setVisibility(View.GONE);

				Location location = SuidingApp.getApp().getFixedLocation();
				if(location != null && tData.Address != null){
					float distance = DistanceUtil.getDistance(location, tData.Address);
					mTvDistance.setText(String.format("%.1fkm",distance/1000));
					mTvDistance.setVisibility(View.VISIBLE);
				}else{
					mTvDistance.setVisibility(View.GONE);
				}
				
				if(tData.Scores > 0){
					mTvComment.setText(String.format("%.1f", tData.Scores));
					mTvComment.setVisibility(View.VISIBLE);
				}else{
					mTvComment.setVisibility(View.GONE);
				}
				
				// 如果图片地址有效加载图片信息
				ImageService.bindImage(tData.HeadImg, mTvHead,
						ImageFolderEnum.HEAD_STORE, ImageSize.SMALLEST);
				// 地址服务加载地址
				AddressService.bindAddress(tData.Address, mTvAddress);
			}
		}

		@Override
		public int getLayoutId() {
			// TODO Auto-generated method stub
			return R.layout.listitem_result_search;
		}

	}
}
