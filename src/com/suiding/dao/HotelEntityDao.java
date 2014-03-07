package com.suiding.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.suiding.dao.framework.Cacheable;
import com.suiding.dao.framework.StoreBaseDao;
import com.suiding.entity.HotelEntity;


public class HotelEntityDao extends StoreBaseDao implements Cacheable<List<HotelEntity>>
{
    public HotelEntityDao(Context context)
    {
        // TODO Auto-generated method stub
        super(context, HotelEntity.class);
    }

    public void add(HotelEntity obj)
    {
        // TODO Auto-generated method stub
        super.add(obj);
    }

    /**
     * 缓存特定接口 更新缓存
     * @param ltEntity
     */
    public void updateCache(List<HotelEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        super.delAll();
        for (HotelEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }
    /**
     * 缓存特定接口 追加缓存
     * @param ltEntity
     */
    public void appendCache(List<HotelEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        for (HotelEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }

    public List<HotelEntity> getAll()
    {
        // TODO Auto-generated method stub
        Cursor cur = getAll("*");
        List<HotelEntity> ltEntity = new ArrayList<HotelEntity>();
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
    private HotelEntity getEntityFromCursor(Cursor cur)
    {
        // TODO Auto-generated method stub
        HotelEntity tEntity = new HotelEntity();
        tEntity.ID = mHelper.getUUID(cur, "ID");
        tEntity.Level = mHelper.getInt(cur, "Level");
        super.getEntityFromCursor(cur, tEntity);
        return tEntity;
    }
}
