package com.suiding.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.suiding.entity.framework.EntityBase;
import com.suiding.entity.framework.StoreBaseEntity;
import com.suiding.model.Restaurant;
import com.suiding.util.UUIDUtil;


public class RestaurantEntity extends StoreBaseEntity implements EntityBase<Restaurant>
{
    //Restaurant ÊôÐÔ
    public int Level;
    public UUID ID = UUIDUtil.Empty;

    public RestaurantEntity()
    {
    }

    public RestaurantEntity(Restaurant tRestaurant)
    {
        super(tRestaurant);
        this.ID = tRestaurant.getRsr_ID();
        this.Level = tRestaurant.Level;

    }

    public Restaurant getModel()
    {
        Restaurant tRestaurant = new Restaurant(super.getStoreBase());
        tRestaurant.setRsr_ID(ID);
        tRestaurant.Level = this.Level;
        return tRestaurant;
    }

    /**
     * List<Model> ×ª»» List<Entity>
     * 
     * @param ltEntity
     * @return
     */
    public static List<RestaurantEntity> listFormModel(List<Restaurant> ltModel)
    {
        // TODO Auto-generated method stub
        List<RestaurantEntity> ltEntity = new ArrayList<RestaurantEntity>();
        for (Restaurant model : ltModel)
        {
            ltEntity.add(new RestaurantEntity(model));
        }
        return ltEntity;
    }

    /**
     * List<Entity> ×ª»» List<Model>
     * 
     * @param ltEntity
     * @return
     */
    public static List<Restaurant> listToModel(List<RestaurantEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        List<Restaurant> ltModel = new ArrayList<Restaurant>();
        for (RestaurantEntity entity : ltEntity)
        {
            ltModel.add(entity.getModel());
        }
        return ltModel;
    }
}
