package com.suiding.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.suiding.entity.framework.EntityBase;
import com.suiding.entity.framework.StoreBaseEntity;
import com.suiding.model.Twitter;
import com.suiding.util.UUIDUtil;

public class TwitterEntity extends StoreBaseEntity implements EntityBase<Twitter>
{
    public UUID ID = UUIDUtil.Empty;
    public UUID For_ID = UUIDUtil.Empty;
    public String tName = "";
    public String Content = "";
    public String Remark = "";
    public Date Date = new Date();
    
    public TwitterEntity()
    {
        // TODO Auto-generated constructor stub
    }
    

    public TwitterEntity(Twitter model)
    {
        // TODO Auto-generated constructor stub
        super(model!= null?model.StoreBase:null);
        if(model != null)
        {
            this.ID = model.getID();
            this.For_ID = model.For_ID;
            this.tName = model.Name;
            this.Content = model.Content;
            this.Remark = model.Remark;
            this.Date = model.Date;
        }
    }

    public Twitter getModel()
    {
        // TODO Auto-generated method stub
        Twitter model = new Twitter();
        
        model.setID(ID);

        model.For_ID = this.For_ID;
        model.Name = this.tName;
        model.Date = this.Date;
        model.Content = this.Content;
        model.Remark = this.Remark;

        model.StoreBase = super.getStoreBase();

        return model;
    }
    /**
     * List<Model> ×ª»» List<Entity> 
     * @param ltEntity
     * @return
     */
    public static List<TwitterEntity> listFormModel(List<Twitter> ltModel)
    {
        // TODO Auto-generated method stub
        List<TwitterEntity> ltEntity = new ArrayList<TwitterEntity>();
        for (Twitter model : ltModel)
        {
            ltEntity.add(new TwitterEntity(model));
        }
        return ltEntity;
    }
    /**
     * List<Entity> ×ª»» List<Model> 
     * @param ltEntity
     * @return
     */
    public static List<Twitter> listToModel(List<TwitterEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        List<Twitter> ltModel = new ArrayList<Twitter>();
        for (TwitterEntity entity : ltEntity)
        {
            ltModel.add(entity.getModel());
        }
        return ltModel;
    }
}
