package com.suiding.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.suiding.entity.framework.EntityBase;
import com.suiding.entity.framework.StoreBaseEntity;
import com.suiding.model.Stall;
import com.suiding.util.UUIDUtil;


public class StallEntity extends StoreBaseEntity implements EntityBase<Stall>
{
    //Stall ÊôÐÔ
    public UUID ID = UUIDUtil.Empty;

    public StallEntity()
    {
    }

    public StallEntity(Stall tStall)
    {
        super(tStall);
        this.ID = tStall.getSta_ID();
    }

    public Stall getModel()
    {
        Stall tStall = new Stall(super.getStoreBase());
        tStall.setSta_ID(ID);
        return tStall;
    }

    /**
     * List<Model> ×ª»» List<Entity>
     * 
     * @param ltEntity
     * @return
     */
    public static List<StallEntity> listFormModel(List<Stall> ltModel)
    {
        // TODO Auto-generated method stub
        List<StallEntity> ltEntity = new ArrayList<StallEntity>();
        for (Stall model : ltModel)
        {
            ltEntity.add(new StallEntity(model));
        }
        return ltEntity;
    }

    /**
     * List<Entity> ×ª»» List<Model>
     * 
     * @param ltEntity
     * @return
     */
    public static List<Stall> listToModel(List<StallEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        List<Stall> ltModel = new ArrayList<Stall>();
        for (StallEntity entity : ltEntity)
        {
            ltModel.add(entity.getModel());
        }
        return ltModel;
    }
}
