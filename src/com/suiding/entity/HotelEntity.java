package com.suiding.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.suiding.entity.framework.EntityBase;
import com.suiding.entity.framework.StoreBaseEntity;
import com.suiding.model.Hotel;
import com.suiding.util.UUIDUtil;


/**
 * HotelEntity
 * 
 * @author SCWANG
 *         Model.Hotel 在手机本地数据库 的实体
 */
public class HotelEntity extends StoreBaseEntity implements EntityBase<Hotel>
{
    //Hotel 属性
    public UUID ID = UUIDUtil.Empty;
    public int Level;

    public HotelEntity()
    {
    }

    public HotelEntity(Hotel tHotel)
    {
        super(tHotel);
        this.ID = tHotel.getHtl_ID();
        this.Level = tHotel.Level;
    }

    public Hotel getModel()
    {
        Hotel tHotel = new Hotel(super.getStoreBase());
        tHotel.setHtl_ID(ID);
        tHotel.Level = this.Level;
        return tHotel;
    }

    /**
     * List<Model> 转换 List<Entity>
     * 
     * @param ltEntity
     * @return
     */
    public static List<HotelEntity> listFormModel(List<Hotel> ltModel)
    {
        // TODO Auto-generated method stub
        List<HotelEntity> ltEntity = new ArrayList<HotelEntity>();
        for (Hotel model : ltModel)
        {
            ltEntity.add(new HotelEntity(model));
        }
        return ltEntity;
    }

    /**
     * List<Entity> 转换 List<Model>
     * 
     * @param ltEntity
     * @return
     */
    public static List<Hotel> listToModel(List<HotelEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        List<Hotel> ltModel = new ArrayList<Hotel>();
        for (HotelEntity entity : ltEntity)
        {
            ltModel.add(entity.getModel());
        }
        return ltModel;
    }
}
