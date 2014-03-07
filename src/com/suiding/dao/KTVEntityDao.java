package com.suiding.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.suiding.dao.framework.Cacheable;
import com.suiding.dao.framework.StoreBaseDao;
import com.suiding.entity.KTVEntity;


public class KTVEntityDao extends StoreBaseDao implements Cacheable<List<KTVEntity>>
{
    public KTVEntityDao(Context context)
    {
        // TODO Auto-generated method stub
        super(context, KTVEntity.class);
    }

    public void add(KTVEntity obj)
    {
        // TODO Auto-generated method stub
        super.add(obj);
    }

    /**
     * �����ض��ӿ� ���»���
     * @param ltEntity
     */
    public void updateCache(List<KTVEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        super.delAll();
        for (KTVEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }
    /**
     * �����ض��ӿ� ׷�ӻ���
     * @param ltEntity
     */
    public void appendCache(List<KTVEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        for (KTVEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }
    
    /**
     * ��ȡȫ��
     * @return
     */
    public List<KTVEntity> getAll()
    {
        // TODO Auto-generated method stub
        Cursor cur = getAll("*");
        List<KTVEntity> ltEntity = new ArrayList<KTVEntity>();
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
    private KTVEntity getEntityFromCursor(Cursor cur)
    {
        // TODO Auto-generated method stub
        KTVEntity tEntity = new KTVEntity();
        tEntity.ID = mHelper.getUUID(cur, "ID");
        super.getEntityFromCursor(cur, tEntity);
        return tEntity;
    }
}
