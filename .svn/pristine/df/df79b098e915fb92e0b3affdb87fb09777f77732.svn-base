package com.suiding.entity;

import java.util.ArrayList;
import java.util.List;

import com.suiding.entity.framework.StoreEntity;
import com.suiding.model.StoreBase;

public class NewsEntity extends StoreEntity
{
    public NewsEntity()
    {
        // TODO Auto-generated constructor stub
    }
    
    public NewsEntity(StoreBase tStoreBase)
    {
        super(tStoreBase);
        // TODO Auto-generated constructor stub
    }


    public StoreBase getStoreBase()
    {
        // TODO Auto-generated method stub
        return super.getStoreBase();
    }

    /**
     * List<Model> ×ª»» List<Entity> 
     * @param ltEntity
     * @return
     */
    public static List<NewsEntity> listFormModel(List<StoreBase> ltModel)
    {
        // TODO Auto-generated method stub
        List<NewsEntity> ltEntity = new ArrayList<NewsEntity>();
        for (StoreBase model : ltModel)
        {
            ltEntity.add(new NewsEntity(model));
        }
        return ltEntity;
    }
    /**
     * List<Entity> ×ª»» List<Model> 
     * @param ltEntity
     * @return
     */
    public static List<StoreBase> listToModel(List<NewsEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        List<StoreBase> ltModel = new ArrayList<StoreBase>();
        for (NewsEntity entity : ltEntity)
        {
            ltModel.add(entity.getModel());
        }
        return ltModel;
    }
    
    
}
