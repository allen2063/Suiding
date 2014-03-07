package com.suiding.dao.framework;

import android.content.Context;
import android.database.Cursor;

import com.suiding.entity.framework.StoreEntity;

public abstract class StoreEntityDao extends StoreBaseDao
{
    public StoreEntityDao(Context context,Class<?> table)
    {
        // TODO Auto-generated method stub
        super(context, table);
    }

    public void add(StoreEntity obj)
    {
        // TODO Auto-generated method stub
        super.add(obj);
    }
    /**
     * 删除
     */
    public void remove(StoreEntity entity)
    {
        // TODO Auto-generated method stub
        super.delWhere("SB_ID='"+entity.SB_ID+"'");
    }
    /**
     * 从Cursor中构造所以字段 返回 Entity
     * 
     * @param cur
     * @return
     */
    protected StoreEntity getEntityFromCursor(Cursor cur,StoreEntity tEntity)
    {
        // TODO Auto-generated method stub
        super.getEntityFromCursor(cur, tEntity);

        tEntity.ID = mHelper.getUUID(cur, "ID");

        tEntity.Level = mHelper.getInt(cur, "Level");
        tEntity.Entity = mHelper.getInt(cur, "Entity");
        
        return tEntity;
    }
}
