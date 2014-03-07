package com.suiding.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.database.Cursor;

import com.suiding.dao.framework.Cacheable;
import com.suiding.dao.framework.DaoBase;
import com.suiding.entity.BirthdayEntity;


public class BirthdayEntityDao extends DaoBase implements Cacheable<List<BirthdayEntity>>
{
    public BirthdayEntityDao(Context context)
    {
        // TODO Auto-generated method stub
        super(context, BirthdayEntity.class);
    }

    public void add(BirthdayEntity obj)
    {
        // TODO Auto-generated method stub
        super.add(obj);
    }

	public void delById(UUID id) {
		// TODO Auto-generated method stub
		super.delWhere("ID='"+id+"'");
	}
	
    public boolean update(BirthdayEntity obj)
    {
        // TODO Auto-generated method stub
        return super.update(obj,"ID='"+obj.ID+"'");
    }

    /**
     * 缓存特定接口 更新缓存
     * @param ltEntity
     */
    public void updateCache(List<BirthdayEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        super.delAll();
        for (BirthdayEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }
    /**
     * 缓存特定接口 追加缓存
     * @param ltEntity
     */
    public void appendCache(List<BirthdayEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        for (BirthdayEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }

    public List<BirthdayEntity> getAll()
    {
        // TODO Auto-generated method stub
        Cursor cur = getAll("*");
        List<BirthdayEntity> ltEntity = new ArrayList<BirthdayEntity>();
        while (cur.moveToNext())
        {
            ltEntity.add(getEntityFromCursor(cur));
        }
        return ltEntity;
    }

    /**
     * 从Cursor中构造所有字段 返回 Entity
     * @param cur
     * @return
     */
    private BirthdayEntity getEntityFromCursor(Cursor cur)
    {
        // TODO Auto-generated method stub
        BirthdayEntity tEntity = new BirthdayEntity();
        tEntity.ID = mHelper.getUUID(cur, "ID");
        tEntity.User_ID = mHelper.getUUID(cur, "User_ID");
        tEntity.Name = mHelper.getString(cur, "Name");
        tEntity.PhoneNo = mHelper.getString(cur, "PhoneNo");
        tEntity.Remark = mHelper.getString(cur, "Remark");
        tEntity.City = mHelper.getString(cur, "City");
        tEntity.Area = mHelper.getString(cur, "Area");
        tEntity.HeadImg = mHelper.getString(cur, "HeadImg");
        tEntity.Birth = mHelper.getDate(cur, "Birth");
        tEntity.Sex = mHelper.getBoolean(cur, "Sex");
        tEntity.IsLunarCalendar = mHelper.getBoolean(cur, "IsLunarCalendar");
        return tEntity;
    }

}
