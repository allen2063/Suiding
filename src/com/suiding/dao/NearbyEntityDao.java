package com.suiding.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.suiding.dao.framework.Cacheable;
import com.suiding.dao.framework.StoreEntityDao;
import com.suiding.entity.NearbyEntity;


public class NearbyEntityDao extends StoreEntityDao implements
        Cacheable<List<NearbyEntity>>
{

    public NearbyEntityDao(Context context)
    {
        // TODO Auto-generated method stub
        super(context, NearbyEntity.class);
    }

    public void add(NearbyEntity obj)
    {
        // TODO Auto-generated method stub
        super.add(obj);
    }

    @Override
    public void updateCache(List<NearbyEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        super.delAll();
        this.appendCache(ltEntity);
    }

    @Override
    public void appendCache(List<NearbyEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        for (NearbyEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }

    /**
     * 获取全部
     * 
     * @return
     */
    public List<NearbyEntity> getAll()
    {
        // TODO Auto-generated method stub
        Cursor cur = getAll("*");
        List<NearbyEntity> ltEntity = new ArrayList<NearbyEntity>();
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
    private NearbyEntity getEntityFromCursor(Cursor cur)
    {
        // TODO Auto-generated method stub
        NearbyEntity tEntity = new NearbyEntity();
        super.getEntityFromCursor(cur, tEntity);
        return tEntity;
    }
}
