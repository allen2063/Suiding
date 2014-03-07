package com.suiding.adapter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.application.ImageService;
import com.suiding.constant.ImageFolderEnum;
import com.suiding.constant.ImageSize;
import com.suiding.layoutbind.framework.ILayoutItem;
import com.suiding.model.BirthFavorite;
import com.suiding.util.DateFormatUtil;
import com.suiding.util.TimeSpan;


/**
 * BirthdayListAdapter 支持继承自Birthday
 * 
 * @author SCWANG
 * 
 */
public class BirthdayListAdapter extends ListAdapterBase
{
    private boolean isRemind = false;

    public BirthdayListAdapter(Context context, List<? extends Object> ltdata)
    {
        this(context, ltdata, false);
    }

    public BirthdayListAdapter(Context context, List<? extends Object> ltdata,
            boolean remind)
    {
        super(context, ltdata);
        isRemind = remind;
    }

    @Override
    protected ILayoutItem getItemLayout()
    {
        // TODO Auto-generated method stub
        if (isRemind)
        {
            return new BirthdayReminidListItem();
        }
        else
        {
            return new BirthdayRecordListItem();
        }
    }

    private static long getRemainDay(Date date)
    {
        Calendar now = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();

        birth.setTime(date);
        birth.set(Calendar.YEAR, now.get(Calendar.YEAR));

        if (birth.before(now))
        {
            birth.set(Calendar.YEAR, now.get(Calendar.YEAR) + 1);
        }

        TimeSpan span = TimeSpan.FromDate(now.getTime(), birth.getTime());
        return Math.round(span.getTotalDays());
    }

    /**
     * BirthdayRecordListItem 酒店数据页面绑定类
     * 
     * @author SCWANG
     *         不需要访问上层数据的内部类都应该加上 static 减小系统开支
     *         BirthdayRecordListItem 不只是简单是存储视图的控件 还负责
     *         控件与数据的绑定
     */
    private static class BirthdayRecordListItem implements ILayoutItem
    {
        public ImageView mIvHeadimg = null;
        public ImageView mIvSeximg = null;
        public TextView mTvLunar = null;
        public TextView mTvName = null;
        public TextView mTvRemainDay = null;
        public TextView mTvBirthday = null;

        /**
         * 从视图中取出控件
         * 
         * @param view
         */
        public void Handle(View view)
        {
            mIvHeadimg = (ImageView) view
                    .findViewById(R.id.listitem_birthrecord_headimg);
            mIvSeximg = (ImageView) view
                    .findViewById(R.id.listitem_birthrecord_iconsex);
            mTvLunar = (TextView) view
                    .findViewById(R.id.listitem_birthrecord_lunar);
            mTvName = (TextView) view
                    .findViewById(R.id.listitem_birthrecord_name);
            mTvRemainDay = (TextView) view
                    .findViewById(R.id.listitem_birthrecord_remainday);
            mTvBirthday = (TextView) view
                    .findViewById(R.id.listitem_birthrecord_birthday);
        }

        /**
         * 将数据绑定到控件显示
         * 
         * @param tData
         */
        public void Binding(Object data)
        {
            if (data instanceof BirthFavorite)
            {
                BirthFavorite tData = (BirthFavorite) data;

                long remain = getRemainDay(tData.Birth);

                mTvName.setText(tData.Name);
                mTvRemainDay.setText("" + (remain == 0 ? "今" : remain));
                mTvBirthday.setText(DateFormatUtil.format("M月d日", tData.Birth));
                mIvSeximg.setImageResource(tData.Sex ? R.drawable.ic_man
                        : R.drawable.ic_female);
                
                if(tData.IsLunarCalendar){
                	mTvLunar.setText(R.string.listitem_birthrecord_lunar);
                	mTvLunar.setVisibility(View.VISIBLE);
                }else{
                	mTvLunar.setVisibility(View.GONE);
                }

                //如果图片地址有效加载图片信息
                ImageService.bindImage(tData.HeadImg, mIvHeadimg,
                        ImageFolderEnum.HEAD_USER, ImageSize.SMALLEST,R.drawable.image_person);
            }
        }

        @Override
        public int getLayoutId()
        {
            // TODO Auto-generated method stub
            return R.layout.listitem_birthrecord;
        }

    }

    /**
     * BirthdayReminidListItem 酒店数据页面绑定类
     * 
     * @author SCWANG
     *         不需要访问上层数据的内部类都应该加上 static 减小系统开支
     *         BirthdayReminidListItem 不只是简单是存储视图的控件 还负责
     *         控件与数据的绑定
     */
    private static class BirthdayReminidListItem implements ILayoutItem
    {
        public ImageView mIvHeadimg = null;
        public ImageView mIvSeximg = null;
        public TextView mTvName = null;
        public TextView mTvRemainDay = null;

        /**
         * 从视图中取出控件
         * 
         * @param view
         */
        public void Handle(View view)
        {
            mIvHeadimg = (ImageView) view
                    .findViewById(R.id.listitem_birthremind_headimg);
            mIvSeximg = (ImageView) view
                    .findViewById(R.id.listitem_birthremind_iconsex);
            mTvName = (TextView) view
                    .findViewById(R.id.listitem_birthremind_name);
            mTvRemainDay = (TextView) view
                    .findViewById(R.id.listitem_birthremind_remainday);
        }

        /**
         * 将数据绑定到控件显示
         * 
         * @param tData
         */
        public void Binding(Object data)
        {
            if (data instanceof BirthFavorite)
            {
                BirthFavorite tData = (BirthFavorite) data;

                long remain = getRemainDay(tData.Birth);

                mTvName.setText(tData.Name);
                mTvRemainDay.setText((remain == 0 ? "今天" : (remain + "天")));
                mIvSeximg.setImageResource(tData.Sex ? R.drawable.ic_man
                        : R.drawable.ic_female);

                //如果图片地址有效加载图片信息
                ImageService.bindImage(tData.HeadImg, mIvHeadimg,
                        ImageFolderEnum.HEAD_USER, ImageSize.SMALLEST,R.drawable.image_person);
            }
        }

        @Override
        public int getLayoutId()
        {
            // TODO Auto-generated method stub
            return R.layout.listitem_birthremind;
        }

    }
}
