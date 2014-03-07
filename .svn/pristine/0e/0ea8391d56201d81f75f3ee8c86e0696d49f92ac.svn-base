package com.suiding.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.suiding.activity.R;
import com.suiding.caches.ImageCaches;
import com.suiding.service.FileService;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.NetworkUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.widget.ImageView;


public class ImageService extends TaskBase
{
	public int mImageSize;
    public int mImageFolder;
    public int mDefaultId;

    public String mImageUrl;
    public String mLinkUrl;
    public String mDefaultUrl;
    public Bitmap mBitmap;
    public List<ImageView> mltImageView = new ArrayList<ImageView>();

    private static Bitmap mBitmapLoading;
    private static Bitmap mBitmapGetFail;
    private static Bitmap mBitmapNotFind;

    //标识是否按照规定的使用post函数 抛送 ImageTask
    private Boolean mIsPostByRule = false;
    //标识是否可以不用下载直接读取缓存
    private Boolean mIsCanReadCaches = false;

    private static HashMap<String, ImageService> mhmLoadingTask = new HashMap<String, ImageService>();

    public ImageService(String imageUrl, ImageView view, int ImageFolder,
            int ImageSize, int defaultid)
    {
        super(SuidingApp.getLooper());
        this.mImageUrl = imageUrl;
        this.mltImageView.add(view);
        this.mImageSize = ImageSize;
        this.mImageFolder = ImageFolder;
        this.mDefaultId = defaultid;
        this.mLinkUrl = FileService.getLocalUrl(imageUrl, ImageFolder,
                ImageSize);
    }

    public ImageService(String imageUrl, ImageView view, int ImageFolder,
            int ImageSize, String defaultid)
    {
        super(SuidingApp.getLooper());
        this.mImageUrl = imageUrl;
        this.mltImageView.add(view);
        this.mImageSize = ImageSize;
        this.mImageFolder = ImageFolder;
        this.mDefaultUrl = defaultid;
        this.mLinkUrl = FileService.getLocalUrl(imageUrl, ImageFolder,
                ImageSize);
    }

    public ImageService(String imageUrl, ImageView view, int ImageFolder,
            int ImageSize)
    {
        this(imageUrl, view, ImageFolder, ImageSize, 0);
    }

    /**
     * 把任务post到App的Worker执行
     */
    public void post()
    {
        //如果在正在加载列表中已经存在当前Url
        ImageService tTask = mhmLoadingTask.get(mLinkUrl);
        if (tTask == null)
        {
            //设置控件目标为本任务
            mltImageView.get(0).setTag(this);
            //把当前Url从添加到正在加载列表
            mhmLoadingTask.put(mLinkUrl, this);
            //标识已经按照post抛送 ImageTask
            mIsPostByRule = true;
            //把任务发送到App线程中去执行
            SuidingApp.postTask(this);
        }
        else
        {
            tTask.incidentallyTake(this);
        }
    }

    /**
     * 两个Task的任务相同情况下，让前一个顺便帮后一个处理
     * 取消后一个Task
     * 
     * @param imageTask
     */
    private void incidentallyTake(ImageService imageTask)
    {
        // TODO Auto-generated method stub
        ImageView tImageView = imageTask.mltImageView.get(0);
        tImageView.setTag(this);
        mltImageView.add(tImageView);
    }

    @Override
    protected void onWorking(Message tMessage) throws Exception
    {
        // TODO Auto-generated method stub
        if (mIsPostByRule == true)
        {
            if (mIsCanReadCaches == true)
            {
                ImageCaches tBitmapCaches = ImageCaches.getInstance(SuidingApp
                        .getAppContext());
                mBitmap = tBitmapCaches.get(mLinkUrl);
                if (mBitmap != null)
                {
                    return;
                }
            }
            mBitmap = FileService.downloadImage(SuidingApp.getAppContext(),
                    mImageUrl, mImageFolder, mImageSize).getBitmap();
        }
        else
        {
            throw new Exception("请使用ImageTask.post()抛送任务！");
        }
    }
    
    @Override
    protected boolean onHandle(Message msg) {
    	// TODO Auto-generated method stub
    	//如果任务成功执行完成
        if (msg.what == ImageService.RESULT_FINISH)
        {
            ImageCaches tBitmapCaches = ImageCaches.getInstance();
            tBitmapCaches.put(mLinkUrl, mBitmap);
            for (ImageView imageview : mltImageView)
            {
                if (imageview.getTag() == this)
                {
                    imageview.setImageBitmap(mBitmap);
                }
            }
        }
        //如果任务执行失败
        else
        {
            for (ImageView view : mltImageView)
            {
                if (view.getTag() == this)
                {
                    if (mDefaultId > 0)
                        view.setImageResource(mDefaultId);
                    else if(mDefaultUrl != null && mDefaultUrl.length()>0)
                    	bindDefault(view,mDefaultUrl, getImageGetFail());
                    else
                        view.setImageBitmap(getImageGetFail());
                }
            }
        }
        //把当前Url从正在加载列表中移除
        mhmLoadingTask.remove(mLinkUrl);
    	return true;
    }

    /**
     * 在没有加载图片之前 可以使用默认图片
     * 
     * @return
     */
    public static Bitmap getImageLoading()
    {
        if (mBitmapGetFail == null)
        {
            mBitmapGetFail = BitmapFactory.decodeResource(SuidingApp.getApp()
                    .getResources(), R.drawable.default_image_loading);
        }
        return mBitmapGetFail;
    }

    /**
     * 在没有加载图片之前 可以使用默认图片
     * 
     * @return
     */
    public static Bitmap getImageGetFail()
    {
        if (mBitmapLoading == null)
        {
            mBitmapLoading = BitmapFactory.decodeResource(SuidingApp.getApp()
                    .getResources(), R.drawable.default_image_fail);
        }
        return mBitmapLoading;
    }

    /**
     * 在没有加载图片之前 可以使用默认图片
     * 
     * @return
     */
    public static Bitmap getImageNotFind()
    {
        if (mBitmapNotFind == null)
        {
            mBitmapNotFind = BitmapFactory.decodeResource(SuidingApp.getApp()
                    .getResources(), R.drawable.default_image_notfind);
        }
        return mBitmapNotFind;
    }

    /**
     * 将图片URL绑定到控件ImageView
     * 
     * @param url
     * @param view
     */
    public static void bindImage(String url, ImageView view,
            int ImageFolder, int ImageSize)
    {
        bindImage(url, view, ImageFolder, ImageSize, 0);
    }

    /**
     * 将图片URL绑定到控件ImageView
     * 
     * @param url
     * @param view
     */
    public static void bindImage(String url, ImageView view,
            int folder, int size, String idefault)
    {
        // TODO Auto-generated method stub
        if (bindNoImage(view,idefault) && url != null && url.length() > 0)
        {
            //先从图片缓冲中读取图片
            String LinkUrl = FileService.getLocalUrl(url, folder,size);
            ImageCaches tBitmapCaches = ImageCaches.getInstance();
            Bitmap tButmap = tBitmapCaches.get(LinkUrl);
            if (tButmap != null)
            {
                view.setImageBitmap(tButmap);
            }
            else
            {
                //如果失败从网络上加载数据
            	bindDefault(view,idefault,getImageLoading());
                new ImageService(url, view, folder, size,idefault).post();
            }
        }
        else
        {
        	bindDefault(view,idefault,getImageNotFind());
        }
    }
    

	/**
     * 将图片URL绑定到控件ImageView
     * 
     * @param url
     * @param view
     */
    public static void bindImage(String url, ImageView view,
            int Folder, int Size, int idefault)
    {
        // TODO Auto-generated method stub
        if (bindNoImage(view,idefault) && url != null && url.length() > 0)
        {
            //先从图片缓冲中读取图片
            String LinkUrl = FileService.getLocalUrl(url, Folder,
                    Size);
            ImageCaches tBitmapCaches = ImageCaches.getInstance();
            Bitmap tButmap = tBitmapCaches.get(LinkUrl);
            if (tButmap != null)
            {
                view.setImageBitmap(tButmap);
            }
            else
            {
                //如果失败从网络上加载数据
            	bindDefault(view,idefault,getImageLoading());
                new ImageService(url, view, Folder, Size,idefault).post();
            }
        }
        else
        {
        	bindDefault(view,idefault,getImageNotFind());
        }
    }
	
	private static boolean isSettingNoImage() {
		// TODO Auto-generated method stub
		int network = SuidingApp.getNetworkStatus();
    	AppSettings setting = AppSettings.getInstance();
		return (network==NetworkUtil.TYPE_MOBILE&&setting.isNoImage());
	}

    private static boolean bindNoImage(ImageView view, int idefault) {
		// TODO Auto-generated method stub
    	if(isSettingNoImage()){
    		bindDefault(view,idefault,ImageService.getImageNotFind());
    		return false;
    	}
    	return true;
	}

	private static boolean bindNoImage(ImageView view, String idefault) {
		// TODO Auto-generated method stub
    	if(isSettingNoImage()){
    		bindDefault(view,idefault,getImageNotFind());
    		return false;
    	}
    	return true;
	}

	private static void bindDefault(ImageView view, int idefault,Bitmap image) {
		// TODO Auto-generated method stub
        //如果失败从网络上加载数据
    	if(idefault == 0)
    	{
            view.setImageBitmap(image);
    	}
    	else
    	{
            view.setImageResource(idefault);
    	}
	}

    private static void bindDefault(ImageView view, String idefault,Bitmap image) {
		// TODO Auto-generated method stub
        //如果失败从网络上加载数据
    	if(idefault == null)
    	{
            view.setImageBitmap(image);
    	}
    	else
    	{
            ImageCaches imagecaches = ImageCaches.getInstance();
    		Bitmap tButmap = imagecaches.get(idefault);
        	if(tButmap != null){
                view.setImageBitmap(tButmap);
        	}else{
                view.setImageBitmap(image);
        	}
    	}
	}
}
