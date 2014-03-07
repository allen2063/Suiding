package com.suiding.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.suiding.dao.framework.DaoBase;
import com.suiding.entity.AreaEntity;

public class AreaEntityDao extends DaoBase
{
    public AreaEntityDao(Context context){
        // TODO Auto-generated method stub
        super(context, AreaEntity.class);
    }

    public void add(AreaEntity obj){
        // TODO Auto-generated method stub
        super.add(obj);
    }
    
    /**
     * 根据ID判断是否存在，不存在则添加
     * @param obj
     */
    public void addWithCheckExistID(AreaEntity obj){
        // TODO Auto-generated method stub
        //先查询ID = obj.ID
        Cursor cur = getWhere("ID", "ID="+obj.ID,1,0);
        //如果不存在 则添加
        if(cur.moveToNext() == false){
            super.add(obj);
        }
    }

    public List<AreaEntity> getAllCity()
    {
        // TODO Auto-generated method stub
        Cursor cur = getWhere("*", "Level=2");
        List<AreaEntity> ltEntity = new ArrayList<AreaEntity>();
        while (cur.moveToNext())
        {
            ltEntity.add(getEntityFromCursor(cur));
        }
        return ltEntity;
    }

    public void addAllWithCheckExistID(List<AreaEntity> mltAllCity)
    {
        // TODO Auto-generated method stub
        for (AreaEntity tEntity : mltAllCity)
        {
            addWithCheckExistID(tEntity);
        }
    }

    public AreaEntity getByNameLike(String name)
    {
        // TODO Auto-generated method stub
        Cursor cur = getWhere("*", "Name like '"+name+"%'",1,0);
        if (cur.moveToNext())
        {
            return getEntityFromCursor(cur);
        }
        return null;
    }

	public AreaEntity getParent(AreaEntity tAreaEntity) {
		// TODO Auto-generated method stub
        Cursor cur = getWhere("*", "ID="+tAreaEntity.UpID+"",1,0);
        if (cur.moveToNext())
        {
            return getEntityFromCursor(cur);
        }
        return null;
	}
    public List<AreaEntity> getChildren(AreaEntity tAreaEntity)
    {
        // TODO Auto-generated method stub
        Cursor cur = getWhere("*", "UpID="+tAreaEntity.ID);
        List<AreaEntity> ltEntity = new ArrayList<AreaEntity>();
        while (cur.moveToNext())
        {
            ltEntity.add(getEntityFromCursor(cur));
        }
        return ltEntity;
    }
    /**
     * 根据ID获取AreaEntity
     * @return
     */
    public AreaEntity getById(int id)
    {
        // TODO Auto-generated method stub
        Cursor cur = getWhere("*", "ID="+id,1,0);
        if (cur.moveToNext())
        {
            return getEntityFromCursor(cur);
        }
        return null;
    }
    
    /**
     * 获取所有AreaEntity 按ID排序
     * @return
     */
    public List<AreaEntity> getAllOrderById()
    {
        // TODO Auto-generated method stub
        Cursor cur = getAll("*","ID");
        List<AreaEntity> ltEntity = new ArrayList<AreaEntity>();
        while (cur.moveToNext())
        {
            ltEntity.add(getEntityFromCursor(cur));
        }
        return ltEntity;
    }

    /**
     * 获取Address服务列表
     * @return
     */
    public List<String> getAddressServerList()
    {
        Cursor cur = getAll("ID,Name","ID");
        List<String> ltAddress = new ArrayList<String>();
        while (cur.moveToNext())
        {
            AreaEntity entity = getEntityFromCursor(cur);
            while(ltAddress.size() < entity.ID - 1)
            {
                ltAddress.add("空地址");
            }
            ltAddress.add(entity.Name);
        }
        return ltAddress;
    }
    /**
     * 从Cursor中构造所有字段 返回 Entity
     * @param cur
     * @return
     */
    private AreaEntity getEntityFromCursor(Cursor cur){
        // TODO Auto-generated method stub
        AreaEntity tEntity = new AreaEntity();
        tEntity.ID = mHelper.getInt(cur,"ID");
        tEntity.Name = mHelper.getString(cur,"Name");
        tEntity.Sell = mHelper.getString(cur,"Sell");
        tEntity.Level = mHelper.getInt(cur,"Level");
        tEntity.UpID = mHelper.getInt(cur,"UpID");
        return tEntity;
    }


}
