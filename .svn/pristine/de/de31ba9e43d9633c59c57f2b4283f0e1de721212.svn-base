package com.suiding.dao;

import java.util.ArrayList;
import java.util.List;

import com.suiding.dao.framework.DaoBase;
import com.suiding.entity.LocationEntity;

import android.content.Context;
import android.database.Cursor;


public class LocationEntityDao extends DaoBase
{

    public LocationEntityDao(Context context)
    {
        // TODO Auto-generated method stub
        super(context, LocationEntity.class);
    }

    public void add(LocationEntity obj)
    {
        // TODO Auto-generated method stub
        super.add(obj);
    }

    /**
     * 根据定位 Location 的特殊需求 数据库只保留上次定位结果
     * 
     * @param obj
     */
    public void updateCache(LocationEntity obj)
    {
        // TODO Auto-generated method stub
        super.delAll();
        super.add(obj);
    }

    /**
     * 根据定位 Location 的特殊需求 数据库只保留上次定位结果
     * 
     * @param obj
     */
    public LocationEntity getCache()
    {
        // TODO Auto-generated method stub
        Cursor cur = getLimit("*", 1, 0);
        if (cur.moveToNext())
        {
            return getEntityFromCursor(cur);
        }
        return null;
    }

    /**
     * 获取全部
     * @return
     */
    public List<LocationEntity> getAll()
    {
        // TODO Auto-generated method stub
        Cursor cur = getAll("*");
        List<LocationEntity> ltEntity = new ArrayList<LocationEntity>();
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
    private LocationEntity getEntityFromCursor(Cursor cur)
    {
        // TODO Auto-generated method stub

        LocationEntity tEntity = new LocationEntity();

        tEntity.Latitude = mHelper.getDouble(cur, "Latitude");
        tEntity.Longitude = mHelper.getDouble(cur, "Longitude");
        tEntity.Altitude = mHelper.getDouble(cur, "Altitude");
        tEntity.Accuracy = mHelper.getFloat(cur, "Accuracy");
        tEntity.Bearing = mHelper.getFloat(cur, "Bearing");
        tEntity.Speed = mHelper.getFloat(cur, "Speed");
        tEntity.Time = mHelper.getLong(cur, "Time");
        tEntity.Provider = mHelper.getString(cur, "Provider");

        return tEntity;
    }
}
