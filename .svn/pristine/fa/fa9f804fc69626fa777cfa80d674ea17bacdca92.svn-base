package com.suiding.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.suiding.dao.framework.Cacheable;
import com.suiding.dao.framework.StoreEntityDao;
import com.suiding.entity.FavoriteEntity;


public class FavoriteEntityDao extends StoreEntityDao implements
        Cacheable<List<FavoriteEntity>>
{

    public FavoriteEntityDao(Context context)
    {
        // TODO Auto-generated method stub
        super(context, FavoriteEntity.class);
    }

    public void add(FavoriteEntity obj)
    {
        // TODO Auto-generated method stub
        super.add(obj);
    }

    /**
     * 根据ID判断是否存在，不存在则添加
     * @param obj
     */
    public void addWithCheckExistID(FavoriteEntity obj){
        // TODO Auto-generated method stub
        //如果不存在 则添加
        if(exist(obj) == false){
            super.add(obj);
        }
    }
    
    @Override
    public void updateCache(List<FavoriteEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        super.delAll();
        this.appendCache(ltEntity);
    }

    @Override
    public void appendCache(List<FavoriteEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        for (FavoriteEntity entity : ltEntity)
        {
            addWithCheckExistID(entity);
        }
    }
    /**
     * 检测收藏
     */
    public boolean exist(FavoriteEntity entity)
    {
        // TODO Auto-generated method stub
        Cursor cur = super.getWhere("SB_ID","SB_ID='"+entity.SB_ID+"'",1,0);
        return cur.moveToNext();
    }
    /**
     * 删除收藏
     */
    public void remove(FavoriteEntity entity)
    {
        // TODO Auto-generated method stub
        super.delWhere("SB_ID='"+entity.SB_ID+"'");
    }
    /**
     * 获取全部
     * @return
     */
    public List<FavoriteEntity> getAll()
    {
        // TODO Auto-generated method stub
        Cursor cur = getAll("*");
        List<FavoriteEntity> ltEntity = new ArrayList<FavoriteEntity>();
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
    private FavoriteEntity getEntityFromCursor(Cursor cur)
    {
        // TODO Auto-generated method stub
        FavoriteEntity tEntity = new FavoriteEntity();
        super.getEntityFromCursor(cur, tEntity);
        return tEntity;
    }

}
