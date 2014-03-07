package com.suiding.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.suiding.dao.framework.Cacheable;
import com.suiding.dao.framework.StoreBaseDao;
import com.suiding.entity.TwitterEntity;


public class TwitterEntityDao extends StoreBaseDao implements Cacheable<List<TwitterEntity>>
{
    public TwitterEntityDao(Context context)
    {
        // TODO Auto-generated method stub
        super(context, TwitterEntity.class);
    }

    public void add(TwitterEntity obj)
    {
        // TODO Auto-generated method stub
        super.add(obj);
    }

    /**
     * 缓存特定接口 更新缓存
     * @param ltEntity
     */
    public void updateCache(List<TwitterEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        super.delAll();
        for (TwitterEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }
    /**
     * 缓存特定接口 追加缓存
     * @param ltEntity
     */
    public void appendCache(List<TwitterEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        for (TwitterEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }

    public List<TwitterEntity> getAll()
    {
        // TODO Auto-generated method stub
        Cursor cur = getAll("*");
        List<TwitterEntity> ltEntity = new ArrayList<TwitterEntity>();
        while (cur.moveToNext())
        {
            ltEntity.add(getEntityFromCursor(cur));
        }
        return ltEntity;
    }

    /**
     * 从Cursor中构造所有字段 返回 Entity
     * @param cur
     * @return
     */
    private TwitterEntity getEntityFromCursor(Cursor cur)
    {
        // TODO Auto-generated method stub
        TwitterEntity tEntity = new TwitterEntity();
        tEntity.ID = mHelper.getUUID(cur, "ID");
        tEntity.For_ID = mHelper.getUUID(cur, "For_ID");
        tEntity.tName = mHelper.getString(cur, "tName");
        tEntity.Content = mHelper.getString(cur, "Content");
        tEntity.Remark = mHelper.getString(cur, "Remark");
        tEntity.Date = mHelper.getDate(cur, "Date");
        super.getEntityFromCursor(cur, tEntity);
        return tEntity;
    }
}
