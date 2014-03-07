package com.suiding.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.layoutbind.framework.ILayoutItem;
import com.suiding.model.Coupon;
import com.suiding.util.DateFormatUtil;


/**
 * CouponListAdapter
 * 
 * @author SCWANG
 * 
 */
public class CouponListAdapter extends ListAdapterBase
{

    public CouponListAdapter(Context context, List<? extends Object> ltdata)
    {
        super(context, ltdata);
    }

    @Override
    protected ILayoutItem getItemLayout()
    {
        // TODO Auto-generated method stub
        return new CouponListItem();
    }

    /**
     * CouponListItem 数据页面绑定类
     * 
     * @author SCWANG
     *         不需要访问上层数据的内部类都应该加上 static 减小系统开支
     *         CouponListItem 不只是简单是存储视图的控件 还负责
     *         控件与数据的绑定
     */
    private static class CouponListItem implements ILayoutItem
    {
    	public TextView mTvDate = null;
        public TextView mTvName = null;
        public TextView mTvDescription = null;

        /**
         * 从视图中取出控件
         * 
         * @param view
         */
        public void Handle(View view)
        {
            mTvName = (TextView) view.findViewById(R.id.listitem_coupon_name);
            mTvDate = (TextView) view.findViewById(R.id.listitem_coupon_date);
            mTvDescription = (TextView) view.findViewById(R.id.listitem_coupon_content);
        }

        /**
         * 将数据绑定到控件显示
         * 
         * @param tData
         */
        public void Binding(Object data)
        {
            if (data instanceof Coupon)
            {
                Coupon tData = (Coupon) data;
                mTvName.setText(tData.Name);
                String Content = "";
                if(tData.Product != null){
                	Content = String.format("[%s]", tData.Product.Name);
                }
                mTvDescription.setText(Content + tData.Content);

                mTvDate.setText(DateFormatUtil.formatDuration(tData.BegDate,tData.EndDate));
            }

        }

        @Override
        public int getLayoutId()
        {
            // TODO Auto-generated method stub
            return R.layout.listitem_coupon;
        }
    }
}
