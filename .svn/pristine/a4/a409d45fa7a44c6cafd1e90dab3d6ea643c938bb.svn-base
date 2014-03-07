package com.suiding.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.suiding.entity.framework.EntityBase;
import com.suiding.model.BirthFavorite;
import com.suiding.util.UUIDUtil;

public class BirthdayEntity implements EntityBase<BirthFavorite>
{
    public UUID ID = UUIDUtil.Empty;
    public UUID User_ID = UUIDUtil.Empty;
    public String Name = "";
    public String PhoneNo = "";
    public String HeadImg = "";
    public String City = "";
    public String Area = "";
    public String Remark = "";
    public boolean Sex = false;
    public Date Birth = new Date();
	public boolean IsLunarCalendar = false;
    
    public BirthdayEntity()
    {
        // TODO Auto-generated constructor stub
    }
    

    public BirthdayEntity(BirthFavorite model)
    {
        // TODO Auto-generated constructor stub
        if(model != null)
        {
            this.ID = model.getID();
            this.User_ID = model.User_ID;
            this.Name = model.Name;
            this.PhoneNo = model.PhoneNo;
            this.HeadImg = model.HeadImg;
            this.Remark = model.Remark;
            this.City = model.City;
            this.Sex = model.Sex;
            this.Area = model.Area;
            this.Birth = model.Birth;
            this.IsLunarCalendar = model.IsLunarCalendar;
        }
    }

    public BirthFavorite getModel()
    {
        // TODO Auto-generated method stub
        BirthFavorite model = new BirthFavorite();
        
        model.setID(ID);

        model.User_ID = this.User_ID;
        model.Name = this.Name;
        model.Birth = this.Birth;
        model.PhoneNo = this.PhoneNo;
        model.Remark = this.Remark;
        model.HeadImg = this.HeadImg;
        model.Name = this.Name;
        model.City = this.City;
        model.Sex = this.Sex;
        model.Area = this.Area;
        model.IsLunarCalendar = this.IsLunarCalendar;

        return model;
    }
    /**
     * List<Model> ×ª»» List<Entity> 
     * @param ltEntity
     * @return
     */
    public static List<BirthdayEntity> listFormModel(List<BirthFavorite> ltModel)
    {
        // TODO Auto-generated method stub
        List<BirthdayEntity> ltEntity = new ArrayList<BirthdayEntity>();
        for (BirthFavorite model : ltModel)
        {
            ltEntity.add(new BirthdayEntity(model));
        }
        return ltEntity;
    }
    /**
     * List<Entity> ×ª»» List<Model> 
     * @param ltEntity
     * @return
     */
    public static List<BirthFavorite> listToModel(List<BirthdayEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        List<BirthFavorite> ltModel = new ArrayList<BirthFavorite>();
        for (BirthdayEntity entity : ltEntity)
        {
            ltModel.add(entity.getModel());
        }
        return ltModel;
    }
}
