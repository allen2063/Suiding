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
     * CouponListItem ����ҳ�����
     * 
     * @author SCWANG
     *         ����Ҫ�����ϲ����ݵ��ڲ��඼Ӧ�ü��� static ��Сϵͳ��֧
     *         CouponListItem ��ֻ�Ǽ��Ǵ洢��ͼ�Ŀؼ� ������
     *         �ؼ������ݵİ�
     */
    private static class CouponListItem implements ILayoutItem
    {
    	public TextView mTvDate = null;
        public TextView mTvName = null;
        public TextView mTvDescription = null;

        /**
         * ����ͼ��ȡ���ؼ�
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
         * �����ݰ󶨵��ؼ���ʾ
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
