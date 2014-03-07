package com.suiding.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.application.ImageService;
import com.suiding.constant.ImageFolderEnum;
import com.suiding.constant.ImageSize;
import com.suiding.constant.OrderStatus;
import com.suiding.layoutbind.framework.ILayoutItem;
import com.suiding.model.Coupon;
import com.suiding.model.Order;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;
import com.suiding.util.DateFormatUtil;

public class OrderListAdapter extends ListAdapterBase{

	public OrderListAdapter(Context context, List<? extends Object> ltdata) {
		super(context, ltdata);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ILayoutItem getItemLayout() {
		// TODO Auto-generated method stub
		return new OrderListItem();
	}
	/**
     * OrderListItem �˵�����ҳ�����
     * 
     * @author SCWANG
     *         ����Ҫ�����ϲ����ݵ��ڲ��඼Ӧ�ü��� static ��Сϵͳ��֧
     *         OrderListItem ��ֻ�Ǽ��Ǵ洢��ͼ�Ŀؼ� ������
     *         �ؼ������ݵİ�
     */
    private static class OrderListItem implements ILayoutItem
    {
        public TextView mTvTitle = null;
        public TextView mTvProduct = null;//����
        public TextView mTvCoupon = null;
        public TextView mTvDescription = null;
        public TextView mTvOldPrice=null;//�Żݼ�
        public TextView mTvNowPrice=null;//ԭ��
        public TextView mTvStatus=null;//��������ȡ��
        public TextView mTvOrderTime=null;//��������ȡ��

        public ImageView mIvHeadImage = null;

        /**
         * ����ͼ��ȡ���ؼ�
         * 
         * @param view
         */
        public void Handle(View view)
        {
        	mTvTitle=(TextView)view.findViewById(R.id.listitem_order_name);
        	mTvProduct=(TextView)view.findViewById(R.id.listitem_order_product);
        	mTvCoupon=(TextView)view.findViewById(R.id.listitem_order_coupon);
        	mTvDescription=(TextView)view.findViewById(R.id.listitem_order_detail);
        	mTvOldPrice=(TextView)view.findViewById(R.id.listitem_order_oldprivate);
        	mTvNowPrice=(TextView)view.findViewById(R.id.listitem_order_nowprice);
        	mTvStatus=(TextView)view.findViewById(R.id.listitem_order_cancel);
        	mTvOrderTime=(TextView)view.findViewById(R.id.listitem_order_time);
        	
        	mTvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        	mIvHeadImage=(ImageView)view.findViewById(R.id.listitem_order_headimg);
        }

        /**
         * �����ݰ󶨵��ؼ���ʾ
         * 
         * @param tData
         */
        public void Binding(Object data)
        {
        	 if (data instanceof Order)
             {
        		 Order tData = (Order) data;
        		 Coupon coupon = tData.getCoupon();
        		 Product product = tData.getProductBase();
        		 StoreBase store = tData.getStoreBase();
        		 if(coupon != null && coupon.StoreBase != null){
        			 store = coupon.StoreBase; 
        		 }
        		 if(coupon != null && coupon.Product != null){
        			 product = coupon.Product; 
        		 }
        		 
        		 if(store != null){
                     mTvTitle.setText(store.Name);
                     mTvDescription.setText(store.Abstract);
                     ImageService.bindImage(store.HeadImg, mIvHeadImage,
                             ImageFolderEnum.HEAD_STORE, ImageSize.SMALLEST);
        		 }else if(product != null){
                     mTvTitle.setText(product.Name);
                     mTvDescription.setText(product.Abstrct);
                     ImageService.bindImage(product.HeadImg, mIvHeadImage,
                             ImageFolderEnum.HEAD_PRODUCT, ImageSize.SMALLEST);
        		 }else{
                     mTvTitle.setText("��Ʒ���ݱ�ɾ��");
                     mTvDescription.setText("����ݱ�ɾ��");
                     ImageService.bindImage(null, mIvHeadImage,0, 0);
        		 }
        		 mTvNowPrice.setText(String.format("%.0f",tData.NowTotalPrice));
        		 if(tData.TotalPrice > tData.NowTotalPrice){
            		 mTvOldPrice.setVisibility(View.GONE);
            		 mTvOldPrice.setText(String.format("%.0f",tData.TotalPrice));
        		 }else{
            		 mTvOldPrice.setVisibility(View.GONE);
        		 }
        		 if(coupon != null && coupon.Name != null && coupon.Name.length() > 0){
        			 mTvCoupon.setText(coupon.Name);
        		 }else{
        			 mTvCoupon.setText("Ԥ�����ܻ�Ա��");
        		 }
        		 
        		 if(product != null){
        			 mTvProduct.setText(product.Name);
                     mTvProduct.setVisibility(View.VISIBLE);
        		 }else{
                     mTvProduct.setVisibility(View.GONE);
        		 }
                 
        		 if(tData.OrderStatus == OrderStatus.CONFIRM){
        			 String notify = "�����Ѿ���������";
        			 notify += DateFormatUtil.formatTime(tData.ArrivalTime);
        			 notify += "����";
        			 if(store != null && store.Telephone!=null && store.Telephone.length()>0){
        				 notify += "��ϵ�绰��" + store.Telephone;
        			 }
        			 mTvDescription.setText(notify);
        		 }
        		 mTvOrderTime.setText(DateFormatUtil.formatTime(tData.Date));
        		 mTvStatus.setText(OrderStatus.getStatusName(tData.OrderStatus));
             }
        }

        @Override
        public int getLayoutId()
        {
            // TODO Auto-generated method stub
            return R.layout.listitem_order;
        }
    }
}
