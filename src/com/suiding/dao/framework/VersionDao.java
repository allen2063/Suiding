package com.suiding.dao.framework;


import android.content.Context;
import android.database.Cursor;

import com.suiding.entity.framework.VersionEntity;

public class VersionDao extends DaoBase
{
    public VersionDao(Context context)
    {
        // TODO Auto-generated method stub
        super(context, VersionEntity.class);
    }
    
    public VersionEntity getVersion()
    {
        // TODO Auto-generated method stub
        Cursor cur = getLimit("*",1,0);
        if(cur.moveToNext())
        {
            return getEntityFromCursor(cur);
        }else{
            //数据库没有数据 说明刚刚创建 添加版本信息
            VersionEntity tEntity = new VersionEntity();
            add(tEntity);
            return tEntity;
        }
    }
    /**
     * 从Cursor中构造所以字段 返回 Entity
     * 
     * @param cur
     * @return
     */
    protected VersionEntity getEntityFromCursor(Cursor cur)
    {
        // TODO Auto-generated method stub
        VersionEntity tEntity = new VersionEntity();
        tEntity.Version = mHelper.getInt(cur, "Version");
        return tEntity;
    }
}
