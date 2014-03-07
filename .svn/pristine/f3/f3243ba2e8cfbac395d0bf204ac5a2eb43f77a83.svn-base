package com.suiding.adapter;

import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.suiding.activity.AlbumActivity;
import com.suiding.activity.R;
import com.suiding.application.ImageService;
import com.suiding.constant.ImageSize;
import com.suiding.model.Photo;
import com.suiding.widget.AlbumView;

/**
 * @author SCWANG
 *         相册的适配器
 */
@SuppressLint("UseSparseArrays")
public class AlbumPagerAdapter extends PagerAdapter
{
    private LayoutInflater mInflater;

    private OnTouchListener mTouchListener;
    private int mPhotoType = AlbumActivity.TYPE_NULL;
    private List<Photo> mltData = null;
	private HashMap<Integer, AlbumPagerItem> mHashMap = new HashMap<Integer, AlbumPagerItem>();

    public AlbumPagerAdapter(Context context, List<Photo> ltData,int type)
    {
        // TODO Auto-generated constructor stub
        mltData = ltData;
        mPhotoType = type;
        mInflater = LayoutInflater.from(context);
    }

	public void setOnTouchListener(OnTouchListener listener) {
		// TODO Auto-generated method stub
		mTouchListener = listener;
	}

    /**
     * 适配器新增 点击更多 数据追加接口
     * 
     * @param ltNews
     */
    public void AddDataSet(List<Photo> ltData)
    {
        // TODO Auto-generated method stub
        mltData.addAll(ltData);
        notifyDataSetChanged();
    }

    /**
     * 适配器新增 数据刷新 接口
     * 
     * @param ltNews
     */
    public void setData(List<Photo> ltData)
    {
        // TODO Auto-generated method stub
        mltData = ltData;
        notifyDataSetChanged();
    }
    
    //这里进行回收，当我们左右滑动的时候，会把早期的图片回收掉.
    @Override
    public void destroyItem(View container, int position, Object object)
    {
        // TODO Auto-generated constructor stub
        View view = mHashMap.get(position).mView;
        ((ViewPager)container).removeView(view);
        mHashMap.remove(position);
    }

    @Override
    public void finishUpdate(View view)
    {
        // TODO Auto-generated constructor stub

    }

    //这里返回相册有多少条,和BaseAdapter一样.
    @Override
    public int getCount()
    {
        // TODO Auto-generated constructor stub
        return mltData.size();
    }

    //这里就是初始化ViewPagerItemView.如果ViewPagerItemView已经存在,
    //重新reload，不存在new一个并且填充数据.
    @Override
    public Object instantiateItem(View container, int position)
    {
        // TODO Auto-generated constructor stub
        View view = null;
        if(mHashMap.containsKey(position)){
            view = mHashMap.get(position).mView;
        }else{
            view = mInflater.inflate(R.layout.album_pager, null);
            AlbumPagerItem tItem = new AlbumPagerItem(view,mltData.get(position));
            tItem.setOnTouchListener(mTouchListener);
            mHashMap.put(position, tItem);
            ((ViewPager)container).addView(view);
        }
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1)
    {

    }

    @Override
    public Parcelable saveState()
    {
        return null;
    }

    @Override
    public void startUpdate(View view)
    {

    }

    private class AlbumPagerItem
    {
        public View mView = null;
        public ImageView mTvImage = null;
        public OnTouchListener mTouchListener = null;
        
        public AlbumPagerItem(View view,Photo photo)
        {
            Handle(view);
            Binding(photo);
        }
        
        public void setOnTouchListener(OnTouchListener listener) {
			// TODO Auto-generated method stub
        	mTouchListener = listener;
            if(mTouchListener != null && mTvImage instanceof AlbumView){
            	AlbumView album = (AlbumView)mTvImage;
            	album.setOnTouchListener(mTouchListener);
            }
		}

		private void Handle(View view)
        {
            mView = view;
            mTvImage = (ImageView)view.findViewById(R.id.album_pager_image);
            if(mTouchListener != null && mTvImage instanceof AlbumView){
            	AlbumView album = (AlbumView)mTvImage;
            	album.setOnTouchListener(mTouchListener);
            }
        }
        /**
         * 将数据绑定到控件显示
         * @param review
         */
        public void Binding(Photo photo)
        {
            ImageService.bindImage(photo.Url, mTvImage, mPhotoType, ImageSize.NORMAL,photo.Remark);
//            switch (mPhotoType)
//            {
//                case AlbumActivity.TYPE_OTHER:
//                    ImageTask.bindImage(photo.Url, mTvImage, ImageFolderEnum.PHOTO_OHTER, ImageSize.NORMAL);
//                    break;
//                case AlbumActivity.TYPE_PRODUCT:
//                    ImageTask.bindImage(photo.Url, mTvImage, ImageFolderEnum.PHOTO_PRODUCT, ImageSize.NORMAL);
//                    break;
//                case AlbumActivity.TYPE_STORE:
//                    ImageTask.bindImage(photo.Url, mTvImage, ImageFolderEnum.PHOTO_STORE, ImageSize.NORMAL);
//                    break;
//                case AlbumActivity.TYPE_USER:
//                    ImageTask.bindImage(photo.Url, mTvImage, ImageFolderEnum.PHOTO_USER, ImageSize.NORMAL);
//                    break;
//                default:
//                case AlbumActivity.TYPE_NULL:
//                    
//                    break;
//            }
        }
    }

}
