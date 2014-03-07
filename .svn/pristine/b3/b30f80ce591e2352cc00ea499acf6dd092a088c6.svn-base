package com.suiding.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.suiding.dao.framework.Cacheable;
import com.suiding.dao.framework.DaoBase;
import com.suiding.entity.StoreTypeEntity;


public class StoreTypeEntityDao extends DaoBase implements Cacheable<List<StoreTypeEntity>>
{
    public StoreTypeEntityDao(Context context)
    {
        // TODO Auto-generated method stub
        super(context, StoreTypeEntity.class);
    }

    public void add(StoreTypeEntity obj)
    {
        // TODO Auto-generated method stub
        super.add(obj);
    }

    public List<StoreTypeEntity> getByType(int type)
    {
        // TODO Auto-generated method stub
        Cursor cur = getWhere("*", "pType=" + type);
        List<StoreTypeEntity> ltEntity = new ArrayList<StoreTypeEntity>();
        while (cur.moveToNext())
        {
            ltEntity.add(getEntityFromCursor(cur));
        }
        return ltEntity;
    }
    
    @Override
    public void appendCache(List<StoreTypeEntity> ltEntity) {
    	// TODO Auto-generated method stub
        for (StoreTypeEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }
    
    @Override
    public void updateCache(List<StoreTypeEntity> ltEntity) {
    	// TODO Auto-generated method stub
        super.delAll();
        for (StoreTypeEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }

    /**
     * 获取全部
     * @return
     */
    public List<StoreTypeEntity> getAll()
    {
        // TODO Auto-generated method stub
        Cursor cur = getAll("*");
        List<StoreTypeEntity> ltEntity = new ArrayList<StoreTypeEntity>();
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
    private StoreTypeEntity getEntityFromCursor(Cursor cur)
    {
        // TODO Auto-generated method stub

        StoreTypeEntity tEntity = new StoreTypeEntity();
        tEntity.ID = mHelper.getUUID(cur, "ID");

        tEntity.Name = mHelper.getString(cur, "Name");
        tEntity.pType = mHelper.getInt(cur, "pType");

        return tEntity;
    }

}
