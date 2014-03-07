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
     * BirthInvitationListItem �Ƶ�����ҳ�����
     * 
     * @author SCWANG
     *         ����Ҫ�����ϲ����ݵ��ڲ��඼Ӧ�ü��� static ��Сϵͳ��֧
     *         BirthInvitationListItem ��ֻ�Ǽ��Ǵ洢��ͼ�Ŀؼ� ������
     *         �ؼ������ݵİ�
     */
    private static class BirthInvitationListItem implements ILayoutItem
    {
        public TextView mTvTitle = null;
        public TextView mTvDescription = null;

        public ImageView mIvHeadImage = null;

        /**
         * ����ͼ��ȡ���ؼ�
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
         * �����ݰ󶨵��ؼ���ʾ
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

                //���ͼƬ��ַ��Ч����ͼƬ��Ϣ
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
