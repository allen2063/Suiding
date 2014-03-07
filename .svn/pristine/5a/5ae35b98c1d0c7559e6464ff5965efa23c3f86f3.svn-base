package com.suiding.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.suiding.dao.framework.Cacheable;
import com.suiding.dao.framework.StoreEntityDao;
import com.suiding.entity.IndustryEntity;


public class IndustryEntityDao extends StoreEntityDao implements
        Cacheable<List<IndustryEntity>>
{

    public IndustryEntityDao(Context context)
    {
        // TODO Auto-generated method stub
        super(context, IndustryEntity.class);
    }

    public void add(IndustryEntity obj)
    {
        // TODO Auto-generated method stub
        super.add(obj);
    }

    @Override
    public void updateCache(List<IndustryEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        super.delAll();
        this.appendCache(ltEntity);
    }

    @Override
    public void appendCache(List<IndustryEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        for (IndustryEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }

    /**
     * 获取全部
     * 
     * @return
     */
    public List<IndustryEntity> getAll()
    {
        // TODO Auto-generated method stub
        Cursor cur = getAll("*");
        List<IndustryEntity> ltEntity = new ArrayList<IndustryEntity>();
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
    private IndustryEntity getEntityFromCursor(Cursor cur)
    {
        // TODO Auto-generated method stub
        IndustryEntity tEntity = new IndustryEntity();
        super.getEntityFromCursor(cur, tEntity);
        return tEntity;
    }
}
