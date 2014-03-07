package com.suiding.dao.framework;


import android.content.Context;
import android.database.Cursor;

import com.suiding.entity.framework.AddressEntity;

public abstract class AddressDao extends DaoBase
{
    public AddressDao(Context context,Class<?> table)
    {
        // TODO Auto-generated method stub
        super(context, table);
    }
    
    /**
     * 从Cursor中构造所以字段 返回 Entity
     * 
     * @param cur
     * @return
     */
    protected AddressEntity getEntityFromCursor(Cursor cur,AddressEntity tEntity)
    {
        // TODO Auto-generated method stub
        tEntity.ADR_ID = mHelper.getUUID(cur, "ADR_ID");
        tEntity.ADR_Contry = mHelper.getInt(cur, "ADR_Contry");
        tEntity.ADR_Province = mHelper.getInt(cur, "ADR_Province");
        tEntity.ADR_City = mHelper.getInt(cur, "ADR_City");
        tEntity.ADR_Xian = mHelper.getInt(cur, "ADR_Xian");
        tEntity.ADR_Xiang = mHelper.getInt(cur, "ADR_Xiang");
        tEntity.ADR_Cun = mHelper.getInt(cur, "ADR_Cun");
        tEntity.ADR_Custom = mHelper.getString(cur, "ADR_Custom");
        tEntity.ADR_PostionX = mHelper.getFloat(cur, "ADR_PostionX");
        tEntity.ADR_PostionY = mHelper.getFloat(cur, "ADR_PostionY");

        return tEntity;
    }
}
