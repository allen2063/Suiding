package com.suiding.entity;

import java.util.ArrayList;
import java.util.List;

import com.suiding.entity.framework.StoreEntity;
import com.suiding.model.StoreBase;

public class FavoriteEntity extends StoreEntity
{
    public FavoriteEntity()
    {
        // TODO Auto-generated constructor stub
    }
    

    public FavoriteEntity(StoreBase store)
    {
        // TODO Auto-generated constructor stub
        super(store);
    }

    /**
     * List<Model> ×ª»» List<Entity> 
     * @param ltEntity
     * @return
     */
    public static List<FavoriteEntity> listFormModel(List<StoreBase> ltModel)
    {
        // TODO Auto-generated method stub
        List<FavoriteEntity> ltEntity = new ArrayList<FavoriteEntity>();
        for (StoreBase model : ltModel)
        {
            ltEntity.add(new FavoriteEntity(model));
        }
        return ltEntity;
    }
    /**
     * List<Entity> ×ª»» List<Model> 
     * @param ltEntity
     * @return
     */
    public static List<StoreBase> listToModel(List<FavoriteEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        List<StoreBase> ltModel = new ArrayList<StoreBase>();
        for (FavoriteEntity entity : ltEntity)
        {
            ltModel.add(entity.getModel());
        }
        return ltModel;
    }
}
