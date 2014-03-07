package com.suiding.dao;

import android.content.Context;
import android.database.Cursor;

import com.suiding.dao.framework.DaoBase;
import com.suiding.entity.ImageCache;

public class ImageCacheDao extends DaoBase
{
    public ImageCacheDao(Context context){
        super(context, ImageCache.class);
    }

    public void add(ImageCache obj){
        super.add(obj);
    }

    public ImageCache getBykey(String key)
    {
        // TODO Auto-generated method stub
        Cursor cur = getWhere("*", "Key = '"+ key + "'", 1, 0);
        if (cur.moveToNext())
        {
            return getEntityFromCursor(cur);
        }
        return null;
    }

    public boolean Exist(String key)
    {
        // TODO Auto-generated method stub
        Cursor cur = getWhere("ID", "Key = '"+ key + "'", 1, 0);
        if (cur.moveToNext()){
            return true;
        }
        return false;
    }


    /**
     * 从Cursor中构造所有字段 返回 Entity
     * @param cur
     * @return
     */
    private ImageCache getEntityFromCursor(Cursor cur){

        ImageCache tImageCache = new ImageCache();
        
        tImageCache.ID = mHelper.getString(cur,("ID"));
        tImageCache.Name = mHelper.getString(cur,("Name"));
        tImageCache.Key = mHelper.getString(cur,("Key"));
        tImageCache.Path = mHelper.getString(cur,("Path"));
        tImageCache.RegDate = mHelper.getDate(cur,("RegDate"));
        tImageCache.LastDate = mHelper.getDate(cur,("LastDate"));
        tImageCache.Remark = mHelper.getString(cur,("Remark"));
        
        return tImageCache;
    }
}
