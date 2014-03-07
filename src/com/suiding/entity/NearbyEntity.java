package com.suiding.entity;

import java.util.ArrayList;
import java.util.List;

import com.suiding.entity.framework.EntityBase;
import com.suiding.entity.framework.StoreEntity;
import com.suiding.model.StoreBase;

public class NearbyEntity extends StoreEntity implements EntityBase<StoreBase>
{
    
    public NearbyEntity()
    {
        // TODO Auto-generated constructor stub
    }
    

    public NearbyEntity(StoreBase model)
    {
        // TODO Auto-generated constructor stub
        super(model);
    }

    /**
     * List<Model> ×ª»» List<Entity> 
     * @param ltEntity
     * @return
     */
    public static List<NearbyEntity> listFormModel(List<StoreBase> ltModel)
    {
        // TODO Auto-generated method stub
        List<NearbyEntity> ltEntity = new ArrayList<NearbyEntity>();
        for (StoreBase model : ltModel)
        {
            ltEntity.add(new NearbyEntity(model));
        }
        return ltEntity;
    }
    /**
     * List<Entity> ×ª»» List<Model> 
     * @param ltEntity
     * @return
     */
    public static List<StoreBase> listToModel(List<NearbyEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        List<StoreBase> ltModel = new ArrayList<StoreBase>();
        for (NearbyEntity entity : ltEntity)
        {
            ltModel.add(entity.getModel());
        }
        return ltModel;
    }
}
