package com.suiding.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.suiding.entity.framework.EntityBase;
import com.suiding.model.StoreType;
import com.suiding.util.UUIDUtil;

public class StoreTypeEntity  implements EntityBase<StoreType>{

	public int pType = 0;
	public String Name = "";
	public UUID ID = UUIDUtil.Empty;

	public StoreTypeEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public StoreTypeEntity(StoreType model) {
		// TODO Auto-generated constructor stub
		this.ID = model.getID();
		this.pType = model.pType;
		this.Name = model.Name;
	}
	

	@Override
	public StoreType getModel() {
		// TODO Auto-generated method stub
		StoreType model = new StoreType(Name, ID);
		model.pType = pType;
		return model;
	}

    /**
     * List<Model> ×ª»» List<Entity> 
     * @param ltEntity
     * @return
     */
    public static List<StoreTypeEntity> listFormModel(List<StoreType> ltModel)
    {
        // TODO Auto-generated method stub
        List<StoreTypeEntity> ltEntity = new ArrayList<StoreTypeEntity>();
        for (StoreType model : ltModel)
        {
            ltEntity.add(new StoreTypeEntity(model));
        }
        return ltEntity;
    }
    /**
     * List<Entity> ×ª»» List<Model> 
     * @param ltEntity
     * @return
     */
    public static List<StoreType> listToModel(List<StoreTypeEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        List<StoreType> ltModel = new ArrayList<StoreType>();
        for (StoreTypeEntity entity : ltEntity)
        {
            ltModel.add(entity.getModel());
        }
        return ltModel;
    }
}
