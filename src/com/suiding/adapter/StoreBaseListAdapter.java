package com.suiding.adapter;

import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suiding.activity.AlbumActivity;
import com.suiding.activity.R;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.application.AddressService;
import com.suiding.application.ImageService;
import com.suiding.application.SuidingApp;
import com.suiding.constant.ImageFolderEnum;
import com.suiding.constant.ImageSize;
import com.suiding.layoutbind.framework.ILayoutItem;
import com.suiding.model.StoreBase;
import com.suiding.service.FileService;
import com.suiding.util.DistanceUtil;
import com.suiding.util.ExtraUtil;


/**
 * StoreBaseListAdapter
 * 
 * @author SCWANG
 * 
 */
public class StoreBaseListAdapter extends ListAdapterBase
{

    public StoreBaseListAdapter(Context context, List<? extends Object> ltdata)
    {
        super(context, ltdata);
    }

    @Override
    protected ILayoutItem getItemLayout()
    {
        // TODO Auto-generated method stub
        return new StoreBaseListItem();
    }

    /**
     * StoreBaseListItem �Ƶ�����ҳ�����
     * 
     * @author SCWANG
     *         ����Ҫ�����ϲ����ݵ��ڲ��඼Ӧ�ü��� static ��Сϵͳ��֧
     *         StoreBaseListItem ��ֻ�Ǽ��Ǵ洢��ͼ�Ŀؼ� ������
     *         �ؼ������ݵİ�
     */
    private static class StoreBaseListItem implements ILayoutItem, OnClickListener
    {
        public TextView mTvTitle = null;
        public TextView mTvAdress = null;
        public TextView mTvCoupon = null;
        public TextView mTvStatus = null;
        public TextView mTvPrice = null;
        public TextView mTvProduct = null;
        public TextView mTvDiscard = null;
        public TextView mTvDescrip = null;
        public TextView mTvDistance = null;

        public ImageView mIvHeadImage = null;
        public ImageView mIvIconWifi = null;
        public ImageView mIvIconPark = null;

        public LinearLayout mLlCoupon = null;
        public LinearLayout mLlFacility = null;
        
        public StoreBase mStore = null;
        /**
         * ����ͼ��ȡ���ؼ�
         * 
         * @param view
         */
        public void Handle(View view)
        {
            mTvTitle = (TextView) view.findViewById(R.id.listitem_storebase_title);
            mTvAdress = (TextView) view.findViewById(R.id.listitem_storebase_address);
            mTvCoupon = (TextView) view.findViewById(R.id.listitem_storebase_coupon);
            mTvStatus = (TextView) view.findViewById(R.id.listitem_storebase_status);
            mTvPrice = (TextView) view.findViewById(R.id.listitem_storebase_price);
            mTvProduct = (TextView) view.findViewById(R.id.listitem_storebase_product);
            mTvDiscard = (TextView) view.findViewById(R.id.listitem_storebase_deprecated);
            mTvStatus = (TextView) view.findViewById(R.id.listitem_storebase_status);
            mTvDescrip = (TextView) view.findViewById(R.id.listitem_storebase_description);
			mTvDistance = (TextView) view.findViewById(R.id.listitem_storebase_distance);
            
            mIvIconPark = (ImageView) view.findViewById(R.id.listitem_storebase_park);
            mIvIconWifi = (ImageView) view.findViewById(R.id.listitem_storebase_wifi);
            
            mIvHeadImage = (ImageView) view.findViewById(R.id.listitem_storebase_headimage);
            
            mLlCoupon = (LinearLayout)view.findViewById(R.id.listitem_storebase_coupon_layout);
            mLlFacility = (LinearLayout)view.findViewById(R.id.listitem_storebase_facility);
            
            mLlCoupon.toString();
            mTvProduct.toString();
            mTvDiscard.toString();
            
            mIvHeadImage.setOnClickListener(this);
            //mTvDiscard.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //ɾ����
        }

        /**
         * �����ݰ󶨵��ؼ���ʾ
         * 
         * @param tData
         */
        public void Binding(Object data)
        {
            if (data instanceof StoreBase)
            {
            	mStore = (StoreBase) data;
                StoreBase tData = (StoreBase) data;
                mTvTitle.setText(tData.Name);
                mTvDescrip.setText(tData.Abstract);

                mTvPrice.setText(""+(int)tData.NowPrice);
                if(tData.Promotions != null && tData.Promotions.length() > 0){
                	mTvCoupon.setText(tData.Promotions);
                }else{
                	mTvCoupon.setText(tData.Abstract);
                }
                //mTvProduct.setVisibility(View.GONE);
                //mLlCoupon.setVisibility(View.GONE);

                mLlFacility.setVisibility(View.VISIBLE);
                mIvIconWifi.setVisibility(tData.HasWifi?View.VISIBLE:View.GONE);
                mIvIconPark.setVisibility(tData.HasPark?View.VISIBLE:View.GONE);
                
                //if(tData.NowPrice < tData.Price){
                //	mTvDiscard.setText(""+(int)tData.Price);
                //	mTvDiscard.setVisibility(View.VISIBLE);
                //}else{
                //	mTvDiscard.setVisibility(View.GONE);
                //}
                
                mTvStatus.setEnabled(!tData.IsBusying);
				mTvStatus.setText(tData.getIsBusyingString());
				
				//mIvHeadImage.set
				
				Location location = SuidingApp.getApp().getFixedLocation();
				if(location != null && tData.Address != null){
					float distance = DistanceUtil.getDistance(location, tData.Address);
					mTvDistance.setText(String.format("%.1fkm",distance/1000));
					mTvDistance.setVisibility(View.VISIBLE);
				}else{
					mTvDistance.setVisibility(View.GONE);
				}
                
                //���ͼƬ��ַ��Ч����ͼƬ��Ϣ
                ImageService.bindImage(tData.HeadImg, mIvHeadImage,
                        ImageFolderEnum.HEAD_STORE, ImageSize.SMALLEST);
                //��ַ
                AddressService.bindAddress(tData.getAddress(), mTvAdress);
            }
        }

        @Override
        public void onClick(View v) {
        	// TODO Auto-generated method stub
    		Context context = v.getContext();
    		String headimage = FileService.getLocalUrl(mStore.HeadImg,
    					ImageFolderEnum.HEAD_STORE, ImageSize.SMALLEST);
    		ExtraUtil.putExtra(AlbumActivity.EXTRA_FORID, UUID.randomUUID());
    		ExtraUtil.putExtra(AlbumActivity.EXTRA_NAME, mStore.Name);
    		ExtraUtil.putExtra(AlbumActivity.EXTRA_DESCRIBE, mStore.Abstract);
    		ExtraUtil.putExtra(AlbumActivity.EXTRA_HEADURL, mStore.HeadImg);
    		ExtraUtil.putExtra(AlbumActivity.EXTRA_SINGLE_URL, mStore.HeadImg);
    		ExtraUtil.putExtra(AlbumActivity.EXTRA_SINGLE_SMALL, headimage);
    		ExtraUtil.putExtra(AlbumActivity.EXTRA_SINGLE_FOLDER,ImageFolderEnum.HEAD_STORE);
    		context.startActivity(new Intent(context, AlbumActivity.class));	
        }
        
        @Override
        public int getLayoutId()
        {
            // TODO Auto-generated method stub
            return R.layout.listitem_storebase;
        }
    }
}
