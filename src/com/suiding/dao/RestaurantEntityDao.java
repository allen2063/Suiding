package com.suiding.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.suiding.dao.framework.StoreBaseDao;
import com.suiding.entity.RestaurantEntity;


public class RestaurantEntityDao extends StoreBaseDao
{
    public RestaurantEntityDao(Context context)
    {
        // TODO Auto-generated method stub
        super(context, RestaurantEntity.class);
    }

    public void Add(RestaurantEntity obj)
    {
        // TODO Auto-generated method stub
        super.add(obj);
    }

    /**
     * �����ض��ӿ� ���»���
     * 
     * @param ltEntity
     */
    public void updateCache(List<RestaurantEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        super.delAll();
        for (RestaurantEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }

    /**
     * �����ض��ӿ� ׷�ӻ���
     * 
     * @param ltEntity
     */
    public void appendCache(List<RestaurantEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        for (RestaurantEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }

    /**
     * ��ȡȫ��
     * 
     * @return
     */
    public List<RestaurantEntity> getAll()
    {
        // TODO Auto-generated method stub
        Cursor cur = getAll("*");
        List<RestaurantEntity> ltEntity = new ArrayList<RestaurantEntity>();
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
    private RestaurantEntity getEntityFromCursor(Cursor cur)
    {
        // TODO Auto-generated method stub
        RestaurantEntity tEntity = new RestaurantEntity();
        tEntity.ID = mHelper.getUUID(cur, "ID");
        tEntity.Level = mHelper.getInt(cur, "TsrLevel");
        super.getEntityFromCursor(cur, tEntity);
        return tEntity;
    }
}
