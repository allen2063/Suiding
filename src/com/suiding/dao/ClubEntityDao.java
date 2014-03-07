package com.suiding.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.suiding.dao.framework.Cacheable;
import com.suiding.dao.framework.StoreBaseDao;
import com.suiding.entity.ClubEntity;


public class ClubEntityDao extends StoreBaseDao implements Cacheable<List<ClubEntity>>
{
    public ClubEntityDao(Context context)
    {
        // TODO Auto-generated method stub
        super(context, ClubEntity.class);
    }

    public void add(ClubEntity obj)
    {
        // TODO Auto-generated method stub
        super.add(obj);
    }

    /**
     * 缓存特定接口 更新缓存
     * @param ltEntity
     */
    public void updateCache(List<ClubEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        super.delAll();
        for (ClubEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }
    /**
     * 缓存特定接口 追加缓存
     * @param ltEntity
     */
    public void appendCache(List<ClubEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        for (ClubEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }

    /**
     * 获取全部
     * @return
     */
    public List<ClubEntity> getAll()
    {
        // TODO Auto-generated method stub
        Cursor cur = getAll("*");
        List<ClubEntity> ltEntity = new ArrayList<ClubEntity>();
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
    private ClubEntity getEntityFromCursor(Cursor cur)
    {
        // TODO Auto-generated method stub
        ClubEntity tEntity = new ClubEntity();
        tEntity.ID = mHelper.getUUID(cur, "ID");
        super.getEntityFromCursor(cur, tEntity);
        return tEntity;
    }
}
