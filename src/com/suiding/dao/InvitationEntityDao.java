package com.suiding.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.suiding.dao.framework.StoreBaseDao;
import com.suiding.entity.InvitationEntity;


public class InvitationEntityDao extends StoreBaseDao 
{
    public InvitationEntityDao(Context context)
    {
        super(context, InvitationEntity.class);
    }

    public void add(InvitationEntity obj)
    {
        super.add(obj);
    }

    public List<InvitationEntity> getAll()
    {
        Cursor cur = getAll("*");
        List<InvitationEntity> ltEntity = new ArrayList<InvitationEntity>();
        while (cur.moveToNext())
        {
            ltEntity.add(getEntityFromCursor(cur));
        }
        return ltEntity;
    }

    /**
     * 从Cursor中构造所以字段 返回 Entity
     * 
     * @param cur
     * @return
     */
    protected InvitationEntity getEntityFromCursor(Cursor cur)
    {
        // TODO Auto-generated method stub
    	InvitationEntity tEntity = new InvitationEntity();

        tEntity.ID = mHelper.getUUID(cur, "ID");
        tEntity.User_ID = mHelper.getUUID(cur, "User_ID");
        tEntity.From_ID = mHelper.getUUID(cur, "From_ID");
        tEntity.Name = mHelper.getString(cur, "Name");
        tEntity.Content = mHelper.getString(cur, "Content");
        tEntity.Remark = mHelper.getString(cur, "Remark");
        tEntity.Date = mHelper.getDate(cur, "Date");
        
        super.getEntityFromCursor(cur, tEntity);
        return tEntity;
    }
}
