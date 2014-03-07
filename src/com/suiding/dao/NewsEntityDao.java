package com.suiding.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.suiding.dao.framework.Cacheable;
import com.suiding.dao.framework.StoreEntityDao;
import com.suiding.entity.NewsEntity;


public class NewsEntityDao extends StoreEntityDao implements Cacheable<List<NewsEntity>>
{

    public NewsEntityDao(Context context)
    {
        // TODO Auto-generated method stub
        super(context, NewsEntity.class);
    }

    public void add(NewsEntity obj)
    {
        // TODO Auto-generated method stub
        super.add(obj);
    }

    /**
     * 缓存特定接口 更新缓存
     * 
     * @param ltEntity
     */
    public void updateCache(List<NewsEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        super.delAll();
        for (NewsEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }

    /**
     * 缓存特定接口 追加缓存
     * 
     * @param ltEntity
     */
    public void appendCache(List<NewsEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        for (NewsEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }

    /**
     * 获取全部
     * 
     * @return
     */
    public List<NewsEntity> getAll()
    {
        // TODO Auto-generated method stub
        Cursor cur = getAll("*");
        List<NewsEntity> ltEntity = new ArrayList<NewsEntity>();
        while (cur.moveToNext())
        {
            ltEntity.add(getEntityFromCursor(cur));
        }
        return ltEntity;
    }

    /**
     * 从Cursor中构造所有字段 返回 Entity
     * 
     * @param cur
     * @return
     */
    private NewsEntity getEntityFromCursor(Cursor cur)
    {
        // TODO Auto-generated method stub
        NewsEntity tEntity = new NewsEntity();
        super.getEntityFromCursor(cur, tEntity);
        return tEntity;
    }
}
