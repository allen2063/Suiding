package com.suiding.adapter;

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
import com.suiding.model.BirthInvitation;


/**
 * BirthInvitationListAdapter
 * 
 * @author SCWANG
 * 
 */
public class BirthInvitationListAdapter extends ListAdapterBase
{
    public BirthInvitationListAdapter(Context context, List<Object> ltdata)
    {
        super(context, ltdata);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected ILayoutItem getItemLayout()
    {
        // TODO Auto-generated method stub
        return new BirthInvitationListItem();
    }

    /**
     * BirthInvitationListItem 酒店数据页面绑定类
     * 
     * @author SCWANG
     *         不需要访问上层数据的内部类都应该加上 static 减小系统开支
     *         BirthInvitationListItem 不只是简单是存储视图的控件 还负责
     *         控件与数据的绑定
     */
    private static class BirthInvitationListItem implements ILayoutItem
    {
        public TextView mTvTitle = null;
        public TextView mTvDescription = null;

        public ImageView mIvHeadImage = null;

        /**
         * 从视图中取出控件
         * 
         * @param view
         */
        @Override
        public void Handle(View view)
        {
            // TODO Auto-generated method stub
            mTvTitle = (TextView) view
                    .findViewById(R.id.listitem_birinvite_title);
            mIvHeadImage = (ImageView) view
                    .findViewById(R.id.listitem_birinvite_headimg);
            mTvDescription = (TextView) view
                    .findViewById(R.id.listitem_birinvite_describe);
        }

        /**
         * 将数据绑定到控件显示
         * 
         * @param tData
         */
        @Override
        public void Binding(Object data)
        {
            // TODO Auto-generated method stub
            if (data instanceof BirthInvitation)
            {
                BirthInvitation tData = (BirthInvitation) data;

                mTvTitle.setText(tData.Name);
                mTvDescription.setText(tData.Content);

                //如果图片地址有效加载图片信息
                if (tData.StoreBase != null)
                {
                    ImageService.bindImage(tData.StoreBase.HeadImg,
                            mIvHeadImage, ImageFolderEnum.HEAD_STORE,
                            ImageSize.SMALLEST);
                }
                else
                {
                    ImageService.bindImage(null, mIvHeadImage,
                            ImageFolderEnum.HEAD_STORE, ImageSize.SMALLEST);
                }
            }
        }

        @Override
        public int getLayoutId()
        {
            // TODO Auto-generated method stub
            return R.layout.listitem_birthinvite;
        }
    }

}
