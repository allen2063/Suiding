package com.suiding.entity;

import java.util.ArrayList;
import java.util.List;

import com.suiding.entity.framework.EntityBase;

import android.location.Location;


public class LocationEntity implements EntityBase<Location>
{
    public double Latitude = 0;
    public double Longitude = 0;
    public double Altitude = 0;
    public float Accuracy = 0;
    public float Bearing = 0;
    public float Speed = 0;
    public long Time = 0;
    public String Provider = "";

    public LocationEntity()
    {
    }

    public LocationEntity(Location tLocatioin)
    {
        Latitude = tLocatioin.getLatitude();
        Longitude = tLocatioin.getLongitude();
        Latitude = tLocatioin.getLatitude();
        Longitude = tLocatioin.getLongitude();
        Altitude = tLocatioin.getAltitude();
        Accuracy = tLocatioin.getAccuracy();
        Bearing = tLocatioin.getBearing();
        Speed = tLocatioin.getSpeed();
        Time = tLocatioin.getTime();
        Provider = tLocatioin.getProvider();
    }

    public Location getModel()
    {
        Location tModel = new Location(Provider);
        tModel.setLatitude(Latitude);
        tModel.setLongitude(Longitude);
        tModel.setLatitude(Latitude);
        tModel.setLongitude(Longitude);
        tModel.setAltitude(Altitude);
        tModel.setAccuracy(Accuracy);
        tModel.setBearing(Bearing);
        tModel.setSpeed(Speed);
        tModel.setTime(Time);
        tModel.setProvider(Provider);
        return tModel;
    }

    /**
     * List<Model> ×ª»» List<Entity> 
     * @param ltEntity
     * @return
     */
    public static List<LocationEntity> listFormModel(List<Location> ltModel)
    {
        // TODO Auto-generated method stub
        List<LocationEntity> ltEntity = new ArrayList<LocationEntity>();
        for (Location model : ltModel)
        {
            ltEntity.add(new LocationEntity(model));
        }
        return ltEntity;
    }
    /**
     * List<Entity> ×ª»» List<Model> 
     * @param ltEntity
     * @return
     */
    public static List<Location> listToModel(List<LocationEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        List<Location> ltModel = new ArrayList<Location>();
        for (LocationEntity entity : ltEntity)
        {
            ltModel.add(entity.getModel());
        }
        return ltModel;
    }
}
