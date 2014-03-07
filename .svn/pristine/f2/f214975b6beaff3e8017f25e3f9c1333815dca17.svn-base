package com.suiding.dao.framework;


import android.content.Context;
import android.database.Cursor;

import com.suiding.entity.framework.StoreBaseEntity;

public abstract class StoreBaseDao extends AddressDao
{
    public StoreBaseDao(Context context,Class<?> table)
    {
        // TODO Auto-generated method stub
        super(context, table);
    }

    public void add(StoreBaseEntity obj)
    {
        // TODO Auto-generated method stub
        super.add(obj);
    }

    /**
     * 从Cursor中构造所以字段 返回 Entity
     * 
     * @param cur
     * @return
     */
    protected StoreBaseEntity getEntityFromCursor(Cursor cur,StoreBaseEntity tEntity)
    {
        // TODO Auto-generated method stub
        super.getEntityFromCursor(cur, tEntity);
        
        tEntity.SB_ID = mHelper.getUUID(cur, "SB_ID");

        tEntity.SB_Name = mHelper.getString(cur, "SB_Name");
        tEntity.SB_HeadImg = mHelper.getString(cur, "SB_HeadImg");
        tEntity.SB_Telephone = mHelper.getString(cur, "SB_Telephone");
        tEntity.SB_BeginBss = mHelper.getDate(cur, "SB_BeginBss");
        tEntity.SB_EndBss = mHelper.getDate(cur, "SB_EndBss");
        tEntity.SB_StopMSG = mHelper.getString(cur, "SB_StopMSG");
        tEntity.SB_NetServer = mHelper.getString(cur, "SB_NetServer");
        tEntity.SB_Abstract = mHelper.getString(cur, "SB_Abstract");
        tEntity.SB_IsOpen = mHelper.getBoolean(cur, "SB_IsOpen");
        tEntity.SB_IsRecommended = mHelper.getBoolean(cur, "SB_IsRecommended");

        tEntity.SB_Type = mHelper.getInt(cur,"SB_Type");
        tEntity.SB_Price = mHelper.getDouble(cur,"SB_Price");
        tEntity.SB_NowPrice = mHelper.getDouble(cur,"SB_NowPrice");
        tEntity.SB_Scores = mHelper.getFloat(cur,"SB_Scores");
        tEntity.SB_LastDate = mHelper.getDate(cur,"SB_LastDate");
        tEntity.SB_IsBusying = mHelper.getBoolean(cur,"SB_IsBusying");
        
        tEntity.SB_HasPark = mHelper.getBoolean(cur,"SB_HasPark");
        tEntity.SB_HasToilet = mHelper.getBoolean(cur,"SB_HasToilet");
        tEntity.SB_HasWifi = mHelper.getBoolean(cur,"SB_HasWifi");

        tEntity.SB_Promotions = mHelper.getString(cur,"SB_Promotions");

        tEntity.SB_LimitDate = mHelper.getInt(cur,"SB_LimitDate");
        tEntity.SB_Cellphone = mHelper.getString(cur,"SB_Cellphone");
        
        return tEntity;
    }
}
