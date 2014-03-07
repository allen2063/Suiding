package com.suiding.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.suiding.dao.framework.DaoBase;
import com.suiding.entity.AddressEntity;


public class AddressEntityDao extends DaoBase
{
    public AddressEntityDao(Context context)
    {
        // TODO Auto-generated method stub
        super(context, AddressEntity.class);
    }

    public void Add(AddressEntity obj)
    {
        // TODO Auto-generated method stub
        super.add(obj);
    }

    /**
     * 根据定位 Address 的特殊需求 数据库只保留上次定位结果
     * 
     * @param obj
     */
    public void updateCache(List<AddressEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        super.delAll();
        for (AddressEntity tEntity : ltEntity)
        {
            super.add(tEntity);
        }
    }

    /**
     * 获取全部
     * @return
     */
    public List<AddressEntity> getAll()
    {
        // TODO Auto-generated method stub
        Cursor cur = getAll("*");
        List<AddressEntity> ltEntity = new ArrayList<AddressEntity>();
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
    private AddressEntity getEntityFromCursor(Cursor cur)
    {
        // TODO Auto-generated method stub
        AddressEntity tEntity = new AddressEntity();

        tEntity.Latitude = mHelper.getDouble(cur, "Latitude");
        tEntity.Longitude = mHelper.getDouble(cur, "Longitude");
        tEntity.AdminArea = mHelper.getString(cur, "AdminArea");
        tEntity.CountryCode = mHelper.getString(cur, "CountryCode");
        tEntity.CountryName = mHelper.getString(cur, "CountryName");
        tEntity.FeatureName = mHelper.getString(cur, "FeatureName");
        tEntity.Locality = mHelper.getString(cur, "Locality");
        tEntity.Phone = mHelper.getString(cur, "Phone");
        tEntity.PostalCode = mHelper.getString(cur, "PostalCode");
        tEntity.Premises = mHelper.getString(cur, "Premises");
        tEntity.SubAdminArea = mHelper.getString(cur, "SubAdminArea");
        tEntity.SubLocality = mHelper.getString(cur, "SubLocality");
        tEntity.SubThoroughfare = mHelper.getString(cur, "SubThoroughfare");
        tEntity.Thoroughfare = mHelper.getString(cur, "Thoroughfare");
        tEntity.Url = mHelper.getString(cur, "Url");

        return tEntity;
    }

}
