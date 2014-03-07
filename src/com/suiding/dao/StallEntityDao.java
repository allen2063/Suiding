package com.suiding.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.suiding.dao.framework.Cacheable;
import com.suiding.dao.framework.StoreBaseDao;
import com.suiding.entity.StallEntity;


public class StallEntityDao extends StoreBaseDao implements Cacheable<List<StallEntity>>
{
    public StallEntityDao(Context context)
    {
        // TODO Auto-generated method stub
        super(context, StallEntity.class);
    }

    public void add(StallEntity obj)
    {
        // TODO Auto-generated method stub
        super.add(obj);
    }

    /**
     * 缓存特定接口 更新缓存
     * @param ltEntity
     */
    public void updateCache(List<StallEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        super.delAll();
        for (StallEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }
    /**
     * 缓存特定接口 追加缓存
     * @param ltEntity
     */
    public void appendCache(List<StallEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        for (StallEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }

    /**
     * 获取全部
     * @return
     */
    public List<StallEntity> getAll()
    {
        // TODO Auto-generated method stub
        Cursor cur = getAll("*");
        List<StallEntity> ltEntity = new ArrayList<StallEntity>();
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
    private StallEntity getEntityFromCursor(Cursor cur)
    {
        // TODO Auto-generated method stub
        StallEntity tEntity = new StallEntity();
        tEntity.ID = mHelper.getUUID(cur, "ID");
        super.getEntityFromCursor(cur, tEntity);
        return tEntity;
    }
}
