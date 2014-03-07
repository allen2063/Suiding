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
     * �����ض��ӿ� ���»���
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
     * �����ض��ӿ� ׷�ӻ���
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
     * ��ȡȫ��
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
     * ��Cursor�й��������ֶ� ���� Entity
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
