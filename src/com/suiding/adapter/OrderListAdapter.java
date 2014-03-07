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
     * OrderListItem 账单数据页面绑定类
     * 
     * @author SCWANG
     *         不需要访问上层数据的内部类都应该加上 static 减小系统开支
     *         OrderListItem 不只是简单是存储视图的控件 还负责
     *         控件与数据的绑定
     */
    private static class OrderListItem implements ILayoutItem
    {
        public TextView mTvTitle = null;
        public TextView mTvProduct = null;//类型
        public TextView mTvCoupon = null;
        public TextView mTvDescription = null;
        public TextView mTvOldPrice=null;//优惠价
        public TextView mTvNowPrice=null;//原价
        public TextView mTvStatus=null;//动作。如取消
        public TextView mTvOrderTime=null;//动作。如取消

        public ImageView mIvHeadImage = null;

        /**
         * 从视图中取出控件
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
         * 将数据绑定到控件显示
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
                     mTvTitle.setText("商品数据被删除");
                     mTvDescription.setText("活动数据被删除");
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
        			 mTvCoupon.setText("预定享受会员价");
        		 }
        		 
        		 if(product != null){
        			 mTvProduct.setText(product.Name);
                     mTvProduct.setVisibility(View.VISIBLE);
        		 }else{
                     mTvProduct.setVisibility(View.GONE);
        		 }
                 
        		 if(tData.OrderStatus == OrderStatus.CONFIRM){
        			 String notify = "订单已经受理，请于";
        			 notify += DateFormatUtil.formatTime(tData.ArrivalTime);
        			 notify += "到店";
        			 if(store != null && store.Telephone!=null && store.Telephone.length()>0){
        				 notify += "联系电话：" + store.Telephone;
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
