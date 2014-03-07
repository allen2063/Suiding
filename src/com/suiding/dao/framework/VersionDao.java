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
            //���ݿ�û������ ˵���ոմ��� ��Ӱ汾��Ϣ
            VersionEntity tEntity = new VersionEntity();
            add(tEntity);
            return tEntity;
        }
    }
    /**
     * ��Cursor�й��������ֶ� ���� Entity
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
