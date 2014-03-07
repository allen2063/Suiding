package com.suiding.caches;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.suiding.application.AppExceptionHandler;
import com.suiding.dao.ImageCacheDao;
import com.suiding.entity.ImageCache;
import com.suiding.util.StringUtil;

public class ImageCaches
{
    // 使用 12.5% 的内存作为内存存储
    private int mMemorySize = 10*1024*1024;//(int)(Runtime.getRuntime().maxMemory()/8);
    private static File mCacheDirFile = null;
    private static ImageCaches mInstance = null;
    private ImageCacheDao mImageCacheDao = null;

    public static synchronized ImageCaches getInstance(Context context)
    {
        if (null == mInstance)
        {
            mInstance = new ImageCaches(context);
        }
        return mInstance;
    }

    public static synchronized void initialize(Context context,String path)
    {
        mInstance = new ImageCaches(context);
        mCacheDirFile = new File(path);
    }

    public static synchronized ImageCaches getInstance()
    {
        return mInstance;
    }
    
    private LruCache<String, Bitmap> mMemoryCache = new LruCache<String, Bitmap>(mMemorySize)
    {
        private String last_key = "";

        @Override
        protected int sizeOf(String key, Bitmap bitmap)
        {
            last_key = key;
            int size = bitmap.getHeight() * bitmap.getWidth() * 2;
            return size;
        }

        @Override
        protected void entryRemoved(boolean evicted, String key,
                Bitmap oldBitmap, Bitmap newBitmap)
        {
            if (!key.equalsIgnoreCase(last_key))
            {
                oldBitmap.recycle();
                oldBitmap = null;
                System.gc();
            }
        }
    };
    
    /**
     * 
     * @param context
     */
    private ImageCaches(Context context)
    {
        mImageCacheDao = new ImageCacheDao(context);
        if (mCacheDirFile == null)
        {
            mCacheDirFile = context.getCacheDir();
        }

        if (mCacheDirFile.exists() == false)
        {
            mCacheDirFile.mkdirs();
        }
    }

    /**
     * 
     * @param url
     * @return
     */
    public Bitmap get(String url)
    {
        Log.e("BitmapCaches", url);

        String key = StringUtil.getMD5(url.getBytes());
        // 从内存中取
        synchronized (mMemoryCache)
        {
            Bitmap bitmap = mMemoryCache.get(key);
            if (bitmap != null)
            {
                if (bitmap.isRecycled())
                {
                    mMemoryCache.remove(key);
                    return null;
                }
                return bitmap;
            }
        }
        // 从文件中取
        try
        {
            ImageCache tImageCache = mImageCacheDao.getBykey(key);
            if(tImageCache == null){
                return null;
            }
            Bitmap bitmap = BitmapFactory.decodeFile(tImageCache.Path);
            if(bitmap != null){
                synchronized (mMemoryCache)
                {
                    mMemoryCache.put(key, bitmap);
                }
            }
            return bitmap;
        }
        catch (Exception e)
        {
            e.printStackTrace();//handled
			AppExceptionHandler.handler(e, "图片缓存，get 出现异常");
        }
        return null;
    }

    /**
     * 
     * @param url
     * @param bitmap
     */
    public void put(String url, Bitmap bitmap)
    {
        String key = StringUtil.getMD5(url.getBytes());
        if (key != null && bitmap != null)
        {
            synchronized (mMemoryCache)
            {
                Log.w("Bitmpa Cache", "add cache: " + key);
                mMemoryCache.put(key, bitmap);
                // 移到文件缓存
                this.putToFile(key, bitmap);
            }
        }
    }

    /**
     * 清除所有缓存
     */
    public void clear()
    {
        if (mMemoryCache != null)
        {
            mMemoryCache.evictAll();
        }

        File[] files = mCacheDirFile.listFiles();
        mImageCacheDao.delAll();
        if (files != null)
        {
            for (File file : files)
                file.delete();
        }
        System.gc();
    }

    /**
     * 获取缓存大小
     */
    public int getCachesSize()
    {
        int size = 0;
        File[] files = mCacheDirFile.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                size += file.length();
            }
        }
        return size;
    }

    /**
     * 
     * @param key
     * @param bitmap
     */
    private void putToFile(String key, Bitmap bitmap)
    {
        try
        {
            if(mImageCacheDao.Exist(key)){
                return;
            }
            String Path = mCacheDirFile.getAbsolutePath() + File.separator + key;
            ImageCache tImageCache = new ImageCache("", key, Path, "");
            
            FileOutputStream tFileOutputStream = new FileOutputStream(Path);
            
            if(bitmap.compress(CompressFormat.JPEG, 100, tFileOutputStream))
            {
                mImageCacheDao.add(tImageCache);
            }
            
            tFileOutputStream.flush();
            tFileOutputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();//handled
			AppExceptionHandler.handler(e, "图片缓存，putToFile 出现异常");
        }
    }

    public File getCachePath()
    {
        // TODO Auto-generated method stub
        return mCacheDirFile;
    }

}
