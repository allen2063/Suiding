package com.suiding.entity;

import java.util.ArrayList;
import java.util.List;

import com.suiding.entity.framework.EntityBase;
import com.suiding.entity.framework.StoreEntity;
import com.suiding.model.StoreBase;

public class IndustryEntity extends StoreEntity implements EntityBase<StoreBase>
{
    
    public IndustryEntity()
    {
        // TODO Auto-generated constructor stub
    }
    

    public IndustryEntity(StoreBase model)
    {
        // TODO Auto-generated constructor stub
        super(model);
    }

    /**
     * List<Model> ×ª»» List<Entity> 
     * @param ltEntity
     * @return
     */
    public static List<IndustryEntity> listFormModel(List<StoreBase> ltModel)
    {
        // TODO Auto-generated method stub
        List<IndustryEntity> ltEntity = new ArrayList<IndustryEntity>();
        for (StoreBase model : ltModel)
        {
            ltEntity.add(new IndustryEntity(model));
        }
        return ltEntity;
    }
    /**
     * List<Entity> ×ª»» List<Model> 
     * @param ltEntity
     * @return
     */
    public static List<StoreBase> listToModel(List<IndustryEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        List<StoreBase> ltModel = new ArrayList<StoreBase>();
        for (IndustryEntity entity : ltEntity)
        {
            ltModel.add(entity.getModel());
        }
        return ltModel;
    }
}
