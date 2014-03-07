package com.suiding.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.database.Cursor;

import com.suiding.dao.framework.DaoBase;
import com.suiding.entity.PhotoEntity;


public class PhotoEntityDao extends DaoBase
{
    public PhotoEntityDao(Context context)
    {
        // TODO Auto-generated method stub
        super(context, PhotoEntity.class);
    }

    public void add(PhotoEntity obj)
    {
        // TODO Auto-generated method stub
        super.add(obj);
    }

    public List<PhotoEntity> getByForId(UUID ID)
    {
        // TODO Auto-generated method stub
        Cursor cur = getWhere("*", "ForID='" + ID + "'");
        List<PhotoEntity> ltEntity = new ArrayList<PhotoEntity>();
        while (cur.moveToNext())
        {
            ltEntity.add(getEntityFromCursor(cur));
        }
        return ltEntity;
    }

    public void getUpdateForId(UUID ID, List<PhotoEntity> listFormModel)
    {
        // TODO Auto-generated method stub
        delWhere("ForID='" + ID + "'");
        for (PhotoEntity entity : listFormModel)
        {
            add(entity);
        }
    }

    /**
     * 获取全部
     * @return
     */
    public List<PhotoEntity> getAll()
    {
        // TODO Auto-generated method stub
        Cursor cur = getAll("*");
        List<PhotoEntity> ltEntity = new ArrayList<PhotoEntity>();
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
    private PhotoEntity getEntityFromCursor(Cursor cur)
    {
        // TODO Auto-generated method stub

        PhotoEntity tEntity = new PhotoEntity();
        tEntity.ID = mHelper.getUUID(cur, "ID");

        tEntity.Name = mHelper.getString(cur, "Name");
        tEntity.Url = mHelper.getString(cur, "Url");
        tEntity.Remark = mHelper.getString(cur, "Remark");
        tEntity.Describe = mHelper.getString(cur, "Describe");
        tEntity.Pid = mHelper.getUUID(cur, "Pid");
        tEntity.ForID = mHelper.getUUID(cur, "ForID");

        return tEntity;
    }

}
